package stepdefinitions;

import org.junit.Assert;

import base.BaseTest;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.LoginPage;
import pages.ProductsPage;

public class PurchaseSteps extends BaseTest {
	LoginPage loginPage;
	ProductsPage productsPage;

	@Given("I am logged in with valid credentials")
	public void i_am_on_the_login_page() {
		setUp();
		loginPage = new LoginPage(driver);
		loginPage.login("standard_user", "secret_sauce");
		Assert.assertTrue(loginPage.getTitle().contains("Products"));
	}

	@When("I add the most expensive two products to the cart")
	public void i_add_the_two_most_expensive_products_to_the_cart() {
		productsPage = new ProductsPage(driver);
		productsPage.addMostExpensiveProductsToCart();
	}

	@And("I click on the cart button")
	public void i_click_on_the_cart_button() {
		productsPage.clickCartButton();
		Assert.assertTrue(productsPage.isOnCartPage());
		Assert.assertTrue(productsPage.areProductsInCart());
	}
	
	@And("I proceed to checkout")
	public void i_proceed_to_checkout() {
		productsPage.clickCheckoutButton();
		Assert.assertTrue(productsPage.isOnCheckoutPage());
		
	}

	@And("I fill Checkout Form")
	public void i_fill_Checkout_Form() {
		productsPage.fillCheckoutForm("John", "Doe", "12345");
		
	}
	
	@And("I click on Continue")
	public void i_click_on_Continue() {
		productsPage.clickContinueButton();
	}
	
	@Then("I should be navigated to the Overview page")
	public void i_should_be_navigated_to_the_Overview_page() {
		Assert.assertTrue(productsPage.isOnOverviewPage());
	}
	
	@And("I should see the correct total amount before taxes")
	public void i_should_see_the_correct_total_amount_before_taxes() {
		productsPage.isTotalAmountCorrect();
	}

	@And("the URL should match the checkout step two URL")
	public void the_url_should_match_the_checkout_step_two_url() {
		String currentUrl = driver.getCurrentUrl();
		Assert.assertEquals("https://www.saucedemo.com/checkout-step-two.html", currentUrl);
	}
	
	@And("I click on finish")
	public void i_click_on_finish() {
		productsPage.clickFinishButton();
	}
	
	@And("Thank You and order has been dispatched messages appear")
	public void the_Thank_You_and_the_order_has_been_dispatched_messages_appear() {
		productsPage.isOrderComplete();
		tearDown();
	}

}