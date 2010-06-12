package feedthecat.webpagefilter;

import java.util.ArrayList;
import java.util.List;

public class PageConfig implements Config {

	private String name;
	private String url;
	private final List<String> visibleElementXPaths = new ArrayList<String>();

	public void showByXPath(String xpath) {
		visibleElementXPaths.add(xpath);
	}

	@Override
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<String> getVisibleElementXPaths() {
		return visibleElementXPaths;
	}

	@Override
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
