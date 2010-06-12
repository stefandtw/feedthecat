//'✧✧✧✧✧'

//Maintainer/author/etc: [[user:Bawolff]] ( http://en.wikinews.org/wiki/user:Bawolff ). Feel free to ask if you have questions.
//Gives the readerFeedback extension a make-over.

feedbackhack = {
 i18n: {
  sec_header: 'Comments from feedback form - "$1"', //new section header.
  commentPrefix: '<!-- Submitted from ReaderFeedback form via [[mediawiki:Gadget-Feedbackhack.js]] -->\n', //also edit summary. prefixed before comment
  commentSuffix: '', //appended after comment. could be used for template
  starFulledURL: 'http://upload.wikimedia.org/wikipedia/commons/thumb/f/f9/Full_Star_Red.svg/25px-Full_Star_Red.svg.png', //http://upload.wikimedia.org/wikipedia/commons/thumb/e/e5/Full_Star_Yellow.svg/25px-Full_Star_Yellow.svg.png
  starFulled: '✦', //must be text for the moment
  starEmptyURL: 'http://upload.wikimedia.org/wikinews/en/7/73/Sharpened-ReaderFeedback-25px-White_Stars_1.png',
  starEmpty: '✧',
  unknownError: 'Feedback modifier gadget had error (tell bawolff)\n-------\nTechnical details: ', //only displayed to sysops.
  timeoutError: 'Error submitting your comments (timeout). Try clicking on the opinions tab at the top of the page to submit your comments.',
  commentIntro: "<b>Comments on article: <small>(To report a factual error with the article, please use the <a href="+ wgServer + wgScript + '?title=talk:' + wgPageName + '&action=edit&section=new' + ">talk page</a>. Comments are public, see this article's <a href="+ wgServer + wgScript + '?title=comments:' + wgPageName + '&action=edit&section=new' + ">opinions page</a> for commentary submitted by other readers.)</small>", //not escaped.
  unsignedWikitext: ' &mdash;~~' + '~~', 
  successCommentsOnly: 'Thank you for submitting your comments. <a href="' + wgArticlePath.replace('$1', wgFormattedNamespaces[102] + ':' + encodeURIComponent(wgTitle)) +'">View comments submitted by others</a>.',
  postFailure: 'Failed trying to send in your comments. Please try again'
 },
 commentPage: (function () { //where to put comments from the box
  if (wgPageName === 'Main_Page') return 'talk:Main Page';
  else return 'Comments:' + wgPageName;
 })(),
 help: { //help text to display on hover with star. use selId as field name, default as fallback.
  'default': ['', '(Poor)', '(Fair)', '(Average)', '(Good)', '(Excellent)']
 }, //END i18n
 'in': function (selId, star) { //WARNING: IN is reservered word. call as feedbackhack['in']('wpnpov', 2)
  try {
   var stars;
   for (var i = 1; i <= star; i++) {
    stars = document.getElementById('starReplIndiv' + i + selId);
    stars.alt = feedbackhack.i18n.starFulled;
    stars.src = feedbackhack.i18n.starFulledURL;
   }
   for (; i <= 5; i++) {
    stars = document.getElementById('starReplIndiv' + i + selId);
    stars.alt = feedbackhack.i18n.starEmpty;
    stars.src = feedbackhack.i18n.starEmptyURL;
   }
   var help = '';
   if (feedbackhack.help[selId] && feedbackhack.help[selId][star] !== undefined) {
    help = feedbackhack.help[selId][star];
   }
   else if (feedbackhack.help['default'] && feedbackhack.help['default'][star] !== undefined) {
    //default a reserved word
    help = feedbackhack.help['default'][star];
   }
   var helpNode = document.getElementById('starReplHelpText' + selId);
   if (!helpNode.firstChild) { //for IE
    helpNode.appendChild(document.createTextNode(help));
   } else {
    helpNode.firstChild.data = help;
   }
  } catch (e) {
   if (wgUserGroups.join(' ').indexOf('sysop') !== -1) {
    alert(feedbackhack.i18n.unknownError + e.name + ': ' + e.message + ';' + ' i='+i + ' selId='+selId + ' star='+ star +' func: in');
   }
  }
 },
 'out': function (selId) {
  try {
   var star = 5 - document.getElementById(decodeURIComponent(selId.replace(/:/g, '%'))).selectedIndex;
   var stars;
   for (var i = 1; i <= star; i++) {
    stars = document.getElementById('starReplIndiv' + i + selId);
    stars.alt = feedbackhack.i18n.starFulled;
    stars.src = feedbackhack.i18n.starFulledURL;
   }
   for (; i <= 5; i++) {
    stars = document.getElementById('starReplIndiv' + i + selId);
    stars.alt = feedbackhack.i18n.starEmpty;
    stars.src = feedbackhack.i18n.starEmptyURL;
   }
   var helpNode = document.getElementById('starReplHelpText' + selId).firstChild;
   if (helpNode) helpNode.data = ' '; //for IE
  } catch (e) {
   if (wgUserGroups.join(' ').indexOf('sysop') !== -1) {
    alert(feedbackhack.i18n.unknownError + e.name + ': ' + e.message + ';' + ' i='+i + ' selId='+selId + ' func: out');
   }
  }
 },
 click: function (selId, star) {
  try {
   wgAjaxFeedback.supported = true;  //re-enable submitting.
   document.getElementById('mw-feedbackform').setAttribute('action', feedbackhack.action);

   document.getElementById(decodeURIComponent(selId.replace(/:/g, '%'))).selectedIndex = 5 - star;
   updateFeedbackForm();
   var i = 1;
   var stars;
   for (; i <= star; i++) {
    stars = document.getElementById('starReplIndiv' + i + selId);
    stars.alt = feedbackhack.i18n.starFulled;
    stars.src = feedbackhack.i18n.starFulledURL;
   }
   for (; i <= 5; i++) {
    stars = document.getElementById('starReplIndiv' + i + selId);
    stars.alt = feedbackhack.i18n.starEmpty;
    stars.src = feedbackhack.i18n.starEmptyURL;
   }
  } catch (e) {
   if (wgUserGroups.join(' ').indexOf('sysop') !== -1) {
    alert(feedbackhack.i18n.unknownError + e.name + ': ' + e.message + ';' + ' i='+i + ' selId='+selId + ' star='+ star +' func: click');
   }
  }
 },
 init: function () {
  try {
   var sel = document.getElementById('mw-feedbackselects');
   if (!sel || !wgIsArticle) return; //no feedback form

   wgAjaxFeedback.supported = false; //hack to stop unfilled form from submitting.
   var form = document.getElementById('mw-feedbackform');
   feedbackhack.action = form.getAttribute('action'); //getAttr due to form control w/ name action
   form.setAttribute('action', 'javascript:void%200'); //prevent submit.

   var prefetch = new Image()
   prefetch.src = feedbackhack.i18n.starFulledURL; //prefetch for rollover.
   var bolds = sel.getElementsByTagName('b'); //prey the html doesn't change. FIXME: This is very fragile.
   var maxBold = 0;
   for (var j = 0; j < bolds.length; j++) {
    if (bolds[j].offsetWidth > maxBold) maxBold = bolds[j].offsetWidth;
   }
   var selects = sel.getElementsByTagName('select');
   for (var i = 0; i < selects.length; i++ ) {
    var lineUpCorrect = 0;
    if (bolds[i]) {
     lineUpCorrect = maxBold - bolds[i].offsetWidth;
    }
    var selId = encodeURIComponent(selects[i].id).replace(/%/g, ':').replace(/['"><]/g, ''); //single quote could be lost
    selects[i].style.display = 'none';
    var container = document.createElement('SPAN');
    container.id = "starReplWholeLine" + selId;
    container.className = "starReplWholeLine";
    container.style.marginLeft = lineUpCorrect + 'px';

    container.innerHTML= '<span style="font-size:x-large;cursor: pointer;vertical-align:center" id="starRepl' + selId + '" class="starRepl"> <img id="starReplIndiv1' + selId + '" onmouseover="feedbackhack[\'in\'](\'' + selId + '\', 1)" onmouseout="feedbackhack.out(\'' + selId + '\', 1)" onclick="feedbackhack.click(\'' + selId + '\', 1)" src="' + feedbackhack.i18n.starEmptyURL + '" alt="' + feedbackhack.i18n.starEmpty + '"/> <img id="starReplIndiv2' + selId + '" onmouseover="feedbackhack[\'in\'](\'' + selId + '\', 2)" onmouseout="feedbackhack.out(\'' + selId + '\', 2)" onclick="feedbackhack.click(\'' + selId + '\', 2)" src="' + feedbackhack.i18n.starEmptyURL + '" alt="' + feedbackhack.i18n.starEmpty + '"/> <img id="starReplIndiv3' + selId + '" onmouseover="feedbackhack[\'in\'](\'' + selId + '\', 3)" onmouseout="feedbackhack.out(\'' + selId + '\', 3)" onclick="feedbackhack.click(\'' + selId + '\', 3)" src="' + feedbackhack.i18n.starEmptyURL + '" alt="' + feedbackhack.i18n.starEmpty + '"/> <img id="starReplIndiv4' + selId + '" onmouseover="feedbackhack[\'in\'](\'' + selId + '\', 4)" onmouseout="feedbackhack.out(\'' + selId + '\', 4)" onclick="feedbackhack.click(\'' + selId + '\', 4)" src="' + feedbackhack.i18n.starEmptyURL + '" alt="' + feedbackhack.i18n.starEmpty + '"/> <img id="starReplIndiv5' + selId + '" onmouseover="feedbackhack[\'in\'](\'' + selId + '\', 5)" onmouseout="feedbackhack.out(\'' + selId + '\', 5)" onclick="feedbackhack.click(\'' + selId + '\', 5)" src="' + feedbackhack.i18n.starEmptyURL + '" alt="' + feedbackhack.i18n.starEmpty + '"/></span> <span class="starReplHelpText" id="starReplHelpText' + selId + '" >&#32;</span><br/>';
sel.insertBefore(container, selects[i]);


   }
   //add comment form at end.
   //konq has issues if you use innerHTML here, so do this instead.
   var commentIntro = document.createElement('span');
   commentIntro.innerHTML = feedbackhack.i18n.commentIntro;
   var feedbackbox = document.createElement('textarea');
   feedbackbox.id = 'ReaderFeedbackHackCommentBox';
   if (wgPageName === 'Main_Page') {
    feedbackbox.style.display = 'none';
    commentIntro.style.display = 'none';
   }
   feedbackbox.onchange = function () {
    document.getElementById("submitfeedback").disabled = false;
    document.getElementById("ReaderFeedbackHackCommentBox").onchange = null;
   };
   feedbackbox.onkeyup = function () {
    document.getElementById("submitfeedback").disabled = false;
    document.getElementById("ReaderFeedbackHackCommentBox").onkeyup = null;
   };

   sel.appendChild(commentIntro);
   sel.appendChild(feedbackbox);

  //this should probably be onsubmit instead of onclick for accessability reasons.
  addClickHandler(document.getElementById('submitfeedback'), feedbackhack.submit);

  } catch (e) {
   if (wgUserGroups.join(' ').indexOf('sysop') !== -1) {
    alert(feedbackhack.i18n.unknownError + e.name + ': ' + e.message + ';' + ' i='+i + ' selId='+selId + ' func: init');
   }
  }
 },
 submitTryCount: 0,
 submit: function () {
  //called on submit. only handles the comment box. ReaderFeedback extension handles other boxes.
  var text = document.getElementById('ReaderFeedbackHackCommentBox').value;
  document.getElementById('ReaderFeedbackHackCommentBox').disabled = true;
  //check if filled out.
  if (text.replace(/\s/g, '').length < 2 || feedbackhack.submitTryCount === 1000) return;

  importScript('User:Bawolff/mwapilib.js');

  if (!(window.Bawolff && Bawolff.mwapi && Bawolff.mwapi.edit)) {

   //helper script not loaded.

   if ( feedbackhack.submitTryCount === 0) {
    feedbackhack.submitTryCount++;
    window.setTimeout(feedbackhack.submit, 800);
   } else if ( feedbackhack.submitTryCount < 35 ) {
    feedbackhack.submitTryCount++;
    window.setTimeout(feedbackhack.submit, 2500);
   } else {
    alert(feedbackhack.i18n.timeoutError);
    document.getElementById('ReaderFeedbackHackCommentBox').disabled = false;
    return;
   }

  }

  //do submit form.
  text = text.replace(/^\s*([\s\S]*\S)\s*$/gm, '$1'); //trim ws.
  var shotText = '';
  if (text.length <= 30) {
   shortText = text;
  } else {
   shortText = text.substring(0, 30) + '...';
  }
  
  //if (!text.match(/~{3,4}/)) {
  // text += feedbackhack.i18n.unsignedWikitext;
  //}

  //post comment to comments page (maybe using lqt)

  var fallback_post = function (err) {
   if (err.name === 'invalid-talkpage') {
    if (!text.match(/~{3,4}/)) {
     text += feedbackhack.i18n.unsignedWikitext;
    }
    Bawolff.mwapi.edit({page: feedbackhack.commentPage, summary: feedbackhack.i18n.sec_header.replace('$1', shortText), section: 'new', content: feedbackhack.i18n.commentPrefix + text + feedbackhack.i18n.commentSuffix}, function() { if (document.getElementById('mw-feedbackform').getAttribute('action') === 'javascript:void%200') jsMsg(feedbackhack.i18n.successCommentsOnly); location = '#mw-js-message';}, function () {alert(feedbackhack.i18n.postFailure) ; location = wgServer + wgScript + '?title=' + wgFormattedNamespaces[102] + ':' + encodeURIComponent(wgTitle) + '&lqt_method=talkpage_new_thread';});

   } else {
    if (wgUserGroups && wgUserGroups.join(' ').indexOf('sysop') !== -1) {
     alert(feedbackhack.i18n.unknownError + err.name + ': ' + err.message + ';' + ' After trying to post new LQT thread');
    }
    alert(feedbackhack.i18n.postFailure);
    location = wgServer + wgScript + '?title=' + wgFormattedNamespaces[102] + ':' + encodeURIComponent(wgTitle) + '&lqt_method=talkpage_new_thread';
   }
  }
  var lqt_post = function (token) {
   var f = new Bawolff.mwapi.Request({action: 'threadaction', talkpage: feedbackhack.commentPage, threadaction: 'newthread', subject: feedbackhack.i18n.sec_header.replace('$1', shortText), text: feedbackhack.i18n.commentPrefix + text + feedbackhack.i18n.commentSuffix, token: token}, 'POST');
   f.send(function(doc) {
    var thread = doc.getElementsByTagName('thread');
    if (thread && thread[0] && thread[0].getAttribute('result') === 'Success' ) {
     if (document.getElementById('mw-feedbackform').getAttribute('action') === 'javascript:void%200') {
      jsMsg(feedbackhack.i18n.successCommentsOnly);
      location = '#mw-js-message';
     }
    } else {
     //CAPTCHA generally
     alert(feedbackhack.i18n.postFailure);
     location = wgServer + wgScript + '?title=' + wgFormattedNamespaces[102] + ':' + encodeURIComponent(wgTitle) + '&lqt_method=talkpage_new_thread';
    }
   }, fallback_post);
  }
  Bawolff.mwapi.getToken(lqt_post);
  //end post comments to talk.

  feedbackhack.submitTryCount = 1000; //mark as submitted.

 }
};
addOnloadHook(feedbackhack.init);