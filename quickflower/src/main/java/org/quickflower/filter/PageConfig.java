package org.quickflower.filter;

import java.util.ArrayList;
import java.util.List;

public class PageConfig {

	private final String url;
	private final List<String> visibleElementXPaths;

	public PageConfig(String url) {
		this.url = url;
		visibleElementXPaths = new ArrayList<String>();
	}

	public void showByXPath(String xpath) {
		visibleElementXPaths.add(xpath);
	}

	public String getUrl() {
		return url;
	}

	public List<String> getVisibleElementXPaths() {
		return visibleElementXPaths;
	}

}
