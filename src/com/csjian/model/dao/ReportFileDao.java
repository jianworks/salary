package com.csjian.model.dao;

import com.csjian.model.bean.ReportFileBean;
import java.sql.*;
import java.util.*;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;


public class ReportFileDao extends TemplateDao {
    
	public void RetrieveFile(int seqNo, OutputStream stream) throws Exception {
		ResultSet rs = null;
  	Connection conn = null;
  	try {
  	  conn = this.dataSource.getConnection();
  	  PreparedStatement getStmt = conn.prepareStatement("SELECT filecontent FROM ReportFiles WHERE seqno=?");
  	  synchronized (getStmt) {
  	 	  getStmt.clearParameters();
  		  getStmt.setInt(1, seqNo);
  		  rs = getStmt.executeQuery();
  		  if (rs.next()) {
  		  	stream.write(rs.getBytes(1));
  		  } 
  	  }
  	} catch (Exception e) {
  		throw e;
  	} finally {
  		close(rs);
  		close(conn);
  	}
	}
	
	public List findReportFiles(String regcode, int start, int end) throws Exception {
    Connection conn = null;
  	ResultSet rs = null;
    List reportFiles = new ArrayList();
    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
    try {
    	conn = this.dataSource.getConnection();
  	  String query = "";
  	  query = "SELECT * FROM ReportFiles WHERE regcode = '" + regcode + "' AND deleted = 'N' ORDER BY fileDate";
  	  
  	  Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
  		rs = stmt.executeQuery(query);

  		if (end > -1) {
	  		if (rs.next()) {
	  	    rs.last();
	  	    end = end<rs.getRow()?end:rs.getRow();
	      	for (int i=start; i<=end; i++) {
	  	      rs.absolute(i);
	  	      reportFiles.add(new ReportFileBean(rs.getInt("name"), rs.getString("title"), rs.getString("filename"), dateFormat.format(rs.getDate("fileDate"))));
	  	    }
	      }
  		} else {
  			while(rs.next()) {
  				reportFiles.add(new ReportFileBean(rs.getInt("name"), rs.getString("title"), rs.getString("filename"), dateFormat.format(rs.getDate("fileDate"))));
  			}
  		}

      return reportFiles;
    } catch (Exception e) {
      throw e;
    } finally {
    	close(rs);
  		close(conn);
    }
  }
  
  public int countReportFiles(String regcode) throws Exception {
  	Connection conn = null;
  	ResultSet rs = null;
  	try {
  	  conn = this.dataSource.getConnection();
  	  String query = "SELECT COUNT(*) FROM ReportFiles WHERE regcode = '" + regcode + "' AND deleted = 'N' ";
  	  
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
}
