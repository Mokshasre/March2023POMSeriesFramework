package com.qa.opencart.pages;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

public class ProductInfoPage {
	
	private WebDriver driver;
	private ElementUtil eleUtil;
	
	private By productHeader = By.cssSelector("div#content h1");
	private By productImages = By.cssSelector("ul.thumbnails img");
	private By quantity = By.name("quantity");
	private By addToCartBtn = By.id("button-cart");
	private By productMetaData = By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[1]/li");
	private By productPriceData = By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[2]/li");
	
	private Map<String, String> productMap;


	public ProductInfoPage(WebDriver driver) {
		// TODO Auto-generated constructor stub
		
		this.driver =driver;
		eleUtil = new ElementUtil(driver);
	}
	
	public String getProductHeaderValue() {
		return eleUtil.doElementGetText(productHeader);
	}
	
	public int getProductImagesCount() {
		
		int actProductImagesCount = eleUtil.waitForElementsVisible(productImages, AppConstants.MEDIUM_TIME_OUT).size();
		System.out.println("total product images for :" + getProductHeaderValue() + "===>" + actProductImagesCount);
		return actProductImagesCount;
		
		
	}
	//below 2 methods are examples of encapsulation since product map is not initialised in below so we get null 
	//so we r excapsulatoing
	private void getProductMetaData()
	{
		
		List<WebElement> metaList = eleUtil.waitForElementsVisible(productMetaData, AppConstants.MEDIUM_TIME_OUT);
		//Map<String,String> metaMap = new HashMap<String,String >();
		for(WebElement e :metaList)
		{
		
			String metaText = e.getText();
			String Key = metaText.split(":")[0].trim();
			String value = metaText.split(":")[1].trim();
			productMap.put(Key, value);
		}
			//return metaMap;
		
		
	}
	
	
	private void getProductPriceData()
	{
		List<WebElement> priceList = eleUtil.waitForElementsVisible(productPriceData, AppConstants.MEDIUM_TIME_OUT);
		//Map<String,String> priceMap = new HashMap<String,String >();
		
		String actPrice = priceList.get(0).getText().trim();
		String exTax = priceList.get(1).getText().split(":")[0].trim();
		String exTaxValue = priceList.get(1).getText().split(":")[1].trim();

		productMap.put("price", actPrice);//price is our own key value we can provide
		productMap.put(exTax, exTaxValue);
		
		
	}
	
	//Master method to get all the price and product data
	public Map<String, String> getProductData() {
		productMap = new HashMap<String, String>();
		//productMap = new LinkedHashMap<String, String>();//it maintains teh order
		//productMap = new TreeMap<String, String>();

		productMap.put("productheader", getProductHeaderValue());//our own key is given as prodheader
		productMap.put("productImages", String.valueOf(getProductImagesCount()));
		getProductMetaData();
		getProductPriceData();
		
		return productMap;
	}

}
