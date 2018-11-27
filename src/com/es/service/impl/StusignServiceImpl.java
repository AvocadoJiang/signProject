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
import com.es.dao.StusignDao;
import com.es.entity.Operation;
import com.es.entity.Order;
import com.es.entity.Stusign;
import com.es.json.form.request.StusignReqForm;
import com.es.json.form.response.StusignRespForm;
import com.es.redis.RedisService;
import com.es.service.StusignService;
import com.es.utils.Utils;

@Service("stusignService")
public class StusignServiceImpl implements StusignService {
	
	@Autowired
	StusignDao stusignDao;
	
	Logger logger=LoggerFactory.getLogger(getClass());
	
	@Override
	public int getStusign(Stusign stusign, StusignReqForm reqForm, Enum<OPERATION> operation) {
		int code = Constants.NO_ERROR_EXIST;
		//获取学院对象
		code = stusignDao.getStusignByID(stusign, reqForm.getStusign_id());
		if(code==Constants.NO_ERROR_EXIST)
		{
			//找到了相应对象，根据操作对对象进行修改
			if(operation.equals(OPERATION.DELETE)){
				//删除課時操作
				stusign.setStuSignStatus(Stusign.STU_SIGN_STATUS.NOT_SIGN.name());
				
			}else if(operation.equals(OPERATION.UPDATE)){
				//更新签到状态操作
				if(reqForm.getSign_status()!=null){
					stusign.setStuSignStatus(reqForm.getSign_status());
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
				stusign.setStuSignID(Utils.getGenerateKey());
				stusign.setLessonID(reqForm.getLesson_id());
				stusign.setStudentID(reqForm.getStu_id());
				stusign.setStuSignStatus(reqForm.getSign_status());
				
			}else
			{
				code=Constants.INFO_NOT_COMPLETE;
				logger.error("请求信息不完整，对象：{}，位置：{}", stusign, getClass().getSimpleName());
			}
		}
		return code;
	}

	@Override
	public int saveOrUpdate(Stusign stusign) {
		int code=Constants.NO_ERROR_EXIST;
		code = stusignDao.saveOrUpdate(stusign);
		if(code == Constants.NO_ERROR_EXIST){
			if(!RedisService.set("orm/stusign/entity/"+stusign.getStuSignID(), stusign))
			{
				code=Constants.REDIS_ENTITY_SAVE_ERROR;
				logger.error("Redis保存实体出错,实体{},位置{}",stusign ,getClass().getSimpleName());
			}else
			{
				RedisService.del("service/collections/syn/stusigns");
				RedisService.del("service/collections/syn/all");
			}
		}
		
		return code;
	}

	@Override
	public int getStusignCollections(StusignReqForm reqForm, List<Object> respForm,Integer start,Integer end) {
		int code=Constants.NO_ERROR_EXIST;
		@SuppressWarnings("unchecked")
		//从redis读取所有课时
		List<Stusign> stusigns=(List<Stusign>) RedisService.get("service/collections/syn/stusigns");
		//读不到去mysql中读取
		if(stusigns==null||stusigns.isEmpty())
		{
			stusigns=new ArrayList<Stusign>();
			code=stusignDao.getStusignCollections(stusigns);
			if(code == Constants.NO_ERROR_EXIST){
				RedisService.set("service/collections/syn/stusigns", stusigns);
			}
			
		}
		if(code==Constants.NO_ERROR_EXIST)
		{
			
			//实现排序
			Collections.sort(stusigns);
			Stusign stusign = null;
			//遍历集合
			for(Integer i = start;(i<=end||end<0)&&i<stusigns.size();i++){
				
				stusign = stusigns.get(i);
				//筛选内容
				boolean flag = true;
				//根据用户课时编号
				if(reqForm.getStusign_id()!=null&&reqForm.getStusign_id().trim()!=""){
					if(!reqForm.getStusign_id().equals(stusign.getStuSignID())){
						flag = false;
					}
				}
				
				if(reqForm.getStu_id()!=null&&reqForm.getStu_id().trim()!=""){
					if(!reqForm.getStu_id().equals(stusign.getStudentID())){
						flag = false;
					}
				}
				
				if(reqForm.getLesson_id()!=null&&reqForm.getLesson_id().trim()!=""){
					if(!reqForm.getLesson_id().equals(stusign.getLessonID())){
						flag = false;
					}
				}
				
				if(reqForm.getSign_status()!=null&&reqForm.getSign_status().trim()!=""){
					if(!reqForm.getSign_status().equals(stusign.getStuSignStatus())){
						flag = false;
					}
				}
				
				if(flag){
					//将stusign存入respForm中
					respForm.add(new StusignRespForm(stusign));
				}
			}
		}
		return code;
	}

	@Override
	public int check(StusignReqForm reqForm, Enum<OPERATION> operation) {
		int code=Constants.NO_ERROR_EXIST;
		//数据是否完整
		if(operation.equals(Operation.OPERATION.ADD))
		{
			//添加数据时
			if(reqForm.getLesson_id()==null||reqForm.getLesson_id().trim()=="")
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
