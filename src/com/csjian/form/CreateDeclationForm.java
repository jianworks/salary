package com.csjian.form;

import com.csjian.model.bean.*;

import java.io.*;
import java.util.*;

import com.lowagie.text.pdf.*;
import com.lowagie.text.Document;

public class CreateDeclationForm extends PdfPageEventHelper {

  public void renderForm(String reason, InputStream pdftemplate, CompanyBean company, String amount, OutputStream os) throws Exception {
    try {
      BaseFont bfChinese = BaseFont.createFont("MHei-Medium", "UniCNS-UCS2-H", BaseFont.NOT_EMBEDDED);

      PdfReader reader = new PdfReader(pdftemplate);
      PdfStamper stamp = new PdfStamper(reader, os);
      AcroFields form = stamp.getAcroFields();
      for(Iterator i = reader.getAcroForm().getFields().iterator(); i.hasNext();) {
        PRAcroForm.FieldInformation field = (PRAcroForm.FieldInformation) i.next();
        form.setFieldProperty(field.getName(), "textfont", bfChinese, null);
      }

      form.setField("cb1." + reason, "Y");

      if (company != null) {
        form.setField("txt1", company.getRegname());
        form.setField("txt2", company.getRegcode());
        form.setField("txt3", company.getBossName());
        form.setField("txt4", company.getBossId());
      }
      form.setField("txt8", amount);
      Calendar cal = Calendar.getInstance();
      form.setField("txt5", (cal.get(Calendar.YEAR)-1911)+"");
  		form.setField("txt6", (cal.get(Calendar.MONTH)+1)+"");
  		form.setField("txt7", cal.get(Calendar.DATE)+"");
      stamp.setFormFlattening(true);
      stamp.close();
 
    } catch ( Exception e ) {
      throw e;
    } 
  }
}