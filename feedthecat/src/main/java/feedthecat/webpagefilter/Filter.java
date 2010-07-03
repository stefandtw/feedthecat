package feedthecat.webpagefilter;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
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
	private final String url;

	public Filter(String url) {
		this.url = url;
	}

	public String getResultHtml(Selector selector) {
		HtmlPage page = getResultPage(selector);
		return new DOMSerializerImpl().writeToString(page);
	}

	public HtmlPage loadPage() {
		WebClient webClient = new WebClient();
		HtmlPage page = null;
		try {
			page = webClient.getPage(url);
		} catch (FailingHttpStatusCodeException e) {
			logger.debug(e.getMessage(), e);
		} catch (MalformedURLException e) {
			logger.debug(e.getMessage(), e);
		} catch (IOException e) {
			logger.debug(e.getMessage(), e);
		}
		return page;
	}

	private void filter(HtmlPage page, Selector selector) {
		List<HtmlElement> elements = selector.getElements(page
				.getDocumentElement());
		HtmlElement root = page.getBody();
		if (elements.size() == 1
				&& (root.equals(elements.get(0)) || elements.get(0)
						.isAncestorOf(root))) {
			return;
		}
		root.removeAllChildren();
		for (HtmlElement element : elements) {
			root.appendChild(element);
		}
	}

	public List<String> getVisibleElementsAsText(HtmlPage originalPage,
			Selector itemsSelector) {
		HtmlPage page = originalPage.cloneNode(true);
		List<HtmlElement> visibleElements = itemsSelector.getElements(page
				.getDocumentElement());
		List<String> results = new ArrayList<String>();
		for (HtmlElement element : visibleElements) {
			results.add(element.asText());
		}
		return results;
	}

	public HtmlPage getResultPage(Selector selector) {
		HtmlPage page = loadPage();
		filter(page, selector);
		return page;
	}
}
