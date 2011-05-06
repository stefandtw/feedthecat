package feedthecat.page;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.util.List;

import junit.framework.AssertionFailedError;
import net.sourceforge.jwebunit.api.IElement;
import net.sourceforge.jwebunit.junit.WebTester;

import com.sun.syndication.feed.synd.SyndFeed;

import feedthecat.tools.FeedLoader;

public class FeedListPageObject {

	private static final String DELETE_TEXT = "delete";

	private final WebTester tester;

	public FeedListPageObject(WebTester tester) {
		this.tester = tester;
	}

	public void feedListPage() {
		tester.beginAt("/feedList");
	}

	public void allExistingFeedsHaveBeenDeleted() {
		feedListPage();

		boolean moreFeeds = true;
		while (moreFeeds) {
			try {
				tester.clickLinkWithExactText(DELETE_TEXT);
			} catch (AssertionFailedError e) {
				moreFeeds = false;
			}
		}

		showAFeedListWithEntries(0);
	}

	public void showAFeedListWithEntries(int count) {
		List<IElement> listElements = tester
				.getElementsByXPath("/html/body//ul/li");
		assertThat(listElements.size(), is(count));
	}

	public void feedListHasALink(String linkText) {
		tester.assertLinkPresentWithExactText(linkText);
	}

	public void iClickOnTheDeleteButtonNextTo(String feedName) {
		/*
		 * HtmlUnitTestingEngineImpl htmlUnitEngine =
		 * (HtmlUnitTestingEngineImpl) tester .getTestingEngine(); WebClient
		 * webClient = htmlUnitEngine.getWebClient(); HtmlPage page = (HtmlPage)
		 * webClient.getCurrentWindow() .getEnclosedPage(); HtmlElement
		 * deleteLink = page.getFirstByXPath("//a[text()='" + feedName +
		 * "']/..//a[text()='" + DELETE_TEXT + "']"); try { deleteLink.click();
		 * } catch (IOException e) { fail(e.getMessage()); }
		 */
		tester.clickElementByXPath("//a[text()='" + feedName
				+ "']/..//a[text()='" + DELETE_TEXT + "']");
	}

	public void linkLeadsToTheFeed(String linkText, String feedName) {
		tester.clickLinkWithExactText(linkText);
		SyndFeed feed = FeedLoader.get(feedName);
		assertThat(feed.getTitle(), is(feedName));
		feedListPage();
	}
}
