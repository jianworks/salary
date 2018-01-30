<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.csjian.model.bean.*" %>

<%
	CompanyBean company = (CompanyBean)request.getAttribute("company");
	Vector agents = (Vector)request.getAttribute("agents");
	GroupBean[] groups = (GroupBean[])request.getAttribute("groups");
	String pageno = (String)request.getAttribute("pageno");
	String keyword = (String)request.getAttribute("keyword");
	String agentcode = (String)request.getAttribute("agentcode");
%>
  <script src="js/nextField.js" type=text/javascript></script>

  <SCRIPT LANGUAGE="JavaScript">
  <!--
  var sequence = ["regcode", "name", "email", "regname", "contact", "phone", "agent", "disable", 
                  "groupno", "btnUpdate"];
  
    function doUpdate() {
    	if (!chkBlank()) {
        } else if (! chkMaxwords()) {
        } else {
    		mainform.submit();
    	}
    }
    
    function doRemove() {
    	if (window.confirm("確定要刪除此客戶 ?")) {
    		mainform.action = "<%=response.encodeURL("account.do?action=remove") %>";
    		mainform.submit();
    	}
    }
    
    function doCancel() {
    	mainform.action = "<%=response.encodeURL("account.do?action=list") %>";
    	mainform.submit();
    }
    
    function doPassword() {
    	if (window.confirm("確定要重設此客戶的密碼(回復成統一編號) ?")) {
    		mainform.action = "<%=response.encodeURL("account.do?action=resetPassword") %>";
    		mainform.submit();
    	}
    }
    
    function chkBlank() {
      with (document.mainform) {
        if (regcode.value == "") {
            window.alert("統一編號不得為空白!");
            return false;
          } else if (name.value == "") {
            window.alert("短名稱不得為空白!");
            return false;
          } else if (groupno.value=="customer" &&  regname.value == "") {
            window.alert("客戶註冊名稱不得為空白!");
            return false;
          } else {
            return true;
          }
        }
    }

    function chkMaxwords() {
      if (count(document.forms["mainform"].regcode.value) > 8) {
        window.alert("廠商統一編號的字數超過上限(最大字元數8)!");
        return false;
      } else if (count(document.forms["mainform"].name.value) > 20) {
        window.alert("廠商短名稱的字數超過上限(最大字元數20)!");
        return false;
      } else if (count(document.forms["mainform"].regname.value) > 45) {
        window.alert("廠商註冊名稱的字數超過上限(最大字元數45)!");
        return false;
      } else if (count(document.forms["mainform"].contact.value) > 20) {
        window.alert("連絡人的字數超過上限(最大字元數20)!");
        return false;
      } else if (count(document.forms["mainform"].email.value) > 20) {
        window.alert("電子郵件字數超過上限(最大字元數45)!");
        return false;
      } else if (count(document.forms["mainform"].phone.value) > 20) {
        window.alert("電話號碼字數超過上限(最大字元數20)!");
        return false;
      } else {
        return true;
      }
    }

    function count(value){
      nowChr = 0;
      //for迴圈判斷value中的每一個字是否在0~255間
      for (var i=0;i<value.length;i++){
        value.charCodeAt(i)<256?nowChr++:nowChr+=2;
      }
      return nowChr;
    }

    function showField() {
      with (document.mainform) {
        if (groupno.value=='customer') {
          customer.style.display='block';
        } else {
          customer.style.display='none';
        }
      }
    }
  //-->
  </SCRIPT>

  <form method="POST" Name="mainform" Action="<%=response.encodeURL("account.do?action=update") %>" onSubmit='return validate();'>
  <table width="100%"  border="0" cellpadding="0" cellspacing="0">
    <tr><td colspan=3>
      <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
        <TBODY>
          <tr>
            <td height="30" class=moduleTitle valign="bottom">客戶資料管理</td>
          </tr>
          <TR><TD>
            <DIV class=hline><IMG height=1 src="../images/blank.gif"></DIV>
          </TD></TR>
      </TBODY></TABLE><BR>
    </td></tr>

    <tr><td colspan=3>
      <TABLE class=formOuterBorder cellSpacing=0 cellPadding=0 width="100%" border=0><TBODY><TR><TD>
        <TABLE cellSpacing=1 cellPadding=0 width="100%" border=0>
          <TBODY><TR><TH class=formSecHeader align=left>基本資料</TH></TR></TBODY>
        </TABLE>
        <TABLE cellSpacing=1 cellPadding=2 width="100%" border=0><TBODY>
          <tr>
            <td class=dataLabel width="20%"><div align="right">統一編號(使用者代號)：</div></td>
            <td align="left"><input name="regcode" id="regcode" value="<%=company.getRegcode()%>" type="text" class="textfield" size="10" maxlength="10"></td>
          </tr>
          <tr>
            <td class=dataLabel><div align="right">廠商短名稱(顯示名稱)：</div></td>
            <td colspan="2" align="left"><input name="name" id="name" value="<%=company.getName()%>" type="text" class="textfield" size="20" maxlength="20"></td>
          </tr>
          <tr>
            <td class=dataLabel><div align="right">電子郵件：</div></td>
            <td colspan="2" align="left"><input name="email" id="email" value="<%=company.getEmail()%>" type="text" class="textfield" size="45" maxlength="45"></td>
          </tr>
          <tr><td colspan=3>
            <span ID="customer" STYLE='DISPLAY:<%=company.getGroupno().equals("customer")?"block":"none"%>'><table cellSpacing=0 cellPadding=0 width="100%" border=0>
              <tr>
                <td class=dataLabel><div align="right">廠商註冊名稱：</div></td>
                <td colspan="2" align="left"><input name="regname" id="regname" value="<%=company.getRegname()%>" type="text" class="textfield" size="45" maxlength="45"></td>
              </tr>
              <tr>
                <td class=dataLabel><div align="right">聯絡人：</div></td>
                <td colspan="2" align="left"><input name="contact" id="contact" value="<%=company.getContact()%>" type="text" class="textfield" size="10" maxlength="10"></td>
              </tr>
              <tr>
                <td class=dataLabel><div align="right">電話號碼：</div></td>
                <td colspan="2" align="left"><input name="phone" id="phone" value="<%=company.getPhone()%>" type="text" class="textfield" size="20" maxlength="20"></td>
              </tr>
              <tr>
                <td class=dataLabel width="20%"><div align="right">帳務管理負責人員：</div></td>
                <td colspan="2" align="left">
                  <select name="agent" id="agent" class="select" onChange='sAgent();'>
                    <option value=""></option>
                    <% for (int i=0; i<agents.size(); i++) { String[] item=(String[])agents.elementAt(i);%>
                    <option value="<%=item[0]%>" <%=item[0].equals(company.getAgent())?"selected":"" %>><%=item[1]%></option>
                    <% } %>
                  </select>
                </td>
              </tr>
            </table></span>
          </td></tr>
        </TBODY></TABLE>
      </TD></TR></TBODY></TABLE>
    </td></tr>
    <tr><td colspan=3 height="10"></td></tr>
    <tr><td colspan=3>
      <TABLE class=formOuterBorder cellSpacing=0 cellPadding=0 width="100%" border=0><TBODY><TR><TD>
        <TABLE cellSpacing=1 cellPadding=0 width="100%" border=0>
          <TBODY><TR><TH class=formSecHeader align=left>權限資料</TH></TR></TBODY>
        </TABLE>
        <TABLE cellSpacing=1 cellPadding=2 width="100%" border=0><TBODY>
          <tr>
            <td class=dataLabel width="20%"><div align="right">是否停用：</div></td>
            <td colspan="2" align="left">
              <select name="disable" id="disable" class="select">
                <option value="Y" <%=company.getDisable().equals("Y")?"selected":"" %>>是</option>
                <option value="N" <%=company.getDisable().equals("N")?"selected":"" %>>否</option>
              </select>
            </td>
          </tr>
          <tr>
            <td class=dataLabel width="20%"><div align="right">權限群組：</div></td>
            <td colspan="2" align="left">
              <select name="groupno" id="groupno" class="select" onChange='showField();'>
                <% for (int i=0; i<groups.length; i++) { %>
                <option value="<%=groups[i].getGroupno()%>" <%=company.getGroupno().equals(groups[i].getGroupno())?"selected":"" %>><%=groups[i].getName()%></option>
                <% } %>
              </select>
            </td>
          </tr>
        </TBODY></TABLE>
      </TD></TR></TBODY></TABLE>
    </td></tr>
    <tr>
      <td height="30" colspan="3">
        <div align="right">
          <input name="Submit0" id="btnUpdate" type="button" value="儲存" onClick='doUpdate();'>
          <%if (!company.getRegcode().equals("")) {%><input name="Submit1" type="button" value="刪除" onClick='doRemove();'><% } %>
          <input name="Submit2" type="button" value="取消" onClick='doCancel();'>
          <%if (!company.getRegcode().equals("")) {%><input name="Submit3" type="button" value="重設密碼" onClick='doPassword();'><% } %>
        </div></td>
    </tr>
  </table>

  <input type=hidden name="agentcode" value="<%=agentcode%>"/>
  <input type=hidden name="pageno" value="<%=pageno%>"/>
  <input type=hidden name="keyword" value="<%=keyword%>"/>
  <input type=hidden name="isnew" value="<%=company.getRegcode().equals("")?"Y":"N"%>"/>
  </form>
