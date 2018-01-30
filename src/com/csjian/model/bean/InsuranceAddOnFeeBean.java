/**
 * 
 */
package com.csjian.model.bean;

/**
 * @author Administrator
 * 
 */
public class InsuranceAddOnFeeBean {
	private int serialNo;
	private String regcode;
	private String unicode;
	private String name;
	private String incomeType;
	private String incomeDate;
	private String healthInsuranceSalary;
	private String incomeAmount;
	private String accumulatedBonusAmount;
	private String stockNote;
	private String trustNote;
	private String isBoss;
	private String bossHealthInsuranceAmount;
	private String ICAAmount;
	private String annualICAAmount;
	private String excludeDate;
	private String insuranceAddOnFee;
	private String address;
	private String incomeTypeName;
	private String incomeTypeDesc;
	private String declareNo;
	
	public InsuranceAddOnFeeBean() {
		this.serialNo = 0;
		this.regcode = "";
		this.unicode = "";
		this.name = "";
		this.incomeType = "";
		this.incomeDate = "";
		this.healthInsuranceSalary = "";
		this.incomeAmount = "";
		this.accumulatedBonusAmount = "";
		this.stockNote = "";
		this.trustNote = "";
		this.ICAAmount = "";
		this.annualICAAmount = "";
		this.excludeDate = "";
		this.insuranceAddOnFee = "";
		this.isBoss = "";
		this.bossHealthInsuranceAmount = "";
		this.address = "";
		this.incomeTypeName = "";
		this.incomeTypeDesc = "";
		this.setDeclareNo("");
	}
	
	public String getIncomeTypeName() {
		switch(Integer.parseInt(incomeType)) {
		case 62:
			return "逾投保金額四倍之獎金";
		case 63:
			return "非所屬投保單位給付之薪資所得";
		case 65:
			return "執行業務收入";
		case 66:
			return "股利所得";
		case 67:
			return "利息所得";
		case 68:
			return "租金收入";
		default:
			return "";
		}
	}
	
	public String getIncomeTypeDesc() {
		switch(Integer.parseInt(incomeType)) {
		case 62:
			return "逾投保金額四倍之獎金";
		case 63:
			return "非所屬投保單位給付之薪資所得";
		case 65:
			return "執行業務收入";
		case 66:
			if (this.trustNote.equals("T"))
				return "股利所得－信託";
			else 
				return "股利所得－非信託";
		case 67:
			if (this.trustNote.equals("T"))
				return "利息所得－信託";
			else
				return "利息所得－非信託";
		case 68:
			if (this.trustNote.equals("T"))
				return "租金收入－信託";
			else
				return "租金收入－非信託";
		default:
			return "";
		}
	}
	
	public int getSerialNo() {
		return serialNo;
	}
	
	public void setSerialNo(int serialNo) {
		this.serialNo = serialNo;
	}
	
	/**
	 * @return the regcode
	 */
	public String getRegcode() {
		return regcode;
	}
	/**
	 * @param regcode the regcode to set
	 */
	public void setRegcode(String regcode) {
		this.regcode = regcode;
	}
	
	public String getUnicode() {
		return unicode;
	}
	public void setUnicode(String unicode) {
		this.unicode = unicode;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIncomeType() {
		return incomeType;
	}
	public void setIncomeType(String incomeType) {
		this.incomeType = incomeType;
	}
	public String getIncomeDate() {
		return incomeDate;
	}
	public void setIncomeDate(String incomeDate) {
		this.incomeDate = incomeDate;
	}
	public String getHealthInsuranceSalary() {
		return healthInsuranceSalary;
	}
	public void setHealthInsuranceSalary(String healthInsuranceSalary) {
		this.healthInsuranceSalary = healthInsuranceSalary;
	}
	public String getIncomeAmount() {
		return incomeAmount;
	}
	public void setIncomeAmount(String incomeAmount) {
		this.incomeAmount = incomeAmount;
	}
	public String getAccumulatedBonusAmount() {
		return accumulatedBonusAmount;
	}
	public void setAccumulatedBonusAmount(String accumulatedBonusAmount) {
		this.accumulatedBonusAmount = accumulatedBonusAmount;
	}
	public String getStockNote() {
		return stockNote;
	}
	public void setStockNote(String stockNote) {
		this.stockNote = stockNote;
	}
	public String getTrustNote() {
		return trustNote;
	}
	public void setTrustNote(String trustNote) {
		this.trustNote = trustNote;
	}
	public String getICAAmount() {
		return ICAAmount;
	}
	public void setICAAmount(String ICAAmount) {
		this.ICAAmount = ICAAmount;
	}
	public String getAnnualICAAmount() {
		return annualICAAmount;
	}
	public void setAnnualICAAmount(String annualICAAmount) {
		this.annualICAAmount = annualICAAmount;
	}
	public String getExcludeDate() {
		return excludeDate;
	}
	public void setExcludeDate(String excludeDate) {
		this.excludeDate = excludeDate;
	}
	public String getInsuranceAddOnFee() {
		return insuranceAddOnFee;
	}
	public void setInsuranceAddOnFee(String insuranceAddOnFee) {
		this.insuranceAddOnFee = insuranceAddOnFee;
	}

	/**
	 * @return the isBoss
	 */
	public String getIsBoss() {
		return isBoss;
	}

	/**
	 * @param isBoss the isBoss to set
	 */
	public void setIsBoss(String isBoss) {
		this.isBoss = isBoss;
	}

	/**
	 * @return the bossHealthInsuranceAmount
	 */
	public String getBossHealthInsuranceAmount() {
		return bossHealthInsuranceAmount;
	}

	/**
	 * @param bossHealthInsuranceAmount the bossHealthInsuranceAmount to set
	 */
	public void setBossHealthInsuranceAmount(String bossHealthInsuranceAmount) {
		this.bossHealthInsuranceAmount = bossHealthInsuranceAmount;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the declareNo
	 */
	public String getDeclareNo() {
		return declareNo;
	}

	/**
	 * @param declareNo the declareNo to set
	 */
	public void setDeclareNo(String declareNo) {
		this.declareNo = declareNo;
	}
}
