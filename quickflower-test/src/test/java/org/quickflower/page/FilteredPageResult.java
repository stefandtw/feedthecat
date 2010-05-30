package org.quickflower.page;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;

import org.openqa.selenium.WebDriver;
import org.quickflower.tools.Browser;

import cuke4duke.annotation.I18n.EN.Then;

public class FilteredPageResult {

	private static final String BASIC_RESULT_URL = "http://localhost:8080/page";

	private final WebDriver driver;

	public FilteredPageResult(Browser browser) {
		driver = browser.getDriver();
	}

	@Then("^result page for '(.*)' contains '(.*)'$")
	public void resultPageForNameContains(String name, String text) {
		switchToResultPage(name);
		String pageSource = driver.getPageSource();
		assertThat(pageSource, containsString(text));
	}

	@Then("^result page for '(.*)' does not contain '(.*)'$")
	public void resultPageForNameDoesNotContain(String name, String text) {
		switchToResultPage(name);
		String pageSource = driver.getPageSource();
		assertThat(pageSource, not(containsString(text)));
	}

	private void switchToResultPage(String name) {
		String resultUrl = BASIC_RESULT_URL + "?name=" + name;
		driver.get(resultUrl);
	}

}
