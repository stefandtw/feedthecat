package feedthecat.webpagefilter;

import java.util.ArrayList;
import java.util.List;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndFeedImpl;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedOutput;

public class FeedBuilder {

	private final FeedConfig feedConfig;
	private final Filter filter;

	public FeedBuilder(FeedConfig feedConfig) {
		this.feedConfig = feedConfig;
		this.filter = new Filter(feedConfig);
	}

	public String getFeedSource() {
		SyndFeedOutput output = new SyndFeedOutput();
		SyndFeed feed = getFeed();
		try {
			return output.outputString(feed);
		} catch (FeedException e) {
			return "";
		}
	}

	public SyndFeed getFeed() {
		HtmlPage page = filter.loadPage();
		List<String> titles = filter.getVisibleElementsAsText(page, feedConfig
				.getTitleXPath());

		SyndFeed feed = new SyndFeedImpl();
		feed.setFeedType("atom_1.0");
		feed.setTitle(feedConfig.getName());
		feed.setLink(feedConfig.getUrl());
		feed.setDescription(feedConfig.getDescription());
		List<SyndEntry> entries = createEntries(titles);
		feed.setEntries(entries);
		return feed;
	}

	private List<SyndEntry> createEntries(List<String> titles) {
		List<SyndEntry> entries = new ArrayList<SyndEntry>();
		for (String title : titles) {
			SyndEntry entry = new SyndEntryImpl();
			entry.setTitle(title);
			entries.add(entry);
		}
		return entries;
	}

}
