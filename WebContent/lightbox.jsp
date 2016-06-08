<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>gantt LightBox</title>
<link rel="stylesheet" href="gantt/dhtmlxgantt.css" type="text/css" media="screen" title="no title" charset="utf-8">

<script type="text/javascript" src="gantt/dhtmlxgantt.js" charset="utf-8"></script>
<script type="text/javascript" src="gantt/locale_cn.js" charset="UTF-8"></script>
<script type="text/javascript" src="gantt/testdata.js" charset="UTF-8"></script>

<script type="text/javascript" src="gantt/dhtmlxgantt_smart_rendering.js" charset="UTF-8"></script>

<script type="text/javascript" src="js/ext-all.js" charset="UTF-8"></script>
<script type="text/javascript" src="js/ext-lang-zh_CN.js" charset="UTF-8"></script>
<link rel="stylesheet" type="text/css" href="resources/css/ext-all.css"/>

<script type="text/javascript" src="js/TimePickerField.js"></script>

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
function showLightBox(task){
	lightBoxWin.show();
	var lightboxForm=Ext.getCmp("lightboxForm");
	lightboxForm.getForm().setValues(task);
}

var states = Ext.create('Ext.data.Store', {
    fields: ['abbr', 'name'],
    data : [
        {"abbr":"AL", "name":"Alabama"},
        {"abbr":"AK", "name":"Alaska"},
        {"abbr":"AZ", "name":"Arizona"}
    ]
});

var lightBoxWin=Ext.create('Ext.window.Window',{
	title:'lightbox',
	width:500,
	height:400,
	modal:true,
	frame:true,
	closeAction:'hide',
	layout:{
		type:'vbox',
		align:'stretch'
	},
	items:[{
		xtype:"form",
		id:'lightboxForm',
		//margin:'10 10 20 10',
		flex:1,
		layout: {
	        type: 'table',
	        columns: 2
	    },
	    defaults:{
	    	labelWidth:52,
			labelAlign:'top',
	    },
		items:[{
			xtype:'textfield',
			fieldLabel:'任务名称',
			name:'text',
			width:480,
			colspan: 2
		},{
			xtype:'textareafield',
			fieldLabel:'任务描述',
			name:'desc',
			width:480,
			colspan: 2
		},{
			xtype:'datetimefield',
			format:'Y-m-d H:i',
			fieldLabel:'任务起始时间',
			name:'start_date',
			width:380,
			colspan: 1,
		},{
			xtype:'numberfield',
			margin:'23 10 10 0',
			width:60,
	        name: 'duration',
			colspan: 1,
			minValue: 1,
			value:1,
		},{
			xtype:'combobox',
			multiSelect:true,
			fieldLabel:'支撑任务',
			store: states,
		    queryMode: 'local',
		    displayField: 'name',
		    valueField: 'abbr',
		},{
			xtype:'hiddenfield',
			name:'id'
		}],
		buttons:[{
			text:'保存',
			handler:function(button){
				var values=this.up().up().getForm().getValues();
				console.log(values);
				var task=gantt.getTask(values.id);
				task["text"]=values.text;
				task["desc"]=values.desc;
				
				task["start_date"]=Ext.Date.parse(values.start_date,'Y-m-d H:i');
				task["end_date"]=Ext.Date.add(task.start_date,Ext.Date.DAY,values.duration);
				
				gantt.updateTask(values.id);//更新任务
				gantt.render();
				
				console.log(task);
				
				this.up().up().up().close();
			}
		},{
			text:'关闭',
			handler:function(button){
				this.up().up().getForm().reset();//将表单重置
				this.up().up().up().close();//关闭弹框
			}
		},'->',{
			text:'删除',
			handler:function(button){
				var values=this.up().up().getForm().getValues();
				var win=this.up().up().up();
				Ext.MessageBox.confirm('提示信息','你确定要删除任务【'+values.text+'】吗?',function(btn){
					if(btn=='yes'){
						gantt.deleteTask(values.id);
						win.close();//关闭弹框
					}
				});
			}
		}]
	}]
});

gantt.attachEvent("onTaskDblClick", function(id,e){
	var task=gantt.getTask(id);
	showLightBox(task);
});

gantt.init("gantt_here");
gantt.parse(users_data);
</script>
</body>
</html>