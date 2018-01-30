package com.csjian.model.dao;

import javax.sql.*;
import javax.naming.*;

import com.csjian.model.bean.CompanyBean;
import com.csjian.model.dao.CompanyDao;
import com.csjian.util.StringUtils;

import java.sql.*;
import java.util.*;

public class CompanyDao extends TemplateDao {
  private CompanyBean makeCompany(ResultSet rs) throws Exception {
  	CompanyBean company = new CompanyBean();
  	company.setRegcode(rs.getString("regcode")!=null?rs.getString("regcode"):"");
  	company.setName(rs.getString("name")!=null?rs.getString("name"):"");
  	company.setRegname(rs.getString("regname")!=null?rs.getString("regname"):"");
  	company.setPassword(rs.getString("password")!=null?rs.getString("password"):"");
  	company.setContact(rs.getString("contact")!=null?rs.getString("contact"):"");
  	company.setEmail(rs.getString("email")!=null?rs.getString("email"):"");
  	company.setPhone(rs.getString("phone")!=null?rs.getString("phone"):"");
  	company.setGroupno(rs.getString("groupno"));
  	company.setDisable(rs.getString("disable"));
  	company.setAgent(rs.getString("agent")!=null?rs.getString("agent"):"");
    company.setLaborCode(rs.getString("laborcode")!=null?rs.getString("laborcode"):"");
    company.setHealthCode(rs.getString("healthcode")!=null?rs.getString("healthcode"):"");
    company.setZip(rs.getString("zip")!=null?rs.getString("zip"):"");
    company.setAddress(rs.getString("address")!=null?rs.getString("address"):"");
    company.setBossName(rs.getString("bossname")!=null?rs.getString("bossname"):"");
    company.setBossId(rs.getString("bossid")!=null?rs.getString("bossid"):"");
    company.setBossIsNative(rs.getString("bossIsNative")!=null?rs.getString("bossIsNative"):"Y");
    company.setSalaryShift(rs.getInt("salaryShift"));
  	return company;
  }
  
  public CompanyBean login(String regcode, String password) {
  	ResultSet rs = null;
  	Connection conn = null;
  	CompanyBean company = null;
  	try {
  	  conn = this.dataSource.getConnection();
  	  PreparedStatement getStmt = conn.prepareStatement("SELECT * FROM Companies WHERE regcode=? and password=?");
  	  synchronized (getStmt) {
  	 	  getStmt.clearParameters();
  		  getStmt.setString(1, regcode);
  		  getStmt.setString(2, com.csjian.util.StringUtils.getMD5String(password));
  		  rs = getStmt.executeQuery();
  		  if (rs.next()) {
  			  company = makeCompany(rs);
  			  rs.close();
  			  Statement stmt = conn.createStatement();
  	      stmt.executeUpdate("DELETE FROM LogHistory WHERE regcode='" + regcode + "'");
  	      stmt.executeUpdate("INSERT INTO LogHistory(regcode, logdate) VALUES('" + regcode + "', getdate())");
  	      stmt.executeUpdate("INSERT INTO LoginLogs(regcode, logdate) VALUES('" + regcode + "', getdate())");
  		  } 
  	  }
  	} catch (Exception e) {  
  		e.printStackTrace();
  	} finally {
  		close(rs);
  		close(conn);
  	}
  	return company;
  }

  public CompanyBean getCompany(String regcode) throws Exception {
  	ResultSet rs = null;
  	Connection conn = null;
  	try {
  	  conn = this.dataSource.getConnection();
  	  PreparedStatement getStmt = conn.prepareStatement("SELECT * FROM Companies WHERE regcode=?");
  	  synchronized (getStmt) {
  	 	  getStmt.clearParameters();
  		  getStmt.setString(1, regcode);
  		  rs = getStmt.executeQuery();
  		  if (rs.next()) {
  			  return makeCompany(rs);
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
  }

  public CompanyBean[] getCompanies(String agent, int start, int end) throws Exception {
  	Connection conn = null;
  	ResultSet rs = null;
  	Collection companies = new ArrayList();
  	try {
  	  conn = this.dataSource.getConnection();
  	  String query = "";
  	  query = "SELECT * FROM Companies WHERE regcode<>'admin' ";
  	  if (agent==null||agent.equals("")) {
  	  	query += " AND groupno='customer' ORDER BY regcode";
  	  } else if (agent.equals("agent")){
  	  	query += " AND groupno='agent' ORDER BY regcode";
  	  } else if (agent.equals("admin")){
  	  	query += " AND groupno='customer' AND agent is null ORDER BY regcode";
  	  } else {
  	  	query += " AND agent='" + agent + "' ORDER BY regcode";
  	  }
      Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
  		rs = stmt.executeQuery(query);

  		if (rs.next()) {
  	    rs.last();
  	    end = end<rs.getRow()?end:rs.getRow();
      	for (int i=start; i<=end; i++) {
  	      rs.absolute(i);
  	      companies.add(makeCompany(rs));
  	    }
      }

  	  return (CompanyBean[]) companies.toArray(new CompanyBean[0]);
  	} catch (Exception e) {
  	  throw e;
  	} finally {
  		close(rs);
  		close(conn);
  	}
  }

  public CompanyBean[] getActiveCompanies(String agent) throws Exception {
  	Connection conn = null;
  	ResultSet rs = null;
  	Collection companies = new ArrayList();
  	try {
  	  conn = this.dataSource.getConnection();
  	  String query = "";
  	  query = "SELECT * FROM Companies WHERE regcode<>'admin' ";
  	  if (agent==null||agent.equals("")) {
  	  	query += " AND groupno='customer' AND regcode in (SELECT regcode FROM LogHistory) ORDER BY regcode";
  	  } else if (agent.equals("agent")){
  	  	query += " AND groupno='agent' AND regcode in (SELECT regcode FROM LogHistory) ORDER BY regcode";
  	  } else if (agent.equals("admin")){
  	  	query += " AND groupno='customer' AND agent is null AND regcode in (SELECT regcode FROM LogHistory) ORDER BY regcode";
  	  } else {
  	  	query += " AND agent='" + agent + "' AND regcode in (SELECT regcode FROM LogHistory) ORDER BY regcode";
  	  }      Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
  		rs = stmt.executeQuery(query);

  		while (rs.next()) {
 	      companies.add(makeCompany(rs));
      }

  	  return (CompanyBean[]) companies.toArray(new CompanyBean[0]);
  	} catch (Exception e) {
  	  throw e;
  	} finally {
  		close(rs);
  		close(conn);
  	}
  }
  
  public List FindCompany(String agent, String keyword, String year, int start, int end) throws Exception {
  	Connection conn = null;
  	ResultSet rs = null;
  	List companies = new ArrayList();
  	try {
  	  conn = this.dataSource.getConnection();
  	  String query = "";
  	  query = "SELECT * FROM Companies WHERE regcode<>'admin' ";
  	  if (agent==null||agent.equals("")) {
  	  	query += " AND groupno='customer' ";
  	  } else if (agent.equals("agent")){
  	  	query += " AND groupno='agent' ";
  	  } else if (agent.equals("admin")){
  	  	query += " AND groupno='customer' AND agent is null ";
  	  } else {
  	  	query += " AND agent='" + agent + "' ";
  	  }      
  	  if (keyword !=null && !keyword.equals("")) {
  	  	query += " AND (regcode like '%" + keyword + "%' or name like '%" + keyword + "%' or regname like '%" + keyword + "%') ";
  	  }
  	  /*
  	  if (year!=null && !year.equals("")) {
  	  	query += " AND regcode in (SELECT regcode FROM loginlog where year(logdate)='" + year + "') ";
  	  } */
  	  query += " ORDER BY regcode";
  	  Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
  		rs = stmt.executeQuery(query);

  		if (rs.next()) {
  	    rs.last();
  	    end = end<rs.getRow()?end:rs.getRow();
      	for (int i=start; i<=end; i++) {
  	      rs.absolute(i);
  	      companies.add(makeCompany(rs));
  	    }
      }
  		return companies;
  	} catch (Exception e) {
  	  throw e;
  	} finally {
  		close(rs);
  		close(conn);
  	}
  }
  
  public int CountCompany(String agent, String keyword, String year) throws Exception {
  	Connection conn = null;
  	ResultSet rs = null;
  	try {
  	  conn = this.dataSource.getConnection();
  	  String query = "";
  	  query = "SELECT count(*) FROM Companies WHERE regcode<>'admin' ";
  	  if (agent==null||agent.equals("")) {
  	  	query += " AND groupno='customer' ";
  	  } else if (agent.equals("agent")){
  	  	query += " AND groupno='agent' ";
  	  } else if (agent.equals("admin")){
  	  	query += " AND groupno='customer' AND agent is null ";
  	  } else {
  	  	query += " AND agent='" + agent + "' ";
  	  }     
  	  if (keyword !=null && !keyword.equals("")) {
  	  	query += " AND (regcode like '%" + keyword + "%' or name like '%" + keyword + "%' or regname like '%" + keyword + "%') ";
  	  }
  	  /*
  	  if (year!=null && !year.equals("")) {
  	  	query += " AND regcode in (SELECT regcode FROM loginlog where year(logdate)='" + year + "') ";
  	  }*/
  	  
  	  Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
  		rs = stmt.executeQuery(query);

  		if (rs.next()) return rs.getInt(1);
  		else return 0;
  	} catch (Exception e) {
  	  throw e;
  	} finally {
  		close(rs);
  		close(conn);
  	}
  }
  
  public CompanyBean[] getAllCompanies(String agent) throws Exception {
  	Connection conn = null;
  	ResultSet rs = null;
  	Collection companies = new ArrayList();
  	try {
  	  conn = this.dataSource.getConnection();
  	  String query = "";
  	  query = "SELECT * FROM Companies WHERE regcode<>'admin' ";
  	  if (agent==null||agent.equals("")) {
  	  	query += " AND groupno='customer' ORDER BY regcode";
  	  } else if (agent.equals("agent")){
  	  	query += " AND groupno='agent' ORDER BY regcode";
  	  } else if (agent.equals("admin")){
  	  	query += " AND groupno='customer' AND agent is null ORDER BY regcode";
  	  } else {
  	  	query += " AND agent='" + agent + "' ORDER BY regcode";
  	  }      Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
  		rs = stmt.executeQuery(query);

  		while (rs.next()) {
 	      companies.add(makeCompany(rs));
      }

  	  return (CompanyBean[]) companies.toArray(new CompanyBean[0]);
  	} catch (Exception e) {
  	  throw e;
  	} finally {
  		close(rs);
  		close(conn);
  	}
  }

  public int allSize(String agent) throws Exception {
  	Connection conn = null;
  	ResultSet rs = null;
  	try {
  	  conn = this.dataSource.getConnection();
  	  Statement stmt = conn.createStatement();
  	  String query = "";
  	  query = "SELECT count(*) FROM Companies WHERE regcode<>'admin' ";
  	  if (agent==null||agent.equals("")) {
  	  	query += " AND groupno='customer'";
  	  } else if (agent.equals("agent")){
  	  	query += " AND groupno='agent'";
  	  } else if (agent.equals("admin")){
  	  	query += " AND groupno='customer' AND agent is null";
  	  } else {
  	  	query += " AND agent='" + agent + "'";
  	  }
  	  rs = stmt.executeQuery(query);
  	  if (rs.next()) return (rs.getInt(1));
  	  else return 0;
  	} catch (Exception e) {
  	  throw e;
  	} finally {
  		close(rs);
  		close(conn);
  	}
  }
  
  public int activeSize(String agent) throws Exception {
  	Connection conn = null;
  	ResultSet rs = null;
  	try {
  	  conn = this.dataSource.getConnection();
  	  Statement stmt = conn.createStatement();
  	  String query = "";
  	  query = "SELECT count(*) FROM Companies WHERE regcode<>'admin' ";
  	  if (agent==null||agent.equals("")) {
  	  	query += " AND groupno='customer'";
  	  } else if (agent.equals("agent")){
  	  	query += " AND groupno='agent'";
  	  } else if (agent.equals("admin")){
  	  	query += " AND groupno='customer' AND agent is null";
  	  } else {
  	  	query += " AND agent='" + agent + "'";
  	  }
  	  query += " AND regcode in (SELECT regcode FROM LogHistory)";
  	  rs = stmt.executeQuery(query);
  	  if (rs.next()) return (rs.getInt(1));
  	  else return 0;
  	} catch (Exception e) {
  	  throw e;
  	} finally {
  		close(rs);
  		close(conn);
  	}
  }

  public int size(String agent) throws Exception {
  	Connection conn = null;
  	ResultSet rs = null;
  	try {
  	  conn = this.dataSource.getConnection();
  	  Statement stmt = conn.createStatement();
  	  String query = "";
  	  query = "SELECT count(*) FROM Companies WHERE regcode<>'admin' ";
  	  if (agent==null||agent.equals("")) {
  	  	query += " AND groupno='customer'";
  	  } else if (agent.equals("agent")){
  	  	query += " AND groupno='agent'";
  	  } else if (agent.equals("admin")){
  	  	query += " AND groupno='customer' AND agent is null";
  	  } else {
  	  	query += " AND agent='" + agent + "'";
  	  }
  	  rs = stmt.executeQuery(query);
  	  if (rs.next()) return (rs.getInt(1));
  	  else return 0;
  	} catch (Exception e) {
  	  throw e;
  	} finally {
  		close(rs);
  		close(conn);
  	}
  }

  public Vector getAgents() throws Exception {
  	Connection conn = null;
  	ResultSet rs = null;
  	Vector agents = new Vector();
  	try {
  	  conn = this.dataSource.getConnection();
  	  String query = "SELECT regcode, name FROM Companies WHERE groupno='agent' ORDER BY regcode";
      Statement stmt = conn.createStatement();
  		rs = stmt.executeQuery(query);

  	  while (rs.next()) {
  		  String[] item = {rs.getString(1), rs.getString(2)};
  		  agents.add(item);
  	  }
  	  return (agents);
  	} catch (Exception e) {
  	  throw e;
  	} finally {
  		close(rs);
  		close(conn);
  	}
  }

  public void update(CompanyBean company) throws Exception {
  	Connection conn = null;
  	try {
  	  conn = this.dataSource.getConnection();
  	  PreparedStatement updStmt = conn.prepareStatement("UPDATE Companies SET name=?, regname=?, contact=?, email=?, phone=?, groupno=?, disable=?, agent=? WHERE regcode=?");
  	  synchronized(updStmt) {
  		  updStmt.clearParameters();
  		  // updStmt.setString(1, member.getPassword()); update 的時候不用更新 password
  		  updStmt.setString(1, company.getName());
  		  updStmt.setString(2, company.getRegname());
  		  updStmt.setString(3, company.getContact());
  		  updStmt.setString(4, company.getEmail());
  		  updStmt.setString(5, company.getPhone());
  		  updStmt.setString(6, company.getGroupno());
  		  updStmt.setString(7, company.getDisable());
  		  updStmt.setString(8, company.getAgent());
  		  updStmt.setString(9, company.getRegcode());

  		  updStmt.executeUpdate();
  	  }
  	} catch (Exception e){
  		throw e;
  	} finally {
  		close(conn);
  	}
  }

  public int updateInfo(CompanyBean company) {
    Connection conn = null;
  	int fetchs = 0;
    try {
      conn = this.dataSource.getConnection();
      PreparedStatement updStmt = conn.prepareStatement("UPDATE Companies SET regname=?, phone=?, laborcode=?, healthcode=?, zip=?, address=?, bossname=?, bossid=?, bossIsNative=? WHERE regcode=?");
      synchronized(updStmt) {
        updStmt.clearParameters();
		  	updStmt.setString(1, company.getRegname());
		  	updStmt.setString(2, company.getPhone());
		  	updStmt.setString(3, company.getLaborCode());
		  	updStmt.setString(4, company.getHealthCode());
		  	updStmt.setString(5, company.getZip());
		  	updStmt.setString(6, company.getAddress());
		  	updStmt.setString(7, company.getBossName());
		  	updStmt.setString(8, company.getBossId());
		  	updStmt.setString(9, company.getBossIsNative());
		  	updStmt.setString(10, company.getRegcode());
		
		  	fetchs = updStmt.executeUpdate();
      }
    } catch (Exception e){
      e.printStackTrace ();
    } finally {
  		close(conn);
    }
    return fetchs;
  }

  public int insert(CompanyBean company) throws Exception {
  	Connection conn = null;
  	int fetchs = 0;
  	try {
  	  conn = this.dataSource.getConnection();
  	  Statement stmt = conn.createStatement();
  	  ResultSet rs = stmt.executeQuery("select * from Companies where regcode='" + company.getRegcode() + "'");
  	  if (rs.next()) {
  	  	return (-1);
  	  }
  	  PreparedStatement putStmt = conn.prepareStatement("INSERT INTO Companies(regcode, password, name, regname, contact, email, phone, groupno, disable, agent) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
  	  synchronized(putStmt) {
  		  putStmt.clearParameters();
  		  putStmt.setString(1, company.getRegcode());
  		  putStmt.setString(2, com.csjian.util.StringUtils.getMD5String(company.getRegcode()));
  		  putStmt.setString(3, company.getName());
  		  putStmt.setString(4, company.getRegname());
  		  putStmt.setString(5, company.getContact());
  		  putStmt.setString(6, company.getEmail());
  		  putStmt.setString(7, company.getPhone());
  		  putStmt.setString(8, company.getGroupno());
  		  putStmt.setString(9, company.getDisable());
  		  putStmt.setString(10, company.getAgent());

  		  fetchs = putStmt.executeUpdate();
  	  }
  	} catch (Exception e) {
  		e.printStackTrace();
  	} finally {
  		close(conn);
  	}
  	return fetchs;
  }

  public boolean updatePass(String regcode, String oldpass, String newpass) throws Exception {
  	Connection conn = null;
  	ResultSet rs;
  	try {
  	  conn = this.dataSource.getConnection();
      PreparedStatement chkPassStmt = conn.prepareStatement("SELECT * FROM Companies WHERE regcode=? and password=?");
      PreparedStatement updPassStmt = conn.prepareStatement("UPDATE Companies SET password=? WHERE regcode=?");
  	  synchronized(chkPassStmt) {
  		  chkPassStmt.clearParameters();
  		  chkPassStmt.setString(1, regcode);
  		  chkPassStmt.setString(2, com.csjian.util.StringUtils.getMD5String(oldpass));
  	    rs = chkPassStmt.executeQuery();
  	    if (rs.next()) {
  	  	  synchronized(updPassStmt) {
  	  	    updPassStmt.clearParameters();
  	  	    updPassStmt.setString(2, regcode);
  	  	    updPassStmt.setString(1, com.csjian.util.StringUtils.getMD5String(newpass));
  	  	    updPassStmt.executeUpdate();
  	  	    return true;
  	  	  }
  	    } else {
  	  	  return false;
  	    }
  	  }
  	} catch (Exception e) {
  		throw e;
  	} finally {
  		close(conn);
  	}
  }

  public boolean resetPass(String regcode) throws Exception {
  	Connection conn = null;
  	ResultSet rs;
  	try {
  	  conn = this.dataSource.getConnection();
      PreparedStatement updPassStmt = conn.prepareStatement("UPDATE Companies SET password=? WHERE regcode=?");
  	  synchronized(updPassStmt) {
  	    updPassStmt.clearParameters();
  	    updPassStmt.setString(2, regcode);
  	    updPassStmt.setString(1, com.csjian.util.StringUtils.getMD5String(regcode));
  	    updPassStmt.executeUpdate();
  	    return true;
  	  }
  	} catch (Exception e) {
  		throw e;
  	} finally {
  		close(conn);
  	}
  }

  public void remove(String regcode) throws Exception {
  	Connection conn = null;
  	try {
  	  conn = this.dataSource.getConnection();
  	  PreparedStatement remStmt = conn.prepareStatement("DELETE FROM Companies WHERE regcode=?");
  	  synchronized(remStmt) {
  		  remStmt.clearParameters();
  		  remStmt.setString(1, regcode);
  		  remStmt.executeUpdate();
  	  }
  	} catch (Exception e) {
  		throw e;
  	} finally {
  		close(conn);
  	}
  }
  
  public Vector loginLog(String startdate, String enddate, String agent) throws Exception {
  	Connection conn = null;
  	ResultSet rs = null;
  	Vector logs = new Vector();
  	try {
  	  conn = this.dataSource.getConnection();
  	  String query = "SELECT a.name, b.logdate FROM Companies AS a, LogHistory AS b WHERE a.regcode=b.regcode ";
      if (startdate!=null&&!startdate.equals("")) query += " AND b.logdate>='" + startdate + "' ";
      if (enddate!=null&&!enddate.equals("")) query += " AND b.logdate<='" + enddate + "' ";
      if (agent==null||agent.equals("")) {
    	  query += " AND a.groupno='customer' ";
    	} else if (agent.equals("agent")){
    	  query += " AND a.groupno='agent' ";
    	} else if (agent.equals("admin")){
    	  query += " AND a.groupno='customer' AND a.agent is null ";
    	} else {
    	  query += " AND a.agent='" + agent + "' ";
    	}

      query += "ORDER BY b.logdate DESC";

      Statement stmt = conn.createStatement();
  		rs = stmt.executeQuery(query);

  	  while (rs.next()) {
  	  	String[] log = {rs.getString(1), rs.getString(2).substring(0,10)};
        logs.add(log);
  	  }
  	  return (logs);
  	} catch (Exception e) {
  		e.printStackTrace();
  	  throw e;
  	} finally {
  		close(rs);
  		close(conn);
  	}
  }
}
