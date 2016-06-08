
<!DOCTYPE html>
<head>
<meta http-equiv="Content-type" content="text/html; charset=utf-8">
<title>Performance tweaks</title>
<script src="gantt/dhtmlxgantt.js" type="text/javascript" charset="utf-8"></script>
<script src="gantt/dhtmlxgantt_smart_rendering.js" type="text/javascript" charset="utf-8"></script>
<link rel="stylesheet" href="gantt/dhtmlxgantt.css" type="text/css" media="screen" title="no title" charset="utf-8">

<script type="text/javascript" src="../common/testdata.js"></script>
<style type="text/css">
html, body {
	height: 100%;
	padding: 0px;
	margin: 0px;
	overflow: hidden;
}
.controls {
	font-family: "Arial";
	width: 650px;
	margin: 0 auto;
}
.controls td {
	text-align: right;
	line-height: 20px;
	color: #535353;
	font-size: 14px;
}

.controls th {
	font-size: 14px;
}

.controls input {
	max-width: 40px;
}

.controls table {
	border-collapse: collapse;
}

.controls td, .controls th {
	border: 1px solid #ececec;
	padding: 0 10px;
}

.dhtmlx-intro div {
	background-color: #FEFFEA;
	border: 1px solid #A2A0A0;
}
</style>

<script>
	(function(i, s, o, g, r, a, m) {
		i['GoogleAnalyticsObject'] = r;
		i[r] = i[r] || function() {
			(i[r].q = i[r].q || []).push(arguments)
		}, i[r].l = 1 * new Date();
		a = s.createElement(o), m = s.getElementsByTagName(o)[0];
		a.async = 1;
		a.src = g;
		m.parentNode.insertBefore(a, m)
	})(window, document, 'script', '//www.google-analytics.com/analytics.js',
			'ga');

	ga('create', 'UA-11031269-1', 'auto');
	ga('send', 'pageview');
</script>
</head>
<body>
	<div class="controls">
		<table>
			<tr>
				<th>Background Mode</th>
				<th>Task element</th>
				<th>Scales</th>
				<th>Data</th>
			</tr>
			<tr>
				<td><label>Default <input type="radio" id="default"
						name="mode"></label></td>
				<td><label>Show progress <input type="checkbox"
						id="progress" checked></label></td>
				<td><label>Year scale <input type="checkbox" id="year"
						checked disabled="disabled"></label></td>
				<td><label>Tasks <input id="tasks" value="100"></label></td>
			</tr>
			<tr>
				<td><label>Simplified <input type="radio" id="no_cells"
						name="mode"></label></td>
				<td><label>Allow resize <input type="checkbox"
						id="resize" checked></label></td>
				<td><label>Month scale <input type="checkbox"
						id="month" checked></label></td>
				<td><button id="regenerate" onclick="resetData()">Regenerate
						test data</button></td>
			</tr>
			<tr>
				<td><label>Dynamic image <input type="radio"
						id="canvas" name="mode" checked></label></td>
				<td><label>Show links <input type="checkbox" id="links"
						checked></label></td>
				<td><label>Week scale <input type="checkbox" id="week"></label></td>
				<td></td>
			</tr>
			<tr>
				<td><label>Smart rendering <input type="checkbox"
						id="smart_render" name="mode" checked></label></td>
				<td></td>
				<td><label>Day scale <input type="checkbox" id="day"
						checked></label></td>
				<td><label>Years <input id="from" value="2012">&ndash;<input
						id="to" value="2013"></label></td>
			</tr>
			<tr>
				<td colspan="4" style="text-align: center;"><button
						id="refresh" onclick="noteTime(gantt.render)">Refresh</button></td>
			</tr>
		</table>
	</div>
	<div id="gantt_here" style='width: 100%; height: 600px;'></div>
	<script type="text/javascript">
		var text = [
				"Gantt chart with the data generated for client-side performance testing.",
				"Change settings and _$tpress 'Refresh'  to see how it works on different configurations and amounts of data.",
				"See browser console for test logs." ];
		for (var i = 0; i < text.length; i++) {
			gantt.message({
				text : text[i],
				expire : 30 * 1000,
				type : "intro"
			});
		}

		function byId(id) {
			return document.getElementById(id);
		}
		function updateMode() {
			gantt.config.static_background = false;
			gantt.config.show_task_cells = true;

			gantt.config.show_progress = byId("progress").checked;
			gantt.config.drag_resize = byId("resize").checked;
			gantt.config.show_links = byId("links").checked;
			gantt.config.smart_rendering = byId("smart_render").checked;

			gantt.config.subscales = [];

			if (byId("week").checked) {
				gantt.config.subscales.push({
					unit : "week",
					step : 1,
					template : weekScaleTemplate
				});
			}
			if (byId("day").checked) {
				gantt.config.subscales.push({
					unit : "day",
					step : 1,
					date : "%d %M"
				});
			}
			if (byId("month").checked) {
				gantt.config.subscales.push({
					unit : "month",
					step : 1,
					date : "%F, %Y"
				});
			}

			gantt.config.scale_height = 25 + 20 * gantt.config.subscales.length;

			setDateRange(byId("from").value, byId("to").value);

			if (byId("canvas").checked) {
				gantt.config.static_background = true;
			} else if (byId("no_cells").checked) {
				gantt.config.show_task_cells = false;
			}
		}
		function featureState(state) {
			return (state ? "Display" : "Hide");
		}
		function printStat(time) {
			var mode = "";
			var report = [];

			if (gantt.config.static_background) {
				mode = "Canvas background rendering";
			} else if (!gantt.config.show_task_cells) {
				mode = "Simplified background rendering";
			} else {
				mode = "Default rendering";
			}
			report.push("Rendered in " + (time) + " seconds");
			//report.push(mode);
			//report.push(featureState(gantt.config.show_progress) + " progress");
			//report.push(featureState(gantt.config.drag_resize)+ " drag handles");
			//report.push(featureState(gantt.config.show_links)+" link handles");

			var scales = [];
			for (var i = 0; i < gantt.config.subscales.length; i++) {
				scales.push(gantt.config.subscales[i].unit);
			}

			//report.push("Scales :" + scales.join(", "));
			//report.push(gantt._order.length + " tasks, " + (gantt._tasks.count)+ " columns in a time scale");
			gantt.message({
				text : report.join(""),
				expire : 10000
			});
			console.log("================");
			console.log(report.join("\n"));

		}
		function noteTime(method) {
			if (gantt.getTaskByTime().length != parseInt(byId("tasks").value,
					10)) {
				resetData();
			}

			gantt.message("Rendering...");
			updateMode();
			var start = Date.now();

			method.call(gantt);
			var end = Date.now();
			printStat((end - start) / 1000);

		}
		function resetData() {
			var start = Date.now();
			gantt.message("Generating random data");
			var count = byId("tasks").value, from = byId("from").value, to = byId("to").value;
			var data = generateData(count, from, to);
			gantt.clearAll();
			gantt.parse(data);
			var end = Date.now();
			gantt.message("Generated and parsed "
					+ gantt.getTaskByTime().length + "  tasks in "
					+ (end - start) / 1000 + " seconds");
		}
		function randomDate(start, end) {
			return new Date(start.getTime() + Math.random()
					* (end.getTime() - start.getTime()));
		}
		function setDateRange(from, to) {
			from = parseInt(from, 10);
			to = parseInt(to, 10);
			gantt.config.start_date = new Date(from, 0, 1);
			gantt.config.end_date = new Date(to, 0, 1);
		}
		function generateData(count, from, to) {
			var tasks = {
				data : [],
				links : []
			};
			setDateRange(from, to);

			count = parseInt(count, 10);
			var date = new Date(gantt.config.start_date.getFullYear(), 5, 1);
			var project_id = 1;
			tasks.data.push({
				id : project_id,
				text : "Project1",
				type : gantt.config.types.project,
				open : true
			});
			for (var i = 1; i < count; i++) {
				date = gantt.date.add(date, 1, "day");
				var task = {
					id : i + 1,
					start_date : date,
					text : "Task " + (i + 1),
					duration : 8,
					parent : project_id
				};

				if (gantt.date.add(date, 8, "day").valueOf() > gantt.config.end_date
						.valueOf()) {
					date = new Date(gantt.config.start_date);
					project_id = i + 1;
					delete task.parent;
					task.open = true;
				}
				tasks.data.push(task);

			}
			return tasks;
		}

		gantt.config.start_date = new Date(2012, 0, 1);
		gantt.config.end_date = new Date(2013, 0, 1);
		//
		gantt.config.min_column_width = 50;
		gantt.config.date_scale = "%Y";
		gantt.config.scale_unit = "year";
		var dateToStr = gantt.date.date_to_str("%d %M");
		var weekScaleTemplate = function(date) {
			var endDate = gantt.date.add(gantt.date.add(date, 1, "week"), -1,
					"day");
			return dateToStr(date) + " - " + dateToStr(endDate);
		};

		gantt.config.static_background = true; // is important only during initialization
		gantt.init("gantt_here");
		updateMode();
		resetData();
	</script>
</body>