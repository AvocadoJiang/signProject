package com.es.dao;

import java.util.List;

import com.es.entity.User;

/**
 * @author Anson
 *	Copyright by EasyShare 2017
 *  
 *  All right reserved
 *
 *  Created on 2017年6月25日 下午4:21:59
 *  
 *  用户信息数据访问接口
 */
public interface UserDao {

	/**
	 * 获取用户对象
	 * @param user
	 * 		待填充的用户对象
	 * @param userId
	 * 		待获取用户编号
	 * @return
	 * 		错误码
	 */
	public int getUserByID(User user, String userID);
	


	/**
	 * 获取的用户集合
	 * @param users
	 * 		待填充的用户对象集合
	 * @return
	 * 		错误码
	 */
	public int getUserCollections(List<User> users);

	/**
	 * 保存或更新用户对象
	 * @param user
	 * 		待处理的用户对象
	 * @return
	 * 		错误码
	 */
	public int saveOrUpdate(User user);
}
