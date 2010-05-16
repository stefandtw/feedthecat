package org.quickflower.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.quickflower.tools.Browser;

import cuke4duke.annotation.I18n.EN.Given;
import cuke4duke.annotation.I18n.EN.When;

public class HomePage {
	
	private static final String HOMEPAGE_URL = "http://localhost:8080/index.jsp";
	private static final String SOURCE_URL_ID = "sourceUrl";
	
	private final WebDriver driver;
	
	public HomePage(Browser browser) {
		this.driver = browser.getDriver();
	}
	
	@Given("^homepage$")
	public void givenHomepage() {
		driver.get(HOMEPAGE_URL);
	}
	
	@When("^I set source url to \"(.*)\"$")
	public void setSourceUrlTo(String sourceUrl) {
		driver.findElement(By.id(SOURCE_URL_ID)).sendKeys(sourceUrl);
	}
		
}
