package org.quickflower.generatedresource;

import java.io.UnsupportedEncodingException;

import org.apache.wicket.markup.html.DynamicWebResource;
import org.quickflower.datasource.DataSource;
import org.quickflower.webpagefilter.FeedBuilder;
import org.quickflower.webpagefilter.FeedConfig;

import com.google.inject.Inject;

public class GeneratedFeedResource extends DynamicWebResource {

	private static final long serialVersionUID = -7600238538904382642L;

	public static final String REFERENCE_NAME = "generatedFeed";
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
				return "application/atom+xml";
			}
		};
	}

	private byte[] loadContent() {
		String name = getParameters().getString(PARAMETER_KEY_NAME);
		FeedConfig feedConfig = dataSource.loadFeedConfig(name);
		FeedBuilder builder = new FeedBuilder(feedConfig);
		final byte[] data;
		try {
			String resultSource = builder.getFeedSource();
			data = resultSource.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("error reading feed", e);
		}
		return data;
	}
}
