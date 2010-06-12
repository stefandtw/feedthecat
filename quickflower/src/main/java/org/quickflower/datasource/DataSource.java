package org.quickflower.datasource;

import org.quickflower.webpagefilter.FeedConfig;
import org.quickflower.webpagefilter.PageConfig;

public interface DataSource {

	public PageConfig loadPageConfig(String name);

	public void savePageConfig(PageConfig pageConfig);

	public FeedConfig loadFeedConfig(String name);

	public void saveFeedConfig(FeedConfig feedConfig);

}
