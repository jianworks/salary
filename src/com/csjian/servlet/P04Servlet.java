package com.csjian.servlet;

import java.io.IOException;
import java.util.List;
import java.io.*;

import com.csjian.model.bean.CompanyBean;
import com.csjian.model.dao.CompanyDao;
import com.csjian.model.dao.DaoFactory;
import com.csjian.model.dao.SalaryDao;
import com.csjian.report.*;
import com.csjian.report.pdf.P04Reporter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * Servlet implementation class for Servlet: ShowFile
 *
 */
public class P04Servlet extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
    /* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public P04Servlet() {
	  super();
  }   	
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	  try {
  	  response.setHeader("Content-Transfer-Encoding","binary");
  	  response.setHeader("Content-Disposition","attachment;filename=\"salary.pdf" +"\""); 
  	  response.setContentType("application/pdf");
  	  String regcode = request.getParameter("regcode")!=null?request.getParameter("regcode"):"";
  	  String year = request.getParameter("year")!=null?request.getParameter("year"):"";
  	  String month = request.getParameter("month")!=null?request.getParameter("month"):"";
  	  
  	  OutputStream os = response.getOutputStream();
  	  
  	  ServletContext servletContext = request.getSession().getServletContext();
  		WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext); 
  		DaoFactory daoFactory = (DaoFactory)wac.getBean("daoFactory");
  		CompanyDao dao = (CompanyDao)daoFactory.createDao(DaoFactory.COMPANY);
  		CompanyBean company = dao.getCompany(regcode);
  		daoFactory.returnDao(DaoFactory.COMPANY, dao);
  		SalaryDao sdao = (SalaryDao)daoFactory.createDao(DaoFactory.SALARY);
  		List salaryDigest = sdao.findSalaryDigest(regcode, year, month);
  		daoFactory.returnDao(DaoFactory.SALARY, sdao);

  	  
  	  P04Reporter.generatePDF(regcode, year, month, company, salaryDigest, os);
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