package feedthecat.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;

import feedthecat.client.service.FeedCreationService;
import feedthecat.shared.FeedConfig;

public class CreateFeed {
	private final FeedCreationService feedCreationService = GWT
			.create(FeedCreationService.class);

	private final FeedConfig feedConfig = new FeedConfig();

	public enum FeedConfigItem {
		NAME, WEB_PAGE, DESCRIPTION, TITLE_SELECTOR, LINKS_SELECTOR, CONTENT_SELECTOR;

		public String getModelString() {
			// return getModel().getObject();
			return "TODO";
		}
	}

	public CreateFeed() {
		// final Form<?> form = new Form<Object>("createFeedForm");
		// add(form);
		// add(new NavigationPanel("navigationPanel"));

		// final TextField<String> inputField = new
		// TextField<String>("inputField") {
		// onchange ...
		// };
		// inputField.setVisible(false);
		// inputField.validate();
		// form.add(inputField);

		for (final FeedConfigItem configItem : FeedConfigItem.values()) {

			Anchor activateButton = new Anchor() {
				// @Override
				// public void onClick() {
				// // inputField.setRequired(true);
				// inputField.setVisible(true);
				// inputField.setModel(configItem.getModel());
				// }
			};
			// form.add(activateButton);
		}

		Button submitButton = new Button("submit") {

			// @Override
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
				feedCreationService.createFeed(feedConfig);

				// getRequestCycle().setRequestTarget(
				// new RedirectRequestTarget(
				// (String) urlFor(new ResourceReference(
				// GeneratedFeedResource.REFERENCE_NAME))
				// + "?name=" + feedConfig.getName()));
			}
		};
		// form.add(submitButton);
	}
}
