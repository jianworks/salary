<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.csjian.util.*" %>
<%@ page import="com.csjian.model.bean.*" %>

<%
	String regcode = (String)request.getAttribute("regcode");
	String[] tolist = {"勞工保險局", "中央健保署台北業務組", "中央健保署北區分局", "中央健保署中區分局", "中央健保署南區分局", "中央健保署高屏分局", "中央健保署東區分局"};
%>


  <SCRIPT LANGUAGE="JavaScript">
  <!--
    function doPrint() {
      with(document.mainform) {
        for (i=0; i<tocode.length; i++) {
          if (tocode[i].checked) {
            submit();
            return;
          }
        }
        alert ("請至少選擇一個送件單位");
      }
    }
    function retrieveForm() {
      with (document.mainform) {
        location.href = "servlet/RetrieveBlankForm?form=mailform";
      }
    }
  //-->
  </SCRIPT>

  <form method="POST" Name="mainform" class="entry-form" Action="<%=response.encodeURL("servlet/MailFormServlet") %>">
  <table width="100%"  border="0" cellpadding="0" cellspacing="0">
    <tr><td colspan=3>
      <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
        <TBODY>
          <tr>
            <td height="30" class=moduleTitle valign="bottom">郵寄封面列印</td>
          </tr>
          <TR><TD>
            <DIV class=hline><IMG height=1 src="images/blank.gif"></DIV>
          </TD></TR>
      </TBODY></TABLE><BR>
    </td></tr>
    <tr>
      <td height="10">&nbsp;</td>
      <td height="10" colspan="2">&nbsp;</td>
    </tr>
    <tr><td colspan=3>
      <TABLE class=formOuterBorder cellSpacing=0 cellPadding=0 width="100%" border=0><TBODY><TR><TD>
        <TABLE cellSpacing=1 cellPadding=0 width="100%" border=0>
          <TBODY><TR><TH class=formSecHeader align=left>送件單位</TH></TR></TBODY>
        </TABLE>
        <fieldset style="height:240px">
        <TABLE cellSpacing=1 cellPadding=2 width="98%" border=0><TBODY>
          <% for (int j=0; j<tolist.length; j++) { %>
          <tr <%=(j%2)==0?"class=evenListRow":"class=oddListRow"%>>
            <td width="30"><input type="checkbox" name="tocode" value="<%=j%>" ></td>
            <td height="20" align="left"><%=tolist[j]%></td>
          </tr>
          <% } %>
        </TABLE>
        </fieldset>
      </TD></TR></TBODY></TABLE>
      </td>
    </tr>
    <tr>
      <td height="10">&nbsp;</td>
      <td height="10" colspan="2">&nbsp;</td>
    </tr>
    <tr>
      <td height="30">&nbsp;</td>
      <td height="30" colspan="2">
        <div align="right">
        <input name="Submit3" type="button" class='ui-state-default' value="下載空白表單" onClick='retrieveForm();'>&nbsp;&nbsp; 
          <input name="Submit1" type="button" class='ui-state-default' value="列印" onClick='doPrint();'>
        </div></td>
    </tr>
  </table>
    <input type=hidden name="regcode" value="<%=regcode%>" />
  </form>