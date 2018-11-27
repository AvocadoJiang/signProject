package com.es.service;

import com.es.json.form.request.SchoolReqForm;
import java.util.List;
import com.es.entity.Operation.OPERATION;
import com.es.entity.School;

/**
 * @author Anson
 *	Copyright by EasyShare 2017
 *  
 *  All right reserved
 *
 *  Created on 2017年6月25日 下午4:40:36
 *  
 *  用户信息服务接口
 */
public interface SchoolService {

	/**
	 * 获取学校对象
	 * @param school
	 * 		待填充的学校对象
	 * @param reqForm
	 * 		学校请求对象
	 * @param operation
	 * 		操作类型
	 * @return
	 * 		错误码
	 */
	public int getSchool(School school, SchoolReqForm reqForm, Enum<OPERATION> operation);
	
	/**
	 * 检查添加数据时信息的完整性
	 * @param registerForm
	 * 		与视图交互的注册表格
	 * @param operation
	 * 		操作类型
	 * @return
	 * 		错误码
	 */
	public int check(SchoolReqForm reqForm, Enum<OPERATION> operation);

	/**
	 * 保存、修改或删除学校
	 * @param school
	 * 		待处理的学校对象
	 * @return
	 * 		错误码
	 */
	public int saveOrUpdate(School school);

	/**
	 * 获取学校集合
	 * @param reqForm
	 * 		待获取的学校信息请求对象
	 * @param objects
	 * 		待填充的学校对象集合
	 * @return
	 * 		错误码
	 */
	public int getSchoolCollections(SchoolReqForm reqForm, List<Object> respForm,Integer start,Integer end);
	
	
}
