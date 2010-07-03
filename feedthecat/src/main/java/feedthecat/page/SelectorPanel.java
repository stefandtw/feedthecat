package feedthecat.page;

import org.apache.wicket.markup.html.form.FormComponentPanel;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import feedthecat.webpagefilter.AllSelector;
import feedthecat.webpagefilter.Selector;
import feedthecat.webpagefilter.XPathSelector;

public class SelectorPanel extends FormComponentPanel<Selector> {

	private final IModel<String> xpathModel;
	private Selector selector = new AllSelector();
	private final TextField<String> xpathField;

	public SelectorPanel(String id) {
		super(id, new Model<Selector>());

		xpathModel = new Model<String>();
		xpathField = new TextField<String>("xpath", xpathModel);
		add(xpathField);
	}

	@Override
	protected void convertInput() {
		super.convertInput();
		String xpath = xpathField.getConvertedInput();
		if (xpath == null) {
			xpath = "//body/*";
		}
		selector = new XPathSelector(xpath);
		setConvertedInput(selector);
	}

	@Override
	public boolean checkRequired() {
		if (isRequired()) {
			xpathField.setRequired(true);
			return xpathField.checkRequired();
		}
		return super.checkRequired();
	}

	public Selector getSelector() {
		return selector;
	}
}
