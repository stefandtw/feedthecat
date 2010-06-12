package feedthecat.filter;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import feedthecat.webpagefilter.PageConfig;
import feedthecat.webpagefilter.Filter;

public class PageFilterTest {

	private static final String WEATHER_URL = "http://www.google.com/search?q=weather+berlin";
	private static final String WEATHER_XPATH = "/html/body[@id='gsr']/div[@id='cnt']/div[2]/div[@id='center_col']/div[@id='res']/div[1]/table/tbody/tr[2]/td/div[2]/nobr";

	@Test
	public void filterOutGoogleTexts() {
		PageConfig pageConfig = new PageConfig();
		pageConfig.setUrl(WEATHER_URL);
		pageConfig.showByXPath(WEATHER_XPATH);

		String pageSource = new Filter(pageConfig).getResultHtml(pageConfig
				.getVisibleElementXPaths());
		assertThat(pageSource, containsString("°"));
		assertThat(pageSource, not(containsString("Google")));
	}

}
