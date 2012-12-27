package feedthecat.server.webpagefilter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.gargoylesoftware.htmlunit.html.HtmlElement;


public class XPathSelector implements Selector {

	private final String itemsXPath;

	public XPathSelector(String itemsXPath) {
		this.itemsXPath = itemsXPath;
	}

	@Override
	public List<HtmlElement> getElements(HtmlElement baseElement) {
		return getVisibleElements(baseElement, Arrays.asList(itemsXPath));
	}

	private List<HtmlElement> getVisibleElements(HtmlElement element,
			List<String> visibleXPaths) {
		List<HtmlElement> visibleElements = new ArrayList<HtmlElement>();
		for (String xpath : visibleXPaths) {
			for (HtmlElement next : (List<HtmlElement>) element
					.getByXPath(xpath)) {
				visibleElements.add(next);
			}
		}
		return visibleElements;
	}

}
