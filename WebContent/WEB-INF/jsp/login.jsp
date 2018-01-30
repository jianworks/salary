<%@ page contentType="text/html;charset=UTF-8"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Frameset//EN" "http://www.w3.org/TR/html4/frameset.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>使用者登入</title>
	<link rel="stylesheet" href="css/styles-1.0.css" type="text/css" />
	<script src="js/jquery-1.9.0.min.js" type="text/javascript"></script>
	<script src="js/general.js" type=text/javascript></script>
	<script type="text/javascript">
		jQuery.fn.center = function () {
			this.css("position", "absolute");
			this.css("top", ($(window).height() - this.height()) / 2 + $(window).scrollTop() + "px");
			this.css("left", ($(window).width() - this.width()) / 2 + $(window).scrollLeft() + "px");
			return this;
		}
				
		$(document).ready(function () {
			$("#divLogin").center();
		});
	</script>
</head>
<%	if (request.getAttribute("msg")!=null&&!request.getAttribute("msg").equals("")) { %>
		<script>
			window.alert('<%=request.getAttribute("msg")%>'); 
		</script>
<% 	} %>

<body onload="checkframe();">
	<div id="divLogin">
		<form action="home.do?action=login" method="POST" >
  			<TABLE class=formOuterBorder cellSpacing=0 cellPadding=0 border=0><TBODY><TR><TD>
    			<TABLE cellSpacing=1 cellPadding=0 width="416" border=0>
      				<TBODY><TR><TH align=center><IMG SRC="images/header_s.gif" ALT="峻誠稅務記帳士事務所"></TH></TR></TBODY>
    			</TABLE>
    			<TABLE cellSpacing=1 cellPadding=2 width="100%" border=0><TBODY>
      				<tr>
        				<td width="150" align="right">公司統一編號：</td>
        				<td width="266" align="left"><input type="text" name="regcode"></td>
      				</tr>
      				<tr>
        				<td width="150" align="right">登入密碼：</td>
        				<td align="left"><input type="password" name="password" ></td>
      				</tr>
      				<tr>
        				<td colspan="2" >
        					<div align="center"><INPUT name="submit" type="submit" value="登入"></div>
        				</td>
      				</tr>
    			</TBODY></TABLE>
  			</TD></TR></TBODY></TABLE>
		</form>
	</div>
</body>
</html>
