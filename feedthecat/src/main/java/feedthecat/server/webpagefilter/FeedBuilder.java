package feedthecat.server.webpagefilter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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

import feedthecat.shared.FeedConfig;

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
		filter.setUserAgent(feedConfig.getUserAgentForScraping());
		HtmlPage page = filter.loadPage();
		HtmlElement baseElement = page.getDocumentElement();
		List<HtmlElement> titleElements = getElements(baseElement,
				feedConfig.getTitleSelectorString());

		List<SyndEntry> entries = new ArrayList<SyndEntry>();
		for (HtmlElement titleElement : titleElements) {
			SyndEntry entry = new SyndEntryImpl();
			String title = titleElement.asText();
			entry.setTitle(title);

			SyndContent content = new SyndContentImpl();
			content.setType(Content.HTML);
			String contentValue = Filter.asHtmlSource(getElements(titleElement,
					feedConfig.getContentSelectorString()));
			content.setValue(contentValue);
			entry.setContents(Arrays.asList(content));

			List<HtmlElement> linkElementList = getElements(titleElement,
					feedConfig.getLinkSelectorString());
			String entryLink = Filter.getLinkSource(linkElementList);
			entry.setLink(entryLink);

			entries.add(entry);
		}
		return entries;
	}

	private List<HtmlElement> getElements(HtmlElement baseElement,
			String xPathSelectorString) {
		if (xPathSelectorString == null || xPathSelectorString.isEmpty()) {
			return Collections.emptyList();
		}
		Selector selector = new XPathSelector(xPathSelectorString);
		return selector.getElements(baseElement);
	}
}
