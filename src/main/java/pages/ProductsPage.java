package pages;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;



public class ProductsPage {
	 private WebDriver driver;

	    private By productPrices = By.xpath("//div[contains(@class,'pricebar')]//div[contains(@class,'item_price')]");
	    private By productNames = By.xpath("//div[contains(@class,'inventory_item_name')]");
	    private By addToCartButtons = By.xpath("//div[contains(@class,'pricebar')]//button[contains(@id,'add-to-cart')]");
	    private By cartButton = By.id("shopping_cart_container");
	    private By checkoutButton = By.id("checkout");
	    private By firstNameField = By.xpath("//div[contains(@class, 'form_group')]//input[@id='first-name']");
	    private By lastNameField = By.xpath("//div[contains(@class, 'form_group')]//input[@id='last-name']");
	    private By postalCodeField = By.xpath("//div[contains(@class, 'form_group')]//input[@id='postal-code']");
	    private By continueButton = By.xpath("//div[contains(@class, 'checkout_buttons')]//input[@id='continue']");
	    private By finishButton = By.xpath("//div[contains(@class, 'cart_footer')]//button[@id='finish']");
	    private By cartItems = By.className("cart_item"); 
	    private By totalAmountLabel = By.className("summary_subtotal_label"); // Locator for the total amount before taxes
	    private By thankYouMessage = By.className("complete-header");
	    private By orderHasBeenDispached = By.className("complete-text");
//or  By.xpath = //div[contains(@class, 'checkout_complete')]//h2[@class='complete-header']
	    
	    private List<Map.Entry<String, String>> addedProducts = new ArrayList<>();

	    public ProductsPage(WebDriver driver) {
	        this.driver = driver;
	    }

	    public boolean isOnProductsPage() {
	        return driver.getCurrentUrl().contains("inventory.html");
	    }

	    public void addMostExpensiveProductsToCart() {
	        List<WebElement> prices = driver.findElements(productPrices);
	        List<WebElement> names = driver.findElements(productNames); 
	        List<WebElement> addButtons = driver.findElements(addToCartButtons);
	        
	        // Sort the products by price, descending
	        List<WebElement> sortedPrices = prices.stream()
	                .sorted((e1, e2) -> Double.compare(
	                        Double.parseDouble(e2.getText().replace("$", "")),
	                        Double.parseDouble(e1.getText().replace("$", ""))))
	                .collect(Collectors.toList());

	        // Add the two most expensive products to the cart
	        for (int i = 0; i < 2; i++) {
	            int index = prices.indexOf(sortedPrices.get(i));
	            WebElement addButton = addButtons.get(index);
	            
	            // Store both product name and price in the list
	            String productName = names.get(index).getText();
	            String productPrice = prices.get(index).getText();
	            addedProducts.add(new AbstractMap.SimpleEntry<>(productName, productPrice));
	            
	            System.out.println("Added product: " + addedProducts);
	            addButton.click();
	        }
	    }

	    public void clickCartButton() {
	        driver.findElement(cartButton).click();
	    }

	    public boolean isOnCartPage() {
	        return driver.getCurrentUrl().contains("cart.html");
	    }

	    public boolean areProductsInCart() {
	    	  // Get the names and prices of products displayed in the cart
	        List<WebElement> cartItemsList = driver.findElements(cartItems);
	        List<Map.Entry<String, String>> cartProducts = new ArrayList<>();

	        for (WebElement item : cartItemsList) {
	            String productName = item.findElement(By.className("inventory_item_name")).getText(); 
	            String productPrice = item.findElement(By.className("inventory_item_price")).getText(); 
	            cartProducts.add(new AbstractMap.SimpleEntry<>(productName, productPrice));
	        }

	        // Check if all added products (name and price) are displayed in the cart
	        return cartProducts.containsAll(addedProducts);
	    }
	       
	    

	    public void clickCheckoutButton() {
	        driver.findElement(checkoutButton).click();
	    }

	    public boolean isOnCheckoutPage() {
	        return driver.getCurrentUrl().contains("checkout-step-one.html");
	    }

	    public void fillCheckoutForm(String firstName, String lastName, String postalCode) {
	        driver.findElement(firstNameField).sendKeys(firstName);
	        driver.findElement(lastNameField).sendKeys(lastName);
	        driver.findElement(postalCodeField).sendKeys(postalCode);
	    }

	    public void clickContinueButton() {
	        driver.findElement(continueButton).click();
	    }

	    public boolean isOnOverviewPage() {
	        return driver.getCurrentUrl().contains("checkout-step-two.html");
	    }

	    public boolean isTotalAmountCorrect() {
	    	 // Extract the total amount displayed on the overview page
	        String displayedTotalText = driver.findElement(totalAmountLabel).getText();
	        double displayedTotal = Double.parseDouble(displayedTotalText.replace("Item total: $", "").trim());

	        // Calculate the expected total by summing the prices of the added products
	        double expectedTotal = addedProducts.stream()
	                .mapToDouble(entry -> Double.parseDouble(entry.getValue().replace("$", "")))
	                .sum();

	        // Compare the displayed total with the expected total
	        return Double.compare(displayedTotal, expectedTotal) == 0;
	    }

	    public void clickFinishButton() {
	        driver.findElement(finishButton).click();
	    }

	    public boolean isOrderComplete() {
	        return driver.findElement(thankYouMessage).isDisplayed() && driver.findElement(orderHasBeenDispached).isDisplayed();
	    }
}