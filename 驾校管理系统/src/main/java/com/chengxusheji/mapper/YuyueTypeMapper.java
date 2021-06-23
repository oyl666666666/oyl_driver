package com.chengxusheji.mapper;

import java.util.ArrayList;

import com.chengxusheji.po.Exam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.chengxusheji.po.YuyueType;

@Mapper
public interface YuyueTypeMapper {
	/*添加缴费类型信息*/
	public void addYuyueType(YuyueType yuyueType) throws Exception;

	/*按照查询条件分页查询缴费类型记录*/
	public ArrayList<YuyueType> queryYuyueType(@Param("where") String where, @Param("startIndex") int startIndex, @Param("pageSize") int pageSize) throws Exception;

	/*按照查询条件查询所有缴费类型记录*/
	public ArrayList<YuyueType> queryYuyueTypeList(@Param("where") String where) throws Exception;
	/*按照查询条件分页查询考试记录*/
	public ArrayList<YuyueType> queryYuyueType02(@Param("where") String where, @Param("startIndex") int startIndex, @Param("pageSize") int pageSize) throws Exception;


	/*按照查询条件的缴费类型记录数*/
	public int queryYuyueTypeCount(@Param("where") String where) throws Exception;
	/*按照查询条件的考试记录数*/
	public int queryYuyueTypeCount02(@Param("where") String where) throws Exception;
	/*按照查询条件查询所有考试记录*/
	public ArrayList<YuyueType> queryYuyueTypeList02(@Param("where") String where) throws Exception;

	/*根据主键查询某条缴费类型记录*/
	public YuyueType getYuyueType(int typeId) throws Exception;

	/*更新缴费类型记录*/
	public void updateYuyueType(YuyueType yuyueType) throws Exception;

	/*删除缴费类型记录*/
	public void deleteYuyueType(int typeId) throws Exception;
/*初始化删除*/
	public void deleteYuyueType02(@Param("where") String where) throws Exception;

	/*初始化预约记录*/
	public void chushihuaYuyueType(@Param("typeId") int typeId,@Param("state") String state,@Param("user_name") String user_name) throws Exception;

}
