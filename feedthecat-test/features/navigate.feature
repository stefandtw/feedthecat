Feature: Navigate the web site
	
	As a visitor I want to reach all important pages by clicking on a link on the home page.


Scenario: Go to the 'Create a feed' page

Given home page
When I click on the 'Create a feed' link
Then show the 'Create a feed' page


Scenario: Go to the 'Filter a web page' page

Given home page
When I click on the 'Filter a web page' link
Then show the 'Filter a web page' page


Scenario: Go to the 'Feeds' page

Given home page
When I click on the 'Feeds' link
Then show the 'Feeds' page


Scenario: Go to the 'Web pages' page

Given home page
When I click on the 'Web pages' link
Then show the 'Web pages' page
