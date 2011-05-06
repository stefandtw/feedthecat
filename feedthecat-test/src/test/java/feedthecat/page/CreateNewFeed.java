package feedthecat.page;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import net.sourceforge.jwebunit.junit.WebTester;

import com.sun.syndication.feed.synd.SyndFeed;

import feedthecat.tools.AjaxTools;
import feedthecat.tools.FeedAssert;
import feedthecat.tools.FeedLoader;
import feedthecat.tools.LocalResource;

public class CreateNewFeed {

	private static final String TITLE_XPATH_ID = "titleSelector:xpathSelectorDiv:xpath";
	private static final String LINK_XPATH_ID = "linkSelector:xpathSelectorDiv:xpath";
	private static final String DESCRIPTION_ID = "description";
	private static final String CONTENT_XPATH_ID = "contentSelector:xpathSelectorDiv:xpath";
	private static final String SOURCE_URL_ID = "sourceUrl";
	private static final String NAME_ID = "name";
	private static final String XPATH_ID = "selector:xpathSelectorDiv:xpath";

	private final WebTester tester;

	public CreateNewFeed(WebTester tester) {
		this.tester = tester;
	}

	public void pageToCreateANewFeed() {
		tester.beginAt("/createFeed");
	}

	public void setTitleXPath(String titleXPath) {
		iChooseXPathSelectorFor("titleSelector");
		tester.setTextField(TITLE_XPATH_ID, titleXPath);
	}

	public void setLinkXPath(String linkXPath) {
		iChooseXPathSelectorFor("linkSelector");
		tester.setTextField(LINK_XPATH_ID, linkXPath);
	}

	public void setContentXPath(String contentXPath) {
		iChooseXPathSelectorFor("contentSelector");
		tester.setTextField(CONTENT_XPATH_ID, contentXPath);
	}

	public void setFeedDescription(String description) {
		tester.setTextField(DESCRIPTION_ID, description);
	}

	public void feedItemTitleIs(int index, String feedName, String expected) {
		SyndFeed feed = FeedLoader.get(feedName);
		FeedAssert.assertEntryTitle(feed, index, expected);
	}

	public void feedItemLinkIs(int index, String feedName, String expected) {
		SyndFeed feed = FeedLoader.get(feedName);
		FeedAssert.assertEntryLink(feed, index, expected);
	}

	public void feedItemContentIs(int index, String feedName, String expected) {
		SyndFeed feed = FeedLoader.get(feedName);
		FeedAssert.assertEntryContentContains(feed, index, expected);
	}

	public void feedDescriptionIs(String feedName, String expected) {
		SyndFeed feed = FeedLoader.get(feedName);
		assertThat(feed.getDescription(), is(expected));
	}

	public void aFeedCalled(String feedName) {
		pageToCreateANewFeed();
		setSourceUrlToLocalFile("Wikinews.html");
		setNameTo(feedName);
		setTitleXPath("/foo/xpath");
		tester.clickButtonWithText("Save");
		assertThat(FeedLoader.get(feedName), is(notNullValue()));
	}

	public void iChooseGraphicalSelectorFor(String selectorName) {
		chooseSelectorWithLink(selectorName, "graphically");
	}

	public void iChooseXPathSelectorFor(String selectorName) {
		chooseSelectorWithLink(selectorName, "XPath");
		AjaxTools.waitForElement(tester, "//div[@id='" + selectorName
				+ "']//input");
	}

	private void chooseSelectorWithLink(String selectorName, String linkText) {
		tester.clickElementByXPath("//div[@id='" + selectorName
				+ "']//a[text()='" + linkText + "']");
	}

	public void setSourceUrlToLocalFile(String localFile) {
		String url = new LocalResource(localFile).getUrl();
		tester.setTextField(SOURCE_URL_ID, url);
	}

	public void setNameTo(String name) {
		tester.setTextField(NAME_ID, name);
	}

	public void setXPathTo(String xpath) {
		iChooseXPathSelectorFor("selector");
		tester.setTextField(XPATH_ID, xpath);
	}

}
