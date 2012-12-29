package feedthecat.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import feedthecat.client.service.NewFeedService;
import feedthecat.datasource.DataSource;
import feedthecat.shared.FeedConfig;

@Singleton
public class NewFeedServiceImpl extends RemoteServiceServlet implements
		NewFeedService {

	@Inject
	private DataSource dataSource;

	@Override
	public void createNewFeed(FeedConfig feedConfig) {
		dataSource.saveFeedConfig(feedConfig);
	}
}
