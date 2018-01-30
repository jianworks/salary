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
import com.csjian.util.DataTablesParamUtility;
import com.csjian.util.JQueryDataTableParamModel;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class SalaryController extends BaseController {

	public static final String MODULE = "salary";
	public static final String PAGE_SALARY_LIST = "list";
	public static final String PAGE_SALARY_EDIT = "edit";
	public static final String PAGE_PITEM = "pitem";
	public static final String PAGE_MITEM = "mitem";

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
		ModelAndView mav = createMAV(PAGE_SALARY_LIST, req.getSession());

		SalaryDao dao = (SalaryDao) daoFactory.createDao(DaoFactory.SALARY);
		try {
			String year = req.getParameter("year") != null
					&& !req.getParameter("year").equals("") ? req
					.getParameter("year") : ""
					+ (Calendar.getInstance()).get(Calendar.YEAR);
			String month = req.getParameter("month") != null
					&& !req.getParameter("month").equals("") ? req
					.getParameter("month") : ""
					+ (Calendar.getInstance().get(Calendar.MONTH) + 1);
			mav.addObject("year", year);
			mav.addObject("month", month);
			mav.addObject("salaryList", dao.findActiveEmployeeSalary(
					sessionHelper.getRegcode(ses), year, month));
		} catch (Exception e) {
			e.printStackTrace();
		}
		daoFactory.returnDao(DaoFactory.SALARY, dao);
		return mav;
	}

	public ModelAndView edit(HttpServletRequest req, HttpServletResponse res) {
		if (!sessionHelper.isLogin(req.getSession())) {
			return HomeController.createLoginMAV();
		}

		HttpSession ses = req.getSession();
		ModelAndView mav = createMAV(PAGE_SALARY_EDIT, req.getSession());

		try {
			String year = req.getParameter("year") != null
					&& !req.getParameter("year").equals("") ? req
					.getParameter("year") : ""
					+ (Calendar.getInstance()).get(Calendar.YEAR);
			String month = req.getParameter("month") != null
					&& !req.getParameter("month").equals("") ? req
					.getParameter("month") : ""
					+ (Calendar.getInstance().get(Calendar.MONTH) + 1);

			String employeeno = req.getParameter("employeeno") != null ? req
					.getParameter("employeeno") : "";
			SalaryDao dao = (SalaryDao) daoFactory.createDao(DaoFactory.SALARY);
			if (!employeeno.equals("")) {
				mav.addObject("salary", dao.retriveSalary(
						sessionHelper.getRegcode(ses), employeeno, year, month,
						false));
			} else {
				mav = this.list(req, res);
				mav.addObject("msg", "請先選擇要進行薪資建檔的員工");
			}
			daoFactory.returnDao(DaoFactory.SALARY, dao);

			EmployeeDao edao = (EmployeeDao) daoFactory
					.createDao(DaoFactory.EMPLOYEE);
			mav.addObject("employee", edao.retriveEmployee(
					sessionHelper.getRegcode(ses), employeeno, year));
			daoFactory.returnDao(DaoFactory.EMPLOYEE, edao);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return mav;
	}

	public ModelAndView update(HttpServletRequest req, HttpServletResponse res) {
		if (!sessionHelper.isLogin(req.getSession())) {
			return HomeController.createLoginMAV();
		}
		// EmployeeBean employee = this.fetchEmployee(req);
		HttpSession ses = req.getSession();
		ModelAndView mav = null;

		try {
			SalaryDao dao = (SalaryDao) daoFactory.createDao(DaoFactory.SALARY);

			dao.update(this.fetchSalary(req));
			mav = this.list(req, res);
			mav.addObject("msg", "薪資資料建檔成功!");

			daoFactory.returnDao(DaoFactory.SALARY, dao);
		} catch (Exception e) {
			e.printStackTrace();
			mav = this.list(req, res);
			mav.addObject("msg", "薪資資料建檔失敗!");
		}

		return mav;
	}

	public ModelAndView remove(HttpServletRequest req, HttpServletResponse res) {
		if (!sessionHelper.isLogin(req.getSession())) {
			return HomeController.createLoginMAV();
		}

		HttpSession ses = req.getSession();
		ModelAndView mav = null;

		SalaryDao dao = (SalaryDao) daoFactory.createDao(DaoFactory.SALARY);
		try {
			dao.remove(sessionHelper.getRegcode(ses),
					(String) req.getParameter("employeeno"),
					(String) req.getParameter("year"),
					(String) req.getParameter("month"));
			mav = this.list(req, res);
			mav.addObject("msg", "刪除成功!");
		} catch (Exception e) {
			mav = this.list(req, res);
			e.printStackTrace();
			mav.addObject("msg", "刪除失敗!");
		}
		daoFactory.returnDao(DaoFactory.SALARY, dao);
		return mav;
	}

	public ModelAndView batchRemove(HttpServletRequest req,
			HttpServletResponse res) {
		if (!sessionHelper.isLogin(req.getSession())) {
			return HomeController.createLoginMAV();
		}

		HttpSession ses = req.getSession();
		ModelAndView mav = null;

		SalaryDao dao = (SalaryDao) daoFactory.createDao(DaoFactory.SALARY);
		try {
			String[] employeenos = req.getParameterValues("selected_id");
			if (employeenos != null) {
				for (int i = 0; i < employeenos.length; i++) {
					dao.remove(sessionHelper.getRegcode(ses), employeenos[i],
							(String) req.getParameter("year"),
							(String) req.getParameter("month"));
				}
			}
			mav = this.list(req, res);
			mav.addObject("msg", "刪除成功!");
		} catch (Exception e) {
			mav = this.list(req, res);
			e.printStackTrace();
			mav.addObject("msg", "刪除失敗!");
		}
		daoFactory.returnDao(DaoFactory.SALARY, dao);
		return mav;
	}

	public ModelAndView copyLastMonth(HttpServletRequest req,
			HttpServletResponse res) {
		if (!sessionHelper.isLogin(req.getSession())) {
			return HomeController.createLoginMAV();
		}
		// EmployeeBean employee = this.fetchEmployee(req);
		HttpSession ses = req.getSession();
		ModelAndView mav = null;

		try {
			SalaryDao dao = (SalaryDao) daoFactory.createDao(DaoFactory.SALARY);

			dao.copy(sessionHelper.getRegcode(ses),
					(String) req.getParameter("year"),
					(String) req.getParameter("month"));

			daoFactory.returnDao(DaoFactory.SALARY, dao);

			mav = this.list(req, res);
			mav.addObject("msg", "複製薪資資料建檔成功!");
		} catch (Exception e) {
			e.printStackTrace();
			mav = this.list(req, res);
			mav.addObject("msg", "複製薪資資料建檔失敗!");
		}

		return mav;
	}

	public ModelAndView copyHistory(HttpServletRequest req,
			HttpServletResponse res) {
		if (!sessionHelper.isLogin(req.getSession())) {
			return HomeController.createLoginMAV();
		}
		// EmployeeBean employee = this.fetchEmployee(req);
		HttpSession ses = req.getSession();
		ModelAndView mav = null;

		try {
			SalaryDao dao = (SalaryDao) daoFactory.createDao(DaoFactory.SALARY);
			String copyFrom = req.getParameter("copyFrom") != null ? req
					.getParameter("copyFrom") : "";
			System.out.println("copyForm = " + copyFrom);
			if (!copyFrom.equals("")) {
				String[] from = copyFrom.split("-");
				dao.copy(sessionHelper.getRegcode(ses), from[0], from[1],
						(String) req.getParameter("year"),
						(String) req.getParameter("month"));
				mav = this.list(req, res);
				mav.addObject("msg", "複製薪資資料建檔成功!");
			} else {
				mav = this.list(req, res);
				mav.addObject("msg", "複製薪資資料建檔失敗!");
			}
			daoFactory.returnDao(DaoFactory.SALARY, dao);
		} catch (Exception e) {
			e.printStackTrace();
			mav = this.list(req, res);
			mav.addObject("msg", "複製薪資資料建檔失敗!");
		}

		return mav;
	}

	public ModelAndView pitem(HttpServletRequest req, HttpServletResponse res) {
		if (!sessionHelper.isLogin(req.getSession())) {
			return HomeController.createLoginMAV();
		}
		HttpSession ses = req.getSession();
		ModelAndView mav = createMAV(PAGE_PITEM, req.getSession());
		mav.addObject("regcode", sessionHelper.getRegcode(ses));

		return mav;
	}
	
	public ModelAndView mitem(HttpServletRequest req, HttpServletResponse res) {
		if (!sessionHelper.isLogin(req.getSession())) {
			return HomeController.createLoginMAV();
		}
		HttpSession ses = req.getSession();
		ModelAndView mav = createMAV(PAGE_MITEM, req.getSession());
		mav.addObject("regcode", sessionHelper.getRegcode(ses));

		return mav;
	}

	public ModelAndView updateItem(HttpServletRequest req, HttpServletResponse res) {
		if (!sessionHelper.isLogin(req.getSession())) {
			return HomeController.createLoginMAV();
		}
		ModelAndView mav = null;

		try {
			HttpSession ses = req.getSession();
			String regcode = sessionHelper.getRegcode(ses);
			
			ItemDao dao = (ItemDao) daoFactory.createDao(DaoFactory.ITEM);
			
			ItemBean item = new ItemBean();
			item.setName(req.getParameter("name"));
			item.setSeqno(Integer.parseInt(req.getParameter("seqno")));
			item.setItemType(req.getParameter("itemType"));
			item.setRegcode(req.getParameter("regcode"));
			item.setEnable(req.getParameter("enable"));
			item.setAddOnFeeFlag(req.getParameter("addOnFeeFlag"));
			item.setBonusFlag(req.getParameter("bonusFlag")!=null ? req.getParameter("bonusFlag") : "N");
			dao.updateItem(item, regcode);

			res.setContentType("text/json;charset=UTF-8");
			res.getWriter().print(new JsonPrimitive("OK"));
		} catch (Exception e) {
			res.setContentType("text/json;charset=UTF-8");
			try {
				res.getWriter().print(new JsonPrimitive("FAIL"));
			} catch (Exception ex){}
			e.printStackTrace();
		}
		return mav;
	}
	
	public void itemDataTable(HttpServletRequest req, HttpServletResponse res) {
		ItemDao dao = (ItemDao) daoFactory.createDao(DaoFactory.ITEM);
		try {
			HttpSession ses = req.getSession();

			String regcode = sessionHelper.getRegcode(ses);
			String itemType = req.getParameter("itemType");

			JQueryDataTableParamModel param = DataTablesParamUtility.getParam(req);

			//String sEcho = param.sEcho;
			int iTotalRecords; // total number of records (unfiltered)
			JsonArray data = new JsonArray(); // data that will be shown in the
												// table

			List<ItemBean> items = dao.getItemList(regcode, itemType);
			iTotalRecords = items.size();

			JsonObject jsonResponse = new JsonObject();
			jsonResponse.addProperty("iTotalRecords", iTotalRecords);

			if (itemType.equals("P")) {
				for (ItemBean c : items) {
					JsonArray row = new JsonArray();
					row.add(new JsonPrimitive(c.getName()));
					row.add(new JsonPrimitive(c.getAddOnFeeFlag().equals("Y")?"是":"否"));
					row.add(new JsonPrimitive(c.getBonusFlag().equals("Y")?"是":"否"));
					row.add(new JsonPrimitive(c.getEnable().equals("Y")?"是":"否"));
					//row.add(new JsonPrimitive(c.getRegcode().equals(regcode)?"Y":"N"));
					row.add(new JsonPrimitive(c.getRegcode() + "_" + c.getSeqno() + "_" + c.getName() + "_" + c.getEnable() + "_" + c.getAddOnFeeFlag() + "_" + c.getBonusFlag() + "_" + (c.getRegcode().equals(regcode)?"Y":"N")));
					data.add(row);
				}
			} else {
				for (ItemBean c : items) {
					JsonArray row = new JsonArray();
					row.add(new JsonPrimitive(c.getName()));
					row.add(new JsonPrimitive(c.getAddOnFeeFlag().equals("Y")?"是":"否"));
					row.add(new JsonPrimitive(c.getEnable().equals("Y")?"是":"否"));
					//row.add(new JsonPrimitive(c.getRegcode().equals(regcode)?"Y":"N"));
					row.add(new JsonPrimitive(c.getRegcode() + "_" + c.getSeqno() + "_" + c.getName() + "_" + c.getEnable() + "_" + c.getAddOnFeeFlag() + "_" + (c.getRegcode().equals(regcode)?"Y":"N")));
					data.add(row);
				}
			}
			
			jsonResponse.add("aaData", data);
			res.setContentType("text/json;charset=UTF-8");
			res.getWriter().print(jsonResponse.toString());
		} catch (Exception e) {
			e.printStackTrace();
			res.setContentType("text/html");
		} finally {
			daoFactory.returnDao(DaoFactory.ITEM, dao);
		}
	}

	// =======================================================
	private ModelAndView createMAV(String mainPage, HttpSession ses) {
		return this.createMAV(mainPage, MODULE, ses);
	}

	private SalaryBean fetchSalary(HttpServletRequest req) {
		SalaryBean salary = new SalaryBean();
		ServletRequestDataBinder binder = new ServletRequestDataBinder(salary, "SalaryBean");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy/MM/dd hh:mm:ss"), true));
		binder.bind(req);
		salary.setRegcode(sessionHelper.getRegcode(req.getSession()));
		Vector itemp = new Vector();
		int totalp = req.getParameter("totalp") != null ? Integer.parseInt(req.getParameter("totalp")) : 0;
		for (int i = 0; i < totalp; i++) {
			String[] item = new String[3];
			item[0] = req.getParameter("pseq" + i) != null ? req.getParameter("pseq" + i) : "";
			item[2] = req.getParameter("pamount" + i) != null ? req.getParameter("pamount" + i) : "";
			itemp.add(item);			
		}
		salary.setItemp(itemp);
		Vector itemm = new Vector();
		int totalm = req.getParameter("totalm") != null ? Integer.parseInt(req.getParameter("totalm")) : 0;
		for (int i = 0; i < totalm; i++) {
			String[] item = new String[3];
			item[0] = req.getParameter("mseq" + i) != null ? req.getParameter("mseq" + i) : "";
			item[2] = req.getParameter("mamount" + i) != null ? req.getParameter("mamount" + i) : "";
			itemm.add(item);
			
			if (item[0].equals("14")) { // 代扣勞保
				salary.setLaborInsuranceFee(item[2]);
			}
			
			if (item[0].equals("14")) { // 代扣健保
				salary.setHealthInsuranceFee(item[2]);
			}
		}
		salary.setItemm(itemm);
		return salary;
	}
}
