package feedthecat.webpagefilter;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.xml.serialize.DOMSerializerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class Filter {

	private static final Logger logger = LoggerFactory.getLogger(Filter.class);
	private final Config config;

	public Filter(Config config) {
		this.config = config;
	}

	public String getResultHtml(List<String> visibleXPaths) {
		HtmlPage page = loadPage();
		filter(page, visibleXPaths);
		return new DOMSerializerImpl().writeToString(page);
	}

	public HtmlPage loadPage() {
		WebClient webClient = new WebClient();
		HtmlPage page = null;
		try {
			page = webClient.getPage(config.getUrl());
		} catch (FailingHttpStatusCodeException e) {
			logger.debug(e.getMessage(), e);
		} catch (MalformedURLException e) {
			logger.debug(e.getMessage(), e);
		} catch (IOException e) {
			logger.debug(e.getMessage(), e);
		}
		return page;
	}

	private void filter(HtmlPage page, List<String> visibleXPaths) {
		List<HtmlElement> elements = getVisibleElements(page, visibleXPaths);
		HtmlElement root = page.getBody();
		root.removeAllChildren();
		for (HtmlElement element : elements) {
			root.appendChild(element);
		}
	}

	private List<HtmlElement> getVisibleElements(HtmlPage page,
			List<String> visibleXPaths) {
		List<HtmlElement> visibleElements = new ArrayList<HtmlElement>();
		for (String xpath : visibleXPaths) {
			for (HtmlElement next : (List<HtmlElement>) page.getByXPath(xpath)) {
				HtmlElement clone = (HtmlElement) next.cloneNode(true);
				visibleElements.add(clone);
			}
		}
		return visibleElements;
	}

	public List<String> getVisibleElementsAsText(HtmlPage originalPage,
			String titleXPath) {
		HtmlPage page = originalPage.cloneNode(true);
		List<HtmlElement> visibleElements = getVisibleElements(page, Arrays
				.asList(titleXPath));
		List<String> results = new ArrayList<String>();
		for (HtmlElement element : visibleElements) {
			results.add(element.asText());
		}
		return results;
	}

}
