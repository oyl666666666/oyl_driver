<%@ page language="java" import="java.util.*"  contentType="text/html;charset=UTF-8"%> 
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1 , user-scalable=no">
<title>车辆添加</title>
<link href="<%=basePath %>plugins/bootstrap.css" rel="stylesheet">
<link href="<%=basePath %>plugins/bootstrap-dashen.css" rel="stylesheet">
<link href="<%=basePath %>plugins/font-awesome.css" rel="stylesheet">
<link href="<%=basePath %>plugins/animate.css" rel="stylesheet">
<link href="<%=basePath %>plugins/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
</head>
<body style="margin-top:70px;">
<jsp:include page="../header.jsp"></jsp:include>
<div class="container">
	<div class="row">
		<div class="col-md-12 wow fadeInUp" data-wow-duration="0.5s">
			<div>
				<!-- Nav tabs -->
				<ul class="nav nav-tabs" role="tablist">
			    	<li role="presentation" ><a href="<%=basePath %>YuyueType/frontlist">教练车辆列表</a></li>
			    	<li role="presentation" class="active"><a href="#yuyueTypeAdd" aria-controls="yuyueTypeAdd" role="tab" data-toggle="tab">添加教练车</a></li>
				</ul>
				<!-- Tab panes -->
				<div class="tab-content">
				    <div role="tabpanel" class="tab-pane" id="yuyueTypeList">
				    </div>
				    <div role="tabpanel" class="tab-pane active" id="yuyueTypeAdd">
				      	<form class="form-horizontal" name="yuyueTypeAddForm" id="yuyueTypeAddForm" enctype="multipart/form-data" method="post"  class="mar_t15">
						  <div class="form-group">
						  	 <label for="yuyueType_typeName" class="col-md-2 text-right">教练车牌号:</label>
						  	 <div class="col-md-8">
							    <input type="text" id="yuyueType_typeName" name="yuyueType.typeName" class="form-control" placeholder="请输入教练车牌号">
							 </div>
						  </div>
							<div class="form-group">
								<label for="yuyueType_coach" class="col-md-2 text-right">教练姓名:</label>
								<div class="col-md-8">
									<input type="text" id="yuyueType_coach" name="yuyueType.coach" class="form-control" placeholder="请输入教练姓名">
								</div>
							</div>
							<div class="form-group">
								<label for="yuyueType_state" class="col-md-2 text-right">预约状态:</label>
								<div class="col-md-8">
									<input type="text" id="yuyueType_state" name="yuyueType.state" class="form-control" placeholder="请输入预约状态">
								</div>
							</div>
				          <div class="form-group">
				             <span class="col-md-2"></span>
				             <span onclick="ajaxYuyueTypeAdd();" class="btn btn-primary bottom5 top5">添加</span>
				          </div>
						</form> 
				        <style>#yuyueTypeAddForm .form-group {margin:10px;}  </style>
					</div>
				</div>
			</div>
		</div>
	</div> 
</div>

<jsp:include page="../footer.jsp"></jsp:include> 
<script src="<%=basePath %>plugins/jquery.min.js"></script>
<script src="<%=basePath %>plugins/bootstrap.js"></script>
<script src="<%=basePath %>plugins/wow.min.js"></script>
<script src="<%=basePath %>plugins/bootstrapvalidator/js/bootstrapValidator.min.js"></script>
<script type="text/javascript" src="<%=basePath %>plugins/bootstrap-datetimepicker.min.js" charset="UTF-8"></script>
<script type="text/javascript" src="<%=basePath %>plugins/locales/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
<script>
var basePath = "<%=basePath%>";
	//提交添加缴费类型信息
	function ajaxYuyueTypeAdd() {
		//提交之前先验证表单
		$("#yuyueTypeAddForm").data('bootstrapValidator').validate();
		if(!$("#yuyueTypeAddForm").data('bootstrapValidator').isValid()){
			return;
		}
		jQuery.ajax({
			type : "post",
			url : basePath + "YuyueType/add",
			dataType : "json" , 
			data: new FormData($("#yuyueTypeAddForm")[0]),
			success : function(obj) {
				if(obj.success){ 
					alert("保存成功！");
					$("#yuyueTypeAddForm").find("input").val("");
					$("#yuyueTypeAddForm").find("textarea").val("");
				} else {
					alert(obj.message);
				}
			},
			processData: false, 
			contentType: false, 
		});
	} 
$(function(){
	/*小屏幕导航点击关闭菜单*/
    $('.navbar-collapse a').click(function(){
        $('.navbar-collapse').collapse('hide');
    });
    new WOW().init();
	//验证缴费类型添加表单字段
	$('#yuyueTypeAddForm').bootstrapValidator({
		feedbackIcons: {
			valid: 'glyphicon glyphicon-ok',
			invalid: 'glyphicon glyphicon-remove',
			validating: 'glyphicon glyphicon-refresh'
		},
		fields: {
			"yuyueType.typeName": {
				validators: {
					notEmpty: {
						message: "类型名称不能为空",
					}
				}
			},			"yuyueType.coach": {
				validators: {
					notEmpty: {
						message: "教练姓名不能为空",
					}
				}
			},	"yuyueType.user_name": {
				validators: {
					notEmpty: {
						message: "学员姓名",
					}
				}
			},	"yuyueType.state": {
				validators: {
					notEmpty: {
						message: "预约状态",
					}
				}
			},
		}
	}); 
})
</script>
</body>
</html>
