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
		//��ȡѧԺ����
		code = academyDao.getAcademyByID(academy, reqForm.getAcademy_id());
		if(code==Constants.NO_ERROR_EXIST)
		{
			//�ҵ�����Ӧ���󣬸��ݲ����Զ�������޸�
			if(operation.equals(OPERATION.DELETE)){
				//ɾ��ѧԺ����
				academy.setAcademyStatus(Academy.ACADEMY_STATUS.DELETE.ordinal());
			}else if(operation.equals(OPERATION.UPDATE)){
				//����ѧԺ����
				if(reqForm.getAcademy_name()!=null&&reqForm.getAcademy_name().trim()!=""){
					academy.setAcademyName(reqForm.getAcademy_name());
				}
				if(reqForm.getSchool_id()!=null&&reqForm.getSchool_id().trim()!=""){
					academy.setSchoolID(reqForm.getSchool_id());
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
				//�����ѧԺ
				academy.setAcademyID(Utils.getGenerateKey());
				academy.setSchoolID(reqForm.getSchool_id());
				academy.setAcademyName(reqForm.getAcademy_name());
			}else
			{
				code=Constants.INFO_NOT_COMPLETE;
				logger.error("������Ϣ������������{}��λ�ã�{}", academy, getClass().getSimpleName());
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
				logger.error("Redis����ʵ�����,ʵ��{},λ��{}",academy ,getClass().getSimpleName());
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
		//��redis��ȡ����ѧԺ
		List<Academy> academys=(List<Academy>) RedisService.get("service/collections/syn/academys");
		//������ȥmysql�ж�ȡ
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
			//ʵ������
			Collections.sort(academys);
			Academy academy = null;
			//��������
			for(Integer i = start;(i<=end||end<0)&&i<academys.size();i++){
				
				academy = academys.get(i);
				//ɸѡ����
				boolean flag = true;
				//�����û�ѧԺ���
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
					//��user����respForm��
					
					respForm.add(new AcademyRespForm(academy));
					
				}
			}
		}
		return code;
	}

	@Override
	public int check(AcademyReqForm reqForm, Enum<OPERATION> operation) {
		int code=Constants.NO_ERROR_EXIST;
		//�����Ƿ�����
		if(operation.equals(Operation.OPERATION.ADD))
		{
			//�������ʱ
			if(reqForm.getAcademy_name()==null||reqForm.getAcademy_name().trim()=="")
			{
				code=Constants.INFO_NOT_COMPLETE;
			}
			//�������ʱ
			if(reqForm.getSchool_id()==null||reqForm.getSchool_id().trim()=="")
			{
				code=Constants.INFO_NOT_COMPLETE;
			}
		}
		return code;
	}

}
