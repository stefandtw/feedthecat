package feedthecat.page;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.sun.syndication.feed.synd.SyndFeed;

import cuke4duke.StepMother;
import cuke4duke.Steps;
import cuke4duke.annotation.I18n.EN.Given;
import cuke4duke.annotation.I18n.EN.Then;
import cuke4duke.annotation.I18n.EN.When;
import feedthecat.tools.Browser;
import feedthecat.tools.FeedAssert;
import feedthecat.tools.FeedLoader;
import feedthecat.tools.Settings;

public class CreateNewFeed extends Steps {

	private static final String CREATE_FEED_URL = Settings.BASE_URL
			+ "createFeed";
	private static final String TITLE_XPATH_ID = "titleXPath";
	private static final String DESCRIPTION_ID = "description";

	private final WebDriver driver;

	public CreateNewFeed(Browser browser, StepMother stepMother) {
		super(stepMother);
		this.driver = browser.getDriver();
	}

	@Given("^page to create a new feed$")
	public void pageToCreateANewFeed() {
		driver.get(CREATE_FEED_URL);
	}

	@When("^I set titleXPath to ''(.*)''$")
	public void setTitleXPath(String titleXPath) {
		driver.findElement(By.name(TITLE_XPATH_ID)).sendKeys(titleXPath);
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

	@Then("^description for '(.*)' is '(.*)'$")
	public void feedDescriptionIs(String feedName, String expected) {
		SyndFeed feed = FeedLoader.get(feedName);
		assertThat(feed.getDescription(), is(expected));
	}

	@Given("^a feed called '(.*)'$")
	public void aFeedCalled(String feedName) {
		pageToCreateANewFeed();
		When("I set source url to local file 'Wikinews.html'");
		When("I set name to '" + feedName + "'");
		setTitleXPath("/foo/xpath");
		When("I click the 'Save' button");
		assertThat(FeedLoader.get(feedName), is(notNullValue()));
	}
}
