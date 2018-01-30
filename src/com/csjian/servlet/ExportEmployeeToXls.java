package com.csjian.servlet;

import java.io.*;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
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
public class ExportEmployeeToXls extends javax.servlet.http.HttpServlet
		implements javax.servlet.Servlet {
	/*
	 * (non-Java-doc)
	 * 
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public ExportEmployeeToXls() {
		super();
	}

	/*
	 * (non-Java-doc)
	 * 
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request,
	 * HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String regcode = request.getParameter("regcode");
		String year = request.getParameter("year");
		ServletContext servletContext = request.getSession()
				.getServletContext();
		WebApplicationContext wac = WebApplicationContextUtils
				.getRequiredWebApplicationContext(servletContext);
		DaoFactory daoFactory = (DaoFactory) wac.getBean("daoFactory");
		EmployeeDao dao = (EmployeeDao) daoFactory
				.createDao(DaoFactory.EMPLOYEE);
		response.setHeader("Content-Transfer-Encoding", "binary");
		response.setHeader("Content-Disposition", "attachment;filename=\""
				+ regcode + ".xls" + "\"");
		response.setContentType("application/vnd.ms-excel");
		ServletOutputStream os = response.getOutputStream();
		try {
			dao.exportEmployeeToXls(regcode, year, os);
		} catch (Exception e) {
			e.toString();
		}
		daoFactory.returnDao(DaoFactory.EMPLOYEE, dao);
		os.flush();
		os.close();
	}

	/*
	 * (non-Java-doc)
	 * 
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request,
	 * HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}