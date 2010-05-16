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
		selectWebDriver("webdriver");
		if (webDriver == null) {
			selectWebDriver("defaultwebdriver");
		}
	}

	private void selectWebDriver(String property) {
		String webdriverName = System.getProperty(property);
		if ("firefox".equalsIgnoreCase(webdriverName)) {
			webDriver = new FirefoxDriver();
		} else if ("htmlunit".equalsIgnoreCase(webdriverName)) {
			webDriver = new HtmlUnitDriver();
		} else if ("chrome".equalsIgnoreCase(webdriverName)) {
			System.setProperty("webdriver.reap_profile", "true");
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
