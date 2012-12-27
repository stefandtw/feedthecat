package feedthecat.application;

import com.google.inject.servlet.ServletModule;

import feedthecat.server.FeedsServiceImpl;

public class ServletConfig extends ServletModule {
	@Override
	protected void configureServlets() {
		super.configureServlets();
		serve("/feedthecat/feeds").with(FeedsServiceImpl.class);
	};

}
