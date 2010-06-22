package feedthecat.application;

import org.apache.wicket.Page;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.guice.GuiceComponentInjector;
import org.apache.wicket.protocol.http.WebApplication;

import feedthecat.generatedresource.GeneratedFeedResource;
import feedthecat.generatedresource.GeneratedPageResource;
import feedthecat.page.CreateFeed;
import feedthecat.page.CreatePage;
import feedthecat.page.FeedList;
import feedthecat.page.PageList;

public class FeedTheCatApplication extends WebApplication {

	public FeedTheCatApplication() {

	}

	@Override
	public Class<? extends Page> getHomePage() {
		return CreatePage.class;
	}

	@Override
	protected void init() {
		super.init();
		GuiceComponentInjector injector = initDependencyManagement();
		initGeneratedResources(injector);
		mountPageUrls();
	}

	private void initGeneratedResources(GuiceComponentInjector injector) {
		GeneratedPageResource pageResource = (GeneratedPageResource) injector
				.inject(new GeneratedPageResource());
		getSharedResources().add(GeneratedPageResource.REFERENCE_NAME,
				pageResource);
		mountSharedResource("/page", new ResourceReference(
				GeneratedPageResource.REFERENCE_NAME).getSharedResourceKey());

		GeneratedFeedResource feedResource = (GeneratedFeedResource) injector
				.inject(new GeneratedFeedResource());
		getSharedResources().add(GeneratedFeedResource.REFERENCE_NAME,
				feedResource);
		mountSharedResource("/feed", new ResourceReference(
				GeneratedFeedResource.REFERENCE_NAME).getSharedResourceKey());
	}

	private GuiceComponentInjector initDependencyManagement() {
		GuiceComponentInjector injector = new GuiceComponentInjector(this,
				new DefaultModule());
		addComponentInstantiationListener(injector);
		return injector;
	}

	private void mountPageUrls() {
		mountBookmarkablePage("createPage", CreatePage.class);
		mountBookmarkablePage("createFeed", CreateFeed.class);
		mountBookmarkablePage("feedList", FeedList.class);
		mountBookmarkablePage("pageList", PageList.class);
	}
}