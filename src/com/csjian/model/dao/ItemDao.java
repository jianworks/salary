package com.csjian.model.dao;

import com.csjian.model.bean.ItemBean;

import java.sql.*;
import java.util.*;

public class ItemDao extends TemplateDao {
	/**
	 * 
	 * @param regcode
	 * @param itemType 是加項(P)或是減項(M) 
	 * @return
	 * @throws Exception
	 */
	public List<ItemBean> getItemList(String regcode, String itemType) throws Exception {
		Connection conn = null;
		ResultSet rs = null;
		List<ItemBean> items = new ArrayList<ItemBean>();
		try {
			conn = this.dataSource.getConnection();
			Statement stmt = conn.createStatement();
			String script = null;
			if (itemType.equals("P")) {
				script = "select m.regcode, m.seqno, m.name, e.seqno, m.addOnFeeFlag, m.bonusFlag " +
						"from PItems m left join enablePs e on m.seqno=e.seqno and e.regcode='" + regcode + "' " +
						"where m.regcode in ('" + regcode + "', 'admin')";
			} else {
				script = "select m.regcode, m.seqno, m.name, e.seqno, m.addOnFeeFlag, m.bonusFlag " +
						"from MItems m left join enableMs e on m.seqno=e.seqno and e.regcode='" + regcode + "' " +
						"where m.regcode in ('" + regcode + "', 'admin')";
			}
			
			rs = stmt.executeQuery(script);
			ItemBean item = null;
			while (rs.next()) {
				item = new ItemBean(
							itemType,
							rs.getString(1),
							rs.getInt(2),
							rs.getString(3),
							rs.getString(4) != null ? "Y" : "N",
							rs.getString(5), 
							rs.getString(6)
						);
				items.add(item);
			}		

			return items;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			close(rs);
			close(conn);
		}
	}

	public void updateItem(ItemBean item, String regcode) throws Exception {
		Connection conn = null;
		try {
			conn = this.dataSource.getConnection();
			conn.setAutoCommit(false);
			Statement stmt = conn.createStatement();
			if (item.getRegcode().equals(regcode)) {
				if (item.getSeqno()==0) {
					stmt.executeUpdate("INSERT INTO " + item.getItemType() + "Items(regcode, name, addOnFeeFlag, bonusFlag, disable) VALUES('" + regcode + "', " + 
							"'" + item.getName() + "', '" + item.getAddOnFeeFlag() + "','" + item.getBonusFlag() + "', 'N')", Statement.RETURN_GENERATED_KEYS);
					ResultSet keys = stmt.getGeneratedKeys();
					keys.next();
		            item.setSeqno(keys.getInt(1));
		            
				} else {
					stmt.executeUpdate("UPDATE " + item.getItemType() + "Items SET name='" + item.getName() + 
							"', addOnFeeFlag='" + item.getAddOnFeeFlag() + "' , bonusFlag='" + item.getBonusFlag() + "' " + 
							"WHERE regcode='" + item.getRegcode() + "' AND seqno='" + item.getSeqno() + "'");
				}
			}
			stmt.executeUpdate("DELETE FROM enable" + item.getItemType() + "s WHERE regcode='" + regcode + "' AND seqno='" + item.getSeqno() + "' ");
			if (item.getEnable().equals("Y")) {
				stmt.executeUpdate("INSERT INTO enable" + item.getItemType() + "s(regcode, seqno) VALUES('" + regcode + "', " + 
						"'" + item.getSeqno() + "')");
			}
			
			conn.commit();
		} catch (Exception e) {
			conn.rollback();
			throw e;
		} finally {
			conn.setAutoCommit(true);
			close(conn);
		}
	}

}
