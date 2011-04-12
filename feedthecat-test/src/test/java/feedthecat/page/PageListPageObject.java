package feedthecat.page;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import feedthecat.tools.Browser;
import feedthecat.tools.Settings;

public class PageListPageObject {

	private static final String PAGE_LIST_URL = Settings.BASE_URL + "pageList";
	private static final String DELETE_TEXT = "delete";
	private final WebDriver driver;

	public PageListPageObject(Browser browser) {
		this.driver = browser.getDriver();
	}

	@Given("^page list$")
	public void pageList() {
		driver.get(PAGE_LIST_URL);
	}

	@Given("^all existing pages have been deleted$")
	public void allExistingPagesHaveBeenDeleted() {
		pageList();
		List<WebElement> deleteLinks = driver.findElements(By
				.linkText(DELETE_TEXT));
		while (!deleteLinks.isEmpty()) {
			deleteLinks.get(0).click();
			deleteLinks = driver.findElements(By.linkText(DELETE_TEXT));
		}
		showPageListWithEntries(0);
	}

	@Then("^show a page list with (\\d+) entries$")
	public void showPageListWithEntries(int entries) {
		fail("not yet implemented ?!");
	}

	@Then("^link '(.*)' leads to the page '(.*)'$")
	public void linkLeadsToThePage(String linkText, String pageName)
			throws UnsupportedEncodingException {
		WebElement link = driver.findElement(By.linkText(linkText));
		link.click();
		String pageSource = driver.getPageSource();
		String url = driver.getCurrentUrl();
		driver.navigate().back();

		String expectedUrlPart = pageName.replaceAll("\\s", "%20");
		assertThat(url, containsString(expectedUrlPart));
		assertThat(pageSource, containsString("<html"));
	}

}
