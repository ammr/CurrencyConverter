package com.xe.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import com.aventstack.extentreports.Status;
import com.xe.listeners.ExtentListeners;
import com.xe.utilities.ExcelReader;


public class TestBase {

	/*
	 * Declaration of necessary tools for running the scripts WebDriver, Web driver
	 * wait, Properties( Or, config) files, Logger, Excel reader, TestNG, Reports *
	 */
	public static WebDriver driver;
	public static Properties or = new Properties();
	public static Properties config = new Properties();
	private static FileInputStream fis;
	public static Logger log = Logger.getLogger(TestBase.class);
	public static ExcelReader excel;
	public static WebDriverWait wait;
	public static String homePage = "";

	/*
	 * Initialization of above declared tools is done here in this method, This
	 * method gets called from the test using @BeforeTest of TestNG so that test can
	 * access all the variable/methods to perform the test
	 * 
	 */
	public void initialization() {
		PropertyConfigurator.configure("./src/test/resources/properties/log4j.properties");
		excel = new ExcelReader(".//src//test//resources//excel//testdata.xlsx");

		try {
			fis = new FileInputStream("./src/test/resources/properties/config.properties");
			config.load(fis);
			log.info("Config properties loaded !!!");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			fis = new FileInputStream("./src/test/resources/properties/OR.properties");
			or.load(fis);
			log.info("OR properties loaded !!!");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		String browserName = config.getProperty("browser").toLowerCase();
		if (browserName.equals("chrome")) {
			driver = new ChromeDriver();
			log.info("Launching Chrome !!!");
		} else if (browserName.equals("firefox")) {
			driver = new FirefoxDriver();
			log.info("Launching Firefox !!!");

		}

		driver.manage().window().maximize();
		driver.manage().timeouts()
				.pageLoadTimeout(Duration.ofSeconds(Integer.parseInt(config.getProperty("pageLoad.timeout"))));
		driver.manage().timeouts()
				.implicitlyWait(Duration.ofSeconds(Integer.parseInt(config.getProperty("implicit.wait"))));
		wait = new WebDriverWait(driver, Duration.ofSeconds(Integer.parseInt(config.getProperty("explicit.wait"))));

		homePage = config.getProperty("testSiteURL");
		driver.get(homePage);
		log.info("Navigated to : " + homePage);
	}

	/*
	 * Closing activities are performed in this method and it gets called in the
	 * test using
	 * 
	 * @AfterTest of TestNG to close the browser after every test is performed
	 */
	public void closeBrowser() {
		driver.quit();
		log.info("Test Execution completed !!!");
	}

	/*
	 * This method is to get any element based on the locator key& it's type
	 * 
	 */
	public static WebElement getElement(String locatorKey) {
		WebElement element = null;
		try {
			if (locatorKey.endsWith("_XPATH")) {
				element = driver.findElement(By.xpath(or.getProperty(locatorKey)));
			} else if (locatorKey.endsWith("_CSS")) {
				element = driver.findElement(By.cssSelector(or.getProperty(locatorKey)));
			} else if (locatorKey.endsWith("_ID")) {
				element = driver.findElement(By.id(or.getProperty(locatorKey)));
			} else if (locatorKey.endsWith("_NAME")) {
				element = driver.findElement(By.name(or.getProperty(locatorKey)));
			} else if (locatorKey.endsWith("_CLASSNAME")) {
				element = driver.findElement(By.className(or.getProperty(locatorKey)));
			} else if (locatorKey.endsWith("_TAGNAME")) {
				element = driver.findElement(By.tagName(or.getProperty(locatorKey)));
			} else if (locatorKey.endsWith("_LINKTEXT")) {
				element = driver.findElement(By.linkText(or.getProperty(locatorKey)));
			}
			ExtentListeners.test.log(Status.INFO, "Finding Element using : " + locatorKey);
		} catch (Throwable t) {
			log.error("Error while locating Element : " + locatorKey + " error message : " + t.getMessage());
			ExtentListeners.test.log(Status.FAIL,
					"Error while locating Element : " + locatorKey + " error message : " + t.getMessage());
			Assert.fail(t.getMessage());

		}
		return element;

	}

	/* clicking an element based on locator key */
	public static void click(String locatorKey) {
		boolean isElementPresent = isElementPresent(locatorKey);
		if (isElementPresent) {
			try {
				getElement(locatorKey).click();
				log.info("Clicking on an Element : " + locatorKey);
				ExtentListeners.test.log(Status.INFO, "Clicking on an Element : " + locatorKey);
			} catch (Throwable t) {
				log.error("Error while Clicking on an Element : " + locatorKey + " error message : " + t.getMessage());
				ExtentListeners.test.log(Status.FAIL,
						"Error while Clicking on an Element : " + locatorKey + " error message : " + t.getMessage());
				Assert.fail(t.getMessage());
			}
		} else {
			log.info("Element: " + locatorKey + " is not present to be clickable");
		}

	}

	/* Getting the text of an element using locator key */
	public static String getElementText(String locatorKey) {
		waitForElementToBeClickable(driver, locatorKey);
		return getElement(locatorKey).getText();
	}

	/* checking the presence of an element using locator key */
	public static boolean isElementPresent(String locatorKey) {
		boolean elementPresent = false;

		try {
			if (locatorKey.endsWith("_XPATH")) {
				elementPresent = driver.findElements(By.xpath(or.getProperty(locatorKey))).size() > 0;
			} else if (locatorKey.endsWith("_CSS")) {
				elementPresent = driver.findElements(By.cssSelector(or.getProperty(locatorKey))).size() > 0;
			} else if (locatorKey.endsWith("_ID")) {
				elementPresent = driver.findElements(By.id(or.getProperty(locatorKey))).size() > 0;
			} else if (locatorKey.endsWith("_NAME")) {
				elementPresent = driver.findElements(By.name(or.getProperty(locatorKey))).size() > 0;
			} else if (locatorKey.endsWith("_CLASSNAME")) {
				elementPresent = driver.findElements(By.className(or.getProperty(locatorKey))).size() > 0;
			} else if (locatorKey.endsWith("_TAGNAME")) {
				elementPresent = driver.findElements(By.tagName(or.getProperty(locatorKey))).size() > 0;
			} else if (locatorKey.endsWith("_LINKTEXT")) {
				elementPresent = driver.findElements(By.linkText(or.getProperty(locatorKey))).size() > 0;
			}
			ExtentListeners.test.log(Status.INFO, "Verifying element presence based on : " + locatorKey);
		} catch (Throwable t) {
			System.out.println("cant find element");
			log.error("Error while locating Element : " + locatorKey + " error message : " + t.getMessage());
			ExtentListeners.test.log(Status.FAIL,
					"Error while locating Element : " + locatorKey + " error message : " + t.getMessage());
			Assert.fail(t.getMessage());

		}

		return elementPresent;

	}

	/* entering value for an an element using locator key */
	public static void type(String locatorKey, String value) {
		waitForElementToBeClickable(driver, locatorKey);
		WebElement element = getElement(locatorKey);
		try {
			element.clear();
			element.sendKeys(Keys.CONTROL + "a");
			element.sendKeys(Keys.DELETE);
			element.sendKeys(value, Keys.ENTER);
			log.info("typing in to Element : " + locatorKey + " entered the value as : " + value);
			ExtentListeners.test.log(Status.INFO,
					"typing in to an Element : " + locatorKey + " entered the value as : " + value);
		} catch (Throwable t) {
			log.error("Error while typing in an Element : " + locatorKey + " error message : " + t.getMessage());
			ExtentListeners.test.log(Status.FAIL,
					"Error while typing in an Element : " + locatorKey + " error message : " + t.getMessage());
			Assert.fail(t.getMessage());

		}

	}

	/*
	 * This method is to wait for an element to be clickable using FluentWait
	 */
	public static void waitForElementToBeClickable(WebDriver driver, String locatorKey) {

		Wait<WebDriver> wait = new FluentWait<>(driver).withTimeout(Duration.ofSeconds(10))
				.pollingEvery(Duration.ofSeconds(1)).ignoring(NoSuchElementException.class);

		if (locatorKey.endsWith("_XPATH")) {
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath(or.getProperty(locatorKey)))).click();
		} else if (locatorKey.endsWith("_CSS")) {
			wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(or.getProperty(locatorKey)))).click();
		} else if (locatorKey.endsWith("_ID")) {
			wait.until(ExpectedConditions.elementToBeClickable(By.id(or.getProperty(locatorKey)))).click();
		} else if (locatorKey.endsWith("_NAME")) {
			wait.until(ExpectedConditions.elementToBeClickable(By.name(or.getProperty(locatorKey)))).click();
		} else if (locatorKey.endsWith("_CLASSNAME")) {
			wait.until(ExpectedConditions.elementToBeClickable(By.className(or.getProperty(locatorKey)))).click();
		} else if (locatorKey.endsWith("_TAGNAME")) {
			wait.until(ExpectedConditions.elementToBeClickable(By.tagName(or.getProperty(locatorKey)))).click();
		} else if (locatorKey.endsWith("_LINKTEXT")) {
			wait.until(ExpectedConditions.elementToBeClickable(By.linkText(or.getProperty(locatorKey)))).click();
		}

	}

	/*
	 * This method is to get fluentWait object so that it can be used with any
	 * element before performing action on/with the element
	 * 
	 */
	public static FluentWait<WebDriver> fleuntWait() {
		return new FluentWait<>(driver).withTimeout(Duration.ofSeconds(10)).pollingEvery(Duration.ofSeconds(1))
				.ignoring(NoSuchElementException.class);
	}

	/*
	 * This is to delay the execution of script to avoid synchronization issues
	 * while performing actions on element
	 */
	public static void delayExecution(long time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/*
	 * This method is to refresh the page after every test method is executed
	 * so that multiple data sets of currency can be checked for conversion
	 */
	public static void goBackToHomePage() {
		driver.navigate().to(homePage);
		driver.navigate().refresh();
		delayExecution(1500);
	}

}
