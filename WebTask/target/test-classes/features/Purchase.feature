Feature: Purchase Products

  Scenario: Purchase the most expensive products
    Given I am logged in with valid credentials
    When I add the most expensive two products to the cart
    And I click on the cart button
    And I proceed to checkout
    And I fill Checkout Form
    And I click on Continue
    Then I should be navigated to the Overview page
    And I should see the correct total amount before taxes
    And the URL should match the checkout step two URL
    And I click on finish
    And Thank You and order has been dispatched messages appear