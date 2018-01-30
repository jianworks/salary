package com.csjian.model.dao;

import javax.sql.*;
import javax.naming.*;
import java.sql.*;
import java.util.*;

import com.csjian.model.bean.DependantBean;
import com.csjian.model.bean.EmployeeBean;

public class InsuranceDao extends TemplateDao {
	public static String[][] relations = {{"1", "配偶"}, {"2", "父母"}, {"3", "子女"}, {"4", "祖父母"}, {"5", "孫子女"}, {"6", "外祖父母"}, {"7", "外孫子女"}, {"8", "曾祖父母"}, {"9", "外曾父母"}};

	
  private EmployeeBean makeEmployee(ResultSet rs) throws Exception {
    EmployeeBean employee = new EmployeeBean();
    employee.setEmployeeno(rs.getString("employeeno")!=null?rs.getString("employeeno"):"");
    employee.setRegcode(rs.getString("regcode")!=null?rs.getString("regcode"):"");
    employee.setAyear(rs.getString("ayear")!=null?rs.getString("ayear"):"");
    employee.setIsnative(rs.getString("isnative")!=null?rs.getString("isnative"):"Y");
    employee.setUnicode(rs.getString("unicode")!=null?rs.getString("unicode"):"");
    //employee.setPassport(rs.getString("passport")!=null?rs.getString("passport"):"");
    employee.setName(rs.getString("name")!=null?rs.getString("name"):"");
    employee.setAddress(rs.getString("address")!=null?rs.getString("address"):"");
    try {
    	employee.setOnboarddate(rs.getString("onboarddate")!=null?rs.getString("onboarddate").substring(0,10):"");
    } catch (Exception e){}
    employee.setAccountno(rs.getString("accountno")!=null?rs.getString("accountno"):"");
    employee.setIsresign(rs.getString("isresign")!=null?rs.getString("isresign"):"N");
    try {
    	employee.setResigndate(rs.getString("resigndate")!=null?rs.getString("resigndate").substring(0,10):"");
    } catch (Exception e){}
    employee.setGovinsurance(rs.getString("govinsurance")!=null?rs.getString("govinsurance"):"");
    employee.setRetirefee(rs.getString("retirefee")!=null?rs.getString("retirefee"):"");
    employee.setTitle(rs.getString("title")!=null?rs.getString("title"):"");
    try {
    	employee.setBirthday(rs.getString("birthday")!=null?rs.getString("birthday").substring(0,10):"");
  	} catch (Exception e){}
    return employee;
  }

  public EmployeeBean getEmployee(String regcode, String employeeno, String ayear) throws Exception {
    Connection conn = null;
  	ResultSet rs = null;
    try {
      conn = this.dataSource.getConnection();
      PreparedStatement getStmt = conn.prepareStatement("SELECT * FROM Employees WHERE regcode=? AND employeeno = ? AND ayear=?");
      synchronized (getStmt) {
        getStmt.clearParameters();
        getStmt.setString(1, regcode);
        getStmt.setString(2, employeeno);
        getStmt.setString(3, ayear);
        rs = getStmt.executeQuery();
        if (rs.next()) {
        	return makeEmployee(rs);
        } else {
        	throw new Exception("Could not find employee#" + regcode + " - " + employeeno);
        }
      }
    } catch (Exception e) {
      throw e;
    } finally {
    	close(rs);
  		close(conn);
    }
  }

  public EmployeeBean[] getEmployees(String regcode, String ayear) {
    Connection conn = null;
  	ResultSet rs = null;
    Collection employees = new ArrayList();
    try {
      conn = this.dataSource.getConnection();
      PreparedStatement getAllStmt = conn.prepareStatement("SELECT * FROM Employees WHERE regcode=? AND ayear=? ORDER BY isresign, employeeno");
      synchronized(getAllStmt) {
        getAllStmt.setString(1, regcode);
        getAllStmt.setString(2, ayear);
        rs = getAllStmt.executeQuery();
      }
      while (rs.next()) {
        employees.add(makeEmployee(rs));
      }      
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
    	close(rs);
  		close(conn);
    }
    return (EmployeeBean[]) employees.toArray(new EmployeeBean[0]);
  }

  public int addInsurance(String regcode, String ayear, Vector people) throws Exception {
    Connection conn = null;
  	int fetchs = 0;
  	try {
      conn = this.dataSource.getConnection();
      PreparedStatement chkStmt = conn.prepareStatement("SELECT * from Dependants WHERE regcode=? AND employeeno=? AND ayear=? AND unicode=?");
      PreparedStatement updStmt = conn.prepareStatement("UPDATE Dependants SET relation=?, name=?, birthday=?, moddate=getdate() WHERE regcode=? AND employeeno=? AND ayear=? AND unicode=?");
      PreparedStatement insStmt = conn.prepareStatement("INSERT INTO Dependants(relation, name, birthday, moddate, regcode, employeeno, ayear, unicode) VALUES(?, ?, ?, getdate(), ?, ?, ?, ?)");
      PreparedStatement empStmt = conn.prepareStatement("UPDATE Employees SET govinsurance=?, birthday=? WHERE regcode=? AND employeeno=? AND ayear=?");

      ResultSet rs = null;
      String[] person = null; // person {員工編號,是否眷屬,姓名,身份證號,出生日期,加保金額,稱謂,到職日}
      for (int i=0; i<people.size(); i++) {
      	person = (String[])people.elementAt(i);
      	if (person[1].equals("N")) {
      		empStmt.clearParameters();
      		empStmt.setString(1, person[5]);
      		empStmt.setString(2, person[4]);
      		empStmt.setString(3, regcode);
      		empStmt.setString(4, person[0]);
      		empStmt.setString(5, ayear);
      		fetchs += empStmt.executeUpdate();      		
      	} else {
      		chkStmt.setString(1, regcode);
          chkStmt.setString(2, person[0]);
          chkStmt.setString(3, ayear);
          chkStmt.setString(4, person[3]);
          rs = chkStmt.executeQuery();
          if (rs.next()) {
          	updStmt.clearParameters();
            updStmt.setString(1, person[6]);
            updStmt.setString(2, person[2]);
            updStmt.setString(3, person[4]);
            updStmt.setString(4, regcode);
            updStmt.setString(5, person[0]);
            updStmt.setString(6, ayear);
            updStmt.setString(7, person[3]);

            updStmt.executeUpdate();
          } else {
          	insStmt.clearParameters();
            insStmt.setString(1, person[6]);
            insStmt.setString(2, person[2]);
            insStmt.setString(3, person[4]);
            insStmt.setString(4, regcode);
            insStmt.setString(5, person[0]);
            insStmt.setString(6, ayear);
            insStmt.setString(7, person[3]);

            insStmt.executeUpdate();
          }
      	}      	
      }
    } catch (Exception e){
      e.printStackTrace();
      return (-1);
    } finally {
  		close(conn);
    }
    return fetchs;
  }
  
  public int adjustInsurance(String regcode, String ayear, Vector people) throws Exception {
    Connection conn = null;
  	int fetchs = 0;
  	try {
      conn = this.dataSource.getConnection();
      PreparedStatement empStmt = conn.prepareStatement("UPDATE Employees SET govinsurance=?, birthday=? WHERE regcode=? AND employeeno=? AND ayear=?");

      ResultSet rs = null;
      String[] person = null; // person {員工編號,是否眷屬,姓名,身份證號,出生日期,加保金額,稱謂,到職日}
      for (int i=0; i<people.size(); i++) {
      	person = (String[])people.elementAt(i);
     		empStmt.clearParameters();
     		empStmt.setString(1, person[5]);
     		empStmt.setString(2, person[3]);
     		empStmt.setString(3, regcode);
     		empStmt.setString(4, person[0]);
     		empStmt.setString(5, ayear);
     		fetchs += empStmt.executeUpdate();      		
      }
    } catch (Exception e){
      e.printStackTrace();
      return (-1);
    } finally {
  		close(conn);
    }
    return fetchs;
  }
  
  public int removeInsurance(String regcode, String ayear, Vector people) throws Exception {
    Connection conn = null;
  	int fetchs = 0;
  	try {
      conn = this.dataSource.getConnection();
      PreparedStatement updStmt = conn.prepareStatement("DELETE FROM Dependants WHERE regcode=? AND employeeno=? AND ayear=? AND unicode=?");
      
      String[] person = null; // person {員工編號,是否眷屬,姓名,身份證號,員工姓名,員工身份證號,員工出生日期}
      for (int i=0; i<people.size(); i++) {
      	person = (String[])people.elementAt(i);
      	if (person[1].equals("Y")) {
      		updStmt.clearParameters();
          updStmt.setString(1, regcode);
          updStmt.setString(2, person[0]);
          updStmt.setString(3, ayear);
          updStmt.setString(4, person[3]);
          fetchs += updStmt.executeUpdate();
      	}      	
      }
    } catch (Exception e){
      e.printStackTrace();
      return (-1);
    } finally {
  		close(conn);
    }
    return fetchs;
  }
}
