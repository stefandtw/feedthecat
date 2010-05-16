package org.quickflower.page;

import static org.junit.Assert.*;
import org.openqa.selenium.WebDriver;
import org.quickflower.tools.Browser;

import cuke4duke.annotation.I18n.EN.Then;

public class FilteredPageResult {

	private static final String RESULT_PAGE_URL = "http://localhost:8080/resultpage";
	
	private WebDriver driver;

	public FilteredPageResult(Browser browser) {
		driver = browser.getDriver();
	}
	
	@Then("^result page contains \"(.*)\"$")
	public void containsText(String text) {
		assertOnPage();
		boolean containsTheText = driver.getPageSource().contains(text);
		assertTrue(containsTheText);
	}
	
	private void assertOnPage() {
		driver.get(RESULT_PAGE_URL);
		String pageSource = driver.getPageSource();
		assertNotNull(pageSource);
		//TODO test; remove
		throw new RuntimeException("check if assertOnPage works. pageSource: " + pageSource);
	}
	
}
