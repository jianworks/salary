From: =?utf-8?Q?=E7=94=B1_Windows_Internet_Explorer_8_=E5=84=B2=E5=AD=98?=
Subject: 
Date: Mon, 19 Nov 2012 15:13:22 +0800
MIME-Version: 1.0
Content-Type: text/html;
	charset="utf-8"
Content-Transfer-Encoding: quoted-printable
Content-Location: http://jqueryui.com/web-base-template/themes/jquery/js/selectivizr.js
X-MimeOLE: Produced By Microsoft MimeOLE V6.1.7601.17609

=EF=BB=BF<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML><HEAD>
<META content=3D"text/html; charset=3Dutf-8" http-equiv=3DContent-Type>
<META name=3DGENERATOR content=3D"MSHTML 8.00.7601.17940"></HEAD>
<BODY>/* selectivizr v1.0.0 - (c) Keith Clark, freely distributable =
under the=20
terms of the MIT license. selectivizr.com */ (function(x){function =
K(a){return=20
a.replace(L,o).replace(M,function(b,e,c){b=3Dc.split(",");c=3D0;for(var=20
g=3Db.length;c<G;C++){VAR=20
j=3D"N(b[c].replace(O,o).replace(P,o))+t,f=3D[];b[c]=3Dj.replace(Q,functi=
on(d,k,l,i,h){if(k){if(f.length">0){d=3Df;var=20
u;h=3Dj.substring(0,h).replace(R,n);if(h=3D=3Dn||h.charAt(h.length-1)=3D=3D=
t)h+=3D"*";try{u=3Dv(h)}catch(da){}if(u){h=3D0;for(l=3Du.length;h<L;H++){=
I=3DU[H];FOR(VAR=20
f=3D'a.indexOf("(");if(f'=20
b=3D'true,e=3DC(a.slice(1)),c=3Da.substring(0,5)=3D=3D":not(",g,j;if(c)a=3D=
a.slice(5,-1);var'=20
T(a){var e+b.join(?,?)})}function d}})}return=20
k}else{if(k=3D'l?T(l):!B||B.test(i)?{className:C(i),b:true}:null){f.push(=
k);return"."+k.className}return'=20
q=3D'd[z];if(!RegExp("(^|\\s)"+q.className+"(\\s|$)").test(i.className))i=
f(q.b&amp;&amp;(q.b=3D=3D=3Dtrue||q.b(i)=3D=3D=3Dtrue))y=3DA(y,q.classNam=
e,true)}i.className=3Dy}}f=3D[]}return'=20
y=3D"i.className,z=3D0,S=3Dd.length;z<S;z++){var">-1)a=3Da.substring(0,f)=
;if(a.charAt(0)=3D=3D":")switch(a.slice(1)){case=20
"root":b=3Dfunction(d){return c?d!=3DD:d=3D=3DD};break;case=20
"target":if(p=3D=3D8){b=3Dfunction(d){function k(){var=20
l=3Dlocation.hash,i=3Dl.slice(1);return=20
c?l=3D=3D""||d.id!=3Di:l!=3D""&amp;&amp;d.id=3D=3Di}x.attachEvent("onhash=
change",function(){r(d,e,k())});return=20
k()};break}return false;case=20
"checked":b=3Dfunction(d){U.test(d.type)&amp;&amp;d.attachEvent("onproper=
tychange",function(){event.propertyName=3D=3D"checked"&amp;&amp;r(d,e,d.c=
hecked!=3D=3Dc)});return=20
d.checked!=3D=3Dc};break;case "disabled":c=3D!c;case=20
"enabled":b=3Dfunction(d){if(V.test(d.tagName)){d.attachEvent("onproperty=
change",function(){event.propertyName=3D=3D"$disabled"&amp;&amp;r(d,e,d.a=
=3D=3D=3Dc)});w.push(d);d.a=3Dd.disabled;return=20
d.disabled=3D=3D=3Dc}return a=3D=3D":enabled"?c:!c};break;case=20
"focus":g=3D"focus";j=3D"blur";case=20
"hover":if(!g){g=3D"mouseenter";j=3D"mouseleave"}b=3Dfunction(d){d.attach=
Event("on"+(c?j:g),function(){r(d,e,true)});d.attachEvent("on"+(c?g:j),fu=
nction(){r(d,e,false)});return=20
c};break;default:if(!W.test(a))return=20
false;break}return{className:e,b:b}}function C(a){return=20
E+"-"+(p=3D=3D6&amp;&amp;X?Y++:a.replace(Z,function(b){return=20
b.charCodeAt(0)}))}function N(a){return =
a.replace(F,o).replace($,t)}function=20
r(a,b,e){var=20
c=3Da.className;b=3DA(c,b,e);if(b!=3Dc){a.className=3Db;a.parentNode.clas=
sName+=3Dn}}function=20
A(a,b,e){var c=3DRegExp("(^|\\s)"+b+"(\\s|$)"),g=3Dc.test(a);return=20
e?g?a:a+t+b:g?a.replace(c,o).replace(F,o):a}function=20
G(a,b){if(/^https?:\/\//i.test(a))return=20
b.substring(0,b.indexOf("/",8))=3D=3Da.substring(0,a.indexOf("/",8))?a:nu=
ll;if(a.charAt(0)=3D=3D"/")return=20
b.substring(0,b.indexOf("/",8))+a;var=20
e=3Db.split("?")[0];if(a.charAt(0)!=3D"?"&amp;&amp;e.charAt(e.length-1)!=3D=
"/")e=3De.substring(0,e.lastIndexOf("/")+1);return=20
e+a}function=20
H(a){if(a){s.open("GET",a,false);s.send();return(s.status=3D=3D200?s.resp=
onseText:n).replace(aa,n).replace(ba,function(b,e,c){return=20
H(G(c,a))})}return n}function ca(){var=20
a,b;a=3Dm.getElementsByTagName("BASE");for(var=20
e=3Da.length&gt;0?a[0].href:m.location.href,c=3D0;c<M.STYLESHEETS.LENGTH;=
C++){B=3DM.STYLESHEETS[C];IF(B.HREF!=3DN)IF(A=3DG(B.HREF,E))B.CSSTEXT=3DK=
(H(A))}W.LENGTH>0&amp;&amp;setInterval(function(){for(var=20
g=3D0,j=3Dw.length;g<J;G++){VAR=20
f=3D"w[g];if(f.disabled!=3D=3Df.a)if(f.disabled){f.disabled=3Dfalse;f.a=3D=
true;f.disabled=3Dtrue}else"=20
||p<6||p .exec(navigator.userAgent)[1];if(!(m.compatMode!=3D"CSS1Compat" =
([\d])=20
null}}(),p=3D"/MSIE" ActiveXObject(?Microsoft.XMLHTTP?)}catch(a){return =
new=20
XMLHttpRequest;try{return=20
m=3D"document,D=3Dm.documentElement,s=3Dfunction(){if(x.XMLHttpRequest)re=
turn"=20
f.a=3D"f.disabled}},250)}if(!/*@cc_on!@*/true){var">8||!s)){var=20
I=3D{NW:"*.Dom.select",DOMAssistant:"*.$",Prototype:"$$",YAHOO:"*.util.Se=
lector.query",MooTools:"$$",Sizzle:"*",jQuery:"*",dojo:"*.query"},v,w=3D[=
],Y=3D0,X=3Dtrue,E=3D"slvzr",J=3DE+"DOMReady",aa=3D/(\/\*[^*]*\*+([^\/][^=
*]*\*+)*\/)\s*/g,ba=3D/@import\s*url\(\s*(["'])?(.*?)\1\s*\)[\w\W]*?;/g,W=
=3D/^:(empty|(first|last|only|nth(-last)?)-(child|of-type))$/,L=3D/:(:fir=
st-(?:line|letter))/g,M=3D/(^|})\s*([^\{]*?[\[:][^{]+)/g,Q=3D/([=20
+~&gt;])|(:[a-z-]+(?:\(.*?\)+)?)|(\[.*?\])/g,R=3D/(:not\()?:(hover|enable=
d|disabled|focus|checked|target|active|visited|first-line|first-letter)\)=
?/g,Z=3D/[^\w-]/g,V=3D/^(INPUT|SELECT|TEXTAREA|BUTTON)$/,U=3D/^(checkbox|=
radio)$/,B=3Dp=3D=3D8?/[\$\^]=3D(['"])\1/:p=3D=3D7?/[\$\^*]=3D(['"])\1/:n=
ull,O=3D/([(\[+~])\s+/g,P=3D/\s+([)\]+~])/g,$=3D/\s+/g,F=3D/^\s*((?:[\S\s=
]*\S)?)\s*$/,n=3D"",t=3D"=20
",o=3D"$1";m.write("
<SCRIPT id=3D+J+ defer =
src=3D""><\/script>");m.getElementById(J).onreadystatechange=3Dfunction()=
{if(this.readyState=3D=3D"complete"){a:{var a;for(var b in =
I)if(x[b]&&(a=3Deval(I[b].replace("*",b)))){v=3Da;break =
a}v=3Dfalse}if(v){ca();this.parentNode.removeChild(this)}}}}}})(this);</S=
CRIPT>
</BODY></HTML>
