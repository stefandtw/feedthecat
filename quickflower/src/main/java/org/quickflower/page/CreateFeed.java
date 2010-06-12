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
import org.quickflower.datasource.DataSource;
import org.quickflower.generatedresource.GeneratedFeedResource;
import org.quickflower.page.tools.Validators;
import org.quickflower.webpagefilter.FeedConfig;

import com.google.inject.Inject;

public class CreateFeed extends WebPage {

	@Inject
	private DataSource dataSource;
	private final FeedConfig feedConfig = new FeedConfig();

	public CreateFeed() {
		Form<?> form = new Form<Object>("createFeedForm");
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
		urlField.add(Validators.URL_VALIDATOR);
		form.add(urlField);

		final IModel<String> titleXPathModel = new Model<String>();
		TextField<String> titleXPathField = new TextField<String>("titleXPath",
				titleXPathModel);
		titleXPathField.setRequired(true);
		form.add(titleXPathField);

		final IModel<String> contentXPathModel = new Model<String>();
		TextField<String> contentXPathField = new TextField<String>(
				"contentXPath", contentXPathModel);
		form.add(contentXPathField);

		Button submitButton = new Button("submit") {

			@Override
			public void onSubmit() {

				feedConfig.setName(nameModel.getObject());
				feedConfig.setUrl(urlModel.getObject());
				feedConfig.setTitleXPath(titleXPathModel.getObject());
				feedConfig.setContentXPath(contentXPathModel.getObject());
				dataSource.saveFeedConfig(feedConfig);

				getRequestCycle().setRequestTarget(
						new RedirectRequestTarget(
								(String) urlFor(new ResourceReference(
										GeneratedFeedResource.REFERENCE_NAME))
										+ "?name=" + feedConfig.getName()));
			}
		};
		form.add(submitButton);
	}

}
