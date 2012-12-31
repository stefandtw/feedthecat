package feedthecat.client;

import com.google.gwt.user.client.ui.Frame;

public class ExternalPageFrame extends Frame {

	public ExternalPageFrame(String url) {
		setUrl(url);
		setWidth("100%");
		setHeight("100%");
	}
}
