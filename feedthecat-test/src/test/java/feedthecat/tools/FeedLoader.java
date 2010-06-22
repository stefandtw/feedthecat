package feedthecat.tools;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

public class FeedLoader {

	private static final String URL_PREFIX = Settings.BASE_URL + "feed?name=";

	public static SyndFeed get(String feedName) {
		feedName = feedName.replaceAll(" ", "%20");
		URL feedUrl = null;
		try {
			feedUrl = new URL(URL_PREFIX + feedName);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		SyndFeedInput input = new SyndFeedInput();
		SyndFeed feed = null;
		try {
			feed = input.build(new XmlReader(feedUrl));
		} catch (FeedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return feed;
	}

}
