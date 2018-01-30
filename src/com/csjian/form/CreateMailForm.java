package com.csjian.form;

import com.csjian.model.bean.*;

import java.io.*;
import java.util.*;

import com.lowagie.text.pdf.*;
import com.lowagie.text.Document;

public class CreateMailForm extends PdfPageEventHelper {

  public void generatePDF(String regcode, String[] tocode, InputStream pdftemplate, CompanyBean company, OutputStream os) throws Exception {
    try {
    	String[] tolist = {"勞工保險局", "中央健保署台北業務組", "中央健保署北區分局", "中央健保署中區分局", "中央健保署南區分局", "中央健保署高屏分局", "中央健保署東區分局"};
    	BaseFont bfChinese = BaseFont.createFont("MHei-Medium", "UniCNS-UCS2-H", BaseFont.NOT_EMBEDDED);
      String[] pdflist = new String[tocode.length];

      for (int k=0; k<tocode.length; k++) {
        pdflist[k] = "c:/temp/" + regcode + "_" + tocode[k] + ".pdf";
        PdfReader reader = new PdfReader(pdftemplate);
        PdfStamper stamp = new PdfStamper(reader, new FileOutputStream(pdflist[k]));
        AcroFields form = stamp.getAcroFields();
        for(Iterator i = reader.getAcroForm().getFields().iterator(); i.hasNext();) {
          PRAcroForm.FieldInformation field = (PRAcroForm.FieldInformation) i.next();
          form.setFieldProperty(field.getName(), "textfont", bfChinese, null);
        }

        form.setField("c1." + tocode[k], "Y");
        form.setField("t7", tolist[Integer.parseInt(tocode[k])]);

        if (company != null) {
          String zip = company.getZip();
          for (int i=0; i<zip.length(); i++) {
            form.setField("t1." + i, zip.charAt(i)+"");
          }
          form.setField("t2", company.getAddress());
          form.setField("t3", company.getRegname());
          form.setField("t4", company.getPhone());
          form.setField("t5", company.getLaborCode());
          form.setField("t6", company.getHealthCode());
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
       /*
       for (int i=0; i<pdflist.length-1; i++) {
         File fout = new File(pdflist[i]);
         fout.delete();
       }*/
    } catch ( Exception e ) {
      throw e;
    } 
  }
}