Feature: Login Feature

  Scenario: Login with invalid credentials
    Given I am on the login page
    When I login with invalid credentials I see an error message

  Scenario: Login with valid credentials
    Given I am on the login page
    When I login with valid credentials
    Then I should see the products page