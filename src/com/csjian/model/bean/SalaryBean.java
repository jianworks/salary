package com.csjian.model.bean;

import java.util.*;

public class SalaryBean {
	private boolean saved;
	private String regcode;
	private String employeeno;
	private String year;
	private String name;
	private String month;
	private String basesalary;
	private String baserate;
	private String overtimerate;
	private String overtime;
	private String oversalary;
	private String workinghr;
	private String btotal;
	private String ptotal;
	private String mtotal;
	private String total;
	private String tax;
	
	/**
	 * 退休金提撥金額
	 */
	private String retireFee;
	private String govinsurance;
	private String healthInsuranceFee;
	private String laborInsuranceFee;
	private String healthInsurance;
	private String laborInsurance;
	
	/**
	 * 勞退提撥工資
	 */
	private String laborRetireFee;

	private Vector itemp; // seqno, name, amount
	private Vector itemm; // seqno, name, amount

	public SalaryBean() {
		this.saved = true;
		this.regcode = "";
		this.employeeno = "";
		this.name = "";
		this.year = "";
		this.month = "";
		this.basesalary = "";
		this.baserate = "";
		this.overtimerate = "";
		this.overtime = "";
		this.oversalary = "";
		this.workinghr = "";
		this.btotal = "";
		this.ptotal = "";
		this.mtotal = "";
		this.total = "";
		this.tax = "";
		this.retireFee = "0";
		this.govinsurance = "";
		this.laborInsuranceFee = "0";
		this.healthInsuranceFee = "0";
		this.laborInsurance = "0";
		this.laborInsuranceFee = "0";
		this.laborRetireFee = "0";
		this.healthInsurance = "0";
		this.itemp = new Vector();
		this.itemm = new Vector();
	}

	public void setSaved(boolean saved) {
		this.saved = saved;
	}

	public boolean getSaved() {
		return this.saved;
	}

	public void setRegcode(String regcode) {
		this.regcode = regcode;
	}

	public String getRegcode() {
		return this.regcode;
	}

	public void setEmployeeno(String employeeno) {
		this.employeeno = employeeno;
	}

	public String getEmployeeno() {
		return this.employeeno;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getMonth() {
		return this.month;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getYear() {
		return this.year;
	}

	public void setBasesalary(String basesalary) {
		this.basesalary = basesalary;
	}

	public String getBasesalary() {
		return this.basesalary;
	}

	public void setBaserate(String baserate) {
		this.baserate = baserate;
	}

	public String getBaserate() {
		return this.baserate;
	}

	public void setOvertimerate(String overtimerate) {
		this.overtimerate = overtimerate;
	}

	public String getOvertimerate() {
		return this.overtimerate;
	}

	public void setOvertime(String overtime) {
		this.overtime = overtime;
	}

	public String getOvertime() {
		return this.overtime;
	}

	public void setOversalary(String oversalary) {
		this.oversalary = oversalary;
	}

	public String getOversalary() {
		return this.oversalary;
	}

	public void setBtotal(String btotal) {
		this.btotal = btotal;
	}

	public String getBtotal() {
		return this.btotal;
	}

	public void setPtotal(String ptotal) {
		this.ptotal = ptotal;
	}

	public String getPtotal() {
		return this.ptotal;
	}

	public void setMtotal(String mtotal) {
		this.mtotal = mtotal;
	}

	public String getMtotal() {
		return this.mtotal;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getTotal() {
		return this.total;
	}

	public void setTax(String tax) {
		this.tax = tax;
	}

	public String getTax() {
		return this.tax;
	}

	public void setWorkinghr(String workinghr) {
		this.workinghr = workinghr;
	}

	public String getWorkinghr() {
		return this.workinghr;
	}

	public void setItemp(Vector itemp) {
		this.itemp = itemp;
	}

	public Vector getItemp() {
		return this.itemp;
	}

	public void setItemm(Vector itemm) {
		this.itemm = itemm;
	}

	public Vector getItemm() {
		return this.itemm;
	}

	public void setRetireFee(String retireFee) {
		this.retireFee = retireFee;
	}

	public String getRetireFee() {
		return this.retireFee;
	}

	public void setGovinsurance(String govinsurance) {
		this.govinsurance = govinsurance;
	}

	public String getGovinsurance() {
		return this.govinsurance;
	}

	public void setLaborInsuranceFee(String laborInsuranceFee) {
		this.laborInsuranceFee = laborInsuranceFee;
	}

	public String getLaborInsuranceFee() {
		return this.laborInsuranceFee;
	}

	public void setHealthInsuranceFee(String healthInsuranceFee) {
		this.healthInsuranceFee = healthInsuranceFee;
	}

	public String getHealthInsuranceFee() {
		return this.healthInsuranceFee;
	}

	/**
	 * @return the healthSalary
	 */
	public String getHealthInsurance() {
		return healthInsurance;
	}

	/**
	 * @param healthSalary
	 *            the healthSalary to set
	 */
	public void setHealthInsurance(String healthInsurance) {
		this.healthInsurance = healthInsurance;
	}

	/**
	 * @return the laborInsurance
	 */
	public String getLaborInsurance() {
		return laborInsurance;
	}

	/**
	 * @param laborInsurance the laborInsurance to set
	 */
	public void setLaborInsurance(String laborInsurance) {
		this.laborInsurance = laborInsurance;
	}

	/**
	 * @return the laborRetireFee
	 */
	public String getLaborRetireFee() {
		return laborRetireFee;
	}

	/**
	 * @param laborRetireFee the laborRetireFee to set
	 */
	public void setLaborRetireFee(String laborRetireFee) {
		this.laborRetireFee = laborRetireFee;
	}
}
