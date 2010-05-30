/**
 * 
 */
package org.quickflower.generatedresource;

import java.io.UnsupportedEncodingException;

import org.apache.wicket.markup.html.DynamicWebResource;
import org.quickflower.datasource.DataSource;
import org.quickflower.webpagefilter.PageConfig;
import org.quickflower.webpagefilter.PageFilter;

import com.google.inject.Inject;

public class GeneratedPageResource extends DynamicWebResource {

	public static final String REFERENCE_NAME = "filteredPage";

	private static final long serialVersionUID = -8313265218186839656L;
	private static final String PARAMETER_KEY_NAME = "name";

	@Inject
	private DataSource dataSource;

	@Override
	protected ResourceState getResourceState() {
		final byte[] data = loadContent();
		return new ResourceState() {
			@Override
			public byte[] getData() {
				return data;
			}

			@Override
			public String getContentType() {
				return "text/html";
			}
		};
	}

	private byte[] loadContent() {
		String name = getParameters().getString(PARAMETER_KEY_NAME);
		PageConfig pageConfig = dataSource.load(name);
		PageFilter contentFilter = new PageFilter(pageConfig);
		final byte[] data;
		try {
			String resultSource = contentFilter.getResultHtml();
			data = resultSource.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("error reading dynamic page", e);
		}
		return data;
	}
}