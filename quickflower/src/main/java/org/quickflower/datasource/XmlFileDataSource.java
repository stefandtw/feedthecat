package org.quickflower.datasource;

import org.quickflower.webpagefilter.PageConfig;

import com.google.inject.Singleton;

@Singleton
public class XmlFileDataSource implements DataSource {

	private volatile PageConfig pageConfig;

	// TODO implement persistence and support multiple configurations

	@Override
	public PageConfig load(String name) {
		return pageConfig;
	}

	@Override
	public void save(PageConfig pageConfig) {
		this.pageConfig = pageConfig;
	}

}
