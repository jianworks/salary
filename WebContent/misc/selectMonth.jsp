<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<HEAD>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>選擇要複製的月份</title>
<style type="text/css">
<!--
@import url(../css/styles.css);
-->
</style>
</HEAD>
<%
	int year = (Calendar.getInstance()).get(Calendar.YEAR);
	int month = (Calendar.getInstance()).get(Calendar.MONTH) + 1;
%>
<BODY>
  <SCRIPT LANGUAGE="JavaScript">
  <!-- Begin
    function doSubmit() {
	  with (document.sform) {
	    //returnValue=year.value + "-" + month.value;
	    //window.close();
	    window.opener.windowOpenReturnFunc(year.value + "-" + month.value);
		window.close();
      }
    }  

  // End -->
  </script>

  <form name="sform" method="post" action="">
    <table width="100%"  border="0" cellpadding="0" cellspacing="0">
      <tr><td><TABLE class=formOuterBorder cellSpacing=0 cellPadding=0 width="100%" border=0><TBODY><TR><TD>
        <TABLE cellSpacing=1 cellPadding=0 width="100%" border=0>
          <TBODY><TR><TH class=formSecHeader align=left>請選擇要複製的月份</TH></TR></TBODY>
        </TABLE>
        <TABLE cellSpacing=1 cellPadding=2 width="100%" border=0><TBODY>
          <tr><td>
          <select name="year" class="select">
          <% for (int i=2006; i<=year; i++) { %>
          <option value="<%=i%>" <%=i==year?"selected":"" %>><%=(i-1911)%></option>
          <% } %>
        </select> &nbsp;&nbsp;年
        &nbsp;&nbsp;&nbsp;&nbsp;
        <select name="month" class="select">
          <% for (int i=1; i<=12; i++) { %>
          <option value="<%=i%>" <%=i==month?"selected":"" %>><%=i%></option>
          <% } %>
        </select> &nbsp;&nbsp;月
          <input name="Submit1" type="button" value="複製" onClick='doSubmit();'>
          </td></tr>
        </TABLE>
      </TD></TR></TBODY></TABLE></td>
      </tr>
    </table>
  </form>
</body>
</html>