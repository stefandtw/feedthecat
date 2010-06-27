package feedthecat.page;

import org.apache.wicket.ResourceReference;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.target.basic.RedirectRequestTarget;

import com.google.inject.Inject;

import feedthecat.datasource.DataSource;
import feedthecat.generatedresource.GeneratedPageResource;
import feedthecat.page.tools.Validators;
import feedthecat.webpagefilter.PageConfig;

public class CreatePage extends WebPage {

	@Inject
	private DataSource dataSource;
	private final PageConfig pageConfig = new PageConfig();

	public CreatePage() {
		Form<?> form = new Form<Object>("createPageForm");
		add(form);
		add(new NavigationPanel("navigationPanel"));

		form.add(new FeedbackPanel("feedback"));

		final IModel<String> nameModel = new Model<String>();
		TextField<String> nameField = new TextField<String>("name", nameModel);
		nameField.setRequired(true);
		form.add(nameField);

		final IModel<String> urlModel = new Model<String>();
		TextField<String> urlField = new TextField<String>("sourceUrl",
				urlModel);
		urlField.setRequired(true);
		urlField.add(Validators.URL_VALIDATOR);
		form.add(urlField);

		final IModel<String> xpathModel = new Model<String>();
		TextField<String> xpathField = new TextField<String>("xpath",
				xpathModel);
		form.add(xpathField);

		Button submitButton = new Button("submit") {

			@Override
			public void onSubmit() {
				pageConfig.setName(nameModel.getObject());
				pageConfig.setUrl(urlModel.getObject());
				String xpath = xpathModel.getObject();
				pageConfig.showByXPath(xpath != null ? xpath : "//body/*");
				dataSource.savePageConfig(pageConfig);

				getRequestCycle().setRequestTarget(
						new RedirectRequestTarget(
								(String) urlFor(new ResourceReference(
										GeneratedPageResource.REFERENCE_NAME))
										+ "?name=" + pageConfig.getName()));
			}
		};
		form.add(submitButton);
	}
}
