package org.quickflower.page.tools;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.apache.wicket.validation.Validatable;
import org.junit.Test;

public class ValidatorsTest {

	@Test
	public void testCommonUrl() {
		String url = "http://en.wikipedia.org/some/other/stuff";
		assertTrue(isUrlValid(url));
	}

	@Test
	public void allowUnixFileUrls() {
		String url = "file:///some-path/to/some_file";
		assertTrue(isUrlValid(url));
	}

	@Test
	public void allowWindowsFileUrls() {
		String url = "file:/C:\\some-path\\to\\some_file";
		assertTrue(isUrlValid(url));
	}

	@Test
	public void httpUrlNeedsHost() {
		String url = "http:///some-path";
		assertFalse(isUrlValid(url));
	}

	private boolean isUrlValid(String url) {
		Validatable<String> validatable = new Validatable<String>(url);
		Validators.URL_VALIDATOR.validate(validatable);
		return validatable.isValid();
	}

}
