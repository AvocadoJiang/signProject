package com.es.dao;

import java.util.List;

import com.es.entity.Class;

/**
 * @author Anson
 *	Copyright by EasyShare 2017
 *  
 *  All right reserved
 *
 *  Created on 2017年6月25日 下午4:21:59
 *  
 *  学院信息数据访问接口
 */
public interface ClassDao {

	/**
	 * 获取班级对象
	 * @param c
	 * 		待填充的班级对象
	 * @param classID
	 * 		待获取学院编号
	 * @return
	 * 		错误码
	 */
	public int getClassByID(Class c, String classID);
	


	/**
	 * 获取的班级集合
	 * @param classes
	 * 		待填充的班级对象集合
	 * @return
	 * 		错误码
	 */
	public int getClassCollections(List<Class> classes);

	/**
	 * 保存或更新班级对象
	 * @param c
	 * 		待处理的班级对象
	 * @param conn 
	 * 		批处理数据库连接对象
	 * @return
	 * 		错误码
	 */
	public int saveOrUpdate(Class c);
}
