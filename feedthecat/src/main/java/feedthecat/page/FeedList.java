package feedthecat.page;

import java.util.List;

import org.apache.wicket.ResourceReference;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.request.target.basic.RedirectRequestTarget;

import com.google.inject.Inject;

import feedthecat.datasource.DataSource;
import feedthecat.server.generatedresource.GeneratedFeedResource;
import feedthecat.shared.FeedConfig;

public class FeedList extends WebPage {

	@Inject
	private DataSource dataSource;

	public FeedList() {
		add(new NavigationPanel("navigationPanel"));
		final List<FeedConfig> feeds = dataSource.loadFeeds();
		add(new ListView<FeedConfig>("feedListItem", feeds) {

			@Override
			protected void populateItem(ListItem<FeedConfig> item) {
				final FeedConfig feedConfig = item.getModelObject();
				Link feedLink = new Link("feedLink") {

					@Override
					public void onClick() {
						getRequestCycle()
								.setRequestTarget(
										new RedirectRequestTarget(
												(String) urlFor(new ResourceReference(
														GeneratedFeedResource.REFERENCE_NAME))
														+ "?name="
														+ feedConfig.getName()));
					}
				};
				Label label = new Label("feedLinkText", feedConfig.getName());
				label.setRenderBodyOnly(true);
				feedLink.add(label);
				item.add(feedLink);
				item.add(new Link("deleteFeedLink") {

					@Override
					public void onClick() {
						dataSource.deleteFeed(feedConfig);
						feeds.remove(feedConfig);
					}
				});
			}
		});
	}
}
