package com.es.service;

import com.es.json.form.request.MajorReqForm;

import java.util.List;
import com.es.entity.Major;
import com.es.entity.Operation.OPERATION;

/**
 * @author Anson
 *	Copyright by EasyShare 2017
 *  
 *  All right reserved
 *
 *  Created on 2017年6月25日 下午4:40:36
 *  
 *  班级信息服务接口
 */
public interface MajorService {

	/**
	 * 获取专业对象
	 * @param c
	 * 		待填充的专业对象
	 * @param reqForm
	 * 		班级请求对象
	 * @param operation
	 * 		操作类型
	 * @return
	 * 		错误码
	 */
	public int getMajor(Major c, MajorReqForm reqForm, Enum<OPERATION> operation);
	
	/**
	 * 检查添加数据时信息的完整性
	 * @param reqForm
	 * 		与视图交互的注册表格
	 * @param operation
	 * 		操作类型
	 * @return
	 * 		错误码
	 */
	public int check(MajorReqForm reqForm, Enum<OPERATION> operation);

	/**
	 * 保存、修改或删除专业
	 * @param c
	 * 		待处理的专业对象
	 * @return
	 * 		错误码
	 */
	public int saveOrUpdate(Major c);

	/**
	 * 获取专业集合
	 * @param reqForm
	 * 		待获取的专业信息请求对象
	 * @param objects
	 * 		待填充的专业对象集合
	 * @return
	 * 		错误码
	 */
	public int getMajorCollections(MajorReqForm reqForm, List<Object> respForm,Integer start,Integer end);
	
	
}
