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
import com.csjian.util.StringUtils;

public class AccountController extends BaseController {

	public static final String MODULE = "account";
	public static final String PAGE_ACCOUNT_LIST = "list";	
	public static final String PAGE_ACCOUNT_EDIT = "edit";	
	public static final String PAGE_GROUP_LIST = "groupList";	
	public static final String PAGE_GROUP_EDIT = "groupEdit";	
	public static final String PAGE_STATISTICS = "statistics";
	public static final String PAGE_EXPORT_DATA = "exportData";

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
		ModelAndView mav = createMAV(PAGE_ACCOUNT_LIST, req.getSession());
		
		CompanyDao dao = (CompanyDao) daoFactory.createDao(DaoFactory.COMPANY);
		try {
			int pageno = req.getParameter("pageno")!=null&&!req.getParameter("pageno").equals("")?Integer.parseInt(req.getParameter("pageno")):1;;
			String keyword = req.getParameter("keyword")!=null?req.getParameter("keyword"):"";

			String agentcode = sessionHelper.getGroupno(ses).equals("admin")?(req.getParameter("agentcode")!=null?req.getParameter("agentcode"):""):sessionHelper.getRegcode(ses);
			mav.addObject("agentcode", agentcode);
			mav.addObject("keyword", keyword);
			if (sessionHelper.getGroupno(ses).equals("admin")) {
				mav.addObject("agents", dao.getAgents());
			} else {
				mav.addObject("agents", null);
			}
			int count = dao.CountCompany(agentcode, keyword, "");
			int pageSize = resources.getApPropertyAsInt(resources.PAGE_SIZES);
	        int totalPage = ((count-1)/pageSize) + 1;
	        int start = (pageno-1)*pageSize + 1;
	        int end = pageno*pageSize < count ? pageno*pageSize:count;
	        mav.addObject("pageno", pageno + "");
			mav.addObject("count", count+ "");
			mav.addObject("start", start+ "");
			mav.addObject("end", end + "");
			mav.addObject("totalPage", totalPage + "");
			mav.addObject("companyList", dao.FindCompany(agentcode, keyword, "", start, end));
		} catch (Exception e) {
			e.printStackTrace();
		}
		daoFactory.returnDao(DaoFactory.COMPANY, dao);
		return mav;
	}
	
	public ModelAndView edit(HttpServletRequest req, HttpServletResponse res) {
		if (!sessionHelper.isLogin(req.getSession())) {
			return HomeController.createLoginMAV();
		}
		
		HttpSession ses = req.getSession();
		ModelAndView mav = createMAV(PAGE_ACCOUNT_EDIT, req.getSession());
		
		try {
			int pageno = req.getParameter("pageno")!=null&&!req.getParameter("pageno").equals("")?Integer.parseInt(req.getParameter("pageno")):1;;
      String agentcode = sessionHelper.getRegcode(ses).equals("admin")?(req.getParameter("agentcode")!=null?req.getParameter("agentcode"):""):sessionHelper.getRegcode(ses);
      CompanyDao dao = (CompanyDao) daoFactory.createDao(DaoFactory.COMPANY);
      mav.addObject("agentcode", agentcode);
			mav.addObject("agents", dao.getAgents());
			String regcode = req.getParameter("regcode")!=null?req.getParameter("regcode"):"";
			if (!regcode.equals("")){
				mav.addObject("company", dao.getCompany(regcode));
			} else {
				mav.addObject("company", new CompanyBean());
			}
			daoFactory.returnDao(DaoFactory.COMPANY, dao);
			
			GroupDao gdao = (GroupDao) daoFactory.createDao(DaoFactory.GROUP);
			mav.addObject("groups", gdao.getGroups());
			daoFactory.returnDao(DaoFactory.GROUP, gdao);
			
			mav.addObject("pageno", pageno + "");
			String keyword = req.getParameter("keyword")!=null?req.getParameter("keyword"):"";
			mav.addObject("keyword", keyword);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return mav;
	}
	
	public ModelAndView update(HttpServletRequest req, HttpServletResponse res) {
		if (!sessionHelper.isLogin(req.getSession())) {
			return HomeController.createLoginMAV();
		}
		CompanyBean company = this.fetchCompany(req);
		HttpSession ses = req.getSession();
		ModelAndView mav = null;
		
		try {
			CompanyDao dao = (CompanyDao) daoFactory.createDao(DaoFactory.COMPANY);
      if (req.getParameter("isnew").equals("Y")) {
      	dao.insert(company);
      } else {
      	dao.update(company);
      }
      mav = this.list(req, res);
      mav.addObject("msg", req.getParameter("isnew").equals("Y")?"新增公司資料成功!":"更新公司資料成功!");
			daoFactory.returnDao(DaoFactory.COMPANY, dao);
		} catch (Exception e) {
			mav = this.list(req, res);
			mav.addObject("msg", req.getParameter("isnew").equals("Y")?"新增公司資料失敗!":"更新公司資料失敗!");
			e.printStackTrace();
		}
		
		return mav;
	}
	
	public ModelAndView resetPassword(HttpServletRequest req, HttpServletResponse res) {
		if (!sessionHelper.isLogin(req.getSession())) {
			return HomeController.createLoginMAV();
		}
		
		ModelAndView mav = this.list(req, res);
		
		CompanyDao dao = (CompanyDao) daoFactory.createDao(DaoFactory.COMPANY);
		try {
			dao.resetPass(req.getParameter("regcode"));
			mav.addObject("msg", "重設密碼成功!");
		} catch (Exception e) {
			e.printStackTrace();
			mav.addObject("msg", "重設密碼失敗!");
		}
		daoFactory.returnDao(DaoFactory.COMPANY, dao);
		return mav;
	}
	
	public ModelAndView remove(HttpServletRequest req, HttpServletResponse res) {
		if (!sessionHelper.isLogin(req.getSession())) {
			return HomeController.createLoginMAV();
		}
		
		HttpSession ses = req.getSession();
		ModelAndView mav = null;
		
		CompanyDao dao = (CompanyDao) daoFactory.createDao(DaoFactory.COMPANY);
		try {
			dao.remove(req.getParameter("regcode"));
			mav = this.list(req, res);
			mav.addObject("msg", "刪除成功!");
		} catch (Exception e) {
			e.printStackTrace();
			mav = this.list(req, res);
			mav.addObject("msg", "刪除失敗!");
		}
		daoFactory.returnDao(DaoFactory.COMPANY, dao);
		return mav;
	}
	
	public ModelAndView batchRemove(HttpServletRequest req, HttpServletResponse res) {
		if (!sessionHelper.isLogin(req.getSession())) {
			return HomeController.createLoginMAV();
		}
		
		HttpSession ses = req.getSession();
		ModelAndView mav = null;
		
		CompanyDao dao = (CompanyDao) daoFactory.createDao(DaoFactory.COMPANY);
		try {
			String[] regcodes = req.getParameterValues("selected_id");
			if (regcodes !=null) {
				for (int i=0; i<regcodes.length; i++) {
					dao.remove(regcodes[i]);
				}
			}
			mav = this.list(req, res);
			mav.addObject("msg", "刪除成功!");
		} catch (Exception e) {
			e.printStackTrace();
			mav = this.list(req, res);
			mav.addObject("msg", "刪除失敗!");
		}
		daoFactory.returnDao(DaoFactory.COMPANY, dao);
		return mav;
	}
	
	public ModelAndView statistics(HttpServletRequest req, HttpServletResponse res) {
		if (!sessionHelper.isLogin(req.getSession())) {
			return HomeController.createLoginMAV();
		}
		
		HttpSession ses = req.getSession();
		ModelAndView mav = createMAV(PAGE_STATISTICS, req.getSession());
		
		CompanyDao dao = (CompanyDao) daoFactory.createDao(DaoFactory.COMPANY);
		try {
			int pageno = req.getParameter("pageno")!=null&&!req.getParameter("pageno").equals("")?Integer.parseInt(req.getParameter("pageno")):1;;
			String agentcode = sessionHelper.getGroupno(ses).equals("admin")?(req.getParameter("agentcode")!=null?req.getParameter("agentcode"):""):sessionHelper.getRegcode(ses);
			
			Vector logs = dao.loginLog(StringUtils.twToAd(req.getParameter("startdate")), StringUtils.twToAd(req.getParameter("enddate")), agentcode);
			mav.addObject("logs", logs);
			mav.addObject("agentcode", agentcode);
			
			if (sessionHelper.getGroupno(ses).equals("admin")) {
				mav.addObject("agents", dao.getAgents());
			} else {
				mav.addObject("agents", null);
			}
			
			int pageSize = resources.getApPropertyAsInt(resources.PAGE_SIZES);
			int totalPage = ((logs.size()-1)/pageSize) + 1;
			int start = (pageno-1)*pageSize + 1;
			int end = pageno*pageSize < logs.size() ? pageno*pageSize:logs.size();
			int count = logs.size();
      
			System.out.println("pageno=" + pageno + ",totalPage=" + totalPage + ",start=" + start + ",end=" + end + ",count=" + count);
			mav.addObject("pageno", pageno + "");
			mav.addObject("start", start+ "");
			mav.addObject("count", count+ "");
			mav.addObject("end", end + "");
			mav.addObject("startdate", req.getParameter("startdate")!=null?req.getParameter("startdate"):"");
			mav.addObject("enddate", req.getParameter("enddate")!=null?req.getParameter("enddate"):"");
			mav.addObject("totalPage", totalPage + "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		daoFactory.returnDao(DaoFactory.COMPANY, dao);
		return mav;
	}
	
	public ModelAndView listGroup(HttpServletRequest req, HttpServletResponse res) {
		if (!sessionHelper.isLogin(req.getSession())) {
			return HomeController.createLoginMAV();
		}
		HttpSession ses = req.getSession();
		ModelAndView mav = createMAV(PAGE_GROUP_LIST, req.getSession());
		
		GroupDao dao = (GroupDao) daoFactory.createDao(DaoFactory.GROUP);
		try {
			int pageno = req.getParameter("pageno")!=null&&!req.getParameter("pageno").equals("")?Integer.parseInt(req.getParameter("pageno")):1;;
			GroupBean[] groups = dao.getGroups();
			mav.addObject("groupList", groups);
			int count = groups.length;
			int pageSize = resources.getApPropertyAsInt(resources.PAGE_SIZES);
			int totalPage = ((count-1)/pageSize) + 1;
			int start = (pageno-1)*pageSize + 1;
			int end = pageno*pageSize < count ? pageno*pageSize:count;
			mav.addObject("pageno", pageno + "");
			mav.addObject("count", count+ "");
			mav.addObject("start", start+ "");
			mav.addObject("end", end + "");
			mav.addObject("totalPage", totalPage + "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		daoFactory.returnDao(DaoFactory.GROUP, dao);
		return mav;
	}
	
	public ModelAndView editGroup(HttpServletRequest req, HttpServletResponse res) {
		if (!sessionHelper.isLogin(req.getSession())) {
			return HomeController.createLoginMAV();
		}
		
		HttpSession ses = req.getSession();
		ModelAndView mav = createMAV(PAGE_GROUP_EDIT, req.getSession());
		
		try {
			int pageno = req.getParameter("pageno")!=null&&!req.getParameter("pageno").equals("")?Integer.parseInt(req.getParameter("pageno")):1;;
			GroupDao dao = (GroupDao) daoFactory.createDao(DaoFactory.GROUP);
			String groupno = req.getParameter("groupno")!=null?req.getParameter("groupno"):"";
			if (!groupno.equals("")){
				mav.addObject("group", dao.getGroup(groupno));
			} else {
				mav.addObject("group", new GroupBean());
			}
			mav.addObject("apList", dao.findApList());
			daoFactory.returnDao(DaoFactory.GROUP, dao);
			
			mav.addObject("pageno", pageno + "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return mav;
	}
	
	public ModelAndView updateGroup(HttpServletRequest req, HttpServletResponse res) {
		if (!sessionHelper.isLogin(req.getSession())) {
			return HomeController.createLoginMAV();
		}
		GroupBean group = this.fetchGroup(req);
		HttpSession ses = req.getSession();
		ModelAndView mav = null;
		
		try {
			GroupDao dao = (GroupDao) daoFactory.createDao(DaoFactory.GROUP);
			if (req.getParameter("isnew").equals("Y")) {
				dao.insert(group);
			} else {
				dao.update(group);
			}
			mav = this.listGroup(req, res);
			mav.addObject("msg", req.getParameter("isnew").equals("Y")?"新增群組資料成功!":"更新群組資料成功!");
			daoFactory.returnDao(DaoFactory.GROUP, dao);
		} catch (Exception e) {
			mav = this.listGroup(req, res);
			mav.addObject("msg", req.getParameter("isnew").equals("Y")?"新增群組資料失敗!":"更新群組資料失敗!");
			e.printStackTrace();
		}
		
		return mav;
	}
	
	public ModelAndView removeGroup(HttpServletRequest req, HttpServletResponse res) {
		if (!sessionHelper.isLogin(req.getSession())) {
			return HomeController.createLoginMAV();
		}
		
		HttpSession ses = req.getSession();
		ModelAndView mav = null;
		
		GroupDao dao = (GroupDao) daoFactory.createDao(DaoFactory.GROUP);
		try {
			dao.remove(req.getParameter("groupno"));
			mav = this.listGroup(req, res);
			mav.addObject("msg", "刪除成功!");
		} catch (Exception e) {
			e.printStackTrace();
			mav = this.listGroup(req, res);
			mav.addObject("msg", "刪除失敗!");
		}
		daoFactory.returnDao(DaoFactory.GROUP, dao);
		return mav;
	}
	
	public ModelAndView batchRemoveGroup(HttpServletRequest req, HttpServletResponse res) {
		if (!sessionHelper.isLogin(req.getSession())) {
			return HomeController.createLoginMAV();
		}
		
		HttpSession ses = req.getSession();
		ModelAndView mav = null;
		
		GroupDao dao = (GroupDao) daoFactory.createDao(DaoFactory.GROUP);
		try {
			String[] groupnos = req.getParameterValues("selected_id");
			if (groupnos !=null) {
				for (int i=0; i<groupnos.length; i++) {
					dao.remove(groupnos[i]);
				}
			}
			mav = this.listGroup(req, res);
			mav.addObject("msg", "刪除成功!");
		} catch (Exception e) {
			e.printStackTrace();
			mav = this.listGroup(req, res);
			mav.addObject("msg", "刪除失敗!");
		}
		daoFactory.returnDao(DaoFactory.COMPANY, dao);
		return mav;
	}
	
	public ModelAndView export(HttpServletRequest req, HttpServletResponse res) {
		if (!sessionHelper.isLogin(req.getSession())) {
			return HomeController.createLoginMAV();
		}
		HttpSession ses = req.getSession();
		ModelAndView mav = createMAV(PAGE_EXPORT_DATA, req.getSession());
		
		CompanyDao dao = (CompanyDao) daoFactory.createDao(DaoFactory.COMPANY);
		try {
			int pageno = req.getParameter("pageno")!=null&&!req.getParameter("pageno").equals("")?Integer.parseInt(req.getParameter("pageno")):1;;
			String keyword = req.getParameter("keyword")!=null?req.getParameter("keyword"):"";
			String year = req.getParameter("year")!=null&&!req.getParameter("year").equals("")?req.getParameter("year"):""+(Calendar.getInstance()).get(Calendar.YEAR);
			String agentcode = sessionHelper.getRegcode(ses).equals("admin")?(req.getParameter("agentcode")!=null?req.getParameter("agentcode"):""):sessionHelper.getRegcode(ses);
			mav.addObject("agentcode", agentcode);
			mav.addObject("keyword", keyword);
			mav.addObject("agents", dao.getAgents());
			mav.addObject("year", year);
			int count = dao.CountCompany(agentcode, keyword, year);
			int pageSize = resources.getApPropertyAsInt(resources.PAGE_SIZES);
		    int totalPage = ((count-1)/pageSize) + 1;
		    int start = (pageno-1)*pageSize + 1;
		    int end = pageno*pageSize < count ? pageno*pageSize:count;
		    mav.addObject("pageno", pageno + "");
			mav.addObject("count", count+ "");
			mav.addObject("start", start+ "");
			mav.addObject("end", end + "");
			mav.addObject("totalPage", totalPage + "");
			mav.addObject("companyList", dao.FindCompany(agentcode, keyword, year, start, end));
		} catch (Exception e) {
			e.printStackTrace();
		}
		daoFactory.returnDao(DaoFactory.COMPANY, dao);
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
	
	private GroupBean fetchGroup(HttpServletRequest req){
		GroupBean group = new GroupBean();
		ServletRequestDataBinder binder = new ServletRequestDataBinder(group, "GroupBean");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy/MM/dd hh:mm:ss"), true));
		binder.bind(req);
		return group;
	}
}
