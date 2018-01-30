package com.csjian.servlet;

import java.io.*;

import com.csjian.form.CreateMailForm;
import com.csjian.model.bean.CompanyBean;
import com.csjian.model.dao.CompanyDao;
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
public class Download extends javax.servlet.http.HttpServlet implements
		javax.servlet.Servlet {
	/*
	 * (non-Java-doc)
	 * 
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public Download() {
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
		try {
			HttpSession ses = request.getSession();
			String fileName = (String)request.getParameter("file");
			response.setHeader("Content-Transfer-Encoding", "binary");
			response.setHeader("Content-Disposition",
					"attachment;filename=\"" + fileName + "\"");
			String extension = fileName.substring(fileName.lastIndexOf('.'));
			response.setContentType("application/" + extension);
			File file = new File(ses.getAttribute("dir")+ "/" + fileName);
			InputStream is = new FileInputStream(file);
		      
		    byte[] buf = new byte[4096];
		    for(int len=-1;(len=is.read(buf))!=-1;)
		      os.write(buf,0,len);

		    os.flush();
		    os.close();
		} catch (Exception e) {
		} finally {
			os.flush();
			os.close();
		}
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