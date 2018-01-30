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
        location.href = "servlet/RetrieveBlankForm?form=startform";
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

 	function chkDate(datename) {
      if (!validateDate(document.getElementById(datename).value)){
        window.alert("出生日期的日期格式錯誤");
        document.getElementById(datename).value = "";
      }
    }
    
    function address(izip, iarea) {
    	
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

  <form method="POST" Name="mainform" class="entry-form" Action="<%=response.encodeURL("servlet/StartFormServlet") %>" >
  <table width="100%"  border="0" cellpadding="0" cellspacing="0">
    <tr><td colspan=3>
      <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
        <TBODY>
          <tr>
            <td height="30" class=moduleTitle valign="bottom">單位成立三合一申請書</td>
          </tr>
          <TR><TD>
            <DIV class=hline><IMG height=1 src="images/blank.gif"></DIV>
          </TD></TR>
      </TBODY></TABLE><BR>
    </td></tr>

    <tr><td colspan=3>
      <TABLE class=formOuterBorder cellSpacing=0 cellPadding=0 width="100%" border=0><TBODY><TR><TD>
        <TABLE cellSpacing=1 cellPadding=0 width="100%" border=0>
          <TBODY><TR><TH class=formSecHeader align=left>單位成立三合一申請書資料</TH></TR></TBODY>
        </TABLE>
        <TABLE cellSpacing=1 cellPadding=2 width="100%" border=0><TBODY>
          <tr>
            <td class=dataLabel width="20%"><div align="right">統一編號：</div></td>
            <td align="left"><input name="regcode" value="<%=company.getRegcode()%>" type="text" class="textfield" size="10" maxlength="10" readonly></td>
          </tr>
          <tr>
            <td class=dataLabel width="20%"><div align="right">公司名稱：</div></td>
            <td align="left"><input name="regname" value="<%=company.getRegname()%>" type="text" class="textfield" size="45" maxlength="30"></td>
          </tr>
          <tr>
            <td class=dataLabel width="20%"><div align="right">是否為公營事業：</div></td>
            <td colspan="2" align="left">
              <input name="public" type="radio" value="0" >是
              <input name="public" type="radio" value="1" checked>否
            </td>
          </tr>
          <tr>
              <td class=dataLabel width="20%"><div align="right">公司證照地址：</div></td>
              <td align="left">
              	<input name="area1" id="area1" value='' type="text" class="textfield" size="10" maxlength="9" readonly=true onClick='address("zip1", "area1")'>(縣鄉鎮)&nbsp;&nbsp;
              	<input name="zip1" id="zip1" value='' type="text" class="textfield" size="10" maxlength="5" readonly=true onClick='address("zip1", "area1")'>(郵遞區號)&nbsp;&nbsp;
              	<input name="address1" id="address1" value='' type="text" class="textfield" size="45" maxlength="100">
          	  </td>
          </tr>
          <tr>
              <td class=dataLabel width="20%"><div align="right">公司連絡地址：</div></td>
              <td align="left">
              	<input name="area2" id="area2" value='' type="text" class="textfield" size="10" maxlength="9" readonly=true onClick='address("zip2", "area2")'>(縣鄉鎮)&nbsp;&nbsp;
              	<input name="zip2" id="zip2" value='' type="text" class="textfield" size="10" maxlength="5" readonly=true onClick='address("zip2", "area2")'>(郵遞區號)&nbsp;&nbsp;
              	<input name="address2" id="address2" value='' type="text" class="textfield" size="45" maxlength="100">
          	  </td>
          </tr>
          <tr>
            <td class=dataLabel width="20%"><div align="right">主要經營業務：</div></td>
            <td colspan="2" align="left"><input name="business" type="text" class="textfield" size="45" maxlength="100"></td>
          </tr>
          <tr>
            <td class=dataLabel width="20%"><div align="right">主要產品或出售貨品：</div></td>
            <td colspan="2" align="left"><input name="product" type="text" class="textfield" size="45" maxlength="100"></td>
          </tr>
          <tr>
            <td class=dataLabel width="20%"><div align="right">勞工退休金雇主提繳率(%)：</div></td>
            <td colspan="2" align="left"><input name="ratio" type="text" class="textfield" size="10" maxlength="3"></td>
          </tr>
          <tr>
            <td class=dataLabel width="20%"><div align="right">負責人姓名：</div></td>
            <td align="left"><input name="bossName" value="<%=company.getBossName()%>" type="text" class="textfield" size="45" maxlength="30"></td>
          </tr>
          <tr>
            <td class=dataLabel width="20%"><div align="right">負責人身份證號：</div></td>
            <td align="left"><input name="bossId" value="<%=company.getBossId()%>" type="text" class="textfield" size="45" maxlength="10"></td>
          </tr>
          <tr>
              <td class=dataLabel width="20%"><div align="right">負責人出生日期：</div></td>
              <td align="left"><input name="birth" id="birth" value='' type="text" class="textfield" size="10" maxlength="9" onChange='chkDate("birth");'>(格式 YY-MM-DD 例如 95-5-24)
           		&nbsp;<A href="javascript:sDate('birth')"><IMG src="images/calendar.gif" border="0"></A>
          	  </td>
          </tr>
          <tr>
            <td class=dataLabel width="20%"><div align="right">負責人戶籍地址：</div></td>
            <td align="left"><input name="bossAddress" type="text" class="textfield" size="45" maxlength="100"></td>
          </tr>
          <tr>
            <td class=dataLabel width="20%"><div align="right">連絡電話：</div></td>
            <td colspan="2" align="left"><input name="phone" value="<%=company.getPhone()%>" type="text" class="textfield" size="16" maxlength="16"></td>
          </tr>
          <tr>
            <td class=dataLabel width="20%"><div align="right">傳真：</div></td>
            <td colspan="2" align="left"><input name="fax" type="text" class="textfield" size="16" maxlength="16"></td>
          </tr>
          <tr>
            <td class=dataLabel width="20%"><div align="right">電子郵件信箱：</div></td>
            <td colspan="2" align="left"><input name="email" type="text" class="textfield" size="45" maxlength="100"></td>
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
  <input type="hidden" name="zip" value="<%=company.getZip()%>">
  <input type="hidden" name="address" value="<%=company.getAddress()%>">
  </form>
