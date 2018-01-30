package com.csjian.model.bean;

/**
 * @author Administrator
 * 用來把查詢條件放在 session 裡
 */
public class FilterBean {
	private String page;
	private String incomePeriod;
	private String incomeType;
	private String unicode;
	private String name;
	private String fromYear;
	private String fromMonth;
	private String toYear;
	private String toMonth;
	
	public FilterBean() {
		this.page = "";
		this.incomePeriod = "";
		this.incomeType = "";
		this.unicode = "";
		this.name = "";
		this.fromYear = "";
		this.fromMonth = "";
		this.toYear = "";
		this.toMonth = "";
	}
	
	/**
	 * @return the page
	 */
	public String getPage() {
		return page;
	}
	/**
	 * @param page the page to set
	 */
	public void setPage(String page) {
		this.page = page;
	}
	/**
	 * @return the incomePeriod
	 */
	public String getIncomePeriod() {
		return incomePeriod;
	}
	/**
	 * @param incomePeriod the incomePeriod to set
	 */
	public void setIncomePeriod(String incomePeriod) {
		this.incomePeriod = incomePeriod;
	}
	/**
	 * @return the incomeType
	 */
	public String getIncomeType() {
		return incomeType;
	}
	/**
	 * @param incomeType the incomeType to set
	 */
	public void setIncomeType(String incomeType) {
		this.incomeType = incomeType;
	}
	/**
	 * @return the unicode
	 */
	public String getUnicode() {
		return unicode;
	}
	/**
	 * @param unicode the unicode to set
	 */
	public void setUnicode(String unicode) {
		this.unicode = unicode;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the fromYear
	 */
	public String getFromYear() {
		return fromYear;
	}

	/**
	 * @param fromYear the fromYear to set
	 */
	public void setFromYear(String fromYear) {
		this.fromYear = fromYear;
	}

	/**
	 * @return the fromMonth
	 */
	public String getFromMonth() {
		return fromMonth;
	}

	/**
	 * @param fromMonth the fromMonth to set
	 */
	public void setFromMonth(String fromMonth) {
		this.fromMonth = fromMonth;
	}

	/**
	 * @return the toYear
	 */
	public String getToYear() {
		return toYear;
	}

	/**
	 * @param toYear the toYear to set
	 */
	public void setToYear(String toYear) {
		this.toYear = toYear;
	}

	/**
	 * @return the toMonth
	 */
	public String getToMonth() {
		return toMonth;
	}

	/**
	 * @param toMonth the toMonth to set
	 */
	public void setToMonth(String toMonth) {
		this.toMonth = toMonth;
	}
}
