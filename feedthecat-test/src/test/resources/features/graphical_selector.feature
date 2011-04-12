!--@dev
!--Feature: Select elements on a page graphically
	
!--	As a web user I want to select interesting parts of a web page so I can use them for feeds.
!--	I want to do this graphically instead of typing an XPath expression.


Scenario: Graphical selector shows preview of the entered URL

Given page to create a new feed
When I set source url to local file 'Wikinews.html'
And I choose graphical selector for 'titleSelector'
Then show preview of local file 'Wikinews.html'


Scenario: Graphical selector for Wikipedia news

Given preview page of local file 'Wikinews.html'
When I click on the link ''Noynoy Aquino elected Philippine president''
And I click on the ''Ok'' button
Then selection 0 as text is ''Noynoy Aquino elected Philippine president''
And selection 1 as text is '''Dewey Defeats Truman' incident in California State Senate election''
And selection 2 as text is ''Football: Chelsea confirm Joe Cole and Michael Ballack departure''
