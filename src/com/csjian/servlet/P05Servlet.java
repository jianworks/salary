package com.csjian.servlet;

import java.io.IOException;
import java.sql.*;
import java.util.Vector;
import java.io.*;

import com.csjian.model.bean.CompanyBean;
import com.csjian.model.dao.CompanyDao;
import com.csjian.model.dao.DaoFactory;
import com.csjian.model.dao.SalaryDao;
import com.csjian.report.*;
import com.csjian.report.pdf.P05Reporter;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.sql.DataSource;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * Servlet implementation class for Servlet: ShowFile
 *
 */
public class P05Servlet extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
    /* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public P05Servlet() {
	  super();
  }   	
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	  try {
  	  response.setHeader("Content-Transfer-Encoding","binary");
  	  response.setHeader("Content-Disposition","attachment;filename=\"yearlyreport.pdf" +"\""); 
  	  response.setContentType("application/pdf");
  	  String regcode = request.getParameter("regcode")!=null?request.getParameter("regcode"):"";
  	  String year = request.getParameter("year")!=null?request.getParameter("year"):"";
  	  
  	  OutputStream os = response.getOutputStream();
  	  
  	  ServletContext servletContext = request.getSession().getServletContext();
  		WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext); 
  		DaoFactory daoFactory = (DaoFactory)wac.getBean("daoFactory");
  		CompanyDao dao = (CompanyDao)daoFactory.createDao(DaoFactory.COMPANY);
  		CompanyBean company = dao.getCompany(regcode);
  		daoFactory.returnDao(DaoFactory.COMPANY, dao);
  		SalaryDao sdao = (SalaryDao)daoFactory.createDao(DaoFactory.SALARY);
  		Vector datamart = sdao.getYearlyReport(regcode, year);
  		daoFactory.returnDao(DaoFactory.SALARY, sdao);

  	  
  	  P05Reporter.generatePDF(regcode, year, company, datamart, os);
      os.flush();
      os.close();
	  } catch (Exception e) {
	  	System.out.println(e.toString());
	  } 
	}  	
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}   	  	    
}