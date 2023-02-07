<!DOCTYPE html>

<html lang="en" xmlns="http://www.w3.org/1999/xhtml">
<%
  String id = request.getParameter("id");
%>
<head>
  <meta charset="utf-8" />
</head>
<body>
  <video controls="controls" width="1366" height="764" autoplay muted>
    <source src="<%=id%>.mp4" type="video/mp4" />
  </video>
</body>
</html>