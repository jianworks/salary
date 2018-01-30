package com.csjian.form;

import com.csjian.model.bean.*;
import com.csjian.util.*;

import java.io.*;
import java.util.*;

import com.lowagie.text.pdf.*;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;

public class M0306 extends PdfPageEventHelper {

	/**
	 * @param company
	 * @param incomePeriod
	 *            給付年月, 格式 YYY-MM
	 * @param amount
	 * @param pdftemplate
	 * @param os
	 * @throws Exception
	 */
	public void renderForm(CompanyBean company, String incomePeriod, String amount, OutputStream os) throws Exception {
		boolean Expression = false;
		String m_PayYYYMM = incomePeriod.replace("-", "");
		Document document = new Document(PageSize.A4, 28f, 28f, 50f, 50f);
		PdfWriter instance = PdfWriter.getInstance(document, os);
		//instance.setEncryption(true, "", "", 2052);
		try {
			String str1 = System.getenv("windir");
			BaseFont font1 = BaseFont.createFont(str1 + "\\fonts\\KAIU.TTF", "Identity-H", true);
			BaseFont font2 = BaseFont.createFont(str1 + "\\fonts\\FRE3OF9X.TTF", "Identity-H", true);
			Font[] fontArray = new Font[] { 
					new Font(font1, 12f),
					new Font(font1, 16f, 17), 
					new Font(font1, 10f) };
			document.open();
			PdfPTable pdfPtable = new PdfPTable(5);
			float[] relativeWidths1 = new float[] { 30f, 12f, 20f, 20f, 20f };
			pdfPtable.setWidths(relativeWidths1);
			pdfPtable.setWidthPercentage(100f);
			pdfPtable.getDefaultCell().setBorder(0);
			StringBuilder stringBuilder = new StringBuilder("");

			PdfPCell cell1 = new PdfPCell(new Phrase("全民健康保險\n投保單位補充保險費繳款書",
					fontArray[1]));
			cell1.setHorizontalAlignment(1);
			cell1.setVerticalAlignment(4);
			cell1.setBorder(0);
			cell1.setFixedHeight(55f);
			cell1.setColspan(4);
			cell1.setLeading(4f, 1f);
			pdfPtable.addCell(cell1);

			PdfPCell cell2 = new PdfPCell(new Phrase(
					"收據聯：\n本聯經代收機構收款\n蓋章後，交扣費義務\n人收執，作繳費憑證", fontArray[2]));
			cell2.setHorizontalAlignment(0);
			cell2.setVerticalAlignment(4);
			cell2.setColspan(1);
			cell2.setLeading(3f, 1f);
			pdfPtable.addCell(cell2);

			PdfPCell cella = new PdfPCell(new Phrase("投保單位代號："
					+ company.getHealthCode() + "\n\n投保單位名稱："
					+ company.getRegname(), fontArray[0]));
			cella.setHorizontalAlignment(0);
			cella.setVerticalAlignment(5);
			cella.setFixedHeight(50f);
			cella.setColspan(5);
			pdfPtable.addCell(cella);

			PdfPCell cellb = new PdfPCell(new Phrase("給     付     年     月",
					fontArray[0]));
			cellb.setHorizontalAlignment(1);
			cellb.setVerticalAlignment(5);
			cellb.setFixedHeight(25f);
			cellb.setColspan(2);
			pdfPtable.addCell(cellb);

			PdfPCell cellc = new PdfPCell(new Phrase(incomePeriod.replace('-',
					'/'), fontArray[0]));
			cellc.setVerticalAlignment(5);
			cellc.setHorizontalAlignment(1);
			cellc.setFixedHeight(25f);
			cellc.setColspan(2);
			pdfPtable.addCell(cellc);

			PdfPCell celld = new PdfPCell(new Phrase("代收機構\n經收人員蓋章",
					fontArray[2]));
			celld.setHorizontalAlignment(1);
			celld.setVerticalAlignment(4);
			celld.setColspan(1);
			pdfPtable.addCell(celld);

			PdfPCell celle = new PdfPCell(new Phrase("繳     款     期     限",
					fontArray[0]));
			celle.setHorizontalAlignment(1);
			celle.setVerticalAlignment(5);
			celle.setFixedHeight(25f);
			celle.setColspan(2);
			pdfPtable.addCell(celle);

			String due_date = NHIDate.lastOfThisMonth(NHIDate.dateAddYMD(
					m_PayYYYMM + "01", "M", 1));
			PdfPCell cellf = new PdfPCell(new Phrase(NHIDate.dataFormatStr2(due_date), fontArray[0]));
			cellf.setVerticalAlignment(5);
			cellf.setHorizontalAlignment(1);
			cellf.setFixedHeight(25f);
			cellf.setColspan(2);
			pdfPtable.addCell(cellf);

			PdfPCell cell3 = new PdfPCell(new Phrase("", fontArray[0]));
			cell3.setColspan(1);
			cell3.setBorderWidthTop(0.0f);
			cell3.setBorderWidthBottom(0.0f);
			cell3.setBorderWidthLeft(0.0f);
			pdfPtable.addCell(cell3);

			PdfPCell cellg = new PdfPCell(new Phrase("應     繳     金     額",
					fontArray[0]));
			cellg.setHorizontalAlignment(1);
			cellg.setVerticalAlignment(5);
			cellg.setFixedHeight(25f);
			cellg.setColspan(2);
			pdfPtable.addCell(cellg);

			PdfPCell cellh = new PdfPCell(new Phrase(
					StringUtils.addComma(amount), fontArray[0]));
			cellh.setVerticalAlignment(5);
			cellh.setHorizontalAlignment(1);
			cellh.setFixedHeight(25f);
			cellh.setColspan(2);
			pdfPtable.addCell(cellh);

			PdfPCell cell4 = new PdfPCell(new Phrase("", fontArray[0]));
			cell4.setColspan(1);
			cell4.setBorderWidthTop(0.0f);
			cell4.setBorderWidthLeft(0.0f);
			cell4.setBorderWidthBottom(0.0f);
			pdfPtable.addCell(cell4);

			PdfPCell cell5 = new PdfPCell(
					new Phrase(
							"說明：\n"
									+ "一、投保單位依健保法第34條規定應負擔之補充保險費，應於寬限期限前繳納，逾期未繳納者，自寬限期滿之翌日起至\n"
									+ "    完納前1日止，每逾1日加徵其應納費額0.1%滯納金；加徵之滯納金額，以至應納費額之15%為限；逾寬限期限繳納\n"
									+ "    保險費者，其應繳之滯納金將另行通知繳納。", fontArray[2]));
			cell5.setHorizontalAlignment(0);
			cell5.setVerticalAlignment(4);
			cell5.setFixedHeight(66f);
			cell5.setBorderWidthBottom(0.0f);
			cell5.setColspan(5);
			cell5.setLeading(3.8f, 1f);
			pdfPtable.addCell(cell5);

			PdfPTable table1 = new PdfPTable(1);
			float[] relativeWidths2 = new float[] { 100f };
			table1.setWidths(relativeWidths2);

			PdfPCell cell6 = new PdfPCell(new Phrase(
					"二、投保單位請持本繳款書至健保局委託代收金融機構繳納；倘繳納金額2萬元以下，亦可至統一、全家、萊爾富及OK\n"
							+ "    等便利商店繳費。\n", fontArray[2]));
			cell6.setHorizontalAlignment(0);
			cell6.setVerticalAlignment(4);
			cell6.setFixedHeight(40f);
			cell6.setBorderWidthBottom(0.0f);
			cell6.setBorderWidthTop(0.0f);
			cell6.setColspan(5);
			cell6.setLeading(3.8f, 1f);
			pdfPtable.addCell(cell6);

			PdfPCell cell7 = new PdfPCell(new Phrase(
					"三、依健保法第35條第2項之規定，投保單位自本繳款書應繳納之日起，逾30日未繳納者，本局得移送行政執行。\n",
					fontArray[2]));
			cell7.setHorizontalAlignment(0);
			cell7.setVerticalAlignment(4);
			cell7.setFixedHeight(20f);
			cell7.setBorderWidthBottom(0.0f);
			cell7.setBorderWidthTop(0.0f);
			cell7.setColspan(5);
			cell7.setLeading(3.8f, 1f);
			pdfPtable.addCell(cell7);

			String str3 = "501231600";
			String s14Barcode = ("61" + company.getHealthCode().substring(0, 8) + NHIBarcode
					.tranBarCodeYYM(m_PayYYYMM)) + (Expression ? "1" : "6");
			String sCheckCode = NHIBarcode.getCheckCode1(s14Barcode, amount,
					due_date);
			String str4 = sCheckCode + s14Barcode;
			String amtTmp = "000000000" + amount;
			String str5 = m_PayYYYMM.substring(m_PayYYYMM.length() - 4) + "**"
					+ amtTmp.substring(amtTmp.length() - 9);
			String checkCode2 = NHIBarcode.getCheckCode2(str3, str4, str5);
			String text = str5.substring(0, 4) + checkCode2 + str5.substring(6);
			String str6 = NHIDate.dataFormatStr2(NHIDate
					.convertDateToChi(Calendar.getInstance()));
			PdfPCell cell8 = new PdfPCell(
					new Phrase(
							"  洽詢電話：0800-030598\n繳款單編號：" + str3
									+ "                                列印日期："
									+ str6 + "\n            " + str4
									+ "\n            " + text, fontArray[2]));
			cell8.setHorizontalAlignment(0);
			cell8.setVerticalAlignment(4);
			cell8.setFixedHeight(76f);
			cell8.setBorderWidthBottom(0.0f);
			cell8.setBorderWidthTop(0.0f);
			cell8.setColspan(5);
			cell8.setLeading(3.8f, 1f);
			pdfPtable.addCell(cell8);

			PdfPTable table2 = new PdfPTable(1);
			float[] relativeWidths3 = new float[] { 100f };
			table2.setWidths(relativeWidths3);

			PdfPCell cell9 = new PdfPCell(new Phrase(" ", fontArray[2]));
			cell9.setHorizontalAlignment(0);
			cell9.setVerticalAlignment(4);
			cell9.setFixedHeight(160f);
			cell9.setBorderWidth(0.0f);
			cell9.setColspan(1);
			table2.addCell(cell9);

			PdfPCell cell10 = new PdfPCell(table1);
			cell10.setHorizontalAlignment(0);
			cell10.setVerticalAlignment(4);
			cell10.setFixedHeight(1f);
			cell10.setBorderWidthTop(0.0f);
			cell10.setBorderWidthRight(0.0f);
			cell10.setColspan(4);
			pdfPtable.addCell(cell10);

			PdfPCell cell11 = new PdfPCell(table2);
			cell11.setHorizontalAlignment(1);
			cell11.setVerticalAlignment(5);
			cell11.setFixedHeight(1f);
			cell11.setBorderWidthTop(0.0f);
			cell11.setBorderWidthLeft(0.0f);
			cell11.setColspan(1);
			pdfPtable.addCell(cell11);

			PdfPCell cell12 = new PdfPCell(
					new Phrase(
							".................................................................................",
							fontArray[0]));
			cell12.setHorizontalAlignment(1);
			cell12.setVerticalAlignment(5);
			cell12.setFixedHeight(50f);
			cell12.setBorderWidth(0.0f);
			cell12.setColspan(5);
			pdfPtable.addCell(cell12);

			PdfPCell cell13 = new PdfPCell(new Phrase("全民健康保險\n投保單位補充保險費繳款書",
					fontArray[1]));
			cell13.setHorizontalAlignment(1);
			cell13.setVerticalAlignment(5);
			cell13.setBorder(0);
			cell13.setFixedHeight(55f);
			cell13.setLeading(4f, 1f);
			cell13.setColspan(4);
			pdfPtable.addCell(cell13);

			PdfPCell celli = new PdfPCell(new Phrase("代收機構存查聯", fontArray[2]));
			celli.setHorizontalAlignment(1);
			celli.setVerticalAlignment(5);
			celli.setColspan(1);
			pdfPtable.addCell(celli);

			PdfPCell cellj = new PdfPCell(new Phrase("條碼區", fontArray[0]));
			cellj.setHorizontalAlignment(1);
			cellj.setVerticalAlignment(5);
			cellj.setFixedHeight(40f);
			cellj.setColspan(1);
			pdfPtable.addCell(cellj);

			PdfPCell cellk = new PdfPCell(new Phrase("代收明細", fontArray[0]));
			cellk.setHorizontalAlignment(1);
			cellk.setVerticalAlignment(5);
			cellk.setFixedHeight(40f);
			cellk.setColspan(4);
			pdfPtable.addCell(cellk);

			PdfPTable table3 = new PdfPTable(1);
			float[] relativeWidths4 = new float[] { 100f };
			table3.setWidths(relativeWidths4);

			PdfPCell cell14 = new PdfPCell(new Phrase(" ", fontArray[2]));
			cell14.setHorizontalAlignment(1);
			cell14.setVerticalAlignment(5);
			cell14.setFixedHeight(150f);
			cell14.setBorderWidth(0.0f);
			cell14.setColspan(1);
			table3.addCell(cell14);

			PdfPCell cell15 = new PdfPCell(table3);
			cell15.setFixedHeight(150f);
			cell15.setColspan(1);
			pdfPtable.addCell(cell15);

			PdfPTable table4 = new PdfPTable(4);
			float[] relativeWidths5 = new float[] { 30f, 30f, 20f, 20f };
			table4.setWidths(relativeWidths5);

			PdfPCell celll = new PdfPCell(new Phrase("投 保 單 位 代 號",
					fontArray[0]));
			celll.setHorizontalAlignment(1);
			celll.setVerticalAlignment(5);
			celll.setFixedHeight(30f);
			celll.setColspan(1);
			table4.addCell(celll);

			PdfPCell cellm = new PdfPCell(new Phrase(company.getHealthCode(),
					fontArray[0]));
			cellm.setHorizontalAlignment(1);
			cellm.setVerticalAlignment(5);
			cellm.setColspan(1);
			table4.addCell(cellm);

			PdfPCell celln = new PdfPCell(new Phrase("聯絡電話", fontArray[0]));
			celln.setHorizontalAlignment(1);
			celln.setVerticalAlignment(5);
			celln.setColspan(1);
			table4.addCell(celln);

			PdfPCell cello = new PdfPCell(new Phrase(company.getPhone(),
					fontArray[0]));
			cello.setHorizontalAlignment(1);
			cello.setVerticalAlignment(5);
			cello.setColspan(1);
			table4.addCell(cello);

			PdfPCell cellp = new PdfPCell(new Phrase("給   付   年   月",
					fontArray[0]));
			cellp.setHorizontalAlignment(1);
			cellp.setVerticalAlignment(5);
			cellp.setFixedHeight(30f);
			cellp.setColspan(1);
			table4.addCell(cellp);

			PdfPCell cellq = new PdfPCell(new Phrase(incomePeriod.replace('-',
					'/'), fontArray[0]));
			cellq.setHorizontalAlignment(1);
			cellq.setVerticalAlignment(5);
			cellq.setColspan(2);
			table4.addCell(cellq);

			PdfPCell cell16 = new PdfPCell(new Phrase("代收機構\n經收人員蓋章",
					fontArray[2]));
			cell16.setHorizontalAlignment(1);
			cell16.setVerticalAlignment(5);
			cell15.setLeading(3f, 1f);
			cell16.setColspan(1);
			table4.addCell(cell16);

			PdfPCell cellr = new PdfPCell(new Phrase("應   繳   金   額",
					fontArray[0]));
			cellr.setHorizontalAlignment(1);
			cellr.setVerticalAlignment(5);
			cellr.setFixedHeight(30f);
			cellr.setColspan(1);
			table4.addCell(cellr);

			PdfPCell cells = new PdfPCell(new Phrase(
					StringUtils.addComma(amount), fontArray[0]));
			cells.setHorizontalAlignment(1);
			cells.setVerticalAlignment(5);
			cells.setColspan(2);
			table4.addCell(cells);

			PdfPCell cell17 = new PdfPCell(new Phrase("", fontArray[0]));
			cell17.setBorderWidth(0.0f);
			cell17.setColspan(1);
			table4.addCell(cell17);

			PdfPCell cellt = new PdfPCell(new Phrase("", fontArray[0]));
			cellt.setFixedHeight(30f);
			cellt.setColspan(3);
			table4.addCell(cellt);

			PdfPCell cell18 = new PdfPCell(new Phrase("", fontArray[0]));
			cell18.setBorderWidth(0.0f);
			cell18.setColspan(1);
			table4.addCell(cell18);

			PdfPCell cellu = new PdfPCell(table4);
			cellu.setFixedHeight(130f);
			cellu.setColspan(4);
			pdfPtable.addCell(cellu);

			int num1 = 32;
			int num2 = 38;
			int num3 = 42;
			int num4 = 40;
			int num5 = 210;
			int num6 = 190;
			int num7 = 185;
			PdfContentByte directContent1 = instance.getDirectContent();
			directContent1.setFontAndSize(font2, 22f);
			directContent1.beginText();
			directContent1.setTextMatrix((float) num1, (float) (num6 + 10));
			directContent1.showText("*" + str3 + "*");
			int num8 = num6 - 20;
			directContent1.setTextMatrix((float) num1, (float) num8);
			directContent1.showText("*" + str4 + "*");
			int num9 = num8 - 20;
			directContent1.setTextMatrix((float) num1, (float) (num9 - 10));
			directContent1.showText("*" + text + "*");
			directContent1.endText();
			PdfContentByte directContent2 = instance.getDirectContent();
			directContent2.beginText();
			directContent2.setFontAndSize(font1, 9f);
			directContent2.setTextMatrix((float) num2, (float) (num5 + 10));
			directContent2.showText("金融機構或便利商店繳費專用條碼");
			if (Expression) {
				int num10 = (num5 - 85);
				directContent2
						.setTextMatrix((float) num3, (float) (num10 + 10));
				directContent2.showText("ATM或網路繳費鍵入資料");
				int num11 = (num10 - 13);
				directContent2.setTextMatrix((float) num3, (float) num11);
				directContent2.showText("1.   銀行代號004");
				int num12 = (num11 - 12);
				directContent2
						.setTextMatrix((float) num3, (float) (num12 - 10));
				directContent2.showText("2.   轉入帳號(銷帳編號)");
			}
			directContent2.endText();
			PdfContentByte directContent3 = instance.getDirectContent();
			directContent3.beginText();
			directContent3.setFontAndSize(font1, 6f);
			directContent3.setTextMatrix((float) num4, (float) (num7 + 10));
			directContent3.showText(str3);
			int num13 = (num7 - 20);
			directContent3.setTextMatrix((float) num4, (float) num13);
			directContent3.showText(str4);
			int num14 = (num13 - 21);
			directContent3.setTextMatrix((float) num4, (float) (num14 - 10));
			directContent3.showText(text);
			directContent3.endText();
			document.add(pdfPtable);
			document.close();

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	private void absText(PdfContentByte cb, String text, int x, int y,
			BaseFont bf, float fontSize) {
		try {
			cb.saveState();
			cb.beginText();
			cb.moveText(x, y);
			cb.setFontAndSize(bf, fontSize);
			cb.showText(text);
			cb.endText();
			cb.restoreState();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}