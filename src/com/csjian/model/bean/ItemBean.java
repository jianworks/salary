package com.csjian.model.bean;

public class ItemBean {
	private String itemType;
	private String regcode;
	private int seqno;
	private String name;
	private String enable;
	private String addOnFeeFlag;
	private String bonusFlag;

	public ItemBean() {
		this.regcode = "";
		this.seqno = 0;
		this.setName("");
		this.setEnable("N");
		this.setAddOnFeeFlag("N");
		this.setBonusFlag("N");
	}

	public ItemBean(String itemType, String regcode, int seqno, String name, String enable, String addOnFeeFlag) {
		this.setItemType(itemType);
		this.regcode = regcode;
		this.seqno = seqno;
		this.name = name;
		this.enable = enable;
		this.addOnFeeFlag = addOnFeeFlag;
	}
	
	public ItemBean(String itemType, String regcode, int seqno, String name, String enable, String addOnFeeFlag, String bonusFlag) {
		this.setItemType(itemType);
		this.regcode = regcode;
		this.seqno = seqno;
		this.name = name;
		this.enable = enable;
		this.addOnFeeFlag = addOnFeeFlag;
		this.bonusFlag = bonusFlag;
	}
	
	public void setRegcode(String regcode) {
		this.regcode = regcode;
	}

	public String getRegcode() {
		return this.regcode;
	}

	/**
	 * @return the seqno
	 */
	public int getSeqno() {
		return seqno;
	}

	/**
	 * @param seqno the seqno to set
	 */
	public void setSeqno(int seqno) {
		this.seqno = seqno;
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
	 * @return the enable
	 */
	public String getEnable() {
		return enable;
	}

	/**
	 * @param enable the enable to set
	 */
	public void setEnable(String enable) {
		this.enable = enable;
	}

	/**
	 * @return the addOnFeeFlag
	 */
	public String getAddOnFeeFlag() {
		return addOnFeeFlag;
	}

	/**
	 * @param addOnFeeFlag the addOnFeeFlag to set
	 */
	public void setAddOnFeeFlag(String addOnFeeFlag) {
		this.addOnFeeFlag = addOnFeeFlag;
	}

	/**
	 * @return the itemType
	 */
	public String getItemType() {
		return itemType;
	}

	/**
	 * @param itemType the itemType to set
	 */
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	/**
	 * @return the bonusFlag
	 */
	public String getBonusFlag() {
		return bonusFlag;
	}

	/**
	 * @param bonusFlag the bonusFlag to set
	 */
	public void setBonusFlag(String bonusFlag) {
		this.bonusFlag = bonusFlag;
	}

}
