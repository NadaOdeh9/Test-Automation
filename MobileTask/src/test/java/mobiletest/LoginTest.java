package mobiletest;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;

public class LoginTest {

    private AndroidDriver<MobileElement> driver;

    @BeforeClass
    public void setUp() throws MalformedURLException {
        // Set Desired Capabilities for Appium
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "11.0"); // Use correct version
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "emulator-5554");
        capabilities.setCapability(MobileCapabilityType.APP, "C:\\Users\\nodeh\\Downloads\\Android.SauceLabs.Mobile.Sample.app.2.7.1.apk");
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");

        // Initialize AndroidDriver (Appium server must be running at /wd/hub)
        driver = new AndroidDriver<>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
    }

    @Test
    public void positiveLoginTest() {
        MobileElement usernameField = driver.findElement(AppiumBy.accessibilityId("test-Username"));
        MobileElement passwordField = driver.findElement(AppiumBy.accessibilityId("test-Password"));
        MobileElement loginButton = driver.findElement(AppiumBy.accessibilityId("test-LOGIN"));

        usernameField.sendKeys("standard_user");
        passwordField.sendKeys("secret_sauce");
        loginButton.click();

        MobileElement productsTitle = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text='PRODUCTS']"));
        Assert.assertTrue(productsTitle.isDisplayed(), "Login failed - Products page not displayed");
    }

    @Test
    public void negativeLoginTest() {
        MobileElement usernameField = driver.findElement(AppiumBy.accessibilityId("test-Username"));
        MobileElement passwordField = driver.findElement(AppiumBy.accessibilityId("test-Password"));
        MobileElement loginButton = driver.findElement(AppiumBy.accessibilityId("test-LOGIN"));

        usernameField.sendKeys("invalid_user");
        passwordField.sendKeys("invalid_password");
        loginButton.click();

        MobileElement errorMessage = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@content-desc='test-Error message']"));
        Assert.assertTrue(errorMessage.isDisplayed(), "Error message not displayed");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}