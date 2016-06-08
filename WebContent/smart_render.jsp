<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>gantt SmartRender性能测试</title>
<link rel="stylesheet" href="gantt/dhtmlxgantt.css" type="text/css" media="screen" title="no title" charset="utf-8">

<script type="text/javascript" src="gantt/dhtmlxgantt.js" charset="utf-8"></script>
<script type="text/javascript" src="gantt/locale_cn.js" charset="UTF-8"></script>
<script type="text/javascript" src="gantt/testdata.js" charset="UTF-8"></script>

<script type="text/javascript" src="gantt/dhtmlxgantt_smart_rendering.js" charset="UTF-8"></script>

<script type="text/javascript" src="js/ext-all.js" charset="UTF-8"></script>
<script type="text/javascript" src="js/ext-lang-zh_CN.js" charset="UTF-8"></script>
<link rel="stylesheet" type="text/css" href="resources/css/ext-all.css"/>

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
var data;
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


gantt.config.scale_unit = "day";
gantt.config.step = 1;
gantt.config.date_scale = "%Y-%m-%d";
gantt.config.min_column_width = 20;

gantt.config.scale_height = 75;

gantt.config.subscales = [
	{unit:"hour", step:1, date : "%H时"},
	{unit:"minute", step:1, date : "%i"}
];
gantt.config.duration_unit = "minute";
gantt.config.duration_step = 1;

gantt.config.show_task_cells = false;
gantt.config.static_background = true;
gantt.config.smart_rendering = true;
gantt.config.show_progress=true;

var start = Date.now();

gantt.init("gantt_here");
//gantt.parse(getGanttTasks());
gantt.parse(demo_tasks);

var end = Date.now();
//alert("spend "+(end-start)/1000);
gantt.message({type:"error", text:"spend "+(end-start)/1000+" s",expire:60000});
</script>
</body>
</html>