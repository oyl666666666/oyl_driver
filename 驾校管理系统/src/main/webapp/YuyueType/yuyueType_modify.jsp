<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<jsp:include page="../check_logstate.jsp"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/yuyueType.css" />
<div id="yuyueType_editDiv">
	<form id="yuyueTypeEditForm" enctype="multipart/form-data"  method="post">
		<div>
			<span class="label">教练车id:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="yuyueType_typeId_edit" name="yuyueType.typeId" value="<%=request.getParameter("typeId") %>" style="width:200px" />
			</span>
		</div>

		<div>
			<span class="label">教练车牌号:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="yuyueType_typeName_edit" name="yuyueType.typeName" style="width:200px" />

			</span>

		</div>
		<div>
			<span class="label">教练姓名:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="yuyueType_coach_edit" name="yuyueType.coach" style="width:200px" />

			</span>

		</div>
		<div>
			<span class="label">预约学员:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="yuyueType_user_name_edit" name="yuyueType.user_name" style="width:200px" />

			</span>

		</div>
		<div>
			<span class="label">预约状态:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="yuyueType_state_edit" name="yuyueType.state" style="width:200px" />

			</span>

		</div>
		<div class="operation">
			<a id="yuyueTypeModifyButton" class="easyui-linkbutton">更新</a>
		</div>
	</form>
</div>
<script src="${pageContext.request.contextPath}/YuyueType/js/yuyueType_modify.js"></script>
