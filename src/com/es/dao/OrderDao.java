package com.es.dao;

import java.util.List;

import com.es.entity.Order;

/**
 * @author Anson
 *	Copyright by EasyShare 2017
 *  
 *  All right reserved
 *
 *  Created on 2017年6月25日 下午4:21:59
 *  
 *  选课信息数据访问接口
 */
public interface OrderDao {

	/**
	 * 获取选课对象
	 * @param order
	 * 		待填充的选课对象
	 * @param orderID
	 * 		待获取选课编号
	 * @return
	 * 		错误码
	 */
	public int getOrderByID(Order order, String orderID);
	


	/**
	 * 获取的选课集合
	 * @param orders
	 * 		待填充的选课对象集合
	 * @return
	 * 		错误码
	 */
	public int getOrderCollections(List<Order> orders);

	/**
	 * 保存或更新选课对象
	 * @param order
	 * 		待处理的选课对象
	 * @param conn 
	 * 		批处理数据库连接对象
	 * @return
	 * 		错误码
	 */
	public int saveOrUpdate(Order order);
}
