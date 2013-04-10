var selector;

module("selector.recreateXpathExpression()", {
	setup : function() {
		selector = createSelector();
	},
	teardown : function() {
		// print the XPath expression to give more information
		ok( typeof selector.getXpath() !== "undefined", "XPath: " + selector.getXpath());
		// assert that the selector really includes or excludes specified parts
		var coveredNodes = selector.select(document);
		selector.includedNodes.forEach(function(node) {
			ok(coveredNodes.contains(node));
		});
		selector.excludedNodes.forEach(function(node) {
			ok(!coveredNodes.contains(node));
		});
	}
});

/**
 * Adds parts of the page to the list of included elements.
 * For testing, a jQuery selector is used to specifiy those elements.
 */
function include(jQuerySelector) {
	jQuery(jQuerySelector).get().forEach(function(node) {
		selector.includedNodes.push(node);
	});
}

test("No specifics => return empty XPath", function() {

	var xpath = selector.recreateXpathExpression();

	equal(xpath, "", "XPath should be empty");
});

test("Same class => use @class", function() {
	include(".news_article");

	var xpath = selector.recreateXpathExpression();

	equal(xpath, "//*[@class='news_article']");
});
