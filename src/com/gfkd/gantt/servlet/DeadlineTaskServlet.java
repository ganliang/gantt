package com.gfkd.gantt.servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gfkd.gantt.bean.DeadlineTask;
import com.gfkd.gantt.bean.GanttLink;
import com.gfkd.gantt.bean.GanttTask;
import com.gfkd.gantt.dao.DeadlineTaskDao;
import com.google.gson.Gson;
@WebServlet(urlPatterns={"/DeadlineTaskServlet"})
public class DeadlineTaskServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1813857659399288479L;
	Gson gson=new Gson();
	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
	SimpleDateFormat sdf2=new SimpleDateFormat("dd-MM-yyyy HH:mm");
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String type=request.getParameter("type");
		String value=request.getParameter("value");
		DeadlineTaskDao deadlineDao=new DeadlineTaskDao();
	    String json="";
	    switch (type) {
	    //获取任务
		case "getTask":
			List<DeadlineTask> tasks=deadlineDao.getDeadlineTask();
			json=getGanttDeadline(tasks);
			break;
		//保存或者编辑任务
		case "update":
			Map map=gson.fromJson(value, Map.class);
			json=deadlineDao.update(map);
			break;
		//删除任务
		case "delete":
			json=deadlineDao.delete(value);
			break;
	    //改变任务的起始时间
		case "changeDate":
			map=gson.fromJson(value, Map.class);
			json=deadlineDao.changeDate(map);
			break;
		default:
			break;
		}
	    System.out.println(json);
	    response.getWriter().print(json);
	}

	/**
	 * 获取到gantt的数据
	 * @param tasks
	 * @return
	 */
	private String getGanttDeadline(List<DeadlineTask> tasks) {
		
		List<GanttTask> ganttTasks=new ArrayList<GanttTask>();
		List<GanttLink> ganttLinks=new ArrayList<GanttLink>();
		
		//遍历任务
        for (DeadlineTask deadlineTask : tasks) {
			String parent=deadlineTask.getParent();
			//不存在父任务
			if(parent==null||"".equals(parent)){
				getDeadlineTask(deadlineTask,tasks,ganttTasks,ganttLinks);
			}
		}
		return "{data:"+gson.toJson(ganttTasks)+",links:"+gson.toJson(ganttLinks)+"}";
	}

	private void getDeadlineTask(DeadlineTask deadlineTask, List<DeadlineTask> tasks, List<GanttTask> ganttTasks,
			List<GanttLink> ganttLinks) {
		  GanttTask ganttTask=new GanttTask();
	   	  ganttTask.setId(deadlineTask.getTaskCode());
	   	  ganttTask.setText(deadlineTask.getTaskName());
	   	  ganttTask.setDesc(deadlineTask.getTaskDesc());
	   	  ganttTask.setOpen(true);
	   	  ganttTask.setProgress(0);
	   	  ganttTask.setSortOrder(0);
	   	  ganttTask.setDuration(timeDiffer(deadlineTask.getTaskStartTime(), deadlineTask.getTaskEndTime()));
	   	  ganttTask.setStart_date(sdf2.format(deadlineTask.getTaskStartTime()));
 		  ganttTask.setDeadline(sdf.format(deadlineTask.getTaskDeadlineTime()));
 		  ganttTask.setParent(deadlineTask.getParent());
 		  ganttTask.setSupportId(deadlineTask.getSupportId());
 		  ganttTask.setSlbd(deadlineTask.getSlbd());
 		  
 		  ganttTasks.add(ganttTask);
 		  
 		  List<DeadlineTask> childrens=getChildrens(deadlineTask.getTaskCode(),tasks);
 		  //判断是否存在子节点
 		  if(childrens.size()>0){
 			  for (DeadlineTask children : childrens) {
 				 //设置任务的链接 
 				 GanttLink ganttLink=new GanttLink();
 				 ganttLink.setId(children.getTaskCode());
 				 ganttLink.setSource(deadlineTask.getTaskCode());
 				 ganttLink.setTarget(children.getTaskCode());
 				 ganttLinks.add(ganttLink);
 				 
 				 children.setParent(deadlineTask.getTaskCode());
 				 getDeadlineTask(children, tasks, ganttTasks, ganttLinks);
			  }
 		  }
          
	}

	/**
	 * 获取子节点
	 * @param parent 任务内码
	 * @param tasks 任务实体对象集合
	 * @return
	 */
	private List<DeadlineTask> getChildrens(String parent, List<DeadlineTask> tasks) {
		List<DeadlineTask> childrens=new ArrayList<DeadlineTask>();
		for (DeadlineTask deadlineTask : tasks) {
			if(parent.equals(deadlineTask.getParent())){
				childrens.add(deadlineTask);
			}
		}
		return childrens;
	}

	/**
	 * 求两个时间之差
	 * @param start 开始时间
	 * @param end 结束时间
	 * @return
	 */
	private int timeDiffer(Date start, Date end){
		long diff=end.getTime()-start.getTime();
		long nd=1000*24*60*60;//一天的毫秒数
		long nh=1000*60*60;//一小时的毫秒数
		long nm=1000*60;//一分钟的毫秒数
		long day=diff/nd;
		//return (int)(diff%nd%nh/nm + day*24*60);
		return (int)(diff/nm/15);
	}
}
