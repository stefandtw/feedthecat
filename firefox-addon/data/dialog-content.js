"use strict";
function createDialogContent(dialogDocument, pageMantle) {

	var content = {
		selectors : {
			"titleSelector" : createSelector(),
			"linkSelector" : createSelector(),
			"contentSelector" : createSelector()
		}
	};

	// switching between panels
	$(".ftc_switch", dialogDocument).click(function() {
		$(".ftc_active_panel", dialogDocument).removeClass("ftc_active_panel");
		$(".ftc_panel[name=" + $(this).data('panel') + "]", dialogDocument).addClass("ftc_active_panel");
		if ($(this).data('selector')) {
			$(".ftc_panel[name=selector_legend]", dialogDocument).addClass("ftc_active_panel");
		}
		pageMantle.setCurrentSelector(content.selectors[$(this).data('selector')]);
	});

	// monitor xpath text field changes
	$(".ftc_selector", dialogDocument).change(function() {
		var selector = content.selectors[$(this).data('selector')];
		selector.setXpath($(this).val());
	});

	// update xpath text field
	$(".ftc_selector", dialogDocument).each(function() {
		var selector = content.selectors[$(this).data('selector')];
		var textField = $(this);
		selector.addXpathObserver(function(newValue) {
			textField.val(newValue);
		});
	});

	// fill in webpage URL
	$(".ftc_data[name=webpageUrl]", dialogDocument).val(pageMantle.pageDocument.URL);

	// test if DOM nodes can be accessed (code may be deleted later)
	$(".ftc_data[name=description]", dialogDocument).val(pageMantle.pageDocument.body.outerHTML);

	return content;
}
