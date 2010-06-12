/*extern addOnloadHook wgUserGroups*/
/*<source lang="javascript" line="1">

This is the javascript that is executed only when viewing the [[Main Page]].

*/

/* add class for autoconfirmed users, so [edit lead] only appears to those who can edit them */ 
addOnloadHook(function() {
if (!wgUserGroups || wgUserGroups.join('').indexOf('autoconfirmed') === -1) {
    try {
    document.body.className += " notAutoconfirmed";
    } catch (e) {}
}
});

function dieWhitespaceDie () {
 try {
 //this is for us low res folks
 //hides some of the members of the dpl list on main page, if list too long.
 //this is not cross browser (yet). IE uses different method names for height
 if (wgPageName !== "Main_Page" || wgAction !== "view") {return;}
 var firstLead = document.getElementById('l_table_numb4'); //switch to last after height:100% hack.
 var excessWS = (firstLead.parentNode.clientHeight - firstLead.clientHeight);
 var DPLList = document.getElementById('MainPage_latest_news_text').getElementsByTagName('ul')[0];
 var DPL = DPLList.getElementsByTagName('li');
 var DPLlength = DPL.length;
 //10 is just emergancy cut off. should stop way before that.
 if (DPLlength < 10) {throw new Error("Latest news should have more items...");}
 var curItem, prev = -1;
 for (var i = 1; i < 10;i++) { //note, start at 1.
  curItem = DPL[DPLlength - i];
  prev = excessWS;
  excessWS -= curItem.clientHeight;
  if (excessWS < 5 || prev === excessWS)  { break;}
  DPLList.removeChild(curItem); //this should perhaps use a range, so only a single re-draw.
  if (prev === (firstLead.parentNode.clientHeight - firstLead.clientHeight)) {break;}
 }
 } catch (e) {}
 
}
addOnloadHook(dieWhitespaceDie);

/*</source>*/