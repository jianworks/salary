package com.csjian.util;

import java.util.Calendar;

public class NHIDate {
	public static String verDate() {
		return "101/11/23";
	}
	
	public static String sysDate() {
        Calendar cal = Calendar.getInstance();
        return (cal.get(Calendar.YEAR) - 1911) + ((cal.get(Calendar.MONTH) + 1) > 9 ?"":"0") + (cal.get(Calendar.MONTH) + 1) + ((cal.get(Calendar.DATE)) > 9 ?"":"0") + (cal.get(Calendar.DATE));
    }
	
	public static String sysTime() {
		Calendar cal = Calendar.getInstance();
		return ((cal.get(Calendar.HOUR)) > 9 ?"":"0") + (cal.get(Calendar.HOUR)) + ((cal.get(Calendar.MINUTE)) > 9 ?"":"0") + (cal.get(Calendar.MINUTE)) + ((cal.get(Calendar.SECOND)) > 9 ?"":"0") + (cal.get(Calendar.SECOND));
    }
	
	public static String convertDateToChi(Calendar cal) {
		return (cal.get(Calendar.YEAR) - 1911) + ((cal.get(Calendar.MONTH) + 1) > 9 ?"":"0") + (cal.get(Calendar.MONTH) + 1) + ((cal.get(Calendar.DATE)) > 9 ?"":"0") + (cal.get(Calendar.DATE));
    }
	
	/**
	 * @param strDate	7 碼的民國日期, ex: 1020225
	 * @return	西元日期, ex: 2013/02/25
	 */
	public static String convertDateToEng(String strDate) {
		if (strDate.length() != 7)
			return "";
        return (Integer.parseInt(strDate.substring(0, 3)) + 1911) + "/" + strDate.substring(3, 5) + "/" + strDate.substring(5);
	}
	
	
	/**
	 * @param strDate	7 碼的民國日期, ex: 1020225
	 * @param YMD	要加的欄位，只可以是 Y, M, D 這三個值
	 * @param n	要加的數值
	 * @return	7 碼的民國日期
	 */
	public static String dateAddYMD(String strDate, String YMD, int n) {
		if (strDate.length() != 7)
			return "";
		Calendar cal = Calendar.getInstance();
		cal.set(Integer.parseInt(strDate.substring(0, 3)) + 1911, Integer.parseInt(strDate.substring(3, 5)) - 1, Integer.parseInt(strDate.substring(5)));
		if (YMD.equals("Y")) 
			cal.add(Calendar.YEAR, n);
		else if (YMD.equals("M"))
			cal.add(Calendar.MONTH, n);
		else if (YMD.equals("D"))
			cal.add(Calendar.DATE, n);
		return convertDateToChi(cal);
    }
	
	
	/**
	 * 計算日期所在月份有幾天
	 * @param strDate	7 碼的民國日期, ex: 1020225
	 * @return	 日期所在月份的天數
	 */
	public static int monthDays(String strDate) {
		Calendar cal = Calendar.getInstance();
		cal.set(Integer.parseInt(strDate.substring(0, 3)) + 1911, Integer.parseInt(strDate.substring(3, 5)) - 1, Integer.parseInt(strDate.substring(5)));

		return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
    }
	
	
	/**
	 * 取得日期所在月份的第一天
	 * @param cal
	 * @return	7 碼的民國日期, ex: 1020201
	 */
	public static String firstOfThisMonth(Calendar cal) {
		cal.set(Calendar.DATE, 1);
		return convertDateToChi(cal);

    }
	
	public static String lastOfThisMonth(String strDate) {
		if (strDate.length() != 7)
			return "";
		Calendar cal = Calendar.getInstance();
		cal.set(Integer.parseInt(strDate.substring(0, 3)) + 1911, Integer.parseInt(strDate.substring(3, 5)) - 1, Integer.parseInt(strDate.substring(5)));
		cal.add(Calendar.MONTH, 1);
		cal.add(Calendar.DATE, -1);
		return NHIDate.convertDateToChi(cal);
	}
	
	/**
	 * 轉換民國日期格式加上 / 符號
	 * @param strDate
	 * @return	7 碼的民國日期, ex: 1020201
	 */
	public static String dataFormatStr2(String strDate) {
		strDate = "0000000" + strDate;
        String str = strDate.substring(strDate.length()-7);
        return str.substring(0, 3) + "/" + str.substring(3, 5) + "/" + str.substring(5);
    }
}
