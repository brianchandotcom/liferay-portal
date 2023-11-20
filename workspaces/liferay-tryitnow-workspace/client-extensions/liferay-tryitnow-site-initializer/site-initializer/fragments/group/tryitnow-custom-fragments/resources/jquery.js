/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

!function(e,t){"use strict";"object"===typeof module&&"object"===typeof module.exports?module.exports=e.document?t(e,!0):function(e){if(!e.document){throw new Error("jQuery requires a window with a document");}

return t(e)}:t(e)}("undefined"!==typeof window?window:this,(C,e)=> {"use strict";const t=[]; const r=Object.getPrototypeOf; const s=t.slice; const g=t.flat?function(e){return t.flat.call(e)}:function(e){return t.concat.apply([],e)}; const u=t.push; const i=t.indexOf; const n={}; const o=n.toString; const v=n.hasOwnProperty; const a=v.toString; const l=a.call(Object); const y={}; const m=function(e){return"function"===typeof e&&"number"!==typeof e.nodeType}; const x=function(e){return null!=e&&e===e.window}; const E=C.document; const c={type:!0,src:!0,nonce:!0,noModule:!0};function b(e,t,n){let r; let i; const o=(n=n||E).createElement("script");if(o.text=e,t){for(r in c){(i=t[r]||t.getAttribute&&t.getAttribute(r))&&o.setAttribute(r,i);}}n.head.appendChild(o).parentNode.removeChild(o)}function w(e){return null==e?e+"":"object"===typeof e||"function"===typeof e?n[o.call(e)]||"object":typeof e}const f="3.5.1"; var S=function(e,t){return new S.fn.init(e,t)};function p(e){const t=!!e&&"length"in e&&e.length; const n=w(e);

return!m(e)&&!x(e)&&("array"===n||0===t||"number"===typeof t&&0<t&&t-1 in e)}S.fn=S.prototype={jquery:f,constructor:S,length:0,toArray(){return s.call(this)},get(e){return null==e?s.call(this):e<0?this[e+this.length]:this[e]},pushStack(e){const t=S.merge(this.constructor(),e);

return t.prevObject=this,t},each(e){return S.each(this,e)},map(n){return this.pushStack(S.map(this,(e,t)=> {return n.call(e,t,e)}))},slice(){return this.pushStack(s.apply(this,arguments))},first(){return this.eq(0)},last(){return this.eq(-1)},even(){return this.pushStack(S.grep(this,(e,t)=> {return(t+1)%2}))},odd(){return this.pushStack(S.grep(this,(e,t)=> {return t%2}))},eq(e){const t=this.length; const n=+e+(e<0?t:0);

return this.pushStack(0<=n&&n<t?[this[n]]:[])},end(){return this.prevObject||this.constructor()},push:u,sort:t.sort,splice:t.splice},S.extend=S.fn.extend=function(){let e; let t; let n; let r; let i; let o; let a=arguments[0]||{}; let s=1; const u=arguments.length; let l=!1;for("boolean"===typeof a&&(l=a,a=arguments[s]||{},s++),"object"===typeof a||m(a)||(a={}),s===u&&(a=this,s--);s<u;s++){if(null!=(e=arguments[s])){for(t in e){r=e[t],"__proto__"!==t&&a!==r&&(l&&r&&(S.isPlainObject(r)||(i=Array.isArray(r)))?(n=a[t],o=i&&!Array.isArray(n)?[]:i||S.isPlainObject(n)?n:{},i=!1,a[t]=S.extend(l,o,r)):void 0!==r&&(a[t]=r));}}}

return a},S.extend({expando:"jQuery"+(f+Math.random()).replace(/\D/g,""),isReady:!0,error(e){throw new Error(e)},noop(){},isPlainObject(e){let t; let n;

return!(!e||"[object Object]"!==o.call(e))&&(!(t=r(e))||"function"===typeof(n=v.call(t,"constructor")&&t.constructor)&&a.call(n)===l)},isEmptyObject(e){let t;for(t in e){return!1;}

return!0},globalEval(e,t,n){b(e,{nonce:t&&t.nonce},n)},each(e,t){let n; let r=0;if(p(e)){for(n=e.length;r<n;r++){if(!1===t.call(e[r],r,e[r])){break}}}else {for(r in e){if(!1===t.call(e[r],r,e[r])){break;}}}

return e},makeArray(e,t){const n=t||[];

return null!=e&&(p(Object(e))?S.merge(n,"string"===typeof e?[e]:e):u.call(n,e)),n},inArray(e,t,n){return null==t?-1:i.call(t,e,n)},merge(e,t){for(var n=+t.length,r=0,i=e.length;r<n;r++){e[i++]=t[r];}

return e.length=i,e},grep(e,t,n){for(var r=[],i=0,o=e.length,a=!n;i<o;i++){!t(e[i],i)!==a&&r.push(e[i]);}

return r},map(e,t,n){let r; let i; let o=0; const a=[];if(p(e)){for(r=e.length;o<r;o++){null!=(i=t(e[o],o,n))&&a.push(i);}}else {for(o in e){null!=(i=t(e[o],o,n))&&a.push(i);}}

return g(a)},guid:1,support:y}),"function"===typeof Symbol&&(S.fn[Symbol.iterator]=t[Symbol.iterator]),S.each("Boolean Number String Function Array Date RegExp Object Error Symbol".split(" "),(e,t)=> {n["[object "+t+"]"]=t.toLowerCase()});const d=function(n){let e; let d; let b; let o; let i; let h; let f; let g; let w; let u; let l; let T; let C; let a; let E; let v; let s; let c; let y; const S="sizzle"+1*new Date; const p=n.document; let k=0; let r=0; const m=ue(); const x=ue(); const A=ue(); const N=ue(); let D=function(e,t){return e===t&&(l=!0),0}; const j={}.hasOwnProperty; let t=[]; const q=t.pop; const L=t.push; let H=t.push; const O=t.slice; const P=function(e,t){for(let n=0,r=e.length;n<r;n++){if(e[n]===t){return n;}}

return-1}; const R="checked|selected|async|autofocus|autoplay|controls|defer|disabled|hidden|ismap|loop|multiple|open|readonly|required|scoped"; const M="[\\x20\\t\\r\\n\\f]"; const I="(?:\\\\[\\da-fA-F]{1,6}"+M+"?|\\\\[^\\r\\n\\f]|[\\w-]|[^\0-\\x7f])+"; const W="\\["+M+"*("+I+")(?:"+M+"*([*^$|!~]?=)"+M+"*(?:'((?:\\\\.|[^\\\\'])*)'|\"((?:\\\\.|[^\\\\\"])*)\"|("+I+"))|)"+M+"*\\]"; const F=":("+I+")(?:\\((('((?:\\\\.|[^\\\\'])*)'|\"((?:\\\\.|[^\\\\\"])*)\")|((?:\\\\.|[^\\\\()[\\]]|"+W+")*)|.*)\\)|)"; const B=new RegExp(M+"+","g"); const $=new RegExp("^"+M+"+|((?:^|[^\\\\])(?:\\\\.)*)"+M+"+$","g"); const _=new RegExp("^"+M+"*,"+M+"*"); const z=new RegExp("^"+M+"*([>+~]|"+M+")"+M+"*"); const U=new RegExp(M+"|>"); const X=new RegExp(F); const V=new RegExp("^"+I+"$"); const G={ID:new RegExp("^#("+I+")"),CLASS:new RegExp("^\\.("+I+")"),TAG:new RegExp("^("+I+"|[*])"),ATTR:new RegExp("^"+W),PSEUDO:new RegExp("^"+F),CHILD:new RegExp("^:(only|first|last|nth|nth-last)-(child|of-type)(?:\\("+M+"*(even|odd|(([+-]|)(\\d*)n|)"+M+"*(?:([+-]|)"+M+"*(\\d+)|))"+M+"*\\)|)","i"),bool:new RegExp("^(?:"+R+")$","i"),needsContext:new RegExp("^"+M+"*[>+~]|:(even|odd|eq|gt|lt|nth|first|last)(?:\\("+M+"*((?:-\\d)?\\d*)"+M+"*\\)|)(?=[^-]|$)","i")}; const Y=/HTML$/i; const Q=/^(?:input|select|textarea|button)$/i; const J=/^h\d$/i; const K=/^[^{]+\{\s*\[native \w/; const Z=/^(?:#([\w-]+)|(\w+)|\.([\w-]+))$/; const ee=/[+~]/; const te=new RegExp("\\\\[\\da-fA-F]{1,6}"+M+"?|\\\\([^\\r\\n\\f])","g"); const ne=function(e,t){const n="0x"+e.slice(1)-65536;

return t||(n<0?String.fromCharCode(n+65536):String.fromCharCode(n>>10|55296,1023&n|56320))}; const re=/([\0-\x1f\x7f]|^-?\d)|^-$|[^\0-\x1f\x7f-\uFFFF\w-]/g; const ie=function(e,t){return t?"\0"===e?"\ufffd":e.slice(0,-1)+"\\"+e.charCodeAt(e.length-1).toString(16)+" ":"\\"+e}; const oe=function(){T()}; const ae=be((e)=> {return!0===e.disabled&&"fieldset"===e.nodeName.toLowerCase()},{dir:"parentNode",next:"legend"});try{H.apply(t=O.call(p.childNodes),p.childNodes),t[p.childNodes.length].nodeType}catch(e){H={apply:t.length?function(e,t){L.apply(e,O.call(t))}:function(e,t){let n=e.length; let r=0;while(e[n++]=t[r++]){;}e.length=n-1}}}function se(t,e,n,r){let i; let o; let a; let s; let u; let l; let c; let f=e&&e.ownerDocument; const p=e?e.nodeType:9;if(n=n||[],"string"!==typeof t||!t||1!==p&&9!==p&&11!==p){return n;}if(!r&&(T(e),e=e||C,E)){if(11!==p&&(u=Z.exec(t))){if(i=u[1]){if(9===p){if(!(a=e.getElementById(i))){return n;}if(a.id===i){return n.push(a),n}}else if(f&&(a=f.getElementById(i))&&y(e,a)&&a.id===i){return n.push(a),n}}else{if(u[2]){return H.apply(n,e.getElementsByTagName(t)),n;}if((i=u[3])&&d.getElementsByClassName&&e.getElementsByClassName){return H.apply(n,e.getElementsByClassName(i)),n}}}if(d.qsa&&!N[t+" "]&&(!v||!v.test(t))&&(1!==p||"object"!==e.nodeName.toLowerCase())){if(c=t,f=e,1===p&&(U.test(t)||z.test(t))){(f=ee.test(t)&&ye(e.parentNode)||e)===e&&d.scope||((s=e.getAttribute("id"))?s=s.replace(re,ie):e.setAttribute("id",s=S)),o=(l=h(t)).length;while(o--){l[o]=(s?"#"+s:":scope")+" "+xe(l[o]);}c=l.join(",")}try{return H.apply(n,f.querySelectorAll(c)),n}catch(e){N(t,!0)}finally{s===S&&e.removeAttribute("id")}}}

return g(t.replace($,"$1"),e,n,r)}function ue(){const r=[];

return function e(t,n){return r.push(t+" ")>b.cacheLength&&delete e[r.shift()],e[t+" "]=n}}function le(e){return e[S]=!0,e}function ce(e){let t=C.createElement("fieldset");try{return!!e(t)}catch(e){return!1}finally{t.parentNode&&t.parentNode.removeChild(t),t=null}}function fe(e,t){const n=e.split("|"); let r=n.length;while(r--){b.attrHandle[n[r]]=t}}function pe(e,t){let n=t&&e; const r=n&&1===e.nodeType&&1===t.nodeType&&e.sourceIndex-t.sourceIndex;if(r){return r;}if(n){while(n=n.nextSibling){if(n===t){return-1;}}}

return e?1:-1}function de(t){return function(e){return"input"===e.nodeName.toLowerCase()&&e.type===t}}function he(n){return function(e){const t=e.nodeName.toLowerCase();

return("input"===t||"button"===t)&&e.type===n}}function ge(t){return function(e){return"form"in e?e.parentNode&&!1===e.disabled?"label"in e?"label"in e.parentNode?e.parentNode.disabled===t:e.disabled===t:e.isDisabled===t||e.isDisabled!==!t&&ae(e)===t:e.disabled===t:"label"in e&&e.disabled===t}}function ve(a){return le((o)=> {return o=+o,le((e,t)=> {let n; const r=a([],e.length,o); let i=r.length;while(i--){e[n=r[i]]&&(e[n]=!(t[n]=e[n]))}})})}function ye(e){return e&&"undefined"!==typeof e.getElementsByTagName&&e}for(e in d=se.support={},i=se.isXML=function(e){const t=e.namespaceURI; const n=(e.ownerDocument||e).documentElement;

return!Y.test(t||n&&n.nodeName||"HTML")},T=se.setDocument=function(e){let t; let n; const r=e?e.ownerDocument||e:p;

return r!=C&&9===r.nodeType&&r.documentElement&&(a=(C=r).documentElement,E=!i(C),p!=C&&(n=C.defaultView)&&n.top!==n&&(n.addEventListener?n.addEventListener("unload",oe,!1):n.attachEvent&&n.attachEvent("onunload",oe)),d.scope=ce((e)=> {return a.appendChild(e).appendChild(C.createElement("div")),"undefined"!==typeof e.querySelectorAll&&!e.querySelectorAll(":scope fieldset div").length}),d.attributes=ce((e)=> {return e.className="i",!e.getAttribute("className")}),d.getElementsByTagName=ce((e)=> {return e.appendChild(C.createComment("")),!e.getElementsByTagName("*").length}),d.getElementsByClassName=K.test(C.getElementsByClassName),d.getById=ce((e)=> {return a.appendChild(e).id=S,!C.getElementsByName||!C.getElementsByName(S).length}),d.getById?(b.filter.ID=function(e){const t=e.replace(te,ne);

return function(e){return e.getAttribute("id")===t}},b.find.ID=function(e,t){if("undefined"!==typeof t.getElementById&&E){const n=t.getElementById(e);

return n?[n]:[]}}):(b.filter.ID=function(e){const n=e.replace(te,ne);

return function(e){const t="undefined"!==typeof e.getAttributeNode&&e.getAttributeNode("id");

return t&&t.value===n}},b.find.ID=function(e,t){if("undefined"!==typeof t.getElementById&&E){let n; let r; let i; let o=t.getElementById(e);if(o){if((n=o.getAttributeNode("id"))&&n.value===e){return[o];}i=t.getElementsByName(e),r=0;while(o=i[r++]){if((n=o.getAttributeNode("id"))&&n.value===e){return[o]}}}

return[]}}),b.find.TAG=d.getElementsByTagName?function(e,t){return"undefined"!==typeof t.getElementsByTagName?t.getElementsByTagName(e):d.qsa?t.querySelectorAll(e):void 0}:function(e,t){let n; const r=[]; let i=0; const o=t.getElementsByTagName(e);if("*"===e){while(n=o[i++]){1===n.nodeType&&r.push(n);}

return r}

return o},b.find.CLASS=d.getElementsByClassName&&function(e,t){if("undefined"!==typeof t.getElementsByClassName&&E){return t.getElementsByClassName(e)}},s=[],v=[],(d.qsa=K.test(C.querySelectorAll))&&(ce((e)=> {let t;a.appendChild(e).innerHTML="<a id='"+S+"'></a><select id='"+S+"-\r\\' msallowcapture=''><option selected=''></option></select>",e.querySelectorAll("[msallowcapture^='']").length&&v.push("[*^$]="+M+"*(?:''|\"\")"),e.querySelectorAll("[selected]").length||v.push("\\["+M+"*(?:value|"+R+")"),e.querySelectorAll("[id~="+S+"-]").length||v.push("~="),(t=C.createElement("input")).setAttribute("name",""),e.appendChild(t),e.querySelectorAll("[name='']").length||v.push("\\["+M+"*name"+M+"*="+M+"*(?:''|\"\")"),e.querySelectorAll(":checked").length||v.push(":checked"),e.querySelectorAll("a#"+S+"+*").length||v.push(".#.+[+~]"),e.querySelectorAll("\\\f"),v.push("[\\r\\n\\f]")}),ce((e)=> {e.innerHTML="<a href='' disabled='disabled'></a><select disabled='disabled'><option/></select>";const t=C.createElement("input");t.setAttribute("type","hidden"),e.appendChild(t).setAttribute("name","D"),e.querySelectorAll("[name=d]").length&&v.push("name"+M+"*[*^$|!~]?="),2!==e.querySelectorAll(":enabled").length&&v.push(":enabled",":disabled"),a.appendChild(e).disabled=!0,2!==e.querySelectorAll(":disabled").length&&v.push(":enabled",":disabled"),e.querySelectorAll("*,:x"),v.push(",.*:")})),(d.matchesSelector=K.test(c=a.matches||a.webkitMatchesSelector||a.mozMatchesSelector||a.oMatchesSelector||a.msMatchesSelector))&&ce((e)=> {d.disconnectedMatch=c.call(e,"*"),c.call(e,"[s!='']:x"),s.push("!=",F)}),v=v.length&&new RegExp(v.join("|")),s=s.length&&new RegExp(s.join("|")),t=K.test(a.compareDocumentPosition),y=t||K.test(a.contains)?function(e,t){const n=9===e.nodeType?e.documentElement:e; const r=t&&t.parentNode;

return e===r||!(!r||1!==r.nodeType||!(n.contains?n.contains(r):e.compareDocumentPosition&&16&e.compareDocumentPosition(r)))}:function(e,t){if(t){while(t=t.parentNode){if(t===e){return!0;}}}

return!1},D=t?function(e,t){if(e===t){return l=!0,0;}let n=!e.compareDocumentPosition-!t.compareDocumentPosition;

return n||(1&(n=(e.ownerDocument||e)==(t.ownerDocument||t)?e.compareDocumentPosition(t):1)||!d.sortDetached&&t.compareDocumentPosition(e)===n?e==C||e.ownerDocument==p&&y(p,e)?-1:t==C||t.ownerDocument==p&&y(p,t)?1:u?P(u,e)-P(u,t):0:4&n?-1:1)}:function(e,t){if(e===t){return l=!0,0;}let n; let r=0; const i=e.parentNode; const o=t.parentNode; const a=[e]; const s=[t];if(!i||!o){return e==C?-1:t==C?1:i?-1:o?1:u?P(u,e)-P(u,t):0;}if(i===o){return pe(e,t);}n=e;while(n=n.parentNode){a.unshift(n);}n=t;while(n=n.parentNode){s.unshift(n);}while(a[r]===s[r]){r++;}

return r?pe(a[r],s[r]):a[r]==p?-1:s[r]==p?1:0}),C},se.matches=function(e,t){return se(e,null,null,t)},se.matchesSelector=function(e,t){if(T(e),d.matchesSelector&&E&&!N[t+" "]&&(!s||!s.test(t))&&(!v||!v.test(t))){try{const n=c.call(e,t);if(n||d.disconnectedMatch||e.document&&11!==e.document.nodeType){return n}}catch(e){N(t,!0)}}

return !!se(t,C,null,[e]).length},se.contains=function(e,t){return(e.ownerDocument||e)!=C&&T(e),y(e,t)},se.attr=function(e,t){(e.ownerDocument||e)!=C&&T(e);const n=b.attrHandle[t.toLowerCase()]; let r=n&&j.call(b.attrHandle,t.toLowerCase())?n(e,t,!E):void 0;

return void 0!==r?r:d.attributes||!E?e.getAttribute(t):(r=e.getAttributeNode(t))&&r.specified?r.value:null},se.escape=function(e){return(e+"").replace(re,ie)},se.error=function(e){throw new Error("Syntax error, unrecognized expression: "+e)},se.uniqueSort=function(e){let t; const n=[]; let r=0; let i=0;if(l=!d.detectDuplicates,u=!d.sortStable&&e.slice(0),e.sort(D),l){while(t=e[i++]){t===e[i]&&(r=n.push(i));}while(r--){e.splice(n[r],1)}}

return u=null,e},o=se.getText=function(e){let t; let n=""; let r=0; const i=e.nodeType;if(i){if(1===i||9===i||11===i){if("string"===typeof e.textContent){return e.textContent;}for(e=e.firstChild;e;e=e.nextSibling){n+=o(e)}}else if(3===i||4===i){return e.nodeValue}}else {while(t=e[r++]){n+=o(t);}}

return n},(b=se.selectors={cacheLength:50,createPseudo:le,match:G,attrHandle:{},find:{},relative:{">":{dir:"parentNode",first:!0}," ":{dir:"parentNode"},"+":{dir:"previousSibling",first:!0},"~":{dir:"previousSibling"}},preFilter:{ATTR(e){return e[1]=e[1].replace(te,ne),e[3]=(e[3]||e[4]||e[5]||"").replace(te,ne),"~="===e[2]&&(e[3]=" "+e[3]+" "),e.slice(0,4)},CHILD(e){return e[1]=e[1].toLowerCase(),"nth"===e[1].slice(0,3)?(e[3]||se.error(e[0]),e[4]=+(e[4]?e[5]+(e[6]||1):2*("even"===e[3]||"odd"===e[3])),e[5]=+(e[7]+e[8]||"odd"===e[3])):e[3]&&se.error(e[0]),e},PSEUDO(e){let t; const n=!e[6]&&e[2];

return G.CHILD.test(e[0])?null:(e[3]?e[2]=e[4]||e[5]||"":n&&X.test(n)&&(t=h(n,!0))&&(t=n.indexOf(")",n.length-t)-n.length)&&(e[0]=e[0].slice(0,t),e[2]=n.slice(0,t)),e.slice(0,3))}},filter:{TAG(e){const t=e.replace(te,ne).toLowerCase();

return"*"===e?function(){return!0}:function(e){return e.nodeName&&e.nodeName.toLowerCase()===t}},CLASS(e){let t=m[e+" "];

return t||(t=new RegExp("(^|"+M+")"+e+"("+M+"|$)"))&&m(e,(e)=> {return t.test("string"===typeof e.className&&e.className||"undefined"!==typeof e.getAttribute&&e.getAttribute("class")||"")})},ATTR(n,r,i){return function(e){let t=se.attr(e,n);

return null==t?"!="===r:!r||(t+="","="===r?t===i:"!="===r?t!==i:"^="===r?i&&0===t.indexOf(i):"*="===r?i&&-1<t.indexOf(i):"$="===r?i&&t.slice(-i.length)===i:"~="===r?-1<(" "+t.replace(B," ")+" ").indexOf(i):"|="===r&&(t===i||t.slice(0,i.length+1)===i+"-"))}},CHILD(h,e,t,g,v){const y="nth"!==h.slice(0,3); const m="last"!==h.slice(-4); const x="of-type"===e;

return 1===g&&0===v?function(e){return!!e.parentNode}:function(e,t,n){let r; let i; let o; let a; let s; let u; let l=y!==m?"nextSibling":"previousSibling"; const c=e.parentNode; const f=x&&e.nodeName.toLowerCase(); const p=!n&&!x; let d=!1;if(c){if(y){while(l){a=e;while(a=a[l]){if(x?a.nodeName.toLowerCase()===f:1===a.nodeType){return!1;}}u=l="only"===h&&!u&&"nextSibling"}

return!0}if(u=[m?c.firstChild:c.lastChild],m&&p){d=(s=(r=(i=(o=(a=c)[S]||(a[S]={}))[a.uniqueID]||(o[a.uniqueID]={}))[h]||[])[0]===k&&r[1])&&r[2],a=s&&c.childNodes[s];while(a=++s&&a&&a[l]||(d=s=0)||u.pop()){if(1===a.nodeType&&++d&&a===e){i[h]=[k,s,d];break}}}else if(p&&(d=s=(r=(i=(o=(a=e)[S]||(a[S]={}))[a.uniqueID]||(o[a.uniqueID]={}))[h]||[])[0]===k&&r[1]),!1===d){while(a=++s&&a&&a[l]||(d=s=0)||u.pop()){if((x?a.nodeName.toLowerCase()===f:1===a.nodeType)&&++d&&(p&&((i=(o=a[S]||(a[S]={}))[a.uniqueID]||(o[a.uniqueID]={}))[h]=[k,d]),a===e)){break;}}}

return(d-=v)===g||d%g==0&&0<=d/g}}},PSEUDO(e,o){let t; const a=b.pseudos[e]||b.setFilters[e.toLowerCase()]||se.error("unsupported pseudo: "+e);

return a[S]?a(o):1<a.length?(t=[e,e,"",o],b.setFilters.hasOwnProperty(e.toLowerCase())?le((e,t)=> {let n; const r=a(e,o); let i=r.length;while(i--){e[n=P(e,r[i])]=!(t[n]=r[i])}}):function(e){return a(e,0,t)}):a}},pseudos:{not:le((e)=> {const r=[]; const i=[]; const s=f(e.replace($,"$1"));

return s[S]?le((e,t,n,r)=> {let i; const o=s(e,null,r,[]); let a=e.length;while(a--){(i=o[a])&&(e[a]=!(t[a]=i))}}):function(e,t,n){return r[0]=e,s(r,null,n,i),r[0]=null,!i.pop()}}),has:le((t)=> {return function(e){return !!se(t,e).length}}),contains:le((t)=> {return t=t.replace(te,ne),function(e){return-1<(e.textContent||o(e)).indexOf(t)}}),lang:le((n)=> {return V.test(n||"")||se.error("unsupported lang: "+n),n=n.replace(te,ne).toLowerCase(),function(e){let t;do{if(t=E?e.lang:e.getAttribute("xml:lang")||e.getAttribute("lang")){return(t=t.toLowerCase())===n||0===t.indexOf(n+"-")}}while((e=e.parentNode)&&1===e.nodeType);

return!1}}),target(e){const t=n.location&&n.location.hash;

return t&&t.slice(1)===e.id},root(e){return e===a},focus(e){return e===C.activeElement&&(!C.hasFocus||C.hasFocus())&&!!(e.type||e.href||~e.tabIndex)},enabled:ge(!1),disabled:ge(!0),checked(e){const t=e.nodeName.toLowerCase();

return"input"===t&&!!e.checked||"option"===t&&!!e.selected},selected(e){return e.parentNode&&e.parentNode.selectedIndex,!0===e.selected},empty(e){for(e=e.firstChild;e;e=e.nextSibling){if(e.nodeType<6){return!1;}}

return!0},parent(e){return!b.pseudos.empty(e)},header(e){return J.test(e.nodeName)},input(e){return Q.test(e.nodeName)},button(e){const t=e.nodeName.toLowerCase();

return"input"===t&&"button"===e.type||"button"===t},text(e){let t;

return"input"===e.nodeName.toLowerCase()&&"text"===e.type&&(null==(t=e.getAttribute("type"))||"text"===t.toLowerCase())},first:ve(()=> {return[0]}),last:ve((e,t)=> {return[t-1]}),eq:ve((e,t,n)=> {return[n<0?n+t:n]}),even:ve((e,t)=> {for(let n=0;n<t;n+=2){e.push(n);}

return e}),odd:ve((e,t)=> {for(let n=1;n<t;n+=2){e.push(n);}

return e}),lt:ve((e,t,n)=> {for(let r=n<0?n+t:t<n?t:n;0<=--r;){e.push(r);}

return e}),gt:ve((e,t,n)=> {for(let r=n<0?n+t:n;++r<t;){e.push(r);}

return e})}}).pseudos.nth=b.pseudos.eq,{radio:!0,checkbox:!0,file:!0,password:!0,image:!0}){b.pseudos[e]=de(e);}for(e in{submit:!0,reset:!0}){b.pseudos[e]=he(e);}function me(){}function xe(e){for(var t=0,n=e.length,r="";t<n;t++){r+=e[t].value;}

return r}function be(s,e,t){const u=e.dir; const l=e.next; const c=l||u; const f=t&&"parentNode"===c; const p=r++;

return e.first?function(e,t,n){while(e=e[u]){if(1===e.nodeType||f){return s(e,t,n);}}

return!1}:function(e,t,n){let r; let i; let o; const a=[k,p];if(n){while(e=e[u]){if((1===e.nodeType||f)&&s(e,t,n)){return!0}}}else {while(e=e[u]){if(1===e.nodeType||f){if(i=(o=e[S]||(e[S]={}))[e.uniqueID]||(o[e.uniqueID]={}),l&&l===e.nodeName.toLowerCase()){e=e[u]||e;}else{if((r=i[c])&&r[0]===k&&r[1]===p){return a[2]=r[2];}if((i[c]=a)[2]=s(e,t,n)){return!0}}}}}

return!1}}function we(i){return 1<i.length?function(e,t,n){let r=i.length;while(r--){if(!i[r](e,t,n)){return!1;}}

return!0}:i[0]}function Te(e,t,n,r,i){for(var o,a=[],s=0,u=e.length,l=null!=t;s<u;s++){(o=e[s])&&(n&&!n(o,r,i)||(a.push(o),l&&t.push(s)));}

return a}function Ce(d,h,g,v,y,e){return v&&!v[S]&&(v=Ce(v)),y&&!y[S]&&(y=Ce(y,e)),le((e,t,n,r)=> {let i; let o; let a; const s=[]; const u=[]; const l=t.length; const c=e||function(e,t,n){for(let r=0,i=t.length;r<i;r++){se(e,t[r],n);}

return n}(h||"*",n.nodeType?[n]:n,[]); const f=!d||!e&&h?c:Te(c,s,d,n,r); let p=g?y||(e?d:l||v)?[]:t:f;if(g&&g(f,p,n,r),v){i=Te(p,u),v(i,[],n,r),o=i.length;while(o--){(a=i[o])&&(p[u[o]]=!(f[u[o]]=a))}}if(e){if(y||d){if(y){i=[],o=p.length;while(o--){(a=p[o])&&i.push(f[o]=a);}y(null,p=[],i,r)}o=p.length;while(o--){(a=p[o])&&-1<(i=y?P(e,a):s[o])&&(e[i]=!(t[i]=a))}}}else {p=Te(p===t?p.splice(l,p.length):p),y?y(null,t,p,r):H.apply(t,p)}})}function Ee(e){for(var i,t,n,r=e.length,o=b.relative[e[0].type],a=o||b.relative[" "],s=o?1:0,u=be((e)=> {return e===i},a,!0),l=be((e)=> {return-1<P(i,e)},a,!0),c=[function(e,t,n){const r=!o&&(n||t!==w)||((i=t).nodeType?u(e,t,n):l(e,t,n));

return i=null,r}];s<r;s++){if(t=b.relative[e[s].type]){c=[be(we(c),t)];}else{if((t=b.filter[e[s].type].apply(null,e[s].matches))[S]){for(n=++s;n<r;n++){if(b.relative[e[n].type]){break;}}

return Ce(1<s&&we(c),1<s&&xe(e.slice(0,s-1).concat({value:" "===e[s-2].type?"*":""})).replace($,"$1"),t,s<n&&Ee(e.slice(s,n)),n<r&&Ee(e=e.slice(n)),n<r&&xe(e))}c.push(t)}}

return we(c)}

return me.prototype=b.filters=b.pseudos,b.setFilters=new me,h=se.tokenize=function(e,t){let n; let r; let i; let o; let a; let s; let u; const l=x[e+" "];if(l){return t?0:l.slice(0);}a=e,s=[],u=b.preFilter;while(a){for(o in n&&!(r=_.exec(a))||(r&&(a=a.slice(r[0].length)||a),s.push(i=[])),n=!1,(r=z.exec(a))&&(n=r.shift(),i.push({value:n,type:r[0].replace($," ")}),a=a.slice(n.length)),b.filter){!(r=G[o].exec(a))||u[o]&&!(r=u[o](r))||(n=r.shift(),i.push({value:n,type:o,matches:r}),a=a.slice(n.length));}if(!n){break}}

return t?a.length:a?se.error(e):x(e,s).slice(0)},f=se.compile=function(e,t){let n; let v; let y; let m; let x; let r; const i=[]; const o=[]; let a=A[e+" "];if(!a){t||(t=h(e)),n=t.length;while(n--){(a=Ee(t[n]))[S]?i.push(a):o.push(a);}(a=A(e,(v=o,m=!!(y=i).length,x=!!v.length,r=function(e,t,n,r,i){let o; let a; let s; let u=0; let l="0"; const c=e&&[]; let f=[]; const p=w; const d=e||x&&b.find.TAG("*",i); const h=k+=null==p?1:Math.random()||.1; const g=d.length;for(i&&(w=t==C||t||i);l!==g&&null!=(o=d[l]);l++){if(x&&o){a=0,t||o.ownerDocument==C||(T(o),n=!E);while(s=v[a++]){if(s(o,t||C,n)){r.push(o);break}}i&&(k=h)}m&&((o=!s&&o)&&u--,e&&c.push(o))}if(u+=l,m&&l!==u){a=0;while(s=y[a++]){s(c,f,t,n);}if(e){if(0<u){while(l--){c[l]||f[l]||(f[l]=q.call(r));}}f=Te(f)}H.apply(r,f),i&&!e&&!!f.length&&1<u+y.length&&se.uniqueSort(r)}

return i&&(k=h,w=p),c},m?le(r):r))).selector=e}

return a},g=se.select=function(e,t,n,r){let i; let o; let a; let s; let u; const l="function"===typeof e&&e; const c=!r&&h(e=l.selector||e);if(n=n||[],1===c.length){if(2<(o=c[0]=c[0].slice(0)).length&&"ID"===(a=o[0]).type&&9===t.nodeType&&E&&b.relative[o[1].type]){if(!(t=(b.find.ID(a.matches[0].replace(te,ne),t)||[])[0])){return n;}l&&(t=t.parentNode),e=e.slice(o.shift().value.length)}i=G.needsContext.test(e)?0:o.length;while(i--){if(a=o[i],b.relative[s=a.type]){break;}if((u=b.find[s])&&(r=u(a.matches[0].replace(te,ne),ee.test(o[0].type)&&ye(t.parentNode)||t))){if(o.splice(i,1),!(e=r.length&&xe(o))){return H.apply(n,r),n;}break}}}

return(l||f(e,c))(r,t,!E,n,!t||ee.test(e)&&ye(t.parentNode)||t),n},d.sortStable=S.split("").sort(D).join("")===S,d.detectDuplicates=!!l,T(),d.sortDetached=ce((e)=> {return 1&e.compareDocumentPosition(C.createElement("fieldset"))}),ce((e)=> {return e.innerHTML="<a href='#'></a>","#"===e.firstChild.getAttribute("href")})||fe("type|href|height|width",(e,t,n)=> {if(!n){return e.getAttribute(t,"type"===t.toLowerCase()?1:2)}}),d.attributes&&ce((e)=> {return e.innerHTML="<input/>",e.firstChild.setAttribute("value",""),""===e.firstChild.getAttribute("value")})||fe("value",(e,t,n)=> {if(!n&&"input"===e.nodeName.toLowerCase()){return e.defaultValue}}),ce((e)=> {return null==e.getAttribute("disabled")})||fe(R,(e,t,n)=> {let r;if(!n){return!0===e[t]?t.toLowerCase():(r=e.getAttributeNode(t))&&r.specified?r.value:null}}),se}(C);S.find=d,S.expr=d.selectors,S.expr[":"]=S.expr.pseudos,S.uniqueSort=S.unique=d.uniqueSort,S.text=d.getText,S.isXMLDoc=d.isXML,S.contains=d.contains,S.escapeSelector=d.escape;const h=function(e,t,n){const r=[]; const i=void 0!==n;while((e=e[t])&&9!==e.nodeType){if(1===e.nodeType){if(i&&S(e).is(n)){break;}r.push(e)}}

return r}; const T=function(e,t){for(var n=[];e;e=e.nextSibling){1===e.nodeType&&e!==t&&n.push(e);}

return n}; const k=S.expr.match.needsContext;function A(e,t){return e.nodeName&&e.nodeName.toLowerCase()===t.toLowerCase()}const N=/^<([a-z][^\/\0>:\x20\t\r\n\f]*)[\x20\t\r\n\f]*\/?>(?:<\/\1>|)$/i;function D(e,n,r){return m(n)?S.grep(e,(e,t)=> {return!!n.call(e,t,e)!==r}):n.nodeType?S.grep(e,(e)=> {return e===n!==r}):"string"!==typeof n?S.grep(e,(e)=> {return-1<i.call(n,e)!==r}):S.filter(n,e,r)}S.filter=function(e,t,n){const r=t[0];

return n&&(e=":not("+e+")"),1===t.length&&1===r.nodeType?S.find.matchesSelector(r,e)?[r]:[]:S.find.matches(e,S.grep(t,(e)=> {return 1===e.nodeType}))},S.fn.extend({find(e){let t; let n; const r=this.length; const i=this;if("string"!==typeof e){return this.pushStack(S(e).filter(function(){for(t=0;t<r;t++){if(S.contains(i[t],this)){return!0}}}));}for(n=this.pushStack([]),t=0;t<r;t++){S.find(e,i[t],n);}

return 1<r?S.uniqueSort(n):n},filter(e){return this.pushStack(D(this,e||[],!1))},not(e){return this.pushStack(D(this,e||[],!0))},is(e){return!!D(this,"string"===typeof e&&k.test(e)?S(e):e||[],!1).length}});let j; const q=/^(?:\s*(<[\w\W]+>)[^>]*|#([\w-]+))$/;(S.fn.init=function(e,t,n){let r; let i;if(!e){return this;}if(n=n||j,"string"===typeof e){if(!(r="<"===e[0]&&">"===e[e.length-1]&&3<=e.length?[null,e,null]:q.exec(e))||!r[1]&&t){return!t||t.jquery?(t||n).find(e):this.constructor(t).find(e);}if(r[1]){if(t=t instanceof S?t[0]:t,S.merge(this,S.parseHTML(r[1],t&&t.nodeType?t.ownerDocument||t:E,!0)),N.test(r[1])&&S.isPlainObject(t)){for(r in t){m(this[r])?this[r](t[r]):this.attr(r,t[r]);}}

return this}

return(i=E.getElementById(r[2]))&&(this[0]=i,this.length=1),this}

return e.nodeType?(this[0]=e,this.length=1,this):m(e)?void 0!==n.ready?n.ready(e):e(S):S.makeArray(e,this)}).prototype=S.fn,j=S(E);const L=/^(?:parents|prev(?:Until|All))/; const H={children:!0,contents:!0,next:!0,prev:!0};function O(e,t){while((e=e[t])&&1!==e.nodeType){;}

return e}S.fn.extend({has(e){const t=S(e,this); const n=t.length;

return this.filter(function(){for(let e=0;e<n;e++){if(S.contains(this,t[e])){return!0}}})},closest(e,t){let n; let r=0; const i=this.length; const o=[]; const a="string"!==typeof e&&S(e);if(!k.test(e)){for(;r<i;r++){for(n=this[r];n&&n!==t;n=n.parentNode){if(n.nodeType<11&&(a?-1<a.index(n):1===n.nodeType&&S.find.matchesSelector(n,e))){o.push(n);break}}}}

return this.pushStack(1<o.length?S.uniqueSort(o):o)},index(e){return e?"string"===typeof e?i.call(S(e),this[0]):i.call(this,e.jquery?e[0]:e):this[0]&&this[0].parentNode?this.first().prevAll().length:-1},add(e,t){return this.pushStack(S.uniqueSort(S.merge(this.get(),S(e,t))))},addBack(e){return this.add(null==e?this.prevObject:this.prevObject.filter(e))}}),S.each({parent(e){const t=e.parentNode;

return t&&11!==t.nodeType?t:null},parents(e){return h(e,"parentNode")},parentsUntil(e,t,n){return h(e,"parentNode",n)},next(e){return O(e,"nextSibling")},prev(e){return O(e,"previousSibling")},nextAll(e){return h(e,"nextSibling")},prevAll(e){return h(e,"previousSibling")},nextUntil(e,t,n){return h(e,"nextSibling",n)},prevUntil(e,t,n){return h(e,"previousSibling",n)},siblings(e){return T((e.parentNode||{}).firstChild,e)},children(e){return T(e.firstChild)},contents(e){return null!=e.contentDocument&&r(e.contentDocument)?e.contentDocument:(A(e,"template")&&(e=e.content||e),S.merge([],e.childNodes))}},(r,i)=> {S.fn[r]=function(e,t){let n=S.map(this,i,e);

return"Until"!==r.slice(-5)&&(t=e),t&&"string"===typeof t&&(n=S.filter(t,n)),1<this.length&&(H[r]||S.uniqueSort(n),L.test(r)&&n.reverse()),this.pushStack(n)}});const P=/[^\x20\t\r\n\f]+/g;function R(e){return e}function M(e){throw e}function I(e,t,n,r){let i;try{e&&m(i=e.promise)?i.call(e).done(t).fail(n):e&&m(i=e.then)?i.call(e,t,n):t.apply(void 0,[e].slice(r))}catch(e){n.apply(void 0,[e])}}S.Callbacks=function(r){let e; let n;r="string"===typeof r?(e=r,n={},S.each(e.match(P)||[],(e,t)=> {n[t]=!0}),n):S.extend({},r);let i; let t; let o; let a; let s=[]; let u=[]; let l=-1; const c=function(){for(a=a||r.once,o=i=!0;u.length;l=-1){t=u.shift();while(++l<s.length){!1===s[l].apply(t[0],t[1])&&r.stopOnFalse&&(l=s.length,t=!1)}}r.memory||(t=!1),i=!1,a&&(s=t?[]:"")}; var f={add(){return s&&(t&&!i&&(l=s.length-1,u.push(t)),function n(e){S.each(e,(e,t)=> {m(t)?r.unique&&f.has(t)||s.push(t):t&&t.length&&"string"!==w(t)&&n(t)})}(arguments),t&&!i&&c()),this},remove(){return S.each(arguments,(e,t)=> {let n;while(-1<(n=S.inArray(t,s,n))){s.splice(n,1),n<=l&&l--}}),this},has(e){return e?-1<S.inArray(e,s):!!s.length},empty(){return s&&(s=[]),this},disable(){return a=u=[],s=t="",this},disabled(){return!s},lock(){return a=u=[],t||i||(s=t=""),this},locked(){return!!a},fireWith(e,t){return a||(t=[e,(t=t||[]).slice?t.slice():t],u.push(t),i||c()),this},fire(){return f.fireWith(this,arguments),this},fired(){return!!o}};

return f},S.extend({Deferred(e){const o=[["notify","progress",S.Callbacks("memory"),S.Callbacks("memory"),2],["resolve","done",S.Callbacks("once memory"),S.Callbacks("once memory"),0,"resolved"],["reject","fail",S.Callbacks("once memory"),S.Callbacks("once memory"),1,"rejected"]]; let i="pending"; var a={state(){return i},always(){return s.done(arguments).fail(arguments),this},catch(e){return a.then(null,e)},pipe(){let i=arguments;

return S.Deferred((r)=> {S.each(o,(e,t)=> {const n=m(i[t[4]])&&i[t[4]];s[t[1]](function(){const e=n&&n.apply(this,arguments);e&&m(e.promise)?e.promise().progress(r.notify).done(r.resolve).fail(r.reject):r[t[0]+"With"](this,n?[e]:arguments)})}),i=null}).promise()},then(t,n,r){let u=0;function l(i,o,a,s){return function(){let n=this; let r=arguments; const e=function(){let e; let t;if(!(i<u)){if((e=a.apply(n,r))===o.promise()){throw new TypeError("Thenable self-resolution");}t=e&&("object"===typeof e||"function"===typeof e)&&e.then,m(t)?s?t.call(e,l(u,o,R,s),l(u,o,M,s)):(u++,t.call(e,l(u,o,R,s),l(u,o,M,s),l(u,o,R,o.notifyWith))):(a!==R&&(n=void 0,r=[e]),(s||o.resolveWith)(n,r))}}; var t=s?e:function(){try{e()}catch(e){S.Deferred.exceptionHook&&S.Deferred.exceptionHook(e,t.stackTrace),u<=i+1&&(a!==M&&(n=void 0,r=[e]),o.rejectWith(n,r))}};i?t():(S.Deferred.getStackHook&&(t.stackTrace=S.Deferred.getStackHook()),C.setTimeout(t))}}

return S.Deferred((e)=> {o[0][3].add(l(0,e,m(r)?r:R,e.notifyWith)),o[1][3].add(l(0,e,m(t)?t:R)),o[2][3].add(l(0,e,m(n)?n:M))}).promise()},promise(e){return null!=e?S.extend(e,a):a}}; var s={};

return S.each(o,(e,t)=> {const n=t[2]; const r=t[5];a[t[1]]=n.add,r&&n.add(()=> {i=r},o[3-e][2].disable,o[3-e][3].disable,o[0][2].lock,o[0][3].lock),n.add(t[3].fire),s[t[0]]=function(){return s[t[0]+"With"](this===s?void 0:this,arguments),this},s[t[0]+"With"]=n.fireWith}),a.promise(s),e&&e.call(s,s),s},when(e){let n=arguments.length; let t=n; const r=Array(t); const i=s.call(arguments); const o=S.Deferred(); const a=function(t){return function(e){r[t]=this,i[t]=1<arguments.length?s.call(arguments):e,--n||o.resolveWith(r,i)}};if(n<=1&&(I(e,o.done(a(t)).resolve,o.reject,!n),"pending"===o.state()||m(i[t]&&i[t].then))){return o.then();}while(t--){I(i[t],a(t),o.reject);}

return o.promise()}});const W=/^(Eval|Internal|Range|Reference|Syntax|Type|URI)Error$/;S.Deferred.exceptionHook=function(e,t){C.console&&C.console.warn&&e&&W.test(e.name)&&C.console.warn("jQuery.Deferred exception: "+e.message,e.stack,t)},S.readyException=function(e){C.setTimeout(()=> {throw e})};const F=S.Deferred();function B(){E.removeEventListener("DOMContentLoaded",B),C.removeEventListener("load",B),S.ready()}S.fn.ready=function(e){return F.then(e)["catch"]((e)=> {S.readyException(e)}),this},S.extend({isReady:!1,readyWait:1,ready(e){(!0===e?--S.readyWait:S.isReady)||(S.isReady=!0)!==e&&0<--S.readyWait||F.resolveWith(E,[S])}}),S.ready.then=F.then,"complete"===E.readyState||"loading"!==E.readyState&&!E.documentElement.doScroll?C.setTimeout(S.ready):(E.addEventListener("DOMContentLoaded",B),C.addEventListener("load",B));var $=function(e,t,n,r,i,o,a){let s=0; const u=e.length; let l=null==n;if("object"===w(n)){for(s in i=!0,n){$(e,t,s,n[s],!0,o,a);}}else if(void 0!==r&&(i=!0,m(r)||(a=!0),l&&(a?(t.call(e,r),t=null):(l=t,t=function(e,t,n){return l.call(S(e),n)})),t)){for(;s<u;s++){t(e[s],n,a?r:r.call(e[s],s,t(e[s],n)));}}

return i?e:l?t.call(e):u?t(e[0],n):o}; const _=/^-ms-/; const z=/-([a-z])/g;function U(e,t){return t.toUpperCase()}function X(e){return e.replace(_,"ms-").replace(z,U)}const V=function(e){return 1===e.nodeType||9===e.nodeType||!+e.nodeType};function G(){this.expando=S.expando+G.uid++}G.uid=1,G.prototype={cache(e){let t=e[this.expando];

return t||(t={},V(e)&&(e.nodeType?e[this.expando]=t:Object.defineProperty(e,this.expando,{value:t,configurable:!0}))),t},set(e,t,n){let r; const i=this.cache(e);if("string"===typeof t){i[X(t)]=n;}else {for(r in t){i[X(r)]=t[r];}}

return i},get(e,t){return void 0===t?this.cache(e):e[this.expando]&&e[this.expando][X(t)]},access(e,t,n){return void 0===t||t&&"string"===typeof t&&void 0===n?this.get(e,t):(this.set(e,t,n),void 0!==n?n:t)},remove(e,t){let n; const r=e[this.expando];if(void 0!==r){if(void 0!==t){n=(t=Array.isArray(t)?t.map(X):(t=X(t))in r?[t]:t.match(P)||[]).length;while(n--){delete r[t[n]]}}(void 0===t||S.isEmptyObject(r))&&(e.nodeType?e[this.expando]=void 0:delete e[this.expando])}},hasData(e){const t=e[this.expando];

return void 0!==t&&!S.isEmptyObject(t)}};const Y=new G; const Q=new G; const J=/^(?:\{[\w\W]*\}|\[[\w\W]*\])$/; const K=/[A-Z]/g;function Z(e,t,n){let r; let i;if(void 0===n&&1===e.nodeType){if(r="data-"+t.replace(K,"-$&").toLowerCase(),"string"===typeof(n=e.getAttribute(r))){try{n="true"===(i=n)||"false"!==i&&("null"===i?null:i===+i+""?+i:J.test(i)?JSON.parse(i):i)}catch(e){}Q.set(e,t,n)}else {n=void 0;}}

return n}S.extend({hasData(e){return Q.hasData(e)||Y.hasData(e)},data(e,t,n){return Q.access(e,t,n)},removeData(e,t){Q.remove(e,t)},_data(e,t,n){return Y.access(e,t,n)},_removeData(e,t){Y.remove(e,t)}}),S.fn.extend({data(n,e){let t; let r; let i; const o=this[0]; const a=o&&o.attributes;if(void 0===n){if(this.length&&(i=Q.get(o),1===o.nodeType&&!Y.get(o,"hasDataAttrs"))){t=a.length;while(t--){a[t]&&0===(r=a[t].name).indexOf("data-")&&(r=X(r.slice(5)),Z(o,r,i[r]));}Y.set(o,"hasDataAttrs",!0)}

return i}

return"object"===typeof n?this.each(function(){Q.set(this,n)}):$(this,function(e){let t;if(o&&void 0===e){return void 0!==(t=Q.get(o,n))?t:void 0!==(t=Z(o,n))?t:void 0;}this.each(function(){Q.set(this,n,e)})},null,e,1<arguments.length,null,!0)},removeData(e){return this.each(function(){Q.remove(this,e)})}}),S.extend({queue(e,t,n){let r;if(e){return t=(t||"fx")+"queue",r=Y.get(e,t),n&&(!r||Array.isArray(n)?r=Y.access(e,t,S.makeArray(n)):r.push(n)),r||[]}},dequeue(e,t){t=t||"fx";const n=S.queue(e,t); let r=n.length; let i=n.shift(); const o=S._queueHooks(e,t);"inprogress"===i&&(i=n.shift(),r--),i&&("fx"===t&&n.unshift("inprogress"),delete o.stop,i.call(e,()=> {S.dequeue(e,t)},o)),!r&&o&&o.empty.fire()},_queueHooks(e,t){const n=t+"queueHooks";

return Y.get(e,n)||Y.access(e,n,{empty:S.Callbacks("once memory").add(()=> {Y.remove(e,[t+"queue",n])})})}}),S.fn.extend({queue(t,n){let e=2;

return"string"!==typeof t&&(n=t,t="fx",e--),arguments.length<e?S.queue(this[0],t):void 0===n?this:this.each(function(){const e=S.queue(this,t,n);S._queueHooks(this,t),"fx"===t&&"inprogress"!==e[0]&&S.dequeue(this,t)})},dequeue(e){return this.each(function(){S.dequeue(this,e)})},clearQueue(e){return this.queue(e||"fx",[])},promise(e,t){let n; let r=1; const i=S.Deferred(); const o=this; let a=this.length; const s=function(){--r||i.resolveWith(o,[o])};"string"!==typeof e&&(t=e,e=void 0),e=e||"fx";while(a--){(n=Y.get(o[a],e+"queueHooks"))&&n.empty&&(r++,n.empty.add(s));}

return s(),i.promise(t)}});const ee=/[+-]?(?:\d*\.|)\d+(?:[eE][+-]?\d+|)/.source; const te=new RegExp("^(?:([+-])=|)("+ee+")([a-z%]*)$","i"); const ne=["Top","Right","Bottom","Left"]; const re=E.documentElement; let ie=function(e){return S.contains(e.ownerDocument,e)}; const oe={composed:!0};re.getRootNode&&(ie=function(e){return S.contains(e.ownerDocument,e)||e.getRootNode(oe)===e.ownerDocument});const ae=function(e,t){return"none"===(e=t||e).style.display||""===e.style.display&&ie(e)&&"none"===S.css(e,"display")};function se(e,t,n,r){let i; let o; let a=20; const s=r?function(){return r.cur()}:function(){return S.css(e,t,"")}; let u=s(); let l=n&&n[3]||(S.cssNumber[t]?"":"px"); let c=e.nodeType&&(S.cssNumber[t]||"px"!==l&&+u)&&te.exec(S.css(e,t));if(c&&c[3]!==l){u/=2,l=l||c[3],c=+u||1;while(a--){S.style(e,t,c+l),(1-o)*(1-(o=s()/u||.5))<=0&&(a=0),c/=o;}c*=2,S.style(e,t,c+l),n=n||[]}

return n&&(c=+c||+u||0,i=n[1]?c+(n[1]+1)*n[2]:+n[2],r&&(r.unit=l,r.start=c,r.end=i)),i}const ue={};function le(e,t){for(var n,r,i,o,a,s,u,l=[],c=0,f=e.length;c<f;c++){(r=e[c]).style&&(n=r.style.display,t?("none"===n&&(l[c]=Y.get(r,"display")||null,l[c]||(r.style.display="")),""===r.style.display&&ae(r)&&(l[c]=(u=a=o=void 0,a=(i=r).ownerDocument,s=i.nodeName,(u=ue[s])||(o=a.body.appendChild(a.createElement(s)),u=S.css(o,"display"),o.parentNode.removeChild(o),"none"===u&&(u="block"),ue[s]=u)))):"none"!==n&&(l[c]="none",Y.set(r,"display",n)));}for(c=0;c<f;c++){null!=l[c]&&(e[c].style.display=l[c]);}

return e}S.fn.extend({show(){return le(this,!0)},hide(){return le(this)},toggle(e){return"boolean"===typeof e?e?this.show():this.hide():this.each(function(){ae(this)?S(this).show():S(this).hide()})}});let ce; let fe; const pe=/^(?:checkbox|radio)$/i; const de=/<([a-z][^\/\0>\x20\t\r\n\f]*)/i; const he=/^$|^module$|\/(?:java|ecma)script/i;ce=E.createDocumentFragment().appendChild(E.createElement("div")),(fe=E.createElement("input")).setAttribute("type","radio"),fe.setAttribute("checked","checked"),fe.setAttribute("name","t"),ce.appendChild(fe),y.checkClone=ce.cloneNode(!0).cloneNode(!0).lastChild.checked,ce.innerHTML="<textarea>x</textarea>",y.noCloneChecked=!!ce.cloneNode(!0).lastChild.defaultValue,ce.innerHTML="<option></option>",y.option=!!ce.lastChild;const ge={thead:[1,"<table>","</table>"],col:[2,"<table><colgroup>","</colgroup></table>"],tr:[2,"<table><tbody>","</tbody></table>"],td:[3,"<table><tbody><tr>","</tr></tbody></table>"],_default:[0,"",""]};function ve(e,t){let n;

return n="undefined"!==typeof e.getElementsByTagName?e.getElementsByTagName(t||"*"):"undefined"!==typeof e.querySelectorAll?e.querySelectorAll(t||"*"):[],void 0===t||t&&A(e,t)?S.merge([e],n):n}function ye(e,t){for(let n=0,r=e.length;n<r;n++){Y.set(e[n],"globalEval",!t||Y.get(t[n],"globalEval"))}}ge.tbody=ge.tfoot=ge.colgroup=ge.caption=ge.thead,ge.th=ge.td,y.option||(ge.optgroup=ge.option=[1,"<select multiple='multiple'>","</select>"]);const me=/<|&#?\w+;/;function xe(e,t,n,r,i){for(var o,a,s,u,l,c,f=t.createDocumentFragment(),p=[],d=0,h=e.length;d<h;d++){if((o=e[d])||0===o){if("object"===w(o)){S.merge(p,o.nodeType?[o]:o);}else if(me.test(o)){a=a||f.appendChild(t.createElement("div")),s=(de.exec(o)||["",""])[1].toLowerCase(),u=ge[s]||ge._default,a.innerHTML=u[1]+S.htmlPrefilter(o)+u[2],c=u[0];while(c--){a=a.lastChild;}S.merge(p,a.childNodes),(a=f.firstChild).textContent=""}else {p.push(t.createTextNode(o));}}}f.textContent="",d=0;while(o=p[d++]){if(r&&-1<S.inArray(o,r)){i&&i.push(o);}else if(l=ie(o),a=ve(f.appendChild(o),"script"),l&&ye(a),n){c=0;while(o=a[c++]){he.test(o.type||"")&&n.push(o)}}}

return f}const be=/^key/; const we=/^(?:mouse|pointer|contextmenu|drag|drop)|click/; const Te=/^([^.]*)(?:\.(.+)|)/;function Ce(){return!0}function Ee(){return!1}function Se(e,t){return e===function(){try{return E.activeElement}catch(e){}}()==("focus"===t)}function ke(e,t,n,r,i,o){let a; let s;if("object"===typeof t){for(s in"string"!==typeof n&&(r=r||n,n=void 0),t){ke(e,s,n,r,t[s],o);}

return e}if(null==r&&null==i?(i=n,r=n=void 0):null==i&&("string"===typeof n?(i=r,r=void 0):(i=r,r=n,n=void 0)),!1===i){i=Ee;}else if(!i){return e;}

return 1===o&&(a=i,(i=function(e){return S().off(e),a.apply(this,arguments)}).guid=a.guid||(a.guid=S.guid++)),e.each(function(){S.event.add(this,t,i,r,n)})}function Ae(e,i,o){o?(Y.set(e,i,!1),S.event.add(e,i,{namespace:!1,handler(e){let t; let n; let r=Y.get(this,i);if(1&e.isTrigger&&this[i]){if(r.length){(S.event.special[i]||{}).delegateType&&e.stopPropagation();}else if(r=s.call(arguments),Y.set(this,i,r),t=o(this,i),this[i](),r!==(n=Y.get(this,i))||t?Y.set(this,i,!1):n={},r!==n){return e.stopImmediatePropagation(),e.preventDefault(),n.value}}else {r.length&&(Y.set(this,i,{value:S.event.trigger(S.extend(r[0],S.Event.prototype),r.slice(1),this)}),e.stopImmediatePropagation())}}})):void 0===Y.get(e,i)&&S.event.add(e,i,Ce)}S.event={global:{},add(t,e,n,r,i){let o; let a; let s; let u; let l; let c; let f; let p; let d; let h; let g; const v=Y.get(t);if(V(t)){n.handler&&(n=(o=n).handler,i=o.selector),i&&S.find.matchesSelector(re,i),n.guid||(n.guid=S.guid++),(u=v.events)||(u=v.events=Object.create(null)),(a=v.handle)||(a=v.handle=function(e){return"undefined"!==typeof S&&S.event.triggered!==e.type?S.event.dispatch.apply(t,arguments):void 0}),l=(e=(e||"").match(P)||[""]).length;while(l--){d=g=(s=Te.exec(e[l])||[])[1],h=(s[2]||"").split(".").sort(),d&&(f=S.event.special[d]||{},d=(i?f.delegateType:f.bindType)||d,f=S.event.special[d]||{},c=S.extend({type:d,origType:g,data:r,handler:n,guid:n.guid,selector:i,needsContext:i&&S.expr.match.needsContext.test(i),namespace:h.join(".")},o),(p=u[d])||((p=u[d]=[]).delegateCount=0,f.setup&&!1!==f.setup.call(t,r,h,a)||t.addEventListener&&t.addEventListener(d,a)),f.add&&(f.add.call(t,c),c.handler.guid||(c.handler.guid=n.guid)),i?p.splice(p.delegateCount++,0,c):p.push(c),S.event.global[d]=!0)}}},remove(e,t,n,r,i){let o; let a; let s; let u; let l; let c; let f; let p; let d; let h; let g; const v=Y.hasData(e)&&Y.get(e);if(v&&(u=v.events)){l=(t=(t||"").match(P)||[""]).length;while(l--){if(d=g=(s=Te.exec(t[l])||[])[1],h=(s[2]||"").split(".").sort(),d){f=S.event.special[d]||{},p=u[d=(r?f.delegateType:f.bindType)||d]||[],s=s[2]&&new RegExp("(^|\\.)"+h.join("\\.(?:.*\\.|)")+"(\\.|$)"),a=o=p.length;while(o--){c=p[o],!i&&g!==c.origType||n&&n.guid!==c.guid||s&&!s.test(c.namespace)||r&&r!==c.selector&&("**"!==r||!c.selector)||(p.splice(o,1),c.selector&&p.delegateCount--,f.remove&&f.remove.call(e,c));}a&&!p.length&&(f.teardown&&!1!==f.teardown.call(e,h,v.handle)||S.removeEvent(e,d,v.handle),delete u[d])}else {for(d in u){S.event.remove(e,d+t[l],n,r,!0);}}}S.isEmptyObject(u)&&Y.remove(e,"handle events")}},dispatch(e){let t; let n; let r; let i; let o; let a; const s=new Array(arguments.length); const u=S.event.fix(e); const l=(Y.get(this,"events")||Object.create(null))[u.type]||[]; const c=S.event.special[u.type]||{};for(s[0]=u,t=1;t<arguments.length;t++){s[t]=arguments[t];}if(u.delegateTarget=this,!c.preDispatch||!1!==c.preDispatch.call(this,u)){a=S.event.handlers.call(this,u,l),t=0;while((i=a[t++])&&!u.isPropagationStopped()){u.currentTarget=i.elem,n=0;while((o=i.handlers[n++])&&!u.isImmediatePropagationStopped()){u.rnamespace&&!1!==o.namespace&&!u.rnamespace.test(o.namespace)||(u.handleObj=o,u.data=o.data,void 0!==(r=((S.event.special[o.origType]||{}).handle||o.handler).apply(i.elem,s))&&!1===(u.result=r)&&(u.preventDefault(),u.stopPropagation()))}}

return c.postDispatch&&c.postDispatch.call(this,u),u.result}},handlers(e,t){let n; let r; let i; let o; let a; const s=[]; const u=t.delegateCount; let l=e.target;if(u&&l.nodeType&&!("click"===e.type&&1<=e.button)){for(;l!==this;l=l.parentNode||this){if(1===l.nodeType&&("click"!==e.type||!0!==l.disabled)){for(o=[],a={},n=0;n<u;n++){void 0===a[i=(r=t[n]).selector+" "]&&(a[i]=r.needsContext?-1<S(i,this).index(l):S.find(i,this,null,[l]).length),a[i]&&o.push(r);}o.length&&s.push({elem:l,handlers:o})}}}

return l=this,u<t.length&&s.push({elem:l,handlers:t.slice(u)}),s},addProp(t,e){Object.defineProperty(S.Event.prototype,t,{enumerable:!0,configurable:!0,get:m(e)?function(){if(this.originalEvent){return e(this.originalEvent)}}:function(){if(this.originalEvent){return this.originalEvent[t]}},set(e){Object.defineProperty(this,t,{enumerable:!0,configurable:!0,writable:!0,value:e})}})},fix(e){return e[S.expando]?e:new S.Event(e)},special:{load:{noBubble:!0},click:{setup(e){const t=this||e;

return pe.test(t.type)&&t.click&&A(t,"input")&&Ae(t,"click",Ce),!1},trigger(e){const t=this||e;

return pe.test(t.type)&&t.click&&A(t,"input")&&Ae(t,"click"),!0},_default(e){const t=e.target;

return pe.test(t.type)&&t.click&&A(t,"input")&&Y.get(t,"click")||A(t,"a")}},beforeunload:{postDispatch(e){void 0!==e.result&&e.originalEvent&&(e.originalEvent.returnValue=e.result)}}}},S.removeEvent=function(e,t,n){e.removeEventListener&&e.removeEventListener(t,n)},S.Event=function(e,t){if(!(this instanceof S.Event)){return new S.Event(e,t);}e&&e.type?(this.originalEvent=e,this.type=e.type,this.isDefaultPrevented=e.defaultPrevented||void 0===e.defaultPrevented&&!1===e.returnValue?Ce:Ee,this.target=e.target&&3===e.target.nodeType?e.target.parentNode:e.target,this.currentTarget=e.currentTarget,this.relatedTarget=e.relatedTarget):this.type=e,t&&S.extend(this,t),this.timeStamp=e&&e.timeStamp||Date.now(),this[S.expando]=!0},S.Event.prototype={constructor:S.Event,isDefaultPrevented:Ee,isPropagationStopped:Ee,isImmediatePropagationStopped:Ee,isSimulated:!1,preventDefault(){const e=this.originalEvent;this.isDefaultPrevented=Ce,e&&!this.isSimulated&&e.preventDefault()},stopPropagation(){const e=this.originalEvent;this.isPropagationStopped=Ce,e&&!this.isSimulated&&e.stopPropagation()},stopImmediatePropagation(){const e=this.originalEvent;this.isImmediatePropagationStopped=Ce,e&&!this.isSimulated&&e.stopImmediatePropagation(),this.stopPropagation()}},S.each({altKey:!0,bubbles:!0,cancelable:!0,changedTouches:!0,ctrlKey:!0,detail:!0,eventPhase:!0,metaKey:!0,pageX:!0,pageY:!0,shiftKey:!0,view:!0,char:!0,code:!0,charCode:!0,key:!0,keyCode:!0,button:!0,buttons:!0,clientX:!0,clientY:!0,offsetX:!0,offsetY:!0,pointerId:!0,pointerType:!0,screenX:!0,screenY:!0,targetTouches:!0,toElement:!0,touches:!0,which(e){const t=e.button;

return null==e.which&&be.test(e.type)?null!=e.charCode?e.charCode:e.keyCode:!e.which&&void 0!==t&&we.test(e.type)?1&t?1:2&t?3:4&t?2:0:e.which}},S.event.addProp),S.each({focus:"focusin",blur:"focusout"},(e,t)=> {S.event.special[e]={setup(){return Ae(this,e,Se),!1},trigger(){return Ae(this,e),!0},delegateType:t}}),S.each({mouseenter:"mouseover",mouseleave:"mouseout",pointerenter:"pointerover",pointerleave:"pointerout"},(e,i)=> {S.event.special[e]={delegateType:i,bindType:i,handle(e){let t; const n=e.relatedTarget; const r=e.handleObj;

return n&&(n===this||S.contains(this,n))||(e.type=r.origType,t=r.handler.apply(this,arguments),e.type=i),t}}}),S.fn.extend({on(e,t,n,r){return ke(this,e,t,n,r)},one(e,t,n,r){return ke(this,e,t,n,r,1)},off(e,t,n){let r; let i;if(e&&e.preventDefault&&e.handleObj){return r=e.handleObj,S(e.delegateTarget).off(r.namespace?r.origType+"."+r.namespace:r.origType,r.selector,r.handler),this;}if("object"===typeof e){for(i in e){this.off(i,t,e[i]);}

return this}

return!1!==t&&"function"!==typeof t||(n=t,t=void 0),!1===n&&(n=Ee),this.each(function(){S.event.remove(this,e,n,t)})}});const Ne=/<script|<style|<link/i; const De=/checked\s*(?:[^=]|=\s*.checked.)/i; const je=/^\s*<!(?:\[CDATA\[|--)|(?:\]\]|--)>\s*$/g;function qe(e,t){return A(e,"table")&&A(11!==t.nodeType?t:t.firstChild,"tr")&&S(e).children("tbody")[0]||e}function Le(e){return e.type=(null!==e.getAttribute("type"))+"/"+e.type,e}function He(e){return"true/"===(e.type||"").slice(0,5)?e.type=e.type.slice(5):e.removeAttribute("type"),e}function Oe(e,t){let n; let r; let i; let o; let a; let s;if(1===t.nodeType){if(Y.hasData(e)&&(s=Y.get(e).events)){for(i in Y.remove(t,"handle events"),s){for(n=0,r=s[i].length;n<r;n++){S.event.add(t,i,s[i][n]);}}}Q.hasData(e)&&(o=Q.access(e),a=S.extend({},o),Q.set(t,a))}}function Pe(n,r,i,o){r=g(r);let e; let t; let a; let s; let u; let l; let c=0; const f=n.length; const p=f-1; const d=r[0]; const h=m(d);if(h||1<f&&"string"===typeof d&&!y.checkClone&&De.test(d)){return n.each(function(e){const t=n.eq(e);h&&(r[0]=d.call(this,e,t.html())),Pe(t,r,i,o)});}if(f&&(t=(e=xe(r,n[0].ownerDocument,!1,n,o)).firstChild,1===e.childNodes.length&&(e=t),t||o)){for(s=(a=S.map(ve(e,"script"),Le)).length;c<f;c++){u=e,c!==p&&(u=S.clone(u,!0,!0),s&&S.merge(a,ve(u,"script"))),i.call(n[c],u,c);}if(s){for(l=a[a.length-1].ownerDocument,S.map(a,He),c=0;c<s;c++){u=a[c],he.test(u.type||"")&&!Y.access(u,"globalEval")&&S.contains(l,u)&&(u.src&&"module"!==(u.type||"").toLowerCase()?S._evalUrl&&!u.noModule&&S._evalUrl(u.src,{nonce:u.nonce||u.getAttribute("nonce")},l):b(u.textContent.replace(je,""),u,l))}}}

return n}function Re(e,t,n){for(var r,i=t?S.filter(t,e):e,o=0;null!=(r=i[o]);o++){n||1!==r.nodeType||S.cleanData(ve(r)),r.parentNode&&(n&&ie(r)&&ye(ve(r,"script")),r.parentNode.removeChild(r));}

return e}S.extend({htmlPrefilter(e){return e},clone(e,t,n){let r; let i; let o; let a; let s; let u; let l; const c=e.cloneNode(!0); const f=ie(e);if(!(y.noCloneChecked||1!==e.nodeType&&11!==e.nodeType||S.isXMLDoc(e))){for(a=ve(c),r=0,i=(o=ve(e)).length;r<i;r++){s=o[r],u=a[r],void 0,"input"===(l=u.nodeName.toLowerCase())&&pe.test(s.type)?u.checked=s.checked:"input"!==l&&"textarea"!==l||(u.defaultValue=s.defaultValue);}}if(t){if(n){for(o=o||ve(e),a=a||ve(c),r=0,i=o.length;r<i;r++){Oe(o[r],a[r]);}}else {Oe(e,c);}}

return !!(a=ve(c,"script")).length&&ye(a,!f&&ve(e,"script")),c},cleanData(e){for(var t,n,r,i=S.event.special,o=0;void 0!==(n=e[o]);o++){if(V(n)){if(t=n[Y.expando]){if(t.events){for(r in t.events){i[r]?S.event.remove(n,r):S.removeEvent(n,r,t.handle);}}n[Y.expando]=void 0}n[Q.expando]&&(n[Q.expando]=void 0)}}}}),S.fn.extend({detach(e){return Re(this,e,!0)},remove(e){return Re(this,e)},text(e){return $(this,function(e){return void 0===e?S.text(this):this.empty().each(function(){1!==this.nodeType&&11!==this.nodeType&&9!==this.nodeType||(this.textContent=e)})},null,e,arguments.length)},append(){return Pe(this,arguments,function(e){1!==this.nodeType&&11!==this.nodeType&&9!==this.nodeType||qe(this,e).appendChild(e)})},prepend(){return Pe(this,arguments,function(e){if(1===this.nodeType||11===this.nodeType||9===this.nodeType){const t=qe(this,e);t.insertBefore(e,t.firstChild)}})},before(){return Pe(this,arguments,function(e){this.parentNode&&this.parentNode.insertBefore(e,this)})},after(){return Pe(this,arguments,function(e){this.parentNode&&this.parentNode.insertBefore(e,this.nextSibling)})},empty(){for(var e,t=0;null!=(e=this[t]);t++){1===e.nodeType&&(S.cleanData(ve(e,!1)),e.textContent="");}

return this},clone(e,t){return e=null!=e&&e,t=null==t?e:t,this.map(function(){return S.clone(this,e,t)})},html(e){return $(this,function(e){let t=this[0]||{}; let n=0; const r=this.length;if(void 0===e&&1===t.nodeType){return t.innerHTML;}if("string"===typeof e&&!Ne.test(e)&&!ge[(de.exec(e)||["",""])[1].toLowerCase()]){e=S.htmlPrefilter(e);try{for(;n<r;n++){1===(t=this[n]||{}).nodeType&&(S.cleanData(ve(t,!1)),t.innerHTML=e);}t=0}catch(e){}}t&&this.empty().append(e)},null,e,arguments.length)},replaceWith(){const n=[];

return Pe(this,arguments,function(e){const t=this.parentNode;S.inArray(this,n)<0&&(S.cleanData(ve(this)),t&&t.replaceChild(e,this))},n)}}),S.each({appendTo:"append",prependTo:"prepend",insertBefore:"before",insertAfter:"after",replaceAll:"replaceWith"},(e,a)=> {S.fn[e]=function(e){for(var t,n=[],r=S(e),i=r.length-1,o=0;o<=i;o++){t=o===i?this:this.clone(!0),S(r[o])[a](t),u.apply(n,t.get());}

return this.pushStack(n)}});const Me=new RegExp("^("+ee+")(?!px)[a-z%]+$","i"); const Ie=function(e){let t=e.ownerDocument.defaultView;

return t&&t.opener||(t=C),t.getComputedStyle(e)}; const We=function(e,t,n){let r; let i; const o={};for(i in t){o[i]=e.style[i],e.style[i]=t[i];}for(i in r=n.call(e),t){e.style[i]=o[i];}

return r}; const Fe=new RegExp(ne.join("|"),"i");function Be(e,t,n){let r; let i; let o; let a; const s=e.style;

return(n=n||Ie(e))&&(""!==(a=n.getPropertyValue(t)||n[t])||ie(e)||(a=S.style(e,t)),!y.pixelBoxStyles()&&Me.test(a)&&Fe.test(t)&&(r=s.width,i=s.minWidth,o=s.maxWidth,s.minWidth=s.maxWidth=s.width=a,a=n.width,s.width=r,s.minWidth=i,s.maxWidth=o)),void 0!==a?a+"":a}function $e(e,t){return{get(){if(!e()){return(this.get=t).apply(this,arguments);}delete this.get}}}!function(){function e(){if(l){u.style.cssText="position:absolute;left:-11111px;width:60px;margin-top:1px;padding:0;border:0",l.style.cssText="position:relative;display:block;box-sizing:border-box;overflow:scroll;margin:auto;border:1px;padding:1px;width:60%;top:1%",re.appendChild(u).appendChild(l);const e=C.getComputedStyle(l);n="1%"!==e.top,s=12===t(e.marginLeft),l.style.right="60%",o=36===t(e.right),r=36===t(e.width),l.style.position="absolute",i=12===t(l.offsetWidth/3),re.removeChild(u),l=null}}function t(e){return Math.round(parseFloat(e))}let n; let r; let i; let o; let a; let s; var u=E.createElement("div"); var l=E.createElement("div");l.style&&(l.style.backgroundClip="content-box",l.cloneNode(!0).style.backgroundClip="",y.clearCloneStyle="content-box"===l.style.backgroundClip,S.extend(y,{boxSizingReliable(){return e(),r},pixelBoxStyles(){return e(),o},pixelPosition(){return e(),n},reliableMarginLeft(){return e(),s},scrollboxSize(){return e(),i},reliableTrDimensions(){let e; let t; let n; let r;

return null==a&&(e=E.createElement("table"),t=E.createElement("tr"),n=E.createElement("div"),e.style.cssText="position:absolute;left:-11111px",t.style.height="1px",n.style.height="9px",re.appendChild(e).appendChild(t).appendChild(n),r=C.getComputedStyle(t),a=3<parseInt(r.height),re.removeChild(e)),a}}))}();const _e=["Webkit","Moz","ms"]; const ze=E.createElement("div").style; const Ue={};function Xe(e){const t=S.cssProps[e]||Ue[e];

return t||(e in ze?e:Ue[e]=function(e){const t=e[0].toUpperCase()+e.slice(1); let n=_e.length;while(n--){if((e=_e[n]+t)in ze){return e}}}(e)||e)}const Ve=/^(none|table(?!-c[ea]).+)/; const Ge=/^--/; const Ye={position:"absolute",visibility:"hidden",display:"block"}; const Qe={letterSpacing:"0",fontWeight:"400"};function Je(e,t,n){const r=te.exec(t);

return r?Math.max(0,r[2]-(n||0))+(r[3]||"px"):t}function Ke(e,t,n,r,i,o){let a="width"===t?1:0; let s=0; let u=0;if(n===(r?"border":"content")){return 0;}for(;a<4;a+=2){"margin"===n&&(u+=S.css(e,n+ne[a],!0,i)),r?("content"===n&&(u-=S.css(e,"padding"+ne[a],!0,i)),"margin"!==n&&(u-=S.css(e,"border"+ne[a]+"Width",!0,i))):(u+=S.css(e,"padding"+ne[a],!0,i),"padding"!==n?u+=S.css(e,"border"+ne[a]+"Width",!0,i):s+=S.css(e,"border"+ne[a]+"Width",!0,i));}

return!r&&0<=o&&(u+=Math.max(0,Math.ceil(e["offset"+t[0].toUpperCase()+t.slice(1)]-o-u-s-.5))||0),u}function Ze(e,t,n){const r=Ie(e); let i=(!y.boxSizingReliable()||n)&&"border-box"===S.css(e,"boxSizing",!1,r); let o=i; let a=Be(e,t,r); const s="offset"+t[0].toUpperCase()+t.slice(1);if(Me.test(a)){if(!n){return a;}a="auto"}

return(!y.boxSizingReliable()&&i||!y.reliableTrDimensions()&&A(e,"tr")||"auto"===a||!parseFloat(a)&&"inline"===S.css(e,"display",!1,r))&&e.getClientRects().length&&(i="border-box"===S.css(e,"boxSizing",!1,r),(o=s in e)&&(a=e[s])),(a=parseFloat(a)||0)+Ke(e,t,n||(i?"border":"content"),o,r,a)+"px"}function et(e,t,n,r,i){return new et.prototype.init(e,t,n,r,i)}S.extend({cssHooks:{opacity:{get(e,t){if(t){const n=Be(e,"opacity");

return""===n?"1":n}}}},cssNumber:{animationIterationCount:!0,columnCount:!0,fillOpacity:!0,flexGrow:!0,flexShrink:!0,fontWeight:!0,gridArea:!0,gridColumn:!0,gridColumnEnd:!0,gridColumnStart:!0,gridRow:!0,gridRowEnd:!0,gridRowStart:!0,lineHeight:!0,opacity:!0,order:!0,orphans:!0,widows:!0,zIndex:!0,zoom:!0},cssProps:{},style(e,t,n,r){if(e&&3!==e.nodeType&&8!==e.nodeType&&e.style){let i; let o; let a; const s=X(t); const u=Ge.test(t); const l=e.style;if(u||(t=Xe(s)),a=S.cssHooks[t]||S.cssHooks[s],void 0===n){return a&&"get"in a&&void 0!==(i=a.get(e,!1,r))?i:l[t];}"string"===(o=typeof n)&&(i=te.exec(n))&&i[1]&&(n=se(e,t,i),o="number"),null!=n&&n==n&&("number"!==o||u||(n+=i&&i[3]||(S.cssNumber[s]?"":"px")),y.clearCloneStyle||""!==n||0!==t.indexOf("background")||(l[t]="inherit"),a&&"set"in a&&void 0===(n=a.set(e,n,r))||(u?l.setProperty(t,n):l[t]=n))}},css(e,t,n,r){let i; let o; let a; const s=X(t);

return Ge.test(t)||(t=Xe(s)),(a=S.cssHooks[t]||S.cssHooks[s])&&"get"in a&&(i=a.get(e,!0,n)),void 0===i&&(i=Be(e,t,r)),"normal"===i&&t in Qe&&(i=Qe[t]),""===n||n?(o=parseFloat(i),!0===n||isFinite(o)?o||0:i):i}}),S.each(["height","width"],(e,u)=> {S.cssHooks[u]={get(e,t,n){if(t){return!Ve.test(S.css(e,"display"))||e.getClientRects().length&&e.getBoundingClientRect().width?Ze(e,u,n):We(e,Ye,()=> {return Ze(e,u,n)})}},set(e,t,n){let r; const i=Ie(e); const o=!y.scrollboxSize()&&"absolute"===i.position; const a=(o||n)&&"border-box"===S.css(e,"boxSizing",!1,i); let s=n?Ke(e,u,n,a,i):0;

return a&&o&&(s-=Math.ceil(e["offset"+u[0].toUpperCase()+u.slice(1)]-parseFloat(i[u])-Ke(e,u,"border",!1,i)-.5)),s&&(r=te.exec(t))&&"px"!==(r[3]||"px")&&(e.style[u]=t,t=S.css(e,u)),Je(0,t,s)}}}),S.cssHooks.marginLeft=$e(y.reliableMarginLeft,(e,t)=> {if(t){return(parseFloat(Be(e,"marginLeft"))||e.getBoundingClientRect().left-We(e,{marginLeft:0},()=> {return e.getBoundingClientRect().left}))+"px"}}),S.each({margin:"",padding:"",border:"Width"},(i,o)=> {S.cssHooks[i+o]={expand(e){for(var t=0,n={},r="string"===typeof e?e.split(" "):[e];t<4;t++){n[i+ne[t]+o]=r[t]||r[t-2]||r[0];}

return n}},"margin"!==i&&(S.cssHooks[i+o].set=Je)}),S.fn.extend({css(e,t){return $(this,(e,t,n)=> {let r; let i; const o={}; let a=0;if(Array.isArray(t)){for(r=Ie(e),i=t.length;a<i;a++){o[t[a]]=S.css(e,t[a],!1,r);}

return o}

return void 0!==n?S.style(e,t,n):S.css(e,t)},e,t,1<arguments.length)}}),((S.Tween=et).prototype={constructor:et,init(e,t,n,r,i,o){this.elem=e,this.prop=n,this.easing=i||S.easing._default,this.options=t,this.start=this.now=this.cur(),this.end=r,this.unit=o||(S.cssNumber[n]?"":"px")},cur(){const e=et.propHooks[this.prop];

return e&&e.get?e.get(this):et.propHooks._default.get(this)},run(e){let t; const n=et.propHooks[this.prop];

return this.options.duration?this.pos=t=S.easing[this.easing](e,this.options.duration*e,0,1,this.options.duration):this.pos=t=e,this.now=(this.end-this.start)*t+this.start,this.options.step&&this.options.step.call(this.elem,this.now,this),n&&n.set?n.set(this):et.propHooks._default.set(this),this}}).init.prototype=et.prototype,(et.propHooks={_default:{get(e){let t;

return 1!==e.elem.nodeType||null!=e.elem[e.prop]&&null==e.elem.style[e.prop]?e.elem[e.prop]:(t=S.css(e.elem,e.prop,""))&&"auto"!==t?t:0},set(e){S.fx.step[e.prop]?S.fx.step[e.prop](e):1!==e.elem.nodeType||!S.cssHooks[e.prop]&&null==e.elem.style[Xe(e.prop)]?e.elem[e.prop]=e.now:S.style(e.elem,e.prop,e.now+e.unit)}}}).scrollTop=et.propHooks.scrollLeft={set(e){e.elem.nodeType&&e.elem.parentNode&&(e.elem[e.prop]=e.now)}},S.easing={linear(e){return e},swing(e){return.5-Math.cos(e*Math.PI)/2},_default:"swing"},S.fx=et.prototype.init,S.fx.step={};let tt; let nt; let rt; let it; const ot=/^(?:toggle|show|hide)$/; const at=/queueHooks$/;function st(){nt&&(!1===E.hidden&&C.requestAnimationFrame?C.requestAnimationFrame(st):C.setTimeout(st,S.fx.interval),S.fx.tick())}function ut(){return C.setTimeout(()=> {tt=void 0}),tt=Date.now()}function lt(e,t){let n; let r=0; const i={height:e};for(t=t?1:0;r<4;r+=2-t){i["margin"+(n=ne[r])]=i["padding"+n]=e;}

return t&&(i.opacity=i.width=e),i}function ct(e,t,n){for(var r,i=(ft.tweeners[t]||[]).concat(ft.tweeners["*"]),o=0,a=i.length;o<a;o++){if(r=i[o].call(n,t,e)){return r}}}function ft(o,e,t){let n; let a; let r=0; const i=ft.prefilters.length; const s=S.Deferred().always(()=> {delete u.elem}); var u=function(){if(a){return!1;}for(var e=tt||ut(),t=Math.max(0,l.startTime+l.duration-e),n=1-(t/l.duration||0),r=0,i=l.tweens.length;r<i;r++){l.tweens[r].run(n);}

return s.notifyWith(o,[l,n,t]),n<1&&i?t:(i||s.notifyWith(o,[l,1,0]),s.resolveWith(o,[l]),!1)}; var l=s.promise({elem:o,props:S.extend({},e),opts:S.extend(!0,{specialEasing:{},easing:S.easing._default},t),originalProperties:e,originalOptions:t,startTime:tt||ut(),duration:t.duration,tweens:[],createTween(e,t){const n=S.Tween(o,l.opts,e,t,l.opts.specialEasing[e]||l.opts.easing);

return l.tweens.push(n),n},stop(e){let t=0; const n=e?l.tweens.length:0;if(a){return this;}for(a=!0;t<n;t++){l.tweens[t].run(1);}

return e?(s.notifyWith(o,[l,1,0]),s.resolveWith(o,[l,e])):s.rejectWith(o,[l,e]),this}}); const c=l.props;for(!function(e,t){let n; let r; let i; let o; let a;for(n in e){if(i=t[r=X(n)],o=e[n],Array.isArray(o)&&(i=o[1],o=e[n]=o[0]),n!==r&&(e[r]=o,delete e[n]),(a=S.cssHooks[r])&&"expand"in a){for(n in o=a.expand(o),delete e[r],o){n in e||(e[n]=o[n],t[n]=i);}}else {t[r]=i}}}(c,l.opts.specialEasing);r<i;r++){if(n=ft.prefilters[r].call(l,o,c,l.opts)){return m(n.stop)&&(S._queueHooks(l.elem,l.opts.queue).stop=n.stop.bind(n)),n;}}

return S.map(c,ct,l),m(l.opts.start)&&l.opts.start.call(o,l),l.progress(l.opts.progress).done(l.opts.done,l.opts.complete).fail(l.opts.fail).always(l.opts.always),S.fx.timer(S.extend(u,{elem:o,anim:l,queue:l.opts.queue})),l}S.Animation=S.extend(ft,{tweeners:{"*":[function(e,t){const n=this.createTween(e,t);

return se(n.elem,e,te.exec(t),n),n}]},tweener(e,t){m(e)?(t=e,e=["*"]):e=e.match(P);for(var n,r=0,i=e.length;r<i;r++){n=e[r],ft.tweeners[n]=ft.tweeners[n]||[],ft.tweeners[n].unshift(t)}},prefilters:[function(e,t,n){let r; let i; let o; let a; let s; let u; let l; let c; const f="width"in t||"height"in t; const p=this; const d={}; const h=e.style; let g=e.nodeType&&ae(e); let v=Y.get(e,"fxshow");for(r in n.queue||(null==(a=S._queueHooks(e,"fx")).unqueued&&(a.unqueued=0,s=a.empty.fire,a.empty.fire=function(){a.unqueued||s()}),a.unqueued++,p.always(()=> {p.always(()=> {a.unqueued--,S.queue(e,"fx").length||a.empty.fire()})})),t){if(i=t[r],ot.test(i)){if(delete t[r],o=o||"toggle"===i,i===(g?"hide":"show")){if("show"!==i||!v||void 0===v[r]){continue;}g=!0}d[r]=v&&v[r]||S.style(e,r)}}if((u=!S.isEmptyObject(t))||!S.isEmptyObject(d)){for(r in f&&1===e.nodeType&&(n.overflow=[h.overflow,h.overflowX,h.overflowY],null==(l=v&&v.display)&&(l=Y.get(e,"display")),"none"===(c=S.css(e,"display"))&&(l?c=l:(le([e],!0),l=e.style.display||l,c=S.css(e,"display"),le([e]))),("inline"===c||"inline-block"===c&&null!=l)&&"none"===S.css(e,"float")&&(u||(p.done(()=> {h.display=l}),null==l&&(c=h.display,l="none"===c?"":c)),h.display="inline-block")),n.overflow&&(h.overflow="hidden",p.always(()=> {h.overflow=n.overflow[0],h.overflowX=n.overflow[1],h.overflowY=n.overflow[2]})),u=!1,d){u||(v?"hidden"in v&&(g=v.hidden):v=Y.access(e,"fxshow",{display:l}),o&&(v.hidden=!g),g&&le([e],!0),p.done(()=> {for(r in g||le([e]),Y.remove(e,"fxshow"),d){S.style(e,r,d[r])}})),u=ct(g?v[r]:0,r,p),r in v||(v[r]=u.start,g&&(u.end=u.start,u.start=0))}}}],prefilter(e,t){t?ft.prefilters.unshift(e):ft.prefilters.push(e)}}),S.speed=function(e,t,n){const r=e&&"object"===typeof e?S.extend({},e):{complete:n||!n&&t||m(e)&&e,duration:e,easing:n&&t||t&&!m(t)&&t};

return S.fx.off?r.duration=0:"number"!==typeof r.duration&&(r.duration in S.fx.speeds?r.duration=S.fx.speeds[r.duration]:r.duration=S.fx.speeds._default),null!=r.queue&&!0!==r.queue||(r.queue="fx"),r.old=r.complete,r.complete=function(){m(r.old)&&r.old.call(this),r.queue&&S.dequeue(this,r.queue)},r},S.fn.extend({fadeTo(e,t,n,r){return this.filter(ae).css("opacity",0).show().end().animate({opacity:t},e,n,r)},animate(t,e,n,r){const i=S.isEmptyObject(t); const o=S.speed(e,n,r); const a=function(){const e=ft(this,S.extend({},t),o);(i||Y.get(this,"finish"))&&e.stop(!0)};

return a.finish=a,i||!1===o.queue?this.each(a):this.queue(o.queue,a)},stop(i,e,o){const a=function(e){const t=e.stop;delete e.stop,t(o)};

return"string"!==typeof i&&(o=e,e=i,i=void 0),e&&this.queue(i||"fx",[]),this.each(function(){let e=!0; let t=null!=i&&i+"queueHooks"; const n=S.timers; const r=Y.get(this);if(t){r[t]&&r[t].stop&&a(r[t]);}else {for(t in r){r[t]&&r[t].stop&&at.test(t)&&a(r[t]);}}for(t=n.length;t--;){n[t].elem!==this||null!=i&&n[t].queue!==i||(n[t].anim.stop(o),e=!1,n.splice(t,1));}!e&&o||S.dequeue(this,i)})},finish(a){return!1!==a&&(a=a||"fx"),this.each(function(){let e; const t=Y.get(this); const n=t[a+"queue"]; const r=t[a+"queueHooks"]; const i=S.timers; const o=n?n.length:0;for(t.finish=!0,S.queue(this,a,[]),r&&r.stop&&r.stop.call(this,!0),e=i.length;e--;){i[e].elem===this&&i[e].queue===a&&(i[e].anim.stop(!0),i.splice(e,1));}for(e=0;e<o;e++){n[e]&&n[e].finish&&n[e].finish.call(this);}delete t.finish})}}),S.each(["toggle","show","hide"],(e,r)=> {const i=S.fn[r];S.fn[r]=function(e,t,n){return null==e||"boolean"===typeof e?i.apply(this,arguments):this.animate(lt(r,!0),e,t,n)}}),S.each({slideDown:lt("show"),slideUp:lt("hide"),slideToggle:lt("toggle"),fadeIn:{opacity:"show"},fadeOut:{opacity:"hide"},fadeToggle:{opacity:"toggle"}},(e,r)=> {S.fn[e]=function(e,t,n){return this.animate(r,e,t,n)}}),S.timers=[],S.fx.tick=function(){let e; let t=0; const n=S.timers;for(tt=Date.now();t<n.length;t++){(e=n[t])()||n[t]!==e||n.splice(t--,1);}n.length||S.fx.stop(),tt=void 0},S.fx.timer=function(e){S.timers.push(e),S.fx.start()},S.fx.interval=13,S.fx.start=function(){nt||(nt=!0,st())},S.fx.stop=function(){nt=null},S.fx.speeds={slow:600,fast:200,_default:400},S.fn.delay=function(r,e){return r=S.fx&&S.fx.speeds[r]||r,e=e||"fx",this.queue(e,(e,t)=> {const n=C.setTimeout(e,r);t.stop=function(){C.clearTimeout(n)}})},rt=E.createElement("input"),it=E.createElement("select").appendChild(E.createElement("option")),rt.type="checkbox",y.checkOn=""!==rt.value,y.optSelected=it.selected,(rt=E.createElement("input")).value="t",rt.type="radio",y.radioValue="t"===rt.value;let pt; const dt=S.expr.attrHandle;S.fn.extend({attr(e,t){return $(this,S.attr,e,t,1<arguments.length)},removeAttr(e){return this.each(function(){S.removeAttr(this,e)})}}),S.extend({attr(e,t,n){let r; let i; const o=e.nodeType;if(3!==o&&8!==o&&2!==o){return"undefined"===typeof e.getAttribute?S.prop(e,t,n):(1===o&&S.isXMLDoc(e)||(i=S.attrHooks[t.toLowerCase()]||(S.expr.match.bool.test(t)?pt:void 0)),void 0!==n?null===n?void S.removeAttr(e,t):i&&"set"in i&&void 0!==(r=i.set(e,n,t))?r:(e.setAttribute(t,n+""),n):i&&"get"in i&&null!==(r=i.get(e,t))?r:null==(r=S.find.attr(e,t))?void 0:r)}},attrHooks:{type:{set(e,t){if(!y.radioValue&&"radio"===t&&A(e,"input")){const n=e.value;

return e.setAttribute("type",t),n&&(e.value=n),t}}}},removeAttr(e,t){let n; let r=0; const i=t&&t.match(P);if(i&&1===e.nodeType){while(n=i[r++]){e.removeAttribute(n)}}}}),pt={set(e,t,n){return!1===t?S.removeAttr(e,n):e.setAttribute(n,n),n}},S.each(S.expr.match.bool.source.match(/\w+/g),(e,t)=> {const a=dt[t]||S.find.attr;dt[t]=function(e,t,n){let r; let i; const o=t.toLowerCase();

return n||(i=dt[o],dt[o]=r,r=null!=a(e,t,n)?o:null,dt[o]=i),r}});const ht=/^(?:input|select|textarea|button)$/i; const gt=/^(?:a|area)$/i;function vt(e){return(e.match(P)||[]).join(" ")}function yt(e){return e.getAttribute&&e.getAttribute("class")||""}function mt(e){return Array.isArray(e)?e:"string"===typeof e&&e.match(P)||[]}S.fn.extend({prop(e,t){return $(this,S.prop,e,t,1<arguments.length)},removeProp(e){return this.each(function(){delete this[S.propFix[e]||e]})}}),S.extend({prop(e,t,n){let r; let i; const o=e.nodeType;if(3!==o&&8!==o&&2!==o){return 1===o&&S.isXMLDoc(e)||(t=S.propFix[t]||t,i=S.propHooks[t]),void 0!==n?i&&"set"in i&&void 0!==(r=i.set(e,n,t))?r:e[t]=n:i&&"get"in i&&null!==(r=i.get(e,t))?r:e[t]}},propHooks:{tabIndex:{get(e){const t=S.find.attr(e,"tabindex");

return t?parseInt(t,10):ht.test(e.nodeName)||gt.test(e.nodeName)&&e.href?0:-1}}},propFix:{for:"htmlFor",class:"className"}}),y.optSelected||(S.propHooks.selected={get(e){const t=e.parentNode;

return t&&t.parentNode&&t.parentNode.selectedIndex,null},set(e){const t=e.parentNode;t&&(t.selectedIndex,t.parentNode&&t.parentNode.selectedIndex)}}),S.each(["tabIndex","readOnly","maxLength","cellSpacing","cellPadding","rowSpan","colSpan","useMap","frameBorder","contentEditable"],function(){S.propFix[this.toLowerCase()]=this}),S.fn.extend({addClass(t){let e; let n; let r; let i; let o; let a; let s; let u=0;if(m(t)){return this.each(function(e){S(this).addClass(t.call(this,e,yt(this)))});}if((e=mt(t)).length){while(n=this[u++]){if(i=yt(n),r=1===n.nodeType&&" "+vt(i)+" "){a=0;while(o=e[a++]){r.indexOf(" "+o+" ")<0&&(r+=o+" ");}i!==(s=vt(r))&&n.setAttribute("class",s)}}}

return this},removeClass(t){let e; let n; let r; let i; let o; let a; let s; let u=0;if(m(t)){return this.each(function(e){S(this).removeClass(t.call(this,e,yt(this)))});}if(!arguments.length){return this.attr("class","");}if((e=mt(t)).length){while(n=this[u++]){if(i=yt(n),r=1===n.nodeType&&" "+vt(i)+" "){a=0;while(o=e[a++]){while(-1<r.indexOf(" "+o+" ")){r=r.replace(" "+o+" "," ");}}i!==(s=vt(r))&&n.setAttribute("class",s)}}}

return this},toggleClass(i,t){const o=typeof i; const a="string"===o||Array.isArray(i);

return"boolean"===typeof t&&a?t?this.addClass(i):this.removeClass(i):m(i)?this.each(function(e){S(this).toggleClass(i.call(this,e,yt(this),t),t)}):this.each(function(){let e; let t; let n; let r;if(a){t=0,n=S(this),r=mt(i);while(e=r[t++]){n.hasClass(e)?n.removeClass(e):n.addClass(e)}}else {void 0!==i&&"boolean"!==o||((e=yt(this))&&Y.set(this,"__className__",e),this.setAttribute&&this.setAttribute("class",e||!1===i?"":Y.get(this,"__className__")||""))}})},hasClass(e){let t; let n; let r=0;t=" "+e+" ";while(n=this[r++]){if(1===n.nodeType&&-1<(" "+vt(yt(n))+" ").indexOf(t)){return!0;}}

return!1}});const xt=/\r/g;S.fn.extend({val(n){let r; let e; let i; const t=this[0];

return arguments.length?(i=m(n),this.each(function(e){let t;1===this.nodeType&&(null==(t=i?n.call(this,e,S(this).val()):n)?t="":"number"===typeof t?t+="":Array.isArray(t)&&(t=S.map(t,(e)=> {return null==e?"":e+""})),(r=S.valHooks[this.type]||S.valHooks[this.nodeName.toLowerCase()])&&"set"in r&&void 0!==r.set(this,t,"value")||(this.value=t))})):t?(r=S.valHooks[t.type]||S.valHooks[t.nodeName.toLowerCase()])&&"get"in r&&void 0!==(e=r.get(t,"value"))?e:"string"===typeof(e=t.value)?e.replace(xt,""):null==e?"":e:void 0}}),S.extend({valHooks:{option:{get(e){const t=S.find.attr(e,"value");

return null!=t?t:vt(S.text(e))}},select:{get(e){let t; let n; let r; const i=e.options; const o=e.selectedIndex; const a="select-one"===e.type; const s=a?null:[]; const u=a?o+1:i.length;for(r=o<0?u:a?o:0;r<u;r++){if(((n=i[r]).selected||r===o)&&!n.disabled&&(!n.parentNode.disabled||!A(n.parentNode,"optgroup"))){if(t=S(n).val(),a){return t;}s.push(t)}}

return s},set(e,t){let n; let r; const i=e.options; const o=S.makeArray(t); let a=i.length;while(a--){((r=i[a]).selected=-1<S.inArray(S.valHooks.option.get(r),o))&&(n=!0);}

return n||(e.selectedIndex=-1),o}}}}),S.each(["radio","checkbox"],function(){S.valHooks[this]={set(e,t){if(Array.isArray(t)){return e.checked=-1<S.inArray(S(e).val(),t)}}},y.checkOn||(S.valHooks[this].get=function(e){return null===e.getAttribute("value")?"on":e.value})}),y.focusin="onfocusin"in C;const bt=/^(?:focusinfocus|focusoutblur)$/; const wt=function(e){e.stopPropagation()};S.extend(S.event,{trigger(e,t,n,r){let i; let o; let a; let s; let u; let l; let c; let f; const p=[n||E]; let d=v.call(e,"type")?e.type:e; let h=v.call(e,"namespace")?e.namespace.split("."):[];if(o=f=a=n=n||E,3!==n.nodeType&&8!==n.nodeType&&!bt.test(d+S.event.triggered)&&(-1<d.indexOf(".")&&(d=(h=d.split(".")).shift(),h.sort()),u=d.indexOf(":")<0&&"on"+d,(e=e[S.expando]?e:new S.Event(d,"object"===typeof e&&e)).isTrigger=r?2:3,e.namespace=h.join("."),e.rnamespace=e.namespace?new RegExp("(^|\\.)"+h.join("\\.(?:.*\\.|)")+"(\\.|$)"):null,e.result=void 0,e.target||(e.target=n),t=null==t?[e]:S.makeArray(t,[e]),c=S.event.special[d]||{},r||!c.trigger||!1!==c.trigger.apply(n,t))){if(!r&&!c.noBubble&&!x(n)){for(s=c.delegateType||d,bt.test(s+d)||(o=o.parentNode);o;o=o.parentNode){p.push(o),a=o;}a===(n.ownerDocument||E)&&p.push(a.defaultView||a.parentWindow||C)}i=0;while((o=p[i++])&&!e.isPropagationStopped()){f=o,e.type=1<i?s:c.bindType||d,(l=(Y.get(o,"events")||Object.create(null))[e.type]&&Y.get(o,"handle"))&&l.apply(o,t),(l=u&&o[u])&&l.apply&&V(o)&&(e.result=l.apply(o,t),!1===e.result&&e.preventDefault());}

return e.type=d,r||e.isDefaultPrevented()||c._default&&!1!==c._default.apply(p.pop(),t)||!V(n)||u&&m(n[d])&&!x(n)&&((a=n[u])&&(n[u]=null),S.event.triggered=d,e.isPropagationStopped()&&f.addEventListener(d,wt),n[d](),e.isPropagationStopped()&&f.removeEventListener(d,wt),S.event.triggered=void 0,a&&(n[u]=a)),e.result}},simulate(e,t,n){const r=S.extend(new S.Event,n,{type:e,isSimulated:!0});S.event.trigger(r,null,t)}}),S.fn.extend({trigger(e,t){return this.each(function(){S.event.trigger(e,t,this)})},triggerHandler(e,t){const n=this[0];if(n){return S.event.trigger(e,t,n,!0)}}}),y.focusin||S.each({focus:"focusin",blur:"focusout"},(n,r)=> {const i=function(e){S.event.simulate(r,e.target,S.event.fix(e))};S.event.special[r]={setup(){const e=this.ownerDocument||this.document||this; const t=Y.access(e,r);t||e.addEventListener(n,i,!0),Y.access(e,r,(t||0)+1)},teardown(){const e=this.ownerDocument||this.document||this; const t=Y.access(e,r)-1;t?Y.access(e,r,t):(e.removeEventListener(n,i,!0),Y.remove(e,r))}}});const Tt=C.location; const Ct={guid:Date.now()}; const Et=/\?/;S.parseXML=function(e){let t;if(!e||"string"!==typeof e){return null;}try{t=(new C.DOMParser).parseFromString(e,"text/xml")}catch(e){t=void 0}

return t&&!t.getElementsByTagName("parsererror").length||S.error("Invalid XML: "+e),t};const St=/\[\]$/; const kt=/\r?\n/g; const At=/^(?:submit|button|image|reset|file)$/i; const Nt=/^(?:input|select|textarea|keygen)/i;function Dt(n,e,r,i){let t;if(Array.isArray(e)){S.each(e,(e,t)=> {r||St.test(n)?i(n,t):Dt(n+"["+("object"===typeof t&&null!=t?e:"")+"]",t,r,i)});}else if(r||"object"!==w(e)){i(n,e);}else {for(t in e){Dt(n+"["+t+"]",e[t],r,i)}}}S.param=function(e,t){let n; const r=[]; const i=function(e,t){const n=m(t)?t():t;r[r.length]=encodeURIComponent(e)+"="+encodeURIComponent(null==n?"":n)};if(null==e){return"";}if(Array.isArray(e)||e.jquery&&!S.isPlainObject(e)){S.each(e,function(){i(this.name,this.value)});}else {for(n in e){Dt(n,e[n],t,i);}}

return r.join("&")},S.fn.extend({serialize(){return S.param(this.serializeArray())},serializeArray(){return this.map(function(){const e=S.prop(this,"elements");

return e?S.makeArray(e):this}).filter(function(){const e=this.type;

return this.name&&!S(this).is(":disabled")&&Nt.test(this.nodeName)&&!At.test(e)&&(this.checked||!pe.test(e))}).map(function(e,t){const n=S(this).val();

return null==n?null:Array.isArray(n)?S.map(n,(e)=> {return{name:t.name,value:e.replace(kt,"\r\n")}}):{name:t.name,value:n.replace(kt,"\r\n")}}).get()}});const jt=/%20/g; const qt=/#.*$/; const Lt=/([?&])_=[^&]*/; const Ht=/^(.*?):[ \t]*([^\r\n]*)$/gm; const Ot=/^(?:GET|HEAD)$/; const Pt=/^\/\//; const Rt={}; const Mt={}; const It="*/".concat("*"); const Wt=E.createElement("a");function Ft(o){return function(e,t){"string"!==typeof e&&(t=e,e="*");let n; let r=0; const i=e.toLowerCase().match(P)||[];if(m(t)){while(n=i[r++]){"+"===n[0]?(n=n.slice(1)||"*",(o[n]=o[n]||[]).unshift(t)):(o[n]=o[n]||[]).push(t)}}}}function Bt(t,i,o,a){const s={}; const u=t===Mt;function l(e){let r;

return s[e]=!0,S.each(t[e]||[],(e,t)=> {const n=t(i,o,a);

return"string"!==typeof n||u||s[n]?u?!(r=n):void 0:(i.dataTypes.unshift(n),l(n),!1)}),r}

return l(i.dataTypes[0])||!s["*"]&&l("*")}function $t(e,t){let n; let r; const i=S.ajaxSettings.flatOptions||{};for(n in t){void 0!==t[n]&&((i[n]?e:r||(r={}))[n]=t[n]);}

return r&&S.extend(!0,e,r),e}Wt.href=Tt.href,S.extend({active:0,lastModified:{},etag:{},ajaxSettings:{url:Tt.href,type:"GET",isLocal:/^(?:about|app|app-storage|.+-extension|file|res|widget):$/.test(Tt.protocol),global:!0,processData:!0,async:!0,contentType:"application/x-www-form-urlencoded; charset=UTF-8",accepts:{"*":It,"text":"text/plain","html":"text/html","xml":"application/xml, text/xml","json":"application/json, text/javascript"},contents:{xml:/\bxml\b/,html:/\bhtml/,json:/\bjson\b/},responseFields:{xml:"responseXML",text:"responseText",json:"responseJSON"},converters:{"* text":String,"text html":!0,"text json":JSON.parse,"text xml":S.parseXML},flatOptions:{url:!0,context:!0}},ajaxSetup(e,t){return t?$t($t(e,S.ajaxSettings),t):$t(S.ajaxSettings,e)},ajaxPrefilter:Ft(Rt),ajaxTransport:Ft(Mt),ajax(e,t){"object"===typeof e&&(t=e,e=void 0),t=t||{};let c; let f; let p; let n; let d; let r; let h; let g; let i; let o; const v=S.ajaxSetup({},t); const y=v.context||v; const m=v.context&&(y.nodeType||y.jquery)?S(y):S.event; const x=S.Deferred(); const b=S.Callbacks("once memory"); let w=v.statusCode||{}; const a={}; const s={}; let u="canceled"; var T={readyState:0,getResponseHeader(e){let t;if(h){if(!n){n={};while(t=Ht.exec(p)){n[t[1].toLowerCase()+" "]=(n[t[1].toLowerCase()+" "]||[]).concat(t[2])}}t=n[e.toLowerCase()+" "]}

return null==t?null:t.join(", ")},getAllResponseHeaders(){return h?p:null},setRequestHeader(e,t){return null==h&&(e=s[e.toLowerCase()]=s[e.toLowerCase()]||e,a[e]=t),this},overrideMimeType(e){return null==h&&(v.mimeType=e),this},statusCode(e){let t;if(e){if(h){T.always(e[T.status]);}else {for(t in e){w[t]=[w[t],e[t]];}}}

return this},abort(e){const t=e||u;

return c&&c.abort(t),l(0,t),this}};if(x.promise(T),v.url=((e||v.url||Tt.href)+"").replace(Pt,Tt.protocol+"//"),v.type=t.method||t.type||v.method||v.type,v.dataTypes=(v.dataType||"*").toLowerCase().match(P)||[""],null==v.crossDomain){r=E.createElement("a");try{r.href=v.url,r.href=r.href,v.crossDomain=Wt.protocol+"//"+Wt.host!=r.protocol+"//"+r.host}catch(e){v.crossDomain=!0}}if(v.data&&v.processData&&"string"!==typeof v.data&&(v.data=S.param(v.data,v.traditional)),Bt(Rt,v,t,T),h){return T;}for(i in(g=S.event&&v.global)&&0==S.active++&&S.event.trigger("ajaxStart"),v.type=v.type.toUpperCase(),v.hasContent=!Ot.test(v.type),f=v.url.replace(qt,""),v.hasContent?v.data&&v.processData&&0===(v.contentType||"").indexOf("application/x-www-form-urlencoded")&&(v.data=v.data.replace(jt,"+")):(o=v.url.slice(f.length),v.data&&(v.processData||"string"===typeof v.data)&&(f+=(Et.test(f)?"&":"?")+v.data,delete v.data),!1===v.cache&&(f=f.replace(Lt,"$1"),o=(Et.test(f)?"&":"?")+"_="+Ct.guid+++o),v.url=f+o),v.ifModified&&(S.lastModified[f]&&T.setRequestHeader("If-Modified-Since",S.lastModified[f]),S.etag[f]&&T.setRequestHeader("If-None-Match",S.etag[f])),(v.data&&v.hasContent&&!1!==v.contentType||t.contentType)&&T.setRequestHeader("Content-Type",v.contentType),T.setRequestHeader("Accept",v.dataTypes[0]&&v.accepts[v.dataTypes[0]]?v.accepts[v.dataTypes[0]]+("*"!==v.dataTypes[0]?", "+It+"; q=0.01":""):v.accepts["*"]),v.headers){T.setRequestHeader(i,v.headers[i]);}if(v.beforeSend&&(!1===v.beforeSend.call(y,T,v)||h)){return T.abort();}if(u="abort",b.add(v.complete),T.done(v.success),T.fail(v.error),c=Bt(Mt,v,t,T)){if(T.readyState=1,g&&m.trigger("ajaxSend",[T,v]),h){return T;}v.async&&0<v.timeout&&(d=C.setTimeout(()=> {T.abort("timeout")},v.timeout));try{h=!1,c.send(a,l)}catch(e){if(h){throw e;}l(-1,e)}}else {l(-1,"No Transport");}function l(e,t,n,r){let i; let o; let a; let s; let u; let l=t;h||(h=!0,d&&C.clearTimeout(d),c=void 0,p=r||"",T.readyState=0<e?4:0,i=200<=e&&e<300||304===e,n&&(s=function(e,t,n){let r; let i; let o; let a; const s=e.contents; const u=e.dataTypes;while("*"===u[0]){u.shift(),void 0===r&&(r=e.mimeType||t.getResponseHeader("Content-Type"));}if(r){for(i in s){if(s[i]&&s[i].test(r)){u.unshift(i);break}}}if(u[0]in n){o=u[0];}else{for(i in n){if(!u[0]||e.converters[i+" "+u[0]]){o=i;break}a||(a=i)}o=o||a}if(o){return o!==u[0]&&u.unshift(o),n[o]}}(v,T,n)),!i&&-1<S.inArray("script",v.dataTypes)&&(v.converters["text script"]=function(){}),s=function(e,t,n,r){let i; let o; let a; let s; let u; const l={}; const c=e.dataTypes.slice();if(c[1]){for(a in e.converters){l[a.toLowerCase()]=e.converters[a];}}o=c.shift();while(o){if(e.responseFields[o]&&(n[e.responseFields[o]]=t),!u&&r&&e.dataFilter&&(t=e.dataFilter(t,e.dataType)),u=o,o=c.shift()){if("*"===o){o=u;}else if("*"!==u&&u!==o){if(!(a=l[u+" "+o]||l["* "+o])){for(i in l){if((s=i.split(" "))[1]===o&&(a=l[u+" "+s[0]]||l["* "+s[0]])){!0===a?a=l[i]:!0!==l[i]&&(o=s[0],c.unshift(s[1]));break}}}if(!0!==a){if(a&&e["throws"]){t=a(t);}else {try{t=a(t)}catch(e){return{state:"parsererror",error:a?e:"No conversion from "+u+" to "+o}}}}}}}

return{state:"success",data:t}}(v,s,T,i),i?(v.ifModified&&((u=T.getResponseHeader("Last-Modified"))&&(S.lastModified[f]=u),(u=T.getResponseHeader("etag"))&&(S.etag[f]=u)),204===e||"HEAD"===v.type?l="nocontent":304===e?l="notmodified":(l=s.state,o=s.data,i=!(a=s.error))):(a=l,!e&&l||(l="error",e<0&&(e=0))),T.status=e,T.statusText=(t||l)+"",i?x.resolveWith(y,[o,l,T]):x.rejectWith(y,[T,l,a]),T.statusCode(w),w=void 0,g&&m.trigger(i?"ajaxSuccess":"ajaxError",[T,v,i?o:a]),b.fireWith(y,[T,l]),g&&(m.trigger("ajaxComplete",[T,v]),--S.active||S.event.trigger("ajaxStop")))}

return T},getJSON(e,t,n){return S.get(e,t,n,"json")},getScript(e,t){return S.get(e,void 0,t,"script")}}),S.each(["get","post"],(e,i)=> {S[i]=function(e,t,n,r){return m(t)&&(r=r||n,n=t,t=void 0),S.ajax(S.extend({url:e,type:i,dataType:r,data:t,success:n},S.isPlainObject(e)&&e))}}),S.ajaxPrefilter((e)=> {let t;for(t in e.headers){"content-type"===t.toLowerCase()&&(e.contentType=e.headers[t]||"")}}),S._evalUrl=function(e,t,n){return S.ajax({url:e,type:"GET",dataType:"script",cache:!0,async:!1,global:!1,converters:{"text script"(){}},dataFilter(e){S.globalEval(e,t,n)}})},S.fn.extend({wrapAll(e){let t;

return this[0]&&(m(e)&&(e=e.call(this[0])),t=S(e,this[0].ownerDocument).eq(0).clone(!0),this[0].parentNode&&t.insertBefore(this[0]),t.map(function(){let e=this;while(e.firstElementChild){e=e.firstElementChild;}

return e}).append(this)),this},wrapInner(n){return m(n)?this.each(function(e){S(this).wrapInner(n.call(this,e))}):this.each(function(){const e=S(this); const t=e.contents();t.length?t.wrapAll(n):e.append(n)})},wrap(t){const n=m(t);

return this.each(function(e){S(this).wrapAll(n?t.call(this,e):t)})},unwrap(e){return this.parent(e).not("body").each(function(){S(this).replaceWith(this.childNodes)}),this}}),S.expr.pseudos.hidden=function(e){return!S.expr.pseudos.visible(e)},S.expr.pseudos.visible=function(e){return!!(e.offsetWidth||e.offsetHeight||e.getClientRects().length)},S.ajaxSettings.xhr=function(){try{return new C.XMLHttpRequest}catch(e){}};const _t={0:200,1223:204}; let zt=S.ajaxSettings.xhr();y.cors=!!zt&&"withCredentials"in zt,y.ajax=zt=!!zt,S.ajaxTransport((i)=> {let o; let a;if(y.cors||zt&&!i.crossDomain){return{send(e,t){let n; const r=i.xhr();if(r.open(i.type,i.url,i.async,i.username,i.password),i.xhrFields){for(n in i.xhrFields){r[n]=i.xhrFields[n];}}for(n in i.mimeType&&r.overrideMimeType&&r.overrideMimeType(i.mimeType),i.crossDomain||e["X-Requested-With"]||(e["X-Requested-With"]="XMLHttpRequest"),e){r.setRequestHeader(n,e[n]);}o=function(e){return function(){o&&(o=a=r.onload=r.onerror=r.onabort=r.ontimeout=r.onreadystatechange=null,"abort"===e?r.abort():"error"===e?"number"!==typeof r.status?t(0,"error"):t(r.status,r.statusText):t(_t[r.status]||r.status,r.statusText,"text"!==(r.responseType||"text")||"string"!==typeof r.responseText?{binary:r.response}:{text:r.responseText},r.getAllResponseHeaders()))}},r.onload=o(),a=r.onerror=r.ontimeout=o("error"),void 0!==r.onabort?r.onabort=a:r.onreadystatechange=function(){4===r.readyState&&C.setTimeout(()=> {o&&a()})},o=o("abort");try{r.send(i.hasContent&&i.data||null)}catch(e){if(o){throw e}}},abort(){o&&o()}}}}),S.ajaxPrefilter((e)=> {e.crossDomain&&(e.contents.script=!1)}),S.ajaxSetup({accepts:{script:"text/javascript, application/javascript, application/ecmascript, application/x-ecmascript"},contents:{script:/\b(?:java|ecma)script\b/},converters:{"text script"(e){return S.globalEval(e),e}}}),S.ajaxPrefilter("script",(e)=> {void 0===e.cache&&(e.cache=!1),e.crossDomain&&(e.type="GET")}),S.ajaxTransport("script",(n)=> {let r; let i;if(n.crossDomain||n.scriptAttrs){return{send(e,t){r=S("<script>").attr(n.scriptAttrs||{}).prop({charset:n.scriptCharset,src:n.url}).on("load error",i=function(e){r.remove(),i=null,e&&t("error"===e.type?404:200,e.type)}),E.head.appendChild(r[0])},abort(){i&&i()}}}});let Ut; const Xt=[]; const Vt=/(=)\?(?=&|$)|\?\?/;S.ajaxSetup({jsonp:"callback",jsonpCallback(){const e=Xt.pop()||S.expando+"_"+Ct.guid++;

return this[e]=!0,e}}),S.ajaxPrefilter("json jsonp",(e,t,n)=> {let r; let i; let o; const a=!1!==e.jsonp&&(Vt.test(e.url)?"url":"string"===typeof e.data&&0===(e.contentType||"").indexOf("application/x-www-form-urlencoded")&&Vt.test(e.data)&&"data");if(a||"jsonp"===e.dataTypes[0]){return r=e.jsonpCallback=m(e.jsonpCallback)?e.jsonpCallback():e.jsonpCallback,a?e[a]=e[a].replace(Vt,"$1"+r):!1!==e.jsonp&&(e.url+=(Et.test(e.url)?"&":"?")+e.jsonp+"="+r),e.converters["script json"]=function(){return o||S.error(r+" was not called"),o[0]},e.dataTypes[0]="json",i=C[r],C[r]=function(){o=arguments},n.always(()=> {void 0===i?S(C).removeProp(r):C[r]=i,e[r]&&(e.jsonpCallback=t.jsonpCallback,Xt.push(r)),o&&m(i)&&i(o[0]),o=i=void 0}),"script"}}),y.createHTMLDocument=((Ut=E.implementation.createHTMLDocument("").body).innerHTML="<form></form><form></form>",2===Ut.childNodes.length),S.parseHTML=function(e,t,n){return"string"!==typeof e?[]:("boolean"===typeof t&&(n=t,t=!1),t||(y.createHTMLDocument?((r=(t=E.implementation.createHTMLDocument("")).createElement("base")).href=E.location.href,t.head.appendChild(r)):t=E),o=!n&&[],(i=N.exec(e))?[t.createElement(i[1])]:(i=xe([e],t,o),o&&o.length&&S(o).remove(),S.merge([],i.childNodes)));let r; let i; let o},S.fn.load=function(e,t,n){let r; let i; let o; const a=this; const s=e.indexOf(" ");

return-1<s&&(r=vt(e.slice(s)),e=e.slice(0,s)),m(t)?(n=t,t=void 0):t&&"object"===typeof t&&(i="POST"),!!a.length&&S.ajax({url:e,type:i||"GET",dataType:"html",data:t}).done(function(e){o=arguments,a.html(r?S("<div>").append(S.parseHTML(e)).find(r):e)}).always(n&&((e,t)=> {a.each(function(){n.apply(this,o||[e.responseText,t,e])})})),this},S.expr.pseudos.animated=function(t){return S.grep(S.timers,(e)=> {return t===e.elem}).length},S.offset={setOffset(e,t,n){let r; let i; let o; let a; let s; let u; const l=S.css(e,"position"); const c=S(e); const f={};"static"===l&&(e.style.position="relative"),s=c.offset(),o=S.css(e,"top"),u=S.css(e,"left"),("absolute"===l||"fixed"===l)&&-1<(o+u).indexOf("auto")?(a=(r=c.position()).top,i=r.left):(a=parseFloat(o)||0,i=parseFloat(u)||0),m(t)&&(t=t.call(e,n,S.extend({},s))),null!=t.top&&(f.top=t.top-s.top+a),null!=t.left&&(f.left=t.left-s.left+i),"using"in t?t.using.call(e,f):("number"===typeof f.top&&(f.top+="px"),"number"===typeof f.left&&(f.left+="px"),c.css(f))}},S.fn.extend({offset(t){if(arguments.length){return void 0===t?this:this.each(function(e){S.offset.setOffset(this,t,e)});}let e; let n; const r=this[0];

return r?r.getClientRects().length?(e=r.getBoundingClientRect(),n=r.ownerDocument.defaultView,{top:e.top+n.pageYOffset,left:e.left+n.pageXOffset}):{top:0,left:0}:void 0},position(){if(this[0]){let e; let t; let n; const r=this[0]; let i={top:0,left:0};if("fixed"===S.css(r,"position")){t=r.getBoundingClientRect();}else{t=this.offset(),n=r.ownerDocument,e=r.offsetParent||n.documentElement;while(e&&(e===n.body||e===n.documentElement)&&"static"===S.css(e,"position")){e=e.parentNode;}e&&e!==r&&1===e.nodeType&&((i=S(e).offset()).top+=S.css(e,"borderTopWidth",!0),i.left+=S.css(e,"borderLeftWidth",!0))}

return{top:t.top-i.top-S.css(r,"marginTop",!0),left:t.left-i.left-S.css(r,"marginLeft",!0)}}},offsetParent(){return this.map(function(){let e=this.offsetParent;while(e&&"static"===S.css(e,"position")){e=e.offsetParent;}

return e||re})}}),S.each({scrollLeft:"pageXOffset",scrollTop:"pageYOffset"},(t,i)=> {const o="pageYOffset"===i;S.fn[t]=function(e){return $(this,(e,t,n)=> {let r;if(x(e)?r=e:9===e.nodeType&&(r=e.defaultView),void 0===n){return r?r[i]:e[t];}r?r.scrollTo(o?r.pageXOffset:n,o?n:r.pageYOffset):e[t]=n},t,e,arguments.length)}}),S.each(["top","left"],(e,n)=> {S.cssHooks[n]=$e(y.pixelPosition,(e,t)=> {if(t){return t=Be(e,n),Me.test(t)?S(e).position()[n]+"px":t}})}),S.each({Height:"height",Width:"width"},(a,s)=> {S.each({"padding":"inner"+a,"content":s,"":"outer"+a},(r,o)=> {S.fn[o]=function(e,t){const n=arguments.length&&(r||"boolean"!==typeof e); const i=r||(!0===e||!0===t?"margin":"border");

return $(this,(e,t,n)=> {let r;

return x(e)?0===o.indexOf("outer")?e["inner"+a]:e.document.documentElement["client"+a]:9===e.nodeType?(r=e.documentElement,Math.max(e.body["scroll"+a],r["scroll"+a],e.body["offset"+a],r["offset"+a],r["client"+a])):void 0===n?S.css(e,t,i):S.style(e,t,n,i)},s,n?e:void 0,n)}})}),S.each(["ajaxStart","ajaxStop","ajaxComplete","ajaxError","ajaxSuccess","ajaxSend"],(e,t)=> {S.fn[t]=function(e){return this.on(t,e)}}),S.fn.extend({bind(e,t,n){return this.on(e,null,t,n)},unbind(e,t){return this.off(e,null,t)},delegate(e,t,n,r){return this.on(t,e,n,r)},undelegate(e,t,n){return 1===arguments.length?this.off(e,"**"):this.off(t,e||"**",n)},hover(e,t){return this.mouseenter(e).mouseleave(t||e)}}),S.each("blur focus focusin focusout resize scroll click dblclick mousedown mouseup mousemove mouseover mouseout mouseenter mouseleave change select submit keydown keypress keyup contextmenu".split(" "),(e,n)=> {S.fn[n]=function(e,t){return arguments.length?this.on(n,null,e,t):this.trigger(n)}});const Gt=/^[\s\uFEFF\xA0]+|[\s\uFEFF\xA0]+$/g;S.proxy=function(e,t){let n; let r; let i;if("string"===typeof t&&(n=e[t],t=e,e=n),m(e)){return r=s.call(arguments,2),(i=function(){return e.apply(t||this,r.concat(s.call(arguments)))}).guid=e.guid=e.guid||S.guid++,i}},S.holdReady=function(e){e?S.readyWait++:S.ready(!0)},S.isArray=Array.isArray,S.parseJSON=JSON.parse,S.nodeName=A,S.isFunction=m,S.isWindow=x,S.camelCase=X,S.type=w,S.now=Date.now,S.isNumeric=function(e){const t=S.type(e);

return("number"===t||"string"===t)&&!isNaN(e-parseFloat(e))},S.trim=function(e){return null==e?"":(e+"").replace(Gt,"")},"function"===typeof define&&define.amd&&define("jquery",[],()=> {return S});const Yt=C.jQuery; const Qt=C.$;

return S.noConflict=function(e){return C.$===S&&(C.$=Qt),e&&C.jQuery===S&&(C.jQuery=Yt),S},"undefined"===typeof e&&(C.jQuery=C.$=S),S});

!function(n){const t=n.prototype.init;n.prototype.init=function(n,i,o){return"#"===n&&(n=""),new t(n,i,o)}}(window.$);

!function(t){t.ajaxSetup({data:{},type:"POST"}),t.ajaxPrefilter((t=>{t.crossDomain&&(t.contents.script=!1),t.url&&(t.url=Liferay.Util.getURLWithSessionId(t.url))}))}(window.$);

/* !
  * Bootstrap v4.3.1 (https://getbootstrap.com/)
  * Copyright 2011-2019 The Bootstrap Authors (https://github.com/twbs/bootstrap/graphs/contributors)
  * Licensed under MIT (https://github.com/twbs/bootstrap/blob/master/LICENSE)
  */
!function(t,e){"object"===typeof exports&&"undefined"!==typeof module?e(exports,require("jquery")):"function"===typeof define&&define.amd?define(["exports","jquery"],e):e((t=t||self).bootstrap={},t.jQuery)}(this,(t,p)=> {"use strict";function i(t,e){for(let n=0;n<e.length;n++){const i=e[n];i.enumerable=i.enumerable||!1,i.configurable=!0,"value"in i&&(i.writable=!0),Object.defineProperty(t,i.key,i)}}function s(t,e,n){return e&&i(t.prototype,e),n&&i(t,n),t}function l(o){for(let t=1;t<arguments.length;t++){var r=null!=arguments[t]?arguments[t]:{}; let e=Object.keys(r);"function"===typeof Object.getOwnPropertySymbols&&(e=e.concat(Object.getOwnPropertySymbols(r).filter((t)=> {return Object.getOwnPropertyDescriptor(r,t).enumerable}))),e.forEach((t)=> {let e; let n; let i;e=o,i=r[n=t],n in e?Object.defineProperty(e,n,{value:i,enumerable:!0,configurable:!0,writable:!0}):e[n]=i})}

return o}p=p&&p.hasOwnProperty("default")?p.default:p;const e="transitionend";function n(t){const e=this; let n=!1;

return p(this).one(m.TRANSITION_END,()=> {n=!0}),setTimeout(()=> {n||m.triggerTransitionEnd(e)},t),this}var m={TRANSITION_END:"bsTransitionEnd",getUID(t){for(;t+=~~(1e6*Math.random()),document.getElementById(t);){;}

return t},getSelectorFromElement(t){let e=t.dataset.target;if(!e||"#"===e){const n=t.getAttribute("href");e=n&&"#"!==n?n.trim():""}try{return document.querySelector(e)?e:null}catch(t){return null}},getTransitionDurationFromElement(t){if(!t){return 0;}let e=p(t).css("transition-duration"); let n=p(t).css("transition-delay"); const i=parseFloat(e); const o=parseFloat(n);

return i||o?(e=e.split(",")[0],n=n.split(",")[0],1e3*(parseFloat(e)+parseFloat(n))):0},reflow(t){return t.offsetHeight},triggerTransitionEnd(t){p(t).trigger(e)},supportsTransitionEnd(){return Boolean(e)},isElement(t){return(t[0]||t).nodeType},typeCheckConfig(t,e,n){for(const i in n){if(Object.prototype.hasOwnProperty.call(n,i)){const o=n[i]; const r=e[i]; const s=r&&m.isElement(r)?"element":(a=r,{}.toString.call(a).match(/\s([a-z]+)/i)[1].toLowerCase());if(!new RegExp(o).test(s)){throw new Error(t.toUpperCase()+': Option "'+i+'" provided type "'+s+'" but expected type "'+o+'".')}}}let a},findShadowRoot(t){if(!document.documentElement.attachShadow){return null;}if("function"!==typeof t.getRootNode){return t instanceof ShadowRoot?t:t.parentNode?m.findShadowRoot(t.parentNode):null;}const e=t.getRootNode();

return e instanceof ShadowRoot?e:null}};p.fn.emulateTransitionEnd=n,p.event.special[m.TRANSITION_END]={bindType:e,delegateType:e,handle(t){if(p(t.target).is(this)){return t.handleObj.handler.apply(this,arguments)}}};const o="alert"; const r="bs.alert"; const a="."+r; const c=p.fn[o]; const h={CLOSE:"close"+a,CLOSED:"closed"+a,CLICK_DATA_API:"click"+a+".data-api"}; const u="alert"; const f="fade"; const d="show"; const g=function(){function i(t){this._element=t}const t=i.prototype;

return t.close=function(t){let e=this._element;t&&(e=this._getRootElement(t)),this._triggerCloseEvent(e).isDefaultPrevented()||this._removeElement(e)},t.dispose=function(){p.removeData(this._element,r),this._element=null},t._getRootElement=function(t){const e=m.getSelectorFromElement(t); let n=!1;

return e&&(n=document.querySelector(e)),n||(n=p(t).closest("."+u)[0]),n},t._triggerCloseEvent=function(t){const e=p.Event(h.CLOSE);

return p(t).trigger(e),e},t._removeElement=function(e){const n=this;if(p(e).removeClass(d),p(e).hasClass(f)){const t=m.getTransitionDurationFromElement(e);p(e).one(m.TRANSITION_END,(t)=> {return n._destroyElement(e,t)}).emulateTransitionEnd(t)}else {this._destroyElement(e)}},t._destroyElement=function(t){p(t).detach().trigger(h.CLOSED).remove()},i._jQueryInterface=function(n){return this.each(function(){const t=p(this); let e=t.data(r);e||(e=new i(this),t.data(r,e)),"close"===n&&e[n](this)})},i._handleDismiss=function(e){return function(t){t&&t.preventDefault(),e.close(this)}},s(i,null,[{key:"VERSION",get(){return"4.3.1"}}]),i}();p(document).on(h.CLICK_DATA_API,'[data-dismiss="alert"]',g._handleDismiss(new g)),p.fn[o]=g._jQueryInterface,p.fn[o].Constructor=g,p.fn[o].noConflict=function(){return p.fn[o]=c,g._jQueryInterface};const _="button"; const v="bs.button"; const y="."+v; const E=".data-api"; const b=p.fn[_]; const w="active"; const C="btn"; const T="focus"; const S='[data-toggle^="button"]'; const D='[data-toggle="buttons"]'; const I='input:not([type="hidden"])'; const A=".active"; const O=".btn"; const N={CLICK_DATA_API:"click"+y+E,FOCUS_BLUR_DATA_API:"focus"+y+E+" blur"+y+E}; const k=function(){function n(t){this._element=t}const t=n.prototype;

return t.toggle=function(){let t=!0; let e=!0; const n=p(this._element).closest(D)[0];if(n){const i=this._element.querySelector(I);if(i){if("radio"===i.type){if(i.checked&&this._element.classList.contains(w)){t=!1;}else{const o=n.querySelector(A);o&&p(o).removeClass(w)}}if(t){if(i.hasAttribute("disabled")||n.hasAttribute("disabled")||i.classList.contains("disabled")||n.classList.contains("disabled")){return;}i.checked=!this._element.classList.contains(w),p(i).trigger("change")}i.focus(),e=!1}}e&&this._element.setAttribute("aria-pressed",!this._element.classList.contains(w)),t&&p(this._element).toggleClass(w)},t.dispose=function(){p.removeData(this._element,v),this._element=null},n._jQueryInterface=function(e){return this.each(function(){let t=p(this).data(v);t||(t=new n(this),p(this).data(v,t)),"toggle"===e&&t[e]()})},s(n,null,[{key:"VERSION",get(){return"4.3.1"}}]),n}();p(document).on(N.CLICK_DATA_API,S,(t)=> {t.preventDefault();let e=t.target;p(e).hasClass(C)||(e=p(e).closest(O)),k._jQueryInterface.call(p(e),"toggle")}).on(N.FOCUS_BLUR_DATA_API,S,(t)=> {const e=p(t.target).closest(O)[0];p(e).toggleClass(T,/^focus(in)?$/.test(t.type))}),p.fn[_]=k._jQueryInterface,p.fn[_].Constructor=k,p.fn[_].noConflict=function(){return p.fn[_]=b,k._jQueryInterface};const L="carousel"; const x="bs.carousel"; const P="."+x; const H=".data-api"; const j=p.fn[L]; const R={interval:5e3,keyboard:!0,slide:!1,pause:"hover",wrap:!0,touch:!0}; const F={interval:"(number|boolean)",keyboard:"boolean",slide:"(boolean|string)",pause:"(string|boolean)",wrap:"boolean",touch:"boolean"}; const M="next"; const W="prev"; const U="left"; const B="right"; const q={SLIDE:"slide"+P,SLID:"slid"+P,KEYDOWN:"keydown"+P,MOUSEENTER:"mouseenter"+P,MOUSELEAVE:"mouseleave"+P,TOUCHSTART:"touchstart"+P,TOUCHMOVE:"touchmove"+P,TOUCHEND:"touchend"+P,POINTERDOWN:"pointerdown"+P,POINTERUP:"pointerup"+P,DRAG_START:"dragstart"+P,LOAD_DATA_API:"load"+P+H,CLICK_DATA_API:"click"+P+H}; const K="carousel"; const Q="active"; const V="slide"; const Y="carousel-item-right"; const z="carousel-item-left"; const X="carousel-item-next"; const G="carousel-item-prev"; const $="pointer-event"; const J=".active"; const Z=".active.carousel-item"; const tt=".carousel-item"; const et=".carousel-item img"; const nt=".carousel-item-next, .carousel-item-prev"; const it=".carousel-indicators"; const ot="[data-slide], [data-slide-to]"; const rt='[data-ride="carousel"]'; const st={TOUCH:"touch",PEN:"pen"}; const at=function(){function r(t,e){this._items=null,this._interval=null,this._activeElement=null,this._isPaused=!1,this._isSliding=!1,this.touchTimeout=null,this.touchStartX=0,this.touchDeltaX=0,this._config=this._getConfig(e),this._element=t,this._indicatorsElement=this._element.querySelector(it),this._touchSupported="ontouchstart"in document.documentElement||0<navigator.maxTouchPoints,this._pointerEvent=Boolean(window.PointerEvent||window.MSPointerEvent),this._addEventListeners()}const t=r.prototype;

return t.next=function(){this._isSliding||this._slide(M)},t.nextWhenVisible=function(){!document.hidden&&p(this._element).is(":visible")&&"hidden"!==p(this._element).css("visibility")&&this.next()},t.prev=function(){this._isSliding||this._slide(W)},t.pause=function(t){t||(this._isPaused=!0),this._element.querySelector(nt)&&(m.triggerTransitionEnd(this._element),this.cycle(!0)),clearInterval(this._interval),this._interval=null},t.cycle=function(t){t||(this._isPaused=!1),this._interval&&(clearInterval(this._interval),this._interval=null),this._config.interval&&!this._isPaused&&(this._interval=setInterval((document.visibilityState?this.nextWhenVisible:this.next).bind(this),this._config.interval))},t.to=function(t){const e=this;this._activeElement=this._element.querySelector(Z);const n=this._getItemIndex(this._activeElement);if(!(t>this._items.length-1||t<0)){if(this._isSliding){p(this._element).one(q.SLID,()=> {return e.to(t)});}else{if(n===t){return this.pause(),void this.cycle();}const i=n<t?M:W;this._slide(i,this._items[t])}}},t.dispose=function(){p(this._element).off(P),p.removeData(this._element,x),this._items=null,this._config=null,this._element=null,this._interval=null,this._isPaused=null,this._isSliding=null,this._activeElement=null,this._indicatorsElement=null},t._getConfig=function(t){return t=l({},R,t),m.typeCheckConfig(L,t,F),t},t._handleSwipe=function(){const t=Math.abs(this.touchDeltaX);if(!(t<=40)){const e=t/this.touchDeltaX;0<e&&this.prev(),e<0&&this.next()}},t._addEventListeners=function(){const e=this;this._config.keyboard&&p(this._element).on(q.KEYDOWN,(t)=> {return e._keydown(t)}),"hover"===this._config.pause&&p(this._element).on(q.MOUSEENTER,(t)=> {return e.pause(t)}).on(q.MOUSELEAVE,(t)=> {return e.cycle(t)}),this._config.touch&&this._addTouchEventListeners()},t._addTouchEventListeners=function(){const n=this;if(this._touchSupported){const e=function(t){n._pointerEvent&&st[t.originalEvent.pointerType.toUpperCase()]?n.touchStartX=t.originalEvent.clientX:n._pointerEvent||(n.touchStartX=t.originalEvent.touches[0].clientX)}; const i=function(t){n._pointerEvent&&st[t.originalEvent.pointerType.toUpperCase()]&&(n.touchDeltaX=t.originalEvent.clientX-n.touchStartX),n._handleSwipe(),"hover"===n._config.pause&&(n.pause(),n.touchTimeout&&clearTimeout(n.touchTimeout),n.touchTimeout=setTimeout((t)=> {return n.cycle(t)},500+n._config.interval))};p(this._element.querySelectorAll(et)).on(q.DRAG_START,(t)=> {return t.preventDefault()}),this._pointerEvent?(p(this._element).on(q.POINTERDOWN,(t)=> {return e(t)}),p(this._element).on(q.POINTERUP,(t)=> {return i(t)}),this._element.classList.add($)):(p(this._element).on(q.TOUCHSTART,(t)=> {return e(t)}),p(this._element).on(q.TOUCHMOVE,(t)=> {let e;(e=t).originalEvent.touches&&1<e.originalEvent.touches.length?n.touchDeltaX=0:n.touchDeltaX=e.originalEvent.touches[0].clientX-n.touchStartX}),p(this._element).on(q.TOUCHEND,(t)=> {return i(t)}))}},t._keydown=function(t){if(!/input|textarea/i.test(t.target.tagName)){switch(t.which){case 37:t.preventDefault(),this.prev();break;case 39:t.preventDefault(),this.next()}}},t._getItemIndex=function(t){return this._items=t&&t.parentNode?[].slice.call(t.parentNode.querySelectorAll(tt)):[],this._items.indexOf(t)},t._getItemByDirection=function(t,e){const n=t===M; const i=t===W; const o=this._getItemIndex(e); const r=this._items.length-1;if((i&&0===o||n&&o===r)&&!this._config.wrap){return e;}const s=(o+(t===W?-1:1))%this._items.length;

return-1===s?this._items[this._items.length-1]:this._items[s]},t._triggerSlideEvent=function(t,e){const n=this._getItemIndex(t); const i=this._getItemIndex(this._element.querySelector(Z)); const o=p.Event(q.SLIDE,{relatedTarget:t,direction:e,from:i,to:n});

return p(this._element).trigger(o),o},t._setActiveIndicatorElement=function(t){if(this._indicatorsElement){const e=[].slice.call(this._indicatorsElement.querySelectorAll(J));p(e).removeClass(Q);const n=this._indicatorsElement.children[this._getItemIndex(t)];n&&p(n).addClass(Q)}},t._slide=function(t,e){let n; let i; let o; const r=this; const s=this._element.querySelector(Z); const a=this._getItemIndex(s); const l=e||s&&this._getItemByDirection(t,s); const c=this._getItemIndex(l); const h=Boolean(this._interval);if(o=t===M?(n=z,i=X,U):(n=Y,i=G,B),l&&p(l).hasClass(Q)){this._isSliding=!1;}else if(!this._triggerSlideEvent(l,o).isDefaultPrevented()&&s&&l){this._isSliding=!0,h&&this.pause(),this._setActiveIndicatorElement(l);const u=p.Event(q.SLID,{relatedTarget:l,direction:o,from:a,to:c});if(p(this._element).hasClass(V)){p(l).addClass(i),m.reflow(l),p(s).addClass(n),p(l).addClass(n);const f=parseInt(l.dataset.interval,10);this._config.interval=f?(this._config.defaultInterval=this._config.defaultInterval||this._config.interval,f):this._config.defaultInterval||this._config.interval;const d=m.getTransitionDurationFromElement(s);p(s).one(m.TRANSITION_END,()=> {p(l).removeClass(n+" "+i).addClass(Q),p(s).removeClass(Q+" "+i+" "+n),r._isSliding=!1,setTimeout(()=> {return p(r._element).trigger(u)},0)}).emulateTransitionEnd(d)}else {p(s).removeClass(Q),p(l).addClass(Q),this._isSliding=!1,p(this._element).trigger(u);}h&&this.cycle()}},r._jQueryInterface=function(i){return this.each(function(){let t=p(this).data(x); let e=l({},R,p(this).data());"object"===typeof i&&(e=l({},e,i));const n="string"===typeof i?i:e.slide;if(t||(t=new r(this,e),p(this).data(x,t)),"number"===typeof i){t.to(i);}else if("string"===typeof n){if("undefined"===typeof t[n]){throw new TypeError('No method named "'+n+'"');}t[n]()}else {e.interval&&e.ride&&(t.pause(),t.cycle())}})},r._dataApiClickHandler=function(t){const e=m.getSelectorFromElement(this);if(e){const n=p(e)[0];if(n&&p(n).hasClass(K)){const i=l({},p(n).data(),p(this).data()); const o=this.getAttribute("data-slide-to");o&&(i.interval=!1),r._jQueryInterface.call(p(n),i),o&&p(n).data(x).to(o),t.preventDefault()}}},s(r,null,[{key:"VERSION",get(){return"4.3.1"}},{key:"Default",get(){return R}}]),r}();p(document).on(q.CLICK_DATA_API,ot,at._dataApiClickHandler),p(window).on(q.LOAD_DATA_API,()=> {for(let t=[].slice.call(document.querySelectorAll(rt)),e=0,n=t.length;e<n;e++){const i=p(t[e]);at._jQueryInterface.call(i,i.data())}}),p.fn[L]=at._jQueryInterface,p.fn[L].Constructor=at,p.fn[L].noConflict=function(){return p.fn[L]=j,at._jQueryInterface};const lt="collapse"; const ct="bs.collapse"; const ht="."+ct; const ut=p.fn[lt]; const ft={toggle:!0,parent:""}; const dt={toggle:"boolean",parent:"(string|element)"}; const pt={SHOW:"show"+ht,SHOWN:"shown"+ht,HIDE:"hide"+ht,HIDDEN:"hidden"+ht,CLICK_DATA_API:"click"+ht+".data-api"}; const mt="show"; const gt="collapse"; const _t="collapsing"; const vt="collapsed"; const yt="width"; const Et="height"; const bt=".show, .collapsing"; const wt='[data-toggle="collapse"]'; const Ct=function(){function a(e,t){this._isTransitioning=!1,this._element=e,this._config=this._getConfig(t),this._triggerArray=[].slice.call(document.querySelectorAll('[data-toggle="collapse"][href="#'+e.id+'"],[data-toggle="collapse"][data-target="#'+e.id+'"]'));for(let n=[].slice.call(document.querySelectorAll(wt)),i=0,o=n.length;i<o;i++){const r=n[i]; const s=m.getSelectorFromElement(r); const a=[].slice.call(document.querySelectorAll(s)).filter((t)=> {return t===e});null!==s&&!!a.length&&(this._selector=s,this._triggerArray.push(r))}this._parent=this._config.parent?this._getParent():null,this._config.parent||this._addAriaAndCollapsedClass(this._element,this._triggerArray),this._config.toggle&&this.toggle()}const t=a.prototype;

return t.toggle=function(){p(this._element).hasClass(mt)?this.hide():this.show()},t.show=function(){let t; let e; const n=this;if(!this._isTransitioning&&!p(this._element).hasClass(mt)&&(this._parent&&!(t=[].slice.call(this._parent.querySelectorAll(bt)).filter((t)=> {return"string"===typeof n._config.parent?t.dataset.parent===n._config.parent:t.classList.contains(gt)})).length&&(t=null),!(t&&(e=p(t).not(this._selector).data(ct))&&e._isTransitioning))){const i=p.Event(pt.SHOW);if(p(this._element).trigger(i),!i.isDefaultPrevented()){t&&(a._jQueryInterface.call(p(t).not(this._selector),"hide"),e||p(t).data(ct,null));const o=this._getDimension();p(this._element).removeClass(gt).addClass(_t),this._element.style[o]=0,this._triggerArray.length&&p(this._triggerArray).removeClass(vt).attr("aria-expanded",!0),this.setTransitioning(!0);const r="scroll"+(o[0].toUpperCase()+o.slice(1)); const s=m.getTransitionDurationFromElement(this._element);p(this._element).one(m.TRANSITION_END,()=> {p(n._element).removeClass(_t).addClass(gt).addClass(mt),n._element.style[o]="",n.setTransitioning(!1),p(n._element).trigger(pt.SHOWN)}).emulateTransitionEnd(s),this._element.style[o]=this._element[r]+"px"}}},t.hide=function(){const t=this;if(!this._isTransitioning&&p(this._element).hasClass(mt)){const e=p.Event(pt.HIDE);if(p(this._element).trigger(e),!e.isDefaultPrevented()){const n=this._getDimension();this._element.style[n]=this._element.getBoundingClientRect()[n]+"px",m.reflow(this._element),p(this._element).addClass(_t).removeClass(gt).removeClass(mt);const i=this._triggerArray.length;if(0<i){for(let o=0;o<i;o++){const r=this._triggerArray[o]; const s=m.getSelectorFromElement(r);if(null!==s){p([].slice.call(document.querySelectorAll(s))).hasClass(mt)||p(r).addClass(vt).attr("aria-expanded",!1)}}}this.setTransitioning(!0);this._element.style[n]="";const a=m.getTransitionDurationFromElement(this._element);p(this._element).one(m.TRANSITION_END,()=> {t.setTransitioning(!1),p(t._element).removeClass(_t).addClass(gt).trigger(pt.HIDDEN)}).emulateTransitionEnd(a)}}},t.setTransitioning=function(t){this._isTransitioning=t},t.dispose=function(){p.removeData(this._element,ct),this._config=null,this._parent=null,this._element=null,this._triggerArray=null,this._isTransitioning=null},t._getConfig=function(t){return(t=l({},ft,t)).toggle=Boolean(t.toggle),m.typeCheckConfig(lt,t,dt),t},t._getDimension=function(){return p(this._element).hasClass(yt)?yt:Et},t._getParent=function(){let t; const n=this;m.isElement(this._config.parent)?(t=this._config.parent,"undefined"!==typeof this._config.parent.jquery&&(t=this._config.parent[0])):t=document.querySelector(this._config.parent);const e='[data-toggle="collapse"][data-parent="'+this._config.parent+'"]'; const i=[].slice.call(t.querySelectorAll(e));

return p(i).each((t,e)=> {n._addAriaAndCollapsedClass(a._getTargetFromElement(e),[e])}),t},t._addAriaAndCollapsedClass=function(t,e){const n=p(t).hasClass(mt);e.length&&p(e).toggleClass(vt,!n).attr("aria-expanded",n)},a._getTargetFromElement=function(t){const e=m.getSelectorFromElement(t);

return e?document.querySelector(e):null},a._jQueryInterface=function(i){return this.each(function(){const t=p(this); let e=t.data(ct); const n=l({},ft,t.data(),"object"===typeof i&&i?i:{});if(!e&&n.toggle&&/show|hide/.test(i)&&(n.toggle=!1),e||(e=new a(this,n),t.data(ct,e)),"string"===typeof i){if("undefined"===typeof e[i]){throw new TypeError('No method named "'+i+'"');}e[i]()}})},s(a,null,[{key:"VERSION",get(){return"4.3.1"}},{key:"Default",get(){return ft}}]),a}();p(document).on(pt.CLICK_DATA_API,wt,function(t){"A"===t.currentTarget.tagName&&t.preventDefault();const n=p(this); const e=m.getSelectorFromElement(this); const i=[].slice.call(document.querySelectorAll(e));p(i).each(function(){const t=p(this); const e=t.data(ct)?"toggle":n.data();Ct._jQueryInterface.call(t,e)})}),p.fn[lt]=Ct._jQueryInterface,p.fn[lt].Constructor=Ct,p.fn[lt].noConflict=function(){return p.fn[lt]=ut,Ct._jQueryInterface};for(var Tt="undefined"!==typeof window&&"undefined"!==typeof document,St=["Edge","Trident","Firefox"],Dt=0,It=0;It<St.length;It+=1){if(Tt&&0<=navigator.userAgent.indexOf(St[It])){Dt=1;break}}const At=Tt&&window.Promise?function(t){let e=!1;

return function(){e||(e=!0,window.Promise.resolve().then(()=> {e=!1,t()}))}}:function(t){let e=!1;

return function(){e||(e=!0,setTimeout(()=> {e=!1,t()},Dt))}};function Ot(t){return t&&"[object Function]"==={}.toString.call(t)}function Nt(t,e){if(1!==t.nodeType){return[];}const n=t.ownerDocument.defaultView.getComputedStyle(t,null);

return e?n[e]:n}function kt(t){return"HTML"===t.nodeName?t:t.parentNode||t.host}function Lt(t){if(!t){return document.body;}switch(t.nodeName){case"HTML":case"BODY":return t.ownerDocument.body;case"#document":return t.body}const e=Nt(t); const n=e.overflow; const i=e.overflowX; const o=e.overflowY;

return/(auto|scroll|overlay)/.test(n+o+i)?t:Lt(kt(t))}const xt=Tt&&!(!window.MSInputMethodContext||!document.documentMode); const Pt=Tt&&/MSIE 10/.test(navigator.userAgent);function Ht(t){return 11===t?xt:10===t?Pt:xt||Pt}function jt(t){if(!t){return document.documentElement;}for(var e=Ht(10)?document.body:null,n=t.offsetParent||null;n===e&&t.nextElementSibling;){n=(t=t.nextElementSibling).offsetParent;}const i=n&&n.nodeName;

return i&&"BODY"!==i&&"HTML"!==i?-1!==["TH","TD","TABLE"].indexOf(n.nodeName)&&"static"===Nt(n,"position")?jt(n):n:t?t.ownerDocument.documentElement:document.documentElement}function Rt(t){return null!==t.parentNode?Rt(t.parentNode):t}function Ft(t,e){if(!(t&&t.nodeType&&e&&e.nodeType)){return document.documentElement;}const n=t.compareDocumentPosition(e)&Node.DOCUMENT_POSITION_FOLLOWING; const i=n?t:e; const o=n?e:t; const r=document.createRange();r.setStart(i,0),r.setEnd(o,0);let s; let a; const l=r.commonAncestorContainer;if(t!==l&&e!==l||i.contains(o)){return"BODY"===(a=(s=l).nodeName)||"HTML"!==a&&jt(s.firstElementChild)!==s?jt(l):l;}const c=Rt(t);

return c.host?Ft(c.host,e):Ft(t,Rt(e).host)}function Mt(t){const e="top"===(1<arguments.length&&void 0!==arguments[1]?arguments[1]:"top")?"scrollTop":"scrollLeft"; const n=t.nodeName;if("BODY"!==n&&"HTML"!==n){return t[e];}const i=t.ownerDocument.documentElement;

return(t.ownerDocument.scrollingElement||i)[e]}function Wt(t,e){const n="x"===e?"Left":"Top"; const i="Left"===n?"Right":"Bottom";

return parseFloat(t["border"+n+"Width"],10)+parseFloat(t["border"+i+"Width"],10)}function Ut(t,e,n,i){return Math.max(e["offset"+t],e["scroll"+t],n["client"+t],n["offset"+t],n["scroll"+t],Ht(10)?parseInt(n["offset"+t])+parseInt(i["margin"+("Height"===t?"Top":"Left")])+parseInt(i["margin"+("Height"===t?"Bottom":"Right")]):0)}function Bt(t){const e=t.body; const n=t.documentElement; const i=Ht(10)&&getComputedStyle(n);

return{height:Ut("Height",e,n,i),width:Ut("Width",e,n,i)}}const qt=function(){function i(t,e){for(let n=0;n<e.length;n++){const i=e[n];i.enumerable=i.enumerable||!1,i.configurable=!0,"value"in i&&(i.writable=!0),Object.defineProperty(t,i.key,i)}}

return function(t,e,n){return e&&i(t.prototype,e),n&&i(t,n),t}}(); const Kt=function(t,e,n){return e in t?Object.defineProperty(t,e,{value:n,enumerable:!0,configurable:!0,writable:!0}):t[e]=n,t}; const Qt=Object.assign||function(t){for(let e=1;e<arguments.length;e++){const n=arguments[e];for(const i in n){Object.prototype.hasOwnProperty.call(n,i)&&(t[i]=n[i])}}

return t};function Vt(t){return {...t,right:t.left+t.width,bottom:t.top+t.height}}function Yt(t){let e={};try{if(Ht(10)){e=t.getBoundingClientRect();const n=Mt(t,"top"); const i=Mt(t,"left");e.top+=n,e.left+=i,e.bottom+=n,e.right+=i}else {e=t.getBoundingClientRect()}}catch(t){}const o={left:e.left,top:e.top,width:e.right-e.left,height:e.bottom-e.top}; const r="HTML"===t.nodeName?Bt(t.ownerDocument):{}; const s=r.width||t.clientWidth||o.right-o.left; const a=r.height||t.clientHeight||o.bottom-o.top; let l=t.offsetWidth-s; let c=t.offsetHeight-a;if(l||c){const h=Nt(t);l-=Wt(h,"x"),c-=Wt(h,"y"),o.width-=l,o.height-=c}

return Vt(o)}function zt(t,e){const n=2<arguments.length&&void 0!==arguments[2]&&arguments[2]; const i=Ht(10); const o="HTML"===e.nodeName; const r=Yt(t); const s=Yt(e); const a=Lt(t); const l=Nt(e); const c=parseFloat(l.borderTopWidth,10); const h=parseFloat(l.borderLeftWidth,10);n&&o&&(s.top=Math.max(s.top,0),s.left=Math.max(s.left,0));let u=Vt({top:r.top-s.top-c,left:r.left-s.left-h,width:r.width,height:r.height});if(u.marginTop=0,u.marginLeft=0,!i&&o){const f=parseFloat(l.marginTop,10); const d=parseFloat(l.marginLeft,10);u.top-=c-f,u.bottom-=c-f,u.left-=h-d,u.right-=h-d,u.marginTop=f,u.marginLeft=d}

return(i&&!n?e.contains(a):e===a&&"BODY"!==a.nodeName)&&(u=function(t,e){const n=2<arguments.length&&void 0!==arguments[2]&&arguments[2]; const i=Mt(e,"top"); const o=Mt(e,"left"); const r=n?-1:1;

return t.top+=i*r,t.bottom+=i*r,t.left+=o*r,t.right+=o*r,t}(u,e)),u}function Xt(t){if(!t||!t.parentElement||Ht()){return document.documentElement;}for(var e=t.parentElement;e&&"none"===Nt(e,"transform");){e=e.parentElement;}

return e||document.documentElement}function Gt(t,e,n,i){const o=4<arguments.length&&void 0!==arguments[4]&&arguments[4]; let r={top:0,left:0}; const s=o?Xt(t):Ft(t,e);if("viewport"===i){r=function(t){const e=1<arguments.length&&void 0!==arguments[1]&&arguments[1]; const n=t.ownerDocument.documentElement; const i=zt(t,n); const o=Math.max(n.clientWidth,window.innerWidth||0); const r=Math.max(n.clientHeight,window.innerHeight||0); const s=e?0:Mt(n); const a=e?0:Mt(n,"left");

return Vt({top:s-i.top+i.marginTop,left:a-i.left+i.marginLeft,width:o,height:r})}(s,o);}else{let a=void 0;"scrollParent"===i?"BODY"===(a=Lt(kt(e))).nodeName&&(a=t.ownerDocument.documentElement):a="window"===i?t.ownerDocument.documentElement:i;const l=zt(a,s,o);if("HTML"!==a.nodeName||function t(e){const n=e.nodeName;if("BODY"===n||"HTML"===n){return!1;}if("fixed"===Nt(e,"position")){return!0;}const i=kt(e);

return!!i&&t(i)}(s)){r=l;}else{const c=Bt(t.ownerDocument); const h=c.height; const u=c.width;r.top+=l.top-l.marginTop,r.bottom=h+l.top,r.left+=l.left-l.marginLeft,r.right=u+l.left}}const f="number"===typeof(n=n||0);

return r.left+=f?n:n.left||0,r.top+=f?n:n.top||0,r.right-=f?n:n.right||0,r.bottom-=f?n:n.bottom||0,r}function $t(t,e,i,n,o){const r=5<arguments.length&&void 0!==arguments[5]?arguments[5]:0;if(-1===t.indexOf("auto")){return t;}const s=Gt(i,n,r,o); const a={top:{width:s.width,height:e.top-s.top},right:{width:s.right-e.right,height:s.height},bottom:{width:s.width,height:s.bottom-e.bottom},left:{width:e.left-s.left,height:s.height}}; const l=Object.keys(a).map((t)=> {return {key:t,...a[t],area:(e=a[t],e.width*e.height)};let e}).sort((t,e)=> {return e.area-t.area}); const c=l.filter((t)=> {const e=t.width; const n=t.height;

return e>=i.clientWidth&&n>=i.clientHeight}); const h=c.length?c[0].key:l[0].key; const u=t.split("-")[1];

return h+(u?"-"+u:"")}function Jt(t,e,n){const i=3<arguments.length&&void 0!==arguments[3]?arguments[3]:null;

return zt(n,i?Xt(e):Ft(e,n),i)}function Zt(t){const e=t.ownerDocument.defaultView.getComputedStyle(t); const n=parseFloat(e.marginTop||0)+parseFloat(e.marginBottom||0); const i=parseFloat(e.marginLeft||0)+parseFloat(e.marginRight||0);

return{width:t.offsetWidth+i,height:t.offsetHeight+n}}function te(t){const e={left:"right",right:"left",bottom:"top",top:"bottom"};

return t.replace(/left|right|bottom|top/g,(t)=> {return e[t]})}function ee(t,e,n){n=n.split("-")[0];const i=Zt(t); const o={width:i.width,height:i.height}; const r=-1!==["right","left"].indexOf(n); const s=r?"top":"left"; const a=r?"left":"top"; const l=r?"height":"width"; const c=r?"width":"height";

return o[s]=e[s]+e[l]/2-i[l]/2,o[a]=n===a?e[a]-i[c]:e[te(a)],o}function ne(t,e){return Array.prototype.find?t.find(e):t.filter(e)[0]}function ie(t,n,e){return(void 0===e?t:t.slice(0,function(t,e,n){if(Array.prototype.findIndex){return t.findIndex((t)=> {return t[e]===n});}const i=ne(t,(t)=> {return t[e]===n});

return t.indexOf(i)}(t,"name",e))).forEach((t)=> {t.function&&console.warn("`modifier.function` is deprecated, use `modifier.fn`!");const e=t.function||t.fn;t.enabled&&Ot(e)&&(n.offsets.popper=Vt(n.offsets.popper),n.offsets.reference=Vt(n.offsets.reference),n=e(n,t))}),n}function oe(t,n){return t.some((t)=> {const e=t.name;

return t.enabled&&e===n})}function re(t){for(let e=[!1,"ms","Webkit","Moz","O"],n=t.charAt(0).toUpperCase()+t.slice(1),i=0;i<e.length;i++){const o=e[i]; const r=o?""+o+n:t;if("undefined"!==typeof document.body.style[r]){return r}}

return null}function se(t){const e=t.ownerDocument;

return e?e.defaultView:window}function ae(t,e,n,i){n.updateBound=i,se(t).addEventListener("resize",n.updateBound,{passive:!0});const o=Lt(t);

return function t(e,n,i,o){const r="BODY"===e.nodeName; const s=r?e.ownerDocument.defaultView:e;s.addEventListener(n,i,{passive:!0}),r||t(Lt(s.parentNode),n,i,o),o.push(s)}(o,"scroll",n.updateBound,n.scrollParents),n.scrollElement=o,n.eventsEnabled=!0,n}function le(){let t; let e;this.state.eventsEnabled&&(cancelAnimationFrame(this.scheduleUpdate),this.state=(t=this.reference,e=this.state,se(t).removeEventListener("resize",e.updateBound),e.scrollParents.forEach((t)=> {t.removeEventListener("scroll",e.updateBound)}),e.updateBound=null,e.scrollParents=[],e.scrollElement=null,e.eventsEnabled=!1,e))}function ce(t){return""!==t&&!isNaN(parseFloat(t))&&isFinite(t)}function he(n,i){Object.keys(i).forEach((t)=> {let e="";-1!==["width","height","top","right","bottom","left"].indexOf(t)&&ce(i[t])&&(e="px"),n.style[t]=i[t]+e})}const ue=Tt&&/Firefox/i.test(navigator.userAgent);function fe(t,e,n){const i=ne(t,(t)=> {return t.name===e}); const o=!!i&&t.some((t)=> {return t.name===n&&t.enabled&&t.order<i.order});if(!o){const r="`"+e+"`"; const s="`"+n+"`";console.warn(s+" modifier is required by "+r+" modifier in order to work, be sure to include it before "+r+"!")}

return o}const de=["auto-start","auto","auto-end","top-start","top","top-end","right-start","right","right-end","bottom-end","bottom","bottom-start","left-end","left","left-start"]; const pe=de.slice(3);function me(t){const e=1<arguments.length&&void 0!==arguments[1]&&arguments[1]; const n=pe.indexOf(t); const i=pe.slice(n+1).concat(pe.slice(0,n));

return e?i.reverse():i}const ge="flip"; const _e="clockwise"; const ve="counterclockwise";function ye(t,o,r,e){const s=[0,0]; const a=-1!==["right","left"].indexOf(e); const n=t.split(/(\+|\-)/).map((t)=> {return t.trim()}); const i=n.indexOf(ne(n,(t)=> {return-1!==t.search(/,|\s/)}));n[i]&&-1===n[i].indexOf(",")&&console.warn("Offsets separated by white space(s) are deprecated, use a comma (,) instead.");const l=/\s*,\s*|\s+/; let c=-1!==i?[n.slice(0,i).concat([n[i].split(l)[0]]),[n[i].split(l)[1]].concat(n.slice(i+1))]:[n];

return(c=c.map((t,e)=> {const n=(1===e?!a:a)?"height":"width"; let i=!1;

return t.reduce((t,e)=> {return""===t[t.length-1]&&-1!==["+","-"].indexOf(e)?(t[t.length-1]=e,i=!0,t):i?(t[t.length-1]+=e,i=!1,t):t.concat(e)},[]).map((t)=> {return function(t,e,n,i){const o=t.match(/((?:\-|\+)?\d*\.?\d*)(.*)/); const r=+o[1]; const s=o[2];if(!r){return t;}if(0!==s.indexOf("%")){return"vh"!==s&&"vw"!==s?r:("vh"===s?Math.max(document.documentElement.clientHeight,window.innerHeight||0):Math.max(document.documentElement.clientWidth,window.innerWidth||0))/100*r;}let a=void 0;switch(s){case"%p":a=n;break;case"%":case"%r":default:a=i}

return Vt(a)[e]/100*r}(t,n,o,r)})})).forEach((n,i)=> {n.forEach((t,e)=> {ce(t)&&(s[i]+=t*("-"===n[e-1]?-1:1))})}),s}const Ee={placement:"bottom",positionFixed:!1,eventsEnabled:!0,removeOnDestroy:!1,onCreate(){},onUpdate(){},modifiers:{shift:{order:100,enabled:!0,fn(t){const e=t.placement; const n=e.split("-")[0]; const i=e.split("-")[1];if(i){const o=t.offsets; const r=o.reference; const s=o.popper; const a=-1!==["bottom","top"].indexOf(n); const l=a?"left":"top"; const c=a?"width":"height"; const h={start:Kt({},l,r[l]),end:Kt({},l,r[l]+r[c]-s[c])};t.offsets.popper={...s,...h[i]}}

return t}},offset:{order:200,enabled:!0,fn(t,e){const n=e.offset; const i=t.placement; const o=t.offsets; const r=o.popper; const s=o.reference; const a=i.split("-")[0]; let l=void 0;

return l=ce(+n)?[+n,0]:ye(n,r,s,a),"left"===a?(r.top+=l[0],r.left-=l[1]):"right"===a?(r.top+=l[0],r.left+=l[1]):"top"===a?(r.left+=l[0],r.top-=l[1]):"bottom"===a&&(r.left+=l[0],r.top+=l[1]),t.popper=r,t},offset:0},preventOverflow:{order:300,enabled:!0,fn(t,i){let e=i.boundariesElement||jt(t.instance.popper);t.instance.reference===e&&(e=jt(e));const n=re("transform"); const o=t.instance.popper.style; const r=o.top; const s=o.left; const a=o[n];o.top="",o.left="",o[n]="";const l=Gt(t.instance.popper,t.instance.reference,i.padding,e,t.positionFixed);o.top=r,o.left=s,o[n]=a,i.boundaries=l;const c=i.priority; let h=t.offsets.popper; const u={primary(t){let e=h[t];

return h[t]<l[t]&&!i.escapeWithReference&&(e=Math.max(h[t],l[t])),Kt({},t,e)},secondary(t){const e="right"===t?"left":"top"; let n=h[e];

return h[t]>l[t]&&!i.escapeWithReference&&(n=Math.min(h[e],l[t]-("right"===t?h.width:h.height))),Kt({},e,n)}};

return c.forEach((t)=> {const e=-1!==["left","top"].indexOf(t)?"primary":"secondary";h={...h,...u[e](t)}}),t.offsets.popper=h,t},priority:["left","right","top","bottom"],padding:5,boundariesElement:"scrollParent"},keepTogether:{order:400,enabled:!0,fn(t){const e=t.offsets; const n=e.popper; const i=e.reference; const o=t.placement.split("-")[0]; const r=Math.floor; const s=-1!==["top","bottom"].indexOf(o); const a=s?"right":"bottom"; const l=s?"left":"top"; const c=s?"width":"height";

return n[a]<r(i[l])&&(t.offsets.popper[l]=r(i[l])-n[c]),n[l]>r(i[a])&&(t.offsets.popper[l]=r(i[a])),t}},arrow:{order:500,enabled:!0,fn(t,e){let n;if(!fe(t.instance.modifiers,"arrow","keepTogether")){return t;}let i=e.element;if("string"===typeof i){if(!(i=t.instance.popper.querySelector(i))){return t}}else if(!t.instance.popper.contains(i)){return console.warn("WARNING: `arrow.element` must be child of its popper element!"),t;}const o=t.placement.split("-")[0]; const r=t.offsets; const s=r.popper; const a=r.reference; const l=-1!==["left","right"].indexOf(o); const c=l?"height":"width"; const h=l?"Top":"Left"; const u=h.toLowerCase(); const f=l?"left":"top"; const d=l?"bottom":"right"; const p=Zt(i)[c];a[d]-p<s[u]&&(t.offsets.popper[u]-=s[u]-(a[d]-p)),a[u]+p>s[d]&&(t.offsets.popper[u]+=a[u]+p-s[d]),t.offsets.popper=Vt(t.offsets.popper);const m=a[u]+a[c]/2-p/2; const g=Nt(t.instance.popper); const _=parseFloat(g["margin"+h],10); const v=parseFloat(g["border"+h+"Width"],10); let y=m-t.offsets.popper[u]-_-v;

return y=Math.max(Math.min(s[c]-p,y),0),t.arrowElement=i,t.offsets.arrow=(Kt(n={},u,Math.round(y)),Kt(n,f,""),n),t},element:"[x-arrow]"},flip:{order:600,enabled:!0,fn(p,m){if(oe(p.instance.modifiers,"inner")){return p;}if(p.flipped&&p.placement===p.originalPlacement){return p;}const g=Gt(p.instance.popper,p.instance.reference,m.padding,m.boundariesElement,p.positionFixed); let _=p.placement.split("-")[0]; let v=te(_); let y=p.placement.split("-")[1]||""; let E=[];switch(m.behavior){case ge:E=[_,v];break;case _e:E=me(_);break;case ve:E=me(_,!0);break;default:E=m.behavior}

return E.forEach((t,e)=> {if(_!==t||E.length===e+1){return p;}_=p.placement.split("-")[0],v=te(_);let n; const i=p.offsets.popper; const o=p.offsets.reference; const r=Math.floor; const s="left"===_&&r(i.right)>r(o.left)||"right"===_&&r(i.left)<r(o.right)||"top"===_&&r(i.bottom)>r(o.top)||"bottom"===_&&r(i.top)<r(o.bottom); const a=r(i.left)<r(g.left); const l=r(i.right)>r(g.right); const c=r(i.top)<r(g.top); const h=r(i.bottom)>r(g.bottom); const u="left"===_&&a||"right"===_&&l||"top"===_&&c||"bottom"===_&&h; const f=-1!==["top","bottom"].indexOf(_); const d=!!m.flipVariations&&(f&&"start"===y&&a||f&&"end"===y&&l||!f&&"start"===y&&c||!f&&"end"===y&&h);(s||u||d)&&(p.flipped=!0,(s||u)&&(_=E[e+1]),d&&(y="end"===(n=y)?"start":"start"===n?"end":n),p.placement=_+(y?"-"+y:""),p.offsets.popper={...p.offsets.popper,...ee(p.instance.popper,p.offsets.reference,p.placement)},p=ie(p.instance.modifiers,p,"flip"))}),p},behavior:"flip",padding:5,boundariesElement:"viewport"},inner:{order:700,enabled:!1,fn(t){const e=t.placement; const n=e.split("-")[0]; const i=t.offsets; const o=i.popper; const r=i.reference; const s=-1!==["left","right"].indexOf(n); const a=-1===["top","left"].indexOf(n);

return o[s?"left":"top"]=r[n]-(a?o[s?"width":"height"]:0),t.placement=te(e),t.offsets.popper=Vt(o),t}},hide:{order:800,enabled:!0,fn(t){if(!fe(t.instance.modifiers,"hide","preventOverflow")){return t;}const e=t.offsets.reference; const n=ne(t.instance.modifiers,(t)=> {return"preventOverflow"===t.name}).boundaries;if(e.bottom<n.top||e.left>n.right||e.top>n.bottom||e.right<n.left){if(!0===t.hide){return t;}t.hide=!0,t.attributes["x-out-of-boundaries"]=""}else{if(!1===t.hide){return t;}t.hide=!1,t.attributes["x-out-of-boundaries"]=!1}

return t}},computeStyle:{order:850,enabled:!0,fn(t,e){const n=e.x; const i=e.y; const o=t.offsets.popper; const r=ne(t.instance.modifiers,(t)=> {return"applyStyle"===t.name}).gpuAcceleration;void 0!==r&&console.warn("WARNING: `gpuAcceleration` option moved to `computeStyle` modifier and will not be supported in future versions of Popper.js!");let s; let a; let l; let c; let h; let u; let f; let d; let p; let m; let g; let _; let v; let y; const E=void 0!==r?r:e.gpuAcceleration; const b=jt(t.instance.popper); const w=Yt(b); const C={position:o.position}; const T=(s=t,a=window.devicePixelRatio<2||!ue,l=s.offsets,c=l.popper,h=l.reference,u=Math.round,f=Math.floor,d=function(t){return t},p=u(h.width),m=u(c.width),g=-1!==["left","right"].indexOf(s.placement),_=-1!==s.placement.indexOf("-"),y=a?u:d,{left:(v=a?g||_||p%2==m%2?u:f:d)(p%2==1&&m%2==1&&!_&&a?c.left-1:c.left),top:y(c.top),bottom:y(c.bottom),right:v(c.right)}); const S="bottom"===n?"top":"bottom"; const D="right"===i?"left":"right"; const I=re("transform"); let A=void 0; let O=void 0;if(O="bottom"===S?"HTML"===b.nodeName?-b.clientHeight+T.bottom:-w.height+T.bottom:T.top,A="right"===D?"HTML"===b.nodeName?-b.clientWidth+T.right:-w.width+T.right:T.left,E&&I){C[I]="translate3d("+A+"px, "+O+"px, 0)",C[S]=0,C[D]=0,C.willChange="transform";}else{const N="bottom"===S?-1:1; const k="right"===D?-1:1;C[S]=O*N,C[D]=A*k,C.willChange=S+", "+D}const L={"x-placement":t.placement};

return t.attributes={...L,...t.attributes},t.styles={...C,...t.styles},t.arrowStyles={...t.offsets.arrow,...t.arrowStyles},t},gpuAcceleration:!0,x:"bottom",y:"right"},applyStyle:{order:900,enabled:!0,fn(t){let e; let n;

return he(t.instance.popper,t.styles),e=t.instance.popper,n=t.attributes,Object.keys(n).forEach((t)=> {!1!==n[t]?e.setAttribute(t,n[t]):e.removeAttribute(t)}),t.arrowElement&&Object.keys(t.arrowStyles).length&&he(t.arrowElement,t.arrowStyles),t},onLoad(t,e,n,i,o){const r=Jt(o,e,t,n.positionFixed); const s=$t(n.placement,r,e,t,n.modifiers.flip.boundariesElement,n.modifiers.flip.padding);

return e.setAttribute("x-placement",s),he(e,{position:n.positionFixed?"fixed":"absolute"}),n},gpuAcceleration:void 0}}}; const be=function(){function r(t,e){const n=this; const i=2<arguments.length&&void 0!==arguments[2]?arguments[2]:{};!function(t,e){if(!(t instanceof e)){throw new TypeError("Cannot call a class as a function")}}(this,r),this.scheduleUpdate=function(){return requestAnimationFrame(n.update)},this.update=At(this.update.bind(this)),this.options={...r.Defaults,...i},this.state={isDestroyed:!1,isCreated:!1,scrollParents:[]},this.reference=t&&t.jquery?t[0]:t,this.popper=e&&e.jquery?e[0]:e,this.options.modifiers={},Object.keys({...r.Defaults.modifiers,...i.modifiers}).forEach((t)=> {n.options.modifiers[t]={...r.Defaults.modifiers[t]||{},...(i.modifiers?i.modifiers[t]:{})}}),this.modifiers=Object.keys(this.options.modifiers).map((t)=> {return {name:t,...n.options.modifiers[t]}}).sort((t,e)=> {return t.order-e.order}),this.modifiers.forEach((t)=> {t.enabled&&Ot(t.onLoad)&&t.onLoad(n.reference,n.popper,n.options,t,n.state)}),this.update();const o=this.options.eventsEnabled;o&&this.enableEventListeners(),this.state.eventsEnabled=o}

return qt(r,[{key:"update",value(){return function(){if(!this.state.isDestroyed){let t={instance:this,styles:{},arrowStyles:{},attributes:{},flipped:!1,offsets:{}};t.offsets.reference=Jt(this.state,this.popper,this.reference,this.options.positionFixed),t.placement=$t(this.options.placement,t.offsets.reference,this.popper,this.reference,this.options.modifiers.flip.boundariesElement,this.options.modifiers.flip.padding),t.originalPlacement=t.placement,t.positionFixed=this.options.positionFixed,t.offsets.popper=ee(this.popper,t.offsets.reference,t.placement),t.offsets.popper.position=this.options.positionFixed?"fixed":"absolute",t=ie(this.modifiers,t),this.state.isCreated?this.options.onUpdate(t):(this.state.isCreated=!0,this.options.onCreate(t))}}.call(this)}},{key:"destroy",value(){return function(){return this.state.isDestroyed=!0,oe(this.modifiers,"applyStyle")&&(this.popper.removeAttribute("x-placement"),this.popper.style.position="",this.popper.style.top="",this.popper.style.left="",this.popper.style.right="",this.popper.style.bottom="",this.popper.style.willChange="",this.popper.style[re("transform")]=""),this.disableEventListeners(),this.options.removeOnDestroy&&this.popper.parentNode.removeChild(this.popper),this}.call(this)}},{key:"enableEventListeners",value(){return function(){this.state.eventsEnabled||(this.state=ae(this.reference,this.options,this.state,this.scheduleUpdate))}.call(this)}},{key:"disableEventListeners",value(){return le.call(this)}}]),r}();be.Utils=("undefined"!==typeof window?window:global).PopperUtils,be.placements=de,be.Defaults=Ee;const we="dropdown"; const Ce="bs.dropdown"; const Te="."+Ce; const Se=".data-api"; const De=p.fn[we]; const Ie=new RegExp("38|40|27"); const Ae={HIDE:"hide"+Te,HIDDEN:"hidden"+Te,SHOW:"show"+Te,SHOWN:"shown"+Te,CLICK:"click"+Te,CLICK_DATA_API:"click"+Te+Se,KEYDOWN_DATA_API:"keydown"+Te+Se,KEYUP_DATA_API:"keyup"+Te+Se}; const Oe="disabled"; const Ne="show"; const ke="dropup"; const Le="dropright"; const xe="dropleft"; const Pe="dropdown-menu-right"; const He="position-static"; const je='[data-toggle="dropdown"]'; const Re=".dropdown form"; const Fe=".dropdown-menu"; const Me=".navbar-nav"; const We=".dropdown-menu .dropdown-item:not(.disabled):not(:disabled)"; const Ue="top-start"; const Be="top-end"; const qe="bottom-start"; const Ke="bottom-end"; const Qe="right-start"; const Ve="left-start"; const Ye={offset:0,flip:!0,boundary:"scrollParent",reference:"toggle",display:"dynamic"}; const ze={offset:"(number|string|function)",flip:"boolean",boundary:"(string|element)",reference:"(string|element)",display:"string"}; const Xe=function(){function c(t,e){this._element=t,this._popper=null,this._config=this._getConfig(e),this._menu=this._getMenuElement(),this._inNavbar=this._detectNavbar(),this._addEventListeners()}const t=c.prototype;

return t.toggle=function(){if(!this._element.disabled&&!p(this._element).hasClass(Oe)){const t=c._getParentFromElement(this._element); const e=p(this._menu).hasClass(Ne);if(c._clearMenus(),!e){const n={relatedTarget:this._element}; const i=p.Event(Ae.SHOW,n);if(p(t).trigger(i),!i.isDefaultPrevented()){if(!this._inNavbar){if("undefined"===typeof be){throw new TypeError("Bootstrap's dropdowns require Popper.js (https://popper.js.org/)");}let o=this._element;"parent"===this._config.reference?o=t:m.isElement(this._config.reference)&&(o=this._config.reference,"undefined"!==typeof this._config.reference.jquery&&(o=this._config.reference[0])),"scrollParent"!==this._config.boundary&&p(t).addClass(He),this._popper=new be(o,this._menu,this._getPopperConfig())}"ontouchstart"in document.documentElement&&!p(t).closest(Me).length&&p(document.body).children().on("mouseover",null,p.noop),this._element.focus(),this._element.setAttribute("aria-expanded",!0),p(this._menu).toggleClass(Ne),p(t).toggleClass(Ne).trigger(p.Event(Ae.SHOWN,n))}}}},t.show=function(){if(!(this._element.disabled||p(this._element).hasClass(Oe)||p(this._menu).hasClass(Ne))){const t={relatedTarget:this._element}; const e=p.Event(Ae.SHOW,t); const n=c._getParentFromElement(this._element);p(n).trigger(e),e.isDefaultPrevented()||(p(this._menu).toggleClass(Ne),p(n).toggleClass(Ne).trigger(p.Event(Ae.SHOWN,t)))}},t.hide=function(){if(!this._element.disabled&&!p(this._element).hasClass(Oe)&&p(this._menu).hasClass(Ne)){const t={relatedTarget:this._element}; const e=p.Event(Ae.HIDE,t); const n=c._getParentFromElement(this._element);p(n).trigger(e),e.isDefaultPrevented()||(p(this._menu).toggleClass(Ne),p(n).toggleClass(Ne).trigger(p.Event(Ae.HIDDEN,t)))}},t.dispose=function(){p.removeData(this._element,Ce),p(this._element).off(Te),this._element=null,(this._menu=null)!==this._popper&&(this._popper.destroy(),this._popper=null)},t.update=function(){this._inNavbar=this._detectNavbar(),null!==this._popper&&this._popper.scheduleUpdate()},t._addEventListeners=function(){const e=this;p(this._element).on(Ae.CLICK,(t)=> {t.preventDefault(),t.stopPropagation(),e.toggle()})},t._getConfig=function(t){return t=l({},this.constructor.Default,p(this._element).data(),t),m.typeCheckConfig(we,t,this.constructor.DefaultType),t},t._getMenuElement=function(){if(!this._menu){const t=c._getParentFromElement(this._element);t&&(this._menu=t.querySelector(Fe))}

return this._menu},t._getPlacement=function(){const t=p(this._element.parentNode); let e=qe;

return t.hasClass(ke)?(e=Ue,p(this._menu).hasClass(Pe)&&(e=Be)):t.hasClass(Le)?e=Qe:t.hasClass(xe)?e=Ve:p(this._menu).hasClass(Pe)&&(e=Ke),e},t._detectNavbar=function(){return !!p(this._element).closest(".navbar").length},t._getOffset=function(){const e=this; const t={};

return"function"===typeof this._config.offset?t.fn=function(t){return t.offsets=l({},t.offsets,e._config.offset(t.offsets,e._element)||{}),t}:t.offset=this._config.offset,t},t._getPopperConfig=function(){const t={placement:this._getPlacement(),modifiers:{offset:this._getOffset(),flip:{enabled:this._config.flip},preventOverflow:{boundariesElement:this._config.boundary}}};

return"static"===this._config.display&&(t.modifiers.applyStyle={enabled:!1}),t},c._jQueryInterface=function(e){return this.each(function(){let t=p(this).data(Ce);if(t||(t=new c(this,"object"===typeof e?e:null),p(this).data(Ce,t)),"string"===typeof e){if("undefined"===typeof t[e]){throw new TypeError('No method named "'+e+'"');}t[e]()}})},c._clearMenus=function(t){if(!t||3!==t.which&&("keyup"!==t.type||9===t.which)){for(let e=[].slice.call(document.querySelectorAll(je)),n=0,i=e.length;n<i;n++){const o=c._getParentFromElement(e[n]); const r=p(e[n]).data(Ce); const s={relatedTarget:e[n]};if(t&&"click"===t.type&&(s.clickEvent=t),r){const a=r._menu;if(p(o).hasClass(Ne)&&!(t&&("click"===t.type&&/input|textarea/i.test(t.target.tagName)||"keyup"===t.type&&9===t.which)&&p.contains(o,t.target))){const l=p.Event(Ae.HIDE,s);p(o).trigger(l),l.isDefaultPrevented()||("ontouchstart"in document.documentElement&&p(document.body).children().off("mouseover",null,p.noop),e[n].setAttribute("aria-expanded","false"),p(a).removeClass(Ne),p(o).removeClass(Ne).trigger(p.Event(Ae.HIDDEN,s)))}}}}},c._getParentFromElement=function(t){let e; const n=m.getSelectorFromElement(t);

return n&&(e=document.querySelector(n)),e||t.parentNode},c._dataApiKeydownHandler=function(t){if((/input|textarea/i.test(t.target.tagName)?!(32===t.which||27!==t.which&&(40!==t.which&&38!==t.which||p(t.target).closest(Fe).length)):Ie.test(t.which))&&(t.preventDefault(),t.stopPropagation(),!this.disabled&&!p(this).hasClass(Oe))){const e=c._getParentFromElement(this); const n=p(e).hasClass(Ne);if(n&&(!n||27!==t.which&&32!==t.which)){const i=[].slice.call(e.querySelectorAll(We));if(0!==i.length){let o=i.indexOf(t.target);38===t.which&&0<o&&o--,40===t.which&&o<i.length-1&&o++,o<0&&(o=0),i[o].focus()}}else{if(27===t.which){const r=e.querySelector(je);p(r).trigger("focus")}p(this).trigger("click")}}},s(c,null,[{key:"VERSION",get(){return"4.3.1"}},{key:"Default",get(){return Ye}},{key:"DefaultType",get(){return ze}}]),c}();p(document).on(Ae.KEYDOWN_DATA_API,je,Xe._dataApiKeydownHandler).on(Ae.KEYDOWN_DATA_API,Fe,Xe._dataApiKeydownHandler).on(Ae.CLICK_DATA_API+" "+Ae.KEYUP_DATA_API,Xe._clearMenus).on(Ae.CLICK_DATA_API,je,function(t){t.preventDefault(),t.stopPropagation(),Xe._jQueryInterface.call(p(this),"toggle")}).on(Ae.CLICK_DATA_API,Re,(t)=> {t.stopPropagation()}),p.fn[we]=Xe._jQueryInterface,p.fn[we].Constructor=Xe,p.fn[we].noConflict=function(){return p.fn[we]=De,Xe._jQueryInterface};const Ge="modal"; const $e="bs.modal"; const Je="."+$e; const Ze=p.fn[Ge]; const tn={backdrop:!0,keyboard:!0,focus:!0,show:!0}; const en={backdrop:"(boolean|string)",keyboard:"boolean",focus:"boolean",show:"boolean"}; const nn={HIDE:"hide"+Je,HIDDEN:"hidden"+Je,SHOW:"show"+Je,SHOWN:"shown"+Je,FOCUSIN:"focusin"+Je,RESIZE:"resize"+Je,CLICK_DISMISS:"click.dismiss"+Je,KEYDOWN_DISMISS:"keydown.dismiss"+Je,MOUSEUP_DISMISS:"mouseup.dismiss"+Je,MOUSEDOWN_DISMISS:"mousedown.dismiss"+Je,CLICK_DATA_API:"click"+Je+".data-api"}; const on="modal-dialog-scrollable"; const rn="modal-scrollbar-measure"; const sn="modal-backdrop"; const an="modal-open"; const ln="fade"; const cn="show"; const hn=".modal-dialog"; const un=".modal-body"; const fn='[data-toggle="modal"]'; const dn='[data-dismiss="modal"]'; const pn=".fixed-top, .fixed-bottom, .is-fixed, .sticky-top"; const mn=".sticky-top"; const gn=function(){function o(t,e){this._config=this._getConfig(e),this._element=t,this._dialog=t.querySelector(hn),this._backdrop=null,this._isShown=!1,this._isBodyOverflowing=!1,this._ignoreBackdropClick=!1,this._isTransitioning=!1,this._scrollbarWidth=0}const t=o.prototype;

return t.toggle=function(t){return this._isShown?this.hide():this.show(t)},t.show=function(t){const e=this;if(!this._isShown&&!this._isTransitioning){p(this._element).hasClass(ln)&&(this._isTransitioning=!0);const n=p.Event(nn.SHOW,{relatedTarget:t});p(this._element).trigger(n),this._isShown||n.isDefaultPrevented()||(this._isShown=!0,this._checkScrollbar(),this._setScrollbar(),this._adjustDialog(),this._setEscapeEvent(),this._setResizeEvent(),p(this._element).on(nn.CLICK_DISMISS,dn,(t)=> {return e.hide(t)}),p(this._dialog).on(nn.MOUSEDOWN_DISMISS,()=> {p(e._element).one(nn.MOUSEUP_DISMISS,(t)=> {p(t.target).is(e._element)&&(e._ignoreBackdropClick=!0)})}),this._showBackdrop(()=> {return e._showElement(t)}))}},t.hide=function(t){const e=this;if(t&&t.preventDefault(),this._isShown&&!this._isTransitioning){const n=p.Event(nn.HIDE);if(p(this._element).trigger(n),this._isShown&&!n.isDefaultPrevented()){this._isShown=!1;const i=p(this._element).hasClass(ln);if(i&&(this._isTransitioning=!0),this._setEscapeEvent(),this._setResizeEvent(),p(document).off(nn.FOCUSIN),p(this._element).removeClass(cn),p(this._element).off(nn.CLICK_DISMISS),p(this._dialog).off(nn.MOUSEDOWN_DISMISS),i){const o=m.getTransitionDurationFromElement(this._element);p(this._element).one(m.TRANSITION_END,(t)=> {return e._hideModal(t)}).emulateTransitionEnd(o)}else {this._hideModal()}}}},t.dispose=function(){[window,this._element,this._dialog].forEach((t)=> {return p(t).off(Je)}),p(document).off(nn.FOCUSIN),p.removeData(this._element,$e),this._config=null,this._element=null,this._dialog=null,this._backdrop=null,this._isShown=null,this._isBodyOverflowing=null,this._ignoreBackdropClick=null,this._isTransitioning=null,this._scrollbarWidth=null},t.handleUpdate=function(){this._adjustDialog()},t._getConfig=function(t){return t=l({},tn,t),m.typeCheckConfig(Ge,t,en),t},t._showElement=function(t){const e=this; const n=p(this._element).hasClass(ln);this._element.parentNode&&this._element.parentNode.nodeType===Node.ELEMENT_NODE||document.body.appendChild(this._element),this._element.style.display="block",this._element.removeAttribute("aria-hidden"),this._element.setAttribute("aria-modal",!0),p(this._dialog).hasClass(on)?this._dialog.querySelector(un).scrollTop=0:this._element.scrollTop=0,n&&m.reflow(this._element),p(this._element).addClass(cn),this._config.focus&&this._enforceFocus();const i=p.Event(nn.SHOWN,{relatedTarget:t}); const o=function(){e._config.focus&&e._element.focus(),e._isTransitioning=!1,p(e._element).trigger(i)};if(n){const r=m.getTransitionDurationFromElement(this._dialog);p(this._dialog).one(m.TRANSITION_END,o).emulateTransitionEnd(r)}else {o()}},t._enforceFocus=function(){const e=this;p(document).off(nn.FOCUSIN).on(nn.FOCUSIN,(t)=> {document!==t.target&&e._element!==t.target&&!p(e._element).has(t.target).length&&e._element.focus()})},t._setEscapeEvent=function(){const e=this;this._isShown&&this._config.keyboard?p(this._element).on(nn.KEYDOWN_DISMISS,(t)=> {27===t.which&&(t.preventDefault(),e.hide())}):this._isShown||p(this._element).off(nn.KEYDOWN_DISMISS)},t._setResizeEvent=function(){const e=this;this._isShown?p(window).on(nn.RESIZE,(t)=> {return e.handleUpdate(t)}):p(window).off(nn.RESIZE)},t._hideModal=function(){const t=this;this._element.style.display="none",this._element.setAttribute("aria-hidden",!0),this._element.removeAttribute("aria-modal"),this._isTransitioning=!1,this._showBackdrop(()=> {p(document.body).removeClass(an),t._resetAdjustments(),t._resetScrollbar(),p(t._element).trigger(nn.HIDDEN)})},t._removeBackdrop=function(){this._backdrop&&(p(this._backdrop).remove(),this._backdrop=null)},t._showBackdrop=function(t){const e=this; const n=p(this._element).hasClass(ln)?ln:"";if(this._isShown&&this._config.backdrop){if(this._backdrop=document.createElement("div"),this._backdrop.className=sn,n&&this._backdrop.classList.add(n),p(this._backdrop).appendTo(document.body),p(this._element).on(nn.CLICK_DISMISS,(t)=> {e._ignoreBackdropClick?e._ignoreBackdropClick=!1:t.target===t.currentTarget&&("static"===e._config.backdrop?e._element.focus():e.hide())}),n&&m.reflow(this._backdrop),p(this._backdrop).addClass(cn),!t){return;}if(!n){return void t();}const i=m.getTransitionDurationFromElement(this._backdrop);p(this._backdrop).one(m.TRANSITION_END,t).emulateTransitionEnd(i)}else if(!this._isShown&&this._backdrop){p(this._backdrop).removeClass(cn);const o=function(){e._removeBackdrop(),t&&t()};if(p(this._element).hasClass(ln)){const r=m.getTransitionDurationFromElement(this._backdrop);p(this._backdrop).one(m.TRANSITION_END,o).emulateTransitionEnd(r)}else {o()}}else {t&&t()}},t._adjustDialog=function(){const t=this._element.scrollHeight>document.documentElement.clientHeight;!this._isBodyOverflowing&&t&&(this._element.style.paddingLeft=this._scrollbarWidth+"px"),this._isBodyOverflowing&&!t&&(this._element.style.paddingRight=this._scrollbarWidth+"px")},t._resetAdjustments=function(){this._element.style.paddingLeft="",this._element.style.paddingRight=""},t._checkScrollbar=function(){const t=document.body.getBoundingClientRect();this._isBodyOverflowing=t.left+t.right<window.innerWidth,this._scrollbarWidth=this._getScrollbarWidth()},t._setScrollbar=function(){const o=this;if(this._isBodyOverflowing){const t=[].slice.call(document.querySelectorAll(pn)); const e=[].slice.call(document.querySelectorAll(mn));p(t).each((t,e)=> {const n=e.style.paddingRight; const i=p(e).css("padding-right");p(e).data("padding-right",n).css("padding-right",parseFloat(i)+o._scrollbarWidth+"px")}),p(e).each((t,e)=> {const n=e.style.marginRight; const i=p(e).css("margin-right");p(e).data("margin-right",n).css("margin-right",parseFloat(i)-o._scrollbarWidth+"px")});const n=document.body.style.paddingRight; const i=p(document.body).css("padding-right");p(document.body).data("padding-right",n).css("padding-right",parseFloat(i)+this._scrollbarWidth+"px")}p(document.body).addClass(an)},t._resetScrollbar=function(){const t=[].slice.call(document.querySelectorAll(pn));p(t).each((t,e)=> {const n=p(e).data("padding-right");p(e).removeData("padding-right"),e.style.paddingRight=n||""});const e=[].slice.call(document.querySelectorAll(""+mn));p(e).each((t,e)=> {const n=p(e).data("margin-right");"undefined"!==typeof n&&p(e).css("margin-right",n).removeData("margin-right")});const n=p(document.body).data("padding-right");p(document.body).removeData("padding-right"),document.body.style.paddingRight=n||""},t._getScrollbarWidth=function(){const t=document.createElement("div");t.className=rn,document.body.appendChild(t);const e=t.getBoundingClientRect().width-t.clientWidth;

return document.body.removeChild(t),e},o._jQueryInterface=function(n,i){return this.each(function(){let t=p(this).data($e); const e=l({},tn,p(this).data(),"object"===typeof n&&n?n:{});if(t||(t=new o(this,e),p(this).data($e,t)),"string"===typeof n){if("undefined"===typeof t[n]){throw new TypeError('No method named "'+n+'"');}t[n](i)}else {e.show&&t.show(i)}})},s(o,null,[{key:"VERSION",get(){return"4.3.1"}},{key:"Default",get(){return tn}}]),o}();p(document).on(nn.CLICK_DATA_API,fn,function(t){let e; const n=this; const i=m.getSelectorFromElement(this);i&&(e=document.querySelector(i));const o=p(e).data($e)?"toggle":l({},p(e).data(),p(this).data());"A"!==this.tagName&&"AREA"!==this.tagName||t.preventDefault();var r=p(e).one(nn.SHOW,(t)=> {t.isDefaultPrevented()||r.one(nn.HIDDEN,()=> {p(n).is(":visible")&&n.focus()})});gn._jQueryInterface.call(p(e),o,this)}),p.fn[Ge]=gn._jQueryInterface,p.fn[Ge].Constructor=gn,p.fn[Ge].noConflict=function(){return p.fn[Ge]=Ze,gn._jQueryInterface};const _n=["background","cite","href","itemtype","longdesc","poster","src","xlink:href"]; const vn={"*":["class","dir","id","lang","role",/^aria-[\w-]*$/i],"a":["target","href","title","rel"],"area":[],"b":[],"br":[],"col":[],"code":[],"div":[],"em":[],"hr":[],"h1":[],"h2":[],"h3":[],"h4":[],"h5":[],"h6":[],"i":[],"img":["src","alt","title","width","height"],"li":[],"ol":[],"p":[],"pre":[],"s":[],"small":[],"span":[],"sub":[],"sup":[],"strong":[],"u":[],"ul":[]}; const yn=/^(?:(?:https?|mailto|ftp|tel|file):|[^&:/?#]*(?:[/?#]|$))/gi; const En=/^data:(?:image\/(?:bmp|gif|jpeg|jpg|png|tiff|webp)|video\/(?:mpeg|mp4|ogg|webm)|audio\/(?:mp3|oga|ogg|opus));base64,[a-z0-9+/]+=*$/i;function bn(t,s,e){if(!t.length){return t;}if(e&&"function"===typeof e){return e(t);}for(var n=(new window.DOMParser).parseFromString(t,"text/html"),a=Object.keys(s),l=[].slice.call(n.body.querySelectorAll("*")),i=function(t,e){const n=l[t]; const i=n.nodeName.toLowerCase();if(-1===a.indexOf(n.nodeName.toLowerCase())){return n.parentNode.removeChild(n),"continue";}const o=[].slice.call(n.attributes); const r=[].concat(s["*"]||[],s[i]||[]);o.forEach((t)=> {(function(t,e){const n=t.nodeName.toLowerCase();if(-1!==e.indexOf(n)){return-1===_n.indexOf(n)||Boolean(t.nodeValue.match(yn)||t.nodeValue.match(En));}for(let i=e.filter((t)=> {return t instanceof RegExp}),o=0,r=i.length;o<r;o++){if(n.match(i[o])){return!0;}}

return!1})(t,r)||n.removeAttribute(t.nodeName)})},o=0,r=l.length;o<r;o++){i(o);}

return n.body.innerHTML}const wn="tooltip"; const Cn="bs.tooltip"; const Tn="."+Cn; const Sn=p.fn[wn]; const Dn="bs-tooltip"; const In=new RegExp("(^|\\s)"+Dn+"\\S+","g"); const An=["sanitize","whiteList","sanitizeFn"]; const On={animation:"boolean",template:"string",title:"(string|element|function)",trigger:"string",delay:"(number|object)",html:"boolean",selector:"(string|boolean)",placement:"(string|function)",offset:"(number|string|function)",container:"(string|element|boolean)",fallbackPlacement:"(string|array)",boundary:"(string|element)",sanitize:"boolean",sanitizeFn:"(null|function)",whiteList:"object"}; const Nn={AUTO:"auto",TOP:"top",RIGHT:"right",BOTTOM:"bottom",LEFT:"left"}; const kn={animation:!0,template:'<div class="tooltip" role="tooltip"><div class="arrow"></div><div class="tooltip-inner"></div></div>',trigger:"hover focus",title:"",delay:0,html:!1,selector:!1,placement:"top",offset:0,container:!1,fallbackPlacement:"flip",boundary:"scrollParent",sanitize:!0,sanitizeFn:null,whiteList:vn}; const Ln="show"; const xn="out"; const Pn={HIDE:"hide"+Tn,HIDDEN:"hidden"+Tn,SHOW:"show"+Tn,SHOWN:"shown"+Tn,INSERTED:"inserted"+Tn,CLICK:"click"+Tn,FOCUSIN:"focusin"+Tn,FOCUSOUT:"focusout"+Tn,MOUSEENTER:"mouseenter"+Tn,MOUSELEAVE:"mouseleave"+Tn}; const Hn="fade"; const jn="show"; const Rn=".tooltip-inner"; const Fn=".arrow"; const Mn="hover"; const Wn="focus"; const Un="click"; const Bn="manual"; const qn=function(){function i(t,e){if("undefined"===typeof be){throw new TypeError("Bootstrap's tooltips require Popper.js (https://popper.js.org/)");}this._isEnabled=!0,this._timeout=0,this._hoverState="",this._activeTrigger={},this._popper=null,this.element=t,this.config=this._getConfig(e),this.tip=null,this._setListeners()}const t=i.prototype;

return t.enable=function(){this._isEnabled=!0},t.disable=function(){this._isEnabled=!1},t.toggleEnabled=function(){this._isEnabled=!this._isEnabled},t.toggle=function(t){if(this._isEnabled){if(t){const e=this.constructor.DATA_KEY; let n=p(t.currentTarget).data(e);n||(n=new this.constructor(t.currentTarget,this._getDelegateConfig()),p(t.currentTarget).data(e,n)),n._activeTrigger.click=!n._activeTrigger.click,n._isWithActiveTrigger()?n._enter(null,n):n._leave(null,n)}else{if(p(this.getTipElement()).hasClass(jn)){return void this._leave(null,this);}this._enter(null,this)}}},t.dispose=function(){clearTimeout(this._timeout),p.removeData(this.element,this.constructor.DATA_KEY),p(this.element).off(this.constructor.EVENT_KEY),p(this.element).closest(".modal").off("hide.bs.modal"),this.tip&&p(this.tip).remove(),this._isEnabled=null,this._timeout=null,this._hoverState=null,(this._activeTrigger=null)!==this._popper&&this._popper.destroy(),this._popper=null,this.element=null,this.config=null,this.tip=null},t.show=function(){const e=this;if("none"===p(this.element).css("display")){throw new Error("Please use show on visible elements");}const t=p.Event(this.constructor.Event.SHOW);if(this.isWithContent()&&this._isEnabled){p(this.element).trigger(t);const n=m.findShadowRoot(this.element); const i=p.contains(null!==n?n:this.element.ownerDocument.documentElement,this.element);if(t.isDefaultPrevented()||!i){return;}const o=this.getTipElement(); const r=m.getUID(this.constructor.NAME);o.setAttribute("id",r),this.element.setAttribute("aria-describedby",r),this.setContent(),this.config.animation&&p(o).addClass(Hn);const s="function"===typeof this.config.placement?this.config.placement.call(this,o,this.element):this.config.placement; const a=this._getAttachment(s);this.addAttachmentClass(a);const l=this._getContainer();p(o).data(this.constructor.DATA_KEY,this),p.contains(this.element.ownerDocument.documentElement,this.tip)||p(o).appendTo(l),p(this.element).trigger(this.constructor.Event.INSERTED),this._popper=new be(this.element,o,{placement:a,modifiers:{offset:this._getOffset(),flip:{behavior:this.config.fallbackPlacement},arrow:{element:Fn},preventOverflow:{boundariesElement:this.config.boundary}},onCreate(t){t.originalPlacement!==t.placement&&e._handlePopperPlacementChange(t)},onUpdate(t){return e._handlePopperPlacementChange(t)}}),p(o).addClass(jn),"ontouchstart"in document.documentElement&&p(document.body).children().on("mouseover",null,p.noop);const c=function(){e.config.animation&&e._fixTransition();const t=e._hoverState;e._hoverState=null,p(e.element).trigger(e.constructor.Event.SHOWN),t===xn&&e._leave(null,e)};if(p(this.tip).hasClass(Hn)){const h=m.getTransitionDurationFromElement(this.tip);p(this.tip).one(m.TRANSITION_END,c).emulateTransitionEnd(h)}else {c()}}},t.hide=function(t){const e=this; const n=this.getTipElement(); const i=p.Event(this.constructor.Event.HIDE); const o=function(){e._hoverState!==Ln&&n.parentNode&&n.parentNode.removeChild(n),e._cleanTipClass(),e.element.removeAttribute("aria-describedby"),p(e.element).trigger(e.constructor.Event.HIDDEN),null!==e._popper&&e._popper.destroy(),t&&t()};if(p(this.element).trigger(i),!i.isDefaultPrevented()){if(p(n).removeClass(jn),"ontouchstart"in document.documentElement&&p(document.body).children().off("mouseover",null,p.noop),this._activeTrigger[Un]=!1,this._activeTrigger[Wn]=!1,this._activeTrigger[Mn]=!1,p(this.tip).hasClass(Hn)){const r=m.getTransitionDurationFromElement(n);p(n).one(m.TRANSITION_END,o).emulateTransitionEnd(r)}else {o();}this._hoverState=""}},t.update=function(){null!==this._popper&&this._popper.scheduleUpdate()},t.isWithContent=function(){return Boolean(this.getTitle())},t.addAttachmentClass=function(t){p(this.getTipElement()).addClass(Dn+"-"+t)},t.getTipElement=function(){return this.tip=this.tip||p(this.config.template)[0],this.tip},t.setContent=function(){const t=this.getTipElement();this.setElementContent(p(t.querySelectorAll(Rn)),this.getTitle()),p(t).removeClass(Hn+" "+jn)},t.setElementContent=function(t,e){"object"!==typeof e||!e.nodeType&&!e.jquery?this.config.html?(this.config.sanitize&&(e=bn(e,this.config.whiteList,this.config.sanitizeFn)),t.html(e)):t.text(e):this.config.html?p(e).parent().is(t)||t.empty().append(e):t.text(p(e).text())},t.getTitle=function(){let t=this.element.dataset.originalTitle;

return t||(t="function"===typeof this.config.title?this.config.title.call(this.element):this.config.title),t},t._getOffset=function(){const e=this; const t={};

return"function"===typeof this.config.offset?t.fn=function(t){return t.offsets=l({},t.offsets,e.config.offset(t.offsets,e.element)||{}),t}:t.offset=this.config.offset,t},t._getContainer=function(){return!1===this.config.container?document.body:m.isElement(this.config.container)?p(this.config.container):p(document).find(this.config.container)},t._getAttachment=function(t){return Nn[t.toUpperCase()]},t._setListeners=function(){const i=this;this.config.trigger.split(" ").forEach((t)=> {if("click"===t){p(i.element).on(i.constructor.Event.CLICK,i.config.selector,(t)=> {return i.toggle(t)});}else if(t!==Bn){const e=t===Mn?i.constructor.Event.MOUSEENTER:i.constructor.Event.FOCUSIN; const n=t===Mn?i.constructor.Event.MOUSELEAVE:i.constructor.Event.FOCUSOUT;p(i.element).on(e,i.config.selector,(t)=> {return i._enter(t)}).on(n,i.config.selector,(t)=> {return i._leave(t)})}}),p(this.element).closest(".modal").on("hide.bs.modal",()=> {i.element&&i.hide()}),this.config.selector?this.config=l({},this.config,{trigger:"manual",selector:""}):this._fixTitle()},t._fixTitle=function(){const t=typeof this.element.dataset.originalTitle;(this.element.getAttribute("title")||"string"!==t)&&(this.element.setAttribute("data-original-title",this.element.getAttribute("title")||""),this.element.setAttribute("title",""))},t._enter=function(t,e){const n=this.constructor.DATA_KEY;(e=e||p(t.currentTarget).data(n))||(e=new this.constructor(t.currentTarget,this._getDelegateConfig()),p(t.currentTarget).data(n,e)),t&&(e._activeTrigger["focusin"===t.type?Wn:Mn]=!0),p(e.getTipElement()).hasClass(jn)||e._hoverState===Ln?e._hoverState=Ln:(clearTimeout(e._timeout),e._hoverState=Ln,e.config.delay&&e.config.delay.show?e._timeout=setTimeout(()=> {e._hoverState===Ln&&e.show()},e.config.delay.show):e.show())},t._leave=function(t,e){const n=this.constructor.DATA_KEY;(e=e||p(t.currentTarget).data(n))||(e=new this.constructor(t.currentTarget,this._getDelegateConfig()),p(t.currentTarget).data(n,e)),t&&(e._activeTrigger["focusout"===t.type?Wn:Mn]=!1),e._isWithActiveTrigger()||(clearTimeout(e._timeout),e._hoverState=xn,e.config.delay&&e.config.delay.hide?e._timeout=setTimeout(()=> {e._hoverState===xn&&e.hide()},e.config.delay.hide):e.hide())},t._isWithActiveTrigger=function(){for(const t in this._activeTrigger){if(this._activeTrigger[t]){return!0;}}

return!1},t._getConfig=function(t){const e=p(this.element).data();

return Object.keys(e).forEach((t)=> {-1!==An.indexOf(t)&&delete e[t]}),"number"===typeof(t=l({},this.constructor.Default,e,"object"===typeof t&&t?t:{})).delay&&(t.delay={show:t.delay,hide:t.delay}),"number"===typeof t.title&&(t.title=t.title.toString()),"number"===typeof t.content&&(t.content=t.content.toString()),m.typeCheckConfig(wn,t,this.constructor.DefaultType),t.sanitize&&(t.template=bn(t.template,t.whiteList,t.sanitizeFn)),t},t._getDelegateConfig=function(){const t={};if(this.config){for(const e in this.config){this.constructor.Default[e]!==this.config[e]&&(t[e]=this.config[e]);}}

return t},t._cleanTipClass=function(){const t=p(this.getTipElement()); const e=t.attr("class").match(In);null!==e&&e.length&&t.removeClass(e.join(""))},t._handlePopperPlacementChange=function(t){const e=t.instance;this.tip=e.popper,this._cleanTipClass(),this.addAttachmentClass(this._getAttachment(t.placement))},t._fixTransition=function(){const t=this.getTipElement(); const e=this.config.animation;null===t.getAttribute("x-placement")&&(p(t).removeClass(Hn),this.config.animation=!1,this.hide(),this.show(),this.config.animation=e)},i._jQueryInterface=function(n){return this.each(function(){let t=p(this).data(Cn); const e="object"===typeof n&&n;if((t||!/dispose|hide/.test(n))&&(t||(t=new i(this,e),p(this).data(Cn,t)),"string"===typeof n)){if("undefined"===typeof t[n]){throw new TypeError('No method named "'+n+'"');}t[n]()}})},s(i,null,[{key:"VERSION",get(){return"4.3.1"}},{key:"Default",get(){return kn}},{key:"NAME",get(){return wn}},{key:"DATA_KEY",get(){return Cn}},{key:"Event",get(){return Pn}},{key:"EVENT_KEY",get(){return Tn}},{key:"DefaultType",get(){return On}}]),i}();p.fn[wn]=qn._jQueryInterface,p.fn[wn].Constructor=qn,p.fn[wn].noConflict=function(){return p.fn[wn]=Sn,qn._jQueryInterface};const Kn="popover"; const Qn="bs.popover"; const Vn="."+Qn; const Yn=p.fn[Kn]; const zn="bs-popover"; const Xn=new RegExp("(^|\\s)"+zn+"\\S+","g"); const Gn=l({},qn.Default,{placement:"right",trigger:"click",content:"",template:'<div class="popover" role="tooltip"><div class="arrow"></div><h3 class="popover-header"></h3><div class="popover-body"></div></div>'}); const $n=l({},qn.DefaultType,{content:"(string|element|function)"}); const Jn="fade"; const Zn="show"; const ti=".popover-header"; const ei=".popover-body"; const ni={HIDE:"hide"+Vn,HIDDEN:"hidden"+Vn,SHOW:"show"+Vn,SHOWN:"shown"+Vn,INSERTED:"inserted"+Vn,CLICK:"click"+Vn,FOCUSIN:"focusin"+Vn,FOCUSOUT:"focusout"+Vn,MOUSEENTER:"mouseenter"+Vn,MOUSELEAVE:"mouseleave"+Vn}; const ii=function(t){let e; let n;function i(){return t.apply(this,arguments)||this}n=t,(e=i).prototype=Object.create(n.prototype),(e.prototype.constructor=e).__proto__=n;const o=i.prototype;

return o.isWithContent=function(){return this.getTitle()||this._getContent()},o.addAttachmentClass=function(t){p(this.getTipElement()).addClass(zn+"-"+t)},o.getTipElement=function(){return this.tip=this.tip||p(this.config.template)[0],this.tip},o.setContent=function(){const t=p(this.getTipElement());this.setElementContent(t.find(ti),this.getTitle());let e=this._getContent();"function"===typeof e&&(e=e.call(this.element)),this.setElementContent(t.find(ei),e),t.removeClass(Jn+" "+Zn)},o._getContent=function(){return this.element.dataset.content||this.config.content},o._cleanTipClass=function(){const t=p(this.getTipElement()); const e=t.attr("class").match(Xn);null!==e&&!!e.length&&t.removeClass(e.join(""))},i._jQueryInterface=function(n){return this.each(function(){let t=p(this).data(Qn); const e="object"===typeof n?n:null;if((t||!/dispose|hide/.test(n))&&(t||(t=new i(this,e),p(this).data(Qn,t)),"string"===typeof n)){if("undefined"===typeof t[n]){throw new TypeError('No method named "'+n+'"');}t[n]()}})},s(i,null,[{key:"VERSION",get(){return"4.3.1"}},{key:"Default",get(){return Gn}},{key:"NAME",get(){return Kn}},{key:"DATA_KEY",get(){return Qn}},{key:"Event",get(){return ni}},{key:"EVENT_KEY",get(){return Vn}},{key:"DefaultType",get(){return $n}}]),i}(qn);p.fn[Kn]=ii._jQueryInterface,p.fn[Kn].Constructor=ii,p.fn[Kn].noConflict=function(){return p.fn[Kn]=Yn,ii._jQueryInterface};const oi="scrollspy"; const ri="bs.scrollspy"; const si="."+ri; const ai=p.fn[oi]; const li={offset:10,method:"auto",target:""}; const ci={offset:"number",method:"string",target:"(string|element)"}; const hi={ACTIVATE:"activate"+si,SCROLL:"scroll"+si,LOAD_DATA_API:"load"+si+".data-api"}; const ui="dropdown-item"; const fi="active"; const di='[data-spy="scroll"]'; const pi=".nav, .list-group"; const mi=".nav-link"; const gi=".nav-item"; const _i=".list-group-item"; const vi=".dropdown"; const yi=".dropdown-item"; const Ei=".dropdown-toggle"; const bi="offset"; const wi="position"; const Ci=function(){function n(t,e){const n=this;this._element=t,this._scrollElement="BODY"===t.tagName?window:t,this._config=this._getConfig(e),this._selector=this._config.target+" "+mi+","+this._config.target+" "+_i+","+this._config.target+" "+yi,this._offsets=[],this._targets=[],this._activeTarget=null,this._scrollHeight=0,p(this._scrollElement).on(hi.SCROLL,(t)=> {return n._process(t)}),this.refresh(),this._process()}const t=n.prototype;

return t.refresh=function(){const e=this; const t=this._scrollElement===this._scrollElement.window?bi:wi; const o="auto"===this._config.method?t:this._config.method; const r=o===wi?this._getScrollTop():0;this._offsets=[],this._targets=[],this._scrollHeight=this._getScrollHeight(),[].slice.call(document.querySelectorAll(this._selector)).map((t)=> {let e; const n=m.getSelectorFromElement(t);if(n&&(e=document.querySelector(n)),e){const i=e.getBoundingClientRect();if(i.width||i.height){return[p(e)[o]().top+r,n]}}

return null}).filter((t)=> {return t}).sort((t,e)=> {return t[0]-e[0]}).forEach((t)=> {e._offsets.push(t[0]),e._targets.push(t[1])})},t.dispose=function(){p.removeData(this._element,ri),p(this._scrollElement).off(si),this._element=null,this._scrollElement=null,this._config=null,this._selector=null,this._offsets=null,this._targets=null,this._activeTarget=null,this._scrollHeight=null},t._getConfig=function(t){if("string"!==typeof(t=l({},li,"object"===typeof t&&t?t:{})).target){let e=p(t.target).attr("id");e||(e=m.getUID(oi),p(t.target).attr("id",e)),t.target="#"+e}

return m.typeCheckConfig(oi,t,ci),t},t._getScrollTop=function(){return this._scrollElement===window?this._scrollElement.pageYOffset:this._scrollElement.scrollTop},t._getScrollHeight=function(){return this._scrollElement.scrollHeight||Math.max(document.body.scrollHeight,document.documentElement.scrollHeight)},t._getOffsetHeight=function(){return this._scrollElement===window?window.innerHeight:this._scrollElement.getBoundingClientRect().height},t._process=function(){const t=this._getScrollTop()+this._config.offset; const e=this._getScrollHeight(); const n=this._config.offset+e-this._getOffsetHeight();if(this._scrollHeight!==e&&this.refresh(),n<=t){const i=this._targets[this._targets.length-1];this._activeTarget!==i&&this._activate(i)}else{if(this._activeTarget&&t<this._offsets[0]&&0<this._offsets[0]){return this._activeTarget=null,void this._clear();}for(let o=this._offsets.length;o--;){this._activeTarget!==this._targets[o]&&t>=this._offsets[o]&&("undefined"===typeof this._offsets[o+1]||t<this._offsets[o+1])&&this._activate(this._targets[o])}}},t._activate=function(e){this._activeTarget=e,this._clear();const t=this._selector.split(",").map((t)=> {return t+'[data-target="'+e+'"],'+t+'[href="'+e+'"]'}); const n=p([].slice.call(document.querySelectorAll(t.join(","))));n.hasClass(ui)?(n.closest(vi).find(Ei).addClass(fi),n.addClass(fi)):(n.addClass(fi),n.parents(pi).prev(mi+", "+_i).addClass(fi),n.parents(pi).prev(gi).children(mi).addClass(fi)),p(this._scrollElement).trigger(hi.ACTIVATE,{relatedTarget:e})},t._clear=function(){[].slice.call(document.querySelectorAll(this._selector)).filter((t)=> {return t.classList.contains(fi)}).forEach((t)=> {return t.classList.remove(fi)})},n._jQueryInterface=function(e){return this.each(function(){let t=p(this).data(ri);if(t||(t=new n(this,"object"===typeof e&&e),p(this).data(ri,t)),"string"===typeof e){if("undefined"===typeof t[e]){throw new TypeError('No method named "'+e+'"');}t[e]()}})},s(n,null,[{key:"VERSION",get(){return"4.3.1"}},{key:"Default",get(){return li}}]),n}();p(window).on(hi.LOAD_DATA_API,()=> {for(let t=[].slice.call(document.querySelectorAll(di)),e=t.length;e--;){const n=p(t[e]);Ci._jQueryInterface.call(n,n.data())}}),p.fn[oi]=Ci._jQueryInterface,p.fn[oi].Constructor=Ci,p.fn[oi].noConflict=function(){return p.fn[oi]=ai,Ci._jQueryInterface};const Ti="bs.tab"; const Si="."+Ti; const Di=p.fn.tab; const Ii={HIDE:"hide"+Si,HIDDEN:"hidden"+Si,SHOW:"show"+Si,SHOWN:"shown"+Si,CLICK_DATA_API:"click"+Si+".data-api"}; const Ai="dropdown-menu"; const Oi="active"; const Ni="disabled"; const ki="fade"; const Li="show"; const xi=".dropdown"; const Pi=".nav, .list-group"; const Hi=".active"; const ji="> li > .active"; const Ri='[data-toggle="tab"], [data-toggle="pill"], [data-toggle="list"]'; const Fi=".dropdown-toggle"; const Mi="> .dropdown-menu .active"; const Wi=function(){function i(t){this._element=t}const t=i.prototype;

return t.show=function(){const n=this;if(!(this._element.parentNode&&this._element.parentNode.nodeType===Node.ELEMENT_NODE&&p(this._element).hasClass(Oi)||p(this._element).hasClass(Ni))){let t; let i; const e=p(this._element).closest(Pi)[0]; const o=m.getSelectorFromElement(this._element);if(e){const r="UL"===e.nodeName||"OL"===e.nodeName?ji:Hi;i=(i=p.makeArray(p(e).find(r)))[i.length-1]}const s=p.Event(Ii.HIDE,{relatedTarget:this._element}); const a=p.Event(Ii.SHOW,{relatedTarget:i});if(i&&p(i).trigger(s),p(this._element).trigger(a),!a.isDefaultPrevented()&&!s.isDefaultPrevented()){o&&(t=document.querySelector(o)),this._activate(this._element,e);const l=function(){const t=p.Event(Ii.HIDDEN,{relatedTarget:n._element}); const e=p.Event(Ii.SHOWN,{relatedTarget:i});p(i).trigger(t),p(n._element).trigger(e)};t?this._activate(t,t.parentNode,l):l()}}},t.dispose=function(){p.removeData(this._element,Ti),this._element=null},t._activate=function(t,e,n){const i=this; const o=(!e||"UL"!==e.nodeName&&"OL"!==e.nodeName?p(e).children(Hi):p(e).find(ji))[0]; const r=n&&o&&p(o).hasClass(ki); const s=function(){return i._transitionComplete(t,o,n)};if(o&&r){const a=m.getTransitionDurationFromElement(o);p(o).removeClass(Li).one(m.TRANSITION_END,s).emulateTransitionEnd(a)}else {s()}},t._transitionComplete=function(t,e,n){if(e){p(e).removeClass(Oi);const i=p(e.parentNode).find(Mi)[0];i&&p(i).removeClass(Oi),"tab"===e.getAttribute("role")&&e.setAttribute("aria-selected",!1)}if(p(t).addClass(Oi),"tab"===t.getAttribute("role")&&t.setAttribute("aria-selected",!0),m.reflow(t),t.classList.contains(ki)&&t.classList.add(Li),t.parentNode&&p(t.parentNode).hasClass(Ai)){const o=p(t).closest(xi)[0];if(o){const r=[].slice.call(o.querySelectorAll(Fi));p(r).addClass(Oi)}t.setAttribute("aria-expanded",!0)}n&&n()},i._jQueryInterface=function(n){return this.each(function(){const t=p(this); let e=t.data(Ti);if(e||(e=new i(this),t.data(Ti,e)),"string"===typeof n){if("undefined"===typeof e[n]){throw new TypeError('No method named "'+n+'"');}e[n]()}})},s(i,null,[{key:"VERSION",get(){return"4.3.1"}}]),i}();p(document).on(Ii.CLICK_DATA_API,Ri,function(t){t.preventDefault(),Wi._jQueryInterface.call(p(this),"show")}),p.fn.tab=Wi._jQueryInterface,p.fn.tab.Constructor=Wi,p.fn.tab.noConflict=function(){return p.fn.tab=Di,Wi._jQueryInterface};const Ui="toast"; const Bi="bs.toast"; const qi="."+Bi; const Ki=p.fn[Ui]; const Qi={CLICK_DISMISS:"click.dismiss"+qi,HIDE:"hide"+qi,HIDDEN:"hidden"+qi,SHOW:"show"+qi,SHOWN:"shown"+qi}; const Vi="fade"; const Yi="hide"; const zi="show"; const Xi="showing"; const Gi={animation:"boolean",autohide:"boolean",delay:"number"}; const $i={animation:!0,autohide:!0,delay:500}; const Ji='[data-dismiss="toast"]'; const Zi=function(){function i(t,e){this._element=t,this._config=this._getConfig(e),this._timeout=null,this._setListeners()}const t=i.prototype;

return t.show=function(){const t=this;p(this._element).trigger(Qi.SHOW),this._config.animation&&this._element.classList.add(Vi);const e=function(){t._element.classList.remove(Xi),t._element.classList.add(zi),p(t._element).trigger(Qi.SHOWN),t._config.autohide&&t.hide()};if(this._element.classList.remove(Yi),this._element.classList.add(Xi),this._config.animation){const n=m.getTransitionDurationFromElement(this._element);p(this._element).one(m.TRANSITION_END,e).emulateTransitionEnd(n)}else {e()}},t.hide=function(t){const e=this;this._element.classList.contains(zi)&&(p(this._element).trigger(Qi.HIDE),t?this._close():this._timeout=setTimeout(()=> {e._close()},this._config.delay))},t.dispose=function(){clearTimeout(this._timeout),this._timeout=null,this._element.classList.contains(zi)&&this._element.classList.remove(zi),p(this._element).off(Qi.CLICK_DISMISS),p.removeData(this._element,Bi),this._element=null,this._config=null},t._getConfig=function(t){return t=l({},$i,p(this._element).data(),"object"===typeof t&&t?t:{}),m.typeCheckConfig(Ui,t,this.constructor.DefaultType),t},t._setListeners=function(){const t=this;p(this._element).on(Qi.CLICK_DISMISS,Ji,()=> {return t.hide(!0)})},t._close=function(){const t=this; const e=function(){t._element.classList.add(Yi),p(t._element).trigger(Qi.HIDDEN)};if(this._element.classList.remove(zi),this._config.animation){const n=m.getTransitionDurationFromElement(this._element);p(this._element).one(m.TRANSITION_END,e).emulateTransitionEnd(n)}else {e()}},i._jQueryInterface=function(n){return this.each(function(){const t=p(this); let e=t.data(Bi);if(e||(e=new i(this,"object"===typeof n&&n),t.data(Bi,e)),"string"===typeof n){if("undefined"===typeof e[n]){throw new TypeError('No method named "'+n+'"');}e[n](this)}})},s(i,null,[{key:"VERSION",get(){return"4.3.1"}},{key:"DefaultType",get(){return Gi}},{key:"Default",get(){return $i}}]),i}();p.fn[Ui]=Zi._jQueryInterface,p.fn[Ui].Constructor=Zi,p.fn[Ui].noConflict=function(){return p.fn[Ui]=Ki,Zi._jQueryInterface},function(){if("undefined"===typeof p){throw new TypeError("Bootstrap's JavaScript requires jQuery. jQuery must be included before Bootstrap's JavaScript.");}const t=p.fn.jquery.split(" ")[0].split(".");if(t[0]<2&&t[1]<9||1===t[0]&&9===t[1]&&t[2]<1||4<=t[0]){throw new Error("Bootstrap's JavaScript requires at least jQuery v1.9.1 but less than v4.0.0")}}(),t.Util=m,t.Alert=g,t.Button=k,t.Carousel=at,t.Collapse=Ct,t.Dropdown=Xe,t.Modal=gn,t.Popover=ii,t.Scrollspy=Ci,t.Tab=Wi,t.Toast=Zi,t.Tooltip=qn,Object.defineProperty(t,"__esModule",{value:!0})});

// # sourceMappingURL=bootstrap.bundle.min.js.map

!function(e){const s=function CollapsibleSearch(s){const l=this;l.$element=e(s),l.$close=l.$element.find(".basic-search-close"),l.$input=l.$element.find('input[type="text"]'),l.$submit=l.$element.find('[type="submit"]'),l.$close.on("click.lexicon.close.collapsible-search",e.proxy(l.close,l)),l.$input.on("blur.lexicon.collapsible-search",e.proxy(l.blur,l)),l.$input.on("focus.lexicon.collapsible-search",e.proxy(l.focus,l)),l.$submit.on("click.lexicon.submit.collapsible-search",e.proxy(l.submit,l))};s.BREAKPOINT=768,s.TRANSITION_DURATION=500,s.prototype={blur(s){e(s.currentTarget).closest(".basic-search").removeClass("focus")},close(l){const c=e(l.currentTarget).closest(".basic-search"); const o=c.find(".basic-search-slider"); const a=c.find('[type="submit"]'); const i=function complete(){c.removeClass("basic-search-transition"),c.trigger("closed.lexicon.collapsible.search")}; const t=bootstrap.Util.supportsTransitionEnd();t&&o.one("bsTransitionEnd",e.proxy(i,this)).emulateTransitionEnd(s.TRANSITION_DURATION),c.addClass("basic-search-transition").removeClass("open"),t?a.focus():i.call(this)},destroy(){const e=this;e.$close.off("click.lexicon.close.collapsible-search"),e.$input.off("blur.lexicon.collapsible-search"),e.$input.off("focus.lexicon.collapsible-search"),e.$submit.off("click.lexicon.submit.collapsible-search")},focus(s){e(s.currentTarget).closest(".basic-search").addClass("focus")},submit(l){if(window.innerWidth<s.BREAKPOINT){const c=e(l.currentTarget).parents(".basic-search"); const o=c.find('input[type="text"]'); const a=c.find(".basic-search-slider"); const i=function complete(){c.removeClass("basic-search-transition"),o.focus(),c.trigger("open.lexicon.collapsible.search")};if(!c.hasClass("open")){l.preventDefault();const t=bootstrap.Util.supportsTransitionEnd();t&&a.one("bsTransitionEnd",e.proxy(i,this)).emulateTransitionEnd(s.TRANSITION_DURATION),c.addClass("basic-search-transition").addClass("open"),t||i.call(this)}}}};const l=e.fn.collapsibleSearch;e.fn.collapsibleSearch=function Plugin(l){return this.each((function(){const c=e(this); let o=c.data("lexicon.collapsible-search");o||(o=new s(this),c.data("lexicon.collapsible-search",o)),"string"===typeof l&&o[l]()}))},e.fn.collapsibleSearch.Constructor=s,e.fn.collapsibleSearch.noConflict=function(){return e.fn.collapsibleSearch=l,this};const c='[data-toggle="collapsible-search"] input[type="text"]';e(document).on("blur.lexicon.collapsible-search.data-api",c,e.proxy(s.prototype.blur,s)).on("click.lexicon.close.collapsible-search.data-api",'[data-toggle="collapsible-search"] .basic-search-close',e.proxy(s.prototype.close,s)).on("click.lexicon.submit.collapsible-search.data-api",'[data-toggle="collapsible-search"] [type="submit"]',e.proxy(s.prototype.submit,s)).on("focus.lexicon.collapsible-search.data-api",c,e.proxy(s.prototype.focus,s))}(jQuery);

!function(e){
/* !
   * jQuery fm Plugin
   * version: 0.1
   * Copyright (c) 2014 Nate Cavanaugh / Liferay Inc.
   * Dual licensed under the MIT and GPL licenses.
   */
e.fn.fm=function(a,n){const t=this; let m=t;if(1===arguments.length){const r=t.map(((n,t)=> {const m=t.form||t;if(m&&e.nodeName(m,"form")){const r=e(m); const f=(r.data("fm.namespace")||r.data("fm-namespace")||"")+a; let o=m[f]||m.ownerDocument.getElementById(f);

return o&&!o.nodeName&&(o=e.makeArray(o)),o}}));m=r}else{"string"===typeof a&&t.data("fm."+a,n);}

return m}}(window.$);

/* !
 * jQuery Form Plugin
 * version: 3.51.0-2014.06.20
 * Requires jQuery v1.5 or later
 * Copyright (c) 2014 M. Alsup
 * Examples and documentation at: http://malsup.com/jquery/form/
 * Project repository: https://github.com/malsup/form
 * Dual licensed under the MIT and GPL licenses.
 * https://github.com/malsup/form#copyright-and-license
 */
/* global ActiveXObject */

(function (factory) {
    "use strict";
    factory(window.$ || window.Zepto);
}

(($) => {
"use strict";

/*
    Usage Note:
    -----------
    Do not use both ajaxSubmit and ajaxForm on the same form.  These
    functions are mutually exclusive.  Use ajaxSubmit if you want
    to bind your own submit handler to the form.  For example,

    $(document).ready(function() {
        $('#myForm').on('submit', function(e) {
            e.preventDefault(); // <-- important
            $(this).ajaxSubmit({
                target: '#output'
            });
        });
    });

    Use ajaxForm when you want the plugin to manage all the event binding
    for you.  For example,

    $(document).ready(function() {
        $('#myForm').ajaxForm({
            target: '#output'
        });
    });

    You can also use ajaxForm with delegation (requires jQuery v1.7+), so the
    form does not have to exist when you invoke ajaxForm:

    $('#myForm').ajaxForm({
        delegation: true,
        target: '#output'
    });

    When using ajaxForm, the ajaxSubmit function will be invoked for you
    at the appropriate time.
*/

/**
 * Feature detection
 */
const feature = {};
feature.fileapi = $("<input type='file'/>").get(0).files !== undefined;
feature.formdata = window.FormData !== undefined;

const hasProp = !!$.fn.prop;

// attr2 uses prop when it can but checks the return type for
// an expected string.  this accounts for the case where a form 
// contains inputs with names like "action" or "method"; in those
// cases "prop" returns the element

$.fn.attr2 = function() {
    if ( ! hasProp ) {
        return this.attr.apply(this, arguments);
    }
    const val = this.prop.apply(this, arguments);
    if ( ( val && val.jquery ) || typeof val === 'string' ) {
        return val;
    }

    return this.attr.apply(this, arguments);
};

/**
 * ajaxSubmit() provides a mechanism for immediately submitting
 * an HTML form using AJAX.
 */
$.fn.ajaxSubmit = function(options) {
    /* jshint scripturl:true */

    // fast fail if nothing selected (http://dev.jquery.com/ticket/2752)

    if (!this.length) {
        log('ajaxSubmit: skipping submit process - no element selected');

        return this;
    }

    let method; let action; let url; const $form = this;

    if (typeof options === 'function') {
        options = { success: options };
    }
    else if ( options === undefined ) {
        options = {};
    }

    method = options.type || this.attr2('method');
    action = options.url  || this.attr2('action');

    url = (typeof action === 'string') ? $.trim(action) : '';
    url = url || window.location.href || '';
    if (url) {
        // clean url (don't include hash vaue)

        url = (url.match(/^([^#]+)/)||[])[1];
    }

    options = $.extend(true, {
        url,
        success: $.ajaxSettings.success,
        type: method || $.ajaxSettings.type,
        iframeSrc: /^https/i.test(window.location.href || '') ? 'javascript:false' : 'about:blank'
    }, options);

    // hook for manipulating the form data before it is extracted;
    // convenient for use with rich editors like tinyMCE or FCKEditor

    const veto = {};
    this.trigger('form-pre-serialize', [this, options, veto]);
    if (veto.veto) {
        log('ajaxSubmit: submit vetoed via form-pre-serialize trigger');

        return this;
    }

    // provide opportunity to alter form data before it is serialized

    if (options.beforeSerialize && options.beforeSerialize(this, options) === false) {
        log('ajaxSubmit: submit aborted via beforeSerialize callback');

        return this;
    }

    let traditional = options.traditional;
    if ( traditional === undefined ) {
        traditional = $.ajaxSettings.traditional;
    }

    const elements = [];
    let qx; const a = this.formToArray(options.semantic, elements);
    if (options.data) {
        options.extraData = options.data;
        qx = $.param(options.data, traditional);
    }

    // give pre-submit callback an opportunity to abort the submit

    if (options.beforeSubmit && options.beforeSubmit(a, this, options) === false) {
        log('ajaxSubmit: submit aborted via beforeSubmit callback');

        return this;
    }

    // fire vetoable 'validate' event

    this.trigger('form-submit-validate', [a, this, options, veto]);
    if (veto.veto) {
        log('ajaxSubmit: submit vetoed via form-submit-validate trigger');

        return this;
    }

    let q = $.param(a, traditional);
    if (qx) {
        q = ( q ? (q + '&' + qx) : qx );
    }
    if (options.type.toUpperCase() == 'GET') {
        options.url += (options.url.indexOf('?') >= 0 ? '&' : '?') + q;
        options.data = null;  // data is null for 'get'
    }
    else {
        options.data = q; // data is the query string for 'post'
    }

    const callbacks = [];
    if (options.resetForm) {
        callbacks.push(() => { $form.resetForm(); });
    }
    if (options.clearForm) {
        callbacks.push(() => { $form.clearForm(options.includeHidden); });
    }

    // perform a load on the target only if dataType is not provided

    if (!options.dataType && options.target) {
        const oldSuccess = options.success || function(){};
        callbacks.push(function(data) {
            const fn = options.replaceTarget ? 'replaceWith' : 'html';

            // Validate `data` through `HTML encoding` when passed
            // `data` is passed to `html()`, as suggested in
            // https://github.com/jquery-form/form/issues/464

            data = options.replaceTarget
                ? data
                : $.parseHTML($('<div>').text(data).html());

            $(options.target)[fn](data).each(oldSuccess, arguments);
        });
    }
    else if (options.success) {
        callbacks.push(options.success);
    }

    options.success = function(data, status, xhr) { // jQuery 1.4+ passes xhr as 3rd arg
        const context = options.context || this ;    // jQuery 1.4+ supports scope context
        for (let i=0, max=callbacks.length; i < max; i++) {
            callbacks[i].apply(context, [data, status, xhr || $form, $form]);
        }
    };

    if (options.error) {
        const oldError = options.error;
        options.error = function(xhr, status, error) {
            const context = options.context || this;
            oldError.apply(context, [xhr, status, error, $form]);
        };
    }

     if (options.complete) {
        const oldComplete = options.complete;
        options.complete = function(xhr, status) {
            const context = options.context || this;
            oldComplete.apply(context, [xhr, status, $form]);
        };
    }

    // are there files to upload?

    // [value] (issue #113), also see comment:
    // https://github.com/malsup/form/commit/588306aedba1de01388032d5f42a60159eea9228#commitcomment-2180219

    const fileInputs = $('input[type=file]:enabled', this).filter(function() { return $(this).val() !== ''; });

    const hasFileInputs = !!fileInputs.length;
    const mp = 'multipart/form-data';
    const multipart = ($form.attr('enctype') == mp || $form.attr('encoding') == mp);

    const fileAPI = feature.fileapi && feature.formdata;
    log("fileAPI :" + fileAPI);
    const shouldUseFrame = (hasFileInputs || multipart) && !fileAPI;

    let jqxhr;

    // options.iframe allows user to force iframe mode
    // 06-NOV-09: now defaulting to iframe mode if file input is detected

    if (options.iframe !== false && (options.iframe || shouldUseFrame)) {
        // hack to fix Safari hang (thanks to Tim Molendijk for this)
        // see:  http://groups.google.com/group/jquery-dev/browse_thread/thread/36395b7ab510dd5d

        if (options.closeKeepAlive) {
            $.get(options.closeKeepAlive, () => {
                jqxhr = fileUploadIframe(a);
            });
        }
        else {
            jqxhr = fileUploadIframe(a);
        }
    }
    else if ((hasFileInputs || multipart) && fileAPI) {
        jqxhr = fileUploadXhr(a);
    }
    else {
        jqxhr = $.ajax(options);
    }

    $form.removeData('jqxhr').data('jqxhr', jqxhr);

    // clear element array

    for (let k=0; k < elements.length; k++) {
        elements[k] = null;
    }

    // fire 'notify' event

    this.trigger('form-submit-notify', [this, options]);

    return this;

    // utility fn for deep serialization

    function deepSerialize(extraData){
        const serialized = $.param(extraData, options.traditional).split('&');
        const len = serialized.length;
        const result = [];
        let i; let part;
        for (i=0; i < len; i++) {
            // #252; undo param space replacement

            serialized[i] = serialized[i].replace(/\+/g,' ');
            part = serialized[i].split('=');

            // #278; use array instead of object storage, favoring array serializations

            result.push([decodeURIComponent(part[0]), decodeURIComponent(part[1])]);
        }

        return result;
    }

     // XMLHttpRequest Level 2 file uploads (big hat tip to francois2metz)

    function fileUploadXhr(a) {
        const formdata = new FormData();

        for (var i=0; i < a.length; i++) {
            formdata.append(a[i].name, a[i].value);
        }

        if (options.extraData) {
            const serializedData = deepSerialize(options.extraData);
            for (i=0; i < serializedData.length; i++) {
                if (serializedData[i]) {
                    formdata.append(serializedData[i][0], serializedData[i][1]);
                }
            }
        }

        options.data = null;

        const s = $.extend(true, {}, $.ajaxSettings, options, {
            contentType: false,
            processData: false,
            cache: false,
            type: method || 'POST'
        });

        if (options.uploadProgress) {
            // workaround because jqXHR does not expose upload property

            s.xhr = function() {
                const xhr = $.ajaxSettings.xhr();
                if (xhr.upload) {
                    xhr.upload.addEventListener('progress', (event) => {
                        let percent = 0;
                        const position = event.loaded || event.position; /* event.position is deprecated*/
                        const total = event.total;
                        if (event.lengthComputable) {
                            percent = Math.ceil(position / total * 100);
                        }
                        options.uploadProgress(event, position, total, percent);
                    }, false);
                }

                return xhr;
            };
        }

        s.data = null;
        const beforeSend = s.beforeSend;
        s.beforeSend = function(xhr, o) {
            // Send FormData() provided by user

            if (options.formData) {
                o.data = options.formData;
            }
            else {
                o.data = formdata;
            }
            if(beforeSend) {
                beforeSend.call(this, xhr, o);
            }
        };

        return $.ajax(s);
    }

    // private function for handling file uploads (hat tip to YAHOO!)

    function fileUploadIframe(a) {
        const form = $form[0]; let el; let i; let s; let g; let id; let $io; let io; let xhr; let sub; let n; let timedOut; let timeoutHandle;
        const deferred = $.Deferred();

        // #341

        deferred.abort = function(status) {
            xhr.abort(status);
        };

        if (a) {
            // ensure that every serialized input is still enabled

            for (i=0; i < elements.length; i++) {
                el = $(elements[i]);
                if ( hasProp ) {
                    el.prop('disabled', false);
                }
                else {
                    el.removeAttr('disabled');
                }
            }
        }

        s = $.extend(true, {}, $.ajaxSettings, options);
        s.context = s.context || s;
        id = 'jqFormIO' + (new Date().getTime());
        if (s.iframeTarget) {
            $io = $(s.iframeTarget);
            n = $io.attr2('name');
            if (!n) {
                $io.attr2('name', id);
            }
            else {
                id = n;
            }
        }
        else {
            $io = $('<iframe name="' + id + '" src="'+ s.iframeSrc +'" />');
            $io.css({ position: 'absolute', top: '-1000px', left: '-1000px' });
        }
        io = $io[0];


        xhr = { // mock object
            aborted: 0,
            responseText: null,
            responseXML: null,
            status: 0,
            statusText: 'n/a',
            getAllResponseHeaders() {},
            getResponseHeader() {},
            setRequestHeader() {},
            abort(status) {
                const e = (status === 'timeout' ? 'timeout' : 'aborted');
                log('aborting upload... ' + e);
                this.aborted = 1;

                try { // #214, #257
                    if (io.contentWindow.document.execCommand) {
                        io.contentWindow.document.execCommand('Stop');
                    }
                }
                catch(ignore) {}

                $io.attr('src', s.iframeSrc); // abort op in progress
                xhr.error = e;
                if (s.error) {
                    s.error.call(s.context, xhr, e, status);
                }
                if (g) {
                    $.event.trigger("ajaxError", [xhr, s, e]);
                }
                if (s.complete) {
                    s.complete.call(s.context, xhr, e);
                }
            }
        };

        g = s.global;

        // trigger ajax global events so that activity/block indicators work like normal

        if (g && 0 === $.active++) {
            $.event.trigger("ajaxStart");
        }
        if (g) {
            $.event.trigger("ajaxSend", [xhr, s]);
        }

        if (s.beforeSend && s.beforeSend.call(s.context, xhr, s) === false) {
            if (s.global) {
                $.active--;
            }
            deferred.reject();

            return deferred;
        }
        if (xhr.aborted) {
            deferred.reject();

            return deferred;
        }

        // add submitting element to data if we know it

        sub = form.clk;
        if (sub) {
            n = sub.name;
            if (n && !sub.disabled) {
                s.extraData = s.extraData || {};
                s.extraData[n] = sub.value;
                if (sub.type == "image") {
                    s.extraData[n+'.x'] = form.clk_x;
                    s.extraData[n+'.y'] = form.clk_y;
                }
            }
        }

        const CLIENT_TIMEOUT_ABORT = 1;
        const SERVER_ABORT = 2;
                
        function getDoc(frame) {
            /* it looks like contentWindow or contentDocument do not
             * carry the protocol property in ie8, when running under ssl
             * frame.document is the only valid response document, since
             * the protocol is know but not on the other two objects. strange?
             * "Same origin policy" http://en.wikipedia.org/wiki/Same_origin_policy
             */
            
            let doc = null;
            
            // IE8 cascading access check

            try {
                if (frame.contentWindow) {
                    doc = frame.contentWindow.document;
                }
            } catch(err) {
                // IE8 access denied under ssl & missing protocol

                log('cannot get iframe.contentWindow document: ' + err);
            }

            if (doc) { // successful getting content
                return doc;
            }

            try { // simply checking may throw in ie8 under ssl or mismatched protocol
                doc = frame.contentDocument ? frame.contentDocument : frame.document;
            } catch(err) {
                // last attempt

                log('cannot get iframe.contentDocument: ' + err);
                doc = frame.document;
            }

            return doc;
        }

        // Rails CSRF hack (thanks to Yvan Barthelemy)

        const csrf_token = $('meta[name=csrf-token]').attr('content');
        const csrf_param = $('meta[name=csrf-param]').attr('content');
        if (csrf_param && csrf_token) {
            s.extraData = s.extraData || {};
            s.extraData[csrf_param] = csrf_token;
        }

        // take a breath so that pending repaints get some cpu time before the upload starts

        function doSubmit() {
            // make sure form attrs are set

            const t = $form.attr2('target'); 
                const a = $form.attr2('action'); 
                const mp = 'multipart/form-data';
                const et = $form.attr('enctype') || $form.attr('encoding') || mp;

            // update form attrs in IE friendly way

            form.setAttribute('target',id);
            if (!method || /post/i.test(method) ) {
                form.setAttribute('method', 'POST');
            }
            if (a != s.url) {
                form.setAttribute('action', s.url);
            }

            // ie borks in some cases when setting encoding

            if (! s.skipEncodingOverride && (!method || /post/i.test(method))) {
                $form.attr({
                    encoding: 'multipart/form-data',
                    enctype:  'multipart/form-data'
                });
            }

            // support timout

            if (s.timeout) {
                timeoutHandle = setTimeout(() => { timedOut = true; cb(CLIENT_TIMEOUT_ABORT); }, s.timeout);
            }

            // look for server aborts

            function checkState() {
                try {
                    const state = getDoc(io).readyState;
                    log('state = ' + state);
                    if (state && state.toLowerCase() == 'uninitialized') {
                        setTimeout(checkState,50);
                    }
                }
                catch(e) {
                    log('Server abort: ' , e, ' (', e.name, ')');
                    cb(SERVER_ABORT);
                    if (timeoutHandle) {
                        clearTimeout(timeoutHandle);
                    }
                    timeoutHandle = undefined;
                }
            }

            // add "extra" data to form if provided in options

            const extraInputs = [];
            try {
                if (s.extraData) {
                    for (const n in s.extraData) {
                        if (s.extraData.hasOwnProperty(n)) {
                           // if using the $.param format that allows for multiple values with the same name

                           if($.isPlainObject(s.extraData[n]) && s.extraData[n].hasOwnProperty('name') && s.extraData[n].hasOwnProperty('value')) {
                               extraInputs.push(
                               $('<input type="hidden" name="'+s.extraData[n].name+'">').val(s.extraData[n].value)
                                   .appendTo(form)[0]);
                           } else {
                               extraInputs.push(
                               $('<input type="hidden" name="'+n+'">').val(s.extraData[n])
                                   .appendTo(form)[0]);
                           }
                        }
                    }
                }

                if (!s.iframeTarget) {
                    // add iframe to doc and submit the form

                    $io.appendTo('body');
                }
                if (io.attachEvent) {
                    io.attachEvent('onload', cb);
                }
                else {
                    io.addEventListener('load', cb, false);
                }
                setTimeout(checkState,15);

                try {
                    form.submit();
                } catch(err) {
                    // just in case form has element with name/id of 'submit'

                    const submitFn = document.createElement('form').submit;
                    submitFn.apply(form);
                }
            }
            finally {
                // reset attrs and remove "extra" input elements

                form.setAttribute('action',a);
                form.setAttribute('enctype', et); // #380
                if(t) {
                    form.setAttribute('target', t);
                } else {
                    $form.removeAttr('target');
                }
                $(extraInputs).remove();
            }
        }

        if (s.forceSync) {
            doSubmit();
        }
        else {
            setTimeout(doSubmit, 10); // this lets dom updates render
        }

        let data; let doc; let domCheckCount = 50; let callbackProcessed;

        function cb(e) {
            if (xhr.aborted || callbackProcessed) {
                return;
            }
            
            doc = getDoc(io);
            if(!doc) {
                log('cannot access response document');
                e = SERVER_ABORT;
            }
            if (e === CLIENT_TIMEOUT_ABORT && xhr) {
                xhr.abort('timeout');
                deferred.reject(xhr, 'timeout');

                return;
            }
            else if (e == SERVER_ABORT && xhr) {
                xhr.abort('server abort');
                deferred.reject(xhr, 'error', 'server abort');

                return;
            }

            if (!doc || doc.location.href == s.iframeSrc) {
                // response not received yet

                if (!timedOut) {
                    return;
                }
            }
            if (io.detachEvent) {
                io.detachEvent('onload', cb);
            }
            else {
                io.removeEventListener('load', cb, false);
            }

            let status = 'success'; let errMsg;
            try {
                if (timedOut) {
                    throw 'timeout';
                }

                const isXml = s.dataType == 'xml' || doc.XMLDocument || $.isXMLDoc(doc);
                log('isXml='+isXml);
                if (!isXml && window.opera && (doc.body === null || !doc.body.innerHTML)) {
                    if (--domCheckCount) {
                        // in some browsers (Opera) the iframe DOM is not always traversable when
                        // the onload callback fires, so we loop a bit to accommodate

                        log('requeing onLoad callback, DOM not available');
                        setTimeout(cb, 250);

                        return;
                    }

                    // let this fall through because server response could be an empty document
                    // log('Could not access iframe DOM after mutiple tries.');
                    // throw 'DOMException: not available';
                }

                // log('response detected');

                const docRoot = doc.body ? doc.body : doc.documentElement;
                xhr.responseText = docRoot ? docRoot.innerHTML : null;
                xhr.responseXML = doc.XMLDocument ? doc.XMLDocument : doc;
                if (isXml) {
                    s.dataType = 'xml';
                }
                xhr.getResponseHeader = function(header){
                    const headers = {'content-type': s.dataType};

                    return headers[header.toLowerCase()];
                };

                // support for XHR 'status' & 'statusText' emulation :

                if (docRoot) {
                    xhr.status = Number( docRoot.getAttribute('status') ) || xhr.status;
                    xhr.statusText = docRoot.getAttribute('statusText') || xhr.statusText;
                }

                const dt = (s.dataType || '').toLowerCase();
                const scr = /(json|script|text)/.test(dt);
                if (scr || s.textarea) {
                    // see if user embedded response in textarea

                    const ta = doc.getElementsByTagName('textarea')[0];
                    if (ta) {
                        xhr.responseText = ta.value;

                        // support for XHR 'status' & 'statusText' emulation :

                        xhr.status = Number( ta.getAttribute('status') ) || xhr.status;
                        xhr.statusText = ta.getAttribute('statusText') || xhr.statusText;
                    }
                    else if (scr) {
                        // account for browsers injecting pre around json response

                        const pre = doc.getElementsByTagName('pre')[0];
                        const b = doc.getElementsByTagName('body')[0];
                        if (pre) {
                            xhr.responseText = pre.textContent ? pre.textContent : pre.innerText;
                        }
                        else if (b) {
                            xhr.responseText = b.textContent ? b.textContent : b.innerText;
                        }
                    }
                }
                else if (dt == 'xml' && !xhr.responseXML && xhr.responseText) {
                    xhr.responseXML = toXml(xhr.responseText);
                }

                try {
                    data = httpData(xhr, dt, s);
                }
                catch (err) {
                    status = 'parsererror';
                    xhr.error = errMsg = (err || status);
                }
            }
            catch (err) {
                log('error caught: ',err);
                status = 'error';
                xhr.error = errMsg = (err || status);
            }

            if (xhr.aborted) {
                log('upload aborted');
                status = null;
            }

            if (xhr.status) { // we've set xhr.status
                status = (xhr.status >= 200 && xhr.status < 300 || xhr.status === 304) ? 'success' : 'error';
            }

            // ordering of these callbacks/triggers is odd, but that's how $.ajax does it

            if (status === 'success') {
                if (s.success) {
                    s.success.call(s.context, data, 'success', xhr);
                }
                deferred.resolve(xhr.responseText, 'success', xhr);
                if (g) {
                    $.event.trigger("ajaxSuccess", [xhr, s]);
                }
            }
            else if (status) {
                if (errMsg === undefined) {
                    errMsg = xhr.statusText;
                }
                if (s.error) {
                    s.error.call(s.context, xhr, status, errMsg);
                }
                deferred.reject(xhr, 'error', errMsg);
                if (g) {
                    $.event.trigger("ajaxError", [xhr, s, errMsg]);
                }
            }

            if (g) {
                $.event.trigger("ajaxComplete", [xhr, s]);
            }

            if (g && ! --$.active) {
                $.event.trigger("ajaxStop");
            }

            if (s.complete) {
                s.complete.call(s.context, xhr, status);
            }

            callbackProcessed = true;
            if (s.timeout) {
                clearTimeout(timeoutHandle);
            }

            // clean up

            setTimeout(() => {
                if (!s.iframeTarget) {
                    $io.remove();
                }
                else { // adding else to clean up existing iframe response.
                    $io.attr('src', s.iframeSrc);
                }
                xhr.responseXML = null;
            }, 100);
        }

        var toXml = $.parseXML || function(s, doc) { // use parseXML if available (jQuery 1.5+)
            if (window.ActiveXObject) {
                doc = new ActiveXObject('Microsoft.XMLDOM');
                doc.async = 'false';
                doc.loadXML(s);
            }
            else {
                doc = (new DOMParser()).parseFromString(s, 'text/xml');
            }

            return (doc && doc.documentElement && doc.documentElement.nodeName != 'parsererror') ? doc : null;
        };
        const parseJSON = $.parseJSON || function(s) {
            // Throw an error instead of making a new function using
            // unsanitized inputs to avoid XSS attacks.

            window.console.error('jquery.parseJSON is undefined');

            return null;
        };

        var httpData = function( xhr, type, s ) { // mostly lifted from jq1.4.4

            const ct = xhr.getResponseHeader('content-type') || '';
                const xml = type === 'xml' || !type && ct.indexOf('xml') >= 0;
                let data = xml ? xhr.responseXML : xhr.responseText;

            if (xml && data.documentElement.nodeName === 'parsererror') {
                if ($.error) {
                    $.error('parsererror');
                }
            }
            if (s && s.dataFilter) {
                data = s.dataFilter(data, type);
            }
            if (typeof data === 'string') {
                if (type === 'json' || !type && ct.indexOf('json') >= 0) {
                    data = parseJSON(data);
                } else if (type === "script" || !type && ct.indexOf("javascript") >= 0) {
                    $.globalEval(data);
                }
            }

            return data;
        };

        return deferred;
    }
};

/**
 * ajaxForm() provides a mechanism for fully automating form submission.
 *
 * The advantages of using this method instead of ajaxSubmit() are:
 *
 * 1: This method will include coordinates for <input type="image" /> elements (if the element
 *    is used to submit the form).
 * 2. This method will include the submit element's name/value data (for the element that was
 *    used to submit the form).
 * 3. This method binds the submit() method to the form for you.
 *
 * The options argument for ajaxForm works exactly as it does for ajaxSubmit.  ajaxForm merely
 * passes the options argument along after properly binding events for submit elements and
 * the form itself.
 */
$.fn.ajaxForm = function(options) {
    options = options || {};
    options.delegation = options.delegation && $.isFunction($.fn.on);

    // in jQuery 1.3+ we can fix mistakes with the ready state

    if (!options.delegation && !this.length) {
        const o = { s: this.selector, c: this.context };
        if (!$.isReady && o.s) {
            log('DOM not ready, queuing ajaxForm');
            $(() => {
                $(o.s,o.c).ajaxForm(options);
            });

            return this;
        }

        // is your DOM ready?  http://docs.jquery.com/Tutorials:Introducing_$(document).ready()

        log('terminating; zero elements found by selector' + ($.isReady ? '' : ' (DOM not ready)'));

        return this;
    }

    if ( options.delegation ) {
        $(document)
            .off('submit.form-plugin', this.selector, doAjaxSubmit)
            .off('click.form-plugin', this.selector, captureSubmittingElement)
            .on('submit.form-plugin', this.selector, options, doAjaxSubmit)
            .on('click.form-plugin', this.selector, options, captureSubmittingElement);

        return this;
    }

    return this.ajaxFormUnbind()
        .bind('submit.form-plugin', options, doAjaxSubmit)
        .bind('click.form-plugin', options, captureSubmittingElement);
};

// private event handlers

function doAjaxSubmit(e) {
    /* jshint validthis:true */
    const options = e.data;
    if (!e.isDefaultPrevented()) { // if event has been canceled, don't proceed
        e.preventDefault();
        $(e.target).ajaxSubmit(options); // #365
    }
}

function captureSubmittingElement(e) {
    /* jshint validthis:true */
    let target = e.target;
    const $el = $(target);
    if (!($el.is("[type=submit],[type=image]"))) {
        // is this a child element of the submit el?  (ex: a span within a button)

        const t = $el.closest('[type=submit]');
        if (!t.length) {
            return;
        }
        target = t[0];
    }
    const form = this;
    form.clk = target;
    if (target.type == 'image') {
        if (e.offsetX !== undefined) {
            form.clk_x = e.offsetX;
            form.clk_y = e.offsetY;
        } else if (typeof $.fn.offset === 'function') {
            const offset = $el.offset();
            form.clk_x = e.pageX - offset.left;
            form.clk_y = e.pageY - offset.top;
        } else {
            form.clk_x = e.pageX - target.offsetLeft;
            form.clk_y = e.pageY - target.offsetTop;
        }
    }

    // clear form vars

    setTimeout(() => { form.clk = form.clk_x = form.clk_y = null; }, 100);
}


// ajaxFormUnbind unbinds the event handlers that were bound by ajaxForm

$.fn.ajaxFormUnbind = function() {
    return this.unbind('submit.form-plugin click.form-plugin');
};

/**
 * formToArray() gathers form element data into an array of objects that can
 * be passed to any of the following ajax functions: $.get, $.post, or load.
 * Each object in the array has both a 'name' and 'value' property.  An example of
 * an array for a simple login form might be:
 *
 * [ { name: 'username', value: 'jresig' }, { name: 'password', value: 'secret' } ]
 *
 * It is this array that is passed to pre-submit callback functions provided to the
 * ajaxSubmit() and ajaxForm() methods.
 */
$.fn.formToArray = function(semantic, elements) {
    const a = [];
    if (!this.length) {
        return a;
    }

    const form = this[0];
    const formId = this.attr('id');
    let els = semantic ? form.getElementsByTagName('*') : form.elements;
    let els2;

    if (els && !/MSIE [678]/.test(navigator.userAgent)) { // #390
        els = $(els).get();  // convert to standard array
    }

    // #386; account for inputs outside the form which use the 'form' attribute

    if ( formId ) {
        els2 = $(':input[form="' + formId + '"]').get(); // hat tip @thet
        if ( els2.length ) {
            els = (els || []).concat(els2);
        }
    }

    if (!els || !els.length) {
        return a;
    }

    let i; let j; let n; let v; let el; let max; let jmax;
    for(i=0, max=els.length; i < max; i++) {
        el = els[i];
        n = el.name;
        if (!n || el.disabled) {
            continue;
        }

        if (semantic && form.clk && el.type == "image") {
            // handle image inputs on the fly when semantic == true

            if(form.clk == el) {
                a.push({name: n, value: $(el).val(), type: el.type });
                a.push({name: n+'.x', value: form.clk_x}, {name: n+'.y', value: form.clk_y});
            }
            continue;
        }

        v = $.fieldValue(el, true);
        if (v && v.constructor == Array) {
            if (elements) {
                elements.push(el);
            }
            for(j=0, jmax=v.length; j < jmax; j++) {
                a.push({name: n, value: v[j]});
            }
        }
        else if (feature.fileapi && el.type == 'file') {
            if (elements) {
                elements.push(el);
            }
            const files = el.files;
            if (files.length) {
                for (j=0; j < files.length; j++) {
                    a.push({name: n, value: files[j], type: el.type});
                }
            }
            else {
                // #180

                a.push({ name: n, value: '', type: el.type });
            }
        }
        else if (v !== null && typeof v !== 'undefined') {
            if (elements) {
                elements.push(el);
            }
            a.push({name: n, value: v, type: el.type, required: el.required});
        }
    }

    if (!semantic && form.clk) {
        // input type=='image' are not found in elements array! handle it here

        const $input = $(form.clk); const input = $input[0];
        n = input.name;
        if (n && !input.disabled && input.type == 'image') {
            a.push({name: n, value: $input.val()});
            a.push({name: n+'.x', value: form.clk_x}, {name: n+'.y', value: form.clk_y});
        }
    }

    return a;
};

/**
 * Serializes form data into a 'submittable' string. This method will return a string
 * in the format: name1=value1&amp;name2=value2
 */
$.fn.formSerialize = function(semantic) {
    // hand off to jQuery.param for proper encoding

    return $.param(this.formToArray(semantic));
};

/**
 * Serializes all field elements in the jQuery object into a query string.
 * This method will return a string in the format: name1=value1&amp;name2=value2
 */
$.fn.fieldSerialize = function(successful) {
    const a = [];
    this.each(function() {
        const n = this.name;
        if (!n) {
            return;
        }
        const v = $.fieldValue(this, successful);
        if (v && v.constructor == Array) {
            for (let i=0,max=v.length; i < max; i++) {
                a.push({name: n, value: v[i]});
            }
        }
        else if (v !== null && typeof v !== 'undefined') {
            a.push({name: this.name, value: v});
        }
    });


    // hand off to jQuery.param for proper encoding

    return $.param(a);
};

/**
 * Returns the value(s) of the element in the matched set.  For example, consider the following form:
 *
 *  <form><fieldset>
 *      <input name="A" type="text" />
 *      <input name="A" type="text" />
 *      <input name="B" type="checkbox" value="B1" />
 *      <input name="B" type="checkbox" value="B2"/>
 *      <input name="C" type="radio" value="C1" />
 *      <input name="C" type="radio" value="C2" />
 *  </fieldset></form>
 *
 *  var v = $('input[type=text]').fieldValue();
 *  // if no values are entered into the text inputs
 *  v == ['','']
 *  // if values entered into the text inputs are 'foo' and 'bar'
 *  v == ['foo','bar']
 *
 *  var v = $('input[type=checkbox]').fieldValue();
 *  // if neither checkbox is checked
 *  v === undefined
 *  // if both checkboxes are checked
 *  v == ['B1', 'B2']
 *
 *  var v = $('input[type=radio]').fieldValue();
 *  // if neither radio is checked
 *  v === undefined
 *  // if first radio is checked
 *  v == ['C1']
 *
 * The successful argument controls whether or not the field element must be 'successful'
 * (per http://www.w3.org/TR/html4/interact/forms.html#successful-controls).
 * The default value of the successful argument is true.  If this value is false the value(s)
 * for each element is returned.
 *
 * Note: This method *always* returns an array.  If no valid value can be determined the
 *    array will be empty, otherwise it will contain one or more values.
 */
$.fn.fieldValue = function(successful) {
    for (var val=[], i=0, max=this.length; i < max; i++) {
        const el = this[i];
        const v = $.fieldValue(el, successful);
        if (v === null || typeof v === 'undefined' || (v.constructor == Array && !v.length)) {
            continue;
        }
        if (v.constructor == Array) {
            $.merge(val, v);
        }
        else {
            val.push(v);
        }
    }

    return val;
};

/**
 * Returns the value of the field element.
 */
$.fieldValue = function(el, successful) {
    const n = el.name; const t = el.type; const tag = el.tagName.toLowerCase();
    if (successful === undefined) {
        successful = true;
    }

    if (successful && (!n || el.disabled || t == 'reset' || t == 'button' ||
        (t == 'checkbox' || t == 'radio') && !el.checked ||
        (t == 'submit' || t == 'image') && el.form && el.form.clk != el ||
        tag == 'select' && el.selectedIndex == -1)) {
            return null;
    }

    if (tag == 'select') {
        const index = el.selectedIndex;
        if (index < 0) {
            return null;
        }
        const a = []; const ops = el.options;
        const one = (t == 'select-one');
        const max = (one ? index+1 : ops.length);
        for(let i=(one ? index : 0); i < max; i++) {
            const op = ops[i];
            if (op.selected) {
                let v = op.value;
                if (!v) { // extra pain for IE...
                    v = (op.attributes && op.attributes.value && !(op.attributes.value.specified)) ? op.text : op.value;
                }
                if (one) {
                    return v;
                }
                a.push(v);
            }
        }

        return a;
    }

    return $(el).val();
};

/**
 * Clears the form data.  Takes the following actions on the form's input fields:
 *  - input text fields will have their 'value' property set to the empty string
 *  - select elements will have their 'selectedIndex' property set to -1
 *  - checkbox and radio inputs will have their 'checked' property set to false
 *  - inputs of type submit, button, reset, and hidden will *not* be effected
 *  - button elements will *not* be effected
 */
$.fn.clearForm = function(includeHidden) {
    return this.each(function() {
        $('input,select,textarea', this).clearFields(includeHidden);
    });
};

/**
 * Clears the selected form elements.
 */
$.fn.clearFields = $.fn.clearInputs = function(includeHidden) {
    const re = /^(?:color|date|datetime|email|month|number|password|range|search|tel|text|time|url|week)$/i; // 'hidden' is not in this list

    return this.each(function() {
        const t = this.type; const tag = this.tagName.toLowerCase();
        if (re.test(t) || tag == 'textarea') {
            this.value = '';
        }
        else if (t == 'checkbox' || t == 'radio') {
            this.checked = false;
        }
        else if (tag == 'select') {
            this.selectedIndex = -1;
        }
        else if (t == "file") {
            if (/MSIE/.test(navigator.userAgent)) {
                $(this).replaceWith($(this).clone(true));
            } else {
                $(this).val('');
            }
        }
        else if (includeHidden) {
            // includeHidden can be the value true, or it can be a selector string
            // indicating a special test; for example:
            //  $('#myForm').clearForm('.special:hidden')
            // the above would clean hidden inputs that have the class of 'special'

            if ( (includeHidden === true && /hidden/.test(t)) ||
                 (typeof includeHidden === 'string' && $(this).is(includeHidden)) ) {
                this.value = '';
            }
        }
    });
};

/**
 * Resets the form data.  Causes all form elements to be reset to their original value.
 */
$.fn.resetForm = function() {
    return this.each(function() {
        // guard against an input with the name of 'reset'
        // note that IE reports the reset function as an 'object'

        if (typeof this.reset === 'function' || (typeof this.reset === 'object' && !this.reset.nodeType)) {
            this.reset();
        }
    });
};

/**
 * Enables or disables any matching elements.
 */
$.fn.enable = function(b) {
    if (b === undefined) {
        b = true;
    }

    return this.each(function() {
        this.disabled = !b;
    });
};

/**
 * Checks/unchecks any matching checkboxes or radio buttons and
 * selects/deselects and matching option elements.
 */
$.fn.selected = function(select) {
    if (select === undefined) {
        select = true;
    }

    return this.each(function() {
        const t = this.type;
        if (t == 'checkbox' || t == 'radio') {
            this.checked = select;
        }
        else if (this.tagName.toLowerCase() == 'option') {
            const $sel = $(this).parent('select');
            if (select && $sel[0] && $sel[0].type == 'select-one') {
                // deselect all other options

                $sel.find('option').selected(false);
            }
            this.selected = select;
        }
    });
};

// expose debug var

$.fn.ajaxSubmit.debug = false;

// helper fn for console logging

function log() {
    if (!$.fn.ajaxSubmit.debug) {
        return;
    }
    const msg = '[jquery.form] ' + Array.prototype.join.call(arguments,'');
    if (window.console && window.console.log) {
        window.console.log(msg);
    }
    else if (window.opera && window.opera.postError) {
        window.opera.postError(msg);
    }
}

}));

/*
 Copyright (C) Federico Zivolo 2019
 Distributed under the MIT License (license terms are at http://opensource.org/licenses/MIT).
 */(function(e,t){'object'===typeof exports&&'undefined'!==typeof module?module.exports=t():'function'===typeof define&&define.amd?define(t):e.Popper=t()})(this,()=> {'use strict';function e(e){return e&&'[object Function]'==={}.toString.call(e)}function t(e,t){if(1!==e.nodeType){return[];}const o=e.ownerDocument.defaultView; const n=o.getComputedStyle(e,null);

return t?n[t]:n}function o(e){return'HTML'===e.nodeName?e:e.parentNode||e.host}function n(e){if(!e){return document.body;}switch(e.nodeName){case'HTML':case'BODY':return e.ownerDocument.body;case'#document':return e.body;}const i=t(e); const r=i.overflow; const p=i.overflowX; const s=i.overflowY;

return /(auto|scroll|overlay)/.test(r+s+p)?e:n(o(e))}function r(e){return 11===e?pe:10===e?se:pe||se}function p(e){if(!e){return document.documentElement;}for(var o=r(10)?document.body:null,n=e.offsetParent||null;n===o&&e.nextElementSibling;){n=(e=e.nextElementSibling).offsetParent;}const i=n&&n.nodeName;

return i&&'BODY'!==i&&'HTML'!==i?-1!==['TH','TD','TABLE'].indexOf(n.nodeName)&&'static'===t(n,'position')?p(n):n:e?e.ownerDocument.documentElement:document.documentElement}function s(e){const t=e.nodeName;

return'BODY'!==t&&('HTML'===t||p(e.firstElementChild)===e)}function d(e){return null===e.parentNode?e:d(e.parentNode)}function a(e,t){if(!e||!e.nodeType||!t||!t.nodeType){return document.documentElement;}const o=e.compareDocumentPosition(t)&Node.DOCUMENT_POSITION_FOLLOWING; const n=o?e:t; const i=o?t:e; const r=document.createRange();r.setStart(n,0),r.setEnd(i,0);const l=r.commonAncestorContainer;if(e!==l&&t!==l||n.contains(i)){return s(l)?l:p(l);}const f=d(e);

return f.host?a(f.host,t):a(e,d(t).host)}function l(e){const t=1<arguments.length&&void 0!==arguments[1]?arguments[1]:'top'; const o='top'===t?'scrollTop':'scrollLeft'; const n=e.nodeName;if('BODY'===n||'HTML'===n){const i=e.ownerDocument.documentElement; const r=e.ownerDocument.scrollingElement||i;

return r[o]}

return e[o]}function f(e,t){const o=2<arguments.length&&void 0!==arguments[2]&&arguments[2]; const n=l(t,'top'); const i=l(t,'left'); const r=o?-1:1;

return e.top+=n*r,e.bottom+=n*r,e.left+=i*r,e.right+=i*r,e}function m(e,t){const o='x'===t?'Left':'Top'; const n='Left'==o?'Right':'Bottom';

return parseFloat(e['border'+o+'Width'],10)+parseFloat(e['border'+n+'Width'],10)}function h(e,t,o,n){return ee(t['offset'+e],t['scroll'+e],o['client'+e],o['offset'+e],o['scroll'+e],r(10)?parseInt(o['offset'+e])+parseInt(n['margin'+('Height'===e?'Top':'Left')])+parseInt(n['margin'+('Height'===e?'Bottom':'Right')]):0)}function c(e){const t=e.body; const o=e.documentElement; const n=r(10)&&getComputedStyle(o);

return{height:h('Height',t,o,n),width:h('Width',t,o,n)}}function g(e){return {...e,right:e.left+e.width,bottom:e.top+e.height}}function u(e){let o={};try{if(r(10)){o=e.getBoundingClientRect();const n=l(e,'top'); const i=l(e,'left');o.top+=n,o.left+=i,o.bottom+=n,o.right+=i}else {o=e.getBoundingClientRect()}}catch(t){}const p={left:o.left,top:o.top,width:o.right-o.left,height:o.bottom-o.top}; const s='HTML'===e.nodeName?c(e.ownerDocument):{}; const d=s.width||e.clientWidth||p.right-p.left; const a=s.height||e.clientHeight||p.bottom-p.top; let f=e.offsetWidth-d; let h=e.offsetHeight-a;if(f||h){const u=t(e);f-=m(u,'x'),h-=m(u,'y'),p.width-=f,p.height-=h}

return g(p)}function b(e,o){const i=2<arguments.length&&void 0!==arguments[2]&&arguments[2]; const p=r(10); const s='HTML'===o.nodeName; const d=u(e); const a=u(o); const l=n(e); const m=t(o); const h=parseFloat(m.borderTopWidth,10); const c=parseFloat(m.borderLeftWidth,10);i&&s&&(a.top=ee(a.top,0),a.left=ee(a.left,0));let b=g({top:d.top-a.top-h,left:d.left-a.left-c,width:d.width,height:d.height});if(b.marginTop=0,b.marginLeft=0,!p&&s){const w=parseFloat(m.marginTop,10); const y=parseFloat(m.marginLeft,10);b.top-=h-w,b.bottom-=h-w,b.left-=c-y,b.right-=c-y,b.marginTop=w,b.marginLeft=y}

return(p&&!i?o.contains(l):o===l&&'BODY'!==l.nodeName)&&(b=f(b,o)),b}function w(e){const t=1<arguments.length&&void 0!==arguments[1]&&arguments[1]; const o=e.ownerDocument.documentElement; const n=b(e,o); const i=ee(o.clientWidth,window.innerWidth||0); const r=ee(o.clientHeight,window.innerHeight||0); const p=t?0:l(o); const s=t?0:l(o,'left'); const d={top:p-n.top+n.marginTop,left:s-n.left+n.marginLeft,width:i,height:r};

return g(d)}function y(e){const n=e.nodeName;if('BODY'===n||'HTML'===n){return!1;}if('fixed'===t(e,'position')){return!0;}const i=o(e);

return!!i&&y(i)}function E(e){if(!e||!e.parentElement||r()){return document.documentElement;}for(var o=e.parentElement;o&&'none'===t(o,'transform');){o=o.parentElement;}

return o||document.documentElement}function v(e,t,i,r){const p=4<arguments.length&&void 0!==arguments[4]&&arguments[4]; let s={top:0,left:0}; const d=p?E(e):a(e,t);if('viewport'===r){s=w(d,p);}else{let l;'scrollParent'===r?(l=n(o(t)),'BODY'===l.nodeName&&(l=e.ownerDocument.documentElement)):'window'===r?l=e.ownerDocument.documentElement:l=r;const f=b(l,d,p);if('HTML'===l.nodeName&&!y(d)){const m=c(e.ownerDocument); const h=m.height; const g=m.width;s.top+=f.top-f.marginTop,s.bottom=h+f.top,s.left+=f.left-f.marginLeft,s.right=g+f.left}else {s=f}}i=i||0;const u='number'===typeof i;

return s.left+=u?i:i.left||0,s.top+=u?i:i.top||0,s.right-=u?i:i.right||0,s.bottom-=u?i:i.bottom||0,s}function x(e){const t=e.width; const o=e.height;

return t*o}function O(e,t,o,n,i){const r=5<arguments.length&&void 0!==arguments[5]?arguments[5]:0;if(-1===e.indexOf('auto')){return e;}const p=v(o,n,r,i); const s={top:{width:p.width,height:t.top-p.top},right:{width:p.right-t.right,height:p.height},bottom:{width:p.width,height:p.bottom-t.bottom},left:{width:t.left-p.left,height:p.height}}; const d=Object.keys(s).map((e)=> {return {key:e,...s[e],area:x(s[e])}}).sort((e,t)=> {return t.area-e.area}); const a=d.filter((e)=> {const t=e.width; const n=e.height;

return t>=o.clientWidth&&n>=o.clientHeight}); const l=a.length?a[0].key:d[0].key; const f=e.split('-')[1];

return l+(f?'-'+f:'')}function L(e,t,o){const n=3<arguments.length&&void 0!==arguments[3]?arguments[3]:null; const i=n?E(t):a(t,o);

return b(o,i,n)}function S(e){const t=e.ownerDocument.defaultView; const o=t.getComputedStyle(e); const n=parseFloat(o.marginTop||0)+parseFloat(o.marginBottom||0); const i=parseFloat(o.marginLeft||0)+parseFloat(o.marginRight||0); const r={width:e.offsetWidth+i,height:e.offsetHeight+n};

return r}function T(e){const t={left:'right',right:'left',bottom:'top',top:'bottom'};

return e.replace(/left|right|bottom|top/g,(e)=> {return t[e]})}function D(e,t,o){o=o.split('-')[0];const n=S(e); const i={width:n.width,height:n.height}; const r=-1!==['right','left'].indexOf(o); const p=r?'top':'left'; const s=r?'left':'top'; const d=r?'height':'width'; const a=r?'width':'height';

return i[p]=t[p]+t[d]/2-n[d]/2,i[s]=o===s?t[s]-n[a]:t[T(s)],i}function C(e,t){return Array.prototype.find?e.find(t):e.filter(t)[0]}function N(e,t,o){if(Array.prototype.findIndex){return e.findIndex((e)=> {return e[t]===o});}const n=C(e,(e)=> {return e[t]===o});

return e.indexOf(n)}function P(t,o,n){const i=void 0===n?t:t.slice(0,N(t,'name',n));

return i.forEach((t)=> {t['function']&&console.warn('`modifier.function` is deprecated, use `modifier.fn`!');const n=t['function']||t.fn;t.enabled&&e(n)&&(o.offsets.popper=g(o.offsets.popper),o.offsets.reference=g(o.offsets.reference),o=n(o,t))}),o}function k(){if(!this.state.isDestroyed){let e={instance:this,styles:{},arrowStyles:{},attributes:{},flipped:!1,offsets:{}};e.offsets.reference=L(this.state,this.popper,this.reference,this.options.positionFixed),e.placement=O(this.options.placement,e.offsets.reference,this.popper,this.reference,this.options.modifiers.flip.boundariesElement,this.options.modifiers.flip.padding),e.originalPlacement=e.placement,e.positionFixed=this.options.positionFixed,e.offsets.popper=D(this.popper,e.offsets.reference,e.placement),e.offsets.popper.position=this.options.positionFixed?'fixed':'absolute',e=P(this.modifiers,e),this.state.isCreated?this.options.onUpdate(e):(this.state.isCreated=!0,this.options.onCreate(e))}}function W(e,t){return e.some((e)=> {const o=e.name; const n=e.enabled;

return n&&o===t})}function H(e){for(let t=[!1,'ms','Webkit','Moz','O'],o=e.charAt(0).toUpperCase()+e.slice(1),n=0;n<t.length;n++){const i=t[n]; const r=i?''+i+o:e;if('undefined'!==typeof document.body.style[r]){return r}}

return null}function B(){return this.state.isDestroyed=!0,W(this.modifiers,'applyStyle')&&(this.popper.removeAttribute('x-placement'),this.popper.style.position='',this.popper.style.top='',this.popper.style.left='',this.popper.style.right='',this.popper.style.bottom='',this.popper.style.willChange='',this.popper.style[H('transform')]=''),this.disableEventListeners(),this.options.removeOnDestroy&&this.popper.parentNode.removeChild(this.popper),this}function A(e){const t=e.ownerDocument;

return t?t.defaultView:window}function M(e,t,o,i){const r='BODY'===e.nodeName; const p=r?e.ownerDocument.defaultView:e;p.addEventListener(t,o,{passive:!0}),r||M(n(p.parentNode),t,o,i),i.push(p)}function F(e,t,o,i){o.updateBound=i,A(e).addEventListener('resize',o.updateBound,{passive:!0});const r=n(e);

return M(r,'scroll',o.updateBound,o.scrollParents),o.scrollElement=r,o.eventsEnabled=!0,o}function I(){this.state.eventsEnabled||(this.state=F(this.reference,this.options,this.state,this.scheduleUpdate))}function R(e,t){return A(e).removeEventListener('resize',t.updateBound),t.scrollParents.forEach((e)=> {e.removeEventListener('scroll',t.updateBound)}),t.updateBound=null,t.scrollParents=[],t.scrollElement=null,t.eventsEnabled=!1,t}function U(){this.state.eventsEnabled&&(cancelAnimationFrame(this.scheduleUpdate),this.state=R(this.reference,this.state))}function Y(e){return''!==e&&!isNaN(parseFloat(e))&&isFinite(e)}function j(e,t){Object.keys(t).forEach((o)=> {let n='';-1!==['width','height','top','right','bottom','left'].indexOf(o)&&Y(t[o])&&(n='px'),e.style[o]=t[o]+n})}function V(e,t){Object.keys(t).forEach((o)=> {const n=t[o];!1===n?e.removeAttribute(o):e.setAttribute(o,t[o])})}function q(e,t){const o=e.offsets; const n=o.popper; const i=o.reference; const r=$; const p=function(e){return e}; const s=r(i.width); const d=r(n.width); const a=-1!==['left','right'].indexOf(e.placement); const l=-1!==e.placement.indexOf('-'); const f=t?a||l||s%2==d%2?r:Z:p; const m=t?r:p;

return{left:f(1==s%2&&1==d%2&&!l&&t?n.left-1:n.left),top:m(n.top),bottom:m(n.bottom),right:f(n.right)}}function K(e,t,o){const n=C(e,(e)=> {const o=e.name;

return o===t}); const i=!!n&&e.some((e)=> {return e.name===o&&e.enabled&&e.order<n.order});if(!i){const r='`'+t+'`';console.warn('`'+o+'`'+' modifier is required by '+r+' modifier in order to work, be sure to include it before '+r+'!')}

return i}function z(e){return'end'===e?'start':'start'===e?'end':e}function G(e){const t=1<arguments.length&&void 0!==arguments[1]&&arguments[1]; const o=ce.indexOf(e); const n=ce.slice(o+1).concat(ce.slice(0,o));

return t?n.reverse():n}function _(e,t,o,n){const i=e.match(/((?:\-|\+)?\d*\.?\d*)(.*)/); const r=+i[1]; const p=i[2];if(!r){return e;}if(0===p.indexOf('%')){let s;switch(p){case'%p':s=o;break;case'%':case'%r':default:s=n;}const d=g(s);

return d[t]/100*r}if('vh'===p||'vw'===p){let a;

return a='vh'===p?ee(document.documentElement.clientHeight,window.innerHeight||0):ee(document.documentElement.clientWidth,window.innerWidth||0),a/100*r}

return r}function X(e,t,o,n){const i=[0,0]; const r=-1!==['right','left'].indexOf(n); const p=e.split(/(\+|\-)/).map((e)=> {return e.trim()}); const s=p.indexOf(C(p,(e)=> {return-1!==e.search(/,|\s/)}));p[s]&&-1===p[s].indexOf(',')&&console.warn('Offsets separated by white space(s) are deprecated, use a comma (,) instead.');const d=/\s*,\s*|\s+/; let a=-1===s?[p]:[p.slice(0,s).concat([p[s].split(d)[0]]),[p[s].split(d)[1]].concat(p.slice(s+1))];

return a=a.map((e,n)=> {const i=(1===n?!r:r)?'height':'width'; let p=!1;

return e.reduce((e,t)=> {return''===e[e.length-1]&&-1!==['+','-'].indexOf(t)?(e[e.length-1]=t,p=!0,e):p?(e[e.length-1]+=t,p=!1,e):e.concat(t)},[]).map((e)=> {return _(e,i,t,o)})}),a.forEach((e,t)=> {e.forEach((o,n)=> {Y(o)&&(i[t]+=o*('-'===e[n-1]?-1:1))})}),i}function J(e,t){let o; const n=t.offset; const i=e.placement; const r=e.offsets; const p=r.popper; const s=r.reference; const d=i.split('-')[0];

return o=Y(+n)?[+n,0]:X(n,p,s,d),'left'===d?(p.top+=o[0],p.left-=o[1]):'right'===d?(p.top+=o[0],p.left+=o[1]):'top'===d?(p.left+=o[0],p.top-=o[1]):'bottom'===d&&(p.left+=o[0],p.top+=o[1]),e.popper=p,e}for(var Q=Math.min,Z=Math.floor,$=Math.round,ee=Math.max,te='undefined'!==typeof window&&'undefined'!==typeof document,oe=['Edge','Trident','Firefox'],ne=0,ie=0;ie<oe.length;ie+=1){if(te&&0<=navigator.userAgent.indexOf(oe[ie])){ne=1;break}}const i=te&&window.Promise; const re=i?function(e){let t=!1;

return function(){t||(t=!0,window.Promise.resolve().then(()=> {t=!1,e()}))}}:function(e){let t=!1;

return function(){t||(t=!0,setTimeout(()=> {t=!1,e()},ne))}}; var pe=te&&!!(window.MSInputMethodContext&&document.documentMode); var se=te&&/MSIE 10/.test(navigator.userAgent); const de=function(e,t){if(!(e instanceof t)){throw new TypeError('Cannot call a class as a function')}}; const ae=function(){function e(e,t){for(var o,n=0;n<t.length;n++){o=t[n],o.enumerable=o.enumerable||!1,o.configurable=!0,'value'in o&&(o.writable=!0),Object.defineProperty(e,o.key,o)}}

return function(t,o,n){return o&&e(t.prototype,o),n&&e(t,n),t}}(); const le=function(e,t,o){return t in e?Object.defineProperty(e,t,{value:o,enumerable:!0,configurable:!0,writable:!0}):e[t]=o,e}; const fe=Object.assign||function(e){for(var t,o=1;o<arguments.length;o++){for(const n in t=arguments[o],t){Object.prototype.hasOwnProperty.call(t,n)&&(e[n]=t[n]);}}

return e}; const me=te&&/Firefox/i.test(navigator.userAgent); const he=['auto-start','auto','auto-end','top-start','top','top-end','right-start','right','right-end','bottom-end','bottom','bottom-start','left-end','left','left-start']; var ce=he.slice(3); const ge={FLIP:'flip',CLOCKWISE:'clockwise',COUNTERCLOCKWISE:'counterclockwise'}; const ue=function(){function t(o,n){const i=this; const r=2<arguments.length&&void 0!==arguments[2]?arguments[2]:{};de(this,t),this.scheduleUpdate=function(){return requestAnimationFrame(i.update)},this.update=re(this.update.bind(this)),this.options={...t.Defaults,...r},this.state={isDestroyed:!1,isCreated:!1,scrollParents:[]},this.reference=o&&o.jquery?o[0]:o,this.popper=n&&n.jquery?n[0]:n,this.options.modifiers={},Object.keys({...t.Defaults.modifiers,...r.modifiers}).forEach((e)=> {i.options.modifiers[e]={...t.Defaults.modifiers[e]||{},...(r.modifiers?r.modifiers[e]:{})}}),this.modifiers=Object.keys(this.options.modifiers).map((e)=> {return {name:e,...i.options.modifiers[e]}}).sort((e,t)=> {return e.order-t.order}),this.modifiers.forEach((t)=> {t.enabled&&e(t.onLoad)&&t.onLoad(i.reference,i.popper,i.options,t,i.state)}),this.update();const p=this.options.eventsEnabled;p&&this.enableEventListeners(),this.state.eventsEnabled=p}

return ae(t,[{key:'update',value(){return k.call(this)}},{key:'destroy',value(){return B.call(this)}},{key:'enableEventListeners',value(){return I.call(this)}},{key:'disableEventListeners',value(){return U.call(this)}}]),t}();

return ue.Utils=('undefined'===typeof window?global:window).PopperUtils,ue.placements=he,ue.Defaults={placement:'bottom',positionFixed:!1,eventsEnabled:!0,removeOnDestroy:!1,onCreate(){},onUpdate(){},modifiers:{shift:{order:100,enabled:!0,fn(e){const t=e.placement; const o=t.split('-')[0]; const n=t.split('-')[1];if(n){const i=e.offsets; const r=i.reference; const p=i.popper; const s=-1!==['bottom','top'].indexOf(o); const d=s?'left':'top'; const a=s?'width':'height'; const l={start:le({},d,r[d]),end:le({},d,r[d]+r[a]-p[a])};e.offsets.popper={...p,...l[n]}}

return e}},offset:{order:200,enabled:!0,fn:J,offset:0},preventOverflow:{order:300,enabled:!0,fn(e,t){let o=t.boundariesElement||p(e.instance.popper);e.instance.reference===o&&(o=p(o));const n=H('transform'); const i=e.instance.popper.style; const r=i.top; const s=i.left; const d=i[n];i.top='',i.left='',i[n]='';const a=v(e.instance.popper,e.instance.reference,t.padding,o,e.positionFixed);i.top=r,i.left=s,i[n]=d,t.boundaries=a;const l=t.priority; let f=e.offsets.popper; const m={primary(e){let o=f[e];

return f[e]<a[e]&&!t.escapeWithReference&&(o=ee(f[e],a[e])),le({},e,o)},secondary(e){const o='right'===e?'left':'top'; let n=f[o];

return f[e]>a[e]&&!t.escapeWithReference&&(n=Q(f[o],a[e]-('right'===e?f.width:f.height))),le({},o,n)}};

return l.forEach((e)=> {const t=-1===['left','top'].indexOf(e)?'secondary':'primary';f={...f,...m[t](e)}}),e.offsets.popper=f,e},priority:['left','right','top','bottom'],padding:5,boundariesElement:'scrollParent'},keepTogether:{order:400,enabled:!0,fn(e){const t=e.offsets; const o=t.popper; const n=t.reference; const i=e.placement.split('-')[0]; const r=Z; const p=-1!==['top','bottom'].indexOf(i); const s=p?'right':'bottom'; const d=p?'left':'top'; const a=p?'width':'height';

return o[s]<r(n[d])&&(e.offsets.popper[d]=r(n[d])-o[a]),o[d]>r(n[s])&&(e.offsets.popper[d]=r(n[s])),e}},arrow:{order:500,enabled:!0,fn(e,o){let n;if(!K(e.instance.modifiers,'arrow','keepTogether')){return e;}let i=o.element;if('string'===typeof i){if(i=e.instance.popper.querySelector(i),!i){return e;}}else if(!e.instance.popper.contains(i)){return console.warn('WARNING: `arrow.element` must be child of its popper element!'),e;}const r=e.placement.split('-')[0]; const p=e.offsets; const s=p.popper; const d=p.reference; const a=-1!==['left','right'].indexOf(r); const l=a?'height':'width'; const f=a?'Top':'Left'; const m=f.toLowerCase(); const h=a?'left':'top'; const c=a?'bottom':'right'; const u=S(i)[l];d[c]-u<s[m]&&(e.offsets.popper[m]-=s[m]-(d[c]-u)),d[m]+u>s[c]&&(e.offsets.popper[m]+=d[m]+u-s[c]),e.offsets.popper=g(e.offsets.popper);const b=d[m]+d[l]/2-u/2; const w=t(e.instance.popper); const y=parseFloat(w['margin'+f],10); const E=parseFloat(w['border'+f+'Width'],10); let v=b-e.offsets.popper[m]-y-E;

return v=ee(Q(s[l]-u,v),0),e.arrowElement=i,e.offsets.arrow=(n={},le(n,m,$(v)),le(n,h,''),n),e},element:'[x-arrow]'},flip:{order:600,enabled:!0,fn(e,t){if(W(e.instance.modifiers,'inner')){return e;}if(e.flipped&&e.placement===e.originalPlacement){return e;}const o=v(e.instance.popper,e.instance.reference,t.padding,t.boundariesElement,e.positionFixed); let n=e.placement.split('-')[0]; let i=T(n); let r=e.placement.split('-')[1]||''; let p=[];switch(t.behavior){case ge.FLIP:p=[n,i];break;case ge.CLOCKWISE:p=G(n);break;case ge.COUNTERCLOCKWISE:p=G(n,!0);break;default:p=t.behavior;}

return p.forEach((s,d)=> {if(n!==s||p.length===d+1){return e;}n=e.placement.split('-')[0],i=T(n);const a=e.offsets.popper; const l=e.offsets.reference; const f=Z; const m='left'===n&&f(a.right)>f(l.left)||'right'===n&&f(a.left)<f(l.right)||'top'===n&&f(a.bottom)>f(l.top)||'bottom'===n&&f(a.top)<f(l.bottom); const h=f(a.left)<f(o.left); const c=f(a.right)>f(o.right); const g=f(a.top)<f(o.top); const u=f(a.bottom)>f(o.bottom); const b='left'===n&&h||'right'===n&&c||'top'===n&&g||'bottom'===n&&u; const w=-1!==['top','bottom'].indexOf(n); const y=!!t.flipVariations&&(w&&'start'===r&&h||w&&'end'===r&&c||!w&&'start'===r&&g||!w&&'end'===r&&u);(m||b||y)&&(e.flipped=!0,(m||b)&&(n=p[d+1]),y&&(r=z(r)),e.placement=n+(r?'-'+r:''),e.offsets.popper={...e.offsets.popper,...D(e.instance.popper,e.offsets.reference,e.placement)},e=P(e.instance.modifiers,e,'flip'))}),e},behavior:'flip',padding:5,boundariesElement:'viewport'},inner:{order:700,enabled:!1,fn(e){const t=e.placement; const o=t.split('-')[0]; const n=e.offsets; const i=n.popper; const r=n.reference; const p=-1!==['left','right'].indexOf(o); const s=-1===['top','left'].indexOf(o);

return i[p?'left':'top']=r[o]-(s?i[p?'width':'height']:0),e.placement=T(t),e.offsets.popper=g(i),e}},hide:{order:800,enabled:!0,fn(e){if(!K(e.instance.modifiers,'hide','preventOverflow')){return e;}const t=e.offsets.reference; const o=C(e.instance.modifiers,(e)=> {return'preventOverflow'===e.name}).boundaries;if(t.bottom<o.top||t.left>o.right||t.top>o.bottom||t.right<o.left){if(!0===e.hide){return e;}e.hide=!0,e.attributes['x-out-of-boundaries']=''}else{if(!1===e.hide){return e;}e.hide=!1,e.attributes['x-out-of-boundaries']=!1}

return e}},computeStyle:{order:850,enabled:!0,fn(e,t){const o=t.x; const n=t.y; const i=e.offsets.popper; const r=C(e.instance.modifiers,(e)=> {return'applyStyle'===e.name}).gpuAcceleration;void 0!==r&&console.warn('WARNING: `gpuAcceleration` option moved to `computeStyle` modifier and will not be supported in future versions of Popper.js!');let s; let d; const a=void 0===r?t.gpuAcceleration:r; const l=p(e.instance.popper); const f=u(l); const m={position:i.position}; const h=q(e,2>window.devicePixelRatio||!me); const c='bottom'===o?'top':'bottom'; const g='right'===n?'left':'right'; const b=H('transform');if(d='bottom'==c?'HTML'===l.nodeName?-l.clientHeight+h.bottom:-f.height+h.bottom:h.top,s='right'==g?'HTML'===l.nodeName?-l.clientWidth+h.right:-f.width+h.right:h.left,a&&b){m[b]='translate3d('+s+'px, '+d+'px, 0)',m[c]=0,m[g]=0,m.willChange='transform';}else{const w='bottom'==c?-1:1; const y='right'==g?-1:1;m[c]=d*w,m[g]=s*y,m.willChange=c+', '+g}const E={"x-placement":e.placement};

return e.attributes={...E,...e.attributes},e.styles={...m,...e.styles},e.arrowStyles={...e.offsets.arrow,...e.arrowStyles},e},gpuAcceleration:!0,x:'bottom',y:'right'},applyStyle:{order:900,enabled:!0,fn(e){return j(e.instance.popper,e.styles),V(e.instance.popper,e.attributes),e.arrowElement&&Object.keys(e.arrowStyles).length&&j(e.arrowElement,e.arrowStyles),e},onLoad(e,t,o,n,i){const r=L(i,t,e,o.positionFixed); const p=O(o.placement,r,t,e,o.modifiers.flip.boundariesElement,o.modifiers.flip.padding);

return t.setAttribute('x-placement',p),j(t,{position:o.positionFixed?'fixed':'absolute'}),o},gpuAcceleration:void 0}}},ue});

// # sourceMappingURL=popper.min.js.map

!function(e){let i; const t=e(document); let n=!1; const a=function debounce(e,i){let t;

return function(){const n=this; const a=arguments; const s=function later(){t=null,e.apply(n,a)};clearTimeout(t),t=setTimeout(s,i)}}; const s=(i=0,function(e,t){let n=e.attr("id");

return n||(n=t+i++,e.attr("id",n)),n}); const o=function toInt(e){return parseInt(e,10)||0}; const r=function SideNavigation(e,i){this.init(e,i)};r.TRANSITION_DURATION=500,r.prototype={_bindUI(){const i=this;i.useDataAttribute||(!function addResizeListener(){n||(e(window).on("resize",a((()=> {t.trigger("screenChange.lexicon.sidenav")}),150)),n=!0)}(),i._onScreenChange()),i.options.useDelegate?i._onDelegateClickTrigger():i._onClickTrigger(),i._onClickSidenavClose()},_focusElement(e){e.focus()},_getSidenavWidth(){const e=this.options.widthOriginal; let i=e; const t=window.innerWidth;

return t<e+40&&(i=t-40),i},_getSimpleSidenavType(){const e=this.options; const i=this._isDesktop(); const t=e.type; const n=e.typeMobile;

return i&&"fixed-push"===t?"desktop-fixed-push":i||"fixed-push"!==n?"fixed":"mobile-fixed-push"},_isDesktop(){return window.innerWidth>=this.options.breakpoint},_isSidenavRight(){const i=this.options;

return e(i.container).hasClass("sidenav-right")},_isSimpleSidenavClosed(){const i=this.options; const t=i.openClass;

return!e(i.container).hasClass(t)},_loadUrl(i,t,n){let a=i.data("url-loaded"); const s=a?a.readyState:0;n=n||i;const o=i.find(".sidebar-body").first();

return s||!o.length||"string"!==typeof t&&!e.isPlainObject(t)||(o.append('<div class="sidenav-loading">'+this.options.loadingIndicatorTPL+"</div>"),a=e.ajax(t).done(((e)=> {o.append(e),n.trigger("urlLoaded.lexicon.sidenav"),o.find(".sidenav-loading").remove()})),i.data("url-loaded",a)),a},_onClickSidenavClose(){const i=this; const n=i.options.container; const a=e(n).find(".sidenav-close").first(); const o="#"+s(a,"generatedLexiconSidenavCloseId"); const r="lexicon."+o;t.data(r)||(t.data(r,"true"),t.on("click.close.lexicon.sidenav",o,((e)=> {e.preventDefault(),i.toggle()}))),i.closeButtonSelector=o,i.dataCloseButtonSelector=r},_onClickTrigger(){const e=this;e.toggler.on("click.lexicon.sidenav",(()=> {e.toggle()}))},_onDelegateClickTrigger(){const e=this; const i=e.toggler; const n="#"+s(i,"generatedLexiconSidenavTogglerId"); const a="lexicon."+n;t.data(a)||(t.data(a,"true"),t.on("click.lexicon.sidenav",n,((i)=> {e.toggle(),i.preventDefault()}))),e.togglerSelector=n,e.dataTogglerSelector=a},_onScreenChange(){const i=this; const n=i.options; const a=e(n.container); const s=i.toggler; let o=i._setScreenSize();t.on("screenChange.lexicon.sidenav",(()=> {let e; const t=i._setScreenSize(); const r=i._isSidenavRight(); const l=t?n.type:n.typeMobile; const d="fixed"===l||"fixed-push"===l; const g=a.find(".sidenav-menu").first(); const v=n.widthOriginal; const c=n.rtl?"left":"right";a.toggleClass("sidenav-fixed",d),(!t&&o||t&&!o)&&(i.hideSidenav(),i.clearStyle(["min-height","height"]),a.addClass("closed").removeClass("open"),s.removeClass("active").removeClass("open"),o=!1,t&&(r&&g.css(c,v).css("width",v),o=!0));const h=a.hasClass("closed");t||(e=v,window.innerWidth<=v&&(e=window.innerWidth-n.gutter-25),r&&(h&&g.css(c,e),g.css("width",e)),o=!1),h||(i.clearStyle(["min-height","height"]),i.showSidenav(),i.setHeight())}))},_onSidenavTransitionEnd(e,i){const t=function complete(){e.removeClass("sidenav-transition"),i&&i()};bootstrap.Util.supportsTransitionEnd()?e.one("bsTransitionEnd",(()=> {t()})).emulateTransitionEnd(r.TRANSITION_DURATION):t.call(this)},_renderNav(){const i=this; const t=i.options; const n=e(t.container); const a=n.find(t.navigation).first().find(".sidenav-menu").first(); const s=n.hasClass("closed"); const o=i._isSidenavRight(); const r=i._getSidenavWidth();if(s){if(a.css("width",r),o){const l=t.rtl?"left":"right";a.css(l,r)}}else {i.showSidenav(),i.setHeight();}n.removeClass("sidenav-js-fouc")},_renderUI(){const i=this; const t=i.options; const n=e(t.container); const a=i.toggler; const s=i.mobile; const o=s?t.typeMobile:t.type;i.useDataAttribute||(s&&(n.addClass("closed").removeClass("open"),a.removeClass("active").removeClass("open")),"right"===t.position&&n.addClass("sidenav-right"),"relative"!==o&&n.addClass("sidenav-fixed"),i._renderNav()),n.css("display","")},_setScreenSize(){const e=function getBreakpointRegion(){const e=window.innerWidth;

return e>=1200?"lg":e>=992?"md":e>=768?"sm":e>=480?"xs":"xxs"}(); const i="sm"===e||"md"===e||"lg"===e;

return this.mobile=!i,this.desktop=i,i},clearStyle(i){const t=this.options; const n=e(t.container); const a=n.find(t.content).first(); const s=n.find(t.navigation).first(); const o=n.find(".sidenav-menu").first(); const r=a.add(s).add(o);if(Array.isArray(i)){for(let l=0;l<i.length;l++){r.css(i[l],"");}}else {r.css(i,"")}},destroy(){const i=this; const n=i.options; const a=e(i.options.container);t.off("click.close.lexicon.sidenav",i.closeButtonSelector),t.data(i.dataCloseButtonSelector,null),n.useDelegate?(t.off("click.lexicon.sidenav",i.togglerSelector),t.data(i.dataTogglerSelector,null)):a.off("click.lexicon.sidenav"),a.data("lexicon.sidenav",null)},hide(){const e=this;e.useDataAttribute?e.hideSimpleSidenav():e.toggleNavigation(!1)},hideSidenav(){const i=this; const t=i.options; const n=e(t.container); const a=n.find(t.content).first(); const s=n.find(t.navigation).first(); const o=s.find(".sidenav-menu").first(); const r=i._isSidenavRight(); let l=t.rtl?"right":"left";r&&(l=t.rtl?"left":"right");const d="padding-"+l;a.css(d,"").css(l,""),s.css("width",""),r&&o.css(l,i._getSidenavWidth())},hideSimpleSidenav(){const i=this; const t=i.options;if(!i._isSimpleSidenavClosed()){const n=e(t.content).first(); const a=e(t.container); const s=t.closedClass; const o=t.openClass; const r=i.toggler; const l=r.attr("href")||r.attr("data-target");a.trigger({toggler:e(i.togglerSelector),type:"closedStart.lexicon.sidenav"}),i._onSidenavTransitionEnd(n,(()=> {a.removeClass("sidenav-transition"),r.removeClass("sidenav-transition"),a.trigger({toggler:e(i.togglerSelector),type:"closed.lexicon.sidenav"})})),n.hasClass(o)&&n.addClass("sidenav-transition").addClass(s).removeClass(o),a.addClass("sidenav-transition"),r.addClass("sidenav-transition"),a.addClass(s).removeClass(o),e('[data-target="'+l+'"]').removeClass(o).removeClass("active"),e('[href="'+l+'"]').removeClass(o).removeClass("active")}},init(i,n){const a=this; const s="sidenav"===i.data("toggle");(n=e.extend({},e.fn.sideNavigation.defaults,n)).breakpoint=o(n.breakpoint),n.container=n.container||i.data("target")||i.attr("href"),n.gutter=o(n.gutter),n.heightType=n.heightType||!!n.equalHeight&&"equalHeight",n.rtl="rtl"===t.attr("dir"),n.width=o(n.width),n.widthOriginal=n.width,s&&(n.closedClass=i.data("closed-class")||"closed",n.content=i.data("content"),n.equalHeight=!1,n.loadingIndicatorTPL=i.data("loading-indicator-tpl")||n.loadingIndicatorTPL,n.openClass=i.data("open-class")||"open",n.toggler=i,n.type=i.data("type"),n.typeMobile=i.data("type-mobile"),n.url=i.data("url"),n.useDelegate=i.data("use-delegate"),n.width="",void 0===n.useDelegate&&(n.useDelegate=!0)),a.toggler=i,a.options=n,a.useDataAttribute=s,a._bindUI(),a._renderUI()},setEqualHeight(){const i=this.options; const t=e(i.container); const n=i.content; const a=i.navigation; const s=this.mobile?i.typeMobile:i.type;if("fixed"!==s&&"fixed-push"!==s){const o=t.find(n).first(); const r=t.find(a).first(); const l=t.find(".sidenav-menu").first(); const d=Math.max(o.outerHeight(),r.outerHeight());o.css("min-height",d),r.css({"height":"100%","min-height":d}),l.css({"height":"100%","min-height":d})}},setFullHeight(){const i=this.options; const n=e(i.container); const a=i.navigation;if("relative"===(this.mobile?i.typeMobile:i.type)){const s=n.find(a).first(); const o=n.find(".sidenav-menu").first(); let r=t.innerHeight()-s.offset().top;o.innerHeight()+s.offset().top>t.innerHeight()&&(r=o.innerHeight()),s.css({"height":"100%","min-height":r}),o.css({"height":"100%","min-height":r})}},setHeight(){const e=this; const i=e.options;"equalHeight"===i.heightType?e.setEqualHeight():"fullHeight"===i.heightType&&e.setFullHeight()},show(){const e=this;e.useDataAttribute?e.showSimpleSidenav():e.toggleNavigation(!0)},showSidenav(){const i=this; const t=i.mobile; const n=i.options; const a=e(n.container); const s=a.find(n.content).first(); const o=a.find(n.navigation).first(); const r=o.find(".sidenav-menu").first(); const l=i._isSidenavRight(); const d=i._getSidenavWidth(); const g=d+n.gutter; const v=n.url;v&&(a.one("urlLoaded.lexicon.sidenav",(()=> {i.setHeight()})),i._loadUrl(r,v,a)),o.css("width",d),r.css("width",d);let c=n.rtl?"right":"left";l&&(c=n.rtl?"left":"right");const h=t?c:"padding-"+c;if("fixed"!==(t?n.typeMobile:n.type)){let f=a.hasClass("open")?o.offset().left-n.gutter:o.offset().left-g; const u=s.offset().left; const p=s.innerWidth(); let C="";n.rtl&&l||!n.rtl&&"left"===n.position?(f=o.offset().left+g)>u&&(C=f-u):(n.rtl&&"left"===n.position||!n.rtl&&l)&&f<u+p&&(C=u+p-f)>=g&&(C=g),s.css(h,C)}},showSimpleSidenav(){const i=this; const t=i.options;if(i._isSimpleSidenavClosed()){const n=e(t.content).first(); const a=e(t.container); const s=t.closedClass; const o=t.openClass; const r=t.toggler; const l=r.data("url");l&&i._loadUrl(a,l),a.trigger({toggler:e(i.togglerSelector),type:"openStart.lexicon.sidenav"}),i._onSidenavTransitionEnd(n,(()=> {a.removeClass("sidenav-transition"),r.removeClass("sidenav-transition"),a.trigger({toggler:e(i.togglerSelector),type:"open.lexicon.sidenav"})})),n.addClass("sidenav-transition").addClass(o).removeClass(s),a.addClass("sidenav-transition"),r.addClass("sidenav-transition"),a.addClass(o).removeClass(s),r.addClass("active").addClass(o)}},toggle(){const e=this;e.useDataAttribute?e.toggleSimpleSidenav():e.toggleNavigation()},toggleNavigation(i){const t=this; const n=t.options; const a=e(n.container); const s=a.find(".sidenav-menu").first(); const o=t.toggler; const r=n.width; const l="boolean"===e.type(i)?i:a.hasClass("closed"); const d=t._isSidenavRight(); const g=l?"showSidenav":"hideSidenav";if(l?a.trigger({toggler:o,type:"openStart.lexicon.sidenav"}):a.trigger({toggler:o,type:"closedStart.lexicon.sidenav"}),t._onSidenavTransitionEnd(a,(()=> {const e=a.find(".sidenav-menu").first();a.hasClass("closed")?(t.clearStyle(["min-height","height"]),o.removeClass("open").removeClass("sidenav-transition"),a.trigger({toggler:o,type:"closed.lexicon.sidenav"})):(o.addClass("open").removeClass("sidenav-transition"),a.trigger({toggler:o,type:"open.lexicon.sidenav"})),t.mobile&&t._focusElement(e)})),l){t.setHeight(),s.css("width",r);const v=n.rtl?"left":"right";d&&s.css(v,"")}a.addClass("sidenav-transition"),o.addClass("sidenav-transition"),t[g](a),a.toggleClass("closed",!l).toggleClass("open",l),o.toggleClass("active",l).toggleClass("open",l)},toggleSimpleSidenav(){const e=this;e._isSimpleSidenavClosed()?e.showSimpleSidenav():e.hideSimpleSidenav()},visible(){let i; const t=this;if(t.useDataAttribute){i=t._isSimpleSidenavClosed();}else{const n=e(t.options.container);i=n.hasClass("sidenav-transition")?!n.hasClass("closed"):n.hasClass("closed")}

return!i}};const l=e.fn.sideNavigation; const d=function initialize(e,i,t){let n=e.data("lexicon.sidenav");

return n||(i||(i={}),i.selector=t,n=new r(e,i),e.data("lexicon.sidenav",n)),n}; const g=function Plugin(i){const t=this; const n=t.selector; let a=t; const s="string"===typeof i; const o="instance"===i; const r=e.makeArray(arguments).slice(1);

return s?this.each((function(){const t=e(this).data("lexicon.sidenav");if(t){if(o){return a=t,!1;}let n;if(e.isFunction(t[i])&&0!==i.indexOf("_")&&(n=t[i].apply(t,r)),n!==t&&void 0!==n){return a=n.jquery?a.pushStack(n.get()):n,!1}}else if(o){return a=null,!1}})):this.each((function(){d(e(this),i,n)})),a};g.noConflict=function(){return e.fn.sideNavigation=l,this},g.defaults={breakpoint:768,content:".sidenav-content",equalHeight:!0,gutter:"0px",heightType:null,loadingIndicatorTPL:'<div class="loading-animation loading-animation-md"></div>',navigation:".sidenav-menu-slider",position:"left",type:"relative",typeMobile:"relative",url:null,useDelegate:!0,width:"225px"},g.Constructor=r,e.fn.sideNavigation=g,e((()=> {const i=e('[data-toggle="sidenav"]');g.call(i)}))}(jQuery);
