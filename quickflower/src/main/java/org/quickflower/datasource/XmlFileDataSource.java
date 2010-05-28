package org.quickflower.datasource;

import org.quickflower.webpagefilter.PageConfig;

public class XmlFileDataSource implements DataSource {

	@Override
	public PageConfig load(String name) {
		/*
		 * TODO load stuff by name String url =
		 * "http://www.google.com/search?q=weather+berlin"; String xpath =
		 * "/html/body[@id='gsr']/div[@id='cnt']/div[2]/div[@id='center_col']/div[@id='res']/div[1]/table/tbody/tr[2]/td/div[2]/nobr"
		 * ; PageConfig pageConfig = new PageConfig(url);
		 * pageConfig.showByXPath(xpath);
		 */
		String url = "http://www.google.com/search?q=weather+berlin";
		String xpath = "/html/body[@id='gsr']/div[@id='cnt']/div[2]/div[@id='center_col']/div[@id='res']/div[1]/table/tbody/tr[2]/td/div[2]/nobr";
		PageConfig pageConfig = new PageConfig(url);
		pageConfig.showByXPath(xpath);
		// TODO Auto-generated method stub
		return pageConfig;
	}

}
