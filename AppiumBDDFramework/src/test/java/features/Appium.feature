
Feature: Validate Different Options

  #Background: Create an instance of android or ios driver before each scenario
    #Given User has slideshare "android" app
@test
   Scenario Outline: Validate login Page
    Given "<User>" logs into the application
    When I tap on Accessibility
    Then I validate Custom View
	Examples:
    |User|
 	  |TMUser|
    
 
   Scenario Outline: Validate login Page22
    Given "<User>" logs into the application
    When I tap on Accessibility
    Then I validate Custom View
	Examples:
    |User|
 	  |TMUser|
