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
 * Feature: List all feeds
 * 
 * As a visitor I want to see all feeds created in the application.
 */
@RunWith(MycilaJunitRunner.class)
@GuiceContext(TestModule.class)
public class ListFeedsFeature {

	@Inject
	WebTester tester;

	FeedListPageObject feedListPage;

	@Before
	public void setUpEmptyFeedList() {
		feedListPage = new FeedListPageObject(tester);
		feedListPage.allExistingFeedsHaveBeenDeleted();
	}

	@Test
	public void zeroFeedsAreAvailable() {
		// * Given feed list page
		feedListPage.feedListPage();
		// Then show a feed list with 0 entries
		feedListPage.showAFeedListWithEntries(0);

	}

	@Test
	public void twoFeedsAreAvailable() {
		// * Given a feed called 'feed 1' And a feed called 'feed 2' And feed
		CreateNewFeed createNewFeedPage = new CreateNewFeed(tester);
		createNewFeedPage.aFeedCalled("feed 1");
		createNewFeedPage.aFeedCalled("feed 2");
		// list
		// * page Then show a feed list with 2 entries
		feedListPage.feedListPage();
		feedListPage.showAFeedListWithEntries(2);
		// And feed list has a link
		// * 'feed 1' And feed list has a link 'feed 2'
		feedListPage.feedListHasALink("feed 1");
		feedListPage.feedListHasALink("feed 2");

		// And link 'feed 1' leads to
		// * the feed 'feed 1' And link 'feed 2' leads to the feed 'feed 2'
		feedListPage.linkLeadsToTheFeed("feed 1", "feed 1");
		feedListPage.linkLeadsToTheFeed("feed 2", "feed 2");

	}
}
