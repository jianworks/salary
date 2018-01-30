<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.csjian.model.bean.*" %>
<%@ page import="com.csjian.util.*" %>

<script src="js/checkPID.js" type=text/javascript></script>
<script src="js/nextField.js" type=text/javascript></script>

<%
	InsuranceAddOnFeeBean insuranceAddOnFee = (InsuranceAddOnFeeBean)request.getAttribute("insuranceAddOnFee");
    Calendar cal = Calendar.getInstance();
    String year = cal.get(Calendar.YEAR) + "";
    String today = (cal.get(Calendar.YEAR)-1911) + "-" + (cal.get(Calendar.MONTH)+1) + "-" + cal.get(Calendar.DATE);
%>

  <script type=text/javascript>
  <!--
  	var sequence = ["unicode", "incomeDate", "incomeType", "healthInsuranceSalary", "incomeAmount62", "accumulatedBonusAmount", 
  	                "incomeAmount63", "incomeAmount65", "stockNote", "isBoss", "incomeAmount66", "trustNote66", 
  	                "bossHealthInsuranceAmount", "ICAAmount", "annualICAAmount", "excludeDate", "incomeAmount67", 
  	                "trustNote67", "incomeAmount68", "trustNote68", "insuranceAddOnFee", "btnUpdate"];
  
  	$(document).ready(function () {
      $("#unicode").autocomplete({
          source: "insuranceAddOn.do?action=incomeEarnerAutoCompleteList",
			minLength: 0,
			change: function (event, ui) {
			    if ($(".ui-autocomplete li:textEquals('" + $(this).val() + "')").size() == 0) {
			        $(this).val('');
			    }
			},
			select: function (event, ui) {
				$('#unicode').val(ui.item.id);
			}
		});
  	});
  
    function doUpdate() {
    	var err = "";
    	if ($('#unicode').val() == '') 
        	err += "所得人身份證號 不得為空白!\n";
    	if ($('#incomeType').val() == '') 
        	err += "請選擇所得類別!\n";
    	switch(parseInt($('#incomeType').val())) {
 		case 62:
 			if ($('#incomeAmount').val() == '')
 				err += "本次給付獎金總額 不得為空白!\n";	
			if ($('#healthInsuranceSalary').val() == '')
 				err += "給付時月投保金額 不得為空白! (請確認所輸入的所得人為員工)\n";		
 			break;
 		case 63:
			if ($('#incomeAmount').val() == '') 
				err += "本次給付兼職收入總額 不得為空白!\n";
 			break;
 		case 65:
			if ($('#incomeAmount').val() == '')
				err += "本次給付執行業務收入總額 不得為空白!\n";
 			break;
 		case 66:
			if ($('#incomeAmount').val() == '')
				err += "本次給付股利所得總額 不得為空白!\n";
			if ($('#stockNote').val() == '')
				err += "股利註記 不得為空白!\n";	
			if ($('#ICAAmount').val() == '')
				err += "扣取時可扣抵稅額 不得為空白!\n";
			if ($('#annualICAAmount').val() == '')
				err += "年度確定可扣抵稅額 不得為空白!\n";
			if ($('#excludeDate').val() == '')
				err += "除權息基準日 不得為空白!\n";	
			if ($("#isBoss").attr('checked') && $('#bossHealthInsuranceAmount').val() == '')
				err += "本次股利所屬期間，以雇主身份之加保總金額 不得為空白!\n";
 			break;
 		case 67:
			if ($('#incomeAmount').val() == '')
				err += "本次給付利息所得總額 不得為空白!\n";
 			break;
 		case 68:
			if ($('#incomeAmount').val() == '')
				err += "本次給付租金收入總額 不得為空白!\n";
 			break;
 		default:
 			break;
 		}
		
		if (err != "") {
            alert(err);
        } else {
            $("#mainform").submit();
        }
    }
    
    function doRemove() {
    	if (window.confirm("確定要刪除此筆資料 ?")) {
    		mainform.action = "<%=response.encodeURL("insuranceAddOn.do?action=removeInsuranceAddOnFee") %>";
    		mainform.submit();
    	}
    }
    
    function doCancel() {
    	mainform.action = "<%=response.encodeURL("insuranceAddOn.do?action=insuranceAddOnFee") %>";
    	mainform.submit();
    }
      
    var rePW = /^[0-9A-Za-z]+$/;
    
    function count(value){
      nowChr = 0;
      //for迴圈判斷value中的每一個字是否在0~255間
      for (var i=0;i<value.length;i++){
        value.charCodeAt(i)<256?nowChr++:nowChr+=2;
      }
      return nowChr;
    }

    function sDate(eventType) {
      with (document.mainform) {
        var returnValue = window.showModalDialog("misc/calendar.html",'dialogArguments',"dialogHeight: 280px; dialogWidth: 280px; center: yes; scroll: no; status: no" );
        if (returnValue) {
          eval(eventType + ".value=returnValue");
        }
      }
    }

    function chkDate(datename) {
      if (datename == "incomeDate") {
        if (!validateDate(document.forms["mainform"].incomeDate.value)){
          window.alert("所得給付日的日期格式錯誤");
          document.forms["mainform"].incomeDate.value = "";
        }
      } else {
        if (!validateDate(document.forms["mainform"].excludeDate.value)){
          window.alert("除權息基準日的日期格式錯誤");
          document.forms["mainform"].excludeDate.value = "";
        }
      }
    }

    function validateDate(datestr) {
	  var datearray = datestr.split("-");
	  if (datearray.length<3) return false;
	  var year = datearray[0];
	  var month = datearray[1];
	  var day = datearray[2];
	  var tempDate = new Date((parseInt(year)+1911) + "/" + month + "/" + day);
	  if (isNaN(tempDate))
	    return (false);
	  if ((tempDate.getMonth()==parseInt(month)-1) && (tempDate.getDate()==parseInt(day))) {
	    return (true);
	  } else
	    return (false);
    }

    function validateNumber(oField) {
      var oNumber = document.getElementById(oField).value;
      var validateNumber = /^[0-9]*$/.test(oNumber);
      if(!validateNumber) {
        window.alert("金額部份只能輸入數字 !");
        document.getElementById(oField).value = "";
        return false;
      } else {
        return true;
      }
    }

    function formatUnicode(){
      with (document.mainform) {
        unicode.value = unicode.value.toUpperCase();
      }
    }

    function changeFocus(strFocusid) {
      document.mainform.FOCUS_NAME.value = strFocusid;
    }

 	function findIncomeEarner() {
 		 $.postJSON("insuranceAddOn.do?action=findIncomeEarner", { unicode: $('#unicode').val() }, function (incomeEarner) {
 			 $("#unicode").val(incomeEarner.unicode);
             $("#name").val(incomeEarner.name);
             $("#healthInsuranceSalary").val(incomeEarner.healthInsurance);
             $("#address").val(incomeEarner.address); 		 	 
         }); 		 
 		if ($('#unicode')=='') {
			alert('所得人資料不存在 !');
		} 
 	}
 	
 	function setIncomeType() {
 		$('#info62').hide();
 		$('#info63').hide();
 		$('#info65').hide();
 		$('#info66').hide();
 		$('#info67').hide();
 		$('#info68').hide();
 		$('#info' + $('#incomeType').val()).show();
 		$("#trustNote66").attr('checked', false);
 		$("#trustNote67").attr('checked', false);
 		$("#trustNote68").attr('checked', false);
 		$("#trustNote").val("");
 		if ($('#incomeType').val() == '62') {
 			$.postJSON("insuranceAddOn.do?action=bonusAmount", { unicode: $('#unicode').val(), incomeDate: $('#incomeDate').val() }, function (result) {
 	 			if (result !='') {
 	 				$("#incomeAmount62").val(result);
 	 			} else {
 	 				$("#incomeAmount62").val('0');
 	 			}
 	 			calcInsuranceAddOnFee();
 	         });
 		}
 	}
 	
 	function setIsBoss() {
 		if ($("#isBoss").attr('checked')) {
			$("#bossInfo").show();
			$('#bossHealthInsuranceAmount').val(parseInt($('#healthInsuranceSalary').val())*12);
		} else {
			$("#bossInfo").hide();
		}
 	}
 	
 	function calcInsuranceAddOnFee() {
 		var datearray = $('#incomeDate').val().split("-");
 		var year = datearray[0];
 		
 		  
        // 2016/1/1 費率由 2% 改為 1.91%, 65,66,67,68 門檻由 5000 升為 20000
 		var rate = 0.02;
 		var minGate = 5000;
 		if (year > 104) {
 			rate = 0.0191;
 			minGate = 20000;
 		}
 		
 		switch(parseInt($('#incomeType').val())) {
 		case 62:
 			$('#incomeAmount').val($('#incomeAmount62').val());
 			var bonus = parseInt($('#incomeAmount').val());
 			var total = bonus;
 			$.postJSON("insuranceAddOn.do?action=totalBonusAmount", { unicode: $('#unicode').val(), incomeDate: $('#incomeDate').val(), serialNo: $('#serialNo').val() }, function (result) {
 	 			if (result !='') {
 					total = bonus + parseInt(result);
 	 			}
 	 			$("#accumulatedBonusAmount").val(total);
 	         });
 			
 			if (total - parseInt($("#healthInsuranceSalary").val())*4 > 0) { // 累計超過 4 倍保額，才收補充保費 	
 				$('#insuranceAddOnFee').val(((total - bonus) > parseInt($("#healthInsuranceSalary").val())*4) ? bonus*rate: (total - parseInt($("#healthInsuranceSalary").val())*4)*rate);
 			} else {
 				$('#insuranceAddOnFee').val('0');
 			}
 			break;
 		case 63:
 			$('#incomeAmount').val($('#incomeAmount63').val());
 			if ($('#incomeAmount').val()>=19273) { // 小於 5000元，不收補充保費, 2014/11/21 改為 19273
 				$('#insuranceAddOnFee').val(parseInt($('#incomeAmount').val())<10000000 ? parseInt($('#incomeAmount').val())*rate : 10000000*rate);
 			} else {
 				$('#insuranceAddOnFee').val('0');
 			}
 			break;
 		case 65:
 			$('#incomeAmount').val($('#incomeAmount65').val());
 			if ($('#incomeAmount').val()>=minGate) { // 小於 5000元，不收補充保費, 2016/1/1 改為 20000
 				$('#insuranceAddOnFee').val(parseInt($('#incomeAmount').val())<10000000 ? parseInt($('#incomeAmount').val())*rate : 10000000*rate); 
 			} else {
 				$('#insuranceAddOnFee').val('0');
 			}
 			break;
 		case 66:
 			$('#incomeAmount').val($('#incomeAmount66').val());
 			if ($("#isBoss").attr('checked')) { // 雇主的計算方式不同
 				if ( (parseInt($('#incomeAmount').val()) - parseInt($('#bossHealthInsuranceAmount').val())>=minGate) ) { // 小於 5000元，不收補充保費, 2016/1/1 改為 20000
 	 				$('#insuranceAddOnFee').val(parseInt($('#incomeAmount').val()) - parseInt($('#bossHealthInsuranceAmount').val()) < 10000000 ? (parseInt($('#incomeAmount').val()) - parseInt($('#bossHealthInsuranceAmount').val()))*rate : 10000000*rate);
 	 			} else {
 	 				$('#insuranceAddOnFee').val('0');
 	 			}
 			} else {
 				if ($('#incomeAmount').val()>=minGate) { // 小於 5000元，不收補充保費, 2016/1/1 改為 20000
 	 				$('#insuranceAddOnFee').val(parseInt($('#incomeAmount').val()) < 10000000 ? parseInt($('#incomeAmount').val())*rate : 10000000*rate);
 	 			} else {
 	 				$('#insuranceAddOnFee').val('0');
 	 			}
 			}
 			break;
 		case 67:
 			$('#incomeAmount').val($('#incomeAmount67').val());
 			if ($('#incomeAmount').val()>=minGate) { // 小於 5000元，不收補充保費, 2016/1/1 改為 20000
 				$('#insuranceAddOnFee').val(parseInt($('#incomeAmount').val())<10000000 ? parseInt($('#incomeAmount').val())*rate : 10000000*rate);
 			} else {
 				$('#insuranceAddOnFee').val('0');
 			}
 			break;
 		case 68:
 			$('#incomeAmount').val($('#incomeAmount68').val());
 			if ($('#incomeAmount').val()>=minGate) { // 小於 5000元，不收補充保費, 2016/1/1 改為 20000
 				$('#insuranceAddOnFee').val(parseInt($('#incomeAmount').val()) < 10000000 ? parseInt($('#incomeAmount').val())*rate : 10000000*rate);
 			} else {
 				$('#insuranceAddOnFee').val('0');
 			}
 			break;
 		default:
 			break;
 		}
 		
 		// 把小數點去掉，四捨五入
 		$('#insuranceAddOnFee').val(Math.round($('#insuranceAddOnFee').val()));
 	}
 	
 	function setTrustNote() {
 		switch(parseInt($('#incomeType').val())) {
 		case 66:
 			if ($("#trustNote66").attr('checked')) {
 				$("#trustNote").val("T");
 			} else {
 				$("#trustNote").val("");
 			}
 			break;
 		case 67:
 			if ($("#trustNote67").attr('checked')) {
 				$("#trustNote").val("T");
 			} else {
 				$("#trustNote").val("");
 			}
 			break;
 		case 68:
 			if ($("#trustNote68").attr('checked')) {
 				$("#trustNote").val("T");
 			} else {
 				$("#trustNote").val("");
 			}
 			break;
 		default:
 			break;
 		}
 	}
  //-->
  </script>

  <form method="POST" Name="mainform" id="mainform" Action="<%=response.encodeURL("insuranceAddOn.do?action=updateInsuranceAddOnFee") %>" class = "entry-form">
  <table width="100%"  border="0" cellpadding="0" cellspacing="0">
    <tr><td colspan=3>
      <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
        <TBODY>
          <tr>
            <td height="30" class=moduleTitle valign="bottom">補充保費資料設定</td>
          </tr>
          <TR><TD>
            <DIV class=hline><IMG height=1 src="images/blank.gif"></DIV>
          </TD></TR>
      </TBODY></TABLE><BR>
    </td></tr>

    <tr><td colspan=3>
      <TABLE class=formOuterBorder cellSpacing=0 cellPadding=0 width="100%" border=0><TBODY>
      	<TR><TD>
	        <TABLE cellSpacing=1 cellPadding=0 width="100%" border=0>
	          <TBODY><TR><TH class=formSecHeader align=left>補充保費資料</TH></TR></TBODY>
	        </TABLE>
	        <TABLE cellSpacing=1 cellPadding=2 width="100%" border=0><TBODY>
	          <tr>
	            <td class=dataLabel><div align="right">所得人身份證號碼：</div></td>
	            <td colspan="2" align="left"><input name="unicode" id="unicode" value="<%=insuranceAddOnFee.getUnicode()%>" type="text" class="textfield" size="15" maxlength="20" onBlur="findIncomeEarner();"></td>
	          </tr>
	          <tr>
	            <td class=dataLabel><div align="right">所得人姓名：</div></td>
	            <td colspan="2" align="left"><input name="name" id="name" readonly="readonly" value="<%=insuranceAddOnFee.getName()%>" type="text" class="textfield" size="20" maxlength="20"></td>
	          </tr>
	          <tr>
	            <td class=dataLabel><div align="right">所得給付日期：</div></td>
	            <td colspan="2" align="left">
	              <input name="incomeDate" id="incomeDate" value="<%=!insuranceAddOnFee.getIncomeDate().equals("")?StringUtils.adToTw(insuranceAddOnFee.getIncomeDate()):today%>" type="text" class="textfield" size="10" maxlength="10" onChange='chkDate("incomeDate");'> (格式 YY-MM-DD 例如 96-5-24)
	              &nbsp;<A href="javascript:sDate('incomeDate')"><IMG src="images/calendar.gif" border="0"></A>
	            </td>
	          </tr>
	          <tr>
		      	<td class=dataLabel width="20%"><div align="right">所得類別：</div></td>
		        <td colspan="2" align="left">
		              <select id="incomeType" name="incomeType" onChange='setIncomeType();'>
	                	<option value="">&nbsp;</option>
	                	<option value="62" <%=insuranceAddOnFee.getIncomeType().equals("62")?"selected":"" %>>逾投保金額四倍之獎金</option>
	                	<option value="63" <%=insuranceAddOnFee.getIncomeType().equals("63")?"selected":"" %>>非所屬投保單位給付之薪資所得</option>
	                	<option value="65" <%=insuranceAddOnFee.getIncomeType().equals("65")?"selected":"" %>>執行業務收入</option>
	                	<option value="66" <%=insuranceAddOnFee.getIncomeType().equals("66")?"selected":"" %>>股利所得</option>
	                	<option value="67" <%=insuranceAddOnFee.getIncomeType().equals("67")?"selected":"" %>>利息所得</option>
	                	<option value="68" <%=insuranceAddOnFee.getIncomeType().equals("68")?"selected":"" %>>租金收入</option>
	            	 </select>	        
	             </td>
		      </tr>          	
	        </TBODY></TABLE>
	        
	        <TABLE cellSpacing=1 cellPadding=2 width="100%" border=0 id="info62" STYLE='DISPLAY:<%=insuranceAddOnFee.getIncomeType().equals("62")?"block":"none"%>'><TBODY>
	          <tr>
	            <td class=dataLabel width="20%"><div align="right">給付時月投保金額：</div></td>
	            <td align="left"><input name="healthInsuranceSalary" id="healthInsuranceSalary" value="<%=insuranceAddOnFee.getHealthInsuranceSalary()%>" type="text" class="textfield" size="10" maxlength="10"></td>
	          </tr>
	          <tr>
	            <td class=dataLabel width="20%"><div align="right">本次給付獎金總額：</div></td>
	            <td align="left"><input name="incomeAmount62" id="incomeAmount62" value="<%=insuranceAddOnFee.getIncomeAmount()%>" type="text" class="textfield" size="10" maxlength="10" onBlur='calcInsuranceAddOnFee();'></td>
	          </tr>
	          <tr>
	            <td class=dataLabel width="20%"><div align="right">同年度累計獎金金額：</div></td>
	            <td align="left"><input name="accumulatedBonusAmount" id="accumulatedBonusAmount" value="<%=insuranceAddOnFee.getAccumulatedBonusAmount()%>" type="text" class="textfield" size="10" maxlength="10"></td>
	          </tr>
	        </TBODY></TABLE>
	        
	        <TABLE cellSpacing=1 cellPadding=2 width="100%" border=0 id="info63" STYLE='DISPLAY:<%=insuranceAddOnFee.getIncomeType().equals("63")?"block":"none"%>'><TBODY>
	          <tr>
	            <td class=dataLabel width="20%"><div align="right">本次給付兼職收入總額：</div></td>
	            <td align="left"><input name="incomeAmount63" id="incomeAmount63" value="<%=insuranceAddOnFee.getIncomeAmount()%>" type="text" class="textfield" size="10" maxlength="10" onBlur='calcInsuranceAddOnFee();'></td>
	          </tr>
	        </TBODY></TABLE>
	        
	        <TABLE cellSpacing=1 cellPadding=2 width="100%" border=0 id="info65" STYLE='DISPLAY:<%=insuranceAddOnFee.getIncomeType().equals("65")?"block":"none"%>'><TBODY>
	          <tr>
	            <td class=dataLabel width="20%"><div align="right">本次給付執行業務收入總額：</div></td>
	            <td align="left"><input name="incomeAmount65" id="incomeAmount65" value="<%=insuranceAddOnFee.getIncomeAmount()%>" type="text" class="textfield" size="10" maxlength="10" onBlur='calcInsuranceAddOnFee();'></td>
	          </tr>
	        </TBODY></TABLE>
	        
	        <TABLE cellSpacing=1 cellPadding=2 width="100%" border=0 id="info66" STYLE='DISPLAY:<%=insuranceAddOnFee.getIncomeType().equals("66")?"block":"none"%>'><TBODY>
	          <tr>
	          	<td class=dataLabel width="20%"><div align="right">股利註記：</div></td>
		        <td align="left">
		              <select id="stockNote" name="stockNote">
	                	<option value="">&nbsp;</option>
	                	<option value="1" <%=insuranceAddOnFee.getStockNote().equals("1")?"selected":"" %>>股票股利</option>
	                	<option value="2" <%=insuranceAddOnFee.getStockNote().equals("2")?"selected":"" %>>現金股利</option>
	                	<option value="3" <%=insuranceAddOnFee.getStockNote().equals("3")?"selected":"" %>>股票股利及現金股利</option>
	            	 </select>&nbsp;&nbsp;
	            	 <input type="checkbox" id="isBoss" name="isBoss" value="Y" <%=insuranceAddOnFee.getIsBoss().equals("Y")?"checked":"" %> onClick="setIsBoss();"/>雇主	        
	             </td>
	          </tr>   
	          <tr>
	            <td class=dataLabel width="20%"><div align="right">本次給付股利所得總額：</div></td>
	            <td align="left">
	            	<input name="incomeAmount66" id="incomeAmount66" value="<%=insuranceAddOnFee.getIncomeAmount()%>" type="text" class="textfield" size="10" maxlength="10" onBlur='calcInsuranceAddOnFee();'>&nbsp;&nbsp;
	            	<input type="checkbox" id="trustNote66" name="trustNote66" value="T" <%=insuranceAddOnFee.getTrustNote().equals("T")?"checked":"" %> onClick="setTrustNote();"/>信託註記
	            </td>
	          </tr>
	          <tr id="bossInfo" STYLE='DISPLAY:<%=insuranceAddOnFee.getIsBoss().equals("Y")?"block":"none"%>'>
	            <td class=dataLabel width="20%"><div align="right">本次股利所屬期間，以雇主身份之加保總金額：</div></td>
	            <td align="left"><input name="bossHealthInsuranceAmount" id="bossHealthInsuranceAmount" value="<%=insuranceAddOnFee.getBossHealthInsuranceAmount()%>" type="text" class="textfield" size="10" maxlength="10" onBlur='calcInsuranceAddOnFee();'></td>
	          </tr>	 
	          <tr>
	            <td class=dataLabel width="20%"><div align="right">扣取時可扣抵稅額：</div></td>
	            <td align="left"><input name="ICAAmount" id="ICAAmount" value="<%=insuranceAddOnFee.getICAAmount()%>" type="text" class="textfield" size="10" maxlength="10"></td>
	          </tr>	      
	          <tr>
	            <td class=dataLabel width="20%"><div align="right">年度確定可扣抵稅額：</div></td>
	            <td align="left"><input name="annualICAAmount" id="annualICAAmount" value="<%=insuranceAddOnFee.getAnnualICAAmount()%>" type="text" class="textfield" size="10" maxlength="10"></td>
	          </tr>	 
	          <tr>
	            <td class=dataLabel width="20%"><div align="right">除權息基準日：</div></td>
	            <td align="left">
	            	<input name="excludeDate" id="excludeDate" value="<%=!insuranceAddOnFee.getExcludeDate().equals("")?StringUtils.adToTw(insuranceAddOnFee.getExcludeDate()):""%>" type="text" class="textfield" size="10" maxlength="10" onChange='chkDate("excludeDate");'> (格式 YY-MM-DD 例如 95-3-21)&nbsp;
	                <A href="javascript:sDate('excludeDate')"><IMG src="images/calendar.gif" border="0"></A>
	            </td>
	          </tr>	
	        </TBODY></TABLE>
	        
	        <TABLE cellSpacing=1 cellPadding=2 width="100%" border=0 id="info67" STYLE='DISPLAY:<%=insuranceAddOnFee.getIncomeType().equals("67")?"block":"none"%>'><TBODY>
	          <tr>
	            <td class=dataLabel width="20%"><div align="right">本次給付利息所得總額：</div></td>
	            <td align="left">
	            	<input name="incomeAmount67" id="incomeAmount67" value="<%=insuranceAddOnFee.getIncomeAmount()%>" type="text" class="textfield" size="10" maxlength="10" onBlur='calcInsuranceAddOnFee();'>&nbsp;&nbsp;
	            	<input type="checkbox" id="trustNote67" name="trustNote67" value="T" <%=insuranceAddOnFee.getTrustNote().equals("T")?"checked":"" %> onClick="setTrustNote();"/>信託註記
	            </td>
	          </tr>
	        </TBODY></TABLE>
	        
	        <TABLE cellSpacing=1 cellPadding=2 width="100%" border=0 id="info68" STYLE='DISPLAY:<%=insuranceAddOnFee.getIncomeType().equals("68")?"block":"none"%>'><TBODY>
	          <tr>
	            <td class=dataLabel width="20%"><div align="right">本次給付租金收入總額：</div></td>
	            <td align="left">
	            	<input name="incomeAmount68" id="incomeAmount68" value="<%=insuranceAddOnFee.getIncomeAmount()%>" type="text" class="textfield" size="10" maxlength="10" onBlur='calcInsuranceAddOnFee();'>&nbsp;&nbsp;
	            	<input type="checkbox" id="trustNote68" name="trustNote68" value="T" <%=insuranceAddOnFee.getTrustNote().equals("T")?"checked":"" %> onClick="setTrustNote();"/>信託註記
	            </td>
	          </tr>
	        </TBODY></TABLE>
	        <hr/>
	        
	        <TABLE cellSpacing=1 cellPadding=2 width="100%" border=0><TBODY>
	          <tr>
	            <td class=dataLabel width="20%"><div align="right">本次扣繳補充保險費金額：</div></td>
        		<td align="left"><input name="insuranceAddOnFee" id="insuranceAddOnFee" value="<%=insuranceAddOnFee.getInsuranceAddOnFee ()%>" type="text" class="textfield" size="15" maxlength="10"></td>
	          </tr>
	        </TBODY></TABLE>
      	</TD></TR>      
      </TBODY></TABLE>
    </td></tr>

    <tr>
      <td height="30" colspan="3">
        <div align="right">
          <input name="Submit0" type="button" id="btnUpdate" class='ui-state-default' value="儲存" onClick='doUpdate();'>
          <%if (insuranceAddOnFee.getSerialNo() > 0) {%><input name="Submit1" type="button" class='ui-state-default' value="刪除" onClick='doRemove();'><% } %>
          <input name="Submit2" type="button" class='ui-state-default' value="取消" onClick='doCancel();'>
        </div></td>
    </tr>
  </table>

  	<input type="hidden" id="serialNo" name="serialNo" value='<%=insuranceAddOnFee.getSerialNo() %>' />
  	<input type="hidden" id="trustNote" name="trustNote" value='<%=insuranceAddOnFee.getTrustNote() %>' />
  	<input type="hidden" id="incomeAmount" name="incomeAmount" value='<%=insuranceAddOnFee.getIncomeAmount() %>' />
  	<input type="hidden" id="address" name="address" value='<%=insuranceAddOnFee.getAddress() %>' />
  </form>
</body>
</html>
