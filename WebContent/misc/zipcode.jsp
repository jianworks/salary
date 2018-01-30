<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Frameset//EN" "http://www.w3.org/TR/html4/frameset.dtd">
<%
	String zip = request.getParameter("zip")!=null?request.getParameter("zip"):"";
	String area = request.getParameter("area")!=null?request.getParameter("area"):"";
%>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<title>郵遞區號查詢</title>
<meta name="generator" content="Namo WebEditor v6.0">
<meta http-equiv="Content-Script-Type" content="text/javascript">
<script language=JavaScript src="../js/address.js" type=text/javascript></script>
<SCRIPT LANGUAGE="JavaScript">
  <!--
  function doSubmit() {
  	opener.document.getElementById("<%=zip%>").value=document.getElementById("b_zip").value + "00";
  	if (opener.document.getElementById("<%=area%>").value=="") {
  		opener.document.getElementById("<%=area%>").value=document.getElementById("b_city").value + document.getElementById("b_area").value;
  	}
  	window.close();
  }  
  //-->
</SCRIPT>
</head>

<body onload="initAddress('b_city', 'b_area');">
<div class="centerLayout">
<div style="float: left;" id="content">
<form method="POST" Name="mainform" Action="" >
<h1>郵遞區號查詢</h1>
<p><select name="b_city" id="b_city" onchange="swap('b_zip','b_city','b_area')" /> 縣/市 
	<select name="b_area" id="b_area" onchange="swaps('b_zip','b_city','b_area')"/> 鄉/鎮/區 
	&nbsp;&nbsp;&nbsp;<input class="button36Big" type="button" onclick='doSubmit();' value="確定"></p>
<input type="hidden" name="b_zip" id="b_zip" value=""/>
</form>
</div>
</div>
</body>

</html>
