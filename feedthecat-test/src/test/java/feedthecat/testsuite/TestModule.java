/**
 * 
 */
package feedthecat.testsuite;

import net.sourceforge.jwebunit.junit.WebTester;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;

import feedthecat.tools.Browser;

public class TestModule extends AbstractModule {

	@Override
	protected void configure() {
		// bind(Browser.class).toInstance(new Browser());
		// bind(Browser.class);
		// bind(Browser.class).asEagerSingleton();
		// bind(Browser.class).in(Singleton.class);
	}

	@Provides
	WebTester provideWebTester(Browser browser) {
		return browser.getWebTester();
	}
}