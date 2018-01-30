<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.csjian.util.*" %>
<%@ page import="com.csjian.model.bean.*" %>

<%
	String year = (String)request.getAttribute("year");
	String regcode = (String)request.getAttribute("regcode");
	EmployeeBean[] employees = (EmployeeBean[])request.getAttribute("employeeList");
	String[][] relations = (String[][])request.getAttribute("relations");
	Calendar cal = Calendar.getInstance();
  	String today = (cal.get(Calendar.YEAR)-1911) + "-" + (cal.get(Calendar.MONTH)+1) + "-" + cal.get(Calendar.DATE);
%>

  <SCRIPT LANGUAGE="JavaScript">
  <!--
    function selEmp() {
      with (document.mainform) {
        var emp = document.getElementById("employees").value.split("#");
        document.getElementById("osalary").value=emp[4];
		document.getElementById("birth").value=emp[3];
      }
    }

    function chkDate(datename) {
      if (!validateDate(document.getElementById(datename).value)){
        window.alert("日期格式錯誤");
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
    
    function sHealth() {
      with (document.mainform) {
        var emp = document.getElementById("employees").value.split("#");
        window.open("misc/healthFee.jsp?amount="+ document.getElementById('nsalary').value, "", "height=320,width=480,scrollbars=yes");
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
        if (document.getElementById("osalary").value=="") {
          window.alert("請輸入原勞健保投保薪資!");
          return false;
        } else if (document.getElementById("birth").value=="") {
          window.alert("請輸入員工的出生日期!");
          return false;
        } else if (document.getElementById("nsalary").value=="") {
          window.alert("請輸入調整後投保薪資!");
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
		  newField = document.createElement('INPUT');
		  newField.type = "text";
		  newField.name = "name" + icount;
		  newField.id = "name" + icount;
          newField.size = "6";
          newField.maxlength = "6";
          newField.value = emp[1];
          newCell.appendChild(newField);
          newField = document.createElement('INPUT');
		  newField.type = "hidden";
		  newField.name = "employeeno" + icount;
		  newField.id = "employeeno" + icount;
          newField.value = emp[0];
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
          newField.value = document.getElementById("birth").value;
          newCell.appendChild(newField);
          newRow.appendChild(newCell);

          newCell = document.createElement('td');
		  newField = document.createElement('INPUT');
		  newField.type = "text";
		  newField.name = "osalary" + icount;
		  newField.id = "osalary" + icount;
          newField.size = "7";
          newField.maxlength = "7";
          newField.value = document.getElementById("osalary").value;
          newCell.appendChild(newField);
          newRow.appendChild(newCell);
          
          newCell = document.createElement('td');
		  newField = document.createElement('INPUT');
		  newField.type = "text";
		  newField.name = "nsalary" + icount;
		  newField.id = "nsalary" + icount;
          newField.size = "7";
          newField.maxlength = "7";
          newField.value = document.getElementById("nsalary").value;
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

    function removePerson(idx) {
      var delrow = document.getElementById("person" + idx);
      var table = document.getElementById('people');
      var tbody = table.getElementsByTagName('tbody')[0];
      tbody.deleteRow(delrow.rowIndex);
    }

	function retrieveForm() {
      with (document.mainform) {
        location.href = "servlet/RetrieveBlankForm?form=adjustform";
      }
    }

  //-->
  </SCRIPT>
</head>

<body leftmargin="20">
<form method="POST" Name="mainform" id="mainform" class = "entry-form" Action="<%=response.encodeURL("servlet/AdjustFormServlet") %>" >
  <table width="100%"  border="0" cellpadding="0" cellspacing="0">
    <tr><td colspan=3>
      <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
        <TBODY>
          <tr>
            <td height="30" class=moduleTitle valign="bottom">薪資調整表列印</td>
          </tr>
          <TR><TD>
            <DIV class=hline><IMG height=1 src="images/blank.gif"></DIV>
          </TD></TR>
      </TBODY></TABLE><BR>
    </td></tr>

	<tr><td colspan=3>
        <fieldset style="overflow-y:scroll">
          <table width="95%"  border="0" cellpadding="0" cellspacing="0">
            <tr>
              <td height="25" colspan=3><div align="left">增加薪資調整人員：</div></td>
            </tr>
            <tr>
              <td height="25" width="15%"><div align="right">員工：</div></td>
              <td height="25" align=left width="25%"><select name="employees" id="employees" onchange='selEmp();'><% if(employees != null&&employees.length>0){ %>
                <% for (int i=0; i<employees.length; i++) { %>
                <option value=<%=employees[i].getEmployeeno()+"#"+employees[i].getName()+"#"+employees[i].getUnicode()+"#"+StringUtils.adToTw(employees[i].getBirthday())+"#"+employees[i].getGovinsurance()+"#"+StringUtils.adToTw(employees[i].getOnboarddate())%>><%=employees[i].getName()%></option>
                <% } %>
              <% } %></select>
              </td>
              <td class=dataLabel><div align="right">出生日期：</div></td>
              <td align="left"><input name="birth" id="birth" value='<%=employees!=null&&employees.length>0?StringUtils.adToTw(employees[0].getBirthday()):""%>' type="text" class="textfield" size="10" maxlength="10" onChange='chkDate("birth");'>(格式 YY-MM-DD 例如 95-5-24)
          		&nbsp;<A href="javascript:sDate('birth')"><IMG src="images/calendar.gif" border="0"></A>
          	  </td>
            </tr>
            <tr>
              <td class=dataLabel width="15%"><div align="right">勞健保原投保薪資：</div></td>
              <td align="left"><input name="osalary" id="osalary" value='<%=employees!=null&&employees.length>0?employees[0].getGovinsurance():""%>' type="text" class="textfield" size="15" maxlength="10"></td>
          	  <td class=dataLabel width="15%"><div align="right">調整後投保薪資：</div></td>
              <td align="left"><input name="nsalary" id="nsalary" type="text" class="textfield" size="15" maxlength="10">&nbsp;&nbsp;<A href="javascript:sHealth()">勞健保薪資級距金額一覽表</A></td>
            </tr>
            <tr><td height="25" colspan="4" align=right>
                <input name="Submit2" type="button" class='ui-state-default' value="加入" onClick='addPerson();'>
            </td></tr>

            <tr>
              <td height="5" colspan=4><hr/></td>
            </tr>

            <tr bgcolor="#fdfaa3"><td colspan=4 align="right"><TABLE id="people" width="95%" border="0" cellpadding="0" cellspacing="0"><tbody>
              <tr height="25" align="center">
                <td>投保人姓名</td>
                <td>身份證號</td>
                <td>出生日期</td>
                <td>調整前薪資</td>
                <td>調整後薪資</td>
                <td>刪除</td>
              </tr>
              <TR><TD class=blackLine colSpan=6 height=1><IMG src="images/blank.gif"></TD></TR>
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
