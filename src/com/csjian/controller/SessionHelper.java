package com.csjian.controller;

import javax.servlet.http.HttpSession;
import com.csjian.model.bean.*;
import com.csjian.model.dao.*;
import java.util.*;

public class SessionHelper {

	// =========================================================================

	public void login(HttpSession session, CompanyBean company, List aplist0, List aplist1, List aplist2, List aplist3, List aplist4, List aplist5) {
		session.setAttribute("company", company);	
		session.setAttribute("aplist0", aplist0);
		session.setAttribute("aplist1", aplist1);
		session.setAttribute("aplist2", aplist2);
		session.setAttribute("aplist3", aplist3);
		session.setAttribute("aplist4", aplist4);
		session.setAttribute("aplist5", aplist5);
		//session.setAttribute("group", company.getGroupno());	
		int salaryShift = 0;
		try {
			salaryShift = company.getSalaryShift();
		} catch (Exception e) {
			e.printStackTrace();
		}
		session.setAttribute("salaryShift", salaryShift);
	}

	public boolean isLogin(HttpSession session) {
		return session.getAttribute("company") != null;
	}

	public String getRegcode(HttpSession session) {
		return ((CompanyBean) session.getAttribute("company")).getRegcode();
	}
	
	public String getGroupno(HttpSession session) {
		return ((CompanyBean) session.getAttribute("company")).getGroupno();
	}

	public String getRegname(HttpSession session) {
		return ((CompanyBean) session.getAttribute("company")).getRegname();
	}
	
	public List getAplist0(HttpSession session) {
		return (List) session.getAttribute("aplist0");
	}
	
	public List getAplist1(HttpSession session) {
		return (List) session.getAttribute("aplist1");
	}
	
	public List getAplist2(HttpSession session) {
		return (List) session.getAttribute("aplist2");
	}
	
	public List getAplist3(HttpSession session) {
		return (List) session.getAttribute("aplist3");
	}
	
	public List getAplist4(HttpSession session) {
		return (List) session.getAttribute("aplist4");
	}
	
	public List getAplist5(HttpSession session) {
		return (List) session.getAttribute("aplist5");
	}
	
	public String getGroup(HttpSession session) {
		return (String) session.getAttribute("group");
	}
	
	public void logout(HttpSession session) {
		session.removeAttribute("company");
	}

	// ============================================
	public void setPreviousUrl(HttpSession session, String url) {
		session.setAttribute("previousUrl", url);
	}

	public String getPreviousUrl(HttpSession session) {
		return (String) session.getAttribute("previousUrl");
	}

	public String removePreviousUrl(HttpSession session) {
		String url = (String) session.getAttribute("previousUrl");
		session.removeAttribute("previousUrl");
		return url;
	}
}
