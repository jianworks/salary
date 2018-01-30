package com.csjian.controller;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;

import com.csjian.util.*;
import com.csjian.form.*;
import com.csjian.model.bean.*;
import com.csjian.model.dao.*;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class InsuranceAddOnController extends BaseController {

	public static final String MODULE = "insuranceAddOn";
	public static final String PAGE_INCOMEEARNER = "incomeEarner";	
	public static final String PAGE_INCOMEEARNER_EDIT = "incomeEarnerEdit";	
	public static final String PAGE_INSURANCEADDONFEE = "insuranceAddOnFee";	
	public static final String PAGE_INSURANCEADDONFEE_EDIT = "insuranceAddOnFeeEdit";
	public static final String PAGE_DECLAREDATAQUERY = "declareDataQuery";
	public static final String PAGE_PRINTM0302 = "printM0302";
	public static final String PAGE_PRINTM0302YEARLY = "printM0302Yearly";
	public static final String PAGE_PRINTM0203 = "printM0203";
	public static final String PAGE_PRINTM0305 = "printM0305";
	public static final String PAGE_PRINTM0306 = "printM0306";
	public static final String PAGE_COMPANYHEALTHINFO = "companyHealthInfo";
	public static final String PAGE_OUTFILERECORD = "outFileRecord";
	
	public ModelAndView incomeEarner(HttpServletRequest req, HttpServletResponse res) {
		if (!sessionHelper.isLogin(req.getSession())) {
			return HomeController.createLoginMAV();
		}
		HttpSession ses = req.getSession();
		ModelAndView mav = createMAV(PAGE_INCOMEEARNER, req.getSession());
		return mav;
	}
	
	public ModelAndView incomeEarnerEdit(HttpServletRequest req, HttpServletResponse res) {
		if (!sessionHelper.isLogin(req.getSession())) {
			return HomeController.createLoginMAV();
		}
		HttpSession ses = req.getSession();
		ModelAndView mav = createMAV(PAGE_INCOMEEARNER_EDIT, req.getSession());
		String regcode = sessionHelper.getRegcode(ses);
		String year = (Calendar.getInstance()).get(Calendar.YEAR) + "";
		
		
		String keyId = req.getParameter("keyId")!=null ? req.getParameter("keyId") : "";
		if (keyId==null || keyId.equals("")) {
			mav.addObject("CREATE", "Y");
			mav.addObject("incomeEarner", new EmployeeBean());
		} else {
			try {
				String[] keys = keyId.split("_");
				String employeeNo = keys[0];
				String unicode = keys[1];				
				mav.addObject("CREATE", "N");
				if (employeeNo !=null && !employeeNo.equals("")) { // 員工
					EmployeeDao dao = (EmployeeDao) daoFactory.createDao(DaoFactory.EMPLOYEE);
					mav.addObject("incomeEarner", dao.retriveEmployee(regcode, employeeNo, year));
					daoFactory.returnDao(DaoFactory.EMPLOYEE, dao);
				} else { // 非員工
					InsuranceAddOnDao dao = (InsuranceAddOnDao) daoFactory.createDao(DaoFactory.INSURANCEADDON);
					mav.addObject("incomeEarner", dao.retrieveIncomeEarner(regcode, unicode));
					daoFactory.returnDao(DaoFactory.INSURANCEADDON, dao);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
		return mav;
	}
	
	public ModelAndView updateIncomeEarner(HttpServletRequest req, HttpServletResponse res) {
		if (!sessionHelper.isLogin(req.getSession())) {
			return HomeController.createLoginMAV();
		}
		EmployeeBean employee = this.fetchEmployee(req);
		HttpSession ses = req.getSession();
		ModelAndView mav = null;
		try {
			if (req.getParameter("isemployee").equals("N")) { // 不是員工
				InsuranceAddOnDao dao = (InsuranceAddOnDao) daoFactory.createDao(DaoFactory.INSURANCEADDON);
				if (req.getParameter("CREATE").equals("Y")) {
					if (dao.insertIncomeEarner(employee)<=0) {
						mav = createMAV(PAGE_INCOMEEARNER_EDIT, req.getSession());
						mav.addObject("incomeEarner", employee);
						mav.addObject("CREATE", "Y");					
						mav.addObject("msg", "新增所得人資料失敗! 資料已存在 !");
					} else {
						mav = this.incomeEarner(req, res);
						mav.addObject("msg", "新增所得人資料成功!");
					}
				} else {
					dao.updateIncomeEarner(employee);
					mav = this.incomeEarner(req, res);
					mav.addObject("msg", "更新所得人資料成功!");
				}
				daoFactory.returnDao(DaoFactory.INSURANCEADDON, dao);
			} else { // 是員工
				EmployeeDao dao = (EmployeeDao) daoFactory.createDao(DaoFactory.EMPLOYEE);
				if (req.getParameter("CREATE").equals("Y")) {
					if (dao.insert(employee)<0) {
						mav = createMAV(PAGE_INCOMEEARNER_EDIT, req.getSession());
						mav.addObject("incomeEarner", employee);
						mav.addObject("CREATE", "Y");					
						mav.addObject("msg", "新增所得人資料失敗! 員工資料已存在 !");
					} else {
						mav = this.incomeEarner(req, res);
						mav.addObject("msg", "新增所得人資料成功!");
					}
				} else {
					dao.update(employee);
					mav = this.incomeEarner(req, res);
					mav.addObject("msg", "更新所得人資料成功!");
				}
				daoFactory.returnDao(DaoFactory.EMPLOYEE, dao);
			}
		} catch (Exception e) {
			mav = createMAV(PAGE_INCOMEEARNER_EDIT, req.getSession());
			mav.addObject("incomeEarner", employee);			
			mav.addObject("CREATE", "Y");	
			mav.addObject("msg", "新增所得人資料發生錯誤!");
			e.printStackTrace();
		}
		return mav;
	}
	
	public ModelAndView removeIncomeEarner(HttpServletRequest req, HttpServletResponse res) {
		if (!sessionHelper.isLogin(req.getSession())) {
			return HomeController.createLoginMAV();
		}
		EmployeeBean employee = this.fetchEmployee(req);
		HttpSession ses = req.getSession();
		ModelAndView mav = null;
		try {
			if (req.getParameter("isemployee").equals("N")) { // 不是員工
				InsuranceAddOnDao dao = (InsuranceAddOnDao) daoFactory.createDao(DaoFactory.INSURANCEADDON);
				dao.removeIncomeEarner(employee.getRegcode(), employee.getUnicode());
				
				mav = this.incomeEarner(req, res);
				mav.addObject("msg", "刪除所得人資料成功!");
				daoFactory.returnDao(DaoFactory.INSURANCEADDON, dao);
			} else { // 是員工
				EmployeeDao dao = (EmployeeDao) daoFactory.createDao(DaoFactory.EMPLOYEE);
				if (!dao.hasSalaryData(employee.getRegcode(), employee.getEmployeeno(), employee.getAyear())) {
					dao.remove(employee.getRegcode(), employee.getEmployeeno(), employee.getAyear());
					mav = this.incomeEarner(req, res);
					mav.addObject("msg", "刪除所得人資料成功!");
				} else {
					mav = createMAV(PAGE_INCOMEEARNER_EDIT, req.getSession());
					mav.addObject("incomeEarner", employee);
					mav.addObject("CREATE", "N");
					mav.addObject("msg", "刪除失敗! 員工有薪資資料 !");
				}
				daoFactory.returnDao(DaoFactory.EMPLOYEE, dao);
			}
		} catch (Exception e) {
			mav = createMAV(PAGE_INCOMEEARNER_EDIT, req.getSession());
			mav.addObject("incomeEarner", employee);			
			mav.addObject("CREATE", "N");	
			mav.addObject("msg", "刪除所得人資料發生錯誤!");
			e.printStackTrace();
		}
		return mav;
	}
	
	
	public void incomeEarnerDataTable(HttpServletRequest req, HttpServletResponse res) {
		InsuranceAddOnDao dao = (InsuranceAddOnDao) daoFactory.createDao(DaoFactory.INSURANCEADDON);
		try {
			HttpSession ses = req.getSession();

			String regcode = sessionHelper.getRegcode(ses);
			String year = (Calendar.getInstance()).get(Calendar.YEAR) + "";

			JQueryDataTableParamModel param = DataTablesParamUtility.getParam(req);

			//String sEcho = param.sEcho;
			int iTotalRecords; // total number of records (unfiltered)
			//int iTotalDisplayRecords; // value will be set when code filters
			JsonArray data = new JsonArray(); // data that will be shown in the
												// table

			List<EmployeeBean> incomeEarners = dao.listIncomeEarners(regcode, year);
			iTotalRecords = incomeEarners.size();

			//final int sortColumnIndex = param.iSortColumnIndex;
			//final int sortDirection = param.sSortDirection.equals("asc") ? -1 : 1;

			JsonObject jsonResponse = new JsonObject();
			//jsonResponse.addProperty("sEcho", sEcho);
			jsonResponse.addProperty("iTotalRecords", iTotalRecords);

			for (EmployeeBean c : incomeEarners) {
				JsonArray row = new JsonArray();
				row.add(new JsonPrimitive(c.getEmployeeno() + "_" + c.getUnicode()));
				row.add(new JsonPrimitive(c.getUnicode()));
				row.add(new JsonPrimitive(c.getName()));
				row.add(new JsonPrimitive(c.getAddress()));
				row.add(new JsonPrimitive(c.getEmployeeno() + "_" + c.getUnicode()));
				data.add(row);
			}
			jsonResponse.add("aaData", data);
			res.setContentType("text/json;charset=UTF-8");
			res.getWriter().print(jsonResponse.toString());
		} catch (Exception e) {
			e.printStackTrace();
			res.setContentType("text/html");
		} finally {
			daoFactory.returnDao(DaoFactory.INSURANCEADDON, dao);
		}
	}
	
	public void findIncomeEarner(HttpServletRequest req, HttpServletResponse res) {
		InsuranceAddOnDao dao = (InsuranceAddOnDao) daoFactory.createDao(DaoFactory.INSURANCEADDON);
		try {
			HttpSession ses = req.getSession();

			String regcode = sessionHelper.getRegcode(ses);
			String unicode = req.getParameter("unicode")!=null ? req.getParameter("unicode") : "";
			String name = req.getParameter("name")!=null ? req.getParameter("name") : "";
			EmployeeBean incomeEarner = dao.findIncomeEarner(regcode, unicode, name);
			Gson gson = new Gson();
			res.setContentType("text/json;charset=UTF-8");
			res.getWriter().print(gson.toJson(incomeEarner));
		} catch (Exception e) {
			e.printStackTrace();
			res.setContentType("text/html");
		} finally {
			daoFactory.returnDao(DaoFactory.INSURANCEADDON, dao);
		}
	}
	
	public void totalBonusAmount(HttpServletRequest req, HttpServletResponse res) {
		InsuranceAddOnDao dao = (InsuranceAddOnDao) daoFactory.createDao(DaoFactory.INSURANCEADDON);
		try {
			HttpSession ses = req.getSession();

			String regcode = sessionHelper.getRegcode(ses);
			String unicode = req.getParameter("unicode")!=null ? req.getParameter("unicode") : "";
			String serialNo = req.getParameter("serialNo")!=null ? req.getParameter("serialNo") : "";
			String incomeDate = req.getParameter("incomeDate")!=null ? StringUtils.twToAd(req.getParameter("incomeDate")) : "";
			int amount = dao.totalBonusAmount(regcode, unicode, incomeDate, serialNo);
			res.setContentType("text/json;charset=UTF-8");
			System.out.println("amount :" + amount);
			res.getWriter().print(amount);
		} catch (Exception e) {
			e.printStackTrace();
			res.setContentType("text/html");
		} finally {
			daoFactory.returnDao(DaoFactory.INSURANCEADDON, dao);
		}
	}
	
	public void bonusAmount(HttpServletRequest req, HttpServletResponse res) {
		InsuranceAddOnDao dao = (InsuranceAddOnDao) daoFactory.createDao(DaoFactory.INSURANCEADDON);
		try {
			HttpSession ses = req.getSession();

			String regcode = sessionHelper.getRegcode(ses);
			String unicode = req.getParameter("unicode")!=null ? req.getParameter("unicode") : "";
			String incomeDate = req.getParameter("incomeDate")!=null ? StringUtils.twToAd(req.getParameter("incomeDate")) : Calendar.getInstance().get(Calendar.YEAR) + "/" + (Calendar.getInstance().get(Calendar.MONTH) + 1) + "/1";
			int amount = dao.bonusAmount(regcode, unicode, incomeDate, Integer.parseInt(ses.getAttribute("salaryShift")+""));
			res.setContentType("text/json;charset=UTF-8");
			res.getWriter().print(amount);
		} catch (Exception e) {
			e.printStackTrace();
			res.setContentType("text/html");
		} finally {
			daoFactory.returnDao(DaoFactory.INSURANCEADDON, dao);
		}
	}
	
	public ModelAndView insuranceAddOnFee(HttpServletRequest req, HttpServletResponse res) {
		if (!sessionHelper.isLogin(req.getSession())) {
			return HomeController.createLoginMAV();
		}
		HttpSession ses = req.getSession();
		FilterBean filter = new FilterBean();
		if (ses.getAttribute("FILTER")!=null && ((FilterBean)ses.getAttribute("FILTER")).getPage().equals("PAGE_INSURANCEADDONFEE")) {
			filter = (FilterBean)ses.getAttribute("FILTER");
		} else {
			filter.setPage("PAGE_INSURANCEADDONFEE");
			Calendar cal = Calendar.getInstance();
			filter.setIncomePeriod((cal.get(Calendar.YEAR)-1911) + "-" + ((cal.get(Calendar.MONTH) + 1) > 9 ?"":"0") + (cal.get(Calendar.MONTH) + 1));
			ses.setAttribute("FILTER", filter);
		}
		CompanyDao dao = (CompanyDao) daoFactory.createDao(DaoFactory.COMPANY);
		ModelAndView mav = createMAV(PAGE_INSURANCEADDONFEE, req.getSession());
		mav.addObject("filter", filter);
		return mav;
	}
	
	public ModelAndView insuranceAddOnFeeEdit(HttpServletRequest req, HttpServletResponse res) {
		if (!sessionHelper.isLogin(req.getSession())) {
			return HomeController.createLoginMAV();
		}
		ModelAndView mav = createMAV(PAGE_INSURANCEADDONFEE_EDIT, req.getSession());
	
		String serialNo = req.getParameter("serialNo")!=null ? req.getParameter("serialNo") : "";
		if (serialNo==null || serialNo.equals("")) {
			mav.addObject("insuranceAddOnFee", new InsuranceAddOnFeeBean());
		} else {
			try {
				InsuranceAddOnDao dao = (InsuranceAddOnDao) daoFactory.createDao(DaoFactory.INSURANCEADDON);
				mav.addObject("insuranceAddOnFee", dao.retrieveInsuranceAddOnFee(Integer.parseInt(serialNo)));
				daoFactory.returnDao(DaoFactory.INSURANCEADDON, dao);
			} catch (Exception e) {
				e.printStackTrace();
			}			
		}
		return mav;
	}
	
	public ModelAndView updateInsuranceAddOnFee(HttpServletRequest req, HttpServletResponse res) {
		if (!sessionHelper.isLogin(req.getSession())) {
			return HomeController.createLoginMAV();
		}
		InsuranceAddOnFeeBean insuranceAddOnFee = this.fetchInsuranceAddOnFee(req);
		ModelAndView mav = null;
		try {
			InsuranceAddOnDao dao = (InsuranceAddOnDao) daoFactory.createDao(DaoFactory.INSURANCEADDON);
			if (insuranceAddOnFee.getSerialNo() == 0) {
				if (dao.insertInsuranceAddOnFee(insuranceAddOnFee)<=0) {
					mav = createMAV(PAGE_INSURANCEADDONFEE_EDIT, req.getSession());
					mav.addObject("insuranceAddOnFee", insuranceAddOnFee);
					mav.addObject("CREATE", "Y");					
					mav.addObject("msg", "新增扣繳補充保費資料失敗!");
				} else {
					mav = this.insuranceAddOnFee(req, res);
					mav.addObject("msg", "新增扣繳補充保費資料成功!");
				}
			} else {
				dao.updateInsuranceAddOnFee(insuranceAddOnFee);
				mav = this.insuranceAddOnFee(req, res);
				mav.addObject("msg", "更新扣繳補充保費資料成功!");
			}
			daoFactory.returnDao(DaoFactory.INSURANCEADDON, dao);
		} catch (Exception e) {
			mav = createMAV(PAGE_INSURANCEADDONFEE_EDIT, req.getSession());
			mav.addObject("insuranceAddOnFee", insuranceAddOnFee);	
			if (req.getParameter("CREATE").equals("Y")) {
				mav.addObject("CREATE", "Y");	
				mav.addObject("msg", "新增扣繳補充保費資料發生錯誤!");
			} else {
				mav.addObject("CREATE", "N");	
				mav.addObject("msg", "更新扣繳補充保費資料發生錯誤!");
			}
			e.printStackTrace();
		}
		return mav;
	}
	
	public ModelAndView removeInsuranceAddOnFee(HttpServletRequest req, HttpServletResponse res) {
		if (!sessionHelper.isLogin(req.getSession())) {
			return HomeController.createLoginMAV();
		}
		InsuranceAddOnFeeBean insuranceAddOnFee = this.fetchInsuranceAddOnFee(req);
		ModelAndView mav = null;
		try {
			InsuranceAddOnDao dao = (InsuranceAddOnDao) daoFactory.createDao(DaoFactory.INSURANCEADDON);
			dao.removeInsuranceAddOnFee(Integer.parseInt(req.getParameter("serialNo")));
							
			mav = this.insuranceAddOnFee(req, res);
			mav.addObject("msg", "刪除扣繳補充保費資料成功!");
			daoFactory.returnDao(DaoFactory.INSURANCEADDON, dao);
		} catch (Exception e) {
			mav = createMAV(PAGE_INSURANCEADDONFEE_EDIT, req.getSession());
			mav.addObject("insuranceAddOnFee", insuranceAddOnFee);			
			mav.addObject("CREATE", "N");	
			mav.addObject("msg", "刪除扣繳補充保費資料發生錯誤!");
			e.printStackTrace();
		}
		return mav;
	}
	
	
	public void insuranceAddOnFeeDataTable(HttpServletRequest req, HttpServletResponse res) {
		InsuranceAddOnDao dao = (InsuranceAddOnDao) daoFactory.createDao(DaoFactory.INSURANCEADDON);
		try {
			HttpSession ses = req.getSession();

			String regcode = sessionHelper.getRegcode(ses);
			List<String> criteries = new ArrayList<String>();

			FilterBean filter = new FilterBean(); 
			filter.setPage("PAGE_INSURANCEADDONFEE");
			if (req.getParameter("incomePeriod")!=null && ! req.getParameter("incomePeriod").equals("")) {
				filter.setIncomePeriod(req.getParameter("incomePeriod"));
				String period = (Integer.parseInt(req.getParameter("incomePeriod").substring(0, 3)) + 1911) + "/" + req.getParameter("incomePeriod").substring(4);
				criteries.add("SUBSTRING(CONVERT(varchar(12) , incomedate, 111 ), 1, 7) ='" + period + "' ");
			}
			if (req.getParameter("incomeType")!=null && ! req.getParameter("incomeType").equals("")) {
				filter.setIncomeType(req.getParameter("incomeType"));
				criteries.add("incomeType ='" + filter.getIncomeType() + "' ");
			}
			if (req.getParameter("unicode")!=null && ! req.getParameter("unicode").equals("")) {
				filter.setUnicode(req.getParameter("unicode"));
				criteries.add("unicode ='" + filter.getUnicode() + "' ");
			}
			if (req.getParameter("name")!=null && ! req.getParameter("name").equals("")) {
				filter.setName(req.getParameter("name"));
				criteries.add("name ='" + filter.getName() + "' ");
			}
			ses.setAttribute("FILTER", filter);
			
			//String sEcho = param.sEcho;
			int iTotalRecords; // total number of records (unfiltered)
			JsonArray data = new JsonArray(); // data that will be shown in the
												// table

			List<InsuranceAddOnFeeBean> insuranceAddOnFees = dao.listInsuranceAddOnFees(regcode, criteries);
			iTotalRecords = insuranceAddOnFees.size();
			JsonObject jsonResponse = new JsonObject();
			jsonResponse.addProperty("iTotalRecords", iTotalRecords);

			for (InsuranceAddOnFeeBean c : insuranceAddOnFees) {
				JsonArray row = new JsonArray();
				row.add(new JsonPrimitive(c.getSerialNo() + ""));
				row.add(new JsonPrimitive(StringUtils.adToTw(c.getIncomeDate())));				
				row.add(new JsonPrimitive(c.getIncomeTypeName()));
				row.add(new JsonPrimitive(c.getUnicode()));
				row.add(new JsonPrimitive(c.getName()));
				row.add(new JsonPrimitive(c.getHealthInsuranceSalary()));
				row.add(new JsonPrimitive(c.getIncomeAmount()));
				row.add(new JsonPrimitive("0"));	// 雇主身份加保之總投保金額
				row.add(new JsonPrimitive(c.getAccumulatedBonusAmount()));
				row.add(new JsonPrimitive(c.getInsuranceAddOnFee()));
				row.add(new JsonPrimitive(c.getSerialNo() + ""));
				data.add(row);
			}
			jsonResponse.add("aaData", data);
			System.out.println("send back" + jsonResponse.toString());
			daoFactory.returnDao(DaoFactory.INSURANCEADDON, dao);
			res.setContentType("text/json;charset=UTF-8");
			res.getWriter().print(jsonResponse.toString());
		} catch (Exception e) {
			e.printStackTrace();
			res.setContentType("text/html");
		}
		daoFactory.returnDao(DaoFactory.INSURANCEADDON, dao);
	}
	
	public void incomeEarnerAutoCompleteList(HttpServletRequest req, HttpServletResponse res) {
		InsuranceAddOnDao dao = (InsuranceAddOnDao) daoFactory.createDao(DaoFactory.INSURANCEADDON);
		try {
			HttpSession ses = req.getSession();

			String regcode = sessionHelper.getRegcode(ses);
			String year = (Calendar.getInstance()).get(Calendar.YEAR) + "";
			List<AutocompleteItemBean> items = dao.incomeEarnerAutoCompleteList(regcode, year);
			Gson gson = new Gson();
			
			daoFactory.returnDao(DaoFactory.INSURANCEADDON, dao);
			res.setContentType("text/json;charset=UTF-8");
			res.getWriter().print(gson.toJson(items));
		} catch (Exception e) {
			e.printStackTrace();
			res.setContentType("text/html");
		}
		daoFactory.returnDao(DaoFactory.INSURANCEADDON, dao);
	}
	
	public void exportIncomeEarnerToCSV(HttpServletRequest req, HttpServletResponse res) {
		InsuranceAddOnDao dao = (InsuranceAddOnDao) daoFactory.createDao(DaoFactory.INSURANCEADDON);
		try {
			HttpSession ses = req.getSession();

			String regcode = sessionHelper.getRegcode(ses);
			String year = (Calendar.getInstance()).get(Calendar.YEAR) + "";
			
			String[] serialNos = req.getParameter("serialNos").split(",");
			List<EmployeeBean> incomeEarners = new ArrayList<EmployeeBean>();
			for(int i=0; i<serialNos.length; i++) {
				incomeEarners.add(dao.retrieveIncomeEarner(regcode, serialNos[i]));
			}
			res.setHeader("Content-Disposition", "attachment;filename=\""+ "M0401" + ".csv" + "\"");
			res.setContentType("text/plain;charset=big5");
			PrintWriter writer = res.getWriter();
			EmployeeBean incomeEarner = null;
			writer.println("統一編號,所得人身分證號,所得人姓名,所得人地址");			
			for (int i=0; i<incomeEarners.size(); i++){
				incomeEarner = incomeEarners.get(i);
				writer.println(regcode + "," + incomeEarner.getUnicode() + "," + incomeEarner.getName() + "," + incomeEarner.getAddress());
			}
			writer.flush();
		} catch (Exception e) {
			e.printStackTrace();
			res.setContentType("text/html");
		}
		daoFactory.returnDao(DaoFactory.INSURANCEADDON, dao);
	}
	
	public void exportInsuranceAddOnFeeToCSV(HttpServletRequest req, HttpServletResponse res) {
		InsuranceAddOnDao dao = (InsuranceAddOnDao) daoFactory.createDao(DaoFactory.INSURANCEADDON);
		try {
			HttpSession ses = req.getSession();

			String regcode = sessionHelper.getRegcode(ses);
			CompanyBean company = dao.retrieveCompanyHealthInfo(regcode);
			String year = (Calendar.getInstance()).get(Calendar.YEAR) + "";
			String[] serialNos = req.getParameter("serialNos").split(",");
			List<InsuranceAddOnFeeBean> insuranceAddOnFees = new ArrayList<InsuranceAddOnFeeBean>();
			for(int i=0; i<serialNos.length; i++) {
				insuranceAddOnFees.add(dao.retrieveInsuranceAddOnFee(Integer.parseInt(serialNos[i])));
			}

			res.setHeader("Content-Disposition", "attachment;filename=\""+ "M0402" + ".csv" + "\"");
			res.setContentType("text/plain;charset=big5");
			PrintWriter writer = res.getWriter();
			InsuranceAddOnFeeBean insuranceAddOnFee = null;
			writer.println("投保單位代碼,統一編號,所得人身分證號,姓名,所得給付日期,所得類別,所得(收入)給付金額,給付當月投保金額,本次股利所屬期間，以雇主身分加保之投保總金額,股利註記,信託註記,扣取時可扣抵稅額,年度確定可扣抵稅額,除權(息)基準日");			
			for (int i=0; i<insuranceAddOnFees.size(); i++){
				insuranceAddOnFee = insuranceAddOnFees.get(i);
				writer.println(company.getHealthCode() + "," + regcode + ", " + insuranceAddOnFee.getUnicode() + "," + insuranceAddOnFee.getName() + "," + StringUtils.adToTw(insuranceAddOnFee.getIncomeDate()) + "," + 
				insuranceAddOnFee.getIncomeType() + "," + insuranceAddOnFee.getIncomeAmount() + "," + insuranceAddOnFee.getHealthInsuranceSalary() + "," + 
				insuranceAddOnFee.getBossHealthInsuranceAmount() + "," + insuranceAddOnFee.getStockNote() + "," + 
				insuranceAddOnFee.getTrustNote() + "," + insuranceAddOnFee.getICAAmount() + "," + insuranceAddOnFee.getAnnualICAAmount() + "," + 
				StringUtils.adToTw(insuranceAddOnFee.getExcludeDate()));
			}
			writer.flush();
		} catch (Exception e) {
			e.printStackTrace();
			res.setContentType("text/html");
		}
		daoFactory.returnDao(DaoFactory.INSURANCEADDON, dao);
	}
	
	// =======================================================
	public ModelAndView printM0302(HttpServletRequest req, HttpServletResponse res) {
		if (!sessionHelper.isLogin(req.getSession())) {
			return HomeController.createLoginMAV();
		}
		HttpSession ses = req.getSession();
		String regcode = sessionHelper.getRegcode(ses);
		String err = this.validateCompanyInfo(regcode);
		if (err !=null && !err.equals("")) {
			return this.forceToSetcompanyHealthInfo(req, res, err);
		}
		FilterBean filter = new FilterBean();
		if (ses.getAttribute("FILTER")!=null && ((FilterBean)ses.getAttribute("FILTER")).getPage().equals("PAGE_PRINTM0302")) {
			filter = (FilterBean)ses.getAttribute("FILTER");
		} else {
			filter.setPage("PAGE_PRINTM0302");
			Calendar cal = Calendar.getInstance();
			filter.setIncomePeriod((cal.get(Calendar.YEAR)-1911) + "-" + ((cal.get(Calendar.MONTH) + 1) > 9 ?"":"0") + (cal.get(Calendar.MONTH) + 1));
			ses.setAttribute("FILTER", filter);
		}
		ModelAndView mav = createMAV(PAGE_PRINTM0302, req.getSession());
		mav.addObject("filter", filter);
		return mav;
	}
	
	public void m0302DataTable(HttpServletRequest req, HttpServletResponse res) {
		InsuranceAddOnDao dao = (InsuranceAddOnDao) daoFactory.createDao(DaoFactory.INSURANCEADDON);
		try {
			HttpSession ses = req.getSession();

			String regcode = sessionHelper.getRegcode(ses);
			List<String> criteries = new ArrayList<String>();

			FilterBean filter = new FilterBean(); 
			filter.setPage("PAGE_PRINTM0302");
			if (req.getParameter("incomePeriod")!=null && ! req.getParameter("incomePeriod").equals("")) {
				filter.setIncomePeriod(req.getParameter("incomePeriod"));
				String period = (Integer.parseInt(req.getParameter("incomePeriod").substring(0, 3)) + 1911) + "/" + req.getParameter("incomePeriod").substring(4);
				criteries.add("SUBSTRING(CONVERT(varchar(12) , incomedate, 111 ), 1, 7) ='" + period + "' ");
			}
			if (req.getParameter("incomeType")!=null && ! req.getParameter("incomeType").equals("")) {
				filter.setIncomeType(req.getParameter("incomeType"));
				criteries.add("incomeType ='" + filter.getIncomeType() + "' ");
			}
			if (req.getParameter("unicode")!=null && ! req.getParameter("unicode").equals("")) {
				filter.setUnicode(req.getParameter("unicode"));
				criteries.add("unicode ='" + filter.getUnicode() + "' ");
			}
			if (req.getParameter("name")!=null && ! req.getParameter("name").equals("")) {
				filter.setName(req.getParameter("name"));
				criteries.add("name ='" + filter.getName() + "' ");
			}
			ses.setAttribute("FILTER", filter);
			
			//String sEcho = param.sEcho;
			int iTotalRecords; // total number of records (unfiltered)
			JsonArray data = new JsonArray(); // data that will be shown in the
												// table

			List<InsuranceAddOnFeeBean> insuranceAddOnFees = dao.listInsuranceAddOnFees(regcode, criteries);
			iTotalRecords = insuranceAddOnFees.size();
			JsonObject jsonResponse = new JsonObject();
			jsonResponse.addProperty("iTotalRecords", iTotalRecords);

			for (InsuranceAddOnFeeBean c : insuranceAddOnFees) {
				JsonArray row = new JsonArray();
				row.add(new JsonPrimitive(c.getSerialNo() + ""));
				row.add(new JsonPrimitive(StringUtils.adToTw(c.getIncomeDate())));				
				row.add(new JsonPrimitive(c.getIncomeTypeName()));
				row.add(new JsonPrimitive(c.getUnicode()));
				row.add(new JsonPrimitive(c.getName()));
				row.add(new JsonPrimitive(c.getIncomeAmount()));
				row.add(new JsonPrimitive(c.getInsuranceAddOnFee()));
				row.add(new JsonPrimitive(c.getSerialNo() + ""));
				data.add(row);
			}
			jsonResponse.add("aaData", data);
			System.out.println("send back" + jsonResponse.toString());
			daoFactory.returnDao(DaoFactory.INSURANCEADDON, dao);
			res.setContentType("text/json;charset=UTF-8");
			res.getWriter().print(jsonResponse.toString());
		} catch (Exception e) {
			e.printStackTrace();
			res.setContentType("text/html");
		}
		daoFactory.returnDao(DaoFactory.INSURANCEADDON, dao);
	}
	
	public void doPrintM0302(HttpServletRequest req, HttpServletResponse res) {
		System.out.println("create M0302!");
		InsuranceAddOnDao dao = (InsuranceAddOnDao) daoFactory.createDao(DaoFactory.INSURANCEADDON);
		try {
			HttpSession ses = req.getSession();
			String regcode = sessionHelper.getRegcode(ses);
			CompanyBean company = dao.retrieveCompanyHealthInfo(regcode);
			String[] serialNos = req.getParameter("serialNos").split(",");
			Vector<InsuranceAddOnFeeBean> insuranceAddOnFees = new Vector<InsuranceAddOnFeeBean>();
			for(int i=0; i<serialNos.length; i++) {
				insuranceAddOnFees.add(dao.retrieveInsuranceAddOnFee(Integer.parseInt(serialNos[i])));
			}
			
			res.setHeader("Content-Transfer-Encoding","binary");
		    res.setHeader("Content-Disposition","attachment;filename=\"M0302.pdf" +"\"");
		    res.setContentType("application/pdf");

		    OutputStream os = res.getOutputStream();
		    this.getServletContext().getRealPath("/WEB-INF/blankform/M0302.pdf");
		    (new M0302()).renderForm(regcode, insuranceAddOnFees, company, this.getServletContext().getRealPath("/WEB-INF/blankform/M0302.pdf"), os);
		    os.flush();
		    os.close();
		} catch (Exception e) {
			e.printStackTrace();
			res.setContentType("text/html");
		} finally {
			daoFactory.returnDao(DaoFactory.INSURANCEADDON, dao);
		}
	}
	
	public ModelAndView printM0302Yearly(HttpServletRequest req, HttpServletResponse res) {
		if (!sessionHelper.isLogin(req.getSession())) {
			return HomeController.createLoginMAV();
		}
		HttpSession ses = req.getSession();
		String regcode = sessionHelper.getRegcode(ses);
		String err = this.validateCompanyInfo(regcode);
		if (err !=null && !err.equals("")) {
			return this.forceToSetcompanyHealthInfo(req, res, err);
		}
		
		FilterBean filter = new FilterBean();
		if (ses.getAttribute("FILTER")!=null && ((FilterBean)ses.getAttribute("FILTER")).getPage().equals("PAGE_PRINTM0302YEARLY")) {
			filter = (FilterBean)ses.getAttribute("FILTER");
		} else {
			filter.setPage("PAGE_PRINTM0302YEARLY");
			Calendar cal = Calendar.getInstance();
			filter.setIncomePeriod((cal.get(Calendar.YEAR)-1911)+"");
			ses.setAttribute("FILTER", filter);
		}
		ModelAndView mav = createMAV(PAGE_PRINTM0302YEARLY, req.getSession());
		mav.addObject("filter", filter);
		return mav;
	}
	
	public void m0302YearlyDataTable(HttpServletRequest req, HttpServletResponse res) {
		InsuranceAddOnDao dao = (InsuranceAddOnDao) daoFactory.createDao(DaoFactory.INSURANCEADDON);
		try {
			HttpSession ses = req.getSession();

			String regcode = sessionHelper.getRegcode(ses);
			List<String> criteries = new ArrayList<String>();

			FilterBean filter = new FilterBean(); 
			filter.setPage("PAGE_PRINTM0302YEARLY");
			if (req.getParameter("incomePeriod")!=null && ! req.getParameter("incomePeriod").equals("")) {
				filter.setIncomePeriod(req.getParameter("incomePeriod"));
				criteries.add("SUBSTRING(CONVERT(varchar(12) , incomedate, 111 ), 1, 4) ='" + (Integer.parseInt(req.getParameter("incomePeriod")) + 1911) + "' ");
			}
			if (req.getParameter("incomeType")!=null && ! req.getParameter("incomeType").equals("")) {
				filter.setIncomeType(req.getParameter("incomeType"));
				criteries.add("incomeType ='" + filter.getIncomeType() + "' ");
			}
			if (req.getParameter("unicode")!=null && ! req.getParameter("unicode").equals("")) {
				filter.setUnicode(req.getParameter("unicode"));
				criteries.add("unicode ='" + filter.getUnicode() + "' ");
			}
			if (req.getParameter("name")!=null && ! req.getParameter("name").equals("")) {
				filter.setName(req.getParameter("name"));
				criteries.add("name ='" + filter.getName() + "' ");
			}
			ses.setAttribute("FILTER", filter);
			
			//String sEcho = param.sEcho;
			int iTotalRecords; // total number of records (unfiltered)
			JsonArray data = new JsonArray(); // data that will be shown in the
												// table

			List<InsuranceAddOnFeeBean> insuranceAddOnFees = dao.listInsuranceAddOnFeeSummarys(regcode, criteries);
			iTotalRecords = insuranceAddOnFees.size();
			JsonObject jsonResponse = new JsonObject();
			jsonResponse.addProperty("iTotalRecords", iTotalRecords);

			for (InsuranceAddOnFeeBean c : insuranceAddOnFees) {
				JsonArray row = new JsonArray();
				row.add(new JsonPrimitive(c.getIncomeDate() + "_" + c.getUnicode() + "_" + c.getIncomeType()));
				row.add(new JsonPrimitive(Integer.parseInt(c.getIncomeDate())-1911));				
				row.add(new JsonPrimitive(c.getIncomeTypeName()));
				row.add(new JsonPrimitive(c.getUnicode()));
				row.add(new JsonPrimitive(c.getName()));
				row.add(new JsonPrimitive(c.getIncomeAmount()));
				row.add(new JsonPrimitive(c.getInsuranceAddOnFee()));
				data.add(row);
			}
			jsonResponse.add("aaData", data);
			System.out.println("send back" + jsonResponse.toString());
			daoFactory.returnDao(DaoFactory.INSURANCEADDON, dao);
			res.setContentType("text/json;charset=UTF-8");
			res.getWriter().print(jsonResponse.toString());
		} catch (Exception e) {
			e.printStackTrace();
			res.setContentType("text/html");
		}
		daoFactory.returnDao(DaoFactory.INSURANCEADDON, dao);
	}
	
	public void doPrintM0302Yearly(HttpServletRequest req, HttpServletResponse res) {
		InsuranceAddOnDao dao = (InsuranceAddOnDao) daoFactory.createDao(DaoFactory.INSURANCEADDON);
		try {
			HttpSession ses = req.getSession();
			String regcode = sessionHelper.getRegcode(ses);
			CompanyBean company = dao.retrieveCompanyHealthInfo(regcode);
			String[] serialNos = req.getParameter("serialNos").split(",");
			Vector<InsuranceAddOnFeeBean> insuranceAddOnFees = new Vector<InsuranceAddOnFeeBean>();
			String[] para = null;
			for(int i=0; i<serialNos.length; i++) {
				para = serialNos[i].split("_"); 
				insuranceAddOnFees.add(dao.retrieveInsuranceAddOnFeeSummary(regcode, para[0], para[1], para[2]));
			}
			
			res.setHeader("Content-Transfer-Encoding","binary");
		    res.setHeader("Content-Disposition","attachment;filename=\"M0302.pdf" +"\"");
		    res.setContentType("application/pdf");

		    OutputStream os = res.getOutputStream();
		    (new M0302()).renderForm(regcode, insuranceAddOnFees, company, this.getServletContext().getRealPath("/WEB-INF/blankform/M0302.pdf"), os);
		    os.flush();
		    os.close();
		} catch (Exception e) {
			e.printStackTrace();
			res.setContentType("text/html");
		} finally {
			daoFactory.returnDao(DaoFactory.INSURANCEADDON, dao);
		}
	}
	
	public ModelAndView printM0203(HttpServletRequest req, HttpServletResponse res) {
		if (!sessionHelper.isLogin(req.getSession())) {
			return HomeController.createLoginMAV();
		}
		HttpSession ses = req.getSession();
		String regcode = sessionHelper.getRegcode(ses);
		String err = this.validateCompanyInfo(regcode);
		if (err !=null && !err.equals("")) {
			return this.forceToSetcompanyHealthInfo(req, res, err);
		}
		
		FilterBean filter = new FilterBean();
		if (ses.getAttribute("FILTER")!=null && ((FilterBean)ses.getAttribute("FILTER")).getPage().equals("PAGE_PRINTM0203")) {
			filter = (FilterBean)ses.getAttribute("FILTER");
		} else {
			filter.setPage("PAGE_PRINTM0203");
			Calendar cal = Calendar.getInstance();
			filter.setFromYear((cal.get(Calendar.YEAR)-1911)+"");
			filter.setFromMonth(((cal.get(Calendar.MONTH) + 1) > 9 ?"":"0") + (cal.get(Calendar.MONTH) + 1));
			filter.setToMonth(((cal.get(Calendar.MONTH) + 1) > 9 ?"":"0") + (cal.get(Calendar.MONTH) + 1));
			ses.setAttribute("FILTER", filter);
		}
		ModelAndView mav = createMAV(PAGE_PRINTM0203, req.getSession());
		mav.addObject("filter", filter);
		return mav;
	}

	public void calcM0203Data(HttpServletRequest req, HttpServletResponse res) {
		InsuranceAddOnDao dao = (InsuranceAddOnDao) daoFactory.createDao(DaoFactory.INSURANCEADDON);
		try {
			HttpSession ses = req.getSession();

			String regcode = sessionHelper.getRegcode(ses);
			FilterBean filter = new FilterBean(); 
			filter.setPage("PAGE_PRINTM0203");
			String fromYear = req.getParameter("fromYear")!=null ? req.getParameter("fromYear") : "";
			filter.setFromYear(fromYear);
			String fromMonth = req.getParameter("fromMonth")!=null ? req.getParameter("fromMonth") : "";
			filter.setFromMonth(fromMonth);
			String toMonth = req.getParameter("toMonth")!=null ? req.getParameter("toMonth") : "";
			filter.setToMonth(toMonth);
			ses.setAttribute("FILTER", filter);
			
			M0203DataBean m0203Data = dao.calcM0203Data(regcode, (Integer.parseInt(fromYear) + 1911) + "", fromMonth, toMonth);
			Gson gson = new Gson();
			res.setContentType("text/json;charset=UTF-8");
			System.out.println(gson.toJson(m0203Data));
			res.getWriter().print(gson.toJson(m0203Data));
		} catch (Exception e) {
			e.printStackTrace();
			res.setContentType("text/html");
		} finally {
			daoFactory.returnDao(DaoFactory.INSURANCEADDON, dao);
		}
	}
	
	public void doPrintM0203(HttpServletRequest req, HttpServletResponse res) {
		InsuranceAddOnDao dao = (InsuranceAddOnDao) daoFactory.createDao(DaoFactory.INSURANCEADDON);
		try {
			HttpSession ses = req.getSession();
			String regcode = sessionHelper.getRegcode(ses);
			CompanyBean company = dao.retrieveCompanyHealthInfo(regcode);
			
			String fromYear = req.getParameter("fromYear")!=null ? req.getParameter("fromYear") : "";
			String fromMonth = req.getParameter("fromMonth")!=null ? req.getParameter("fromMonth") : "";
			String toMonth = req.getParameter("toMonth")!=null ? req.getParameter("toMonth") : "";
			
			M0203DataBean m0203Data = dao.calcM0203Data(regcode, (Integer.parseInt(fromYear) + 1911) + "", fromMonth, toMonth);
			
			res.setHeader("Content-Transfer-Encoding","binary");
		    res.setHeader("Content-Disposition","attachment;filename=\"M0203.pdf" +"\"");
		    res.setContentType("application/pdf");

		    OutputStream os = res.getOutputStream();
		    (new M0203()).renderForm(company, m0203Data, fromYear, fromMonth, fromYear, toMonth, os);
		    os.flush();
		    os.close();
		} catch (Exception e) {
			e.printStackTrace();
			res.setContentType("text/html");
		} finally {
			daoFactory.returnDao(DaoFactory.INSURANCEADDON, dao);
		}
	}
	
	public ModelAndView printM0305(HttpServletRequest req, HttpServletResponse res) {
		if (!sessionHelper.isLogin(req.getSession())) {
			return HomeController.createLoginMAV();
		}
		HttpSession ses = req.getSession();
		String regcode = sessionHelper.getRegcode(ses);
		String err = this.validateCompanyInfo(regcode);
		if (err !=null && !err.equals("")) {
			return this.forceToSetcompanyHealthInfo(req, res, err);
		}
		
		FilterBean filter = new FilterBean();
		if (ses.getAttribute("FILTER")!=null && ((FilterBean)ses.getAttribute("FILTER")).getPage().equals("PAGE_PRINTM0305")) {
			filter = (FilterBean)ses.getAttribute("FILTER");
		} else {
			filter.setPage("PAGE_PRINTM0305");
			Calendar cal = Calendar.getInstance();
			filter.setFromYear((cal.get(Calendar.YEAR)-1911)+"");
			filter.setFromMonth(((cal.get(Calendar.MONTH) + 1) > 9 ?"":"0") + (cal.get(Calendar.MONTH) + 1));
			filter.setToMonth(((cal.get(Calendar.MONTH) + 1) > 9 ?"":"0") + (cal.get(Calendar.MONTH) + 1));
			ses.setAttribute("FILTER", filter);
		}
		ModelAndView mav = createMAV(PAGE_PRINTM0305, req.getSession());
		mav.addObject("filter", filter);
		return mav;
	}
	
	public void m0305DataTable(HttpServletRequest req, HttpServletResponse res) {
		InsuranceAddOnDao dao = (InsuranceAddOnDao) daoFactory.createDao(DaoFactory.INSURANCEADDON);
		try {
			HttpSession ses = req.getSession();

			String regcode = sessionHelper.getRegcode(ses);
			List<String> criteries = new ArrayList<String>();

			FilterBean filter = new FilterBean(); 
			filter.setPage("PAGE_PRINTM0305");
			String fromYear = req.getParameter("fromYear")!=null ? req.getParameter("fromYear") : "";
			filter.setFromYear(fromYear);
			String fromMonth = req.getParameter("fromMonth")!=null ? req.getParameter("fromMonth") : "";
			filter.setFromMonth(fromMonth);
			String toMonth = req.getParameter("toMonth")!=null ? req.getParameter("toMonth") : "";
			filter.setToMonth(toMonth);
			ses.setAttribute("FILTER", filter);
			
			int iTotalRecords; // total number of records (unfiltered)
			JsonArray data = new JsonArray(); // data that will be shown in the
												// table

			List<InsuranceAddOnFeeBean> insuranceAddOnFees = dao.listM0305Data(regcode, (Integer.parseInt(fromYear) + 1911) + "", fromMonth, toMonth);
			iTotalRecords = insuranceAddOnFees.size();
			JsonObject jsonResponse = new JsonObject();
			jsonResponse.addProperty("iTotalRecords", iTotalRecords);

			for (InsuranceAddOnFeeBean c : insuranceAddOnFees) {
				JsonArray row = new JsonArray();
				row.add(new JsonPrimitive(c.getIncomeDate() + "_" + c.getIncomeType()));
				row.add(new JsonPrimitive(c.getIncomeTypeDesc()));
				row.add(new JsonPrimitive(c.getIncomeDate()));
				row.add(new JsonPrimitive(c.getInsuranceAddOnFee()));
				data.add(row);
			}
			jsonResponse.add("aaData", data);
			System.out.println("send back" + jsonResponse.toString());
			daoFactory.returnDao(DaoFactory.INSURANCEADDON, dao);
			res.setContentType("text/json;charset=UTF-8");
			res.getWriter().print(jsonResponse.toString());
		} catch (Exception e) {
			e.printStackTrace();
			res.setContentType("text/html");
		}
		daoFactory.returnDao(DaoFactory.INSURANCEADDON, dao);
	}
	
	public void doPrintM0305(HttpServletRequest req, HttpServletResponse res) {
		System.out.println("print M0305!");
		InsuranceAddOnDao dao = (InsuranceAddOnDao) daoFactory.createDao(DaoFactory.INSURANCEADDON);
		try {
			HttpSession ses = req.getSession();
			String regcode = sessionHelper.getRegcode(ses);
			CompanyBean company = dao.retrieveCompanyHealthInfo(regcode);
			String[] serialNos = req.getParameter("serialNos").split(",");
			Vector<InsuranceAddOnFeeBean> insuranceAddOnFees = new Vector<InsuranceAddOnFeeBean>();
			String[] para = null;
			String incomePeriod = "";
			for(int i=0; i<serialNos.length; i++) {
				para = serialNos[i].split("_"); 
				incomePeriod = (Integer.parseInt(para[0].substring(0, 3)) + 1911) + "/" + para[0].substring(4);
				insuranceAddOnFees.add(dao.retrieveM0305Data(regcode, incomePeriod, para[1]));
			}
			
			res.setHeader("Content-Transfer-Encoding","binary");
		    res.setHeader("Content-Disposition","attachment;filename=\"M0305.pdf" +"\"");
		    res.setContentType("application/pdf");

		    OutputStream os = res.getOutputStream();
		    (new M0305()).renderForm(regcode, insuranceAddOnFees, company, this.getServletContext().getResourceAsStream("/WEB-INF/blankform/M0305.pdf"), os);
		    os.flush();
		    os.close();
		} catch (Exception e) {
			e.printStackTrace();
			res.setContentType("text/html");
		} finally {
			daoFactory.returnDao(DaoFactory.INSURANCEADDON, dao);
		}
	}
	
	public ModelAndView printM0306(HttpServletRequest req, HttpServletResponse res) {
		if (!sessionHelper.isLogin(req.getSession())) {
			return HomeController.createLoginMAV();
		}
		HttpSession ses = req.getSession();
		String regcode = sessionHelper.getRegcode(ses);
		String err = this.validateCompanyInfo(regcode);
		if (err !=null && !err.equals("")) {
			return this.forceToSetcompanyHealthInfo(req, res, err);
		}
		FilterBean filter = new FilterBean();
		if (ses.getAttribute("FILTER")!=null && ((FilterBean)ses.getAttribute("FILTER")).getPage().equals("PAGE_PRINTM0306")) {
			filter = (FilterBean)ses.getAttribute("FILTER");
		} else {
			filter.setPage("PAGE_PRINTM0306");
			Calendar cal = Calendar.getInstance();
			filter.setIncomePeriod((cal.get(Calendar.YEAR)-1911) + "-" + ((cal.get(Calendar.MONTH) + 1) > 9 ?"":"0") + (cal.get(Calendar.MONTH) + 1));
			ses.setAttribute("FILTER", filter);
		}
		ModelAndView mav = createMAV(PAGE_PRINTM0306, req.getSession());
		mav.addObject("filter", filter);
		return mav;
	}
	
	public void doPrintM0306(HttpServletRequest req, HttpServletResponse res) {
		System.out.println("print M0306!");
		InsuranceAddOnDao dao = (InsuranceAddOnDao) daoFactory.createDao(DaoFactory.INSURANCEADDON);
		try {
			HttpSession ses = req.getSession();
			String regcode = sessionHelper.getRegcode(ses);
			CompanyBean company = dao.retrieveCompanyHealthInfo(regcode);
			
			String incomePeriod = req.getParameter("incomePeriod")!=null ? req.getParameter("incomePeriod") : "";
			String amount = req.getParameter("amount")!=null ? req.getParameter("amount") : "";
		
			res.setHeader("Content-Transfer-Encoding","binary");
		    res.setHeader("Content-Disposition","attachment;filename=\"M0306.pdf" +"\"");
		    res.setContentType("application/pdf");

		    OutputStream os = res.getOutputStream();
		    (new M0306()).renderForm(company, incomePeriod, amount, os);
		    os.flush();
		    os.close();
		} catch (Exception e) {
			e.printStackTrace();
			res.setContentType("text/html");
		} finally {
			daoFactory.returnDao(DaoFactory.INSURANCEADDON, dao);
		}
	}
	
	public void calcSalaryAmount(HttpServletRequest req, HttpServletResponse res) {
		System.out.println("calcSalaryAmount");
		InsuranceAddOnDao dao = (InsuranceAddOnDao) daoFactory.createDao(DaoFactory.INSURANCEADDON);
		try {
			HttpSession ses = req.getSession();
			String regcode = sessionHelper.getRegcode(ses);
			String incomePeriod = req.getParameter("incomePeriod")!=null ? req.getParameter("incomePeriod") : "";
			String[] para = incomePeriod.split("-");
			String[] data = dao.calcSalaryAmount(regcode, (Integer.parseInt(para[0]) + 1911) + "", para[1], Integer.parseInt(ses.getAttribute("salaryShift")+""));
			res.setContentType("text/json;charset=UTF-8");
			System.out.println(new JsonPrimitive(data[0] + "," + data[1]));
			res.getWriter().print(new JsonPrimitive(data[0] + "," + data[1]));
		} catch (Exception e) {
			e.printStackTrace();
			res.setContentType("text/html");
		} finally {
			daoFactory.returnDao(DaoFactory.INSURANCEADDON, dao);
		}
	}
	
	public void exportSalaryAmountDetailtoCSV(HttpServletRequest req, HttpServletResponse res) {
		InsuranceAddOnDao dao = (InsuranceAddOnDao) daoFactory.createDao(DaoFactory.INSURANCEADDON);
		try {
			HttpSession ses = req.getSession();

			String regcode = sessionHelper.getRegcode(ses);
			String incomePeriod = req.getParameter("incomePeriod")!=null ? req.getParameter("incomePeriod") : "";
			String[] para = incomePeriod.split("-");
		
			List<SalaryBean> salaries = dao.salaryAmountDetail(regcode, (Integer.parseInt(para[0]) + 1911) + "", para[1], Integer.parseInt(ses.getAttribute("salaryShift")+""));

			res.setHeader("Content-Disposition", "attachment;filename=\""+ "M0306Detail" + ".csv" + "\"");
			res.setContentType("text/plain;charset=big5");
			PrintWriter writer = res.getWriter();
			SalaryBean salary = null;
			writer.println("員工編號,所得人姓名,薪資總額,健保投保金額");		
			int total = 0;
			int healthInsurance = 0;
			for (int i=0; i<salaries.size(); i++){
				salary = salaries.get(i);
				total += Integer.parseInt(salary.getTotal());
				healthInsurance += Integer.parseInt(salary.getHealthInsurance());
				writer.println(salary.getEmployeeno() + "," + salary.getName() + "," + salary.getTotal() + "," + salary.getHealthInsurance());
			}
			writer.println("總計 " + "," + "  " + "," + total + "," + healthInsurance);
			writer.flush();
		} catch (Exception e) {
			e.printStackTrace();
			res.setContentType("text/html");
		}
		daoFactory.returnDao(DaoFactory.INSURANCEADDON, dao);
	}
	
	public void verifyHealthInsuranceBlank(HttpServletRequest req, HttpServletResponse res) {
		System.out.println("verifyHealthInsuranceBlank");
		InsuranceAddOnDao dao = (InsuranceAddOnDao) daoFactory.createDao(DaoFactory.INSURANCEADDON);
		try {
			HttpSession ses = req.getSession();

			String regcode = sessionHelper.getRegcode(ses);
			String incomePeriod = req.getParameter("incomePeriod")!=null ? req.getParameter("incomePeriod") : "";
			String[] para = incomePeriod.split("-");
			List<String> employees = dao.verifyHealthInsuranceBlank(regcode, (Integer.parseInt(para[0]) + 1911) + "", para[1], Integer.parseInt(ses.getAttribute("salaryShift")+""));
			res.setContentType("text/json;charset=UTF-8");
			String result = "OK";
			if (employees.size()>0){
				result = "";
				for(int i=0; i<employees.size(); i++) {
					result += (i==0 ? "":",") + employees.get(i);
				}
			}
			res.getWriter().print(new JsonPrimitive(result));
		} catch (Exception e) {
			e.printStackTrace();
			res.setContentType("text/html");
		} finally {
			daoFactory.returnDao(DaoFactory.INSURANCEADDON, dao);
		}
	}
	// =======================================================
	public ModelAndView companyHealthInfo(HttpServletRequest req, HttpServletResponse res) {
		if (!sessionHelper.isLogin(req.getSession())) {
			return HomeController.createLoginMAV();
		}
		HttpSession ses = req.getSession();
		ModelAndView mav = createMAV(PAGE_COMPANYHEALTHINFO, req.getSession());		
		String regcode = sessionHelper.getRegcode(ses);
		InsuranceAddOnDao dao = (InsuranceAddOnDao) daoFactory.createDao(DaoFactory.INSURANCEADDON);
		try {
			mav.addObject("company", dao.retrieveCompanyHealthInfo(regcode));
		} catch (Exception e) {
			e.printStackTrace();
			mav.addObject("msg", "讀取投保單位資料發生錯誤!");
		} finally {
			daoFactory.returnDao(DaoFactory.INSURANCEADDON, dao);
		}
		
		return mav;
	}
	
	public ModelAndView updateCompanyHealthInfo(HttpServletRequest req, HttpServletResponse res) {
		if (!sessionHelper.isLogin(req.getSession())) {
			return HomeController.createLoginMAV();
		}
		CompanyBean company = this.fetchCompany(req);
		HttpSession ses = req.getSession();
		ModelAndView mav = null;
		try {
			InsuranceAddOnDao dao = (InsuranceAddOnDao) daoFactory.createDao(DaoFactory.INSURANCEADDON);
			dao.updateCompanyHealthInfo(company);
			mav = this.companyHealthInfo(req, res);
			mav.addObject("msg", "更新投保單位資料成功!");
			daoFactory.returnDao(DaoFactory.INSURANCEADDON, dao);
			int salaryShift = 0;
			try {
				salaryShift = company.getSalaryShift();
			} catch (Exception e) {
				e.printStackTrace();
			}
			ses.setAttribute("salaryShift", salaryShift);
		} catch (Exception e) {
			mav = createMAV(PAGE_COMPANYHEALTHINFO, req.getSession());
			mav.addObject("company", company);			
			mav.addObject("msg", "更新投保單位資料發生錯誤!");
			e.printStackTrace();
		}
		return mav;
	}
	
	// =======================================================
	public ModelAndView outFileRecord(HttpServletRequest req, HttpServletResponse res) {
		if (!sessionHelper.isLogin(req.getSession())) {
			return HomeController.createLoginMAV();
		}
		HttpSession ses = req.getSession();
		FilterBean filter = new FilterBean();
		if (ses.getAttribute("FILTER")!=null && ((FilterBean)ses.getAttribute("FILTER")).getPage().equals("PAGE_OUTFILERECORD")) {
			filter = (FilterBean)ses.getAttribute("FILTER");
		} else {
			filter.setPage("PAGE_OUTFILERECORD");
			Calendar cal = Calendar.getInstance();
			filter.setIncomePeriod((cal.get(Calendar.YEAR)-1911) + "-" + ((cal.get(Calendar.MONTH) + 1) > 9 ?"":"0") + (cal.get(Calendar.MONTH) + 1));
			ses.setAttribute("FILTER", filter);
		}
		ModelAndView mav = createMAV(PAGE_OUTFILERECORD, req.getSession());
		mav.addObject("filter", filter);
		return mav;
	}
	
	public void getOutFileRecordSummary(HttpServletRequest req, HttpServletResponse res) {
		InsuranceAddOnDao dao = (InsuranceAddOnDao) daoFactory.createDao(DaoFactory.INSURANCEADDON);
		try {
			HttpSession ses = req.getSession();

			String regcode = sessionHelper.getRegcode(ses);
			FilterBean filter = new FilterBean(); 
			
			String incomePeriod = req.getParameter("incomePeriod")!=null ? req.getParameter("incomePeriod") : "";
			String[] para = incomePeriod.split("-");
			
			
			String incomeTypes=req.getParameter("incomeTypes");
		
			M0203DataBean outFileRecordSummary = dao.getOutFileRecordSummary(regcode, (Integer.parseInt(para[0]) + 1911) + "", para[1], incomeTypes);
			
			
			
			Gson gson = new Gson();
			res.setContentType("text/json;charset=UTF-8");
			System.out.println(gson.toJson(outFileRecordSummary));
			res.getWriter().print(gson.toJson(outFileRecordSummary));
		} catch (Exception e) {
			e.printStackTrace();
			res.setContentType("text/html");
		} finally {
			daoFactory.returnDao(DaoFactory.INSURANCEADDON, dao);
		}
	}
	
	public void outFileRecordDetailDataTable(HttpServletRequest req, HttpServletResponse res) {
		InsuranceAddOnDao dao = (InsuranceAddOnDao) daoFactory.createDao(DaoFactory.INSURANCEADDON);
		try {
			HttpSession ses = req.getSession();

			String regcode = sessionHelper.getRegcode(ses);
			List<String> criteries = new ArrayList<String>();
			String incomePeriod = req.getParameter("incomePeriod")!=null ? req.getParameter("incomePeriod") : "";
			String[] para = incomePeriod.split("-");
			
			criteries.add("declareNo IS NOT NULL ");

			String period = (Integer.parseInt(para[0]) + 1911) + "/" + (para[1].length() == 1 ? "0" + para[1] : para[1] );
			criteries.add("SUBSTRING(CONVERT(varchar(12) , incomedate, 111 ), 1, 7) ='" + period + "' ");
			criteries.add("incomeType = '" + req.getParameter("incomeType") + "' ");
			
			//String sEcho = param.sEcho;
			int iTotalRecords; // total number of records (unfiltered)
			JsonArray data = new JsonArray(); // data that will be shown in the
												// table

			List<InsuranceAddOnFeeBean> insuranceAddOnFees = dao.listInsuranceAddOnFees(regcode, criteries);
			iTotalRecords = insuranceAddOnFees.size();
			JsonObject jsonResponse = new JsonObject();
			jsonResponse.addProperty("iTotalRecords", iTotalRecords);

			for (InsuranceAddOnFeeBean c : insuranceAddOnFees) {
				JsonArray row = new JsonArray();
				row.add(new JsonPrimitive(c.getDeclareNo() + ""));
				row.add(new JsonPrimitive(StringUtils.adToTw(c.getIncomeDate())));				
				row.add(new JsonPrimitive(c.getIncomeType()));
				row.add(new JsonPrimitive(c.getUnicode()));
				row.add(new JsonPrimitive(c.getHealthInsuranceSalary()));
				row.add(new JsonPrimitive(c.getAccumulatedBonusAmount()));
				row.add(new JsonPrimitive(c.getIncomeAmount()));
				row.add(new JsonPrimitive(c.getInsuranceAddOnFee()));
				row.add(new JsonPrimitive(c.getName()));
				data.add(row);
			}
			jsonResponse.add("aaData", data);
			System.out.println("send back" + jsonResponse.toString());
			daoFactory.returnDao(DaoFactory.INSURANCEADDON, dao);
			res.setContentType("text/json;charset=UTF-8");
			res.getWriter().print(jsonResponse.toString());
		} catch (Exception e) {
			e.printStackTrace();
			res.setContentType("text/html");
		}
		daoFactory.returnDao(DaoFactory.INSURANCEADDON, dao);
	}
	
	public void exportOutFileRecordDetailtoCSV(HttpServletRequest req, HttpServletResponse res) {
		InsuranceAddOnDao dao = (InsuranceAddOnDao) daoFactory.createDao(DaoFactory.INSURANCEADDON);
		try {
			HttpSession ses = req.getSession();

			String regcode = sessionHelper.getRegcode(ses);
			List<String> criteries = new ArrayList<String>();
			String incomePeriod = req.getParameter("incomePeriod")!=null ? req.getParameter("incomePeriod") : "";
			String[] para = incomePeriod.split("-");
			
			criteries.add("declareNo IS NOT NULL ");

			String period = (Integer.parseInt(para[0]) + 1911) + "/" + (para[1].length() == 1 ? "0" + para[1] : para[1] );
			criteries.add("SUBSTRING(CONVERT(varchar(12) , incomedate, 111 ), 1, 7) ='" + period + "' ");
			criteries.add("incomeType = '" + req.getParameter("incomeType") + "' ");
			
			
			List<InsuranceAddOnFeeBean> insuranceAddOnFees = dao.listInsuranceAddOnFees(regcode, criteries);
			
			res.setHeader("Content-Disposition", "attachment;filename=\""+ "OutFileRecordDetail" + ".csv" + "\"");
			res.setContentType("text/plain;charset=big5");
			PrintWriter writer = res.getWriter();
			writer.println("申報編號,給付日期,所得(收入)類別代號,所得人身份證號,扣費當月投保金額,同年度累積獎金金額,所得人(收入)給付金額,扣繳補充保費金額,所得人姓名");		
			for (InsuranceAddOnFeeBean c : insuranceAddOnFees) {
				writer.println(
						c.getDeclareNo() + "," +
						StringUtils.adToTw(c.getIncomeDate()) + "," +				
						c.getIncomeType() + "," +
						c.getUnicode() + "," +
						c.getHealthInsuranceSalary() + "," +
						c.getAccumulatedBonusAmount() + "," +
						c.getIncomeAmount() + "," +
						c.getInsuranceAddOnFee() + "," +
						c.getName());
			}
			
			writer.flush();
		} catch (Exception e) {
			e.printStackTrace();
			res.setContentType("text/html");
		}
		daoFactory.returnDao(DaoFactory.INSURANCEADDON, dao);
	}
	
	// =======================================================
	public void createOutFile(HttpServletRequest req, HttpServletResponse res) {
		InsuranceAddOnDao dao = (InsuranceAddOnDao) daoFactory.createDao(DaoFactory.INSURANCEADDON);
		try {
			HttpSession ses = req.getSession();
			Calendar cal = Calendar.getInstance();
			String regcode = sessionHelper.getRegcode(ses);
			String fromYear = req.getParameter("fromYear")!=null ? req.getParameter("fromYear") : "";
			String fromMonth = req.getParameter("fromMonth")!=null ? req.getParameter("fromMonth") : "";
			String toMonth = req.getParameter("toMonth")!=null ? req.getParameter("toMonth") : "";
			if (fromMonth.trim().length()==1) fromMonth = "0" + fromMonth.trim();
			if (toMonth.trim().length()==1) toMonth = "0" + toMonth.trim();
			String declareNoLead = regcode + NHIDate.convertDateToChi(cal) + "001" + "X"; // 19 碼
			
			CompanyBean company = dao.retrieveCompanyHealthInfo(regcode);
			List<EmployeeBean> incomeEarners = new ArrayList<EmployeeBean>();
			res.setHeader("Content-Disposition", "attachment;filename=\""+ "DPR" + company.getRegcode() + NHIDate.convertDateToChi(cal) + "001.txt" + "\"");
			res.setContentType("text/plain;charset=big5");
			PrintWriter writer = res.getWriter();
			
			M0203DataBean m0203Data = dao.calcM0203Data(regcode, (Integer.parseInt(fromYear) + 1911) + "", fromMonth, toMonth);
			List<String> criteries;
			String criteria1 = "SUBSTRING(CONVERT(varchar(12) , incomedate, 111 ), 1, 7) >='" + (Integer.parseInt(fromYear) + 1911) + "/" + fromMonth + "' ";
			String criteria2 = "SUBSTRING(CONVERT(varchar(12) , incomedate, 111 ), 1, 7) <='" + (Integer.parseInt(fromYear) + 1911) + "/" + toMonth + "' ";
			List<InsuranceAddOnFeeBean> insuranceAddOnFees;
			HashMap<String, String> feeDeclares = new HashMap<String, String>();
			InsuranceAddOnFeeBean insuranceAddOnFee;
			if (m0203Data.getCount62()>0) {
				writer.println(this.prepareOutFileHeader(company, "62", fromYear, fromMonth, toMonth));
				criteries = new ArrayList<String>();
				criteries.add("incomeType ='62' ");
				criteries.add(criteria1);
				criteries.add(criteria2);
				insuranceAddOnFees = dao.listInsuranceAddOnFees(regcode, criteries);
				for (int i=0; i<insuranceAddOnFees.size(); i++) {
					insuranceAddOnFee = insuranceAddOnFees.get(i);
					writer.println(this.prepareOutFileDetail(company, insuranceAddOnFee, declareNoLead, i+1));
					feeDeclares.put(insuranceAddOnFee.getSerialNo() + "", declareNoLead + insuranceAddOnFee.getIncomeType() + StringUtils.appendLeft0((i+1) + "", 9));
				}	
				writer.println(this.prepareOutFileSummary(company, "62", m0203Data.getCount62(), m0203Data.getIncomeAmount62(), m0203Data.getInsuranceAddOnFee62()));
			}
			if (m0203Data.getCount63()>0) {
				writer.println(this.prepareOutFileHeader(company, "63", fromYear, fromMonth, toMonth));
				criteries = new ArrayList<String>();
				criteries.add("incomeType ='63' ");
				criteries.add(criteria1);
				criteries.add(criteria2);
				insuranceAddOnFees = dao.listInsuranceAddOnFees(regcode, criteries);
				for (int i=0; i<insuranceAddOnFees.size(); i++) {
					insuranceAddOnFee = insuranceAddOnFees.get(i);
					writer.println(this.prepareOutFileDetail(company, insuranceAddOnFee, declareNoLead, i+1));
					feeDeclares.put(insuranceAddOnFee.getSerialNo() + "", declareNoLead + insuranceAddOnFee.getIncomeType() + StringUtils.appendLeft0((i+1) + "", 9));
				}	
				writer.println(this.prepareOutFileSummary(company, "63", m0203Data.getCount63(), m0203Data.getIncomeAmount63(), m0203Data.getInsuranceAddOnFee63()));
			}
			if (m0203Data.getCount65()>0) {
				writer.println(this.prepareOutFileHeader(company, "65", fromYear, fromMonth, toMonth));
				criteries = new ArrayList<String>();
				criteries.add("incomeType ='65' ");
				criteries.add(criteria1);
				criteries.add(criteria2);
				insuranceAddOnFees = dao.listInsuranceAddOnFees(regcode, criteries);
				for (int i=0; i<insuranceAddOnFees.size(); i++) {
					insuranceAddOnFee = insuranceAddOnFees.get(i);
					writer.println(this.prepareOutFileDetail(company, insuranceAddOnFee, declareNoLead, i+1));
					feeDeclares.put(insuranceAddOnFee.getSerialNo() + "", declareNoLead + insuranceAddOnFee.getIncomeType() + StringUtils.appendLeft0((i+1) + "", 9));
				}	
				writer.println(this.prepareOutFileSummary(company, "65", m0203Data.getCount65(), m0203Data.getIncomeAmount65(), m0203Data.getInsuranceAddOnFee65()));
			}
			if (m0203Data.getCount66()>0) {
				writer.println(this.prepareOutFileHeader(company, "66", fromYear, fromMonth, toMonth));
				criteries = new ArrayList<String>();
				criteries.add("incomeType ='66' ");
				criteries.add(criteria1);
				criteries.add(criteria2);
				insuranceAddOnFees = dao.listInsuranceAddOnFees(regcode, criteries);
				for (int i=0; i<insuranceAddOnFees.size(); i++) {
					insuranceAddOnFee = insuranceAddOnFees.get(i);
					writer.println(this.prepareOutFileDetail(company, insuranceAddOnFee, declareNoLead, i+1));
					feeDeclares.put(insuranceAddOnFee.getSerialNo() + "", declareNoLead + insuranceAddOnFee.getIncomeType() + StringUtils.appendLeft0((i+1) + "", 9));
				}	
				writer.println(this.prepareOutFileSummary(company, "66", m0203Data.getCount66(), m0203Data.getIncomeAmount66(), m0203Data.getInsuranceAddOnFee66()));
			}
			if (m0203Data.getCount67()>0) {
				writer.println(this.prepareOutFileHeader(company, "67", fromYear, fromMonth, toMonth));
				criteries = new ArrayList<String>();
				criteries.add("incomeType ='67' ");
				criteries.add(criteria1);
				criteries.add(criteria2);
				insuranceAddOnFees = dao.listInsuranceAddOnFees(regcode, criteries);
				for (int i=0; i<insuranceAddOnFees.size(); i++) {
					insuranceAddOnFee = insuranceAddOnFees.get(i);
					writer.println(this.prepareOutFileDetail(company, insuranceAddOnFee, declareNoLead, i+1));
					feeDeclares.put(insuranceAddOnFee.getSerialNo() + "", declareNoLead + insuranceAddOnFee.getIncomeType() + StringUtils.appendLeft0((i+1) + "", 9));
				}	
				writer.println(this.prepareOutFileSummary(company, "67", m0203Data.getCount67(), m0203Data.getIncomeAmount67(), m0203Data.getInsuranceAddOnFee67()));
			}
			if (m0203Data.getCount68()>0) {
				writer.println(this.prepareOutFileHeader(company, "68", fromYear, fromMonth, toMonth));
				criteries = new ArrayList<String>();
				criteries.add("incomeType ='68' ");
				criteries.add(criteria1);
				criteries.add(criteria2);
				insuranceAddOnFees = dao.listInsuranceAddOnFees(regcode, criteries);
				for (int i=0; i<insuranceAddOnFees.size(); i++) {
					insuranceAddOnFee = insuranceAddOnFees.get(i);
					writer.println(this.prepareOutFileDetail(company, insuranceAddOnFee, declareNoLead, i+1));
					feeDeclares.put(insuranceAddOnFee.getSerialNo() + "", declareNoLead + insuranceAddOnFee.getIncomeType() + StringUtils.appendLeft0((i+1) + "", 9));
				}	
				writer.println(this.prepareOutFileSummary(company, "68", m0203Data.getCount68(), m0203Data.getIncomeAmount68(), m0203Data.getInsuranceAddOnFee68()));
			}
			
			dao.updateDeclareNo(feeDeclares);
			writer.flush();
		} catch (Exception e) {
			e.printStackTrace();
			res.setContentType("text/html");
		}
		daoFactory.returnDao(DaoFactory.INSURANCEADDON, dao);
	}
	
	
	private String validateCompanyInfo(String regcode) {
		InsuranceAddOnDao dao = (InsuranceAddOnDao) daoFactory.createDao(DaoFactory.INSURANCEADDON);
		String err = "";
		try {
			CompanyBean company = dao.retrieveCompanyHealthInfo(regcode);
			if (company.getHealthCode().equals("") || company.getHealthCode().length()<9 || !StringUtils.isNumeric(company.getHealthCode()) ){
				err += "投保單位代號有誤<br/>";
			}
			if (company.getRegname().equals("")) {
				err += "單位名稱未設定<br/>";
			}
			if (company.getAddress().equals("")) {
				err += "單位住址未設定<br/>";
			}
			if (company.getBossName().equals("")) {
				err += "扣費義務人姓名未設定<br/>";
			}
			if (company.getContact().equals("")) {
				err += "連絡人姓名未設定<br/>";
			}
			if (company.getPhone().equals("")) {
				err += "連絡電話未設定<br/>";
			}
			if (company.getEmail().equals("")) {
				err += "申報單位電子郵件信箱未設定<br/>";
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			daoFactory.returnDao(DaoFactory.INSURANCEADDON, dao);
		}
		if (!err.equals("")) {
			err = "投保單位資料未設定，進行列印作業前，請先修正以下資料問題：<br/>"  + err;
		}
		return err;
	}
	
	private ModelAndView forceToSetcompanyHealthInfo(HttpServletRequest req, HttpServletResponse res, String err) {
		if (!sessionHelper.isLogin(req.getSession())) {
			return HomeController.createLoginMAV();
		}
		HttpSession ses = req.getSession();
		ModelAndView mav = createMAV(PAGE_COMPANYHEALTHINFO, req.getSession());		
		String regcode = sessionHelper.getRegcode(ses);
		InsuranceAddOnDao dao = (InsuranceAddOnDao) daoFactory.createDao(DaoFactory.INSURANCEADDON);
		try {
			mav.addObject("company", dao.retrieveCompanyHealthInfo(regcode));
			mav.addObject("msg", err);
		} catch (Exception e) {
			e.printStackTrace();
			mav.addObject("msg", "讀取投保單位資料發生錯誤!");
		} finally {
			daoFactory.returnDao(DaoFactory.INSURANCEADDON, dao);
		}
		
		return mav;
	}
	
	private String prepareOutFileHeader(CompanyBean company, String incomeType, String fromYear, String fromMonth, String toMonth) {
		StringBuilder sb = new StringBuilder();
		sb.append("1");	// 1	資料識别碼	1-1
		sb.append(StringUtils.appendBlank(company.getRegcode(), 8, false)); //	2	申報單位統一編號	2-9
		sb.append(StringUtils.appendBlank(incomeType, 2, false));	//	3	所得(收入)類別	10-11
		sb.append(StringUtils.appendBlank(fromYear + fromMonth, 5, false));	// 4	所得給付起始年月	12-16
		sb.append(StringUtils.appendBlank(fromYear + toMonth, 5, false));	// 5	所得給付結束年月	17-21
		sb.append(StringUtils.appendBlank(NHIDate.convertDateToChi(Calendar.getInstance()), 7, false));	// 6	檔案製作日期	22-28
		sb.append(StringUtils.appendBlank("", 8, false));	// 7	總機構統一編號	29-36
		sb.append(StringUtils.appendBlank(company.getEmail(), 30, false));	// 8	申報單位電子郵件信箱帳號	37-66
		sb.append(StringUtils.appendBlank(company.getBossName(), 25, true));	// 9	扣費義務人名稱	67-116
		sb.append(StringUtils.appendBlank("", 84, false));// 10	保留欄位	117-200
		return sb.toString();
	}
	
	private String prepareOutFileDetail(CompanyBean company, InsuranceAddOnFeeBean insuranceAddOnFee, String declareNoLead, int serialNo) {
		StringBuilder sb = new StringBuilder();
		sb.append("2");	// 1	資料識别碼	1-1
		sb.append(StringUtils.appendBlank(company.getRegcode(), 8, false)); // 2	申報單位統一編號	2-9
		sb.append(StringUtils.appendBlank(insuranceAddOnFee.getIncomeType(), 2, false));	// 3	所得(收入)類別	10-11
		sb.append(StringUtils.appendLeft0(serialNo+"", 9));	// 4	流水序號	12-20
		sb.append(insuranceAddOnFee.getDeclareNo().equals("") ? "I" : "R" );	// 5	資料處理方式	21-21
		sb.append(StringUtils.adToTwCode(insuranceAddOnFee.getIncomeDate()));// 6	所得給付日期	22-28
		sb.append(StringUtils.appendBlank(insuranceAddOnFee.getUnicode(), 10, false));	// 7	所得人身分證號	29-38
		sb.append(declareNoLead + insuranceAddOnFee.getIncomeType() + StringUtils.appendLeft0(serialNo + "", 9));// 8	申報編號	39-68
		sb.append(StringUtils.appendLeft0(insuranceAddOnFee.getIncomeAmount(), 14));	// 9	所得(收入)給付金額	69-82
		sb.append(StringUtils.appendLeft0(insuranceAddOnFee.getInsuranceAddOnFee(), 10));	// 10	扣繳補充保險費金額	83-92
		
		// 11	共用欄位區	93-132
		if (insuranceAddOnFee.getIncomeType().equals("62")) {
			sb.append(StringUtils.appendBlank(company.getHealthCode(), 9, false));	// 投保單位代號	93-101
			sb.append(StringUtils.appendLeft0(insuranceAddOnFee.getHealthInsuranceSalary(), 6));	// 扣費當月投保金額	102-107
			sb.append(StringUtils.appendLeft0(insuranceAddOnFee.getAccumulatedBonusAmount(), 10));	// 同年度累計獎金金額	108-117
			sb.append(StringUtils.appendBlank("", 15, false));	// 保留欄位	118-132
		} else if (insuranceAddOnFee.getIncomeType().equals("66")) {
			sb.append(StringUtils.appendLeft0(insuranceAddOnFee.getICAAmount(), 10));	// 扣取時可扣抵稅額	93-102
			sb.append(StringUtils.appendLeft0(insuranceAddOnFee.getAnnualICAAmount(), 10));	// 年度確定可扣抵稅額	103-112
			// 已列入投保金額計算保險費之股利金額	113-122
			if (insuranceAddOnFee.getTrustNote().equals("T")) {
				sb.append(StringUtils.appendLeft0("0", 10));
			} else {
				sb.append(StringUtils.appendLeft0(insuranceAddOnFee.getBossHealthInsuranceAmount(), 10));
			}
			sb.append(StringUtils.adToTwCode(insuranceAddOnFee.getExcludeDate()));	// 除權(息)基準日期	123-129
			sb.append(StringUtils.appendBlank(insuranceAddOnFee.getStockNote(), 1, false));	// 股利註記	130-130
			sb.append(StringUtils.appendBlank("", 2, false));	// 保留欄位	131-132
		} else {
			sb.append(StringUtils.appendBlank("", 40, false));
		}
		
		sb.append(insuranceAddOnFee.getTrustNote().equals("T") ? "T" : " ");	// 12	信託註記	133-133
		sb.append(StringUtils.appendBlank(insuranceAddOnFee.getName(), 25, true));	// 13	所得人姓名	134-183
		sb.append(StringUtils.appendBlank("", 17, false));	// 14	保留欄位	184-200
		return sb.toString();
	}
	
	private String prepareOutFileSummary(CompanyBean company, String incomeType, int count, int incomeAmount, int insuranceAddOnfeeAmount) {
		StringBuilder sb = new StringBuilder();
		sb.append("3");	// 1	資料識别碼	1-1
		sb.append(StringUtils.appendBlank(company.getRegcode(), 8, false));	// 2	申報單位統一編號	2-9
		sb.append(StringUtils.appendBlank(incomeType, 2, false));	// 3	所得(收入)類別	10-11
		sb.append(StringUtils.appendLeft0(count + "", 9));	// 4	申報總筆數	12-20
		sb.append(StringUtils.appendLeft0(incomeAmount + "", 20));	// 5	所得(收入)給付總額	21-40
		sb.append(StringUtils.appendLeft0(insuranceAddOnfeeAmount + "", 16));	// 6	扣繳補充保險費總額	41-56
		sb.append(StringUtils.appendBlank(company.getPhone(), 15, false));// 7	聯絡電話	57-71
		sb.append(StringUtils.appendBlank(company.getContact(), 25, true));	// 8	聯絡人姓名	72-121
		sb.append(StringUtils.appendBlank("", 79, false));	// 9	保留欄位	122-200
		return sb.toString();
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
	
	private InsuranceAddOnFeeBean fetchInsuranceAddOnFee(HttpServletRequest req) {
		InsuranceAddOnFeeBean insuranceAddOnFee = new InsuranceAddOnFeeBean();
		ServletRequestDataBinder binder = new ServletRequestDataBinder(insuranceAddOnFee, "InsuranceAddOnFeeBean");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy/MM/dd hh:mm:ss"), true));
		binder.bind(req);
		insuranceAddOnFee.setRegcode(sessionHelper.getRegcode(req.getSession()));
		insuranceAddOnFee.setIncomeDate(StringUtils.twToAd(insuranceAddOnFee.getIncomeDate()));
		insuranceAddOnFee.setExcludeDate(StringUtils.twToAd(insuranceAddOnFee.getExcludeDate()));
		return insuranceAddOnFee;
	}
	
	private CompanyBean fetchCompany(HttpServletRequest req) {
		CompanyBean company = new CompanyBean();
		ServletRequestDataBinder binder = new ServletRequestDataBinder(company, "CompanyBean");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy/MM/dd hh:mm:ss"), true));
		binder.bind(req);
		return company;
	}
}
