package feedthecat.page;

import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;

public class NavigationPanel extends Panel {

	public NavigationPanel(String id) {
		super(id);
		add(new Link("filterPage") {
			@Override
			public void onClick() {
				setResponsePage(new CreatePage());
			}
		});
		add(new Link("createFeed") {
			@Override
			public void onClick() {
				setResponsePage(new CreateFeed());
			}
		});
		add(new Link("feeds") {
			@Override
			public void onClick() {
				setResponsePage(new FeedList());
			}
		});
		add(new Link("webPages") {
			@Override
			public void onClick() {
				setResponsePage(new PageList());
			}
		});
	}

}
