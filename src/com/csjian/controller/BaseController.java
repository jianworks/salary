package com.csjian.controller;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.csjian.util.Pagination;
import com.csjian.util.Resources;
import com.csjian.model.dao.*;

public class BaseController extends MultiActionController {

	protected SessionHelper sessionHelper;
	protected Resources resources;
	protected DaoFactory daoFactory;
	
	protected ModelAndView createMAV(String mainPage, String Module, HttpSession ses) {
		ModelAndView mav = new ModelAndView("main");
		mav.addObject("includeModule", Module);
		mav.addObject("includeMain", mainPage);
		mav.addObject("name", sessionHelper.getRegname(ses));
		mav.addObject("aplist0", sessionHelper.getAplist0(ses));
		mav.addObject("aplist1", sessionHelper.getAplist1(ses));
		mav.addObject("aplist2", sessionHelper.getAplist2(ses));
		mav.addObject("aplist3", sessionHelper.getAplist3(ses));
		mav.addObject("aplist4", sessionHelper.getAplist4(ses));
		mav.addObject("aplist5", sessionHelper.getAplist5(ses));

		return mav;
	}

	public void setSessionHelper(SessionHelper sessionHelper) {
		this.sessionHelper = sessionHelper;
	}

	public void setResources(Resources resources) {
		this.resources = resources;
	}
	
	public void setDaoFactory(DaoFactory daoFactory) {
		this.daoFactory = daoFactory;
	}
}
