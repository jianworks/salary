<%@ page contentType="text/html;charset=UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>全民健康保險負擔金額表</title>
<style type="text/css">
<!--
@import url(../css/styles.css);
-->
</style>
</head>

<body leftmargin="20">
  <%    
    int amount = request.getParameter("amount")!=null&&!request.getParameter("amount").equals("")?Integer.parseInt(request.getParameter("amount")):0;
    int[][] health={{1,26400,409,818,1227,1636},
{2,27600,428,856,1284,1712},
{3,28800,447,894,1341,1788},
{4,30300,470,940,1410,1880},
{5,31800,493,986,1479,1972},
{6,33300,516,1032,1548,2064},
{7,34800,540,1080,1620,2160},
{8,36300,563,1126,1689,2252},
{9,38200,592,1184,1776,2368},
{10,40100,622,1244,1866,2488},
{11,42000,651,1302,1953,2604},
{12,43900,681,1362,2043,2724},
{13,45800,710,1420,2130,2840},
{14,48200,748,1496,2244,2992},
{15,50600,785,1570,2355,3140},
{16,53000,822,1644,2466,3288},
{17,55400,859,1718,2577,3436},
{18,57800,896,1792,2688,3584},
{19,60800,943,1886,2829,3772},
{20,63800,990,1980,2970,3960},
{21,66800,1036,2072,3108,4144},
{22,69800,1083,2166,3249,4332},
{23,72800,1129,2258,3387,4516},
{24,76500,1187,2374,3561,4748},
{25,80200,1244,2488,3732,4976},
{26,83900,1301,2602,3903,5204},
{27,87600,1359,2718,4077,5436},
{28,92100,1428,2856,4284,5712},
{29,96600,1498,2996,4494,5992},
{30,101100,1568,3136,4704,6272},
{31,105600,1638,3276,4914,6552},
{32,110100,1708,3416,5124,6832},
{33,115500,1791,3582,5373,7164},
{34,120900,1875,3750,5625,7500},
{35,126300,1959,3918,5877,7836},
{36,131700,2043,4086,6129,8172},
{37,137100,2126,4252,6378,8504},
{38,142500,2210,4420,6630,8840},
{39,147900,2294,4588,6882,9176},
{40,150000,2327,4654,6981,9308},
{41,156400,2426,4852,7278,9704},
{42,162800,2525,5050,7575,10100},
{43,169200,2624,5248,7872,10496},
{44,175600,2724,5448,8172,10896},
{45,182000,2823,5646,8469,11292},
{46,189500,2939,5878,8817,11756},
{47,197000,3055,6110,9165,12220},
{48,204500,3172,6344,9516,12688},
{49,212000,3288,6576,9864,13152},
{50,219500,3404,6808,10212,13616}} ;
  %>
  
  <form method="POST" Name="mainform" Action="" onSubmit='return validate();'>
  <table class=FormBorder width="100%"  border="0" cellpadding="0" cellspacing="0">
    <tr><td colspan=7> 
      <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
        <TBODY>
          <tr>
            <td height="30" class=moduleTitle valign="bottom">全民健康保險負擔金額表</td>
          </tr>
          <TR><TD>
            <DIV class=hline><IMG height=1 src="../images/blank.gif"></DIV>
          </TD></TR>
      </TBODY></TABLE><BR>
    </td></tr>
    <tr><td colspan=7><%=amount>0?" 健保投保金額為" + amount + " 元":"" %>  </td></tr>
    <tr>
      <td colspan="7" class="background_left_menu"><div align="right">
        <table width="100%"  border="0" cellpadding="0" cellspacing="0">
          <TR><TD class=blackLine colSpan=13 height=1><IMG src="../images/blank.gif"></TD></TR>
          <tr class=moduleListTitle height="25">
            <TD class=blackLine width=1 rowspan=3><IMG src="../images/blank.gif"></TD>
            <td rowspan=3><div align="center">投保金額等級</div></td> 
            <TD class=blackLine width=1 rowspan=3><IMG src="../images/blank.gif"></TD>
            <td rowspan=3><div align="center">月投保金額</div></td>
            <TD class=blackLine width=1 rowspan=3><IMG src="../images/blank.gif"></TD>
            <td colspan=7><div align="center">被保險人及眷屬負擔金額</div></td>
            <TD class=blackLine width=1><IMG src="../images/blank.gif"></TD>
          </tr>
          <TR><TD class=blackLine colSpan=8 height=1><IMG src="../images/blank.gif"></TD></TR>
          <tr class=moduleListTitle height="25">
            <td><div align="center">本人</div></td> 
            <TD class=blackLine width=1><IMG src="../images/blank.gif"></TD>
            <td><div align="center">本人+ 1 眷口</div></td>
            <TD class=blackLine width=1><IMG src="../images/blank.gif"></TD>
            <td><div align="center">本人+ 2 眷口</div></td>
            <TD class=blackLine width=1><IMG src="../images/blank.gif"></TD>
            <td><div align="center">本人+ 3 眷口</div></td>
            <TD class=blackLine width=1><IMG src="../images/blank.gif"></TD>
          </tr>
          <TR><TD class=blackLine colSpan=13 height=1><IMG src="../images/blank.gif"></TD></TR>
          <% for (int i=0; i<health.length; i++) { %>
          <tr height="25" <%=amount<=health[i][1]&&(i==0||amount>health[i-1][1])||amount>health[i][1]&&(i==health.length-1)?"bgcolor=#FF6666":""%>>   
            <TD class=blackLine width=1><IMG src="../images/blank.gif"></TD>
            <td><div align="center"><%=health[i][0]%></div></td>
            <TD class=blackLine width=1><IMG src="../images/blank.gif"></TD>
            <td><div align="center"><%=health[i][1]%></div></td>
            <TD class=blackLine width=1><IMG src="../images/blank.gif"></TD>
            <td><div align="center"><%=health[i][2]%></div></td>
            <TD class=blackLine width=1><IMG src="../images/blank.gif"></TD>
            <td><div align="center"><%=health[i][3]%></div></td>
            <TD class=blackLine width=1><IMG src="../images/blank.gif"></TD>
            <td><div align="center"><%=health[i][4]%></div></td>
            <TD class=blackLine width=1><IMG src="../images/blank.gif"></TD>
            <td><div align="center"><%=health[i][5]%></div></td>
            <TD class=blackLine width=1><IMG src="../images/blank.gif"></TD>
          </TR>
          <% } %>         
          <TR><TD class=blackLine colSpan=13 height=1><IMG src="../images/blank.gif"></TD></TR>
        </table>
      </td>
    </tr>
  </table>
</body>
</html>
