<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.csjian.util.*" %>
<%@ page import="com.csjian.model.bean.*" %>

<script type="text/javascript" src="js/dojo.js"></script>
<%
	String year = (String)request.getAttribute("year");
	String regcode = (String)request.getAttribute("regcode");
	EmployeeBean[] employees = (EmployeeBean[])request.getAttribute("employeeList");
	Calendar cal = Calendar.getInstance();
  	String today = (cal.get(Calendar.YEAR)-1911) + "-" + (cal.get(Calendar.MONTH)+1) + "-" + cal.get(Calendar.DATE);
%>

  <SCRIPT LANGUAGE="JavaScript"><!--
  
  	$( function() {
  		$( "#birth1" ).datepickerTW();
  		$( "#birth2" ).datepickerTW();
	} );
  
    function selEmp() {
      with (document.mainform) {
        dependant.style.display='none';
        var emp = document.getElementById("employees").value.split("#");
        document.getElementById("salary").value=emp[4];
		document.getElementById("birth1").value=emp[3];
      }
    }

    function showDependant(isshow) {
      with (document.mainform) {
        if (isshow=='Y') {
          dependant.style.display='block';
        } else {
          dependant.style.display='none';
        }
      }
    }

    function chkDate(datename) {
      if (!validateDate(document.getElementById(datename).value)){
        window.alert("出生日期的日期格式錯誤");
        document.getElementById(datename).value = "";
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
	  if ((tempDate.getMonth()==parseInt(month)-1) && (tempDate.getDate()==parseInt(day)))
	    return (true);
	  else
	    return (false);
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
    function chkPID() {
      with (document.mainform) {
       if (unicode.value.length != 10) {
		  window.alert("身分證字號長度應為 10 ！");
		  unicode.value = "";
	    } else {
		  unicode.value = trim(unicode.value.toUpperCase());
		  if (!chkPID_CHAR(unicode.value)) unicode.value = "";
		  var iChkNum = getPID_SUM(unicode.value);
		  if (iChkNum % 10 != 0) {
		    window.alert("身分證字號不正確 ! ");
		    unicode.value = "";
		  }
	    }
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

    function addPerson() {
      if (document.mainform.isdependant[0].checked) {
        if (document.getElementById("birth1").value=="") {
          window.alert("請輸入員工的出生日期!");
          return false;
        } else {
          var icount = document.getElementById("count").value;
          var table = document.getElementById('people');
          var tbody = table.getElementsByTagName('tbody')[0];
		  var emp = document.getElementById("employees").value.split("#");
		  newRow = document.createElement('tr');
		  newRow.id = "person" + icount;
		  newRow.align="center";

		  newCell = document.createElement('td');
          newText = document.createTextNode("員工");
          newCell.appendChild(newText);
          newField = document.createElement('INPUT');
		  newField.type = "hidden";
		  newField.name = "employeeno" + icount;
		  newField.id = "employeeno" + icount;
          newField.value = emp[0];
          newCell.appendChild(newField);
          newField = document.createElement('INPUT');
		  newField.type = "hidden";
		  newField.name = "isdependant" + icount;
		  newField.id = "isdependant" + icount;
          newField.value = "N";
          newCell.appendChild(newField);
          newField = document.createElement('INPUT');
		  newField.type = "hidden";
		  newField.name = "onboarddate" + icount;
		  newField.id = "onboarddate" + icount;
          newField.value = emp[5];
          newCell.appendChild(newField);
          newField = document.createElement('INPUT');
		  newField.type = "hidden";
		  newField.name = "ename" + icount;
		  newField.id = "ename" + icount;
          newField.value = emp[1];
          newCell.appendChild(newField);
          newField = document.createElement('INPUT');
		  newField.type = "hidden";
		  newField.name = "eunicode" + icount;
		  newField.id = "eunicode" + icount;
          newField.value = emp[2];
          newCell.appendChild(newField);
          newField = document.createElement('INPUT');
		  newField.type = "hidden";
		  newField.name = "ebirth" + icount;
		  newField.id = "ebirth" + icount;
          newField.value = document.getElementById("birth1").value;
          newCell.appendChild(newField);
          newRow.appendChild(newCell);
          
		  newCell = document.createElement('td');
		  newField = document.createElement('INPUT');
		  newField.type = "text";
		  newField.name = "name" + icount;
		  newField.id = "name" + icount;
          newField.size = "6";
          newField.maxlength = "6";
          newField.value = emp[1];
          newCell.appendChild(newField);
          newRow.appendChild(newCell);

          newCell = document.createElement('td');
		  newField = document.createElement('INPUT');
		  newField.type = "text";
		  newField.name = "unicode" + icount;
		  newField.id = "unicode" + icount;
          newField.size = "10";
          newField.maxlength = "10";
          newField.value = emp[2];
          newCell.appendChild(newField);
          newRow.appendChild(newCell);

          newCell = document.createElement('td');
		  newField = document.createElement('INPUT');
		  newField.type = "text";
		  newField.name = "birthday" + icount;
		  newField.id = "birthday" + icount;
          newField.size = "10";
          newField.maxlength = "10";
          newField.value = document.getElementById("birth1").value;
          newCell.appendChild(newField);
          newRow.appendChild(newCell);
          
          newCell = document.createElement('td');
		  newField = document.createElement('INPUT');
		  newField.type = "text";
		  newField.name = "amount_1" + icount;
		  newField.id = "amount_1" + icount;
          newField.size = "10";
          newField.maxlength = "9";
          newField.value = document.getElementById("amount1").value;
          newCell.appendChild(newField);
          newRow.appendChild(newCell);
          
          newCell = document.createElement('td');
		  newField = document.createElement('INPUT');
		  newField.type = "text";
		  newField.name = "amount_2" + icount;
		  newField.id = "amount_2" + icount;
          newField.size = "10";
          newField.maxlength = "9";
          newField.value = document.getElementById("amount2").value;
          newCell.appendChild(newField);
          newRow.appendChild(newCell);
          
          newCell = document.createElement('td');
		  newField = document.createElement('INPUT');
		  newField.type = "text";
		  newField.name = "amount_3" + icount;
		  newField.id = "amount_3" + icount;
          newField.size = "10";
          newField.maxlength = "9";
          newField.value = document.getElementById("amount3").value;
          newCell.appendChild(newField);
          newRow.appendChild(newCell);

          // make the link
          newCell = document.createElement('td');
          oAddLink = document.createElement('a');
          oAddLink.setAttribute('href','#');
          oAddLink.id = "rem" + icount;
          var onC='removePerson("' + icount + '")';
          oAddLink.appendChild(document.createTextNode("刪除"));
          newCell.appendChild(oAddLink);
          newRow.appendChild(newCell);
          tbody.appendChild(newRow);

          document.getElementById("rem" + icount).onclick=new Function(onC);
          document.getElementById("count").value = (parseInt(icount) + 1);
        }
      } else {
        if (document.getElementById("name").value=="") {
          window.alert("請輸入眷屬的姓名!");
          return false;
        } else if (document.getElementById("unicode").value=="") {
          window.alert("請輸入眷屬的身份證號!");
          return false;
        } else if (document.getElementById("birth2").value=="") {
          window.alert("請輸入眷屬的出生日期!");
          return false;
        } else {
          var icount = document.getElementById("count").value;
          var table = document.getElementById('people');
          var tbody = table.getElementsByTagName('tbody')[0];
		  var emp = document.getElementById("employees").value.split("#");
		  newRow = document.createElement('tr');
		  newRow.id = "person" + icount;
		  newRow.align="center";

		  newCell = document.createElement('td');
          newText = document.createTextNode("眷屬");
          newCell.appendChild(newText);
          newField = document.createElement('INPUT');
		  newField.type = "hidden";
		  newField.name = "employeeno" + icount;
		  newField.id = "employeeno" + icount;
          newField.value = emp[0];
          newCell.appendChild(newField);
          newField = document.createElement('INPUT');
		  newField.type = "hidden";
		  newField.name = "isdependant" + icount;
		  newField.id = "isdependant" + icount;
          newField.value = "Y";
          newCell.appendChild(newField);
          newField = document.createElement('INPUT');
		  newField.type = "hidden";
		  newField.name = "onboarddate" + icount;
		  newField.id = "onboarddate" + icount;
          newField.value = emp[5];
          newCell.appendChild(newField);
          //newField = document.createElement('INPUT');
		  //newField.type = "hidden";
		  //newField.name = "relation" + icount;
		  //newField.id = "relation" + icount;
          //newField.value = document.getElementById("relation").value;
          //newCell.appendChild(newField);
          newField = document.createElement('INPUT');
		  newField.type = "hidden";
		  newField.name = "ename" + icount;
		  newField.id = "ename" + icount;
          newField.value = emp[1];
          newCell.appendChild(newField);
          newField = document.createElement('INPUT');
		  newField.type = "hidden";
		  newField.name = "eunicode" + icount;
		  newField.id = "eunicode" + icount;
          newField.value = emp[2];
          newCell.appendChild(newField);
          newField = document.createElement('INPUT');
		  newField.type = "hidden";
		  newField.name = "ebirth" + icount;
		  newField.id = "ebirth" + icount;
          newField.value = document.getElementById("birth1").value;
          newCell.appendChild(newField);
          newRow.appendChild(newCell);

		  newCell = document.createElement('td');
		  newField = document.createElement('INPUT');
		  newField.type = "text";
		  newField.name = "name" + icount;
		  newField.id = "name" + icount;
          newField.size = "6";
          newField.maxlength = "6";
          newField.value = document.getElementById("name").value;
          newCell.appendChild(newField);
          newRow.appendChild(newCell);

          newCell = document.createElement('td');
		  newField = document.createElement('INPUT');
		  newField.type = "text";
		  newField.name = "unicode" + icount;
		  newField.id = "unicode" + icount;
          newField.size = "10";
          newField.maxlength = "10";
          newField.value = document.getElementById("unicode").value;
          newCell.appendChild(newField);
          newRow.appendChild(newCell);

          newCell = document.createElement('td');
		  newField = document.createElement('INPUT');
		  newField.type = "text";
		  newField.name = "birthday" + icount;
		  newField.id = "birthday" + icount;
          newField.size = "10";
          newField.maxlength = "10";
          newField.value = document.getElementById("birth2").value;
          newCell.appendChild(newField);
          newRow.appendChild(newCell);

          newCell = document.createElement('td');
		  newField = document.createElement('INPUT');
		  newField.type = "text";
		  newField.name = "amount_1" + icount;
		  newField.id = "amount_1" + icount;
          newField.size = "10";
          newField.maxlength = "9";
          newField.value = document.getElementById("amount1").value;
          newCell.appendChild(newField);
          newRow.appendChild(newCell);
          
          newCell = document.createElement('td');
		  newField = document.createElement('INPUT');
		  newField.type = "text";
		  newField.name = "amount_2" + icount;
		  newField.id = "amount_2" + icount;
          newField.size = "10";
          newField.maxlength = "9";
          newField.value = document.getElementById("amount2").value;
          newCell.appendChild(newField);
          newRow.appendChild(newCell);
          
          newCell = document.createElement('td');
		  newField = document.createElement('INPUT');
		  newField.type = "text";
		  newField.name = "amount_3" + icount;
		  newField.id = "amount_3" + icount;
          newField.size = "10";
          newField.maxlength = "9";
          newField.value = document.getElementById("amount3").value;
          newCell.appendChild(newField);
          newRow.appendChild(newCell);

          // make the link
          newCell = document.createElement('td');
          oAddLink = document.createElement('a');
          oAddLink.setAttribute('href','#');
          oAddLink.id = "rem" + icount;
          var onC='removePerson("' + icount + '")';
          oAddLink.appendChild(document.createTextNode("刪除"));
          newCell.appendChild(oAddLink);
          newRow.appendChild(newCell);
          tbody.appendChild(newRow);

          document.getElementById("rem" + icount).onclick=new Function(onC);
          document.getElementById("count").value = (parseInt(icount) + 1);
        }
      }
    }

    function removePerson(idx) {
      var delrow = document.getElementById("person" + idx);
      var table = document.getElementById('people');
      var tbody = table.getElementsByTagName('tbody')[0];
      tbody.deleteRow(delrow.rowIndex);
    }

    function sHealth() {
      with (document.mainform) {
        var emp = document.getElementById("employees").value.split("#");
        window.open("misc/healthFee.jsp", "", "height=320,width=480,scrollbars=yes");
      }
    }
    
    function retrieveForm() {
      with (document.mainform) {
        location.href = "servlet/RetrieveBlankForm?form=paform";
      }
    }


  //
  --></SCRIPT>


<form method="POST" Name="mainform" class="entry-form" Action="<%=response.encodeURL("servlet/PaFormServlet") %>" >
  <table width="100%"  border="0" cellpadding="0" cellspacing="0">
    <tr><td colspan=3>
      <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
        <TBODY>
          <tr>
            <td height="30" class=moduleTitle valign="bottom">委任書</td>
          </tr>
          <TR><TD>
            <DIV class=hline><IMG height=1 src="images/blank.gif"></DIV>
          </TD></TR>
      </TBODY></TABLE><BR>
    </td></tr>

	<tr><td colspan=3 align="center">
        <fieldset style="overflow-y:scroll">
          <table width="97%"  border="0" cellpadding="0" cellspacing="0">
            <tr>
              <td height="25" colspan=4><div align="left">投保人員：</div></td>
            </tr>
            <tr>
              <td height="25" width="15%"><div align="right">員工：</div></td>
              <td height="25" align=left width="30%"><select name="employees" onchange='selEmp();'><% if (employees != null&&employees.length>0) { %>
                <% for (int i=0; i<employees.length; i++) {%>
                <option value=<%=employees[i].getEmployeeno()+"#"+employees[i].getName()+"#"+employees[i].getUnicode()+"#"+StringUtils.adToTw(employees[i].getBirthday())+"#"+employees[i].getGovinsurance()+"#"+StringUtils.adToTw(employees[i].getOnboarddate())%>><%=employees[i].getName()%></option>
                <% } %>
                <% } %></select>
              </td>
              <td height="25" width="15%"><div align="right">本人或眷屬：</td>
              <td height="25"><div align="left">
                <input name="isdependant" type="radio" value="N" onClick='showDependant("N");' checked >本人
                <input name="isdependant" type="radio" value="Y" onClick='showDependant("Y");'>眷屬
              </td>
            </tr>
            <tr>
              <td width="15%"><div align="right">員工出生日期：</div></td>
              <td align="left" colspan="3"><input name="birth1" id="birth1" value='<%=employees!=null&&employees.length>0?StringUtils.adToTw(employees[0].getBirthday()):""%>' type="text" class="textfield" size="10" maxlength="9" onChange='chkDate("birth1");'>(格式 YY-MM-DD 例如 95-5-24)
          	  </td>
            </tr>
            <tr bgcolor="#FE9934"><td colspan=4>
              <span ID="employee" STYLE='DISPLAY:block'><table cellSpacing=0 cellPadding=0 width="100%" border=0>
                <tr>
                  <td width="15%"><div align="right">勞保金額：</div></td>
                  <td align="left" width="30%"><input name="amount1" id="amount1" type="text" class="textfield" size="16" maxlength="10"></td>
                  <td width="15%"><div align="right">健保金額：</div></td>
                  <td align="left" width="30%"><input name="amount2" id="amount2" type="text" class="textfield" size="16" maxlength="10"></td>
                </tr>
				<tr>
                  <td width="15%"><div align="right">勞退金額：</div></td>
                  <td align="left" colspan="3"><input name="amount3" id="amount3" type="text" class="textfield" size="16" maxlength="10"></td>
                </tr>
              </table></span>
            </td></tr>
            <tr bgcolor="#FE9934"><td colspan=4>
              <span ID="dependant" STYLE='DISPLAY:none'><table cellSpacing=0 cellPadding=0 width="100%" border=0>
                <tr>
                  <td width="15%"><div align="right">眷屬姓名：</div></td>
                  <td align="left" colspan="3"><input name="name" id="name" type="text" class="textfield" size="16" maxlength="10"></td>
                </tr>
                <tr>
                  <td width="15%"><div align="right">眷屬身份證號：</div></td>
                  <td align="left" width="30%"><input name="unicode" id="unicode" type="text" class="textfield" size="16" maxlength="10" onChange='chkPID();'></td>
                  <td width="15%"><div align="right">眷屬出生日期：</div></td>
                  <td align="left">
                    <input name="birth2" id="birth2" type="text" class="textfield" size="10" maxlength="9" onChange='chkDate("birth2");'>(格式 YY-MM-DD 例如 95-5-24)
              	  </td>
                </tr>
              </table></span>
            </td></tr>
            <tr><td height="25" colspan="4" align=right>
               	 <input name="Submit2" type="button" class='ui-state-default' value="加入" onClick='addPerson();'>&nbsp;&nbsp;&nbsp;&nbsp;
            </td></tr>

            <tr>
              <td height="5" colspan=4><hr/></td>
            </tr>

            <tr bgcolor="#fdfaa3"><td colspan=4 align="right"><TABLE id="people" width="95%" border="0" cellpadding="0" cellspacing="0"><tbody>
              <tr height="25" align="center">
                <td>員工/眷屬</td>
                <td>加保人姓名</td>
                <td>加保人身份證號</td>
                <td>加保人出生日期</td>
                <td>勞保金額</td>
                <td>健保金額</td>
                <td>勞退金額</td>
                <td>刪除</td>
              </tr>
              <TR><TD class=blackLine colSpan=8 height=1><IMG src="images/blank.gif"></TD></TR>
            </tbody></TABLE></td></tr>
          </table>
        </fieldset>
      </td></tr>
    <tr>
      <td height="30" colspan="4">
        <div align="right">
        	<input name="Submit3" type="button" class='ui-state-default' value="下載空白表單" onClick='retrieveForm();'>&nbsp;&nbsp;      
          <input name="Submit1" type="submit" class='ui-state-default' value="列印" />              
        </div>
      </td>
    </tr>
  </table>
  <input type=hidden name="count" id="count" value="0" />
  <input type=hidden name="regcode" id="regcode" value="<%=regcode%>" />
  <input type=hidden name="year" id="year" value="<%=year%>" />
  </form>
