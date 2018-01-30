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
			},
	        "sAjaxSource": '<%=response.encodeURL("insuranceAddOn.do?action=m0302YearlyDataTable") %>' + "&incomePeriod=" + $('#incomePeriod').val() + "&incomeType=" + $('#incomeType').val() + "&unicode=" + $('#unicode').val() + "&name=" + $('#name').val(),
	        "aoColumns": [
	        	{
					"sName": "Blank1",
					"bSearchable": false,
					"bSortable": false,
					"fnRender": function (oObj) {
						return '<input type="checkbox" name="selected" value="' + oObj.aData[0] + '" onclick="checkParent();" />';
					}
				},
	            { "sName": "incomeDate" },
	            { "sName": "incomeType" },
	            { "sName": "unicode" },
	            { "sName": "name" },
	            { "sName": "incomeAmount" },
	            { "sName": "insuranceAddOnFee" }
	        ]
	    });
	});
	
	function doPrint() {
		var serialNos = "";
        $("input[name='selected']:checked").each(function () {
        	serialNos += $(this).val() + ",";
	    });
		window.location.href='<%=response.encodeURL("insuranceAddOn.do?action=doPrintM0302Yearly") %>' + "&serialNos=" + serialNos;
	}
	
	function doSearch() {
	    oTable.fnReloadAjax('<%=response.encodeURL("insuranceAddOn.do?action=m0302YearlyDataTable") %>' + "&incomePeriod=" + $('#incomePeriod').val() + "&incomeType=" + $('#incomeType').val() + "&unicode=" + $('#unicode').val() + "&name=" + $('#name').val());
	}
</script>

<div class="ui-widget-content ui-corner-all" style="width: 98%; margin: 5px; padding: 2px" align="right">
	<form method="POST" Name="main-search" Action="<%=response.encodeURL("insuranceAddOn.do?action=printM0302Yearly") %>" class = "entry-form" onSubmit='return validate();'>        
		給付年度：<input type="text" id="incomePeriod" name="incomePeriod" value='<%=filter.getIncomePeriod()%>'/>&nbsp;&nbsp;
		所得類別：<select id="incomeType" name="incomeType">
                	<option value="">&nbsp;</option>
                	<option value="62" <%=filter.getIncomeType().equals("62")?"selected":"" %>>逾投保金額四倍之獎金</option>
                	<option value="63" <%=filter.getIncomeType().equals("63")?"selected":"" %>>非所屬投保單位給付之薪資所得</option>
                	<option value="65" <%=filter.getIncomeType().equals("65")?"selected":"" %>>執行業務收入</option>
                	<option value="66" <%=filter.getIncomeType().equals("66")?"selected":"" %>>股利所得</option>
                	<option value="67" <%=filter.getIncomeType().equals("67")?"selected":"" %>>利息所得</option>
                	<option value="68" <%=filter.getIncomeType().equals("68")?"selected":"" %>>租金收入</option>
            	 </select>&nbsp;&nbsp;
                所得人身份證號：<input type="text" id="unicode" name="unicode" value='<%=filter.getUnicode()%>'/>&nbsp;&nbsp;    	
                所得人姓名：<input type="text" id="name" name="name" value='<%=filter.getName()%>'/>&nbsp;&nbsp;  
        <input type="button" class='ui-state-default' value="查詢" onclick="doSearch()" />&nbsp;&nbsp;
		<input type="button" class='ui-state-default' value="列印勾選資料" onclick="doPrint()" />&nbsp;&nbsp;
	</form>
</div>
<div class="ui-widget-content ui-corner-all entry-form"	style="width: 98%; margin: 5px; padding: 2px">
	<table style="height: 100%; width: 100%" border="0" cellspacing="0" cellpadding="0" class="display" id="tblList">
		<thead>
			<tr>
				<th width="20px;"><input type="checkbox" name="allSelected"	id="allSelected" onclick="checkAllChild();" /></th>
				<th>給付年度</th>
				<th>所得類別</th>
				<th>所得人身份證號</th>
				<th>所得人姓名</th>
				<th>所得(收入)總額</th>
				<th>扣繳補充保費金額</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td colspan="4" class="dataTables_empty">取得資料中…</td>
			</tr>
		</tbody>
	</table>
</div>
