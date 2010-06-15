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
	}

}
