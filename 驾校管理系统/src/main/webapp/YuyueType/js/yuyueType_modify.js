$(function () {
	$.ajax({
		url : "YuyueType/" + $("#yuyueType_typeId_edit").val() + "/update",
		type : "get",
		data : {
			//typeId : $("#yuyueType_typeId_edit").val(),
		},
		beforeSend : function () {
			$.messager.progress({
				text : "正在获取中...",
			});
		},
		success : function (yuyueType, response, status) {
			$.messager.progress("close");
			if (yuyueType) {
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
				$("#yuyueType_user_name_edit").validatebox({
					required : true,
					missingMessage : "请输入预约学员!!",
				});
				$("#yuyueType_state_edit").val(yuyueType.state);
				$("#yuyueType_state_edit").validatebox({
					required : true,
					missingMessage : "请输入预约状态",
				});
			} else {
				$.messager.alert("获取失败！", "未知错误导致失败，请重试！", "warning");
				$(".messager-window").css("z-index",10000);
			}
		}
	});

	$("#yuyueTypeModifyButton").click(function(){
		if ($("#yuyueTypeEditForm").form("validate")) {
			$("#yuyueTypeEditForm").form({
			    url:"YuyueType/" +  $("#yuyueType_typeId_edit").val() + "/update",
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
                	var obj = jQuery.parseJSON(data);
                    if(obj.success){
                        $.messager.alert("消息","信息修改成功！");
                        $(".messager-window").css("z-index",10000);
                        //location.href="frontlist";
                    }else{
                        $.messager.alert("消息",obj.message);
                        $(".messager-window").css("z-index",10000);
                    } 
			    }
			});
			//提交表单
			$("#yuyueTypeEditForm").submit();
		} else {
			$.messager.alert("错误提示","你输入的信息还有错误！","warning");
			$(".messager-window").css("z-index",10000);
		}
	});
});
