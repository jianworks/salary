package com.csjian.model.dao;

import javax.sql.*;
import javax.naming.*;

import com.csjian.model.bean.BsalaryBean;
import com.csjian.model.dao.BsalaryDao;

import java.sql.*;
import java.util.*;

public class BsalaryDao extends TemplateDao {
	public BsalaryBean retriveBsalary(String regcode, String employeeno,String ayear) throws Exception {
		ResultSet rs = null, rs0 = null;
		Vector itemp = new Vector();
		Vector itemm = new Vector();
		BsalaryBean bsalary = new BsalaryBean();
		Connection conn = null;
		try {
			bsalary.setRegcode(regcode);
			bsalary.setEmployeeno(employeeno);

			conn = this.dataSource.getConnection();
			Statement stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM Employees WHERE regcode='"
					+ regcode + "' AND employeeno='" + employeeno
					+ "' AND ayear='" + ayear + "'");
			if (rs.next()) {
				bsalary.setGovinsurance(rs.getString("govinsurance") != null ? rs
						.getString("govinsurance") : "");
				bsalary.setName(rs.getString("name") != null ? rs
						.getString("name") : "");
			}
			rs = stmt.executeQuery("SELECT * FROM BSalarys WHERE regcode='"
					+ regcode + "' AND employeeno='" + employeeno
					+ "' AND ayear='" + ayear + "'");
			if (rs.next()) {
				bsalary.setBasesalary(rs.getString("basesalary") != null ? rs
						.getString("basesalary") : "");
				bsalary.setBaserate(rs.getString("baserate") != null ? rs
						.getString("baserate") : "");
				bsalary.setOvertimerate(rs.getString("overtimerate") != null ? rs
						.getString("overtimerate") : "");
				bsalary.setMark(rs.getString("mark") != null ? rs
						.getString("mark") : "");
			}

			PreparedStatement pstmt = conn.prepareStatement("SELECT amount FROM BSalaryItems WHERE regcode=? AND employeeno=? AND itemmark=? AND seqno=? AND ayear=?");
			rs = stmt.executeQuery("SELECT a.seqno, b.name FROM Enableps AS a, PItems AS b WHERE a.seqno=b.seqno AND a.regcode='"
							+ regcode + "'");
			pstmt.setString(1, regcode);
			pstmt.setString(2, employeeno);
			pstmt.setString(3, "P");
			pstmt.setString(5, ayear);
			while (rs.next()) {
				pstmt.setString(4, rs.getString(1));
				rs0 = pstmt.executeQuery();
				String[] item = {
						rs.getString(1),
						rs.getString(2),
						(rs0.next() && rs0.getString(1) != null) ? rs0
								.getString(1) : "" };
				itemp.addElement(item);
			}
			bsalary.setItemp(itemp);

			rs = stmt
					.executeQuery("SELECT a.seqno, b.name FROM Enablems AS a, MItems AS b WHERE a.seqno=b.seqno AND a.regcode='"
							+ regcode + "'");
			pstmt.setString(1, regcode);
			pstmt.setString(2, employeeno);
			pstmt.setString(3, "M");
			while (rs.next()) {
				pstmt.setString(4, rs.getString(1));
				rs0 = pstmt.executeQuery();
				String[] item = {
						rs.getString(1),
						rs.getString(2),
						(rs0.next() && rs0.getString(1) != null) ? rs0
								.getString(1) : "" };
				itemm.addElement(item);
			}
			bsalary.setItemm(itemm);

			return bsalary;
		} catch (Exception e) {
			throw e;
		} finally {
			close(rs);
			close(rs0);
			close(conn);
		}
	}

	public void update(BsalaryBean bsalary) throws Exception {
		Connection conn = null;
		try {
			conn = this.dataSource.getConnection();
			Statement stmt = conn.createStatement();
			PreparedStatement updStmt = null;

			stmt.executeUpdate("DELETE FROM BSalarys WHERE regcode='"
					+ bsalary.getRegcode() + "' AND employeeno='"
					+ bsalary.getEmployeeno() + "' AND ayear='"
					+ bsalary.getAyear() + "'");
			updStmt = conn.prepareStatement("INSERT INTO BSalarys(regcode, employeeno, basesalary, baserate, overtimerate, mark, ayear) VALUES(?, ?, ?, ?, ?, ?, ?)");
			updStmt.setString(1, bsalary.getRegcode());
			updStmt.setString(2, bsalary.getEmployeeno());
			if (bsalary.getBasesalary() != null
					&& !bsalary.getBasesalary().equals("")) {
				updStmt.setString(3, bsalary.getBasesalary());
			} else {
				updStmt.setNull(3, Types.INTEGER);
			}
			if (bsalary.getBaserate() != null
					&& !bsalary.getBaserate().equals("")) {
				updStmt.setString(4, bsalary.getBaserate());
			} else {
				updStmt.setNull(4, Types.INTEGER);
			}
			if (bsalary.getOvertimerate() != null
					&& !bsalary.getOvertimerate().equals("")) {
				updStmt.setString(5, bsalary.getOvertimerate());
			} else {
				updStmt.setNull(5, Types.INTEGER);
			}
			if (bsalary.getMark() != null && !bsalary.getMark().equals("")) {
				updStmt.setString(6, bsalary.getMark());
			} else {
				updStmt.setNull(6, Types.CHAR);
			}
			updStmt.setString(7, bsalary.getAyear());
			updStmt.executeUpdate();

			stmt.executeUpdate("DELETE FROM BSalaryItems WHERE regcode='"
					+ bsalary.getRegcode() + "' AND employeeno='"
					+ bsalary.getEmployeeno() + "' AND ayear='"
					+ bsalary.getAyear() + "'");
			updStmt = conn
					.prepareStatement("INSERT INTO BSalaryItems(regcode, employeeno, seqno, itemmark, amount, ayear) VALUES(?, ?, ?, ?, ?, ?)");
			updStmt.setString(1, bsalary.getRegcode());
			updStmt.setString(2, bsalary.getEmployeeno());
			updStmt.setString(6, bsalary.getAyear());

			Vector itemp = bsalary.getItemp();
			updStmt.setString(4, "P");
			for (int i = 0; i < itemp.size(); i++) {
				String[] item = (String[]) itemp.elementAt(i);
				if (item[2] != null && !item[2].equals("")) {
					updStmt.setString(3, item[0]);
					updStmt.setString(5, item[2]);
					updStmt.executeUpdate();
				}
			}

			Vector itemm = bsalary.getItemm();
			updStmt.setString(4, "M");
			for (int i = 0; i < itemm.size(); i++) {
				String[] item = (String[]) itemm.elementAt(i);
				if (item[2] != null && !item[2].equals("")) {
					updStmt.setString(3, item[0]);
					updStmt.setString(5, item[2]);
					updStmt.executeUpdate();
				}
			}
		} catch (Exception e) {
			throw e;
		} finally {
			close(conn);
		}
	}

	public void remove(String regcode, String employeeno, String ayear)
			throws Exception {
		Connection conn = null;
		try {
			conn = this.dataSource.getConnection();
			Statement stmt = conn.createStatement();

			stmt.executeUpdate("DELETE FROM BSalarys WHERE regcode='" + regcode
					+ "' AND employeeno='" + employeeno + "' AND ayear='"
					+ ayear + "'");
			stmt.executeUpdate("DELETE FROM BSalaryItems WHERE regcode='"
					+ regcode + "' AND employeeno='" + employeeno
					+ "' AND ayear='" + ayear + "'");

		} catch (Exception e) {
			throw e;
		} finally {
			close(conn);
		}
	}
}
