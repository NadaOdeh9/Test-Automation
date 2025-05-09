package stepdefinitions;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.Assert;

import base.BaseTest;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.LoginPage;
import utils.ExcelReader;

public class LoginSteps extends BaseTest {
	LoginPage loginPage;
	ExcelReader excelReader;
	List<String[]> testData;
	String expectedErrorMessage;

	@Given("I am on the login page")
	public void i_am_on_the_login_page() {
		setUp();
		loginPage = new LoginPage(driver);
	}

	@When("I login with valid credentials")
	public void i_login_with_valid_credentials() {
		loginPage.login("standard_user", "secret_sauce");
	}

	@Then("I should see the products page")
	public void i_should_see_the_products_page() {
		Assert.assertTrue(loginPage.getTitle().contains("Products"));
		tearDown();
	}

	@When("I login with invalid credentials I see an error message")
	public void i_login_with_invalid_credentials() throws EncryptedDocumentException, IOException {

		FileInputStream file = new FileInputStream(new File("src/test/resources/Test_Data.xlsx"));
		Workbook workbook = WorkbookFactory.create(file);
		Sheet sheet = workbook.getSheetAt(0);

		for (int i = 1; i <= sheet.getLastRowNum(); i++) {
			refresh();

			try {
				Row row = sheet.getRow(i);

				String username = row.getCell(0).getStringCellValue();
				System.out.println("Username at cell " + i + " : " + username);
				String password = row.getCell(1).getStringCellValue();
				expectedErrorMessage = row.getCell(2).getStringCellValue();

				System.out.println("Login Attempt #" + i);

				loginPage.login(username, password);

				i_should_see_an_error_message(i);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		tearDown();
	}

	public void i_should_see_an_error_message(Integer rowIndex) {
		String actualErrorMessage = loginPage.getErrorMessage();

		Assert.assertEquals(expectedErrorMessage, actualErrorMessage);
		System.out.println("Error message #" + rowIndex);
	}
}