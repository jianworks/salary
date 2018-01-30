package com.csjian.servlet;

import java.io.*;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import com.csjian.model.dao.*;

/**
 * Servlet implementation class for Servlet: ShowFile
 *
 */
public class ExportEmployeeToTxt extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
    /* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public ExportEmployeeToTxt() {
	  super();
  }   	
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
 	  String regcode = request.getParameter("regcode");
 	  String year = request.getParameter("year");
		ServletContext servletContext = request.getSession().getServletContext();
		WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext); 
		DaoFactory daoFactory = (DaoFactory)wac.getBean("daoFactory");
		CompanyDao cdao = (CompanyDao)daoFactory.createDao(DaoFactory.COMPANY);
		String filename = "";
		try {
			filename = cdao.getCompany(regcode).getName();
		} catch (Exception e) {
			filename = regcode;
		}
		daoFactory.returnDao(DaoFactory.COMPANY, cdao);
		response.setHeader("Content-Disposition","attachment;filename=\""+ new String(filename.getBytes("Big5"), "ISO8859_1") + ".txt" +"\""); 
  	response.setContentType("text/plain;charset=big5");
 	 	EmployeeDao dao = (EmployeeDao)daoFactory.createDao(DaoFactory.EMPLOYEE);
 	  try {
 	  	dao.exportEmployeeToTxt(regcode, year, response.getWriter());
 	  } catch (Exception e) {
 	  	e.toString();
 	  }
 	  daoFactory.returnDao(DaoFactory.EMPLOYEE, dao);
	}  	
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}   	  	    
}