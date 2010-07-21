package feedthecat.page.tools;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.Assert;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;

public class FeedAssert {

	public static void assertEntryTitle(SyndFeed feed, int index,
			String expected) {
		SyndEntry entry = getEntry(feed, index);
		assertThat(entry.getTitle(), is(expected));
	}

	private static SyndEntry getEntry(SyndFeed feed, int index) {
		Assert.assertNotNull("feed must not be null", feed);
		List<SyndEntry> entries = feed.getEntries();
		SyndEntry entry = entries.get(index);
		return entry;
	}

	public static void assertEntryLink(SyndFeed feed, int index, String expected) {
		SyndEntry entry = getEntry(feed, index);
		assertThat(entry.getLink(), is(expected));
	}

}
