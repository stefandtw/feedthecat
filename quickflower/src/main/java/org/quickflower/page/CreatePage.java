package org.quickflower.page;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;

public class CreatePage extends WebPage {

	public CreatePage() {
		Form<?> form = new Form<Object>("createPageForm");
		add(form);

		Button submitButton = new Button("submit") {
			@Override
			public void onSubmit() {
				// TODO validate; go to result page
				super.onSubmit();
			}
		};
		form.add(submitButton);
	}

}
