package com.csjian.servlet;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

import com.csjian.model.dao.*;
import com.csjian.model.bean.*;
import com.csjian.form.CreateDeclationForm;
import com.csjian.util.*;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;



/**
 * Servlet implementation class for Servlet: ShowFile
 *
 */
public class DeclationFormServlet extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
    /* (non-Java-doc)
     * @see javax.servlet.http.HttpServlet#HttpServlet()
     */
  public DeclationFormServlet() {
    super();
  }

  /* (non-Java-doc)
   * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    try {
    	response.setHeader("Content-Transfer-Encoding","binary");
      response.setHeader("Content-Disposition","attachment;filename=\"declationform.pdf" +"\"");
      response.setContentType("application/pdf");
      String regcode = request.getParameter("regcode")!=null?request.getParameter("regcode"):"";
      String reason = request.getParameter("reason")!=null?request.getParameter("reason"):"0";
      String amount = request.getParameter("amount")!=null?request.getParameter("amount"):"0";
      CompanyBean company = this.fetchCompany(request);
            
      ServletContext servletContext = request.getSession().getServletContext();
  		WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext); 
  		DaoFactory daoFactory = (DaoFactory)wac.getBean("daoFactory");
  		CompanyDao dao = (CompanyDao)daoFactory.createDao(DaoFactory.COMPANY);
  		dao.updateInfo(company);
  		daoFactory.returnDao(DaoFactory.COMPANY, dao);

      OutputStream os = response.getOutputStream();
      (new CreateDeclationForm()).renderForm(reason, this.getServletContext().getResourceAsStream("/WEB-INF/blankform/declationform.pdf"), company, amount, os);
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
  
  private CompanyBean fetchCompany(HttpServletRequest req){
		CompanyBean company = new CompanyBean();
   	ServletRequestDataBinder binder = new ServletRequestDataBinder(company, "CompanyBean");
   	binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy/MM/dd hh:mm:ss"), true));
   	binder.bind(req);
   	return company;
  }
  
}