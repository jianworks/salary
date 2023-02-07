<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.csjian.model.bean.*"%>

<%
	FilterBean filter = (FilterBean)request.getAttribute("filter");
%>

<script type="text/javascript">
	var oTable;
	$(document).ready(function () {
	    oTable = $("#tblList").dataTable({
			"bProcessing": true,
			"sPaginationType": "full_numbers",
			"bLengthChange": false,
			"bFilter": false,
			"bJQueryUI": true,
			"iDisplayLength": 12,
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
			},
	        "sAjaxSource": '<%=response.encodeURL("insuranceAddOn.do?action=m0305DataTable") %>' + "&fromYear=" + $('#fromYear').val() + "&fromMonth=" + $('#fromMonth').val() + "&toMonth=" + $('#toMonth').val(),
	        "aoColumns": [
	        	{
					"sName": "Blank1",
					"bSearchable": false,
					"bSortable": false,
					"fnRender": function (oObj) {
						return '<input type="checkbox" name="selected" value="' + oObj.aData[0] + '" onclick="checkParent();" />';
					}
				},
	            { "sName": "incomeType" },
	            { "sName": "incomePeriod" },
	            { "sName": "insuranceAddOnFee" }
	        ]
	    });
	});
	
	function doPrint() {
		var serialNos = "";
        $("input[name='selected']:checked").each(function () {
        	serialNos += $(this).val() + ",";
	    });
		window.location.href='<%=response.encodeURL("insuranceAddOn.do?action=doPrintM0305") %>' + "&serialNos=" + serialNos;
	}
	
	function doSearch() {
	    oTable.fnReloadAjax('<%=response.encodeURL("insuranceAddOn.do?action=m0305DataTable") %>' + "&fromYear=" + $('#fromYear').val() + "&fromMonth=" + $('#fromMonth').val() + "&toMonth=" + $('#toMonth').val());
	}
</script>

<div class="ui-widget-content ui-corner-all" style="width: 98%; margin: 5px; padding: 2px" align="right">
	<form method="POST" Name="main-search" Action="<%=response.encodeURL("insuranceAddOn.do?action=printM0305") %>" class = "entry-form" onSubmit='return validate();'>        
		給付年月：<input type="text" id="fromYear" name="fromYear" value='<%=filter.getFromYear()%>'/>&nbsp;年&nbsp;&nbsp;
		<input type="text" id="fromMonth" name="fromMonth" value='<%=filter.getFromMonth()%>'/>&nbsp;月&nbsp;&nbsp; ～
		<input type="text" id="toMonth" name="toMonth" value='<%=filter.getToMonth()%>'/>&nbsp;月&nbsp;&nbsp;		
        <input type="button" class='ui-state-default' value="查詢" onclick="doSearch()" />&nbsp;&nbsp;
		<input type="button" class='ui-state-default' value="列印繳款書" onclick="doPrint()" />&nbsp;&nbsp;
	</form>
</div>
<div class="ui-widget-content ui-corner-all entry-form"	style="width: 98%; margin: 5px; padding: 2px">
	<table style="height: 100%; width: 100%" border="0" cellspacing="0" cellpadding="0" class="display" id="tblList">
		<thead>
			<tr>
				<th width="20px;"><input type="checkbox" name="allSelected"	id="allSelected" onclick="checkAllChild();" /></th>
				<th>所得類別</th>
				<th>給付年月</th>
				<th>應扣繳補充保險費</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td colspan="4" class="dataTables_empty">取得資料中…</td>
			</tr>
		</tbody>
	</table>
</div>
