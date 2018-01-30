package com.csjian.form;

import com.csjian.model.bean.*;
import com.csjian.util.NHIBarcode;
import com.csjian.util.NHIDate;
import com.csjian.util.StringUtils;

import java.io.*;
import java.util.*;

import com.lowagie.text.pdf.*;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;

public class M0305 extends PdfPageEventHelper {

	public void renderForm(String regcode, Vector insuranceAddOnFees, CompanyBean company, InputStream pdftemplate, OutputStream os) throws Exception {
		try {
			int count = insuranceAddOnFees.size();
			Document document = new Document(PageSize.A4, 5f, 5f, 10f, 10f);
			PdfWriter instance = PdfWriter.getInstance(document, os);
			document.open();
			for (int i=0; i<count; i++) {
				this.renderPage(regcode, (InsuranceAddOnFeeBean)insuranceAddOnFees.elementAt(i), company, document, instance, i+1);
			}
			document.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	private void renderPage(String regcode, InsuranceAddOnFeeBean insuranceAddOnFee, CompanyBean company, Document document, PdfWriter instance, int num1) throws Exception {
		boolean IsWeb = false;
		String str1 = System.getenv("windir");
		BaseFont font1 = BaseFont.createFont(str1 + "\\fonts\\KAIU.TTF", "Identity-H", true);
		BaseFont font2 = BaseFont.createFont(str1 + "\\fonts\\FRE3OF9X.TTF", "Identity-H", true);
		String m_PayYYYMM = (Integer.parseInt(insuranceAddOnFee.getIncomeDate().substring(0, 4))-1911) + insuranceAddOnFee.getIncomeDate().substring(5, 7);
		
	    Font[] fontArray = new Font[] {
	    	new Font(font1, 12f),
		    new Font(font1, 16f, 17),
		    new Font(font1, 12f, 17),
		    new Font(font1, 9f),
		    new Font(font1, 2f),
		    new Font(font1, 11f),
		    new Font(font1, 10f)
	    };
	    
	      int numColumns = 7;
	      float[] relativeWidths1 = new float[] {
	        4f,
	        17f,
	        9f,
	        10f,
	        10f,
	        1f,
	        9f
	      };
	      
	      PdfPTable pdfPtable1 = new PdfPTable(numColumns);
	      pdfPtable1.setWidths(relativeWidths1);
	      pdfPtable1.setWidthPercentage(94.5f);
	      float[] relativeWidths2 = new float[] {
	        16f,
	        12f,
	        10f,
	        5f,
	        1f,
	        8f
	      };
	      PdfPTable pdfPtable2 = new PdfPTable(6);
	      pdfPtable2.setWidths(relativeWidths2);
	      pdfPtable2.setWidthPercentage(90f);
	      //int num1 = 1;
	      if (num1 != 1)
	      {
	        document.add(pdfPtable1);
	        document.add(pdfPtable2);
	        document.newPage();
	        pdfPtable1.deleteBodyRows();
	        pdfPtable2.deleteBodyRows();
	      }
	      
	      PdfPCell cell1 = new PdfPCell(new Phrase("", fontArray[4]));
	      cell1.setFixedHeight(35f);
	      cell1.setHorizontalAlignment(1);
	      cell1.setVerticalAlignment(5);
	      cell1.setBorder(0);
	      cell1.setColspan(7);
	      pdfPtable1.addCell(cell1);
	      
	      PdfPCell cell2 = new PdfPCell(new Phrase("全民健康保險\n扣費義務人各類所得(收入)補充保險費繳款書", fontArray[1]));
	      cell2.setFixedHeight(55f);
	      cell2.setHorizontalAlignment(1);
	      cell2.setVerticalAlignment(5);
	      cell2.setLeading(4f, 1f);
	      cell2.setBorder(0);
	      cell2.setColspan(5);
	      pdfPtable1.addCell(cell2);
	      
	      PdfPCell cell3 = new PdfPCell(new Phrase("收據聯：\n本聯經代收機構收款\n蓋章後，交扣費義務\n人收執，作繳費憑證", fontArray[3]));
	      cell3.setHorizontalAlignment(0);
	      cell3.setVerticalAlignment(4);
	      cell3.setLeading(3f, 1f);
	      cell3.setColspan(2);
	      pdfPtable1.addCell(cell3);
	      
	      PdfPCell cella = new PdfPCell(new Phrase("單位統一編號：" + company.getRegcode() + "\n\n單位名稱：" + company.getRegname(), fontArray[0]));
	      cella.setFixedHeight(55f);
	      cella.setHorizontalAlignment(0);
	      cella.setVerticalAlignment(5);
	      cella.setColspan(7);
	      pdfPtable1.addCell(cella);
	      
	      PdfPCell cellb = new PdfPCell(new Phrase("所 得 類 別 及 代 號", fontArray[0]));
	      cellb.setFixedHeight(35f);
	      cellb.setHorizontalAlignment(1);
	      cellb.setVerticalAlignment(5);
	      cellb.setColspan(2);
	      pdfPtable1.addCell(cellb);
	      
	      PdfPCell cellc = new PdfPCell(new Phrase("給  付  年  月", fontArray[0]));
	      cellc.setHorizontalAlignment(1);
	      cellc.setVerticalAlignment(5);
	      cellc.setColspan(2);
	      pdfPtable1.addCell(cellc);
	      
	      PdfPCell celld = new PdfPCell(new Phrase("繳  款  期  限", fontArray[0]));
	      celld.setHorizontalAlignment(1);
	      celld.setVerticalAlignment(5);
	      celld.setColspan(2);
	      pdfPtable1.addCell(celld);
	      
	      PdfPCell celle = new PdfPCell(new Phrase("代收機構\n經收人員蓋章", fontArray[3]));
	      celle.setHorizontalAlignment(1);
	      celle.setVerticalAlignment(5);
	      pdfPtable1.addCell(celle);
	      
	      PdfPCell cellf = new PdfPCell(new Phrase(insuranceAddOnFee.getIncomeType() + "\n" + insuranceAddOnFee.getIncomeTypeDesc(), fontArray[0]));
	      cellf.setFixedHeight(35f);
	      cellf.setHorizontalAlignment(1);
	      cellf.setVerticalAlignment(5);
	      cellf.setColspan(2);
	      pdfPtable1.addCell(cellf);
	      
	      String str2 = m_PayYYYMM.substring(0, 3) + "/" + m_PayYYYMM.substring(3);
	      PdfPCell cellg = new PdfPCell(new Phrase(str2, fontArray[0]));
	      cellg.setHorizontalAlignment(1);
	      cellg.setVerticalAlignment(5);
	      cellg.setColspan(2);
	      pdfPtable1.addCell(cellg);
	      
	      String str3 = NHIDate.lastOfThisMonth(NHIDate.dateAddYMD(m_PayYYYMM + "01", "M", 1));
	      String str4 = NHIDate.dataFormatStr2(str3);	//due_date
	      
	      String Left = insuranceAddOnFee.getIncomeType();	//this.sUtl_GetBarIncomeType(this.m_IncomeType);
	      
	      // 如果是信託的話，要把繳款日變成次年 1/31
	      if (insuranceAddOnFee.getTrustNote().equals("T") && (Left.equals("66")||Left.equals("67")||Left.equals("68"))) {
	    	  Left = (Integer.parseInt(Left) + 10)+ "";
	    	  str4 = (NHIDate.dateAddYMD(m_PayYYYMM + "01", "Y", 1)).substring(0, 3) + "/01/31"; 
	      }
	      /*
	      if (Conversions.ToBoolean(this.m_bTrust))
	        Left = Conversions.ToString(Conversions.ToDouble(Left) + 10.0);
	      if (Operators.CompareString(Left, "76", false) == 0 || Operators.CompareString(Left, "77", false) == 0 || Operators.CompareString(Left, "78", false) == 0)
	        str4 = Strings.Left(clsDate.Utl_lastOfThisMonth(clsDate.Utl_DateAdd_YMD(this.m_PayYYYMM + "01", "Y", 1)), 3) + "/01/31";
	      else if (Operators.CompareString(this.m_PayYYYMM, "", false) != 0)
	      {
	        string str5 = clsDate.Utl_lastOfThisMonth(clsDate.Utl_DateAdd_YMD(this.m_PayYYYMM + "01", "M", 1));
	        str4 = Strings.Left(str5, 3) + "/" + Strings.Mid(str5, 4, 2) + "/" + Strings.Right(str5, 2);
	      }*/
	      PdfPCell cellh = new PdfPCell(new Phrase(str4, fontArray[0]));
	      cellh.setHorizontalAlignment(1);
	      cellh.setVerticalAlignment(5);
	      cellh.setColspan(2);
	      pdfPtable1.addCell(cellh);
	      
	      PdfPCell cell4 = new PdfPCell(new Phrase("", fontArray[0]));
	      cell4.setHorizontalAlignment(1);
	      cell4.setVerticalAlignment(5);
	      cell4.setBorderWidthTop(0.0f);
	      cell4.setBorderWidthBottom(0.0f);
	      cell4.setBorderWidthLeft(0.0f);
	      pdfPtable1.addCell(cell4);
	      
	      PdfPCell celli = new PdfPCell(new Phrase("應       繳       金       額", fontArray[0]));
	      celli.setFixedHeight(30f);
	      celli.setHorizontalAlignment(1);
	      celli.setVerticalAlignment(5);
	      celli.setColspan(3);
	      pdfPtable1.addCell(celli);
	      
	      PdfPCell cellj = new PdfPCell(new Phrase(StringUtils.addComma(insuranceAddOnFee.getInsuranceAddOnFee()), fontArray[0]));
	      cellj.setHorizontalAlignment(1);
	      cellj.setVerticalAlignment(5);
	      cellj.setColspan(3);
	      pdfPtable1.addCell(cellj);
	      
	      PdfPCell cell5 = new PdfPCell(new Phrase("", fontArray[0]));
	      cell5.setHorizontalAlignment(1);
	      cell5.setVerticalAlignment(5);
	      cell5.setBorderWidthTop(0.0f);
	      cell5.setBorderWidthLeft(0.0f);
	      pdfPtable1.addCell(cell5);
	      
	      int num2 = 13;
	      PdfPCell cell6 = new PdfPCell(new Phrase("說明：", fontArray[0]));
	      cell6.setFixedHeight(18f);
	      cell6.setHorizontalAlignment(0);
	      cell6.setVerticalAlignment(4);
	      cell6.setColspan(7);
	      cell6.setBorderWidthTop(0.0f);
	      cell6.setBorderWidthBottom(0.0f);
	      pdfPtable1.addCell(cell6);
	      
	      PdfPCell cell7 = new PdfPCell(new Phrase("一、", fontArray[3]));
	      cell7.setFixedHeight((float) num2);
	      cell7.setHorizontalAlignment(2);
	      cell7.setVerticalAlignment(4);
	      cell7.setBorderWidthTop(0.0f);
	      cell7.setBorderWidthRight(0.0f);
	      cell7.setBorderWidthBottom(0.0f);
	      pdfPtable1.addCell(cell7);
	      
	      PdfPCell cell8 = new PdfPCell(new Phrase("扣費義務人依健保法第31條規定扣取之補充保險費，應於寬限期限前繳納，逾期未繳納者，自寬限期滿之翌日起至完", fontArray[3]));
	      cell8.setHorizontalAlignment(0);
	      cell8.setVerticalAlignment(4);
	      cell8.setBorderWidthTop(0.0f);
	      cell8.setBorderWidthLeft(0.0f);
	      cell8.setBorderWidthBottom(0.0f);
	      cell8.setColspan(7);
	      pdfPtable1.addCell(cell8);
	      
	      PdfPCell cell9 = new PdfPCell(new Phrase("", fontArray[3]));
	      cell9.setFixedHeight((float) num2);
	      cell9.setHorizontalAlignment(2);
	      cell9.setVerticalAlignment(4);
	      cell9.setBorderWidthTop(0.0f);
	      cell9.setBorderWidthRight(0.0f);
	      cell9.setBorderWidthBottom(0.0f);
	      pdfPtable1.addCell(cell9);
	      
	      PdfPCell cell10 = new PdfPCell(new Phrase("納前1日止，每逾1日加徵其應納費額0.1%滯納金；加徵之滯納金額，以至應納費額之15%為限；逾寬限期限繳納保", fontArray[3]));
	      cell10.setHorizontalAlignment(0);
	      cell10.setVerticalAlignment(4);
	      cell10.setBorderWidthTop(0.0f);
	      cell10.setBorderWidthLeft(0.0f);
	      cell10.setBorderWidthBottom(0.0f);
	      cell10.setColspan(7);
	      pdfPtable1.addCell(cell10);
	      
	      PdfPCell cell11 = new PdfPCell(new Phrase("", fontArray[3]));
	      cell11.setFixedHeight((float) num2);
	      cell11.setHorizontalAlignment(2);
	      cell11.setVerticalAlignment(4);
	      cell11.setBorderWidthTop(0.0f);
	      cell11.setBorderWidthRight(0.0f);
	      cell11.setBorderWidthBottom(0.0f);
	      pdfPtable1.addCell(cell11);
	      
	      PdfPCell cell12 = new PdfPCell(new Phrase("險費者，其應繳之滯納金將另行通知繳納。", fontArray[3]));
	      cell12.setHorizontalAlignment(0);
	      cell12.setVerticalAlignment(4);
	      cell12.setBorderWidthTop(0.0f);
	      cell12.setBorderWidthLeft(0.0f);
	      cell12.setBorderWidthBottom(0.0f);
	      cell12.setColspan(7);
	      pdfPtable1.addCell(cell12);
	      
	      PdfPCell cell13 = new PdfPCell(new Phrase("二、", fontArray[3]));
	      cell13.setFixedHeight((float) num2);
	      cell13.setHorizontalAlignment(2);
	      cell13.setVerticalAlignment(4);
	      cell13.setBorderWidthTop(0.0f);
	      cell13.setBorderWidthRight(0.0f);
	      cell13.setBorderWidthBottom(0.0f);
	      pdfPtable1.addCell(cell13);
	      
	      PdfPCell cell14 = new PdfPCell(new Phrase("扣費義務人請持本繳款書至健保局委託代收金融機構繳納；倘繳納金額2萬元以下，亦可至統一、全家、萊爾富及OK", fontArray[3]));
	      cell14.setHorizontalAlignment(0);
	      cell14.setVerticalAlignment(4);
	      cell14.setColspan(6);
	      cell14.setBorderWidthTop(0.0f);
	      cell14.setBorderWidthLeft(0.0f);
	      cell14.setBorderWidthBottom(0.0f);
	      pdfPtable1.addCell(cell14);
	      
	      PdfPCell cell15 = new PdfPCell(new Phrase("", fontArray[3]));
	      cell15.setFixedHeight((float) num2);
	      cell15.setHorizontalAlignment(2);
	      cell15.setVerticalAlignment(4);
	      cell15.setBorderWidthTop(0.0f);
	      cell15.setBorderWidthRight(0.0f);
	      cell15.setBorderWidthBottom(0.0f);
	      pdfPtable1.addCell(cell15);
	      
	      PdfPCell cell16 = new PdfPCell(new Phrase("等便利商店繳費。\n", fontArray[3]));
	      cell16.setHorizontalAlignment(0);
	      cell16.setVerticalAlignment(4);
	      cell16.setColspan(4);
	      cell16.setBorder(0);
	      pdfPtable1.addCell(cell16);
	      
	      PdfPCell cell17 = new PdfPCell(new Phrase("", fontArray[4]));
	      cell17.setHorizontalAlignment(0);
	      cell17.setVerticalAlignment(4);
	      cell17.setColspan(2);
	      cell17.setBorderWidthTop(0.0f);
	      cell17.setBorderWidthLeft(0.0f);
	      cell17.setBorderWidthBottom(0.0f);
	      pdfPtable1.addCell(cell17);
	      
	      PdfPCell cell18 = new PdfPCell(new Phrase("三、", fontArray[3]));
	      cell18.setFixedHeight((float) num2);
	      cell18.setHorizontalAlignment(2);
	      cell18.setVerticalAlignment(4);
	      cell18.setBorderWidthTop(0.0f);
	      cell18.setBorderWidthRight(0.0f);
	      cell18.setBorderWidthBottom(0.0f);
	      pdfPtable1.addCell(cell18);
	      
	      PdfPCell cell19 = new PdfPCell(new Phrase("依健保法第35條第2項之規定，扣費義務人自本繳款書應繳納之日起，逾30日未繳納者，本局得移送行政執行。", fontArray[3]));
	      cell19.setHorizontalAlignment(0);
	      cell19.setVerticalAlignment(4);
	      cell19.setColspan(6);
	      cell19.setBorderWidthTop(0.0f);
	      cell19.setBorderWidthLeft(0.0f);
	      cell19.setBorderWidthBottom(0.0f);
	      pdfPtable1.addCell(cell19);
	      
	      PdfPCell cell20 = new PdfPCell(new Phrase("四、", fontArray[3]));
	      cell20.setFixedHeight((float) num2);
	      cell20.setHorizontalAlignment(2);
	      cell20.setVerticalAlignment(4);
	      cell20.setBorderWidthTop(0.0f);
	      cell20.setBorderWidthRight(0.0f);
	      cell20.setBorderWidthBottom(0.0f);
	      pdfPtable1.addCell(cell20);
	      
	      PdfPCell cell21 = new PdfPCell(new Phrase("依健保法第85條之規定，扣費義務人未依31條規定扣繳保險對象應負擔之補充保險費者，本局得限期補繳外，並", fontArray[3]));
	      cell21.setHorizontalAlignment(0);
	      cell21.setVerticalAlignment(4);
	      cell21.setBorderWidthTop(0.0f);
	      cell21.setBorderWidthLeft(0.0f);
	      cell21.setBorderWidthBottom(0.0f);
	      cell21.setColspan(7);
	      pdfPtable1.addCell(cell21);
	      
	      PdfPCell cell22 = new PdfPCell(new Phrase("", fontArray[3]));
	      cell22.setFixedHeight((float) num2);
	      cell22.setHorizontalAlignment(2);
	      cell22.setVerticalAlignment(4);
	      cell22.setBorderWidthTop(0.0f);
	      cell22.setBorderWidthRight(0.0f);
	      cell22.setBorderWidthBottom(0.0f);
	      pdfPtable1.addCell(cell22);
	      
	      PdfPCell cell23 = new PdfPCell(new Phrase("按應扣繳金額處一倍之罰鍰；未於限期內補繳者，處三倍之罰鍰。", fontArray[3]));
	      cell23.setHorizontalAlignment(0);
	      cell23.setVerticalAlignment(4);
	      cell23.setBorderWidthTop(0.0f);
	      cell23.setBorderWidthLeft(0.0f);
	      cell23.setBorderWidthBottom(0.0f);
	      cell23.setColspan(7);
	      pdfPtable1.addCell(cell23);
	      
	      String str6 = "501231600";
	      String str7 = NHIBarcode.tranBarCodeYYM(m_PayYYYMM);
	      String s14Barcode = (Left + company.getRegcode() + str7) + (IsWeb ? "1" : "6");
	      String due_date = NHIDate.lastOfThisMonth(NHIDate.dateAddYMD(m_PayYYYMM + "01", "M", 1));
	      String sCheckCode = NHIBarcode.getCheckCode1(s14Barcode, insuranceAddOnFee.getInsuranceAddOnFee(), due_date);

	      String str5 = sCheckCode + s14Barcode;
	      String amtTmp = "000000000" + insuranceAddOnFee.getInsuranceAddOnFee();
	      String str8 = m_PayYYYMM.substring(m_PayYYYMM.length() - 4) + "**" + amtTmp.substring(amtTmp.length() - 9);
	      String checkCode2 = NHIBarcode.getCheckCode2(str6, str5, str8);
	      String text = str8.substring(0, 4) + checkCode2 + str8.substring(6);
	      
	      PdfPCell cell24 = new PdfPCell(
					new Phrase(
							"  洽詢電話：0800-030598\n繳款單編號：" + str6
									+ "                                          列印日期："
									+ NHIDate.dataFormatStr2(NHIDate.convertDateToChi(Calendar.getInstance())) + "\n            " + str5
									+ "\n            " + text, fontArray[3]));
	      cell24.setFixedHeight(45f);
	        cell24.setHorizontalAlignment(0);
	        cell24.setVerticalAlignment(6);
	        cell24.setBorderWidthTop(0.0f);
	        cell24.setBorderWidthBottom(0.0f);
	        cell24.setBorderWidthRight(0.0f);
	        cell24.setColspan(7);
	        pdfPtable1.addCell(cell24);
	      /*
	      PdfPCell cell24 = new PdfPCell(new Phrase("   洽詢電話：0800-030598\n 繳款單編號：" + str6 + "\n             " + str5 + "\n             " + text, fontArray[3]));
	        cell24.setFixedHeight(45f);
	        cell24.setHorizontalAlignment(0);
	        cell24.setVerticalAlignment(6);
	        cell24.setBorderWidthTop(0.0f);
	        cell24.setBorderWidthBottom(0.0f);
	        cell24.setBorderWidthRight(0.0f);
	        cell24.setColspan(3);
	        pdfPtable1.addCell(cell24);
	        /*
	        PdfPCell cell25 = new PdfPCell(new Phrase("  列印日期：" + NHIDate.dataFormatStr2(NHIDate.convertDateToChi(Calendar.getInstance())), fontArray[3]));
	        cell25.setFixedHeight(45f);
	        cell25.setHorizontalAlignment(0);
	        cell25.setVerticalAlignment(Element.ALIGN_TOP);
	        cell25.setColspan(4);
	        cell25.setBorderWidthTop(0.0f);
	        cell25.setBorderWidthBottom(0.0f);
	        cell25.setBorderWidthLeft(0.0f);
	        pdfPtable1.addCell(cell25);
	        */
	        PdfPCell cell26 = new PdfPCell(new Phrase(" ", fontArray[3]));
	        cell26.setFixedHeight(15f);
	        cell26.setHorizontalAlignment(0);
	        cell26.setVerticalAlignment(6);
	        cell26.setBorderWidthTop(0.0f);
	        cell26.setColspan(7);
	        pdfPtable1.addCell(cell26);
	        
	        String str9 = "▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓";
	        PdfPCell cell27 = new PdfPCell(new Phrase(str9, fontArray[4]));
	        cell27.setFixedHeight(50f);
	        cell27.setHorizontalAlignment(1);
	        cell27.setVerticalAlignment(5);
	        cell27.setBorder(0);
	        cell27.setColspan(7);
	        pdfPtable1.addCell(cell27);
	        
	        PdfPCell cell28 = new PdfPCell(new Phrase(str9, fontArray[4]));
	        cell28.setFixedHeight(5f);
	        cell28.setHorizontalAlignment(1);
	        cell28.setVerticalAlignment(5);
	        cell28.setBorder(0);
	        cell28.setColspan(7);
	        pdfPtable1.addCell(cell28);
	        
	        PdfPCell cell29 = new PdfPCell(new Phrase("全民健康保險\n扣費義務人各類所得(收入)補充保險費繳款書", fontArray[1]));
	        cell29.setFixedHeight(55f);
	        cell29.setHorizontalAlignment(1);
	        cell29.setVerticalAlignment(4);
	        cell29.setLeading(4f, 1f);
	        cell29.setBorder(0);
	        cell29.setColspan(4);
	        pdfPtable2.addCell(cell29);
	        
	        PdfPCell cellk = new PdfPCell(new Phrase("代收機構存查聯", fontArray[3]));
	        cellk.setHorizontalAlignment(1);
	        cellk.setVerticalAlignment(5);
	        cellk.setColspan(2);
	        pdfPtable2.addCell(cellk);
	        
	        PdfPCell celll = new PdfPCell(new Phrase("條碼區", fontArray[0]));
	        celll.setFixedHeight(40f);
	        celll.setHorizontalAlignment(1);
	        celll.setVerticalAlignment(5);
	        pdfPtable2.addCell(celll);
	        
	        PdfPCell cellm = new PdfPCell(new Phrase("代收明細", fontArray[0]));
	        cellm.setFixedHeight(40f);
	        cellm.setHorizontalAlignment(1);
	        cellm.setVerticalAlignment(5);
	        cellm.setColspan(5);
	        pdfPtable2.addCell(cellm);
	        
	        PdfPCell cell30 = new PdfPCell(new Phrase("", fontArray[0]));
	        cell30.setFixedHeight(40f);
	        cell30.setHorizontalAlignment(1);
	        cell30.setVerticalAlignment(5);
	        cell30.setBorderWidthTop(0.0f);
	        cell30.setBorderWidthRight(0.0f);
	        cell30.setBorderWidthBottom(0.0f);
	        pdfPtable2.addCell(cell30);
	        
	        PdfPCell celln = new PdfPCell(new Phrase("單位統一編號", fontArray[0]));
	        celln.setHorizontalAlignment(8);
	        celln.setVerticalAlignment(5);
	        pdfPtable2.addCell(celln);
	        
	        PdfPCell cello = new PdfPCell(new Phrase(company.getRegcode(), fontArray[0]));
	        cello.setHorizontalAlignment(1);
	        cello.setVerticalAlignment(5);
	        pdfPtable2.addCell(cello);
	        
	        PdfPCell cellp = new PdfPCell(new Phrase("聯絡電話", fontArray[0]));
	        cellp.setHorizontalAlignment(1);
	        cellp.setVerticalAlignment(5);
	        cellp.setColspan(2);
	        pdfPtable2.addCell(cellp);
	        
	        PdfPCell cellq = new PdfPCell(new Phrase(company.getPhone(), fontArray[0]));
	        cellq.setHorizontalAlignment(1);
	        cellq.setVerticalAlignment(5);
	        pdfPtable2.addCell(cellq);
	        
	        PdfPCell cell31 = new PdfPCell(new Phrase("", fontArray[0]));
	        cell31.setFixedHeight(40f);
	        cell31.setHorizontalAlignment(1);
	        cell31.setVerticalAlignment(5);
	        cell31.setBorderWidthTop(0.0f);
	        cell31.setBorderWidthRight(0.0f);
	        cell31.setBorderWidthBottom(0.0f);
	        pdfPtable2.addCell(cell31);
	        
	        PdfPCell cellr = new PdfPCell(new Phrase("所得類別及代號", fontArray[0]));
	        cellr.setHorizontalAlignment(8);
	        cellr.setVerticalAlignment(5);
	        pdfPtable2.addCell(cellr);
	        
	        PdfPCell cells = new PdfPCell(new Phrase(insuranceAddOnFee.getIncomeType() + "\r" + insuranceAddOnFee.getIncomeTypeDesc(), fontArray[0]));
	        cells.setHorizontalAlignment(1);
	        cells.setVerticalAlignment(5);
	        pdfPtable2.addCell(cells);
	        
	        PdfPCell cellt = new PdfPCell(new Phrase("代收機構經手人員蓋章", fontArray[0]));
	        cellt.setHorizontalAlignment(1);
	        cellt.setVerticalAlignment(5);
	        cellt.setColspan(3);
	        pdfPtable2.addCell(cellt);
	        
	        PdfPCell cell32 = new PdfPCell(new Phrase("", fontArray[0]));
	        cell32.setFixedHeight(40f);
	        cell32.setHorizontalAlignment(1);
	        cell32.setVerticalAlignment(5);
	        cell32.setBorderWidthTop(0.0f);
	        cell32.setBorderWidthRight(0.0f);
	        cell32.setBorderWidthBottom(0.0f);
	        pdfPtable2.addCell(cell32);
	        
	        PdfPCell cellu = new PdfPCell(new Phrase("給付年月", fontArray[0]));
	        cellu.setHorizontalAlignment(8);
	        cellu.setVerticalAlignment(5);
	        pdfPtable2.addCell(cellu);
	        
	        PdfPCell cellv = new PdfPCell(new Phrase(str2, fontArray[0]));
	        cellv.setHorizontalAlignment(1);
	        cellv.setVerticalAlignment(5);
	        pdfPtable2.addCell(cellv);
	        
	        PdfPCell cell33 = new PdfPCell(new Phrase("", fontArray[0]));
	        cell33.setHorizontalAlignment(1);
	        cell33.setVerticalAlignment(5);
	        cell33.setColspan(3);
	        cell33.setBorderWidthTop(0.0f);
	        cell33.setBorderWidthLeft(0.0f);
	        cell33.setBorderWidthBottom(0.0f);
	        pdfPtable2.addCell(cell33);
	        
	        PdfPCell cell34 = new PdfPCell(new Phrase("", fontArray[0]));
	        cell34.setFixedHeight(40f);
	        cell34.setHorizontalAlignment(1);
	        cell34.setVerticalAlignment(5);
	        cell34.setBorderWidthTop(0.0f);
	        cell34.setBorderWidthRight(0.0f);
	        pdfPtable2.addCell(cell34);
	        
	        PdfPCell cellw = new PdfPCell(new Phrase("應繳金額", fontArray[0]));
	        cellw.setHorizontalAlignment(8);
	        cellw.setVerticalAlignment(5);
	        pdfPtable2.addCell(cellw);
	        
	        PdfPCell cellx = new PdfPCell(new Phrase(StringUtils.addComma(insuranceAddOnFee.getInsuranceAddOnFee()), fontArray[0]));
	        cellx.setHorizontalAlignment(1);
	        cellx.setVerticalAlignment(5);
	        pdfPtable2.addCell(cellx);
	        
	        PdfPCell cell35 = new PdfPCell(new Phrase("", fontArray[0]));
	        cell35.setHorizontalAlignment(1);
	        cell35.setVerticalAlignment(5);
	        cell35.setColspan(3);
	        cell35.setBorderWidthTop(0.0f);
	        cell35.setBorderWidthLeft(0.0f);
	        pdfPtable2.addCell(cell35);
	        int num3 = (num1 + 1);
	        int num4 = 220;
	        int num5 = 200;
	        int num6 = 195;
	        
	        document.add(pdfPtable1);
	        document.add(pdfPtable2);
	        System.out.println("開始列印條碼 - ");
	        PdfContentByte directContent1 = instance.getDirectContent();
	        directContent1.setFontAndSize(font2, 22f);
	        directContent1.beginText();
	        directContent1.setTextMatrix(40f, (float)(num5 + 10));
	        directContent1.showText("*" + str6 + "*");
	        System.out.println("條碼 - " + str6);
	        int num7 = (num5 - 20);
	        directContent1.setTextMatrix(40f, (float) num7);
	        directContent1.showText("*" + str5 + "*");
	        System.out.println("條碼 - " + str5);
	        int num8 = (num7 - 20);
	        directContent1.setTextMatrix(40f, (float)(num8 - 10));
	        directContent1.showText("*" + text + "*");
	        System.out.println("條碼 - " + text);
	        directContent1.endText();
	        System.out.println("結束列印條碼 - ");
	        
	        PdfContentByte directContent2 = instance.getDirectContent();
	        directContent2.beginText();
	        directContent2.setFontAndSize(font1, 9f);
	        directContent2.setTextMatrix(50f, (float)(num4 + 10));
	        directContent2.showText("金融機構或便利商店繳費專用條碼");
	        if (IsWeb)
	        {
	          int num9 = (num4 - 95);
	          directContent2.setTextMatrix(48f, (float) num9);
	          directContent2.showText("ATM或網路繳費鍵入資料");
	          int num10 = (num9 - 13);
	          directContent2.setTextMatrix(48f, (float) num10);
	          directContent2.showText("1.   銀行代號004");
	          int num11 = (num10 - 12);
	          directContent2.setTextMatrix(48f, (float) num11);
	          directContent2.showText("2.   轉入帳號(銷帳編號)");
	        }
	        directContent2.endText();
	        
	        PdfContentByte directContent3 = instance.getDirectContent();
	        directContent3.beginText();
	        directContent3.setFontAndSize(font1, 6f);
	        directContent3.setTextMatrix(70f, (float)(num6 + 10));
	        directContent3.showText(str6);
	        int num12 = (num6 - 20);
	        directContent3.setTextMatrix(70f, (float)num12);
	        directContent3.showText(str5);
	        int num13 = (num12 - 21);
	        directContent3.setTextMatrix(70f, (float)(num13 - 10));
	        directContent3.showText(text);
	        directContent3.endText();
	        //document.add(pdfPtable1);
	        //document.add(pdfPtable2);
	}
}