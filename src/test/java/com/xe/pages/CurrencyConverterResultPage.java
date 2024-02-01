package com.xe.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.Status;
import com.xe.base.TestBase;
import com.xe.listeners.ExtentListeners;

public class CurrencyConverterResultPage extends TestBase{
	
	@FindBy(xpath = "//*[@id='__next']/div[4]/div[2]/section/div[2]/div/main/div[2]/div[2]/div[1]/p[2]")
	WebElement convertedValue;
	
	public CurrencyConverterResultPage() {
		PageFactory.initElements(driver, this);
	}
	
	public String verifyConveriionsValue() {
		ExtentListeners.test.log(Status.INFO, "Getting the converted currency value from page");
		return convertedValue.getText();
	}

}
