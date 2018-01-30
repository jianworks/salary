package com.csjian.form;

import com.csjian.model.bean.*;
import com.csjian.util.*;

import java.io.*;
import java.util.*;

import com.lowagie.text.pdf.*;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;

public class CopyOfM0306 extends PdfPageEventHelper {
	
	/**
	 * @param company
	 * @param incomePeriod	給付年月, 格式 YYY-MM
	 * @param amount
	 * @param pdftemplate
	 * @param os
	 * @throws Exception
	 */
	public void renderForm(CompanyBean company,	String incomePeriod, String amount, InputStream pdftemplate, OutputStream os) throws Exception {
		try {
			Calendar cal = Calendar.getInstance();
			boolean Expression = false;
			String m_PayYYYMM = incomePeriod.replace("-", "");
			String printDate = (cal.get(Calendar.YEAR) - 1911) + "/" + ((cal.get(Calendar.MONTH) + 1) > 9 ?"":"0") + (cal.get(Calendar.MONTH) + 1) + "/" + ((cal.get(Calendar.DATE)) > 9 ?"":"0") + (cal.get(Calendar.DATE));
			String barcode1 = "501231600";	// str3
			String s14Barcode = ("61" + company.getHealthCode().substring(0, 8) + NHIBarcode.tranBarCodeYYM(m_PayYYYMM)) + (Expression ? "1" : "6");
	        String dueDate = NHIDate.lastOfThisMonth(NHIDate.dateAddYMD(m_PayYYYMM + "01", "M", 1));
	        String sCheckCode = NHIBarcode.getCheckCode1(s14Barcode, amount, dueDate);
	        String barcode2 = sCheckCode + s14Barcode;
	        
	        String amtTmp = "000000000" + amount;
	        String str5 = m_PayYYYMM.substring(m_PayYYYMM.length()-4) + "**" + amtTmp.substring(amtTmp.length()-9);
	        String checkCode2 = NHIBarcode.getCheckCode2(barcode1, barcode2, str5);
	        String barcode3 = str5.substring(0, 4) + checkCode2 + str5.substring(6);			
			

			//BaseFont bfChinese = BaseFont.createFont("MHei-Medium",	"UniCNS-UCS2-H", BaseFont.NOT_EMBEDDED);
			String windir = System.getenv("windir");
	        BaseFont font1 = BaseFont.createFont(windir + "\\fonts\\KAIU.TTF", "Identity-H", true);
	        BaseFont font2 = BaseFont.createFont(windir + "\\fonts\\FRE3OF9X.TTF", "Identity-H", true);
	        
			/*
	        PdfReader reader = new PdfReader(pdftemplate);
			PdfStamper stamp = new PdfStamper(reader, os);

			AcroFields form = stamp.getAcroFields();
			
			for (Iterator i = reader.getAcroForm().getFields().iterator(); i.hasNext();) {
				PRAcroForm.FieldInformation field = (PRAcroForm.FieldInformation) i.next();
				form.setFieldProperty(field.getName(), "textfont", font1, null);
				form.setFieldProperty(field.getName(), "textsize", new Float(12), null);
			}
			form.setFieldProperty("printDate", "textsize", new Float(10), null);
			form.setFieldProperty("barcode2", "textsize", new Float(10), null);
			form.setFieldProperty("barcode3", "textsize", new Float(10), null);
			
			form.setField("healthCode", company.getHealthCode());
			form.setField("name", company.getRegname());
			form.setField("incomePeriod", incomePeriod.replace('-', '/'));
			form.setField("amount", amount);
			form.setField("tel", company.getPhone());			
			form.setField("printDate", printDate);
			form.setField("dueDate", NHIDate.dataFormatStr2(dueDate));			
			form.setField("barcode2", barcode2);			
			form.setField("barcode3", barcode3);		

			stamp.setFormFlattening(true);
			stamp.close();
			*/
	        
	     // Create output PDF
	        Document document = new Document(PageSize.A4);
	        PdfWriter writer = PdfWriter.getInstance(document, os);
	        document.open();
	        PdfContentByte cb = writer.getDirectContent();

	        // Load existing PDF
	        PdfReader reader = new PdfReader(pdftemplate);
	        PdfImportedPage page = writer.getImportedPage(reader, 1); 

	        // Copy first page of existing PDF into output PDF
	        document.newPage();
	        cb.addTemplate(page, 0, 0);
	        this.absText(cb, company.getHealthCode(), 100, 200, font1, new Float(12));
	        
	        document.close();

		} catch (Exception e) {
			throw e;
		}
	}
	
	private void absText(PdfContentByte cb, String text, int x, int y, BaseFont bf, float fontSize) {
		try {
			cb.saveState();
			cb.beginText();
			cb.moveText(x, y);
			cb.setFontAndSize(bf, fontSize);
			cb.showText(text);
			cb.endText();
			cb.restoreState();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}