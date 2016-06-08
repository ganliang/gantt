<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>gantt性能测试</title>
<link rel="stylesheet" href="css/dhtmlxgantt.css" type="text/css" media="screen" title="no title" charset="utf-8">

<script type="text/javascript" src="js/dhtmlxgantt.js" charset="utf-8"></script>
<script type="text/javascript" src="js/locale_cn.js" charset="UTF-8"></script>
<script type="text/javascript" src="js/testdata.js" charset="UTF-8"></script>

<script type="text/javascript" src="js/dhtmlxgantt_smart_rendering.js" charset="UTF-8"></script>

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
<div id="gantt_here" style='width:100%; height:90%;'></div>
<script type="text/javascript">

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
gantt.config.duration_step = 15;

/**
 * disable the rendering of single cells and leave just rendering of rows
 */
gantt.config.show_task_cells = false;

/**
 * To set the background image for the timeline area instead of rendering the actual lines
 */
gantt.config.static_background = true;

/**
 * dynamic loading (当子任务非常多的时候 可以使用分支加载)
 */
//gantt.config.branch_loading = true;

/**
 * increase the scale's step ;decrease the range of displayable dates(start_date、start_date)
 */
 
/**
 *  remove progress bars from the tasks
 */
gantt.config.show_progress = false;
 
gantt.init("gantt_here");
gantt.parse(demo_tasks);

</script>
</body>
</html>