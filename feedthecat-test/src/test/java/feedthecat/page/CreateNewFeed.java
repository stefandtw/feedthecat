package feedthecat.page;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.steps.Steps;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.sun.syndication.feed.synd.SyndFeed;

import feedthecat.tools.AjaxTools;
import feedthecat.tools.Browser;
import feedthecat.tools.FeedAssert;
import feedthecat.tools.FeedLoader;
import feedthecat.tools.Settings;

public class CreateNewFeed extends Steps {

	private static final String CREATE_FEED_URL = Settings.BASE_URL
			+ "createFeed";
	private static final String TITLE_XPATH_ID = "titleSelector:xpathSelectorDiv:xpath";
	private static final String LINK_XPATH_ID = "linkSelector:xpathSelectorDiv:xpath";
	private static final String DESCRIPTION_ID = "description";
	private static final String CONTENT_XPATH_ID = "contentSelector:xpathSelectorDiv:xpath";

	private final WebDriver driver;
	private final Browser browser;

	public CreateNewFeed(Browser browser) {
		this.browser = browser;
		this.driver = browser.getDriver();
	}

	@Given("^page to create a new feed$")
	public void pageToCreateANewFeed() {
		driver.get(CREATE_FEED_URL);
	}

	@When("^I set titleXPath to ''(.*)''$")
	public void setTitleXPath(String titleXPath) {
		iChooseXPathSelectorFor("titleSelector");
		driver.findElement(By.name(TITLE_XPATH_ID)).sendKeys(titleXPath);
	}

	@When("^I set linkXPath to ''(.*)''$")
	public void setLinkXPath(String linkXPath) {
		iChooseXPathSelectorFor("linkSelector");
		driver.findElement(By.name(LINK_XPATH_ID)).sendKeys(linkXPath);
	}

	@When("^I set contentXPath to ''(.*)''$")
	public void setContentXPath(String contentXPath) {
		iChooseXPathSelectorFor("contentSelector");
		driver.findElement(By.name(CONTENT_XPATH_ID)).sendKeys(contentXPath);
	}

	@When("^I set description to '(.*)'$")
	public void setFeedDescription(String description) {
		driver.findElement(By.name(DESCRIPTION_ID)).sendKeys(description);
	}

	@Then("^feed item title (\\d+) for '(.*)' is ''(.*)''$")
	public void feedItemTitleIs(int index, String feedName, String expected) {
		SyndFeed feed = FeedLoader.get(feedName);
		FeedAssert.assertEntryTitle(feed, index, expected);
	}

	@Then("^feed item link (\\d+) for '(.*)' is ''(.*)''$")
	public void feedItemLinkIs(int index, String feedName, String expected) {
		SyndFeed feed = FeedLoader.get(feedName);
		FeedAssert.assertEntryLink(feed, index, expected);
	}

	@Then("^feed item content (\\d+) for '(.*)' contains ''(.*)''$")
	public void feedItemContentIs(int index, String feedName, String expected) {
		SyndFeed feed = FeedLoader.get(feedName);
		FeedAssert.assertEntryContentContains(feed, index, expected);
	}

	@Then("^description for '(.*)' is '(.*)'$")
	public void feedDescriptionIs(String feedName, String expected) {
		SyndFeed feed = FeedLoader.get(feedName);
		assertThat(feed.getDescription(), is(expected));
	}

	@Given("^a feed called '(.*)'$")
	public void aFeedCalled(String feedName) {
		pageToCreateANewFeed();
		new CreateNewPage(browser).setSourceUrlToLocalFile("Wikinews.html");
		new CreateNewPage(browser).setNameTo(feedName);
		setTitleXPath("/foo/xpath");
		new CreateNewPage(browser).clickThe("Save");
		assertThat(FeedLoader.get(feedName), is(notNullValue()));
	}

	@When("^I choose graphical selector for '(.*)'$")
	public void iChooseGraphicalSelectorFor(String selectorName) {
		driver.findElement(By.id(selectorName))
				.findElement(By.linkText("graphically")).click();
	}

	@When("^I choose xpath selector for '(.*)'$")
	public void iChooseXPathSelectorFor(String selectorName) {
		driver.findElement(By.id(selectorName))
				.findElement(By.linkText("XPath")).click();
		AjaxTools.waitForElement(driver,
				By.name(selectorName + ":xpathSelectorDiv:xpath"));
	}
}
