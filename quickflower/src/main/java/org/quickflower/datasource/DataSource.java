package org.quickflower.datasource;

import org.quickflower.webpagefilter.PageConfig;

public interface DataSource {

	public PageConfig load(String name);
	// public FeedConfig load(String name);

}
