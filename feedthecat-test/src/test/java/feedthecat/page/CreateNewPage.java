package feedthecat.page;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.util.List;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import feedthecat.tools.Browser;
import feedthecat.tools.LocalResource;
import feedthecat.tools.Settings;

public class CreateNewPage {

	private static final String HOMEPAGE_URL = Settings.BASE_URL;
	private static final String SOURCE_URL_ID = "sourceUrl";
	private static final String NAME_ID = "name";
	private static final String XPATH_ID = "selector:xpathSelectorDiv:xpath";

	private final WebDriver driver;
	private final Browser browser;

	public CreateNewPage(Browser browser) {
		this.browser = browser;
		this.driver = browser.getDriver();
	}

	@Given("^home page$")
	public void givenHomepage() {
		driver.get(HOMEPAGE_URL);
	}

	@When("^I set source url to '(.*)'$")
	public void setSourceUrlTo(String sourceUrl) {
		driver.findElement(By.name(SOURCE_URL_ID)).sendKeys(sourceUrl);
	}

	@When("^I set source url to local file '(.*)'$")
	public void setSourceUrlToLocalFile(String localFile) {
		String url = new LocalResource(localFile).getUrl();
		driver.findElement(By.name(SOURCE_URL_ID)).sendKeys(url);
	}

	@When("^I set name to '(.*)'$")
	public void setNameTo(String name) {
		driver.findElement(By.name(NAME_ID)).sendKeys(name);
	}

	@When("^I set xpath to ''(.*)''$")
	public void setXPathTo(String xpath) {
		new CreateNewFeed(browser).iChooseXPathSelectorFor("selector");
		driver.findElement(By.name(XPATH_ID)).sendKeys(xpath);
	}

	@When("^I click the '(.*)' button$")
	public void clickThe(String button) {
		driver.findElement(
				By.xpath("/html/body//form//input[@value='" + button + "']"))
				.click();
	}

	@Then("^show error message ''(.*)''$")
	public void showErrorMessage(String message) {
		String className = "feedbackPanelERROR";
		List<WebElement> errorMessageElements = driver.findElements(By
				.className(className));
		boolean found = false;
		for (WebElement element : errorMessageElements) {
			if (element.getText().equals(message)) {
				found = true;
			}
		}
		assertThat(found, is(true));
	}

	@Given("^a page called '(.*)'$")
	public void aPageCalled(String pageName) {
		givenHomepage();
		setSourceUrlTo("Wikinews.html");
		setNameTo(pageName);
		clickThe("Save");
		new FilteredPageResult(browser).resultPageForNameContains(pageName,
				"html");
	}
}
