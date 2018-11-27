package com.es.dao;

import java.util.List;

import com.es.entity.School;

/**
 * @author Anson
 *	Copyright by EasyShare 2017
 *  
 *  All right reserved
 *
 *  Created on 2017年6月25日 下午4:21:59
 *  
 *  学校信息数据访问接口
 */
public interface SchoolDao {

	/**
	 * 获取学校对象
	 * @param school
	 * 		待填充的学校对象
	 * @param schoolID
	 * 		待获取学校编号
	 * @return
	 * 		错误码
	 */
	public int getSchoolByID(School school, String schoolID);
	


	/**
	 * 获取的学校集合
	 * @param schools
	 * 		待填充的学校对象集合
	 * @return
	 * 		错误码
	 */
	public int getSchoolCollections(List<School> schools);

	/**
	 * 保存或更新学校对象
	 * @param school
	 * 		待处理的学校对象
	 * @return
	 * 		错误码
	 */
	public int saveOrUpdate(School school);
}
