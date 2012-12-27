package feedthecat.server.webpagefilter;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import org.junit.Test;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import feedthecat.page.tools.LocalResource;
import feedthecat.server.webpagefilter.EmptySelector;
import feedthecat.server.webpagefilter.Filter;

public class EmptySelectorTest {
	private static final String NEWS_URL = new LocalResource("Wikinews.html")
			.getUrl();

	@Test
	public void selectionIsEmpty() throws FailingHttpStatusCodeException,
			MalformedURLException, IOException {
		Filter filter = new Filter(NEWS_URL);
		HtmlPage page = filter.loadPage();
		List<HtmlElement> selection = new EmptySelector().getElements(page
				.getDocumentElement());

		assertThat(selection.size(), is(0));
	}

}
