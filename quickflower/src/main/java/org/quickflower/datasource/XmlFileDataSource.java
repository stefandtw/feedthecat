package org.quickflower.datasource;

import org.quickflower.webpagefilter.FeedConfig;
import org.quickflower.webpagefilter.PageConfig;

import com.google.inject.Singleton;

@Singleton
public class XmlFileDataSource implements DataSource {

	private volatile PageConfig pageConfig;
	private volatile FeedConfig feedConfig;

	// TODO implement persistence and support multiple configurations

	@Override
	public PageConfig loadPageConfig(String name) {
		return pageConfig;
	}

	@Override
	public void savePageConfig(PageConfig pageConfig) {
		this.pageConfig = pageConfig;
	}

	@Override
	public FeedConfig loadFeedConfig(String name) {
		return feedConfig;
	}

	@Override
	public void saveFeedConfig(FeedConfig feedConfig) {
		this.feedConfig = feedConfig;
	}

}
