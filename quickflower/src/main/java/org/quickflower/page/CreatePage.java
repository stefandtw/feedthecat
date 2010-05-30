package org.quickflower.page;

import org.apache.wicket.ResourceReference;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.target.basic.RedirectRequestTarget;
import org.apache.wicket.validation.validator.UrlValidator;
import org.quickflower.datasource.DataSource;
import org.quickflower.generatedresource.GeneratedPageResource;
import org.quickflower.webpagefilter.PageConfig;

import com.google.inject.Inject;

public class CreatePage extends WebPage {

	@Inject
	private DataSource dataSource;
	private final PageConfig pageConfig = new PageConfig();

	public CreatePage() {
		Form<?> form = new Form<Object>("createPageForm");
		add(form);

		form.add(new FeedbackPanel("feedback"));

		final IModel<String> nameModel = new Model<String>();
		TextField<String> nameField = new TextField<String>("name", nameModel);
		nameField.setRequired(true);
		form.add(nameField);

		final IModel<String> urlModel = new Model<String>();
		TextField<String> urlField = new TextField<String>("sourceUrl",
				urlModel);
		urlField.setRequired(true);
		urlField.add(new UrlValidator());
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
				pageConfig.showByXPath(xpathModel.getObject());
				dataSource.save(pageConfig);

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
