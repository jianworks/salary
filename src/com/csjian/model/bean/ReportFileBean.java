package com.csjian.model.bean;

public class ReportFileBean {
	private int seqNo;
	private String title;
	private String fileName;
	private String reportDate;
	
	public ReportFileBean(){
		this.seqNo = 0;
		this.title = "";
		this.fileName = "";
		this.reportDate = "";
	}
	
	public ReportFileBean(int seqNo, String title, String fileName, String reportDate){
		this.seqNo = seqNo;
		this.title = title;
		this.fileName = fileName;
		this.reportDate = reportDate;
	}
	
	public void setSeqNo(int seqNo){
		this.seqNo = seqNo;		
	}
	
	public int getSeqNo() {
		return this.seqNo;
	}
	
	public void setTitle(String title){
		this.title = title;
	}
	
	public String getTitle(){
		return this.title;
	}
	
	public void setFileName(String fileName){
		this.fileName = fileName;
	}
	
	public String getFileName(){
		return this.fileName;
	}
	
	public void setReportDate(String reportDate){
		this.reportDate = reportDate;
	}
	
	public String getReportDate(){
		return this.reportDate;
	}
}
