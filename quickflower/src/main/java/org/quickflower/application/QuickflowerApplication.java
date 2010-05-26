package org.quickflower.application;

import org.apache.wicket.Page;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.protocol.http.WebApplication;
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
		getSharedResources().add("filteredPage", new DynamicPage());
		mountSharedResource("/page/weather", new ResourceReference(
				"filteredPage").getSharedResourceKey());
	}

}