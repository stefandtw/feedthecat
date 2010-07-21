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
import feedthecat.generatedresource.GeneratedFeedResource;
import feedthecat.page.tools.Validators;
import feedthecat.webpagefilter.FeedConfig;

public class CreateFeed extends WebPage {

	@Inject
	private DataSource dataSource;
	private final FeedConfig feedConfig = new FeedConfig();

	public CreateFeed() {
		Form<?> form = new Form<Object>("createFeedForm");
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

		final IModel<String> descriptionModel = new Model<String>();
		TextField<String> descriptionField = new TextField<String>(
				"description", descriptionModel);
		form.add(descriptionField);

		final SelectorPanel titleSelectorPanel = new SelectorPanel(
				"titleSelector");
		titleSelectorPanel.setRequired(true);
		form.add(titleSelectorPanel);

		final SelectorPanel linkSelectorPanel = new SelectorPanel(
				"linkSelector");
		form.add(linkSelectorPanel);

		final SelectorPanel contentSelectorPanel = new SelectorPanel(
				"contentSelector");
		form.add(contentSelectorPanel);

		Button submitButton = new Button("submit") {

			@Override
			public void onSubmit() {

				feedConfig.setName(nameModel.getObject());
				feedConfig.setUrl(urlModel.getObject());
				feedConfig.setDescription(descriptionModel.getObject());
				feedConfig.setTitleSelector(titleSelectorPanel.getSelector());
				feedConfig.setLinkSelector(linkSelectorPanel.getSelector());
				feedConfig.setContentSelector(contentSelectorPanel
						.getSelector());
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
