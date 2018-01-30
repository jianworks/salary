package com.csjian.servlet;

import java.io.*;
import java.util.*;

import com.csjian.model.dao.*;
import com.csjian.form.CreateAddForm;
import com.csjian.util.*;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * Servlet implementation class for Servlet: GetMaterialName
 *
 */
 public class GetDependants extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
    /* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public GetDependants() {
		super();
	}   	
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String employeeno = request.getParameter("employeeno")!=null?request.getParameter("employeeno"):"";
		String regcode = request.getParameter("regcode")!=null?request.getParameter("regcode"):"";
		String year = request.getParameter("year")!=null?request.getParameter("`year"):"";
		String results = "";
		
		try {
			ServletContext servletContext = request.getSession().getServletContext();
  		WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext); 
  		DaoFactory daoFactory = (DaoFactory)wac.getBean("daoFactory");
  		EmployeeDao dao = (EmployeeDao)daoFactory.createDao(DaoFactory.EMPLOYEE);
  		List dependants = dao.findDependant(regcode, employeeno, year);
  		daoFactory.returnDao(DaoFactory.EMPLOYEE, dao);
  		
  		String[] dependant = null;
      for (int i=0; i<dependants.size(); i++) {
      	dependant = (String[])dependants.get(i);
      	results += dependant[0] + "," + dependant[1] + "|";
      }      
      if (results.indexOf("|")>0) results = results.substring(0, results.length()-1);
		} catch (Exception e) {
			System.out.println(e.toString());
		} 
		//System.out.println("results " + results);
		response.setContentType("text/html;charset=big5");
		//response.setCharacterEncoding("BIG5");
		response.getWriter().print(results);
	}  	
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}   	  	    
}