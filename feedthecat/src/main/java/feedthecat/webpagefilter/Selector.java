package feedthecat.webpagefilter;

import java.util.List;

import com.gargoylesoftware.htmlunit.html.HtmlElement;

public interface Selector {

	List<HtmlElement> getElements(HtmlElement baseElement);

}
