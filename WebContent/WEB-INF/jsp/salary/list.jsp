<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.csjian.model.bean.*" %>

<%
	String year = (String)request.getAttribute("year");
	String month = (String)request.getAttribute("month");
    List salaryList = (List)request.getAttribute("salaryList");
    int currentYear = (Calendar.getInstance()).get(Calendar.YEAR);
%>

  <SCRIPT LANGUAGE="JavaScript">
  <!--
    function doSearch() {
    	mainform.submit();
    }
    
    function doEdit(isn) {
      with (document.mainform) {
        employeeno.value = isn;
        mainform.action = '<%=response.encodeURL("salary.do?action=edit") %>';
        submit();
      }
    } 
    
    function doRemove(isn) {      
      if (window.confirm("確定要清除本筆薪資資料 ?")) {
        with (document.mainform) {
          employeeno.value = isn;
          //pageno.value = "";
          mainform.action = '<%=response.encodeURL("salary.do?action=remove") %>';
          submit();
        }
      }  
    }
    
    function batchRemove() {      
      if (window.confirm("確定要清除所選擇的薪資資料 ?")) {
        with (document.mainform) {
          //pageno.value = "";
          mainform.action = '<%=response.encodeURL("salary.do?action=batchRemove") %>';
          submit();
        }
      }  
    }

    function doCopyHistory() {
        with (document.mainform) {
          var returnValue = window.showModalDialog("misc/selectMonth.jsp",'dialogArguments',"dialogHeight: 250px; dialogWidth: 280px; center: yes; scroll: no; status: no" );
          if (returnValue != null) {
			copyFrom.value=returnValue;
			mainform.action = '<%=response.encodeURL("salary.do?action=copyHistory") %>';
            mainform.submit();
          }
        }
      }
    
    function doCopyLastMonth() {
    	with (document.mainform) {
            var lyear = "";
            var imonth = "";
            var tyear = "";
            var tmonth = "";
            tyear = year.value;
            tmonth = month.value;
            if (month.value=="1") {
              lyear = (parseInt(year.value)-1);
              lmonth = "12";
            } else {
              lyear = year.value;
              lmonth = (month.value-1);
            }
            if (window.confirm("確定要將" + lyear + "年" + lmonth + "月" + "的薪資資料複製到" + tyear + "年" + tmonth + "月 ?")) {
              mainform.action = '<%=response.encodeURL("salary.do?action=copyLastMonth") %>';
          	  submit();
            }
        }
    } 
  
   //-->
  </SCRIPT>

  <form method="POST" Name="mainform" Action="<%=response.encodeURL("salary.do?action=list") %>" class = "entry-form" onSubmit='return validate();'>
  <table width="100%"  border="0" cellpadding="0" cellspacing="0">
    <tr><td colspan=7>
      <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
        <TBODY>
          <tr>
            <td height="30" class=moduleTitle valign="bottom">薪資資料建檔(總表)</td>
          </tr>
          <TR><TD>
            <DIV class=hline><IMG height=1 src="images/blank.gif"></DIV>
          </TD></TR>
      </TBODY></TABLE><BR>
    </td></tr>
    <tr><td colspan=3 height="20">
      <div align="left">
        <select name="year" class="select" onChange='doSearch();'>
          <% for (int i=2006; i<=currentYear; i++) { %>
          <option value="<%=i%>" <%=(i+"").equals(year)?"selected":"" %>><%=(i-1911)%></option>
          <% } %>
        </select> &nbsp;&nbsp;年
        &nbsp;&nbsp;&nbsp;&nbsp;
        <select name="month" class="select" onChange='doSearch();'>
          <% for (int i=1; i<=12; i++) { %>
          <option value="<%=i%>" <%=i==Integer.parseInt(month)?"selected":"" %>><%=i%></option>
          <% } %>
        </select> &nbsp;&nbsp;月
      </div>
      </td>
      <td colspan="4">
        <div align="right">
          <input name="Submit0" type="button" class='ui-state-default' value="複製建檔薪資資料" onClick='doCopyHistory();'>
          <input name="Submit1" type="button" class='ui-state-default' value="清除" onClick='batchRemove();'>
        </div></td>
    </tr>
    <tr><TD class=listFormHeaderLinks colSpan=7>
      <table width="100%"><TBODY>
      <tr>
        <td>總共&nbsp;&nbsp;<%=salaryList.size()%>&nbsp;&nbsp;筆</td>
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
            <td><div align="center">員工編號</div></td>
            <TD class=blackLine width=1><IMG src="images/blank.gif"></TD>
            <td><div align="center">姓名</div></td>
            <TD class=blackLine width=1><IMG src="images/blank.gif"></TD>
            <td><div align="center">本月實發金額</div></td>
            <TD class=blackLine width=1><IMG src="images/blank.gif"></TD>
            <td><div align="center">清除|修改或建檔</div></td>
            <TD class=blackLine width=1><IMG src="images/blank.gif"></TD>
          </tr>
          <TR><TD class=blackLine colSpan=11 height=1><IMG src="images/blank.gif"></TD></TR>
          <% 
            for (int i=0; i<salaryList.size(); i++) {
               String[] sitem = (String [])salaryList.get(i);
          %>
          <tr <%=(i%2)==0?"class=evenListRow":"class=oddListRow"%> height="25">
            <TD class=blackLine width=1><IMG src="images/blank.gif"></TD>
            <td><INPUT onclick='toggleSelectAll(this.name,"selectall")' type=checkbox value="<%=sitem[0]%>" name=selected_id></td>
            <TD class=blackLine width=1><IMG src="images/blank.gif"></TD>
            <td><div align="center"><A href="javascript:doEdit('<%=sitem[0]%>')" ><%=sitem[0]%></a></div></td>
            <TD class=blackLine width=1><IMG src="images/blank.gif"></TD>
            <td><div align="center"><A href="javascript:doEdit('<%=sitem[0]%>')" ><%=sitem[1]%></a></div></td>
            <TD class=blackLine width=1><IMG src="images/blank.gif"></TD>
            <td><div align="center"><%=sitem[2].equals("")?"尚未建檔":sitem[2]%></div></td>
            <TD class=blackLine width=1><IMG src="images/blank.gif"></TD>
            <td><div align="center">
              <A href="javascript:doRemove('<%=sitem[0]%>')" >清除</a>|
              <A href="javascript:doEdit('<%=sitem[0]%>')" ><%=sitem[2].equals("")?"建檔":"修改"%></a>
            </div></td>
            <TD class=blackLine width=1><IMG src="images/blank.gif"></TD>
          </tr>
          <% } %>
          <TR><TD class=blackLine colSpan=11 height=1><IMG src="images/blank.gif"></TD></TR>
        </table>
      </td>
    </tr>    
  </table>
  <input type=hidden name="employeeno" id="employeeno" value=""/>
  <input type=hidden name="copyFrom" value=""/>
  </form>
</body>
</html>
