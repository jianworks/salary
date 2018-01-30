package com.csjian.util;

import java.security.*;
import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * @author Administrator
 *
 */
public class StringUtils {
  public static String getMD5String(String input) {      
    try {
      /*
    	java.security.MessageDigest md = null; 
      md = MessageDigest.getInstance("MD5");
      String result = new String(md.digest(input.getBytes()));
      return (new sun.misc.BASE64Encoder()).encode( result.getBytes() ); 
      */
    	return input;
    } catch (Exception e) {
      return null;
    }
  }
  
  public static String addComma(String original) {
  	String newstr = "";
  	if (original != null&&original.length()>0) {
      int count = ((original.length()-1)/3);
      int lastchar = original.length();
      for (int i=0; i<count; i++) {
    	  newstr = "," + original.substring(lastchar-3, lastchar) + newstr;
    	  lastchar -=3;      	
      }
      newstr =original.substring(0, lastchar) + newstr;
  	} else {
  		newstr = "";
  	}
    return newstr;
  }
  
  public static String twToAd(String tw) {
  	String ad = "";
  	if (tw != null) {
  		try {
  			String[] dates = tw.split("-");
  			ad = (Integer.parseInt(dates[0])+1911) + "-" + dates[1] + "-" + dates[2];
  		} catch (Exception e) {
  			ad = "";
  		}
  	}
  	return ad;
  }
  
  
  /**
   * 轉換西元日期字串變成民國日期字串
   * @param ad	西元日期字串 ex. 2012-3-21
   * @return	民國日期字串 ex. 101-3-21
   */
  public static String adToTw(String ad) {
  	String tw = "";
  	try {
  		String[] dates = ad.split("-");
  		tw = (Integer.parseInt(dates[0])-1911) + "-" + dates[1] + "-" + dates[2];
  	} catch (Exception e) {
  	  tw = "";
  	}
  	return tw;
  }
  
  /**
   * 轉換西元日期字串變成民國日期字串碼
   * @param ad	西元日期字串 ex. 2012-3-21
   * @return	民國日期字串 ex. 1010321
   */
  public static String adToTwCode(String ad) {
  	String tw = "";
  	try {
  		String[] dates = ad.split("-");
  		tw = appendLeft0((Integer.parseInt(dates[0])-1911)+"", 3) + appendLeft0(dates[1], 2) + appendLeft0(dates[2], 2);
  	} catch (Exception e) {
  	  tw = "";
  	}
  	return tw;
  }
  
  	/**
	 * 在字串右邊補空白
	 * @param str	原始字串
	 * @param length	要變成的長度
	 * @param dBytes	是否為全形
	 * @return
	 */
	public static String appendBlank(String str, int length, boolean dBytes) {
		if (str.length() > length) {
			return str.substring(0, length);
		}
		StringBuilder sb = new StringBuilder(str);
		for (int i=str.length(); i<length; i++) {
			if (dBytes) 
				sb.append("　");
			else
				sb.append(" ");
		}
		return sb.toString();
	}
  
  	/**
	 * 在字串左邊補 0
	 * @param str	原始字串
	 * @param length	要變成的長度
	 * @return
	 */
	public static String appendLeft0(String str, int length) {
		if (str.length() > length) {
			return str.substring(0, length);
		}
		StringBuilder sb = new StringBuilder("");
		for (int i=str.length(); i<length; i++) {
			sb.append("0");
		}
		sb.append(str);
		return sb.toString();
	}
	
	/**
	 * 判斷字串是否為數字
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}
}
