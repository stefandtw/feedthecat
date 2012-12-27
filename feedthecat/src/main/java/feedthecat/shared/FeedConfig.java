package feedthecat.shared;

import java.io.Serializable;

public class FeedConfig implements Serializable {

	private String name;
	private String url;
	private String description = "";
	private String titleSelectorString;
	private String contentSelectorString;
	private String linkSelectorString;
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

	public String getTitleSelectorString() {
		return titleSelectorString;
	}

	public void setTitleSelectorString(String titleSelectorString) {
		this.titleSelectorString = titleSelectorString;
	}

	public String getContentSelectorString() {
		return contentSelectorString;
	}

	public void setContentSelectorString(String contentSelectorString) {
		this.contentSelectorString = contentSelectorString;
	}

	public String getLinkSelectorString() {
		return linkSelectorString;
	}

	public void setLinkSelectorString(String linkSelectorString) {
		this.linkSelectorString = linkSelectorString;
	}

	public void setUserAgentForScraping(String userAgent) {
		this.userAgentForScraping = userAgent;
	}

	public String getUserAgentForScraping() {
		return userAgentForScraping;
	}

}
