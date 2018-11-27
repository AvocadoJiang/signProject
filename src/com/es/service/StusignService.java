package com.es.service;

import com.es.json.form.request.StusignReqForm;
import java.util.List;
import com.es.entity.Operation.OPERATION;
import com.es.entity.Stusign;


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
public interface StusignService {

	/**
	 * 获取学生签到对象
	 * @param stusign
	 * 		待填充的学生签到对象
	 * @param reqForm
	 * 		学生签到请求对象
	 * @param operation
	 * 		操作类型
	 * @return
	 * 		错误码
	 */
	public int getStusign(Stusign stusign, StusignReqForm reqForm, Enum<OPERATION> operation);
	
	/**
	 * 检查添加数据时信息的完整性
	 * @param reqForm
	 * 		与视图交互的注册表格
	 * @param operation
	 * 		操作类型
	 * @return
	 * 		错误码
	 */
	public int check(StusignReqForm reqForm, Enum<OPERATION> operation);

	/**
	 * 保存、修改或删除学生签到
	 * @param stusign
	 * 		待处理的学生签到对象
	 * @return
	 * 		错误码
	 */
	public int saveOrUpdate(Stusign stusign);

	/**
	 * 获取学生签到集合
	 * @param reqForm
	 * 		待获取的学生签到信息请求对象
	 * @param objects
	 * 		待填充的学生签到对象集合
	 * @return
	 * 		错误码
	 */
	public int getStusignCollections(StusignReqForm reqForm, List<Object> respForm,Integer start,Integer end);
	
	
}
