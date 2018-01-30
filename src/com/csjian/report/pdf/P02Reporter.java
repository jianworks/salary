package com.csjian.report.pdf;

import com.csjian.util.*;
import com.csjian.model.bean.CompanyBean;
import com.csjian.model.bean.SalaryBean;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.*;
import java.util.*;

import javax.sql.*;
import java.awt.*;
import javax.naming.*;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.ExceptionConverter;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfGState;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfTemplate;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.Paragraph;


public class P02Reporter extends PdfPageEventHelper {
  public PdfPTable table;  // The headertable.
  public PdfGState gstate;
  public PdfTemplate tpl; // A template that will hold the total number of pages.
  public BaseFont bfChinese;
  public Font FontChineseS;
  public static String companyname;
  public static String year;
  public static String month;

  public static void generatePDF(String regcode, String year, String month, CompanyBean company, SalaryBean[] salaryList, OutputStream os) throws Exception {
    P02Reporter.year = year;
    P02Reporter.month = month;

	  try {
	   	if (company != null) {
        P02Reporter.companyname = company.getRegname();

     	  Document doc = new Document(PageSize.A4, 50, 50, 100, 72);
        PdfWriter writer = PdfWriter.getInstance(doc, os);
        doc.open();
	      BaseFont bfChinese = BaseFont.createFont("MHei-Medium", "UniCNS-UCS2-H", BaseFont.NOT_EMBEDDED);
        Font FontChineseS = new com.lowagie.text.Font(bfChinese, 12, com.lowagie.text.Font.NORMAL);;

   		  for(int i=0; i<salaryList.length; i++) {
   		  	PdfPTable tbl = new PdfPTable(6);
   		  	tbl.setKeepTogether(true);
          tbl.setWidthPercentage(100f);
          tbl.getDefaultCell().setBorderWidth(0.1f);
          PdfPCell cell = null;
    	    cell = new PdfPCell(new Phrase(P02Reporter.companyname +  "薪資條", FontChineseS));
    	    cell.setColspan(6);
    	    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    	    tbl.addCell(cell);
    	    cell = new PdfPCell(new Phrase("員工編號："+salaryList[i].getEmployeeno(), FontChineseS));
    	    cell.setColspan(6);
    	    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    	    tbl.addCell(cell);

    	    cell = new PdfPCell(new Phrase("員工姓名："+salaryList[i].getName(), FontChineseS));
    	    cell.setColspan(4);
    	    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    	    tbl.addCell(cell);

    	    cell = new PdfPCell(new Phrase("薪資年月："+(Integer.parseInt(P02Reporter.year)-1911) + "年" + P02Reporter.month + "月", FontChineseS));
    	    cell.setColspan(2);
    	    cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    	    tbl.addCell(cell);

    	    cell = new PdfPCell(new Phrase("應領金額", FontChineseS));
    	    cell.setColspan(2);
    	    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    	    tbl.addCell(cell);

    	    cell = new PdfPCell(new Phrase("應扣金額", FontChineseS));
    	    cell.setColspan(2);
    	    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    	    tbl.addCell(cell);

    	    cell = new PdfPCell(new Phrase("合計", FontChineseS));
    	    cell.setColspan(2);
    	    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    	    tbl.addCell(cell);

   		  	Vector pitem = salaryList[i].getItemp();
          Vector mitem = salaryList[i].getItemm();
          String itemlist = "";
          itemlist = "基本薪資：" + '\n' + "加班薪資：" + '\n';
          for (int j=0; j<pitem.size(); j++) {
            String[] item = (String[])pitem.elementAt(j);
            itemlist += item[1] + "：" + '\n';
          }
          tbl.addCell(new Phrase(itemlist, FontChineseS));

          itemlist = StringUtils.addComma(salaryList[i].getBasesalary()) + '\n' + StringUtils.addComma(salaryList[i].getOversalary()) + '\n';
          for (int j=0; j<pitem.size(); j++) {
            String[] item = (String[])pitem.elementAt(j);
            itemlist += StringUtils.addComma(item[2]) + '\n';
          }
          tbl.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
          tbl.addCell(new Phrase(itemlist, FontChineseS));
          tbl.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
          itemlist = "";
          for (int j=0; j<mitem.size(); j++) {
            String[] item = (String[])mitem.elementAt(j);
            itemlist += item[1] + "：" + '\n';
          }
          itemlist += "扣抵稅額：";
          tbl.addCell(new Phrase(itemlist, FontChineseS));

          itemlist = "";
          for (int j=0; j<mitem.size(); j++) {
            String[] item = (String[])mitem.elementAt(j);
            itemlist += StringUtils.addComma(item[2]) + '\n';
          }
          itemlist += StringUtils.addComma(salaryList[i].getTax());
          tbl.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
          tbl.addCell(new Phrase(itemlist, FontChineseS));
          tbl.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);


          tbl.addCell(new Phrase("應領金額：" + '\n' + "應扣金額：" + '\n' + "實發金額：", FontChineseS));
          itemlist = "";
          itemlist = StringUtils.addComma((Integer.parseInt(!salaryList[i].getBtotal().equals("")?salaryList[i].getBtotal():"0") + Integer.parseInt(!salaryList[i].getPtotal().equals("")?salaryList[i].getPtotal():"0")) + "") + '\n';
          itemlist += StringUtils.addComma(salaryList[i].getMtotal()) + '\n' + StringUtils.addComma(salaryList[i].getTotal());
          tbl.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
          tbl.addCell(new Phrase(itemlist, FontChineseS));
          tbl.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);

     	    tbl.setTotalWidth(doc.right() - doc.left());
     	    tbl.setSpacingAfter(30f);

          doc.add(tbl);
        }
   		  doc.add(new Phrase("無本月份薪資資料", FontChineseS));


        doc.close();
	  	}
    } catch ( Exception e ) {
      throw e;
    }
  }
}