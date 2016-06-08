package com.gfkd.gantt.bean;

/**
 * 任务关系类
 *
 */
public class GanttTask {

	private String id;//
	private String start_date;// 开始时间
	private String deadline;// 截止时间
	private String text;// 任务名称
	private float progress;// 进度条
	private int duration;// 持续事件
	private int sortOrder;// 排序
	private String parent;// 父节点
	private boolean open = true;//
	private String desc;
	private String slbd;

	private String supportId;// 支撑任务的id

	private String innerCode;// 内码

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStart_date() {
		return start_date;
	}

	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public float getProgress() {
		return progress;
	}

	public void setProgress(float progress) {
		this.progress = progress;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public int getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public String getInnerCode() {
		return innerCode;
	}

	public void setInnerCode(String innerCode) {
		this.innerCode = innerCode;
	}

	public boolean isOpen() {
		return true;
	}

	public void setOpen(boolean open) {
		this.open = true;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getDeadline() {
		return deadline;
	}

	public void setDeadline(String deadline) {
		this.deadline = deadline;
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
