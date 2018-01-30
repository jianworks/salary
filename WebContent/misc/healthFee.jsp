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
    int[][] health={{1,21009,296,592,888,1184},
    		{2,21900,308,616,924,1232},
    		{3,22800,321,642,963,1284},
    		{4,24000,338,676,1014,1352},
    		{5,25200,355,710,1065,1420},
    		{6,26400,371,742,1113,1484},
    		{7,27600,388,776,1164,1552},
    		{8,28800,405,810,1215,1620},
    		{9,30300,426,852,1278,1704},
    		{10,31800,447,894,1341,1788},
    		{11,33300,469,938,1407,1876},
    		{12,34800,490,980,1470,1960},
    		{13,36300,511,1022,1533,2044},
    		{14,38200,537,1074,1611,2148},
    		{15,40100,564,1128,1692,2256},
    		{16,42000,591,1182,1773,2364},
    		{17,43900,618,1236,1854,2472},
    		{18,45800,644,1288,1932,2576},
    		{19,48200,678,1356,2034,2712},
    		{20,50600,712,1424,2136,2848},
    		{21,53000,746,1492,2238,2984},
    		{22,55400,779,1558,2337,3116},
    		{23,57800,813,1626,2439,3252},
    		{24,60800,855,1710,2565,3420},
    		{25,63800,898,1796,2694,3592},
    		{26,66800,940,1880,2820,3760},
    		{27,69800,982,1964,2946,3928},
    		{28,72800,1024,2048,3072,4096},
    		{29,76500,1076,2152,3228,4304},
    		{30,80200,1128,2256,3384,4512},
    		{31,83900,1180,2360,3540,4720},
    		{32,87600,1233,2466,3699,4932},
    		{33,92100,1296,2592,3888,5184},
    		{34,96600,1359,2718,4077,5436},
    		{35,101100,1422,2844,4266,5688},
    		{36,105600,1486,2972,4458,5944},
    		{37,110100,1549,3098,4647,6196},
    		{38,115500,1625,3250,4875,6500},
    		{39,120900,1701,3402,5103,6804},
    		{40,126300,1777,3554,5331,7108},
    		{41,131700,1853,3706,5559,7412},
    		{42,137100,1929,3858,5787,7716},
    		{43,142500,2005,4010,6015,8020},
    		{44,147900,2081,4162,6243,8324},
    		{45,150000,2111,4222,6333,8444},
    		{46,156400,2201,4402,6603,8804},
    		{47,162800,2291,4582,6873,9164},
    		{48,169200,2381,4762,7143,9524},
    		{49,175600,2471,4942,7413,9884},
    		{50,182000,2561,5122,7683,10244}} ;
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
