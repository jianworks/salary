package com.csjian.form;

import com.csjian.model.bean.*;

import java.io.*;
import java.util.*;

import com.lowagie.text.pdf.*;
import com.lowagie.text.Document;

public class CreatePaForm extends PdfPageEventHelper {

	public void renderForm(Vector people, InputStream pdftemplate, CompanyBean company, OutputStream os) throws Exception {
		try {
			BaseFont bfChinese = BaseFont.createFont("MHei-Medium", "UniCNS-UCS2-H", BaseFont.NOT_EMBEDDED);

			PdfReader reader = new PdfReader(pdftemplate);
			PdfStamper stamp = new PdfStamper(reader, os);
			AcroFields form = stamp.getAcroFields();
			for (Iterator i = reader.getAcroForm().getFields().iterator(); i.hasNext();) {
				PRAcroForm.FieldInformation field = (PRAcroForm.FieldInformation) i.next();
				form.setFieldProperty(field.getName(), "textfont", bfChinese, null);
			}
			form.setField("txt1", company.getRegname());
			form.setField("txt2", company.getRegname());
			form.setField("txt3", company.getBossName());

			int count = people.size() > 10 ? 10 : people.size();
			for (int i = 0; i < count; i++) {
				String[] person = (String[]) people.elementAt(i); // person
				// {員工編號,是否眷屬,姓名,身份證號,出生日期,勞保,健保,勞退}
				form.setField("txt5." + i + ".0", person[2]);
				String[] birth = person[4].split("-");
				form.setField("txt5." + i + ".1", birth[0] + "" + (birth[1].length()<2?"0"+birth[1]:birth[1]) + "" + (birth[2].length()<2?"0"+birth[2]:birth[2]));
				form.setField("txt5." + i + ".2", person[3]);
				form.setField("txt5." + i + ".3", person[5]);
				form.setField("txt5." + i + ".4", person[6]);
				form.setField("txt5." + i + ".5", person[7]);
				if (person[1].equals("Y"))
					form.setField("txt5." + i + ".4", "眷屬");		
			}

			Calendar cal = Calendar.getInstance();
			form.setField("txt4.0", (cal.get(Calendar.YEAR) - 1911) + "");
			form.setField("txt4.1", (cal.get(Calendar.MONTH) + 1) + "");
			form.setField("txt4.2", cal.get(Calendar.DATE) + "");

			stamp.setFormFlattening(true);
			stamp.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
}