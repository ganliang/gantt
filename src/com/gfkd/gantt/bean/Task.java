package com.gfkd.gantt.bean;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class Task {

	private int id;//行号
	private String taskCode;//任务内码
	private String taskName;//任务名称
	private Date taskStartTime;//任务开始时间
	private Date taskEndTime;//任务结束时间
	private String taskDesc;
	
	public Task() {
		super();
	}
	public Task(ResultSet rs) {
		try {
			this.id = rs.getInt("ROWNUM");
			this.taskCode = rs.getString("TASKCODE");
			this.taskName = rs.getString("TASKNAME");
			this.taskStartTime = rs.getDate("TASK_START_TIME");
			this.taskEndTime = rs.getDate("TASK_END_TIME");
			this.taskDesc = rs.getString("TASKDESC");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public Task(int id, String taskCode, String taskName, Date taskStartTime, Date taskEndTime, String taskDesc) {
		super();
		this.id = id;
		this.taskCode = taskCode;
		this.taskName = taskName;
		this.taskStartTime = taskStartTime;
		this.taskEndTime = taskEndTime;
		this.taskDesc = taskDesc;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTaskCode() {
		return taskCode;
	}
	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public Date getTaskStartTime() {
		return taskStartTime;
	}
	public void setTaskStartTime(Date taskStartTime) {
		this.taskStartTime = taskStartTime;
	}
	public Date getTaskEndTime() {
		return taskEndTime;
	}
	public void setTaskEndTime(Date taskEndTime) {
		this.taskEndTime = taskEndTime;
	}
	public String getTaskDesc() {
		return taskDesc;
	}
	public void setTaskDesc(String taskDesc) {
		this.taskDesc = taskDesc;
	}
	
	private String parentId;

	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
}
