package feedthecat.webpagefilter;

public class PageBuilder {

	private final PageConfig pageConfig;
	private final Filter filter;

	public PageBuilder(PageConfig pageConfig) {
		this.pageConfig = pageConfig;
		this.filter = new Filter(pageConfig);
	}

	public String getResultHtml() {
		return filter.getResultHtml(pageConfig.getVisibleElementXPaths());
	}
}
