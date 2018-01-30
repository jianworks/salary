package com.csjian.model.bean;

/**
 * 
 * @author Administrator Employee 與 IncomeEarner 共用，以是否有 employeeno 作為判斷
 */
public class EmployeeBean {
	private String employeeno;
	private String regcode;
	private String ayear;
	private String isnative;
	private String unicode;
	
	// 為了處理二代健保外國人的問題，把護照號碼拿掉，改用身份證號欄來存
	//private String passport;
	private String name;
	private String address;
	private String onboarddate;
	private String accountno;
	private String isresign;
	private String resigndate;
	
	/**
	 * 退休金提撥金額
	 */
	private String retirefee;
	
	private String govinsurance;
	
	private String laborInsurance;
	
	/**
	 * 健保投保薪資
	 */
	private String healthInsurance;
	
	/**
	 * 勞退提撥工資
	 */
	private String laborRetireFee;
	
	private String title;
	private String birthday;

	public EmployeeBean() {
		this.employeeno = "";
		this.regcode = "";
		this.ayear = "";
		this.isnative = "Y";
		this.unicode = "";
		//this.passport = "";
		this.name = "";
		this.address = "";
		this.onboarddate = "";
		this.accountno = "";
		this.isresign = "N";
		this.resigndate = "";
		this.retirefee = "";
		this.title = "";
		this.govinsurance = "";
		this.birthday = "";
		this.laborInsurance = "";
		this.healthInsurance = "";
		this.laborRetireFee = "";
	}

	public void setEmployeeno(String employeeno) {
		this.employeeno = employeeno;
	}

	public String getEmployeeno() {
		return this.employeeno;
	}

	public void setAyear(String ayear) {
		this.ayear = ayear;
	}

	public String getAyear() {
		return this.ayear;
	}

	public void setRegcode(String regcode) {
		this.regcode = regcode;
	}

	public String getRegcode() {
		return this.regcode;
	}

	public void setIsnative(String isnative) {
		this.isnative = isnative;
	}

	public String getIsnative() {
		return this.isnative;
	}

	public void setUnicode(String unicode) {
		this.unicode = unicode;
	}

	public String getUnicode() {
		return this.unicode;
	}

	/*
	public void setPassport(String passport) {
		this.passport = passport;
	}

	public String getPassport() {
		return this.passport;
	}*/

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddress() {
		return this.address;
	}

	public void setOnboarddate(String onboarddate) {
		this.onboarddate = onboarddate;
	}

	public String getOnboarddate() {
		return this.onboarddate;
	}

	public void setAccountno(String accountno) {
		this.accountno = accountno;
	}

	public String getAccountno() {
		return this.accountno;
	}

	public void setIsresign(String isresign) {
		this.isresign = isresign;
	}

	public String getIsresign() {
		return this.isresign;
	}

	public void setResigndate(String resigndate) {
		this.resigndate = resigndate;
	}

	public String getResigndate() {
		return this.resigndate;
	}

	public void setRetirefee(String retirefee) {
		this.retirefee = retirefee;
	}

	public String getRetirefee() {
		return this.retirefee;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return this.title;
	}

	public void setGovinsurance(String govinsurance) {
		this.govinsurance = govinsurance;
	}

	public String getGovinsurance() {
		return this.govinsurance;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getBirthday() {
		return this.birthday;
	}

	public void setLaborInsurance(String laborInsurance) {
		this.laborInsurance = laborInsurance;
	}

	public String getLaborInsurance() {
		return this.laborInsurance;
	}

	public void setHealthInsurance(String healthInsurance) {
		this.healthInsurance = healthInsurance;
	}

	public String getHealthInsurance() {
		return this.healthInsurance;
	}

	public void setLaborRetireFee(String laborRetireFee) {
		this.laborRetireFee = laborRetireFee;
	}

	public String getLaborRetireFee() {
		return this.laborRetireFee;
	}
}
