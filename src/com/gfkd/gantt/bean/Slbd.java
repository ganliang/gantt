package com.gfkd.gantt.bean;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Slbd {

	private String slbdId;
	private String slbdName;
	private String slbdDesc;

	public Slbd() {
	}

	public Slbd(ResultSet rs) {
		try {
			this.slbdId = rs.getString("SLBD_ID");
			this.slbdName = rs.getString("SLBD_NAME");
			this.slbdDesc = rs.getString("SLBD_DESC");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Slbd(String slbdId, String slbdName, String slbdDesc) {
		super();
		this.slbdId = slbdId;
		this.slbdName = slbdName;
		this.slbdDesc = slbdDesc;
	}

	public String getSlbdId() {
		return slbdId;
	}

	public void setSlbdId(String slbdId) {
		this.slbdId = slbdId;
	}

	public String getSlbdName() {
		return slbdName;
	}

	public void setSlbdName(String slbdName) {
		this.slbdName = slbdName;
	}

	public String getSlbdDesc() {
		return slbdDesc;
	}

	public void setSlbdDesc(String slbdDesc) {
		this.slbdDesc = slbdDesc;
	}
}
