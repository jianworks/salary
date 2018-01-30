<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ page import="java.util.*"%>
<%@ taglib prefix="c" uri="/WEB-INF/jstl-c.tld"%>

<html style="height: 100%;">

<head>
<title>峻誠稅務記帳士事務所</title>
<link rel="stylesheet" href="css/styles-1.0.css" type="text/css" />
<link rel="stylesheet" href="css/menu.css" type="text/css" />
<link rel="stylesheet" href="css/jquery-ui-1.10.0.custom.min.css" type="text/css" />
<link rel="stylesheet" href="css/jquery.dataTables.css" type="text/css" />
<link rel="stylesheet" href="css/form-1.1.css" type="text/css" />
<script src="js/general.js" type=text/javascript></script>
<script src="js/jquery-1.8.2.js" type=text/javascript></script>
<script src="js/jquery-ui-1.9.1.custom.min.js" type=text/javascript></script>
<script src="js/jquery.dataTables.min.js" type=text/javascript></script>
<script src="js/common-1.0.js" type=text/javascript></script>
<script src="js/menu-collapsed.js" type=text/javascript></script>
    <!--[if (gte IE 6)&(lte IE 8)]>
    <script src="js/selectivizr.js" type=text/javascript></script>
    <![endif]-->
<style type="text/css">
	#qna {
    	position: absolute;
    	z-index: 3;
    	right: 50px;
    	top: 32px;
	}
	#logout {
    	position: absolute;
    	z-index: 3;
    	right: 10px;
    	top: 32px;
	}
</style>
<style type="text/css" media="print">
	.noprint { display: none; }
</style>

<script type="text/javascript">
	$(document).ready(function () {	
		$("#message").dialog({
	        modal: true,
	        autoOpen: false,
	        width: 400,
	        height: 200,
	        buttons: {
	            "確定": function () {
	                $(this).dialog("close");
	            }
	        }
	    });
		<%	if (request.getAttribute("msg")!=null&&!request.getAttribute("msg").equals("")) { %>
			alert('<%=request.getAttribute("msg")%>');	
			// $('#messageBody').html('<%=request.getAttribute("msg")%>');
	    	//$("#message").dialog("open");	 
		<% 	request.removeAttribute("msg");} %>
	});	

	$.postJSON = function (url, data, callback) {
		$.post(url, data, callback, "json");
	}
</script>
</head>
<body leftMargin="0" topMargin="0" style="height: 100%; width: 100%">
    <div id="message" title="訊息通知" style="position:absolute; left:200px; top:200px;">
        <p id="messageBody">
        </p>
    </div>
	<!--<c:import url="message.jsp" />	-->
	<%  
  	List aplist0 = (ArrayList)request.getAttribute("aplist0");
  	List aplist1 = (ArrayList)request.getAttribute("aplist1");
  	List aplist2 = (ArrayList)request.getAttribute("aplist2");
  	List aplist3 = (ArrayList)request.getAttribute("aplist3");
  	List aplist4 = (ArrayList)request.getAttribute("aplist4");
  	List aplist5 = (ArrayList)request.getAttribute("aplist5");
  %>
  <IMG SRC="images/header.gif" ALT="峻誠稅務會計事務所">
	<table style="height: 100%; width: 100%" border="0" cellspacing="0" cellpadding="0" >		
		<tr align=left>
			<td vAlign=top style="width:223px; background-image: url('images/buttunBg.gif'); background-repeat: y-repeat; background-position: center;">				
			<a href="home.do?action=goodies" style="border:0px; border-color:#6BA613;">	
				<IMG SRC="images/button.gif" style="margin-left:7px; border:0px; border-color:#6BA613;">
			</a>
				<ul id="menu">
					<% if (aplist0.size()>0) { %>
					<li><a href="#">&nbsp;系統管理功能</a>
						<ul>
							<%	for (int i = 0; i < aplist0.size(); i++) {
									String[] ap = (String[]) aplist0.get(i);	%>
							<li><A href="<%=response.encodeURL(ap[1])%>" class="navlink">&nbsp;&nbsp;<%=ap[2]%></A></li>
							<%	}	%>
						</ul>		
					</li>
					<% } %>
					<% if (aplist1.size()>0) { %>
					<li><a href="#">&nbsp;員工及薪資資料建檔</a>
						<ul>
							<%	for (int i = 0; i < aplist1.size(); i++) {
									String[] ap = (String[]) aplist1.get(i);	%>
							<li><A href="<%=response.encodeURL(ap[1])%>" class="navlink">&nbsp;&nbsp;<%=ap[2]%></A></li>
							<%	}	%>
						</ul>		
					</li>
					<% } %>
					<% if (aplist2.size()>0) { %>
					<li><a href="#">&nbsp;報表產生列印</a>
						<ul>
							<%	for (int i = 0; i < aplist2.size(); i++) {
									String[] ap = (String[]) aplist2.get(i);	%>
							<li><A href="<%=response.encodeURL(ap[1])%>" class="navlink">&nbsp;&nbsp;<%=ap[2]%></A></li>
							<%	}	%>
							<li><A href='<%=response.encodeURL("report.do?action=employeeList")%>' class="navlink">&nbsp;&nbsp;員工基本資料報表</A></li>
						</ul>		
					</li>
					<% } %>
					<% if (aplist3.size()>0) { %>
					<li><a href="#">&nbsp;勞健保加退產生列印</a>
						<ul>
							<%	for (int i = 0; i < aplist3.size(); i++) {
									String[] ap = (String[]) aplist3.get(i);	%>
							<li><A href="<%=response.encodeURL(ap[1])%>" class="navlink">&nbsp;&nbsp;<%=ap[2]%></A></li>
							<%	}	%>
						</ul>		
					</li>
					<% } %>
					<% if (aplist4.size()>0) { %>
					<li><a href="#">&nbsp;成立勞健保投保單位</a>
						<ul>
							<%	for (int i = 0; i < aplist4.size(); i++) {
									String[] ap = (String[]) aplist4.get(i);	%>
							<li><A href="<%=response.encodeURL(ap[1])%>" class="navlink">&nbsp;&nbsp;<%=ap[2]%></A></li>
							<%	}	%>
						</ul>		
					</li>
					<% } %>
					<% if (aplist5.size()>0) { %>
					<li><a href="#">&nbsp;補充保費區</a>
						<ul>
							<%	for (int i = 0; i < aplist5.size(); i++) {
									String[] ap = (String[]) aplist5.get(i);	%>
							<li><A href="<%=response.encodeURL(ap[1])%>" class="navlink">&nbsp;&nbsp;<%=ap[2]%></A></li>
							<%	}	%>
						</ul>			
					</li>
					<% } %>
				</ul>
			
			</td>
			<td width="10">&nbsp;</td>
			<td vAlign=top align=left><c:import url="${includeModule}/${includeMain}.jsp" /></td>
		</tr>
	</table>
	<div id="qna" class="noprint">
      	<A href="home.do"><IMG hspace=4 src="images/help.gif" align=absMiddle border=0>網站使用教學</A>
  </div>	
  <div id="logout" class="noprint">
      	<A href="<%=response.encodeURL("home.do?action=logout")%>" ><IMG hspace=4 src="images/logout.gif" align=absMiddle border=0>登出本系統</A>
  </div>	
</body>
</html>