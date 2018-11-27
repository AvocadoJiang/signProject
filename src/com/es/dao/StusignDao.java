package com.es.dao;

import java.util.List;

import com.es.entity.Stusign;

/**
 * @author Anson
 *	Copyright by EasyShare 2017
 *  
 *  All right reserved
 *
 *  Created on 2017年6月25日 下午4:21:59
 *  
 *  学生签到信息数据访问接口
 */
public interface StusignDao {

	/**
	 * 获取学生签到对象
	 * @param stusign
	 * 		待填充的学生签到对象
	 * @param stusignID
	 * 		待获取学生签到编号
	 * @return
	 * 		错误码
	 */
	public int getStusignByID(Stusign stusign, String stusignID);
	


	/**
	 * 获取的学生签到集合
	 * @param stusigns
	 * 		待填充的学生签到对象集合
	 * @return
	 * 		错误码
	 */
	public int getStusignCollections(List<Stusign> stusigns);

	/**
	 * 保存或更新学生签到对象
	 * @param stusign
	 * 		待处理的学生签到对象
	 * @param conn 
	 * 		批处理数据库连接对象
	 * @return
	 * 		错误码
	 */
	public int saveOrUpdate(Stusign stusign);
}
