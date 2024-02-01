package com.xe.pages;

import java.util.Hashtable;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.aventstack.extentreports.Status;
import com.xe.base.TestBase;
import com.xe.listeners.ExtentListeners;

import io.qameta.allure.Step;

public class CurrencyConverterHomePage extends TestBase {
	
	@FindBy(xpath = "//button[@class='Submenus__NavSubmenuButton-sc-2ubi8a-3 lbsDBH'][normalize-space()='Sign In']")
	WebElement signInBtn;
	
	@FindBy(xpath = "//button[@class='Submenus__NavSubmenuButton-sc-2ubi8a-3 eYFQGm']")
	WebElement registerBtn;
	
	@FindBy(id = "amount")
	WebElement amount;
	
	@FindBy(xpath = "//*[@aria-describedby='midmarketFromCurrency-current-selection']")
	WebElement fromCurrency;
	
	@FindBy(xpath = "//*[@aria-describedby='midmarketToCurrency-current-selection']")
	WebElement toCurrency;
	
	@FindBy(xpath = "/html/body/div[1]/div[4]/div[2]/section/div[2]/div/main/div/div[2]/div[2]/button")
	WebElement convertBtn;
	
	public CurrencyConverterHomePage() {
		PageFactory.initElements(driver, this);
	}

	@Step("Getting Conversion page title step...")
	public String validatePageTitle() {
		ExtentListeners.test.log(Status.INFO, "getting the page title : ");
		return driver.getTitle();
	}
	
	@Step("Verifying SignIn button is displayed step...")
	public boolean validateSignInButton() {
		ExtentListeners.test.log(Status.INFO, "Checking the presence of SignIn button on page ");
		return signInBtn.isDisplayed();
	}
	

	@Step("Verifying Register button is displayed step...")
	public boolean validateRegisterButton() {
		ExtentListeners.test.log(Status.INFO, "Checking the presence of Register button on page ");
		return registerBtn.isDisplayed();
	}
	
	@Step("Verifying Currency Conversion process step...")
	public CurrencyConverterResultPage doCurrencyConversion(Hashtable<String, String> data) {
		fleuntWait().until(ExpectedConditions.elementToBeClickable(amount)).click();;
		amount.sendKeys(Keys.CONTROL + "a");
		amount.sendKeys(Keys.DELETE);
		amount.sendKeys(data.get("Amount"), Keys.ENTER);
		fleuntWait().until(ExpectedConditions.elementToBeClickable(fromCurrency)).click();;
		fromCurrency.sendKeys(data.get("From_Currency"), Keys.ENTER);
		fleuntWait().until(ExpectedConditions.elementToBeClickable(toCurrency)).click();;
		toCurrency.sendKeys(data.get("To_Currency"), Keys.ENTER);
		if (convertBtn.isDisplayed()) {
			convertBtn.click();
		}
		delayExecution(1000);
		return new CurrencyConverterResultPage();
	}
}
