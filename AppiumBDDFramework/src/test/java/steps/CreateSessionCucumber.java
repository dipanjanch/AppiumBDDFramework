package steps;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import logger.Log;
import runner.CucumberRunnerUtil;
import utility.DriverManager;
import utility.TestContext;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import com.cucumber.listener.Reporter;
import com.google.common.io.Files;

import config.Constants;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;
import java.util.Properties;


/**
 * contains all the methods to create a new session and destroy the 
 * session after the test(s) execution is over. 
 */
public class CreateSessionCucumber {

	public  WebDriver driver = null;
	String OS;

	private AppiumDriverLocalService service;
	private AppiumServiceBuilder builder;
	private DesiredCapabilities cap;


TestContext testContext;
	
	public CreateSessionCucumber(TestContext context) {
		testContext = context;
	}

	/** 
	 * this method starts Appium server. Calls startAppiumServer method to start the session depending upon your OS.
	 * @throws Exception Unable to start appium server
	 */
	public void invokeAppium() throws Exception
	{
		Log.info("Starting Appium server");
		OS = System.getProperty("os.name").toLowerCase();
		try{
			startAppiumServer(OS);
			Log.info("Appium server started successfully");
		}
		catch (Exception e) {
			Log.logError(getClass().getName(), "startAppium", "Unable to start appium server");
			throw new Exception(e.getMessage());
		}
	}

	/** 
	 * this method stops Appium server.Calls stopAppiumServer method to 
	 * stop session depending upon your OS.
	 * @throws Exception Unable to stop appium server
	 */
	public void stopAppium() throws Exception {
		Log.info("Stopping Appium server");
		try{
			stopAppiumServer(OS);
			Log.info("Appium server stopped successfully");

		}
		catch (Exception e) {
			Log.logError(getClass().getName(), "stopAppium", "Unable to stop appium server");
			throw new Exception(e.getMessage());
		}
	}


	/** 
	 * this method creates the driver depending upon the passed parameter (android or iOS)
	 * @throws Exception issue while loading properties files or creation of driver.
	 */
	@Before
	public  void createDriver() throws Exception{
		System.out.println("BeforeScenario");
		Log.info("------------------Before Scenario--------------------");

		if (Constants.Platform.equalsIgnoreCase("android")){
			androidDriver();
			Log.info("Android driver created");

		}																		         
		else if (Constants.Platform.equalsIgnoreCase("iOS")){
			iOSDriver();
			Log.info("iOS driver created");
		}
	}
	
	/** 
	 * This method will take Screenshot after every failed Scenario 
	 */
	 @After(order = 1)
	 public void afterScenario(Scenario scenario) {
	 if (scenario.isFailed()) {
		 if(scenario.isFailed()) {
	            try {
	            	Log.info("---------Adding Screenshot---------");
	                byte[] screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES);
	                scenario.embed(screenshot, "image/png");
	            } catch (WebDriverException somePlatformsDontSupportScreenshots) {
	                System.err.println(somePlatformsDontSupportScreenshots.getMessage());
	            }

	        }	 
	 
	 }
	 }

	/** 
	 * this method quit the driver after the execution of test(s) 
	 */
	@After(order = 0)
	public void teardown(){
		Log.info("Shutting down driver");
		driver.quit();
		Log.info("---------Driver Quited---------");
	}



	/** 
	 *  this method creates the android driver
	 *  @param buildPath - path to pick the location of the app
	 *  @param methodName - name of the method under execution 
	 * @throws MalformedURLException Thrown to indicate that a malformed URL has occurred.
	 */
	public synchronized void androidDriver() throws MalformedURLException{
		
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("deviceName", TestContext.getDataFromDevicelist(Constants.AndroidDeviceName, "deviceName"));
		capabilities.setCapability("platformName","Android");
		capabilities.setCapability("UDID",TestContext.getDataFromDevicelist(Constants.AndroidDeviceName, "udid"));
		if(!Constants.installAppFromProject)
		{
		capabilities.setCapability("appPackage", TestContext.getDataFromDevicelist(Constants.AndroidDeviceName, "appPackage"));
		capabilities.setCapability("appActivity",TestContext.getDataFromDevicelist(Constants.AndroidDeviceName, "appActivity"));
		}
		else
		{
		File app = new File(Constants.androidAppPath);
		capabilities.setCapability("app", app.getAbsolutePath());
		}
		capabilities.setCapability(MobileCapabilityType.NO_RESET, false);
		capabilities.setCapability("automationName", "UiAutomator2");
		driver = new AndroidDriver( new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
		DriverManager.setDriver(driver);
		//sending the driver instance to a static driverManagerSetter and in that class a getter will be used everywhere to get driver instance.
			
	}

	/** 
	 *  this method creates the iOS driver
	 *  @param buildPath- path to pick the location of the app
	 *  @param methodName- name of the method under execution  Method methodName
	 * @throws MalformedURLException Thrown to indicate that a malformed URL has occurred.
	 */
	public void iOSDriver() throws MalformedURLException {

		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("platformName", "iOS");
		capabilities.setCapability("platformVersion", TestContext.getDataFromDevicelist(Constants.iOSDeviceName, "osVersion"));
	    capabilities.setCapability("deviceName", TestContext.getDataFromDevicelist(Constants.iOSDeviceName, "deviceName"));
	    capabilities.setCapability("noReset", "true");
	    capabilities.setCapability("automationName", "XCUITest");
	    capabilities.setCapability("app",TestContext.getDataFromDevicelist(Constants.iOSDeviceName, "appToTest"));
	    capabilities.setCapability("udid", TestContext.getDataFromDevicelist(Constants.iOSDeviceName, "udid"));
	    driver = new IOSDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
	    DriverManager.setDriver(driver);
	    //sending the driver instance to a static driverManagerSetter and in that class a getter will be used everywhere to get driver instance.
	}



	/** 
	 *  this method starts the appium  server depending on your OS.
	 * @param os your machine OS (windows/linux/mac)
	 * @throws IOException Signals that an I/O exception of some sort has occurred
	 * @throws ExecuteException An exception indicating that the executing a subprocesses failed
	 * @throws InterruptedException Thrown when a thread is waiting, sleeping, 
	 * or otherwise occupied, and the thread is interrupted, either before
	 *  or during the activity.
	 */
	public void startAppiumServer(String os) throws ExecuteException, IOException, InterruptedException{
		Log.info("## The platform is ## "+ os);
		if (os.contains("windows")){
			// need to add it  
		}
		else if (os.contains("mac os x")){
			cap = new DesiredCapabilities();
			cap.setCapability("noReset", "false");
			
			//Build the Appium service
			builder = new AppiumServiceBuilder();
			builder.withIPAddress("127.0.0.1");
			builder.usingPort(4723);
			builder.withCapabilities(cap);
			builder.withArgument(GeneralServerFlag.SESSION_OVERRIDE);
			builder.withArgument(GeneralServerFlag.LOG_LEVEL,"error");
			
			//Start the server with the builder
			service = AppiumDriverLocalService.buildService(builder);
			service.start();
			
												
		}
		else if (os.contains("linux")){
			// need to add it	

		}
		else{
			Log.info(os + "is not supported yet");
		}
	}

	/** 
	 *  this method stops the appium  server.
	 * @param os your machine OS (windows/linux/mac).
	 * @throws IOException Signals that an I/O exception of some sort has occurred. 
	 * @throws ExecuteException An exception indicating that the executing a subprocesses failed.
	 */
	public void stopAppiumServer(String os) throws ExecuteException, IOException {
		if (os.contains("windows")){
			// need to add it
		}
		else if (os.contains("mac os x")){
			service.stop();

		}
		else if (os.contains("linux")){
			// need to add it
		}
	}




}

