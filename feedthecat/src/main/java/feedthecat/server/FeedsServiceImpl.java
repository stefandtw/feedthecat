package feedthecat.server;

import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import feedthecat.client.service.FeedsService;
import feedthecat.datasource.DataSource;
import feedthecat.shared.FeedConfig;

@SuppressWarnings("serial")
@Singleton
public class FeedsServiceImpl extends RemoteServiceServlet implements
		FeedsService {

	@Inject
	private DataSource dataSource;

	@Override
	public List<FeedConfig> getFeedConfigs() {
		return dataSource.loadFeeds();
	}

	@Override
	public void deleteFeed(FeedConfig feedConfig) {
		dataSource.deleteFeed(feedConfig);
	}
}
