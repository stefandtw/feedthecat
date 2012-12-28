package feedthecat.application;

import com.google.inject.servlet.ServletModule;

import feedthecat.server.FeedBuilderServlet;
import feedthecat.server.FeedsServiceImpl;

public class ServletConfig extends ServletModule {

	public static final String FEED_BUILDER_PATH = "/feedthecat/feed";

	@Override
	protected void configureServlets() {
		super.configureServlets();
		serve("/feedthecat/feeds").with(FeedsServiceImpl.class);
		serve(FEED_BUILDER_PATH).with(FeedBuilderServlet.class);
	};

}
