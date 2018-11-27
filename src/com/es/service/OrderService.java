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
 *  Created on 2017��6��25�� ����4:40:36
 *  
 *  �û���Ϣ����ӿ�
 */
public interface OrderService {

	/**
	 * ��ȡѡ�ζ���
	 * @param order
	 * 		������ѡ�ζ���
	 * @param reqForm
	 * 		ѡ���������
	 * @param operation
	 * 		��������
	 * @return
	 * 		������
	 */
	public int getOrder(Order order, OrderReqForm reqForm, Enum<OPERATION> operation);
	
	/**
	 * ����������ʱ��Ϣ��������
	 * @param reqForm
	 * 		����ͼ������ע����
	 * @param operation
	 * 		��������
	 * @return
	 * 		������
	 */
	public int check(OrderReqForm reqForm, Enum<OPERATION> operation);

	/**
	 * ���桢�޸Ļ�ɾ��ѡ��
	 * @param order
	 * 		�������ѡ�ζ���
	 * @return
	 * 		������
	 */
	public int saveOrUpdate(Order order);

	/**
	 * ��ȡѡ�μ���
	 * @param reqForm
	 * 		����ȡ��ѡ����Ϣ�������
	 * @param objects
	 * 		������ѡ�ζ��󼯺�
	 * @return
	 * 		������
	 */
	public int getOrderCollections(OrderReqForm reqForm, List<Object> respForm,Integer start,Integer end);
	
	
}
