<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>gantt导出</title>
<link rel="stylesheet" href="css/dhtmlxgantt.css" type="text/css"
	media="screen" title="no title" charset="utf-8">
<style type="text/css">
html, body {
	height: 100%;
	padding: 0px;
	margin: 0px;
	overflow: hidden;
}
</style>

<script type="text/javascript" src="js/dhtmlxgantt.js" charset="utf-8"></script>
<script type="text/javascript" src="js/locale_cn.js" charset="UTF-8"></script>
<script type="text/javascript" src="js/testdata.js" charset="UTF-8"></script>
<script type="text/javascript" src="js/dhtmlxgantt_api.js"
	charset="UTF-8"></script>
</head>
<body onload="init()">
	<input value="Export to PDF" type="button"
		onclick='gantt.exportToPDF()' style='margin: 20px;'>
	<input value="Export to PNG" type="button"
		onclick='gantt.exportToPNG()' style='margin: 20px;'>
	<input value="Export :: Meadow" type="button"
		onclick='gantt.exportToPNG({ skin:"meadow" })' style='margin: 20px;'>
	<input value="Export :: Broadway" type="button"
		onclick='gantt.exportToPDF({ skin:"broadway" })' style='margin: 20px;'>
	<input value="Export :: Skyblue" type="button"
		onclick='gantt.exportToPDF({ skin:"skyblue" })' style='margin: 20px;'>
		
	<input value="Export :: ICal" type="button"
		onclick='gantt.exportToICal()' style='margin: 20px;'>
	<input value="Export :: Excel" type="button"
		onclick='gantt.exportToExcel()' style='margin: 20px;'>
		
	<input value="Export :: XML" type="button"
		onclick='gantt.exportToMSProject()' style='margin: 20px;'>
	<input value="Export :: JSON" type="button"
		onclick='gantt.exportToJSON()' style='margin: 20px;'>

	<div id="gantt_here" style='width: 100%; height: 100%;'></div>
	
	<script type="text/javascript">
		function init() {
			gantt.config.sort=true;
			gantt.init("gantt_here");
			gantt.parse(demo_tasks);
		}
	</script>
</body>
</html>