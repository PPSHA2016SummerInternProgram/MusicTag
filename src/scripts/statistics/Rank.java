import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Rank {

	public static void rank(String filename) throws Exception {
		BufferedReader reader = new BufferedReader(new FileReader(filename));
		List<String> header = new ArrayList<>();
		List<String> mbid = new ArrayList<>();
		Map<String, List<Integer>> values = new HashMap<>();
		int rows = 0;
		for (String columnName : reader.readLine().split(",")) {
			header.add(columnName);
		}
		for (int i = 1; i < header.size(); i++) {
			values.put(header.get(i), new ArrayList<>());
		}
		String line;
		while ((line = reader.readLine()) != null) {
			String arr[] = line.split(",");
			String id = arr[0];
			mbid.add(id);
			for (int i = 1; i < arr.length; i++) {
				values.get(header.get(i)).add(Integer.parseInt(arr[i]));
			}
			rows++;
		}
		reader.close();
		for (int i = 1; i < header.size(); i++) {
			String colName = header.get(i);
			List<Integer> list = values.get(colName);
			List<Integer> score = calculateScore(colName, list);
			List<Integer> rank = calculateRank(list);
			values.put(colName + "_score", score);
			values.put(colName + "_rank", rank);
		}

		Map<String, List<String>> dimensionMap = new HashMap<>();
		Map<String, List<Double>> dimensionRateMap = new HashMap<>();
		dimensionMap.put("relationship", str2map(new String[] { "contacts_amount" }));
		dimensionRateMap.put("relationship", double2map(new double[] { 1.0 }));
		dimensionMap.put("popularity", str2map(new String[] { "edit_amount", "listener_amount", "play_amount" }));
		dimensionRateMap.put("popularity", double2map(new double[] { 0.2, 0.4, 0.4 }));
		dimensionMap.put("influence", str2map(new String[] { "country_amount" }));
		dimensionRateMap.put("influence", double2map(new double[] { 1.0 }));
		dimensionMap.put("productivity", str2map(new String[] { "recording_amount", "release_amount" }));
		dimensionRateMap.put("productivity", double2map(new double[] { 0.3, 0.7 }));
		dimensionMap.put("active_span", str2map(new String[] { "active_years" }));
		dimensionRateMap.put("active_span", double2map(new double[] { 1.0 }));
		for (Entry<String, List<String>> pair : dimensionMap.entrySet()) {
			String colName = pair.getKey();
			List<String> colNames = pair.getValue();
			List<Double> rates = dimensionRateMap.get(colName);
			int sz = colNames.size();
			List<Integer> score = new ArrayList<>();
			List<Integer> tmp_value = new ArrayList<>();
			for (int i = 0; i < rows; i++) {
				int scoreSum = 0;
				double valueSum = 0;
				for (int j = 0; j < colNames.size(); j++) {
					String subColName = colNames.get(j);
					Double rate = rates.get(j);
					scoreSum += values.get(subColName + "_score").get(i);
					valueSum += values.get(subColName).get(i) * rate;
				}
				scoreSum /= sz;
				score.add(scoreSum);
				tmp_value.add((int) valueSum);
			}
			values.put(colName + "_score", score);
			values.put(colName, tmp_value);
		}

		for (String colName : dimensionMap.keySet()) {
			List<Integer> list = values.get(colName);
			List<Integer> rank = calculateRank(list);
			values.put(colName + "_rank", rank);
		}

		for (int i = 0; i < mbid.size(); i++) {
			System.out.print("{\"gid\":\"" + mbid.get(i) + "\",");
			for (int j = 1; j < header.size(); j++) {
				String colName = header.get(j);
				System.out.print("\"" + colName + "\":" + values.get(colName).get(i) + ",");
				System.out.print("\"" + colName + "_score\":" + values.get(colName + "_score").get(i) + ",");
				System.out.print("\"" + colName + "_rank\":" + values.get(colName + "_rank").get(i) + ",");
			}
			int sz = dimensionMap.size();
			int amount = 0;
			for (String colName : dimensionMap.keySet()) {
				System.out.print("\"" + colName + "_score\":" + values.get(colName + "_score").get(i) + ",");
				System.out.print("\"" + colName + "_rank\":" + values.get(colName + "_rank").get(i));
				if (amount++ < sz - 1) {
					System.out.print(",");
				}
			}
			System.out.println("}");
		}
	}

	public static List<String> str2map(String[] arr) {
		List<String> list = new ArrayList<>();
		for (String s : arr) {
			list.add(s);
		}
		return list;
	}

	public static List<Double> double2map(double[] arr) {
		List<Double> list = new ArrayList<>();
		for (Double s : arr) {
			list.add(s);
		}
		return list;
	}

	public static List<Integer> calculateRank(List<Integer> list) {
		List<Integer> sort = new ArrayList<>(list);
		Collections.sort(sort);

		List<Integer> result = new ArrayList<>();
		int score[] = new int[101];
		int sz = sort.size();
		for (int i = 1; i <= 100; i++) {
			int index = (int) (i / 100.0 * sz);
			index = Math.max(0, index);
			index = Math.min(sz - 1, index);
			score[i - 1] = sort.get(index);
		}
		outer: for (Integer value : list) {
			for (int i = 1; i < score.length; i++) {
				if (score[i] > value) {
					result.add(i - 1);
					continue outer;
				}
			}
			result.add(100);
		}
		return result;
	}

	public static List<Integer> calculateScore(String colName, List<Integer> list) {
		List<Integer> score = new ArrayList<>();
		for (Integer amount : list) {
			int xAxis = 0;
			int min = 0;
			int max = 0;
			switch (colName) {
			case "edit_amount":
				xAxis = (int) (Math.log(1 + Math.pow(amount, 0.2)) * 15);
				min = 5;
				max = 25;
				break;
			case "recording_amount":
				xAxis = (int) (Math.log((1 + Math.pow(amount, 0.7))) * 5);
				min = 0;
				max = 20;
				break;
			case "release_amount":
				xAxis = (int) (Math.log((1 + Math.pow(amount, 0.6))) * 5);
				min = 0;
				max = 12;
				break;
			case "active_years":
				xAxis = (int) (((Math.pow(amount, 0.2) - 0.7)) * 10);
				min = 0;
				max = 17;
				break;
			case "contacts_amount":
				xAxis = (int) (((Math.pow(amount, 0.02) - 0.95)) * 100);
				min = 0;
				max = 15;
				break;
			case "country_amount":
				xAxis = (int) (Math.pow(amount, 0.48) * 5);
				min = 0;
				max = 20;
				break;
			case "listener_amount":
				xAxis = (int) (Math.log(amount + 1) * 1.5);
				min = 0;
				max = 27;
				break;
			case "play_amount":
				xAxis = (int) (Math.log(amount + 1) * 1.5);
				min = 0;
				max = 22;
				break;
			default:
			}
			int prettyXAxis = Math.min(Math.max(min, xAxis), max);
			int scoreValue = (int) ((double) (prettyXAxis - min) / (max - min) * 100);
			score.add(scoreValue);
		}
		return score;
	}

	public static void main(String[] args) throws Exception {
		rank("distribution.csv");
	}
}

