<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.csjian.model.bean.*"%>

<%
	FilterBean filter = (FilterBean)request.getAttribute("filter");
%>

<script type="text/javascript">	
	$(document).ready(function () {
    	$("#tblList").dataTable({
    		"bProcessing": true,
    		"bSort": false,
			"sPaginationType": "full_numbers",
			"bLengthChange": false,
			"bFilter": false,
			"bJQueryUI": true,
			"iDisplayLength": 10,
			"oLanguage": {
			    "sLengthMenu": "每頁 _MENU_ 筆資料",
			    "sZeroRecords": "",
			    "sInfo": "顯示 _TOTAL_ 筆資料中的第 _START_ 至 _END_ 筆",
			    "sInfoEmpty": "顯示第 0 至 0 資料，總共 0 資料",
			    "sInfoFiltered": "(從 _MAX_ 筆資料中篩選)",
			    "sSearch": "篩選 :",
			    "oPaginate": {
			        "sFirst": "第一頁",
			        "sPrevious": "上一頁",
			        "sNext": "下一頁",
			        "sLast": "最後一頁"
			    }
			}
    	});    	
    	doSearch();
    });
	
	function doPrint() {
		var err = "";
		if ($('#fromYear')==''||$('#fromMonth')==''||$('#toMonth')=='')
			err += "請輸入給付年月!\n";
		if (err != "") {
	        alert(err);
	    } else {
			window.location.href='<%=response.encodeURL("insuranceAddOn.do?action=doPrintM0203") %>' + "&fromYear=" + $('#fromYear').val() + "&fromMonth=" + $('#fromMonth').val() + "&toMonth=" + $('#toMonth').val();
	    }
	}
	
	function doCreateOutFile() {
		var err = "";
		if ($('#fromYear')==''||$('#fromMonth')==''||$('#toMonth')=='')
			err += "請輸入給付年月!\n";
		if (err != "") {
	        alert(err);
	    } else {
			window.location.href='<%=response.encodeURL("insuranceAddOn.do?action=createOutFile") %>' + "&fromYear=" + $('#fromYear').val() + "&fromMonth=" + $('#fromMonth').val() + "&toMonth=" + $('#toMonth').val();
	    }
	}
	
	function doSearch() {
		var err = "";
		if ($('#fromYear')==''||$('#fromMonth')==''||$('#toMonth')=='')
			err += "請輸入給付年月!\n";
		if (err != "") {
	        alert(err);
	    } else {
			$.postJSON("insuranceAddOn.do?action=calcM0203Data", { fromYear: $('#fromYear').val(), fromMonth: $('#fromMonth').val(), toMonth: $('#toMonth').val() }, function (addOnDeclareData) {
			 	$("#count62").html(addOnDeclareData.count62);
			 	$("#incomeAmount62").html(addOnDeclareData.incomeAmount62);
			 	$("#insuranceAddOnFee62").html(addOnDeclareData.insuranceAddOnFee62);
			 	$("#count63").html(addOnDeclareData.count63);
			 	$("#incomeAmount63").html(addOnDeclareData.incomeAmount63);
			 	$("#insuranceAddOnFee63").html(addOnDeclareData.insuranceAddOnFee63);
			 	$("#count65").html(addOnDeclareData.count65);
			 	$("#incomeAmount65").html(addOnDeclareData.incomeAmount65);
			 	$("#insuranceAddOnFee65").html(addOnDeclareData.insuranceAddOnFee65);
			 	$("#count66").html(addOnDeclareData.count66);
			 	$("#incomeAmount66").html(addOnDeclareData.incomeAmount66);
			 	$("#insuranceAddOnFee66").html(addOnDeclareData.insuranceAddOnFee66);
			 	$("#count67").html(addOnDeclareData.count67);
			 	$("#incomeAmount67").html(addOnDeclareData.incomeAmount67);
			 	$("#insuranceAddOnFee67").html(addOnDeclareData.insuranceAddOnFee67);
			 	$("#count68").html(addOnDeclareData.count68);
			 	$("#incomeAmount68").html(addOnDeclareData.incomeAmount68);
			 	$("#insuranceAddOnFee68").html(addOnDeclareData.insuranceAddOnFee68);
			 	$("#count").html(addOnDeclareData.count);
			 	$("#incomeAmount").html(addOnDeclareData.incomeAmount);
			 	$("#insuranceAddOnFee").html(addOnDeclareData.insuranceAddOnFee);
	        });
	    }
	}
</script>

<div class="ui-widget-content ui-corner-all" style="width: 98%; margin: 5px; padding: 2px" align="right">
	<form method="POST" Name="main-search" Action="<%=response.encodeURL("insuranceAddOn.do?action=printM0203") %>" class = "entry-form" onSubmit='return validate();'>        
		給付年月：<input type="text" id="fromYear" name="fromYear" value='<%=filter.getFromYear()%>'/>&nbsp;年&nbsp;&nbsp;
		<input type="text" id="fromMonth" name="fromMonth" value='<%=filter.getFromMonth()%>'/>&nbsp;月&nbsp;&nbsp; ～
		<input type="text" id="toMonth" name="toMonth" value='<%=filter.getToMonth()%>'/>&nbsp;月&nbsp;&nbsp;		
        <input type="button" class='ui-state-default' value="查詢" onclick="doSearch()" />&nbsp;&nbsp;
		<input type="button" class='ui-state-default' value="列印" onclick="doPrint()" />&nbsp;&nbsp;
		<input type="button" class='ui-state-default' value="產出媒體檔" onclick="doCreateOutFile()" />&nbsp;&nbsp;
	</form>
</div>
<div class="ui-widget-content ui-corner-all entry-form"	style="width: 98%; margin: 5px; padding: 2px">
	<table style="height: 100%; width: 100%" border="0" cellspacing="0" cellpadding="0" class="display" id="tblList">
		<thead>
			<tr>
				<th>所得(收入)類別</th>
				<th>代號</th>
				<th>件數</th>
				<th>給付總額</th>
				<th>扣費金額</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td>所屬投保單位給付全年累計逾當月投保金額四倍之獎金</td>
				<td>62</td>
				<td id="count62">0</td>
				<td id="incomeAmount62">0</td>
				<td id="insuranceAddOnFee62">0</td>
			</tr>
			<tr>
				<td>非所屬投保單位給付之薪資所得</td>
				<td>63</td>
				<td id="count63">0</td>
				<td id="incomeAmount63">0</td>
				<td id="insuranceAddOnFee63">0</td>
			</tr>
			<tr>
				<td>執行業務收入</td>
				<td>65</td>
				<td id="count65">0</td>
				<td id="incomeAmount65">0</td>
				<td id="insuranceAddOnFee65">0</td>
			</tr>
			<tr>
				<td>股利所得</td>
				<td>66</td>
				<td id="count66">0</td>
				<td id="incomeAmount66">0</td>
				<td id="insuranceAddOnFee66">0</td>
			</tr>
			<tr>
				<td>利息所得</td>
				<td>67</td>
				<td id="count67">0</td>
				<td id="incomeAmount67">0</td>
				<td id="insuranceAddOnFee67">0</td>
			</tr>
			<tr>
				<td>租金收入</td>
				<td>68</td>
				<td id="count68">0</td>
				<td id="incomeAmount68">0</td>
				<td id="insuranceAddOnFee68">0</td>
			</tr>
			<tr>
				<td>合計</td>
				<td></td>
				<td id="count">0</td>
				<td id="incomeAmount">0</td>
				<td id="insuranceAddOnFee">0</td>
			</tr>
		</tbody>
	</table>
</div>
