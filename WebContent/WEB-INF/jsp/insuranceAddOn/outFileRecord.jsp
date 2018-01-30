<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.csjian.model.bean.*"%>

<%
	FilterBean filter = (FilterBean)request.getAttribute("filter");
%>

<script type="text/javascript">	
	var oTable;
	$(document).ready(function () {
		$("#outFileDetail").dialog({
	        modal: true,
	        autoOpen: false,
	        width: 1000,
	        buttons: {
	            "關閉": function () {
	                $(this).dialog("close");
	            }
	        }
	    });
		
	    oTable = $("#outList").dataTable({
			"bProcessing": true,
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
	
	function doSearch() {
		var err = "";
		if ($('#incomePeriod')=='')
			err += "請輸入給付年月!\n";
			
		var incomeTypes = "";
	    $("input[name='selected']:checked").each(function () {
	      	incomeTypes += $(this).val() + ",";
		});
	    if (incomeTypes.length < 2) {
	      	err += "請至少勾選一種所得類別!\n";
	    }	
		if (err != "") {
	        alert(err);
	    } else {
			$.postJSON("insuranceAddOn.do?action=getOutFileRecordSummary", { incomePeriod: $('#incomePeriod').val(), incomeTypes: incomeTypes }, function (outFileRecordSummary) {
			 	if (outFileRecordSummary.count62 != '0') {
			 		$("#data62").show();
			 		$("#incomePeriod62").html($("incomePrriod").val());
					$("#count62").html(outFileRecordSummary.count62);
			 		$("#insuranceAddOnFee62").html(outFileRecordSummary.insuranceAddOnFee62);
			 	} else {
			 		$("#data62").hide();
			 	}
			 	if (outFileRecordSummary.count63 != '0') {
			 		$("#data63").show();
			 		$("#incomePeriod63").html($("incomePrriod").val());
					$("#count63").html(outFileRecordSummary.count63);
			 		$("#insuranceAddOnFee63").html(outFileRecordSummary.insuranceAddOnFee63);
			 	} else {
			 		$("#data63").hide();
			 	}
			 	if (outFileRecordSummary.count65 != '0') {
			 		$("#data65").show();
			 		$("#incomePeriod65").html($("incomePrriod").val());
					$("#count65").html(outFileRecordSummary.count65);
			 		$("#insuranceAddOnFee65").html(outFileRecordSummary.insuranceAddOnFee65);
			 	} else {
			 		$("#data65").hide();
			 	}
			 	if (outFileRecordSummary.count66 != '0') {
			 		$("#data66").show();
			 		$("#incomePeriod66").html($("incomePrriod").val());
					$("#count66").html(outFileRecordSummary.count66);
			 		$("#insuranceAddOnFee66").html(outFileRecordSummary.insuranceAddOnFee66);
			 	} else {
			 		$("#data66").hide();
			 	}
			 	if (outFileRecordSummary.count67 != '0') {
			 		$("#data67").show();
			 		$("#incomePeriod67").html($("incomePrriod").val());
					$("#count67").html(outFileRecordSummary.count67);
			 		$("#insuranceAddOnFee67").html(outFileRecordSummary.insuranceAddOnFee67);
			 	} else {
			 		$("#data67").hide();
			 	}
			 	if (outFileRecordSummary.count68 != '0') {
			 		$("#data68").show();
			 		$("#incomePeriod68").html($("incomePrriod").val());
					$("#count68").html(outFileRecordSummary.count68);
			 		$("#insuranceAddOnFee68").html(outFileRecordSummary.insuranceAddOnFee68);
			 	} else {
			 		$("#data68").hide();
			 	}
	        });
	    }
	}
	
	function showDetail(incomeType) {
		oTable.fnReloadAjax('<%=response.encodeURL("insuranceAddOn.do?action=outFileRecordDetailDataTable") %>' + "&incomePeriod=" + $('#incomePeriod').val() + "&incomeType=" + incomeType);
		$("#outFileDetail").dialog("open");		
	}
	
	function downloadDetail(incomeType) {   	
	   	window.location.href='<%=response.encodeURL("insuranceAddOn.do?action=exportOutFileRecordDetailtoCSV") %>'+ "&incomePeriod=" + $('#incomePeriod').val() + "&incomeType=" + incomeType;
	}
</script>

<div class="ui-widget-content ui-corner-all" style="width: 98%; margin: 5px; padding: 2px" align="right">
	<form method="POST" Name="main-search" Action="<%=response.encodeURL("insuranceAddOn.do?action=printM0203") %>" class = "entry-form" onSubmit='return validate();'>        
		給付年月：<input type="text" id="incomePeriod" name="incomePeriod" value='<%=filter.getIncomePeriod()%>'/> (格式：YYY-MM)&nbsp;&nbsp;	
		(所得類別：<input type="checkbox" name="selected" value="62" checked="checked"/>&nbsp;逾投保金額四倍之獎金&nbsp;&nbsp;
				<input type="checkbox" name="selected" value="63" checked="checked"/>&nbsp;非所屬投保單位給付之薪資所得&nbsp;&nbsp;
				<input type="checkbox" name="selected" value="65" checked="checked"/>&nbsp;執行業務收入&nbsp;&nbsp;
				<input type="checkbox" name="selected" value="66" checked="checked"/>&nbsp;股利所得&nbsp;&nbsp;
				<input type="checkbox" name="selected" value="67" checked="checked"/>&nbsp;利息所得&nbsp;&nbsp;
				<input type="checkbox" name="selected" value="68" checked="checked"/>&nbsp;租金收入&nbsp;&nbsp;)&nbsp;&nbsp;		
        <input type="button" class='ui-state-default' value="查詢" onclick="doSearch()" />&nbsp;&nbsp;
	</form>
</div>
<div class="ui-widget-content ui-corner-all entry-form"	style="width: 98%; margin: 5px; padding: 2px">
	<table style="height: 100%; width: 100%" border="0" cellspacing="0" cellpadding="0" class="display" id="tblList">
		<thead>
			<tr>
				<th>所得(收入)類別</th>
				<th>給付年月</th>
				<th>扣費金額</th>
				<th>件數</th>
				<th>&nbsp;</th>
			</tr>
		</thead>
		<tbody>
			<tr id="data62">
				<td>所屬投保單位給付全年累計逾當月投保金額四倍之獎金</td>
				<td id="incomePeriod62"></td>
				<td id="insuranceAddOnFee62">0</td>
				<td id="count62">0</td>
				<td><input type="button" class='ui-state-default' value="申報明細" onclick="showDetail('62')" />&nbsp;&nbsp;<input type="button" class='ui-state-default' value="下載明細" onclick="downloadDetail('62')" /></td>
			</tr>
			<tr id="data63">
				<td>非所屬投保單位給付之薪資所得</td>
				<td id="incomePeriod63"></td>
				<td id="insuranceAddOnFee63">0</td>
				<td id="count63">0</td>
				<td><input type="button" class='ui-state-default' value="申報明細" onclick="showDetail('63')" />&nbsp;&nbsp;<input type="button" class='ui-state-default' value="下載明細" onclick="downloadDetail('63')" /></td>
			</tr>
			<tr id="data65">
				<td>執行業務收入</td>
				<td id="incomePeriod65"></td>
				<td id="insuranceAddOnFee65">0</td>
				<td id="count65">0</td>
				<td><input type="button" class='ui-state-default' value="申報明細" onclick="showDetail('65')" />&nbsp;&nbsp;<input type="button" class='ui-state-default' value="下載明細" onclick="downloadDetail('65')" /></td>
			</tr>
			<tr id="data66">
				<td>股利所得</td>
				<td id="incomePeriod66"></td>
				<td id="insuranceAddOnFee66">0</td>
				<td id="count66">0</td>
				<td><input type="button" class='ui-state-default' value="申報明細" onclick="showDetail('66')" />&nbsp;&nbsp;<input type="button" class='ui-state-default' value="下載明細" onclick="downloadDetail('66')" /></td>
			</tr>
			<tr id="data67">
				<td>利息所得</td>
				<td id="incomePeriod67"></td>
				<td id="insuranceAddOnFee67">0</td>
				<td id="count67">0</td>
				<td><input type="button" class='ui-state-default' value="申報明細" onclick="showDetail('67')" />&nbsp;&nbsp;<input type="button" class='ui-state-default' value="下載明細" onclick="downloadDetail('67')" /></td>
			</tr>
			<tr id="data68">
				<td>租金收入</td>
				<td id="incomePeriod68"></td>
				<td id="insuranceAddOnFee68">0</td>
				<td id="count68">0</td>
				<td><input type="button" class='ui-state-default' value="申報明細" onclick="showDetail('68')" />&nbsp;&nbsp;<input type="button" class='ui-state-default' value="下載明細" onclick="downloadDetail('68')" /></td>
			</tr>
		</tbody>
	</table>
</div>

<div id="outFileDetail" title="申報明細">
   	<table style="height: 100%; width: 100%" id="outList">
		<thead>
			<tr>
				<th>申報編號</th>
				<th>給付日期</th>
				<th>所得(收入)類別代號</th>
				<th>所得人身份證號</th>
				<th>扣費當月投保金額</th>
				<th>同年度累積獎金金額</th>
				<th>所得人(收入)給付金額</th>
				<th>扣繳補充保費金額</th>
				<th>所得人姓名</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
		</tbody>
	</table>
</div>
