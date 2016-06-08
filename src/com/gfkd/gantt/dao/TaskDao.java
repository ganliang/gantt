package com.gfkd.gantt.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.gfkd.gantt.bean.Task;
import com.gfkd.gantt.util.ConnectionPool;

public class TaskDao {
	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
	/**
	 * 获取任务
	 * @return
	 */
	public List<Task> getTask() {
		Connection connection=null;
		ResultSet rs=null;
		PreparedStatement pst=null;
		List<Task> tasks=new ArrayList<Task>();
		try {
			connection=ConnectionPool.getConnection();
			pst=connection.prepareStatement("SELECT ROW_NUMBER() OVER(ORDER BY TASKCODE) as ROWNUM,TASKCODE,TASKNAME,TASK_START_TIME,TASK_END_TIME,TASKDESC FROM TASK");
			rs=pst.executeQuery();
			while(rs.next()){
				tasks.add(new Task(rs));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			ConnectionPool.closeResources(rs, connection, pst);
		}
		return tasks;
	}

	/**
	 * 保存或者编辑任务
	 * @param map
	 * @return
	 */
	public String update(Map map) {
		Connection connection=null;
		PreparedStatement pst=null;
		String json="";
		
		Object isnew = map.get("$new");
		
		Object start_date = map.get("start_date");
		String start = start_date.toString().substring(0, 10);
		try {
			Date startDate = sdf.parse(start);
			startDate.setDate(startDate.getDate()+1);
			start=sdf.format(startDate);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		
		
		Object text = map.get("text");
		Object desc = map.get("desc");
		Object innerCode = map.get("innerCode");
		
		Object end_date = map.get("end_date");
		String end = end_date.toString().substring(0, 10);
		try {
			Date endDate = sdf.parse(end);
			endDate.setDate(endDate.getDate()+1);
			end=sdf.format(endDate);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
	    
		Object parent = map.get("parent");
		try {
			connection=ConnectionPool.getConnection();
			//如果isnew为真 说明这条记录是新增
			if(isnew!=null&&"true".equalsIgnoreCase(isnew.toString())){
				pst=connection.prepareStatement("INSERT INTO TASK(TASKCODE,TASKNAME,TASKDESC,TASK_START_TIME,TASK_END_TIME) VALUES(?,?,?,?,?)");
				pst.setString(1, innerCode.toString());
				pst.setString(2, text.toString());
			    pst.setString(3, desc.toString());
			    pst.setString(4, start);
			    pst.setString(5, end);
			}
			//否则 是编辑任务
			else{
				pst=connection.prepareStatement("UPDATE TASK SET TASKNAME=?,TASKDESC=?,TASK_START_TIME=?,TASK_END_TIME=? WHERE TASKCODE=?");
			    pst.setString(1, text.toString());
			    pst.setString(2, desc.toString());
			    pst.setString(3, start);
			    pst.setString(4, end);
			    pst.setString(5, innerCode.toString());
			}
			pst.execute();
			json="success";
		} catch (SQLException e) {
			json=e.getLocalizedMessage();
			e.printStackTrace();
		}catch (Exception e) {
			json=e.getLocalizedMessage();
			e.printStackTrace();
		}finally{
			ConnectionPool.closeResources(null, connection, pst);
		}
		return json;
	}

	/**
	 * 删除任务
	 * @param taskCode 任务内码
	 * @return
	 */
	public String delete(String taskCode) {
		Connection connection=null;
		PreparedStatement pst=null;
		String json="";
		try {
			connection=ConnectionPool.getConnection();
			pst=connection.prepareStatement("DELETE FROM TASK WHERE TASKCODE=?");
			pst.setString(1, taskCode);
			pst.execute();
			json="success";
		} catch (SQLException e) {
			json=e.getLocalizedMessage();
			e.printStackTrace();
		}finally{
			ConnectionPool.closeResources(null, connection, pst);
		}
		return json;
	}

	/**
	 * 改变任务的起始时间
	 * @param map
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public String changeDate(Map map) {
		Connection connection=null;
		PreparedStatement pst=null;
		String json="";
		
		Object start_date = map.get("start_date");
		String start = start_date.toString().substring(0, 10);
		try {
			Date startDate = sdf.parse(start);
			startDate.setDate(startDate.getDate()+1);
			start=sdf.format(startDate);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		
		Object end_date = map.get("end_date");
		String end = end_date.toString().substring(0, 10);
		try {
			Date endDate = sdf.parse(end);
			endDate.setDate(endDate.getDate()+1);
			end=sdf.format(endDate);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
	    
	    Object innerCode = map.get("innerCode");
		try {
			connection=ConnectionPool.getConnection();
 			pst=connection.prepareStatement("UPDATE TASK SET TASK_START_TIME=?,TASK_END_TIME=? WHERE TASKCODE=?");
			pst.setString(1, start);
			pst.setString(2, end);
			pst.setString(3, innerCode.toString());
 			pst.execute();
			json="success";
		} catch (SQLException e) {
			json=e.getLocalizedMessage();
			e.printStackTrace();
		}catch (Exception e) {
			json=e.getLocalizedMessage();
			e.printStackTrace();
		}finally{
			ConnectionPool.closeResources(null, connection, pst);
		}
		return json;
	}

}
