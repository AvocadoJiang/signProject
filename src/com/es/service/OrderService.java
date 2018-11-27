package com.es.service;

import com.es.json.form.request.OrderReqForm;
import java.util.List;
import com.es.entity.Order;
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
public interface OrderService {

	/**
	 * 获取选课对象
	 * @param order
	 * 		待填充的选课对象
	 * @param reqForm
	 * 		选课请求对象
	 * @param operation
	 * 		操作类型
	 * @return
	 * 		错误码
	 */
	public int getOrder(Order order, OrderReqForm reqForm, Enum<OPERATION> operation);
	
	/**
	 * 检查添加数据时信息的完整性
	 * @param reqForm
	 * 		与视图交互的注册表格
	 * @param operation
	 * 		操作类型
	 * @return
	 * 		错误码
	 */
	public int check(OrderReqForm reqForm, Enum<OPERATION> operation);

	/**
	 * 保存、修改或删除选课
	 * @param order
	 * 		待处理的选课对象
	 * @return
	 * 		错误码
	 */
	public int saveOrUpdate(Order order);

	/**
	 * 获取选课集合
	 * @param reqForm
	 * 		待获取的选课信息请求对象
	 * @param objects
	 * 		待填充的选课对象集合
	 * @return
	 * 		错误码
	 */
	public int getOrderCollections(OrderReqForm reqForm, List<Object> respForm,Integer start,Integer end);
	
	
}
