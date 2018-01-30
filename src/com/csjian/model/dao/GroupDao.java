package com.csjian.model.dao;

import com.csjian.model.bean.GroupBean;
import com.csjian.model.dao.GroupDao;

import java.sql.*;
import java.util.*;

public class GroupDao extends TemplateDao {
  
  private GroupBean makeGroup(ResultSet rs) throws Exception {
  	Connection conn = null;
  	GroupBean group = new GroupBean();
  	try {
  		conn = this.dataSource.getConnection();
	  	group.setName(rs.getString("name")!=null?rs.getString("name"):"");
	  	group.setGroupno(rs.getString("groupno"));
	  	
	    Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
	  	ResultSet rs0 = stmt.executeQuery("SELECT apno FROM GroupDetails WHERE groupno='" + rs.getString("groupno") + "'");
	  	Collection apnolist = new ArrayList();
	  	while(rs0.next()) {
	  		apnolist.add(rs0.getString("apno"));
	  	}
	    group.setApnolist((String[])apnolist.toArray(new String[0]));
	    
	    stmt.close();
  	} catch (Exception e) {  		
  	} finally {
  		close(conn);
  	}
  	return group;
  }
  
  public GroupBean getGroup(String groupno) throws Exception {
  	Connection conn = null;
  	ResultSet rs = null;
  	try {
  	  conn = this.dataSource.getConnection();
  	  Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
  	  rs = stmt.executeQuery("SELECT * FROM UserGroups WHERE groupno='" + groupno + "'");
  	  if (rs.next()) {
  		  return makeGroup(rs);
  	  } else {
  		  throw new Exception("Could not find group#" + groupno);
  	  }
  	} catch (Exception e) {
  		throw e;
  	} finally {
  		close(rs);
  		close(conn);
  	}
  }
  
  public GroupBean getGroupAt(int index) throws Exception {
  	Connection conn = null;
  	ResultSet rs = null;
  	try {
  	  conn = this.dataSource.getConnection();
  	  Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
  	  rs = stmt.executeQuery("SELECT * FROM UserGroups ORDER BY groupno");
  	  if (rs.absolute(index)) {
  		  return makeGroup(rs);
  	  } else {
  		  throw new Exception("Could not find group in index#" + index);
  	  }
  	} catch (Exception e) {
  		throw e;
  	} finally {
  		close(rs);
  		close(conn);
  	}
  }
  
  public GroupBean[] getGroups() throws Exception {
	  Connection conn = null;
	  ResultSet rs = null;
	  Collection groups = new ArrayList();
	  try {
		  conn = this.dataSource.getConnection();
		  Statement stmt = conn.createStatement();
		  rs = stmt.executeQuery("SELECT * FROM UserGroups ORDER BY groupno");
		  while (rs.next()) {
			  groups.add(makeGroup(rs));
		  }
		  return (GroupBean[]) groups.toArray(new GroupBean[0]);
	  } catch (Exception e) {
		  throw e;
	  } finally {
		  close(rs);
		  close(conn);
	  }
  }
  
  public List<String[]> findApList(String groupno, String category) {
  	Connection conn = null;
  	ResultSet rs = null;
  	List<String[]> aplist = new ArrayList<String[]>(); 
  	try {
  	  conn = this.dataSource.getConnection();
  	  Statement stmt = conn.createStatement();
  	  rs = stmt.executeQuery("SELECT a.mvc, a.apno, a.name FROM APs AS a, GroupDetails AS b WHERE a.apno=b.apno AND a.category='" + category + "' AND b.groupno='" + groupno + "'");
      while (rs.next()) {
      	String[] ap = {rs.getString(2), rs.getString(1)!=null?rs.getString(1):"", rs.getString(3)};
      	aplist.add(ap);
      }  	  
  	} catch (Exception e) {  		
  	} finally {
  		close(rs);
  		close(conn);
  	}
  	return aplist;
  }
  
  public List<String[]> findApList() {
  	Connection conn = null;
  	ResultSet rs = null;
  	List<String[]> aplist = new ArrayList<String[]>(); 
  	try {
  	  conn = this.dataSource.getConnection();
  	  Statement stmt = conn.createStatement();
  	  rs = stmt.executeQuery("SELECT apno, name FROM APs order by category, apno");
      while (rs.next()) {
      	String[] ap = {rs.getString(1), rs.getString(2)};
      	aplist.add(ap);
      }  	  
  	} catch (Exception e) {  		
  	} finally {
  		close(rs);
  		close(conn);
  	}
  	return aplist;
  }
  
  public void update(GroupBean group) throws Exception {
  	Connection conn = null;
  	try {
  	  conn = this.dataSource.getConnection();
  	  Statement stmt = conn.createStatement();
  	  stmt.executeUpdate("UPDATE UserGroups SET name='" + group.getName() + "' WHERE groupno='" + group.getGroupno() + "'");
  	  stmt.executeUpdate("DELETE FROM GroupDetails WHERE groupno='" + group.getGroupno() + "'");
    
  	  PreparedStatement pstmt = conn.prepareStatement("INSERT INTO GroupDetails(groupno, apno) VALUES(?, ?)");
  	  pstmt.setString(1, group.getGroupno());
  	  String[] apnolist = group.getApnolist();
  	  if (apnolist != null) {
  	    for (int i=0; i<apnolist.length; i++) {
  		    pstmt.setString(2, apnolist[i]);
  		    pstmt.executeUpdate();
  	    }
  	  }
      stmt.close();
      pstmt.close();
  	} catch (Exception e) {
  		throw e;
  	} finally {
  		close(conn);
  	}
  }
  
  public void insert(GroupBean group) throws Exception {
  	Connection conn = null;
  	ResultSet rs = null;
  	try {
  	  conn = this.dataSource.getConnection();
  	  Statement stmt = conn.createStatement();
  	  rs = stmt.executeQuery("SELECT * FROM UserGroups WHERE groupno='" + group.getGroupno() + "'");
  	  if (!rs.next()) {
  	    stmt.executeUpdate("INSERT INTO UserGroups(groupno, name) values('" + group.getGroupno() + "', '" + group.getName() + "')");
 		    PreparedStatement pstmt = conn.prepareStatement("INSERT INTO GroupDetails(groupno, apno) VALUES(?, ?)");
   	    pstmt.setString(1, group.getGroupno());
   	    String[] apnolist = group.getApnolist();
   	    for (int i=0; i<apnolist.length; i++) {
   		    pstmt.setString(2, apnolist[i]);
   		    pstmt.executeUpdate();
   	    }
        stmt.close();
        pstmt.close();
 	    }
  	} catch (Exception e) {
  		throw e;
  	} finally {
  		close(rs);
  		close(conn);
  	}
  }
   
  public void remove(String groupno) throws Exception {
  	Connection conn = null;
  	try {
  	  conn = this.dataSource.getConnection();
  	  Statement stmt = conn.createStatement();
  	  stmt.executeUpdate("DELETE FROM UserGroups WHERE groupno = '" + groupno + "'");
      stmt.close();
  	} catch (Exception e) {
  		throw e;
  	} finally {
  		close(conn);
  	}
  }
  
}
