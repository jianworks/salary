package com.csjian.report.pdf;

import com.csjian.util.*;
import com.csjian.model.bean.CompanyBean;
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

public class P05Reporter extends PdfPageEventHelper {
  public PdfPTable table;  // The headertable.
  public PdfGState gstate;
  public PdfTemplate tpl; // A template that will hold the total number of pages.
  public BaseFont bfChinese;
  public Font FontChineseS;
  public static String companyname;
  public static String year;
  public static String month;

  public static void generatePDF(String regcode, String year, CompanyBean company, Vector datamart, OutputStream os) throws Exception {
	  Calendar cal = Calendar.getInstance();
    P05Reporter.year = year;

	  try {
	  	if (company != null) {
        P05Reporter.companyname = company.getRegname();

     	  Document doc = new Document(PageSize.A4.rotate(), 45, 45, 100, 72);
        PdfWriter writer = PdfWriter.getInstance(doc, os);
        writer.setPageEvent(new P05Reporter());
        doc.open();
	      BaseFont bfChinese = BaseFont.createFont("MHei-Medium", "UniCNS-UCS2-H", BaseFont.NOT_EMBEDDED);
        Font FontChineseS = new com.lowagie.text.Font(bfChinese, 10, com.lowagie.text.Font.NORMAL);;

	      float[] widths = {0.09f, 0.06f, 0.065f, 0.065f, 0.065f, 0.065f, 0.065f, 0.065f, 0.065f, 0.065f, 0.065f, 0.065f, 0.065f, 0.065f, 0.07f};
   		  PdfPTable tbl = new PdfPTable(widths);
        tbl.setWidthPercentage(100f);
        tbl.getDefaultCell().setBorderWidth(0f);
        tbl.getDefaultCell().setFixedHeight(20);
        tbl.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
        tbl.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        tbl.setHeaderRows(3);
        tbl.getDefaultCell().setBorderWidthTop(0.1f);
        tbl.addCell(new Phrase("員工編號\\證號", FontChineseS));
        tbl.addCell(new Phrase("給付總額", FontChineseS));
        for (int i=0; i<13; i++) {
          tbl.addCell(new Phrase("", FontChineseS));
        }
        tbl.getDefaultCell().setBorderWidthTop(0f);
        tbl.addCell(new Phrase("所得人姓名", FontChineseS));
        tbl.addCell(new Phrase("扣繳稅額", FontChineseS));
        tbl.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
        tbl.addCell(new Phrase("一月", FontChineseS));
        tbl.addCell(new Phrase("二月", FontChineseS));
        tbl.addCell(new Phrase("三月", FontChineseS));
        tbl.addCell(new Phrase("四月", FontChineseS));
        tbl.addCell(new Phrase("五月", FontChineseS));
        tbl.addCell(new Phrase("六月", FontChineseS));
        tbl.addCell(new Phrase("七月", FontChineseS));
        tbl.addCell(new Phrase("八月", FontChineseS));
        tbl.addCell(new Phrase("九月", FontChineseS));
        tbl.addCell(new Phrase("十月", FontChineseS));
        tbl.addCell(new Phrase("十一月", FontChineseS));
        tbl.addCell(new Phrase("十二月", FontChineseS));
        tbl.addCell(new Phrase("合計", FontChineseS));
        tbl.getDefaultCell().setBorderWidthBottom(0.1f);
        tbl.addCell(new Phrase("", FontChineseS));
        tbl.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
        tbl.addCell(new Phrase("給付總額", FontChineseS));
        for (int i=0; i<13; i++) {
          tbl.addCell(new Phrase("", FontChineseS));
        }
        tbl.getDefaultCell().setBorderWidthBottom(0f);

        for (int i=0; i<datamart.size()-1; i++) {
        	String[] item = (String[]) datamart.elementAt(i);
        	PdfPCell cell = null;
        	tbl.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
    	    cell = new PdfPCell(new Phrase(item[0] + "   " + item[1], FontChineseS));
    	    cell.setBorderWidth(0f);
    	    cell.setColspan(2);
    	    tbl.addCell(cell);
    	    tbl.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
    	    for (int j=1; j<=13; j++) {
    	    	tbl.addCell(new Phrase(item[3+j]!=null&&!item[3+j].equals("")?StringUtils.addComma(item[3+j]):"0", FontChineseS));
    	    }
    	    tbl.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
    	    cell = new PdfPCell(new Phrase(item[2], FontChineseS));
    	    cell.setBorderWidth(0f);
    	    cell.setColspan(2);
    	    tbl.addCell(cell);
    	    tbl.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
    	    for (int j=1; j<=13; j++) {
    	    	tbl.addCell(new Phrase(item[16+j]!=null&&!item[16+j].equals("")?StringUtils.addComma(item[16+j]):"0", FontChineseS));
    	    }
    	    cell = new PdfPCell(new Phrase("", FontChineseS));
    	    cell.setBorderWidth(0f);
    	    cell.setColspan(2);
    	    tbl.addCell(cell);
    	    for (int j=1; j<=13; j++) {
    	    	tbl.addCell(new Phrase(item[29+j]!=null&&!item[29+j].equals("")?StringUtils.addComma(item[29+j]):"0", FontChineseS));
    	    }
    	    tbl.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
    	    cell = new PdfPCell(new Phrase(item[3], FontChineseS));
    	    cell.setBorderWidth(0f);
    	    cell.setColspan(15);
    	    tbl.addCell(cell);
        }
        if (datamart.size()>1) {
        	String[] item = (String[]) datamart.elementAt(datamart.size()-1);
        	PdfPCell cell = null;
        	tbl.getDefaultCell().setBorderWidthTop(0.1f);
        	tbl.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
    	    cell = new PdfPCell(new Phrase("給付總額總計：", FontChineseS));
    	    cell.setBorderWidth(0f);
    	    cell.setBorderWidthTop(0.1f);
    	    cell.setColspan(2);
    	    tbl.addCell(cell);
    	    tbl.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
    	    for (int j=1; j<=13; j++) {
    	    	tbl.addCell(new Phrase(item[3+j]!=null&&!item[3+j].equals("")?StringUtils.addComma(item[3+j]):"0", FontChineseS));
    	    }
    	    tbl.getDefaultCell().setBorderWidthTop(0f);
    	    tbl.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
    	    cell = new PdfPCell(new Phrase("扣繳稅額總計：", FontChineseS));
    	    cell.setBorderWidth(0f);
    	    cell.setColspan(2);
    	    tbl.addCell(cell);
    	    tbl.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
    	    for (int j=1; j<=13; j++) {
    	    	tbl.addCell(new Phrase(item[16+j]!=null&&!item[16+j].equals("")?StringUtils.addComma(item[16+j]):"0", FontChineseS));
    	    }
    	    cell = new PdfPCell(new Phrase("給付淨額總計：", FontChineseS));
    	    cell.setBorderWidth(0f);
    	    cell.setColspan(2);
    	    tbl.addCell(cell);
    	    for (int j=1; j<=13; j++) {
    	    	tbl.addCell(new Phrase(item[29+j]!=null&&!item[29+j].equals("")?StringUtils.addComma(item[29+j]):"0", FontChineseS));
    	    }
        }

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
	    cell = new PdfPCell(new Phrase(P05Reporter.companyname +  (Integer.parseInt(P05Reporter.year)-1911) + "年度月薪資彙總表", FontChineseL));
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