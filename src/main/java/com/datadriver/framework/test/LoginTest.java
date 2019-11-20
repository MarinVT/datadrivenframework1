package com.datadriver.framework.test;

import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.datadriven.framework.base.BaseUI;
import com.datadriver.framework.utils.ExtendReportManager;

public class LoginTest extends BaseUI {
	
	//Test git + github
	//Test git + github 2222
	
	ExtentReports report = ExtendReportManager.getReportInstance();
	
	@Test
	public void testOne() {
		
		logger = report.createTest("Login to main page Dashboard");
		
		logger.log(Status.INFO, "Init the browser");
		invokeBrowser("chrome");
		
		logger.log(Status.INFO, "Opening WebSite");
		openURL("websiteURL");
		
		logger.log(Status.INFO, "Click on sign in button");
		clickElement("signButton_Xpath");
		
		// Locate Username and Password
		logger.log(Status.INFO, "Entering email and Pass");
		//add username
		enterText("usernameTextbox_Xpath", "Marin_2050");
		//add password
		enterText("passwordTextbox_Xpath", "marinvt2050");
		
		logger.log(Status.FAIL, "Test is Fail successfully");

//		tearDown();
		
		takeScreenShotFailure();
	}
	  
	@AfterTest
	public void endReport() {
		report.flush();
	}
	
//	@Test(dependsOnMethods = "testOne")
//	public void testTwo() {
//		invokeBrowser("Mozila");
//		openURL("websiteURL");
//		clickElement("signButtonInfo");
//		enterText("usernameTextbox_xpath", "marinvt1");
//		tearDown();
//	}
	
//	@Test(dependsOnMethods = "testTwo")
//	public void testThree() {
//		invokeBrowser("IE");
//		openURL("websiteURL");
//		clickElement("signButtonInfo");
//		enterText("usernameTextbox_xpath", "marinvt1");
//		tearDown();
//	}
}
