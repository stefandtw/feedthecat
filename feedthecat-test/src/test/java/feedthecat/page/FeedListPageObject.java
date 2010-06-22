package feedthecat.page;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.sun.syndication.feed.synd.SyndFeed;

import cuke4duke.annotation.I18n.EN.Given;
import cuke4duke.annotation.I18n.EN.Then;
import cuke4duke.annotation.I18n.EN.When;
import feedthecat.tools.Browser;
import feedthecat.tools.FeedLoader;
import feedthecat.tools.Settings;

public class FeedListPageObject {

	private static final String DELETE_TEXT = "delete";
	private static final String FEED_LIST_URL = Settings.BASE_URL + "feedList";
	private final WebDriver driver;

	public FeedListPageObject(Browser browser) {
		this.driver = browser.getDriver();
	}

	@Given("^feed list page$")
	public void feedListPage() {
		driver.get(FEED_LIST_URL);
	}

	@Given("^all existing feeds have been deleted$")
	public void allExistingFeedsHaveBeenDeleted() {
		feedListPage();
		List<WebElement> deleteLinks = driver.findElements(By
				.linkText(DELETE_TEXT));
		while (!deleteLinks.isEmpty()) {
			deleteLinks.get(0).click();
			deleteLinks = driver.findElements(By.linkText(DELETE_TEXT));
		}
		showAFeedListWithEntries(0);
	}

	@Then("^show a feed list with (\\d+) entries$")
	public void showAFeedListWithEntries(int count) {
		List<WebElement> entries = driver.findElements(By
				.xpath("/html/body//ul/li"));
		assertThat(entries.size(), is(count));
	}

	@Given("^feed list has a link '(.*)'$")
	public void feedListHasALink(String feedName) {
		try {
			driver.findElement(By.linkText(feedName));
		} catch (NoSuchElementException e) {
			fail("Feed " + feedName + " is not listed: " + e.getMessage());
		}
	}

	@When("^I click on the delete button next to '(.*)'$")
	public void iClickOnTheDeleteButtonNextTo(String feedName) {
		WebElement feedLink = driver.findElement(By.linkText(feedName));
		WebElement deleteLink = feedLink.findElement(By.xpath(".."))
				.findElement(By.linkText(DELETE_TEXT));
		deleteLink.click();
	}

	@Then("^link '(.*)' leads to the feed '(.*)'$")
	public void linkLeadsToTheFeed(String linkText, String feedName) {
		driver.findElement(By.linkText(linkText)).click();
		SyndFeed feed = FeedLoader.get(feedName);
		assertThat(feed.getTitle(), is(feedName));
		driver.navigate().back();
	}
}
