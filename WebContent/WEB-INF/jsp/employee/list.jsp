<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.csjian.model.bean.*" %>

<%
	String keyword = (String)request.getAttribute("keyword");
	String year = (String)request.getAttribute("year");
	String resign = (String)request.getAttribute("resign");
    int pageno = Integer.parseInt((String)request.getAttribute("pageno"));      
    int totalPage = Integer.parseInt((String)request.getAttribute("totalPage"));      
    int count = Integer.parseInt((String)request.getAttribute("count"));      
    int start = Integer.parseInt((String)request.getAttribute("start"));      
    int end = Integer.parseInt((String)request.getAttribute("end"));    
    List employeeList = (List)request.getAttribute("employeeList");
    int currentYear = (Calendar.getInstance()).get(Calendar.YEAR);
%>

<script src="js/jquery.upload-1.0.2.min.js" type=text/javascript></script>
<script type="text/javascript">
  <!--
  $(document).ready(function () {
		$("#importFileDlg").dialog({
	        modal: true,
	        autoOpen: false,
	        width: 1000,
	        buttons: {
	        	"確定": function () {
	        		$('#uploadFile').upload('<%=response.encodeURL("employee.do?action=importFile") %>', { year: $('#year').val()}, function(result) {		
	        			if (result.status == 'OK') {
	        				alert("共匯入 " + result.count + "筆資料!");
	        				$("#importFileDlg").dialog("close");
	        			} else {
	        				alert("匯入發生錯誤，請檢查檔案是否正確 ! ");
	        			}
	                }, 'json');        			                
	            },
	            "關閉": function () {
	                $(this).dialog("close");
	            }	            
	        },
	        close: function () {
            	with (document.mainform) {
                    mainform.action='<%=response.encodeURL("employee.do?action=list") %>';
                    submit();
                  }
			}
	    });
		
		$( "#dialog-confirm" ).dialog({
		      resizable: false,
		      height: "auto",
		      width: 400,
		      modal: true,
		      autoOpen: false,
		      buttons: {
		        "確定": function() {
		        	with (document.mainform) {
		        		overwrite.value = $('input[name*=write]:checked').val();
		            	pageno.value = "";
		          		mainform.action = '<%=response.encodeURL("employee.do?action=transfer") %>';
		          		submit();
		        	}
	          		$( this ).dialog( "close" );
		        },
		        "取消": function() {
		          $( this ).dialog( "close" );
		        }
		      }
		    });
  });
  
    function doAdd() {
      with (document.mainform) {
        mainform.action='<%=response.encodeURL("employee.do?action=edit") %>';
        submit();
      }
    } 
    
    function doEdit(isn) {
      with (document.mainform) {
        employeeno.value = isn;
        mainform.action = '<%=response.encodeURL("employee.do?action=edit") %>';
        submit();
      }
    } 
    
    function doEditBsalary(isn) {
      with (document.mainform) {
        employeeno.value = isn;
        mainform.action = '<%=response.encodeURL("employee.do?action=editBsalary") %>';
        submit();
      }
    } 
    
    function doRemove(isn) {   
      if (window.confirm("確定要刪除選擇的員工 ?")) {
        with (document.mainform) {
          employeeno.value = isn;
          pageno.value = "";
          mainform.action = '<%=response.encodeURL("employee.do?action=remove") %>';
          submit();
        }
      }  
    }
    
    function doRemove() {      
      if (window.confirm("確定要刪除選擇的員工 ?")) {
        with (document.mainform) {
          pageno.value = "";
          mainform.action = '<%=response.encodeURL("employee.do?action=batchRemove") %>';
          submit();
        }
      }  
    }
    
    function doTransfer() {   
    	$("#dialog-confirm").dialog("open");		
    }
    

    function doSearch() {
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

    function doImport() {
    	$("#importFileDlg").dialog("open");		
    }
   //-->
</script>

 
  <form method="POST" Name="mainform" Action="<%=response.encodeURL("employee.do?action=list") %>" onSubmit='return validate();' class = "entry-form">
  <table width="100%"  border="0" cellpadding="0" cellspacing="0">
    <tr><td colspan=7>
      <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
        <TBODY>
          <tr>
            <td height="30" class=moduleTitle valign="bottom">員工基本資料管理(總表)</td>
          </tr>
          <TR><TD>
            <DIV class=hline><IMG height=1 src="images/blank.gif"></DIV>
          </TD></TR>
      </TBODY></TABLE><BR>
    </td></tr>
    <tr>
      <td colspan=7 height="20">
        <div align="left">
          年度別：<select name="year" id="year" class="select" onChange='doSearch();'>
            <% for (int i=2006; i<=currentYear; i++) { %>
              <option value="<%=i%>" <%=(i+"").equals(year)?"selected":"" %>><%=(i-1911)%></option>
            <% } %>
          </select>&nbsp;年 &nbsp;&nbsp;         
          在職或離職：<select name="resign" class="select">
            <option value="">全部</option>
            <option value="N" <%=resign.equals("N")?"selected":"" %>>在職</option>
            <option value="Y" <%=resign.equals("Y")?"selected":"" %>>離職</option>
          </select>&nbsp;&nbsp;
          關鍵字：<input name="keyword" value="<%=keyword%>" type="text" class="textfield" size="10" maxlength="10">(可用員工姓名、身份證號碼查詢)
      	  &nbsp;&nbsp;<input name="Submit8" type="button" class='ui-state-default' value="查詢" onClick='doSearch();'>     
        </div>
      </td>
   </tr>
   <tr>   
      <td colspan=7 height="20">
        <div align="right">        
          <% /* if(year.equals(currentYear + "")) { */ %><input name="Submit0" type="button" class='ui-state-default' value="結轉上一年度仍在職員工資料至目前所選定的年度" onClick='doTransfer();'><% /* } */ %>
          &nbsp;&nbsp;<input type="button" class='ui-state-default' value="匯入文中員工資料檔" onClick='doImport();'>  
          <input name="Submit3" type="button" value="刪除" class='ui-state-default' onClick='doRemove();'>
          <input name="Submit2" type="button" value="新增" class='ui-state-default' onClick='doAdd();'>
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
            <td><div align="center">員工編號</div></td>
            <TD class=blackLine width=1><IMG src="images/blank.gif"></TD>
            <td><div align="center">職稱</div></td>
            <TD class=blackLine width=1><IMG src="images/blank.gif"></TD>
            <td><div align="center">姓名</div></td>
            <TD class=blackLine width=1><IMG src="images/blank.gif"></TD>
            <td><div align="center">統一證號</div></td>
            <TD class=blackLine width=1><IMG src="images/blank.gif"></TD>
            <td><div align="center">住址</div></td>
            <TD class=blackLine width=1><IMG src="images/blank.gif"></TD>
            <td><div align="center">銀行帳號</div></td>
            <TD class=blackLine width=1><IMG src="images/blank.gif"></TD>
            <td><div align="center">是否離職</div></td>
            <TD class=blackLine width=1><IMG src="images/blank.gif"></TD>
            <td><div align="center">修改|修改薪資資料</div></td>
            <TD class=blackLine width=1><IMG src="images/blank.gif"></TD>
          </tr>
          <TR><TD class=blackLine colSpan=21 height=1><IMG src="images/blank.gif"></TD></TR>
          <% 
            EmployeeBean employee = null;
            for (int i=0; i<employeeList.size(); i++) {
               employee = (EmployeeBean)employeeList.get(i);
          %>
          <tr <%=(i%2)==0?"class=evenListRow":"class=oddListRow"%> height="25">
            <TD class=blackLine width=1><IMG src="images/blank.gif"></TD>
            <td><INPUT onclick='toggleSelectAll(this.name,"selectall")' type=checkbox value="<%=employee.getEmployeeno()%>" name=selected_id></td>
            <TD class=blackLine width=1><IMG src="images/blank.gif"></TD>
            <td><div align="center"><A href="javascript:doEdit('<%=employee.getEmployeeno()%>')" ><%=employee.getEmployeeno()%></a></div></td>
            <TD class=blackLine width=1><IMG src="images/blank.gif"></TD>
            <td><div align="center"><%=employee.getTitle()!=null&&!employee.getTitle().equals("")?employee.getTitle():""%></div></td>
            <TD class=blackLine width=1><IMG src="images/blank.gif"></TD>
            <td><div align="center"><A href="javascript:doEdit('<%=employee.getEmployeeno()%>')" ><%=employee.getName()%></a></div></td>
            <TD class=blackLine width=1><IMG src="images/blank.gif"></TD>
            <td><div align="center"><%=employee.getUnicode()%></div></td>
            <TD class=blackLine width=1><IMG src="images/blank.gif"></TD>
            <td><div align="center"><%=employee.getAddress()!=null&&!employee.getAddress().equals("")?employee.getAddress():""%></div></td>
            <TD class=blackLine width=1><IMG src="images/blank.gif"></TD>
            <td><div align="center"><%=employee.getAccountno()!=null&&!employee.getAccountno().equals("")?employee.getAccountno():""%></div></td>
            <TD class=blackLine width=1><IMG src="images/blank.gif"></TD>
            <td><div align="center"><%=employee.getIsresign().equals("Y")?"已離職":"否"%></div></td>
            <TD class=blackLine width=1><IMG src="images/blank.gif"></TD>
            <td><div align="center">
              <!--<A href="javascript:doRemove('<%=employee.getEmployeeno()%>')" >刪除</a>|
              --><A href="javascript:doEdit('<%=employee.getEmployeeno()%>')" >修改</a>|
              <A href="javascript:doEditBsalary('<%=employee.getEmployeeno()%>')" >修改薪資資料</a>
            </div></td>
            <TD class=blackLine width=1><IMG src="images/blank.gif"></TD>
          </tr>
          <% } %>
          <TR><TD class=blackLine colSpan=19 height=1><IMG src="images/blank.gif"></TD></TR>
        </table>
      </td>
    </tr>
  </table>
  <input type=hidden name="pageno" value="<%=pageno%>"/>
  <input type="hidden" name="employeeno" value="" />
  <input type="hidden" name="overwrite" value="N" />
  </form>
  
<div id="importFileDlg" title="選擇匯入檔案">
	選擇檔案：<input type="file" id="uploadFile" name="uploadFile"/>   	
</div>

<div id="dialog-confirm" title="覆蓋員工資料確認?">
  <p><span class="ui-icon ui-icon-alert" style="float:left; margin:12px 12px 20px 0;"></span>是否要覆蓋目前所選定的年度已建檔之員工資料 ?</p>
  	<input name="write" type="radio" value="Y" checked>是
    <input name="write" type="radio" value="N">否
</div>
