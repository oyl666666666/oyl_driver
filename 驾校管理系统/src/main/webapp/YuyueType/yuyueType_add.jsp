<%@ page language="java" import="java.util.*"  contentType="text/html;charset=UTF-8"%>
<jsp:include page="../check_logstate.jsp"/>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/yuyueType.css" />
<div id="yuyueTypeAddDiv">
	<form id="yuyueTypeAddForm" enctype="multipart/form-data"  method="post">
		<div>
			<span class="label">教练车牌号:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="yuyueType_typeName" name="yuyueType.typeName" style="width:200px" />
			</span>
		</div>
<%--		<div>
			<span class="label">教练姓名:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="yuyueType_coach" name="yuyueType.coach" style="width:200px" />
			</span>
		</div>--%>
		<div>
			<span class="label">教练姓名:</span>
			<span class="inputControl">
<%--				yuyueType.coach这个坑了我半晚上--%>
				<input class="textbox" type="text" id="yuyueType_coach" name="yuyueType.coach" style="width: auto"/>

			</span>
		</div>
<%--		<div>
			<span class="label">预约学员:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="yuyueType_user_name" name="yuyueType.user_name" style="width:200px" />
			</span>
		</div>--%>
		<div>
			<span class="label">预约状态:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="yuyueType_state" name="yuyueType.state" style="width:200px" />
			</span>
		</div>
		<div class="operation">
			<a id="yuyueTypeAddButton" class="easyui-linkbutton">添加</a>
			<a id="yuyueTypeClearButton" class="easyui-linkbutton">重填</a>
		</div> 
	</form>
</div>
<script src="${pageContext.request.contextPath}/YuyueType/js/yuyueType_add.js"></script>
