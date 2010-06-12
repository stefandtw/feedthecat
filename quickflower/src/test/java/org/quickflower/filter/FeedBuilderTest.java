package org.quickflower.filter;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Assert;
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

	private final String wikiNewsUrl = new LocalResource("Wikinews.html")
			.getUrl();
	private SyndFeed build;

	@Test
	public void feedTitleEqualsConfigName() {
		SyndFeed feed = createWikiNewsFeed();
		assertEquals(feed.getTitle(), "wikipedia news");
	}

	@Test
	public void feedUrlLinkEqualsUrlInConfig() {
		SyndFeed feed = createWikiNewsFeed();
		assertEquals(feed.getLink(), wikiNewsUrl);
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
		assertEquals(feed.getLink(), wikiNewsUrl);
	}

	private SyndFeed createWikiNewsFeed() {
		FeedConfig config = createWikiNewsConfig();
		SyndFeed feed = new FeedBuilder(config).getFeed();
		return feed;
	}

	private FeedConfig createWikiNewsConfig() {
		FeedConfig config = new FeedConfig();
		config.setName("wikipedia news");
		String titleXPath = "/html/body/div[@id='content']/div[@id='bodyContent']/table/tbody/tr[2]/td[@id='MainPage_latest_news']/div[@id='MainPage_latest_news_text']/ul/li/a";
		config.setTitleXPath(titleXPath);
		config.setUrl(wikiNewsUrl);
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
		Assert.assertNotNull(feed);
	}

}
