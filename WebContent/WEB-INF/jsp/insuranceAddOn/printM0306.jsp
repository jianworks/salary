<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.csjian.model.bean.*"%>

<%
	FilterBean filter = (FilterBean)request.getAttribute("filter");
%>

<script type="text/javascript">	
	$(document).ready(function () {
		$('#errorEmp').hide();
		doSearch();
	});
	function doPrint() {
		var err = "";
		if ($('#salaryAmount')==''||$('#insuranceAmount')==''||$('#addOnFee')=='')
			err += "無資料!\n";
		if (err != "") {
	        alert(err);
	    } else {
			window.location.href='<%=response.encodeURL("insuranceAddOn.do?action=doPrintM0306") %>' + "&incomePeriod=" + $('#incomePeriod').val() + "&amount=" + $('#addOnFee').val();
	    }
	}
	
	function doSearch() {
		var err = "";
		if ($('#incomePeriod')=='')
			err += "請輸入給付年月!\n";
		if (err != "") {
	        alert(err);
	    } else {
	    	$.postJSON("insuranceAddOn.do?action=calcSalaryAmount", { incomePeriod: $('#incomePeriod').val() }, function (result) {
 	 			if (result !='') { 
 	 				var part = result.split(',');
 	 				$('#salaryAmount').val(part[0]);
 	 				$('#insuranceAmount').val(part[1]);	
 	 				calcAddOnFee();
 	 			}
 	         });
	    	$.postJSON("insuranceAddOn.do?action=verifyHealthInsuranceBlank", { incomePeriod: $('#incomePeriod').val() }, function (result) {
 	 			if (result !='OK') { 
 	 				$('#employeeList').html(result);
 	 				$('#errorEmp').show();
 	 			} else {
 	 				$('#errorEmp').hide();
 	 			}
 	         });
	    }
	}
	
	function doDownload() {
		var err = "";
		if ($('#incomePeriod')=='')
			err += "請輸入給付年月!\n";
		if (err != "") {
	        alert(err);
	    } else {	    	
	    	$.postJSON("insuranceAddOn.do?action=calcSalaryAmount", { incomePeriod: $('#incomePeriod').val() }, function (result) {
 	 			if (result !='') { 
 	 				var part = result.split(',');
 	 				$('#salaryAmount').val(part[0]);
 	 				$('#insuranceAmount').val(part[1]);	
 	 				calcAddOnFee();
 	 			}
 	         });
	    	window.location.href='<%=response.encodeURL("insuranceAddOn.do?action=exportSalaryAmountDetailtoCSV") %>'+ "&incomePeriod=" + $('#incomePeriod').val();
	    }
	}
	
	function calcAddOnFee() {
		if (parseInt($('#salaryAmount').val(), 10) > parseInt($('#insuranceAmount').val(), 10)) {
			$('#addOnFee').val(Math.round((parseInt($('#salaryAmount').val(), 10) - parseInt($('#insuranceAmount').val(), 10))*0.0191) );
		} else {
			$('#addOnFee').val('0');
		}
	}
</script>

<div class="ui-widget-content ui-corner-all" style="width: 500px; margin: 5px; padding: 2px" align="right">
	<form method="POST" Name="main-search" Action="<%=response.encodeURL("insuranceAddOn.do?action=printM0306") %>" class = "entry-form" onSubmit='return validate();'>        
		給付年月：<input type="text" id="incomePeriod" name="incomePeriod" value='<%=filter.getIncomePeriod()%>'/>&nbsp;&nbsp;
        <input type="button" class='ui-state-default' value="查詢" onclick="doSearch();" />&nbsp;&nbsp;
        <input type="button" class='ui-state-default' value="下載明細資料" onclick="doDownload();" />&nbsp;&nbsp;
		<input type="button" class='ui-state-default' value="列印" onclick="doPrint();" />&nbsp;&nbsp;
	</form>
</div>
<div class="ui-widget-content ui-corner-all entry-form"	style="width: 500px; margin: 5px; padding: 2px">
	<form method="POST" Name="mainform" id="mainform" Action="<%=response.encodeURL("insuranceAddOn.do?action=updateInsuranceAddOnFee")%>">
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<TBODY>
				<tr>
					<td width="30%" class=dataLabel><div align="right">本月薪資總額：</div></td>
					<td align="left"><input name="salaryAmount"	id="salaryAmount" type="text" class="textfield" size="15" maxlength="10" onBlur="calcAddOnFee();"></td>
				</tr>
				<tr>
					<td width="30%" class=dataLabel><div align="right">本月投保金額總額：</div></td>
					<td align="left"><input name="insuranceAmount" id="insuranceAmount"	type="text" class="textfield" size="15" maxlength="10" onBlur="calcAddOnFee();"></td>
				</tr>
				<tr><td colspan="2"><hr/></td></tr>
				<tr>
					<td width="30%" class=dataLabel><div align="right">應繳補充保費金額：</div></td>
					<td align="left"><input name="addOnFee" id="addOnFee"	type="text" class="textfield" size="15" maxlength="10"></td>
				</tr>
			</TBODY>
		</table>
	</form>
</div>
<div class="ui-widget-content ui-corner-all ui-state-highlight" style="width: 500px; margin: 5px; padding: 2px"><b>
提示：<br/>
1. 如果實際發薪資的時間較薪資月份晚一個月 (例如：2月發1月的薪資)，請先使用 <a href="insuranceAddOn.do?action=companyHealthInfo" > 補充保費區 -> 投保單位資料維護</a>功能&nbsp;&nbsp;設定薪資發放時間<br/>
2. 如果部份薪資加項不計入薪資總額，請使用 <a href="salary.do?action=pitem" > 員工及薪資資料建檔 -> 薪資加項設定</a>功能&nbsp;&nbsp;設定<br/>
3. 如果部份薪資減項須加在薪資總額，請使用 <a href="salary.do?action=mitem" > 員工及薪資資料建檔 -> 薪資減項設定</a>功能&nbsp;&nbsp;設定
</b></div>
<br/><br/>
<div id="errorEmp" class="ui-widget-content ui-corner-all ui-state-highlight" style="width: 500px; margin: 5px; padding: 2px; color: red;"><b>
以下員工的薪資資料內沒有健保投保薪資資料，請修正：<br/>
<span id="employeeList"></span>
<br/>
提示：<br/>
1. 請先使用 <a href="employee.do?action=edit" > 員工及薪資資料建檔 -> 員工基本資料設定</a>功能&nbsp;&nbsp;設定健保投保薪資<br/>
2. 再使用 <a href="salary.do?action=list" > 員工及薪資資料建檔 -> 薪資資料建檔</a>功能&nbsp;&nbsp;開啟該名員工薪資資料再儲存一次<br/>
</b></div>
