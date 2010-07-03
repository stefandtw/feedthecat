package feedthecat.webpagefilter;

public class PageConfig implements Config {

	private String name;
	private String url;
	private Selector contentSelector;

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

	public Selector getContentSelector() {
		return contentSelector;
	}

	public void setContentSelector(Selector contentSelector) {
		this.contentSelector = contentSelector;
	}

}
