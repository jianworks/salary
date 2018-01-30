package com.csjian.model.dao;

import jxl.Sheet;
import jxl.Workbook;
import jxl.write.*;

import java.io.File;
import java.io.OutputStream;
import java.io.PrintWriter;

import com.csjian.model.bean.EmployeeBean;

import java.sql.*;
import java.util.*;

import org.apache.commons.io.FileUtils;

public class EmployeeDao extends TemplateDao {

	private EmployeeBean makeEmployee(ResultSet rs) throws Exception {
		EmployeeBean employee = new EmployeeBean();
		employee.setEmployeeno(rs.getString("employeeno") != null ? rs
				.getString("employeeno") : "");
		employee.setRegcode(rs.getString("regcode") != null ? rs
				.getString("regcode") : "");
		employee.setAyear(rs.getString("ayear") != null ? rs.getString("ayear")
				: "");
		employee.setIsnative(rs.getString("isnative") != null ? rs
				.getString("isnative") : "Y");
		employee.setUnicode(rs.getString("unicode") != null ? rs
				.getString("unicode") : "");
		// employee.setPassport(rs.getString("passport")!=null?rs.getString("passport"):"");
		employee.setName(rs.getString("name") != null ? rs.getString("name")
				: "");
		employee.setAddress(rs.getString("address") != null ? rs
				.getString("address") : "");
		try {
			employee.setOnboarddate(rs.getString("onboarddate") != null ? rs
					.getString("onboarddate").substring(0, 10) : "");
		} catch (Exception e) {
		}
		employee.setAccountno(rs.getString("accountno") != null ? rs
				.getString("accountno") : "");
		employee.setIsresign(rs.getString("isresign") != null ? rs
				.getString("isresign") : "N");
		try {
			employee.setResigndate(rs.getString("resigndate") != null ? rs
					.getString("resigndate").substring(0, 10) : "");
		} catch (Exception e) {
		}
		employee.setRetirefee(rs.getString("retirefee") != null ? rs
				.getString("retirefee") : "");
		employee.setTitle(rs.getString("title") != null ? rs.getString("title")
				: "");
		employee.setGovinsurance(rs.getString("govinsurance") != null ? rs
				.getString("govinsurance") : "");
		try {
			employee.setBirthday(rs.getString("birthday") != null ? rs
					.getString("birthday").substring(0, 10) : "");
		} catch (Exception e) {
		}
		employee.setLaborRetireFee(rs.getString("laborretirefee") != null ? rs
				.getString("laborretirefee") : "");
		employee.setLaborInsurance(rs.getString("laborinsurance") != null ? rs
				.getString("laborinsurance") : "");
		employee.setHealthInsurance(rs.getString("healthInsurance") != null ? rs
				.getString("healthInsurance") : "");
		return employee;
	}

	public EmployeeBean retriveEmployee(String regcode, String employeeno,
			String ayear) throws Exception {
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = this.dataSource.getConnection();
			PreparedStatement getStmt = conn
					.prepareStatement("SELECT * FROM Employees WHERE regcode=? AND employeeno = ? AND ayear=?");
			synchronized (getStmt) {
				getStmt.clearParameters();
				getStmt.setString(1, regcode);
				getStmt.setString(2, employeeno);
				getStmt.setString(3, ayear);
				rs = getStmt.executeQuery();
				if (rs.next()) {
					return makeEmployee(rs);
				} else {
					throw new Exception("Could not find employee#" + regcode
							+ " - " + employeeno);
				}
			}
		} catch (Exception e) {
			throw e;
		} finally {
			close(rs);
			close(conn);
		}
	}

	public List findEmployee(String regcode, String keyword, String resign,
			String year, int start, int end) throws Exception {
		Connection conn = null;
		ResultSet rs = null;
		List employees = new ArrayList();
		try {
			conn = this.dataSource.getConnection();
			String query = "";
			query = "SELECT * FROM Employees WHERE regcode = '" + regcode + "'";
			if (keyword != null && !keyword.equals("")) {
				query += " AND (unicode like '%" + keyword
						+ "%' or name like '%" + keyword
						+ "%' or passport like '%" + keyword + "%')";
			}
			if (year != null && !year.equals("")) {
				query += " AND ayear = '" + year + "' ";
			}
			if (resign != null && !resign.equals("")) {
				query += " AND isresign = '" + resign + "' ";
			}
			query += "ORDER BY isresign, employeeno";

			Statement stmt = conn
					.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_READ_ONLY);
			rs = stmt.executeQuery(query);

			if (end > -1) {
				if (rs.next()) {
					rs.last();
					end = end < rs.getRow() ? end : rs.getRow();
					for (int i = start; i <= end; i++) {
						rs.absolute(i);
						employees.add(makeEmployee(rs));
					}
				}
			} else {
				while (rs.next()) {
					employees.add(makeEmployee(rs));
				}
			}

			return employees;
		} catch (Exception e) {
			throw e;
		} finally {
			close(rs);
			close(conn);
		}
	}

	public int importFromWintonTxt(String regcode, String year, File txtFile)
			throws Exception {
		int total = 0;
		try {
			List<String> lines = FileUtils.readLines(txtFile);
			System.out.println("line Count" + lines.size());
			System.out.println("regcode" + regcode);
			int startNo = 0;
			boolean gotStartNo = false;
			do {
				gotStartNo = false;
				for (int i = startNo; i < lines.size(); i++) {
					if (lines.get(i).indexOf("戶籍地址") > 0) {
						startNo = i + 1;
						gotStartNo = true;
						break;
					}
				}
				if (gotStartNo) {
					System.out.println("startNO" + startNo);
					EmployeeBean employee = null;
					String[] dataItem = null;
					for (int i = startNo; i < lines.size(); i += 2) {
						employee = new EmployeeBean();
						if (lines.get(i).length() > 50) {
							employee.setRegcode(regcode);
							employee.setAyear(year);
							dataItem = lines.get(i).trim().split("\\s+");
							employee.setEmployeeno(dataItem[0]);
							if (!employee.getEmployeeno().equals("")) {
								employee.setUnicode(dataItem[1]);
								employee.setName(dataItem[2]);
								employee.setAddress(lines.get(i + 1).trim());
								System.out.println("Employee regcode="
										+ employee.getRegcode()
										+ ", employeeno="
										+ employee.getEmployeeno());

								this.insert(employee);
								total++;
							}
						} else {
							startNo = i++;
							break;
						}
					}
				} else {
					return total;
				}
			} while (startNo < lines.size() - 1);

		} catch (Exception e) {
			throw e;
		}
		return total;
	}

	public int countEmployee(String regcode, String keyword, String resign,
			String year) throws Exception {
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = this.dataSource.getConnection();
			String query = "";
			query = "SELECT count(*) FROM Employees WHERE regcode = '"
					+ regcode + "'";
			if (keyword != null && !keyword.equals("")) {
				query += " AND (unicode like '%" + keyword
						+ "%' or name like '%" + keyword
						+ "%' or passport like '%" + keyword + "%')";
			}
			if (year != null && !year.equals("")) {
				query += " AND ayear = '" + year + "' ";
			}
			if (resign != null && !resign.equals("")) {
				query += " AND isresign = '" + resign + "' ";
			}
			Statement stmt = conn
					.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_READ_ONLY);
			rs = stmt.executeQuery(query);

			if (rs.next())
				return rs.getInt(1);
			else
				return 0;
		} catch (Exception e) {
			throw e;
		} finally {
			close(rs);
			close(conn);
		}
	}

	public void update(EmployeeBean employee) throws Exception {
		Connection conn = null;
		try {
			conn = this.dataSource.getConnection();
			PreparedStatement updStmt = conn
					.prepareStatement("UPDATE Employees SET isnative=?, unicode=?, name=?, address=?, onboarddate=?, accountno=?, isresign=?, resigndate=?, govinsurance=?, retirefee=?, title=?, birthday=?, laborinsurance=?, healthinsurance=?, laborretirefee=? WHERE regcode=? AND employeeno=? AND ayear=?");
			synchronized (updStmt) {
				updStmt.clearParameters();
				updStmt.setString(1, employee.getIsnative());
				if (employee.getUnicode() != null) {
					updStmt.setString(2, employee.getUnicode().toUpperCase());
				} else {
					updStmt.setNull(2, Types.CHAR);
				}
				updStmt.setString(3, employee.getName());
				updStmt.setString(4, employee.getAddress());
				if (employee.getOnboarddate() != null
						&& !employee.getOnboarddate().equals("")) {
					updStmt.setString(5, employee.getOnboarddate());
				} else {
					updStmt.setNull(5, Types.DATE);
				}
				updStmt.setString(6, employee.getAccountno());
				updStmt.setString(7, employee.getIsresign());
				if (employee.getResigndate() != null
						&& !employee.getResigndate().equals("")) {
					updStmt.setString(8, employee.getResigndate());
				} else {
					updStmt.setNull(8, Types.DATE);
				}
				if (employee.getGovinsurance() != null
						&& !employee.getGovinsurance().equals("")) {
					updStmt.setString(9, employee.getGovinsurance());
				} else {
					updStmt.setNull(9, Types.INTEGER);
				}
				if (employee.getRetirefee() != null
						&& !employee.getRetirefee().equals("")) {
					updStmt.setString(10, employee.getRetirefee());
				} else {
					updStmt.setNull(10, Types.INTEGER);
				}
				if (employee.getTitle() != null
						&& !employee.getTitle().equals("")) {
					updStmt.setString(11, employee.getTitle());
				} else {
					updStmt.setNull(11, Types.CHAR);
				}
				if (employee.getBirthday() != null
						&& !employee.getBirthday().equals("")) {
					updStmt.setString(12, employee.getBirthday());
				} else {
					updStmt.setNull(12, Types.DATE);
				}
				if (employee.getLaborInsurance() != null
						&& !employee.getLaborInsurance().equals("")) {
					updStmt.setString(13, employee.getLaborInsurance());
				} else {
					updStmt.setNull(13, Types.INTEGER);
				}
				if (employee.getHealthInsurance() != null
						&& !employee.getHealthInsurance().equals("")) {
					updStmt.setString(14, employee.getHealthInsurance());
				} else {
					updStmt.setNull(14, Types.INTEGER);
				}
				if (employee.getLaborRetireFee() != null
						&& !employee.getLaborRetireFee().equals("")) {
					updStmt.setString(15, employee.getLaborRetireFee());
				} else {
					updStmt.setNull(15, Types.INTEGER);
				}
				updStmt.setString(16, employee.getRegcode());
				updStmt.setString(17, employee.getEmployeeno());
				updStmt.setString(18, employee.getAyear());

				updStmt.executeUpdate();
			}
		} catch (Exception e) {
			throw e;
		} finally {
			close(conn);
		}
	}

	public int insert(EmployeeBean employee) throws Exception {
		Connection conn = null;
		int fetchs = 0;
		try {
			conn = this.dataSource.getConnection();
			ResultSet rs = null;
			PreparedStatement putStmt = conn
					.prepareStatement("SELECT * FROM Employees WHERE regcode=? AND employeeno = ? AND ayear=?");
			putStmt.setString(1, employee.getRegcode());
			putStmt.setString(2, employee.getEmployeeno());
			putStmt.setString(3, employee.getAyear());
			rs = putStmt.executeQuery();
			if (rs.next()) {
				return (-1);
			}

			putStmt = conn
					.prepareStatement("INSERT INTO Employees(isnative, unicode, name, address, onboarddate, accountno, isresign, resigndate, govinsurance, retirefee, title, birthday, laborinsurance, healthinsurance, laborretirefee, regcode, employeeno, ayear) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			synchronized (putStmt) {
				putStmt.clearParameters();
				putStmt.setString(1, employee.getIsnative());
				if (employee.getUnicode() != null) {
					putStmt.setString(2, employee.getUnicode().toUpperCase());
				} else {
					putStmt.setNull(2, Types.CHAR);
				}
				putStmt.setString(3, employee.getName());
				putStmt.setString(4, employee.getAddress());
				if (employee.getOnboarddate() != null
						&& !employee.getOnboarddate().equals("")) {
					putStmt.setString(5, employee.getOnboarddate());
				} else {
					putStmt.setNull(5, Types.DATE);
				}
				putStmt.setString(6, employee.getAccountno());
				putStmt.setString(7, employee.getIsresign());
				if (employee.getResigndate() != null
						&& !employee.getResigndate().equals("")) {
					putStmt.setString(8, employee.getResigndate());
				} else {
					putStmt.setNull(8, Types.DATE);
				}
				if (employee.getGovinsurance() != null
						&& !employee.getGovinsurance().equals("")) {
					putStmt.setString(9, employee.getGovinsurance());
				} else {
					putStmt.setNull(9, Types.INTEGER);
				}
				if (employee.getRetirefee() != null
						&& !employee.getRetirefee().equals("")) {
					putStmt.setString(10, employee.getRetirefee());
				} else {
					putStmt.setNull(10, Types.INTEGER);
				}
				if (employee.getTitle() != null
						&& !employee.getTitle().equals("")) {
					putStmt.setString(11, employee.getTitle());
				} else {
					putStmt.setNull(11, Types.CHAR);
				}
				if (employee.getBirthday() != null
						&& !employee.getBirthday().equals("")) {
					putStmt.setString(12, employee.getBirthday());
				} else {
					putStmt.setNull(12, Types.DATE);
				}
				if (employee.getLaborInsurance() != null
						&& !employee.getLaborInsurance().equals("")) {
					putStmt.setString(13, employee.getLaborInsurance());
				} else {
					putStmt.setNull(13, Types.INTEGER);
				}
				if (employee.getHealthInsurance() != null
						&& !employee.getHealthInsurance().equals("")) {
					putStmt.setString(14, employee.getHealthInsurance());
				} else {
					putStmt.setNull(14, Types.INTEGER);
				}
				if (employee.getLaborRetireFee() != null
						&& !employee.getLaborRetireFee().equals("")) {
					putStmt.setString(15, employee.getLaborRetireFee());
				} else {
					putStmt.setNull(15, Types.INTEGER);
				}
				putStmt.setString(16, employee.getRegcode());
				putStmt.setString(17, employee.getEmployeeno());
				putStmt.setString(18, employee.getAyear());

				fetchs = putStmt.executeUpdate();
			}
		} catch (Exception e) {
			throw e;
		} finally {
			close(conn);
		}
		return fetchs;
	}

	public boolean hasSalaryData(String regcode, String employeeno, String ayear)
			throws Exception {
		boolean exist = true;
		Connection conn = null;
		try {
			conn = this.dataSource.getConnection();
			PreparedStatement countStmt = conn
					.prepareStatement("SELECT total FROM Salarys WHERE regcode=? AND employeeno=? AND ayear=?");

			synchronized (countStmt) {
				countStmt.clearParameters();
				countStmt.setString(1, regcode);
				countStmt.setString(2, employeeno);
				countStmt.setString(3, ayear);
				ResultSet rs = countStmt.executeQuery();
				if (!rs.next())
					exist = false;
			}
		} catch (Exception e) {
			throw e;
		} finally {
			close(conn);
		}
		return exist;
	}

	public void remove(String regcode, String employeeno, String ayear)
			throws Exception {
		Connection conn = null;
		try {
			conn = this.dataSource.getConnection();
			PreparedStatement remStmt = conn
					.prepareStatement("DELETE FROM Employees WHERE regcode=? AND employeeno=? AND ayear=?");
			PreparedStatement remSalary = conn
					.prepareStatement("DELETE FROM Salarys WHERE regcode=? AND employeeno=? AND ayear=?");
			PreparedStatement remSalaryitem = conn
					.prepareStatement("DELETE FROM SalaryItems WHERE regcode=? AND employeeno=? AND ayear=?");
			PreparedStatement remBsalary = conn
					.prepareStatement("DELETE FROM BSalarys WHERE regcode=? AND employeeno=? AND ayear=?");
			PreparedStatement remBsalaryitem = conn
					.prepareStatement("DELETE FROM BSalaryItems WHERE regcode=? AND employeeno=? AND ayear=?");
			PreparedStatement remDependant = conn
					.prepareStatement("DELETE FROM Dependants WHERE regcode=? AND employeeno=? AND ayear=?");

			synchronized (remStmt) {
				remStmt.clearParameters();
				remStmt.setString(1, regcode);
				remStmt.setString(2, employeeno);
				remStmt.setString(3, ayear);
				remStmt.executeUpdate();
				remSalary.setString(1, regcode);
				remSalary.setString(2, employeeno);
				remSalary.setString(3, ayear);
				remSalary.executeUpdate();
				remSalaryitem.setString(1, regcode);
				remSalaryitem.setString(2, employeeno);
				remSalaryitem.setString(3, ayear);
				remSalaryitem.executeUpdate();
				remBsalary.setString(1, regcode);
				remBsalary.setString(2, employeeno);
				remBsalary.setString(3, ayear);
				remBsalary.executeUpdate();
				remBsalaryitem.setString(1, regcode);
				remBsalaryitem.setString(2, employeeno);
				remBsalaryitem.setString(3, ayear);
				remBsalaryitem.executeUpdate();
				remDependant.setString(1, regcode);
				remDependant.setString(2, employeeno);
				remDependant.setString(3, ayear);
				remDependant.executeUpdate();
			}
		} catch (Exception e) {
			System.out.println(e.toString());
			throw e;
		} finally {
			close(conn);
		}
	}

	public void copyLastYear(String regcode, String ayear, boolean overwrite)
			throws Exception {
		Connection conn = null;
		try {
			conn = this.dataSource.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = null;
			String lyear = (Integer.parseInt(ayear) - 1) + "";

			if (overwrite) {
				stmt.executeUpdate("DELETE FROM Employees WHERE regcode='"
						+ regcode + "' AND ayear='" + ayear + "'");
				stmt.executeUpdate("DELETE FROM Salarys WHERE regcode='"
						+ regcode + "' AND ayear='" + ayear + "'");
				stmt.executeUpdate("DELETE FROM SalaryItems WHERE regcode='"
						+ regcode + "' AND ayear='" + ayear + "'");
				stmt.executeUpdate("DELETE FROM BSalarys WHERE regcode='"
						+ regcode + "' AND ayear='" + ayear + "'");
				stmt.executeUpdate("DELETE FROM BSalaryItems WHERE regcode='"
						+ regcode + "' AND ayear='" + ayear + "'");
			}

			PreparedStatement putStmt = conn
					.prepareStatement("INSERT INTO Employees(isnative, unicode, passport, name, address, onboarddate, accountno, isresign, resigndate, govinsurance, retirefee, title, regcode, employeeno, birthday, laborinsurance, healthinsurance, laborretirefee, ayear) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			PreparedStatement updStmt = conn
					.prepareStatement("INSERT INTO BSalarys(regcode, employeeno, basesalary, baserate, overtimerate, mark, ayear) VALUES(?, ?, ?, ?, ?, ?, ?)");
			PreparedStatement updItem = conn
					.prepareStatement("INSERT INTO BSalaryItems(regcode, employeeno, seqno, itemmark, amount, ayear) VALUES(?, ?, ?, ?, ?, ?)");
			PreparedStatement dependantStmt = conn
					.prepareStatement("INSERT INTO Dependants(regcode, employeeno, unicode, relation, name, birthday, ayear) values(?, ?, ?, ?, ?, ?, ?)");

			stmt.executeUpdate("INSERT INTO Employees(isnative, unicode, passport, name, "
					+ "address, onboarddate, accountno, isresign, resigndate, govinsurance, retirefee, "
					+ "title, regcode, employeeno, birthday, laborinsurance, healthinsurance, "
					+ "laborretirefee, ayear) "
					+ "select isnative, unicode, passport, name, "
					+ "address, onboarddate, accountno, isresign, resigndate, govinsurance, retirefee, "
					+ "title, regcode, employeeno, birthday, laborinsurance, healthinsurance, "
					+ "laborretirefee, '"
					+ ayear
					+ "' as ayear from Employees "
					+ "where regcode='"
					+ regcode
					+ "' and ayear='"
					+ lyear
					+ "' AND isresign='N' "
					+ "and employeeno not in (select employeeno from Employees where regcode='"
					+ regcode + "' and ayear='" + ayear + "')");

			stmt.executeUpdate("INSERT INTO BSalarys(regcode, employeeno, basesalary, baserate, "
					+ "overtimerate, mark, ayear) "
					+ "select regcode, employeeno, basesalary, baserate, "
					+ "overtimerate, mark, '"
					+ ayear
					+ "' as ayear from BSalarys "
					+ "where regcode='"
					+ regcode
					+ "' and ayear='"
					+ lyear
					+ "' "
					+ "and employeeno not in "
					+ "(select employeeno from BSalarys where regcode='"
					+ regcode
					+ "' and ayear='"
					+ ayear
					+ "' "
					+ " UNION SELECT employeeno FROM employees WHERE regcode='"
					+ regcode
					+ "' and ayear='"
					+ lyear
					+ "' AND isresign='N' )");

			stmt.executeUpdate("INSERT INTO BSalaryItems(regcode, employeeno, seqno, itemmark, amount, ayear) "
					+ "select regcode, employeeno, seqno, itemmark, amount, '"
					+ ayear
					+ "' as ayear "
					+ "from BSalaryItems "
					+ "where regcode='"
					+ regcode
					+ "' and ayear='"
					+ lyear
					+ "' "
					+ "and employeeno not in (select employeeno from BSalaryItems where regcode='"
					+ regcode
					+ "' and ayear='"
					+ ayear
					+ "' "
					+ " UNION SELECT employeeno FROM employees WHERE regcode='"
					+ regcode
					+ "' and ayear='"
					+ lyear
					+ "' AND isresign='N' )");

			stmt.executeUpdate("INSERT INTO Dependants(regcode, employeeno, unicode, relation, name, "
					+ "birthday, ayear) "
					+ "select regcode, employeeno, unicode, relation, name, birthday, '"
					+ ayear
					+ "' as ayear "
					+ "from Dependants "
					+ "where regcode='"
					+ regcode
					+ "' and ayear='"
					+ lyear
					+ "' "
					+ "and employeeno not in (select employeeno from Dependants where regcode='"
					+ regcode
					+ "' and ayear='"
					+ ayear
					+ "' "
					+ " UNION SELECT employeeno FROM employees WHERE regcode='"
					+ regcode
					+ "' and ayear='"
					+ lyear
					+ "' AND isresign='N' )");

			/*
			 * rs = stmt.executeQuery(
			 * "SELECT isnative, unicode, passport, name, address, onboarddate, accountno, isresign, resigndate, govinsurance, retirefee, title, regcode, employeeno, birthday, laborinsurance, healthinsurance, laborretirefee, ayear FROM employee WHERE regcode='"
			 * + regcode + "' AND ayear='" + lyear + "' AND isresign='N'");
			 * while(rs.next()) { for (int i=1; i<=18; i++) { if
			 * (rs.getString(i)!=null&&!rs.getString(i).equals(""))
			 * putStmt.setString(i, rs.getString(i)); else putStmt.setNull(i,
			 * Types.CHAR); } putStmt.setString(19, ayear);
			 * putStmt.executeUpdate(); }
			 * 
			 * rs = stmt.executeQuery(
			 * "SELECT a.regcode, a.employeeno, a.basesalary, a.baserate, a.overtimerate, a.mark, a.ayear FROM bsalary AS a, employee AS b WHERE a.regcode='"
			 * + regcode + "' AND a.ayear='" + lyear +
			 * "' AND a.ayear=b.ayear AND a.regcode=b.regcode AND a.employeeno=b.employeeno AND b.isresign='N'"
			 * ); while(rs.next()) { for (int i=1; i<=6; i++) { if
			 * (rs.getString(i)!=null&&!rs.getString(i).equals(""))
			 * updStmt.setString(i, rs.getString(i)); else updStmt.setNull(i,
			 * Types.CHAR); } updStmt.setString(7, ayear);
			 * updStmt.executeUpdate(); }
			 * 
			 * rs = stmt.executeQuery(
			 * "SELECT a.regcode, a.employeeno, a.seqno, a.itemmark, a.amount, a.ayear FROM salaryitem AS a, employee AS b WHERE a.regcode='"
			 * + regcode + "' AND a.ayear='" + lyear +
			 * "' AND a.ayear=b.ayear AND a.regcode=b.regcode AND a.employeeno=b.employeeno AND b.isresign='N'"
			 * ); while(rs.next()) { for (int i=1; i<=5; i++) { if
			 * (rs.getString(i)!=null&&!rs.getString(i).equals(""))
			 * updItem.setString(i, rs.getString(i)); else updItem.setNull(i,
			 * Types.CHAR); } updItem.setString(6, ayear);
			 * updItem.executeUpdate(); }
			 * 
			 * rs = stmt.executeQuery(
			 * "SELECT a.regcode, a.employeeno, a.unicode, a.relation, a.name, a.birthday, a.ayear FROM dependant AS a, employee AS b WHERE a.regcode='"
			 * + regcode + "' AND a.ayear='" + lyear +
			 * "' AND a.ayear=b.ayear AND a.regcode=b.regcode AND a.employeeno=b.employeeno AND b.isresign='N'"
			 * ); while(rs.next()) { for (int i=1; i<=6; i++) { if
			 * (rs.getString(i)!=null&&!rs.getString(i).equals(""))
			 * dependantStmt.setString(i, rs.getString(i)); else
			 * dependantStmt.setNull(i, Types.CHAR); }
			 * dependantStmt.setString(7, ayear); dependantStmt.executeUpdate();
			 * }
			 */
		} catch (Exception e) {
			throw e;
		} finally {
			close(conn);
		}
	}

	public List findDependant(String regcode, String employeeno, String year)
			throws Exception {
		Connection conn = null;
		ResultSet rs = null;
		List dependants = new ArrayList();
		try {
			conn = this.dataSource.getConnection();
			String query = "";
			query = "SELECT name, unicode FROM Dependants WHERE employeeno='"
					+ employeeno + "' and regcode='" + regcode
					+ "' and ayear='" + year + "'";

			Statement stmt = conn
					.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_READ_ONLY);
			rs = stmt.executeQuery(query);

			while (rs.next()) {
				dependants
						.add(new String[] { rs.getString(1), rs.getString(2) });
			}

			return dependants;
		} catch (Exception e) {
			throw e;
		} finally {
			close(rs);
			close(conn);
		}
	}

	public void exportEmployeeToXls(String regcode, String year, OutputStream os)
			throws Exception {
		String[] column = { "類別", "員工代號", "員工姓名", "身份證號", "註記", "郵遞區號;地址",
				"證號別" };
		ArrayList employees = (ArrayList) this.findEmployee(regcode, "", "",
				year, 0, -1);
		WritableWorkbook workbook = Workbook.createWorkbook(os);
		WritableSheet sheetAp = workbook.createSheet("員工", 0);
		int row = 0;
		for (int i = 0; i < column.length; i++)
			sheetAp.addCell(new Label(i, row, column[i]));
		row++;
		EmployeeBean employee = null;
		for (int i = 0; i < employees.size(); i++) {
			employee = (EmployeeBean) employees.get(i);
			sheetAp.addCell(new Label(0, row, "50"));
			sheetAp.addCell(new Label(1, row, employee.getEmployeeno()));
			sheetAp.addCell(new Label(2, row, employee.getName()));
			sheetAp.addCell(new Label(3, row, employee.getUnicode()));
			sheetAp.addCell(new Label(5, row, employee.getAddress()));
			row++;
		}
		workbook.write();
		workbook.close();
	}

	public void exportEmployeeToTxt(String regcode, String year, PrintWriter out)
			throws Exception {
		int[] datasize = { 12, 44, 10, 60, 4, 4 }; // 員工代號、員工姓名、身份證字號、地址、所得所屬年月起、所得所屬年月迄、退休金提撥金額

		ArrayList employees = (ArrayList) this.findEmployee(regcode, "", "",
				year, 0, -1);
		EmployeeBean employee = null;
		String annual = ("0" + (Integer.parseInt(year) - 1911));
		annual = annual.substring(annual.length() - 3);
		String itype = "50 ";
		for (int i = 0; i < employees.size(); i++) {
			employee = (EmployeeBean) employees.get(i);
			String dataline = annual + itype
					+ formatDataTxt(employee.getEmployeeno(), datasize[0])
					+ formatDataTxt(employee.getName(), datasize[1])
					+ formatDataTxt(employee.getUnicode(), datasize[2])
					+ formatDataTxt(employee.getAddress(), datasize[3])
					+ annual.substring(1) + "01" + annual.substring(1) + "12"
					+ employee.getRetirefee();
			out.println(dataline);
		}
	}

	private String formatDataTxt(String data, int length) {
		String spacer = "";
		for (int j = 0; j < length - data.getBytes().length; j++) {
			spacer += " ";
		}
		return (data + spacer);
	}
}
