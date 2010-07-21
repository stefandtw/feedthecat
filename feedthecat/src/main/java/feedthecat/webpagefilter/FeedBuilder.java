package feedthecat.webpagefilter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.sun.syndication.feed.atom.Content;
import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndContentImpl;
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
		this.filter = new Filter(feedConfig.getUrl());
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
		SyndFeed feed = new SyndFeedImpl();
		feed.setFeedType("atom_1.0");
		feed.setTitle(feedConfig.getName());
		feed.setLink(feedConfig.getUrl());
		feed.setDescription(feedConfig.getDescription());
		List<SyndEntry> entries = createEntries();
		feed.setEntries(entries);
		return feed;
	}

	private List<SyndEntry> createEntries() {
		HtmlPage page = filter.loadPage();
		Selector titleSelector = feedConfig.getTitleSelector();
		List<HtmlElement> titleElements = titleSelector.getElements(page
				.getDocumentElement());
		Selector contentSelector = feedConfig.getContentSelector();

		List<SyndEntry> entries = new ArrayList<SyndEntry>();
		for (HtmlElement titleElement : titleElements) {
			SyndEntry entry = new SyndEntryImpl();
			String title = titleElement.asText();
			entry.setTitle(title);

			SyndContent content = new SyndContentImpl();
			content.setType(Content.HTML);
			String contentValue = Filter.asHtmlSource(contentSelector
					.getElements(titleElement));
			content.setValue(contentValue);
			entry.setContents(Arrays.asList(content));

			entries.add(entry);
		}
		return entries;
	}
}
