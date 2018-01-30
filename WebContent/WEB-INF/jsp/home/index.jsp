<%@ page contentType="text/html;charset=UTF-8"%>

  <SCRIPT LANGUAGE="JavaScript">
  <!--
      function ShowQA(qano) {
      var windowName = "popupWindow";
      var winWidth = 640;
      //the center properties
      var winLeft = (screen.width - winWidth)/2;
      newWindow = window.open('QA/playswf.jsp?id='+qano,windowName,'height=480,width=640,left=' + winLeft + ',top=50, resizable=no,scrollbars=no,status=no,titlebar=no');
    }
   //-->
  </SCRIPT>
 
  <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
    <TBODY>
      <tr>
        <td height="30" class=moduleTitle valign="bottom">網站使用教學</td>
      </tr>
      <TR><TD>
        <DIV class=hline><IMG height=1 src="/images/blank.gif"></DIV>
      </TD></TR>
  </TBODY></TABLE><BR>
  <table id="QA" class=QA width="95%" border="0" cellpadding="0" cellspacing="0">
  	<tr><td>&nbsp;&nbsp;</td></tr>
  	<tr><td>&nbsp;&nbsp;<a href="/doc/menu.doc" class=moduleTitle >二代健保系統操作說明書下載</a></td></tr>
    <tr><td>&nbsp;&nbsp;</td></tr>
    <tr height="25"><td>&nbsp;&nbsp;1. <a href="#" onclick="ShowQA('1');">如何變更登入密碼 ?</a></td></tr>
    <tr height="25"><td>&nbsp;&nbsp;2. <a href="#" onclick="ShowQA('2');">如何增加一筆新的員工資料 ?</a></td></tr>
    <tr height="25"><td>&nbsp;&nbsp;3. <a href="#" onclick="ShowQA('3');">員工離職要如何設定 ?</a></td></tr>
    <tr height="25"><td>&nbsp;&nbsp;4. <a href="#" onclick="ShowQA('4');">員工一些薪資項目的金額不太會改變，有什麼方式可以在輸入每月薪資時自動帶出，而不用重覆輸入 ?</a></td></tr>
    <tr height="25"><td>&nbsp;&nbsp;5. <a href="#" onclick="ShowQA('5');">在輸入薪資資料時，發現有些薪資加項沒有要如何解決 ?</a></td></tr>
    <tr height="25"><td>&nbsp;&nbsp;6. <a href="#" onclick="ShowQA('6');">在輸入薪資資料時，發現有些薪資減項沒有要如何解決 ?</a></td></tr>
    <tr height="25"><td>&nbsp;&nbsp;7. <a href="#" onclick="ShowQA('7');">如何輸入每月的薪資資料 ?</a></td></tr>
    <tr height="25"><td>&nbsp;&nbsp;8. <a href="#" onclick="ShowQA('8');">這個月的薪資資料和上個月的相似，可不可以直接拿上個月的資料來套用 ?</a></td></tr>
    <tr height="25"><td>&nbsp;&nbsp;9. <a href="#" onclick="window.open('QA/9.htm','popupWindow','height=240,width=300,left=100, top=50, resizable=no,scrollbars=no,status=no,titlebar=no');">在報表列印的地方都有 "產生PDF報表"的按鈕，這是什麼 ? 要如何讀取 ?</a></td></tr>
  </table>
