<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.csjian.model.bean.*" %>

<script type="text/javascript">
	var oTable;
	var myRegcode = '<%=request.getAttribute("regcode")%>';
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
	        "sAjaxSource": '<%=response.encodeURL("salary.do?action=itemDataTable") %>' + "&itemType=P",
	        "aoColumns": [
	            { "sName": "name" },
	            { "sName": "addOnFeeFlag" },
	            { "sName": "bonusFlag" },
	            { "sName": "enable" },
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
	    
	    $("#ItemForm").dialog({
		    bgiframe: true,
		    autoOpen: false,
		    draggable: true,
		    position: 'center',
		    width: 500,
		    height: 300,
		    modal: true,
		    buttons: {
			    "儲存": function () {
			        $.postJSON('<%=response.encodeURL("salary.do?action=updateItem") %>', { itemType:'P', name: $('#name').val(), seqno: $('#seqno').val(), enable: $('#enable').val(), addOnFeeFlag: $('#addOnFeeFlag').val(), regcode: $('#regcode').val(), bonusFlag: $('#bonusFlag').val() },
                        function (result) {
						    if (result == 'OK') {
							    alert("儲存薪資加項成功 !");
							    $("#ItemForm").dialog("close");
						    } else {
							    alert("儲存薪資加項發生錯誤 !");
						    }
					});
				},
				"取消": function () {
				            $(this).dialog("close");
						}
				},
				close: function () {
					oTable.fnReloadAjax('<%=response.encodeURL("salary.do?action=itemDataTable") %>' + "&itemType=P");
				}
	    });
	});
	
	function doEdit(desc) {
		$('#name').removeAttr("readonly");
		$('#addOnFeeFlag').attr("disabled", false); 
		$('#sysItem').hide();
		
		var part = desc.split('_');
		$('#regcode').val(part[0]);
		$('#seqno').val(part[1]);
		$('#name').val(part[2]);
		
		$("#enable").children().each(function(){
		    if ($(this).text()== part[3]){
		        $(this).attr("selected","true"); //或是給selected也可
		    }
		});
		
		$("#addOnFeeFlag").children().each(function(){
		    if ($(this).text()== part[4]){
		        $(this).attr("selected","true"); //或是給selected也可
		    }
		});
		
		$("#bonusFlag").children().each(function(){
		    if ($(this).text()== part[5]){
		        $(this).attr("selected","true"); //或是給selected也可
		    }
		});
		
		if (part[6] == 'N') {
			$('#name').attr("readonly", true);
			$('#addOnFeeFlag').attr("disabled", true); 
			$('#sysItem').show();
		}
		
		$("#ItemForm").dialog('open');
	}
	
	function doAdd() {
		$('#name').removeAttr("readonly");
		$('#addOnFeeFlag').attr("disabled", false); 
		$('#sysItem').hide();
		
		$('#regcode').val(myRegcode);
		$('#seqno').val('0');
		$('#name').val('');
		
		$("#enable").children().each(function(){
		    if ($(this).text()== 'Y'){
		        $(this).attr("selected","true"); //或是給selected也可
		    }
		});
		
		$("#addOnFeeFlag").children().each(function(){
		    if ($(this).text()== 'N'){
		        $(this).attr("selected","true"); //或是給selected也可
		    }
		});
		
		$("#bonusFlag").children().each(function(){
		    if ($(this).text()== 'N'){
		        $(this).attr("selected","true"); //或是給selected也可
		    }
		});
		
		$("#ItemForm").dialog('open');
	}
</script>
<div class="ui-widget-content ui-corner-all" style="width: 98%; margin: 5px; padding: 2px" align="right">
	<form method="POST" Name="main-search" Action="<%=response.encodeURL("salary.do?action=pitem") %>" class = "entry-form" onSubmit='return validate();'>        
        <input type="button" class='ui-state-default' value="增加薪資加項" onclick="doAdd();" />&nbsp;&nbsp;
	</form>
</div>
<div class="ui-widget-content ui-corner-all entry-form"	style="width: 98%; margin: 5px; padding: 2px">
	<table style="height: 100%; width: 100%" border="0" cellspacing="0" cellpadding="0" class="display" id="tblList">
		<thead>
			<tr>
				<th>薪資加項名稱</th>
				<th>是否不計入二代健保薪資</th>
				<th>是否列為二代健保獎金</th>
				<th>啟用</th>
				<th>修改</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td colspan="4" class="dataTables_empty">取得資料中…</td>
			</tr>
		</tbody>
	</table>
</div>

<div id="ItemForm" title="新增修改薪加項" class="ui-widget-content ui-corner-all entry-form">
    <fieldset>
        <div><label for="name" class="styled">薪資加項名稱</label><input id="name" type="text" class="textfield" size="30" maxlength="30"></div>
        <div><label for="addOnFeeFlag" class="styled">不計入二代健保薪資</label>
			<select id="addOnFeeFlag">
			    <option value="N">否</option>
        		<option value="Y">是</option>
        	</select>
		</div>
		<div><label for="bonusFlag" class="styled">列為二代健保獎金</label>
			<select id="bonusFlag">
			    <option value="N">否</option>
        		<option value="Y">是</option>
        	</select>
		</div>
        <div><label for="enable" class="styled">是否啟用</label>
        	<select id="enable">
        		<option value="Y">是</option>
        		<option value="N">否</option>
        	</select>
        </div>
    </fieldset>
    <div id="sysItem" class="ui-widget-content ui-corner-all ui-state-highlight" style="width: 450px; margin: 5px; padding: 2px"><b>
		說明：<br/>
		此薪資加項為系統預設，無法設定名稱及是否不計入二代健保薪資</b>
	</div>
    <input type="hidden" id="seqno" value="" />
    <input type="hidden" id="regcode" value="" />
</div>