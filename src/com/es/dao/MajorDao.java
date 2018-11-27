package com.es.dao;

import java.util.List;

import com.es.entity.Major;

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
public interface MajorDao {

	/**
	 * 获取班级对象
	 * @param major
	 * 		待填充的专业对象
	 * @param majorID
	 * 		待获取专业编号
	 * @return
	 * 		错误码
	 */
	public int getMajorByID(Major major, String majorID);
	


	/**
	 * 获取的专业集合
	 * @param majors
	 * 		待填充的班级对象集合
	 * @return
	 * 		错误码
	 */
	public int getMajorCollections(List<Major> majors);

	/**
	 * 保存或更新专业对象
	 * @param major
	 * 		待处理的专业对象
	 * @param conn 
	 * 		批处理数据库连接对象
	 * @return
	 * 		错误码
	 */
	public int saveOrUpdate(Major major);
}
