package feedthecat.filter;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import org.junit.Test;

import feedthecat.webpagefilter.Filter;
import feedthecat.webpagefilter.PageConfig;
import feedthecat.webpagefilter.Selector;
import feedthecat.webpagefilter.XPathSelector;

public class PageFilterTest {

	private static final String WEATHER_URL = "http://www.google.com/search?q=weather+berlin";
	private static final String WEATHER_XPATH = "/html/body[@id='gsr']//div[@id='topstuff']/div/table/tbody/tr[2]/td/div[1]/div[1]/b";

	@Test
	public void filterOutGoogleTexts() {
		PageConfig pageConfig = new PageConfig();
		pageConfig.setUrl(WEATHER_URL);
		Selector selector = new XPathSelector(WEATHER_XPATH);
		pageConfig.setContentSelector(selector);

		String pageSource = new Filter(pageConfig.getUrl())
				.getResultHtml(selector);
		assertThat(pageSource, containsString("°"));
		assertThat(pageSource, not(containsString("<input")));
	}

}
