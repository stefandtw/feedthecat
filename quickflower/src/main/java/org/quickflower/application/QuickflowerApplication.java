package org.quickflower.application;

import org.apache.wicket.Page;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.guice.GuiceComponentInjector;
import org.apache.wicket.protocol.http.WebApplication;
import org.quickflower.generatedresource.GeneratedPageResource;
import org.quickflower.page.CreatePage;

public class QuickflowerApplication extends WebApplication {

	public QuickflowerApplication() {

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
	}

	private void initGeneratedResources(GuiceComponentInjector injector) {
		GeneratedPageResource resource = (GeneratedPageResource) injector
				.inject(new GeneratedPageResource());
		getSharedResources().add(GeneratedPageResource.REFERENCE_NAME, resource);
		mountSharedResource("/page", new ResourceReference(GeneratedPageResource.REFERENCE_NAME)
				.getSharedResourceKey());
	}

	private GuiceComponentInjector initDependencyManagement() {
		GuiceComponentInjector injector = new GuiceComponentInjector(this,
				new DefaultModule());
		addComponentInstantiationListener(injector);
		return injector;
	}
}