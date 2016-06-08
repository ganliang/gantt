var demo_tasks = {
	"data":[
		{"id":11, "text":"Project #1", "start_date":"01-04-2013", "duration":"11", "progress": 0.6, "open": true},
		{"id":1, "text":"Project #2", "start_date":"01-04-2013", "duration":"18", "progress": 0.4, "open": true},

		{"id":2, "text":"Task #1", "start_date":"02-04-2013", "duration":"8", "parent":"1", "progress":0.5, "open": true},
		{"id":3, "text":"Task #2", "start_date":"02-04-2013", "duration":"8", "parent":"1", "progress": 0.6, "open": true},
		{"id":4, "text":"Task #3", "start_date":"06-04-2013", "duration":"6", "parent":"1", "progress": 0.5, "open": true},
		{"id":5, "text":"Task #1.1", "start_date":"02-04-2013", "duration":"7", "parent":"2", "progress": 0.6, "open": true},
		{"id":6, "text":"Task #1.2", "start_date":"03-04-2013", "duration":"7", "parent":"2", "progress": 0.6, "open": true},
		{"id":7, "text":"Task #2.1", "start_date":"03-04-2013", "duration":"8", "parent":"3", "progress": 0.6, "open": true},
		{"id":8, "text":"Task #3.1", "start_date":"04-04-2013", "duration":"5", "parent":"4", "progress": 0.5, "open": true},
		{"id":9, "text":"Task #3.2", "start_date":"15-04-2013", "duration":"4", "parent":"4", "progress": 0.5, "open": true},
		{"id":10, "text":"Task #3.3", "start_date":"04-04-2013", "duration":"3", "parent":"4", "progress": 0.5, "open": true},
		
		{"id":12, "text":"Task #1", "start_date":"03-04-2013", "duration":"5", "parent":"11", "progress": 1, "open": true},
		{"id":13, "text":"Task #2", "start_date":"02-04-2013", "duration":"7", "parent":"11", "progress": 0.5, "open": true},
		{"id":14, "text":"Task #3", "start_date":"02-04-2013", "duration":"6", "parent":"11", "progress": 0.8, "open": true},
		{"id":15, "text":"Task #4", "start_date":"02-04-2013", "duration":"5", "parent":"11", "progress": 0.2, "open": true},
		{"id":16, "text":"Task #5", "start_date":"02-04-2013", "duration":"7", "parent":"11", "progress": 0, "open": true},

		{"id":17, "text":"Task #2.1", "start_date":"03-04-2013", "duration":"2", "parent":"13", "progress": 1, "open": true},
		{"id":18, "text":"Task #2.2", "start_date":"06-04-2013", "duration":"3", "parent":"13", "progress": 0.8, "open": true},
		{"id":19, "text":"Task #2.3", "start_date":"10-04-2013", "duration":"4", "parent":"13", "progress": 0.2, "open": true},
		{"id":20, "text":"Task #2.4", "start_date":"10-04-2013", "duration":"4", "parent":"13", "progress": 0, "open": true},
		{"id":21, "text":"Task #4.1", "start_date":"03-04-2013", "duration":"4", "parent":"15", "progress": 0.5, "open": true},
		{"id":22, "text":"Task #4.2", "start_date":"03-04-2013", "duration":"4", "parent":"15", "progress": 0.1, "open": true},
		{"id":23, "text":"Task #4.2", "start_date":"03-04-2013", "duration":"4", "parent":"15", "progress": 0.1, "open": true},
		{"id":24, "text":"Task #4.2", "start_date":"03-04-2013", "duration":"4", "parent":"15", "progress": 0.1, "open": true},
		{"id":25, "text":"Task #4.2", "start_date":"03-04-2013", "duration":"4", "parent":"15", "progress": 0.1, "open": true},
		{"id":26, "text":"Task #4.2", "start_date":"03-04-2013", "duration":"4", "parent":"15", "progress": 0.1, "open": true},
		{"id":27, "text":"Task #4.2", "start_date":"03-04-2013", "duration":"4", "parent":"15", "progress": 0.1, "open": true},
		{"id":28, "text":"Task #4.2", "start_date":"03-04-2013", "duration":"4", "parent":"15", "progress": 0.1, "open": true},
		{"id":29, "text":"Task #4.2", "start_date":"03-04-2013", "duration":"4", "parent":"15", "progress": 0.1, "open": true},
		{"id":30, "text":"Task #4.2", "start_date":"03-04-2013", "duration":"4", "parent":"15", "progress": 0.1, "open": true},
		{"id":31, "text":"Task #4.2", "start_date":"03-04-2013", "duration":"4", "parent":"15", "progress": 0.1, "open": true},
		{"id":32, "text":"Task #4.2", "start_date":"03-04-2013", "duration":"4", "parent":"15", "progress": 0.1, "open": true},
		{"id":33, "text":"Task #4.2", "start_date":"03-04-2013", "duration":"4", "parent":"15", "progress": 0.1, "open": true},
		{"id":34, "text":"Task #4.2", "start_date":"03-04-2013", "duration":"4", "parent":"15", "progress": 0.1, "open": true},
		{"id":35, "text":"Task #4.2", "start_date":"03-04-2013", "duration":"4", "parent":"15", "progress": 0.1, "open": true},
		{"id":36, "text":"Task #4.2", "start_date":"03-04-2013", "duration":"4", "parent":"15", "progress": 0.1, "open": true},
		{"id":37, "text":"Task #4.2", "start_date":"03-04-2013", "duration":"4", "parent":"15", "progress": 0.1, "open": true},
		{"id":38, "text":"Task #4.2", "start_date":"03-04-2013", "duration":"4", "parent":"15", "progress": 0.1, "open": true},
		{"id":39, "text":"Task #4.2", "start_date":"03-04-2013", "duration":"4", "parent":"15", "progress": 0.1, "open": true},
		{"id":40, "text":"Task #4.2", "start_date":"03-04-2013", "duration":"4", "parent":"15", "progress": 0.1, "open": true},
		{"id":41, "text":"Task #4.2", "start_date":"03-04-2013", "duration":"4", "parent":"15", "progress": 0.1, "open": true},
		{"id":42, "text":"Task #4.2", "start_date":"03-04-2013", "duration":"4", "parent":"15", "progress": 0.1, "open": true},
		{"id":43, "text":"Task #4.2", "start_date":"03-04-2013", "duration":"4", "parent":"15", "progress": 0.1, "open": true},
		{"id":44, "text":"Task #4.2", "start_date":"03-04-2013", "duration":"4", "parent":"15", "progress": 0.1, "open": true},
		{"id":45, "text":"Task #4.2", "start_date":"03-04-2013", "duration":"4", "parent":"15", "progress": 0.1, "open": true},
		{"id":46, "text":"Task #4.2", "start_date":"03-04-2013", "duration":"4", "parent":"15", "progress": 0.1, "open": true},
		{"id":47, "text":"Task #4.2", "start_date":"03-04-2013", "duration":"4", "parent":"15", "progress": 0.1, "open": true},
		{"id":48, "text":"Task #4.2", "start_date":"03-04-2013", "duration":"4", "parent":"15", "progress": 0.1, "open": true},
		{"id":49, "text":"Task #4.2", "start_date":"03-04-2013", "duration":"4", "parent":"15", "progress": 0.1, "open": true},
		{"id":50, "text":"Task #4.2", "start_date":"03-04-2013", "duration":"4", "parent":"15", "progress": 0.1, "open": true},
		{"id":51, "text":"Task #4.2", "start_date":"03-04-2013", "duration":"4", "parent":"15", "progress": 0.1, "open": true},
		{"id":52, "text":"Task #4.2", "start_date":"03-04-2013", "duration":"4", "parent":"15", "progress": 0.1, "open": true},
		{"id":53, "text":"Task #4.2", "start_date":"03-04-2013", "duration":"4", "parent":"15", "progress": 0.1, "open": true},
		{"id":54, "text":"Task #4.2", "start_date":"03-04-2013", "duration":"4", "parent":"15", "progress": 0.1, "open": true},
		{"id":55, "text":"Task #4.2", "start_date":"03-04-2013", "duration":"4", "parent":"15", "progress": 0.1, "open": true},
		{"id":56, "text":"Task #4.2", "start_date":"03-04-2013", "duration":"4", "parent":"15", "progress": 0.1, "open": true},
		{"id":57, "text":"Task #4.2", "start_date":"03-04-2013", "duration":"4", "parent":"15", "progress": 0.1, "open": true},
		{"id":58, "text":"Task #4.2", "start_date":"03-04-2013", "duration":"4", "parent":"15", "progress": 0.1, "open": true},
		{"id":59, "text":"Task #4.2", "start_date":"03-04-2013", "duration":"4", "parent":"15", "progress": 0.1, "open": true},
		{"id":60, "text":"Task #4.2", "start_date":"03-04-2013", "duration":"4", "parent":"15", "progress": 0.1, "open": true},
		{"id":61, "text":"Task #4.2", "start_date":"03-04-2013", "duration":"4", "parent":"15", "progress": 0.1, "open": true},
		{"id":62, "text":"Task #4.2", "start_date":"03-04-2013", "duration":"4", "parent":"15", "progress": 0.1, "open": true},
		{"id":63, "text":"Task #4.2", "start_date":"03-04-2013", "duration":"4", "parent":"15", "progress": 0.1, "open": true},
		{"id":64, "text":"Task #4.2", "start_date":"03-04-2013", "duration":"4", "parent":"15", "progress": 0.1, "open": true},
		{"id":65, "text":"Task #4.2", "start_date":"03-04-2013", "duration":"4", "parent":"15", "progress": 0.1, "open": true},
		{"id":66, "text":"Task #4.2", "start_date":"03-04-2013", "duration":"4", "parent":"15", "progress": 0.1, "open": true},
		{"id":67, "text":"Task #4.2", "start_date":"03-04-2013", "duration":"4", "parent":"15", "progress": 0.1, "open": true},
		{"id":68, "text":"Task #4.2", "start_date":"03-04-2013", "duration":"4", "parent":"15", "progress": 0.1, "open": true},
		{"id":69, "text":"Task #4.2", "start_date":"03-04-2013", "duration":"4", "parent":"15", "progress": 0.1, "open": true},
		{"id":70, "text":"Task #4.2", "start_date":"03-04-2013", "duration":"4", "parent":"15", "progress": 0.1, "open": true},
		{"id":71, "text":"Task #4.2", "start_date":"03-04-2013", "duration":"4", "parent":"15", "progress": 0.1, "open": true},
		{"id":72, "text":"Task #4.2", "start_date":"03-04-2013", "duration":"4", "parent":"15", "progress": 0.1, "open": true},
		{"id":73, "text":"Task #4.2", "start_date":"03-04-2013", "duration":"4", "parent":"15", "progress": 0.1, "open": true},
		{"id":74, "text":"Task #4.2", "start_date":"03-04-2013", "duration":"4", "parent":"15", "progress": 0.1, "open": true},
		{"id":200, "text":"Task #4.3", "start_date":"03-04-2013", "duration":"5", "parent":"15", "progress": 0, "open": true}
	],
	"links":[
		{"id":"1","source":"1","target":"2","type":"1"},
		{"id":"2","source":"2","target":"3","type":"0"},
		{"id":"3","source":"3","target":"4","type":"0"},
		{"id":"4","source":"2","target":"5","type":"2"},
		{"id":"5","source":"2","target":"6","type":"2"},
		{"id":"6","source":"3","target":"7","type":"2"},
		{"id":"7","source":"4","target":"8","type":"2"},
		{"id":"8","source":"4","target":"9","type":"2"},
		{"id":"9","source":"4","target":"10","type":"2"},
		{"id":"10","source":"11","target":"12","type":"1"},
		{"id":"11","source":"11","target":"13","type":"1"},
		{"id":"12","source":"11","target":"14","type":"1"},
		{"id":"13","source":"11","target":"15","type":"1"},
		{"id":"14","source":"11","target":"16","type":"1"},
		{"id":"15","source":"13","target":"17","type":"1"},
		{"id":"16","source":"17","target":"18","type":"0"},
		{"id":"17","source":"18","target":"19","type":"0"},
		{"id":"18","source":"19","target":"20","type":"0"},
		{"id":"19","source":"15","target":"21","type":"2"},
		{"id":"20","source":"15","target":"22","type":"2"},
		{"id":"21","source":"15","target":"23","type":"2"}
	]
};

var users_data = {
	"data":[
		{"id":1, "text":"Project #1", "start_date":"01-04-2013", "duration":"11", "progress": 0.6, "open": true, "users": ["John", "Mike", "Anna"], "priority": "2"},
		{"id":2, "text":"Task #1", "start_date":"03-04-2013", "duration":"5", "parent":"1", "progress": 1, "open": true, "users": ["John", "Mike"], "priority": "1"},
		{"id":3, "text":"Task #2", "start_date":"02-04-2013", "duration":"7", "parent":"1", "progress": 0.5, "open": true, "users": ["Anna"], "priority": "1"},
		{"id":4, "text":"Task #3", "start_date":"02-04-2013", "duration":"6", "parent":"1", "progress": 0.8, "open": true, "users": ["Mike", "Anna"], "priority": "2"},
		{"id":5, "text":"Task #4", "start_date":"02-04-2013", "duration":"5", "parent":"1", "progress": 0.2, "open": true, "users": ["John"], "priority": "3"},
		{"id":6, "text":"Task #5", "start_date":"02-04-2013", "duration":"7", "parent":"1", "progress": 0, "open": true, "users": ["John"], "priority": "2"},
		{"id":7, "text":"Task #2.1", "start_date":"03-04-2013", "duration":"2", "parent":"3", "progress": 1, "open": true, "users": ["Mike", "Anna"], "priority": "2"},
		{"id":8, "text":"Task #2.2", "start_date":"06-04-2013", "duration":"3", "parent":"3", "progress": 0.8, "open": true, "users": ["Anna"], "priority": "3"},
		{"id":9, "text":"Task #2.3", "start_date":"10-04-2013", "duration":"4", "parent":"3", "progress": 0.2, "open": true, "users": ["Mike", "Anna"], "priority": "1"},
		{"id":10, "text":"Task #2.4", "start_date":"10-04-2013", "duration":"4", "parent":"3", "progress": 0, "open": true, "users": ["John", "Mike"], "priority": "1"},
		{"id":11, "text":"Task #4.1", "start_date":"03-04-2013", "duration":"4", "parent":"5", "progress": 0.5, "open": true, "users": ["John", "Anna"], "priority": "3"},
		{"id":12, "text":"Task #4.2", "start_date":"03-04-2013", "duration":"4", "parent":"5", "progress": 0.1, "open": true, "users": ["John"], "priority": "3"},
		{"id":13, "text":"Task #4.3", "start_date":"03-04-2013", "duration":"5", "parent":"5", "progress": 0, "open": true, "users": ["Anna"], "priority": "3"}
	],
	"links":[
		{"id":"10","source":"11","target":"12","type":"1"},
		{"id":"11","source":"11","target":"13","type":"1"},
		{"id":"12","source":"11","target":"14","type":"1"},
		{"id":"13","source":"11","target":"15","type":"1"},
		{"id":"14","source":"11","target":"16","type":"1"},

		{"id":"15","source":"13","target":"17","type":"1"},
		{"id":"16","source":"17","target":"18","type":"0"},
		{"id":"17","source":"18","target":"19","type":"0"},
		{"id":"18","source":"19","target":"20","type":"0"},
		{"id":"19","source":"15","target":"21","type":"2"},
		{"id":"20","source":"15","target":"22","type":"2"},
		{"id":"21","source":"15","target":"23","type":"2"}
	]
};

var projects_with_milestones = {
	"data":[
		{"id":11, "text":"Project #1", type:gantt.config.types.project, "progress": 0.6, "open": true},

		{"id":12, "text":"Task #1", "start_date":"03-04-2013", "duration":"5", "parent":"11", "progress": 1, "open": true},
		{"id":13, "text":"Task #2", "start_date":"03-04-2013", type:gantt.config.types.project, "parent":"11", "progress": 0.5, "open": true},
		{"id":14, "text":"Task #3", "start_date":"02-04-2013", "duration":"6", "parent":"11", "progress": 0.8, "open": true},
		{"id":15, "text":"Task #4", type:gantt.config.types.project, "parent":"11", "progress": 0.2, "open": true},
		{"id":16, "text":"Final milestone", "start_date":"15-04-2013", type:gantt.config.types.milestone, "parent":"11", "progress": 0, "open": true},

		{"id":17, "text":"Task #2.1", "start_date":"03-04-2013", "duration":"2", "parent":"13", "progress": 1, "open": true},
		{"id":18, "text":"Task #2.2", "start_date":"06-04-2013", "duration":"3", "parent":"13", "progress": 0.8, "open": true},
		{"id":19, "text":"Task #2.3", "start_date":"10-04-2013", "duration":"4", "parent":"13", "progress": 0.2, "open": true},
		{"id":20, "text":"Task #2.4", "start_date":"10-04-2013", "duration":"4", "parent":"13", "progress": 0, "open": true},
		{"id":21, "text":"Task #4.1", "start_date":"03-04-2013", "duration":"4", "parent":"15", "progress": 0.5, "open": true},
		{"id":22, "text":"Task #4.2", "start_date":"03-04-2013", "duration":"4", "parent":"15", "progress": 0.1, "open": true},
		{"id":23, "text":"Mediate milestone", "start_date":"14-04-2013", type:gantt.config.types.milestone, "parent":"15", "progress": 0, "open": true}
	],
	"links":[
		{"id":"10","source":"11","target":"12","type":"1"},
		{"id":"11","source":"11","target":"13","type":"1"},
		{"id":"12","source":"11","target":"14","type":"1"},
		{"id":"13","source":"11","target":"15","type":"1"},
		{"id":"14","source":"23","target":"16","type":"0"},
		{"id":"15","source":"13","target":"17","type":"1"},
		{"id":"16","source":"17","target":"18","type":"0"},
		{"id":"17","source":"18","target":"19","type":"0"},
		{"id":"18","source":"19","target":"20","type":"0"},
		{"id":"19","source":"15","target":"21","type":"2"},
		{"id":"20","source":"15","target":"22","type":"2"},
		{"id":"21","source":"15","target":"23","type":"0"}
	]
};