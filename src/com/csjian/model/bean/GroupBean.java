package com.csjian.model.bean;

public class GroupBean {
  private String groupno;
  private String name;
  private String[] apnolist;
  
  public GroupBean() {
  	this.groupno = "";
  	this.name = "";  	
  }
  
  public void setGroupno(String groupno) {
  	this.groupno = groupno;
  }
  
  public String getGroupno() {
  	return this.groupno;
  }
  
  public void setName(String name) {
  	this.name = name;
  }
  
  public String getName() {
  	return this.name;
  }
  
  public void setApnolist(String[] apnolist) {
  	this.apnolist = apnolist;
  }
  
  public String[] getApnolist() {
  	return this.apnolist;
  }
}
