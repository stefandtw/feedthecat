package feedthecat.application;

import com.google.inject.servlet.ServletModule;

import feedthecat.server.FeedBuilderServlet;
import feedthecat.server.FeedsServiceImpl;
import feedthecat.server.NewFeedServiceImpl;

public class ServletConfig extends ServletModule {

	public static final String FEEDS_SERVICE_RELATIVE_PATH = "feeds";
	public static final String NEW_FEED_SERVICE_RELATIVE_PATH = "newfeed";
	public static final String FEED_BUILDER_PATH = "/feedthecat/feed";

	@Override
	protected void configureServlets() {
		super.configureServlets();
		serve("/feedthecat/" + FEEDS_SERVICE_RELATIVE_PATH).with(
				FeedsServiceImpl.class);
		serve("/feedthecat/" + NEW_FEED_SERVICE_RELATIVE_PATH).with(
				NewFeedServiceImpl.class);
		serve(FEED_BUILDER_PATH).with(FeedBuilderServlet.class);
	};

}
