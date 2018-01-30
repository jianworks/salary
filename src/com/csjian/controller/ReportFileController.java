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

public class ReportFileController extends BaseController {

	public static final String MODULE = "reportFile";
	public static final String PAGE_LIST = "index";	
	public static final String PAGE_DOWNLOAD = "download";	

	public ModelAndView welcome(HttpServletRequest req, HttpServletResponse res) {
		if (sessionHelper.isLogin(req.getSession())) {
			return list(req, res);
		} else {
			return HomeController.createLoginMAV();
		}
	}
	
	public ModelAndView list(HttpServletRequest req, HttpServletResponse res) {
		if (!sessionHelper.isLogin(req.getSession())) {
			return HomeController.createLoginMAV();
		}
		HttpSession ses = req.getSession();
		ModelAndView mav = createMAV(PAGE_LIST, req.getSession());
		
		ReportFileDao dao = (ReportFileDao) daoFactory.createDao(DaoFactory.REPORTFILE);
		try {
			int pageno = req.getParameter("pageno")!=null&&!req.getParameter("pageno").equals("")?Integer.parseInt(req.getParameter("pageno")):1;;
			String regcode = sessionHelper.getRegcode(ses);
			int count = dao.countReportFiles(regcode);
			int pageSize = resources.getApPropertyAsInt(resources.PAGE_SIZES);
			int totalPage = ((count-1)/pageSize) + 1;
			int start = (pageno-1)*pageSize + 1;
			int end = pageno*pageSize < count ? pageno*pageSize:count;
			mav.addObject("pageno", pageno + "");
			mav.addObject("count", count+ "");
			mav.addObject("start", start+ "");
			mav.addObject("end", end + "");
			mav.addObject("totalPage", totalPage + "");
			mav.addObject("reportFileList", dao.findReportFiles(regcode, start, end));
		} catch (Exception e) {
			e.printStackTrace();
		}
		daoFactory.returnDao(DaoFactory.REPORTFILE, dao);
		return mav;
	}
	
	private ModelAndView createMAV(String mainPage, HttpSession ses) {
		return this.createMAV(mainPage, MODULE, ses);
	}
}
