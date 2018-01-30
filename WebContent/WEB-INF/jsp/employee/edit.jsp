<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.csjian.model.bean.*" %>
<%@ page import="com.csjian.util.*" %>

<%
	String keyword = (String)request.getAttribute("keyword");
	String year = (String)request.getAttribute("year");
	String resign = (String)request.getAttribute("resign");
    int pageno = Integer.parseInt((String)request.getAttribute("pageno"));      
    EmployeeBean employee = (EmployeeBean)request.getAttribute("employee");
    Calendar cal = Calendar.getInstance();
    String today = (cal.get(Calendar.YEAR)-1911) + "-" + (cal.get(Calendar.MONTH)+1) + "-" + cal.get(Calendar.DATE);
%>
<script src="js/nextField.js" type=text/javascript></script>
  <script type=text/javascript>
  <!--
    
  var sequence = ["employeeno", "title", "name", "isnativeY", "isnativeN", "unicode", "birthday", "address", 
                  "accountno", "onboarddate", "laborInsurance", "healthInsurance", "laborRetireFee", "retirefee", 
                  "isresignY", "isresignN", "resigndate", "btnUpdate"];
  
  function doUpdate() {
    	with (document.mainform) {
    		if (!chkBlank()) {
        	} else if (! chkMaxwords()) {
        	} else if (isnative[0].checked && !CheckPID(unicode.value)) {
        	} else {
    			mainform.submit();
    		}
    	}
    }
    
    function doRemove() {
    	if (window.confirm("確定要刪除此名員工 ?")) {
    		mainform.action = "<%=response.encodeURL("employee.do?action=remove") %>";
    		mainform.submit();
    	}
    }
    
    function doCancel() {
    	mainform.action = "<%=response.encodeURL("employee.do?action=list") %>";
    	mainform.submit();
    }
      
    var rePW = /^[0-9A-Za-z]+$/;
    function chkBlank() {
      with (document.mainform) {
        if (employeeno.value == "") {
          window.alert("員工編號不得為空白!");
          return false;
        } else if (!rePW.test(employeeno.value)) {
          window.alert("員工編號必須是英文或數字所組成！");
          employeeno.focus();
          return false;
        } else if (name.value == "") {
          window.alert("員工姓名不得為空白!");
          return false;
        } else if (unicode.value == "") {
          window.alert("請輸入身份證號碼/統一證號!");
          return false;
        } else if (address.value == "") {
          window.alert("住址不得為空白!");
          return false;
        } else if (onboarddate.value == "") {
          window.alert("到職日不得為空白!");
          return false;
        } else if (isresign[0].checked&&resigndate.value=="") {
          window.alert("請輸入離職日期!");
          return false;
        } else {
          return true;
        }
      }
    }

    function chkMaxwords() {
      if (count(document.forms["mainform"].employeeno.value) > 10) {
        window.alert("員工編號的字數超過上限(最大字元數10)!");
        return false;
      } else if (count(document.forms["mainform"].name.value) > 25) {
        window.alert("員工姓名的字數超過上限(最大字元數25)!");
        return false;
      } else if (count(document.forms["mainform"].address.value) > 100) {
        window.alert("住址的字數超過上限(最大字元數100)!");
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

    function sDate(eventType) {
      with (document.mainform) {
        var returnValue = window.showModalDialog("misc/calendar.html",'dialogArguments',"dialogHeight: 280px; dialogWidth: 280px; center: yes; scroll: no; status: no" );
        if (returnValue) {
          eval(eventType + ".value=returnValue");
        }
      }
    }

    function showResign(isshow) {
      with (document.mainform) {
        if (isshow=='Y') {
          resign3.style.display='block';
        } else {
          resign3.style.display='none';
          resigndate.value = "";
        }
      }
    }

    function chkDate(datename) {
      if (datename == "onboarddate") {
        if (!validateDate(document.forms["mainform"].onboarddate.value)){
          window.alert("到職日的日期格式錯誤");
          document.forms["mainform"].onboarddate.value = "";
        }
      } else if (datename== "birthday") {
    	  if (!validateDate(document.forms["mainform"].birthday.value)){
              window.alert("出生日期的日期格式錯誤");
              document.forms["mainform"].birthday.value = "";
            }
      } else {
        if (!validateDate(document.forms["mainform"].resigndate.value)){
          window.alert("離職日的日期格式錯誤");
          document.forms["mainform"].resigndate.value = "";
        }
      }
    }

    function validateDate(datestr) {
	  var datearray = datestr.split("-");
	  if (datearray.length<3) return false;
	  var year = datearray[0];
	  var month = datearray[1];
	  var day = datearray[2];
	  var tempDate = new Date((parseInt(year)+1911) + "/" + month + "/" + day);
	  if (isNaN(tempDate))
	    return (false);
	  if ((tempDate.getMonth()==parseInt(month)-1) && (tempDate.getDate()==parseInt(day))) {
	    return (true);
	  } else
	    return (false);
    }

    function validateNumber(oField) {
      var oNumber = document.getElementById(oField).value;
      var validateNumber = /^[0-9]*$/.test(oNumber);
      if(!validateNumber) {
        window.alert("金額部份只能輸入數字 !");
        document.getElementById(oField).value = "";
        return false;
      } else {
        return true;
      }
    }

    function formatUnicode(){
      with (document.mainform) {
        unicode.value = unicode.value.toUpperCase();
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
    
    function sRetire() {
      with (document.mainform) {
        window.open("misc/retireSalaryGrade.jsp", "", "height=320,resizable=yes,scrollbars=yes");
      }
    }
    
    function sHealth() {
        with (document.mainform) {
        	window.open("misc/healthFee.jsp?amount=0", "", "height=320,width=480,scrollbars=yes");
        }
      }

      function sLabor() {
        with (document.mainform) {
        	window.open("misc/laborFee.jsp?amount=0", "", "height=320,resizable=yes,scrollbars=yes");
        }
      }
  //-->
  </script>

  <form method="POST" Name="mainform" Action="<%=response.encodeURL("employee.do?action=update") %>" class = "entry-form" onSubmit='return validate();'>
  <table width="100%"  border="0" cellpadding="0" cellspacing="0">
    <tr><td colspan=3>
      <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
        <TBODY>
          <tr>
            <td height="30" class=moduleTitle valign="bottom">員工基本資料設定</td>
          </tr>
          <TR><TD>
            <DIV class=hline><IMG height=1 src="images/blank.gif"></DIV>
          </TD></TR>
      </TBODY></TABLE><BR>
    </td></tr>

    <tr><td colspan=3>
      <TABLE class=formOuterBorder cellSpacing=0 cellPadding=0 width="100%" border=0><TBODY><TR><TD>
        <TABLE cellSpacing=1 cellPadding=0 width="100%" border=0>
          <TBODY><TR><TH class=formSecHeader align=left>員工基本資料</TH></TR></TBODY>
        </TABLE>
        <TABLE cellSpacing=1 cellPadding=2 width="100%" border=0><TBODY>
          <tr>
            <td class=dataLabel width="20%"><div align="right">員工編號：</div></td>
            <td align="left"><input name="employeeno" id="employeeno" value="<%=employee.getEmployeeno()%>" type="text" class="textfield" size="10" maxlength="10"></td>
          </tr>
          <tr>
            <td class=dataLabel width="20%"><div align="right">職稱：</div></td>
            <td align="left"><input name="title" id="title" value="<%=employee.getTitle()%>" type="text" class="textfield" size="10" maxlength="10"></td>
          </tr>
          <tr>
            <td class=dataLabel><div align="right">員工姓名：</div></td>
            <td colspan="2" align="left"><input name="name" id="name" value="<%=employee.getName()%>" type="text" class="textfield" size="20" maxlength="20"></td>
          </tr>
          <tr>
            <td class=dataLabel width="20%"><div align="right">是否為本國人：</div></td>
            <td colspan="2" align="left">
              <input name="isnative" id="isnativeY" type="radio" value="Y" <%=employee.getIsnative().equals("Y")?"checked":""%>>是
              <input name="isnative" id="isnativeN" type="radio" value="N" <%=employee.getIsnative().equals("N")?"checked":""%>>否
            </td>
          </tr>
          <tr>
            <td class=dataLabel width="20%"><div align="right">身份證號碼/統一證號：</div></td>
            <td colspan="2" align="left"><input name="unicode" id="unicode" value="<%=employee.getUnicode()%>" type="text" class="textfield" size="15" maxlength="20" onChange="formatUnicode();"></td>
          </tr>
		  <tr>
            <td class=dataLabel><div align="right">出生年月日：</div></td>
            <td colspan="2" align="left">
              <input name="birthday" id="birthday" value="<%=!employee.getBirthday().equals("")?StringUtils.adToTw(employee.getBirthday()):""%>" type="text" class="textfield" size="10" maxlength="10" onChange='chkDate("birthday");'> (格式 YY-MM-DD 例如 96-5-24)
              &nbsp;<A href="javascript:sDate('birthday')"><IMG src="images/calendar.gif" border="0"></A>
            </td>
          </tr>
          <tr>
            <td class=dataLabel><div align="right">住址：</div></td>
            <td colspan="2" align="left"><input name="address" id="address" value="<%=employee.getAddress()%>" type="text" class="textfield" size="45" maxlength="100"></td>
          </tr>
          <tr>
            <td class=dataLabel><div align="right">銀行帳戶：</div></td>
            <td colspan="2" align="left"><input name="accountno" id="accountno" value="<%=employee.getAccountno()%>" type="text" class="textfield" size="20" maxlength="20"></td>
          </tr>
          <tr>
            <td class=dataLabel><div align="right">到職日：</div></td>
            <td colspan="2" align="left">
              <input name="onboarddate" id="onboarddate" value="<%=!employee.getOnboarddate().equals("")?StringUtils.adToTw(employee.getOnboarddate()):today%>" type="text" class="textfield" size="10" maxlength="10" onChange='chkDate("onboarddate");'> (格式 YY-MM-DD 例如 96-5-24)
              &nbsp;<A href="javascript:sDate('onboarddate')"><IMG src="images/calendar.gif" border="0"></A>
            </td>
          </tr>
          <tr>
            <td class=dataLabel><div align="right">勞保投保薪資：</div></td>
            <td colspan="2" align="left">
            	<input name="laborInsurance" id="laborInsurance" value="<%=employee.getLaborInsurance()%>" type="text" class="textfield" size="10" maxlength="8" onChange='validateNumber("laborInsurance");'>&nbsp;&nbsp;
            	<A href="javascript:sLabor()">勞保負擔金額一覽表</A>
            </td>
          </tr>
		  <tr>
            <td class=dataLabel><div align="right">健保投保薪資：</div></td>
            <td colspan="2" align="left">
            	<input name="healthInsurance" id="healthInsurance" value="<%=employee.getHealthInsurance()%>" type="text" class="textfield" size="10" maxlength="8" onChange='validateNumber("healthInsurance");'>&nbsp;&nbsp;
            	<A href="javascript:sHealth()">健保負擔金額一覽表</A>
            </td>
          </tr>
		  <tr>
            <td class=dataLabel><div align="right">勞退提撥工資：</div></td>
            <td colspan="2" align="left">
            	<input name="laborRetireFee" id="laborRetireFee" value="<%=employee.getLaborRetireFee()%>" type="text" class="textfield" size="10" maxlength="8" onChange='validateNumber("healthInsurance");'>&nbsp;&nbsp;
            	<A href="javascript:sRetire()">勞工退休金月提繳工資分級表</A>
            </td>
          </tr>
          <tr>
            <td class=dataLabel><div align="right">退休金提撥金額：</div></td>
            <td colspan="2" align="left"><input name="retirefee" id="retirefee" value="<%=employee.getRetirefee()%>" type="text" class="textfield" size="10" maxlength="8" onChange='validateNumber("retirefee");'></td>
          </tr>
          <tr>
            <td class=dataLabel width="20%"><div align="right">是否已離職：</div></td>
            <td colspan="2" align="left">
              <input name="isresign" id="isresignY" type="radio" value="Y" <%=employee.getIsresign().equals("Y")?"checked":""%> onClick='showResign("Y");'>是
              <input name="isresign" id="isresignN" type="radio" value="N" <%=employee.getEmployeeno().equals("")||employee.getIsresign().equals("N")?"checked":""%>  onClick='showResign("N");'>否
            </td>
          </tr>
          <tr><td colspan=3>
            <span ID="resign3" STYLE='DISPLAY:<%=employee.getIsresign().equals("Y")?"block":"none"%>'><table cellSpacing=0 cellPadding=0 width="100%" border=0>
              <tr>
                <td class=dataLabel width="20%"><div align="right">離職日期：</div></td>
                <td colspan="2" align="left">
                  <input name="resigndate" id="resigndate" value="<%=StringUtils.adToTw(employee.getResigndate())%>" type="text" class="textfield" size="10" maxlength="10" onChange='chkDate("resigndate");'> (格式 YY-MM-DD 例如 96-5-24)
                  &nbsp;<A href="javascript:sDate('resigndate')"><IMG src="images/calendar.gif" border="0"></A>
                </td>
              </tr>
            </table></span>
          </td></tr>
        </TBODY></TABLE>
      </TD></TR></TBODY></TABLE>
    </td></tr>

    <tr>
      <td height="30" colspan="3">
        <div align="right">
          <input name="Submit0" id="btnUpdate" class='ui-state-default' type="button" value="儲存" onClick='doUpdate();'>
          <%if (!employee.getEmployeeno().equals("")) {%><input name="Submit1" type="button" class='ui-state-default' value="刪除" onClick='doRemove();'><% } %>
          <input name="Submit2" class='ui-state-default' type="button" value="取消" onClick='doCancel();'>
        </div></td>
    </tr>
  </table>

  <input type=hidden name="pageno" value="<%=pageno%>"/>
  <input type=hidden name="year" value="<%=year%>"/>
  <input type=hidden name="ayear" value="<%=year%>"/>
  <input type=hidden name="keyword" value="<%=keyword%>"/>
  <input type=hidden name="resign" value="<%=resign%>"/>
  <input type=hidden name="isnew" value="<%=employee.getEmployeeno().equals("")?"Y":"N"%>"/>
  <input type="hidden" name="FOCUS_NAME"/>
  </form>
</body>
</html>
