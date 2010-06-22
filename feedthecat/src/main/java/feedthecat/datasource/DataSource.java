package feedthecat.datasource;

import java.util.List;

import feedthecat.webpagefilter.FeedConfig;
import feedthecat.webpagefilter.PageConfig;

public interface DataSource {

	public PageConfig loadPageConfig(String name);

	public void savePageConfig(PageConfig pageConfig);

	public FeedConfig loadFeedConfig(String name);

	public void saveFeedConfig(FeedConfig feedConfig);

	public List<FeedConfig> loadFeeds();

	public void deleteFeed(FeedConfig feedConfig);

}
