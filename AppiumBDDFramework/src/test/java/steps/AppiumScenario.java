package steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import page.actions.loginPage;


public class AppiumScenario {
	loginPage loginpage = new loginPage();

	
	@Given("^\"([^\"]*)\" logs into the application$")
	public void i_open_the_application(String user) throws Exception {
		System.out.println("wwwwww11wwwwwww");
		loginpage.loginToSFDCapp(user);
		
		
	}

	@When("^I tap on Accessibility$")
	public void i_tap_on_Accessibility() throws Exception {
		System.out.println("wwwww22wwwwwwww");
		
	}

	@Then("^I validate Custom View$")
	public void i_validate_Custom_View() throws Exception {
		System.out.println("wwwwww33wwwwwww");
	  
	}
}
