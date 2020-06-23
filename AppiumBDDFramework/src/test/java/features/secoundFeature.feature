Feature: Validate second feature

  #Background: Create an instance of android or ios driver before each scenario
    #Given User has slideshare "android" app
    
   Scenario Outline: Feature 2 scenario 1
    Given "<User>" logs into the application
    When I tap on Accessibility
    Then I validate Custom View
	Examples:
    |User|
 	  |TMUser|
    
   Scenario Outline:  Feature 2 scenario 1
    Given "<User>" logs into the application
    When I tap on Accessibility
    Then I validate Custom View
	Examples:
    |User|
 	  |TMUser|
 	@test  
 	  Scenario Outline:  Feature 2 scenario 222
    Given "<User>" logs into the application
    When I tap on Accessibility
    Then I validate Custom View
	Examples:
    |User|
 	  |TMUser|