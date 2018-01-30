package com.csjian.report.xls;

import com.csjian.model.bean.*;
import java.io.OutputStream;
import java.util.*;

import jxl.CellView;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.write.Label;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class EmployeeListXLS {

	public static void generateXLS(CompanyBean company, List<EmployeeBean> employees, OutputStream os)
			throws Exception {
		try {
			WritableWorkbook workbook = Workbook.createWorkbook(os);
			WritableSheet sheetAp = workbook.createSheet("員工基本資料表", 0);
			
			int row = 0;
			int col = 0;
			if (company != null) {
					// 設定 table header
					jxl.write.WritableFont wf = new jxl.write.WritableFont(WritableFont.TIMES, 18, WritableFont.BOLD, true);  
					jxl.write.WritableCellFormat wcfF = new jxl.write.WritableCellFormat(wf);  
					wcfF.setAlignment(Alignment.CENTRE);
					
					sheetAp.mergeCells(0, row, 6, row);
					sheetAp.addCell(new Label(col, row, company.getRegname() + " 員工基本資料表", wcfF));
					
					col = 0;
					row++;
					sheetAp.addCell(new Label(col++, row, "員工編號"));					
					sheetAp.addCell(new Label(col++, row, "職稱"));
					sheetAp.addCell(new Label(col++, row, "姓名"));
					sheetAp.addCell(new Label(col++, row, "身份證號"));
					sheetAp.addCell(new Label(col++, row, "住址"));
					sheetAp.addCell(new Label(col++, row, "銀行帳號"));
					
					EmployeeBean employee = null;
					for (int i = 0; i < employees.size(); i++) {
						row++;
						employee = employees.get(i);
						col = 0;
						sheetAp.addCell(new Label(col++, row, employee.getEmployeeno()));
						sheetAp.addCell(new Label(col++, row, employee.getTitle()));
						sheetAp.addCell(new Label(col++, row, employee.getName()));
						sheetAp.addCell(new Label(col++, row, employee.getUnicode()));
						sheetAp.addCell(new Label(col++, row, employee.getAddress()));
						sheetAp.addCell(new Label(col++, row, employee.getAccountno()));
						
					}
					
					CellView cellView;
					for(int x=0;x<6;x++) {
						cellView=sheetAp.getColumnView(x);
						cellView.setAutosize(true);
						sheetAp.setColumnView(x, cellView);
					}
				workbook.write();
			    workbook.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
}