<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="js/dhtmlxgantt.js" type="text/javascript" charset="utf-8"></script>
<link rel="stylesheet" href="css/dhtmlxgantt.css" type="text/css" media="screen" title="no title" charset="utf-8">
<link rel="stylesheet" type="text/css" href="resources/css/ext-all.css"/>
<script type="text/javascript" src="js/ext-all.js" charset="UTF-8"></script>
<script type="text/javascript" src="js/ext-lang-zh_CN.js" charset="UTF-8"></script>
<script type="text/javascript" src="js/locale_cn.js" charset="UTF-8"></script>
<script type="text/javascript" src="js/dhtmlxgantt_marker.js" charset="UTF-8"></script>
<style type="text/css">
	html, body{ 
		height:100%; 
		padding:0px; 
		margin:0px; 
		overflow: hidden;
	}
</style>
</head>
<body>
<div id="gantt_here" style='width:100%; height:100%;'></div>
<script type="text/javascript">
//获取到任务
function getTasks(){
	var tasks =  {
	        data:[
	            {id:1, text:"Project #2", start_date:"27-01-2016", duration:18,order:10,
	                progress:0.4, open: true},
	            {id:2, text:"Task #1", 	  start_date:"24-01-2016", duration:8, order:10,progress:1,
	                progress:0.6, parent:1},
	            {id:3, text:"Task #2",    start_date:"25-01-2016", duration:8, order:20,
	                progress:0.6, parent:1},
	            {id:4, text:"Task #3",    start_date:"28-01-2016", duration:8, order:20,
	                progress:0.6},
	            {id:5, text:"Task #4",    start_date:"29-01-2016", duration:8, order:20,
	                progress:0.6},
	            {id:6, text:"Task #5",    start_date:"02-02-2016", duration:8, order:20,
	                progress:0.6},
	            {id:7, text:"Task #6",    start_date:"25-01-2016", duration:8, order:20,
	                progress:0.6},
	        ],
	        links:[
	              { id:1, source:1, target:2, type:"1"},
	              { id:2, source:2, target:3, type:"0"},
	              { id:3, source:3, target:4, type:"0"},
	             ]
	    };
	return tasks;
}

//配置gantt
function setGanttConfig(){
	 //配置gantt的行标签
    gantt.config.columns=[{name:'text',label:'Task_name',width:'*',tree:true},
                          {name:'start_date',label:'start_time',align:'center'},
                          {name:'duration',label:'duration',align:'center'},
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
    	               "&nbsp;&nbsp;&nbsp;&nbsp;任务名称&nbsp;&nbsp;<input type='text'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;任务序号&nbsp;&nbsp;<input type='text'></br>"+
    	               "&nbsp;&nbsp;&nbsp;&nbsp;任务简介&nbsp;&nbsp;<input type='text'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;任务简介&nbsp;&nbsp;<input type='text'></br>"+
    	               "&nbsp;&nbsp;&nbsp;&nbsp;任务简介&nbsp;&nbsp;<input type='text'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;任务简介&nbsp;&nbsp;<input type='text'></br>"+
    	               "</div>";
    	    },
    	    //当进入到弹框 调用set_value 设置各字段的值
    	    set_value:function(node, value, task,section) {
    	        var taskId=task.id;//获取到任务的id 然后根据任务的id 来获取任务的基本信息
    	    },
    	    //当关闭 或者 保存的时候 会调用 get_value
    	    get_value:function(node, task,section) {
    	        task.users = node.childNodes[4].value;
    	        return node.childNodes[1].value;
    	    },
    	    focus:function(node) {
    	        var a = node.childNodes[1];
    	        a.select();
    	        a.focus();
    	    }
    };
    gantt.locale.labels.section_description = "任务基本信息";
    gantt.locale.labels.section_details = "任务简介";
    gantt.config.lightbox.sections = [
          { name:"description", height:250,  type:"taskDesc", focus:true},
          { name:"details", height:60, map_to:"text", type:"textarea", focus:true},
          {name:"time", height:52, map_to:"auto", type:"duration"}
    ];	
    //任务保存
    gantt.attachEvent("onLightboxSave", function(id, item){
    	alert('保存任务');
        return true;
    });
    //任务关闭
    gantt.attachEvent("onLightboxCancel", function(id){
    	alert('关闭');
    });
    //删除任务
	gantt.attachEvent("onBeforeTaskDelete", function(id,item){
		alert('删除');
	    return true;
	});
    //当点击 + 号 创建新的任务 触发onTaskCreated事件
	gantt.attachEvent("onTaskCreated", function(task){
		 alert('创建');
		 return true;
	});
}

//设置gantt时间线变动
function setGanttTimeLine(){
	 //任务事件发生改变
	gantt.attachEvent("onBeforeTaskChanged", function(id, mode, old_task){
	    var task = gantt.getTask(id);
	    if(mode == gantt.config.drag_mode.progress){
	        if(task.progress < old_task.progress){
	            dhtmlx.message(task.text + " progress can't be undone!");
	            return false;         
	        }
	    }
	    return true;
	});
}
function initGantt(){
	var task=getTasks();
	setGanttConfig();
	setLightBox();
	setGanttTimeLine();
	
	gantt.init("gantt_here");
	gantt.parse(getTasks()); 
}
initGantt();    
</script>	
<input type="text" width="60px">
</body>
</html>