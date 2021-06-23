<%@ page language="java" import="java.util.*"  contentType="text/html;charset=UTF-8"%> 
<%@ page import="com.chengxusheji.po.YuyueType" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    YuyueType yuyueType = (YuyueType)request.getAttribute("yuyueType");

%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1 , user-scalable=no">
  <TITLE>修改教练车牌号信息</TITLE>
  <link href="<%=basePath %>plugins/bootstrap.css" rel="stylesheet">
  <link href="<%=basePath %>plugins/bootstrap-dashen.css" rel="stylesheet">
  <link href="<%=basePath %>plugins/font-awesome.css" rel="stylesheet">
  <link href="<%=basePath %>plugins/animate.css" rel="stylesheet"> 
</head>
<body style="margin-top:70px;"> 
<div class="container">
<jsp:include page="../header.jsp"></jsp:include>
	<div class="col-md-9 wow fadeInLeft">
	<ul class="breadcrumb">
  		<li><a href="<%=basePath %>index.jsp">首页</a></li>
  		<li class="active">教练车信息修改</li>
	</ul>
		<div class="row"> 
      	<form class="form-horizontal" name="yuyueTypeEditForm" id="yuyueTypeEditForm" enctype="multipart/form-data" method="post"  class="mar_t15">
		  <div class="form-group">
			 <label for="yuyueType_typeId_edit" class="col-md-3 text-right">教练车id:</label>
			 <div class="col-md-9"> 
			 	<input type="text" id="yuyueType_typeId_edit" name="yuyueType.typeId" class="form-control" placeholder="请输入教练车id" readOnly>
			 </div>
		  </div> 
		  <div class="form-group">
		  	 <label for="yuyueType_typeName_edit" class="col-md-3 text-right">教练车牌号:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="yuyueType_typeName_edit" name="yuyueType.typeName" class="form-control" placeholder="请输入教练车牌号">
			 </div>
		  </div>
			<div class="form-group">
				<label for="yuyueType_coach_edit" class="col-md-3 text-right">教练姓名:</label>
				<div class="col-md-9">
					<input type="text" id="yuyueType_coach_edit" name="yuyueType.coach" class="form-control" placeholder="请输入教练姓名">
				</div>
			</div>
			<div class="form-group">
				<label for="yuyueType_state_edit" class="col-md-3 text-right">预约状态:</label>
				<div class="col-md-9">
					<input type="text" id="yuyueType_state_edit" name="yuyueType.state" class="form-control" placeholder="请输入预约状态">
				</div>
			</div>
			  <div class="form-group">
			  	<span class="col-md-3"></span>
			  	<span onclick="ajaxYuyueTypeModify();" class="btn btn-primary bottom5 top5">修改</span>
			  </div>
		</form> 
	    <style>#yuyueTypeEditForm .form-group {margin-bottom:5px;}  </style>
      </div>
   </div>
</div>


<jsp:include page="../footer.jsp"></jsp:include>
<script src="<%=basePath %>plugins/jquery.min.js"></script>
<script src="<%=basePath %>plugins/bootstrap.js"></script>
<script src="<%=basePath %>plugins/wow.min.js"></script>
<script src="<%=basePath %>plugins/bootstrap-datetimepicker.min.js"></script>
<script src="<%=basePath %>plugins/locales/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript" src="<%=basePath %>js/jsdate.js"></script>
<script>
var basePath = "<%=basePath%>";
/*弹出修改缴费类型界面并初始化数据*/
function yuyueTypeEdit(typeId) {
	$.ajax({
		url :  basePath + "YuyueType/" + typeId + "/update",
		type : "get",
		dataType: "json",
		success : function (yuyueType, response, status) {
			if (yuyueType) {
				$("#yuyueType_typeId_edit").val(yuyueType.typeId);
				$("#yuyueType_typeName_edit").val(yuyueType.typeName);
				$("#yuyueType_coach_edit").val(yuyueType.coach);
				$("#yuyueType_user_name_edit").val(yuyueType.user_name);
				$("#yuyueType_state_edit").val(yuyueType.state);
			} else {
				alert("获取信息失败！");
			}
		}
	});
}

/*ajax方式提交缴费类型信息表单给服务器端修改*/
function ajaxYuyueTypeModify() {
	$.ajax({
		url :  basePath + "YuyueType/" + $("#yuyueType_typeId_edit").val() + "/update",
		type : "post",
		dataType: "json",
		data: new FormData($("#yuyueTypeEditForm")[0]),
		success : function (obj, response, status) {
            if(obj.success){
                alert("信息修改成功！");
                location.reload(true);
                location.href= basePath + "YuyueType/frontlist";
            }else{
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
    yuyueTypeEdit("<%=request.getParameter("typeId")%>");
 })
 </script> 
</body>
</html>

