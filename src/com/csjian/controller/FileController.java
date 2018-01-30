package com.csjian.controller;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;

import com.csjian.model.bean.*;
import com.csjian.model.dao.*;
import java.io.*;

public class FileController extends BaseController {

	public static final String MODULE = "file";
	public static final String PAGE_INDEX = "index";

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
		ModelAndView mav = createMAV(PAGE_INDEX, req.getSession());

		File dir = new File(resources.getApProperty(resources.FILE_PATH) + sessionHelper.getRegcode(ses));
		ses.setAttribute("dir", dir);
		String[] files = dir.list();
		
		int pageno = req.getParameter("pageno")!=null&&!req.getParameter("pageno").equals("")?Integer.parseInt(req.getParameter("pageno")):1;
		int count = files != null? files.length : 0;
		int pageSize = resources.getApPropertyAsInt(resources.PAGE_SIZES);
        int totalPage = ((count-1)/pageSize) + 1;
        int start = (pageno-1)*pageSize + 1;
        int end = pageno*pageSize < count ? pageno*pageSize:count;
		
        List fileList = new ArrayList();
        for (int i=start; i<=end; i++) {
        	fileList.add(files[i-1]);
        }
        
        mav.addObject("pageno", pageno + "");
		mav.addObject("count", count+ "");
		mav.addObject("start", start+ "");
		mav.addObject("end", end + "");
		mav.addObject("totalPage", totalPage + "");
		mav.addObject("files", fileList.toArray(new String[0]));
		mav.addObject("regcode", ses.getId());
		
		return mav;
	}

	// =======================================================
	private ModelAndView createMAV(String mainPage, HttpSession ses) {
		return this.createMAV(mainPage, MODULE, ses);
	}
}
