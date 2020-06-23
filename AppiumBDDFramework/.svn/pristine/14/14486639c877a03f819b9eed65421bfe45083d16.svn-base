package utility;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import org.json.simple.parser.ParseException;

import config.Constants;

public class TestContext {
	 private DriverManager driverManager;
	
	 
	 public TestContext(){
		 driverManager = new DriverManager();
	 }
	 
	 public DriverManager getWebDriverManager() {
	 return driverManager;
	 }
	  
	 public static String getDataFromDatalist(String Role, String Key)
	 {

			try {

				Object object =  new JSONParser().parse(new FileReader(Constants.dataListPath));
				JSONObject jsonObject = (JSONObject) object;
				JSONArray jsonArray = (JSONArray) jsonObject.get(Constants.Environment);
				JSONObject j = (JSONObject)jsonArray.get(0);
				JSONArray childArray = (JSONArray) j.get(Role);
				JSONObject childObject = (JSONObject)childArray.get(0);
				String value = (String) childObject.get(Key);

				return value;

				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return null;
		 
	 }
	 
	 public static String getDataFromDevicelist(String Device, String Key)
	 {

			try {

				Object object =  new JSONParser().parse(new FileReader(Constants.deviceListPath));
				JSONObject jsonObject = (JSONObject) object;
				JSONArray jsonArray = (JSONArray) jsonObject.get(Constants.Platform);
				JSONObject j = (JSONObject)jsonArray.get(0);
				JSONArray childArray = (JSONArray) j.get(Device);
				JSONObject childObject = (JSONObject)childArray.get(0);
				String value = (String) childObject.get(Key);

				return value;

				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return null;
		 
	 }
	 
	}
