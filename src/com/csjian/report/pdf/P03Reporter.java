package com.csjian.report.pdf;

import com.csjian.util.*;
import com.csjian.model.bean.*;
import java.io.OutputStream;
import java.util.*;


import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.ExceptionConverter;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfGState;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfTemplate;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.PdfPCell;

public class P03Reporter extends PdfPageEventHelper {
  public PdfPTable table;  // The headertable.
  public PdfGState gstate;
  public PdfTemplate tpl; // A template that will hold the total number of pages.
  public BaseFont bfChinese;
  public Font FontChineseS;
  public static String companyname;
  public static String year;
  public static String month;

  public static void generatePDF(String regcode, String year, String month, CompanyBean company, List salaryDigest, OutputStream os) throws Exception {
    P03Reporter.year = year;
    P03Reporter.month = month;

	  try {
	  	if (company != null) {
        P03Reporter.companyname = company.getRegname();

        Document doc = new Document(PageSize.A4, 50, 50, 100, 72);
        PdfWriter writer = PdfWriter.getInstance(doc, os);
        writer.setPageEvent(new P03Reporter());
        doc.open();
	      BaseFont bfChinese = BaseFont.createFont("MHei-Medium", "UniCNS-UCS2-H", BaseFont.NOT_EMBEDDED);
        Font FontChineseS = new com.lowagie.text.Font(bfChinese, 12, com.lowagie.text.Font.NORMAL);;

	      float[] widths = {0.12f, 0.25f, 0.38f, 0.25f};
   		  PdfPTable tbl = new PdfPTable(widths);
        tbl.setWidthPercentage(100f);
        tbl.getDefaultCell().setBorderWidth(0.1f);
        tbl.getDefaultCell().setFixedHeight(25);
        tbl.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
        tbl.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        tbl.setHeaderRows(1);
        tbl.addCell(new Phrase("序號", FontChineseS));
        tbl.addCell(new Phrase("姓名", FontChineseS));
        tbl.addCell(new Phrase("銀行帳號", FontChineseS));
        tbl.addCell(new Phrase("實發金額", FontChineseS));
        int total = 0;
        String[] item = null;
        for (int i=0; i<salaryDigest.size(); i++) {
        	item = (String[]) salaryDigest.get(i);
        	tbl.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        	tbl.addCell(new Phrase((i+1)+"", FontChineseS));
        	tbl.addCell(new Phrase(item[0], FontChineseS));
        	tbl.addCell(new Phrase(item[1], FontChineseS));
        	tbl.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
        	tbl.addCell(new Phrase(StringUtils.addComma(item[2]), FontChineseS));
        	total += item[2].equals("")?0:Integer.parseInt(item[2]);
        }
        tbl.addCell(new Phrase("", FontChineseS));
        tbl.addCell(new Phrase("", FontChineseS));
        tbl.addCell(new Phrase("", FontChineseS));
        tbl.addCell(new Phrase("", FontChineseS));

        PdfPCell cell = new PdfPCell(new Phrase("合計", FontChineseS));
  	    cell.setColspan(2);
  	    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
  	    tbl.addCell(cell);
  	    tbl.addCell(new Phrase("", FontChineseS));
  	    tbl.addCell(new Phrase(StringUtils.addComma(total+""), FontChineseS));

   	    tbl.setTotalWidth(doc.right() - doc.left());
        doc.add(tbl);
        doc.close();
	  	}
    } catch ( Exception e ) {
      throw e;
    }
  }

  public void onOpenDocument(PdfWriter writer, Document document){
	  try {
      bfChinese = BaseFont.createFont("MHei-Medium", "UniCNS-UCS2-H", BaseFont.NOT_EMBEDDED);
      FontChineseS = new com.lowagie.text.Font(bfChinese, 12, com.lowagie.text.Font.NORMAL);
      Font FontChineseL = new com.lowagie.text.Font(bfChinese, 20, com.lowagie.text.Font.NORMAL);
      tpl = writer.getDirectContent().createTemplate(200, 100);

      table = new PdfPTable(2);
      PdfPCell cell = null;
	    cell = new PdfPCell(new Phrase(P03Reporter.companyname +  (Integer.parseInt(P03Reporter.year)-1911) + "年" + P03Reporter.month + "月薪資轉帳表", FontChineseL));
	    cell.setColspan(2);
	    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	    cell.setBorderWidth(0);

      table.getDefaultCell().setBorderWidth(0);
      table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
      table.addCell(cell);
      table.getDefaultCell().setFixedHeight(18);

      String dateStr = ("" + (new java.sql.Date((Calendar.getInstance()).getTimeInMillis()))).substring(0,10);
      dateStr = (Integer.parseInt(dateStr.substring(0,4))-1911) + dateStr.substring(4);
      table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
      table.addCell(new Phrase("", FontChineseS));
      table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
      table.addCell(new Phrase("製表日期：" + dateStr, FontChineseS));

	  } catch(Exception e) {
	    throw new ExceptionConverter(e);
	  }
  }

  public void onEndPage(PdfWriter writer, Document document){
	  try {
      PdfContentByte cb = writer.getDirectContent();
      //cb.saveState();
      bfChinese = BaseFont.createFont("MHei-Medium", "UniCNS-UCS2-H", BaseFont.NOT_EMBEDDED);
      Font FontChineseS = new com.lowagie.text.Font(bfChinese, 12, com.lowagie.text.Font.NORMAL);;

      table.setTotalWidth(document.right() - document.left());
      table.writeSelectedRows(0, -1, document.left(), document.getPageSize().getHeight() - 50, cb);

      String text = "第 " + writer.getPageNumber() + " 之 ";
      float textSize = bfChinese.getWidthPoint(text, 12);
      float textBase = document.bottom() - 20;
      float adjust = bfChinese.getWidthPoint("0 頁", 12);
      cb.moveTo(document.left(), 70);
      cb.lineTo(document.right(), 70);
      cb.stroke();
      cb.beginText();
      cb.setFontAndSize(bfChinese, 12);
      cb.setTextMatrix(document.right() - textSize - adjust, textBase);
      cb.showText(text);
      cb.endText();
      cb.addTemplate(tpl, document.right() - adjust, textBase);
      //cb.saveState();
    } catch (Exception e){

    }
  }

  public void onCloseDocument(PdfWriter writer, Document document) {
    tpl.beginText();
    tpl.setFontAndSize(bfChinese, 12);
    tpl.setTextMatrix(0, 0);
    tpl.showText("" + (writer.getPageNumber() - 1) + " 頁");
    tpl.endText();
  }
}