package com.csjian.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.sql.DataSource;

import org.apache.commons.beanutils.PropertyUtils;

public class TemplateDao {

	protected DataSource dataSource;
    
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	/**
   * 關閉Connection的工具
   * @param conn Connection
   */
  public final void close(Connection conn) {
    if (conn != null) {
      try {
        conn.close();
      } catch (Exception ex) {
      	ex.printStackTrace();
      }
    }
  }

  /**
   * free Statement的工具
   * @param stmt Statement
   */
  public final void close(Statement stmt) {
    if (stmt != null) {
      try {
        stmt.close();
      } catch (Exception ex) {
      	ex.printStackTrace();
      }
    }
  }

  /**
   * free PreparedStatement的工具
   * @param pstmt PreparedStatement
   */
  public final void close(PreparedStatement pstmt) {
    if (pstmt != null) {
      try {
        pstmt.close();
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }
  }

  /**
   * free ResultSet的工具
   * @param rs ResultSet
   */
  public final void close(ResultSet rs) {
    if (rs != null) {
      try {
        rs.close();
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }
  }
}
