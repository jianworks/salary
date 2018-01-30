package com.csjian.servlet;

import java.io.*;

import com.csjian.model.dao.CompanyDao;
import com.csjian.model.dao.ReportFileDao;
import com.csjian.model.dao.DaoFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * Servlet implementation class for Servlet: ShowFile
 * 
 */
public class DownloadReportFile extends javax.servlet.http.HttpServlet implements
		javax.servlet.Servlet {
	/*
	 * (non-Java-doc)
	 * 
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public DownloadReportFile() {
		super();
	}

	/*
	 * (non-Java-doc)
	 * 
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request,
	 * HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		OutputStream os = response.getOutputStream();
		ServletContext servletContext = request.getSession().getServletContext();
		WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext); 
		DaoFactory daoFactory = (DaoFactory)wac.getBean("daoFactory");
		ReportFileDao reportFileDAO = (ReportFileDao)daoFactory.createDao(DaoFactory.REPORTFILE);
		
		try {
			HttpSession ses = request.getSession();
			int seqNo = Integer.parseInt(request.getParameter("seqNo"));
			String fileName = (String)request.getParameter("fileName");
			response.setHeader("Content-Transfer-Encoding", "binary");
			response.setHeader("Content-Disposition", "attachment;filename=\"" + fileName + "\"");
			String extension = fileName.substring(fileName.lastIndexOf('.'));
			response.setContentType("application/" + extension);
			
			reportFileDAO.RetrieveFile(seqNo, os);
	    os.flush();
	    os.close();
		} catch (Exception e) {
		} finally {
			os.flush();
			os.close();
		}
    daoFactory.returnDao(DaoFactory.REPORTFILE, reportFileDAO);
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