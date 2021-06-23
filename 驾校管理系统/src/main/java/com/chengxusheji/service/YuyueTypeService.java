package com.chengxusheji.service;

import java.util.ArrayList;
import java.util.Calendar;
import javax.annotation.Resource;

import com.chengxusheji.po.*;
import org.springframework.stereotype.Service;

import com.chengxusheji.mapper.YuyueTypeMapper;
@Service
public class YuyueTypeService {

	@Resource YuyueTypeMapper yuyueTypeMapper;
    /*每页显示记录数目*/
    private int rows = 10;;
    public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}

    /*保存查询后总的页数*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    /*保存查询到的总记录数*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    int month01=Calendar.getInstance().get(Calendar.MONTH)+1;
    int today01 =Calendar.getInstance().get(Calendar.DATE);
    int hour01 =Calendar.getInstance().get(Calendar.HOUR);
    public void auto_update()throws Exception{
        int month=month01;
        int today =today01;
        int hour =hour01;
        ArrayList<YuyueType> yuyueTypes = queryAllYuyueType();
        int time=8;
        //老子不处理月份
        System.out.println("今天："+today+"号");
        for (YuyueType yu:yuyueTypes) {
            if (yu==null)
            {
                System.out.println("一个也没有！");
                break;
            }
            //拿到日期
            String yuyue_day=yu.getYuyue_day();
            //转换为数字
            StringBuffer buf=new StringBuffer();
            char[] ch=yuyue_day.toCharArray();
            for(int i=0;i<ch.length;i++){
                if(ch[i]>'0' && ch[i]<'9'){
                    buf.append(ch[i]);
                }
            }
            int b=Integer.valueOf(buf.toString())%100;
            System.out.println("提取的int值为 "+b);
            System.out.println("看"+yuyue_day+"号过期没有");
            //看是否过期
            if(b<today)
            {
                System.out.println("他妈的过期了！！！");
                System.out.println(yu.getCoach()+"教练，日期为："+yuyue_day+"号  的过期了");
                //开始删除
                deleteTime(yu.getYuyue_day(),yu.getCoach());

                //再加一天，嘻嘻嘻！
                    yu.setYuyue_day(month+"月"+(today+1)+"日");
                    for (int j = 0; j < 5; j++) {
                /*
                8点到10点  10点到12点  12点到14点  14点到16点    16点到18点
                 */
                        yu.setYuyue_time(time+"点到"+(time+2)+"点");
                        System.out.println("设置yuyueType里面：  "+time+"点到"+(time+2)+"点");
                        System.out.println("写进系统的时间："+month+"月"+today+"日"+time+"时");
                        yuyueTypeMapper.addYuyueType(yu);
                        time=time+2;//10 12 14 16
                    }

            }
        }
    }
    public void deleteTime(String yuyue_day,String coach) throws Exception {
        String where = "where 1=1";
        System.out.println("按照日期和教练删除");
        System.out.println(coach+"教练"+yuyue_day+"日期");
        where = where + " and t_yuyuetype.yuyue_day like '%" + yuyue_day + "%'";
        where = where + " and t_yuyuetype.coach like '%" + coach + "%'";
        yuyueTypeMapper.deleteYuyueType02(where);
    }

    /*添加缴费类型记录*/
    public void addYuyueType(YuyueType yuyueType) throws Exception {
        int month=month01;
        int today =today01;
        int hour =hour01;
        int time=8;
        int zhen_today=today;
        System.out.println(month+"月"+today+"日"+hour+"时");
        for (int i = 0; i < 2; i++) {
            yuyueType.setYuyue_day(month+"月"+today+"日");
            for (int j = 0; j < 5; j++) {
                /*
                8点到10点  10点到12点  12点到14点  14点到16点    16点到18点
                 */
                if (zhen_today==today&&time<=hour&&hour>=8&&hour<18)
                {
                          time=(hour%2==0)?hour:hour+1;
                          System.out.println("现在的时间hour: "+hour+"点");
                          System.out.println("改后的时间time: "+time+"点");
                }
                yuyueType.setYuyue_time(time+"点到"+(time+2)+"点");
                System.out.println("设置yuyueType里面：  "+time+"点到"+(time+2)+"点");
                System.out.println("写进系统的时间："+month+"月"+today+"日"+time+"时");
                yuyueTypeMapper.addYuyueType(yuyueType);
                if (time==16)
                {
                    break;
                }
                time=time+2;//8  10 12 14 16
                System.out.println(time+"时");
            }
            System.out.println("第二天");
            time=8;
            today=today+1;
        }
    }

    /*按照查询条件分页查询缴费类型记录*/
    public ArrayList<YuyueType> queryYuyueType(int currentPage) throws Exception {
     	String where = "where 1=1";
    	int startIndex = (currentPage-1) * this.rows;
    	return yuyueTypeMapper.queryYuyueType(where, startIndex, this.rows);
    }
    /*按照查询条件分页查询考试记录*/
    public ArrayList<YuyueType> queryYuyueTypeList02(String typeName, String coach, String user_name, String state,String yuyue_day,String yuyue_time,int currentPage) throws Exception {
        String where = "where 1=1";
        System.out.println("按照查询条件分页查询考试记录");
        System.out.println(typeName);
        System.out.println(coach);
        System.out.println(user_name);
        System.out.println(state);
        if(!typeName.equals("")) where = where + " and t_yuyuetype.typeName like '%" + typeName + "%'";
        if(!coach.equals("")) where = where + " and t_yuyuetype.coach like '%" + coach + "%'";
        if(null != user_name  && user_name.equals(""))  where += " and t_yuyuetype.user_name='" + user_name + "'";
        if(!state.equals("")) where = where + " and t_yuyuetype.state like '" + state + "%'";
        if(!yuyue_day.equals("")) where = where + " and t_yuyuetype.yuyue_day like '" + yuyue_day + "%'";
        if(!yuyue_time.equals("")) where = where + " and t_yuyuetype.yuyue_time like '" + yuyue_time + "%'";
        //求得开始索引
        int startIndex = (currentPage-1) * this.rows;
        return yuyueTypeMapper.queryYuyueType02(where, startIndex, this.rows);
    }
    /*按照查询条件查询所有记录*/
    public ArrayList<YuyueType> queryYuyueTypeList02(String typeName, String coach, String user_name, String state) throws Exception  {
        String where = "where 1=1";
        System.out.println("按照查询条件分页查询考试记录");
        System.out.println(typeName);
        System.out.println(coach);
        System.out.println(user_name);
        System.out.println(state);
        if(!typeName.equals("")) where = where + " and t_yuyuetype.typeName like '%" + typeName + "%'";
        if(!coach.equals("")) where = where + " and t_yuyuetype.coach like '%" + coach + "%'";
        if(null != user_name  && user_name.equals(""))  where += " and t_yuyuetype.user_name='" + user_name + "'";
        if(!state.equals("")) where = where + " and t_yuyuetype.state like '%" + state + "%'";
        return yuyueTypeMapper.queryYuyueTypeList02(where);
    }
    /*按照查询条件查询所有记录*/
    public ArrayList<YuyueType> queryYuyueType() throws Exception  {
     	String where = "where 1=1";
    	return yuyueTypeMapper.queryYuyueTypeList(where);
    }

    /*查询所有缴费类型记录*/
    public ArrayList<YuyueType> queryAllYuyueType()  throws Exception {
        return yuyueTypeMapper.queryYuyueTypeList("where 1=1");
    }

    /*当前查询条件下计算总的页数和记录数*/
    public void queryTotalPageAndRecordNumber() throws Exception {
     	String where = "where 1=1";
        recordNumber = yuyueTypeMapper.queryYuyueTypeCount(where);
        int mod = recordNumber % this.rows;
        totalPage = recordNumber / this.rows;
        if(mod != 0) totalPage++;
    }
    /*当前查询条件下计算总的页数和记录数*/
    public void queryTotalPageAndRecordNumber02(String typeName,String coach,String yuyue_day,String yuyue_time,String user_name,String state) throws Exception {
        String where = "where 1=1";
        System.out.println("当前查询条件下计算总的页数和记录数"+typeName+coach+user_name+state);
        if(!typeName.equals("")) where = where + " and t_yuyuetype.typeName like '%" + typeName + "%'";
        if(!coach.equals("")) where = where + " and t_yuyuetype.coach like '%" + coach + "%'";
        if(null != user_name && !user_name.equals(""))  where += " and t_yuyuetype.user_name='" + user_name + "'";
        if(!state.equals("")) where = where + " and t_yuyuetype.state like '%" + state + "%'";
        if(!yuyue_day.equals("")) where = where + " and t_yuyuetype.yuyue_day like '" + yuyue_day + "%'";
        if(!yuyue_time.equals("")) where = where + " and t_yuyuetype.yuyue_time like '" + yuyue_time + "%'";
        recordNumber = yuyueTypeMapper.queryYuyueTypeCount02(where);
        int mod = recordNumber % this.rows;
        totalPage = recordNumber / this.rows;
        if(mod != 0) totalPage++;
        System.out.println("总记录数"+recordNumber);
        System.out.println("页数："+totalPage);
    }

    /*根据主键获取缴费类型记录*/
    public YuyueType getYuyueType(int typeId) throws Exception  {
        YuyueType yuyueType = yuyueTypeMapper.getYuyueType(typeId);
        return yuyueType;
    }

    /*更新缴费类型记录*/
    public void updateYuyueType(YuyueType yuyueType) throws Exception {
        yuyueTypeMapper.updateYuyueType(yuyueType);
    }

    /*删除一条缴费类型记录*/
    public void deleteYuyueType (int typeId) throws Exception {
        yuyueTypeMapper.deleteYuyueType(typeId);
    }

    /*删除多条缴费类型信息*/
    public int deleteYuyueTypes (String typeIds) throws Exception {
    	String _typeIds[] = typeIds.split(",");
    	for(String _typeId: _typeIds) {
    		yuyueTypeMapper.deleteYuyueType(Integer.parseInt(_typeId));
    	}
    	return _typeIds.length;
    }

    /*删除多条缴费类型信息*/
    public int chushihuaYuyueTypes (String typeIds) throws Exception {
        String _typeIds[] = typeIds.split(",");
        for(String _typeId: _typeIds) {
            yuyueTypeMapper.chushihuaYuyueType(Integer.parseInt(_typeId),"可预约","");
        }
        return _typeIds.length;
    }


}
