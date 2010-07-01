package feedthecat.webpagefilter;


public class PageConfig implements Config {

	private String name;
	private String url;
	private String visibleElementXPath;

	public void showByXPath(String xpath) {
		visibleElementXPath = xpath;
	}

	@Override
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVisibleElementXPath() {
		return visibleElementXPath;
	}

}
