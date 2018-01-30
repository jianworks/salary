<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.csjian.model.bean.*" %>

<%
	CompanyBean company = (CompanyBean)request.getAttribute("company");
String[] tolist = {"中央健保署台北業務組", "中央健保署北區業務組", "中央健保署中區業務組", "中央健保署南區業務組", "中央健保署高屏業務組", "中央健保署東區業務組"};
%>

  <SCRIPT LANGUAGE="JavaScript">
  <!--
	function retrieveForm() {
      with (document.mainform) {
        location.href = "servlet/RetrieveBlankForm?form=proclamationform";
      }
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
    
    function sDate(eventType) {
        with (document.mainform) {
          var returnValue = window.showModalDialog("misc/calendar.html",'dialogArguments',"dialogHeight: 280px; dialogWidth: 280px; center: yes; scroll: no; status: no" );
          if (returnValue) {
            eval(eventType + ".value=returnValue");
          }
        }
      }

  //-->
  </SCRIPT>

  <form method="POST" Name="mainform" class="entry-form" Action="<%=response.encodeURL("servlet/ProclamationFormServlet") %>" >
  <table width="100%"  border="0" cellpadding="0" cellspacing="0">
    <tr><td colspan=3>
      <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
        <TBODY>
          <tr>
            <td height="30" class=moduleTitle valign="bottom">負責人調降健保金額聲明書</td>
          </tr>
          <TR><TD>
            <DIV class=hline><IMG height=1 src="images/blank.gif"></DIV>
          </TD></TR>
      </TBODY></TABLE><BR>
    </td></tr>

    <tr><td colspan=3>
      <TABLE class=formOuterBorder cellSpacing=0 cellPadding=0 width="100%" border=0><TBODY><TR><TD>
        <TABLE cellSpacing=1 cellPadding=0 width="100%" border=0>
          <TBODY><TR><TH class=formSecHeader align=left>聲明書資料</TH></TR></TBODY>
        </TABLE>
        <TABLE cellSpacing=1 cellPadding=2 width="100%" border=0><TBODY>
          <tr>
            <td class=dataLabel width="20%"><div align="right">投保單位代號：</div></td>
            <td align="left"><input name="healthCode" value="<%=company.getHealthCode()%>" type="text" class="textfield" size="10" maxlength="10"></td>
          </tr>
          <tr>
            <td class=dataLabel width="20%"><div align="right">公司名稱：</div></td>
            <td align="left"><input name="regname" value="<%=company.getRegname()%>" type="text" class="textfield" size="45" maxlength="30" ></td>
          </tr>
          <tr>
            <td class=dataLabel width="20%"><div align="right">負責人姓名：</div></td>
            <td align="left"><input name="bossName" value="<%=company.getBossName()%>" type="text" class="textfield" size="45" maxlength="30" ></td>
          </tr>
          <tr>
            <td class=dataLabel width="20%"><div align="right">負責人身份證號：</div></td>
            <td align="left"><input name="bossId" value="<%=company.getBossId()%>" type="text" class="textfield" size="45" maxlength="10"></td>
          </tr>
          <tr>
            <td class=dataLabel width="20%"><div align="right">連絡電話：</div></td>
            <td align="left"><input name="phone" value="<%=company.getPhone()%>" type="text" class="textfield" size="45" maxlength="10"></td>
          </tr>
          <tr>
            <td class=dataLabel width="20%"><div align="right">本單位係：</div></td>
            <td colspan="2" align="left">
              <input name="reason1" type="radio" value="0" checked >新設立之單位，設立核准日期：
              <input name="date0" id="date0" value="" type="text" class="textfield" size="10" maxlength="10"> (格式 YY-MM-DD 例如 96-5-24)
              &nbsp;<A href="javascript:sDate('date0')"><IMG src="images/calendar.gif" border="0"></A><br/>
              <input name="reason1" type="radio" value="1" >變更負責人之單位，變更核准日期：
              <input name="date1" id="date1" value="" type="text" class="textfield" size="10" maxlength="10"> (格式 YY-MM-DD 例如 96-5-24)
              &nbsp;<A href="javascript:sDate('date1')"><IMG src="images/calendar.gif" border="0"></A><br/>
              <input name="reason1" type="radio" value="2" >復業之單位，復業核准日期：
              <input name="date2" id="date2" value="" type="text" class="textfield" size="10" maxlength="10"> (格式 YY-MM-DD 例如 96-5-24)
              &nbsp;<A href="javascript:sDate('date2')"><IMG src="images/calendar.gif" border="0"></A><br/>
              <input name="reason1" type="radio" value="3" >營業額在一定標準以下，免課所得稅之小規模營利單位<br/>             
              <input name="reason1" type="radio" value="4" >其它
              <input name="resontxt" value="" type="text" class="textfield" size="25" maxlength="20">
            </td>
          </tr>
          <tr>
            <td class=dataLabel width="20%"><div align="right">投保金額：</div></td>
            <td colspan="2" align="left">
              切結申報健保投保金額：
              <input name="amount" value="" type="text" class="textfield" size="45" maxlength="10">元<br/>
              <input name="reason2" type="radio" value="0" checked>單位僱用員工滿五人(含)之事業負責人(105年5月1日起投保金額最低不得於４５８００元及所屬員工申報之最高投保金額，且不得低於其適用勞工退休金月提繳工資)<br/>
              <input name="reason2" type="radio" value="1" >單位僱用員工未滿五人之事業負責人(投保金額最低不得於３４８００元及所屬員工申報之最高投保金額，且不得低於其適用勞工保險之投保薪資及勞工退休金月提繳工資)<br/>              
            </td>
          </tr>
          <tr>
          	<td class=dataLabel width="20%"><div align="right">收件單位：</div></td>
            <td colspan="2" align="left">
              <% for (int j=0; j<tolist.length; j++) { %>
                 <input type="radio" name="tocode" value="<%=j%>" >&nbsp;&nbsp;<%=tolist[j]%><br/>
              <% } %>
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
  <input type="hidden" name="regcode" value="<%=company.getRegcode()%>">
  <input type="hidden" name="laborCode" value="<%=company.getLaborCode()%>">
  <input type="hidden" name="zip" value="<%=company.getZip()%>">
  <input type="hidden" name="address" value="<%=company.getAddress()%>">
  </form>
