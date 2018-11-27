package com.es.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.es.entity.Operation.OPERATION;
import com.es.globle.Constants;
import com.es.dao.CourseDao;
import com.es.dao.OrderDao;
import com.es.dao.UserDao;
import com.es.entity.Course;
import com.es.entity.Lesson;
import com.es.entity.Operation;
import com.es.entity.Order;
import com.es.entity.User;
import com.es.json.form.request.OrderReqForm;
import com.es.json.form.response.OrderRespForm;
import com.es.redis.RedisService;
import com.es.service.OrderService;
import com.es.utils.Utils;

@Service("orderService")
public class OrderServiceImpl implements OrderService {

	@Autowired
	OrderDao orderDao;
	@Autowired
	CourseDao courseDao;
	@Autowired
	UserDao userDao;
	
	Logger logger=LoggerFactory.getLogger(getClass());
	
	@Override
	public int getOrder(Order order, OrderReqForm reqForm, Enum<OPERATION> operation) {
		int code = Constants.NO_ERROR_EXIST;
		//��ȡѧԺ����
		code = orderDao.getOrderByID(order, reqForm.getOrder_id());
		if(code==Constants.NO_ERROR_EXIST)
		{
			//�ҵ�����Ӧ���󣬸��ݲ����Զ�������޸�
			if(operation.equals(OPERATION.DELETE)){
				//ɾ���n�r����
				order.setOrderStatus(Order.ORDER_STATUS.DELETE.ordinal());
			}else if(operation.equals(OPERATION.UPDATE)){
				//�����n�r����
				if(reqForm.getCourse_id()!=null){
					order.setCourseID(reqForm.getCourse_id());
				}
				if(reqForm.getStu_id()!=null){
					order.setStudentID(reqForm.getStu_id());
				}
			}
		}else
		{
			//���ݿ�û���ҵ����û������
			code=Constants.NO_ERROR_EXIST;
			//����ύ����Ϣ�Ƿ�Ϸ�
			code = check(reqForm, operation);
			if(code==Constants.NO_ERROR_EXIST)
			{
				//����¿�ʱ
				order.setOrderID(Utils.getGenerateKey());
				order.setCourseID(reqForm.getCourse_id());
				order.setStudentID(reqForm.getStu_id());
				
			}else
			{
				code=Constants.INFO_NOT_COMPLETE;
				logger.error("������Ϣ������������{}��λ�ã�{}", order, getClass().getSimpleName());
			}
		}
		return code;
	}

	@Override
	public int saveOrUpdate(Order order) {
		int code=Constants.NO_ERROR_EXIST;
		code = orderDao.saveOrUpdate(order);
		if(code == Constants.NO_ERROR_EXIST){
			if(!RedisService.set("orm/order/entity/"+order.getOrderID(), order))
			{
				code=Constants.REDIS_ENTITY_SAVE_ERROR;
				logger.error("Redis����ʵ�����,ʵ��{},λ��{}",order ,getClass().getSimpleName());
			}else
			{
				RedisService.del("service/collections/syn/orders");
				RedisService.del("service/collections/syn/all");
			}
		}
		
		return code;
	}

	@Override
	public int getOrderCollections(OrderReqForm reqForm, List<Object> respForm,Integer start,Integer end) {
		int code=Constants.NO_ERROR_EXIST;
		@SuppressWarnings("unchecked")
		//��redis��ȡ���п�ʱ
		List<Order> orders=(List<Order>) RedisService.get("service/collections/syn/orders");
		//������ȥmysql�ж�ȡ
		if(orders==null||orders.isEmpty())
		{
			orders=new ArrayList<Order>();
			code=orderDao.getOrderCollections(orders);
			if(code == Constants.NO_ERROR_EXIST){
				RedisService.set("service/collections/syn/orders", orders);
			}
			
		}
		if(code==Constants.NO_ERROR_EXIST)
		{
			//ʵ������
			Collections.sort(orders);
			Order order = null;
			//��������
			for(Integer i = start;(i<=end||end<0)&&i<orders.size();i++){
				
				order = orders.get(i);
				//ɸѡ����
				boolean flag = true;
				//�����û���ʱ���
				if(reqForm.getOrder_id()!=null&&reqForm.getOrder_id().trim()!=""){
					if(!reqForm.getOrder_id().equals(order.getOrderID())){
						flag = false;
					}
				}
				if(reqForm.getCourse_id()!=null&&reqForm.getCourse_id().trim()!=""){
					if(!reqForm.getCourse_id().equals(order.getCourseID())){
						flag = false;
					}
				}
				
				if(reqForm.getStu_id()!=null&&reqForm.getStu_id().trim()!=""){
					if(!reqForm.getStu_id().equals(order.getStudentID())){
						flag = false;
					}
				}
				if(Order.ORDER_STATUS.DELETE.ordinal()==order.getOrderStatus()){
					flag = false;
				}
				
				if(flag){
					OrderRespForm orderResp = new OrderRespForm(order);
					Course course = new Course();
					courseDao.getCourseByID(course, order.getCourseID());
					User user = new User();
					userDao.getUserByID(user, order.getStudentID());
					orderResp.setStudent_name(user.getName());
					orderResp.setCourse_name(course.getCourseName());
					respForm.add(orderResp);
				}
			}
		}
		return code;
	}

	@Override
	public int check(OrderReqForm reqForm, Enum<OPERATION> operation) {
		int code=Constants.NO_ERROR_EXIST;
		//�����Ƿ�����
		if(operation.equals(Operation.OPERATION.ADD))
		{
			//�������ʱ
			if(reqForm.getCourse_id()==null||reqForm.getCourse_id().trim()=="")
			{
				code=Constants.INFO_NOT_COMPLETE;
			}
			if(reqForm.getStu_id()==null||reqForm.getStu_id().trim()=="")
			{
				code=Constants.INFO_NOT_COMPLETE;
			}
		}
		return code;
	}


}
