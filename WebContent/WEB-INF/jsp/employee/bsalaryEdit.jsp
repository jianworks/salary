<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.csjian.model.bean.*" %>

<%
	String keyword = (String)request.getAttribute("keyword");
	String year = (String)request.getAttribute("year");
	String resign = (String)request.getAttribute("resign");
	int pageno = 1;
	try {
    	pageno = Integer.parseInt((String)request.getAttribute("pageno"));      
	} catch (Exception e) {
		// do nothing
	}
    EmployeeBean employee = (EmployeeBean)request.getAttribute("employee");
    BsalaryBean bsalary = (BsalaryBean)request.getAttribute("bsalary");
    Vector itemp = bsalary.getItemp();
    Vector itemm = bsalary.getItemm();
    int totalp = itemp.size();
    int rowp = (totalp-1)/2 + 1;
    int totalm = itemm.size();
    int rowm = (totalm-1)/2 + 1;
%>
	<script src="js/nextField.js" type=text/javascript></script>
  <script type="text/javascript">
  <!--
  	var sequence = ["basesalary", "baserate", "ovettimerate"];
  	<% for (int i=0; i<itemp.size(); i++) { %>
  		sequence.push('pamount<%=(i)%>');
  	<%}%>
  	<% for (int i=0; i<itemm.size(); i++) { %>
		sequence.push('mamount<%=(i)%>');
	<%}%>
	sequence.push("mark");
	sequence.push("btnUpdate");
	
    function doUpdate() {
    	mainform.submit();
    }
    
    function doCancel() {
    	mainform.action = "<%=response.encodeURL("employee.do?action=list") %>";
    	mainform.submit();
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
  <form method="POST" Name="mainform" Action="<%=response.encodeURL("employee.do?action=updateBsalary") %>" onSubmit='return validate();' class = "entry-form">
  <table width="100%"  border="0" cellpadding="0" cellspacing="0">
    <tr><td colspan=3>
      <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
        <TBODY>
          <tr>
            <td height="30" class=moduleTitle valign="bottom">員工薪資資料設定</td>
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
            <td class=dataLabel width="20%"><div align="right">基本薪資：</div></td>
            <td align="left"><input name="basesalary" id="basesalary" value="<%=bsalary.getBasesalary()%>" type="text" class="textfield" size="10" maxlength="8"></td>
          </tr>
          <tr>
            <td class=dataLabel><div align="right">基本時薪：</div></td>
            <td colspan="2" align="left"><input name="baserate" id="baserate" value="<%=bsalary.getBaserate()%>" type="text" class="textfield" size="10" maxlength="8"></td>
          </tr>
          <tr>
            <td class=dataLabel><div align="right">加班時薪：</div></td>
            <td colspan="2" align="left"><input name="ovettimerate" id="ovettimerate" value="<%=bsalary.getOvertimerate()%>" type="text" class="textfield" size="10" maxlength="8"></td>
          </tr>
        </TBODY></TABLE>
      </TD></TR></TBODY></TABLE>
    </td></tr>

    <tr><td colspan=3 height="10"></td></tr>

    <tr><td colspan=3>
      <TABLE class=formOuterBorder cellSpacing=0 cellPadding=0 width="100%" border=0><TBODY><TR><TD>
        <TABLE cellSpacing=1 cellPadding=0 width="100%" border=0>
          <TBODY><TR><TH class=formSecHeader align=left>其他加項薪資設定</TH></TR></TBODY>
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
              <input name="pamount<%=(i*2 + j)%>" id="pamount<%=(i*2 + j)%>" value="<%=item[2]%>" type="text" class="textfield" size="10" maxlength="8">
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
          <TBODY><TR><TH class=formSecHeader align=left>其他減項薪資設定</TH></TR></TBODY>
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
              <input name="mamount<%=(i*2 + j)%>" id="mamount<%=(i*2 + j)%>" value="<%=item[2]%>" type="text" class="textfield" size="10" maxlength="8">
              <input type=hidden name="mseq<%=(i*2 + j)%>" value="<%=item[0]%>"/>
              <% if (item[1].equals("代扣勞保")) { %>&nbsp;&nbsp;<A href="javascript:sLabor()">勞保負擔金額一覽表</A><% } %>
              <% if (item[1].equals("代扣健保")) { %>&nbsp;&nbsp;<A href="javascript:sHealth()">健保負擔金額一覽表</A><% } %>
            </td>
            <%   }
               }
            %>
          </tr>
          <% } %>
        </TBODY></TABLE>
      </TD></TR></TBODY></TABLE>
    </td></tr>
    <tr>
      <td class=dataLabel width="20%"><div align="right">備註：</div></td>
      <td colspan=2><textarea name="mark" id="mark" cols="60" rows="3"><%=bsalary.getMark()%></textarea></td>
    </tr>
    <tr>
      <td height="30" colspan="3">
        <div align="right">
          <input name="Submit0" id="btnUpdate" class='ui-state-default' type="button" value="儲存" onClick='doUpdate();'>
          <input name="Submit2" class='ui-state-default' type="button" value="取消" onClick='doCancel();'>
        </div></td>
    </tr>
  </table>

  <input type=hidden name="employeeno" value="<%=bsalary.getEmployeeno()%>"/>
  <input type=hidden name="pageno" value="<%=pageno%>"/>
  <input type=hidden name="year" value="<%=year%>"/>
  <input type=hidden name="ayear" value="<%=year%>"/>
  <input type=hidden name="keyword" value="<%=keyword%>"/>
  <input type=hidden name="resign" value="<%=resign%>"/>  
  <input type=hidden name="totalp" value="<%=totalp%>"/>
  <input type=hidden name="totalm" value="<%=totalm%>"/>
  </form>
