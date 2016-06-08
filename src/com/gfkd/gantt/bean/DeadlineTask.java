package com.gfkd.gantt.bean;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DeadlineTask {

	private String taskCode;// 任务内码
	private String taskName;// 任务名称
	private String taskDesc;
	private Date taskStartTime;// 任务开始时间
	private Date taskEndTime;// 任务结束时间
	private Date taskDeadlineTime;// 任务结束时间
	private String parent;// 父任务
	
	private String supportId;// 支撑任务的id
	private String slbd;// 支撑任务的id
	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");

	public DeadlineTask() {
		super();
	}

	public DeadlineTask(ResultSet rs) {
		try {
			this.taskCode = rs.getString("TASKCODE");
			this.taskName = rs.getString("TASKNAME");
			this.taskDesc = rs.getString("TASKDESC");
			this.parent = rs.getString("PARENT");
			String startDate = rs.getString("TASK_START_TIME");
			String endDate = rs.getString("TASK_END_TIME");
			String deadline = rs.getString("TASK_DEADLINE_TIME");
			this.taskStartTime = sdf.parse(startDate);
			this.taskEndTime = sdf.parse(endDate);
			this.taskDeadlineTime = sdf.parse(deadline);
			this.slbd = rs.getString("SLBD");
			
			this.supportId=rs.getString("SUPPORT_ID");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
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

	public String getTaskDesc() {
		return taskDesc;
	}

	public void setTaskDesc(String taskDesc) {
		this.taskDesc = taskDesc;
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

	public Date getTaskDeadlineTime() {
		return taskDeadlineTime;
	}

	public void setTaskDeadlineTime(Date taskDeadlineTime) {
		this.taskDeadlineTime = taskDeadlineTime;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public String getSupportId() {
		return supportId;
	}

	public void setSupportId(String supportId) {
		this.supportId = supportId;
	}

	public String getSlbd() {
		return slbd;
	}

	public void setSlbd(String slbd) {
		this.slbd = slbd;
	}
}
