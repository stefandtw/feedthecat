Feature: Show a possibly filtered web page
	
	As a web user I want to see only relevant things on a web page


Scenario: See google weather applet

Given homepage
When I set source url to 'http://www.google.com/search?q=weather+berlin'
And I set name to 'berlin weather'
And I set xpath to ''/html/body[@id='gsr']/div[@id='cnt']/div[2]/div[@id='center_col']/div[@id='res']/div[1]/table/tbody/tr[2]/td/div[2]/nobr''
And I click the 'Save' button
Then result page for 'berlin weather' contains '°'
And result page for 'berlin weather' does not contain 'Google'

