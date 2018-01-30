package com.csjian.form;

import com.csjian.model.bean.*;
import com.csjian.util.NHIDate;
import com.csjian.util.StringUtils;

import java.io.*;
import java.util.*;

import com.lowagie.text.pdf.*;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;

public class M0203 extends PdfPageEventHelper {

	public void renderForm(CompanyBean company,
			M0203DataBean addOnFeeDeclareData, String fromYear,
			String fromMonth, String toYear, String toMonth, OutputStream os)
			throws Exception {
		boolean flag1 = true;
		BaseFont font = BaseFont.createFont(System.getenv("windir")
				+ "\\fonts\\KAIU.TTF", "Identity-H", true);
		Document document2 = new Document(PageSize.A4, 50f, 50f, 50f, 50f);
		PdfWriter instance = PdfWriter.getInstance(document2, os);
		PdfPTable pdfPtable = new PdfPTable(9);
		try {
			
			document2.open();
			Font[] fontArray = new Font[] { new Font(font, 12f),
					new Font(font, 16f, 17), new Font(font, 12f, 17),
					new Font(font, 9f), new Font(font, 10f),
					new Font(font, 14f, 17), new Font(font, 13f, 17) };
			boolean flag2 = false;
			int num2 = 0;
			int num3 = 1;
			do {
				++num2;
				if (flag2 || num2 == 1) {
					pdfPtable = new PdfPTable(9);
					float[] relativeWidths = new float[] { 12f, 12f, 12f, 12f,
							15f, 5f, 8f, 12f, 12f };
					pdfPtable.setWidths(relativeWidths);
					pdfPtable.setWidthPercentage(100f);
					pdfPtable.getDefaultCell().setBorder(0);
				}
				if (flag2)
					document2.newPage();
				PdfPTable table1 = new PdfPTable(1);
				float[] relativeWidths1 = new float[] { 100f };
				table1.setWidths(relativeWidths1);
				table1.setWidthPercentage(100f);
				table1.getDefaultCell().setBorder(0);
				PdfPCell cell1 = new PdfPCell(
						new Phrase("全民健康保險", fontArray[1]));
				cell1.setHorizontalAlignment(1);
				cell1.setVerticalAlignment(5);
				cell1.setBorderWidth(0.0f);
				cell1.setFixedHeight(60f);
				cell1.setColspan(1);
				table1.addCell(cell1);
				PdfPCell cell2 = new PdfPCell(new Phrase(
						"各類所得(收入)扣繳補充保險費明細申報(更正)書", fontArray[6]));
				cell2.setHorizontalAlignment(1);
				cell2.setVerticalAlignment(5);
				cell2.setBorder(0);
				cell2.setFixedHeight(60f);
				cell2.setColspan(1);
				table1.addCell(cell2);
				PdfPCell cell3 = new PdfPCell(table1);
				cell3.setBorderWidth(0.0f);
				cell3.setFixedHeight(120f);
				cell3.setColspan(5);
				pdfPtable.addCell(cell3);

				PdfPCell cella = new PdfPCell(new Phrase(
						"扣      費       單       位", fontArray[0]));
				cella.setHorizontalAlignment(1);
				cella.setVerticalAlignment(5);
				cella.setFixedHeight(120f);
				cella.setColspan(1);
				pdfPtable.addCell(cella);
				PdfPTable table2 = new PdfPTable(2);
				float[] relativeWidths2 = new float[] { 25f, 75f };
				table2.setWidths(relativeWidths2);
				table2.setWidthPercentage(100f);
				table2.getDefaultCell().setBorder(0);
				PdfPCell cellb = new PdfPCell(
						new Phrase("統一\n編號", fontArray[4]));
				cellb.setHorizontalAlignment(1);
				cellb.setVerticalAlignment(5);
				cellb.setFixedHeight(30f);
				cellb.setColspan(1);
				table2.addCell(cellb);

				PdfPCell cellc = new PdfPCell(new Phrase(company.getRegcode(),
						fontArray[4]));
				cellc.setHorizontalAlignment(1);
				cellc.setVerticalAlignment(5);
				cellc.setColspan(1);
				table2.addCell(cellc);

				PdfPCell cell4 = new PdfPCell(new Phrase("名稱", fontArray[4]));
				cell4.setHorizontalAlignment(1);
				cell4.setVerticalAlignment(5);
				if (company.getRegname().length() <= 11)
					cell4.setFixedHeight(30f);
				cell4.setColspan(1);
				table2.addCell(cell4);

				PdfPCell celld = new PdfPCell(new Phrase(company.getRegname(),
						fontArray[4]));
				celld.setHorizontalAlignment(1);
				celld.setVerticalAlignment(5);
				celld.setColspan(1);
				table2.addCell(celld);

				PdfPCell cell5 = new PdfPCell(new Phrase("地址", fontArray[4]));
				cell5.setHorizontalAlignment(1);
				cell5.setVerticalAlignment(5);
				if (company.getAddress().length() <= 11)
					cell5.setFixedHeight(30f);
				cell5.setColspan(1);
				table2.addCell(cell5);

				PdfPCell celle = new PdfPCell(new Phrase(company.getAddress(),
						fontArray[4]));
				celle.setHorizontalAlignment(1);
				celle.setVerticalAlignment(5);
				celle.setColspan(1);
				table2.addCell(celle);

				PdfPCell cell6 = new PdfPCell(new Phrase("扣費\n義務人",
						fontArray[4]));
				cell6.setHorizontalAlignment(1);
				cell6.setVerticalAlignment(5);
				if (company.getBossName().length() <= 11)
					cell6.setFixedHeight(30f);
				cell6.setColspan(1);
				table2.addCell(cell6);

				PdfPCell cellf = new PdfPCell(new Phrase(company.getBossName(),
						fontArray[4]));
				cellf.setHorizontalAlignment(1);
				cellf.setVerticalAlignment(5);
				cellf.setColspan(1);
				table2.addCell(cellf);

				PdfPCell cellg = new PdfPCell(table2);
				cellg.setFixedHeight(120f);
				cellg.setColspan(3);
				pdfPtable.addCell(cellg);

				PdfPCell cellh = new PdfPCell(new Phrase("給  付  期  間",
						fontArray[0]));
				cellh.setHorizontalAlignment(1);
				cellh.setVerticalAlignment(5);
				cellh.setFixedHeight(30f);
				cellh.setColspan(2);
				pdfPtable.addCell(cellh);

				PdfPCell celli = new PdfPCell(new Phrase("自" + fromYear + "年"
						+ fromMonth + "月01日至" + fromYear + "年" + toMonth + "月"
						+ NHIDate.monthDays(fromYear + toMonth + "01") + "日",
						fontArray[0]));
				celli.setHorizontalAlignment(0);
				celli.setVerticalAlignment(5);
				celli.setFixedHeight(30f);
				celli.setColspan(8);
				pdfPtable.addCell(celli);

				PdfPCell cellj = new PdfPCell(new Phrase("申  報  方  式",
						fontArray[0]));
				cellj.setHorizontalAlignment(1);
				cellj.setVerticalAlignment(5);
				cellj.setFixedHeight(20f);
				cellj.setColspan(2);
				pdfPtable.addCell(cellj);

				PdfPCell cellk = new PdfPCell(new Phrase(
						"□媒體：○光碟(   )   ○其他(   )        □書面", fontArray[0]));
				cellk.setHorizontalAlignment(0);
				cellk.setVerticalAlignment(5);
				cellk.setFixedHeight(30f);
				cellk.setColspan(8);
				pdfPtable.addCell(cellk);

				PdfPTable table3 = new PdfPTable(5);
				float[] relativeWidths3 = new float[] { 22f, 3f, 15f, 25f, 25f };
				table3.setWidths(relativeWidths3);
				table3.setWidthPercentage(100f);
				table3.getDefaultCell().setBorder(0);

				PdfPCell celll = new PdfPCell(new Phrase("所得類別及扣繳代號",
						fontArray[0]));
				celll.setHorizontalAlignment(1);
				celll.setVerticalAlignment(5);
				celll.setFixedHeight(30f);
				celll.setColspan(2);
				table3.addCell(celll);

				PdfPCell cellm = new PdfPCell(new Phrase("件數", fontArray[0]));
				cellm.setHorizontalAlignment(1);
				cellm.setVerticalAlignment(5);
				cellm.setColspan(1);
				table3.addCell(cellm);

				PdfPCell celln = new PdfPCell(new Phrase("給付總額", fontArray[0]));
				celln.setHorizontalAlignment(1);
				celln.setVerticalAlignment(5);
				celln.setColspan(1);
				table3.addCell(celln);

				PdfPCell cello = new PdfPCell(new Phrase("扣繳補充保險費金額",
						fontArray[0]));
				cello.setHorizontalAlignment(1);
				cello.setVerticalAlignment(5);
				cello.setColspan(1);
				table3.addCell(cello);
				int index = 0;
				do {
					switch (index) {
					case 0: {
						PdfPCell cell7 = new PdfPCell(new Phrase(
								"所屬投保單位給付全年累計逾當月投保金額四倍部分之獎金", fontArray[4]));
						cell7.setHorizontalAlignment(0);
						cell7.setVerticalAlignment(5);
						cell7.setColspan(1);
						table3.addCell(cell7);

						PdfPCell cellp = new PdfPCell(new Phrase("62",
								fontArray[4]));
						cellp.setHorizontalAlignment(1);
						cellp.setVerticalAlignment(5);
						table3.addCell(cellp);

						if (index != 7) {
							PdfPCell cell13 = new PdfPCell(new Phrase(
									addOnFeeDeclareData.getCount62() + "",
									fontArray[4]));
							cell13.setHorizontalAlignment(1);
							cell13.setVerticalAlignment(5);
							cell13.setColspan(1);
							table3.addCell(cell13);
							PdfPCell cell14 = new PdfPCell(new Phrase(
									addOnFeeDeclareData.getIncomeAmount62()
											+ "", fontArray[4]));
							cell14.setHorizontalAlignment(1);
							cell14.setVerticalAlignment(5);
							cell14.setColspan(1);
							table3.addCell(cell14);
						}
						PdfPCell cell15 = new PdfPCell(new Phrase(
								addOnFeeDeclareData.getInsuranceAddOnFee62()
										+ "", fontArray[4]));
						cell15.setHorizontalAlignment(1);
						cell15.setVerticalAlignment(5);
						table3.addCell(cell15);
					}
						break;
					case 1: {
						PdfPCell cell7 = new PdfPCell(new Phrase(
								"非所屬投保單位給付之薪資所得", fontArray[4]));
						cell7.setHorizontalAlignment(0);
						cell7.setVerticalAlignment(5);
						cell7.setColspan(1);
						table3.addCell(cell7);

						PdfPCell cellp = new PdfPCell(new Phrase("63",
								fontArray[4]));
						cellp.setHorizontalAlignment(1);
						cellp.setVerticalAlignment(5);
						table3.addCell(cellp);
						if (index != 7) {
							PdfPCell cell13 = new PdfPCell(new Phrase(
									addOnFeeDeclareData.getCount63() + "",
									fontArray[4]));
							cell13.setHorizontalAlignment(1);
							cell13.setVerticalAlignment(5);
							cell13.setColspan(1);
							table3.addCell(cell13);
							PdfPCell cell14 = new PdfPCell(new Phrase(
									addOnFeeDeclareData.getIncomeAmount63()
											+ "", fontArray[4]));
							cell14.setHorizontalAlignment(1);
							cell14.setVerticalAlignment(5);
							cell14.setColspan(1);
							table3.addCell(cell14);
						}
						PdfPCell cell15 = new PdfPCell(new Phrase(
								addOnFeeDeclareData.getInsuranceAddOnFee63()
										+ "", fontArray[4]));
						cell15.setHorizontalAlignment(1);
						cell15.setVerticalAlignment(5);
						table3.addCell(cell15);
					}
						break;
					case 2: {
						PdfPCell cell7 = new PdfPCell(new Phrase("執行業務收入",
								fontArray[4]));
						cell7.setHorizontalAlignment(0);
						cell7.setVerticalAlignment(5);
						cell7.setFixedHeight(30f);
						cell7.setColspan(1);
						table3.addCell(cell7);

						PdfPCell cellp = new PdfPCell(new Phrase("65",
								fontArray[4]));
						cellp.setHorizontalAlignment(1);
						cellp.setVerticalAlignment(5);
						table3.addCell(cellp);

						if (index != 7) {
							PdfPCell cell13 = new PdfPCell(new Phrase(
									addOnFeeDeclareData.getCount65() + "",
									fontArray[4]));
							cell13.setHorizontalAlignment(1);
							cell13.setVerticalAlignment(5);
							cell13.setColspan(1);
							table3.addCell(cell13);
							PdfPCell cell14 = new PdfPCell(new Phrase(
									addOnFeeDeclareData.getIncomeAmount65()
											+ "", fontArray[4]));
							cell14.setHorizontalAlignment(1);
							cell14.setVerticalAlignment(5);
							cell14.setColspan(1);
							table3.addCell(cell14);
						}
						PdfPCell cell15 = new PdfPCell(new Phrase(
								addOnFeeDeclareData.getInsuranceAddOnFee65()
										+ "", fontArray[4]));
						cell15.setHorizontalAlignment(1);
						cell15.setVerticalAlignment(5);
						table3.addCell(cell15);
					}
						break;
					case 3: {
						PdfPCell cell7 = new PdfPCell(new Phrase("股利所得",
								fontArray[4]));
						cell7.setHorizontalAlignment(0);
						cell7.setVerticalAlignment(5);
						cell7.setFixedHeight(30f);
						cell7.setColspan(1);
						table3.addCell(cell7);

						PdfPCell cellp = new PdfPCell(new Phrase("66",
								fontArray[4]));
						cellp.setHorizontalAlignment(1);
						cellp.setVerticalAlignment(5);
						table3.addCell(cellp);

						if (index != 7) {
							PdfPCell cell13 = new PdfPCell(new Phrase(
									addOnFeeDeclareData.getCount66() + "",
									fontArray[4]));
							cell13.setHorizontalAlignment(1);
							cell13.setVerticalAlignment(5);
							cell13.setColspan(1);
							table3.addCell(cell13);
							PdfPCell cell14 = new PdfPCell(new Phrase(
									addOnFeeDeclareData.getIncomeAmount66()
											+ "", fontArray[4]));
							cell14.setHorizontalAlignment(1);
							cell14.setVerticalAlignment(5);
							cell14.setColspan(1);
							table3.addCell(cell14);
						}
						PdfPCell cell15 = new PdfPCell(new Phrase(
								addOnFeeDeclareData.getInsuranceAddOnFee66()
										+ "", fontArray[4]));
						cell15.setHorizontalAlignment(1);
						cell15.setVerticalAlignment(5);
						table3.addCell(cell15);
					}
						break;
					case 4: {
						PdfPCell cell7 = new PdfPCell(new Phrase("利息所得",
								fontArray[4]));
						cell7.setHorizontalAlignment(0);
						cell7.setVerticalAlignment(5);
						cell7.setColspan(1);
						table3.addCell(cell7);

						PdfPCell cellp = new PdfPCell(new Phrase("67",
								fontArray[4]));
						cellp.setHorizontalAlignment(1);
						cellp.setVerticalAlignment(5);
						cell7.setFixedHeight(30f);
						table3.addCell(cellp);

						if (index != 7) {
							PdfPCell cell13 = new PdfPCell(new Phrase(
									addOnFeeDeclareData.getCount67() + "",
									fontArray[4]));
							cell13.setHorizontalAlignment(1);
							cell13.setVerticalAlignment(5);
							cell13.setColspan(1);
							table3.addCell(cell13);
							PdfPCell cell14 = new PdfPCell(new Phrase(
									addOnFeeDeclareData.getIncomeAmount67()
											+ "", fontArray[4]));
							cell14.setHorizontalAlignment(1);
							cell14.setVerticalAlignment(5);
							cell14.setColspan(1);
							table3.addCell(cell14);
						}
						PdfPCell cell15 = new PdfPCell(new Phrase(
								addOnFeeDeclareData.getInsuranceAddOnFee67()
										+ "", fontArray[4]));
						cell15.setHorizontalAlignment(1);
						cell15.setVerticalAlignment(5);
						table3.addCell(cell15);
					}
						break;
					case 5: {
						PdfPCell cell7 = new PdfPCell(new Phrase("租金收入",
								fontArray[4]));
						cell7.setHorizontalAlignment(0);
						cell7.setVerticalAlignment(5);
						cell7.setFixedHeight(30f);
						cell7.setColspan(1);
						table3.addCell(cell7);

						PdfPCell cellp = new PdfPCell(new Phrase("68",
								fontArray[4]));
						cellp.setHorizontalAlignment(1);
						cellp.setVerticalAlignment(5);
						table3.addCell(cellp);

						if (index != 7) {
							PdfPCell cell13 = new PdfPCell(new Phrase(
									addOnFeeDeclareData.getCount68() + "",
									fontArray[4]));
							cell13.setHorizontalAlignment(1);
							cell13.setVerticalAlignment(5);
							cell13.setColspan(1);
							table3.addCell(cell13);
							PdfPCell cell14 = new PdfPCell(new Phrase(
									addOnFeeDeclareData.getIncomeAmount68()
											+ "", fontArray[4]));
							cell14.setHorizontalAlignment(1);
							cell14.setVerticalAlignment(5);
							cell14.setColspan(1);
							table3.addCell(cell14);
						}
						PdfPCell cell15 = new PdfPCell(new Phrase(
								addOnFeeDeclareData.getInsuranceAddOnFee68()
										+ "", fontArray[4]));
						cell15.setHorizontalAlignment(1);
						cell15.setVerticalAlignment(5);
						table3.addCell(cell15);
					}
						break;
					case 6: {
						PdfPCell cellq = new PdfPCell(new Phrase(
								"合           計", fontArray[4]));
						cellq.setHorizontalAlignment(1);
						cellq.setVerticalAlignment(5);
						cellq.setFixedHeight(30f);
						cellq.setColspan(2);
						table3.addCell(cellq);

						if (index != 7) {
							PdfPCell cell13 = new PdfPCell(new Phrase(
									addOnFeeDeclareData.getCount() + "",
									fontArray[4]));
							cell13.setHorizontalAlignment(1);
							cell13.setVerticalAlignment(5);
							cell13.setColspan(1);
							table3.addCell(cell13);
							PdfPCell cell14 = new PdfPCell(new Phrase(
									addOnFeeDeclareData.getIncomeAmount() + "",
									fontArray[4]));
							cell14.setHorizontalAlignment(1);
							cell14.setVerticalAlignment(5);
							cell14.setColspan(1);
							table3.addCell(cell14);
						}
						PdfPCell cell15 = new PdfPCell(
								new Phrase(addOnFeeDeclareData
										.getInsuranceAddOnFee() + "",
										fontArray[4]));
						cell15.setHorizontalAlignment(1);
						cell15.setVerticalAlignment(5);
						table3.addCell(cell15);
					}
						break;
					}

					++index;
				} while (index <= 6);

				PdfPCell cellr = new PdfPCell(table3);
				cellr.setFixedHeight(240f);
				cellr.setColspan(9);
				pdfPtable.addCell(cellr);
				PdfPTable table4 = new PdfPTable(2);
				float[] relativeWidths4 = new float[] { 10f, 90f };
				table4.setWidths(relativeWidths4);
				table4.setWidthPercentage(100f);
				table4.getDefaultCell().setBorder(0);
				PdfPCell cell16 = new PdfPCell(new Phrase("", fontArray[4]));
				cell16.setHorizontalAlignment(0);
				cell16.setVerticalAlignment(1);
				cell16.setBorderWidth(0.0f);
				cell16.setFixedHeight(30f);
				cell16.setColspan(1);
				table4.addCell(cell16);
				PdfPCell cell17 = new PdfPCell(new Phrase("此致", fontArray[4]));
				cell17.setHorizontalAlignment(0);
				cell17.setVerticalAlignment(5);
				cell17.setBorderWidth(0.0f);
				cell17.setColspan(1);
				table4.addCell(cell17);
				PdfPCell cell18 = new PdfPCell(new Phrase("", fontArray[5]));
				cell18.setHorizontalAlignment(0);
				cell18.setVerticalAlignment(5);
				cell18.setBorderWidth(0.0f);
				cell18.setFixedHeight(30f);
				cell18.setColspan(1);
				table4.addCell(cell18);
				PdfPCell cell19 = new PdfPCell(new Phrase("行政院衛生署中央健康保險局",
						fontArray[5]));
				cell19.setHorizontalAlignment(0);
				cell19.setVerticalAlignment(5);
				cell19.setBorderWidth(0.0f);
				cell19.setColspan(1);
				table4.addCell(cell19);
				PdfPCell cell20 = new PdfPCell(new Phrase("", fontArray[0]));
				cell20.setHorizontalAlignment(0);
				cell20.setVerticalAlignment(5);
				cell20.setBorderWidth(0.0f);
				cell20.setFixedHeight(30f);
				cell20.setColspan(1);
				table4.addCell(cell20);
				PdfPCell cell21 = new PdfPCell(new Phrase("扣費單位蓋章：",
						fontArray[0]));
				cell21.setHorizontalAlignment(0);
				cell21.setVerticalAlignment(5);
				cell21.setBorderWidth(0.0f);
				cell21.setColspan(1);
				table4.addCell(cell21);
				PdfPCell cell22 = new PdfPCell(new Phrase("", fontArray[0]));
				cell22.setHorizontalAlignment(0);
				cell22.setVerticalAlignment(5);
				cell22.setBorderWidth(0.0f);
				cell22.setFixedHeight(30f);
				cell22.setColspan(1);
				table4.addCell(cell22);
				PdfPCell cell23 = new PdfPCell(new Phrase("扣費義務人簽章：",
						fontArray[0]));
				cell23.setHorizontalAlignment(0);
				cell23.setVerticalAlignment(5);
				cell23.setBorderWidth(0.0f);
				cell23.setColspan(1);
				table4.addCell(cell23);
				PdfPCell cell24 = new PdfPCell(new Phrase("", fontArray[0]));
				cell24.setHorizontalAlignment(0);
				cell24.setVerticalAlignment(5);
				cell24.setBorderWidth(0.0f);
				cell24.setFixedHeight(30f);
				cell24.setColspan(1);
				table4.addCell(cell24);
				PdfPCell cell25 = new PdfPCell(new Phrase("聯絡人簽章：",
						fontArray[0]));
				cell25.setHorizontalAlignment(0);
				cell25.setVerticalAlignment(5);
				cell25.setBorderWidth(0.0f);
				cell25.setColspan(1);
				table4.addCell(cell25);
				PdfPCell cell26 = new PdfPCell(new Phrase("", fontArray[0]));
				cell26.setHorizontalAlignment(0);
				cell26.setVerticalAlignment(5);
				cell26.setBorderWidth(0.0f);
				cell26.setFixedHeight(30f);
				cell26.setColspan(1);
				table4.addCell(cell26);
				PdfPCell cell27 = new PdfPCell(
						new Phrase("聯絡電話：", fontArray[0]));
				cell27.setHorizontalAlignment(0);
				cell27.setVerticalAlignment(5);
				cell27.setBorderWidth(0.0f);
				cell27.setColspan(1);
				table4.addCell(cell27);
				PdfPCell cell28 = new PdfPCell(new Phrase("", fontArray[0]));
				cell28.setHorizontalAlignment(0);
				cell28.setVerticalAlignment(5);
				cell28.setBorderWidth(0.0f);
				cell28.setFixedHeight(30f);
				cell28.setColspan(1);
				table4.addCell(cell28);
				PdfPCell cell29 = new PdfPCell(
						new Phrase(
								"中 　華 　民 　國          年               月               日",
								fontArray[0]));
				cell29.setHorizontalAlignment(0);
				cell29.setVerticalAlignment(5);
				cell29.setBorderWidth(0.0f);
				cell29.setColspan(1);
				table4.addCell(cell29);
				PdfPCell cell30 = new PdfPCell(new Phrase("", fontArray[0]));
				cell30.setHorizontalAlignment(0);
				cell30.setVerticalAlignment(5);
				cell30.setBorderWidth(0.0f);
				cell30.setFixedHeight(30f);
				cell30.setColspan(1);
				table4.addCell(cell30);
				if ((double) num2 == 1) {
					PdfPCell cell7 = new PdfPCell(new Phrase(
							"(一式二聯，第１聯報核聯：由健保局建檔後存查)", fontArray[0]));
					cell7.setHorizontalAlignment(0);
					cell7.setVerticalAlignment(5);
					cell7.setBorderWidth(0.0f);
					cell7.setColspan(1);
					table4.addCell(cell7);
					PdfPCell cell8 = new PdfPCell(table4);
					cell8.setFixedHeight(240f);
					cell8.setBorderWidth(0.0f);
					cell8.setColspan(9);
					pdfPtable.addCell(cell8);
				} else {
					PdfPCell cell7 = new PdfPCell(new Phrase(
							"(一式二聯，第２聯備查聯：由扣費單位保存備查)", fontArray[0]));
					cell7.setHorizontalAlignment(0);
					cell7.setVerticalAlignment(5);
					cell7.setBorderWidth(0.0f);
					cell7.setColspan(1);
					table4.addCell(cell7);
					PdfPCell cell8 = new PdfPCell(table4);
					cell8.setBorderWidth(0.0f);
					cell8.setFixedHeight(270f);
					cell8.setColspan(9);
					pdfPtable.addCell(cell8);
				}
				if (num2 == 1) {
					flag2 = true;
					document2.add(pdfPtable);
				} else
					flag2 = false;
				++num3;
			} while (num3 <= 2);
			document2.add(pdfPtable);
			flag1 = true;
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			document2.close();
			instance.close();
		}
	}
}