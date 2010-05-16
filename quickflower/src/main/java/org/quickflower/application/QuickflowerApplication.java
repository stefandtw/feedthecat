package org.quickflower.application;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;
import org.quickflower.page.HomePage;

public class QuickflowerApplication extends WebApplication {

	public QuickflowerApplication() {

	}

	@Override
	public Class<? extends Page> getHomePage() {
		return HomePage.class;
	}
}