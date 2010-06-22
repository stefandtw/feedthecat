package feedthecat.page;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import org.openqa.selenium.WebDriver;

import cuke4duke.annotation.I18n.EN.Then;
import feedthecat.tools.Browser;
import feedthecat.tools.Settings;

public class FilteredPageResult {

	private static final String BASIC_RESULT_URL = Settings.BASE_URL + "page";

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
