package feedthecat.filter;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.SyndFeedOutput;
import com.sun.syndication.io.XmlReader;

import feedthecat.page.tools.FeedAssert;
import feedthecat.page.tools.LocalResource;
import feedthecat.webpagefilter.FeedBuilder;
import feedthecat.webpagefilter.FeedConfig;
import feedthecat.webpagefilter.XPathSelector;

public class FeedBuilderTest {

	private static final String NEWS_NAME = "wikipedia news";
	private static final String NEWS_DESCRIPTION = "Wiki News: News reported by users.";
	private static final String NEWS_URL = new LocalResource("Wikinews.html")
			.getUrl();

	@Test
	public void feedTitleEqualsConfigName() {
		SyndFeed feed = createWikiNewsFeed();
		assertThat(feed.getTitle(), is(NEWS_NAME));
	}

	@Test
	public void feedUrlLinkEqualsUrlInConfig() {
		SyndFeed feed = createWikiNewsFeed();
		assertThat(feed.getLink(), is(NEWS_URL));
	}

	@Test
	public void testFeedDescription() {
		SyndFeed feed = createWikiNewsFeed();
		assertThat(feed.getDescription(), is(NEWS_DESCRIPTION));
	}

	@Test
	public void testFeedTitles() {
		SyndFeed feed = createWikiNewsFeed();
		FeedAssert.assertEntryTitle(feed, 0,
				"Noynoy Aquino elected Philippine president");
		FeedAssert
				.assertEntryTitle(feed, 1,
						"'Dewey Defeats Truman' incident in California State Senate election");
		FeedAssert
				.assertEntryTitle(feed, 2,
						"Football: Chelsea confirm Joe Cole and Michael Ballack departure");
	}

	@Test
	public void testFeedEntryLinks() {
		SyndFeed feed = createWikiNewsFeed();
		FeedAssert
				.assertEntryLink(
						feed,
						0,
						"http://en.wikinews.org/wiki/Noynoy_Aquino_elected_Philippine_president?dpl_id=190484");
		FeedAssert
				.assertEntryLink(
						feed,
						1,
						"http://en.wikinews.org/wiki/%27Dewey_Defeats_Truman%27_incident_in_California_State_Senate_election?dpl_id=190686");
		FeedAssert
				.assertEntryLink(
						feed,
						2,
						"http://en.wikinews.org/wiki/Football:_Chelsea_confirm_Joe_Cole_and_Michael_Ballack_departure?dpl_id=190604");

	}

	private SyndFeed createWikiNewsFeed() {
		FeedConfig config = createWikiNewsConfig();
		SyndFeed feed = new FeedBuilder(config).getFeed();
		return feed;
	}

	private FeedConfig createWikiNewsConfig() {
		FeedConfig config = new FeedConfig();
		config.setName(NEWS_NAME);
		String titleXPath = "/html/body/div[@id='content']/div[@id='bodyContent']/table/tbody/tr[2]/td[@id='MainPage_latest_news']/div[@id='MainPage_latest_news_text']/ul/li/a";
		config.setTitleSelector(new XPathSelector(titleXPath));
		config.setLinkSelector(new XPathSelector("."));
		config.setUrl(NEWS_URL);
		config.setDescription(NEWS_DESCRIPTION);
		return config;
	}

	@Test
	public void generatedFeedIsValid() throws IllegalArgumentException,
			FeedException, IOException {
		SyndFeed originalFeed = createWikiNewsFeed();
		String xml = new SyndFeedOutput().outputString(originalFeed);
		SyndFeedInput input = new SyndFeedInput();
		InputStream stream = new ByteArrayInputStream(xml.getBytes());
		SyndFeed feed = input.build(new XmlReader(stream, "UTF-8"));
		assertNotNull(feed);
	}

}
