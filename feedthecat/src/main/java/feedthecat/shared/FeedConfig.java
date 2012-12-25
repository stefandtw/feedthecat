package feedthecat.shared;

public class FeedConfig {

	private String name;
	private String url;
	private String description = "";
	private Selector titleSelector;
	private Selector contentSelector = new EmptySelector();
	private Selector linkSelector = new EmptySelector();
	private String userAgentForScraping;

	public FeedConfig(String url) {
		this.url = url;
	}

	public FeedConfig() {
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public Selector getTitleSelector() {
		return titleSelector;
	}

	public void setTitleSelector(Selector titleSelector) {
		this.titleSelector = titleSelector;
	}

	public Selector getContentSelector() {
		return contentSelector;
	}

	public void setContentSelector(Selector contentSelector) {
		this.contentSelector = contentSelector;
	}

	public Selector getLinkSelector() {
		return linkSelector;
	}

	public void setLinkSelector(Selector linkSelector) {
		this.linkSelector = linkSelector;
	}

	public void setUserAgentForScraping(String userAgent) {
		this.userAgentForScraping = userAgent;
	}

	public String getUserAgentForScraping() {
		return userAgentForScraping;
	}

}
