package com.csjian.form;

import com.csjian.model.bean.*;

import java.io.*;
import java.util.*;

import com.lowagie.text.pdf.*;
import com.lowagie.text.Document;

public class CreateAdjustForm extends PdfPageEventHelper {

  public void renderForm(String regcode, Vector people, InputStream pdftemplate, CompanyBean company, OutputStream os) throws Exception {
    try {
      BaseFont bfChinese = BaseFont.createFont("MHei-Medium", "UniCNS-UCS2-H", BaseFont.NOT_EMBEDDED);
      int count = people.size();
      int pages = (count-1)/10 + 1;
      String[] pdflist = new String[pages];
      Calendar cal = Calendar.getInstance();

      for (int k=0; k<pages; k++) {
        pdflist[k] = "c:/temp/" + regcode + "_" + k + ".pdf";
        PdfReader reader = new PdfReader(new FileInputStream("d:/blankform/adjustform.pdf"));
        PdfStamper stamp = new PdfStamper(reader, new FileOutputStream(pdflist[k]));
        AcroFields form = stamp.getAcroFields();
        for(Iterator i = reader.getAcroForm().getFields().iterator(); i.hasNext();) {
          PRAcroForm.FieldInformation field = (PRAcroForm.FieldInformation) i.next();
          form.setFieldProperty(field.getName(), "textfont", bfChinese, null);
        }
        form.setField("t17.0", (cal.get(Calendar.YEAR)-1911)+"");
    		form.setField("t17.1", (cal.get(Calendar.MONTH)+1)+"");
    		form.setField("t17.2", cal.get(Calendar.DATE)+"");

        if (company != null) {
        	form.setField("t1", company.getLaborCode());
        	String healthcode = company.getHealthCode();
          for (int i=0; i<healthcode.length(); i++) {
            form.setField("t2." + i, healthcode.charAt(i)+"");
          }
          form.setField("t3", regcode);
          form.setField("t8", company.getRegname());
          form.setField("t9", company.getAddress());
          form.setField("t10", company.getPhone());
          
          if (!company.getZip().equals("")) {
      	    try {
          	  int zip = Integer.parseInt(company.getZip().substring(0,3));
        	    if (zip>=950 && zip<990) {
        	    	form.setField("t18", "東區");
        	    }	else if (zip>=800 && zip<950) {
        	    	form.setField("t18", "高屏");
              } else if (zip>=600 && zip<800) {
              	form.setField("t18", "南區");
              } else if (zip>=400 && zip<600) {
              	form.setField("t18", "中區");
        	    } else if (zip>=300 && zip<=400) {
        	    	form.setField("t18", "北區");
        	    } else {
        	    	form.setField("t18", "臺北");
        	    }
      	    } catch (Exception ex){}
          }
        }

        for (int i=0; i<10; i++) {
        	int idx = k*10 + i;
        	if (idx<count) {
        		String[] person = (String[])people.elementAt(idx); // person {員工編號,姓名,身份證號,出生日期,原加保金額,調整後金額}
        		form.setField("t4." + i, person[1]);
        		for (int j=0; j<person[2].length(); j++) {
              form.setField("t5." + i + "." + j, person[2].charAt(j)+"");
            }
        		String[] birth = person[3].split("-");
        		form.setField("t6." + i + ".0", (Integer.parseInt(birth[0])-1911)+"");
        		form.setField("t6." + i + ".1", birth[1]);
        		form.setField("t6." + i + ".2", birth[2]);
        		form.setField("t7." + i + ".0", person[4]);
        		form.setField("t7." + i + ".1", person[5]);
        	}
        }
        stamp.setFormFlattening(true);
        stamp.close();
      }

      // Concatenate PDF files
      int pageOffset = 0;
      int f = 0;
      Document document =  null;
      PdfCopy  writer = null;
      while (f < pdflist.length) {
        // we create a reader for a certain document
        PdfReader reader = new PdfReader(pdflist[f]);
        reader.consolidateNamedDestinations();
        // we retrieve the total number of pages
        int n = reader.getNumberOfPages();
        pageOffset += n;

        if (f == 0) {
          // step 1: creation of a document-object
          document = new Document(reader.getPageSizeWithRotation(1));
          // step 2: we create a writer that listens to the document
          writer = new PdfCopy(document, os);
          // step 3: we open the document
          document.open();
        }

        // step 4: we add content
        PdfImportedPage page;
        for (int i = 0; i < n; ) {
          ++i;
          page = writer.getImportedPage(reader, i);
          writer.addPage(page);
        }
        PRAcroForm form = reader.getAcroForm();
        if (form != null) writer.copyAcroForm(reader);
        f++;
        reader.close();
      }

       // step 5: we close the document
       document.close();
       for (int i=0; i<pdflist.length-1; i++) {
         File fout = new File(pdflist[i]);
         fout.delete();
       }
    } catch ( Exception e ) {
    	e.printStackTrace();
      throw e;
    } 
  }
}