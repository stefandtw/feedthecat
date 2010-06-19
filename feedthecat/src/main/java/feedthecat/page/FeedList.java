package feedthecat.page;

import org.apache.wicket.markup.html.WebPage;

public class FeedList extends WebPage {

	public FeedList() {
		add(new NavigationPanel("navigationPanel"));
	}
}
