package feedthecat.application;

import org.apache.wicket.Page;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.guice.GuiceComponentInjector;
import org.apache.wicket.protocol.http.WebApplication;

import feedthecat.page.CreateFeed;
import feedthecat.page.FeedList;
import feedthecat.server.generatedresource.GeneratedFeedResource;

public class FeedTheCatApplication extends WebApplication {

	public FeedTheCatApplication() {

	}

	@Override
	public Class<? extends Page> getHomePage() {
		return FeedList.class;
	}

	@Override
	protected void init() {
		super.init();
		GuiceComponentInjector injector = initDependencyManagement();
		initGeneratedResources(injector);
		mountPageUrls();
	}

	private void initGeneratedResources(GuiceComponentInjector injector) {
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
		mountBookmarkablePage("createFeed", CreateFeed.class);
		mountBookmarkablePage("feedList", FeedList.class);
	}
}