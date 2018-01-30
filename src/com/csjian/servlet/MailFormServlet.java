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
public class MailFormServlet extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
    /* (non-Java-doc)
     * @see javax.servlet.http.HttpServlet#HttpServlet()
     */
    public MailFormServlet() {
    super();
  }

  /* (non-Java-doc)
   * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  	OutputStream os = response.getOutputStream();
  	try {
    	response.setHeader("Content-Transfer-Encoding","binary");
      response.setHeader("Content-Disposition","attachment;filename=\"mailform.pdf" +"\"");
      response.setContentType("application/pdf");
      String regcode = request.getParameter("regcode")!=null?request.getParameter("regcode"):"";
      String[] tocode = request.getParameterValues("tocode");

      ServletContext servletContext = request.getSession().getServletContext();
  		WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext); 
  		DaoFactory daoFactory = (DaoFactory)wac.getBean("daoFactory");

  		CompanyDao cdao = (CompanyDao)daoFactory.createDao(DaoFactory.COMPANY);
  		CompanyBean company = cdao.getCompany(regcode);
  		daoFactory.returnDao(DaoFactory.COMPANY, cdao);
  		
      (new CreateMailForm()).generatePDF(regcode, tocode, this.getServletContext().getResourceAsStream("/WEB-INF/blankform/mailform.pdf"), company, os);
    } catch (Exception e) {
    } finally {
      os.flush();
      os.close();
    }
  }

  /* (non-Java-doc)
   * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
   */
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    doGet(request, response);
  }
}