package org.quickflower.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.quickflower.tools.Browser;

import cuke4duke.annotation.I18n.EN.Given;
import cuke4duke.annotation.I18n.EN.When;

public class CreatePage {

	private static final String HOMEPAGE_URL = "http://localhost:8080/";
	private static final String SOURCE_URL_ID = "sourceUrl";
	private static final String NAME_ID = "name";
	private static final String XPATH_ID = "xpath";

	private final WebDriver driver;

	public CreatePage(Browser browser) {
		this.driver = browser.getDriver();
	}

	@Given("^homepage$")
	public void givenHomepage() {
		driver.get(HOMEPAGE_URL);
	}

	@When("^I set source url to '(.*)'$")
	public void setSourceUrlTo(String sourceUrl) {
		driver.findElement(By.name(SOURCE_URL_ID)).sendKeys(sourceUrl);
	}

	@When("^I set name to '(.*)'$")
	public void setNameTo(String name) {
		driver.findElement(By.name(NAME_ID)).sendKeys(name);
	}

	@When("^I set xpath to ''(.*)''$")
	public void setXPathTo(String xpath) {
		driver.findElement(By.name(XPATH_ID)).sendKeys(xpath);
	}

	@When("I click the '(.*)' button")
	public void clickThe(String button) {
		driver.findElement(
				By.xpath("/html/body/form//input[@value='" + button + "']"))
				.click();
	}

}
