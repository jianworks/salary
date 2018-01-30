<%@ page contentType="text/html;charset=UTF-8"%>

<%@ page import="java.util.*" %>
<%@ page import="com.csjian.model.bean.*" %>

<%
	String agentcode = (String)request.getAttribute("agentcode");
	String keyword = (String)request.getAttribute("keyword");
	Vector agents = (Vector)request.getAttribute("agents");
    int pageno = Integer.parseInt((String)request.getAttribute("pageno"));      
    int totalPage = Integer.parseInt((String)request.getAttribute("totalPage"));      
    int count = Integer.parseInt((String)request.getAttribute("count"));      
    int start = Integer.parseInt((String)request.getAttribute("start"));      
    int end = Integer.parseInt((String)request.getAttribute("end"));    
    List companyList = (List)request.getAttribute("companyList");
%>
  <SCRIPT LANGUAGE="JavaScript">
  <!--
    function doAdd() {
      with (document.mainform) {
        mainform.action='<%=response.encodeURL("account.do?action=edit") %>'
        submit();
      }
    } 
    
    function doEdit(isn) {
      with (document.mainform) {
        mainform.action = '<%=response.encodeURL("account.do?action=edit") %>&regcode=' + isn;
        submit();
      }
    } 
    
    function doRemove(isn) {      
      if (window.confirm("確定要刪除選擇的項目 ?")) {
        with (document.mainform) {
          mainform.action = '<%=response.encodeURL("account.do?action=remove") %>&regcode=' + isn;
          submit();
        }
      }  
    }
    
    function doRemove() {      
      if (window.confirm("確定要刪除選擇的項目 ?")) {
        with (document.mainform) {
          mainform.action = '<%=response.encodeURL("account.do?action=batchRemove") %>';
          submit();
        }
      }  
    }
    
    function selPage(ipageno) {
	  with (document.mainform) {
        pageno.value = ipageno;
        submit();
	  }
    }

    function doSearch() {
	  with (document.mainform) {
	  	pageno.value = "";
        submit();
	  }
    }

   //-->
  </SCRIPT>

  <form method="POST" Name="mainform" Action="<%=response.encodeURL("account.do?action=list") %>" onSubmit='return validate();'>
  <table width="100%"  border="0" cellpadding="0" cellspacing="0">
    <tr><td colspan=7>
      <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
        <TBODY>
          <tr>
            <td height="30" class=moduleTitle valign="bottom">客戶資料管理(總表)</td>
          </tr>
          <TR><TD>
            <DIV class=hline><IMG height=1 src="images/blank.gif"></DIV>
          </TD></TR>
      </TBODY></TABLE><BR>
    </td></tr>
    <tr>
      <td colspan=3 height="20">
        <div align="left">
          <% if (agents != null){ %>	
          <select name="agentcode" class="select">
            <option value="">全部客戶</option>
            <option value="agent" <%=agentcode.equals("agent")?"selected":"" %>>全部帳務人員</option>
            <option value="admin" <%=agentcode.equals("admin")?"selected":"" %>>未分配帳務人員之客戶</option>
            <% for (int i=0; i<agents.size(); i++) { String[] item=(String[])agents.elementAt(i);%>
            <option value="<%=item[0]%>" <%=item[0].equals(agentcode)?"selected":"" %>><%=item[1]+"  負責的客戶"%></option>
            <% } %>
          </select>&nbsp;&nbsp;
           <% } %>
          關鍵字：<input name="keyword" value="<%=keyword%>" type="text" class="textfield" size="10" maxlength="10">(可用統一編號、公司名稱查詢)
      	  &nbsp;&nbsp;<input name="Submit8" type="button" value="查詢" onClick='doSearch();'>
        </div>	  
      </td>
      <td colspan=4 height="20">
        <div align="right">
          <input name="Submit3" type="button" value="刪除" onClick='doRemoves();'>
          <input name="Submit2" type="button" value="新增" onClick='doAdd();'>
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
            <td width="10"><INPUT onclick='toggleSelect(this.checked,"selected_id")' type=checkbox name=selectall></td>
            <TD class=blackLine width=1><IMG src="images/blank.gif"></TD>
            <td><div align="center">客戶統編</div></td>
            <TD class=blackLine width=1><IMG src="images/blank.gif"></TD>
            <td><div align="center">客戶公司名稱</div></td>
            <TD class=blackLine width=1><IMG src="images/blank.gif"></TD>
            <td><div align="center">連絡人</div></td>
            <TD class=blackLine width=1><IMG src="images/blank.gif"></TD>
            <td><div align="center">連絡電話</div></td>
            <TD class=blackLine width=1><IMG src="images/blank.gif"></TD>
            <td><div align="center">是否停用</div></td>
            <TD class=blackLine width=1><IMG src="images/blank.gif"></TD>
            <td><div align="center">刪除|修改</div></td>
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
            <td><INPUT onclick='toggleSelectAll(this.name,"selectall")' type=checkbox value="<%=company.getRegcode()%>" name=selected_id></td>
            <TD class=blackLine width=1><IMG src="images/blank.gif"></TD>
            <td><div align="center"><a href="javascript:doEdit('<%=company.getRegcode()%>')"><%=company.getRegcode()%></a></div></td>
            <TD class=blackLine width=1><IMG src="images/blank.gif"></TD>
            <td><div align="center"><a href="javascript:doEdit('<%=company.getRegcode()%>')"><%=company.getName()%></a></div></td>
            <TD class=blackLine width=1><IMG src="images/blank.gif"></TD>
            <td><div align="center"><%=company.getContact()%></div></td>
            <TD class=blackLine width=1><IMG src="images/blank.gif"></TD>
            <td><div align="center"><%=company.getPhone()%></div></td>
            <TD class=blackLine width=1><IMG src="images/blank.gif"></TD>
            <td><div align="center"><%=company.getDisable().equals("Y")?"已停用":""%></div></td>
            <TD class=blackLine width=1><IMG src="images/blank.gif"></TD>
            <td><div align="center">
              <A href="javascript:doRemove('<%=company.getRegcode()%>')" >刪除</a>|
              <A href="javascript:doEdit('<%=company.getRegcode()%>')" >修改</a>
            </div></td>
            <TD class=blackLine width=1><IMG src="images/blank.gif"></TD>
          </tr>
          <% } %>
          <TR><TD class=blackLine colSpan=15 height=1><IMG src="images/blank.gif"></TD></TR>
        </table>
      </td>
    </tr>
  </table>
  <input type=hidden name="pageno" value="<%=pageno%>"/>
  </form>
