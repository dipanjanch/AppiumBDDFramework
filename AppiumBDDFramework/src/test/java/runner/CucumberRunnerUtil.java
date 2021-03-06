package runner;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import cucumber.api.testng.AbstractTestNGCucumberTests;
import cucumber.api.testng.CucumberFeatureWrapper;
import cucumber.api.testng.TestNGCucumberRunner;
import steps.CreateSessionCucumber;
import utility.GenericMethods;
import utility.TestContext;

import com.relevantcodes.extentreports.DisplayOrder;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.NetworkMode;

import config.Constants;

import org.junit.runner.RunWith;
import org.testng.annotations.*;

import com.cucumber.listener.ExtentCucumberFormatter;
import com.cucumber.listener.Reporter;

import java.io.File;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


@RunWith(Cucumber.class)
@CucumberOptions(
        monochrome = true,
        features = "src/test/java/features",
        glue = {"steps"},
        plugin = {"com.cucumber.listener.ExtentCucumberFormatter"}

)
public class CucumberRunnerUtil{

    private TestNGCucumberRunner testNGCucumberRunner;
    public static String timeStamp=new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
    TestContext testcontext = new TestContext();
    CreateSessionCucumber sessionObj= new CreateSessionCucumber(testcontext);

    @BeforeSuite(alwaysRun = true)
    public void setCreateSession() throws Exception { 	
     // write if anything needs to be set up once before tests run.
    	if(Constants.startAppiumFromCode)
    		sessionObj.invokeAppium();
    	
    }
    

    /**
     * In @BeforeClass The Extentreport is setup and the Locators(android/iOS) property file is loaded.
     */
    @BeforeClass(alwaysRun = true)
    public void setUpClass() throws Exception {
    	testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
	    ExtentCucumberFormatter.initiateExtentCucumberFormatter("output/reports", 
	    		Boolean.TRUE, DisplayOrder.NEWEST_FIRST, NetworkMode.OFFLINE, Locale.ENGLISH);
		ExtentCucumberFormatter.loadConfig(new File("src/test/resources/extent-config.xml"));
		
		if(Constants.Platform.equalsIgnoreCase("android"))
			GenericMethods.locatorsReader(Constants.androidLocators);
		else if(Constants.Platform.equalsIgnoreCase("ios"))
			GenericMethods.locatorsReader(Constants.iosLocators);
        
    }




    @Test(groups = "cucumber", description = "Runs Cucumber Feature",dataProvider = "features" )
    public void feature(CucumberFeatureWrapper cucumberFeature) {
        testNGCucumberRunner.runCucumber(cucumberFeature.getCucumberFeature());
    }


    @DataProvider
    public Object[][] features() {
        return testNGCucumberRunner.provideFeatures();
    }



    @AfterClass(alwaysRun = true)
    public void tearDownClass() throws Exception {
        testNGCucumberRunner.finish();
               
    }

    @AfterSuite(alwaysRun = true)
    public void setDestroySession() throws Exception {
     // write if anything needs to be destroy up once after tests ran.
    	if(Constants.startAppiumFromCode)
    		sessionObj.stopAppium();
    }


}
