 // see [[Wikinews:Commentary pages on news events]].
 // This script has some crappy hacks in it
 // ==========================================================================
 // MediaWiki interface button for Comments page based on [[commons:mediawiki:extra-tabs.js]].
 // Originally written by Dbenbenn, Avatar, Duesentrieb and Arnomane
 // Modified by bawolff for wikinews. fixed by darklama
 
var _hasAttribute = function(elm, attribute) {
//IE does not element hasAttribute method of XML Dom elements
 try {
  return elm.hasAttribute(attribute);
 }
 catch(e) {
  return elm.getAttribute(attribute) !== null;
 }
}
 // Interface strings,
 tab_view_comment_on_article = 'Opinions';
 
 // Switch for people that don't want the extra tabs.
 // var load_extratabs = false;
 
 // Appends a new tab.
 function global_append_tab(action, name, where, id, title)
 {
   var tools = document.getElementById(where ? where : 'column-one');
   
   if (!where && tools)
     tools = tools.getElementsByTagName('div')[0].getElementsByTagName('ul')[0];
   if (!tools)
     return;
   
   var na = document.createElement('a').appendChild(document.createTextNode(name)).parentNode;
   
   if (typeof action == "string")
     na.setAttribute('href', action);
   else if (typeof action == "function")
     na.onclick = action;
   else
     return;
   
   var li = document.createElement('li').appendChild(na).parentNode;
   if (id) li.id = id;
   if (title) li.setAttribute("title", title);
   
   if (typeof id == "string") li.id = id;
   
   if (where)
     tools.parentNode.insertBefore(li, tools.nextSibling);
   else
     tools.appendChild(li);
 }
 
 function global_do_onload()
 {
   if (window.load_extratabs && load_extratabs == false)
     return;
   
   var title = wgPageName;
   switch(wgNamespaceNumber)
   {
     case 1:
       title = title.replace(/^[^:]*:/, "");
     case 0:
       if (title === 'Main_Page')
         return;
       title = encodeURIComponent(title).replace(/%2F/g, "/");
       if (skin == "vector") {
       //crappy temp hack. probably will break
           addPortletLink('p-namespaces', wgArticlePath.replace("$1", "Comments:" + title), tab_view_comment_on_article, 'ca-comments', 'Discuss your personal viewpoint on the topic at hand')
       } else {
       global_append_tab(wgArticlePath.replace("$1", "Comments:" + title), tab_view_comment_on_article, 'ca-talk', 'ca-comments');
       }
       try {
         checkCommentaryPageExist("Comments:" + title);
       }
       catch (e) {
          //In case Same orgin policy issue, problem with initiling, etc AJAX seems to have a lot of things that could go wrong
          //Ignore error: Is it really that bad if its the wrong colour?
       }
       break;
   }
 }
 
 // Run a background check of api.php to see if the particular page exists,
 // so a non-existing comment page can be red, and an existing comment page
 // will look like a normal tab.
 
 function checkCommentaryPageExist(pageName) {
   var x;
   x = sajax_init_object();
   
   // do nothing if we can't check
   if (!x) {
     return;
   }
   
   x.open("GET", wgScriptPath + "/api.php?action=query&format=xml&titles=" + pageName, true);
   x.setRequestHeader("Pragma", "cache=yes");
   x.setRequestHeader("Cache-Control", "no-transform");
   x.onreadystatechange = function() {
     if (x.readyState == 4) {
       if (x.status == 200) {
         var xmldoc = x.responseXML;
         var xpage = xmldoc.getElementsByTagName('page');
         if (_hasAttribute(xpage[0], 'missing')) { // is missing
           document.getElementById('ca-comments').className = 'new';
           document.getElementById('ca-comments').firstChild.setAttribute('href',
             wgServer+wgScript+ "?title=" + pageName + '&action=edit&preload=Wikinews:Commentary_pages_on_news_events/body' +
             '&editintro=Wikinews:Commentary_pages_on_news_events/intro');
         }
       }
     }
   };
   x.send(null);
 }
 
 addOnloadHook( global_do_onload );