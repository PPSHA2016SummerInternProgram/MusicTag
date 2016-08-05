import java.util.*;
import java.io.*;
import java.util.Map.Entry;

public class GenerateHotInfo{

	public static void main(String[] args) throws IOException{
		if(args.length != 1){
			System.out.println("1 args is needed.");
			return;
		}

		String collectionAndType = args[0];

		Map<Integer, Integer> map = readHot(collectionAndType);
		List<int[]> list = map2list(map);
		sortList(list);
		printMongoInsertScript(collectionAndType, list);
	}

	static Map<Integer, Integer> readHot(String filename) throws IOException{
		BufferedReader reader = new BufferedReader(new FileReader(filename));
		String line;
		Map<Integer, Integer> map = new HashMap<>();
		while((line = reader.readLine()) != null){
			try{
				int amount = Integer.parseInt(line);
				int key = (int) (Math.log(amount+1) * 100);
				int value = 0;
				if(map.containsKey(key)){
					value = map.get(key);
				}
				value++;
				map.put(key, value);
			}catch(Exception e){
			}
		}
		return map;
	}

	static List<int[]> map2list(Map<Integer, Integer> map) {
		List<int[]> list = new ArrayList<>(map.size());
		for (Entry<Integer, Integer> pair : map.entrySet()) {
			int arr[] = new int[2];
			arr[0] = pair.getKey();
			arr[1] = pair.getValue();
			list.add(arr);
		}
		return list;
	}

	static void sortList(List<int[]> list) {
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
	}

	static void printMongoInsertScript(String key, List<int[]> list) {
		System.out.println("db.statistics.remove({type:'" + key + "'})");
		System.out.print("db.statistics.insertOne({type:'" + key + "', data:[");

		int sz = list.size();
		for (int i = 0; i < sz; i++) {
			int[] arr = list.get(i);
			System.out.print("[" + arr[0] + "," + arr[1] + "]");
			if (i != sz - 1) {
				System.out.print(",");
			}
		}

		System.out.print("]})");
	}
}
