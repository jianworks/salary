<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ page import="java.util.*"%>
<%@ taglib prefix="c" uri="/WEB-INF/jstl-c.tld"%>

<html>

<head>
<title>峻誠稅務記帳士事務所</title>
<link rel="stylesheet" href="css/styles.css" type="text/css" />
<link rel="stylesheet" href="css/jquery-ui-1.10.0.custom.min.css" type="text/css" />
<script src="js/general.js" type=text/javascript></script>
<script src="js/jquery-1.9.0.min.js" type=text/javascript></script>
<script src="js/jquery-ui-1.10.0.custom.min.js" type=text/javascript></script>
<script src="js/menu-collapsed.js" type=text/javascript></script>
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
		$("#menu").menu();
	});
</script>
</head>
<body width="1000" leftMargin="0" topMargin="0">
	<c:import url="message.jsp" />	
	<%  
  	List<String[]> aplist0 = (ArrayList<String[]>)request.getAttribute("aplist0");
  	List<String[]> aplist1 = (ArrayList<String[]>)request.getAttribute("aplist1");
  	List<String[]> aplist2 = (ArrayList<String[]>)request.getAttribute("aplist2");
  	List<String[]> aplist3 = (ArrayList<String[]>)request.getAttribute("aplist3");
  //List aplist4 = (ArrayList)request.getAttribute("aplist4");
  %>
  <IMG SRC="images/header.gif" ALT="峻誠稅務會計事務所">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">		
		<tr align=left>
			<td vAlign=top width="223" style="background-image: url('images/buttunBg.gif'); background-repeat: y-repeat; background-position: center;">				
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
					
					<li><a href="#">&nbsp;成立勞健保投保單位</a>
						<ul>
							<li><A href='<%=response.encodeURL("form.do?action=paForm")%>' class="navlink">&nbsp;&nbsp;代辦委託書</A></li>
							<li><A href='<%=response.encodeURL("form.do?action=startForm")%>' class="navlink">&nbsp;&nbsp;三合一成立申請書</A></li>
							<li><A href='<%=response.encodeURL("form.do?action=addForm2")%>' class="navlink">&nbsp;&nbsp;三合一加保申報表</A></li>
							<li><A href='<%=response.encodeURL("form.do?action=declationForm")%>' class="navlink">&nbsp;&nbsp;勞保切結書</A></li>
							<li><A href='<%=response.encodeURL("form.do?action=proclamationForm")%>' class="navlink">&nbsp;&nbsp;健保切結書</A></li>
						</ul>		
					</li>
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