package com.csjian.model.dao;

import com.csjian.model.bean.SalaryBean;

import java.sql.*;
import java.util.*;

public class SalaryDao extends TemplateDao {

	public SalaryBean retriveSalary(String regcode, String employeeno, String year, String month, boolean isread)
			throws Exception {
		Connection conn = null;
		ResultSet rs = null, rs0 = null, rs1 = null;
		Vector itemp = new Vector();
		Vector itemm = new Vector();
		SalaryBean salary = new SalaryBean();
		try {
			salary.setRegcode(regcode);
			salary.setEmployeeno(employeeno);
			salary.setYear(year);
			salary.setMonth(month);

			conn = this.dataSource.getConnection();
			Statement stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM Employees WHERE regcode='" + regcode + "' AND employeeno='" + employeeno
					+ "' AND ayear='" + year + "'");
			if (rs.next()) {
				salary.setGovinsurance(rs.getString("govinsurance") != null ? rs.getString("govinsurance") : "");
				salary.setName(rs.getString("name") != null ? rs.getString("name") : "");
			}
			rs = stmt.executeQuery("SELECT * FROM Salarys WHERE regcode='" + regcode + "' AND employeeno='" + employeeno
					+ "' AND ayear='" + year + "' AND amonth='" + month + "'");
			if (rs.next()) { // 已有薪資資料
				salary.setBasesalary(rs.getString("basesalary") != null ? rs.getString("basesalary") : "");
				salary.setBaserate(rs.getString("baserate") != null ? rs.getString("baserate") : "");
				salary.setOvertimerate(rs.getString("overtimerate") != null ? rs.getString("overtimerate") : "");
				salary.setOvertime(rs.getString("overtime") != null ? rs.getString("overtime") : "");
				salary.setOversalary(rs.getString("oversalary") != null ? rs.getString("oversalary") : "");
				salary.setWorkinghr(rs.getString("workinghr") != null ? rs.getString("workinghr") : "");
				salary.setBtotal(rs.getString("btotal") != null ? rs.getString("btotal") : "");
				salary.setPtotal(rs.getString("ptotal") != null ? rs.getString("ptotal") : "");
				salary.setMtotal(rs.getString("mtotal") != null ? rs.getString("mtotal") : "");
				salary.setTotal(rs.getString("total") != null ? rs.getString("total") : "");
				salary.setTax(rs.getString("tax") != null ? rs.getString("tax") : "");

				PreparedStatement pstmt = conn.prepareStatement("SELECT amount FROM SalaryItems WHERE regcode='" + regcode + "'"
						+ " AND employeeno='" + employeeno + "' AND ayear='" + year + "' AND amonth='" + month
						+ "' AND itemmark=? AND seqno=?");
				pstmt.setString(1, "P");

				rs0 = stmt.executeQuery("SELECT a.seqno, b.name FROM Enableps AS a, PItems AS b "
						+ "WHERE a.seqno=b.seqno AND a.regcode='" + regcode + "' ORDER BY a.seqno");
				while (rs0.next()) {
					pstmt.setString(2, rs0.getString(1));
					rs1 = pstmt.executeQuery();
					String[] item = { rs0.getString(1), rs0.getString(2),
							rs1.next() && rs1.getString(1) != null ? rs1.getString(1) : "" };
					itemp.addElement(item);
				}
				salary.setItemp(itemp);

				rs0 = stmt.executeQuery("SELECT a.seqno, b.name FROM Enablems AS a, MItems AS b "
						+ "WHERE a.seqno=b.seqno AND a.regcode='" + regcode + "' ORDER BY a.seqno");
				pstmt.setString(1, "M");
				while (rs0.next()) {
					pstmt.setString(2, rs0.getString(1));
					rs1 = pstmt.executeQuery();
					String[] item = { rs0.getString(1), rs0.getString(2),
							rs1.next() && rs1.getString(1) != null ? rs1.getString(1) : "" };
					itemm.addElement(item);
				}
				salary.setItemm(itemm);
			} else if (!isread) { // 從 bsalary 取得基本資料
				salary.setSaved(false);
				int btotal = 0;
				int ptotal = 0;
				int mtotal = 0;

				rs = stmt.executeQuery("SELECT * FROM BSalarys WHERE regcode='" + regcode + "' AND employeeno='" + employeeno
						+ "' AND ayear='" + year + "'");
				if (rs.next()) {
					salary.setBasesalary(rs.getString("basesalary") != null ? rs.getString("basesalary") : "");
					salary.setBaserate(rs.getString("baserate") != null ? rs.getString("baserate") : "");
					salary.setOvertimerate(rs.getString("overtimerate") != null ? rs.getString("overtimerate") : "");
				}

				if (!salary.getBasesalary().equals(""))
					btotal = Integer.parseInt(salary.getBasesalary());
				salary.setBtotal("" + btotal);

				rs0 = stmt.executeQuery("SELECT a.seqno, b.name FROM Enableps AS a, PItems AS b "
						+ "WHERE a.seqno=b.seqno AND a.regcode='" + regcode + "' ORDER BY a.seqno");
				PreparedStatement pstmt = conn.prepareStatement("SELECT amount FROM BSalaryItems WHERE regcode='" + regcode
						+ "'" + " AND employeeno='" + employeeno + "' AND ayear='" + year + "' AND itemmark=? AND seqno=?");
				pstmt.setString(1, "P");

				while (rs0.next()) {
					pstmt.setString(2, rs0.getString(1));
					rs1 = pstmt.executeQuery();
					String[] item = { rs0.getString(1), rs0.getString(2),
							rs1.next() && rs1.getString(1) != null ? rs1.getString(1) : "" };
					if (!item[2].equals(""))
						ptotal += Integer.parseInt(item[2]);
					itemp.addElement(item);
				}
				salary.setItemp(itemp);
				salary.setPtotal("" + ptotal);

				rs0 = stmt.executeQuery("SELECT a.seqno, b.name FROM Enablems AS a, MItems AS b "
						+ "WHERE a.seqno=b.seqno AND a.regcode='" + regcode + "' ORDER BY a.seqno");
				pstmt.setString(1, "M");
				while (rs0.next()) {
					pstmt.setString(2, rs0.getString(1));
					rs1 = pstmt.executeQuery();
					String[] item = { rs0.getString(1), rs0.getString(2),
							rs1.next() && rs1.getString(1) != null ? rs1.getString(1) : "" };
					if (!item[2].equals(""))
						mtotal += Integer.parseInt(item[2]);
					itemm.addElement(item);
				}
				salary.setItemm(itemm);
				salary.setMtotal("" + mtotal);

				salary.setTotal("" + (btotal + ptotal - mtotal));
			}
		} catch (Exception e) {
			System.out.println("error in regcode " + regcode);
			e.printStackTrace();
		} finally {
			close(rs);
			close(rs0);
			close(rs1);
			close(conn);
		}
		return salary;
	}

	public void update(SalaryBean salary) throws Exception {
		Connection conn = null;
		try {
			conn = this.dataSource.getConnection();
			Statement stmt = conn.createStatement();
			PreparedStatement updStmt = null;

			stmt.executeUpdate("DELETE FROM Salarys WHERE regcode='" + salary.getRegcode() + "' AND employeeno='"
					+ salary.getEmployeeno() + "' AND ayear='" + salary.getYear() + "' AND amonth='" + salary.getMonth() + "'");
			updStmt = conn.prepareStatement("INSERT INTO Salarys(regcode, employeeno, ayear, amonth, basesalary, baserate, overtimerate, " + 
					"overtime, oversalary, workinghr, btotal, ptotal, mtotal, total, tax, " + 
					"healthinsurance, laborinsurance, healthinsurancefee, laborinsurancefee, laborretirefee, retirefee) " + 
					"VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			updStmt.setString(1, salary.getRegcode());
			updStmt.setString(2, salary.getEmployeeno());
			updStmt.setString(3, salary.getYear());
			updStmt.setString(4, salary.getMonth());
			if (salary.getBasesalary() != null && !salary.getBasesalary().equals("")) {
				updStmt.setString(5, salary.getBasesalary());
			} else {
				updStmt.setNull(5, Types.INTEGER);
			}
			if (salary.getBaserate() != null && !salary.getBaserate().equals("")) {
				updStmt.setString(6, salary.getBaserate());
			} else {
				updStmt.setNull(6, Types.INTEGER);
			}
			if (salary.getOvertimerate() != null && !salary.getOvertimerate().equals("")) {
				updStmt.setString(7, salary.getOvertimerate());
			} else {
				updStmt.setNull(7, Types.INTEGER);
			}
			if (salary.getOvertime() != null && !salary.getOvertime().equals("")) {
				updStmt.setString(8, salary.getOvertime());
			} else {
				updStmt.setNull(8, Types.INTEGER);
			}
			if (salary.getOversalary() != null && !salary.getOversalary().equals("")) {
				updStmt.setString(9, salary.getOversalary());
			} else {
				updStmt.setNull(9, Types.INTEGER);
			}
			if (salary.getWorkinghr() != null && !salary.getWorkinghr().equals("")) {
				updStmt.setString(10, salary.getWorkinghr());
			} else {
				updStmt.setNull(10, Types.INTEGER);
			}
			if (salary.getBtotal() != null && !salary.getBtotal().equals("")) {
				updStmt.setString(11, salary.getBtotal());
			} else {
				updStmt.setNull(11, Types.INTEGER);
			}
			if (salary.getPtotal() != null && !salary.getPtotal().equals("")) {
				updStmt.setString(12, salary.getPtotal());
			} else {
				updStmt.setNull(12, Types.INTEGER);
			}
			if (salary.getMtotal() != null && !salary.getMtotal().equals("")) {
				updStmt.setString(13, salary.getMtotal());
			} else {
				updStmt.setNull(13, Types.INTEGER);
			}
			if (salary.getTotal() != null && !salary.getTotal().equals("")) {
				updStmt.setString(14, salary.getTotal());
			} else {
				updStmt.setNull(14, Types.INTEGER);
			}
			if (salary.getTax() != null && !salary.getTax().equals("")) {
				updStmt.setString(15, salary.getTax());
			} else {
				updStmt.setNull(15, Types.INTEGER);
			}
			if (salary.getHealthInsurance() != null && !salary.getHealthInsurance().equals("")) {
				updStmt.setString(16, salary.getHealthInsurance());
			} else {
				updStmt.setInt(16, 0);
			}
			if (salary.getLaborInsurance() != null && !salary.getLaborInsurance().equals("")) {
				updStmt.setString(17, salary.getLaborInsurance());
			} else {
				updStmt.setInt(17, 0);
			}
			if (salary.getHealthInsuranceFee() != null && !salary.getHealthInsuranceFee().equals("")) {
				updStmt.setString(18, salary.getHealthInsuranceFee());
			} else {
				updStmt.setInt(18, 0);
			}
			if (salary.getLaborInsuranceFee() != null && !salary.getLaborInsuranceFee().equals("")) {
				updStmt.setString(19, salary.getLaborInsuranceFee());
			} else {
				updStmt.setInt(19, 0);
			}
			if (salary.getLaborRetireFee() != null && !salary.getLaborRetireFee().equals("")) {
				updStmt.setString(20, salary.getLaborRetireFee());
			} else {
				updStmt.setInt(20, 0);
			}
			if (salary.getRetireFee() != null && !salary.getRetireFee().equals("")) {
				updStmt.setString(21, salary.getRetireFee());
			} else {
				updStmt.setInt(21, 0);
			}
			updStmt.executeUpdate();

			stmt.executeUpdate("DELETE FROM SalaryItems WHERE regcode='" + salary.getRegcode() + "' AND employeeno='"
					+ salary.getEmployeeno() + "' AND ayear='" + salary.getYear() + "' AND amonth='" + salary.getMonth() + "'");
			updStmt = conn
					.prepareStatement("INSERT INTO SalaryItems(regcode, employeeno, ayear, amonth, seqno, itemmark, amount) VALUES(?, ?, ?, ?, ?, ?, ?)");
			updStmt.setString(1, salary.getRegcode());
			updStmt.setString(2, salary.getEmployeeno());
			updStmt.setString(3, salary.getYear());
			updStmt.setString(4, salary.getMonth());

			Vector itemp = salary.getItemp();
			updStmt.setString(6, "P");
			for (int i = 0; i < itemp.size(); i++) {
				String[] item = (String[]) itemp.elementAt(i);
				// if (item[2]!=null && !item[2].equals("")) {
				updStmt.setString(5, item[0]);
				updStmt.setString(7, item[2] != null && !item[2].equals("") ? item[2] : "0");
				updStmt.executeUpdate();
				// }
			}

			Vector itemm = salary.getItemm();
			updStmt.setString(6, "M");
			for (int i = 0; i < itemm.size(); i++) {
				String[] item = (String[]) itemm.elementAt(i);
				// if (item[2]!=null && !item[2].equals("")) {
				updStmt.setString(5, item[0]);
				updStmt.setString(7, item[2] != null && !item[2].equals("") ? item[2] : "0");
				updStmt.executeUpdate();
				// }
			}
		} catch (Exception e) {
			System.out.println("error in regcode " + salary.getRegcode());
			e.printStackTrace();
		} finally {
			close(conn);
		}
	}

	public List findActiveEmployeeSalary(String regcode, String year, String month) {
		Connection conn = null;
		List salarys = new ArrayList();
		String mstart = year + "/" + (month.length()>1?month:"0"+month) + "/01";
		String mend = year + "/" + (month.length()>1?month:"0"+month) + "/31";
		ResultSet rs = null, rs0 = null;
		try {
			conn = this.dataSource.getConnection();
			Statement stmt = conn.createStatement();

			rs = stmt.executeQuery("SELECT employeeno, name FROM Employees WHERE CONVERT(varchar(12) , onboarddate, 111 )<='"
					+ mend + "' " + " AND (resigndate is null OR CONVERT(varchar(12) , resigndate, 111 )>='" + mstart + "') "
					+ " AND regcode='" + regcode + "' AND ayear='" + year + "' ORDER BY employeeno");
			PreparedStatement pstmt = conn.prepareStatement("SELECT total FROM Salarys WHERE regcode='" + regcode
					+ "' AND ayear='" + year + "' AND amonth='" + month + "' AND employeeno=?");
			while (rs.next()) {
				pstmt.setString(1, rs.getString(1));
				rs0 = pstmt.executeQuery();
				String[] salary = { rs.getString(1), rs.getString(2),
						rs0.next() && rs0.getString(1) != null ? rs0.getString(1) : "" };
				salarys.add(salary);
			}
		} catch (Exception e) {
			System.out.println("error in regcode " + regcode);
			e.printStackTrace();
		} finally {
			close(rs);
			close(rs0);
			close(conn);
		}
		return salarys;
	}

	public List findSalary(String regcode, String year, String month) {
		Connection conn = null;
		List salarys = new ArrayList();
		// String mstart = year + "/" + month + "/1";
		// String mend = year + "/" + month + "/31";
		ResultSet rs = null, rs0 = null;
		try {
			conn = this.dataSource.getConnection();
			Statement stmt = conn.createStatement();
			/*
			 * rs =stmt.executeQuery(
			 * "SELECT employeeno, name FROM employee WHERE onboarddate<='" + mend +
			 * "' " + " AND (resigndate is null OR resigndate>='" + mstart + "') " +
			 * " AND regcode='" + regcode + "' AND ayear='" + ayear +
			 * "' ORDER BY employeeno");
			 */
			rs = stmt.executeQuery("SELECT DISTINCT a.employeeno, a.name FROM Employees a, Salarys b WHERE "
					+ " a.regcode=b.regcode and a.employeeno=b.employeeno " + " AND b.ayear='" + year + "' AND b.amonth='"
					+ month + "' and a.regcode='" + regcode + "' ORDER BY a.employeeno");

			PreparedStatement pstmt = conn.prepareStatement("SELECT total FROM Salarys WHERE regcode='" + regcode
					+ "' AND ayear='" + year + "' AND amonth='" + month + "' AND employeeno=?");
			while (rs.next()) {
				pstmt.setString(1, rs.getString(1));
				rs0 = pstmt.executeQuery();
				String[] salary = { rs.getString(1), rs.getString(2),
						rs0.next() && rs0.getString(1) != null ? rs0.getString(1) : "" };
				salarys.add(salary);
			}
		} catch (Exception e) {
			System.out.println("error in regcode " + regcode);
			e.printStackTrace();
		} finally {
			close(rs);
			close(rs0);
			close(conn);
		}
		return salarys;
	}

	public SalaryBean[] getAllSalaries(String regcode, String ayear, String amonth) throws Exception {
		Connection conn = null;
		Collection salaries = new ArrayList();
		Vector employees = new Vector();
		String mstart = ayear + "/" + (amonth.length()>1?amonth:"0"+amonth) + "/1";
		String mend = ayear + "/" + (amonth.length()>1?amonth:"0"+amonth) + "/31";
		try {
			conn = this.dataSource.getConnection();
			Statement stmt = conn.createStatement();

			ResultSet rs = stmt
					.executeQuery("SELECT employeeno, name FROM Employees WHERE regcode='" + regcode + "' AND ayear='" + ayear
							+ "' AND employeeno in (SELECT employeeno FROM Salarys WHERE regcode='" + regcode + "' AND ayear='"
							+ ayear + "' AND amonth='" + amonth + "') ORDER BY employeeno");
			while (rs.next()) {
				String[] employee = { rs.getString(1), rs.getString(2) };
				employees.add(employee);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			close(conn);
		}

		String[] employee = null;
		for (int i = 0; i < employees.size(); i++) {
			employee = (String[]) employees.elementAt(i);
			SalaryBean salary = this.retriveSalary(regcode, employee[0], ayear, amonth, true);
			salary.setName(employee[1]);
			salaries.add(salary);
		}
		return (SalaryBean[]) salaries.toArray(new SalaryBean[0]);
	}

	public SalaryBean getYearlySalary(String regcode, String employeeno, String year) {
		Connection conn = null;
		ResultSet rs = null, rs0 = null, rs1 = null;
		Vector itemp = new Vector();
		Vector itemm = new Vector();
		SalaryBean salary = new SalaryBean();
		try {
			salary.setRegcode(regcode);
			salary.setEmployeeno(employeeno);
			// salary.setYearmonth(yearmonth);

			conn = this.dataSource.getConnection();
			Statement stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM Employees WHERE regcode='" + regcode + "' AND employeeno='" + employeeno
					+ "' AND ayear='" + year + "'");
			if (rs.next()) {
				salary.setGovinsurance(rs.getString("govinsurance") != null ? rs.getString("govinsurance") : "");
				salary.setName(rs.getString("name") != null ? rs.getString("name") : "");
			}
			rs = stmt
					.executeQuery("SELECT sum(basesalary), sum(oversalary), sum(btotal), sum(ptotal), sum(mtotal), sum(total), sum(tax) FROM Salarys WHERE regcode='"
							+ regcode + "' AND employeeno='" + employeeno + "' AND ayear= '" + year + "'");
			if (rs.next()) { // 已有薪資資料
				salary.setBasesalary(rs.getString(1) != null ? rs.getString(1) : "");
				salary.setOversalary(rs.getString(2) != null ? rs.getString(2) : "");
				salary.setBtotal(rs.getString(3) != null ? rs.getString(3) : "");
				salary.setPtotal(rs.getString(4) != null ? rs.getString(4) : "");
				salary.setMtotal(rs.getString(5) != null ? rs.getString(5) : "");
				salary.setTotal(rs.getString(6) != null ? rs.getString(6) : "");
				salary.setTax(rs.getString(7) != null ? rs.getString(7) : "");

				rs0 = stmt.executeQuery("SELECT a.seqno, b.name FROM Enableps AS a, PItems AS b "
						+ "WHERE a.seqno=b.seqno AND a.regcode='" + regcode + "' ORDER BY a.seqno");
				PreparedStatement pstmt = conn.prepareStatement("SELECT sum(amount) FROM SalaryItems WHERE regcode='" + regcode
						+ "'" + " AND employeeno='" + employeeno + "' AND ayear = '" + year + "' AND itemmark=? AND seqno=?");
				pstmt.setString(1, "P");
				while (rs0.next()) {
					pstmt.setString(2, rs0.getString(1));
					rs1 = pstmt.executeQuery();
					String[] item = { rs0.getString(1), rs0.getString(2),
							rs1.next() && rs1.getString(1) != null ? rs1.getInt(1) + "" : "" };
					itemp.addElement(item);
				}
				salary.setItemp(itemp);

				rs0 = stmt.executeQuery("SELECT a.seqno, b.name FROM Enablems AS a, MItems AS b "
						+ "WHERE a.seqno=b.seqno AND a.regcode='" + regcode + "' ORDER BY a.seqno");
				pstmt.setString(1, "M");
				while (rs0.next()) {
					pstmt.setString(2, rs0.getString(1));
					rs1 = pstmt.executeQuery();
					String[] item = { rs0.getString(1), rs0.getString(2),
							rs1.next() && rs1.getString(1) != null ? rs1.getInt(1) + "" : "" };
					itemm.addElement(item);
				}
				salary.setItemm(itemm);
			}

		} catch (Exception e) {
			System.out.println("error in regcode " + regcode);
			e.printStackTrace();
		} finally {
			close(rs);
			close(rs0);
			close(rs1);
			close(conn);
		}
		return salary;
	}

	public SalaryBean[] getAllYearlySalaries(String regcode, String year) throws Exception {
		Connection conn = null;
		Collection salaries = new ArrayList();
		Vector employees = new Vector();
		String mstart = year + "/01/01";
		String mend = year + "/12/31";
		try {
			conn = this.dataSource.getConnection();
			Statement stmt = conn.createStatement();
			/*ResultSet rs = stmt
					.executeQuery("SELECT employeeno, name FROM employee WHERE CONVERT(varchar(12) , onboarddate, 111 )<='"
							+ mend + "' " + " AND (resigndate is null OR CONVERT(varchar(12) , resigndate, 111 )>='" + mstart + "') "
							+ " AND regcode='" + regcode + "' AND ayear='" + year + "' ORDER BY employeeno"); */
			ResultSet rs = stmt
			.executeQuery("SELECT employeeno, name FROM Employees WHERE regcode='" + regcode + "' AND ayear='" + year + "' ORDER BY employeeno");
			while (rs.next()) {
				String[] employee = { rs.getString(1), rs.getString(2) };
				employees.add(employee);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			close(conn);
		}

		String[] employee = null;
		for (int i = 0; i < employees.size(); i++) {
			employee = (String[]) employees.elementAt(i);
			SalaryBean salary = this.getYearlySalary(regcode, employee[0], year);
			salary.setName(employee[1]);
			salaries.add(salary);
		}
		return (SalaryBean[]) salaries.toArray(new SalaryBean[0]);
	}

	public void remove(String regcode, String employeeno, String ayear, String amonth) throws Exception {
		Connection conn = null;
		try {
			conn = this.dataSource.getConnection();
			Statement stmt = conn.createStatement();

			stmt.executeUpdate("DELETE FROM Salarys WHERE regcode='" + regcode + "' AND employeeno='" + employeeno
					+ "' AND ayear='" + ayear + "' AND amonth='" + amonth + "'");
			stmt.executeUpdate("DELETE FROM SalaryItems WHERE regcode='" + regcode + "' AND employeeno='" + employeeno
					+ "' AND ayear='" + ayear + "' AND amonth='" + amonth + "'");

		} catch (Exception e) {
			throw e;
		} finally {
			close(conn);
		}
	}

	public void copy(String regcode, String ayear, String amonth) throws Exception {
		Connection conn = null;
		try {
			conn = this.dataSource.getConnection();
			Statement stmt = conn.createStatement();
			String mstart = ayear + "/" + (amonth.length()>1?amonth:"0"+amonth)+ "/01";
			String mend = ayear + "/" + (amonth.length()>1?amonth:"0"+amonth) + "/31";

			String lyear = "";
			String lmonth = "";
			if (amonth.equals("1")) {
				lyear = (Integer.parseInt(ayear) - 1) + "";
				lmonth = "12";
			} else {
				lmonth = (Integer.parseInt(amonth) - 1) + "";
				lyear = ayear;
			}

			stmt.executeUpdate("DELETE FROM Salarys WHERE regcode='" + regcode + "' AND ayear='" + ayear + "' AND amonth='"
					+ amonth + "'");
			stmt.executeUpdate("DELETE FROM SalaryItems WHERE regcode='" + regcode + "' AND ayear='" + ayear
					+ "' AND amonth='" + amonth + "'");
			PreparedStatement updStmt = conn
					.prepareStatement("INSERT INTO Salarys(regcode, employeeno, ayear, amonth, basesalary, baserate, overtimerate, overtime, oversalary, " + 
									"workinghr, btotal, ptotal, mtotal, total, tax, healthinsurance, laborinsurance, healthinsurancefee, laborinsurancefee, " + 
									"laborretirefee, retirefee) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			PreparedStatement updItem = conn
					.prepareStatement("INSERT INTO SalaryItems(regcode, employeeno, ayear, amonth, seqno, itemmark, amount) VALUES(?, ?, ?, ?, ?, ?, ?)");
			PreparedStatement selStmt = conn
					.prepareStatement("SELECT regcode, employeeno, ayear, amonth, basesalary, baserate, overtimerate, overtime, oversalary, workinghr, btotal, ptotal, mtotal, total, tax, healthinsurance, laborinsurance, healthinsurancefee, laborinsurancefee, laborretirefee, retirefee FROM Salarys WHERE regcode='"
							+ regcode + "' AND ayear='" + lyear + "' AND amonth='" + lmonth + "' AND employeeno=?");
			PreparedStatement selItem = conn
					.prepareStatement("SELECT regcode, employeeno, ayear, amonth, seqno, itemmark, amount FROM SalaryItems WHERE regcode='"
							+ regcode + "' AND ayear='" + lyear + "' AND amonth='" + lmonth + "' AND employeeno=?");

			ResultSet rs = stmt
					.executeQuery("SELECT employeeno, name FROM Employees WHERE CONVERT(varchar(12) , onboarddate, 111 )<='"
							+ mend + "' " + " AND (resigndate is null OR CONVERT(varchar(12) , resigndate, 111 )>='" + mstart + "') "
							+ " AND regcode='" + regcode + "' AND ayear='" + ayear + "' ORDER BY employeeno");
			ResultSet rs1 = null;
			while (rs.next()) {
				selStmt.setString(1, rs.getString(1));
				rs1 = selStmt.executeQuery();
				if (rs1.next()) {
					for (int i = 1; i <= 21; i++) {
						if (rs1.getString(i) != null && !rs1.getString(i).equals(""))
							updStmt.setString(i, rs1.getString(i));
						else
							updStmt.setNull(i, Types.CHAR);
					}
					updStmt.setString(3, ayear);
					updStmt.setString(4, amonth);
					updStmt.executeUpdate();
				}
				selItem.setString(1, rs.getString(1));
				rs1 = selItem.executeQuery();
				while (rs1.next()) {
					for (int i = 1; i <= 7; i++) {
						if (rs1.getString(i) != null && !rs1.getString(i).equals(""))
							updItem.setString(i, rs1.getString(i));
						else
							updItem.setNull(i, Types.CHAR);
					}
					updItem.setString(3, ayear);
					updItem.setString(4, amonth);
					updItem.executeUpdate();
				}
			}
		} catch (Exception e) {
			throw e;
		} finally {
			close(conn);
		}
	}

	public void copy(String regcode, String fromYear, String fromMonth, String toYear, String toMonth) throws Exception {
		Connection conn = null;
		try {
			conn = this.dataSource.getConnection();
			Statement stmt = conn.createStatement();
			String mstart = toYear + "/" + (toMonth.length()>1?toMonth:"0"+toMonth) + "/01";
			String mend = toYear + "/" + (toMonth.length()>1?toMonth:"0"+toMonth) + "/31";

			stmt.executeUpdate("DELETE FROM Salarys WHERE regcode='" + regcode + "' AND ayear='" + toYear + "' AND amonth='"
					+ toMonth + "'");
			stmt.executeUpdate("DELETE FROM SalaryItems WHERE regcode='" + regcode + "' AND ayear='" + toYear
					+ "' AND amonth='" + toMonth + "'");
			PreparedStatement updStmt = conn
					.prepareStatement("INSERT INTO Salarys(regcode, employeeno, ayear, amonth, basesalary, baserate, overtimerate, overtime, oversalary, workinghr, btotal, ptotal, mtotal, total, tax, healthinsurance, laborinsurance, healthinsurancefee, laborinsurancefee, laborretirefee, retirefee) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			PreparedStatement updItem = conn
					.prepareStatement("INSERT INTO SalaryItems(regcode, employeeno, ayear, amonth, seqno, itemmark, amount) VALUES(?, ?, ?, ?, ?, ?, ?)");
			PreparedStatement selStmt = conn
					.prepareStatement("SELECT regcode, employeeno, ayear, amonth, basesalary, baserate, overtimerate, overtime, oversalary, workinghr, btotal, ptotal, mtotal, total, tax, healthinsurance, laborinsurance, healthinsurancefee, laborinsurancefee, laborretirefee, retirefee FROM Salarys WHERE regcode='"
							+ regcode + "' AND ayear='" + fromYear + "' AND amonth='" + fromMonth + "' AND employeeno=?");
			PreparedStatement selItem = conn
					.prepareStatement("SELECT regcode, employeeno, ayear, amonth, seqno, itemmark, amount FROM SalaryItems WHERE regcode='"
							+ regcode + "' AND ayear='" + fromYear + "' AND amonth='" + fromMonth + "' AND employeeno=?");

			ResultSet rs = stmt
					.executeQuery("SELECT employeeno, name FROM Employees WHERE CONVERT(varchar(12) , onboarddate, 111 )<='"
							+ mend + "' " + " AND (resigndate is null OR CONVERT(varchar(12) , resigndate, 111 )>='" + mstart + "') "
							+ " AND regcode='" + regcode + "' AND ayear='" + toYear + "' ORDER BY employeeno");
			ResultSet rs1 = null;
			while (rs.next()) {
				selStmt.setString(1, rs.getString(1));
				rs1 = selStmt.executeQuery();
				if (rs1.next()) {
					for (int i = 1; i <= 21; i++) {
						if (rs1.getString(i) != null && !rs1.getString(i).equals(""))
							updStmt.setString(i, rs1.getString(i));
						else
							updStmt.setNull(i, Types.CHAR);
					}
					updStmt.setString(3, toYear);
					updStmt.setString(4, toMonth);
					updStmt.executeUpdate();
				}
				selItem.setString(1, rs.getString(1));
				rs1 = selItem.executeQuery();
				while (rs1.next()) {
					for (int i = 1; i <= 7; i++) {
						if (rs1.getString(i) != null && !rs1.getString(i).equals(""))
							updItem.setString(i, rs1.getString(i));
						else
							updItem.setNull(i, Types.CHAR);
					}
					updItem.setString(3, toYear);
					updItem.setString(4, toMonth);
					updItem.executeUpdate();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			close(conn);
		}
	}

	public List findSalaryDigest(String regcode, String year, String month) {
		Connection conn = null;
		List salaryDigest = new ArrayList();
		try {
			conn = this.dataSource.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt
					.executeQuery("SELECT b.employeeno, b.name, b.accountno, a.total FROM Salarys AS a, Employees AS b "
							+ " WHERE a.regcode=b.regcode AND a.employeeno=b.employeeno AND a.ayear=b.ayear AND a.regcode='"
							+ regcode + "' AND a.ayear='" + year + "' AND a.amonth='" + month + "' ORDER BY b.employeeno");
			while (rs.next()) {
				String[] item = { rs.getString(2), rs.getString(3), rs.getString(4) };
				salaryDigest.add(item);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(conn);
		}
		return salaryDigest;
	}

	public Vector getYearlyReport(String regcode, String year) throws Exception {
		Vector datamart = new Vector();
		ResultSet rs, rs0;
		String mstart = year + "/01/1";
		String mend = year + "/12/31";
		Connection conn = null;
		try {
			conn = this.dataSource.getConnection();
			Statement stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM Employees WHERE CONVERT(varchar(12) , onboarddate, 111 )<='" + mend + "' "
					+ " AND (resigndate is null OR CONVERT(varchar(12) , resigndate, 111 )>='" + mstart + "') "
					+ " AND regcode='" + regcode + "' AND ayear='" + year + "' ORDER BY employeeno");
			PreparedStatement pstmt = conn
					.prepareStatement("SELECT total, tax FROM Salarys WHERE regcode=? AND employeeno=? AND ayear='" + year
							+ "' AND amonth=? ");
			pstmt.setString(1, regcode);
			int[] total = new int[43];
			for (int i = 0; i < total.length; i++)
				total[i] = 0;
			int aytotal = 0;
			int aytax = 0;
			while (rs.next()) {
				int mtotal = 0;
				int mtax = 0;
				int ytotal = 0;
				int ytax = 0;
				String[] data = new String[43];
				data[0] = rs.getString("employeeno");
				data[1] = rs.getString("isnative") != null && rs.getString("isnative").equals("N") ? rs.getString("passport")
						: rs.getString("unicode");
				data[2] = rs.getString("name");
				data[3] = rs.getString("address");

				pstmt.setString(2, rs.getString("employeeno"));
				for (int i = 1; i <= 12; i++) {
					pstmt.setString(3, i + "");
					rs0 = pstmt.executeQuery();
					if (rs0.next()) {
						mtotal = Integer.parseInt(rs0.getString("total") != null && !rs0.getString("total").equals("") ? rs0
								.getString("total") : "0");
						mtax = Integer.parseInt(rs0.getString("tax") != null && !rs0.getString("tax").equals("") ? rs0
								.getString("tax") : "0");
						data[3 + i] = "" + (mtotal + mtax);
						data[16 + i] = "" + mtax;
						data[29 + i] = "" + mtotal;
						ytotal += mtotal;
						ytax += mtax;
						total[3 + i] += (mtotal + mtax);
						total[16 + i] += mtax;
						total[29 + i] += mtotal;
						aytotal += mtotal;
						aytax += mtax;
					}
				}
				data[16] = "" + (ytotal + ytax);
				data[29] = "" + ytax;
				data[42] = "" + ytotal;

				datamart.add(data);
			}
			total[16] = (aytotal + aytax);
			total[29] = aytax;
			total[42] = aytotal;
			String stotal[] = new String[43];
			for (int i = 0; i < stotal.length; i++)
				stotal[i] = total[i] + "";

			datamart.add(stotal);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.close(conn);
		}
		return datamart;
	}
}
