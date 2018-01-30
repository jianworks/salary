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
	        "sAjaxSource": '<%=response.encodeURL("insuranceAddOn.do?action=insuranceAddOnFeeDataTable") %>' + "&incomePeriod=" + $('#incomePeriod').val() + "&incomeType=" + $('#incomeType').val() + "&unicode=" + $('#unicode').val() + "&name=" + $('#name').val(),
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
	            { "sName": "healthInsuranceSalary" },
	            { "sName": "incomeAmount" },
	            { "sName": "dummy" },
	            { "sName": "accumulatedBonusAmount" },
	            { "sName": "insuranceAddOnFee" },
	            {
				    "sName": "Blank2",
				    "bSearchable": false,
				    "bSortable": false,
				    "fnRender": function (oObj) {
					    return "<input type='button' value='修改' onclick=\"doEdit('" + oObj.aData[10] + "')\" class='ui-state-default' />";
					}
				}
	        ]
	    });
	});
	
	function doEdit(serialNo) {
		window.location.href='<%=response.encodeURL("insuranceAddOn.do?action=insuranceAddOnFeeEdit&serialNo=") %>' + serialNo;
	}
	
	function doExport() {
		var serialNos = "";
        $("input[name='selected']:checked").each(function () {
        	serialNos += $(this).val() + ",";
	    });
        
        var err = "";
    	if (serialNos.length < 2) {
        	err += "請至少勾選一筆資料匯出!\n";
        }
    	if (err != "") {
            alert(err);
        } else {
        	window.location.href='<%=response.encodeURL("insuranceAddOn.do?action=exportInsuranceAddOnFeeToCSV") %>'+ "&serialNos=" + serialNos;
        }		
	}
	
	function doSearch() {
	    oTable.fnReloadAjax('<%=response.encodeURL("insuranceAddOn.do?action=insuranceAddOnFeeDataTable") %>' + "&incomePeriod=" + $('#incomePeriod').val() + "&incomeType=" + $('#incomeType').val() + "&unicode=" + $('#unicode').val() + "&name=" + $('#name').val());
	}
</script>

<div class="ui-widget-content ui-corner-all" style="width: 98%; margin: 5px; padding: 2px" align="right">
	<form method="POST" Name="main-search" Action="<%=response.encodeURL("insuranceAddOn.do?action=incomeEarnerlist") %>" class = "entry-form" onSubmit='return validate();'>        
		給付年月：<input type="text" id="incomePeriod" name="incomePeriod" value='<%=filter.getIncomePeriod()%>'/>&nbsp;&nbsp;
		所得類別：<select id="incomeType" name="incomeType">
                	<option value="">六大類全部</option>
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
		<!-- <input type="button" class='ui-state-default' value="刪除勾選人員" onclick="doRemove()" /><text>&nbsp;&nbsp;</text> -->
		<input type="button" class='ui-state-default' value="匯出勾選資料" onclick="doExport()" />&nbsp;&nbsp;
        <input type="button" class='ui-state-default' onclick="window.location.href = '<%=response.encodeURL("insuranceAddOn.do?action=insuranceAddOnFeeEdit") %>'" value="新增" />&nbsp;&nbsp;
	</form>
</div>
<div class="ui-widget-content ui-corner-all entry-form"	style="width: 98%; margin: 5px; padding: 2px">
	<table style="height: 100%; width: 100%" border="0" cellspacing="0" cellpadding="0" class="display" id="tblList">
		<thead>
			<tr>
				<th width="20px;"><input type="checkbox" name="allSelected"	id="allSelected" onclick="checkAllChild();" /></th>
				<th>給付日期</th>
				<th>所得類別</th>
				<th>所得人身份證號</th>
				<th>所得人姓名</th>
				<th>給付時月投保金額</th>
				<th>本次給付所得金額</th>
				<th>雇主身份加保之投保總金額</th>
				<th>同年度累積獎金金額</th>
				<th>本次扣繳補充保費金額</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td colspan="11" class="dataTables_empty">取得資料中…</td>
			</tr>
		</tbody>
	</table>
</div>
