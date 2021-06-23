package com.chengxusheji.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.chengxusheji.mapper.YuyueTypeMapper;
import com.chengxusheji.po.*;
import com.chengxusheji.service.UserInfoService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.chengxusheji.utils.ExportExcelUtil;
import com.chengxusheji.utils.UserException;
import com.chengxusheji.service.YuyueTypeService;

//YuyueType管理控制层
@Controller
@RequestMapping("/YuyueType")
public class YuyueTypeController extends BaseController {

    /*业务层对象*/
    @Resource YuyueTypeService yuyueTypeService;
	@Resource UserInfoService userInfoService;

	@InitBinder("yuyueType")
	public void initBinderYuyueType(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("yuyueType.");
	}
	@InitBinder("coachObj")
	public void initBindercoachObj(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("coachObj.");
	}
	/*跳转到添加YuyueType视图*/
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model,HttpServletRequest request) throws Exception {
		model.addAttribute(new YuyueType());
		return "YuyueType_add";
	}

	/*客户端ajax方式提交添加缴费类型信息*/
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void add(@Validated YuyueType yuyueType, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
		boolean success = false;
		if (br.hasErrors()) {
			message = "输入信息不符合要求！";
			writeJsonResponse(response, success, message);
			return ;
		}
		System.out.println(yuyueType);
		System.out.println("增加学员"+yuyueType.getUser_name()+"      id:"+yuyueType.getTypeId());
        yuyueType.setUser_name("");

		ArrayList<YuyueType> yuyueTypes = yuyueTypeService.queryYuyueType();
		int same=0;
		String typename="";
		String coach="";
		for (YuyueType yuyueType1:yuyueTypes) {
			System.out.println("教练姓名：  "+yuyueType1.getCoach()+"  教练车牌号： "+yuyueType1.getTypeName());
             if(yuyueType.getTypeName().equals(yuyueType1.getTypeName())||yuyueType.getCoach().equals(yuyueType1.getCoach()))
			 {
			 	typename=yuyueType1.getTypeName();
			    coach=yuyueType1.getCoach();
			 	same=1;
			 	break;
			 }
		}
		System.out.println("same:         "+same);
		//该车和教练都没有被占用
		if (same==0)
		{
			if ("可预约".equals(yuyueType.getState()))
			{
				yuyueTypeService.addYuyueType(yuyueType);
				System.out.println("教练车添加成功");
				message = "教练车添加成功!";
				success = true;
			}
			else
			{
				System.out.println("可预约要填");
				message = "预约状态只能填可预约!";
				success = false;
			}

		}
		//该车或教练可能被占用
		if (same==1)
		{
			System.out.println(yuyueType);
			System.out.println("教练车添加失败");
			message = coach+"教练已绑定过车牌号为："+typename+" 的教学车辆!";
			success = false;
		}
        writeJsonResponse(response, success, message);
	}
	/*ajax方式按照查询条件分页查询缴费类型信息*/
	@RequestMapping(value = { "/list" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void list(Integer page,Integer rows, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		yuyueTypeService.auto_update();


		if (page==null || page == 0) page = 1;
		if(rows != 0)yuyueTypeService.setRows(rows);
		List<YuyueType> yuyueTypeList = yuyueTypeService.queryYuyueType(page);
	    /*计算总的页数和总的记录数*/
	    yuyueTypeService.queryTotalPageAndRecordNumber();
	    /*获取到总的页码数目*/
	    int totalPage = yuyueTypeService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = yuyueTypeService.getRecordNumber();
        response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象
		JSONObject jsonObj=new JSONObject();
		jsonObj.accumulate("total", recordNumber);
		JSONArray jsonArray = new JSONArray();
		for(YuyueType yuyueType:yuyueTypeList) {
			JSONObject jsonYuyueType = yuyueType.getJsonObject();
			jsonArray.put(jsonYuyueType);
		}
		jsonObj.accumulate("rows", jsonArray);
		out.println(jsonObj.toString());
		out.flush();
		out.close();
	}


	/*ajax方式按照查询条件分页查询缴费类型信息*/
	@RequestMapping(value = { "/listAll" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void listAll(HttpServletResponse response) throws Exception {
		List<YuyueType> yuyueTypeList = yuyueTypeService.queryAllYuyueType();
        response.setContentType("text/json;charset=UTF-8"); 
		PrintWriter out = response.getWriter();
		JSONArray jsonArray = new JSONArray();
		for(YuyueType yuyueType:yuyueTypeList) {
			JSONObject jsonYuyueType = new JSONObject();
			jsonYuyueType.accumulate("typeId", yuyueType.getTypeId());
			jsonYuyueType.accumulate("typeName", yuyueType.getTypeName());
			jsonYuyueType.accumulate("coach", yuyueType.getCoach());
			jsonYuyueType.accumulate("user_name", yuyueType.getUser_name());
			jsonYuyueType.accumulate("state", yuyueType.getState());
			jsonArray.put(jsonYuyueType);
		}
		out.println(jsonArray.toString());
		out.flush();
		out.close();
	}

	/*前台按照查询条件分页查询缴费类型信息*/
	@RequestMapping(value = { "/frontlist" }, method = {RequestMethod.GET,RequestMethod.POST})
	public String frontlist(Integer currentPage, Model model, HttpServletRequest request) throws Exception  {
		if (currentPage==null || currentPage == 0) currentPage = 1;
		List<YuyueType> yuyueTypeList = yuyueTypeService.queryYuyueType(currentPage);
	    /*计算总的页数和总的记录数*/
	    yuyueTypeService.queryTotalPageAndRecordNumber();
	    /*获取到总的页码数目*/
	    int totalPage = yuyueTypeService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = yuyueTypeService.getRecordNumber();
	    request.setAttribute("yuyueTypeList",  yuyueTypeList);
	    request.setAttribute("totalPage", totalPage);
	    request.setAttribute("recordNumber", recordNumber);
	    request.setAttribute("currentPage", currentPage);
		return "YuyueType/yuyueType_frontquery_result";
	}
	@RequestMapping(value = { "/frontlist02" }, method = {RequestMethod.GET,RequestMethod.POST})
	public String frontlist02(String typeName,String coach,String yuyue_day,String yuyue_time,String user_name,@ModelAttribute("userObj") UserInfo userObj,String state,Integer currentPage,Model model, HttpServletRequest request) throws Exception  {
		if (currentPage==null || currentPage == 0) currentPage = 1;
		if (typeName == null) typeName = "";
		if (coach == null) coach = "";
		if (state == null) state = "";
		if (yuyue_day == null) yuyue_day = "";
		if (yuyue_time == null) yuyue_time = "";
		System.out.println("日期");
		System.out.println(yuyue_day);
		System.out.println("时间段");
		System.out.println(yuyue_time);
		System.out.println("预约状态");
		System.out.println(state);
		user_name=userObj.getUser_name();
		List<YuyueType> yuyueTypeList = yuyueTypeService.queryYuyueTypeList02(typeName, coach,user_name, state,yuyue_day, yuyue_time,currentPage);
		//计算总的页数和总的记录数
		yuyueTypeService.queryTotalPageAndRecordNumber02(typeName, coach, state,yuyue_day, user_name, state);
		//获取到总的页码数目
		int totalPage = yuyueTypeService.getTotalPage();
		//当前查询条件下总记录数
		int recordNumber = yuyueTypeService.getRecordNumber();
		request.setAttribute("yuyueTypeList",  yuyueTypeList);
		request.setAttribute("totalPage", totalPage);
		request.setAttribute("recordNumber", recordNumber);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("typeName", typeName);
		request.setAttribute("coach", coach);
		request.setAttribute("yuyue_day", yuyue_day);
		request.setAttribute("yuyue_time", yuyue_time);
		request.setAttribute("user_name", user_name);
		request.setAttribute("state", state);
		List<UserInfo> userInfoList = userInfoService.queryAllUserInfo();
		request.setAttribute("userInfoList", userInfoList);
		return "YuyueType/yuyueType_frontquery_result02";
	}
	/*前台按照查询条件分页查询考试信息*/
	@RequestMapping(value = { "/userFrontlist02" }, method = {RequestMethod.GET,RequestMethod.POST})
	public String userFrontlist(String typeName,String coach,String user_name,@ModelAttribute("userObj") UserInfo userObj,String state,String yuyue_day,String yuyue_time,Integer currentPage, Model model, HttpServletRequest request,HttpSession session) throws Exception  {
		if (currentPage==null || currentPage == 0) currentPage = 1;
		if (typeName == null) typeName = "";
		if (coach == null) coach = "";
		if (state == null) state = "";
		if (yuyue_day == null) yuyue_day = "";
		if (yuyue_time == null) yuyue_time = "";
		if (user_name == null) user_name = "";
		user_name=session.getAttribute("user_name").toString();
		System.out.println(user_name);
		List<YuyueType> yuyueTypeList = yuyueTypeService.queryYuyueTypeList02(typeName, coach,user_name, state,yuyue_day,yuyue_time, currentPage);
		/*计算总的页数和总的记录数*/
		yuyueTypeService.queryTotalPageAndRecordNumber();
		/*获取到总的页码数目*/
		int totalPage = yuyueTypeService.getTotalPage();
		/*当前查询条件下总记录数*/
		int recordNumber = yuyueTypeService.getRecordNumber();
		request.setAttribute("yuyueTypeList",  yuyueTypeList);
		request.setAttribute("totalPage", totalPage);
		request.setAttribute("recordNumber", recordNumber);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("typeName", typeName);
		request.setAttribute("coach", coach);
		request.setAttribute("state", state);
		request.setAttribute("yuyue_day", yuyue_day);
		request.setAttribute("yuyue_time", yuyue_time);
		request.setAttribute("user_name", user_name);
		List<UserInfo> userInfoList = userInfoService.queryAllUserInfo();
		request.setAttribute("userInfoList", userInfoList);
		return "YuyueType/yuyueType_frontquery_result02";
	}
	/*前台按照查询条件分页查询考试信息*/
	@RequestMapping(value = { "/{typeId}/userFrontlist03" }, method = {RequestMethod.GET,RequestMethod.POST})
	public String userFrontlist(@PathVariable Integer typeId,String typeName,String coach,String yuyue_day,String yuyue_time,String user_name,@ModelAttribute("userObj") UserInfo userObj,String state,Integer currentPage, Model model, HttpServletRequest request,HttpSession session) throws Exception  {
		if (currentPage==null || currentPage == 0) currentPage = 1;
		if (typeName == null) typeName = "";
		if (coach == null) coach = "";
		if (state == null) state = "";
		if (yuyue_day == null) yuyue_day = "";
		if (yuyue_time == null) yuyue_time = "";
		if (user_name == null) user_name = "";
		user_name=session.getAttribute("user_name").toString();
		//////////////////////////////////////////////////////////////////
		YuyueType yuyueType=new YuyueType();
		String state0 = request.getParameter("state0");
		String yu = request.getParameter("yu");
		System.out.println(yu);
		if ("1".equals(yu))
		{
			System.out.println("预约来的!");
			/*根据主键typeId获取YuyueType对象*/
		     yuyueType = yuyueTypeService.getYuyueType(typeId);
		}
		if ("2".equals(yu))
		{
			System.out.println("取消预约来的!");
			/*根据主键typeId获取YuyueType对象*/
			 yuyueType = yuyueTypeService.getYuyueType(typeId);
		}
		System.out.println("user_name:   "+user_name);
		System.out.println("typeId:  "+typeId);
		System.out.println("预约状态：  "+state);
		if("可预约".equals(state0)&&yu.equals("1"))
		{
			String u_name01=user_name;
			yuyueType.setState("不可预约");
			System.out.println("预约学员："+u_name01);
			yuyueType.setUser_name(u_name01);
			System.out.println(yuyueType);
			yuyueTypeService.updateYuyueType(yuyueType);
			yuyueType=yuyueTypeService.getYuyueType(typeId);
			System.out.println("预约后：  ");
			System.out.println(yuyueType);
		}
		if("不可预约".equals(state0)&&yuyueType.getUser_name().equals(user_name)&&yu.equals("2"))
		{
			String u_name02=user_name;
			yuyueType.setState("可预约");
			yuyueType.setUser_name("");
			yuyueTypeService.updateYuyueType(yuyueType);
			yuyueType=yuyueTypeService.getYuyueType(typeId);
			System.out.println("取消预约后：  ");
			System.out.println(yuyueType);
		}

		List<YuyueType> yuyueTypeList = yuyueTypeService.queryYuyueTypeList02(typeName, coach,user_name, state,yuyue_day,yuyue_time, currentPage);
		/*计算总的页数和总的记录数*/
		yuyueTypeService.queryTotalPageAndRecordNumber();
		/*获取到总的页码数目*/
		int totalPage = yuyueTypeService.getTotalPage();
		/*当前查询条件下总记录数*/
		int recordNumber = yuyueTypeService.getRecordNumber();
		request.setAttribute("yuyueTypeList",  yuyueTypeList);
		request.setAttribute("totalPage", totalPage);
		request.setAttribute("recordNumber", recordNumber);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("typeName", typeName);
		request.setAttribute("coach", coach);
		request.setAttribute("state", state);
		request.setAttribute("user_name", user_name);
		List<UserInfo> userInfoList = userInfoService.queryAllUserInfo();
		request.setAttribute("userInfoList", userInfoList);
		return "YuyueType/yuyueType_frontquery_result02";
	}

     /*前台查询YuyueType信息*/
	@RequestMapping(value="/{typeId}/frontshow",method=RequestMethod.GET)
	public String frontshow(@PathVariable Integer typeId,Model model,HttpServletRequest request) throws Exception {
		/*根据主键typeId获取YuyueType对象*/
        YuyueType yuyueType = yuyueTypeService.getYuyueType(typeId);

		request.setAttribute("yuyueType",  yuyueType);
        return "YuyueType/yuyueType_frontshow";
	}
	/*前台查询Exam信息*/
	@RequestMapping(value="/{typeId}/frontshow02",method=RequestMethod.GET)
	public String frontshow02(@PathVariable Integer typeId,Model model,HttpServletRequest request,HttpSession session) throws Exception {
		/*根据主键typeId获取YuyueType对象*/
		YuyueType yuyueType = yuyueTypeService.getYuyueType(typeId);
/*		String state = request.getParameter("state");
		String user_name = request.getParameter("user_name");
		String yu = request.getParameter("yu");
		System.out.println(yu);
		if ("1".equals(yu))
		{
			System.out.println("预约来的!");
		}
		if ("2".equals(yu))
		{
			System.out.println("取消预约来的!");
		}
		System.out.println("user_name:   "+user_name);
		System.out.println("typeId:  "+typeId);
		System.out.println("预约状态：  "+state);
		if("可预约".equals(state)&&yu.equals("1"))
		  {
		  	  String u_name01=user_name;
		  	  yuyueType.setState("不可预约");
			  System.out.println("预约学员："+u_name01);
		  	  yuyueType.setUser_name(u_name01);
			  System.out.println(yuyueType);
		  	  yuyueTypeService.updateYuyueType(yuyueType);
			  yuyueType=yuyueTypeService.getYuyueType(typeId);
			  System.out.println("预约后：  ");
			  System.out.println(yuyueType);
		  }
		if("不可预约".equals(state)&&yuyueType.getUser_name().equals(user_name)&&yu.equals("2"))
		{
			String u_name02=user_name;
			yuyueType.setState("可预约");
			yuyueType.setUser_name("");
			yuyueTypeService.updateYuyueType(yuyueType);
			yuyueType=yuyueTypeService.getYuyueType(typeId);
			System.out.println("取消预约后：  ");
			System.out.println(yuyueType);
		}*/
		List<UserInfo> userInfoList = userInfoService.queryAllUserInfo();
		request.setAttribute("userInfoList", userInfoList);
		request.setAttribute("yuyueType",  yuyueType);
		return "YuyueType/yuyueType_frontshow02";
	}

	/*ajax方式显示缴费类型修改jsp视图页*/
	@RequestMapping(value="/{typeId}/update",method=RequestMethod.GET)
	public void update(@PathVariable Integer typeId,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
        /*根据主键typeId获取YuyueType对象*/
        YuyueType yuyueType = yuyueTypeService.getYuyueType(typeId);

        response.setContentType("text/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象 
		JSONObject jsonYuyueType = yuyueType.getJsonObject();
		out.println(jsonYuyueType.toString());
		out.flush();
		out.close();
	}

	/*ajax方式更新缴费类型信息*/
	@RequestMapping(value = "/{typeId}/update", method = RequestMethod.POST)
	public void update(@Validated YuyueType yuyueType, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
    	boolean success = false;
		if (br.hasErrors()) { 
			message = "输入的信息有错误！";
			writeJsonResponse(response, success, message);
			return;
		}
		try {

			System.out.println("修改：  "+yuyueType.getUser_name());

			yuyueTypeService.updateYuyueType(yuyueType);
			message = "预约更新成功!";
			success = true;
			writeJsonResponse(response, success, message);
		} catch (Exception e) {
			e.printStackTrace();
			message = "预约更新失败!";
			writeJsonResponse(response, success, message); 
		}
	}
    /*删除缴费类型信息*/
	@RequestMapping(value="/{typeId}/delete",method=RequestMethod.GET)
	public String delete(@PathVariable Integer typeId,HttpServletRequest request) throws UnsupportedEncodingException {
		  try {
			  yuyueTypeService.deleteYuyueType(typeId);
	            request.setAttribute("message", "该预约删除成功!");
	            return "message";
	        } catch (Exception e) { 
	            e.printStackTrace();
	            request.setAttribute("error", "该预约删除失败!");
				return "error";

	        }

	}

	/*ajax方式删除多条缴费类型记录*/
	@RequestMapping(value="/deletes",method=RequestMethod.POST)
	public void delete(String typeIds,HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException {
		String message = "";
    	boolean success = false;
        try { 
        	int count = yuyueTypeService.deleteYuyueTypes(typeIds);
        	success = true;
        	message = count + "条记录删除成功";
        	writeJsonResponse(response, success, message);
        } catch (Exception e) { 
            //e.printStackTrace();
            message = "有记录存在外键约束,删除失败";
            writeJsonResponse(response, success, message);
        }
	}

	/*ajax方式删除多条缴费类型记录*/
	@RequestMapping(value="/chushihua",method=RequestMethod.POST)
	public void chushihua(String typeIds,HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException {
		String message = "";
		boolean success = false;
		try {
			int count = yuyueTypeService.chushihuaYuyueTypes(typeIds);
			success = true;
			message = count + "条记录初始化成功";
			writeJsonResponse(response, success, message);
		} catch (Exception e) {
			//e.printStackTrace();
			message = "有记录存在外键约束,删除失败";
			writeJsonResponse(response, success, message);
		}
	}



	/*按照查询条件导出缴费类型信息到Excel*/
	@RequestMapping(value = { "/OutToExcel" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void OutToExcel( Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
        List<YuyueType> yuyueTypeList = yuyueTypeService.queryYuyueType();
        ExportExcelUtil ex = new ExportExcelUtil();
        String _title = "YuyueType信息记录";
        String[] headers = { "类型id","类型名称"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<yuyueTypeList.size();i++) {
        	YuyueType yuyueType = yuyueTypeList.get(i);
        	dataset.add(new String[]{yuyueType.getTypeId() + "",yuyueType.getTypeName()});
        }
        /*
        OutputStream out = null;
		try {
			out = new FileOutputStream("C://output.xls");
			ex.exportExcel(title,headers, dataset, out);
		    out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
		OutputStream out = null;//创建一个输出流对象 
		try { 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"YuyueType.xls");//filename是下载的xls的名，建议最好用英文
			response.setContentType("application/msexcel;charset=UTF-8");//设置类型 
			response.setHeader("Pragma","No-cache");//设置头 
			response.setHeader("Cache-Control","no-cache");//设置头 
			response.setDateHeader("Expires", 0);//设置日期头  
			String rootPath = request.getSession().getServletContext().getRealPath("/");
			ex.exportExcel(rootPath,_title,headers, dataset, out);
			out.flush();
		} catch (IOException e) { 
			e.printStackTrace(); 
		}finally{
			try{
				if(out!=null){ 
					out.close(); 
				}
			}catch(IOException e){ 
				e.printStackTrace(); 
			} 
		}
    }
}
