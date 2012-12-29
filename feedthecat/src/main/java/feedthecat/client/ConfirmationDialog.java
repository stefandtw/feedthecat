package feedthecat.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;

public abstract class ConfirmationDialog extends DialogBox {

	public ConfirmationDialog(String text) {
		setText(text);
		Panel buttonPanel = new HorizontalPanel();
		add(buttonPanel);
		buttonPanel.add(okButton());
		buttonPanel.add(cancelButton());
		show();
		center();
	}

	private Button okButton() {
		Button okButton = new Button("OK");
		okButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				hide();
				onOk();
			}
		});
		return okButton;
	}

	private Button cancelButton() {
		Button cancelButton = new Button("Cancel");
		cancelButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				hide();
			}
		});
		return cancelButton;
	}

	public abstract void onOk();

}
