/**
 * 
 */
package org.quickflower.application;

import java.io.UnsupportedEncodingException;

import org.apache.wicket.markup.html.DynamicWebResource;
import org.quickflower.filter.PageConfig;
import org.quickflower.filter.PageFilter;

final class DynamicPage extends DynamicWebResource {

	private static final long serialVersionUID = -8313265218186839656L;

	@Override
	protected ResourceState getResourceState() {
		return new ResourceState() {
			@Override
			public byte[] getData() {
				String url = "http://www.google.com/search?q=weather+berlin";
				PageFilter contentFilter = new PageFilter(new PageConfig(url));
				try {
					String resultSource = contentFilter.getResultHtml();
					return resultSource.getBytes("UTF-8");
				} catch (UnsupportedEncodingException e) {
					throw new RuntimeException("error reading dynamic page", e);
				}
			}

			@Override
			public String getContentType() {
				return "text/html";
			}
		};
	}
}