package org.quickflower.webpagefilter;

import java.util.ArrayList;
import java.util.List;

public class PageConfig {

	private String name;
	private String url;
	private final List<String> visibleElementXPaths = new ArrayList<String>();

	public PageConfig(String url) {
		this.url = url;
	}

	public PageConfig() {
	}

	public void showByXPath(String xpath) {
		visibleElementXPaths.add(xpath);
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<String> getVisibleElementXPaths() {
		return visibleElementXPaths;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
