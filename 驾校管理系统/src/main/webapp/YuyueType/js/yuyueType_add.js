
$(function () {
	$("#yuyueType_typeName").validatebox({
		required : true, 
		missingMessage : '请输入车牌号',
	});
/*	$("#yuyueType_coach").validatebox({
		required : true,
		missingMessage : '请输入教练姓名',
	});*/
	$("#yuyueType_coach").combobox({
		url:'Coach/listAll',
		valueField: "name",
		textField: "name",
		panelHeight: "auto",
		editable: false, //不允许手动输入
		required : true,
		onLoadSuccess: function () { //数据加载完毕事件
			var data = $("#yuyueType_coach").combobox("getData");
			if (data.length > 0) {
				$("#yuyueType_coach").combobox("select", data[0].name);
			}
		}
	});
/*	$("#yuyueType_user_name").validatebox({
		required : false,
		missingMessage : '请输入学员姓名',
	});*/
	$("#yuyueType_state").validatebox({
		required : true,
		missingMessage : '请输入预约状态',
	});

	//单击添加按钮
	$("#yuyueTypeAddButton").click(function () {
		//验证表单 
		if(!$("#yuyueTypeAddForm").form("validate")) {
			$.messager.alert("错误提示","你输入的信息还有错误！","warning");
			$(".messager-window").css("z-index",10000);
		} else {
			$("#yuyueTypeAddForm").form({
			    url:"YuyueType/add",
			    onSubmit: function(){
					if($("#yuyueTypeAddForm").form("validate"))  {
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
                    //此处data={"Success":true}是字符串
                	var obj = jQuery.parseJSON(data); 
                    if(obj.success){ 
                        $.messager.alert("消息","保存成功！");
                        $(".messager-window").css("z-index",10000);
                        $("#yuyueTypeAddForm").form("clear");
                    }else{
                        $.messager.alert("消息",obj.message);
                        $(".messager-window").css("z-index",10000);
                    }
			    }
			});
			//提交表单
			$("#yuyueTypeAddForm").submit();
		}
	});

	//单击清空按钮
	$("#yuyueTypeClearButton").click(function () {
		$("#yuyueTypeAddForm").form("clear");
	});
});
