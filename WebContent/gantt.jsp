<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>甘特图</title>

<link rel="stylesheet" href="css/dhtmlxgantt.css" type="text/css" media="screen" title="no title" charset="utf-8">
<link rel="stylesheet" type="text/css" href="resources/css/ext-all.css"/>

<script type="text/javascript" src="js/ext-all.js" charset="UTF-8"></script>
<script type="text/javascript" src="js/ext-lang-zh_CN.js" charset="UTF-8"></script>

<script type="text/javascript" src="js/dhtmlxgantt.js" charset="utf-8" ></script>
<script type="text/javascript" src="js/dhtmlxgantt_marker.js" charset="UTF-8"></script>
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
<script type="text/javascript">
</script>
</head>
<body>
<div id="gantt_here" style='width:100%; height:100%;'></div>
<script type="text/javascript">
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
	var tasks=handleRequest('TaskServlet','getTask',null);
	return Ext.JSON.decode(tasks);
}
/**
 * 复制任务
 */
function copyTask(item){
	var task={};
	for(var p in item)
	{
		task[p]=item[p];
	}
	task.start_date=Ext.Date.format(item.start_date,'Y-m-d H:i:s');
	task.end_date=Ext.Date.format(item.end_date,'Y-m-d H:i:s');
	task.deadline=Ext.Date.format(item.deadline,'Y-m-d H:i');
	return task;
}

//配置gantt
function setGanttConfig(){
	gantt.config.grid_width = 420;
	gantt.locale.labels.section_deadline = "Deadline";
	//配置gantt的行标签
    gantt.config.columns=[{name:'text',label:'Task_name',width:'*',tree:true},
                          {name:'start_date',label:'start_time',width:80,align:'center'},
                          {name: "deadline", label: "Deadline", width:80, align: "center" },
                          {name:'duration',label:'duration',width:40,align:'center'},
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
    var markerId = gantt.addMarker({
        start_date: new Date(),
        css: "today",
        text: "Now", 
        //title:date_to_str( new Date()) 
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
    gantt.config.lightbox.sections = [
          { name:"taskName", height:20,map_to:"text", type:"textarea", focus:true},
          { name:"taskCode", height:20,map_to:"innerCode", type:"textarea"},
          { name:"taskDesc", height:60,map_to:"desc", type:"textarea"},
          { name:"taskTime", height:52, map_to:"auto", type:"duration"}
    ];	
    //任务保存
    gantt.attachEvent("onLightboxSave", function(id, item){
    	//保存或者编辑任务
    	var str=handleRequest('TaskServlet', 'update', JSON.stringify(item));
    	//保存成功 刷新甘特图
    	if(str=='success'){
    		gantt.updateTask(id)
            return true;
    	}else{
    		 return false;
    	}
    });
    //任务关闭
    gantt.attachEvent("onLightboxCancel", function(id){
    	//alert('关闭');
    });
    //删除任务
	gantt.attachEvent("onBeforeTaskDelete", function(id,item){
		var str=handleRequest('TaskServlet', 'delete', id);
		if(str=='success'){
			gantt.alert('删除成功');
			 return true;
		}else{
			gantt.alert('删除失败'+str);
			 return false;
		}
	});
    //当点击 + 号 创建新的任务 触发onTaskCreated事件
	gantt.attachEvent("onTaskCreated", function(task){
		 return true;
	});
    
	 //任务事件发生改变
	gantt.attachEvent("onBeforeTaskChanged", function(id, mode, old_task){
		//如果是移动滚动条 则放弃操作
		if(mode == gantt.config.drag_mode.progress){
	        return false;
	    }
	    var task = gantt.getTask(id);
	    
	    var myTask=copyTask(task);
	    
	    console.log(task);
	    var str=handleRequest('DeadlineTaskServlet', 'changeDate', JSON.stringify(myTask));
	    if(str=='success'){
	    	return true;
	    }else{
	    	return false;
	    }
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
	    var str=handleRequest('TaskServlet', 'changeDate', JSON.stringify(task));
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
	        var date=Ext.Date.parse(task.deadline,'Y-m-d');
	        var sizes = gantt.getTaskPosition(task, date);
	        el.style.left = sizes.left + 'px';
	        el.style.top = sizes.top + 'px';
	        
	        el.setAttribute('title', gantt.templates.task_date(date));
	        return el;
	    }
	    return false;
	});
	gantt.templates.task_class = function (start, end, task) {
		var date=Ext.Date.parse(task.deadline,'Y-m-d');
		if (task.deadline && end.valueOf() > date.valueOf()) {
			return 'overdue';
		}
	};
	gantt.templates.rightside_text = function (start, end, task) {
		if (task.deadline) {
			var date=Ext.Date.parse(task.deadline,'Y-m-d');
			if (end.valueOf() > date.valueOf()) {
				var overdue = Math.ceil(Math.abs((end.getTime() - date.getTime()) / (24 * 60 * 60 * 1000)));
				var text = "<b>Overdue: " + overdue + " days</b>";
				return text;
			}
		}
	};
}
//初始化gantt图
function initGantt(){
	setGanttConfig();
	setLightBox();
	setGanttTimeLine();
	//setDeadline();
	
	gantt.init("gantt_here");
	gantt.parse(getGanttTasks()); 
}
initGantt();    
</script>	
</body>
</html>