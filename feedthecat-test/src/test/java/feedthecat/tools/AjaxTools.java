package feedthecat.tools;

import static org.junit.Assert.*;
import junit.framework.AssertionFailedError;
import net.sourceforge.jwebunit.junit.WebTester;

public class AjaxTools {

	public static void waitForElement(WebTester tester, String xpath) {
		int timeout = 7;
		for (int i = 0; i < timeout; i++) {
			try {
				tester.getElementByXPath(xpath);
				return;
			} catch (AssertionFailedError e) {
				// keep trying
			}
			try {
				Thread.sleep(200 + i * 500);
			} catch (InterruptedException e) {
				fail(e.getMessage());
			}
		}
		fail("Element not found (timeout: " + timeout + ") - " + xpath);
	}

}
