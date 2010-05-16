Feature: Show a possibly filtered web page
	
	As a web user I want to see only relevant things on a web page


Scenario: See google weather applet

Given homepage
When I set source url to "http://www.google.com/search?q=weather+berlin"
And I set name to "berlin weather"
And I click "Submit"
Then result page for "berlin weather" contains "°C"

