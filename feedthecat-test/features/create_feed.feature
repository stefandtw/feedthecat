Feature: Create a feed
	
	As a web user I want a web feed compiled of interesting things on a web page.


Scenario: Show error messages

Given page to create a new feed
When I click the 'Save' button
Then show error message ''Field 'Name' is required.''
And show error message ''Field 'URL' is required.''
And show error message ''Field 'Item Title XPath' is required.''


Scenario: Wikipedia news

Given page to create a new feed
When I set source url to local file 'Wikinews.html'
And I set name to 'wikipedia news'
And I set titleXPath to ''/html/body/div[@id='content']/div[@id='bodyContent']/table/tbody/tr[2]/td[@id='MainPage_latest_news']/div[@id='MainPage_latest_news_text']/ul/li/a''
And I set description to 'Wiki News.'
And I click the 'Save' button
Then feed item title 0 for 'wikipedia news' is ''Noynoy Aquino elected Philippine president''
And feed item title 1 for 'wikipedia news' is '''Dewey Defeats Truman' incident in California State Senate election''
And feed item title 2 for 'wikipedia news' is ''Football: Chelsea confirm Joe Cole and Michael Ballack departure''
And description for 'wikipedia news' is 'Wiki News.'

