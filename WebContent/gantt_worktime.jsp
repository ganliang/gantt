<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>gantt工作时间</title>
<link rel="stylesheet" href="css/dhtmlxgantt.css" type="text/css"
	media="screen" title="no title" charset="utf-8">
<link rel="stylesheet" type="text/css" href="resources/css/ext-all.css" />

<script type="text/javascript" src="js/ext-all.js" charset="UTF-8"></script>
<script type="text/javascript" src="js/ext-lang-zh_CN.js"
	charset="UTF-8"></script>

<script type="text/javascript" src="js/dhtmlxgantt.js" charset="utf-8"></script>
<script type="text/javascript" src="js/dhtmlxgantt_marker.js"
	charset="UTF-8"></script>
<script type="text/javascript" src="js/locale_cn.js" charset="UTF-8"></script>
<script type="text/javascript" src="js/testdata.js"></script>
<style type="text/css">
html, body {
	height: 100%;
	padding: 0px;
	margin: 0px;
	overflow: hidden;
}

.gantt_task_cell.week_end {
	background-color: silver;
}

.gantt_task_row.gantt_selected .gantt_task_cell.week_end {
	background-color: #F8EC9C;
}
</style>
</head>
<body>
	<div id="gantt_here" style="width: 100%; height: 100%"></div>
	<script type="text/javascript">
		gantt.config.work_time = true;
		gantt.config.correct_work_time = true;

		gantt.config.scale_unit = "day";
		gantt.config.date_scale = "%D, %d";
		gantt.config.min_column_width = 60;
		gantt.config.duration_unit = "day";
		gantt.config.scale_height = 20 * 3;
		gantt.config.row_height = 30;

		var weekScaleTemplate = function(date) {
			var dateToStr = gantt.date.date_to_str("%d %M");
			var weekNum = gantt.date.date_to_str("(week %W)");
			var endDate = gantt.date.add(gantt.date.add(date, 1, "week"), -1,
					"day");
			return dateToStr(date) + " - " + dateToStr(endDate) + " "
					+ weekNum(date);
		};

		gantt.config.subscales = [ {
			unit : "month",
			step : 1,
			date : "%F, %Y"
		}, {
			unit : "week",
			step : 1,
			template : weekScaleTemplate
		} ];

		gantt.templates.task_cell_class = function(task, date) {
			if (!gantt.isWorkTime(date))
				return "week_end";
			return "";
		};

		gantt.init("gantt_here");
		gantt.parse(demo_tasks);
		
		//将gantt数据转化为json对象
		var json=gantt.serialize();
		console.log(json);
		
		//将gantt数据转化为xml
		var xml=gantt.serialize('xml');
		console.log(xml);
		
	</script>
</body>
</html>