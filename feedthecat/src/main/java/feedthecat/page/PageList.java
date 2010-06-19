package feedthecat.page;

import org.apache.wicket.markup.html.WebPage;

public class PageList extends WebPage {

	public PageList() {
		add(new NavigationPanel("navigationPanel"));
	}
}
