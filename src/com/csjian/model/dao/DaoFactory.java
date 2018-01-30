package com.csjian.model.dao;

import org.apache.commons.pool.BasePoolableObjectFactory;
import org.apache.commons.pool.impl.StackObjectPool;

import javax.sql.DataSource;
import com.csjian.model.dao.DaoFactory;

public class DaoFactory {
	public static final int POOL_SIZE = 9;
	public static final byte BSALARY = 0;
	public static final byte COMPANY = 1;
	public static final byte EMPLOYEE = 2;	
	public static final byte GROUP = 3;
	public static final byte INSURANCE = 4;
	public static final byte ITEM = 5;
	public static final byte SALARY = 6;
	public static final byte REPORTFILE = 7;
	public static final byte INSURANCEADDON = 8;
	
	private DataSource dataSource;
    
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	//=========================================================
	
	private StackObjectPool[] pools;
	
	private void createPool(final byte index, final Class c){
		
		pools[index] = new StackObjectPool(
				new BasePoolableObjectFactory(){
					public Object makeObject() throws Exception{
						TemplateDao dao = (TemplateDao)c.newInstance();
						dao.setDataSource(dataSource);
						return dao;
					}
				}
		);
	}
	
	private void initPools(){
		pools = new StackObjectPool[POOL_SIZE];
		createPool(BSALARY, BsalaryDao.class);
		createPool(COMPANY,CompanyDao.class);
		createPool(EMPLOYEE,EmployeeDao.class);
		createPool(GROUP,GroupDao.class);
		createPool(INSURANCE,InsuranceDao.class);
		createPool(ITEM,ItemDao.class);
		createPool(SALARY,SalaryDao.class);
		createPool(REPORTFILE,ReportFileDao.class);
		createPool(INSURANCEADDON,InsuranceAddOnDao.class);
	}
	
	public Object createDao(byte daoIndex) {
		if(pools == null){
			initPools();
		}
		
		TemplateDao dao = null;
		try {
			dao= (TemplateDao)pools[daoIndex].borrowObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dao;
	}
	
	public void returnDao(byte daoIndex, Object dao) {
		try {
			pools[daoIndex].returnObject(dao);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
