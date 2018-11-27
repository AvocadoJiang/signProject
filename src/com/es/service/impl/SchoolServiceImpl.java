package com.es.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.es.dao.SchoolDao;
import com.es.entity.Operation.OPERATION;
import com.es.globle.Constants;
import com.es.entity.Operation;
import com.es.entity.Order;
import com.es.entity.School;
import com.es.json.form.request.SchoolReqForm;
import com.es.json.form.response.SchoolRespForm;
import com.es.redis.RedisService;
import com.es.service.SchoolService;
import com.es.utils.Utils;

@Service("schoolService")
public class SchoolServiceImpl implements SchoolService {
	
	@Autowired
	SchoolDao schoolDao;
	
	Logger logger=LoggerFactory.getLogger(getClass());
	@Override
	public int getSchool(School school, SchoolReqForm reqForm, Enum<OPERATION> operation) {
		int code = Constants.NO_ERROR_EXIST;
		//��ȡѧУ����
		code = schoolDao.getSchoolByID(school, reqForm.getSchool_id());
		
		if(code == Constants.NO_ERROR_EXIST){
			//�ҵ�����Ӧ���󣬸��ݲ�����������޸�
			if(operation.equals(OPERATION.DELETE)){
				school.setSchoolStatus(School.SCHOOL_STATUS.DELETE.ordinal());	
			}else if(operation.equals(OPERATION.UPDATE)){
				//�����û�����
				if(reqForm.getSchool_name()!=null&&reqForm.getSchool_name().trim()!=""){
					school.setSchoolName(reqForm.getSchool_name());
				}
			}
		}else{
			//���ݿ�û���ҵ����û������
			code=Constants.NO_ERROR_EXIST;
			code = check(reqForm, operation);
			if(code==Constants.NO_ERROR_EXIST){
				school.setSchoolID(Utils.getGenerateKey());
				school.setSchoolName(reqForm.getSchool_name());
			}else
			{
				code=Constants.INFO_NOT_COMPLETE;
				logger.error("������Ϣ������������{}��λ�ã�{}", school, getClass().getSimpleName());
			}
		}
		return code;
	}
	
	@Override
	public int check(SchoolReqForm reqForm, Enum<OPERATION> operation) {
		int code=Constants.NO_ERROR_EXIST;
		//�����Ƿ�����
		if(operation.equals(Operation.OPERATION.ADD))
		{
			//�������ʱ
			if(reqForm.getSchool_name()==null||reqForm.getSchool_name().trim()=="")
			{
				code=Constants.INFO_NOT_COMPLETE;
			}
		}
		return code;
	}

	@Override
	public int saveOrUpdate(School school) {
		int code=Constants.NO_ERROR_EXIST;
		code = schoolDao.saveOrUpdate(school);
		if(code == Constants.NO_ERROR_EXIST){
			if(!RedisService.set("orm/school/entity/"+school.getSchoolID(), school))
			{
				code=Constants.REDIS_ENTITY_SAVE_ERROR;
				logger.error("Redis����ʵ�����,ʵ��{},λ��{}",school ,getClass().getSimpleName());
			}else
			{
				RedisService.del("service/collections/syn/schools");
				RedisService.del("service/collections/syn/all");
			}
		}
		
		return code;
	}

	@Override
	public int getSchoolCollections(SchoolReqForm reqForm, List<Object> respForm,Integer start,Integer end) {
		int code=Constants.NO_ERROR_EXIST;
		
		@SuppressWarnings("unchecked")
		//��redis��ȡ����ѧУ
		List<School> schools=(List<School>) RedisService.get("service/collections/syn/schools");
		//������ȥmysql�ж�ȡ
		if(schools==null||schools.isEmpty())
		{
			schools=new ArrayList<School>();
			code=schoolDao.getSchoolCollections(schools);
			if(code == Constants.NO_ERROR_EXIST){
				RedisService.set("service/collections/syn/schools", schools);
			}
		}
		if(code==Constants.NO_ERROR_EXIST)
		{
			
			//ʵ������
			Collections.sort(schools);
			School school = null;
			//��������
			for(Integer i = start;(i<=end||end<0)&&i<schools.size();i++){
				school = schools.get(i);
				//ɸѡ����
				boolean flag = true;
				//����ѧУIDɸѡ
				if(reqForm.getSchool_id()!=null&&reqForm.getSchool_id().trim()!=""){
					if(!reqForm.getSchool_id().equals(school.getSchoolID())){
						flag = false;
					}
				}
				if(reqForm.getSchool_name()!=null&&reqForm.getSchool_name().trim()!=""){
					if(!reqForm.getSchool_name().equals(school.getSchoolName())){
						flag = false;
					}
				}
				if(School.SCHOOL_STATUS.DELETE.ordinal()==school.getSchoolStatus()){
					flag = false;
				}
				
				if(flag){
					respForm.add(new SchoolRespForm(school));
				}
			}
		}
		return code;
	}

	
}
