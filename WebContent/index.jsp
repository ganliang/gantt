<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="js/dhtmlxgantt.js" type="text/javascript" charset="utf-8"></script>
<link rel="stylesheet" type="text/css" href="resources/css/ext-all.css"/>
<script type="text/javascript" src="js/ext-all.js" charset="UTF-8"></script>
<script type="text/javascript" src="js/ext-lang-zh_CN.js" charset="UTF-8"></script>
<script type="text/javascript" src="js/TimePickerField.js" charset="UTF-8"></script>
<style type="text/css">

#div{
	height:800px; 
	width:99.9%;
	margin:0px;
	padding:0px;
}
</style>

<script type="text/javascript">
Ext.onReady(function(){
	Ext.create('Ext.data.Store', {
	    storeId:'simpsonsStore',
	    fields:['name', 'email', 'time'],
	    data:{'items':[
	        { 'name': 'Lisa',  "email":"lisa@simpsons.com",  "time":"2016-01-28 12:00:00"  },
	        { 'name': 'Bart',  "email":"bart@simpsons.com",  "time":"2016-01-28 12:00:00" },
	        { 'name': 'Homer', "email":"homer@simpsons.com",  "time":"2016-01-28 12:00:00"  },
	        { 'name': 'Marge', "email":"marge@simpsons.com", "time":"2016-01-28 12:00:00"  }
	    ]},
	    proxy: {
	        type: 'memory',
	        reader: {
	            type: 'json',
	            root: 'items'
	        }
	    }
	});

	var grid=Ext.create('Ext.grid.Panel', {
		flex:1,
		frame:true,
		layout:'anchor',
		width:'100%',
		height:'100%',
	    title: 'Simpsons',
	    store: Ext.data.StoreManager.lookup('simpsonsStore'),
	    selType: 'rowmodel',
	    plugins: {
	        ptype: 'cellediting',
	        clicksToEdit: 2
	    },
	    columns: [
	        { text: 'Name',  dataIndex: 'name',width:180, flex: 1 },
	        { text: 'Email', dataIndex: 'email',width:180, flex: 1 },
	        { text: 'Phone', dataIndex: 'time' ,width:180,editor:{
	        	xtype:'datetimefield',
	        	format:'Y-m-d H:i:s'
	        },renderer:function(value){
	        	if(value instanceof Date){//如果是Date类型
	        		value=Ext.Date.format(value, "Y-m-d H:i:s");
	        	}
	        	return value;
	        }}
	    ],
	    //renderTo: 'div'
	});
	
	Ext.create('Ext.container.Viewport',{
		layout:{
		   	type:'vbox',
	    	align:'stretch'
		},
		items:grid
	});
});

</script>
</head>
<body>
<div id="div"></div>
</body>
</html>