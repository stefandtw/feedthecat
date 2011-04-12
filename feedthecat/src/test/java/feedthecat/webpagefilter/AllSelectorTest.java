package feedthecat.webpagefilter;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.io.IOException;
import java.net.MalformedURLException;

import org.junit.Test;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import feedthecat.page.tools.LocalResource;

public class AllSelectorTest {

	private static final String NEWS_URL = new LocalResource("Wikinews.html")
			.getUrl();

	@Test
	public void selectionEqualsInput() throws FailingHttpStatusCodeException,
			MalformedURLException, IOException {
		Filter filter = new Filter(NEWS_URL);
		String unfilteredPageText = filter.loadPage().asText();
		HtmlPage filteredPage = filter.getResultPage(new AllSelector());

		assertThat(filteredPage.asText(), equalTo(unfilteredPageText));
	}

}
