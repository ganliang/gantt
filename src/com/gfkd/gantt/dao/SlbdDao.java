package com.gfkd.gantt.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.gfkd.gantt.bean.Slbd;
import com.gfkd.gantt.bean.Task;
import com.gfkd.gantt.util.ConnectionPool;

public class SlbdDao {

	/**
	 * 获取所有的受领部队
	 * 
	 * @return
	 */
	public List<Slbd> getAll() {
		Connection connection = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
		List<Slbd> slbds = new ArrayList<Slbd>();
		try {
			connection = ConnectionPool.getConnection();
			pst = connection.prepareStatement("SELECT SLBD_ID,SLBD_NAME,SLBD_DESC FROM SLBD");
			rs = pst.executeQuery();
			while (rs.next()) {
				slbds.add(new Slbd(rs));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ConnectionPool.closeResources(rs, connection, pst);
		}
		return slbds;
	}

}
