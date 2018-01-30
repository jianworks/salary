$.fn.dataTableExt.oApi.fnReloadAjax = function (oSettings, sNewSource, fnCallback, bStandingRedraw) {
		if (typeof sNewSource != 'undefined' && sNewSource != null) {
				oSettings.sAjaxSource = sNewSource;
		}
		this.oApi._fnProcessingDisplay(oSettings, true);
		var that = this;
		var iStart = oSettings._iDisplayStart;

		oSettings.fnServerData(oSettings.sAjaxSource, [], function (json) {
				/* Clear the old information from the table */
				that.oApi._fnClearTable(oSettings);

				/* Got the data - add it to the table */
				if (json.aaData.length == 0) {
						alert("查詢結果沒有資料 !");
				}
				for (var i = 0; i < json.aaData.length; i++) {
						that.oApi._fnAddData(oSettings, json.aaData[i]);
				}

				oSettings.aiDisplay = oSettings.aiDisplayMaster.slice();
				that.fnDraw();

				if (typeof bStandingRedraw != 'undefined' && bStandingRedraw === true) {
						oSettings._iDisplayStart = iStart;
						that.fnDraw(false);
				}

				that.oApi._fnProcessingDisplay(oSettings, false);

				/* Callback user function - for event handlers etc */
				if (typeof fnCallback == 'function' && fnCallback != null) {
						fnCallback(oSettings);
				}
		}, oSettings);
}

$.postJSON = function (url, data, callback) {
		$.post(url, data, callback, "json");
}

var info = "<span class='ui-icon ui-icon-info' style='float:left;'></span>";

function checkAllChild() {
		if ($("#allSelected").attr('checked')) {
				$("input[name='selected']").attr("checked", true);
		} else {
				$("input[name='selected']").attr("checked", false);
		}
}

function checkParent() {
		if ($("input[name='selected']:checked").length == $("input[name='selected']").length) {
				$("#allSelected").attr('checked', true);
		} else {
				$("#allSelected").attr('checked', false);
		}
}