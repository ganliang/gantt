<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="gantt/dhtmlxgantt.css" type="text/css"
	media="screen" title="no title" charset="utf-8">
<script type="text/javascript" src="gantt/dhtmlxgantt.js"
	charset="utf-8"></script>
<script type="text/javascript" src="gantt/testdata.js"></script>
<style type="text/css">
html, body {
	height: 100%;
	padding: 0px;
	margin: 0px;
	overflow: hidden;
}
</style>
</head>
<body>
	<div id="gantt_here" style="width: 100%; height: 100%"></div>
	<script type="text/javascript">
		gantt.config.scale_unit = "month";
		gantt.config.step = 1;
		gantt.config.date_scale = "%F, %Y";
		gantt.config.min_column_width = 50;

		gantt.config.scale_height = 90;

		gantt.config.subscales = [ {
			unit : "week",
			step : 1,
			date : '%d %M'
		}, {
			unit : "day",
			step : 1,
			date : "%D"
		} ];

		gantt.init("gantt_here");
		gantt.parse(demo_tasks);
	</script>

</body>
</html>