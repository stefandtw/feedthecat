package feedthecat.datasource;

import java.util.List;

import feedthecat.shared.FeedConfig;

public interface DataSource {

	public FeedConfig loadFeedConfig(String name);

	public void saveFeedConfig(FeedConfig feedConfig);

	public List<FeedConfig> loadFeeds();

	public void deleteFeed(FeedConfig feedConfig);

}
