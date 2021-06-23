var yuyueType_manage_tool = null;
$(function () { 
	initYuyueTypeManageTool(); //建立YuyueType管理对象
	yuyueType_manage_tool.init(); //如果需要通过下拉框查询，首先初始化下拉框的值
	$("#yuyueType_manage").datagrid({
		url : 'YuyueType/list',
		fit : true,
		fitColumns : true,
		striped : true,
		rownumbers : true,
		border : false,
		pagination : true,
		pageSize : 5,
		pageList : [5, 10, 15, 20, 25],
		pageNumber : 1,
		sortName : "typeId",
		sortOrder : "desc",
		toolbar : "#yuyueType_manage_tool",
		columns : [[
			{
				field : "typeId",
				title : "车辆id",
				width : 70,
			},
			{
				field : "typeName",
				title : "车牌号",
				width : 140,
			},
			{
				field : "coach",
				title : "教练姓名",
				width : 140,
			},
			{
				field : "user_name",
				title : "预约学员",
				width : 140,
			},
			{
				field : "state",
				title : "预约状态",
				width : 140,
			},
			{
				field : "yuyue_day",
				title : "日期",
				width : 140,
			},
			{
				field : "yuyue_time",
				title : "时段",
				width : 140,
			},
		]],
	});

	$("#yuyueTypeEditDiv").dialog({
		title : "修改管理",
		top: "50px",
		width : 700,
		height : 515,
		modal : true,
		closed : true,
		iconCls : "icon-edit-new",
		buttons : [{
			text : "提交",
			iconCls : "icon-edit-new",
			handler : function () {
				if ($("#yuyueTypeEditForm").form("validate")) {
					//验证表单 
					if(!$("#yuyueTypeEditForm").form("validate")) {
						$.messager.alert("错误提示","你输入的信息还有错误！","warning");
					} else {
						$("#yuyueTypeEditForm").form({
						    url:"YuyueType/" + $("#yuyueType_typeId_edit").val() + "/update",
						    onSubmit: function(){
								if($("#yuyueTypeEditForm").form("validate"))  {
				                	$.messager.progress({
										text : "正在提交数据中...",
									});
				                	return true;
				                } else { 
				                    return false; 
				                }
						    },
						    success:function(data){
						    	$.messager.progress("close");
						    	console.log(data);
			                	var obj = jQuery.parseJSON(data);
			                    if(obj.success){
			                        $.messager.alert("消息","信息修改成功！");
			                        $("#yuyueTypeEditDiv").dialog("close");
			                        yuyueType_manage_tool.reload();
			                    }else{
			                        $.messager.alert("消息",obj.message);
			                    } 
						    }
						});
						//提交表单
						$("#yuyueTypeEditForm").submit();
					}
				}
			},
		},{
			text : "取消",
			iconCls : "icon-redo",
			handler : function () {
				$("#yuyueTypeEditDiv").dialog("close");
				$("#yuyueTypeEditForm").form("reset");
			},
		}],
	});
});

function initYuyueTypeManageTool() {
	yuyueType_manage_tool = {
		init: function() {
		},
		reload : function () {
			$("#yuyueType_manage").datagrid("reload");
		},
		redo : function () {
			$("#yuyueType_manage").datagrid("unselectAll");
		},
		search: function() {
			$("#yuyueType_manage").datagrid("load");
		},
		exportExcel: function() {
			$("#yuyueTypeQueryForm").form({
			    url:"YuyueType/OutToExcel",
			});
			//提交表单
			$("#yuyueTypeQueryForm").submit();
		},
		remove : function () {
			var rows = $("#yuyueType_manage").datagrid("getSelections");
			if (rows.length > 0) {
				$.messager.confirm("确定操作", "您正在要删除所选的记录吗？", function (flag) {
					if (flag) {
						var typeIds = [];
						for (var i = 0; i < rows.length; i ++) {
							typeIds.push(rows[i].typeId);
						}
						$.ajax({
							type : "POST",
							url : "YuyueType/deletes",
							data : {
								typeIds : typeIds.join(","),
							},
							beforeSend : function () {
								$("#yuyueType_manage").datagrid("loading");
							},
							success : function (data) {
								if (data.success) {
									$("#yuyueType_manage").datagrid("loaded");
									$("#yuyueType_manage").datagrid("load");
									$("#yuyueType_manage").datagrid("unselectAll");
									$.messager.show({
										title : "提示",
										msg : data.message
									});
								} else {
									$("#yuyueType_manage").datagrid("loaded");
									$("#yuyueType_manage").datagrid("load");
									$("#yuyueType_manage").datagrid("unselectAll");
									$.messager.alert("消息",data.message);
								}
							},
						});
					}
				});
			} else {
				$.messager.alert("提示", "请选择要删除的记录！", "info");
			}
		},

		chushihua : function () {
			var rows = $("#yuyueType_manage").datagrid("getSelections");
			if (rows.length > 0) {
				$.messager.confirm("确定操作", "您正在要初始化所选的预约信息吗？", function (flag) {
					if (flag) {
						var typeIds = [];
						for (var i = 0; i < rows.length; i ++) {
							typeIds.push(rows[i].typeId);
						}
						$.ajax({
							type : "POST",
							url : "YuyueType/chushihua",
							data : {
								typeIds : typeIds.join(","),
							},
							beforeSend : function () {
								$("#yuyueType_manage").datagrid("loading");
							},
							success : function (data) {
								if (data.success) {
									$("#yuyueType_manage").datagrid("loaded");
									$("#yuyueType_manage").datagrid("load");
									$("#yuyueType_manage").datagrid("unselectAll");
									$.messager.show({
										title : "提示",
										msg : data.message
									});
								} else {
									$("#yuyueType_manage").datagrid("loaded");
									$("#yuyueType_manage").datagrid("load");
									$("#yuyueType_manage").datagrid("unselectAll");
									$.messager.alert("消息",data.message);
								}
							},
						});
					}
				});
			} else {
				$.messager.alert("提示", "请选择要初始化的信息！", "info");
			}
		},



		edit : function () {
			var rows = $("#yuyueType_manage").datagrid("getSelections");
			if (rows.length > 1) {
				$.messager.alert("警告操作！", "编辑记录只能选定一条数据！", "warning");
			} else if (rows.length == 1) {
				$.ajax({
					url : "YuyueType/" + rows[0].typeId +  "/update",
					type : "get",
					data : {
						//typeId : rows[0].typeId,
					},
					beforeSend : function () {
						$.messager.progress({
							text : "正在获取中...",
						});
					},
					success : function (yuyueType, response, status) {
						$.messager.progress("close");
						if (yuyueType) {
							$("#yuyueTypeEditDiv").dialog("open");
							$("#yuyueType_typeId_edit").val(yuyueType.typeId);
							$("#yuyueType_typeId_edit").validatebox({
								required : true,
								missingMessage : "请输入教练车id",
								editable: false
							});
							$("#yuyueType_typeName_edit").val(yuyueType.typeName);
							$("#yuyueType_typeName_edit").validatebox({
								required : true,
								missingMessage : "请输入车牌号",
							});
							$("#yuyueType_coach_edit").val(yuyueType.coach);
							$("#yuyueType_coach_edit").validatebox({
								required : true,
								missingMessage : "请输入教练姓名",
							});
							$("#yuyueType_user_name_edit").val(yuyueType.user_name);
/*							$("#yuyueType_user_name_edit").validatebox({
								required : true,
								missingMessage : "请输入预约学员!",
							});*/
							$("#yuyueType_state_edit").val(yuyueType.state);
							$("#yuyueType_state_edit").validatebox({
								required : true,
								missingMessage : "请输入预约状态",
							});
						} else {
							$.messager.alert("获取失败！", "未知错误导致失败，请重试！", "warning");
						}
					}
				});
			} else if (rows.length == 0) {
				$.messager.alert("警告操作！", "编辑记录至少选定一条数据！", "warning");
			}
		},
	};
}
