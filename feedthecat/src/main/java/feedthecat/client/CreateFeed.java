package feedthecat.client;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import com.google.inject.Inject;

import feedthecat.datasource.DataSource;
import feedthecat.shared.FeedConfig;

public class CreateFeed extends WebPage {

	@Inject
	private DataSource dataSource;
	private final FeedConfig feedConfig = new FeedConfig();

	public enum FeedConfigItem {
		NAME("name"), WEB_PAGE("sourceUrl"), DESCRIPTION("description"), TITLE_SELECTOR(
				"titleSelector"), LINKS_SELECTOR("linkSelector"), CONTENT_SELECTOR(
				"contentSelector");

		private String wicketId;
		private Model<String> model;

		private FeedConfigItem(String wicketId) {
			this.wicketId = wicketId;
			model = new Model<String>();
		}

		public String getWicketId() {
			return wicketId;
		}

		public IModel<String> getModel() {
			return model;
		}

		public String getModelString() {
			return getModel().getObject();
		}
	}

	public CreateFeed() {
		final Form<?> form = new Form<Object>("createFeedForm");
		add(form);
		// add(new NavigationPanel("navigationPanel"));

		form.add(new FeedbackPanel("feedback"));

		final TextField<String> inputField = new TextField<String>("inputField") {
			// onchange ...
		};
		inputField.setVisible(false);
		// inputField.validate();
		form.add(inputField);

		for (final FeedConfigItem configItem : FeedConfigItem.values()) {

			Link activateButton = new Link(configItem.getWicketId()) {
				@Override
				public void onClick() {
					// inputField.setRequired(true);
					inputField.setVisible(true);
					inputField.setModel(configItem.getModel());
				}
			};
			form.add(activateButton);
		}

		Button submitButton = new Button("submit") {

			@Override
			public void onSubmit() {

				feedConfig.setName(FeedConfigItem.NAME.getModelString());
				feedConfig.setUrl(FeedConfigItem.WEB_PAGE.getModelString());
				feedConfig.setDescription(FeedConfigItem.DESCRIPTION
						.getModelString());
				// //important for empty selectors:
				// if (xpath == null) {
				// xpath = "//body/*";
				// }
				// selector = new XPathSelector(xpath);
				// /////

				// feedConfig.setTitleSelector(titleSelectorPanel.getSelector());
				// feedConfig.setLinkSelector(linkSelectorPanel.getSelector());
				// feedConfig.setContentSelector(contentSelectorPanel
				// .getSelector());
				// WebClientInfo webClientInfo = (WebClientInfo) this
				// .getRequestCycle().getClientInfo();
				// feedConfig
				// .setUserAgentForScraping(webClientInfo.getUserAgent());
				dataSource.saveFeedConfig(feedConfig);

				// getRequestCycle().setRequestTarget(
				// new RedirectRequestTarget(
				// (String) urlFor(new ResourceReference(
				// GeneratedFeedResource.REFERENCE_NAME))
				// + "?name=" + feedConfig.getName()));
			}
		};
		form.add(submitButton);
	}
}
