import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

class MyException extends Exception {

	private static final long serialVersionUID = 1L;

	public MyException(String msg) {
		super(msg);
	}
}

enum Operation {

	NO_CHANGE("Not changed.") {
		@Override
		public int operate(int value) {
			return value;
		}
	},
	LOG("Add 1, then log scale with e") {
		@Override
		public int operate(int value) {
			return (int) Math.log(1 + value);
		}
	};

	private String description;

	Operation(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public int operate(int value) {
		return value;
	}
}

public class Distribution {

	private String delimiter = ",";

	private List<Integer> readCsv(String filename, String colName) throws IOException, MyException {

		BufferedReader reader = new BufferedReader(new FileReader(filename));
		String header = reader.readLine();
		String[] cols = header.split(delimiter);
		int colIndex = -1;
		for (int i = 0; i < cols.length; i++) {
			if (cols[i].equals(colName)) {
				colIndex = i;
				break;
			}
		}

		if (colIndex < 0) {
			reader.close();
			throw new MyException("Cannot find column: " + colName);
		}

		List<Integer> result = new ArrayList<Integer>();
		String line;
		while ((line = reader.readLine()) != null) {
			String[] values = line.split(delimiter);
			result.add(Integer.parseInt(values[colIndex]));
		}
		reader.close();
		return result;
	}

	private void operate(List<Integer> list, Operation operation) {
		for (int i = 0; i < list.size(); i++) {
			list.set(i, operation.operate(list.get(i)));
		}
	}

	private Map<Integer, Integer> count(List<Integer> list) {
		Map<Integer, Integer> map = new HashMap<>();
		int value;
		for (int key : list) {
			value = 1;
			if (map.containsKey(key)) {
				value += map.get(key);
			}
			map.put(key, value);
		}
		return map;
	}

	private List<int[]> sort(Map<Integer, Integer> map) {
		List<int[]> list = new ArrayList<>();
		for (Entry<Integer, Integer> pair : map.entrySet()) {
			int[] arr = new int[2];
			arr[0] = pair.getKey();
			arr[1] = pair.getValue();
			list.add(arr);
		}
		Collections.sort(list, new Comparator<int[]>() {
			@Override
			public int compare(int[] o1, int[] o2) {
				if (o1[0] < o2[0]) {
					return -1;
				}
				if (o1[0] > o2[0]) {
					return 1;
				}
				return 0;
			}
		});
		return list;
	}

	private String mongoQuery = "db.distribution.";

	private String helper(String name, String value) {
		return "\"" + name + "\":\"" + value + "\"";
	}

	private void print(List<int[]> list, String colName, Operation operation) {

		String id = colName + " (" + operation.getDescription() + ")";
		System.out.println(mongoQuery + "remove({" + helper("id", id) + "})");
		System.out.print(mongoQuery + "insertOne({" + helper("id", id) + "," + helper("type", colName) + ","
				+ helper("description", operation.getDescription()) + ", \"data\":[");
		for (int i = 0; i < list.size(); i++) {
			System.out.print("[" + list.get(i)[0] + "," + list.get(i)[1] + "]");
			if (i < list.size() - 1) {
				System.out.print(",");
			}
		}
		System.out.println("]})");
	}

	public void calculate(String filename) throws IOException, MyException {
		String colNames[] = { "edit_amount", "country_amount", "release_amount", "recording_amount", "contacts_amount",
				"active_years", "listener_amount", "play_amount" };
		Operation operations[] = Operation.values();

		for (String colName : colNames) {
			for (Operation operation : operations) {
				List<Integer> amountList = readCsv(filename, colName);
				operate(amountList, operation);
				Map<Integer, Integer> map = count(amountList);
				List<int[]> list = sort(map);
				print(list, colName, operation);
			}
		}
	}

	public static void main(String[] args) throws IOException, MyException {

		Distribution d = new Distribution();
		d.calculate("distribution.csv");
	}
}

