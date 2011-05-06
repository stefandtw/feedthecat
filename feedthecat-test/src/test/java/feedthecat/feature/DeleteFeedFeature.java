package feedthecat.feature;

import net.sourceforge.jwebunit.junit.WebTester;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;
import com.mycila.testing.junit.MycilaJunitRunner;
import com.mycila.testing.plugin.guice.GuiceContext;

import feedthecat.page.CreateNewFeed;
import feedthecat.page.FeedListPageObject;
import feedthecat.testsuite.TestModule;

/**
 * Feature: Delete a feed
 * 
 * As a user I want to delete a feed that is no longer relevant.
 */
@RunWith(MycilaJunitRunner.class)
@GuiceContext(TestModule.class)
public class DeleteFeedFeature {

	@Inject
	WebTester tester;

	private FeedListPageObject feedListPageObject;

	@Before
	public void setUpDeleteExistingFeeds() {
		feedListPageObject = new FeedListPageObject(tester);
		feedListPageObject.allExistingFeedsHaveBeenDeleted();

		CreateNewFeed createNewFeedPage = new CreateNewFeed(tester);
		createNewFeedPage.aFeedCalled("feed 1");
		createNewFeedPage.aFeedCalled("feed 2");

	}

	@Test
	public void deletedFeedsDisapperFromList() {
		// Given feed list page
		feedListPageObject.feedListPage();
		// When I click on the delete button next to 'feed 1'
		feedListPageObject.iClickOnTheDeleteButtonNextTo("feed 1");
		// Then show a feed list with 1 entries
		feedListPageObject.showAFeedListWithEntries(1);
		// And feed list has a link 'feed 2'
		feedListPageObject.feedListHasALink("feed 2");

	}
}
