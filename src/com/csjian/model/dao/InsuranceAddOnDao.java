package com.csjian.model.dao;

import javax.sql.*;
import javax.naming.*;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.*;
import java.util.*;

import com.csjian.model.bean.*;

public class InsuranceAddOnDao extends TemplateDao {
	public EmployeeBean retrieveIncomeEarner(String regcode, String unicode) throws Exception {
		Connection conn = null;
		ResultSet rs = null;
		EmployeeBean employee = null;
		try {
			conn = this.dataSource.getConnection();
			PreparedStatement getStmt = conn.prepareStatement("SELECT * FROM IncomeEarners WHERE regcode=? AND unicode = ?");
			synchronized (getStmt) {
				getStmt.clearParameters();
				getStmt.setString(1, regcode);
				getStmt.setString(2, unicode);
				rs = getStmt.executeQuery();
				if (rs.next()) {
					employee = new EmployeeBean();
					employee.setRegcode(rs.getString("regcode") != null ? rs.getString("regcode") : "");
					employee.setUnicode(rs.getString("unicode") != null ? rs.getString("unicode") : "");
					employee.setName(rs.getString("name") != null ? rs.getString("name") : "");
					employee.setAddress(rs.getString("address") != null ? rs.getString("address") : "");					
				} else {
					throw new Exception("Could not find incomeearner#" + regcode + " - " + unicode);
				}
			}
		} catch (Exception e) {
			throw e;
		} finally {
			close(rs);
			close(conn);
		}
		return employee;
	}

	
	public List<EmployeeBean> listIncomeEarners(String regcode, String year) throws Exception {
		Connection conn = null;
		ResultSet rs = null;
		List<EmployeeBean> employees = new ArrayList<EmployeeBean>();
		try {
			conn = this.dataSource.getConnection();
			PreparedStatement getAllStmt = conn.prepareStatement("SELECT regcode, employeeno, unicode, name, address FROM Employees where regcode=? AND ayear=? "
							+ " UNION "
							+ " SELECT regcode, '' as employeeno, unicode, name, address FROM IncomeEarners WHERE regcode=? "
							+ " ORDER BY name");
			synchronized (getAllStmt) {
				getAllStmt.setString(1, regcode);
				getAllStmt.setString(2, year);
				getAllStmt.setString(3, regcode);
				rs = getAllStmt.executeQuery();
			}
			EmployeeBean employee = null;
			while (rs.next()) {
				employee = new EmployeeBean();
				employee.setRegcode(rs.getString("regcode") != null ? rs.getString("regcode") : "");
				employee.setUnicode(rs.getString("unicode") != null ? rs.getString("unicode") : "");
				employee.setName(rs.getString("name") != null ? rs.getString("name") : "");
				employee.setAddress(rs.getString("address") != null ? rs.getString("address") : "");
				employee.setEmployeeno(rs.getString("employeeno") != null ? rs.getString("employeeno") : "");

				employees.add(employee);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(conn);
		}
		return employees;

	}
	
	public List<AutocompleteItemBean> incomeEarnerAutoCompleteList(String regcode, String year) throws Exception {
		Connection conn = null;
		ResultSet rs = null;
		List<AutocompleteItemBean> items = new ArrayList<AutocompleteItemBean>();
		try {
			conn = this.dataSource.getConnection();
			PreparedStatement getAllStmt = conn.prepareStatement("SELECT regcode, employeeno, unicode, name, address FROM Employees where regcode=? AND ayear=? "
							+ " UNION "
							+ " SELECT regcode, '' as employeeno, unicode, name, address FROM IncomeEarners WHERE regcode=? "
							+ " ORDER BY name");
			synchronized (getAllStmt) {
				getAllStmt.setString(1, regcode);
				getAllStmt.setString(2, year);
				getAllStmt.setString(3, regcode);
				rs = getAllStmt.executeQuery();
			}
			AutocompleteItemBean item = null;
			while (rs.next()) {
				item = new AutocompleteItemBean();
				item.setId(rs.getString("unicode"));
				item.setLabel(rs.getString("unicode") + " " + rs.getString("name"));
				item.setValue(rs.getString("unicode"));
				items.add(item);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(conn);
		}
		return items;

	}
	
	public void updateIncomeEarner(EmployeeBean employee) throws Exception {
		System.out.println("in update");
		Connection conn = null;
		try {
			conn = this.dataSource.getConnection();
			PreparedStatement updStmt = conn.prepareStatement("UPDATE IncomeEarners SET name=?, address=?, isnative=? WHERE regcode=? AND unicode=? ");
			synchronized (updStmt) {
				updStmt.clearParameters();
				updStmt.setString(1, employee.getName());
				updStmt.setString(2, employee.getAddress());
				updStmt.setString(3, employee.getIsnative());
				updStmt.setString(4, employee.getRegcode());
				if (employee.getUnicode() != null) {
					updStmt.setString(5, employee.getUnicode().toUpperCase());
				} else {
					updStmt.setNull(5, Types.CHAR);
				}

				updStmt.executeUpdate();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			close(conn);
		}
	}

	public int insertIncomeEarner(EmployeeBean employee) throws Exception {
		Connection conn = null;
		int fetchs = 0;
		try {
			conn = this.dataSource.getConnection();
			ResultSet rs = null;
			String year = (Calendar.getInstance()).get(Calendar.YEAR) + "";
			PreparedStatement putStmt = conn.prepareStatement("SELECT * FROM Employees WHERE regcode=? AND unicode = ? AND ayear='" + year + "'");
			putStmt.setString(1, employee.getRegcode());
			putStmt.setString(2, employee.getUnicode());
			rs = putStmt.executeQuery();
			if (rs.next()) {
				return (-1);
			}
			putStmt = conn.prepareStatement("INSERT INTO IncomeEarners(name, address, regcode, unicode, isnative) values(?, ?, ?, ?, ?)");
			synchronized (putStmt) {
				putStmt.clearParameters();
				putStmt.setString(1, employee.getName());
				putStmt.setString(2, employee.getAddress());
				putStmt.setString(3, employee.getRegcode());
				if (employee.getUnicode() != null) {
					putStmt.setString(4, employee.getUnicode().toUpperCase());
				} else {
					putStmt.setNull(4, Types.CHAR);
				}
				putStmt.setString(5, employee.getIsnative());

				fetchs = putStmt.executeUpdate();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			close(conn);
		}
		return fetchs;
	}
	
	public void removeIncomeEarner(String regcode, String unicode)	throws Exception {
		Connection conn = null;
		try {
			conn = this.dataSource.getConnection();
			PreparedStatement remStmt = conn.prepareStatement("DELETE FROM IncomeEarners WHERE regcode=? AND unicode=? ");
			synchronized (remStmt) {
				remStmt.clearParameters();
				remStmt.setString(1, regcode);
				remStmt.setString(2, unicode);
				remStmt.executeUpdate();
			}
		} catch (Exception e) {
			System.out.println(e.toString());
			throw e;
		} finally {
			close(conn);
		}
	}
	
	public EmployeeBean findIncomeEarner(String regcode, String unicode, String name) {
		EmployeeBean incomeEarner = new EmployeeBean();
		Connection conn = null;
		ResultSet rs = null;
		String year = (Calendar.getInstance()).get(Calendar.YEAR) + "";
		try {
			conn = this.dataSource.getConnection();
			Statement stmt = conn.createStatement();
			String sql = null;
			sql = "SELECT * FROM IncomeEarners WHERE regcode='" + regcode + "' ";
			if (unicode !=null && !unicode.equals(""))
				sql += " AND unicode='" + unicode + "' ";
			if (name !=null && !name.equals(""))
				sql += " AND name='" + name + "' ";
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				incomeEarner.setUnicode(rs.getString("unicode") != null ? rs.getString("unicode") : "");
				incomeEarner.setName(rs.getString("name") != null ? rs.getString("name") : "");
				incomeEarner.setAddress(rs.getString("address") != null ? rs.getString("address") : "");
			}
			if (incomeEarner.getUnicode().equals("")) {
				sql = "SELECT * FROM Employees WHERE regcode='" + regcode + "' ";
				if (unicode !=null && !unicode.equals(""))
					sql += " AND unicode='" + unicode + "' ";
				if (name !=null && !name.equals(""))
					sql += " AND name='" + name + "' ";
				sql += " AND ayear='" + year + "' ";
				rs = stmt.executeQuery(sql);
				if (rs.next()) {
					incomeEarner.setUnicode(rs.getString("unicode") != null ? rs.getString("unicode") : "");
					incomeEarner.setName(rs.getString("name") != null ? rs.getString("name") : "");
					incomeEarner.setAddress(rs.getString("address") != null ? rs.getString("address") : "");
					incomeEarner.setHealthInsurance(rs.getString("healthInsurance") != null ? rs.getString("healthInsurance") : "");
				}
			}			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(conn);
		}
		return incomeEarner;
	}
	
	public InsuranceAddOnFeeBean retrieveInsuranceAddOnFee(int serialNo) throws Exception {
		Connection conn = null;
		ResultSet rs = null;
		InsuranceAddOnFeeBean insuranceAddOnFee = null;
		try {
			conn = this.dataSource.getConnection();
			PreparedStatement getStmt = conn.prepareStatement("SELECT * FROM InsuranceAddOnFees WHERE serialNo=?");
			synchronized (getStmt) {
				getStmt.clearParameters();
				getStmt.setInt(1, serialNo);
				rs = getStmt.executeQuery();
				if (rs.next()) {
					insuranceAddOnFee = this.makeInsuranceAddOnFee(rs);
				} else {
					throw new Exception("Could not find insuranceAddOnFee #" + serialNo);
				}
			}
		} catch (Exception e) {
			throw e;
		} finally {
			close(rs);
			close(conn);
		}
		return insuranceAddOnFee;
	}
	
	public List<InsuranceAddOnFeeBean> listInsuranceAddOnFees(String regcode, List<String> criteries) throws Exception {
		Connection conn = null;
		ResultSet rs = null;
		List<InsuranceAddOnFeeBean> insuranceAddOnFees = new ArrayList<InsuranceAddOnFeeBean>();
		try {
			conn = this.dataSource.getConnection();
			String sql = "SELECT * FROM InsuranceAddOnFees WHERE regcode='" + regcode + "' ";
			for (int i=0; i<criteries.size(); i++) {
				sql += " AND " + criteries.get(i);
			}
			System.out.println(sql);
			Statement stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				insuranceAddOnFees.add(this.makeInsuranceAddOnFee(rs));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(conn);
		}
		return insuranceAddOnFees;
	}
	
	public List<InsuranceAddOnFeeBean> listInsuranceAddOnFeeSummarys(String regcode, List<String> criteries) throws Exception {
		Connection conn = null;
		ResultSet rs = null;
		List<InsuranceAddOnFeeBean> insuranceAddOnFees = new ArrayList<InsuranceAddOnFeeBean>();
		try {
			conn = this.dataSource.getConnection();
			String sql = "SELECT SUBSTRING(CONVERT(varchar(12) , incomedate, 111 ), 1, 4) AS incomeDate, incomeType, " + 
					"unicode, name, sum(incomeAmount) AS incomeAmount, sum(insuranceAddOnFee) AS insuranceAddOnFee " +
					"FROM InsuranceAddOnFees WHERE regcode='" + regcode + "' ";
		
			for (int i=0; i<criteries.size(); i++) {
				sql += " AND " + criteries.get(i);
			}
			sql += " GROUP BY SUBSTRING(CONVERT(varchar(12) , incomedate, 111 ), 1, 4), incomeType, unicode, name ";
			System.out.println(sql);
			Statement stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				insuranceAddOnFees.add(this.makeInsuranceAddOnFeeSummary(rs));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(conn);
		}
		return insuranceAddOnFees;
	}
	
	public void updateInsuranceAddOnFee(InsuranceAddOnFeeBean insuranceAddOnFee) throws Exception {
		Connection conn = null;
		try {
			conn = this.dataSource.getConnection();
			PreparedStatement updStmt = conn.prepareStatement("UPDATE InsuranceAddOnFees SET unicode=?, name=?, incomeType=?, " + 
					"incomeDate=?, healthInsuranceSalary=?, incomeAmount=?, accumulatedBonusAmount=?, stockNote=?, trustNote=?, " + 
					"ICAAmount=?, annualICAAmount=?, excludeDate=?, isBoss=?, bossHealthInsuranceAmount=?, address=?,  " +
					"insuranceAddOnFee=? WHERE serialNo=?");
			synchronized (updStmt) {
				updStmt.clearParameters();
				updStmt.setString(1, insuranceAddOnFee.getUnicode());
				updStmt.setString(2, insuranceAddOnFee.getName());
				updStmt.setString(3, insuranceAddOnFee.getIncomeType());
				if (insuranceAddOnFee.getIncomeDate()!=null&&!insuranceAddOnFee.getIncomeDate().equals("")) {
					updStmt.setString(4, insuranceAddOnFee.getIncomeDate());
			    } else {
			    	updStmt.setNull(4, Types.DATE);
			    }				
				if (insuranceAddOnFee.getHealthInsuranceSalary() != null && !insuranceAddOnFee.getHealthInsuranceSalary().equals("")) {
					updStmt.setString(5, insuranceAddOnFee.getHealthInsuranceSalary());
				} else {
					updStmt.setNull(5, Types.INTEGER);
				}
				updStmt.setString(6, insuranceAddOnFee.getIncomeAmount());	
				if (insuranceAddOnFee.getAccumulatedBonusAmount() != null && !insuranceAddOnFee.getAccumulatedBonusAmount().equals("")) {
					updStmt.setString(7, insuranceAddOnFee.getAccumulatedBonusAmount());
				} else {
					updStmt.setNull(7, Types.INTEGER);
				}
				if (insuranceAddOnFee.getStockNote() != null && !insuranceAddOnFee.getStockNote().equals("")) {
					updStmt.setString(8, insuranceAddOnFee.getStockNote());
				} else {
					updStmt.setNull(8, Types.CHAR);
				}
				if (insuranceAddOnFee.getTrustNote() != null && !insuranceAddOnFee.getTrustNote().equals("")) {
					updStmt.setString(9, insuranceAddOnFee.getTrustNote());
				} else {
					updStmt.setNull(9, Types.CHAR);
				}
				if (insuranceAddOnFee.getICAAmount() != null && !insuranceAddOnFee.getICAAmount().equals("")) {
					updStmt.setString(10, insuranceAddOnFee.getICAAmount());
				} else {
					updStmt.setNull(10, Types.INTEGER);
				}
				if (insuranceAddOnFee.getAnnualICAAmount() != null && !insuranceAddOnFee.getAnnualICAAmount().equals("")) {
					updStmt.setString(11, insuranceAddOnFee.getAnnualICAAmount());
				} else {
					updStmt.setNull(11, Types.INTEGER);
				}
				if (insuranceAddOnFee.getExcludeDate()!=null&&!insuranceAddOnFee.getExcludeDate().equals("")) {
					updStmt.setString(12, insuranceAddOnFee.getExcludeDate());
			    } else {
			    	updStmt.setNull(12, Types.DATE);
			    }
				if (insuranceAddOnFee.getIsBoss() != null && !insuranceAddOnFee.getIsBoss().equals("")) {
					updStmt.setString(13, insuranceAddOnFee.getIsBoss());
				} else {
					updStmt.setNull(13, Types.CHAR);
				}
				if (insuranceAddOnFee.getBossHealthInsuranceAmount() != null && !insuranceAddOnFee.getBossHealthInsuranceAmount().equals("")) {
					updStmt.setString(14, insuranceAddOnFee.getBossHealthInsuranceAmount());
				} else {
					updStmt.setNull(14, Types.INTEGER);
				}
				if (insuranceAddOnFee.getAddress() != null && !insuranceAddOnFee.getAddress().equals("")) {
					updStmt.setString(15, insuranceAddOnFee.getAddress());
				} else {
					updStmt.setNull(15, Types.CHAR);
				}
				updStmt.setString(16, insuranceAddOnFee.getInsuranceAddOnFee());	
				updStmt.setInt(17, insuranceAddOnFee.getSerialNo());	
				updStmt.executeUpdate();
			}
			this.reCalcInsuranceAddOnFee(insuranceAddOnFee.getRegcode(), insuranceAddOnFee.getUnicode(), insuranceAddOnFee.getIncomeDate().substring(0, 4));
		} catch (Exception e) {
			throw e;
		} finally {
			close(conn);
		}
	}
	
	public int insertInsuranceAddOnFee(InsuranceAddOnFeeBean insuranceAddOnFee) throws Exception {
		Connection conn = null;
		int fetchs = 0;
		try {
			conn = this.dataSource.getConnection();
			PreparedStatement updStmt = conn.prepareStatement("INSERT INTO InsuranceAddOnFees(unicode, name, incomeType, " + 
					"incomeDate, healthInsuranceSalary, incomeAmount, accumulatedBonusAmount, stockNote, trustNote, " + 
					"ICAAmount, annualICAAmount, excludeDate, isBoss, bossHealthInsuranceAmount, address, insuranceAddOnFee, regcode) " +
					"VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			synchronized (updStmt) {
				updStmt.clearParameters();
				updStmt.setString(1, insuranceAddOnFee.getUnicode());
				updStmt.setString(2, insuranceAddOnFee.getName());
				updStmt.setString(3, insuranceAddOnFee.getIncomeType());
				if (insuranceAddOnFee.getIncomeDate()!=null&&!insuranceAddOnFee.getIncomeDate().equals("")) {
					updStmt.setString(4, insuranceAddOnFee.getIncomeDate());
			    } else {
			    	updStmt.setNull(4, Types.DATE);
			    }
				if (insuranceAddOnFee.getHealthInsuranceSalary() != null && !insuranceAddOnFee.getHealthInsuranceSalary().equals("")) {
					updStmt.setString(5, insuranceAddOnFee.getHealthInsuranceSalary());
				} else {
					updStmt.setNull(5, Types.INTEGER);
				}
				updStmt.setString(6, insuranceAddOnFee.getIncomeAmount());	
				if (insuranceAddOnFee.getAccumulatedBonusAmount() != null && !insuranceAddOnFee.getAccumulatedBonusAmount().equals("")) {
					updStmt.setString(7, insuranceAddOnFee.getAccumulatedBonusAmount());
				} else {
					updStmt.setNull(7, Types.INTEGER);
				}
				if (insuranceAddOnFee.getStockNote() != null && !insuranceAddOnFee.getStockNote().equals("")) {
					updStmt.setString(8, insuranceAddOnFee.getStockNote());
				} else {
					updStmt.setNull(8, Types.CHAR);
				}
				if (insuranceAddOnFee.getTrustNote() != null && !insuranceAddOnFee.getTrustNote().equals("")) {
					updStmt.setString(9, insuranceAddOnFee.getTrustNote());
				} else {
					updStmt.setNull(9, Types.CHAR);
				}
				if (insuranceAddOnFee.getICAAmount() != null && !insuranceAddOnFee.getICAAmount().equals("")) {
					updStmt.setString(10, insuranceAddOnFee.getICAAmount());
				} else {
					updStmt.setNull(10, Types.INTEGER);
				}
				if (insuranceAddOnFee.getAnnualICAAmount() != null && !insuranceAddOnFee.getAnnualICAAmount().equals("")) {
					updStmt.setString(11, insuranceAddOnFee.getAnnualICAAmount());
				} else {
					updStmt.setNull(11, Types.INTEGER);
				}
				if (insuranceAddOnFee.getExcludeDate()!=null&&!insuranceAddOnFee.getExcludeDate().equals("")) {
					updStmt.setString(12, insuranceAddOnFee.getExcludeDate());
			    } else {
			    	updStmt.setNull(12, Types.DATE);
			    }
				if (insuranceAddOnFee.getIsBoss() != null && !insuranceAddOnFee.getIsBoss().equals("")) {
					updStmt.setString(13, insuranceAddOnFee.getIsBoss());
				} else {
					updStmt.setNull(13, Types.CHAR);
				}
				if (insuranceAddOnFee.getBossHealthInsuranceAmount() != null && !insuranceAddOnFee.getBossHealthInsuranceAmount().equals("")) {
					updStmt.setString(14, insuranceAddOnFee.getBossHealthInsuranceAmount());
				} else {
					updStmt.setNull(14, Types.INTEGER);
				}
				if (insuranceAddOnFee.getAddress() != null && !insuranceAddOnFee.getAddress().equals("")) {
					updStmt.setString(15, insuranceAddOnFee.getAddress());
				} else {
					updStmt.setNull(15, Types.CHAR);
				}
				updStmt.setString(16, insuranceAddOnFee.getInsuranceAddOnFee());	
				updStmt.setString(17, insuranceAddOnFee.getRegcode());	
				fetchs = updStmt.executeUpdate();
			}
			this.reCalcInsuranceAddOnFee(insuranceAddOnFee.getRegcode(), insuranceAddOnFee.getUnicode(), insuranceAddOnFee.getIncomeDate().substring(0, 4));
		} catch (Exception e) {
			throw e;
		} finally {
			close(conn);
		}
		return fetchs;
	}
	
	public void removeInsuranceAddOnFee(int serialNo) throws Exception {
		Connection conn = null;
		try {
			InsuranceAddOnFeeBean fee = this.retrieveInsuranceAddOnFee(serialNo);
			
			conn = this.dataSource.getConnection();
			PreparedStatement updStmt = conn.prepareStatement("DELETE FROM InsuranceAddOnFees WHERE serialNo=?");
			synchronized (updStmt) {
				updStmt.clearParameters();
				updStmt.setInt(1, serialNo);				
				updStmt.executeUpdate();
			}
			
			// Oct. 11, 2013 刪除後，重算累積獎金
			this.reCalcInsuranceAddOnFee(fee.getRegcode(), fee.getUnicode(), fee.getIncomeDate().substring(0, 4));
			
		} catch (Exception e) {
			throw e;
		} finally {
			close(conn);
		}
	}
	
	private void reCalcInsuranceAddOnFee(String regcode, String unicode, String year) {		
		// 把同一個人當年度所有 incomeType=62 的取出
		System.out.println("reCalcInsuranceAddOnFee - regcode:" + regcode + ", unicode:" + unicode + ", year:" + year); 
		List<String> criteries = new ArrayList<String>();
		criteries.add("incomeType='62'");
		criteries.add("SUBSTRING(CONVERT(varchar(12) , incomedate, 111 ), 1, 4)='" + year + "'");
		criteries.add("unicode='" + unicode + "'");
		double feeRate = 2;
		// 2016/1/1 費率由 2% 改為 1.91%
		if (year.equals("2015")) {
			feeRate = 0.02;
		} else {
			feeRate = 0.0191;
		}
		try {
			Connection conn = this.dataSource.getConnection();
			PreparedStatement updStmt = conn.prepareStatement("UPDATE InsuranceAddOnFees SET accumulatedBonusAmount=?, insuranceAddOnFee=? WHERE serialNo=?");

			List<InsuranceAddOnFeeBean> insuranceAddOnFees = this.listInsuranceAddOnFees(regcode, criteries);
			int total = 0;
			int bonus = 0;
			int healthSalary = 0;
			int serialNo = 0;
			int fee = 0;
			for(int i=0; i<insuranceAddOnFees.size(); i++) {
				total = 0;
				fee = 0;
				bonus = Integer.parseInt(insuranceAddOnFees.get(i).getIncomeAmount());
				healthSalary = Integer.parseInt(insuranceAddOnFees.get(i).getHealthInsuranceSalary());
				serialNo = insuranceAddOnFees.get(i).getSerialNo();
				// 重新計算累積金額
				total = bonus + this.totalBonusAmount(regcode, unicode, insuranceAddOnFees.get(i).getIncomeDate(), serialNo +"");
				// 重新計算附加保費
				if (total - healthSalary*4 > 0) { // 累計超過 4 倍保額，才收補充保費
	 				float tmpfee = (float) (((total-bonus) > healthSalary*4) ? ((float)bonus*feeRate): ((float)(total - healthSalary*4)*feeRate));
	 				fee = Math.round(tmpfee);
	 			} else {
	 				fee = 0;
	 			}
				System.out.println("total=" + total + ", bonus=" + bonus + ", healthSalary=" + healthSalary + ", fee=" + fee);
				// 更新系統
				updStmt.clearParameters();
				updStmt.setString(1, total + "");
				updStmt.setString(2, fee + "");
				updStmt.setInt(3, serialNo);
				updStmt.executeUpdate();
			}				
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param regcode
	 * @param unicode
	 * @param incomeDate 收入日期，格式 2013-3-25
	 * @param serialNo 當筆收入的序號
	 * @return
	 * @throws Exception
	 */
	
	public int bonusAmount(String regcode, String unicode, String incomeDate, int salaryShift) throws Exception {
		int amount = 0;
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = this.dataSource.getConnection();
			Statement stmt = conn.createStatement();
			String sql = "";
			incomeDate = incomeDate.replace('-', '/');
			String[] part = incomeDate.split("/");
			String year = part[0];
			String month = part[1];
			String employeeno;
			
			if (salaryShift==1) {
				if (month.equals("1")) {
					year = (Integer.parseInt(year) - 1) + "";
					month = "12";
				} else {
					month = (Integer.parseInt(month) - 1) + "";
				}
			}
			
			
			rs = stmt.executeQuery("SELECT employeeno FROM Employees WHERE regcode='" + regcode + "' " + 
					" AND ayear='" + year + "' AND unicode= '" + unicode + "' ");
			if (rs.next()) {
				employeeno = rs.getString(1);				
			} else {
				return 0;
			}
			
			sql = "SELECT SUM(amount) FROM SalaryItems WHERE itemmark='P' AND regcode='" + regcode + "' " + 
					"AND employeeno='" + employeeno + "' AND ayear ='" + year + "' AND amonth ='" + month + "' " + 
					"AND seqno IN (SELECT seqno FROM PItems WHERE bonusFlag='Y' AND regcode in ('" + regcode + "', 'admin') ) ";

			System.out.println(sql);
			rs = stmt.executeQuery(sql);

			if (rs.next()) {
				amount = rs.getInt(1);
			}
			
		} catch (Exception e) {
			throw e;
		} finally {
			close(conn);
		}
		return amount;
	}
	
	/**
	 * 
	 * @param regcode
	 * @param unicode
	 * @param incomeDate 收入日期，格式 2013-3-25
	 * @param serialNo 當筆收入的序號
	 * @return
	 * @throws Exception
	 */
	
	public int totalBonusAmount(String regcode, String unicode, String incomeDate, String serialNo) throws Exception {
		int amount = 0;
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = this.dataSource.getConnection();
			Statement stmt = conn.createStatement();
			String sql = "";
			incomeDate = incomeDate.replace('-', '/');
			String[] part = incomeDate.split("/");
			String year = part[0];
			incomeDate = part[0] + "/" + (part[1].length()>1 ? part[1] : "0" + part[1]) + "/" + (part[2].length()>1 ? part[2] : "0" + part[2]);
			if (incomeDate!=null && !incomeDate.equals("")) {
				sql = "SELECT SUM(incomeAmount) FROM InsuranceAddOnFees WHERE regcode='" + regcode + "' " + 
						"AND unicode='" + unicode + "' " + 
						"AND (SUBSTRING(CONVERT(varchar(12) , incomedate, 111 ), 1, 10)<'" + incomeDate + "' OR (SUBSTRING(CONVERT(varchar(12) , incomedate, 111 ), 1, 10)='" + incomeDate + "' AND serialNo <'" + serialNo + "')) AND incomeType='62' AND SUBSTRING(CONVERT(varchar(12) , incomedate, 111 ), 1, 4)='" + year + "' ";
			} else {
				return 0;
			}
			System.out.println(sql);
			rs = stmt.executeQuery(sql);

			if (rs.next()) {
				amount = rs.getInt(1);
			}
			
		} catch (Exception e) {
			throw e;
		} finally {
			close(conn);
		}
		return amount;
	}
	
	public InsuranceAddOnFeeBean retrieveInsuranceAddOnFeeSummary(String regcode, String year, String unicode, String incomeType) throws Exception {
		Connection conn = null;
		ResultSet rs = null;
		InsuranceAddOnFeeBean insuranceAddOnFee = null;
		try {
			conn = this.dataSource.getConnection();
			Statement stmt = conn.createStatement();
			String sql = "SELECT * FROM InsuranceAddOnFees WHERE regcode='" + regcode + "' AND unicode='" + unicode + "' " + 
					" AND incomeType='" + incomeType + "' " +
					" AND SUBSTRING(CONVERT(varchar(12) , incomedate, 111 ), 1, 4) ='" + year + "' ";
			System.out.println(sql);
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				insuranceAddOnFee = this.makeInsuranceAddOnFee(rs);
			} else {
				throw new Exception("Could not find insuranceAddOnFee for regcode:" + regcode + ",unicode:" + unicode + ",year:" + year + ",incomeType:" + incomeType);
			}
			
			sql = "SELECT SUM(incomeAmount), SUM(insuranceAddOnFee) FROM insuranceAddOnFees WHERE regcode='" + regcode + "' AND unicode='" + unicode + "' " + 
					" AND incomeType='" + incomeType + "' " +
					" AND SUBSTRING(CONVERT(varchar(12) , incomedate, 111 ), 1, 4) ='" + year + "' ";
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				insuranceAddOnFee.setIncomeAmount(rs.getString(1)!=null?rs.getString(1):"");
				insuranceAddOnFee.setInsuranceAddOnFee(rs.getString(2)!=null?rs.getString(2):"");
			} 			
		} catch (Exception e) {
			throw e;
		} finally {
			close(rs);
			close(conn);
		}
		return insuranceAddOnFee;
	}
	
	public M0203DataBean calcM0203Data(String regcode, String fromYear, String fromMonth, String toMonth) throws Exception {
		M0203DataBean m0203 = new M0203DataBean();
		System.out.println("fromMonth before=" + fromMonth);
		if (fromMonth.trim().length()==1) fromMonth = "0" + fromMonth.trim();
		if (toMonth.trim().length()==1) toMonth = "0" + toMonth.trim();
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = this.dataSource.getConnection();
			Statement stmt = conn.createStatement();
			String sql = "SELECT incomeType, count(*) AS count, SUM(incomeAmount) AS incomeAmount, SUM(insuranceAddOnFee) AS insuranceAddOnFee " + 
					" FROM InsuranceAddOnFees " +
					" WHERE regcode='" + regcode + "' " + 
					" AND SUBSTRING(CONVERT(varchar(12) , incomedate, 111 ), 1, 7) >='" + fromYear + "/" + fromMonth + "' " + 
					" AND SUBSTRING(CONVERT(varchar(12) , incomedate, 111 ), 1, 7) <='" + fromYear + "/" + toMonth + "' " + 
					" GROUP BY incomeType";
			System.out.println(sql);
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				switch(Integer.parseInt(rs.getString(1))) {
				case 62:
					m0203.setCount62(rs.getInt(2));
					m0203.setIncomeAmount62(rs.getInt(3));
					m0203.setInsuranceAddOnFee62(rs.getInt(4));
					break;
				case 63:
					m0203.setCount63(rs.getInt(2));
					m0203.setIncomeAmount63(rs.getInt(3));
					m0203.setInsuranceAddOnFee63(rs.getInt(4));
					break;
				case 65:
					m0203.setCount65(rs.getInt(2));
					m0203.setIncomeAmount65(rs.getInt(3));
					m0203.setInsuranceAddOnFee65(rs.getInt(4));
					break;
				case 66:
					m0203.setCount66(rs.getInt(2));
					m0203.setIncomeAmount66(rs.getInt(3));
					m0203.setInsuranceAddOnFee66(rs.getInt(4));
					break;
				case 67:
					m0203.setCount67(rs.getInt(2));
					m0203.setIncomeAmount67(rs.getInt(3));
					m0203.setInsuranceAddOnFee67(rs.getInt(4));
					break;
				case 68:
					m0203.setCount68(rs.getInt(2));
					m0203.setIncomeAmount68(rs.getInt(3));
					m0203.setInsuranceAddOnFee68(rs.getInt(4));
					break;
				default:
					break;
				}
			
			}
		
		} catch (Exception e) {
			throw e;
		} finally {
			close(rs);
			close(conn);
		}
		return m0203;		
	}
	
	public List<InsuranceAddOnFeeBean> listM0305Data(String regcode, String fromYear, String fromMonth, String toMonth) throws Exception {
		Connection conn = null;
		ResultSet rs = null;
		List<InsuranceAddOnFeeBean> insuranceAddOnFees = new ArrayList<InsuranceAddOnFeeBean>();
		if (fromMonth.length()==1) fromMonth = "0" + fromMonth;
		if (toMonth.length()==1) toMonth = "0" + toMonth;
		try {
			conn = this.dataSource.getConnection();
			String sql = "SELECT SUBSTRING(CONVERT(varchar(12) , incomedate, 111 ), 1, 7) AS incomeDate, incomeType, trustNote, " + 
					"sum(insuranceAddOnFee) AS insuranceAddOnFee " +
					"FROM InsuranceAddOnFees " +
					" WHERE regcode='" + regcode + "' " + 
					" AND SUBSTRING(CONVERT(varchar(12) , incomedate, 111 ), 1, 7) >='" + fromYear + "/" + fromMonth + "' " + 
					" AND SUBSTRING(CONVERT(varchar(12) , incomedate, 111 ), 1, 7) <='" + fromYear + "/" + toMonth + "' " +
					" GROUP BY SUBSTRING(CONVERT(varchar(12) , incomedate, 111 ), 1, 7), incomeType, trustNote ";
			System.out.println(sql);
			Statement stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				insuranceAddOnFees.add(this.makeM0305Item(rs));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(conn);
		}
		return insuranceAddOnFees;
	}
	
	public InsuranceAddOnFeeBean retrieveM0305Data(String regcode, String incomePeriod, String incomeType) throws Exception {
		Connection conn = null;
		ResultSet rs = null;
		InsuranceAddOnFeeBean insuranceAddOnFee = null;
		try {
			conn = this.dataSource.getConnection();
			Statement stmt = conn.createStatement();
			String sql = "SELECT * FROM InsuranceAddOnFees WHERE regcode='" + regcode + "' " + 
					" AND incomeType='" + incomeType + "' " +
					" AND SUBSTRING(CONVERT(varchar(12) , incomedate, 111 ), 1, 7) ='" + incomePeriod + "' ";
			System.out.println(sql);
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				insuranceAddOnFee = this.makeInsuranceAddOnFee(rs);
			} else {
				throw new Exception("Could not find retrieveM0305Data for regcode:" + regcode + ", incomePeriod:" + incomePeriod + ",incomeType:" + incomeType);
			}
			
			sql = "SELECT SUM(insuranceAddOnFee) FROM insuranceAddOnFees WHERE regcode='" + regcode + "' " +
					" AND incomeType='" + incomeType + "' " +
					" AND SUBSTRING(CONVERT(varchar(12) , incomedate, 111 ), 1, 7) ='" + incomePeriod + "' ";
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				insuranceAddOnFee.setInsuranceAddOnFee(rs.getString(1)!=null?rs.getString(1):"");
			} 			
		} catch (Exception e) {
			throw e;
		} finally {
			close(rs);
			close(conn);
		}
		return insuranceAddOnFee;
	}
	
	public String[] calcSalaryAmount(String regcode, String year, String month, int salaryShift) throws Exception {
		List<SalaryBean> salaries = this.salaryAmountDetail(regcode, year, month, salaryShift);
		int healthInsurance = 0;
		int total = 0;
		SalaryBean salary = null;
		for (int i=0; i<salaries.size(); i++){
			salary = salaries.get(i);
			total += Integer.parseInt(salary.getTotal());
			healthInsurance += Integer.parseInt(salary.getHealthInsurance());
		}
		String[] data = new String[2];
		data[0] = total + "";
		data[1] = healthInsurance + "";
		return data;
	}
	
	public List<SalaryBean> salaryAmountDetail(String regcode, String year, String month, int salaryShift) throws Exception {
		Connection conn = null;
		ResultSet rs = null;
		List<SalaryBean> salaries = new ArrayList<SalaryBean>();
		if (month.length()==1) month = "0" + month;
		String salaryYear = year;
		String salaryMonth = month;
		if (salaryShift==1) {
			if (month.equals("01")) {
				salaryYear = (Integer.parseInt(year) - 1) + "";
				salaryMonth = "12";
			} else {
				salaryMonth = ((Integer.parseInt(salaryMonth) - 1)>9) ? ((Integer.parseInt(salaryMonth) - 1) + "") : ("0" + (Integer.parseInt(salaryMonth) - 1));
			}
		}
		try {
			conn = this.dataSource.getConnection();
			Statement stmt = conn.createStatement();
			String bossno = "";
			rs = stmt.executeQuery("SELECT employeeno FROM Employees WHERE regcode='" + regcode + "' " + 
					" AND ayear='" + year + "' AND unicode= (SELECT bossid FROM Companies WHERE regcode='" + regcode + "')");
			if (rs.next()) {
				bossno = rs.getString(1)!=null?rs.getString(1):"";				
			}
			System.out.println("salaryAmountDetail year=" + year + " ,month=" + month + ",bossno=" + bossno);
			
			// 2013/5/13 修改，把扣繳稅額加回
			String sql = "SELECT s.employeeno, e.unicode, e.name, (s.total - ISNULL(s.oversalary, 0) + ISNULL(tax, 0)) AS total, " +
					"(case when SUBSTRING(CONVERT(varchar(12) , e.resigndate, 111 ), 1, 7) <= '" + year + "/" + month + "' then '0' else s.healthinsurance end) AS healthinsurance " + 
					" FROM Salarys s, Employees e WHERE s.regcode='" + regcode + "' " + 
					" AND SUBSTRING(CONVERT(varchar(12) , onboarddate, 111 ), 1, 7) <= '" + year + "/" + month + "' " +
					" AND s.ayear='" + salaryYear + "' AND s.amonth='" + salaryMonth + "' " + 
					" AND s.regcode=e.regcode AND s.employeeno=e.employeeno AND s.ayear=e.ayear ";
			System.out.println("查詢 SalaryAmount Detail, sql -");
			System.out.println(sql);
			rs = stmt.executeQuery(sql);
			SalaryBean salary = null;
			while (rs.next()) {
				salary = new SalaryBean();
				salary.setEmployeeno(rs.getString("employeeno") != null ? rs.getString("employeeno") : "");
				salary.setName(rs.getString("name") != null ? rs.getString("name") : "");
				salary.setHealthInsurance(rs.getString("healthinsurance") != null ? rs.getString("healthinsurance") : "0");
				salary.setTotal(rs.getString("total") != null ? rs.getString("total") : "0");
				if (salary.getEmployeeno().equals(bossno)) {
					System.out.println("salaryAmountDetail got bossno !!");
					salary.setHealthInsurance("0");
				}
				
				salaries.add(salary);
			} 
			
			// 扣除不計入二代健保薪資項目
			sql = "SELECT seqno FROM pitems where regcode in ('" + regcode + "', 'admin') and addOnFeeFlag='Y' ";
			rs = stmt.executeQuery(sql);
			String seqno = "'0'";
			while (rs.next()) {
				seqno += ", '" + rs.getString(1) + "' ";
			} 
						
			sql = "SELECT amount FROM SalaryItems WHERE regcode='" + regcode + "' " + 
					" AND ayear='" + salaryYear + "' AND amonth='" + salaryMonth + "' AND seqno IN (" + seqno + ") AND itemmark='P' AND employeeno=?";
			System.out.println("    扣除不計入二代健保薪資項目 sql - " + sql);
			PreparedStatement preStmt = conn.prepareStatement(sql);
			for (int i=0; i<salaries.size(); i++) {
				preStmt.clearParameters();
				preStmt.setString(1, salaries.get(i).getEmployeeno());
				rs = preStmt.executeQuery();
				while (rs.next()) {
					salaries.get(i).setTotal((Integer.parseInt(salaries.get(i).getTotal()) - (rs.getString(1)!=null ? rs.getInt(1) : 0)) +"");
				}
			}
			
			// 加回要計入二代健保薪資項目
			sql = "SELECT seqno FROM mitems where regcode in ('" + regcode + "', 'admin') and addOnFeeFlag='Y' ";
			rs = stmt.executeQuery(sql);
			seqno = "'0'";
			while (rs.next()) {
				seqno += ", '" + rs.getString(1) + "' ";
			} 
			
			sql = "SELECT SUM(ISNULL(amount, 0)) FROM SalaryItems WHERE regcode='" + regcode + "' " + 
					" AND ayear='" + salaryYear + "' AND amonth='" + salaryMonth + "' AND seqno IN (" + seqno + ") AND itemmark='M' AND employeeno=?";
			System.out.println("    加回要計入二代健保薪資項目 sql - " + sql);
			preStmt = conn.prepareStatement(sql);
			for (int i=0; i<salaries.size(); i++) {
				preStmt.clearParameters();
				preStmt.setString(1, salaries.get(i).getEmployeeno());
				rs = preStmt.executeQuery();
				while (rs.next()) {
					salaries.get(i).setTotal((Integer.parseInt(salaries.get(i).getTotal()) + (rs.getString(1)!=null ? rs.getInt(1) : 0)) +"");
				}
			}
			
			// 重新取健保費
			// 2013/11/2 移除這一段
			/*
			sql = "SELECT ISNULL(healthinsurance, 0) FROM Salarys WHERE regcode='" + regcode + "' " + 
					" AND ayear='" + year + "' AND amonth='" + salaryMonth + "' AND employeeno=?";
			System.out.println("sql=" + sql);
			preStmt = conn.prepareStatement(sql);
			for (int i=0; i<salaries.size(); i++) {
				if (!salaries.get(i).getEmployeeno().equals(bossno)) {
					preStmt.clearParameters();
					preStmt.setString(1, salaries.get(i).getEmployeeno());
					rs = preStmt.executeQuery();
					if (rs.next()) {
						salaries.get(i).setHealthInsurance(rs.getString(1));
					} else {
						rs = stmt.executeQuery("SELECT healthinsurance FROM Employees WHERE regcode='" + regcode + "' AND ayear='" + year + "' AND employeeno='" + salaries.get(i).getEmployeeno() + "'");
						if (rs.next()) {
							salaries.get(i).setHealthInsurance(rs.getString("healthInsurance"));
						}
					}
				}
			}*/
			
			String lyear;
			String lmonth;
			if (month.equals("01")) {
				lyear = (Integer.parseInt(year) - 1) + "";
				lmonth = "12";
			} else {
				lmonth = ((Integer.parseInt(salaryMonth) - 1)>9) ? ((Integer.parseInt(salaryMonth) - 1) + "") : ("0" + (Integer.parseInt(salaryMonth) - 1));
				lyear = year;
			}
			
			// 把那些還沒有薪資，但有健保的加入清單
			sql = "SELECT employeeno, unicode, name, healthinsurance FROM Employees WHERE regcode='" + regcode + "' " + 
					" AND ayear='" + year + "' AND SUBSTRING(CONVERT(varchar(12) , onboarddate, 111 ), 1, 7) <= '" + year + "/" + month + "' AND isresign='N' " +
					" AND SUBSTRING(CONVERT(varchar(12) , onboarddate, 111 ), 1, 7) >= '" + lyear + "/" + lmonth + "'" + 
					" AND employeeno NOT IN (SELECT employeeno FROM Salarys WHERE regcode='" + regcode + "' AND ayear='" + salaryYear + "' AND amonth='" + salaryMonth + "' )";
			System.out.println("還沒有薪資，但有健保的加入清單 sql - ");
			System.out.println(sql);
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				salary = new SalaryBean();
				salary.setEmployeeno(rs.getString("employeeno") != null ? rs.getString("employeeno") : "");
				salary.setName(rs.getString("name") != null ? rs.getString("name") : "");
				salary.setHealthInsurance(rs.getString("healthinsurance") != null ? rs.getString("healthinsurance") : "0");
				salary.setTotal("0");
				if (salary.getEmployeeno().equals(bossno)) {
					salary.setHealthInsurance("0");
				}
				
				salaries.add(salary);
			} 
			
		} catch (Exception e) {
			throw e;
		} finally {
			close(rs);
			close(conn);
		}
		return salaries;
	}
	
	/**
	 * 檢查是否有那些員工的月薪資裡沒有設定健保投保薪資
	 * @param regcode
	 * @param year
	 * @param month
	 * @param salaryShift
	 * @return 沒有設定健保投保薪資的員工姓名列表
	 * @throws Exception
	 */
	public List<String> verifyHealthInsuranceBlank(String regcode, String year, String month, int salaryShift) throws Exception {
		Connection conn = null;
		ResultSet rs = null;
		List<String> employees = new ArrayList<String>();
		if (month.length()==1) month = "0" + month;
		String salaryYear = year;
		String salaryMonth = month;
		if (salaryShift==1) {
			if (month.equals("01")) {
				salaryYear = (Integer.parseInt(year) - 1) + "";
				salaryMonth = "12";
			} else {
				salaryMonth = ((Integer.parseInt(salaryMonth) - 1)>9) ? ((Integer.parseInt(salaryMonth) - 1) + "") : ("0" + (Integer.parseInt(salaryMonth) - 1));
			}
		}
		try {
			conn = this.dataSource.getConnection();
			Statement stmt = conn.createStatement();
			String bossno = "";
			rs = stmt.executeQuery("SELECT employeeno FROM Employees WHERE regcode='" + regcode + "' " + 
					" AND ayear='" + year + "' AND unicode= (SELECT bossid FROM Companies WHERE regcode='" + regcode + "')");
			if (rs.next()) {
				bossno = rs.getString(1)!=null?rs.getString(1):"";				
			}
			
			String sql = "SELECT e.name " +
					" FROM Salarys s, Employees e WHERE s.regcode='" + regcode + "' " + 
					" AND s.ayear='" + salaryYear + "' AND s.amonth='" + salaryMonth + "' " + 
					" AND s.regcode=e.regcode AND s.employeeno=e.employeeno AND s.ayear=e.ayear AND s.healthinsurance IS NULL and s.employeeno !='" + bossno + "'";
			rs = stmt.executeQuery(sql);
			SalaryBean salary = null;
			while (rs.next()) {
				employees.add(rs.getString("name"));
			} 
		} catch (Exception e){
			e.printStackTrace();
			throw e;
		}
		return employees;
	}
	
	public void updateCompanyHealthInfo(CompanyBean company) throws Exception {
		Connection conn = null;
		int fetchs = 0;
		try {
			conn = this.dataSource.getConnection();
			PreparedStatement updStmt = conn.prepareStatement("UPDATE Companies SET contact = ?, phone = ?, address = ?, healthCode = ?, bossName = ?, " +
					"regName = ?, email = ?, bossId=?, salaryShift=?, bossIsNative=? WHERE regcode = ?");
			synchronized (updStmt) {
				updStmt.clearParameters();
				updStmt.setString(1, company.getContact() != null ? company.getContact():"");
				updStmt.setString(2, company.getPhone() != null ? company.getPhone():"");
				updStmt.setString(3, company.getAddress() != null ? company.getAddress():"");
				updStmt.setString(4, company.getHealthCode() != null ? company.getHealthCode():"");
				updStmt.setString(5, company.getBossName() != null ? company.getBossName():"");
				updStmt.setString(6, company.getRegname() != null ? company.getRegname():"");
				updStmt.setString(7, company.getEmail() != null ? company.getEmail():"");
				updStmt.setString(8, company.getBossId() != null ? company.getBossId():"");
				updStmt.setInt(9, company.getSalaryShift());
				updStmt.setString(10, company.getBossIsNative() != null ? company.getBossIsNative():"Y");
				updStmt.setString(11, company.getRegcode());
				
				fetchs = updStmt.executeUpdate();
			}
		} catch (Exception e) {
			throw e;
		} finally {
			close(conn);
		}
	}
	
	public CompanyBean retrieveCompanyHealthInfo(String regcode) throws Exception {
		Connection conn = null;
		ResultSet rs = null;
		CompanyBean company = null;
		try {
			conn = this.dataSource.getConnection();
			PreparedStatement getStmt = conn.prepareStatement("SELECT * FROM Companies WHERE regcode=?");
			synchronized (getStmt) {
				getStmt.clearParameters();
				getStmt.setString(1, regcode);
				rs = getStmt.executeQuery();
				if (rs.next()) {
					company = new CompanyBean();
					company.setRegcode(rs.getString("regcode") != null ? rs.getString("regcode") : "");
					company.setHealthCode(rs.getString("healthCode") != null ? rs.getString("healthCode") : "");
					company.setContact(rs.getString("contact") != null ? rs.getString("contact") : "");
					company.setBossName(rs.getString("bossName") != null ? rs.getString("bossName") : "");
					company.setPhone(rs.getString("phone") != null ? rs.getString("phone") : "");
					company.setRegname(rs.getString("regname") != null ? rs.getString("regname") : "");
					company.setName(rs.getString("regname") != null ? rs.getString("regname") : "");
					company.setAddress(rs.getString("address") != null ? rs.getString("address") : "");
					company.setEmail(rs.getString("email") != null ? rs.getString("email") : "");
					company.setBossIsNative(rs.getString("bossIsNative") != null ? rs.getString("bossIsNative") : "Y");
					company.setBossId(rs.getString("bossId") != null ? rs.getString("bossId") : "");
					company.setSalaryShift(rs.getInt("salaryShift"));
				} else {
					throw new Exception("Could not find company#" + regcode);
				}
			}
		} catch (Exception e) {
			throw e;
		} finally {
			close(rs);
			close(conn);
		}
		return company;
	}
	
	public void updateDeclareNo(HashMap<String, String> feeDeclares) throws Exception {
		Connection conn = null;
		try {
			conn = this.dataSource.getConnection();
			PreparedStatement updStmt = conn.prepareStatement("UPDATE InsuranceAddOnFees SET declareNo = ? WHERE serialNo = ?");
			synchronized (updStmt) {
				Iterator iter = feeDeclares.entrySet().iterator(); 
				Map.Entry entry;
				while (iter.hasNext()) { 
				    entry = (Map.Entry) iter.next(); 
				    updStmt.clearParameters();
				    updStmt.setString(1, (String)entry.getValue());
				    updStmt.setString(2, (String)entry.getKey());
				    updStmt.executeUpdate();
				}				
			}
		} catch (Exception e) {
			throw e;
		} finally {
			close(conn);
		}
	}
	
	public M0203DataBean getOutFileRecordSummary(String regcode, String year, String month, String incomeTypes) throws Exception {
		M0203DataBean m0203 = new M0203DataBean();
		if (month.length()==1) month = "0" + month;
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = this.dataSource.getConnection();
			Statement stmt = conn.createStatement();
			String sql = "SELECT incomeType, count(*) AS count, SUM(incomeAmount) AS incomeAmount, SUM(insuranceAddOnFee) AS insuranceAddOnFee " + 
					" FROM InsuranceAddOnFees " +
					" WHERE regcode='" + regcode + "' " + 
					" AND SUBSTRING(CONVERT(varchar(12) , incomedate, 111 ), 1, 7) ='" + year + "/" + month + "' " + 
					" AND declareNo IS NOT NULL ";
			if (incomeTypes!=null && incomeTypes.length()>2) {
				String[] para2 = incomeTypes.split(",");
				StringBuilder sb = new StringBuilder(" AND incomeType IN (");
				for (int i=0; i<para2.length; i++) {
					sb.append(i!=0?"," : "");
					sb.append("'" + para2[i] + "' ");
				}
				sb.append(")");
				sql += sb.toString();
			}		
			
			sql += " GROUP BY incomeType";
			
			System.out.println(sql);
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				switch(Integer.parseInt(rs.getString(1))) {
				case 62:
					m0203.setCount62(rs.getInt(2));
					m0203.setIncomeAmount62(rs.getInt(3));
					m0203.setInsuranceAddOnFee62(rs.getInt(4));
					break;
				case 63:
					m0203.setCount63(rs.getInt(2));
					m0203.setIncomeAmount63(rs.getInt(3));
					m0203.setInsuranceAddOnFee63(rs.getInt(4));
					break;
				case 65:
					m0203.setCount65(rs.getInt(2));
					m0203.setIncomeAmount65(rs.getInt(3));
					m0203.setInsuranceAddOnFee65(rs.getInt(4));
					break;
				case 66:
					m0203.setCount66(rs.getInt(2));
					m0203.setIncomeAmount66(rs.getInt(3));
					m0203.setInsuranceAddOnFee66(rs.getInt(4));
					break;
				case 67:
					m0203.setCount67(rs.getInt(2));
					m0203.setIncomeAmount67(rs.getInt(3));
					m0203.setInsuranceAddOnFee67(rs.getInt(4));
					break;
				case 68:
					m0203.setCount68(rs.getInt(2));
					m0203.setIncomeAmount68(rs.getInt(3));
					m0203.setInsuranceAddOnFee68(rs.getInt(4));
					break;
				default:
					break;
				}
			
			}
		
		} catch (Exception e) {
			throw e;
		} finally {
			close(rs);
			close(conn);
		}
		return m0203;		
	}
	
	private InsuranceAddOnFeeBean makeInsuranceAddOnFee(ResultSet rs) throws Exception {
		InsuranceAddOnFeeBean insuranceAddOnFee = new InsuranceAddOnFeeBean();
		insuranceAddOnFee.setSerialNo(rs.getInt("serialNo"));
		insuranceAddOnFee.setRegcode(rs.getString("regcode"));
		insuranceAddOnFee.setUnicode(rs.getString("unicode"));
		insuranceAddOnFee.setName(rs.getString("name"));
		insuranceAddOnFee.setAddress(rs.getString("address")!=null?rs.getString("address"):"");
		insuranceAddOnFee.setIncomeType(rs.getString("incomeType"));
		try {
			insuranceAddOnFee.setIncomeDate(rs.getString("incomeDate")!=null?rs.getString("incomeDate").substring(0,10):"");
	    } catch (Exception e){}
		insuranceAddOnFee.setHealthInsuranceSalary(rs.getString("healthInsuranceSalary")!=null?rs.getString("healthInsuranceSalary"):"");
		insuranceAddOnFee.setIncomeAmount(rs.getString("incomeAmount")!=null?rs.getString("incomeAmount"):"");
		insuranceAddOnFee.setAccumulatedBonusAmount(rs.getString("accumulatedBonusAmount")!=null?rs.getString("accumulatedBonusAmount"):"");
		insuranceAddOnFee.setStockNote(rs.getString("stockNote")!=null?rs.getString("stockNote"):"");
		insuranceAddOnFee.setTrustNote(rs.getString("trustNote")!=null?rs.getString("trustNote"):"");
		insuranceAddOnFee.setICAAmount(rs.getString("ICAAmount")!=null?rs.getString("ICAAmount"):"");
		insuranceAddOnFee.setAnnualICAAmount(rs.getString("annualICAAmount")!=null?rs.getString("annualICAAmount"):"");
		try {
			insuranceAddOnFee.setExcludeDate(rs.getString("excludeDate")!=null?rs.getString("excludeDate").substring(0,10):"");
	    } catch (Exception e){}
		insuranceAddOnFee.setIsBoss(rs.getString("isBoss")!=null?rs.getString("isBoss"):"");
		insuranceAddOnFee.setBossHealthInsuranceAmount(rs.getString("bossHealthInsuranceAmount")!=null?rs.getString("bossHealthInsuranceAmount"):"");
		insuranceAddOnFee.setInsuranceAddOnFee(rs.getString("insuranceAddOnFee")!=null?rs.getString("insuranceAddOnFee"):"");
		insuranceAddOnFee.setDeclareNo(rs.getString("declareNo")!=null?rs.getString("declareNo"):"");
	    return insuranceAddOnFee;
	}
	
	private InsuranceAddOnFeeBean makeInsuranceAddOnFeeSummary(ResultSet rs) throws Exception {
		InsuranceAddOnFeeBean insuranceAddOnFee = new InsuranceAddOnFeeBean();
		insuranceAddOnFee.setUnicode(rs.getString("unicode"));
		insuranceAddOnFee.setName(rs.getString("name"));
		insuranceAddOnFee.setIncomeType(rs.getString("incomeType"));
		try {
			insuranceAddOnFee.setIncomeDate(rs.getString("incomeDate")!=null?rs.getString("incomeDate"):"");
	    } catch (Exception e){}
		insuranceAddOnFee.setIncomeAmount(rs.getString("incomeAmount")!=null?rs.getString("incomeAmount"):"");
		insuranceAddOnFee.setInsuranceAddOnFee(rs.getString("insuranceAddOnFee")!=null?rs.getString("insuranceAddOnFee"):"");
	    return insuranceAddOnFee;
	}
	
	private InsuranceAddOnFeeBean makeM0305Item(ResultSet rs) throws Exception {
		InsuranceAddOnFeeBean insuranceAddOnFee = new InsuranceAddOnFeeBean();
		insuranceAddOnFee.setIncomeType(rs.getString("incomeType"));
		insuranceAddOnFee.setTrustNote(rs.getString("trustNote")!=null?rs.getString("trustNote"):"");
		try {
			String incomeDate = rs.getString("incomeDate");
			insuranceAddOnFee.setIncomeDate((Integer.parseInt(incomeDate.substring(0, 4))-1911) + "/" + incomeDate.substring(5) );
	    } catch (Exception e){}
		insuranceAddOnFee.setInsuranceAddOnFee(rs.getString("insuranceAddOnFee")!=null?rs.getString("insuranceAddOnFee"):"");
	    return insuranceAddOnFee;
	}

}
