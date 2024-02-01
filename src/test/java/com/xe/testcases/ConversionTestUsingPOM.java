package com.xe.testcases;


import java.util.Hashtable;

import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.xe.base.TestBase;
import com.xe.listeners.AllureListener;
import com.xe.pages.CurrencyConverterHomePage;
import com.xe.pages.CurrencyConverterResultPage;
import com.xe.utilities.DataUtil;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Listeners({AllureListener.class})
public class ConversionTestUsingPOM extends TestBase{
	CurrencyConverterHomePage homePage;
	CurrencyConverterResultPage resultPage;
	
	
	
	@Test(priority =1, description = "Verifying Conversion Page Title")
	@Severity(SeverityLevel.NORMAL)
	@Description(" Test Case Description: Verify Conversion Page Title")
	@Story("Story Name: To check Conversion Page Title")
	public void verifyConversionPageTitle() {
		String title = homePage.validatePageTitle();
		Assert.assertEquals(title, "Xe Currency Converter - Live Exchange Rates Today");
	}
	
	@Test(priority =2, description = "Verifying SignIn button")
	@Severity(SeverityLevel.NORMAL)
	@Description(" Test Case Description: Verify SignIn button on Conversion Page")
	@Story("Story Name: To check SignIn button on Conversion Page")
	public void verifySignInButtonIsPresent() {
		boolean singInBtnPresent = homePage.validateSignInButton();
		Assert.assertTrue(singInBtnPresent);
	}

	@Test(priority = 3, dataProvider = "getTestData")
	@Severity(SeverityLevel.CRITICAL)
	@Description(" Test Case Description: Verify Currency Conversion functionality")
	@Story("Story Name: check conversion functionality")
	public void currencyConverionTest(Hashtable<String, String> data)  {
		
			
		if(data.get("Run_Mode").equalsIgnoreCase("N")) {
			throw new SkipException("Skipping the test as the run mode is NO");
		}else {
			System.out.println(data.get("Amount")+"--"+data.get("From_Currency")+"--"+data.get("To_Currency"));
		}
		
		resultPage = homePage.doCurrencyConversion(data);
		String convertedValue = resultPage.verifyConveriionsValue();
		Assert.assertTrue(convertedValue!=null);
		System.out.println("Converted value is:"+convertedValue);
	}
	
	
	
	@BeforeTest
	public void initialize() {
		initialization();
		homePage = new CurrencyConverterHomePage();
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
		return DataUtil.getDataIntoHashTable(excel, "ConversionTest");
	}
	
}
