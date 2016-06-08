<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>甘特图任务截止时间</title>
<link rel="stylesheet" href="css/dhtmlxgantt.css" type="text/css" media="screen" title="no title" charset="utf-8">
<link rel="stylesheet" type="text/css" href="resources/css/ext-all.css"/>

<script type="text/javascript" src="js/ext-all.js" charset="UTF-8"></script>
<script type="text/javascript" src="js/ext-lang-zh_CN.js" charset="UTF-8"></script>

<script type="text/javascript" src="js/dhtmlxgantt.js" charset="utf-8" ></script>
<script type="text/javascript" src="js/dhtmlxgantt_marker.js" charset="UTF-8"></script>
<script type="text/javascript" src="js/dhtmlxgantt_tooltip.js" charset="UTF-8"></script>
<script type="text/javascript" src="js/locale_cn.js" charset="UTF-8"></script>
<style type="text/css">
	html, body{ 
		height:100%; 
		padding:0px; 
		margin:0px; 
		overflow: hidden;
	}
	.deadline {
			position: absolute;
			border-radius: 12px;
			border: 2px solid #585858;
			-moz-box-sizing: border-box;
			box-sizing: border-box;

			width: 22px;
			height: 22px;
			margin-left: -11px;
			margin-top: 6px;
			z-index: 1;
			background: url("common/deadline_icon.png") center no-repeat;
		}

		.overdue-indicator {
			width: 24px;
			margin-top: 5px;
			height: 24px;
			-moz-box-sizing: border-box;
			box-sizing: border-box;
			border-radius: 17px;
			color: white;
			background: rgb(255, 60, 60);
			line-height: 25px;
			text-align: center;
			font-size: 24px;
		}
</style>
</head>
<body>
<div id="gantt_here" style='width:100%; height:90%;'></div>
<script type="text/javascript">
var data;
var markerId;
//处理ajax请求
function handleRequest(url,type,value){
	var result;
	Ext.Ajax.request({
		url:url,
		params:{
			type:type,
			value:value
		},
		method:'POST',
		async:false,
		success:function(response,opts){
			result=response.responseText;
		},
		failure:function(response,opts){
		}
	});
	return result;
}
//获取gantt图的数据
function getGanttTasks(){
	var tasks=handleRequest('DeadlineTaskServlet','getTask',null);
	data=Ext.JSON.decode(tasks);
	return data;
}
//配置gantt
function setGanttConfig(){
	gantt.config.grid_width = 420;
	//配置gantt的行标签
    gantt.config.columns=[{name: "overdue", label: "", width: 38, template: function (obj) {
						  if (obj.deadline) {
								var deadline = gantt.date.parseDate(obj.deadline, "xml_date");
								if (deadline && obj.end_date > deadline) {
									return '<div class="overdue-indicator">!</div>';
								}
							}
							return '<div></div>';
						}
						},
                          {name:'text',label:'任务名称',width:'*',tree:true},
                          {name:'start_date',label:'开始时间',width:80,align:'center'},
                          {name: "deadline", label: "最终期限", width:80, align: "center" },
                          {name:'duration',label:'周长',width:40,align:'center'},
                          {name:'add',label:'',width:44}//添加任务
                         ];
    //点击gantt的标签头 对任务进行排序
    gantt.config.sort = true; 
    
    //显示所有的标签
    gantt.config.show_markers = true;
    
    //设置gantt图的刻度
    gantt.config.scale_unit= "day";
    gantt.config.date_scale = "%F, %d"; 
    gantt.config.step = 1;  
    
    //添加一个当日的标签
    var date_to_str = gantt.date.date_to_str(gantt.config.task_date);
    markerId = gantt.addMarker({
        start_date: new Date(),
        css: "today",
        text: "Now", 
        title:date_to_str( new Date())
    });
    //定时刷新标签 
    setInterval(function(){
        var today = gantt.getMarker(markerId);
        today.start_date = new Date();
        today.title = date_to_str(today.start_date);
        gantt.updateMarker(markerId);
    }, 1000*60*60);//每小时刷新一次 
}

//设置LightBox弹框
function setLightBox(){
	//双击任务 弹框内容
    gantt.form_blocks["taskDesc"] = {
    	    render:function(sns) {
    	        return "<div class='dhx_cal_ltext' style='height:60px;'>"+
    	               "&nbsp;&nbsp;&nbsp;&nbsp;任务名称&nbsp;&nbsp;<input type='text'></br>"+
    	               "</div>";
    	    },
    	    //当进入到弹框 调用set_value 设置各字段的值
    	    set_value:function(node, value, task,section) {
    	        var taskId=task.id;//获取到任务的id 然后根据任务的id 来获取任务的基本信息
    	    },
    	    //当关闭 或者 保存的时候 会调用 get_value
    	    get_value:function(node, task,section) {
    	    },
    	    focus:function(node) {
    	        var a = node.childNodes[1];
    	        a.select();
    	        a.focus();
    	    }
    };
	
    gantt.locale.labels.section_taskName = "任务名称";
    gantt.locale.labels.section_taskDesc = "任务简介";
    gantt.locale.labels.section_taskCode = "任务内码";
    gantt.locale.labels.section_taskTime = "任务起始时间";
    gantt.locale.labels.section_deadline = "任务最终期限";
    gantt.config.lightbox.sections = [
          { name:"taskName", height:20,map_to:"text", type:"textarea", focus:true},
          { name:"taskDesc", height:60,map_to:"desc", type:"textarea"},
          { name:"taskTime", height:30, map_to:"auto", type:"duration"},
          {name: "deadline", map_to: {start_date:"deadline"},type: "duration_optional",single_date: true}
    ];	
    //任务保存
    gantt.attachEvent("onLightboxSave", function(id, item){
    	if(!item.text){
            dhtmlx.message({type:"error", text:"Enter task description!"});
            return false;
        }
    	//保存或者编辑任务
    	var str=handleRequest('DeadlineTaskServlet', 'update', JSON.stringify(item));
    	//保存成功 刷新甘特图
    	if(str=='success'){
    		//gantt.refreshData();
    		window.location.reload(true);
            return true;
    	}else{
    		 return false;
    	}
    });
    //任务关闭
    gantt.attachEvent("onLightboxCancel", function(id){
    	//alert('关闭');
    });
    
    /**
	 * 递归遍历任务 获取任务的所有子任务
	 * @param taskCode 任务内码
	 * @param taskCodes 子任务内码数组
	 * @param data 任务数组
	 */
    function getChildrenTaskCode(taskCode,taskCodes,data){
    	Ext.Array.each(data,function(task,index){
    		if(task.parent==taskCode){
    			taskCodes.push(task.id);
    			getChildrenTaskCode(task.id,taskCodes,data);
    		}
    	});
    }
    
    //删除任务
	gantt.attachEvent("onBeforeTaskDelete", function(id,item){
		var taskCodes=[];
		getChildrenTaskCode(id,taskCodes,data.data);
		taskCodes.push(id);
		var str=handleRequest('DeadlineTaskServlet', 'delete', taskCodes.join(','));
		if(str=='success'){
			 alert('删除成功');
			 return true;
		}else{
			alert('删除失败'+str);
			 return false;
		}
	});
    //当点击 + 号 创建新的任务 触发onTaskCreated事件
	gantt.attachEvent("onTaskCreated", function(task){
		 task.deadline=new Date();
		 return true;
	});
    
    //在任务加载的时候 将deadline转化为date
	gantt.attachEvent("onTaskLoading", function(task){
		if(task.deadline){
			task.deadline =Ext.Date.parse(task.deadline,'Y-m-d');
		}
		return true;
	});
}

//设置gantt时间线变动
function setGanttTimeLine(){
	 //任务事件发生改变
	gantt.attachEvent("onBeforeTaskChanged", function(id, mode, old_task){
		//如果是移动滚动条 则放弃操作
		if(mode == gantt.config.drag_mode.progress){
	        return false;
	    }
	    var task = gantt.getTask(id);
	    var str=handleRequest('DeadlineTaskServlet', 'changeDate', JSON.stringify(task));
	    if(str=='success'){
	    	return true;
	    }else{
	    	return false;
	    }
	});
}

//设置 任务的deadline 任务最大准许时间 任务超出时间
function setDeadline(){
	gantt.addTaskLayer(function draw_deadline(task) {
	    if (task.deadline) {
	        var el = document.createElement('div');
	        el.className = 'deadline';
	        //将字符串转化为Date日期
	        var sizes = gantt.getTaskPosition(task, task.deadline);
	        el.style.left = sizes.left + 'px';
	        el.style.top = sizes.top + 'px';
	        
	        el.setAttribute('title', gantt.templates.task_date(task.deadline));
	        return el;
	    }
	    return false;
	});
	gantt.templates.task_class = function (start, end, task) {
		if (task.deadline && end.valueOf() > task.deadline.valueOf()) {
			return 'overdue';
		}
	};
	gantt.templates.rightside_text = function (start, end, task) {
		if (task.deadline) {
			if (end.valueOf() > task.deadline.valueOf()) {
				var overdue = Math.ceil(Math.abs((end.getTime() - task.deadline.getTime()) / (24 * 60 * 60 * 1000)));
				var text = "<b>Overdue: " + overdue + " days</b>";
				return text;
			}
		}
	};
}

/**
 * 获取gantt任务的最小时间
 */
function getMinDate(tasks){
	var minDate=tasks[0].start_date;
	for(var i=1;i<tasks.length;i++){
		if(tasks[i].start_date.getTime()<minDate.getTime()){
			minDate=tasks[i].start_date;
		}
	}
	return minDate;
}
/**
 * 获取gantt任务的最大时间
 */
function getMaxDate(tasks){
	var maxDate=tasks[0].end_date;
	for(var i=1;i<tasks.length;i++){
		if(tasks[i].end_date.getTime()>maxDate.getTime()){
			maxDate=tasks[i].end_date;
		}
	}
	return maxDate;
}
/**
 * 刷新时间刻度
 */
function refreshProgress(){
	var i=0;
	var tasks = gantt.getTaskByTime();
	var startDate=getMinDate(tasks);
	var endDate=getMaxDate(tasks);
	var marker=gantt.getMarker(markerId);
	var progress=setInterval(function(){
		var now=startDate.getTime()+(++i)*1000*60*60*3;//每秒前进三小时
		for(var j=0;j<tasks.length;j++){
			//更新marker
			marker.start_date=new Date(now);
			gantt.updateMarker(marker);
			
			var task=tasks[j];
			var start_date=task.start_date.getTime();//任务开始时间
			var end_date=task.end_date.getTime();//任务结束时间
			//当第一次 当前时间大于 任务的重点时间的时候 
			if(now>end_date){
				task.progress=1;
				gantt.updateTask(task.id);
			}
			else if(start_date<now&&end_date>=now){
				task.progress=(now-start_date)/(end_date-start_date);
				if(task.progress>1){
					task.progress=1;
				}
				gantt.templates.progress_text=function(start, end, task){
					return "<div style='text-align:left;position:absolute'>"+Math.round(task.progress*100)+ "% </div>";
				}
				gantt.updateTask(task.id);
			}
		}
		//如果时间大于最大刻度的时间 则结束定时器
		if(now>=endDate.getTime()){
			clearInterval(progress);
			//refreshProgress();
		}
		
	}, 1000);

}

//显示任务提示框
function showTooltip(){
	gantt.config.tooltip_hide_timeout = 500;
	gantt.templates.tooltip_text = function(start,end,task){
		start=Ext.Date.format(start,'Y-m-d');
		end=Ext.Date.format(end,'Y-m-d');
		var deadline=Ext.Date.format(task.deadline,'Y-m-d');
	    return "<b>任务名称:</b> "+task.text+"<br/><b>持续周期:</b> " + task.duration+"</br><b>开始日期:</b>"+start+"</br><b>结束日期:</b>"+end+"</br><b>最终期限:</b>"+deadline+"</br><b>任务描述:</b>"+task.desc;
	};
}

//初始化gantt图
function initGantt(){
	setGanttConfig();
	setLightBox();
	setGanttTimeLine();
	setDeadline();
	
	showTooltip();
	
	gantt.init("gantt_here");
	gantt.parse(getGanttTasks()); 
	
	refreshProgress();
}
initGantt();  
</script>
</body>
</html>