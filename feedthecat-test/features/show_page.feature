@dev
Feature: Show a filtered web page
	
	As a web user I want to see only relevant things on a web page


Scenario: See google weather applet

Given home page
When I set source url to 'http://www.google.com/search?q=weather+berlin'
And I set name to 'berlin weather'
And I set xpath to ''/html/body[@id='gsr']//div[@id='res']/div[1]/table/tbody/tr[2]/td/div[2]/nobr''
And I click the 'Save' button
Then result page for 'berlin weather' contains '°'
And result page for 'berlin weather' does not contain '<input'


Scenario: Show error messages

Given home page
When I click the 'Save' button
Then show error message ''Field 'Name' is required.''
And show error message ''Field 'URL' is required.''
