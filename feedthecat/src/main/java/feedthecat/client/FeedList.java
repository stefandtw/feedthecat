package feedthecat.client;

import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import feedthecat.application.ServletConfig;
import feedthecat.client.service.FeedsService;
import feedthecat.client.service.FeedsServiceAsync;
import feedthecat.server.FeedBuilderServlet;
import feedthecat.shared.FeedConfig;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class FeedList implements EntryPoint {
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";

	/**
	 * Create a remote service proxy to talk to the server-side Greeting
	 * service.
	 */
	private final FeedsServiceAsync feedsService = GWT
			.create(FeedsService.class);

	private final Messages messages = GWT.create(Messages.class);

	@Override
	public void onModuleLoad() {
		// final Button sendButton = new Button(messages.createButton());
		// final TextBox nameField = new TextBox();
		// nameField.setText("foo bar");
		// final Label errorLabel = new Label();
		//
		// // We can add style names to widgets
		// sendButton.addStyleName("sendButton");

		final VerticalPanel feedListPanel = new VerticalPanel();
		feedsService.getFeedConfigs(new AsyncCallback<List<FeedConfig>>() {

			@Override
			public void onSuccess(List<FeedConfig> feedConfigs) {
				for (final FeedConfig feedConfig : feedConfigs) {
					final HorizontalPanel feedItem = new HorizontalPanel();
					feedListPanel.add(feedItem);
					String feedHref = "" + //
							ServletConfig.FEED_BUILDER_PATH //
							+ "?"//
							+ FeedBuilderServlet.PARAMETER_KEY_NAME//
							+ "="//
							+ feedConfig.getName();
					Anchor feedLink = new Anchor(feedConfig.getName(), feedHref);

					Anchor deleteLink = new Anchor(messages.deleteLink());
					deleteLink.addClickHandler(new ClickHandler() {

						@Override
						public void onClick(ClickEvent event) {
							feedsService.deleteFeed(feedConfig,
									new AsyncCallback<Void>() {

										@Override
										public void onSuccess(Void arg0) {
											feedListPanel.remove(feedItem);
										}

										@Override
										public void onFailure(Throwable arg0) {
										}
									});
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
		// Add the nameField and sendButton to the RootPanel
		// Use RootPanel.get() to get the entire body element
		RootPanel.get("feedList").add(feedListPanel);
		// TODO: RootPanel.get("navigationPanel").add(
		// new NavigationPanel("navigationPanel"));

		// Strin serverInfo = geSevletContext().getServerInfo();
		// String userAgent = getThreadLocalRequest().getHeader("User-Agent");

		// ////////////////
		// RootPanel.get("sendButtonContainer").add(sendButton);
		// RootPanel.get("errorLabelContainer").add(errorLabel);

		// // Focus the cursor on the name field when the app loads
		// nameField.setFocus(true);
		// nameField.selectAll();
		//
		// // Create the popup dialog box
		// final DialogBox dialogBox = new DialogBox();
		// dialogBox.setText("Remote Procedure Call");
		// dialogBox.setAnimationEnabled(true);
		// final Button closeButton = new Button("Close");
		// // We can set the id of a widget by accessing its Element
		// closeButton.getElement().setId("closeButton");
		// final Label textToServerLabel = new Label();
		// final HTML serverResponseLabel = new HTML();
		// VerticalPanel dialogVPanel = new VerticalPanel();
		// dialogVPanel.addStyleName("dialogVPanel");
		// dialogVPanel.add(new HTML("<b>Sending name to the server:</b>"));
		// dialogVPanel.add(textToServerLabel);
		// dialogVPanel.add(new HTML("<br><b>Server replies:</b>"));
		// dialogVPanel.add(serverResponseLabel);
		// dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
		// dialogVPanel.add(closeButton);
		// dialogBox.setWidget(dialogVPanel);
		//
		// // Add a handler to close the DialogBox
		// closeButton.addClickHandler(new ClickHandler() {
		// public void onClick(ClickEvent event) {
		// dialogBox.hide();
		// sendButton.setEnabled(true);
		// sendButton.setFocus(true);
		// }
		// });
		//
		// // Create a handler for the sendButton and nameField
		// class MyHandler implements ClickHandler, KeyUpHandler {
		// /**
		// * Fired when the user clicks on the sendButton.
		// */
		// public void onClick(ClickEvent event) {
		// sendNameToServer();
		// }
		//
		// /**
		// * Fired when the user types in the nameField.
		// */
		// public void onKeyUp(KeyUpEvent event) {
		// if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
		// sendNameToServer();
		// }
		// }
		//
		// /**
		// * Send the name from the nameField to the server and wait for a
		// * response.
		// */
		// private void sendNameToServer() {
		// // First, we validate the input.
		// errorLabel.setText("");
		// String textToServer = nameField.getText();
		// if (!FieldVerifier.isValidName(textToServer)) {
		// errorLabel.setText("Please enter at least four characters");
		// return;
		// }
		//
		// // Then, we send the input to the server.
		// sendButton.setEnabled(false);
		// textToServerLabel.setText(textToServer);
		// serverResponseLabel.setText("");
		// feedsService.greetServer(textToServer,
		// new AsyncCallback<String>() {
		// public void onFailure(Throwable caught) {
		// // Show the RPC error message to the user
		// dialogBox
		// .setText("Remote Procedure Call - Failure");
		// serverResponseLabel
		// .addStyleName("serverResponseLabelError");
		// serverResponseLabel.setHTML(SERVER_ERROR);
		// dialogBox.center();
		// closeButton.setFocus(true);
		// }
		//
		// public void onSuccess(String result) {
		// dialogBox.setText("Remote Procedure Call");
		// serverResponseLabel
		// .removeStyleName("serverResponseLabelError");
		// serverResponseLabel.setHTML(result);
		// dialogBox.center();
		// closeButton.setFocus(true);
		// }
		// });
		// }
		// }
		//
		// // Add a handler to send the name to the server
		// MyHandler handler = new MyHandler();
		// sendButton.addClickHandler(handler);
		// nameField.addKeyUpHandler(handler);
	}
}
