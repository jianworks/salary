package com.csjian.form;

import com.csjian.model.bean.*;
import com.csjian.util.StringUtils;

import java.io.*;
import java.util.*;

import com.lowagie.text.pdf.*;
import com.lowagie.text.Document;

public class M0302 extends PdfPageEventHelper {

	public void renderForm(String regcode, Vector insuranceAddOnFees, CompanyBean company, String pdftemplate, OutputStream os) throws Exception {
		try {
			BaseFont bfChinese = BaseFont.createFont("MHei-Medium",	"UniCNS-UCS2-H", BaseFont.NOT_EMBEDDED);
			int pages = insuranceAddOnFees.size();
			//int pages = (count - 1) / 10 + 1;
			String[] pdflist = new String[pages];
			Calendar cal = Calendar.getInstance();

			InsuranceAddOnFeeBean insuranceAddOnFee = null;			
			for (int k = 0; k < pages; k++) {
				insuranceAddOnFee = (InsuranceAddOnFeeBean)insuranceAddOnFees.elementAt(k);
				pdflist[k] = "c:/temp/" + regcode + "_" + k + ".pdf";
				PdfReader tempReader = new PdfReader(pdftemplate);
				PdfStamper stamp = new PdfStamper(tempReader, new FileOutputStream(pdflist[k]));
				AcroFields form = stamp.getAcroFields();
				for (Iterator i = tempReader.getAcroForm().getFields().iterator(); i.hasNext();) {
					PRAcroForm.FieldInformation field = (PRAcroForm.FieldInformation) i.next();
					form.setFieldProperty(field.getName(), "textfont",	bfChinese, null);
				}
				form.setField("regcode", company.getRegcode());				
				form.setField("unicode", insuranceAddOnFee.getUnicode());
				form.setField("incomeType", insuranceAddOnFee.getIncomeType());
				form.setField("healthCode", company.getHealthCode());
				form.setField("name", insuranceAddOnFee.getName());
				form.setField("address", insuranceAddOnFee.getAddress());
				form.setField("incomeAmount", StringUtils.addComma(insuranceAddOnFee.getIncomeAmount()));
				form.setField("insuranceAddOnFee", insuranceAddOnFee.getInsuranceAddOnFee());
				form.setField("companyName", company.getName());
				form.setField("companyAddress", company.getAddress());
				form.setField("boss", company.getBossName());
				
				String year = insuranceAddOnFee.getIncomeDate().equals("")? (cal.get(Calendar.YEAR)-1911) + "" : StringUtils.adToTw(insuranceAddOnFee.getIncomeDate()).substring(0, 3);	
				form.setField("year", year);
				stamp.setFormFlattening(true);
				stamp.close();
			}

			// Concatenate PDF files
			int pageOffset = 0;
			int f = 0;
			Document document = null;
			PdfCopy writer = null;
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
				for (int i = 0; i < n;) {
					++i;
					page = writer.getImportedPage(reader, i);
					writer.addPage(page);
				}
				PRAcroForm form = reader.getAcroForm();
				if (form != null)
					writer.copyAcroForm(reader);
				f++;
			}

			// step 5: we close the document
			document.close();
			for (int i = 0; i < pdflist.length - 1; i++) {
				File fout = new File(pdflist[i]);
				fout.delete();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
}