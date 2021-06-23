<%@ page language="java"  contentType="text/html;charset=UTF-8"%>
<jsp:include page="../check_logstate.jsp"/> 
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/yuyueType.css" />

<div id="yuyueType_manage"></div>
<div id="yuyueType_manage_tool" style="padding:5px;">
	<div style="margin-bottom:5px;">
		<a href="#" class="easyui-linkbutton" iconCls="icon-edit-new" plain="true" onclick="yuyueType_manage_tool.edit();">修改</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-reload" plain="true" onclick="yuyueType_manage_tool.chushihua();">初始化</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-delete-new" plain="true" onclick="yuyueType_manage_tool.remove();">删除</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-reload" plain="true"  onclick="yuyueType_manage_tool.reload();">刷新</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-redo" plain="true" onclick="yuyueType_manage_tool.redo();">取消选择</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-export" plain="true" onclick="yuyueType_manage_tool.exportExcel();">导出到excel</a>
	</div>
	<div style="padding:0 0 0 7px;color:#333;">
		<form id="yuyueTypeQueryForm" method="post">
		</form>	
	</div>
</div>

<div id="yuyueTypeEditDiv">
	<form id="yuyueTypeEditForm" enctype="multipart/form-data"  method="post">
		<div>
			<span class="label">教练车id:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="yuyueType_typeId_edit" name="yuyueType.typeId" style="width:200px" />
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
		<div>
			<span class="label">日期:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="yuyueType_yuyue_day_edit" name="yuyueType.yuyue_day" style="width:200px" />
			</span>
		</div>
		<div>
			<span class="label">时间段:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="yuyueType_yuyue_time_edit" name="yuyueType.yuyue_time" style="width:200px" />
			</span>
		</div>
	</form>
</div>
<script type="text/javascript" src="YuyueType/js/yuyueType_manage.js"></script>
