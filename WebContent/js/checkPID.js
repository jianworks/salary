// 身分證字號檢查器
    function CheckPID(sPID) {
	  if (sPID == '') {
		return false;
	  } else if (sPID.length != 10) {
		return false;
	  } else {
		sPID = trim(sPID.toUpperCase());
		if (!chkPID_CHAR(sPID)) return false;
		var iChkNum = getPID_SUM(sPID);
		if (iChkNum % 10 != 0) {
		  return false;
		}
	  }
	  return true;
    }
 
    var ALP_STR = "ABCDEFGHJKLMNPQRSTUVXYWZIO";
    var NUM_STR = "0123456789";
    var SEX_STR = "12";
    var MAX_COUNT = 999;
    //身份證字號檢查器 - 累加檢查碼
    function getPID_SUM(sPID) {
	  var iChkNum = 0;
	  // 第 1 碼
	  iChkNum = ALP_STR.indexOf(sPID.substr(0,1)) + 10;
	  iChkNum = Math.floor(iChkNum/10) + (iChkNum%10*9);
	  // 第 2 - 9 碼
	  for(var i=1; i<sPID.length-1; i++) {
		iChkNum += sPID.substr(i,1) * (9-i);
	  }

	  // 第 10 碼
	  iChkNum += sPID.substr(9,1)*1;
	  return iChkNum;
    }

    // 身分證字號檢查器 - 檢查合法字元
    function chkPID_CHAR(sPID) {
	  var sMsg = "";
	  //sPID = trim(sPID.toUpperCase());
	  var iPIDLen = String(sPID).length;

	  var sChk = ALP_STR + NUM_STR;
	  for(i=0;i<iPIDLen;i++) {
		if (sChk.indexOf(sPID.substr(i,1)) < 0) {
		  sMsg = "這個身分證字號含有不正確的字元！";
		  break;
		}
	  }

	  if (sMsg.length == 0) {
		if (ALP_STR.indexOf(sPID.substr(0,1)) < 0) {
		  sMsg = "身分證字號第 1 碼應為英文字母(A~Z)。";
		} else if ((sPID.substr(1,1) != "1") && (sPID.substr(1,1) != "2")) {
		  sMsg = "身分證字號第 2 碼應為數字(1~2)。";
		} else {
		  for(var i=2; i<iPIDLen; i++) {
			if (NUM_STR.indexOf(sPID.substr(i, 1)) < 0) {
			  sMsg = "第 " + (i+1) + " 碼應為數字(0~9)。";
			  break;
			}
		  }
		}
	  }
	  if (sMsg.length != 0) {
		return false;
	  } else {
		return true;
	  }
    }
    
    function trim(str) {
	  while (str.indexOf(" ")==0) {
		str = str.substring(1, str.length);
	  }
	  while ((str.length>0) && (str.indexOf(" ")==(str.length-1))) {
		str = str.substring(0, str.length-1);
	  }
	  return str;
    }