package feedthecat.tools;

import net.sourceforge.jwebunit.junit.WebTester;

import com.google.inject.Singleton;

@Singleton
public class Browser {

	private WebTester webTester;

	public Browser() {
		startBrowser();
		webTester.setBaseUrl(Settings.HOMEPAGE_URL);
	}

	private void startBrowser() {
		webTester = new WebTester();

		// Thread hook = new QuitBrowserHook();
		// Runtime.getRuntime().addShutdownHook(hook);
	}

	// private void closeBrowser() {
	// webTester.closeBrowser();
	// }

	// private final class QuitBrowserHook extends Thread {
	// @Override
	// public void run() {
	// closeBrowser();
	// }
	// }

	public WebTester getWebTester() {
		return webTester;
	}

}
