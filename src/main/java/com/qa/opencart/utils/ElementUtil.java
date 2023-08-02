package com.qa.opencart.utils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qa.opencart.factory.DriverFactory;

import io.qameta.allure.Step;

//import CustomException.FrameworkException;

public class ElementUtil {

	private WebDriver driver;
	private Actions act;
	private JavaScriptUtil jsUtil;

//	public WebDriver getDriver() {
//		return driver;
//	}
//
//	public void setDriver(WebDriver driver) {
//		this.driver = driver;
//	}
//when we create obj of element util along with webdriver the actions class also initialized with same driver
	// dont create another constructor again we need to supply onceagain driver
	public ElementUtil(WebDriver driver) {

		this.driver = driver;
		act = new Actions(driver);// session id
		jsUtil = new JavaScriptUtil(driver);//if we dont write this we get null exceptin
	}

	public void doSendKeys(By locator, String value) {
		if (value == null) {
			System.out.println("value can not be null while using sendkeys method");
			//throw new FrameworkException("VALUECANNOTBENULL");

		}

		getElement(locator).sendKeys(value);

	}

	public void doClick(By locator) {
		getElement(locator).click();

	}

	public WebElement getLinkEleByText(String linkText) {
		return driver.findElement(By.linkText(linkText));

	}

	public boolean checkElementIsDisplayed(By locator) {

		return getElement(locator).isDisplayed();
	}

	public String doElementGetText(By locator) {
		String eleText = getElement(locator).getText();
		System.out.println("Element text is  ---> " + eleText);
		return eleText;

	}

	public String getElementAttribute(By locator, String attrName) {
		return getElement(locator).getAttribute(attrName);

	}

	public boolean checkElementIsDisabled(By locator) {

		String disabledValue = getElement(locator).getAttribute("disabled");// since disabled property is same in the
																			// DOm html code so w r writing
		if (disabledValue.equals("true")) {
			return true;
		}
		return false;

	}

	// this method is used in all other methods to call driver.findelement
	@Step("getting element for locator: {0}")
	public WebElement getElement(By locator) {
		WebElement element = driver.findElement(locator);
		if(Boolean.parseBoolean(DriverFactory.highlight))
		{
			jsUtil.flash(element);
		}
		
		return element;

	}

	public int getElementsCount(By locator) {

		return getElements(locator).size();
	}

	public List<WebElement> getElements(By locator) {

		return driver.findElements(locator);
	}

	public ArrayList<String> getElementsTextList(By locator) {

		List<WebElement> eleList = getElements(locator);
		ArrayList<String> eleTextList = new ArrayList<String>();// pc=0

		for (WebElement e : eleList) {
			String text = e.getText();
			if (!text.isEmpty()) {
				// we no need to print but we need to store in list of strings
				eleTextList.add(text);
			}
		}
		return eleTextList;// we r returning since we can do validate
	}

	public void clickOnElement(By locator, String linkText) {
		List<WebElement> linksList = getElements(locator);
		System.out.println("total number of links " + linksList.size());

		for (WebElement e : linksList) {
			String text = e.getText();
			System.out.println(text);
			if (text.equals(linkText)) {
				e.click();
				break;
			}
		}

	}

	// Google search and click on any of suggestion LIst
	public void doSearch(By searchLocator, By searchSuggLocator, String searchKey, String suggName)
			throws InterruptedException {
		// driver.findElement(searchLocator).sendKeys(searchKey);
		doSendKeys(searchLocator, searchKey);

		Thread.sleep(4000);

		List<WebElement> suggList = getElements(searchSuggLocator);

		System.out.println(suggList.size());

		for (WebElement e : suggList) {
			String text = e.getText();
			System.out.println(text);
			if (text.contains(suggName)) {
				e.click();
				break;
			}
		}

	}

	// ***********************Drop Down Utils*********************************

	public void doSelectDropDownByIndex(By locator, int index) {

		// check for index number
		if (index < 0) {

			System.out.println("please pass the right (+ve) index");
			return;// we are writing these because need to comeout of it
		}
		Select select = new Select(getElement(locator));
		select.selectByIndex(index);

	}

	public void doSelectDropDownByVisibleText(By locator, String visibleText) {
		if (visibleText == null) {

			System.out.println("please pass the right visible text and it can not be null");
		}

		Select select = new Select(getElement(locator));
		select.selectByVisibleText(visibleText);

	}

	public void doSelectDropDownByValue(By locator, String value) {
		if (value == null) {
			System.out.println("please pass the right visible text and it can not be null");
			return;
		}
		Select select = new Select(getElement(locator));
		select.selectByValue(value);
	}
	
	public  List<String> getDropDownTextList(By locator) {

		Select select = new Select(getElement(locator));

		List<WebElement> optionsList = select.getOptions();
		// Create blank ArrayList
		List<String> optionsTextList = new ArrayList<String>();

		for (WebElement e : optionsList) {
			String text = e.getText();
			optionsTextList.add(text);

		}
		
		return optionsTextList;
	}
	
	public  int getDropDownOptionsCount(By locator) {
		Select select = new Select(getElement(locator));
		return select.getOptions().size();
	}
	
	public void doSelectDropDownValue(By locator, String dropDownValue) {
		Select select = new Select(getElement(locator));

		List<WebElement> optionsList = select.getOptions();
		for (WebElement e : optionsList) {
			String text = e.getText();
			System.out.println(text);
			if (text.equals(dropDownValue)) {
				e.click();
				break;
			}

		}
	}
	
	public void doSelectDropDownValueUsingLocator(By locator, String dropDownValue) {
		List<WebElement> optionsList = getElements(locator);		
		for(WebElement e : optionsList) {
			String text = e.getText();
			System.out.println(text);
				if(text.equals(dropDownValue)) {
					e.click();
					break;
				}
		}
	}

	// ************************Action Utils***********************

	public void doActionsClick(By locator) {

		act.click(getElement(locator)).perform();

	}

	public void doActionsSendKeys(By locator, String value) {

		act.sendKeys(getElement(locator), value).perform();

	}

	public void selectRightClickOption(By contextMenuLocator, String optionValue) {
		Actions act = new Actions(driver);
		act.contextClick(getElement(contextMenuLocator)).perform();// right click is done
		By optionLocator = By.xpath("//*[text()='" + optionValue + "']");// to click on options upon rightclick
		// In above * we used since its applicable for all tags
		doClick(optionLocator);
	}

	/**
	 * this method will handle the menu upto 2 levels
	 * 
	 * @param level1MenuLocator
	 * @param level2MenuLocator
	 * @throws InterruptedException
	 */

	// below 3 methods r example of method overloading
	public void multiLevelMenuHandling(By level1MenuLocator, By level2MenuLocator) throws InterruptedException {
		Actions act = new Actions(driver);
		act.moveToElement(getElement(level1MenuLocator)).build().perform();
		Thread.sleep(1500);

		doClick(level2MenuLocator);

	}

	public void multiLevelMenuHandling(By level1Locator, String level2, String level3, String level4)
			throws InterruptedException

	{
		Actions act = new Actions(driver);
		act.moveToElement(getElement(level1Locator)).build().perform();
		Thread.sleep(1500);
		act.moveToElement(getLinkEleByText(level2)).perform();
		Thread.sleep(1500);
		act.moveToElement(getLinkEleByText(level3)).perform();
		Thread.sleep(1500);

		getLinkEleByText(level4).click();

	}

	public void multiLevelMenuHandling(By level1Locator, String level2, String level3) throws InterruptedException

	{
		Actions act = new Actions(driver);
		act.moveToElement(getElement(level1Locator)).build().perform();
		Thread.sleep(1500);
		act.moveToElement(getLinkEleByText(level2)).perform();
		Thread.sleep(1500);
		getLinkEleByText(level3).click();

	}
	
	//**************WaitUtils*************************//
		/**
		 * An expectation for checking that an element is present on the DOM of a page. 
		 * This does not necessarily mean that the element is visible.
		 * @param locator
		 * @param timeOut
		 * @return
		 */
	@Step("waiting for the title and capture the title....")
	public String waitForTitleIs(String titleValue, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		try {
			if (wait.until(ExpectedConditions.titleIs(titleValue))) {
				return driver.getTitle();
			} else {
				System.out.println(titleValue + " title value is not present...");
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(titleValue + " title value is not present...");
			return null;
		}
	}
	
	/**
	 * An expectation for checking that all elements present on the web page that match the locator are visible.
	 *  Visibility means that the elements are not only displayed 
	 *  but also have a height and width that is greater than 0.
	 * @param locator
	 * @param timeOut
	 * @return
	 */
	public List<WebElement> waitForElementsVisible(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
	}
	/**
	 * An expectation for checking that an element is present on the DOM of a page and visible.
	 *  Visibility means that the element is not only displayed 
	 *  but also has a height and width that is greater than 0.
	 * @param locator
	 * @param timeOut
	 * @return
	 */
	@Step("....waiting for element is visible for locator: {0} with timeout: {1}")
   public WebElement waitForElementVisible(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		WebElement element =  wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		if(Boolean.parseBoolean(DriverFactory.highlight))
		{
			jsUtil.flash(element);
		}
		return element;
	}
	
	public WebElement waitForElementVisible(By locator, int timeOut, int pollingTime) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut), Duration.ofSeconds(pollingTime));
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}
	/**
	 * An expectation for the URL of the current page to contain specific text.
	 * @param urlFraction
	 * @param timeOut
	 * @return
	 */
	@Step("...waiting for page url and fetching it...url fraction : {0} ")
	public String waitForURLContains(String urlFraction, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		try {
			if (wait.until(ExpectedConditions.urlContains(urlFraction))) {
				return driver.getCurrentUrl();
			} else {
				System.out.println(urlFraction + " url value is not present...");
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(urlFraction + " url value is not present...");
			return null;
		}
	}
	
	/**
	 * An expectation for the URL of the current page to be a specific url.
	 * @param urlValue
	 * @param timeOut
	 * @return
	 */
	public String waitForURLToBe(String urlValue, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		try {
			if (wait.until(ExpectedConditions.urlToBe(urlValue))) {
				return driver.getCurrentUrl();
			} else {
				System.out.println(urlValue + " url value is not present...");
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(urlValue + " url value is not present...");
			return null;
		}
	}
	
	/* An expectation for checking an element is visible and enabled such that you can click it.
	 * @param locator
	 * @param timeOut
	 */
	public void clickElementWhenReady(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
	}
	
	

}
