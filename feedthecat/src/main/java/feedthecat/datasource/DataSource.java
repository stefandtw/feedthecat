package feedthecat.datasource;

import feedthecat.webpagefilter.FeedConfig;
import feedthecat.webpagefilter.PageConfig;

public interface DataSource {

	public PageConfig loadPageConfig(String name);

	public void savePageConfig(PageConfig pageConfig);

	public FeedConfig loadFeedConfig(String name);

	public void saveFeedConfig(FeedConfig feedConfig);

}
