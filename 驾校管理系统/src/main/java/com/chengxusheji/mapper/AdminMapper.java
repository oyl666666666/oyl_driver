package com.chengxusheji.mapper;


import org.apache.ibatis.annotations.Mapper;

import com.chengxusheji.po.Admin;

@Mapper
public interface AdminMapper {
 
	public Admin findAdminByUserName(String username) throws Exception;
	
	public void changePassword(Admin admin) throws Exception;
	
	
	
}
