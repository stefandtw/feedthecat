package feedthecat.client;

import com.google.gwt.user.client.ui.Anchor;

import feedthecat.application.ServletConfig;
import feedthecat.server.FeedBuilderServlet;

public class FeedLink extends Anchor {
	public FeedLink(String text, String feedName) {
		super(text);
		String feedUrl = "" + //
				ServletConfig.FEED_BUILDER_PATH //
				+ "?"//
				+ FeedBuilderServlet.PARAMETER_KEY_NAME//
				+ "="//
				+ feedName;
		setHref(feedUrl);
	}

}
