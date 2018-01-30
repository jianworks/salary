package com.csjian.form;

import java.io.*;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import com.lowagie.text.pdf.*;


public class CreateStartForm extends PdfPageEventHelper {

  public void renderForm(HttpServletRequest request, InputStream pdftemplate, OutputStream os) throws Exception {
    try {
      BaseFont bfChinese = BaseFont.createFont("MHei-Medium", "UniCNS-UCS2-H", BaseFont.NOT_EMBEDDED);

      PdfReader reader = new PdfReader(pdftemplate);
      PdfStamper stamp = new PdfStamper(reader, os);
      AcroFields form = stamp.getAcroFields();
      for(Iterator i = reader.getAcroForm().getFields().iterator(); i.hasNext();) {
        PRAcroForm.FieldInformation field = (PRAcroForm.FieldInformation) i.next();
        form.setFieldProperty(field.getName(), "textfont", bfChinese, null);
      }

      form.setField("cb1." + request.getParameter("public"), "Y");
      form.setField("txt0", request.getParameter("regname")!=null?request.getParameter("regname"):"");
      form.setField("txt1.0", request.getParameter("area1")!=null?request.getParameter("area1"):"");
      form.setField("txt1.1", request.getParameter("address1")!=null?request.getParameter("address1"):"");
      String zip = request.getParameter("zip1")!=null?request.getParameter("zip1"):"";
      for (int i=0; i<zip.length(); i++) {
      	form.setField("txt2." + i, zip.charAt(i)+ "");
      }
      form.setField("txt3.0", request.getParameter("area2")!=null?request.getParameter("area2"):"");
      form.setField("txt3.1", request.getParameter("address2")!=null?request.getParameter("address2"):"");
      zip = request.getParameter("zip2")!=null?request.getParameter("zip2"):"";
      for (int i=0; i<zip.length(); i++) {
      	form.setField("txt4." + i, zip.charAt(i)+ "");
      }
      form.setField("txt5", request.getParameter("bossName")!=null?request.getParameter("bossName"):"");
      zip = request.getParameter("bossId")!=null?request.getParameter("bossId"):"";
      for (int i=0; i<zip.length(); i++) {
      	form.setField("txt6." + i, zip.charAt(i)+ "");
      }
      form.setField("txt7", request.getParameter("birth")!=null?request.getParameter("birth"):"");
      form.setField("txt8", request.getParameter("phone")!=null?request.getParameter("phone"):"");
      form.setField("txt9", request.getParameter("bossAddress")!=null?request.getParameter("bossAddress"):"");
      form.setField("txt10", request.getParameter("business")!=null?request.getParameter("business"):"");
      form.setField("txt11", request.getParameter("product")!=null?request.getParameter("product"):"");
      form.setField("txt12", request.getParameter("ratio")!=null?request.getParameter("ratio") + "%":"");
      form.setField("txt13", request.getParameter("regcode")!=null?request.getParameter("regcode"):"");
      form.setField("txt14", request.getParameter("email")!=null?request.getParameter("email"):"");
      form.setField("txt15", request.getParameter("fax")!=null?request.getParameter("fax"):"");
      form.setField("txt16", request.getParameter("regname")!=null?request.getParameter("regname"):"");
      form.setField("txt17", request.getParameter("bossName")!=null?request.getParameter("bossName"):"");
      
      Calendar cal = Calendar.getInstance();
      form.setField("txt18.0", (cal.get(Calendar.YEAR)-1911)+"");
  		form.setField("txt18.1", (cal.get(Calendar.MONTH)+1)+"");
  		form.setField("txt18.2", cal.get(Calendar.DATE)+"");
      stamp.setFormFlattening(true);
      stamp.close();
 
    } catch ( Exception e ) {
      throw e;
    } 
  }
}