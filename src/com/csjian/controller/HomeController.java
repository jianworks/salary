package com.csjian.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;

import com.csjian.model.bean.*;
import com.csjian.model.dao.*;

public class HomeController extends BaseController {

	public static final String MODULE = "home";
	public static final String PAGE_WELCOME = "index";	
	private static final String PAGE_LOGIN = "login";
	public static final String PAGE_PASSWORD = "password";
	public static final String PAGE_GOODIES = "goodies";

	public static ModelAndView createLoginMAV() {
		ModelAndView mav = new ModelAndView(PAGE_LOGIN);
		return mav;
	}
	
	public ModelAndView welcome(HttpServletRequest req, HttpServletResponse res) {
		if (sessionHelper.isLogin(req.getSession())) {
			return createMAV(PAGE_WELCOME, req.getSession());
		} else {
			return HomeController.createLoginMAV();
		}
	}
	
	public ModelAndView goodies(HttpServletRequest req, HttpServletResponse res) {
		if (sessionHelper.isLogin(req.getSession())) {
			return createMAV(PAGE_GOODIES, req.getSession());
		} else {
			return HomeController.createLoginMAV();
		}
	}
	
	public ModelAndView password(HttpServletRequest req, HttpServletResponse res) {
		if (sessionHelper.isLogin(req.getSession())) {
			return createMAV(PAGE_PASSWORD, req.getSession());
		} else {
			return HomeController.createLoginMAV();
		}
	}
	
	public ModelAndView doPassword(HttpServletRequest req, HttpServletResponse res) {
		if (!sessionHelper.isLogin(req.getSession())) {
			return HomeController.createLoginMAV();
		}
		CompanyDao dao = (CompanyDao) daoFactory.createDao(DaoFactory.COMPANY);
		ModelAndView mav = password(req, res);
		try {
			if (dao.updatePass(sessionHelper.getRegcode(req.getSession()), req.getParameter("oldpass"), req.getParameter("newpass"))) {
				mav.addObject("msg", "更新密碼成功!");
			} else {
				mav.addObject("msg", "更新密碼失敗!");
			}
		} catch (Exception e) {
			mav.addObject("msg", "更新密碼有誤，請稍後再試!");
		}
		daoFactory.returnDao(DaoFactory.COMPANY, dao);
		return mav;
	}
	
	public ModelAndView login(HttpServletRequest req, HttpServletResponse res) {
		System.out.println("[" + new java.util.Date() + "] login from ip " + req.getRemoteAddr());
		HttpSession ses = req.getSession();
		try {
			System.out.println("[" + new java.util.Date() + "] login from ip " + req.getRemoteAddr() + " old session regcode " + sessionHelper.getRegcode(ses));
		} catch (Exception e) {
			// 代表之前沒有登入過，do nothing
		}
		// 清除舊的 login 資料
		ses.invalidate();
		
		ses = req.getSession();

		// 先把之前login的資料logout
		//sessionHelper.logout(req.getSession());

		String regcode = req.getParameter("regcode");
		String password = req.getParameter("password");
		CompanyDao dao = (CompanyDao) daoFactory.createDao(DaoFactory.COMPANY);
		CompanyBean company = dao.login(regcode, password);
		daoFactory.returnDao(DaoFactory.COMPANY, dao);
		
		ModelAndView mav = null;
		if (company != null) {
			if (company.getDisable().equals("Y")) {
				mav = welcome(req, res);
				mav.addObject("msg", "帳號已停用!");
			} else {
				GroupDao groupDao= (GroupDao) daoFactory.createDao(DaoFactory.GROUP);
				sessionHelper.login(ses, company, groupDao.findApList(company.getGroupno(), "0"), 
					groupDao.findApList(company.getGroupno(), "1"), 
					groupDao.findApList(company.getGroupno(), "2"), 
					groupDao.findApList(company.getGroupno(), "3"),
					groupDao.findApList(company.getGroupno(), "4"),
					groupDao.findApList(company.getGroupno(), "5"));
				daoFactory.returnDao(DaoFactory.GROUP, groupDao);
				mav = welcome(req, res);
			}
		} else {
			mav = welcome(req, res);
			mav.addObject("msg", "登入失敗!");
		}
		System.out.println("[" + new java.util.Date() + "] login completed from ip " + req.getRemoteAddr());
		return mav;
	}
	
	public ModelAndView logout(HttpServletRequest req, HttpServletResponse res) {
		sessionHelper.logout(req.getSession());
		ModelAndView mav = welcome(req, res);		
		
		//mav.addObject("msg", "已登出系統!");
		return mav;
	}

	// =======================================================
	private ModelAndView createMAV(String mainPage, HttpSession ses) {
		return this.createMAV(mainPage, MODULE, ses);
	}
}
