package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

public class RegisterPage {
	
	private WebDriver driver;
	private ElementUtil eleUtil;

	public RegisterPage(WebDriver driver) {
		
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
		
	}
	
	private By firstName = By.id("input-firstname");
	private By lastName = By.id("input-lastname");
	private By email = By.id("input-email");
	private By telephone = By.id("input-telephone");
	private By password = By.id("input-password");
	private By confirmpassword = By.id("input-confirm");

	private By subscribeYes = By.xpath("//label[normalize-space()='Yes']");
	private By subscribeNo = By.xpath("//label[normalize-space()='No']");

	private By agreeCheckBox = By.name("agree");
	private By continueButton = By.xpath("//input[@type='submit' and @value='Continue']");

	private By successMessg = By.cssSelector("div#content h1");
	private By logoutLink = By.linkText("Logout");
	private By registerLink = By.linkText("Register");
	
//    driver.findElement(By.name("agree")).click();
//    driver.findElement(By.xpath("//*[@id=\"content\"]/form/div/div/input[2]")).click();
	
	
	public String  getRegisterPageTitle()
	{
		String title = eleUtil.waitForTitleIs(AppConstants.REGISTER_PAGE_TITLE, AppConstants.SHORT_TIME_OUT);

		System.out.println("Register page  title is " + title);
		return title;
		
		
	}
	public String getAccountSuccMsgHeader() {
		return eleUtil.doElementGetText(successMessg);
	}

	public boolean registerUser(String firstName, String lastName, String email, String telephone, String password,
			String subscribe) {

		eleUtil.waitForElementVisible(this.firstName, AppConstants.MEDIUM_TIME_OUT).sendKeys(firstName);
		eleUtil.doSendKeys(this.lastName, lastName);
		eleUtil.doSendKeys(this.email, email);
		eleUtil.doSendKeys(this.telephone, telephone);

		eleUtil.doSendKeys(this.password, password);
		eleUtil.doSendKeys(this.confirmpassword, password);

		if (subscribe.equalsIgnoreCase("yes")) {
			eleUtil.doClick(subscribeYes);
		} else {
			eleUtil.doClick(subscribeNo);
		}
		eleUtil.doClick(agreeCheckBox);
		eleUtil.doClick(continueButton);

		String successMessg = eleUtil.waitForElementVisible(this.successMessg, AppConstants.MEDIUM_TIME_OUT).getText();
		System.out.println(successMessg);
		if (successMessg.contains(AppConstants.USER_REGISTER_SUCCESS_MESSG)) {
			eleUtil.doClick(logoutLink);//we click on logout here since to see the register page and to enter 2nd set of values from dataprovider
			eleUtil.doClick(registerLink);//click on register from login page 
			return true;
		}
		return false;

	}
//	public boolean doRegister(String inputField) throws InterruptedException {
//		
//		eleUtil.waitForElementVisible(this.firstName, AppConstants.MEDIUM_TIME_OUT).sendKeys(inputField);
//		Thread.sleep(3000);
//		
//	
//	eleUtil.doSendKeys(lastName, inputField);
//		eleUtil.doSendKeys(email, inputField);
//		eleUtil.doSendKeys(telephone, inputField);
//		eleUtil.doSendKeys(password, inputField);
//		eleUtil.doSendKeys(confirmpassword, inputField);
//		eleUtil.doClick(agreeCheckBox);
//		eleUtil.doClick(continueButton);
//		
//		String successMessg = eleUtil.waitForElementVisible(this.successMessg, AppConstants.MEDIUM_TIME_OUT).getText();
//		System.out.println(successMessg);
//		if(successMessg.contains(AppConstants.USER_REGISTER_SUCCESS_MESSG)) {
//			eleUtil.doClick(logoutLink);
//			//eleUtil.doClick(registerLink);
//			return true;
//		}
//		return false;
//		//return eleUtil.waitForTitleIs(AppConstants.ACCOUNTS_PAGE_TITLE, AppConstants.SHORT_TIME_OUT);
//		//return new AccountsPage(driver);
//	
//	}

}
