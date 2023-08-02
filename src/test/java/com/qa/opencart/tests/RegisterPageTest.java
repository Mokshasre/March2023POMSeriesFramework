package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.pages.RegisterPage;
import com.qa.opencart.utils.ExcelUtil;

public class RegisterPageTest extends BaseTest{
	//base test will launch the login page and gives teh loginpage reference
	@BeforeClass
	public void registerInfoSetup() {
		//dologin returns teh Accounts page class refernce
		//we have to login since every page is independent
		regPage  = loginPage.navigateToRegisterPage();//this method gives the register page class refernce
	
		
	}
	
	@Test
	public void registerPageTitleTest()
	{
		
		String regPageactTitle = regPage.getRegisterPageTitle();
		Assert.assertEquals(regPageactTitle,AppConstants.REGISTER_PAGE_TITLE);
		
	}
	//this will generate random emails 
	public String getRandomEmailId() {
		return "openauto"+System.currentTimeMillis()+"@open.com";
	}
	
	
	//3*5 matrix
	@DataProvider
	public Object[][] getUserRegData() {
		return new Object[][] {
			{"Pooja", "agrawal", "9090909090", "pooja@123", "yes"},
			{"Shubham", "gupta", "9090909011", "shubh@123", "no"},
			{"mitaj", "kumar", "9090909012", "mitaj@123", "yes"},
		};
	}
	//using Excel.Util class below method is used
	@DataProvider
	public Object[][] getUserRegSheetData() {
		
		return ExcelUtil.getTestData(AppConstants.REGISTER_SHEET_NAME);
		
		
		
		
	}
	
//	@DataProvider
//	public Object[][] registerTestData() {
//		return new Object[][] {
//			{"sravs1"},
//			{"pot"},
//			{"sravs1@gmail.com"},
//			{"9876543211"},
//			{"test@1234"}
//			
//
//		};
//	} 
	
	//if i use like below method everytime the inoutfield passed its filled with registerpage with same value
//	@Test(dataProvider = "registerTestData")
//	public void registerTest(String inputVal) throws InterruptedException
//	{
//		Assert.assertTrue(regPage.doRegister(inputVal));
//		//this.regPage.doRegister(inputVal);
//		String succMessage = regPage.getAccountSuccMsgHeader();
//		System.out.println("Account Created Succesfully " + succMessage);
//		
//		
//		
//	}
	
	@Test(dataProvider = "getUserRegData")
	public void userRegisterTest(String firstName, String lastName, String telephone, String password, String subscribe) {
		Assert.assertTrue(regPage.registerUser(firstName, lastName, getRandomEmailId(), telephone,  password,  subscribe));
		
	}


}
