<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%
	String agentcode = (String)request.getAttribute("agentcode");
	Vector agents = (Vector)request.getAttribute("agents");
    int pageno = Integer.parseInt((String)request.getAttribute("pageno"));      
    int totalPage = Integer.parseInt((String)request.getAttribute("totalPage"));      
    int count = Integer.parseInt((String)request.getAttribute("count"));      
    int start = Integer.parseInt((String)request.getAttribute("start"));      
    int end = Integer.parseInt((String)request.getAttribute("end"));    
    String startdate = (String)request.getAttribute("startdate");      
    String enddate = (String)request.getAttribute("enddate");    
    Vector logs = (Vector)request.getAttribute("logs");
%>

  <SCRIPT LANGUAGE="JavaScript">
  <!--
    function selPage(ipageno) {
	  with (document.mainform) {
        pageno.value = ipageno;
        submit();
	  }
    }

    function sDate(eventType) {
      with (document.mainform) {
        var returnValue = window.showModalDialog("misc/calendar.html",'dialogArguments',"dialogHeight: 250px; dialogWidth: 280px; center: yes; scroll: no; status: no" );
        eval(eventType + ".value=returnValue");
      }
    }

    function sAgent() {
	  with (document.mainform) {
	  	pageno.value = "";
        submit();
	  }
    }
   //-->
  </SCRIPT>

  <form method="POST" Name="mainform" Action="<%=response.encodeURL("account.do?action=statistics") %>" onSubmit='return validate();'>
  <table class=FormBorder width="100%"  border="0" cellpadding="0" cellspacing="0">
    <tr><td colspan=5>
      <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
        <TBODY>
          <tr>
            <td height="30" class=moduleTitle valign="bottom">使用者統計表</td>
          </tr>
          <TR><TD>
            <DIV class=hline><IMG height=1 src="../images/blank.gif"></DIV>
          </TD></TR>
      </TBODY></TABLE><BR>
    </td></tr>
    <tr valign=middle>
      <td colspan=3 height="20">
        <div align="left">
          <% if (agents != null){ %>
          <select name="agentcode" class="select" onChange='sAgent();'>
            <option value="">全部客戶</option>
            <option value="admin" <%=agentcode.equals("admin")?"selected":"" %>>未分配帳務人員之客戶</option>
            <% for (int i=0; i<agents.size(); i++) { String[] item=(String[])agents.elementAt(i);%>
            <option value="<%=item[0]%>" <%=item[0].equals(agentcode)?"selected":"" %>><%=item[1]+"  負責的客戶"%></option>
            <% } %>
          </select>
          <% } %>
        </div>
      </td>
      <td colspan=2 height="20">
        <div align="left">
                         統計區間
          <input name="startdate" value="<%=startdate%>" type="text" class="textfield" size="10" maxlength="10" readonly=true>&nbsp;<A href="javascript:sDate('startdate')"><IMG src="images/calendar.gif" border="0"></A>&nbsp;&nbsp;&nbsp;&nbsp;至&nbsp;&nbsp;&nbsp;&nbsp;
          <input name="enddate" value="<%=enddate%>" type="text" class="textfield" size="10" maxlength="10" readonly=true>&nbsp;<A href="javascript:sDate('enddate')"><IMG src="images/calendar.gif" border="0"></A>
          <input name="Submit3" type="submit" value="查詢">
        </div>
      </td>
    </tr>

    <tr><TD class=listFormHeaderLinks colSpan=5>
      <table width="100%"><TBODY>
      <tr>
        <td>總共&nbsp;&nbsp;<%=logs.size()%>&nbsp;&nbsp;筆中的第&nbsp;&nbsp;<%=start+1%>&nbsp; - &nbsp;<%=end%>&nbsp;&nbsp;筆</td>
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
    <TR><TD class=blackLine colSpan=5 height=1><IMG src="images/blank.gif"></TD></TR>

    <tr>
      <td colspan="5" class="background_left_menu"><div align="right">
        <table width="100%"  border="0" cellpadding="0" cellspacing="0">
          <tr class=moduleListTitle height="25">
            <TD class=blackLine width=1><IMG src="images/blank.gif"></TD>
            <td><div align="center">使用者名稱</div></td>
            <TD class=blackLine width=1><IMG src="images/blank.gif"></TD>
            <td><div align="center">最後登入使用日期</div></td>
            <TD class=blackLine width=1><IMG src="images/blank.gif"></TD>
          </tr>
          <TR><TD class=blackLine colSpan=5 height=1><IMG src="images/blank.gif"></TD></TR>
          <% for (int i=start; i<end; i++) { String[] log = (String[])logs.elementAt(i);%>
          <tr <%=(i%2)==0?"class=evenListRow":"class=oddListRow"%> height="25">
            <TD class=blackLine width=1><IMG src="images/blank.gif"></TD>
            <td><div align="center"><%=log[0]%></div></td>
            <TD class=blackLine width=1><IMG src="images/blank.gif"></TD>
            <td><div align="center"><%=log[1]%></div></td>
            <TD class=blackLine width=1><IMG src="images/blank.gif"></TD>
          </tr>
          <% } %>
          <TR><TD class=blackLine colSpan=5 height=1><IMG src="images/blank.gif"></TD></TR>
        </table>
      </td>
    </tr>
  </table>
  <input type=hidden name="pageno" value="<%=pageno%>"/>
  </form>
