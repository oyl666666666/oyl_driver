<%@ page language="java" import="java.util.*"  contentType="text/html;charset=UTF-8"%> 
<%@ page import="com.chengxusheji.po.Exam" %>
<%@ page import="com.chengxusheji.po.Coach" %>
<%@ page import="com.chengxusheji.po.UserInfo" %>
<%@ page import="com.chengxusheji.po.YuyueType" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    List<YuyueType> yuyueTypeList = (List<YuyueType>)request.getAttribute("yuyueTypeList");
    //获取所有的userObj信息
    List<UserInfo> userInfoList = (List<UserInfo>)request.getAttribute("userInfoList");
    int currentPage =  (Integer)request.getAttribute("currentPage"); //当前页
    int totalPage =   (Integer)request.getAttribute("totalPage");  //一共多少页
    int recordNumber =   (Integer)request.getAttribute("recordNumber");  //一共多少记录
    String typeName = (String)request.getAttribute("typeName"); //考试名称查询关键字
    String coach = (String)request.getAttribute("coach"); //考试科目查询关键字
	String user_name =  (String)request.getAttribute("user_name");
	String yuyue_day =  (String)request.getAttribute("yuyue_day");
	String yuyue_time =  (String)request.getAttribute("yuyue_time");
    String state = (String)request.getAttribute("state"); //考试日期查询关键字
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1 , user-scalable=no">
<title>预约查询</title>
<link href="<%=basePath %>plugins/bootstrap.css" rel="stylesheet">
<link href="<%=basePath %>plugins/bootstrap-dashen.css" rel="stylesheet">
<link href="<%=basePath %>plugins/font-awesome.css" rel="stylesheet">
<link href="<%=basePath %>plugins/animate.css" rel="stylesheet">
<link href="<%=basePath %>plugins/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
</head>
<body style="margin-top:70px;">
<div class="container">
<jsp:include page="../header.jsp"></jsp:include>
	<div class="row"> 
		<div class="col-md-9 wow fadeInDown" data-wow-duration="0.5s">
			<div>
				<!-- Nav tabs -->
				<ul class="nav nav-tabs" role="tablist">
			    	<li><a href="<%=basePath %>index.jsp">首页</a></li>
			    	<li role="presentation" class="active"><a href="#yuyueTypeListPanel" aria-controls="yuyueTypeListPanel" role="tab" data-toggle="tab">预约列表</a></li>
<%--					<li role="presentation" ><a href="<%=basePath %>Exam/exam_frontAdd.jsp" style="display:none;">添加考试</a></li>--%>
				</ul>
			  	<!-- Tab panes -->
			  	<div class="tab-content">
				    <div role="tabpanel" class="tab-pane active" id="examListPanel">
				    		<div class="row">
				    			<div class="col-md-12 top5">
				    				<div class="table-responsive">
				    				<table class="table table-condensed table-hover">
				    					<tr class="success bold"><td>序号</td><td>预约id</td><td>教练车牌号</td><td>教练姓名</td><td>预约学员</td><td>预约状态</td><td>日期</td><td>时间段</td><td>操作</td></tr>
				    					<% 
				    						/*计算起始序号*/
				    	            		int startIndex = (currentPage -1) * 5;
				    	            		/*遍历记录*/
				    	            		for(int i=0;i<yuyueTypeList.size();i++) {
					    	            		int currentIndex = startIndex + i + 1; //当前记录的序号
					    	            		YuyueType yuyueType = yuyueTypeList.get(i); //获取到考试对象
 										%>
 										<tr>
 											<td><%=currentIndex %></td>
 											<td><%=yuyueType.getTypeId() %></td>
 											<td><%=yuyueType.getTypeName() %></td>
 											<td><%=yuyueType.getCoach() %></td>
 											<td><%=yuyueType.getUser_name()%></td>
 											<td><%=yuyueType.getState() %></td>
											<td><%=yuyueType.getYuyue_day() %></td>
											<td><%=yuyueType.getYuyue_time() %></td>
 											<td>
												<a href="<%=basePath  %>YuyueType/<%=yuyueType.getTypeId() %>/frontshow02"><i class="fa fa-info"></i>&nbsp;查看</a>&nbsp;
												<a href="<%=basePath  %>YuyueType/<%=yuyueType.getTypeId() %>/userFrontlist03?state0=<%=yuyueType.getState() %>&&yu=1"><i class="fa fa-info"></i>&nbsp;预约</a>&nbsp;
												<a href="<%=basePath  %>YuyueType/<%=yuyueType.getTypeId() %>/userFrontlist03?state0=<%=yuyueType.getState() %>&&yu=2"><i class="fa fa-info"></i>&nbsp;取消预约</a>&nbsp;
<%--												<a href="<%=basePath  %>YuyueType/<%=yuyueType.getTypeId() %>/frontshow02?user_name=<%=yuyueType.getUser_name() %>&&state=<%=yuyueType.getState() %>&&yu=1"><i class="fa fa-info"></i>&nbsp;预约</a>&nbsp;
												<a href="<%=basePath  %>YuyueType/<%=yuyueType.getTypeId() %>/frontshow02?user_name=<%=yuyueType.getUser_name() %>&&state=<%=yuyueType.getState() %>&&yu=2"><i class="fa fa-info"></i>&nbsp;取消预约</a>&nbsp;--%>
 												<a href="#" onclick="examEdit('<%=yuyueType.getTypeId() %>');" style="display:none;"><i class="fa fa-pencil fa-fw"></i>编辑</a>&nbsp;
 												<a href="#" onclick="examDelete('<%=yuyueType.getTypeId() %>');" style="display:none;"><i class="fa fa-trash-o fa-fw"></i>删除</a>
 											</td>
<%--											<td>
												<a href="<%=basePath  %>YuyueType/<%=exam.getTypeId() %>/frontshow02"><i class="fa fa-info"></i>&nbsp;预约</a>&nbsp;
												<a href="#" onclick="examEdit('<%=exam.getTypeId() %>');" style="display:none;"><i class="fa fa-pencil fa-fw"></i>编辑</a>&nbsp;
												<a href="#" onclick="examDelete('<%=exam.getTypeId() %>');" style="display:none;"><i class="fa fa-trash-o fa-fw"></i>删除</a>
											</td>--%>
										</tr>
 										<%}%>
				    				</table>
				    				</div>
				    			</div>
				    		</div>

				    		<div class="row">
					            <div class="col-md-12">
						            <nav class="pull-left">
						                <ul class="pagination">
						                    <li><a href="#" onclick="GoToPage(<%=currentPage-1 %>,<%=totalPage %>);" aria-label="Previous"><span aria-hidden="true">&laquo;</span></a></li>
						                     <%
						                    	int startPage = currentPage - 5;
						                    	int endPage = currentPage + 5;
						                    	if(startPage < 1) startPage=1;
						                    	if(endPage > totalPage) endPage = totalPage;
						                    	for(int i=startPage;i<=endPage;i++) {
						                    %>
						                    <li class="<%= currentPage==i?"active":"" %>"><a href="#"  onclick="GoToPage(<%=i %>,<%=totalPage %>);"><%=i %></a></li>
						                    <%  } %> 
						                    <li><a href="#" onclick="GoToPage(<%=currentPage+1 %>,<%=totalPage %>);"><span aria-hidden="true">&raquo;</span></a></li>
						                </ul>
						            </nav>
						            <div class="pull-right" style="line-height:75px;" >共有<%=recordNumber %>条记录，当前第 <%=currentPage %>/<%=totalPage %> 页</div>
					            </div>
				            </div> 
				    </div>
				</div>
			</div>
		</div>
	<div class="col-md-3 wow fadeInRight">
		<div class="page-header">
    		<h1>预约查询</h1>
		</div>
<%--		<form name="yuyueTypeQueryForm" id="yuyueTypeQueryForm" action="<%=basePath %>YuyueType/frontlist02" class="mar_t15" method="post">--%>
		<form name="yuyueTypeQueryForm" id="yuyueTypeQueryForm" action="<%=basePath %>YuyueType/frontlist02" class="mar_t15" method="post">
<%--			<div class="form-group">
				<label for="state">教练车牌号:</label>
				<input type="text" id="state" name="state" value="<%=state %>" class="form-control" placeholder="请输入车牌号">
			</div>--%>
<%--			<div class="form-group">
				<label for="examName">教练车牌号:</label>
				<input type="text" id="examName" name="examName" value="<%=typeName %>" class="form-control" placeholder="请输入车牌号">
			</div>
			<div class="form-group">
				<label for="kemu">教练工号:</label>
				<input type="text" id="kemu" name="kemu" value="<%=coach %>" class="form-control" placeholder="请输入教练工号">
			</div>--%>
            <div class="form-group">
            	<label for="yuyue_day">日期：</label>
<%--                <select id="yuyue_day" name="yuyue_day" class="form-control">
                	<option value="">不限制</option>
	 				<%
	 				for(YuyueType yuyueType:yuyueTypeList) {
	 					String selected = "";
 					if(yuyue_day!=null  && yuyue_day.equals(yuyueType.getState()))
 						selected = "selected";
	 				%>
 				 <option value="<%=yuyueType.getYuyue_day() %>" <%=selected %>><%=yuyueType.getYuyue_day() %></option>
	 				<%
	 				}
	 				%>
 			</select>--%>
				<select id="yuyue_day" name="yuyue_day" class="form-control">
					<option value="">不限制</option>
					<%
						int month=Calendar.getInstance().get(Calendar.MONTH)+1;
						int today=Calendar.getInstance().get(Calendar.DATE);
						for(int i=0;i<2;i++) {
							String selected = "";
//							selected = "selected";
							String yuyue_day01=month+"月"+today+"日";
					%>
					<option value="<%=yuyue_day01%>" <%=selected %>><%=yuyue_day01%></option>
					<%
						today=today+1;}
					%>
				</select>
            </div>
			<div class="form-group">
				<label for="yuyue_time">时间段：</label>
				<select id="yuyue_time" name="yuyue_time" class="form-control">
					<option value="">不限制</option>
					<option value="8点到10点">8点到10点</option>
					<option value="10点到12点">10点到12点</option>
					<option value="12点到14点">12点到14点</option>
					<option value="14点到16点">14点到16点</option>
					<option value="16点到18点">16点到18点</option>
				</select>
			</div>
			<div class="form-group">
				<label for="state">预约状态：</label>
				<select id="state" name="state" class="form-control">
					<option value="">不限制</option>
					<option value="可预约">可预约</option>
					<option value="不可预约">不可预约</option>
				</select>
			</div>
<%--			<div class="form-group">
				<label for="examDate">考试日期:</label>
				<input type="text" id="examDate" name="examDate" class="form-control"  placeholder="请选择考试日期" value="<%=examDate %>" onclick="SelectDate(this,'yyyy-MM-dd')" />
			</div>--%>
<%--			<div class="form-group">
				<label for="examPlace">预约状态:</label>
				<input type="text" id="examPlace" name="examPlace" value="<%=state %>" class="form-control" placeholder="请输入预约状态">
			</div>--%>
<%--			<div class="form-group">
				<label for="state">预约状态：</label>
				<select id="state" name="state" class="form-control">
						<option value="state01">不可预约</option>
						<option value="state02">可预约</option>
				</select>
			</div>---%>
<%--            <div class="form-group">
            	<label for="coachObj_coachNo">教练：</label>
                <select id="coachObj_coachNo" name="coachObj.coachNo" class="form-control">
                	<option value="">不限制</option>
	 				<%
	 				for(Coach coachTemp:coachList) {
	 					String selected = "";
 					if(coachObj!=null && coachObj.getCoachNo()!=null && coachObj.getCoachNo().equals(coachTemp.getCoachNo()))
 						selected = "selected";
	 				%>
 				 <option value="<%=coachTemp.getCoachNo() %>" <%=selected %>><%=coachTemp.getName() %></option>
	 				<%
	 				}
	 				%>
 			</select>
            </div>--%>
            <input type=hidden name=currentPage value="<%=currentPage %>" />
            <button type="submit" class="btn btn-primary">查询</button>
        </form>
	</div>

		</div>
	</div> 
<div id="examEditDialog" class="modal fade" tabindex="-1" role="dialog">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title"><i class="fa fa-edit"></i>&nbsp;考试信息编辑</h4>
      </div>
      <div class="modal-body" style="height:450px; overflow: scroll;">
      	<form class="form-horizontal" name="examEditForm" id="examEditForm" enctype="multipart/form-data" method="post"  class="mar_t15">
		  <div class="form-group">
			 <label for="exam_examId_edit" class="col-md-3 text-right">考试id:</label>
			 <div class="col-md-9"> 
			 	<input type="text" id="exam_examId_edit" name="exam.examId" class="form-control" placeholder="请输入考试id" readOnly>
			 </div>
		  </div> 
		  <div class="form-group">
		  	 <label for="exam_examName_edit" class="col-md-3 text-right">考试名称:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="exam_examName_edit" name="exam.examName" class="form-control" placeholder="请输入考试名称">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="exam_kemu_edit" class="col-md-3 text-right">考试科目:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="exam_kemu_edit" name="exam.kemu" class="form-control" placeholder="请输入考试科目">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="exam_userObj_user_name_edit" class="col-md-3 text-right">考试学员:</label>
		  	 <div class="col-md-9">
			    <select id="exam_userObj_user_name_edit" name="exam.userObj.user_name" class="form-control">
			    </select>
		  	 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="exam_examDate_edit" class="col-md-3 text-right">考试日期:</label>
		  	 <div class="col-md-9">
                <div class="input-group date exam_examDate_edit col-md-12" data-link-field="exam_examDate_edit"  data-link-format="yyyy-mm-dd">
                    <input class="form-control" id="exam_examDate_edit" name="exam.examDate" size="16" type="text" value="" placeholder="请选择考试日期" readonly>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                </div>
		  	 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="exam_startTime_edit" class="col-md-3 text-right">考试开始时间:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="exam_startTime_edit" name="exam.startTime" class="form-control" placeholder="请输入考试开始时间">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="exam_examPlace_edit" class="col-md-3 text-right">考试地点:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="exam_examPlace_edit" name="exam.examPlace" class="form-control" placeholder="请输入考试地点">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="exam_coachObj_coachNo_edit" class="col-md-3 text-right">教练:</label>
		  	 <div class="col-md-9">
			    <select id="exam_coachObj_coachNo_edit" name="exam.coachObj.coachNo" class="form-control">
			    </select>
		  	 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="exam_examMemo_edit" class="col-md-3 text-right">考试备注:</label>
		  	 <div class="col-md-9">
			    <textarea id="exam_examMemo_edit" name="exam.examMemo" rows="8" class="form-control" placeholder="请输入考试备注"></textarea>
			 </div>
		  </div>
		</form> 
	    <style>#examEditForm .form-group {margin-bottom:5px;}  </style>
      </div>
      <div class="modal-footer"> 
      	<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
      	<button type="button" class="btn btn-primary" onclick="ajaxExamModify();">提交</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<jsp:include page="../footer.jsp"></jsp:include> 
<script src="<%=basePath %>plugins/jquery.min.js"></script>
<script src="<%=basePath %>plugins/bootstrap.js"></script>
<script src="<%=basePath %>plugins/wow.min.js"></script>
<script src="<%=basePath %>plugins/bootstrap-datetimepicker.min.js"></script>
<script src="<%=basePath %>plugins/locales/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript" src="<%=basePath %>js/jsdate.js"></script>
<script>
var basePath = "<%=basePath%>";
/*跳转到查询结果的某页*/
function GoToPage(currentPage,totalPage) {
    if(currentPage==0) return;
    if(currentPage>totalPage) return;
/*    document.examQueryForm.currentPage.value = currentPage;
    document.examQueryForm.submit();*/
	document.yuyueTypeQueryForm.currentPage.value = currentPage;
	document.yuyueTypeQueryForm.submit();
}

/*可以直接跳转到某页*/
function changepage(totalPage)
{
    var pageValue=document.examQueryForm.pageValue.value;
    if(pageValue>totalPage) {
        alert('你输入的页码超出了总页数!');
        return ;
    }
    document.examQueryForm.currentPage.value = pageValue;
    documentexamQueryForm.submit();
}

/*弹出修改考试界面并初始化数据*/
function examEdit(examId) {
	$.ajax({
		url :  basePath + "Exam/" + examId + "/update",
		type : "get",
		dataType: "json",
		success : function (exam, response, status) {
			if (exam) {
				$("#exam_examId_edit").val(exam.examId);
				$("#exam_examName_edit").val(exam.examName);
				$("#exam_kemu_edit").val(exam.kemu);
				$.ajax({
					url: basePath + "UserInfo/listAll",
					type: "get",
					success: function(userInfos,response,status) { 
						$("#exam_userObj_user_name_edit").empty();
						var html="";
		        		$(userInfos).each(function(i,userInfo){
		        			html += "<option value='" + userInfo.user_name + "'>" + userInfo.name + "</option>";
		        		});
		        		$("#exam_userObj_user_name_edit").html(html);
		        		$("#exam_userObj_user_name_edit").val(exam.userObjPri);
					}
				});
				$("#exam_examDate_edit").val(exam.examDate);
				$("#exam_startTime_edit").val(exam.startTime);
				$("#exam_examPlace_edit").val(exam.examPlace);
				$.ajax({
					url: basePath + "Coach/listAll",
					type: "get",
					success: function(coachs,response,status) { 
						$("#exam_coachObj_coachNo_edit").empty();
						var html="";
		        		$(coachs).each(function(i,coach){
		        			html += "<option value='" + coach.coachNo + "'>" + coach.name + "</option>";
		        		});
		        		$("#exam_coachObj_coachNo_edit").html(html);
		        		$("#exam_coachObj_coachNo_edit").val(exam.coachObjPri);
					}
				});
				$("#exam_examMemo_edit").val(exam.examMemo);
				$('#examEditDialog').modal('show');
			} else {
				alert("获取信息失败！");
			}
		}
	});
}

/*删除考试信息*/
function examDelete(examId) {
	if(confirm("确认删除这个记录")) {
		$.ajax({
			type : "POST",
			url : basePath + "Exam/deletes",
			data : {
				examIds : examId,
			},
			success : function (obj) {
				if (obj.success) {
					alert("删除成功");
					$("#examQueryForm").submit();
					//location.href= basePath + "Exam/frontlist";
				}
				else 
					alert(obj.message);
			},
		});
	}
}

/*ajax方式提交考试信息表单给服务器端修改*/
function ajaxExamModify() {
	$.ajax({
		url :  basePath + "Exam/" + $("#exam_examId_edit").val() + "/update",
		type : "post",
		dataType: "json",
		data: new FormData($("#examEditForm")[0]),
		success : function (obj, response, status) {
            if(obj.success){
                alert("信息修改成功！");
                $("#examQueryForm").submit();
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

    /*考试日期组件*/
    $('.exam_examDate_edit').datetimepicker({
    	language:  'zh-CN',  //语言
    	format: 'yyyy-mm-dd',
    	minView: 2,
    	weekStart: 1,
    	todayBtn:  1,
    	autoclose: 1,
    	minuteStep: 1,
    	todayHighlight: 1,
    	startView: 2,
    	forceParse: 0
    });
})
</script>
</body>
</html>

