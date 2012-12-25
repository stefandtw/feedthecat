package feedthecat.shared;

import java.util.Collections;
import java.util.List;

import com.gargoylesoftware.htmlunit.html.HtmlElement;


public class EmptySelector implements Selector {

	@Override
	public List<HtmlElement> getElements(HtmlElement baseElement) {
		return Collections.emptyList();
	}

}
