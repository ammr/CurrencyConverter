package com.xe.testcases;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

public class SampleTest {

	public static void main(String[] args) throws InterruptedException {

		
		WebDriver driver = new ChromeDriver();
		driver.get("http://www.xe.com/currencyconverter/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
		
		
		Wait<WebDriver> wait= new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(10))
                .pollingEvery(Duration.ofSeconds(2))
                .ignoring(NoSuchElementException.class); 
   
		wait.until(ExpectedConditions.elementToBeClickable(By.id("amount"))).click();
		driver.findElement(By.id("amount")).sendKeys("150");
		
		String fromCurrency = "//*[@aria-describedby='midmarketFromCurrency-current-selection']";
		//String fromCurrency = "/html/body/div[1]/div[4]/div[2]/section/div[2]/div/main/div/div[2]/div[1]/div[3]/div[2]/div/input";
							   //*[@id="midmarketFromCurrency"]/div[2]/div/input
		
		waitForElementToBeClickable(driver, fromCurrency);
		//Thread.sleep(500);
		driver.findElement(By.xpath(fromCurrency)).sendKeys("EUR", Keys.ENTER);
		//Thread.sleep(2000);
		
		String toCurrency = "//*[@aria-describedby='midmarketToCurrency-current-selection']";
		//String toCurrency = "/html/body/div[1]/div[4]/div[2]/section/div[2]/div/main/div/div[2]/div[1]/div[8]/div[2]/div/input";
		//waitForElementToBeClickable(driver, toCurrency);
		driver.findElement(By.xpath(toCurrency)).sendKeys("USD", Keys.ENTER);
		
		
		driver.findElement(By.xpath("/html/body/div[1]/div[4]/div[2]/section/div[2]/div/main/div/div[2]/div[2]/button")).click();
		
		String value_xpath = "//*[@id='__next']/div[4]/div[2]/section/div[2]/div/main/div[2]/div[2]/div[1]/p[2]";
		waitForElementToBeClickable(driver, value_xpath);
		String value = driver.findElement(By.xpath(value_xpath)).getText();
		System.out.println(value);
	}
	
	public static void waitForElementToBeClickable(WebDriver driver, String locatorKey) {
		
		Wait<WebDriver> wait= new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(10))
                .pollingEvery(Duration.ofSeconds(1))
                .ignoring(NoSuchElementException.class); 
   
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(locatorKey))).click();
		
	}

}
