<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.csjian.model.bean.*" %>
<%@ page import="com.csjian.util.*" %>

<script src="js/checkPID.js" type=text/javascript></script>
<script src="js/nextField.js" type=text/javascript></script>

<%
	String CREATE = (String)request.getAttribute("CREATE");
    EmployeeBean employee = (EmployeeBean)request.getAttribute("incomeEarner");
    Calendar cal = Calendar.getInstance();
    String year = CREATE.equals("Y") ? cal.get(Calendar.YEAR) + "" : employee.getAyear();
    String today = (cal.get(Calendar.YEAR)-1911) + "-" + (cal.get(Calendar.MONTH)+1) + "-" + cal.get(Calendar.DATE);
%>

 <script type=text/javascript>
  <!--
   	var sequence = ["name", "isnativeY", "isnativeN", "unicode", "address", "employeeno", "title", "birthday", "accountno", "onboarddate", 
                   "laborInsurance", "healthInsurance", "laborRetireFee", "retirefee", "resigndate", "btnUpdate"];
  	
  
    function doUpdate() {
    	var err = "";
    	if ($('#name').val() == '') {
        	err += "所得人姓名 不得為空白!\n";
        } 
  	  	if (count($('#name').val()) > 25) {
          err += "所得人姓名的字數超過上限(最大字元數25)!\n";
        }
		if ($('#unicode').val() == '') {
        	err += "身份證號碼/統一證號 不得為空白!\n";
        } 
		if (document.mainform.isnative[0].checked && !CheckPID($('#unicode').val())) {
	        err += "身份證號碼不正確!\n";
	    } 
		
		if ($('#address').val() == '') {
            err += "住址 不得為空白!\n";
        } 
		if (count($('#address').val()) > 100) {
	        err += "住址的字數超過上限(最大字元數100)!\n";
	    }
		if ($('#isemployee').val() == 'Y' || $('input[name=isemployee]:checked').val()=='Y') {
	    	if ($('#employeeno').val() == '') {
	            err += "員工編號 不得為空白!\n";
	        } 
			if (!rePW.test($('#employeeno').val())) {
	            err += "員工編號必須是英文或數字所組成！\n";
	        } 
		    if (count($('#employeeno').val()) > 10) {
		        err += "員工編號的字數超過上限(最大字元數10)!\n";
		    } 
			
			if ($('#onboarddate').val() == '') {
	            err += "到職日 不得為空白!\n";
	          } 
			if ($('#isresign').val() == 'Y' && $('#resigndate').val() == '') {
	            err += "請輸入離職日期!\n";	            
	        }    	
		}
		
		if (err != "") {
            alert(err);
        } else {
            $("#mainform").submit();
        }
    }
    
    function doRemove() {
    	if (window.confirm("確定要刪除此筆所得人資料 ?")) {
    		mainform.action = "<%=response.encodeURL("insuranceAddOn.do?action=removeIncomeEarner") %>";
    		mainform.submit();
    	}
    }
    
    function doCancel() {
    	mainform.action = "<%=response.encodeURL("insuranceAddOn.do?action=incomeEarner") %>";
    	mainform.submit();
    }
      
    var rePW = /^[0-9A-Za-z]+$/;
    
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
        var returnValue = window.showModalDialog("misc/calendar.html",'dialogArguments',"dialogHeight: 250px; dialogWidth: 280px; center: yes; scroll: no; status: no" );
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

 	function sRetire() {
      with (document.mainform) {
        window.open("misc/retireSalaryGrade.jsp", "", "height=320,resizable=yes,scrollbars=yes");
      }
    }
  //-->
  </script>

  <form method="POST" Name="mainform" id="mainform" Action="<%=response.encodeURL("insuranceAddOn.do?action=updateIncomeEarner") %>" class = "entry-form">
  <table width="100%"  border="0" cellpadding="0" cellspacing="0">
    <tr><td colspan=3>
      <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
        <TBODY>
          <tr>
            <td height="30" class=moduleTitle valign="bottom">所得人基本資料設定</td>
          </tr>
          <TR><TD>
            <DIV class=hline><IMG height=1 src="images/blank.gif"></DIV>
          </TD></TR>
      </TBODY></TABLE><BR>
    </td></tr>

    <tr><td colspan=3>
      <TABLE class=formOuterBorder cellSpacing=0 cellPadding=0 width="100%" border=0><TBODY><TR><TD>
        <TABLE cellSpacing=1 cellPadding=0 width="100%" border=0>
          <TBODY><TR><TH class=formSecHeader align=left>所得人基本資料</TH></TR></TBODY>
        </TABLE>
        <TABLE cellSpacing=1 cellPadding=2 width="100%" border=0><TBODY>
          <% if (CREATE.equals("Y")) { %>
	          <tr>
	            <td class=dataLabel width="20%"><div align="right">是否為員工：</div></td>
	            <td colspan="2" align="left">
	              <input name="isemployee" type="radio" value="N" <%=employee.getEmployeeno()==null || employee.getEmployeeno().equals("")?"checked":""%> onClick="$('#employeeInfo').hide();">否
	              <input name="isemployee" type="radio" value="Y" <%=employee.getEmployeeno()!=null && !employee.getEmployeeno().equals("")?"checked":""%>  onClick="$('#employeeInfo').show();">是(建立在員工基本資料表)
	            </td>
	          </tr>
          <% } else {%>
          	  <tr><td colspan="3">	
          	  <input type="hidden" id="isemployee" name="isemployee" value='<%=employee.getEmployeeno()==null||employee.getEmployeeno().equals("")?"N":"Y" %>' />
          	  </tr></td>	
          <% } %>          
          <tr>
            <td class=dataLabel><div align="right">所得人姓名：</div></td>
            <td colspan="2" align="left"><input name="name" id="name" value="<%=employee.getName()%>" type="text" class="textfield" size="20" maxlength="20" ></td>
          </tr>
          <tr>
            <td class=dataLabel width="20%"><div align="right">是否為本國人：</div></td>
            <td colspan="2" align="left">
              <input name="isnative" id="isnativeY" type="radio" value="Y" <%=employee.getIsnative().equals("Y")?"checked":""%>>是
              <input name="isnative" id="isnativeN" type="radio" value="N" <%=employee.getIsnative().equals("N")?"checked":""%>>否
            </td>
          </tr>
          <tr>
            <td class=dataLabel><div align="right">身份證號碼/統一證號：</div></td>
            <td colspan="2" align="left"><input name="unicode" id="unicode" value="<%=employee.getUnicode()%>" type="text" class="textfield" size="15" maxlength="20" onChange="formatUnicode();" ></td>
          </tr>
          <tr>
            <td class=dataLabel><div align="right">住址：</div></td>
            <td colspan="2" align="left"><input name="address" id="address" value="<%=employee.getAddress()%>" type="text" class="textfield" size="45" maxlength="100" ></td>
          </tr>          
        </TBODY></TABLE>
        <TABLE cellSpacing=1 cellPadding=2 width="100%" border=0 id="employeeInfo" STYLE='DISPLAY:<%=CREATE.equals("N")&&!employee.getEmployeeno().equals("")?"block":"none"%>'><TBODY>
          <tr>
            <td class=dataLabel width="20%"><div align="right">員工編號：</div></td>
            <td align="left"><input name="employeeno" id="employeeno" value="<%=employee.getEmployeeno()%>" type="text" class="textfield" size="10" maxlength="10" ></td>
          </tr>
          <tr>
            <td class=dataLabel width="20%"><div align="right">職稱：</div></td>
            <td align="left"><input name="title" id="title" value="<%=employee.getTitle()%>" type="text" class="textfield" size="10" maxlength="10" ></td>
          </tr>
		  <tr>
            <td class=dataLabel><div align="right">出生年月日：</div></td>
            <td colspan="2" align="left">
              <input name="birthday" id="birthday" value="<%=!employee.getBirthday().equals("")?StringUtils.adToTw(employee.getBirthday()):""%>" type="text" class="textfield" size="10" maxlength="10" onChange='chkDate("birthday");' > (格式 YY-MM-DD 例如 96-5-24)
              &nbsp;<A href="javascript:sDate('birthday')"><IMG src="images/calendar.gif" border="0"></A>
            </td>
          </tr>
          <tr>
            <td class=dataLabel><div align="right">銀行帳戶：</div></td>
            <td colspan="2" align="left"><input name="accountno" id="accountno" value="<%=employee.getAccountno()%>" type="text" class="textfield" size="20" maxlength="20" onFocus="changeFocus('5');"></td>
          </tr>
          <tr>
            <td class=dataLabel><div align="right">到職日：</div></td>
            <td colspan="2" align="left">
              <input name="onboarddate" id="onboarddate" value="<%=!employee.getOnboarddate().equals("")?StringUtils.adToTw(employee.getOnboarddate()):today%>" type="text" class="textfield" size="10" maxlength="10" onChange='chkDate("onboarddate");' > (格式 YY-MM-DD 例如 96-5-24)
              &nbsp;<A href="javascript:sDate('onboarddate')"><IMG src="images/calendar.gif" border="0"></A>
            </td>
          </tr>
          <tr>
            <td class=dataLabel><div align="right">勞保投保薪資：</div></td>
            <td colspan="2" align="left"><input name="laborInsurance" id="laborInsurance" value="<%=employee.getLaborInsurance()%>" type="text" class="textfield" size="10" maxlength="10" onChange='validateNumber("laborInsurance");' ></td>
          </tr>
		  <tr>
            <td class=dataLabel><div align="right">健保投保薪資：</div></td>
            <td colspan="2" align="left"><input name="healthInsurance" id="healthInsurance" value="<%=employee.getHealthInsurance()%>" type="text" class="textfield" size="10" maxlength="10" onChange='validateNumber("healthInsurance");' ></td>
          </tr>
		  <tr>
            <td class=dataLabel><div align="right">勞退提撥工資：</div></td>
            <td colspan="2" align="left"><input name="laborRetireFee" id="laborRetireFee" value="<%=employee.getLaborRetireFee()%>" type="text" class="textfield" size="10" maxlength="10" onChange='validateNumber("healthInsurance");' ><A href="javascript:sRetire()">勞工退休金月提繳工資分級表</A></td>
          </tr>
          <tr>
            <td class=dataLabel><div align="right">退休金提撥金額：</div></td>
            <td colspan="2" align="left"><input name="retirefee" id="retirefee" value="<%=employee.getRetirefee()%>" type="text" class="textfield" size="10" maxlength="10" onChange='validateNumber("retirefee");' ></td>
          </tr>
          <tr>
            <td class=dataLabel width="20%"><div align="right">是否已離職：</div></td>
            <td colspan="2" align="left">
              <input name="isresign" type="radio" value="Y" <%=employee.getIsresign().equals("Y")?"checked":""%> onClick='showResign("Y");'>是
              <input name="isresign" type="radio" value="N" <%=employee.getEmployeeno().equals("")||employee.getIsresign().equals("N")?"checked":""%>  onClick='showResign("N");'>否
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
            <input type=hidden name="ayear" value="<%=year%>"/>
          </td></tr>
        </TBODY></TABLE>
      </TD></TR></TBODY></TABLE>
    </td></tr>
    <tr>
      <td height="30" colspan="3">
        <div align="right">
          <input name="Submit0" type="button" id="btnUpdate" class='ui-state-default' value="儲存" onClick='doUpdate();'>
          <%if (!CREATE.equals("Y")) {%><input name="Submit1" type="button" class='ui-state-default' value="刪除" onClick='doRemove();'><% } %>
          <input name="Submit2" type="button" class='ui-state-default' value="取消" onClick='doCancel();'>
        </div></td>
    </tr>
  </table>

  <input type=hidden name="CREATE" value="<%=CREATE%>"/>
  </form>
</body>
</html>
