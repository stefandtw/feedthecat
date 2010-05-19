package org.quickflower.tools;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import cuke4duke.annotation.After;
import cuke4duke.annotation.Before;

public class Browser {

	private WebDriver webDriver;

	@Before
	public void startBrowser() {
		webDriver = null;
		String webDriverName = System.getProperty("webdriver");
		selectWebDriver(webDriverName);
	}

	private void selectWebDriver(String name) {
		if ("firefox".equalsIgnoreCase(name)) {
			webDriver = new FirefoxDriver();
		} else if ("htmlunit".equalsIgnoreCase(name)) {
			HtmlUnitDriver htmlUnitDriver = new HtmlUnitDriver();
			htmlUnitDriver.setJavascriptEnabled(true);
			webDriver = htmlUnitDriver;
		} else if ("chrome".equalsIgnoreCase(name)) {
			webDriver = new ChromeDriver();
		}
	}

	@After
	public void closeBrowser() {
		webDriver.close();
	}

	public WebDriver getDriver() {
		return webDriver;
	}
}
