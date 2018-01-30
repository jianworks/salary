<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.csjian.model.bean.*" %>

<%
	CompanyBean company = (CompanyBean)request.getAttribute("company");
%>
<script src="js/nextField.js" type=text/javascript></script>
<script src="js/checkPID.js" type=text/javascript></script>
  <script type=text/javascript>
  <!--
  var sequence = ["healthCode", "regname", "address", "bossIsNativeY", "bossIsNativeN", "bossId", "bossName", "contact", "phone", "email", 
                  "salaryShift", "btnUpdate"];

  
    function doUpdate() {
	  var err = "";	  
	  if ($('#healthCode').val() == "") {
          err += "投保單位代號 不得為空白!\n";
      } 
	  var reg = new RegExp('^\\d+$');
	  if (!reg.test($('#healthCode').val()) || $('#healthCode').val().length!=9) {
          err += "投保單位代號 格式錯誤 (應為 9 碼數字)!\n";
      } 
	  if ($('#regname').val() == "") {
          err += "單位名稱 不得為空白!\n";
      } 
	  if ($('#address').val() == "") {
          err += "單位住址 不得為空白!\n";
      } 
	  if ($('#bossName').val() == "") {
          err += "扣費義務人姓名 不得為空白!\n";
      } 
	  if ($('#contact').val() == "") {
          err += "連絡人姓名 不得為空白!\n";
      } 
	  if ($('#phone').val() == "") {
          err += "連絡電話 不得為空白!\n";
      } 
	  if ($('#email').val() == "") {
          err += "申報單位電子郵件信箱 不得為空白!\n";
      } 
	  if ($('#bossId').val() == "") {
          err += "負責人身份證號碼/統一證號 不得為空白!\n";
      } 
	  if (document.mainform.bossIsNative[0].checked && !CheckPID($('#bossId').val())) {
	        err += "負責人身份證號碼不正確!\n";
	  }
	  
	  if (err != "") {
          alert(err);
      } else {
          $("#mainform").submit();
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
  //-->
  </script>

  <form method="POST" Name="mainform" id="mainform" Action="<%=response.encodeURL("insuranceAddOn.do?action=updateCompanyHealthInfo") %>" class = "entry-form">
  <table width="100%"  border="0" cellpadding="0" cellspacing="0">
    <tr><td colspan=3>
      <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
        <TBODY>
          <tr>
            <td height="30" class=moduleTitle valign="bottom">投保單位資料維護</td>
          </tr>
          <TR><TD>
            <DIV class=hline><IMG height=1 src="../images/blank.gif"></DIV>
          </TD></TR>
      </TBODY></TABLE><BR>
    </td></tr>

    <tr><td colspan=3>
      <TABLE class=formOuterBorder cellSpacing=0 cellPadding=0 width="100%" border=0><TBODY><TR><TD>
        <TABLE cellSpacing=1 cellPadding=2 width="100%" border=0><TBODY>
          <tr>
            <td class=dataLabel><div align="right">投保單位代號：</div></td>
            <td colspan="2" align="left"><input name="healthCode" id="healthCode" value="<%=company.getHealthCode()%>" type="text" class="textfield" size="10" maxlength="10"></td>
          </tr>
          <tr>
            <td class=dataLabel><div align="right">單位名稱：</div></td>
            <td colspan="2" align="left"><input name="regname" id="regname" value="<%=company.getRegname()%>" type="text" class="textfield" size="20" maxlength="20"></td>
          </tr>
          <tr>
            <td class=dataLabel><div align="right">單位住址：</div></td>
            <td colspan="2" align="left"><input name="address" id="address" value="<%=company.getAddress()%>" type="text" class="textfield" size="100" maxlength="100"></td>
          </tr>
          <tr>
            <td class=dataLabel width="20%"><div align="right">負責人是否為本國人：</div></td>
            <td colspan="2" align="left">
              <input name="bossIsNative" id="bossIsNativeY" type="radio" value="Y" <%=company.getBossIsNative().equals("Y")?"checked":""%>>是
              <input name="bossIsNative" id="bossIsNativeN" type="radio" value="N" <%=company.getBossIsNative().equals("N")?"checked":""%>>否
            </td>
          </tr>
          <tr>
            <td class=dataLabel><div align="right">負責人身份證號碼/統一證號：</div></td>
            <td colspan="2" align="left"><input name="bossId" id="bossId" value="<%=company.getBossId()%>" type="text" class="textfield" size="20" maxlength="20"></td>
          </tr>
          <tr>
            <td class=dataLabel><div align="right">扣費義務人姓名：</div></td>
            <td colspan="2" align="left"><input name="bossName" id="bossName" value="<%=company.getBossName()%>" type="text" class="textfield" size="20" maxlength="20"></td>
          </tr>
          <tr>
            <td class=dataLabel><div align="right">連絡人姓名：</div></td>
            <td colspan="2" align="left"><input name="contact" id="contact" value="<%=company.getContact()%>" type="text" class="textfield" size="20" maxlength="20"></td>
          </tr>
          <tr>
            <td class=dataLabel><div align="right">連絡電話：</div></td>
            <td colspan="2" align="left"><input name="phone" id="phone" value="<%=company.getPhone()%>" type="text" class="textfield" size="20" maxlength="20"></td>
          </tr>
          <tr>
            <td class=dataLabel><div align="right">申報單位電子郵件信箱：</div></td>
            <td colspan="2" align="left"><input name="email" id="email" value="<%=company.getEmail()%>" type="text" class="textfield" size="100" maxlength="100"></td>
          </tr> 
          <tr>
            <td class=dataLabel><div align="right">薪資發放時間：</div></td>
            <td colspan="2" align="left">
            	 <select id="salaryShift" name="salaryShift">
	             	<option value="0">在薪資月份當月發放薪資</option>
	                <option value="1" <%=company.getSalaryShift()==1?"selected":"" %>>在薪資月份次月發放薪資</option>
	             </select>	  
            </td>
          </tr>          
        </TBODY></TABLE>
      </TD></TR></TBODY></TABLE>
    </td></tr>
    <tr>
      <td height="30" colspan="3">
        <div align="right">
          <input name="Submit0" id="btnUpdate" type="button" class='ui-state-default' value="儲存" onClick='doUpdate();'>
        </div></td>
    </tr>
  </table>

  <input type=hidden name="regcode" value="<%=company.getRegcode()%>"/>
  </form>
