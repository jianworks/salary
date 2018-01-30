<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.csjian.util.*" %>
<%@ page import="com.csjian.model.bean.*" %>

<%
	String year = (String)request.getAttribute("year");
	String month = (String)request.getAttribute("month");
	String yearly = (String)request.getAttribute("yearly");
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

    function cYear() {
      with (document.mainform) {
        yearly.value = "Y";
        submit();
      }
    }
    
    function cMonth() {
      with (document.mainform) {
        yearly.value = "N";
        submit();
      }
    }

    function doPDF() {
      with (document.mainform) {        
      	location.href = "servlet/P01Servlet?regcode=<%=company.getRegcode()%>&year=" + year.value + "&yearly=" + yearly.value + "&month=" + month.value;
      }
    }
    
    function doXLS() {
        with (document.mainform) {
          location.href = "servlet/P01XlsServlet?regcode=<%=company.getRegcode()%>&year=" + year.value + "&yearly=" + yearly.value + "&month=" + month.value;
        }
      }
   //-->
  </SCRIPT>

  <form method="POST" Name="mainform" Action="<%=response.encodeURL("report.do?action=p01") %>" class = "entry-form" onSubmit='return validate();'>
  <table border="0" cellpadding="0" cellspacing="0" width="100%">
    <tr><td colspan=7>
      <div class="noprint">
      <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
        <TBODY>
          <tr>
            <td height="30" class=moduleTitle valign="bottom"><%=yearly.equals("Y")?"年":"月" %>薪資表列印</td>
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
        <% if (yearly.equals("N")) { %>
        &nbsp;&nbsp;&nbsp;&nbsp;
        <select name="month" class="select" onChange='sYearMonth();'>
          <% for (int i=1; i<=12; i++) { %>
          <option value="<%=i>=10?""+i:"0"+i%>" <%=i==Integer.parseInt(month)?"selected":"" %>><%=i%></option>
          <% } %>
        </select> &nbsp;&nbsp;月
        <% } %>
        <input name="Submit3" class='ui-state-default' type="button" value="列印" onClick='doPrint();'>&nbsp;&nbsp;
        <input name="Submit3" class='ui-state-default' type="button" value="產生PDF報表" onClick='doPDF();'>
        <input name="Submit3" class='ui-state-default' type="button" value="產生Excel報表" onClick='doXLS();'>
        &nbsp;&nbsp;
        <% if (yearly.equals("N")) { %>
        <A href="javascript:cYear()">切換到年薪資表</A>
        <% } else { %>
        <A href="javascript:cMonth()">切換到月薪資表</A>
        <% } %>
      </div>
    </td></tr>
    <% if (salaryList.length>0) {
         Vector pitem = salaryList[0].getItemp();
         Vector mitem = salaryList[0].getItemm();
         int[] total = new int[pitem.size() + mitem.size() + 4];
    %>
    <tr>
      <td colspan="7" class="background_left_menu"><div align="left">
        <table width="95%"  border="1" cellpadding="0" cellspacing="0">
          <tr class=moduleListTitle height="25">
            <td colspan=<%=2+total.length %>><div align="center"><%=company.getRegname() + "  " + (Integer.parseInt(year)-1911) + "年" + month + "月 薪資表"%></div></td>
          </tr>
          <tr class=moduleListTitle height="25">
            <td><div align="center">員工編號</div></td>
            <td><div align="center">姓名</div></td>
            <td><div align="center">基本薪資</div></td>
            <td><div align="center">加班薪資</div></td>
            <% for (int j=0; j<pitem.size(); j++) {
                 String[] item = (String [])pitem.elementAt(j);
            %>
            <td><div align="center"><%=item[1]%></div></td>
            <% } %>
            <% for (int j=0; j<mitem.size(); j++) {
                 String[] item = (String [])mitem.elementAt(j);
            %>
            <td><div align="center"><%=item[1]%></div></td>
            <% } %>
            <td><div align="center">扣繳稅額</div></td>
            <td><div align="center">實發金額</div></td>
          </tr>
          <% for (int i=0; i<salaryList.length; i++) {
        	  pitem = salaryList[i].getItemp();
        	  mitem = salaryList[i].getItemm();
        	  int k = 0;
          %>
          <tr height="25">
            <td><div align="center"><%=salaryList[i].getEmployeeno()%></div></td>
            <td><div align="center"><%=salaryList[i].getName()%></div></td>
            <td><div align="center"><%=salaryList[i].getBasesalary().equals("")?"0":StringUtils.addComma(salaryList[i].getBasesalary())%></div></td> <% total[k] += Integer.parseInt(salaryList[i].getBasesalary()!=null&&!salaryList[i].getBasesalary().equals("")?salaryList[i].getBasesalary():"0"); k++; %>
            <td><div align="center"><%=salaryList[i].getOversalary().equals("")?"0":StringUtils.addComma(salaryList[i].getOversalary())%></div></td> <% total[k] += Integer.parseInt(salaryList[i].getOversalary()!=null&&!salaryList[i].getOversalary().equals("")?salaryList[i].getOversalary():"0"); k++; %>
            <% for (int j=0; j<pitem.size(); j++) {
                 String[] item = (String [])pitem.elementAt(j);
            %>
            <td><div align="center"><%=item[2]!=null&&!item[2].equals("")?StringUtils.addComma(item[2]):"0"%></div></td><% total[k] += Integer.parseInt(item[2]!=null&&!item[2].equals("")?item[2]:"0"); k++; %>
            <% } %>
            <% for (int j=0; j<mitem.size(); j++) {
                 String[] item = (String [])mitem.elementAt(j);
            %>
            <td><div align="center"><%=item[2]!=null&&!item[2].equals("")?StringUtils.addComma(item[2]):"0"%></div></td><% total[k] += Integer.parseInt(item[2]!=null&&!item[2].equals("")?item[2]:"0"); k++; %>
            <% } %>
            <td><div align="center"><%=salaryList[i].getTax().equals("")?"0":StringUtils.addComma(salaryList[i].getTax())%></div></td><% total[k] += Integer.parseInt(salaryList[i].getTax()!=null&&!salaryList[i].getTax().equals("")?salaryList[i].getTax():"0"); k++; %>
            <td><div align="center"><%=salaryList[i].getTotal().equals("")?"0":StringUtils.addComma(salaryList[i].getTotal())%></div></td><% total[k] += Integer.parseInt(salaryList[i].getTotal()!=null&&!salaryList[i].getTotal().equals("")?salaryList[i].getTotal():"0"); k++; %>
          </tr>
          <% } %>
          <tr height="25">
            <% for (int i=0; i<(2+total.length); i++) { %>
            <td><div align="center">&nbsp;</div></td>
            <% } %>
          </tr>
          <tr height="25">
            <td colspan=2><div align="center">合計</div></td>
            <% for (int i=0; i<total.length; i++) { %>
            <td><div align="center"><%=StringUtils.addComma(total[i]+"")%></div></td>
            <% } %>
          </tr>
        </table>
      </td>
    </tr>
    <%  } else {%>
    <tr height=50><td colspan=7>&nbsp;</td></tr>
    <tr><td colspan=7><font color="#FF0000">本<%=yearly.equals("Y")?"年度":"月份" %>無建檔之薪資資料</font></td></tr>
    <%  } %>
  </table>
  <input type=hidden name="regcode" value="<%=company.getRegcode()%>"/>
  <input type=hidden name="yearly" value="<%=yearly%>"/>
  <% if (yearly.equals("Y")) { %>
  <input type=hidden name="month" value=""/>
  <% } %>
  
  </form>