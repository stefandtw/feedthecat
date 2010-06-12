package org.quickflower.filter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;
import org.quickflower.page.tools.FeedAssert;
import org.quickflower.page.tools.LocalResource;
import org.quickflower.webpagefilter.FeedBuilder;
import org.quickflower.webpagefilter.FeedConfig;

import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.SyndFeedOutput;
import com.sun.syndication.io.XmlReader;

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
		assertEquals(feed.getLink(), NEWS_URL);
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
		config.setTitleXPath(titleXPath);
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
