<%@ page contentType="text/html;charset=UTF-8"%>

  <SCRIPT LANGUAGE="JavaScript">
  <!--
    function doSubmit() {
      with (document.mainform) {
        if (oldpass.value=="") {
          window.alert("請輸入舊的密碼!");
        } else if (newpass.value=="") {
          window.alert("請輸入更新密碼!");
        } else if (rnewpass.value=="") {
          window.alert("請輸入確認更新密碼!");
        } else if (newpass.value != rnewpass.value) {
          window.alert("二次更新密碼值不同，請重新輸入!");
        } else {
          Submit0.disabled = true;
          submit();
        }
      }
    }
  //-->
  </SCRIPT>

  <form method="POST" Name="mainform" Action="<%=response.encodeURL("home.do?action=doPassword") %>" onSubmit='return validate();'>
  <table width="100%" align=left><tr><td align=left>
     <TABLE class=formOuterBorder cellSpacing=0 cellPadding=0 width="95%" border=0><TBODY><TR><TD>
        <TABLE cellSpacing=1 cellPadding=0 width="100%" border=0>
          <TBODY><TR><TH class=formSecHeader align=left>變更密碼資料</TH></TR></TBODY>
        </TABLE>
        <TABLE cellSpacing=1 cellPadding=2 width="100%" border=0><TBODY>
          <tr>
            <td class=dataLabel width="25%"><div align="right">舊的密碼：</div></td>
            <td colspan="2" align="left"><input name="oldpass" value="" type="password" class="textfield" size="20" maxlength="20"></td>
          </tr>
          <tr>
            <td class=dataLabel width="25%"><div align="right">新的密碼：</div></td>
            <td colspan="2" align="left"><input name="newpass" value="" type="password" class="textfield" size="20" maxlength="20"></td>
          </tr>
          <tr>
            <td class=dataLabel width="25%"><div align="right">確認新的密碼：</div></td>
            <td colspan="2" align="left"><input name="rnewpass" value="" type="password" class="textfield" size="20" maxlength="20"></td>
          </tr>
        </TBODY></TABLE>
      </TD></TR></TBODY></TABLE>
    </td></tr>
    <tr>
      <td colspan=3>
        <div align="right" >
          <input name="Submit0" type="button" value="更新" onClick='doSubmit();'>
        </div>
      </td>
    </tr>
  </table>
  </form>
