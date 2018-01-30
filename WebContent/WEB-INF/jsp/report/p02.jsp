<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.csjian.util.*" %>
<%@ page import="com.csjian.model.bean.*" %>

<%
	String year = (String)request.getAttribute("year");
	String month = (String)request.getAttribute("month");
	CompanyBean company = (CompanyBean)request.getAttribute("company");
	SalaryBean[] salaryList = (SalaryBean[])request.getAttribute("salaryList");
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
        location.href = "servlet/P02Servlet?regcode=<%=company.getRegcode()%>&year=" + year.value + "&month=" + month.value;
      }
    }
   //-->
  </SCRIPT>

  <form method="POST" Name="mainform" Action="<%=response.encodeURL("report.do?action=p02") %>" class = "entry-form" onSubmit='return validate();'>
  <table width="100%"  border="0" cellpadding="0" cellspacing="0">
    <tr><td colspan=7>
      <div class="noprint">
      <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
        <TBODY>
          <tr>
            <td height="30" class=moduleTitle valign="bottom">薪資條列印</td>
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
          <% for (int i=2006; i<currentyear+1; i++) { %>
          <option value="<%=i%>" <%=(i+"").equals(year)?"selected":"" %>><%=(i-1911)%></option>
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
    <% for (int i=0; i<salaryList.length; i++) {
         Vector pitem = salaryList[i].getItemp();
         Vector mitem = salaryList[i].getItemm();
    %>
    <tr>
      <td colspan="7" class="background_left_menu"><div align="left">
        <table width="95%"  border="1" cellpadding="0" cellspacing="0">
          <tr class=moduleListTitle height="25">
            <td colspan=6><div align="center"><%=company.getRegname() + "薪資單"%></div></td>
          </tr>
          <tr class=moduleListTitle height="25">
            <td colspan=6>
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr><td colspan=2 align=left>員工編號：<%=salaryList[i].getEmployeeno()%></td></tr>
                <tr><td align=left>員工姓名：<%=salaryList[i].getName()%></td><td align=right>薪資年月：<%=(Integer.parseInt(year)-1911) + "年" +  month + "月"%></td></tr>
              </table>
            </td>
          </tr>
          <tr>
            <td colspan=2 align=center>應領金額</td>
            <td colspan=2 align=center>應扣金額</td>
            <td colspan=2 align=center>合計</td>
          </tr>
          <tr valign=top>
            <td width="20%"><div align="left">基本薪資：<br/>加班薪資：<br/>
            <% for (int j=0; j<pitem.size(); j++) {
                 String[] item = (String[])pitem.elementAt(j);
            %>
               <%=item[1]%>：<br/>

            <% } %>
            </div></td>
            <td width="13%"><div align="right"><%=StringUtils.addComma(salaryList[i].getBasesalary())%>&nbsp;<br/><%=StringUtils.addComma(salaryList[i].getOversalary())%>&nbsp;<br/>
            <% for (int j=0; j<pitem.size(); j++) {
                 String[] item = (String[])pitem.elementAt(j);
            %>
               <%=StringUtils.addComma(item[2])%>&nbsp;<br/>

            <% } %>
            </div></td>
            <td width="25%"><div align="left">
            <% for (int j=0; j<mitem.size(); j++) {
                 String[] item = (String[])mitem.elementAt(j);
            %>
               <%=item[1]%>：<br/>

            <% } %>扣繳稅額：
            </div></td>
            <td width="13%"><div align="right">
            <% for (int j=0; j<mitem.size(); j++) {
                 String[] item = (String[])mitem.elementAt(j);
            %>
               <%=StringUtils.addComma(item[2])%>&nbsp;<br/>

            <% } %><%=StringUtils.addComma(salaryList[i].getTax())%>&nbsp;
            </div></td>
            <td width="15%"><div align="left">應領金額：<br/>應扣金額：</br>實發金額：</br></div></td>
            <td><div align="right"><%=StringUtils.addComma(Integer.parseInt(!salaryList[i].getBtotal().equals("")?salaryList[i].getBtotal():"0") + Integer.parseInt(!salaryList[i].getPtotal().equals("")?salaryList[i].getPtotal():"0") + "")%>&nbsp;
            <br/><%=StringUtils.addComma(salaryList[i].getMtotal())%>&nbsp;<br/><%=StringUtils.addComma(salaryList[i].getTotal())%>&nbsp;
            </div></td>
          </tr>
        </table>
      </td>
    </tr>
    <tr><td colspan=7 height="30"></td></tr>
    <%  } %>
    <%  if (salaryList.length<1) {%>
    <tr height=50><td colspan=7>&nbsp;</td></tr>
    <tr><td colspan=7><font color="#FF0000">本月份無建檔之薪資資料</font></td></tr>
    <%  } %>
  </table>
  <input type=hidden name="regcode" value="<%=company.getRegcode()%>"/>
  </form>
