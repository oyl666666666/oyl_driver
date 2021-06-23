package com.chengxusheji.po;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.json.JSONException;
import org.json.JSONObject;

public class YuyueType {
    /*类型id*/
    private Integer typeId;
    public Integer getTypeId(){
        return typeId;
    }
    public void setTypeId(Integer typeId){
        this.typeId = typeId;
    }

    /*车牌号*/
    @NotEmpty(message="类型名称不能为空")
    private String typeName;
    public String getTypeName() {
        return typeName;
    }
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
    /*教练工号*/
    @NotEmpty(message="教练工号不能为空")
    private String coach;
    public String getCoach() {
        return coach;
    }
    public void setCoach(String coach) {
        this.coach = coach;
    }
    /*学员名字*/
    private String user_name;
    public String getUser_name() {
        return user_name;
    }
    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
    /*预约状态*/
    @NotEmpty(message="预约状态不能为空")
    private String state;
    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }

    private String yuyue_day;
    public String getYuyue_day() {
        return yuyue_day;
    }
    public void setYuyue_day(String yuyue_day) {
        this.yuyue_day = yuyue_day;
    }

    private String yuyue_time;
    public String getYuyue_time() {
        return yuyue_time;
    }
    public void setYuyue_time(String yuyue_time) {
        this.yuyue_time = yuyue_time;
    }

    @Override
    public String toString() {
        return "YuyueType{" +
                "typeId=" + typeId +
                ", typeName='" + typeName + '\'' +
                ", coach='" + coach + '\'' +
                ", user_name='" + user_name + '\'' +
                ", state='" + state + '\'' +
                ", yuyue_day='" + yuyue_day + '\'' +
                ", yuyue_time='" + yuyue_time + '\'' +
                '}';
    }

    public JSONObject getJsonObject() throws JSONException {
    	JSONObject jsonYuyueType=new JSONObject(); 
		jsonYuyueType.accumulate("typeId", this.getTypeId());
		jsonYuyueType.accumulate("typeName", this.getTypeName());
        jsonYuyueType.accumulate("coach", this.getCoach());
        jsonYuyueType.accumulate("user_name", this.getUser_name());
        jsonYuyueType.accumulate("state", this.getState());
        jsonYuyueType.accumulate("yuyue_day", this.getYuyue_day());
        jsonYuyueType.accumulate("yuyue_time", this.getYuyue_time());
		return jsonYuyueType;
    }}