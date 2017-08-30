package websocket.chat;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DataReader {
	/*
	 * public static void main(String[] args) throws ParseException, IOException
	 * { getTheDataFromCSV(); }
	 * 
	 */
	
	

	public static List<Map<String, String>> getTheDataFromCSV(String csvPath) {
		System.out.println("starting reading");
		List<Map<String, String>> columnsData = new ArrayList<>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(csvPath));

			String line = null;
			List<String> columnNames = new ArrayList<>();

			if ((line = br.readLine()) != null) {
				String str[] = line.split(",");
				columnNames.addAll(Arrays.asList(str));
			}
			// System.out.println(columnNames);

			while ((line = br.readLine()) != null) {
				String str[] = line.split(",");
				Map<String, String> map = new LinkedHashMap<String, String>();
				for (int i = 1; i < str.length; i++) {
					// String arr[] = str[i].split(":");
					// System.out.println(str[i]);
					map.put(columnNames.get(i), str[i]);
				}
				columnsData.add(map);
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return columnsData;

	}
}