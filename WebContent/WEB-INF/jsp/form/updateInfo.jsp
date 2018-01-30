<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.csjian.model.bean.*" %>

<%
	CompanyBean company = (CompanyBean)request.getAttribute("company");
%>
	<script src="js/nextField.js" type=text/javascript></script>
  <SCRIPT LANGUAGE="JavaScript">
  <!--
  var sequence = ["regcode", "regname", "bossName", "bossIsNativeY", "bossIsNativeN", "bossId", "laborCode", "healthCode", "phone", "zip", 
                  "address", "btnUpdate"];
  
    function doSubmit() {
      with (document.mainform) {
        if (validate()) {
          Submit0.disabled = true;
          submit();
        }
      }
    }

    var rePW = /^[0-9]*$/;
    function validate() {
      with (document.mainform) {
        if (regname.value == "") {
          window.alert("公司名稱不得為空白!");
          laborCode.focus();
          return false;
        } else if ($('#bossId').val() == "") {
        	window.alert("負責人身份證號碼/統一證號 不得為空白!");
        	bossId.focus();
        } else if (document.mainform.bossIsNative[0].checked && !CheckPID($('#bossId').val())) {
        	window.alert("負責人身份證號碼不正確!");
        	bossId.focus();
  	    } else if (laborCode.value == "") {
          window.alert("勞工保險證號不得為空白!");
          laborCode.focus();
          return false;
        } else if (healthCode.value == "") {
          window.alert("全民健保投保單位代號不得為空白!");
          healthCode.focus();
          return false;
        } else if (phone.value == "") {
          window.alert("連絡電話不得為空白!");
          phone.focus();
          return false;
        } else if (address.value == "") {
          window.alert("住址不得為空白!");
          address.focus();
          return false;
        } else if (zip.value == "") {
          window.alert("請填寫郵遞區號!");
          zip.focus();
          return false;
        } else if (!rePW.test(zip.value)) {
          window.alert("郵遞區號必須是數字所組成！");
          zip.focus();
          return false;
        } else {
          return true;
        }
      }
    }
    
    var ALP_STR = "ABCDEFGHJKLMNPQRSTUVXYWZIO";
    var NUM_STR = "0123456789";
    var SEX_STR = "12";
    var MAX_COUNT = 999;
    //身份證字號檢查器 - 累加檢查碼
    function getPID_SUM(sPID) {
	  var iChkNum = 0;
	  // 第 1 碼
	  iChkNum = ALP_STR.indexOf(sPID.substr(0,1)) + 10;
	  iChkNum = Math.floor(iChkNum/10) + (iChkNum%10*9);
	  // 第 2 - 9 碼
	  for(var i=1; i<sPID.length-1; i++) {
		iChkNum += sPID.substr(i,1) * (9-i);
	  }

	  // 第 10 碼
	  iChkNum += sPID.substr(9,1)*1;
	  return iChkNum;
    }

    // 身分證字號檢查器 - 檢查合法字元
    function chkPID_CHAR(sPID) {
	  var sMsg = "";
	  //sPID = trim(sPID.toUpperCase());
	  var iPIDLen = String(sPID).length;

	  var sChk = ALP_STR + NUM_STR;
	  for(i=0;i<iPIDLen;i++) {
		if (sChk.indexOf(sPID.substr(i,1)) < 0) {
		  sMsg = "這個身分證字號含有不正確的字元！";
		  break;
		}
	  }

	  if (sMsg.length == 0) {
		if (ALP_STR.indexOf(sPID.substr(0,1)) < 0) {
		  sMsg = "身分證字號第 1 碼應為英文字母(A~Z)。";
		} else if ((sPID.substr(1,1) != "1") && (sPID.substr(1,1) != "2")) {
		  sMsg = "身分證字號第 2 碼應為數字(1~2)。";
		} else {
		  for(var i=2; i<iPIDLen; i++) {
			if (NUM_STR.indexOf(sPID.substr(i, 1)) < 0) {
			  sMsg = "第 " + (i+1) + " 碼應為數字(0~9)。";
			  break;
			}
		  }
		}
	  }
	  if (sMsg.length != 0) {
		alert(sMsg);
		return false;
	  } else {
		return true;
	  }
    }

    // 身分證字號檢查器
    function CheckPID(sPID) {
	  if (sPID == '') {
		window.alert("請輸入身分證字號/統一證號");
		return false;
	  } else if (sPID.length != 10) {
		window.alert("身分證字號長度應為 10 ！");
		return false;
	  } else {
		sPID = trim(sPID.toUpperCase());
		if (!chkPID_CHAR(sPID)) return false;
		var iChkNum = getPID_SUM(sPID);
		if (iChkNum % 10 != 0) {
		  window.alert("身分證字號不正確 ! ");
		  return false;
		}
	  }
	  return true;
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

  <form method="POST" Name="mainform" class="entry-form" Action="<%=response.encodeURL("form.do?action=updateInfo") %>">
  <table width="100%"  border="0" cellpadding="0" cellspacing="0">
    <tr><td colspan=3>
      <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
        <TBODY>
          <tr>
            <td height="30" class=moduleTitle valign="bottom">公司資料設定</td>
          </tr>
          <TR><TD>
            <DIV class=hline><IMG height=1 src="images/blank.gif"></DIV>
          </TD></TR>
      </TBODY></TABLE><BR>
    </td></tr>

    <tr><td colspan=3>
      <TABLE class=formOuterBorder cellSpacing=0 cellPadding=0 width="100%" border=0><TBODY><TR><TD>
        <TABLE cellSpacing=1 cellPadding=0 width="100%" border=0>
          <TBODY><TR><TH class=formSecHeader align=left>公司基本資料</TH></TR></TBODY>
        </TABLE>
        <TABLE cellSpacing=1 cellPadding=2 width="100%" border=0><TBODY>
          <tr>
            <td class=dataLabel width="20%"><div align="right">統一編號：</div></td>
            <td align="left"><input name="regcode" id="regcode" value="<%=company.getRegcode()%>" type="text" class="textfield" size="10" maxlength="10" readonly></td>
          </tr>
          <tr>
            <td class=dataLabel width="20%"><div align="right">公司名稱：</div></td>
            <td align="left"><input name="regname" id="regname" value="<%=company.getRegname()%>" type="text" class="textfield" size="45" maxlength="30"></td>
          </tr>
          <tr>
            <td class=dataLabel width="20%"><div align="right">負責人姓名：</div></td>
            <td align="left"><input name="bossName" id="bossName" value="<%=company.getBossName()%>" type="text" class="textfield" size="45" maxlength="30"></td>
          </tr>
          <tr>
            <td class=dataLabel width="20%"><div align="right">負責人是否為本國人：</div></td>
            <td colspan="2" align="left">
              <input name="bossIsNative" id="bossIsNativeY" type="radio" value="Y" <%=company.getBossIsNative().equals("Y")?"checked":""%>>是
              <input name="bossIsNative" id="bossIsNativeN" type="radio" value="N" <%=company.getBossIsNative().equals("N")?"checked":""%>>否
            </td>
          </tr>
          <tr>
            <td class=dataLabel width="20%"><div align="right">負責人身份證號：</div></td>
            <td align="left"><input name="bossId" id="bossId" value="<%=company.getBossId()%>" type="text" class="textfield" size="45" maxlength="20"></td>
          </tr>
          <tr>
            <td class=dataLabel width="20%"><div align="right">勞工保險證號：</div></td>
            <td align="left"><input name="laborCode" id="laborCode" value="<%=company.getLaborCode()%>" type="text" class="textfield" size="12" maxlength="12"></td>
          </tr>
          <tr>
            <td class=dataLabel><div align="right">全民健保投保單位代號：</div></td>
            <td colspan="2" align="left"><input name="healthCode" id="healthCode" value="<%=company.getHealthCode()%>" type="text" class="textfield" size="10" maxlength="9"></td>
          </tr>
          <tr>
            <td class=dataLabel width="20%"><div align="right">連絡電話：</div></td>
            <td colspan="2" align="left"><input name="phone" id="phone" value="<%=company.getPhone()%>" type="text" class="textfield" size="16" maxlength="16"></td>
          </tr>
          <tr>
            <td class=dataLabel><div align="right">公司地址：</div></td>
            <td colspan="2" align="left">
              <input name="zip" id="zip" value="<%=company.getZip()%>" type="text" class="textfield" size="6" maxlength="5">
              <input name="address" id="address" value="<%=company.getAddress()%>" type="text" class="textfield" size="45" maxlength="100">
            </td>
          </tr>
        </TBODY></TABLE>
      </TD></TR></TBODY></TABLE>
    </td></tr>
    <tr>
      <td height="30" colspan="3">
        <div align="right">
          <input name="Submit0" id="btnUpdate" type="button" class='ui-state-default' value="儲存" onClick='doSubmit();'>
        </div></td>
    </tr>
  </table>
  </form>
