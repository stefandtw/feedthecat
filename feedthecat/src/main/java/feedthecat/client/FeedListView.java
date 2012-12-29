package feedthecat.client;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import feedthecat.client.service.FeedsService;
import feedthecat.client.service.FeedsServiceAsync;
import feedthecat.shared.FeedConfig;

public class FeedListView extends SimplePanel {

	private final FeedsServiceAsync feedsService = GWT
			.create(FeedsService.class);
	private final Messages messages = GWT.create(Messages.class);

	public FeedListView() {
		final VerticalPanel feedListPanel = new VerticalPanel();
		feedsService.getFeedConfigs(new AsyncCallback<List<FeedConfig>>() {

			@Override
			public void onSuccess(List<FeedConfig> feedConfigs) {
				for (final FeedConfig feedConfig : feedConfigs) {
					final HorizontalPanel feedItem = new HorizontalPanel();
					feedListPanel.add(feedItem);
					Anchor feedLink = new FeedLink(feedConfig.getName(),
							feedConfig.getName());

					Anchor deleteLink = new Anchor(messages.link_DeleteFeed());
					deleteLink.addClickHandler(new ClickHandler() {

						@Override
						public void onClick(ClickEvent event) {
							new ConfirmationDialog(messages
									.confirm_DeleteFeed()) {
								@Override
								public void onOk() {
									feedsService.deleteFeed(feedConfig,
											new AsyncCallback<Void>() {

												@Override
												public void onSuccess(Void arg0) {
													feedListPanel
															.remove(feedItem);
												}

												@Override
												public void onFailure(
														Throwable arg0) {
												}
											});
								}
							};
						}
					});
					feedItem.add(feedLink);
					feedItem.add(new HTML(" ["));
					feedItem.add(deleteLink);
					feedItem.add(new HTML("]"));
				}
			}

			@Override
			public void onFailure(Throwable arg0) {
			}
		});
		add(feedListPanel);
	}
}
