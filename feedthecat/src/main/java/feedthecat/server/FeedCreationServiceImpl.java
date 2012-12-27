package feedthecat.server;

import com.google.inject.Inject;

import feedthecat.client.service.FeedCreationService;
import feedthecat.datasource.DataSource;
import feedthecat.shared.FeedConfig;

public class FeedCreationServiceImpl implements FeedCreationService {

	@Inject
	private DataSource dataSource;

	@Override
	public void createFeed(FeedConfig feedConfig) {
		dataSource.saveFeedConfig(feedConfig);
	}
}
