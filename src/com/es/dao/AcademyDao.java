package com.es.dao;

import java.util.List;

import com.es.entity.Academy;

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
public interface AcademyDao {

	/**
	 * 获取学院对象
	 * @param academy
	 * 		待填充的学院对象
	 * @param academyID
	 * 		待获取学院编号
	 * @return
	 * 		错误码
	 */
	public int getAcademyByID(Academy academy, String academyID);
	


	/**
	 * 获取的学院集合
	 * @param academys
	 * 		待填充的学院对象集合
	 * @return
	 * 		错误码
	 */
	public int getAcademyCollections(List<Academy> academys);

	/**
	 * 保存或更新学院对象
	 * @param academy
	 * 		待处理的学院对象
	 * @param conn 
	 * 		批处理数据库连接对象
	 * @return
	 * 		错误码
	 */
	public int saveOrUpdate(Academy academy);
}
