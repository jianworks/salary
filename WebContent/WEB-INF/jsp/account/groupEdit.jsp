<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.csjian.model.bean.*" %>

<%
  	GroupBean group = (GroupBean)request.getAttribute("group");
	String[] apnolist = group.getApnolist();
  	List apList = (List)request.getAttribute("apList");
  	String pageno = (String)request.getAttribute("pageno");
%>

  <SCRIPT LANGUAGE="JavaScript">
  <!--
  	function doUpdate() {
    	if (!chkBlank()) {
        } else {
    		mainform.submit();
    	}
    }
    
    function doRemove() {
    	if (window.confirm("確定要刪除此群組 ?")) {
    		mainform.action = "<%=response.encodeURL("account.do?action=removeGroup") %>";
    		mainform.submit();
    	}
    }
    
    function doCancel() {
    	mainform.action = "<%=response.encodeURL("account.do?action=listGroup") %>";
    	mainform.submit();
    }
    
    function chkBlank() {
      with (document.mainform) {
        if (groupno.value == "") {
          window.alert("群組代碼不得為空白!");
          return false;
        } else if (name.value == "") {
          window.alert("群組名稱不得為空白!");
          return false;
        } else {
          return true;
        }
      }
    }

  //-->
  </SCRIPT>

  <form method="POST" Name="mainform" Action="<%=response.encodeURL("account.do?action=updateGroup") %>" onSubmit='return validate();'>
  <table width="100%"  border="0" cellpadding="0" cellspacing="0">
    <tr><td colspan=3>
      <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
        <TBODY>
          <tr>
            <td height="30" class=moduleTitle valign="bottom">群組管理</td>
          </tr>
          <TR><TD>
            <DIV class=hline><IMG height=1 src="../images/blank.gif"></DIV>
          </TD></TR>
      </TBODY></TABLE><BR>
    </td></tr>
    <tr>
      <td height="25" class=dataLabel><div align="left">群組代碼：</div></td>
      <td height="25" colspan="2" align=left><input name="groupno" value="<%=group.getGroupno()%>" type="text" class="textfield" size="10" maxlength="10"></td>
    </tr>
    <tr>
      <td height="25" class=dataLabel><div align="left">群組名稱：</div></td>
      <td height="25" colspan="2" align=left><input name="name" value="<%=group.getName()%>" type="text" class="textfield" size="20" maxlength="10"></td>
    </tr>
    <tr>
      <td height="10">&nbsp;</td>
      <td height="10" colspan="2">&nbsp;</td>
    </tr>
    <tr><td colspan=3>
      <TABLE class=formOuterBorder cellSpacing=0 cellPadding=0 width="100%" border=0><TBODY><TR><TD>
        <TABLE cellSpacing=1 cellPadding=0 width="100%" border=0>
          <TBODY><TR><TH class=formSecHeader align=left>群組權限</TH></TR></TBODY>
        </TABLE>
        <fieldset style="height:300px; overflow-y:scroll">
        <TABLE cellSpacing=1 cellPadding=2 width="100%" border=0><TBODY>
          <%
            boolean isMy = false;
        	String[] ap = null;
            for (int j=0; j<apList.size(); j++) {
              ap = (String[])apList.get(j);
              isMy = false;
              if (apnolist != null) {
                for (int k=0; k<apnolist.length; k++) {
            	  if (ap[0].equals(apnolist[k])) {
            	    isMy = true;
            	  }
                }
              }
          %>
          <tr <%=(j%2)==0?"class=evenListRow":"class=oddListRow"%>>
            <td width="30"><input type="checkbox" name="apnolist" value="<%=ap[0]%>" <%=isMy?"checked":""%>></td>
            <td height="20" align="left"><%=ap[1]%></td>
          </tr>
          <% } %>
        </TABLE>
        </fieldset>
      </TD></TR></TBODY></TABLE>
      </td>
    </tr>
    <tr>
      <td height="10">&nbsp;</td>
      <td height="10" colspan="2">&nbsp;</td>
    </tr>
    <tr>
      <td height="30">&nbsp;</td>
      <td height="30" colspan="2">
        <div align="right">
          <input name="Submit0" type="button" value="儲存" onClick='doUpdate();'>
          <%if (!group.getGroupno().equals("")) {%><input name="Submit1" type="button" value="刪除" onClick='doRemove();'><%}%>
          <input name="Submit2" type="button" value="取消" onClick='doCancel();'>
        </div></td>
    </tr>
  </table>
  <input type=hidden name="pageno" value="<%=pageno%>"/>
  <input type=hidden name="isnew" value="<%=group.getGroupno().equals("")?"Y":"N"%>"/>
  </form>
