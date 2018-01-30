<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%	if (request.getAttribute("msg")!=null&&!request.getAttribute("msg").equals("")) { %>
	<script>
		 window.alert('<%=request.getAttribute("msg")%>'); 
	</script>
<% 	request.removeAttribute("msg");} %>