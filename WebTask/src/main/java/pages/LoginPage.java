package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
	  WebDriver driver;

	    @FindBy(id = "user-name")
	    WebElement usernameField;

	    @FindBy(id = "password")
	    WebElement passwordField;

	    @FindBy(id = "login-button")
	    WebElement loginButton;
	    
	    @FindBy(xpath = "//div[contains(@class,'error-message')]//h3")
	    WebElement errorMessage;

	    public LoginPage(WebDriver driver) {
	        this.driver = driver;
	        PageFactory.initElements(driver, this);
	    }

	    public void login(String username, String password) {
	        usernameField.sendKeys(username);
	        passwordField.sendKeys(password);
	        loginButton.click();
	    }
	    
	    public String getTitle() {
	    	return driver.findElement(By.className("title")).getText();
	    
	    }
	    
	    public String getErrorMessage() {
			return errorMessage.getText();
		}
	    
	    public void resetFields() {
	    	usernameField.clear();
	    	passwordField.clear();
	    }
}