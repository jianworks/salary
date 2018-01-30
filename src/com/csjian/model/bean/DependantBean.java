package com.csjian.model.bean;

public class DependantBean {
  private String employeeno;
  private String regcode;
  private String ayear;
  private String unicode;
  private String relation;
  private String birthday;
  private String name;
  
  public DependantBean() {
    this.employeeno = "";
    this.regcode = "";
    this.ayear = "";
    this.unicode = "";
    this.relation = "";
    this.birthday = "";
    this.name = "";
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
  
  public void setUnicode(String unicode) {
	  this.unicode = unicode;  
  }
  
  public String getUnicode() {
  	return this.unicode;
  }
  
  public void setRelation(String relation) {
    this.relation = relation;  
  }
  
  public String getRelation() {
    return this.relation;
  }
  
  public void setName(String name) {
  	this.name = name;
  }
  
  public String getName() {
  	return this.name;
  }
  
  public void setBirthday(String birthday) {
  	this.birthday = birthday;
  }
  
  public String getBirthday() {
  	return this.birthday;
  }
 
}
