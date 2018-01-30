<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.csjian.model.bean.*" %>

<%
    int pageno = Integer.parseInt((String)request.getAttribute("pageno"));      
    int totalPage = Integer.parseInt((String)request.getAttribute("totalPage"));      
    int count = Integer.parseInt((String)request.getAttribute("count"));      
    int start = Integer.parseInt((String)request.getAttribute("start"));      
    int end = Integer.parseInt((String)request.getAttribute("end"));    
    GroupBean[] groupList = (GroupBean[])request.getAttribute("groupList");
%>


<SCRIPT LANGUAGE="JavaScript">
  <!--
    function doAdd() {
      with (document.mainform) {
        mainform.action='<%=response.encodeURL("account.do?action=editGroup") %>'
        submit();
      }
    } 
    
    function doEdit(isn) {
      with (document.mainform) {
        mainform.action = '<%=response.encodeURL("account.do?action=editGroup") %>' + '&groupno=' + isn;
        submit();
      }
    } 
    
    function doRemove(isn) {      
      if (window.confirm("確定要刪除選擇的項目 ?")) {
        with (document.mainform) {
          mainform.action = '<%=response.encodeURL("account.do?action=removeGroup") %>' + '&groupno=' + isn;
          submit();
        }
      }  
    }
    
    function doRemove() {      
      if (window.confirm("確定要刪除選擇的項目 ?")) {
        with (document.mainform) {
          mainform.action = '<%=response.encodeURL("account.do?action=batchRemoveGroup") %>';
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

   //-->
  </SCRIPT>

  <form method="POST" Name="mainform" Action="<%=response.encodeURL("account.do?action=listGroup") %>" onSubmit='return validate();'>
  <table width="100%"  border="0" cellpadding="0" cellspacing="0">
    <tr><td colspan=7>
      <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
        <TBODY>
          <tr>
            <td height="30" class=moduleTitle valign="bottom">群組管理(總表)</td>
          </tr>
          <TR><TD>
            <DIV class=hline><IMG height=1 src="images/blank.gif"></DIV>
          </TD></TR>
      </TBODY></TABLE><BR>
    </td></tr>
    <tr><td colspan=7 height="20">
      <div align="right">
        <input name="Submit3" type="button" value="刪除" onClick='doRemove();'>
        <input name="Submit2" type="button" value="新增" onClick='doAdd();'>
      </div>
    </td></tr>

    <tr><TD class=listFormHeaderLinks colSpan=7>
      <table width="100%"><TBODY>
      <tr>
        <td>總共&nbsp;&nbsp;<%=groupList.length%>&nbsp;&nbsp;筆中的第&nbsp;&nbsp;<%=count>0?start:0%>&nbsp; - &nbsp;<%=end%>&nbsp;&nbsp;筆</td>
        <td align="right">
          <% if (pageno!=1) { %><a href="javascript:selPage('1')"><img src="images/start.gif" align=absMiddle border=0></a>
          <% } else { %><img src="images/start_disabled.gif" align=absMiddle border=0><% } %>
          <% if (pageno>1) { %><a href="javascript:selPage('<%=(pageno-1)%>')"><img src="images/previous.gif" align=absMiddle border=0></a>
          <% } else { %><img src="images/previous_disabled.gif" align=absMiddle border=0><% } %>
          <% if (pageno<totalPage) { %><a href="javascript:selPage('<%=(pageno+1)%>')" ><img src="images/next.gif" align=absMiddle border=0></a>
          <% } else { %><img src="images/next_disabled.gif" align=absMiddle border=0><% } %>
          <% if (pageno!=totalPage) { %><a href="javascript:selPage('<%=totalPage%>')" ><img src="images/end.gif" align=absMiddle border=0></a>
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
            <td><div align="center">群組名稱</div></td>
            <TD class=blackLine width=1><IMG src="images/blank.gif"></TD>
            <td><div align="center">刪除|修改</div></td>
            <TD class=blackLine width=1><IMG src="images/blank.gif"></TD>
          </tr>
          <TR><TD class=blackLine colSpan=7 height=1><IMG src="images/blank.gif"></TD></TR>
          <% for (int i=0; i<groupList.length; i++) { %>
          <tr <%=(i%2)==0?"class=evenListRow":"class=oddListRow"%> height="25">
            <TD class=blackLine width=1><IMG src="images/blank.gif"></TD>
            <td><INPUT onclick='toggleSelectAll(this.name,"selectall")' type=checkbox value="<%=groupList[i].getGroupno()%>" name=selected_id></td>
            <TD class=blackLine width=1><IMG src="images/blank.gif"></TD>
            <td><div align="center"><a href="javascript:doEdit('<%=groupList[i].getGroupno()%>')" ><%=groupList[i].getName()%></a></div></td>
            <TD class=blackLine width=1><IMG src="images/blank.gif"></TD>
            <td><div align="center">
              <A href="javascript:doRemove('<%=groupList[i].getGroupno()%>')')">刪除</a>|
              <a href="javascript:doEdit('<%=groupList[i].getGroupno()%>')" >修改</a>
            </div></td>
            <TD class=blackLine width=1><IMG src="images/blank.gif"></TD>
          </tr>
          <% } %>
          <TR><TD class=blackLine colSpan=7 height=1><IMG src="images/blank.gif"></TD></TR>
        </table>
      </td>
    </tr>
  </table>
  <input type=hidden name="pageno" value="<%=pageno%>"/>

