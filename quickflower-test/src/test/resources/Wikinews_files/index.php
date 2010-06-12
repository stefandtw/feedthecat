/* generated javascript */
var skin = 'vector';
var stylepath = 'http://bits.wikimedia.org/skins-1.5';

/* MediaWiki:Common.js */
 /* Any JavaScript here will be loaded for all users on every page load. Some other stuff is located at [[mediawiki:monobook.js]] */
 /* If you don't know what you are doing, please test/ask for advice before hitting save */
/*Please document this page at [[Wikinews:Javascript]]*/
//The jslint stuff just covers the editintro for wikipedians for now.

/*global wgPageName, addOnloadHook, wgNamespaceNumber, getElementsByClassName */
/*jslint white: true, browser: true, undef: true, nomen: true, eqeqeq: true, bitwise: true, regexp: true, newcap: true, immed: true, indent: 1 */
/*members April, August, December, February, January, July, June, March, 
    May, November, October, September, UTC, dateFunc, dateRegex, 
    getElementById, getElementsByTagName, getTime, href, indexOf, length, 
    oldNews, parseDate, referrer, replace, title
*/

/*Avoid importScript() as it seems to slow down page-load time*/

 
// Adding functions to import external scripts
 importedScripts = {}; // object keeping track of included scripts, so a script ain't included twice
 function importScript( page ) {
   page = encodeURIComponent(page.replace(/ /g, '_' ));
   
   if (importedScripts[page]) return; else importedScripts[page] = true;
   
   var scriptElem = document.createElement( 'script' );
   scriptElem.setAttribute( 'type' , 'text/javascript' );
   //increment the scrver to force hard refresh
   scriptElem.setAttribute( 'src' , wgScript + '?title=' + page + '&action=raw&ctype=text/javascript' + '&scrver=7');
   document.getElementsByTagName( 'head' )[0].appendChild( scriptElem );
 }
 
 function importStylesheet( page ) {
   page = encodeURIComponent(page.replace(/ /g, '_' ));
   
   if (importedScripts[page]) return; else importedScripts[page] = true;
   
   if (document.createStyleSheet) {
      document.createStyleSheet(wgScript + '?title=' + page + "&action=raw&ctype=text/css");
   } else {
      var styleElem = document.createElement('style');
      styleElem.setAttribute('type', 'text/css');
      styleElem.appendChild(document.createTextNode('@import "' + wgScript + '?title=' + page + '&action=raw&ctype=text/css";'));
      document.getElementsByTagName('head')[0].appendChild(styleElem);
   }
 }


 // for backwards compatibility
 var addLoadEvent = addOnloadHook;
 
 // Provides an easy way to disable load dependent features
 function delLoadEvent(func) {
   for (var i = 0; i < onloadFuncts.length; i++) {
     if (onloadFuncts[i] == func)
       onloadFuncts.splice(i, 1);
   }
 }
 
 // hasClass()
 // Description: Uses regular expressions and caching for better performance.
 // Maintainers: w:User:Mike Dillon, w:User:R. Koot, w:User:SG
 
 var hasClass = (function () {
    var reCache = {};
    return function (element, className) {
      return (reCache[className] ? reCache[className] : (reCache[className] = new RegExp("(?:\\s|^)" + className + "(?:\\s|$)"))).test(element.className);
    };
 })();

 // name of project
 var wgProject = wgServer.replace("http://", "");
 
 /*
==RSS Feed insert. Only latest news and category. Should we add others? ==
 */
 
/* Handled in php - [[bugzilla:15080]]. document.write('<link type="application/rss+xml" href="http://feeds.feedburner.com/WikinewsLatestNews" rel="alternate" title="Lastest News (RSS)" \/>'); */

//Zach's Awesome category thingy takes a cat (?cat=foo) and intersects it with published to make an rss feed. Also can take ?lang=bar for other lang
 if (wgNamespaceNumber === 14 ) {
  document.write('<link type="application/rss+xml" href="http://toolserver.org/~zach/cgi-bin/rss.cgi?cat=' + encodeURIComponent(wgTitle.replace(' ', "_")) + '" rel="alternate" title="Lastest ' + wgTitle.replace('"', "").replace('&', "&amp;") + ' News (RSS)" \/>');
}
addOnloadHook(function () {
if (wgNamespaceNumber === 100 && document.getElementById("rssFeedLink")) { //portal with cat of same name
 var link = document.createElement('link');
 link.type = 'application/rss+xml';
 link.href = 'http://toolserver.org/~zach/cgi-bin/rss.cgi?cat=' + encodeURIComponent(wgTitle.replace(' ', "_"));
 link.rel = "alternate";
 link.title =  'Lastest ' + wgTitle + ' News (RSS)'
 document.getElementsByTagName('head')[0].appendChild(link);
}
});
 /*
==Dynamic Navigation==
 */
 // Makes some dynamic nav boxes auto-close. See [[user:Bawolff/onebox-select.js]] & [[Template:Dynamic navigation noncentered]]
 // *Tested in MSIE 6, Opera 9.01, and firefox (1.5.0.11 and 2.0.0.something(I think its a 2 at the end)
 // *Adapted from the dynamic nav box script which is from somewhere on wikipedia
 
 // set up the words in your language
 var NavigationBarHide = '[ ↑ ]';
 var NavigationBarShow = '[ ↓ ]';
 var ONCE_NavigationBarHide = '[ ↑ ]';
 var ONCE_NavigationBarShow = '[ ↓ ]';
 
 // set up max count of Navigation Bars on page, if there are more, all will be hidden
 // NavigationBarShowDefault = 0; // all bars will be hidden
 // NavigationBarShowDefault = 1; // on pages with more than 1 bar all bars will be hidden
 var NavigationBarShowDefault = 1;
 var ONCE_NavigationBarShowDefault = 0;
 
 // shows and hides content and picture (if available) of navigation bars
 // Parameters:
 //     indexNavigationBar: the index of navigation bar to be toggled
 function ONCE_toggleNavigationBar(ONCE_indexNavigationBar)
 {
   var ONCE_NavToggle = document.getElementById("NavToggleOnce" + ONCE_indexNavigationBar);
   var ONCE_NavFrame = document.getElementById("NavFrameOnce" + ONCE_indexNavigationBar);
   
   if (!ONCE_NavFrame || !ONCE_NavToggle) {
     return false;
   }
   
   // if shown now
   if (ONCE_NavToggle.firstChild.data == ONCE_NavigationBarHide) {
     for (var ONCE_NavChild = ONCE_NavFrame.firstChild; ONCE_NavChild != null; ONCE_NavChild = ONCE_NavChild.nextSibling) {
       if (hasClass(ONCE_NavChild, 'NavPic') || hasClass(ONCE_NavChild, 'NavContent')) {
         ONCE_NavChild.style.display = 'none';
       }
     }
     ONCE_NavToggle.firstChild.data = ONCE_NavigationBarShow;
   
   // if hidden now
   } else if (ONCE_NavToggle.firstChild.data == ONCE_NavigationBarShow) {
     //Start hiding all open boxes. things with f is loops to close everything
     for (f = 1; f < 50; f++)  { //prevent indef loop
       var ONCE_f_NavToggle = document.getElementById("NavToggleOnce" + f);
       var ONCE_f_NavFrame = document.getElementById("NavFrameOnce" + f);
       
       if (!ONCE_f_NavFrame || !ONCE_f_NavToggle) {
         break;
       }
       for (var ONCE_f_NavChild = ONCE_f_NavFrame.firstChild; ONCE_f_NavChild != null; ONCE_f_NavChild = ONCE_f_NavChild.nextSibling) {
         if (hasClass(ONCE_f_NavChild, 'NavPic') || hasClass(ONCE_f_NavChild, 'NavContent')) {
           ONCE_f_NavChild.style.display = 'none';
         }
       }
       ONCE_f_NavToggle.firstChild.data = ONCE_NavigationBarShow; 
     }
     
     // open selected one
     for (var ONCE_NavChild = ONCE_NavFrame.firstChild; ONCE_NavChild != null; ONCE_NavChild = ONCE_NavChild.nextSibling) {
       if (hasClass(ONCE_NavChild, 'NavPic') || hasClass(ONCE_NavChild, 'NavContent')) {
         ONCE_NavChild.style.display = 'block';
       }
     }
     ONCE_NavToggle.firstChild.data = ONCE_NavigationBarHide;
   }
 }
 
 // shows and hides content and picture (if available) of navigation bars
 // Parameters:
 //     indexNavigationBar: the index of navigation bar to be toggled
 function toggleNavigationBar(indexNavigationBar)
 {
   var NavToggle = document.getElementById("NavToggle" + indexNavigationBar);
   var NavFrame = document.getElementById("NavFrame" + indexNavigationBar);
   
   if (!NavFrame || !NavToggle) {
     return false;
   }
   
   if (NavToggle.firstChild.data == NavigationBarHide) {
     // if shown now
     for (var NavChild = NavFrame.firstChild; NavChild != null; NavChild = NavChild.nextSibling) {
        if (hasClass(NavChild, 'NavPic') || hasClass(NavChild, 'NavContent')) {
          NavChild.style.display = 'none';
        }
      }
      NavToggle.firstChild.data = NavigationBarShow;
   } else if (NavToggle.firstChild.data == NavigationBarShow) {
     // if hidden now
     for (var NavChild = NavFrame.firstChild; NavChild != null; NavChild = NavChild.nextSibling) {
       if (hasClass(NavChild, 'NavPic') || hasClass(NavChild, 'NavContent')) {
         NavChild.style.display = 'block';
       }
     }
     NavToggle.firstChild.data = NavigationBarHide;
   }
 }
 
 // adds show/hide-button to navigation bars
 function createNavigationBarToggleButton()
 {
   var indexNavigationBar = 0, ONCE_indexNavigationBar = 0;
   var NavFrames = document.getElementsByTagName("div");
   
   // iterate over all < div >-elements
   for (var i=0; NavFrame = NavFrames[i]; i++) {
     // if found a once navigation bar
     if (hasClass(NavFrame, 'NavFrame') && hasClass(NavFrame, 'NavOnce')) {
       ONCE_indexNavigationBar++;
       var ONCE_NavToggle = document.createElement("a");
       ONCE_NavToggle.className = 'NavToggle';
       ONCE_NavToggle.setAttribute('id', 'NavToggleOnce' + ONCE_indexNavigationBar);
       ONCE_NavToggle.setAttribute('href', 'javascript:ONCE_toggleNavigationBar(' + ONCE_indexNavigationBar + ');');
       
       var ONCE_NavToggleText = document.createTextNode(ONCE_NavigationBarHide);
       ONCE_NavToggle.appendChild(ONCE_NavToggleText);
       // Find the NavHead and attach the toggle link (Must be this complicated because Moz's firstChild handling is borked)
       for (var j=0; j < NavFrame.childNodes.length; j++) {
         if (hasClass(NavFrame.childNodes[j], "NavHead")) {
           NavFrame.childNodes[j].appendChild(ONCE_NavToggle);
         }
       }
       NavFrame.setAttribute('id', 'NavFrameOnce' + ONCE_indexNavigationBar);
     } else if (hasClass(NavFrame, "NavFrame")) {
       // if found a navigation bar
       indexNavigationBar++;
       var NavToggle = document.createElement("a");
       NavToggle.className = 'NavToggle';
       NavToggle.setAttribute('id', 'NavToggle' + indexNavigationBar);
       NavToggle.setAttribute('href', 'javascript:toggleNavigationBar(' + indexNavigationBar + ');');
       
       var NavToggleText = document.createTextNode(NavigationBarHide);
       NavToggle.appendChild(NavToggleText);
       // Find the NavHead and attach the toggle link (Must be this complicated because Moz's firstChild handling is borked)
       for (var j=0; j < NavFrame.childNodes.length; j++) {
         if (hasClass(NavFrame.childNodes[j], "NavHead")) {
           NavFrame.childNodes[j].appendChild(NavToggle);
         }
       }
       NavFrame.setAttribute('id', 'NavFrame' + indexNavigationBar);
     }
   }
   
   // if more Navigation Bars found than Default: hide all
   if (NavigationBarShowDefault < indexNavigationBar) {
     for (var i=1; i<=indexNavigationBar; i++) {
       toggleNavigationBar(i);
     }
   }
   if (ONCE_NavigationBarShowDefault < ONCE_indexNavigationBar) {
     for (var i=1; i<=ONCE_indexNavigationBar; i++) {
        ONCE_toggleNavigationBar(i);
     }
   }
 }
 
 addLoadEvent(createNavigationBarToggleButton);
 
 // END Dynamic Navigation Bars
 // ============================================================
 
 /*
== custom edit buttons ==
 */
 
 if (mwCustomEditButtons) {
   mwCustomEditButtons[mwCustomEditButtons.length] = {
     "imageFile": "http://upload.wikimedia.org/wikipedia/commons/7/7f/Button_link_to_Wikipedia.png",
     "speedTip": "wikipedia link",
     "tagOpen": "[[w:",
     "tagClose": "|]]",
     "sampleText": "article title"
   };
   
   mwCustomEditButtons[mwCustomEditButtons.length] = {
     "imageFile": "http://upload.wikimedia.org/wikipedia/commons/4/47/Button_redir.png",
     "speedTip": "Redirect",
     "tagOpen": "#REDIRECT[[",
     "tagClose": "]]",
     "sampleText": "Redirected to"
   };
 }
 
 /*
== Comment tabs ==
 */
 importScript("MediaWiki:Comments.js");
 

  
 /*
==CGI:IRC login form==
 */
 // See [[mediawiki:Irc.js]]. If this does something stupid, blame [[user:Bawolff]]
 
 //load irc login box if on page
 addLoadEvent(function () {
   if (document.getElementById("cgiircbox")) {
     var url = "http://en.wikinews.org/w/index.php?title=Mediawiki:Irc.js&action=raw&ctype=text/javascript&dontcountme=s";
     var scriptElem = document.createElement( 'script' );
     scriptElem.setAttribute( 'src' , url );
     scriptElem.setAttribute( 'type' , 'text/javascript' );
     document.getElementsByTagName( 'head' )[0].appendChild( scriptElem );
   }
 });
 


/*
==Tabber code==
 */

/*
This was originally from http://www.barelyfitz.com/projects/tabber/
This has been modified very slightly for wikinews by [[user:Bawolff]] (you can use any of my modifications under the same license as the original author specified)

Please note, only the tabber code is under the MIT license. (the rest is probably either cc-by, GPL, or some combination thereof. its not really clear)

Note i'm slowly modifying to use hasClass() as it is supposedly more efficient

==================================================
  $Id: tabber.js,v 1.9 2006/04/27 20:51:51 pat Exp $
  tabber.js by Patrick Fitzgerald pat@barelyfitz.com

  Documentation can be found at the following URL:
  http://www.barelyfitz.com/projects/tabber/

  License (http://www.opensource.org/licenses/mit-license.php)

  Copyright (c) 2006 Patrick Fitzgerald, modifed by Bawolff

  Permission is hereby granted, free of charge, to any person
  obtaining a copy of this software and associated documentation files
  (the "Software"), to deal in the Software without restriction,
  including without limitation the rights to use, copy, modify, merge,
  publish, distribute, sublicense, and/or sell copies of the Software,
  and to permit persons to whom the Software is furnished to do so,
  subject to the following conditions:

  The above copyright notice and this permission notice shall be
  included in all copies or substantial portions of the Software.

  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
  EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
  MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
  NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS
  BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN
  ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
  CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
  SOFTWARE.
  ==================================================*/

function tabberObj(argsObj)
{
  var arg; /* name of an argument to override */

  /* Element for the main tabber div. If you supply this in argsObj,
     then the init() method will be called.
  */
  this.div = null;

  /* Class of the main tabber div */
  this.classMain = "tabber";

  /* Rename classMain to classMainLive after tabifying
     (so a different style can be applied)
  */
  this.classMainLive = "tabberlive";

  /* Class of each DIV that contains a tab */
  this.classTab = "tabbertab";

  /* Class to indicate which tab should be active on startup */
  this.classTabDefault = "tabbertabdefault";

  /* Class for the navigation UL */
  this.classNav = "tabbernav";

  /* add space between tabs to force line break. use class UseTabBreaks*/
  this.spaceTabs = false;



  /* When a tab is to be hidden, instead of setting display='none', we
     set the class of the div to classTabHide. In your screen
     stylesheet you should set classTabHide to display:none.  In your
     print stylesheet you should set display:block to ensure that all
     the information is printed.
  */
  this.classTabHide = "tabbertabhide";

  /* Class to set the navigation LI when the tab is active, so you can
     use a different style on the active tab.
  */
  this.classNavActive = "tabberactive";

  /* Elements that might contain the title for the tab, only used if a
     title is not specified in the TITLE attribute of DIV classTab.
  */
  this.titleElements = ['h2','h3','h4','h5','h6'];

  /* Should we strip out the HTML from the innerHTML of the title elements?
     This should usually be true.
  */
  this.titleElementsStripHTML = true;

  /* If the user specified the tab names using a TITLE attribute on
     the DIV, then the browser will display a tooltip whenever the
     mouse is over the DIV. To prevent this tooltip, we can remove the
     TITLE attribute after getting the tab name.
  */
  this.removeTitle = true;

  /* If you want to add an id to each link set this to true */
  this.addLinkId = false;

  /* If addIds==true, then you can set a format for the ids.
     <tabberid> will be replaced with the id of the main tabber div.
     <tabnumberzero> will be replaced with the tab number
       (tab numbers starting at zero)
     <tabnumberone> will be replaced with the tab number
       (tab numbers starting at one)
     <tabtitle> will be replaced by the tab title
       (with all non-alphanumeric characters removed)
   */
  this.linkIdFormat = '<tabberid>nav<tabnumberone>';

  /* You can override the defaults listed above by passing in an object:
     var mytab = new tabber({property:value,property:value});
  */
  for (arg in argsObj) { this[arg] = argsObj[arg]; }

  /* Create regular expressions for the class names; Note: if you
     change the class names after a new object is created you must
     also change these regular expressions.
  */
  this.REclassMain = new RegExp('\\b' + this.classMain + '\\b', 'gi');
  this.REclassMainLive = new RegExp('\\b' + this.classMainLive + '\\b', 'gi');
  this.REclassTab = new RegExp('\\b' + this.classTab + '\\b', 'gi');
  this.REclassTabDefault = new RegExp('\\b' + this.classTabDefault + '\\b', 'gi');
  this.REclassTabHide = new RegExp('\\b' + this.classTabHide + '\\b', 'gi');

  /* Array of objects holding info about each tab */
  this.tabs = new Array();

  /* If the main tabber div was specified, call init() now */
  if (this.div) {

    this.init(this.div);

    /* We don't need the main div anymore, and to prevent a memory leak
       in IE, we must remove the circular reference between the div
       and the tabber object. */
    this.div = null;
  }
}


/*--------------------------------------------------
  Methods for tabberObj
  --------------------------------------------------*/


tabberObj.prototype.init = function(e)
{
  /* Set up the tabber interface.

     e = element (the main containing div)

     Example:
     init(document.getElementById('mytabberdiv'))
   */

  var
  childNodes, /* child nodes of the tabber div */
  i, i2, /* loop indices */
  t, /* object to store info about a single tab */
  defaultTab=0, /* which tab to select by default */
  DOM_ul, /* tabbernav list */
  DOM_li, /* tabbernav list item */
  DOM_a, /* tabbernav link */
  aId, /* A unique id for DOM_a */
  headingElement; /* searching for text to use in the tab */

  /* Verify that the browser supports DOM scripting */
  if (!document.getElementsByTagName) { return false; }

  /* If the main DIV has an ID then save it. */
  if (e.id) {
    this.id = e.id;
  }

  /* Clear the tabs array (but it should normally be empty) */
  this.tabs.length = 0;

  /* Loop through an array of all the child nodes within our tabber element. */
  childNodes = e.childNodes;
  for(i=0; i < childNodes.length; i++) {

    /* Find the nodes where class="tabbertab" */
    if(childNodes[i].className &&
       hasClass(childNodes[i], this.classTab)) {
      
      /* Create a new object to save info about this tab */
      t = new Object();
      
      /* Save a pointer to the div for this tab */
      t.div = childNodes[i];
      
      /* Add the new object to the array of tabs */
      this.tabs[this.tabs.length] = t;

      /* If the class name contains classTabDefault,
	 then select this tab by default.
      */
      if (childNodes[i].className.match(this.REclassTabDefault)) {
	defaultTab = this.tabs.length-1;
      }
    }
  }

  /* Create a new UL list to hold the tab headings */
  DOM_ul = document.createElement("ul");
  DOM_ul.className = this.classNav;
  
  /* Loop through each tab we found */
  for (i=0; i < this.tabs.length; i++) {

    t = this.tabs[i];

    /* Get the label to use for this tab:
       From the title attribute on the DIV,
       Or from one of the this.titleElements[] elements,
       Or use an automatically generated number.
     */
    t.headingText = t.div.title;

    /* Remove the title attribute to prevent a tooltip from appearing */
    if (this.removeTitle) { t.div.title = ''; }

    if (!t.headingText) {

      /* Title was not defined in the title of the DIV,
	 So try to get the title from an element within the DIV.
	 Go through the list of elements in this.titleElements
	 (typically heading elements ['h2','h3','h4'])
      */
      for (i2=0; i2<this.titleElements.length; i2++) {
	headingElement = t.div.getElementsByTagName(this.titleElements[i2])[0];
	if (headingElement) {
	  t.headingText = headingElement.innerHTML;
	  if (this.titleElementsStripHTML) {
	    t.headingText.replace(/<br>/gi," ");
	    t.headingText = t.headingText.replace(/<[^>]+>/g,"");
	  }
	  break;
	}
      }
    }

    if (!t.headingText) {
      /* Title was not found (or is blank) so automatically generate a
         number for the tab.
      */
      t.headingText = i + 1;
    }

    /* Create a list element for the tab */
    DOM_li = document.createElement("li");

    /* Save a reference to this list item so we can later change it to
       the "active" class */
    t.li = DOM_li;

    /* Create a link to activate the tab */
    DOM_a = document.createElement("a");
    DOM_a.appendChild(document.createTextNode(t.headingText));
    DOM_a.href = "javascript:void(null);";
    DOM_a.title = t.headingText;
    DOM_a.onclick = this.navClick;

    /* Add some properties to the link so we can identify which tab
       was clicked. Later the navClick method will need this.
    */
    DOM_a.tabber = this;
    DOM_a.tabberIndex = i;

    /* Do we need to add an id to DOM_a? */
    if (this.addLinkId && this.linkIdFormat) {

      /* Determine the id name */
      aId = this.linkIdFormat;
      aId = aId.replace(/<tabberid>/gi, this.id);
      aId = aId.replace(/<tabnumberzero>/gi, i);
      aId = aId.replace(/<tabnumberone>/gi, i+1);
      aId = aId.replace(/<tabtitle>/gi, t.headingText.replace(/[^a-zA-Z0-9\-]/gi, ''));

      DOM_a.id = aId;
    }

    /* Add the link to the list element */
    DOM_li.appendChild(DOM_a);

    /* Add the list element to the list */
    DOM_ul.appendChild(DOM_li);

     /* add a space to get line breaks */
    if (this.spaceTabs) {
    DOM_ul.appendChild(document.createTextNode(" "));
    }


  }

  /* Add the UL list to the beginning of the tabber div */
  e.insertBefore(DOM_ul, e.firstChild);

  /* Make the tabber div "live" so different CSS can be applied */
  e.className = e.className.replace(this.REclassMain, this.classMainLive);

  /* Activate the default tab, and do not call the onclick handler */
  this.tabShow(defaultTab);

  /* If the user specified an onLoad function, call it now. */
  if (typeof this.onLoad == 'function') {
    this.onLoad({tabber:this});
  }

  return this;
};


tabberObj.prototype.navClick = function(event)
{
  /* This method should only be called by the onClick event of an <A>
     element, in which case we will determine which tab was clicked by
     examining a property that we previously attached to the <A>
     element.

     Since this was triggered from an onClick event, the variable
     "this" refers to the <A> element that triggered the onClick
     event (and not to the tabberObj).

     When tabberObj was initialized, we added some extra properties
     to the <A> element, for the purpose of retrieving them now. Get
     the tabberObj object, plus the tab number that was clicked.
  */

  var
  rVal, /* Return value from the user onclick function */
  a, /* element that triggered the onclick event */
  self, /* the tabber object */
  tabberIndex, /* index of the tab that triggered the event */
  onClickArgs; /* args to send the onclick function */

  a = this;
  if (!a.tabber) { return false; }

  self = a.tabber;
  tabberIndex = a.tabberIndex;

  /* Remove focus from the link because it looks ugly.
     I don't know if this is a good idea...
  */
  a.blur();

  /* If the user specified an onClick function, call it now.
     If the function returns false then do not continue.
  */
  if (typeof self.onClick == 'function') {

    onClickArgs = {'tabber':self, 'index':tabberIndex, 'event':event};

    /* IE uses a different way to access the event object */
    if (!event) { onClickArgs.event = window.event; }

    rVal = self.onClick(onClickArgs);
    if (rVal === false) { return false; }
  }

  self.tabShow(tabberIndex);

  return false;
};


tabberObj.prototype.tabHideAll = function()
{
  var i; /* counter */

  /* Hide all tabs and make all navigation links inactive */
  for (i = 0; i < this.tabs.length; i++) {
    this.tabHide(i);
  }
};


tabberObj.prototype.tabHide = function(tabberIndex)
{
  var div;

  if (!this.tabs[tabberIndex]) { return false; }

  /* Hide a single tab and make its navigation link inactive */
  div = this.tabs[tabberIndex].div;

  /* Hide the tab contents by adding classTabHide to the div */
  if (!div.className.match(this.REclassTabHide)) {
    div.className += ' ' + this.classTabHide;
  }
  this.navClearActive(tabberIndex);

  return this;
};


tabberObj.prototype.tabShow = function(tabberIndex)
{
  /* Show the tabberIndex tab and hide all the other tabs */

  var div;

  if (!this.tabs[tabberIndex]) { return false; }

  /* Hide all the tabs first */
  this.tabHideAll();

  /* Get the div that holds this tab */
  div = this.tabs[tabberIndex].div;

  /* Remove classTabHide from the div */
  div.className = div.className.replace(this.REclassTabHide, '');

  /* Mark this tab navigation link as "active" */
  this.navSetActive(tabberIndex);

  /* If the user specified an onTabDisplay function, call it now. */
  if (typeof this.onTabDisplay == 'function') {
    this.onTabDisplay({'tabber':this, 'index':tabberIndex});
  }

  return this;
};

tabberObj.prototype.navSetActive = function(tabberIndex)
{
  /* Note: this method does *not* enforce the rule
     that only one nav item can be active at a time.
  */

  /* Set classNavActive for the navigation list item */
  this.tabs[tabberIndex].li.className = this.classNavActive;

  return this;
};


tabberObj.prototype.navClearActive = function(tabberIndex)
{
  /* Note: this method does *not* enforce the rule
     that one nav should always be active.
  */

  /* Remove classNavActive from the navigation list item */
  this.tabs[tabberIndex].li.className = '';

  return this;
};


/*==================================================*/


function tabberAutomatic(tabberArgs)
{
  /* This function finds all DIV elements in the document where
     class=tabber.classMain, then converts them to use the tabber
     interface.

     tabberArgs = an object to send to "new tabber()"
  */

  var
    tempObj, /* Temporary tabber object */
    divs, /* Array of all divs on the page */
    i; /* Loop index */

  if (!tabberArgs) { tabberArgs = {}; }

  /* Create a tabber object so we can get the value of classMain */
  tempObj = new tabberObj(tabberArgs);

  /* Find all DIV elements in the document that have class=tabber */

  /* First get an array of all DIV elements and loop through them */
  divs = document.getElementsByTagName("div");
  for (i=0; i < divs.length; i++) {
    
    /* Is this DIV the correct class? */
    if (divs[i].className &&
	hasClass(divs[i], tempObj.classMain)) {

    if (hasClass(divs[i], "UseTabBreaks")) {
        tabberArgs.spaceTabs =  true;
    }
      
      /* Now tabify the DIV */
      tabberArgs.div = divs[i];
      divs[i].tabber = new tabberObj(tabberArgs);
    }
  }
/******
Begin special case links for the howdy template.
This is kind of ugly, but as good a place as any to stick the code.

Creates magic links for certain ids, in certain tab boxes
*/
  var howdy = document.getElementById('HowdyWelcomeTemplate');
  if (howdy) {
    var policy = document.getElementById('HowdyWelcomeTemplatePolicy');
    var talk = document.getElementById('HowdyWelcomeTemplateDiscussions');
    var a;
    if (talk) {
      a = document.createElement('a');
      a.href = "#";
      a.onclick = function() {howdy.tabber.tabShow(2); return false;} //show tab 3
      a.appendChild(talk.firstChild);
      talk.appendChild(a);

    }
    if (policy) {
      a = document.createElement('a');
      a.href = "#";
      a.onclick = function() {howdy.tabber.tabShow(1); return false;} //show tab 2
      a.appendChild(policy.firstChild);
      policy.appendChild(a);
    }
  }

/*******
End special case code for special links
*****/
  
  return this;
}


/*==================================================*/


function tabberAutomaticOnLoad(tabberArgs)
{

  /* This function adds tabberAutomatic to the window.onload event,
     so it will run after the document has finished loading.
  */
//  var oldOnLoad;

  if (!tabberArgs) { tabberArgs = {}; }

  /* Taken from: http://simon.incutio.com/archive/2004/05/26/addLoadEvent */

  /*oldOnLoad = window.onload;
  if (typeof window.onload != 'function') {
    window.onload = function() {
      tabberAutomatic(tabberArgs);
    };
  } else {
    window.onload = function() {
      oldOnLoad();
      tabberAutomatic(tabberArgs);
    };
  }*/

//Use the wiki onload
addOnloadHook(function() {
      tabberAutomatic(tabberArgs);
    })



}


/*==================================================*/


/* Run tabberAutomaticOnload() unless the "manualStartup" option was specified */

if (typeof tabberOptions == 'undefined') {

    tabberAutomaticOnLoad();

} else {

  if (!tabberOptions['manualStartup']) {
    tabberAutomaticOnLoad(tabberOptions);
  }

}


/*
End Tabber. Note, code after this line probably is not under the MIT license.
*/

 /*
== Miscellaneous/Other ==
 */
 
 // use [[User:TheFearow]]'s perpage css/js code. Includes [[MediaWiki:common.css/PAGENAME]] and [[MediaWiki:common.js/PAGENAME]]
 importStylesheet("MediaWiki:common.css/" + wgPageName);
 importScript("MediaWiki:common.js/" + wgPageName);

//Bawolff's modified ticker. if all goes well, will switch over shortly
 addLoadEvent(function () {if ((window.disable_ticker2 !== true) && (document.getElementById("singleTickerForPage") || document.getElementById('enableTickers'))) importScript("MediaWiki:ticker2.js");});

 
 // fix for the football portal
 if (wgPageName == "Portal:Football") { NavigationBarShowDefault = 50; }
 

 var load_extratabs = true;

 // edit tools selectbox (see [[MediaWiki:Edittools]] and [[MediaWiki:Edittools.js]])
 var load_edittools = true;
 importScript("MediaWiki:Edittools.js");

 //Twitter/facebook etc. See [[template:Social bookmarks]]
 //makes stuff in id="social_bookmarks" open in new window
 //and dynamically re-writes links to twitter to shorten urls
var newSmallPopup = function(url) {
return (function () {
window.open(url, "_blank", "width=640,height=480,menubar,resizable,scrollbars,status,toolbar");
return false;
});
}

addOnloadHook(function () {
var soc = document.getElementById('social_bookmarks');
if (soc) {
var links = soc.getElementsByTagName('a')
for (i=0;i<links.length;i++) {
if ( links[i].href.indexOf("http://twitter.com/?status") === 0) { /*isTwitter*/
links[i].href = "http://twitter.com/?status=" + encodeURIComponent("Look what I found on Wikinews: http://enwn.net/+" + wgArticleId);
//enwn.net is owned by ShakataGaNai
}
links[i].onclick = newSmallPopup(links[i].href);
}}});
/******************
Begin editintro stuff
This makes a custom editintro for people editing old pages, or coming directly from 'pedia
maintainer: User:bawolff
*****************/

function addEditIntro(template) {
//This function assumes that all edit links are in proper order (as in the title arg is first argument), and proper case
//This function appends &editintro=template to any link that edits the CURRENT page.
//template parameter should already be escaped

 //var as = document.getElementsByTagName('a');
 var bodyContent = document.getElementById('bodyContent');
 var list = getElementsByClassName((bodyContent ? bodyContent : document), "span", "editsection");
 list[list.length] = document.getElementById('ca-edit');

 for (var i = 0; i < list.length; i++) {
  var a = list[i].getElementsByTagName('a')[0];
  if (!a) {
   continue; //just in case
  }
  if (a.href.indexOf('&action=edit') === -1) { //if not an edit link
   continue;
  }
  if (a.href.indexOf('?title=' + wgPageName) === -1) { //not a link to this page
   continue;
  }
  if (a.href.indexOf('&editintro') !== -1) { //if it already has an editintro don't add another
   continue;
  }
  a.href = a.href + '&editintro=' + template;
 }

}

function doEditIntro() {
//called onload
 try {
  if (wgNamespaceNumber !== 0 || wgPageName === 'Main_Page') {
   return;
  }
  var notCurrent = doEditIntro.oldNews();
  var fromPedia = (document.referrer.indexOf('http://en.wikipedia.org') === 0);
  if (notCurrent) {
   addEditIntro('Template:Editintro_notcurrent');
  } else if (fromPedia) {
   addEditIntro('Template:Editintro_from_wikipedia');
  }
 }
 catch (e) {
  //in case of different skin and can't find cat links or something.
 }

}

doEditIntro.oldNews = function () {
 var cats = document.getElementById('catlinks').getElementsByTagName('a');
 var pubDate, pubCat, archiveCat, tmp, catName;
 for (var i = 0; i < cats.length; i++) {
  catName = cats[i].title;
  tmp = doEditIntro.parseDate(catName);
  if (!isNaN(tmp)) {
   pubDate = tmp;
  }
  if (catName === "Category:Published") {
   pubCat = true;
  }
  if (catName === "Category:Archived" || catName === "Category:AutoArchived") {
   archiveCat = true;
  }
 } //end getting cats

 if (!pubDate || !pubCat || archiveCat) {
 //not in a date category, not published, or already archived.
  return false;
 }
 if (((new Date()).getTime() - pubDate) > 48 * 60 * 60 * 1000) { //if more than 48h elasped
  return true;
 }
 else {
  return false;
 }
}

doEditIntro.parseDate = function (datestring) {
 // + sign converts to number
 //Returns NaN in even of parse failure.
 var date = datestring.replace(doEditIntro.dateRegex, doEditIntro.dateFunc);
 if (date === datestring) {
  return NaN;
 }
 return +date;
}
 
doEditIntro.dateRegex = /^Category:(January|February|March|April|May|June|July|August|September|October|November|December) (\d\d?), (20\d\d)$/;

doEditIntro.dateFunc = function (str, month, day, year) {
 //Since JS month starts at month 0
 
 var months = {'January' : 0, 'February': 1, 'March': 2, 'April': 3, 'May': 4, 'June': 5, 'July': 6, 'August': 7, 'September': 8, 'October': 9,  'November': 10,  'December': 11};

 return Date.UTC(year, months[month], day);
 
}
addOnloadHook(doEditIntro);

/*extern api, importScript, wgPageName */
/****
function to automate removing {{tl|develop}} and adding {{tl|review}}
Apperently people actually have problems with this.

Also removes {{tl|tasks}}

maintainers: [[user:Bawolff]]

****/
addOnloadHook(function () {
    var changeDevelopToReviewButton = document.getElementById('develop_to_review_link');
    if (changeDevelopToReviewButton) {
        try {
            //in a try since this makes some assumptions about the format of
            //the {{tl|develop}} template, which could change.
            importScript('User:Bawolff/mwapilib2.js');
            var button = document.createElement('button');
            button.type = 'button';
            button.appendChild(changeDevelopToReviewButton.firstChild.firstChild);
            changeDevelopToReviewButton.replaceChild(button, changeDevelopToReviewButton.firstChild);
            button.onclick = function () {
                if (this && this.firstChild && this.firstChild.data) this.firstChild.data = "Loading...";
                api(wgPageName).getPage().
                    setDefaultSummary("Please review this article. (moved using js from button)").
                    replace(/\{\{(?:[dD]evelop(?:ing|ment)?|[tT]asks)(?:\|[^}]*)?\}\}/g, "").
                    replace(/^/, "\{\{review}}\n").
                    savePage().
                    lift(function () {
                        alert("The article is now listed as needing review.");
                        location.reload();
                    }).
                    exec();
            }
        }
        catch (e) {}
    }
});

//See [[User:ShakataGaNai/Enwn.net]]
//based on http://enwn.net/bookmark.php

//adds a button to the toolbar to shorten the url of the page.

addOnloadHook(function () {addPortletLink('p-tb', "javascript:open('http://enwn.net/api.php?m=create&url='+escape(document.URL),%20'_new','width=300,height=100');void%200;", 'Shorten URL', 'enwn-url-shortener', 'Make a enwn.net shortcut for this page')});

//Load EasyPeerReview for editors (since everyone uses it, might as well put it in here)
if (wgUserGroups && wgUserGroups.join(' ').indexOf('editor') !== -1) {
 if (!(window.Bawolff && Bawolff.review)) {
  importScript('MediaWiki:Gadget-easyPeerReview.js');
 }
}

if (wgIsArticle) importScript('MediaWiki:Gadget-Feedbackhack.js'); //load feedback form styles

/* MediaWiki:Vector.js */
/* Any JavaScript here will be loaded for users using the Vector skin */

// !!'''Alternate stylesheets'''!! Can select from view->page style in firefox.

document.write('<link type="text/css" href="http://en.wikinews.org/w/index.php?title=Wikinews:Skins/vector-newspaper-back.css&action=raw&ctype=text/css" rel="alternate stylesheet" title="Newspaper background" media="screen,projection" \/>');


//to allow adding portlets easier for the namespace tabs
function addVectorNSTab (href, text, id, tooltip, accesskey, nextnode) {
           return addPortletLink('p-namespaces', href, text, id, tooltip, accesskey, nextnode) 
}

//some vector specific stuff for the comment namespace.
addOnloadHook(function() {
if (wgNamespaceNumber === 102) {
 var before = document.getElementById('ca-nstab-comments') ;
 var talk = addVectorNSTab(encodeURI(wgArticlePath.replace("$1", "talk:" + wgTitle)), "Discussion", "ca-main-talk", undefined, undefined, before);
 addVectorNSTab(encodeURI(wgArticlePath.replace("$1", wgTitle)), "Article", "ca-nstab-main", undefined, undefined, talk);
}

if (wgNamespaceNumber === 90) {

 var page = wgTitle.replace(/\/.*$/, '');
 page = page.replace(/^Comments:/, '');
 var before =  document.getElementById('ca-article');
 var talk = addVectorNSTab(encodeURI(wgArticlePath.replace("$1", "Talk:" + page)), "Discussion", "ca-main-talk", undefined, undefined, before);
 addVectorNSTab(encodeURI(wgArticlePath.replace("$1", page)), "Article", "ca-nstab-main", undefined, undefined, talk);
}
});