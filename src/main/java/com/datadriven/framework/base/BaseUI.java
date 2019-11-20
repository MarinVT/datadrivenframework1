package com.datadriven.framework.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.testng.Assert;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.datadriver.framework.utils.DateUtils;
import com.datadriver.framework.utils.ExtendReportManager;

public class BaseUI {

	//Test git + github 2222
	
	public WebDriver driver;
	public Properties prop;
	public ExtentReports reporter = ExtendReportManager.getReportInstance();
	public ExtentTest logger;

	public void invokeBrowser(String browserName) {
		if (browserName.equalsIgnoreCase("Chrome")) {
			System.setProperty("webdriver.chrome.driver",
					System.getProperty("user.dir") + "\\src\\drivers\\chromedriver.exe");
			driver = new ChromeDriver();
		} else if (browserName.equalsIgnoreCase("Mozila")) {
			System.setProperty("webdriver.gecko.driver",
					System.getProperty("user.dir") + "\\src\\drivers\\geckodriver.exe");
			driver = new FirefoxDriver();
		} else if (browserName.equalsIgnoreCase("IE")) {
			System.setProperty("webdriver.ie.driver",
					System.getProperty("user.dir") + "\\src\\drivers\\IEDriverServer.exe");
			driver = new FirefoxDriver();
		} else {
			System.setProperty("webdriver.opera.driver",
					System.getProperty("user.dir") + "\\src\\drivers\\operadriver.exe");
			driver = new OperaDriver();
		}

		driver.manage().timeouts().implicitlyWait(180, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.manage().timeouts().pageLoadTimeout(180, TimeUnit.SECONDS);

		if (prop == null) {
			prop = new Properties();
			try {
				FileInputStream file = new FileInputStream(System.getProperty("user.dir")
						+ "//src//test//resources//ObjectRepository//projectConfig.properties");
				prop.load(file);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

				Assert.fail("Failing the TestCase : " + e.getMessage());
			}
		}

	}

	public void openURL(String webSiteURLKey) {
		driver.get(prop.getProperty(webSiteURLKey));
	}

	public void tearDown() {
		driver.close();
	}

	public void quitBrowser() {
		driver.quit();
	}

	// Enter TEXT in text fields
	public void enterText(String xpathKey, String data) {
		getElement(xpathKey).sendKeys(data);
//		driver.findElement(By.xpath(prop.getProperty(xpathKey))).sendKeys(data);
	}

	// Click method useful for click operations
	public void clickElement(String xpathKey) {
		getElement(xpathKey).click();
	}

	// To Identify the Page element
	public WebElement getElement(String locaterKey) {
		WebElement element = null;

		try {
			if (locaterKey.endsWith("_Id")) {
				element = driver.findElement(By.id(prop.getProperty(locaterKey)));
			} else if (locaterKey.endsWith("_Xpath")) {
				element = driver.findElement(By.xpath(prop.getProperty(locaterKey)));
			} else if (locaterKey.endsWith("_CSS")) {
				element = driver.findElement(By.cssSelector(prop.getProperty(locaterKey)));
			} else if (locaterKey.endsWith("_LinkText")) {
				element = driver.findElement(By.linkText(prop.getProperty(locaterKey)));
			} else if (locaterKey.endsWith("_PartialLinkText")) {
				element = driver.findElement(By.partialLinkText(prop.getProperty(locaterKey)));
			} else if (locaterKey.endsWith("_Name")) {
				element = driver.findElement(By.name(prop.getProperty(locaterKey)));
			} else {
				reportFail("Failing the test Case" + locaterKey);
//				Assert.fail("Failing the Test Case, Invalid Locator " + locaterKey);
			}
		} catch (Exception e) {
			// Fail test case and report the error
			reportFail(e.getMessage());
			e.printStackTrace();

			Assert.fail("Fail the TestCase" + e.getMessage());
		}

		return element;
	}

	public void reportFail(String reportString) {

	}
	
	public void reportPass(String reportString) {
		
	}
	
	public void takeScreenShotFailure() {
		TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
		File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
		
		File destFile = new File(System.getProperty("user.dir") + "\\Screenshots\\" + DateUtils.getTimeStamp() + ".png");
		
		try {
			FileUtils.copyFile(sourceFile, destFile);
			logger.addScreenCaptureFromPath(System.getProperty("user.dir") + "\\Screenshots\\" + DateUtils.getTimeStamp() + ".png");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
