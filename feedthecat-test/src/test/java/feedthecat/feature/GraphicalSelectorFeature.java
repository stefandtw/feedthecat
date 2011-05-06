package feedthecat.feature;

import static org.junit.Assert.*;
import net.sourceforge.jwebunit.junit.WebTester;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;
import com.mycila.testing.junit.MycilaJunitRunner;
import com.mycila.testing.plugin.guice.GuiceContext;

import feedthecat.page.CreateNewFeed;
import feedthecat.testsuite.TestModule;

/**
 * Feature: Select elements on a page graphically
 * 
 * As a web user I want to select interesting parts of a web page so I can use
 * them for feeds. I want to do this graphically instead of typing an XPath
 * expression.
 */
@RunWith(MycilaJunitRunner.class)
@GuiceContext(TestModule.class)
public class GraphicalSelectorFeature {

	@Inject
	private WebTester tester;

	@Test
	public void showsPreviewOfEnteredUrl() {
		CreateNewFeed createNewFeed = new CreateNewFeed(tester);
		// Given page to create a new feed
		createNewFeed.pageToCreateANewFeed();
		// When I set source url to local file 'Wikinews.html'
		createNewFeed.setSourceUrlToLocalFile("Wikinews.html");
		// And I choose graphical selector for 'titleSelector'
		createNewFeed.iChooseGraphicalSelectorFor("titleSelector");
		// Then show preview of local file 'Wikinews.html'
		fail("nyi: NEW FEATURE ");
		// createNewFeed.showPreviewOfLocalFile("Wikinews.html");
	}

	@Test
	public void forWikipediaNews() {
		//
		// Given preview page of local file 'Wikinews.html'
		// When I click on the link ''Noynoy Aquino elected Philippine
		// president''
		// And I click on the ''Ok'' button
		// Then selection 0 as text is ''Noynoy Aquino elected Philippine
		// president''
		// And selection 1 as text is '''Dewey Defeats Truman' incident in
		// California State Senate election''
		// And selection 2 as text is ''Football: Chelsea confirm Joe Cole and
		// Michael Ballack departure''
		fail("nyi: NEW FEATURE ");
	}

}
