package feedthecat.webpagefilter;

public class PageBuilder {

	private final PageConfig pageConfig;
	private final Filter filter;

	public PageBuilder(PageConfig pageConfig) {
		this.pageConfig = pageConfig;
		this.filter = new Filter(pageConfig.getUrl());
	}

	public String getResultHtml() {
		Selector selector = new XPathSelector(pageConfig
				.getVisibleElementXPath());
		return filter.getResultHtml(selector);
	}
}
