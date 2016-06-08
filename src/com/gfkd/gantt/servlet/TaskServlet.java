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

import com.gfkd.gantt.bean.GanttLink;
import com.gfkd.gantt.bean.GanttTask;
import com.gfkd.gantt.bean.Task;
import com.gfkd.gantt.dao.TaskDao;
import com.google.gson.Gson;

@WebServlet("/TaskServlet")
public class TaskServlet extends HttpServlet{

	private static final long serialVersionUID = 6500744895254798283L;
	Gson gson=new Gson();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    
		String type=request.getParameter("type");
		String value=request.getParameter("value");
	    TaskDao taskDao=new TaskDao();
	    String json="";
	    switch (type) {
	    //获取任务
		case "getTask":
			List<Task> tasks=taskDao.getTask();
			json=getGantt(tasks);
			break;
		//保存或者编辑任务
		case "update":
			Map map=gson.fromJson(value, Map.class);
			json=taskDao.update(map);
			break;
		//删除任务
		case "delete":
			json=taskDao.delete(value);
			break;
	    //改变任务的起始时间
		case "changeDate":
			map=gson.fromJson(value, Map.class);
			json=taskDao.changeDate(map);
			break;
		default:
			break;
		}
	    System.out.println(json);
	    response.getWriter().print(json);
	}

	/**
	 * 获取gantt
	 * @param tasks
	 * @return
	 */
	private String getGantt(List<Task> list) {
		List<GanttLink> links=new ArrayList<GanttLink>();
		List<GanttTask> tasks=new ArrayList<GanttTask>();
		//遍历任务
		for (Task task : list) {
			String taskCode = task.getTaskCode();
			//如果没有父节点
			if(!hasParent(taskCode, list)){
				setGanttTask(tasks,links,list,task);
			}
		}
		String data = gson.toJson(tasks);
		String link=gson.toJson(links);
		return "{data:"+data+",links:"+link+"}";
	}
	
	/**
	 * 
	 * @param tasks
	 * @param links
	 * @param list
	 * @param task 
	 */
	private void setGanttTask(List<GanttTask> tasks, List<GanttLink> links, List<Task> list, Task task) {
		SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
		SimpleDateFormat sdf2=new SimpleDateFormat("yyyy-MM-dd");
		GanttTask ganttTask=new GanttTask();
		ganttTask.setId(task.getTaskCode());
		ganttTask.setInnerCode(task.getTaskCode());
		ganttTask.setText(task.getTaskName());
		ganttTask.setStart_date(sdf.format(task.getTaskStartTime()));
		ganttTask.setSortOrder(0);
		ganttTask.setProgress((float) 0.2);
		ganttTask.setDuration(timeDiffer(task.getTaskStartTime(), task.getTaskEndTime()));
		ganttTask.setParent(task.getParentId());
		ganttTask.setDesc(task.getTaskDesc());
		Date deadline=task.getTaskEndTime();
		deadline.setDate(deadline.getDate()+1);
		ganttTask.setDeadline(sdf2.format(deadline));
		
		tasks.add(ganttTask);
		List<Task> childrens = getChildrens(task.getTaskCode(), list);
		for (Task task2 : childrens) {
			//添加连接
			GanttLink link=new GanttLink();
			link.setId(task2.getTaskCode());
			link.setSource(task.getTaskCode());
			link.setTarget(task2.getTaskCode());
			link.setType(GanttLink.FINISH_TO_START);
			links.add(link);
			//设置任务
			task2.setParentId(task.getTaskCode());
			setGanttTask(tasks, links, list, task2);
		}
	}

	//判断是否有父节点
	private boolean hasParent(String taskCode,List<Task> list){
		for (Task task : list) {
			String code = task.getTaskCode();
			if(!code.equals(taskCode)&&taskCode.contains(code)){
				return true;
			}
		}
		return false;
	}
	
	//获取子节点
	private List<Task> getChildrens(String taskCode,List<Task> list){
		List<Task> tasks=new ArrayList<Task>();
		for (Task task : list) {
			String code = task.getTaskCode();
			if(code.contains(taskCode)&&!code.equals(taskCode)){
				tasks.add(task);
			}
		}
		return tasks;
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
		return (int)(diff/nd);
	}
}
