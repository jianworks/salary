package com.csjian.form;

import com.csjian.util.Resources;
import com.csjian.model.dao.*;
import com.csjian.controller.SessionHelper;
import com.lowagie.text.pdf.*;

public class BaseForm extends PdfPageEventHelper {

	protected SessionHelper sessionHelper;
	protected Resources resources;
	protected DaoFactory daoFactory;

	public void setSessionHelper(SessionHelper sessionHelper) {
		this.sessionHelper = sessionHelper;
	}

	public void setResources(Resources resources) {
		this.resources = resources;
	}
	
	public void setDaoFactory(DaoFactory daoFactory) {
		this.daoFactory = daoFactory;
	}
}
