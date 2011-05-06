package feedthecat.feature;

import net.sourceforge.jwebunit.junit.WebTester;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;
import com.mycila.testing.junit.MycilaJunitRunner;
import com.mycila.testing.plugin.guice.GuiceContext;

import feedthecat.testsuite.TestModule;
import feedthecat.tools.Settings;

/**
 * Feature: Navigate the web site
 * 
 * As a visitor I want to reach all important pages by clicking on a link on the
 * home page.
 */
@RunWith(MycilaJunitRunner.class)
@GuiceContext(TestModule.class)
public class NavigateFeature {

	@Inject
	WebTester tester;

	@Test
	public void goToPageCreateAFeed() {
		tester.beginAt(Settings.HOMEPAGE_URL);

		tester.clickLinkWithText("Create a feed");

		tester.assertTitleEquals("FeedTheCat - Create a new feed");
	}

	@Test
	public void goToPageFeeds() {
		tester.beginAt(Settings.HOMEPAGE_URL);

		tester.clickLinkWithText("Feeds");

		tester.assertTitleEquals("FeedTheCat - Feeds");
	}

}
