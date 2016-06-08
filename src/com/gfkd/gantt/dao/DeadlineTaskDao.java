package com.gfkd.gantt.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.gfkd.gantt.bean.DeadlineTask;
import com.gfkd.gantt.util.ConnectionPool;

public class DeadlineTaskDao {
	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
	/**
	 * 获取任务
	 * @return
	 */
	public List<DeadlineTask> getDeadlineTask() {
		Connection connection=null;
		ResultSet rs=null;
		PreparedStatement pst=null;
		List<DeadlineTask> tasks=new ArrayList<DeadlineTask>();
		try {
			connection=ConnectionPool.getConnection();
			pst=connection.prepareStatement("SELECT TASKCODE,TASKNAME,TASKDESC,PARENT,TASK_START_TIME,TASK_END_TIME,TASK_DEADLINE_TIME,SUPPORT_ID,SLBD FROM DEADLINE_TASK ORDER BY CREATE_DATE ASC");
			rs=pst.executeQuery();
			while(rs.next()){
				tasks.add(new DeadlineTask(rs));
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
		
		Object text = map.get("text");
		Object desc = map.get("desc");
		Object taskCode = map.get("id");
		
		Object end_date = map.get("end_date");
		
		Object deadline = map.get("deadline");
	    
		Object parent = map.get("$rendered_parent");
		Object supportName = map.get("supportName");
		Object slbd = map.get("slbd");
		try {
			connection=ConnectionPool.getConnection();
			//如果isnew为真 说明这条记录是新增
			if(isnew!=null&&"true".equalsIgnoreCase(isnew.toString())){
				pst=connection.prepareStatement("INSERT INTO DEADLINE_TASK(TASKCODE,TASKNAME,TASKDESC,TASK_START_TIME,TASK_END_TIME,TASK_DEADLINE_TIME,PARENT,SUPPORT_ID,SLBD,CREATE_DATE) VALUES(?,?,?,?,?,?,?,?,?,NOW())");
				pst.setString(1, UUID.randomUUID().toString().replace("-", ""));
				pst.setString(2, text.toString());
			    pst.setString(3, desc.toString());
			    pst.setString(4, start_date.toString());
			    pst.setString(5, end_date.toString());
			    pst.setString(6, deadline.toString());
			    pst.setString(7, parent.toString().equals("0.0")?null:parent.toString());
			    pst.setString(8, supportName.toString());
			    pst.setString(9, slbd.toString());
			}
			//否则 是编辑任务
			else{
				pst=connection.prepareStatement("UPDATE DEADLINE_TASK SET TASKNAME=?,TASKDESC=?,TASK_START_TIME=?,TASK_END_TIME=?,TASK_DEADLINE_TIME=?,SUPPORT_ID=?,slbd=? WHERE TASKCODE=?");
			    pst.setString(1, text.toString());
			    pst.setString(2, desc.toString());
			    pst.setString(3, start_date.toString());
			    pst.setString(4, end_date.toString());
			    pst.setString(5, deadline.toString());
			    pst.setString(6, supportName.toString());
			    pst.setString(7, slbd.toString());
			    pst.setString(8, taskCode.toString());
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
	 * @param taskCodes 任务内码数组
	 * @return
	 */
	public String delete(String taskCodes) {
		Connection connection=null;
		PreparedStatement pst=null;
		String json="";
		try {
			connection=ConnectionPool.getConnection();
			String[] codes = taskCodes.split(",");
			StringBuilder builder=new StringBuilder();
			for(int i=0;i<codes.length;i++){
				builder.append("?");
				if(i!=codes.length-1){
					builder.append(",");
				}
			}
			//删除该任务
			pst=connection.prepareStatement("DELETE FROM DEADLINE_TASK WHERE TASKCODE IN("+builder.toString()+")");
            for (int i = 0; i < codes.length; i++) {
				pst.setString(i+1, codes[i]);
			}
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
	public String changeDate(Map map) {
		Connection connection=null;
		PreparedStatement pst=null;
		String json="";
		
		Object start_date = map.get("start_date");
		
		Object end_date = map.get("end_date");
		
	    Object taskCode = map.get("id");
		try {
			connection=ConnectionPool.getConnection();
 			pst=connection.prepareStatement("UPDATE DEADLINE_TASK SET TASK_START_TIME=?,TASK_END_TIME=? WHERE TASKCODE=?");
			pst.setString(1, start_date.toString());
			pst.setString(2, end_date.toString());
			pst.setString(3, taskCode.toString());
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
