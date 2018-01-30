package com.csjian.model.bean;

public class CompanyBean {
	private String regcode;
	private String name;
	private String regname;
	private String password;
	private String contact;
	private String phone;
	private String email;
	private String groupno;
	private String disable;
	private String agent;
	private String laborCode;
	private String healthCode;
	private String zip;
	private String address;
	private String bossName;
	private String bossId;	
	private String bossIsNative;
	private int salaryShift;

	public CompanyBean() {
		this.regcode = "";
		this.name = "";
		this.regname = "";
		this.password = "";
		this.contact = "";
		this.phone = "";
		this.email = "";
		this.groupno = "customer";
		this.disable = "N";
		this.agent = "";
		this.laborCode = "";
		this.healthCode = "";
		this.zip = "";
		this.address = "";
		this.bossName = "";
		this.bossId = "";
		this.bossIsNative = "Y";
		this.setSalaryShift(0);
	}

	public void setRegcode(String regcode) {
		this.regcode = regcode;
	}

	public String getRegcode() {
		return this.regcode;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setRegname(String regname) {
		this.regname = regname;
	}

	public String getRegname() {
		return this.regname;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return this.password;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getContact() {
		return this.contact;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return this.email;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setGroupno(String groupno) {
		this.groupno = groupno;
	}

	public String getGroupno() {
		return this.groupno;
	}

	public void setDisable(String disable) {
		this.disable = disable;
	}

	public String getDisable() {
		return this.disable;
	}

	public void setAgent(String agent) {
		this.agent = agent;
	}

	public String getAgent() {
		return this.agent;
	}

	public void setLaborCode(String laborCode) {
		this.laborCode = laborCode;
	}

	public String getLaborCode() {
		return this.laborCode;
	}

	public void setHealthCode(String healthCode) {
		this.healthCode = healthCode;
	}

	public String getHealthCode() {
		return this.healthCode;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getZip() {
		return this.zip;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddress() {
		return this.address;
	}

	public void setBossName(String bossName) {
		this.bossName = bossName;
	}

	public String getBossName() {
		return this.bossName;
	}

	public void setBossId(String bossId) {
		this.bossId = bossId;
	}

	public String getBossId() {
		return this.bossId;
	}

	/**
	 * @return the salaryShift
	 */
	public int getSalaryShift() {
		return salaryShift;
	}

	/**
	 * @param salaryShift the salaryShift to set
	 */
	public void setSalaryShift(int salaryShift) {
		this.salaryShift = salaryShift;
	}
	
	public void setBossIsNative(String bossIsNative) {
		this.bossIsNative = bossIsNative;
	}
	
	public String getBossIsNative() {
		return this.bossIsNative;
	}
}
