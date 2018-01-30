<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.csjian.model.bean.*" %>

<%
    SalaryBean salary = (SalaryBean)request.getAttribute("salary");
	EmployeeBean employee = (EmployeeBean)request.getAttribute("employee");
	Vector itemp = salary.getItemp();
	Vector itemm = salary.getItemm();
	int totalp = itemp.size();
	int rowp = (totalp-1)/2 + 1;
	int totalm = itemm.size();
	int rowm = (totalm-1)/2 + 1;
	int days = 30;
	if (employee.getIsresign().equals("Y")) {
		try {
			String[] resigndate = employee.getResigndate().split("-");
			days = Integer.parseInt(resigndate[2]);
		}catch(Exception e){}
	}
%>
	<script src="js/nextField.js" type=text/javascript></script>
  <script type="text/javascript">
  <!--
  	var sequence = ["baserate", "workinghr", "basesalary", "overtimerate", "overtime", "oversalary", "tax"];
	<% for (int i=0; i<itemp.size(); i++) { %>
		sequence.push('pamount<%=(i)%>');
	<%}%>
	<% for (int i=0; i<itemm.size(); i++) { %>
		sequence.push('mamount<%=(i)%>');
	<%}%>
	sequence.push("btnUpdate");
	
    function doUpdate() {
	  calBsalary();
	  calOsalary();
	  calBtotal();
	  calPtotal();
	  calMtotal();
	  calTotal();
      document.mainform.submit();	
    }
    
    function doCancel() {
      document.mainform.action = '<%=response.encodeURL("salary.do?action=list") %>';
      document.mainform.submit();
    }
    
    function calBsalary() {
      with (document.mainform) {
        var totals = 0;
        if (baserate.value != "" && workinghr.value != "" && baserate.value != "0" && workinghr.value != "0") {
          totals = parseInt(baserate.value)*parseFloat(workinghr.value);
          basesalary.value = parseInt(totals);
          calBtotal();
        }
      }
    }

    function calOsalary() {
      with (document.mainform) {
        var totals = 0;
        if (overtimerate.value != "" && overtime.value != "" && overtimerate.value != "0" && overtime.value != "0") {
          totals = parseInt(overtimerate.value)*parseFloat(overtime.value);
          oversalary.value = parseInt(totals);
          calBtotal();
        }
      }
    }

    function calBtotal() {
      with (document.mainform) {
        var totals = 0;
        if (basesalary.value != "") totals += parseInt(basesalary.value);
        if (oversalary.value != "") totals += parseInt(oversalary.value);
        btotal.value = totals;
        calTotal();
      }
    }

    function calPtotal() {
      with (document.mainform) {
        var totals = 0;
        var a = 0;
        for (var i=0; i<totalp.value; i++) {
          eval ("a=pamount" + i + ".value");
          if (a != "") {
            totals += parseInt(a);
          }
        }
        ptotal.value = totals;
        calTotal();
      }
    }

    function calMtotal() {
      with (document.mainform) {
        var totals = 0;
        var a = 0;
        for (var i=0; i<totalm.value; i++) {
          eval ("a=mamount" + i + ".value");
          if (a != "") {
            totals += parseInt(a);
          }
        }
        if (tax.value != "") totals+= parseInt(tax.value);
        mtotal.value = totals;
        calTotal();
      }
    }

    function calTotal() {
      with (document.mainform) {
        var totals = 0;
        if (btotal.value != "" && btotal.value != "0") totals += parseInt(btotal.value);
        if (ptotal.value != "" && ptotal.value != "0") totals += parseInt(ptotal.value);
        if (mtotal.value != "" && mtotal.value != "0") totals -= parseInt(mtotal.value);
        total.value = totals;
      }
    }

    function sHealth() {
      with (document.mainform) {
      	window.open("misc/healthFee.jsp?amount=<%=employee.getHealthInsurance()%>", "", "height=320,width=480,scrollbars=yes");
      }
    }

    function sLabor() {
      with (document.mainform) {
      	window.open("misc/laborFee.jsp?amount=<%=employee.getLaborInsurance()%>", "", "height=320,resizable=yes,scrollbars=yes");
      }
    }
  //-->
  </script>

  <form method="POST" Name="mainform" Action="<%=response.encodeURL("salary.do?action=update") %>" onSubmit='return validate();' class = "entry-form">
  <table width="100%"  border="0" cellpadding="0" cellspacing="0">
    <tr><td colspan=3>
      <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
        <TBODY>
          <tr>
            <td height="30" class=moduleTitle valign="bottom">每月薪資資料建檔</td>
          </tr>
          <TR><TD>
            <DIV class=hline><IMG height=1 src="images/blank.gif"></DIV>
          </TD></TR>
      </TBODY></TABLE><BR>
    </td></tr>

    <tr><td colspan=3 align=left>員工編號：<%=employee.getEmployeeno()%>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;姓名：<%=employee.getName()%></td></tr>

    <tr><td colspan=3>
      <TABLE class=formOuterBorder cellSpacing=0 cellPadding=0 width="100%" border=0><TBODY><TR><TD>
        <TABLE cellSpacing=1 cellPadding=0 width="100%" border=0>
          <TBODY><TR><TH class=formSecHeader align=left>員工基本薪資設定</TH></TR></TBODY>
        </TABLE>
        <TABLE cellSpacing=1 cellPadding=2 width="100%" border=0><TBODY>
          <tr>
            <td class=dataLabel><div align="right">基本時薪：</div></td>
            <td align="left"><input name="baserate" id="baserate" value="<%=salary.getBaserate()%>" type="text" class="textfield" size="10" maxlength="10" onChange='calBsalary();'></td>
            <td class=dataLabel><div align="right">工作時數：</div></td>
            <td align="left"><input name="workinghr" id="workinghr" value="<%=salary.getWorkinghr()%>" type="text" class="textfield" size="10" maxlength="7" onChange='calBsalary();'></td>
          </tr>
          <tr>
            <td class=dataLabel width="20%"><div align="right">基本薪資：</div></td>
            <td colspan="3" align="left"><input name="basesalary" id="basesalary" value="<%=salary.getBasesalary()%>" type="text" class="textfield" size="10" maxlength="10" onChange='calBtotal();'></td>
          </tr>
          <tr>
            <td class=dataLabel><div align="right">加班時薪：</div></td>
            <td align="left"><input name="overtimerate" id="overtimerate" value="<%=salary.getOvertimerate()%>" type="text" class="textfield" size="7" maxlength="7" onChange='calOsalary();'></td>
            <td class=dataLabel><div align="right">加班時數：</div></td>
            <td align="left"><input name="overtime" id="overtime" value="<%=salary.getOvertime()%>" type="text" class="textfield" size="7" maxlength="7" onChange='calOsalary();'></td>
          </tr>
          <tr>
            <td class=dataLabel width="20%"><div align="right">加班薪資：</div></td>
            <td colspan="3" align="left"><input name="oversalary" id="oversalary" value="<%=salary.getOversalary()%>" type="text" class="textfield" size="10" maxlength="10" onChange='calBtotal();'></td>
          </tr>
        </TBODY></TABLE>
      </TD></TR></TBODY></TABLE>
    </td></tr>

    <tr><td colspan=3 height="10"></td></tr>

    <tr><td colspan=3>
      <TABLE class=formOuterBorder cellSpacing=0 cellPadding=0 width="100%" border=0><TBODY><TR><TD>
        <TABLE cellSpacing=1 cellPadding=0 width="100%" border=0>
          <TBODY><TR><TH class=formSecHeader align=left>其他薪資加項</TH></TR></TBODY>
        </TABLE>
        <TABLE cellSpacing=1 cellPadding=2 width="100%" border=0><TBODY>
          <% for (int i=0; i<rowp; i++) { %>
          <tr>
            <% for (int j=0; j<2; j++) {
                 if ((i*2 + j)<totalp) {
                   String[] item = (String[])itemp.elementAt((i*2 + j));
            %>
            <td class=dataLabel width="20%"><div align="right"><%=item[1]%>：</div></td>
            <td align="left">
              <input name="pamount<%=(i*2 + j)%>" id="pamount<%=(i*2 + j)%>" value="<%=item[2]%>" type="text" class="textfield" size="10" maxlength="10" onChange='calPtotal();'>
              <input type=hidden name="pseq<%=(i*2 + j)%>" value="<%=item[0]%>"/>
            </td>
            <%   }
               }
            %>
          </tr>
          <% } %>
        </TBODY></TABLE>
      </TD></TR></TBODY></TABLE>
    </td></tr>

    <tr><td colspan=3 height="10"></td></tr>

    <tr><td colspan=3>
      <TABLE class=formOuterBorder cellSpacing=0 cellPadding=0 width="100%" border=0><TBODY><TR><TD>
        <TABLE cellSpacing=1 cellPadding=0 width="100%" border=0>
          <TBODY><TR><TH class=formSecHeader align=left>其他薪資減項</TH></TR></TBODY>
        </TABLE>
        <TABLE cellSpacing=1 cellPadding=2 width="100%" border=0><TBODY>
          <% for (int i=0; i<rowm; i++) { %>
          <tr>
            <% for (int j=0; j<2; j++) {
                 if ((i*2 + j)<totalm) {
                   String[] item = (String[])itemm.elementAt((i*2 + j));
            %>
            <td class=dataLabel width="20%"><div align="right"><%=item[1]%>：</div></td>
            <td align="left">
              <input type=hidden name="mseq<%=(i*2 + j)%>" value="<%=item[0]%>"/>
			  <% 
			  	 String amount = item[2];
			     if (item[1].equals("代扣勞保")&&!salary.getSaved()) { 
			  	   try {
						amount = "" + ((Integer.parseInt(item[2]))*days/30);
				   } catch(Exception e){}
			     } %>
              <input name="mamount<%=(i*2 + j)%>" id="mamount<%=(i*2 + j)%>" value="<%=amount%>" type="text" class="textfield" size="10" maxlength="10" onChange='calMtotal();'>
              <% if (item[1].equals("代扣勞保")) { %>(按日計代扣)&nbsp;&nbsp;<A href="javascript:sLabor()">勞保負擔金額一覽表</A><% } %>
              <% if (item[1].equals("代扣健保")) { %>(當月離職不用扣健保費)&nbsp;&nbsp;<A href="javascript:sHealth()">健保負擔金額一覽表</A><% } %>
            </td>
            <%   }
               }
            %>
          </tr>
          <% } %>
          <tr>
            <td class=dataLabel><div align="right">扣繳稅額：</div></td>
            <td align="left" colspan=3><input name="tax" id="tax" value="<%=salary.getTax()%>" type="text" class="textfield" size="7" maxlength="7" onChange='calMtotal();'></td>
          </tr>
        </TBODY></TABLE>
      </TD></TR></TBODY></TABLE>
    </td></tr>
    <tr><td colspan=3 height=10><hr/></td></tr>
    <tr>
      <td class=dataLabel width="20%"><div align="right">本薪小計：</div></td>
      <td align="left"><input name="btotal" value="<%=salary.getBtotal()%>" type="text" class="textfield" size="10" maxlength="10" readonly=true></td>
    </tr>
    <tr>
      <td class=dataLabel width="20%"><div align="right">加項小計：</div></td>
      <td align="left"><input name="ptotal" value="<%=salary.getPtotal()%>" type="text" class="textfield" size="10" maxlength="10" readonly=true></td>
    </tr>
    <tr>
      <td class=dataLabel width="20%"><div align="right">減項小計：</div></td>
      <td align="left"><input name="mtotal" value="<%=salary.getMtotal()%>" type="text" class="textfield" size="10" maxlength="10" readonly=true></td>
    </tr>
    <tr>
      <td class=dataLabel width="20%"><div align="right">本月實發金額：</div></td>
      <td align="left"><input name="total" value="<%=salary.getTotal()%>" type="text" class="textfield" size="10" maxlength="10" readonly=true></td>
    </tr>
    <tr>
      <td height="30" colspan="3">
        <div align="right">
          <input name="Submit0" id="btnUpdate" class='ui-state-default' type="button" value="儲存" onClick='doUpdate();'>
          <input name="Submit2" class='ui-state-default' type="button" value="取消" onClick='doCancel();'>
        </div></td>
    </tr>
  </table>

  <input type=hidden name="employeeno" value="<%=salary.getEmployeeno()%>"/>
  <input type=hidden name="year" value="<%=salary.getYear()%>"/>
  <input type=hidden name="month" value="<%=salary.getMonth()%>"/>
  <input type=hidden name="totalp" value="<%=totalp%>"/>
  <input type=hidden name="totalm" value="<%=totalm%>"/>
  <input type=hidden name="healthInsurance" value="<%=employee.getHealthInsurance()!=null?employee.getHealthInsurance():"0"%>"/>
  <input type=hidden name="laborInsurance" value="<%=employee.getLaborInsurance()!=null?employee.getLaborInsurance():"0"%>"/>
  <input type=hidden name="laborRetireFee" value="<%=employee.getLaborRetireFee()!=null?employee.getLaborRetireFee():"0"%>"/>
  <input type=hidden name="retireFee" value="<%=employee.getRetirefee()!=null?employee.getRetirefee():"0"%>"/>
  </form>
</body>
</html>
