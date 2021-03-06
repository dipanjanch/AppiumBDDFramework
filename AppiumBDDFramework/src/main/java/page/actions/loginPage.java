package page.actions;

import org.testng.Assert;
import org.openqa.selenium.WebDriver;
import utility.GenericMethods;
import utility.TestContext;

public class loginPage{
	GenericMethods appiumLibrary = new GenericMethods();
	
	public void loginToSFDCapp(String user) throws InterruptedException {

		Thread.sleep(2000);
		
		if(appiumLibrary.isElementPresent("welcomeScreen.UserProfilePhoto"))
		{
			appiumLibrary.click("welcomeScreen.UserProfilePhoto");
			appiumLibrary.click("welcomeScreen.LogoutOption");
			if(appiumLibrary.isElementPresent("welcomeScreen.LogoutPopUp"))
				appiumLibrary.click("welcomeScreen.LogoutPopUp");
		}
		
		if(appiumLibrary.isAndroid()) {
			appiumLibrary.click("welcomeScreen.OrderFormSuppliment");		
			appiumLibrary.waitForElement("welcomeScreen.SettingIcon");
			appiumLibrary.click("welcomeScreen.SettingIcon");
			appiumLibrary.click("welcomeScreen.ChangeServer");
			appiumLibrary.click("welcomeScreen.SandboxOption");
			appiumLibrary.click("welcomeScreen.ChangeServerApply");
		}
		if(appiumLibrary.isiOS()) {
			appiumLibrary.waitForElement("welcomeScreen.SettingIcon");
			appiumLibrary.click("welcomeScreen.SettingIcon");
			appiumLibrary.click("welcomeScreen.SandboxOption");
		}
		Assert.assertTrue(false, "Login Sucessfull");
		appiumLibrary.click("welcomeScreen.UserNameTextBox");
		appiumLibrary.sendText("welcomeScreen.UserNameTextBox", TestContext.getDataFromDatalist(user, "username"));
		appiumLibrary.click("welcomeScreen.PasswordTextBox");
		appiumLibrary.sendText("welcomeScreen.PasswordTextBox", TestContext.getDataFromDatalist(user, "password"));
		appiumLibrary.click("welcomeScreen.LoginButton");
		
		if(appiumLibrary.waitForElement(("welcomeScreen.AllowAccess")))
			appiumLibrary.click("welcomeScreen.Allow");
		
		if(appiumLibrary.waitForElement("welcomeScreen.UserProfilePhoto"))
			Assert.assertTrue(false, "Login Sucessfull");
		else 
			Assert.assertFalse(true, "Login Unsucessfull");
		
		
		
	}



}
