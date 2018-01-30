<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.csjian.util.*" %>
<%@ page import="com.csjian.model.bean.*" %>

<%
	String year = (String)request.getAttribute("year");
	String month = (String)request.getAttribute("month");
	CompanyBean company = (CompanyBean)request.getAttribute("company");
	Vector datamart = (Vector)request.getAttribute("datamart");
	Calendar cal = Calendar.getInstance();
  	int currentyear = cal.get(Calendar.YEAR);

%>

  <SCRIPT LANGUAGE="JavaScript">
  <!--
    function doPrint() {
      window.print();
    }


    function doPDF() {
      with (document.mainform) {
        location.href = "servlet/P05Servlet?regcode=<%=company.getRegcode()%>&year=" + year.value;
      }
    }
   //-->
  </SCRIPT>

  <form method="POST" Name="mainform" Action="<%=response.encodeURL("report.do?action=p05")%>" class = "entry-form" onSubmit='return validate();'>
  <table border="0" cellpadding="0" cellspacing="0" width="95%">
    <tr><td colspan=7>
      <div class="noprint">
      <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
        <TBODY>
          <tr>
            <td height="30" class=moduleTitle valign="bottom">年度薪資彙總表</td>
          </tr>
          <TR><TD>
            <DIV class=hline><IMG height=1 src="images/blank.gif"></DIV>
          </TD></TR>
      </TBODY></TABLE><BR>
      </div>
    </td></tr>
    <tr><td colspan=7>
      <div align="left" class="noprint">
        <select name="year" class="select" onChange='submit();'>
          <% for (int i=2006; i<currentyear+2; i++) { %>
          <option value="<%=i%>" <%=(i+"").equals(year)?"selected":"" %>><%=i-1911%></option>
          <% } %>
        </select> &nbsp;&nbsp;年
        <input name="Submit3" type="button" class='ui-state-default' value="列印" onClick='doPrint();'>
        <input name="Submit3" type="button" class='ui-state-default' value="產生PDF報表" onClick='doPDF();'>
      </div>
    </td></tr>
    <tr>
      <td colspan="7" class="background_left_menu"><div align="right">
        <table width="100%" border="0" cellpadding="0" cellspacing="2" bordercolor="#FFFFFF" >
          <tr class=moduleListTitle height="25" style="border-bottom-color:#000000">
            <td colspan=<%=15%> width=100%><div align="center"><%=company.getRegname() + "  " + (Integer.parseInt(year)-1911) + "年度月薪資彙總表"%></div></td>
          </tr>
          <tr class=moduleListTitle height="25" valign="center" style="border-left-color:#FFFFFF">
            <td><div align="left">員工編號\證號</div></td>
            <td><div align="left">給付總額</div></td>
            <td rowspan=3><div align="center">一月</div></td>
            <td rowspan=3><div align="center">二月</div></td>
            <td rowspan=3><div align="center">三月</div></td>
            <td rowspan=3><div align="center">四月</div></td>
            <td rowspan=3><div align="center">五月</div></td>
            <td rowspan=3><div align="center">六月</div></td>
            <td rowspan=3><div align="center">七月</div></td>
            <td rowspan=3><div align="center">八月</div></td>
            <td rowspan=3><div align="center">九月</div></td>
            <td rowspan=3><div align="center">十月</div></td>
            <td rowspan=3><div align="center">十一月</div></td>
            <td rowspan=3><div align="center">十二月</div></td>
            <td rowspan=3><div align="center">合計</div></td>
          </tr>
          <tr class=moduleListTitle height="25" valign="center">
            <td><div align="left">所得人姓名</div></td>
            <td><div align="left">扣繳稅額</div></td>
          </tr>
          <tr valign="center" class=moduleListTitle height="25">
            <td><div align="center">&nbsp;</div></td>
            <td><div align="left">給付淨額</div></td>
          </tr>
          <tr height="5" valign="top">
            <td colspan=15><hr/></td>
          </tr>
          <% for (int i=0; i<datamart.size()-1; i++) {
               String[] data = (String[])datamart.elementAt(i);
          %>
          <tr height="25" valign="center">
            <td colspan=2><div align="left"><%=data[0] + "   " + data[1]%></div></td>
            <% for (int j=1; j<=13; j++) { %>
            <td><div align="right"><%=data[3+j]!=null&&!data[3+j].equals("")?StringUtils.addComma(data[3+j]):"0"%></div></td>
            <% } %>
          </tr>
          <tr height="25" valign="center">
            <td colspan=2><div align="left"><%=data[2]%></div></td>
            <% for (int j=1; j<=13; j++) { %>
            <td><div align="right"><%=data[16+j]!=null&&!data[16+j].equals("")?StringUtils.addComma(data[16+j]):"0"%></div></td>
            <% } %>
          </tr>
          <tr height="25" valign="center">
            <td colspan=2><div align="left">&nbsp;</div></td>
            <% for (int j=1; j<=13; j++) { %>
            <td><div align="right"><%=data[29+j]!=null&&!data[29+j].equals("")?StringUtils.addComma(data[29+j]):"0"%></div></td>
            <% } %>
          </tr>
          <tr height="25" valign="center">
            <td colspan=15><div align="left"><%=data[3] %></div></td>
          </tr>
          <% } %>
          <tr height="5" valign="top">
            <td colspan=15><hr/></td>
          </tr>
          <% if (datamart.size()>1) { %>
          <% String[] total = (String[])datamart.elementAt(datamart.size()-1); %>
          <tr height="25" valign="center">
            <td colspan=2><div align="left">給付總額總計</div></td>
            <% for (int j=1; j<=13; j++) { %>
            <td><div align="right"><%=StringUtils.addComma(total[3+j])%></div></td>
            <% } %>
          </tr>
          <tr height="25" valign="center">
            <td colspan=2><div align="left">扣總稅額總計</div></td>
            <% for (int j=1; j<=13; j++) { %>
            <td><div align="right"><%=StringUtils.addComma(total[16+j])%></div></td>
            <% } %>
          </tr>
          <tr height="25" valign="center">
            <td colspan=2><div align="left">給付淨額總計</div></td>
            <% for (int j=1; j<=13; j++) { %>
            <td><div align="right"><%=StringUtils.addComma(total[29+j])%></div></td>
            <% } %>
          </tr>
          <% } %>
        </table>
      </td>
    </tr>
  </table>
  <input type=hidden name="regcode" value="<%=company.getRegcode()%>"/>
  </form>