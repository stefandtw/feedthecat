package feedthecat.webpagefilter;

public class FeedConfig implements Config {

	private String name;
	private String url;
	private String description = "";
	private Selector titleSelector;
	private Selector contentSelector = new EmptySelector();
	private Selector linkSelector = new EmptySelector();

	public FeedConfig(String url) {
		this.url = url;
	}

	public FeedConfig() {
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

}
