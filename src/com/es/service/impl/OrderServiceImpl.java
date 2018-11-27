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
		//获取学院对象
		code = orderDao.getOrderByID(order, reqForm.getOrder_id());
		if(code==Constants.NO_ERROR_EXIST)
		{
			//找到了相应对象，根据操作对对象进行修改
			if(operation.equals(OPERATION.DELETE)){
				//删除課時操作
				order.setOrderStatus(Order.ORDER_STATUS.DELETE.ordinal());
			}else if(operation.equals(OPERATION.UPDATE)){
				//更新課時操作
				if(reqForm.getCourse_id()!=null){
					order.setCourseID(reqForm.getCourse_id());
				}
				if(reqForm.getStu_id()!=null){
					order.setStudentID(reqForm.getStu_id());
				}
			}
		}else
		{
			//数据库没有找到该用户的情况
			code=Constants.NO_ERROR_EXIST;
			//检查提交的信息是否合法
			code = check(reqForm, operation);
			if(code==Constants.NO_ERROR_EXIST)
			{
				//添加新课时
				order.setOrderID(Utils.getGenerateKey());
				order.setCourseID(reqForm.getCourse_id());
				order.setStudentID(reqForm.getStu_id());
				
			}else
			{
				code=Constants.INFO_NOT_COMPLETE;
				logger.error("请求信息不完整，对象：{}，位置：{}", order, getClass().getSimpleName());
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
				logger.error("Redis保存实体出错,实体{},位置{}",order ,getClass().getSimpleName());
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
		//从redis读取所有课时
		List<Order> orders=(List<Order>) RedisService.get("service/collections/syn/orders");
		//读不到去mysql中读取
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
			//实现排序
			Collections.sort(orders);
			Order order = null;
			//遍历集合
			for(Integer i = start;(i<=end||end<0)&&i<orders.size();i++){
				
				order = orders.get(i);
				//筛选内容
				boolean flag = true;
				//根据用户课时编号
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
		//数据是否完整
		if(operation.equals(Operation.OPERATION.ADD))
		{
			//添加数据时
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
