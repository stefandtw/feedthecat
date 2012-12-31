package feedthecat.client;

import java.util.Arrays;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window.Navigator;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import feedthecat.client.service.NewFeedService;
import feedthecat.client.service.NewFeedServiceAsync;
import feedthecat.shared.FeedConfig;

public class NewFeedView extends VerticalPanel {

	private final NewFeedServiceAsync newFeedService = GWT
			.create(NewFeedService.class);
	private final Messages messages = GWT.create(Messages.class);

	private final FeedConfig feedConfig = new FeedConfig();
	private FeedConfigItem currentConfigItem;

	public enum FeedConfigItem {
		NAME, WEB_PAGE, DESCRIPTION, TITLE_SELECTOR, LINKS_SELECTOR, CONTENT_SELECTOR;

		private final Messages messages = GWT.create(Messages.class);

		public String getButtonText() {
			return messages.getString("button_FeedConfigItem_" + name());
		}

		public String getLabel() {
			return messages.getString("label_FeedConfigItem_" + name());
		}

		public static List<FeedConfigItem> getGlobalItems() {
			return Arrays.asList(NAME, WEB_PAGE, DESCRIPTION);
		}

		public static List<FeedConfigItem> getEntrySpecificItems() {
			return Arrays.asList(TITLE_SELECTOR, LINKS_SELECTOR,
					CONTENT_SELECTOR);
		}

		public String getFeedConfigValue(FeedConfig feedConfig) {
			switch (this) {
			case CONTENT_SELECTOR:
				return feedConfig.getContentSelectorString();
			case DESCRIPTION:
				return feedConfig.getDescription();
			case LINKS_SELECTOR:
				return feedConfig.getLinkSelectorString();
			case NAME:
				return feedConfig.getName();
			case TITLE_SELECTOR:
				return feedConfig.getTitleSelectorString();
			case WEB_PAGE:
				return feedConfig.getUrl();
			default:
				throw new UnsupportedOperationException(this
						+ " not implemented");
			}
		}

		public void setFeedConfigValue(FeedConfig feedConfig, String value) {
			switch (this) {
			case CONTENT_SELECTOR:
				feedConfig.setContentSelectorString(value);
				return;
			case DESCRIPTION:
				feedConfig.setDescription(value);
				return;
			case LINKS_SELECTOR:
				feedConfig.setLinkSelectorString(value);
				return;
			case NAME:
				feedConfig.setName(value);
				return;
			case TITLE_SELECTOR:
				feedConfig.setTitleSelectorString(value);
				return;
			case WEB_PAGE:
				feedConfig.setUrl(value);
				return;
			default:
				throw new UnsupportedOperationException(this
						+ " not implemented");
			}
		}
	}

	public NewFeedView(String webPageUrl) {
		feedConfig.setUrl(webPageUrl);

		final VerticalPanel errorPanel = new VerticalPanel();
		add(errorPanel);

		final Label label = new Label();
		final TextBox inputField = new TextBox();
		inputField.setVisible(false);
		inputField.addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {
				currentConfigItem.setFeedConfigValue(feedConfig,
						inputField.getValue());
			}
		});

		Panel buttonPanel = new HorizontalPanel();
		buttonPanel.add(new Label(messages.label_GlobalFeedConfigItems()));
		List<FeedConfigItem> globalConfigItems = FeedConfigItem
				.getGlobalItems();
		addFeedConfigItems(globalConfigItems, buttonPanel, label, inputField);
		buttonPanel
				.add(new Label(messages.label_EntrySpecificFeedConfigItems()));
		List<FeedConfigItem> entrySpecificConfigItems = FeedConfigItem
				.getEntrySpecificItems();
		addFeedConfigItems(entrySpecificConfigItems, buttonPanel, label,
				inputField);
		add(buttonPanel);

		Panel inputPanel = new HorizontalPanel();
		inputPanel.add(label);
		inputPanel.add(inputField);
		add(inputPanel);

		Button submitButton = new Button(messages.button_CreateFeed());
		submitButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (feedConfig.getContentSelectorString().isEmpty()) {
					feedConfig.setContentSelectorString("//body/*");
				}
				String userAgent = Navigator.getUserAgent();
				feedConfig.setUserAgentForScraping(userAgent);

				errorPanel.clear();
				newFeedService.createNewFeed(feedConfig,
						new AsyncCallback<Void>() {

							@Override
							public void onSuccess(Void result) {
								add(new FeedLink(messages
										.navigation_NewFeedLink(), feedConfig
										.getName()));
							}

							@Override
							public void onFailure(Throwable caught) {
								errorPanel.add(new HTML(messages
										.error_CreateNewFeedFailed()));
							}
						});
			}
		});
		add(submitButton);
	}

	private void addFeedConfigItems(List<FeedConfigItem> configItems,
			Panel buttonPanel, final Label inputPanelLabel,
			final TextBox inputPanelField) {
		for (final FeedConfigItem configItem : configItems) {

			Button activateButton = new Button(configItem.getButtonText());
			activateButton.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					inputPanelLabel.setText(configItem.getLabel());
					inputPanelField.setVisible(true);
					currentConfigItem = configItem;
					inputPanelField.setValue(configItem
							.getFeedConfigValue(feedConfig));
				}
			});
			buttonPanel.add(activateButton);
		}
	}
}
