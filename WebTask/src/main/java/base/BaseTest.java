package base;

import org.openqa.selenium.WebDriver;

import utils.ConfigReader;
import utils.WebDriverFactory;

public class BaseTest {
	protected WebDriver driver;

    public void setUp() {
        ConfigReader config = new ConfigReader();
        driver = WebDriverFactory.getDriver(config.getBrowser());
        driver.get(config.getUrl());
        driver.manage().window().maximize();
    }

    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
    
    public void refresh() {
        ConfigReader config = new ConfigReader();
        driver.get(config.getUrl());
    }
}