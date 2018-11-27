package com.es.service;

import com.es.json.form.request.AcademyReqForm;
import java.util.List;
import com.es.entity.Academy;
import com.es.entity.Operation.OPERATION;

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
public interface AcademyService {

	/**
	 * 获取学院对象
	 * @param academy
	 * 		待填充的学院对象
	 * @param reqForm
	 * 		学院请求对象
	 * @param operation
	 * 		操作类型
	 * @return
	 * 		错误码
	 */
	public int getAcademy(Academy academy, AcademyReqForm reqForm, Enum<OPERATION> operation);
	
	/**
	 * 检查添加数据时信息的完整性
	 * @param reqForm
	 * 		与视图交互的注册表格
	 * @param operation
	 * 		操作类型
	 * @return
	 * 		错误码
	 */
	public int check(AcademyReqForm reqForm, Enum<OPERATION> operation);

	/**
	 * 保存、修改或删除学院
	 * @param academy
	 * 		待处理的学院对象
	 * @return
	 * 		错误码
	 */
	public int saveOrUpdate(Academy academy);

	/**
	 * 获取学院集合
	 * @param reqForm
	 * 		待获取的学院信息请求对象
	 * @param objects
	 * 		待填充的学院对象集合
	 * @return
	 * 		错误码
	 */
	public int getAcademyCollections(AcademyReqForm reqForm, List<Object> respForm,Integer start,Integer end);
	
	
}
