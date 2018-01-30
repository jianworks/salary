package com.csjian.controller;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;

import com.csjian.report.pdf.*;
import com.csjian.report.xls.EmployeeListXLS;
import com.csjian.model.bean.*;
import com.csjian.model.dao.*;

public class ReportController extends BaseController {

	public static final String MODULE = "report";
	public static final String PAGE_REPORT_P01 = "p01";	
	public static final String PAGE_REPORT_P02 = "p02";
	public static final String PAGE_REPORT_P03 = "p03";
	public static final String PAGE_REPORT_P04 = "p04";
	public static final String PAGE_REPORT_P05 = "p05";
	public static final String PAGE_REPORT_EMPLOYEELIST = "employeeList";

	public ModelAndView welcome(HttpServletRequest req, HttpServletResponse res) {
		if (sessionHelper.isLogin(req.getSession())) {
			return p01(req, res);
		} else {
			return HomeController.createLoginMAV();
		}
	}
	
	public ModelAndView p01(HttpServletRequest req, HttpServletResponse res) {
		if (!sessionHelper.isLogin(req.getSession())) {
			return HomeController.createLoginMAV();
		}
		HttpSession ses = req.getSession();
		ModelAndView mav = createMAV(PAGE_REPORT_P01, req.getSession());
		
		try {
			String year = req.getParameter("year")!=null&&!req.getParameter("year").equals("")?req.getParameter("year"):""+(Calendar.getInstance()).get(Calendar.YEAR);
			String month = req.getParameter("month")!=null&&!req.getParameter("month").equals("")?req.getParameter("month"):""+(Calendar.getInstance().get(Calendar.MONTH) + 1);
			String yearly = req.getParameter("yearly")!=null&&!req.getParameter("yearly").equals("")?req.getParameter("yearly"):"N";
			String regcode = sessionHelper.getRegcode(ses);
			CompanyDao dao = (CompanyDao) daoFactory.createDao(DaoFactory.COMPANY);
			mav.addObject("company", dao.getCompany(regcode));
			daoFactory.returnDao(DaoFactory.COMPANY, dao);
			SalaryDao sdao = (SalaryDao) daoFactory.createDao(DaoFactory.SALARY);		
			if (yearly.equals("Y"))	mav.addObject("salaryList", sdao.getAllYearlySalaries(regcode, year));
			else mav.addObject("salaryList", sdao.getAllSalaries(regcode, year, month));
			daoFactory.returnDao(DaoFactory.SALARY, sdao);
			mav.addObject("year", year);
			mav.addObject("month", month);
			mav.addObject("yearly", yearly);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}
	
	public ModelAndView p02(HttpServletRequest req, HttpServletResponse res) {
		if (!sessionHelper.isLogin(req.getSession())) {
			return HomeController.createLoginMAV();
		}
		HttpSession ses = req.getSession();
		ModelAndView mav = createMAV(PAGE_REPORT_P02, req.getSession());
		
		try {
			String year = req.getParameter("year")!=null&&!req.getParameter("year").equals("")?req.getParameter("year"):""+(Calendar.getInstance()).get(Calendar.YEAR);
			String month = req.getParameter("month")!=null&&!req.getParameter("month").equals("")?req.getParameter("month"):""+(Calendar.getInstance().get(Calendar.MONTH) + 1);
			String regcode = sessionHelper.getRegcode(ses);
			CompanyDao dao = (CompanyDao) daoFactory.createDao(DaoFactory.COMPANY);
			mav.addObject("company", dao.getCompany(regcode));
			daoFactory.returnDao(DaoFactory.COMPANY, dao);
			SalaryDao sdao = (SalaryDao) daoFactory.createDao(DaoFactory.SALARY);		
			mav.addObject("salaryList", sdao.getAllSalaries(regcode, year, month));
			daoFactory.returnDao(DaoFactory.SALARY, sdao);
			mav.addObject("year", year);
			mav.addObject("month", month);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}
	
	public ModelAndView p03(HttpServletRequest req, HttpServletResponse res) {
		if (!sessionHelper.isLogin(req.getSession())) {
			return HomeController.createLoginMAV();
		}
		HttpSession ses = req.getSession();
		ModelAndView mav = createMAV(PAGE_REPORT_P03, req.getSession());
		
		try {
			String year = req.getParameter("year")!=null&&!req.getParameter("year").equals("")?req.getParameter("year"):""+(Calendar.getInstance()).get(Calendar.YEAR);
			String month = req.getParameter("month")!=null&&!req.getParameter("month").equals("")?req.getParameter("month"):""+(Calendar.getInstance().get(Calendar.MONTH) + 1);
			String regcode = sessionHelper.getRegcode(ses);
			CompanyDao dao = (CompanyDao) daoFactory.createDao(DaoFactory.COMPANY);
			mav.addObject("company", dao.getCompany(regcode));
			daoFactory.returnDao(DaoFactory.COMPANY, dao);
			SalaryDao sdao = (SalaryDao) daoFactory.createDao(DaoFactory.SALARY);		
			mav.addObject("datamart", sdao.findSalaryDigest(regcode, year, month));
			daoFactory.returnDao(DaoFactory.SALARY, sdao);
			mav.addObject("year", year);
			mav.addObject("month", month);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}
	
	public ModelAndView p04(HttpServletRequest req, HttpServletResponse res) {
		if (!sessionHelper.isLogin(req.getSession())) {
			return HomeController.createLoginMAV();
		}
		HttpSession ses = req.getSession();
		ModelAndView mav = createMAV(PAGE_REPORT_P04, req.getSession());
		
		try {
			String year = req.getParameter("year")!=null&&!req.getParameter("year").equals("")?req.getParameter("year"):""+(Calendar.getInstance()).get(Calendar.YEAR);
			String month = req.getParameter("month")!=null&&!req.getParameter("month").equals("")?req.getParameter("month"):""+(Calendar.getInstance().get(Calendar.MONTH) + 1);
			String regcode = sessionHelper.getRegcode(ses);
			CompanyDao dao = (CompanyDao) daoFactory.createDao(DaoFactory.COMPANY);
			mav.addObject("company", dao.getCompany(regcode));
			daoFactory.returnDao(DaoFactory.COMPANY, dao);
			SalaryDao sdao = (SalaryDao) daoFactory.createDao(DaoFactory.SALARY);		
			mav.addObject("datamart", sdao.findSalaryDigest(regcode, year, month));
			daoFactory.returnDao(DaoFactory.SALARY, sdao);
			mav.addObject("year", year);
			mav.addObject("month", month);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}
	
	public ModelAndView p05(HttpServletRequest req, HttpServletResponse res) {
		if (!sessionHelper.isLogin(req.getSession())) {
			return HomeController.createLoginMAV();
		}
		HttpSession ses = req.getSession();
		ModelAndView mav = createMAV(PAGE_REPORT_P05, req.getSession());
		
		try {
			String year = req.getParameter("year")!=null&&!req.getParameter("year").equals("")?req.getParameter("year"):""+(Calendar.getInstance()).get(Calendar.YEAR);
			String regcode = sessionHelper.getRegcode(ses);
			CompanyDao dao = (CompanyDao) daoFactory.createDao(DaoFactory.COMPANY);
			mav.addObject("company", dao.getCompany(regcode));
			daoFactory.returnDao(DaoFactory.COMPANY, dao);
			SalaryDao sdao = (SalaryDao) daoFactory.createDao(DaoFactory.SALARY);		
			mav.addObject("datamart", sdao.getYearlyReport(regcode, year));
			daoFactory.returnDao(DaoFactory.SALARY, sdao);
			mav.addObject("year", year);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}
	
	public ModelAndView employeeList(HttpServletRequest req, HttpServletResponse res) {
		if (!sessionHelper.isLogin(req.getSession())) {
			return HomeController.createLoginMAV();
		}
		HttpSession ses = req.getSession();
		ModelAndView mav = createMAV(PAGE_REPORT_EMPLOYEELIST, req.getSession());
		
		try {
			String year = req.getParameter("year")!=null&&!req.getParameter("year").equals("")?req.getParameter("year"):""+(Calendar.getInstance()).get(Calendar.YEAR);
			String regcode = sessionHelper.getRegcode(ses);
			CompanyDao dao = (CompanyDao) daoFactory.createDao(DaoFactory.COMPANY);
			mav.addObject("company", dao.getCompany(regcode));
			daoFactory.returnDao(DaoFactory.COMPANY, dao);
			mav.addObject("year", year);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}
	
	public void employeeListPDF(HttpServletRequest req, HttpServletResponse res) {
		EmployeeDao dao = (EmployeeDao)daoFactory.createDao(DaoFactory.EMPLOYEE);
		CompanyDao companyDao = (CompanyDao)daoFactory.createDao(DaoFactory.COMPANY);
		try {
			HttpSession ses = req.getSession();
			String regcode = sessionHelper.getRegcode(ses);		
			CompanyBean company = companyDao.getCompany(regcode);
			String year = req.getParameter("year")!=null&&!req.getParameter("year").equals("")?req.getParameter("year"):""+(Calendar.getInstance()).get(Calendar.YEAR);
			String format = req.getParameter("format")!=null&&!req.getParameter("format").equals("")?req.getParameter("format"):"PDF";
			List<EmployeeBean> employees = dao.findEmployee(regcode, "", "", year, 1, -1);
			
			if (format.equals("PDF")) {
				res.setHeader("Content-Transfer-Encoding","binary");
				res.setHeader("Content-Disposition","attachment;filename=\"employeeList.pdf" +"\"");
				res.setContentType("application/pdf");
				OutputStream os = res.getOutputStream();
				EmployeeListPDF.generatePDF(company, employees, os);
				os.flush();
				os.close();
			} else {
				res.setHeader("Content-Transfer-Encoding","binary");
				res.setHeader("Content-Disposition","attachment;filename=\"employeeList.xls" +"\"");
				res.setContentType("application/vnd.ms-excel");
				OutputStream os = res.getOutputStream();
				EmployeeListXLS.generateXLS(company, employees, os);
				os.flush();
				os.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			res.setContentType("text/html");
		} finally {
			daoFactory.returnDao(DaoFactory.COMPANY, companyDao);
			daoFactory.returnDao(DaoFactory.EMPLOYEE, dao);
		}
	}
	
	// =======================================================
	private ModelAndView createMAV(String mainPage, HttpSession ses) {
		return this.createMAV(mainPage, MODULE, ses);
	}
}
