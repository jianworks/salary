package com.csjian.report.xls;

import com.csjian.model.bean.*;
import com.csjian.util.*;

import java.io.OutputStream;
import java.util.*;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class P01Reporter {

	public static void generatePDF(String regcode, String year, String month,
			CompanyBean company, SalaryBean[] salaryList, OutputStream os)
			throws Exception {
		Calendar cal = Calendar.getInstance();
		try {
			WritableWorkbook workbook = Workbook.createWorkbook(os);
			WritableSheet sheetAp = workbook.createSheet("薪資報表", 0);
			
			int row = 0;
			int col = 0;
			if (company != null) {
				if (salaryList != null && salaryList.length > 0) {
					Vector pitem = salaryList[0].getItemp();
					Vector mitem = salaryList[0].getItemm();
					// 總欄位數
					int[] total = new int[pitem.size() + mitem.size() + 4];
					// 設定 table header
					jxl.write.WritableFont wf = new jxl.write.WritableFont(WritableFont.TIMES, 18, WritableFont.BOLD, true);  
					jxl.write.WritableCellFormat wcfF = new jxl.write.WritableCellFormat(wf);  
					wcfF.setAlignment(Alignment.CENTRE);
					
					sheetAp.mergeCells(0, row, pitem.size() + mitem.size() + 4 + 2, row);
					sheetAp.addCell(new Label(col, row, company.getRegname() + year + "年" + (month.equals("")?"":month + "月") + "薪資表", wcfF));
					
					col = 0;
					row++;
					sheetAp.addCell(new Label(col++, row, "員工編號"));
					sheetAp.addCell(new Label(col++, row, "姓名"));
					sheetAp.addCell(new Label(col++, row, "基本薪資"));
					sheetAp.addCell(new Label(col++, row, "加班薪資"));
					
					for (int i = 0; i < pitem.size(); i++) {
						sheetAp.addCell(new Label(col++, row, ((String[]) pitem.elementAt(i))[1]));
					}
					for (int i = 0; i < mitem.size(); i++) {
						sheetAp.addCell(new Label(col++, row, ((String[]) mitem.elementAt(i))[1]));
					}
					sheetAp.addCell(new Label(col++, row, "扣繳稅額"));
					sheetAp.addCell(new Label(col++, row, "實發金額"));
					
					row++;

					for (int i = 0; i < salaryList.length; i++) {
						col = 0;
						pitem = salaryList[i].getItemp();
						mitem = salaryList[i].getItemm();
						int k = 0;
						sheetAp.addCell(new Label(col++, row, salaryList[i].getEmployeeno()));
						sheetAp.addCell(new Label(col++, row, salaryList[i].getName()));
						sheetAp.addCell(new Label(col++, row, salaryList[i].getBasesalary()));
						total[k] += Integer.parseInt(salaryList[i].getBasesalary() != null && !salaryList[i].getBasesalary().equals("") ? salaryList[i].getBasesalary() : "0");
						k++;
						sheetAp.addCell(new Label(col++, row, salaryList[i].getOversalary()));
						total[k] += Integer.parseInt(salaryList[i].getOversalary() != null && !salaryList[i].getOversalary().equals("") ? salaryList[i].getOversalary() : "0");
						k++;

						for (int j = 0; j < pitem.size(); j++) {
							String[] item = (String[]) pitem.elementAt(j);
							sheetAp.addCell(new Label(col++, row, item[2] != null ? item[2]:"0"));
							total[k] += Integer.parseInt(item[2] != null && !item[2].equals("") ? item[2] : "0");
							k++;
						}

						for (int j = 0; j < mitem.size(); j++) {
							String[] item = (String[]) mitem.elementAt(j);
							sheetAp.addCell(new Label(col++, row, item[2] != null ? item[2]:"0"));
							total[k] += Integer.parseInt(item[2] != null && !item[2].equals("") ? item[2] : "0");
							k++;
						}
						sheetAp.addCell(new Label(col++, row, salaryList[i].getTax()));
						total[k] += Integer.parseInt(salaryList[i].getTax() != null	&& !salaryList[i].getTax().equals("") ? salaryList[i].getTax() : "0");
						k++;
						sheetAp.addCell(new Label(col++, row, salaryList[i].getTotal()));
						total[k] += Integer.parseInt(salaryList[i].getTotal() != null && !salaryList[i].getTotal().equals("") ? salaryList[i].getTotal() : "0");
						k++;
						
						row++;
					}

					col = 0;
					for (int i = 0; i < (2 + total.length); i++) {
						sheetAp.addCell(new Label(col++, row, ""));
					}
					row++;
					col = 0;
					sheetAp.addCell(new Label(col++, row, "合計"));
					sheetAp.addCell(new Label(col++, row, ""));
					for (int i = 0; i < total.length; i++) {
						sheetAp.addCell(new Label(col++, row, total[i]+""));
					}
				} else {
					sheetAp.addCell(new Label(col++, row, "員工編號"));
					sheetAp.addCell(new Label(col++, row, "姓名"));
					sheetAp.addCell(new Label(col++, row, "基本薪資"));
					sheetAp.addCell(new Label(col++, row, "加班薪資"));
					
					row++;

					sheetAp.addCell(new Label(col++, row, ""));
					sheetAp.addCell(new Label(col++, row, ""));
					sheetAp.addCell(new Label(col++, row, ""));
					sheetAp.addCell(new Label(col++, row, ""));
				}

				workbook.write();
			    workbook.close();
			}
		} catch (Exception e) {
			System.out.println("regcode:" + regcode + "|year:" + year
					+ "|month" + month);
			e.printStackTrace();
			throw e;
		}
	}
}