package com.es.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.es.dao.ClassDao;
import com.es.entity.Class;
import com.es.entity.Major;
import com.es.entity.Operation;
import com.es.entity.Operation.OPERATION;
import com.es.globle.Constants;
import com.es.json.form.request.ClassReqForm;
import com.es.json.form.response.ClassRespForm;
import com.es.redis.RedisService;
import com.es.service.ClassService;
import com.es.utils.Utils;

@Service("classService")
public class ClassServiceImpl implements ClassService {
	
	@Autowired
	ClassDao classDao;
	
	Logger logger=LoggerFactory.getLogger(getClass());
	@Override
	public int getClass(Class c, ClassReqForm reqForm, Enum<OPERATION> operation) {
		int code = Constants.NO_ERROR_EXIST;
		//获取学院对象
		code = classDao.getClassByID(c, reqForm.getClass_id());
		if(code==Constants.NO_ERROR_EXIST)
		{
			//找到了相应对象，根据操作对对象进行修改
			if(operation.equals(OPERATION.DELETE)){
				//删除班级操作
				c.setClassStatus(Class.CLASS_STATUS.DELETE.ordinal());
			}else if(operation.equals(OPERATION.UPDATE)){
				//更新班级操作
				if(reqForm.getClass_name()!=null&&reqForm.getClass_name().trim()!=""){
					c.setClassName(reqForm.getClass_name());
				}
				if(reqForm.getGrade()!=0){
					c.setGrade(reqForm.getGrade());
				}
				if(reqForm.getMajor_id()!=null&&reqForm.getMajor_id().trim()!=""){
					c.setMajorID(reqForm.getMajor_id());
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
				//添加新班级
				c.setClassID(Utils.getGenerateKey());
				c.setClassName(reqForm.getClass_name());
				c.setMajorID(reqForm.getMajor_id());
				c.setGrade(reqForm.getGrade());
			}else
			{
				code=Constants.INFO_NOT_COMPLETE;
				logger.error("请求信息不完整，对象：{}，位置：{}", c, getClass().getSimpleName());
			}
		}
		return code;
	}

	@Override
	public int saveOrUpdate(Class c) {
		int code=Constants.NO_ERROR_EXIST;
		code = classDao.saveOrUpdate(c);
		if(code == Constants.NO_ERROR_EXIST){
			if(!RedisService.set("orm/class/entity/"+c.getClassID(), c))
			{
				code=Constants.REDIS_ENTITY_SAVE_ERROR;
				logger.error("Redis保存实体出错,实体{},位置{}",c ,getClass().getSimpleName());
			}else
			{
				RedisService.del("service/collections/syn/classes");
				RedisService.del("service/collections/syn/all");
			}
		}
		
		return code;
	}

	@Override
	public int getClassCollections(ClassReqForm reqForm, List<Object> respForm,Integer start,Integer end) {
		int code=Constants.NO_ERROR_EXIST;
		System.out.println(reqForm);
		@SuppressWarnings("unchecked")
		//从redis读取所有班级
		List<Class> classes=(List<Class>) RedisService.get("service/collections/syn/classes");
		//读不到去mysql中读取
		if(classes==null||classes.isEmpty())
		{
			System.out.println("没有班级");
			classes=new ArrayList<Class>();
			code=classDao.getClassCollections(classes);
			if(code == Constants.NO_ERROR_EXIST){
				RedisService.set("service/collections/syn/classes", classes);
			}
			
		}
		if(code==Constants.NO_ERROR_EXIST)
		{
			
			//实现排序
			Collections.sort(classes);
			Class c = null;
			//遍历集合
			for(Integer i = start;(i<=end||end<0)&&i<classes.size();i++){

				c = classes.get(i);
				
				//筛选内容
				boolean flag = true;
				//根据用户班级编号
				if(reqForm.getClass_id()!=null&&reqForm.getClass_id().trim()!=""){
					if(!reqForm.getClass_id().equals(c.getClassID())){
						flag = false;
					}
				}
				if(reqForm.getMajor_id()!=null&&reqForm.getMajor_id().trim()!=""){
					
					if(!reqForm.getMajor_id().equals(c.getMajorID())){
						flag = false;
					}
				}
				if(reqForm.getGrade()!=0){
					if(reqForm.getGrade()!=c.getGrade()){
						flag = false;
					}
				}
				
				if(reqForm.getClass_name()!=null&&reqForm.getClass_name().trim()!=""){
					
					if(!reqForm.getClass_name().equals(c.getClassName())){
						flag = false;
					}
				}
				
				if(Class.CLASS_STATUS.DELETE.ordinal()==c.getClassStatus()){
					flag = false;
				}
				
				if(flag){
					//将c存入respForm中
					respForm.add(new ClassRespForm(c));
					
				}
			}
		}
		return code;
	}

	@Override
	public int check(ClassReqForm reqForm, Enum<OPERATION> operation) {
		int code=Constants.NO_ERROR_EXIST;
		//数据是否完整
		if(operation.equals(Operation.OPERATION.ADD))
		{
			//添加数据时
			if(reqForm.getClass_name()==null||reqForm.getClass_name().trim()=="")
			{
				code=Constants.INFO_NOT_COMPLETE;
			}
			if(reqForm.getMajor_id()==null||reqForm.getMajor_id().trim()=="")
			{
				code=Constants.INFO_NOT_COMPLETE;
			}
			if(reqForm.getGrade()==0)
			{
				code=Constants.INFO_NOT_COMPLETE;
			}
		}
		return code;
	}

}
