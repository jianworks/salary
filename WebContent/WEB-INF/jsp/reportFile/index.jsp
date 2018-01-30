<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.csjian.model.bean.*" %>

<%
    int pageno = Integer.parseInt((String)request.getAttribute("pageno"));      
    int totalPage = Integer.parseInt((String)request.getAttribute("totalPage"));      
    int count = Integer.parseInt((String)request.getAttribute("count"));      
    int start = Integer.parseInt((String)request.getAttribute("start"));      
    int end = Integer.parseInt((String)request.getAttribute("end"));    
    List reportFileList = (List)request.getAttribute("reportFileList");
%>

<SCRIPT LANGUAGE="JavaScript">
	function doDownload(seqNo, file) {
		with (document.mainform) {
			location.href = "servlet/DownloadReportFile?seqNo=" + seqNo + "&fileName=" + file;
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

 
  <form method="POST" Name="mainform" Action="<%=response.encodeURL("reportFile.do?action=list") %>" class = "entry-form" onSubmit='return validate();'>
  <table width="100%"  border="0" cellpadding="0" cellspacing="0">
    <tr><td colspan=7>
      <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
        <TBODY>
          <tr>
            <td height="30" class=moduleTitle valign="bottom">報表檔案下載</td>
          </tr>
          <TR><TD>
            <DIV class=hline><IMG height=1 src="images/blank.gif"></DIV>
          </TD></TR>
      </TBODY></TABLE><BR>
    </td></tr>    
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
            <td><div align="center">報表名稱</div></td>
            <TD class=blackLine width=1><IMG src="images/blank.gif"></TD>
            <td><div align="center">報表日期</div></td>   
            <TD class=blackLine width=1><IMG src="images/blank.gif"></TD>
            <td><div align="center">&nbsp;</div></td>
            <TD class=blackLine width=1><IMG src="images/blank.gif"></TD>
          </tr>
          <TR><TD class=blackLine colSpan=7 height=1><IMG src="images/blank.gif"></TD></TR>
          <% 
            ReportFileBean reportFile = null;
            for (int i=0; i<reportFileList.size(); i++) {
            	reportFile = (ReportFileBean)reportFileList.get(i);
          %>
          <tr <%=(i%2)==0?"class=evenListRow":"class=oddListRow"%> height="25">
            <TD class=blackLine width=1><IMG src="images/blank.gif"></TD>
            <td><div align="center"><%=reportFile.getTitle()%></div></td>
            <TD class=blackLine width=1><IMG src="images/blank.gif"></TD>
            <td><div align="center"><%=reportFile.getReportDate()%></div></td>
            <TD class=blackLine width=1><IMG src="images/blank.gif"></TD>
            <td><div align="center">
              <A href="javascript:doDownload('<%=reportFile.getSeqNo()%>', '<%=reportFile.getFileName()%>')" >下載</a>
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
  </form>
