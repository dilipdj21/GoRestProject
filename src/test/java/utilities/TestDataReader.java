package utilities;

import org.json.simple.parser.*;
import java.io.*;
import org.json.simple.JSONObject;

public class TestDataReader {
	private static JSONObject jsonObject = null;

	public static void init() {
		if (jsonObject == null) {
			JSONParser parser = new JSONParser();
			Reader reader = null;

			try {
				reader = new FileReader("src/test/resources/testData.json");
				Object jsonObj = parser.parse(reader);
				jsonObject = (JSONObject) jsonObj;
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	public static Object getData(String testCaseName) {
		init();
		return jsonObject.get(testCaseName);

//		return null;

	}

}
