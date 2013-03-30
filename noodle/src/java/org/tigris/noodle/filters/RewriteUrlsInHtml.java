package org.tigris.noodle.filters;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.tigris.noodle.NoodleData;
import org.tigris.noodle.NoodleResponseFilter;
import org.tigris.noodle.ResponseBlock;

import HTTPClient.ModuleException;

public class RewriteUrlsInHtml implements NoodleResponseFilter {

	@Override
	public int filter(NoodleData noodleData, ResponseBlock block)
			throws Exception {
		String contentType = noodleData.getClientResponse().getContentType();
		String characterEncoding = noodleData.getClientResponse()
				.getCharacterEncoding();
		if (contentType == null || !contentType.contains("html")) {
			return KILL_THIS_FILTER;
		}

		String urlPrefix = (String) noodleData.getClientRequest().getAttribute(
				ChangeRemoteUrl.PROXY_PREFIX);
		byte[] body = readResponseBody(noodleData);

		String rawHtml = new String(body, characterEncoding);
		String rewrittenHtml = rewriteHtml(rawHtml, urlPrefix);
		noodleData.getClientResponse().getWriter().write(rewrittenHtml);
		return KILL_THIS_FILTER;
	}

	private byte[] readResponseBody(NoodleData noodleData) throws IOException,
			ModuleException {
		InputStream inputStream = noodleData.getProxyResponse()
				.getInputStream();
		DataInputStream dataInputStream = new DataInputStream(inputStream);
		byte[] data = new byte[dataInputStream.available()];
		dataInputStream.readFully(data);
		return data;
	}

	private String rewriteHtml(String html, String urlPrefix) {
		Document doc = Jsoup.parse(html);

		rewriteElements("script", "src", urlPrefix, doc);
		rewriteElements("link", "href", urlPrefix, doc);
		rewriteElements("a", "href", urlPrefix, doc);
		rewriteElements("img", "src", urlPrefix, doc);

		return doc.outerHtml();
	}

	private void rewriteElements(String elementName, String attributeName,
			String urlPrefix, Document doc) {
		String cssQuery = elementName + "[" + attributeName + "]";
		debug("selecting... " + cssQuery); // TODO remove
		Elements elements = doc.select(cssQuery);
		debug(elements.size() + " elements.");
		for (Element element : elements) {
			debug("next element: " + elementName + ", " + attributeName + "="
					+ element.attr(attributeName)); // TODO remove
			String absUrl = element.attr("abs:" + attributeName);
			if (!absUrl.startsWith("http")) {
				debug("did not rewrite " + absUrl); // TODO remove
				continue;
			}
			String rewrittenUrl = rewriteUrl(absUrl, urlPrefix);
			element.attributes().put(attributeName, rewrittenUrl);
		}
	}

	private void debug(String string) {
		// TODO remove method
		System.out.println(string);
	}

	private String rewriteUrl(String absUrl, String urlPrefix) {
		try {
			return urlPrefix + URLEncoder.encode(absUrl, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

}
