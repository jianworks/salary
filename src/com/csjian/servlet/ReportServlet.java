package com.csjian.servlet;

import java.io.IOException;
import java.sql.*;
import java.util.*;
import java.io.*;

import com.csjian.model.dao.*;
import com.csjian.model.bean.*;
import com.csjian.report.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.sql.DataSource;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.*;
import net.sf.jasperreports.j2ee.servlets.*;

/**
 * Servlet implementation class for Servlet: ShowFile
 *
 */
public class ReportServlet extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
    /* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public ReportServlet() {
	  super();
  }   	
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		OutputStream os = response.getOutputStream();
		String regcode = request.getParameter("regcode")!=null?request.getParameter("regcode"):"";
	  String year = request.getParameter("year")!=null?request.getParameter("year"):"";
	  String format = request.getParameter("format")!=null?request.getParameter("format"):"PDF";
		String report = request.getParameter("report")!=null?request.getParameter("report"):"";
	  System.out.println("format : " + request.getParameter("format"));
	  JasperPrint jasperPrint = null;
		JasperReport jasperReport =null;
		Map parameters = new HashMap();
		List datalist = null;
		ServletContext servletContext = request.getSession().getServletContext();
		WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext); 
		
		try {
			if (report.equals("employeeList")) {
				DaoFactory daoFactory = (DaoFactory)wac.getBean("daoFactory");
				EmployeeDao dao = (EmployeeDao)daoFactory.createDao(DaoFactory.EMPLOYEE);
				datalist = dao.findEmployee(regcode, "", "", year, 1, -1);
				daoFactory.returnDao(DaoFactory.EMPLOYEE, dao);
			}
		
			jasperReport = JasperCompileManager.compileReport(this.getServletContext().getResourceAsStream("/WEB-INF/report/" + report + ".jrxml"));
			jasperPrint = JasperFillManager.fillReport(jasperReport,parameters, new JRBeanCollectionDataSource(datalist));
			request.getSession().setAttribute(ImageServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE,jasperPrint);

			if(format.equals("HTML")){
				response.setContentType("application/html");
				response.setHeader("Content-Disposition", "inline; filename=\"" + report + ".html\"");
				JRHtmlExporter exporter = new JRHtmlExporter();
				exporter.setParameter(JRHtmlExporterParameter.JASPER_PRINT,jasperPrint);
				exporter.setParameter(JRHtmlExporterParameter.OUTPUT_STREAM, os);
				exporter.setParameter(JRHtmlExporterParameter.CHARACTER_ENCODING,"BIG5");
				exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN,Boolean.FALSE);			
				exporter.exportReport();
			} else if(format.equals("CSV")){
				response.setContentType("application/CSV");
				response.setHeader("Content-Disposition", "inline; filename=\"" + report + ".CSV\"");
				JRXlsExporter exporter = new JRXlsExporter();
				exporter.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
				exporter.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, os);
				exporter.setParameter(JRXlsExporterParameter.CHARACTER_ENCODING,"BIG5");		
				exporter.exportReport();
			}else {
				response.setContentType("application/pdf");
				response.setHeader("Content-Disposition", "inline; filename=\"" + report + ".pdf\"");
				JRPdfExporter exporter = new JRPdfExporter();
				exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT,jasperPrint);
				exporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, os);
				exporter.setParameter(JRPdfExporterParameter.CHARACTER_ENCODING,"BIG5");		
				exporter.exportReport();
			} 
	  } catch (Exception e) {
	  	e.printStackTrace();
	  } 
	}  	
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}   	  	    
}