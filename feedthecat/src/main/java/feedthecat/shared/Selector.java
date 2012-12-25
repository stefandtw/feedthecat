package feedthecat.shared;

import java.io.Serializable;
import java.util.List;

import com.gargoylesoftware.htmlunit.html.HtmlElement;

public interface Selector extends Serializable {

	List<HtmlElement> getElements(HtmlElement baseElement);

}
