# Test-Automation

Overview

This project is a test automation framework that automates the testing of a web application, an Android mobile app, and an API. It uses Java and several automation tools like Selenium, Cucumber, Appium, and REST Assured to cover different types of testing.

What’s Included UI Tests (Web):

Automates test scenarios on the website https://www.saucedemo.com/ using Selenium and Cucumber. Supports Chrome and Firefox browsers. Uses a configuration file to control the browser and URL. Tests can run in parallel for faster execution. Follows the Page Object Model (POM) design pattern. Automates a data-driven test that runs with multiple sets of data. Mobile Tests (Android):

Automates login scenarios (positive and negative) for the provided Android app. Uses Appium to interact with the mobile application. API Tests:

Automates basic API requests like GET, POST, PATCH, and DELETE using REST Assured. Covers standard CRUD operations. Prerequisites Before running the tests, make sure you have the following installed:

Java (JDK 8+) Maven ChromeDriver (for Chrome) and GeckoDriver (for Firefox) Appium and Android Studio (for mobile testing) Git Tools and Technologies Java: Programming language used for writing the tests. Selenium WebDriver: Browser automation tool. Cucumber: Used to write test cases in plain English (Gherkin language). Junit: Manages test execution. REST Assured: For API testing. Appium: For mobile app automation. Extent Reports: Generates detailed test execution reports.

How to Run the Tests

Running UI Tests (Web) Configure the Tests:
Open the config.properties file and set the following values: browser=chrome (or firefox) url=https://www.saucedemo.com/ 2. Run the Tests. 3. View the Report: After the tests complete, a report is generated in the target/reports directory.

Running Mobile Tests (Android) Setup: Make sure Appium is installed and Android Studio is configured. Connect your Android device or start an emulator.

Run the Tests:

Running API Tests:

Run the Tests.

Test Scenarios

UI Test Scenarios

Invalid Login: Attempts to log in with incorrect credentials and verifies that the correct error message is displayed. Valid Login and Purchase:

Logs in with valid credentials.

Adds the two most expensive products to the cart.

Proceeds to checkout and verifies the total amount before taxes.

Completes the order and checks for a successful completion message.

Mobile Test Scenarios: Login Scenarios: Automates both positive and negative login scenarios in the Android app, verifying successful logins and handling invalid credentials.

API Test Scenarios: CRUD Operations: Automates basic Create, Read, Update, and Delete operations using REST Assured.

Reports and Logging Test Reports: After running the tests, detailed reports are generated using Extent Reports. You can find them in the target/reports directory.
