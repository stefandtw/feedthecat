package feedthecat.page;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import cuke4duke.annotation.I18n.EN.Then;
import cuke4duke.annotation.I18n.EN.When;
import feedthecat.tools.Browser;

public class PageWithPanel {

	private final WebDriver driver;

	public PageWithPanel(Browser browser) {
		this.driver = browser.getDriver();
	}

	@When("I click on the '(.*)' link")
	public void clickOnLink(String linkText) {
		WebElement link = driver.findElement(By.linkText(linkText));
		link.click();
	}

	@Then("show the '(.*)' page")
	public void showThePage(String pageName) {
		WebElement headline = driver.findElement(By.tagName("h2"));
		assertThat(headline.getText(), is(pageName));
	}

}
