package com.csjian.form;

import com.csjian.model.bean.*;
import com.csjian.util.*;

import java.io.*;
import java.util.*;

import com.lowagie.text.pdf.*;
import com.lowagie.text.Document;

public class CreateProclamationForm extends PdfPageEventHelper {

  public void renderForm(String reason1, String txt1, String reason2, String amount, int tocode, InputStream pdftemplate, CompanyBean company, OutputStream os) throws Exception {
    try {
      BaseFont bfChinese = BaseFont.createFont("MHei-Medium", "UniCNS-UCS2-H", BaseFont.NOT_EMBEDDED);

      PdfReader reader = new PdfReader(pdftemplate);
      PdfStamper stamp = new PdfStamper(reader, os);
      AcroFields form = stamp.getAcroFields();
      for(Iterator i = reader.getAcroForm().getFields().iterator(); i.hasNext();) {
        PRAcroForm.FieldInformation field = (PRAcroForm.FieldInformation) i.next();
        form.setFieldProperty(field.getName(), "textfont", bfChinese, null);
      }

      form.setField("cb1." + reason1, "Y");
      if (reason1.equals("0") || reason1.equals("1") || reason1.equals("2")) {
    	  String[] data = txt1.split("-");
    	  try {
    		  form.setField("txt1." + reason1 + ".Y", data[0]);
    		  form.setField("txt1." + reason1 + ".M", data[1]);
    		  form.setField("txt1." + reason1 + ".D", data[2]);
    	  } catch(Exception e) {
    		  e.printStackTrace();
    	  }
      } else if (reason1.equals("4")) {
    	  form.setField("txt1." + reason1, txt1);
      }
      
      form.setField("cb2." + reason2, "Y");
      form.setField("txt25", StringUtils.addComma(amount));
      
      if (company != null) {
        form.setField("txt5", company.getRegname());
        form.setField("txt6", company.getHealthCode());
        form.setField("txt7", company.getBossName());
        form.setField("txt8", company.getBossId());
        form.setField("txt9", company.getPhone());
        form.setField("txt13", company.getRegcode());
      }
      
      String[] tolist = {"台北", "北區", "中區", "南區", "高屏", "東區"};
      try {
    	  form.setField("txt14", tolist[tocode]);
      } catch(Exception e) {
    	  e.printStackTrace();
      }
      
      
      Calendar cal = Calendar.getInstance();
      form.setField("txt10", (cal.get(Calendar.YEAR)-1911)+"");
  		form.setField("txt11", (cal.get(Calendar.MONTH)+1)+"");
  		form.setField("txt12", cal.get(Calendar.DATE)+"");
  		
      stamp.setFormFlattening(true);
      stamp.close();
 
    } catch ( Exception e ) {
      throw e;
    } 
  }
}