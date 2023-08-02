package com.qa.opencart.base;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.asserts.SoftAssert;

import com.qa.opencart.factory.DriverFactory;
import com.qa.opencart.pages.AccountsPage;
import com.qa.opencart.pages.LoginPage;
import com.qa.opencart.pages.ProductInfoPage;
import com.qa.opencart.pages.RegisterPage;
import com.qa.opencart.pages.SearchResultsPage;

public class BaseTest {

	WebDriver driver;
	protected Properties prop;
	protected LoginPage loginPage;// reference created
	// default modifier by default so we cant access in different package since same
	// package only we can access
	protected RegisterPage regPage;
	protected AccountsPage accPage;
	protected SearchResultsPage searchResPage;
	protected ProductInfoPage productInfoPage;
	DriverFactory df;

	protected SoftAssert softAssert;
	
	@BeforeTest
	public void setup() {

		df = new DriverFactory();
		prop = df.initProp();
		driver = df.initDriver(prop);//if we wont assign the right side to driver variable then we get null refernce for driver
		loginPage = new LoginPage(driver);// initialised here and passed to loginpage class
		softAssert = new SoftAssert();

	}

	@AfterTest
	public void tearDown() {

		driver.quit();
	}

}
