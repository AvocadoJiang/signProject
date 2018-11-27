package com.es.service;

import com.es.json.form.request.SigninForm;

import java.util.List;

import com.es.entity.Operation.OPERATION;
import com.es.entity.User;
import com.es.json.form.request.UserReqForm;

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
public interface UserService {

	/**
	 * 检查用户登录
	 * @param signinForm
	 * 		SigninForm - 与视图交互的注册表格
	 * @param user
	 * 		待填充的用户对象
	 * @return
	 * 		int - 错误码
	 */
	public int checkLoginInfo(User user, SigninForm signinForm);

	/**
	 * 检查注册信息
	 * @param registerForm
	 * 		与视图交互的注册表格
	 * @param operation
	 * 		操作类型
	 * @return
	 * 		错误码
	 */
	public int check(UserReqForm reqForm, Enum<OPERATION> operation);

	/**
	 * 获取用户对象
	 * @param user
	 * 		待填充的用户对象
	 * @param reqForm
	 * 		用户注册请求对象
	 * @param operation
	 * 		操作类型
	 * @return
	 * 		错误码
	 */
	public int getUser(User user, UserReqForm reqForm, Enum<OPERATION> operation);

	/**
	 * 保存、修改或删除用户
	 * @param user
	 * 		待处理的用户对象
	 * @return
	 * 		错误码
	 */
	public int saveOrUpdate(User user);

	/**
	 * 获取用户集合
	 * @param reqForm
	 * 		待获取的用户信息请求对象
	 * @param objects
	 * 		待填充的用户对象集合
	 * @return
	 * 		错误码
	 */
	public int getUserCollections(UserReqForm reqForm, List<Object> respForm,Integer start,Integer end);
	
	
}
