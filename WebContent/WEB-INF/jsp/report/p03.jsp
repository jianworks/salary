<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.csjian.util.*" %>
<%@ page import="com.csjian.model.bean.*" %>

<%
	String year = (String)request.getAttribute("year");
	String month = (String)request.getAttribute("month");
	String yearly = (String)request.getAttribute("yearly");
	CompanyBean company = (CompanyBean)request.getAttribute("company");
	List datamart = (List)request.getAttribute("datamart");
	Calendar cal = Calendar.getInstance();
  	int currentyear = cal.get(Calendar.YEAR);

%>

  <SCRIPT LANGUAGE="JavaScript">
  <!--
    function doPrint() {
      window.print();
    }

    function sYearMonth() {
	  with (document.mainform) {
        submit();
	  }
    }

    function doPDF() {
      with (document.mainform) {
        location.href = "servlet/P03Servlet?regcode=<%=company.getRegcode()%>&year=" + year.value + "&month=" + month.value;
      }
    }
   //-->
  </SCRIPT>

  <form method="POST" Name="mainform" Action="<%=response.encodeURL("report.do?action=p03") %>" class = "entry-form" onSubmit='return validate();'>
  <table width="100%"  border="0" cellpadding="0" cellspacing="0">
    <tr><td colspan=7>
      <div class="noprint">
      <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
        <TBODY>
          <tr>
            <td height="30" class=moduleTitle valign="bottom">銀行薪資轉帳表</td>
          </tr>
          <TR><TD>
            <DIV class=hline><IMG height=1 src="images/blank.gif"></DIV>
          </TD></TR>
      </TBODY></TABLE><BR>
      </div>
    </td></tr>
    <tr><td colspan=7>
      <div align="left" class="noprint">
        <select name="year" class="select" onChange='sYearMonth();'>
          <% for (int i=2006; i<currentyear+2; i++) { %>
          <option value="<%=i%>" <%=(i+"").equals(year)?"selected":"" %>><%=i-1911%></option>
          <% } %>
        </select> &nbsp;&nbsp;年
        &nbsp;&nbsp;&nbsp;&nbsp;
        <select name="month" class="select" onChange='sYearMonth();'>
          <% for (int i=1; i<=12; i++) { %>
          <option value="<%=i>=10?""+i:"0"+i%>" <%=i==Integer.parseInt(month)?"selected":"" %>><%=i%></option>
          <% } %>
        </select> &nbsp;&nbsp;月
        <input name="Submit3" type="button" class='ui-state-default' value="列印" onClick='doPrint();'>&nbsp;&nbsp;
        <input name="Submit3" type="button" class='ui-state-default' value="產生PDF報表" onClick='doPDF();'>
      </div>
    </td></tr>

    <tr>
      <td colspan="7" class="background_left_menu"><div align="left">
        <table width="95%"  border="1" cellpadding="0" cellspacing="0">
          <tr class=moduleListTitle height="25">
            <td colspan=4><div align="center"><%=company.getRegname() + "  " + (Integer.parseInt(year)-1911) + "年" +  month + "月 薪資轉帳表"%></div></td>
          </tr>
          <tr class=moduleListTitle height="25">
            <td><div align="center">序號</div></td>
            <td><div align="center">姓名</div></td>
            <td><div align="center">銀行帳號</div></td>
            <td><div align="center">實發金額</div></td>
          </tr>
          <% 
          	int total = 0;
          	for (int i=0; i<datamart.size(); i++) {
               String[] item = (String [])datamart.get(i);
               total += item[2].equals("")?0:Integer.parseInt(item[2]);
          %>
          <tr <%=(i%2)==0?"class=evenListRow":"class=oddListRow"%> height="25">
            <td><div align="center"><%=i+1%></div></td>
            <td><div align="center"><%=item[0]%></div></td>
            <td><div align="center"><%=item[1].equals("")?"&nbsp;":item[1]%></div></td>
            <td><div align="center"><%=item[2].equals("")?"0":StringUtils.addComma(item[2])%></div></td>
          </tr>
          <% } %>
          <tr height="25">
            <td><div align="center">&nbsp;</div></td>
            <td><div align="center">&nbsp;</div></td>
            <td><div align="center">&nbsp;</div></td>
            <td><div align="center">&nbsp;</div></td>
          </tr>
          <tr height="25">
            <td colspan=2><div align="center">合計</div></td>
            <td><div align="center">&nbsp;</div></td>
            <td><div align="center"><%=StringUtils.addComma(total+"")%></div></td>
          </tr>
        </table>
      </td>
    </tr>
  </table>
  <input type=hidden name="regcode" value="<%=company.getRegcode()%>"/>
  </form>
