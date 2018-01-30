<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.csjian.util.*" %>
<%@ page import="com.csjian.model.bean.*" %>

<%
	String year = (String)request.getAttribute("year");
	CompanyBean company = (CompanyBean)request.getAttribute("company");
	Calendar cal = Calendar.getInstance();
  	int currentyear = cal.get(Calendar.YEAR);

%>
<script type="text/javascript">	
function doPrint() {
	window.location.href='<%=response.encodeURL("report.do?action=employeeListPDF") %>' + "&year=" + $('#year').val() + "&format=" + $('input[name=format]:checked').val();
}
</script>
  <form method="POST" Name="mainform" Action="<%=response.encodeURL("servlet/ReportServlet") %>" class = "entry-form" onSubmit='return validate();'>
  <table width="100%"  border="0" cellpadding="0" cellspacing="0">
    <tr><td colspan=2>
      <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
        <TBODY>
          <tr>
            <td height="30" class=moduleTitle valign="bottom">員工基本資料報表</td>
          </tr>
          <TR><TD>
            <DIV class=hline><IMG height=1 src="images/blank.gif"></DIV>
          </TD></TR>
      </TBODY></TABLE><BR>
    </td></tr>
    <tr>
		<td>年度：</td>
        
		<td><select name="year" id="year" class="select">
          <% for (int i=2006; i<currentyear+1; i++) { %>
          <option value="<%=i%>" <%=(i+"").equals(year)?"selected":"" %>><%=(i-1911)%></option>
          <% } %>
        </select> &nbsp;&nbsp;年</td>
	</tr> 
	<tr><td colspan=2 align="right">      	
       &nbsp;&nbsp;
    </td></tr>   
	<tr>
		<td>報表格式：</td>
		<td><input name="format" type="radio" value="PDF" checked><IMG height=25 width=25 src="images/pdf.jpg">&nbsp;PDF檔&nbsp;&nbsp;&nbsp;
			<input name="format" type="radio" value="CSV" ><IMG height=25 width=25 src="images/excel.jpg">&nbsp;CSV檔&nbsp;&nbsp;&nbsp;
		</td>        
	</tr>  
	<tr><td colspan=2 align="right">      	
        <input name="Submit3" type="button" class='ui-state-default' value="產生報表" onclick="doPrint();">
    </td></tr>   
  </table>
  <input type=hidden name="regcode" value="<%=company.getRegcode()%>"/>
	<input type=hidden name="report" value="employeeList"/>
  </form>
