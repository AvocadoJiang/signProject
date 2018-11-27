package com.es.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.es.dao.AcademyDao;
import com.es.entity.Academy;
import com.es.entity.Operation;
import com.es.entity.Operation.OPERATION;
import com.es.globle.Constants;
import com.es.json.form.request.AcademyReqForm;
import com.es.json.form.response.AcademyRespForm;
import com.es.redis.RedisService;
import com.es.service.AcademyService;
import com.es.utils.Utils;

@Service("academyService")
public class AcademyServiceImpl implements AcademyService {
	
	@Autowired
	AcademyDao academyDao;
	
	Logger logger=LoggerFactory.getLogger(getClass());
	@Override
	public int getAcademy(Academy academy, AcademyReqForm reqForm, Enum<OPERATION> operation) {
		int code = Constants.NO_ERROR_EXIST;
		//获取学院对象
		code = academyDao.getAcademyByID(academy, reqForm.getAcademy_id());
		if(code==Constants.NO_ERROR_EXIST)
		{
			//找到了相应对象，根据操作对对象进行修改
			if(operation.equals(OPERATION.DELETE)){
				//删除学院操作
				academy.setAcademyStatus(Academy.ACADEMY_STATUS.DELETE.ordinal());
			}else if(operation.equals(OPERATION.UPDATE)){
				//更新学院操作
				if(reqForm.getAcademy_name()!=null&&reqForm.getAcademy_name().trim()!=""){
					academy.setAcademyName(reqForm.getAcademy_name());
				}
				if(reqForm.getSchool_id()!=null&&reqForm.getSchool_id().trim()!=""){
					academy.setSchoolID(reqForm.getSchool_id());
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
				//添加新学院
				academy.setAcademyID(Utils.getGenerateKey());
				academy.setSchoolID(reqForm.getSchool_id());
				academy.setAcademyName(reqForm.getAcademy_name());
			}else
			{
				code=Constants.INFO_NOT_COMPLETE;
				logger.error("请求信息不完整，对象：{}，位置：{}", academy, getClass().getSimpleName());
			}
		}
		return code;
	}

	@Override
	public int saveOrUpdate(Academy academy) {
		int code=Constants.NO_ERROR_EXIST;
		code = academyDao.saveOrUpdate(academy);
		if(code == Constants.NO_ERROR_EXIST){
			if(!RedisService.set("orm/academy/entity/"+academy.getAcademyID(), academy))
			{
				code=Constants.REDIS_ENTITY_SAVE_ERROR;
				logger.error("Redis保存实体出错,实体{},位置{}",academy ,getClass().getSimpleName());
			}else
			{
				RedisService.del("service/collections/syn/academys");
				RedisService.del("service/collections/syn/all");
			}
		}
		
		return code;
	}

	@Override
	public int getAcademyCollections(AcademyReqForm reqForm, List<Object> respForm,Integer start,Integer end) {
		int code=Constants.NO_ERROR_EXIST;
		@SuppressWarnings("unchecked")
		//从redis读取所有学院
		List<Academy> academys=(List<Academy>) RedisService.get("service/collections/syn/academys");
		//读不到去mysql中读取
		if(academys==null||academys.isEmpty())
		{
			academys=new ArrayList<Academy>();
			code=academyDao.getAcademyCollections(academys);
			if(code == Constants.NO_ERROR_EXIST){
				RedisService.set("service/collections/syn/academys", academys);
			}
			
		}
		if(code==Constants.NO_ERROR_EXIST)
		{
			//实现排序
			Collections.sort(academys);
			Academy academy = null;
			//遍历集合
			for(Integer i = start;(i<=end||end<0)&&i<academys.size();i++){
				
				academy = academys.get(i);
				//筛选内容
				boolean flag = true;
				//根据用户学院编号
				if(reqForm.getAcademy_id()!=null&&reqForm.getAcademy_id().trim()!=""){
					if(!reqForm.getAcademy_id().equals(academy.getAcademyID())){
						flag = false;
					}
				}
				if(reqForm.getSchool_id()!=null&&reqForm.getSchool_id().trim()!=""){
					if(!reqForm.getSchool_id().equals(academy.getSchoolID())){
						flag = false;
					}
				}
				if(reqForm.getAcademy_name()!=null&&reqForm.getAcademy_name().trim()!=""){
					if(!reqForm.getAcademy_name().equals(academy.getAcademyName())){
						flag = false;
					}
				}
				if(Academy.ACADEMY_STATUS.DELETE.ordinal()==academy.getAcademyStatus()){
					flag = false;
				}
				
				if(flag){
					//将user存入respForm中
					
					respForm.add(new AcademyRespForm(academy));
					
				}
			}
		}
		return code;
	}

	@Override
	public int check(AcademyReqForm reqForm, Enum<OPERATION> operation) {
		int code=Constants.NO_ERROR_EXIST;
		//数据是否完整
		if(operation.equals(Operation.OPERATION.ADD))
		{
			//添加数据时
			if(reqForm.getAcademy_name()==null||reqForm.getAcademy_name().trim()=="")
			{
				code=Constants.INFO_NOT_COMPLETE;
			}
			//添加数据时
			if(reqForm.getSchool_id()==null||reqForm.getSchool_id().trim()=="")
			{
				code=Constants.INFO_NOT_COMPLETE;
			}
		}
		return code;
	}

}
