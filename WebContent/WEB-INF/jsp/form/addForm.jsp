<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.csjian.util.*" %>
<%@ page import="com.csjian.model.bean.*" %>

<script type="text/javascript" src="js/dojo.js"></script>
<%
	String year = (String)request.getAttribute("year");
	String regcode = (String)request.getAttribute("regcode");
	EmployeeBean[] employees = (EmployeeBean[])request.getAttribute("employeeList");
	String[][] relations = (String[][])request.getAttribute("relations");
	Calendar cal = Calendar.getInstance();
  	String today = (cal.get(Calendar.YEAR)-1911) + "-" + (cal.get(Calendar.MONTH)+1) + "-" + cal.get(Calendar.DATE);
%>

<script src="js/nextField.js" type=text/javascript></script>
  <script type=text/javascript>
  <!--
  var sequence = ["employees", "isdependantN", "isdependantY", "salary", "birth1", "name", "relation", "unicode", 
                  "birth2", "adate", "btnAdd"];

  
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

    function sDate(eventType) {
      with (document.mainform) {
        var returnValue = window.showModalDialog("misc/calendar.html",'dialogArguments',"dialogHeight: 250px; dialogWidth: 280px; center: yes; scroll: no; status: no" );
        if (returnValue) {
          eval(eventType + ".value=returnValue");
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
        if (document.getElementById("salary").value=="") {
          window.alert("請輸入投保薪資!");
          return false;
        } else if (document.getElementById("birth1").value=="") {
          window.alert("請輸入員工的出生日期!");
          return false;
        } else if (document.getElementById("adate").value=="") {
          window.alert("請輸入加保日期!");
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
		  newField.name = "salary" + icount;
		  newField.id = "salary" + icount;
          newField.size = "7";
          newField.maxlength = "7";
          newField.value = document.getElementById("salary").value;
          newCell.appendChild(newField);
          newRow.appendChild(newCell);
          
          newCell = document.createElement('td');
		  newField = document.createElement('INPUT');
		  newField.type = "text";
		  newField.name = "adate" + icount;
		  newField.id = "adate" + icount;
          newField.size = "10";
          newField.maxlength = "9";
          newField.value = document.getElementById("adate").value;
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
        } else if (document.getElementById("salary").value=="") {
          window.alert("請輸入投保薪資!");
          return false;
        } else if (document.getElementById("birth1").value=="") {
          window.alert("請輸入員工的出生日期!");
          return false;
        } else if (document.getElementById("adate").value=="") {
          window.alert("請輸入加保日期!");
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
          newField = document.createElement('INPUT');
		  newField.type = "hidden";
		  newField.name = "relation" + icount;
		  newField.id = "relation" + icount;
          newField.value = document.getElementById("relation").value;
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
		  newField.name = "salary" + icount;
		  newField.id = "salary" + icount;
          newField.size = "7";
          newField.maxlength = "7";
          newField.value = document.getElementById("salary").value;
          newCell.appendChild(newField);
          newRow.appendChild(newCell);
          
          newCell = document.createElement('td');
		  newField = document.createElement('INPUT');
		  newField.type = "text";
		  newField.name = "adate" + icount;
		  newField.id = "adate" + icount;
          newField.size = "10";
          newField.maxlength = "9";
          newField.value = document.getElementById("adate").value;
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
        window.open("misc/healthFee.jsp?amount=" + document.getElementById('salary').value, "", "height=320,width=480,scrollbars=yes");
      }
    }
    
    function retrieveForm() {
    	alert("on call");
      with (document.mainform) {
        location.href = "servlet/RetrieveBlankForm?form=addform";
      }
    }
	
    function doSubmit() {
		$('#mainform').submit();
	}

  //-->
  </script>


<form method="POST" Name="mainform" id="mainform" class="entry-form" Action="<%=response.encodeURL("servlet/AddFormServlet") %>" >
  <table width="100%"  border="0" cellpadding="0" cellspacing="0">
    <tr><td colspan=3>
      <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
        <TBODY>
          <tr>
            <td height="30" class=moduleTitle valign="bottom">加保申請表列印</td>
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
              <td height="25" colspan=4><div align="left">增加加保人員：</div></td>
            </tr>
            <tr>
              <td height="25" width="15%"><div align="right">員工：</div></td>
              <td height="25" align=left width="30%"><select name="employees" id="employees" onchange='selEmp();'><% if (employees != null&&employees.length>0) { %>
                <% for (int i=0; i<employees.length; i++) {%>
                <option value=<%=employees[i].getEmployeeno()+"#"+employees[i].getName()+"#"+employees[i].getUnicode()+"#"+StringUtils.adToTw(employees[i].getBirthday())+"#"+employees[i].getGovinsurance()+"#"+StringUtils.adToTw(employees[i].getOnboarddate())%>><%=employees[i].getName()%></option>
                <% } %>
                <% } %></select>
              </td>
              <td height="25" width="15%"><div align="right">本人或眷屬：</td>
              <td height="25"><div align="left">
                <input name="isdependant" id="isdependantN" type="radio" value="N" onClick='showDependant("N");' checked >本人
                <input name="isdependant" id="isdependantY" type="radio" value="Y" onClick='showDependant("Y");'>眷屬
              </td>
            </tr>
            <tr>
              <td width="15%"><div align="right">勞健保加保薪資：</div></td>
              <td width="30%" align="left"><input name="salary" id="salary" value='<%=employees!=null&&employees.length>0?employees[0].getGovinsurance():""%>' type="text" class="textfield" size="15" maxlength="10">&nbsp;&nbsp;<A href="javascript:sHealth()">薪資級距金額一覽表</A></td>
              <td width="15%"><div align="right">員工出生日期：</div></td>
              <td align="left"><input name="birth1" id="birth1" value='<%=employees!=null&&employees.length>0?StringUtils.adToTw(employees[0].getBirthday()):""%>' type="text" class="textfield" size="10" maxlength="9" onChange='chkDate("birth1");'>(格式 YY-MM-DD 例如 95-5-24)
           		&nbsp;<A href="javascript:sDate('birth1')"><IMG src="images/calendar.gif" border="0"></A>
          	  </td>
            </tr>
            <tr bgcolor="#FE9934"><td colspan=4>
              <span ID="dependant" STYLE='DISPLAY:none'><table cellSpacing=0 cellPadding=0 width="100%" border=0>
                <tr>
                  <td width="15%"><div align="right">眷屬姓名：</div></td>
                  <td align="left" width="30%"><input name="name" id="name" type="text" class="textfield" size="16" maxlength="10"></td>
                  <td width="15%"><div align="right">眷屬稱謂：</div></td>
                  <td align="left"><select name="relation" id="relation">
                	<% for (int i=0; i<relations.length; i++) { %>
                		<option value=<%=relations[i][0]%>><%=relations[i][1]%></option>
                	<% } %>
              	  </select></td>
                </tr>
                <tr>
                  <td width="15%"><div align="right">眷屬身份證號：</div></td>
                  <td align="left" width="30%"><input name="unicode" id="unicode" type="text" class="textfield" size="16" maxlength="10" onChange='chkPID();'></td>
                  <td width="15%"><div align="right">眷屬出生日期：</div></td>
                  <td align="left">
                    <input name="birth2" id="birth2" type="text" class="textfield" size="10" maxlength="9" onChange='chkDate("birth2");'>(格式 YY-MM-DD 例如 95-5-24)
              		&nbsp;<A href="javascript:sDate('birth2')"><IMG src="images/calendar.gif" border="0"></A>
              	  </td>
                </tr>
              </table></span>
            </td></tr>
            <tr><td height="25" colspan="4" align=right>
               	 加保日期：<input name="adate" id="adate" value="<%=today%>" type="text" class="textfield" size="10" maxlength="9" onChange='chkDate("adate");'>(格式 YY-MM-DD 例如 95-5-24)
                &nbsp;<A href="javascript:sDate('adate')"><IMG src="images/calendar.gif" border="0"></A>
                &nbsp;&nbsp;&nbsp;&nbsp;<input name="Submit2" id="btnAdd" type="button" class='ui-state-default' value="加入" onClick='addPerson();'>&nbsp;&nbsp;&nbsp;&nbsp;
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
                <td>員工投保薪資</td>
                <td>加保日期</td>
                <td>刪除</td>
              </tr>
              <TR><TD class=blackLine colSpan=7 height=1><IMG src="images/blank.gif"></TD></TR>
            </tbody></TABLE></td></tr>
          </table>
        </fieldset>
      </td></tr>
    <tr>
      <td height="30" colspan="4">
        <div align="right">
        	<input name="Submit3" type="button" class='ui-state-default' value="下載空白表單" onClick='retrieveForm();'>&nbsp;&nbsp;      
          	<input name="Submit1" type="button" class='ui-state-default' value="列印" onClick='doSubmit();' />              
        </div>
      </td>
    </tr>
  </table>
  <input type=hidden name="count" id="count" value="0" />
  <input type=hidden name="regcode" id="regcode" value="<%=regcode%>" />
  <input type=hidden name="year" id="year" value="<%=year%>" />
  </form>
