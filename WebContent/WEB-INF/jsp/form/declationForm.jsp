<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.csjian.model.bean.*" %>

<%
	CompanyBean company = (CompanyBean)request.getAttribute("company");
%>

  <SCRIPT LANGUAGE="JavaScript">
  <!--
	function retrieveForm() {
      with (document.mainform) {
        location.href = "servlet/RetrieveBlankForm?form=declationform";
      }
    }

    function disKey(key) {
      var fields = new Array("regname","bossName","bossId");
      if (key == 38) {
        document.mainform.FOCUS_NAME.value = parseInt(document.mainform.FOCUS_NAME.value)>0?(parseInt(document.mainform.FOCUS_NAME.value)-1):"0";
        document.getElementById(fields[document.mainform.FOCUS_NAME.value]).focus();
      } else if (key == 40) {
        document.mainform.FOCUS_NAME.value = parseInt(document.mainform.FOCUS_NAME.value)<87?(parseInt(document.mainform.FOCUS_NAME.value)+1):"2";
        document.getElementById(fields[document.mainform.FOCUS_NAME.value]).focus();
      }
    }

    function changeFocus(strFocusid) {
      document.mainform.FOCUS_NAME.value = strFocusid;
    }

    function trim(str) {
	  while (str.indexOf(" ")==0) {
		str = str.substring(1, str.length);
	  }
	  while ((str.length>0) && (str.indexOf(" ")==(str.length-1))) {
		str = str.substring(0, str.length-1);
	  }
	  return str;
    }

  //-->
  </SCRIPT>

  <form method="POST" Name="mainform" class="entry-form" Action="<%=response.encodeURL("servlet/DeclationFormServlet") %>" onKeyDown="disKey(event.keyCode);">
  <table width="100%"  border="0" cellpadding="0" cellspacing="0">
    <tr><td colspan=3>
      <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
        <TBODY>
          <tr>
            <td height="30" class=moduleTitle valign="bottom">勞保局切結聲明書</td>
          </tr>
          <TR><TD>
            <DIV class=hline><IMG height=1 src="images/blank.gif"></DIV>
          </TD></TR>
      </TBODY></TABLE><BR>
    </td></tr>

    <tr><td colspan=3>
      <TABLE class=formOuterBorder cellSpacing=0 cellPadding=0 width="100%" border=0><TBODY><TR><TD>
        <TABLE cellSpacing=1 cellPadding=0 width="100%" border=0>
          <TBODY><TR><TH class=formSecHeader align=left>切結聲明書資料</TH></TR></TBODY>
        </TABLE>
        <TABLE cellSpacing=1 cellPadding=2 width="100%" border=0><TBODY>
          <tr>
            <td class=dataLabel width="20%"><div align="right">統一編號：</div></td>
            <td align="left"><input name="regcode" value="<%=company.getRegcode()%>" type="text" class="textfield" size="10" maxlength="10" readonly></td>
          </tr>
          <tr>
            <td class=dataLabel width="20%"><div align="right">公司名稱：</div></td>
            <td align="left"><input name="regname" value="<%=company.getRegname()%>" type="text" class="textfield" size="45" maxlength="30" onFocus="changeFocus('0');"></td>
          </tr>
          <tr>
            <td class=dataLabel width="20%"><div align="right">負責人姓名：</div></td>
            <td align="left"><input name="bossName" value="<%=company.getBossName()%>" type="text" class="textfield" size="45" maxlength="30" onFocus="changeFocus('1');"></td>
          </tr>
          <tr>
            <td class=dataLabel width="20%"><div align="right">負責人身份證號：</div></td>
            <td align="left"><input name="bossId" value="<%=company.getBossId()%>" type="text" class="textfield" size="45" maxlength="10" onFocus="changeFocus('2');"></td>
          </tr>
          <tr>
            <td class=dataLabel width="20%"><div align="right">切結聲明：</div></td>
            <td colspan="2" align="left">
              <input name="reason" type="radio" value="0" checked >新成立之營利事業單位/機構，負責人<br/>
              <input name="reason" type="radio" value="1" >因負責人變更，新負責人<br/>
              所得未達勞工保險投保薪資分級表最高一級，因無營利事業所得稅核定通知書及各類所得扣繳資料以資佐證，茲依照勞工保險條例第14條之2規定(最低不得低於所屬員工申報之最高投保薪資適用之等級)申報投保薪資為
	<input name="amount" value="26400" type="text" class="textfield" size="6" maxlength="6">元，如申報不實，願由 貴局依勞工保險條例第14條之1規定逕予調整，及依同條例第72條罰則規定處以短報保險費金額2倍罰鍰，絕無異議。
            </td>
          </tr>
        </TBODY></TABLE>
      </TD></TR></TBODY></TABLE>
    </td></tr>
    <tr>
      <td height="30">&nbsp;</td>
      <td height="30" colspan="2">
        <div align="right">
        <input name="Submit3" type="button" class='ui-state-default' value="下載空白表單" onClick='retrieveForm();'>&nbsp;&nbsp; 
          <input name="Submit1" type="submit" class='ui-state-default' value="列印">
        </div></td>
    </tr>
  </table>
  <input type="hidden" name="laborCode" value="<%=company.getLaborCode()%>">
  <input type="hidden" name="healthCode" value="<%=company.getHealthCode()%>">
  <input type="hidden" name="phone" value="<%=company.getPhone()%>">
  <input type="hidden" name="zip" value="<%=company.getZip()%>">
  <input type="hidden" name="address" value="<%=company.getAddress()%>">
  </form>
