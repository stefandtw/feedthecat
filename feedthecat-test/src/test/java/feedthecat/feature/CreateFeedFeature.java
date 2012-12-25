package feedthecat.feature;

import net.sourceforge.jwebunit.junit.WebTester;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;
import com.mycila.testing.junit.MycilaJunitRunner;
import com.mycila.testing.plugin.guice.GuiceContext;

import feedthecat.page.CreateNewFeed;
import feedthecat.testsuite.TestModule;

/**
 * Feature: Create a feed
 * 
 * As a web user I want a web feed compiled of interesting things on a web page.
 */
@RunWith(MycilaJunitRunner.class)
@GuiceContext(TestModule.class)
public class CreateFeedFeature {

	@Inject
	WebTester tester;

	@Test
	public void showErrorMessages() {
		tester.beginAt("/createFeed");

		tester.clickButtonWithText("Create");

		tester.assertTextPresent("Field 'Name' is required.");
		tester.assertTextPresent("Field 'URL' is required.");
		tester.assertTextPresent("Please select feed entry titles.");
	}

	@Test
	public void wikipediaNews() {
		// Scenario: Wikipedia news
		CreateNewFeed page = new CreateNewFeed(tester);
		//
		// Given page to create a new feed
		tester.beginAt("/createFeed");
		// When I set source url to local file 'Wikinews.html'
		page.setSourceUrlToLocalFile("Wikinews.html");
		// And I set name to 'wikipedia news'
		page.setNameTo("wikipedia news");
		// And I set titleXPath to
		page.setTitleXPath("/html/body/div[@id='content']/div[@id='bodyContent']/table/tbody/tr[2]/td[@id='MainPage_latest_news']/div[@id='MainPage_latest_news_text']/ul/li/a");
		// And I set contentXPath to ''..''
		page.setContentXPath("..");
		// And I set linkXPath to ''.''
		page.setLinkXPath(".");
		// And I set description to 'Wiki News.'
		page.setFeedDescription("Wiki News.");
		// And I click the 'Save' button
		tester.clickButtonWithText("Create");
		// Then feed item title 0 for 'wikipedia news' is ''
		page.feedItemTitleIs(0, "wikipedia news",
				"Noynoy Aquino elected Philippine president");
		// And feed item title 1 for 'wikipedia news' is '''Dewey Defeats
		// Truman'
		// incident in California State Senate election''
		page.feedItemTitleIs(1, "wikipedia news",
				"'Dewey Defeats Truman' incident in California State Senate election");
		// And feed item title 2 for 'wikipedia news' is ''Football: Chelsea
		// confirm Joe Cole and Michael Ballack departure''
		page.feedItemTitleIs(2, "wikipedia news",
				"Football: Chelsea confirm Joe Cole and Michael Ballack departure");
		// And description for 'wikipedia news' is 'Wiki News.'
		page.feedDescriptionIs("wikipedia news", "Wiki News.");
		// And feed item content 0 for 'wikipedia news' contains ''Noynoy Aquino
		// elected Philippine president''
		page.feedItemContentIs(0, "wikipedia news",
				"Noynoy Aquino elected Philippine president");
		// And feed item link 0 for 'wikipedia news' is
		// ''http://en.wikinews.org/wiki/Noynoy_Aquino_elected_Philippine_president?dpl_id=190484''
		page.feedItemLinkIs(
				0,
				"wikipedia news",
				"http://en.wikinews.org/wiki/Noynoy_Aquino_elected_Philippine_president?dpl_id=190484");
	}

}
