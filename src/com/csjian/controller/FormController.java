package com.csjian.controller;

import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;

import com.csjian.model.bean.*;
import com.csjian.model.dao.*;

public class FormController extends BaseController {

	public static final String MODULE = "form";
	public static final String PAGE_ADD_FORM = "addForm";	
	public static final String PAGE_UPDATE_INFO = "updateInfo";	
	public static final String PAGE_ADJUST_FORM = "adjustForm";	
	public static final String PAGE_MAIL_FORM = "mailForm";	
	public static final String PAGE_REMOVE_FORM = "removeForm";	
	public static final String PAGE_DECLATION_FORM = "declationForm";	
	public static final String PAGE_PROCLAMATION_FORM = "proclamationForm";
	public static final String PAGE_START_FORM = "startForm";
	public static final String PAGE_PA_FORM = "paForm";

	public ModelAndView welcome(HttpServletRequest req, HttpServletResponse res) {
		if (sessionHelper.isLogin(req.getSession())) {
			return addForm(req, res);
		} else {
			return HomeController.createLoginMAV();
		}
	}
	
	public ModelAndView info(HttpServletRequest req, HttpServletResponse res) {
		if (!sessionHelper.isLogin(req.getSession())) {
			return HomeController.createLoginMAV();
		}
		HttpSession ses = req.getSession();
		ModelAndView mav = createMAV(PAGE_UPDATE_INFO, req.getSession());
		
		CompanyDao dao = (CompanyDao) daoFactory.createDao(DaoFactory.COMPANY);
		try {
			String regcode = sessionHelper.getRegcode(ses);
			mav.addObject("company", dao.getCompany(regcode));
		} catch (Exception e) {
			e.printStackTrace();
		}
		daoFactory.returnDao(DaoFactory.COMPANY, dao);
		return mav;
	}
	
	public ModelAndView updateInfo(HttpServletRequest req, HttpServletResponse res) {
		if (!sessionHelper.isLogin(req.getSession())) {
			return HomeController.createLoginMAV();
		}
		ModelAndView mav = null;
		
		CompanyDao dao = (CompanyDao) daoFactory.createDao(DaoFactory.COMPANY);
		try {
			CompanyBean company = this.fetchCompany(req);
			dao.updateInfo(company);
			mav = this.info(req, res);
			mav.addObject("msg", "更新公司資料成功");
		} catch (Exception e) {
			mav = this.info(req, res);
			mav.addObject("msg", "更新公司資料失敗");
			e.printStackTrace();
		}
		daoFactory.returnDao(DaoFactory.COMPANY, dao);
		return mav;
	}
	
	public ModelAndView addForm(HttpServletRequest req, HttpServletResponse res) {
		if (!sessionHelper.isLogin(req.getSession())) {
			return HomeController.createLoginMAV();
		}
		HttpSession ses = req.getSession();
		ModelAndView mav = createMAV(PAGE_ADD_FORM, req.getSession());
		
		InsuranceDao dao = (InsuranceDao) daoFactory.createDao(DaoFactory.INSURANCE);
		try {
			String year = req.getParameter("year")!=null&&!req.getParameter("year").equals("")?req.getParameter("year"):""+(Calendar.getInstance()).get(Calendar.YEAR);
			String regcode = sessionHelper.getRegcode(ses);
			mav.addObject("year", year);
			mav.addObject("regcode", regcode);
			mav.addObject("employeeList", dao.getEmployees(regcode, year));
			mav.addObject("relations", dao.relations);
		} catch (Exception e) {
			e.printStackTrace();
		}
		daoFactory.returnDao(DaoFactory.INSURANCE, dao);
		return mav;
	}
	
	public ModelAndView addForm2(HttpServletRequest req, HttpServletResponse res) {
		if (!sessionHelper.isLogin(req.getSession())) {
			return HomeController.createLoginMAV();
		}
		HttpSession ses = req.getSession();
		ModelAndView mav = createMAV(PAGE_ADD_FORM, req.getSession());
		
		InsuranceDao dao = (InsuranceDao) daoFactory.createDao(DaoFactory.INSURANCE);
		try {
			String year = req.getParameter("year")!=null&&!req.getParameter("year").equals("")?req.getParameter("year"):""+(Calendar.getInstance()).get(Calendar.YEAR);
			String regcode = sessionHelper.getRegcode(ses);
			mav.addObject("year", year);
			mav.addObject("regcode", regcode);
			mav.addObject("employeeList", dao.getEmployees(regcode, year));
			mav.addObject("relations", dao.relations);
		} catch (Exception e) {
			e.printStackTrace();
		}
		daoFactory.returnDao(DaoFactory.INSURANCE, dao);
		return mav;
	}
	
	public ModelAndView mailForm(HttpServletRequest req, HttpServletResponse res) {
		if (!sessionHelper.isLogin(req.getSession())) {
			return HomeController.createLoginMAV();
		}
		HttpSession ses = req.getSession();
		ModelAndView mav = createMAV(PAGE_MAIL_FORM, req.getSession());
		
		try {
			String regcode = sessionHelper.getRegcode(ses);
			mav.addObject("regcode", regcode);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}	
	
	public ModelAndView adjustForm(HttpServletRequest req, HttpServletResponse res) {
		if (!sessionHelper.isLogin(req.getSession())) {
			return HomeController.createLoginMAV();
		}
		HttpSession ses = req.getSession();
		ModelAndView mav = createMAV(PAGE_ADJUST_FORM, req.getSession());
		
		InsuranceDao dao = (InsuranceDao) daoFactory.createDao(DaoFactory.INSURANCE);
		try {
			String year = req.getParameter("year")!=null&&!req.getParameter("year").equals("")?req.getParameter("year"):""+(Calendar.getInstance()).get(Calendar.YEAR);
			String regcode = sessionHelper.getRegcode(ses);
			mav.addObject("year", year);
			mav.addObject("regcode", regcode);
			mav.addObject("employeeList", dao.getEmployees(regcode, year));
			mav.addObject("relations", dao.relations);
		} catch (Exception e) {
			e.printStackTrace();
		}
		daoFactory.returnDao(DaoFactory.INSURANCE, dao);
		return mav;
	}
	
	public ModelAndView removeForm(HttpServletRequest req, HttpServletResponse res) {
		if (!sessionHelper.isLogin(req.getSession())) {
			return HomeController.createLoginMAV();
		}
		HttpSession ses = req.getSession();
		ModelAndView mav = createMAV(PAGE_REMOVE_FORM, req.getSession());
		
		InsuranceDao dao = (InsuranceDao) daoFactory.createDao(DaoFactory.INSURANCE);
		try {
			String year = req.getParameter("year")!=null&&!req.getParameter("year").equals("")?req.getParameter("year"):""+(Calendar.getInstance()).get(Calendar.YEAR);
			String regcode = sessionHelper.getRegcode(ses);
			mav.addObject("year", year);
			mav.addObject("regcode", regcode);
			mav.addObject("employeeList", dao.getEmployees(regcode, year));
			mav.addObject("relations", dao.relations);
		} catch (Exception e) {
			e.printStackTrace();
		}
		daoFactory.returnDao(DaoFactory.INSURANCE, dao);
		return mav;
	}
	
	public ModelAndView declationForm(HttpServletRequest req, HttpServletResponse res) {
		if (!sessionHelper.isLogin(req.getSession())) {
			return HomeController.createLoginMAV();
		}
		HttpSession ses = req.getSession();
		ModelAndView mav = createMAV(PAGE_DECLATION_FORM, req.getSession());
		
		CompanyDao dao = (CompanyDao) daoFactory.createDao(DaoFactory.COMPANY);
		try {
			String regcode = sessionHelper.getRegcode(ses);
			mav.addObject("company", dao.getCompany(regcode));
		} catch (Exception e) {
			e.printStackTrace();
		}
		daoFactory.returnDao(DaoFactory.COMPANY, dao);
		return mav;
	}
	
	public ModelAndView proclamationForm(HttpServletRequest req, HttpServletResponse res) {
		if (!sessionHelper.isLogin(req.getSession())) {
			return HomeController.createLoginMAV();
		}
		HttpSession ses = req.getSession();
		ModelAndView mav = createMAV(PAGE_PROCLAMATION_FORM, req.getSession());
		
		CompanyDao dao = (CompanyDao) daoFactory.createDao(DaoFactory.COMPANY);
		try {
			String regcode = sessionHelper.getRegcode(ses);
			mav.addObject("company", dao.getCompany(regcode));
		} catch (Exception e) {
			e.printStackTrace();
		}
		daoFactory.returnDao(DaoFactory.COMPANY, dao);
		return mav;
	}
	
	public ModelAndView startForm(HttpServletRequest req, HttpServletResponse res) {
		if (!sessionHelper.isLogin(req.getSession())) {
			return HomeController.createLoginMAV();
		}
		HttpSession ses = req.getSession();
		ModelAndView mav = createMAV(PAGE_START_FORM, req.getSession());
		
		CompanyDao dao = (CompanyDao) daoFactory.createDao(DaoFactory.COMPANY);
		try {
			String regcode = sessionHelper.getRegcode(ses);
			mav.addObject("company", dao.getCompany(regcode));
		} catch (Exception e) {
			e.printStackTrace();
		}
		daoFactory.returnDao(DaoFactory.COMPANY, dao);
		return mav;
	}
	
	public ModelAndView paForm(HttpServletRequest req, HttpServletResponse res) {
		if (!sessionHelper.isLogin(req.getSession())) {
			return HomeController.createLoginMAV();
		}
		HttpSession ses = req.getSession();
		ModelAndView mav = createMAV(PAGE_PA_FORM, req.getSession());
		String year = req.getParameter("year")!=null&&!req.getParameter("year").equals("")?req.getParameter("year"):""+(Calendar.getInstance()).get(Calendar.YEAR);
		InsuranceDao dao = (InsuranceDao) daoFactory.createDao(DaoFactory.INSURANCE);
		try {
			String regcode = sessionHelper.getRegcode(ses);
			mav.addObject("year", year);
			mav.addObject("regcode", regcode);
			mav.addObject("employeeList", dao.getEmployees(regcode, year));
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		daoFactory.returnDao(DaoFactory.INSURANCE, dao);
		return mav;
	}
	
	// =======================================================
	private ModelAndView createMAV(String mainPage, HttpSession ses) {
		return this.createMAV(mainPage, MODULE, ses);
	}
	
	private CompanyBean fetchCompany(HttpServletRequest req){
		CompanyBean company = new CompanyBean();
   	ServletRequestDataBinder binder = new ServletRequestDataBinder(company, "CompanyBean");
   	binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy/MM/dd hh:mm:ss"), true));
   	binder.bind(req);
   	return company;
  }
}
