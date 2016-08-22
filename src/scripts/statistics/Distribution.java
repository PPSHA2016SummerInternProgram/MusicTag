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

public class Distribution {

	private String delimiter = ",";
	private Map<String, List<Integer>> cache = new HashMap<>();

	private List<Integer> readCsv(String colName) throws IOException, MyException {
		String filename = "distribution.csv";
		return readCsv(filename, colName);
	}

	private List<Integer> readCsv(String filename, String colName) throws IOException, MyException {

		if (cache.containsKey(colName)) {
			return cache.get(colName);
		}

		BufferedReader reader = new BufferedReader(new FileReader(filename));
		String header = reader.readLine();
		String[] cols = header.split(delimiter);

		String line;
		while ((line = reader.readLine()) != null) {
			String[] values = line.split(delimiter);
			for (int i = 1; i < values.length; i++) {
				if (!cache.containsKey(cols[i])) {
					cache.put(cols[i], new ArrayList<Integer>());
				}
				List<Integer> list = cache.get(cols[i]);
				list.add(Integer.parseInt(values[i]));
			}
		}
		reader.close();
		for (Entry<?, List<Integer>> pair : cache.entrySet()) {
			Collections.sort(pair.getValue());
		}

		if (cache.containsKey(colName)) {
			return cache.get(colName);
		} else {
			throw new MyException("ERROR:" + colName);
		}
	}

	private List<Integer> operate(List<Integer> list, Operation operation) {
		List<Integer> result = new ArrayList<>(list.size());
		for (int i = 0; i < list.size(); i++) {
			result.add(operation.operate(list.get(i)));
		}
		return result;
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

	private void print(List<int[]> list, String colName, String description) {

		String id = colName + " (" + description + ")";
		System.out.println(mongoQuery + "remove({" + helper("id", id) + "})");
		System.out.print(mongoQuery + "insertOne({" + helper("id", id) + "," + helper("type", colName) + ","
				+ helper("description", description) + ", \"data\":[");
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
				for (int i = 0; i < 4; i++) {
					List<Integer> amountList = readCsv(filename, colName);
					String description = operation.getDescription();
					switch (i) {
					case 0:
						break;
					case 1:
						amountList = removeMin(amountList, 0);
						description += ", remove <=0 items";
						break;
					case 2:
						amountList = removeMin(amountList, 1);
						description += ", remove <=1 items";
						break;
					case 3:
						amountList = removeMax(amountList, 100000);
						description += ", remove >=100000 items";
						break;
					default:
					}
					amountList = operate(amountList, operation);
					Map<Integer, Integer> map = count(amountList);
					List<int[]> list = sort(map);
					print(list, colName, description);
				}
			}
		}
	}

	private List<Integer> removeMin(List<Integer> list, int min) {
		List<Integer> result = new ArrayList<>(list.size());
		for (int i = 0; i < list.size(); i++) {
			int value = list.get(i);
			if (value > min) {
				result.add(value);
			}
		}
		return result;
	}

	private List<Integer> removeMax(List<Integer> list, int max) {
		List<Integer> result = new ArrayList<>(list.size());
		for (int i = 0; i < list.size(); i++) {
			int value = list.get(i);
			if (value < max) {
				result.add(value);
			}
		}
		return result;
	}

	private List<Integer> removeMaxPer(List<Integer> list, double rate){
		int sz = list.size();
		int count = (int)(sz*rate);
		List<Integer> result = new ArrayList<>(list.size());
                for (int i = 0; i < list.size()-count; i++) {
                        int value = list.get(i);
                        result.add(value);
                }   
                return result;
	}

	private List<int[]> addZero(List<int[]> list) {

		List<int[]> result = new ArrayList<>();
		int index = 0;
		for (int[] pair : list) {
			while (index < pair[0]) {
				int arr[] = new int[2];
				arr[0] = index++;
				arr[1] = 0;
				result.add(arr);
			}
			result.add(pair);
			index++;
		}
		return result;
	}

	public static void printD()throws Exception{
		String filename = "distribution.csv";
                String colName = "country_amount";
		Distribution d = new Distribution();
		List<Integer> amountList = d.readCsv(filename, colName);
//		amountList = d.removeMin(amountList, 1);
//		amountList = d.removeMax(amountList, 1);
		amountList = d.removeMaxPer(amountList, 0.0001);
		int sz = amountList.size();
		for(int i=0; i<sz; i++){
			if(i<5 || i==sz/2 || i>sz-30){
				System.out.println(i + ": " + amountList.get(i));
			}
		}
	}

	public static void printEditAmount(Distribution d) throws Exception {
                String colName = "edit_amount";
                Operation operation = Operation.EDIT_AMOUNT;
		List<Integer> amountList = d.readCsv(colName);
		amountList = d.operate(amountList, operation);
                Map<Integer, Integer> map = d.count(amountList);
                List<int[]> list = d.sort(map);
                list = d.addZero(list);
		d.print(list, colName, operation.getDescription());
	}
	public static void printRecordingAmount(Distribution d) throws Exception {
                String colName = "recording_amount";
                Operation operation = Operation.RECORDING_AMOUNT;
		List<Integer> amountList = d.readCsv(colName);
		amountList = d.removeMin(amountList, 1);
		amountList = d.removeMaxPer(amountList, 0.0001);
		amountList = d.operate(amountList, operation);
                Map<Integer, Integer> map = d.count(amountList);
                List<int[]> list = d.sort(map);
                list = d.addZero(list);
		d.print(list, colName, operation.getDescription());
	}
	public static void printReleaseAmount(Distribution d) throws Exception {
                String colName = "release_amount";
                Operation operation = Operation.RELEASE_AMOUNT;
		List<Integer> amountList = d.readCsv(colName);
		amountList = d.removeMin(amountList, 2);
		amountList = d.removeMaxPer(amountList, 0.0001);
		amountList = d.operate(amountList, operation);
                Map<Integer, Integer> map = d.count(amountList);
                List<int[]> list = d.sort(map);
                list = d.addZero(list);
		d.print(list, colName, operation.getDescription());
	}
	public static void printActiveYears(Distribution d) throws Exception {
                String colName = "active_years";
                Operation operation = Operation.ACTIVE_YEARS;
		List<Integer> amountList = d.readCsv(colName);
		amountList = d.removeMin(amountList, 1);
		amountList = d.operate(amountList, operation);
                Map<Integer, Integer> map = d.count(amountList);
                List<int[]> list = d.sort(map);
                list = d.addZero(list);
		d.print(list, colName, operation.getDescription());
	}
	public static void printContactsAmount(Distribution d) throws Exception {
                String colName = "contacts_amount";
                Operation operation = Operation.CONTACTS_AMOUNT;
		List<Integer> amountList = d.readCsv(colName);
		amountList = d.removeMin(amountList, 1);
		amountList = d.operate(amountList, operation);
                Map<Integer, Integer> map = d.count(amountList);
                List<int[]> list = d.sort(map);
                list = d.addZero(list);
		d.print(list, colName, operation.getDescription());
	}
	public static void printCountryAmount(Distribution d) throws Exception {
                String colName = "country_amount";
                Operation operation = Operation.COUNTRY_AMOUNT;
		List<Integer> amountList = d.readCsv(colName);
		amountList = d.removeMin(amountList, 2);
		amountList = d.removeMax(amountList, 50);
		amountList = d.operate(amountList, operation);
                Map<Integer, Integer> map = d.count(amountList);
                List<int[]> list = d.sort(map);
                list = d.addZero(list);
		d.print(list, colName, operation.getDescription());
	}
	public static void printPlayAmount(Distribution d) throws Exception {
                String colName = "play_amount";
                Operation operation = Operation.PLAY_AMOUNT;
		List<Integer> amountList = d.readCsv(colName);
		amountList = d.operate(amountList, operation);
                Map<Integer, Integer> map = d.count(amountList);
                List<int[]> list = d.sort(map);
                list = d.addZero(list);
		d.print(list, colName, operation.getDescription());
	}
	public static void printListenerAmount(Distribution d) throws Exception {
                String colName = "listener_amount";
                Operation operation = Operation.LISTENER_AMOUNT;
		List<Integer> amountList = d.readCsv(colName);
		amountList = d.operate(amountList, operation);
                Map<Integer, Integer> map = d.count(amountList);
                List<int[]> list = d.sort(map);
                list = d.addZero(list);
		d.print(list, colName, operation.getDescription());
	}

	public static void printMongo()throws Exception{
		System.out.println("db.distribution.drop()");

		String filename = "distribution.csv";
		String colName = "play_amount";
		Operation operation = Operation.TEST;
		Distribution d = new Distribution();
		for(int i=1; i<50; i++){
			List<Integer> amountList = d.readCsv(filename, colName);
			amountList = d.removeMaxPer(amountList, 0.0001);
			double rate = 0.0 + i/100.0;
			operation.setRate(rate);
			amountList = d.operate(amountList, operation);
			Map<Integer, Integer> map = d.count(amountList);
			List<int[]> list = d.sort(map);
			list = d.addZero(list);
			d.print(list, colName, operation.getDescription() + " (" + (rate) + ")");
		}

	}

	public static void main(String[] args) throws Exception{
//		printD();
//		printMongo();
		System.out.println("db.distribution.drop()");
		Distribution d = new Distribution();
		printEditAmount(d);
		printRecordingAmount(d);
		printReleaseAmount(d);
		printActiveYears(d);
		printContactsAmount(d);
		printCountryAmount(d);
		printListenerAmount(d);
		printPlayAmount(d);
	}
}

enum Operation {

	EDIT_AMOUNT("log(1 + value^0.2) * 15") {
		@Override
		public int operate(int value){
			return (int) (Math.log(1 + Math.pow(value, 0.2)) * 15);
		}
	},

	RECORDING_AMOUNT("log(1 + value^0.7) * 5") {
		@Override
		public int operate(int value){
			return (int) (Math.log((1 + Math.pow(value, 0.7)))*5);
		}
	},
	RELEASE_AMOUNT("(1 + value^0.6) * 5") {
		@Override
		public int operate(int value){
			return (int) (Math.log((1 + Math.pow(value, 0.6)))*5);
		}
	},
	ACTIVE_YEARS("(value^0.2-0.7) * 10") {
		@Override
		public int operate(int value){
			return (int) (((Math.pow(value, 0.2)-0.7))*10);
		}
	},
	CONTACTS_AMOUNT("(value^0.02 - 0.95) * 100") {
		@Override
		public int operate(int value){
			return (int) (((Math.pow(value, 0.02)-0.95))*100);
		}
	},
	COUNTRY_AMOUNT("value^0.48 * 5") {
		@Override
		public int operate(int value){
			return (int) (((Math.pow(value, 0.48) * 5)));
		}
	},
	LISTENER_AMOUNT("log(1 + value) * 1.5") {
		@Override
		public int operate(int value){
			return (int) (Math.log(value + 1) *1.5 );
		}
	},
	PLAY_AMOUNT("log(1 + value) * 1.5") {
		@Override
		public int operate(int value){
			return (int) (Math.log(value + 1) *1.5 );
		}
	},


	TEST("TEST") {
		@Override
		public int operate(int value) {
			return (int) (Math.log(value + 1) *1.5 );
		}
	},

	NO_CHANGE("Not changed.") {
		@Override
		public int operate(int value) {
			return value;
		}
	},
	DIV_100("Divide 100") {
		@Override
		public int operate(int value) {
			return (int) value / 100;
		}
	},
	DIV_1000("Divide 1000") {
		@Override
		public int operate(int value) {
			return (int) value / 1000;
		}
	},
	LOG("Add 1, then log scale with e") {
		@Override
		public int operate(int value) {
			return (int) Math.log(1 + value);
		}
	},
	LOG5("Add 1, then log scale with e, then multiply 5") {
		@Override
		public int operate(int value) {
			return (int) (Math.log(1 + value) * 5);
		}
	},
	LOG10("Add 1, then log scale with e, then multiply 10") {
		@Override
		public int operate(int value) {
			return (int) (Math.log(1 + value) * 10);
		}
	},
	LOG20("Add 1, then log scale with e, then multiply 20") {
		@Override
		public int operate(int value) {
			return (int) (Math.log(1 + value) * 20);
		}
	},
	LOG100("Add 1, then log scale with e, then multiply 100") {
		@Override
		public int operate(int value) {
			return (int) (Math.log(1 + value) * 100);
		}
	},
	ROOT2("Extraction of root 2") {
		@Override
		public int operate(int value) {
			return (int) Math.pow(value, 1.0 / 2);
		}
	},
	ROOT3("Extraction of root 3") {
		@Override
		public int operate(int value) {
			return (int) Math.pow(value, 1.0 / 3);
		}
	},
	ROOT10("Extraction of root 10") {
		@Override
		public int operate(int value) {
			return (int) Math.pow(value, 1.0 / 10);
		}
	};

	private String description;
	double powRate = 1;

	Operation(String description) {
		this.description = description;
	}

	public void setRate(double d){
		this.powRate = d;
	}

	public String getDescription() {
		return description;
	}

	public int operate(int value) {
		return value;
	}
}

