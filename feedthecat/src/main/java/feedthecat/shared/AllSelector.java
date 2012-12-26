/**
 * 
 */
package feedthecat.shared;

import java.util.Arrays;
import java.util.List;

import com.gargoylesoftware.htmlunit.html.HtmlElement;

public final class AllSelector implements Selector {
	@Override
	public List<HtmlElement> getElements(HtmlElement baseElement) {
		return Arrays.asList(baseElement);
	}
}