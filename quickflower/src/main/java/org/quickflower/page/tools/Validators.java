package org.quickflower.page.tools;

import java.net.MalformedURLException;

import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.validator.AbstractValidator;
import org.apache.wicket.validation.validator.UrlValidator;

import com.gargoylesoftware.htmlunit.util.UrlUtils;

public class Validators {

	private static final class FileUrlValidator extends
			AbstractValidator<String> {
		private static final long serialVersionUID = 4334232098106529939L;

		@Override
		protected void onValidate(IValidatable<String> validatable) {
			String url = validatable.getValue();
			if (url.startsWith("file:")) {
				try {
					UrlUtils.toUrlUnsafe(url);
					return;
				} catch (MalformedURLException e) {
				}
			}
			new UrlValidator().validate(validatable);
		}
	}

	public static final AbstractValidator<String> URL_VALIDATOR = new FileUrlValidator();

}
