package org.quickflower.filter;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class PageFilterTest {

	private static final String WEATHER_URL = "http://www.google.com/search?q=weather+berlin";
	private static final String WEATHER_XPATH = "/html/body[@id='gsr']/div[@id='cnt']/div[2]/div[@id='center_col']/div[@id='res']/div[1]/table/tbody/tr[2]/td/div[2]/nobr";

	@Test
	public void filterOutGoogleTexts() {
		PageConfig pageConfig = new PageConfig(WEATHER_URL);
		pageConfig.showByXPath(WEATHER_XPATH);

		String pageSource = new PageFilter(pageConfig).getResultHtml();
		assertThat(pageSource, containsString("°"));
		assertThat(pageSource, not(containsString("Google")));
	}

}
