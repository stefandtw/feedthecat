package feedthecat.server;

import com.google.inject.Inject;

import feedthecat.client.service.NewFeedService;
import feedthecat.datasource.DataSource;
import feedthecat.shared.FeedConfig;

public class NewFeedServiceImpl implements NewFeedService {

	@Inject
	private DataSource dataSource;

	@Override
	public void createNewFeed(FeedConfig feedConfig) {
		dataSource.saveFeedConfig(feedConfig);
	}
}
