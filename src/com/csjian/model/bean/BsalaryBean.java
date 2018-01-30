package com.csjian.model.bean;
import java.util.*;

public class BsalaryBean {
  private String regcode;
  private String name;
  private String employeeno;
  private String ayear;
  private String basesalary;
  private String baserate;
  private String overtimerate;
  private String mark;
  private String govinsurance;
  private Vector itemp; // seqno, name, amount
  private Vector itemm; // seqno, name, amount

  public BsalaryBean() {
	  this.regcode = "";
	  this.employeeno = "";
	  this.ayear = "";
	  this.name = "";
	  this.basesalary = "";
	  this.baserate = "";
	  this.overtimerate = "";
	  this.mark = "";
	  this.govinsurance = "";
	  this.itemp = new Vector();
	  this.itemm = new Vector();
	}

  public void setRegcode(String regcode) {
	  this.regcode = regcode;
  }

  public String getRegcode() {
  	return this.regcode;
  }

  public void setAyear(String ayear) {
	  this.ayear = ayear;
  }

  public String getAyear() {
  	return this.ayear;
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

  public void setMark(String mark) {
	  this.mark = mark;
  }

  public String getMark() {
  	return this.mark;
  }

  public void setGovinsurance(String govinsurance) {
	  this.govinsurance = govinsurance;
  }

  public String getGovinsurance() {
  	return this.govinsurance;
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
}
