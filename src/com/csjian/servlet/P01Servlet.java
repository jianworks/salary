package com.csjian.servlet;

import java.io.IOException;
import java.sql.*;
import java.io.*;

import com.csjian.model.dao.*;
import com.csjian.model.bean.*;
import com.csjian.report.*;
import com.csjian.report.pdf.P01Reporter;

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
public class P01Servlet extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
    /* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public P01Servlet() {
	  super();
  }   	
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	  try {
  	  response.setHeader("Content-Transfer-Encoding","binary");
  	  response.setHeader("Content-Disposition","attachment;filename=\"monthlysalary.pdf" +"\""); 
  	  response.setContentType("application/pdf");
  	  String regcode = request.getParameter("regcode")!=null?request.getParameter("regcode"):"";
  	  String year = request.getParameter("year")!=null?request.getParameter("year"):"";
  	  String month = request.getParameter("month")!=null?request.getParameter("month"):"";
  	  String yearly = request.getParameter("yearly")!=null&&!request.getParameter("yearly").equals("")?request.getParameter("yearly"):"N";
  	  System.out.println("P01Servlet " + regcode + "|" + year + "|" + month + "|" + yearly);
  	  
  	  ServletContext servletContext = request.getSession().getServletContext();
  		WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext); 
  		DaoFactory daoFactory = (DaoFactory)wac.getBean("daoFactory");
  		CompanyDao dao = (CompanyDao)daoFactory.createDao(DaoFactory.COMPANY);
  		CompanyBean company = dao.getCompany(regcode);
  		daoFactory.returnDao(DaoFactory.COMPANY, dao);
  		SalaryDao sdao = (SalaryDao)daoFactory.createDao(DaoFactory.SALARY);
  		SalaryBean[] salaryList = null;
  		if (yearly.equals("Y")) salaryList = sdao.getAllYearlySalaries(regcode, year);
      else  salaryList = sdao.getAllSalaries(regcode, year, month);
  		daoFactory.returnDao(DaoFactory.SALARY, sdao);

  	  
  	  OutputStream os = response.getOutputStream();
  	  P01Reporter.generatePDF(regcode, year, month, company, salaryList, os);
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