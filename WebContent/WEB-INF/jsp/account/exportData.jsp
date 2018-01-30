<%@ page contentType="text/html;charset=UTF-8"%>

<%@ page import="java.util.*" %>
<%@ page import="com.csjian.model.bean.*" %>

<%
	String agentcode = (String)request.getAttribute("agentcode");
	String year = (String)request.getAttribute("year");
	String keyword = (String)request.getAttribute("keyword");
	Vector agents = (Vector)request.getAttribute("agents");
    int pageno = Integer.parseInt((String)request.getAttribute("pageno"));      
    int totalPage = Integer.parseInt((String)request.getAttribute("totalPage"));      
    int count = Integer.parseInt((String)request.getAttribute("count"));      
    int start = Integer.parseInt((String)request.getAttribute("start"));      
    int end = Integer.parseInt((String)request.getAttribute("end"));    
    List companyList = (List)request.getAttribute("companyList");
    int currentYear = (Calendar.getInstance()).get(Calendar.YEAR);
%>
  <SCRIPT LANGUAGE="JavaScript">
  <!--
    function doExport(iregcode) {
      with (document.mainform) {
        mainform.action='<%=response.encodeURL("servlet/exportEmployeeToTxt") %>'
	  	regcode.value = iregcode;
        submit();
	  }
    } 

    function doSearch() {
      mainform.action = '<%=response.encodeURL("account.do?action=export") %>';
	  with (document.mainform) {
	  	pageno.value = "";
        submit();
	  }
    }

    function selPage(ipageno) {
  	  with (document.mainform) {
          pageno.value = ipageno;
          submit();
  	  }
    }

   //-->
  </SCRIPT>

  <form method="POST" Name="mainform" Action="<%=response.encodeURL("account.do?action=export") %>" onSubmit='return validate();'>
  <table width="100%"  border="0" cellpadding="0" cellspacing="0">
    <tr><td colspan=7>
      <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
        <TBODY>
          <tr>
            <td height="30" class=moduleTitle valign="bottom">匯出客戶員工資料</td>
          </tr>
          <TR><TD>
            <DIV class=hline><IMG height=1 src="images/blank.gif"></DIV>
          </TD></TR>
      </TBODY></TABLE><BR>
    </td></tr>
    <tr>
      <td colspan=3 height="20">
        <div align="left">
          <select name="agentcode" class="select">
            <option value="">全部客戶</option>
            <option value="agent" <%=agentcode.equals("agent")?"selected":"" %>>全部帳務人員</option>
            <option value="admin" <%=agentcode.equals("admin")?"selected":"" %>>未分配帳務人員之客戶</option>
            <% for (int i=0; i<agents.size(); i++) { String[] item=(String[])agents.elementAt(i);%>
            <option value="<%=item[0]%>" <%=item[0].equals(agentcode)?"selected":"" %>><%=item[1]+"  負責的客戶"%></option>
            <% } %>
          </select>&nbsp;&nbsp;
          關鍵字：<input name="keyword" value="<%=keyword%>" type="text" class="textfield" size="10" maxlength="10">(可用統一編號、公司名稱查詢)
      	  &nbsp;&nbsp;
      	  年度別：<select name="year" class="select">
      		<% for (int i=2006; i<=currentYear; i++) { %>
          		<option value="<%=i%>" <%=(i+"").equals(year)?"selected":"" %>><%=(i-1911)%></option>
          	<% } %>
          </select>&nbsp;&nbsp;
          <input name="Submit8" type="button" value="查詢" onClick='doSearch();'>
        </div>
      </td>
      <td colspan=4 height="20">
        <div align="right">
          &nbsp;
        </div>
      </td>
    </tr>

    <tr><TD class=listFormHeaderLinks colSpan=7>
      <table width="100%"><TBODY>
      <tr>
        <td>總共&nbsp;&nbsp;<%=count%>&nbsp;&nbsp;筆中的第&nbsp;&nbsp;<%=count>0?start:0%>&nbsp; - &nbsp;<%=end%>&nbsp;&nbsp;筆</td>
        <td align="right">
          <% if (pageno!=1) { %><A href="javascript:selPage('1')" ><img src="images/start.gif" align=absMiddle border=0></a>
          <% } else { %><img src="images/start_disabled.gif" align=absMiddle border=0><% } %>
          <% if (pageno>1) { %><A href="javascript:selPage('<%=(pageno-1)%>')" ><img src="images/previous.gif" align=absMiddle border=0></a>
          <% } else { %><img src="images/previous_disabled.gif" align=absMiddle border=0><% } %>
          <% if (pageno<totalPage) { %><A href="javascript:selPage('<%=(pageno+1)%>')" ><img src="images/next.gif" align=absMiddle border=0></a>
          <% } else { %><img src="images/next_disabled.gif" align=absMiddle border=0><% } %>
          <% if (pageno!=totalPage) { %><A href="javascript:selPage('<%=totalPage%>')" ><img src="images/end.gif" align=absMiddle border=0></a>
          <% } else { %><img src="images/end_disabled.gif" align=absMiddle border=0><% } %>
        </td>
      </tr>
      </TBODY></table>
    </TD></tr>
    <TR><TD class=blackLine colSpan=7 height=1><IMG src="images/blank.gif"></TD></TR>

    <tr>
      <td colspan="7" class="background_left_menu"><div align="right">
        <table width="100%"  border="0" cellpadding="0" cellspacing="0">
          <tr class=moduleListTitle height="25">
            <TD class=blackLine width=1><IMG src="images/blank.gif"></TD>
            <td><div align="center">客戶統編</div></td>
            <TD class=blackLine width=1><IMG src="images/blank.gif"></TD>
            <td><div align="center">客戶公司名稱</div></td>
            <TD class=blackLine width=1><IMG src="images/blank.gif"></TD>
            <td><div align="center">連絡人</div></td>
            <TD class=blackLine width=1><IMG src="images/blank.gif"></TD>
            <td><div align="center">連絡電話</div></td>
            <TD class=blackLine width=1><IMG src="images/blank.gif"></TD>
            <td><div align="center">匯出</div></td>
            <TD class=blackLine width=1><IMG src="images/blank.gif"></TD>
          </tr>
          <TR><TD class=blackLine colSpan=15 height=1><IMG src="images/blank.gif"></TD></TR>
          <% 
            CompanyBean company = null;
            for (int i=0; i<companyList.size(); i++) { 
              company = (CompanyBean)companyList.get(i);
          %>
          <tr <%=(i%2)==0?"class=evenListRow":"class=oddListRow"%> height="25">
            <TD class=blackLine width=1><IMG src="images/blank.gif"></TD>
            <td><div align="center"><%=company.getRegcode()%></div></td>
            <TD class=blackLine width=1><IMG src="images/blank.gif"></TD>
            <td><div align="center"><%=company.getName()%></div></td>
            <TD class=blackLine width=1><IMG src="images/blank.gif"></TD>
            <td><div align="center"><%=company.getContact()%></div></td>
            <TD class=blackLine width=1><IMG src="images/blank.gif"></TD>
            <td><div align="center"><%=company.getPhone()%></div></td>
            <TD class=blackLine width=1><IMG src="images/blank.gif"></TD>
            <td><div align="center"><%=company.getDisable().equals("Y")?"已停用":""%></div></td>
            <TD class=blackLine width=1><IMG src="images/blank.gif"></TD>
            <td><div align="center">
              <input name="Submit8" type="button" value="匯出" onClick='doExport("<%=company.getRegcode()%>");'>
            </div></td>
            <TD class=blackLine width=1><IMG src="images/blank.gif"></TD>
          </tr>
          <% } %>
          <TR><TD class=blackLine colSpan=11 height=1><IMG src="images/blank.gif"></TD></TR>
        </table>
      </td>
    </tr>
  </table>
  <input type=hidden name="pageno" value="<%=pageno%>"/>
  <input type=hidden name="regcode" value=""/>
  </form>
