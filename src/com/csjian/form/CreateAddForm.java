package com.csjian.form;

import com.csjian.model.bean.*;

import java.io.*;
import java.util.*;

import com.lowagie.text.pdf.*;
import com.lowagie.text.Document;

public class CreateAddForm extends PdfPageEventHelper {

  public void renderForm(String regcode, Vector people, InputStream pdftemplate, CompanyBean company, OutputStream os) throws Exception {
  	try {
      BaseFont bfChinese = BaseFont.createFont("MHei-Medium", "UniCNS-UCS2-H", BaseFont.NOT_EMBEDDED);
      int count = people.size();
      int pages = (count-1)/10 + 1;
      String[] pdflist = new String[pages];
      Calendar cal = Calendar.getInstance();

      for (int k=0; k<pages; k++) {
        pdflist[k] = "c:/temp/" + regcode + "_" + k + ".pdf";
        PdfReader reader = new PdfReader(pdftemplate);
        PdfStamper stamp = new PdfStamper(reader, new FileOutputStream(pdflist[k]));
        AcroFields form = stamp.getAcroFields();
        for(Iterator i = reader.getAcroForm().getFields().iterator(); i.hasNext();) {
          PRAcroForm.FieldInformation field = (PRAcroForm.FieldInformation) i.next();
          form.setFieldProperty(field.getName(), "textfont", bfChinese, null);
        }
    		form.setField("t17.0", (cal.get(Calendar.YEAR)-1911)+"");
    		form.setField("t17.1", (cal.get(Calendar.MONTH)+1)+"");
    		form.setField("t17.2", cal.get(Calendar.DATE)+"");

        form.setField("c1.0", "Y");
        if (company !=null) {
        	form.setField("t1", company.getLaborCode());
        	String healthcode = company.getHealthCode();
          for (int i=0; i<healthcode.length(); i++) {
            form.setField("t2." + i, healthcode.charAt(i)+"");
          }
          form.setField("t3", regcode);
          form.setField("t14", company.getRegname());
          form.setField("t15", company.getAddress());
          form.setField("t16", company.getPhone());
          
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
        		String[] person = (String[])people.elementAt(idx); // person {員工編號,是否眷屬,姓名,身份證號,出生日期,加保金額,稱謂,到職日,員工姓名,員工身份證號,員工出生日期}

        		if (person[1].equals("N")) form.setField("c2." + i + ".0", "Y");
        		else form.setField("c2." + i + ".1", "Y");
        		form.setField("t4." + i, person[8]);
        		for (int j=0; j<person[9].length(); j++) {
              form.setField("t5." + i + "." + j, person[9].charAt(j)+"");
            }
        		String[] birth = person[10].split("-");
        		form.setField("t6." + i + ".0", (Integer.parseInt(birth[0])-1911)+"");
        		form.setField("t6." + i + ".1", birth[1]);
        		form.setField("t6." + i + ".2", birth[2]);
        		form.setField("t7." + i, person[5]);
        		if (person[1].equals("N")) form.setField("t12." + i, "到職");
        		else form.setField("t12." + i, "眷屬");
        		String[] adate = person[11].split("-");
        		//form.setField("t13." + i, (cal.get(Calendar.YEAR)-1911)+"."+(cal.get(Calendar.MONTH)+1)+"."+cal.get(Calendar.DATE));
        		form.setField("t13." + i, (Integer.parseInt(adate[0])-1911)+ "."+adate[1]+"."+adate[2]);
        		if (person[1].equals("Y")) {
        			form.setField("t8." + i, person[2]);
        			for (int j=0; j<person[3].length(); j++) {
                form.setField("t9." + i + "." + j, person[3].charAt(j)+"");
              }
        			birth = person[4].split("-");
        			form.setField("t10." + i + ".0", (Integer.parseInt(birth[0])-1911)+"");
        			form.setField("t10." + i + ".1", birth[1]);
        			form.setField("t10." + i + ".2", birth[2]);
        			form.setField("t11." + i, person[6]);
        			//String[] onboard = person[7].split("-");
        			//form.setField("t13." + i, (Integer.parseInt(onboard[0])-1911)+"."+onboard[1]+"."+onboard[2]);
        		}
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
      }

       // step 5: we close the document
       document.close();
       for (int i=0; i<pdflist.length-1; i++) {
         File fout = new File(pdflist[i]);
         fout.delete();
       }
    } catch ( Exception e ) {
      throw e;
    } 
  }
}