package com.xe.testcases;


import java.util.Hashtable;

import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.xe.base.TestBase;
import com.xe.utilities.DataUtil;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Step;
import io.qameta.allure.Story;

public class ConversionTest extends TestBase{
	
	
	@Test(priority =1, description = "Verifying Conversion Page Title")
	@Severity(SeverityLevel.NORMAL)
	@Description(" Test Case Description: Verify Conversion Page Title")
	@Story("Story Name: To check Conversion Page Title")
	public void verifyConversionPageTitle() {
		String title = driver.getTitle();
		Assert.assertEquals(title, "Xe Currency Converter - Live Exchange Rates Today");
	}
	
	@Test(priority =2, description = "Verifying SignIn button")
	@Severity(SeverityLevel.NORMAL)
	@Description(" Test Case Description: Verify SignIn button on Conversion Page")
	@Story("Story Name: To check SignIn button on Conversion Page")
	public void verifySignInButtonIsPresent() {
		Assert.assertTrue(isElementPresent("signInBtin_XPATH"));
	}

	@Test(priority = 3, dataProvider = "getTestData")
	@Severity(SeverityLevel.CRITICAL)
	@Description(" Test Case Description: Verify Currency Conversion functionality")
	@Story("Story Name: check conversion functionality")
	@Step("Performing conversion functionality step...")
	public void currencyConverionTest(Hashtable<String, String> data)  {
		
			
		if(data.get("Run_Mode").equalsIgnoreCase("N")) {
			throw new SkipException("Skipping the test as the run mode is NO");
		}else {
			System.out.println(data.get("Amount")+"--"+data.get("From_Currency")+"--"+data.get("To_Currency"));
		}
		
		type("amount_ID", data.get("Amount"));
		type("fromCurrency_XPATH", data.get("From_Currency"));
		type("toCurrency_XPATH", data.get("To_Currency"));
		click("ConvertBtn_XPATH");
		String convertedValue = getElementText("ConvertedValue_XPATH");
		Assert.assertTrue(convertedValue!=null);
		System.out.println("Converted value is:"+convertedValue);
	}
	
	
	@BeforeTest
	public void setUp() {
		initialization();
	}
	
	@AfterMethod
	public void refreshHomePage() {
		goBackToHomePage();
	}
	
	@AfterTest
	public void tearDown() {
		closeBrowser();
	}
	
	
	@DataProvider
	public Object[][] getTestData() {
		return DataUtil.getDataIntoHashTable(excel, this.getClass()
				.getSimpleName());
	}
	
}
