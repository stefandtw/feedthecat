package feedthecat.tools;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.util.List;

import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;

public class FeedAssert {

	public static void assertEntryTitle(SyndFeed feed, int index,
			String expected) {
		SyndEntry entry = getEntry(feed, index);
		assertThat(entry.getTitle(), is(expected));
	}

	private static SyndEntry getEntry(SyndFeed feed, int index) {
		assertNotNull("feed must not be null", feed);
		List<SyndEntry> entries = feed.getEntries();
		SyndEntry entry = entries.get(index);
		return entry;
	}

	public static void assertEntryContentText(SyndFeed feed, int index,
			String expected) {
		SyndEntry entry = getEntry(feed, index);
		List<SyndContent> contents = entry.getContents();
		String text = "";
		for (SyndContent content : contents) {
			text += content.getValue();
		}
		assertThat(text, is(expected));
	}
}
