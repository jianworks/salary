package com.csjian.util;

public class NHIBarcode {	

	/** 
	 * 轉換民國年月變成3位條碼
	 * @param pYYYMM	民國年月, ex: 10203
	 * @return
	 */
	public static String tranBarCodeYYM(String pYYYMM) {
        String inputStr = (Integer.parseInt(pYYYMM.substring(0, 3)) + 1911) + pYYYMM.substring(3, 5);
        String tmp = "00000" + ((Integer.parseInt(inputStr) - 191100) + (int)((Double.parseDouble(inputStr)-200300.0)/1000.0)*20);
        tmp = tmp.substring(tmp.length()-6);
        return tmp.substring(3, 6);
    }
  
	/**
	 * @param s14Barcode
	 * @param premAmt
	 * @param dueDate 民國日期, ex: 1020301
	 * @return
	 * @throws Exception
	 */
	public static String getCheckCode1(String s14Barcode, String premAmt, String dueDate) throws Exception {
		String checkCode;
		int num1 = 1;
		int num2 = 3;
      
		if (s14Barcode.length() != 14 || dueDate.length() != 7)
			throw new Exception ("s14Barcode.length() != 14 || dueDate.length() != 7");
		premAmt = String.format("%07d", Integer.parseInt(premAmt)); // 左邊補 0 成 7 碼
		dueDate = dueDate.substring(1); // 日期只取右邊的 6 碼
		String str1 = "11699" + s14Barcode + premAmt + dueDate;
		if (str1.length() != 32)
			throw new Exception ("str1.length() != 32");
		String str2 = str1.substring(0, 16); // 取前16碼
		String str3 = str1.substring(16); // 取後16碼
		int Start1 = 0;
		int num3 = 0;
		do {
			num3 = (int)Math.round(((double)num3) + Double.parseDouble(str2.charAt(Start1) + "") * ((double)num1));
        	Start1 += 2;
		} while (Start1 < 16);
		int Start2 = 1;
		do {
			num3 = (int)Math.round(((double)num3) + Double.parseDouble(str2.charAt(Start2) + "") * ((double)num2));
        	Start2 += 2;
		} while (Start2 < 16);
		
		int Start3 = 0;
		int num4 = 0;
		do {
			num4 = (int)Math.round(((double)num4) + Double.parseDouble(str3.charAt(Start3) + "") * ((double)num1));
			Start3 += 2;
		} while (Start3 < 16);
		int Start4 = 1;
		do {
			num4 = (int)Math.round(((double)num4) + Double.parseDouble(str3.charAt(Start4) + "") * ((double)num2));
			Start4 += 2;
		} while (Start4 < 16);
      
		String str4 = num3 % 10 != 0 ? (10 - (num3 % 10)) + "" : "0";
		String str5 = num4 % 10 != 0 ? (10 - (num4 % 10)) + "" : "0";
		checkCode = str4 + str5;
		return checkCode;
    }
	
    public static String getCheckCode2(String barcode1, String barcode2, String barcode3) {
        int num1 = 0;
        int num2 = 0;
        int num3 = 0;
        int num4 = 0;
        int num5 = 0;
        int num6 = 0;
        
        int length1 = barcode1.trim().length();
        int Start1 = 0;
        while (Start1 < length1) {
            if (Start1 % 2 == 1)
                num1 = (int)Math.round((double)num1 + Double.parseDouble(NHIBarcode.getCodeNum(barcode1.charAt(Start1)) ));
            else
                num4 = (int)Math.round((double)num4 + Double.parseDouble(NHIBarcode.getCodeNum(barcode1.charAt(Start1)) ));
            ++Start1;
        }
        
        int length2 = barcode2.trim().length();
        int Start2 = 0;
        while (Start2 < length2) {
            if (Start2 % 2 == 1)
                num2 = (int)Math.round((double)num2 + Double.parseDouble(NHIBarcode.getCodeNum(barcode2.charAt(Start2)) ));
            else
                num5 = (int)Math.round((double)num5 + Double.parseDouble(NHIBarcode.getCodeNum(barcode2.charAt(Start2)) ));
            ++Start2;
        }
        
        int length3 = barcode3.trim().length();
        int Start3 = 0;
        while (Start3 < length3) {
            if (Start3 != 4 && Start3 != 5) {
                if (Start3 % 2 == 1)
                    num3 = (int)Math.round((double)num3 + Double.parseDouble(NHIBarcode.getCodeNum(barcode3.charAt(Start3)) ));
                else
                    num6 = (int)Math.round((double)num6 + Double.parseDouble(NHIBarcode.getCodeNum(barcode3.charAt(Start3)) ));
            }
            ++Start3;
        }
        return ((num4 + num5 + num6) % 11 != 0 ? ((num4 + num5 + num6) % 11 != 10 ? ((num4 + num5 + num6) % 11) + "" : "B") : "A") + ((num1 + num2 + num3) % 11 != 0 ? ((num1 + num2 + num3) % 11 != 10 ? ((num1 + num2 + num3) % 11) + "" : "Y") : "X");
    }
	
	
	public static String getCodeNum(char sIdEng) {
		int num = sIdEng;
		String str;
		if (num >= 65 & num <= 90) {
			str = ((num - 64) % 9) + "";
			System.out.println("str=" + str);
			if (Double.parseDouble(str) == 0.0)
				str = "9";
		} else
			str = sIdEng + "";
		return str;
	}
}
