package org.quickflower.filter;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class PageFilter {

	private final PageConfig pageConfig;

	public PageFilter(PageConfig pageConfig) {
		this.pageConfig = pageConfig;
	}

	public String getResultHtml() {
		HtmlPage page = loadPage();
		filter(page);
		return page.getBody().asXml();
	}

	private HtmlPage loadPage() {
		WebClient webClient = new WebClient();
		HtmlPage page = null;
		try {
			page = webClient.getPage(pageConfig.getUrl());
		} catch (FailingHttpStatusCodeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return page;
	}

	private void filter(HtmlPage page) {
		List<HtmlElement> elements = getVisibleElements(page);
		HtmlElement root = page.getBody();
		root.removeAllChildren();
		for (HtmlElement element : elements) {
			root.appendChild(element);
		}
	}

	private List<HtmlElement> getVisibleElements(HtmlPage page) {
		List<HtmlElement> visibleElements = new ArrayList<HtmlElement>();
		for (String xpath : pageConfig.getVisibleElementXPaths()) {
			for (HtmlElement next : (List<HtmlElement>) page.getByXPath(xpath)) {
				HtmlElement clone = (HtmlElement) next.cloneNode(true);
				visibleElements.add(clone);
			}
		}
		return visibleElements;
	}

}
