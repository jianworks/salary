<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.csjian.model.bean.*"%>

<%
	// TODO
%>

<script type="text/javascript">
	$(document).ready(function () {
	    $("#tblList").dataTable({
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
	        "sAjaxSource": '<%=response.encodeURL("insuranceAddOn.do?action=incomeEarnerDataTable") %>',
	        "aoColumns": [
	        	{
					"sName": "Blank1",
					"bSearchable": false,
					"bSortable": false,
					"fnRender": function (oObj) {
						return '<input type="checkbox" name="selected" value="' + oObj.aData[0] + '" onclick="checkParent();" />';
					}
				},
	            { "sName": "unicode" },
	            { "sName": "name" },
	            { "sName": "address" },
	            {
				    "sName": "Blank2",
				    "bSearchable": false,
				    "bSortable": false,
				    "fnRender": function (oObj) {
					    return "<input type='button' value='修改' onclick=\"doEdit('" + oObj.aData[4] + "')\" class='ui-state-default' />";
					}
				}
	        ]
	    });
	});
	
	function doEdit(keyId) {
		window.location.href='<%=response.encodeURL("insuranceAddOn.do?action=incomeEarnerEdit&keyId=") %>' + keyId;
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
        	window.location.href='<%=response.encodeURL("insuranceAddOn.do?action=exportIncomeEarnerToCSV") %>'+ "&serialNos=" + serialNos;
        }		
	}
</script>

<div class="ui-widget-content ui-corner-all" style="width: 98%; margin: 5px; padding: 2px" align="right">
	<form method="POST" Name="main-search" Action="<%=response.encodeURL("insuranceAddOn.do?action=incomeEarnerlist") %>" class = "entry-form" onSubmit='return validate();'>        
		<!-- <input type="button" class='ui-state-default' value="刪除勾選人員" onclick="doRemove()" /><text>&nbsp;&nbsp;</text> -->
		<input type="button" class='ui-state-default' value="匯出勾選人員" onclick="doExport()" />&nbsp;&nbsp;
        <input type="button" class='ui-state-default' onclick="window.location.href = '<%=response.encodeURL("insuranceAddOn.do?action=incomeEarnerEdit") %>'" value="新增" />&nbsp;&nbsp;
	</form>
</div>
<div class="ui-widget-content ui-corner-all entry-form"	style="width: 98%; margin: 5px; padding: 2px">
	<table style="height: 100%; width: 100%" border="0" cellspacing="0" cellpadding="0" class="display" id="tblList">
		<thead>
			<tr>
				<th width="20px;"><input type="checkbox" name="allSelected"	id="allSelected" onclick="checkAllChild();" /></th>
				<th width="80px;">身份證字號</th>
				<th width="80px;">姓名</th>
				<th>地址</th>
				<th width="60px">操作</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td colspan="4" class="dataTables_empty">取得資料中…</td>
			</tr>
		</tbody>
	</table>
</div>
