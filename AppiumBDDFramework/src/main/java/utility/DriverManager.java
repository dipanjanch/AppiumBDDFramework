package utility;

import org.openqa.selenium.WebDriver;

import io.appium.java_client.AppiumDriver;

public class DriverManager {
	
	public static  WebDriver driver = null;
	
	public static WebDriver getDriver() {
		return  driver;		
	}
	
	public static  void setDriver(WebDriver Driver) {
		driver = Driver;		
	}

}
