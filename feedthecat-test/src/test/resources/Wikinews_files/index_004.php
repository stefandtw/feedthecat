// Drop down box for the Special character menu in [[MediaWiki:Edittools]]
// will be called from [[MediaWiki:Common.js]].
 
// Switch for people that don't want it
if (load_edittools == true)
{
 
function addCharSubsetMenu() {
 var specialchars = document.getElementById('specialchars');
 
 if (specialchars) {
  var menu = "<select style=\"display:inline\" onChange=\"chooseCharSubset(selectedIndex)\">";
  menu += "<option>Templates</option>";
  menu += "<option>Wikicode</option>";
  menu += "<option>Letters</option>";
  menu += "<option>Symbols</option>";
  menu += "<option>Review</option>";
  menu += "<option>HaveYourSay</option>";
  menu += "</select>";
  specialchars.innerHTML = menu + specialchars.innerHTML;
 
  // Standard-CharSubset
  chooseCharSubset(0);
 }
}
 
// CharSubset selection
function chooseCharSubset(s) {
 var l = document.getElementById('specialchars').getElementsByTagName('p');
 for (var i = 0; i < l.length ; i++) {
  l[i].style.display = i == s ? 'inline' : 'none';
  // l[i].style.visibility = i == s ? 'visible' : 'hidden';
 }
}
 
// Menu insertion
if (window.addEventListener) 
  window.addEventListener("load", addCharSubsetMenu, false);
else if (window.attachEvent) 
  window.attachEvent("onload", addCharSubsetMenu);
}