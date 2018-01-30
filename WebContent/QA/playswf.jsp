<!-- saved from url=(0014)about:internet -->
<HTML>
<%
  String id = request.getParameter("id");
%>
<BODY>
<center>
<OBJECT CLASSID="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" WIDTH="615" HEIGHT="468" CODEBASE="http://active.macromedia.com/flash5/cabs/swflash.cab#version=5,0,0,0">
<PARAM NAME=movie VALUE="http://www.4545jsp.com/QA/<%=id%>.swf">
<PARAM NAME=play VALUE=true>
<PARAM NAME=loop VALUE=false>
<PARAM NAME=quality VALUE=low>
<EMBED SRC="<%=id%>.swf" WIDTH=615 HEIGHT=468 quality=low loop=false TYPE="application/x-shockwave-flash" PLUGINSPAGE="http://www.macromedia.com/shockwave/download/index.cgi?P1_Prod_Version=ShockwaveFlash">
</EMBED>
</OBJECT>
</center>
</BODY>
</HTML>
