package com.csjian.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class Resources {

	public static String DB_DRIVER = "db.driver";
	public static String DB_URL = "db.url";
	public static String DB_USER = "db.user";
	public static String DB_PASSWORD = "db.password";

	public static String SMTP_HOST = "smtp.host";
	public static String SMTP_USER = "smtp.user";
	public static String SMTP_PASSWORD = "smtp.password";
	public static String SMTP_FROM = "smtp.from";
	public static String PAGE_SIZES = "page.sizes";

	public static String ROOT_FOLDER = "root.folder";

	public static String FILE_PATH = "file.path";

	private HashMap map = new HashMap();

	public Resources(String xmlPropFilePath) throws IOException, DocumentException {
		InputStream in = this.getClass().getResourceAsStream(xmlPropFilePath);
		InputStreamReader ir = new InputStreamReader(in);
		SAXReader reader = new SAXReader(false);
		Document doc = reader.read(ir);
		Element root = doc.getRootElement();
		List list = root.selectNodes("entry");
		for (int i = 0, s = list.size(); i < s; i++) {
			Element e = (Element) list.get(i);
			String key = e.attributeValue("key");
			String val = e.getText();
			map.put(key, val);
		}
	}

	public String getApProperty(String key) {
		String result = (String) map.get(key);
		return result;
	}

	public Integer getApPropertyAsInteger(String key) {
		String result = (String) map.get(key);
		return new Integer(result);
	}

	public int getApPropertyAsInt(String key) {
		String result = (String) map.get(key);
		return Integer.parseInt(result);
	}

	public boolean getApPropertyAsBool(String key) {
		String result = ((String) map.get(key)).trim().toLowerCase();
		if (result.equals("true") || result.equals("yes") || result.equals("y"))
			return true;
		else
			return false;
	}

	public Boolean getApPropertyAsBoolean(String key) {
		return new Boolean(getApPropertyAsBool(key));
	}
}
