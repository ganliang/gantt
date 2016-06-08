package com.gfkd.gantt.bean;

/**
 * 任务关系类
 *
 */
public class GanttLink {
	
	private String id;
	//源任务ID
	private String source;
	//目标任务ID
	private String target;
	/**
	 * 链接类型
	 * 0：从源任务尾到目标任务头
     * 1：从源任务头到目标任务头
     * 2：从源任务尾到目标任务尾
	 */
	private int type;
	public static final int FINISH_TO_START=0;
	public static final int START_TO_START=1;
	public static final int FINISH_TO_FINISH=2;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	
	public String getJSON(){
		StringBuilder builder=new StringBuilder();
		
		builder.append("{");
		builder.append("\"id\":\"" + id + "\"");
		builder.append(",");
		builder.append("\"source\":\"" + source + "\"");
		builder.append(",");
		builder.append("\"target\":\"" + target + "\"");
		builder.append(",");
		builder.append("\"type\":\"" + type + "\"");
		
		builder.append("}");
		
		return builder.toString();
	}
}
