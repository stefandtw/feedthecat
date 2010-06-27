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
import feedthecat.generatedresource.GeneratedPageResource;
import feedthecat.webpagefilter.PageConfig;

public class PageList extends WebPage {

	@Inject
	private DataSource dataSource;

	public PageList() {
		add(new NavigationPanel("navigationPanel"));
		final List<PageConfig> pages = dataSource.loadPages();
		add(new ListView<PageConfig>("pageListItem", pages) {

			@Override
			protected void populateItem(ListItem<PageConfig> item) {
				final PageConfig pageConfig = item.getModelObject();
				Link pageLink = new Link("pageLink") {
					@Override
					public void onClick() {
						getRequestCycle()
								.setRequestTarget(
										new RedirectRequestTarget(
												(String) urlFor(new ResourceReference(
														GeneratedPageResource.REFERENCE_NAME))
														+ "?name="
														+ pageConfig.getName()));
					}
				};
				Label label = new Label("pageLinkText", pageConfig.getName());
				label.setRenderBodyOnly(true);
				pageLink.add(label);
				item.add(pageLink);
				item.add(new Link("deletePageLink") {
					@Override
					public void onClick() {
						dataSource.deletePage(pageConfig);
						pages.remove(pageConfig);
					}
				});
			}
		});
	}

}
