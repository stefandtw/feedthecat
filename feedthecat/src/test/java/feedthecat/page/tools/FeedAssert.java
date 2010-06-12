package feedthecat.page.tools;

import java.util.List;

import org.junit.Assert;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;

public class FeedAssert {

	public static void assertEntryTitle(SyndFeed feed, int index,
			String expected) {
		Assert.assertNotNull("feed must not be null", feed);
		List<SyndEntry> entries = feed.getEntries();
		SyndEntry entry = entries.get(index);
		Assert.assertEquals(entry.getTitle(), expected);
	}

}
