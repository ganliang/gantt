<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="js/dhtmlxgantt.js" type="text/javascript" charset="utf-8"></script>
<link rel="stylesheet" type="text/css" href="resources/css/ext-all.css" />
<script type="text/javascript" src="js/ext-all.js" charset="UTF-8"></script>
<script type="text/javascript" src="js/ext-lang-zh_CN.js"
	charset="UTF-8"></script>
<style type="text/css">
</style>
<script type="text/javascript">
	Ext.onReady(function() {
		Ext.create('Ext.form.Panel', {
			title:'解析文件',
			height : 100,
			frame : true,
			items : [ {
				xtype : 'filefield',
				fieldLabel : '上传',
				buttonText : '选择',
				labelWidth : 100,
				name:'uploadFileName',
				labelAlign : 'right',
				allowBlank:false
			} ],
			buttons : [ '->', {
				text : '上传',
				handler : function() {
                   var form=this.up().up();
                   if(form.getForm().isValid()){
                	   form.getForm().submit({
                		   method:'POST',
                		   url:'poiServlet',
                		   success:function(form,action){
                			   if(action.result.msg!=''){
                				   Ext.MessageBox.alert('提示信息',action.result.msg);
                			   }else{
                				   Ext.MessageBox.alert('提示信息','解析成功');   
                			   }
                		   },
						   failure:function(form,action){
							   Ext.MessageBox.alert('提示信息','解析失败');    			   
						   }
                	   });
                   }
				}
			}, '->' ],
			renderTo : 'form'
		});
	});
</script>
</head>
<body>
	<div style="width: 40%; height: 50px;float: left;"></div>
	<div id="form"
		style="width: 20%; height: auto;margin-top: 100px; float: left;"></div>
</body>
</html>