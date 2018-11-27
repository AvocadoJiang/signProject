package com.es.dao;

import java.util.List;

import com.es.entity.Order;

/**
 * @author Anson
 *	Copyright by EasyShare 2017
 *  
 *  All right reserved
 *
 *  Created on 2017��6��25�� ����4:21:59
 *  
 *  ѡ����Ϣ���ݷ��ʽӿ�
 */
public interface OrderDao {

	/**
	 * ��ȡѡ�ζ���
	 * @param order
	 * 		������ѡ�ζ���
	 * @param orderID
	 * 		����ȡѡ�α��
	 * @return
	 * 		������
	 */
	public int getOrderByID(Order order, String orderID);
	


	/**
	 * ��ȡ��ѡ�μ���
	 * @param orders
	 * 		������ѡ�ζ��󼯺�
	 * @return
	 * 		������
	 */
	public int getOrderCollections(List<Order> orders);

	/**
	 * ��������ѡ�ζ���
	 * @param order
	 * 		�������ѡ�ζ���
	 * @param conn 
	 * 		���������ݿ����Ӷ���
	 * @return
	 * 		������
	 */
	public int saveOrUpdate(Order order);
}
