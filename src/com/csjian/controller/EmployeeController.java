package com.csjian.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.JSONObject;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;

import com.csjian.model.bean.*;
import com.csjian.model.dao.*;
import com.csjian.util.*;
import com.google.gson.JsonPrimitive;

public class EmployeeController extends BaseController {

	public static final String MODULE = "employee";
	public static final String PAGE_EMPLOYEE_LIST = "list";
	public static final String PAGE_EMPLOYEE_EDIT = "edit";
	public static final String PAGE_BSALARY_EDIT = "bsalaryEdit";

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
		ModelAndView mav = createMAV(PAGE_EMPLOYEE_LIST, req.getSession());

		EmployeeDao dao = (EmployeeDao) daoFactory
				.createDao(DaoFactory.EMPLOYEE);
		try {
			int pageno = req.getParameter("pageno") != null
					&& !req.getParameter("pageno").equals("") ? Integer
					.parseInt(req.getParameter("pageno")) : 1;
			;
			String year = req.getParameter("year") != null
					&& !req.getParameter("year").equals("") ? req
					.getParameter("year") : ""
					+ (Calendar.getInstance()).get(Calendar.YEAR);
			String keyword = req.getParameter("keyword") != null ? req
					.getParameter("keyword") : "";
			String resign = req.getParameter("resign") != null ? req
					.getParameter("resign") : "";
			String regcode = sessionHelper.getRegcode(ses);
			int count = dao.countEmployee(regcode, keyword, resign, year);
			int pageSize = resources.getApPropertyAsInt(resources.PAGE_SIZES);
			int totalPage = ((count - 1) / pageSize) + 1;
			int start = (pageno - 1) * pageSize + 1;
			int end = pageno * pageSize < count ? pageno * pageSize : count;
			mav.addObject("year", year);
			mav.addObject("resign", resign);
			mav.addObject("keyword", keyword);
			mav.addObject("pageno", pageno + "");
			mav.addObject("count", count + "");
			mav.addObject("start", start + "");
			mav.addObject("end", end + "");
			mav.addObject("totalPage", totalPage + "");
			mav.addObject("employeeList", dao.findEmployee(regcode, keyword,
					resign, year, start, end));
		} catch (Exception e) {
			e.printStackTrace();
		}
		daoFactory.returnDao(DaoFactory.EMPLOYEE, dao);
		return mav;
	}

	public ModelAndView edit(HttpServletRequest req, HttpServletResponse res) {
		if (!sessionHelper.isLogin(req.getSession())) {
			return HomeController.createLoginMAV();
		}

		HttpSession ses = req.getSession();
		ModelAndView mav = createMAV(PAGE_EMPLOYEE_EDIT, req.getSession());

		try {
			int pageno = req.getParameter("pageno") != null
					&& !req.getParameter("pageno").equals("") ? Integer
					.parseInt(req.getParameter("pageno")) : 1;
			;
			String year = req.getParameter("year") != null
					&& !req.getParameter("year").equals("") ? req
					.getParameter("year") : ""
					+ (Calendar.getInstance()).get(Calendar.YEAR);
			String keyword = req.getParameter("keyword") != null ? req
					.getParameter("keyword") : "";
			String resign = req.getParameter("resign") != null ? req
					.getParameter("resign") : "";

			String employeeno = req.getParameter("employeeno") != null ? req
					.getParameter("employeeno") : "";
			EmployeeDao dao = (EmployeeDao) daoFactory
					.createDao(DaoFactory.EMPLOYEE);
			if (!employeeno.equals("")) {
				mav.addObject("employee", dao.retriveEmployee(
						sessionHelper.getRegcode(ses), employeeno, year));
			} else {
				mav.addObject("employee", new EmployeeBean());
			}
			daoFactory.returnDao(DaoFactory.EMPLOYEE, dao);

			mav.addObject("pageno", pageno + "");
			mav.addObject("year", year + "");
			mav.addObject("keyword", keyword);
			mav.addObject("resign", resign);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return mav;
	}

	public ModelAndView update(HttpServletRequest req, HttpServletResponse res) {
		if (!sessionHelper.isLogin(req.getSession())) {
			return HomeController.createLoginMAV();
		}
		EmployeeBean employee = this.fetchEmployee(req);
		HttpSession ses = req.getSession();
		ModelAndView mav = null;

		try {
			EmployeeDao dao = (EmployeeDao) daoFactory
					.createDao(DaoFactory.EMPLOYEE);
			if (req.getParameter("isnew").equals("Y")) {
				if (dao.insert(employee) < 0) {
					mav = createMAV(PAGE_EMPLOYEE_EDIT, req.getSession());
					employee.setEmployeeno("");
					mav.addObject("employee", employee);
					int pageno = req.getParameter("pageno") != null
							&& !req.getParameter("pageno").equals("") ? Integer
							.parseInt(req.getParameter("pageno")) : 1;
					;
					String year = req.getParameter("year") != null
							&& !req.getParameter("year").equals("") ? req
							.getParameter("year") : ""
							+ (Calendar.getInstance()).get(Calendar.YEAR);
					String keyword = req.getParameter("keyword") != null ? req
							.getParameter("keyword") : "";
					String resign = req.getParameter("resign") != null ? req
							.getParameter("resign") : "";
					mav.addObject("pageno", pageno + "");
					mav.addObject("year", year + "");
					mav.addObject("keyword", keyword);
					mav.addObject("resign", resign);
					mav.addObject("msg", "新增員工資料失敗! 員工編號已存在 !");
				} else {
					mav = this.list(req, res);
					mav.addObject("msg", "新增員工資料成功!");
				}
			} else {
				dao.update(employee);
				mav = this.list(req, res);
				mav.addObject("msg", "更新員工資料成功!");
			}
			daoFactory.returnDao(DaoFactory.EMPLOYEE, dao);
		} catch (Exception e) {
			mav = this.list(req, res);
			mav.addObject(
					"msg",
					req.getParameter("isnew").equals("Y") ? "新增員工資料失敗! ["
							+ e.toString() + "]" : "更新員工資料失敗! [" + e.toString()
							+ "]");
			e.printStackTrace();
		}

		return mav;
	}

	public ModelAndView transfer(HttpServletRequest req, HttpServletResponse res) {
		if (!sessionHelper.isLogin(req.getSession())) {
			return HomeController.createLoginMAV();
		}
		HttpSession ses = req.getSession();
		ModelAndView mav = null;

		try {
			boolean overwrite = req.getParameter("overwrite").equalsIgnoreCase(
					"Y") ? true : false;
			EmployeeDao dao = (EmployeeDao) daoFactory
					.createDao(DaoFactory.EMPLOYEE);
			dao.copyLastYear(sessionHelper.getRegcode(ses),
					req.getParameter("year"), overwrite);

			mav = this.list(req, res);
			mav.addObject("msg", "複製去年度員工資料成功!");
			daoFactory.returnDao(DaoFactory.EMPLOYEE, dao);
		} catch (Exception e) {
			mav = this.list(req, res);
			mav.addObject("msg", "複製去年度員工資料失敗!");
			e.printStackTrace();
		}

		return mav;
	}

	public ModelAndView remove(HttpServletRequest req, HttpServletResponse res) {
		if (!sessionHelper.isLogin(req.getSession())) {
			return HomeController.createLoginMAV();
		}

		HttpSession ses = req.getSession();
		ModelAndView mav = null;

		EmployeeDao dao = (EmployeeDao) daoFactory
				.createDao(DaoFactory.EMPLOYEE);
		try {
			if (!dao.hasSalaryData(sessionHelper.getRegcode(ses),
					(String) req.getParameter("employeeno"),
					(String) req.getParameter("year"))) {
				dao.remove(sessionHelper.getRegcode(ses),
						(String) req.getParameter("employeeno"),
						(String) req.getParameter("year"));
				mav = this.list(req, res);
				mav.addObject("msg", "刪除成功!");
			} else {
				mav = this.list(req, res);
				mav.addObject("msg", "刪除失敗! 員工有薪資資料 !");
			}
		} catch (Exception e) {
			e.printStackTrace();
			mav = this.list(req, res);
			mav.addObject("msg", "刪除失敗!");
		}
		daoFactory.returnDao(DaoFactory.EMPLOYEE, dao);
		return mav;
	}

	public ModelAndView batchRemove(HttpServletRequest req,
			HttpServletResponse res) {
		if (!sessionHelper.isLogin(req.getSession())) {
			return HomeController.createLoginMAV();
		}

		HttpSession ses = req.getSession();
		ModelAndView mav = null;

		EmployeeDao dao = (EmployeeDao) daoFactory
				.createDao(DaoFactory.EMPLOYEE);
		try {
			String[] employeenos = req.getParameterValues("selected_id");
			String msg = "";
			if (employeenos != null) {
				for (int i = 0; i < employeenos.length; i++) {
					if (!dao.hasSalaryData(sessionHelper.getRegcode(ses),
							employeenos[i], (String) req.getParameter("year"))) {
						dao.remove(sessionHelper.getRegcode(ses),
								employeenos[i],
								(String) req.getParameter("year"));
					} else {
						msg += employeenos[i] + " ";
					}
				}
			}
			mav = this.list(req, res);
			mav.addObject("msg", "刪除成功!"
					+ ((msg.length() > 0) ? "(員工編號 : " + msg
							+ " 等員工仍有薪資資料不能刪除 !)" : ""));
		} catch (Exception e) {
			e.printStackTrace();
			mav = this.list(req, res);
			mav.addObject("msg", "刪除失敗!");
		}
		daoFactory.returnDao(DaoFactory.EMPLOYEE, dao);
		return mav;
	}

	public ModelAndView editBsalary(HttpServletRequest req,
			HttpServletResponse res) {
		if (!sessionHelper.isLogin(req.getSession())) {
			return HomeController.createLoginMAV();
		}

		HttpSession ses = req.getSession();
		ModelAndView mav = createMAV(PAGE_BSALARY_EDIT, req.getSession());

		try {
			int pageno = req.getParameter("pageno") != null
					&& !req.getParameter("pageno").equals("") ? Integer
					.parseInt(req.getParameter("pageno")) : 1;
			;
			String year = req.getParameter("year") != null
					&& !req.getParameter("year").equals("") ? req
					.getParameter("year") : ""
					+ (Calendar.getInstance()).get(Calendar.YEAR);
			String keyword = req.getParameter("keyword") != null ? req
					.getParameter("keyword") : "";
			String resign = req.getParameter("resign") != null ? req
					.getParameter("resign") : "";

			String employeeno = req.getParameter("employeeno") != null ? req
					.getParameter("employeeno") : "";
			BsalaryDao dao = (BsalaryDao) daoFactory
					.createDao(DaoFactory.BSALARY);
			mav.addObject("bsalary", dao.retriveBsalary(
					sessionHelper.getRegcode(ses), employeeno, year));
			daoFactory.returnDao(DaoFactory.BSALARY, dao);

			EmployeeDao edao = (EmployeeDao) daoFactory
					.createDao(DaoFactory.EMPLOYEE);
			mav.addObject("employee", edao.retriveEmployee(
					sessionHelper.getRegcode(ses), employeeno, year));
			daoFactory.returnDao(DaoFactory.EMPLOYEE, edao);

			mav.addObject("pageno", pageno + "");
			mav.addObject("year", year + "");
			mav.addObject("keyword", keyword);
			mav.addObject("resign", resign);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return mav;
	}

	public ModelAndView updateBsalary(HttpServletRequest req,
			HttpServletResponse res) {
		if (!sessionHelper.isLogin(req.getSession())) {
			return HomeController.createLoginMAV();
		}
		BsalaryBean bsalary = this.fetchBsalary(req);
		HttpSession ses = req.getSession();
		ModelAndView mav = null;

		try {
			BsalaryDao dao = (BsalaryDao) daoFactory
					.createDao(DaoFactory.BSALARY);
			dao.update(bsalary);
			mav = this.list(req, res);
			mav.addObject("msg", "更新員工薪資資料成功!");
			daoFactory.returnDao(DaoFactory.BSALARY, dao);
		} catch (Exception e) {
			mav = this.list(req, res);
			mav.addObject("msg", "更新員工薪資資料失敗!");
			e.printStackTrace();
		}

		return mav;
	}

	public void importFile(HttpServletRequest req, HttpServletResponse res) {
		EmployeeDao dao = (EmployeeDao) daoFactory.createDao(DaoFactory.EMPLOYEE);
		JSONObject totalJason = new JSONObject();
		try {
			List<FileItem> multiparts = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(req);
			HttpSession ses = req.getSession();
			String year = req.getParameter("year")!=null ? req.getParameter("year") : (Calendar.getInstance()).get(Calendar.YEAR) + "";
			int count = 0;
			for (FileItem item : multiparts) {
				if (!item.isFormField()) {
					File fNew= new File("C:/Temp/", item.getName());
					item.write(fNew);
					count = dao.importFromWintonTxt(sessionHelper.getRegcode(ses), year, fNew);
				}
			}
			res.setContentType("text/html");
			totalJason.put("status", "OK");
			totalJason.put("count", count);
			res.getWriter().print(totalJason.toString());
		} catch (Exception e) {
			e.printStackTrace();
			res.reset();
			res.setCharacterEncoding("UTF-8");
			try {
				totalJason.put("status", "NG");
				res.getWriter().print(totalJason.toString());
			} catch (Exception ex) {
			
			}
		} finally {
			daoFactory.returnDao(DaoFactory.EMPLOYEE, dao);
		}
	}

	// =======================================================
	private ModelAndView createMAV(String mainPage, HttpSession ses) {
		return this.createMAV(mainPage, MODULE, ses);
	}

	private EmployeeBean fetchEmployee(HttpServletRequest req) {
		EmployeeBean employee = new EmployeeBean();
		ServletRequestDataBinder binder = new ServletRequestDataBinder(employee, "EmployeeBean");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy/MM/dd hh:mm:ss"), true));
		binder.bind(req);
		employee.setRegcode(sessionHelper.getRegcode(req.getSession()));
		employee.setBirthday(StringUtils.twToAd(employee.getBirthday()));
		employee.setResigndate(StringUtils.twToAd(employee.getResigndate()));
		employee.setOnboarddate(StringUtils.twToAd(employee.getOnboarddate()));
		return employee;
	}

	private BsalaryBean fetchBsalary(HttpServletRequest req) {
		BsalaryBean bsalary = new BsalaryBean();
		ServletRequestDataBinder binder = new ServletRequestDataBinder(bsalary,
				"BsalaryBean");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				new SimpleDateFormat("yyyy/MM/dd hh:mm:ss"), true));
		binder.bind(req);
		bsalary.setRegcode(sessionHelper.getRegcode(req.getSession()));

		Vector itemp = new Vector();
		int totalp = req.getParameter("totalp") != null ? Integer.parseInt(req
				.getParameter("totalp")) : 0;
		for (int i = 0; i < totalp; i++) {
			String[] item = new String[3];
			item[0] = req.getParameter("pseq" + i) != null ? req
					.getParameter("pseq" + i) : "";
			item[2] = req.getParameter("pamount" + i) != null ? req
					.getParameter("pamount" + i) : "";
			itemp.add(item);
		}
		bsalary.setItemp(itemp);
		Vector itemm = new Vector();
		int totalm = req.getParameter("totalm") != null ? Integer.parseInt(req
				.getParameter("totalm")) : 0;
		for (int i = 0; i < totalm; i++) {
			String[] item = new String[3];
			item[0] = req.getParameter("pseq" + i) != null ? req
					.getParameter("mseq" + i) : "";
			item[2] = req.getParameter("pamount" + i) != null ? req
					.getParameter("mamount" + i) : "";
			itemm.add(item);
		}
		bsalary.setItemm(itemm);
		return bsalary;
	}
}
