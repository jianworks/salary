package com.csjian.servlet;

import java.io.*;
import java.util.*;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.*;

/**
 * Servlet implementation class for Servlet: ShowFile
 *
 */
public class RetrieveBlankForm extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
    /* (non-Java-doc)
     * @see javax.servlet.http.HttpServlet#HttpServlet()
     */
  public RetrieveBlankForm() {
    super();
  }

  /* (non-Java-doc)
   * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    try {
    	String form = request.getParameter("form")!=null?request.getParameter("form"):"";
    	response.setHeader("Content-Transfer-Encoding","binary");
    	response.setHeader("Content-Disposition","attachment;filename=\"" + form + ".doc" +"\"");
      response.setContentType("application/msword");
           
      OutputStream os = response.getOutputStream();
      InputStream is = this.getServletContext().getResourceAsStream("/WEB-INF/blankform/" + form + ".doc");
      
      byte[] buf = new byte[4096];
      for(int len=-1;(len=is.read(buf))!=-1;)
        os.write(buf,0,len);

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