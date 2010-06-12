package org.quickflower.webpagefilter;

public class FeedConfig implements Config {

	private String name;
	private String url;
	private String contentXPath;
	private String titleXPath;
	private String description = "";

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

	public void setTitleXPath(String xpath) {
		this.titleXPath = xpath;
	}

	public String getTitleXPath() {
		return titleXPath;
	}

	public void setContentXPath(String xpath) {
		this.contentXPath = xpath;
	}

	public String getContentXPath() {
		return contentXPath;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

}
