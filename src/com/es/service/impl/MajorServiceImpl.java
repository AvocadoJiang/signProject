package com.es.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.es.dao.MajorDao;
import com.es.entity.Major;
import com.es.entity.Operation;
import com.es.entity.Operation.OPERATION;
import com.es.globle.Constants;
import com.es.json.form.request.MajorReqForm;
import com.es.json.form.response.MajorRespForm;
import com.es.redis.RedisService;
import com.es.service.MajorService;
import com.es.utils.Utils;

@Service("majorService")
public class MajorServiceImpl implements MajorService {
	
	@Autowired
	MajorDao majorDao;
	
	Logger logger=LoggerFactory.getLogger(getClass());
	@Override
	public int getMajor(Major major, MajorReqForm reqForm, Enum<OPERATION> operation) {
		int code = Constants.NO_ERROR_EXIST;
		//��ȡרҵ����
		code = majorDao.getMajorByID(major, reqForm.getMajor_id());
		if(code==Constants.NO_ERROR_EXIST)
		{
			//�ҵ�����Ӧ���󣬸��ݲ����Զ�������޸�
			if(operation.equals(OPERATION.DELETE)){
				//ɾ��רҵ����
				major.setMajorStatus(Major.MAJOR_STATUS.DELETE.ordinal());
			}else if(operation.equals(OPERATION.UPDATE)){
				//����רҵ����
				if(reqForm.getMajor_name()!=null&&reqForm.getMajor_name().trim()!=""){
					major.setMajorName(reqForm.getMajor_name());
				}
				//����רҵ����ѧԺ
				if(reqForm.getAcademy_id()!=null&&reqForm.getAcademy_id().trim()!=""){
					major.setAcademyID(reqForm.getAcademy_id());
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
				//�����רҵ
				major.setMajorID(Utils.getGenerateKey());
				major.setAcademyID(reqForm.getAcademy_id());
				major.setMajorName(reqForm.getMajor_name());
			}else
			{
				code=Constants.INFO_NOT_COMPLETE;
				logger.error("������Ϣ������������{}��λ�ã�{}", major, getClass().getSimpleName());
			}
		}
		return code;
	}

	@Override
	public int saveOrUpdate(Major major) {
		int code=Constants.NO_ERROR_EXIST;
		code = majorDao.saveOrUpdate(major);
		if(code == Constants.NO_ERROR_EXIST){
			if(!RedisService.set("orm/major/entity/"+major.getMajorID(), major))
			{
				code=Constants.REDIS_ENTITY_SAVE_ERROR;
				logger.error("Redis����ʵ�����,ʵ��{},λ��{}",major ,getClass().getSimpleName());
			}else
			{
				RedisService.del("service/collections/syn/majors");
				RedisService.del("service/collections/syn/all");
			}
		}
		
		return code;
	}

	@Override
	public int getMajorCollections(MajorReqForm reqForm, List<Object> respForm,Integer start,Integer end) {
		int code=Constants.NO_ERROR_EXIST;
		@SuppressWarnings("unchecked")
		//��redis��ȡ���а༶
		List<Major> majors=(List<Major>) RedisService.get("service/collections/syn/majors");
		//������ȥmysql�ж�ȡ
		if(majors==null||majors.isEmpty())
		{
			majors=new ArrayList<Major>();
			code=majorDao.getMajorCollections(majors);
			if(code == Constants.NO_ERROR_EXIST){
				RedisService.set("service/collections/syn/majors", majors);
			}
		}
		for(Major ma : majors){
			System.out.println(ma);
		}
		if(code==Constants.NO_ERROR_EXIST)
		{
			//ʵ������
			Collections.sort(majors);
			Major major = null;
			//��������
			for(Integer i = start;(i<=end||end<0)&&i<majors.size();i++){
				major = majors.get(i);
				//ɸѡ����
				boolean flag = true;
				//�����û�רҵ���
				if(reqForm.getMajor_id()!=null&&reqForm.getMajor_id().trim()!=""){
					if(!reqForm.getMajor_id().equals(major.getMajorID())){
						flag = false;
					}
				}
				if(reqForm.getAcademy_id()!=null&&reqForm.getAcademy_id().trim()!=""){
					
					if(!reqForm.getAcademy_id().equals(major.getAcademyID())){
						flag = false;
					}
				}
				if(reqForm.getMajor_name()!=null&&reqForm.getMajor_name().trim()!=""){
					if(!reqForm.getMajor_name().equals(major.getMajorName())){
						flag = false;
					}
				}
				
				if(Major.MAJOR_STATUS.DELETE.ordinal()==major.getMajorStatus()){
					flag = false;
				}
				
				if(flag){
					//��major����respForm��
					respForm.add(new MajorRespForm(major));
					
				}
			}
		}
		
		return code;
	}

	@Override
	public int check(MajorReqForm reqForm, Enum<OPERATION> operation) {
		int code=Constants.NO_ERROR_EXIST;
		//�����Ƿ�����
		if(operation.equals(Operation.OPERATION.ADD))
		{
			//רҵ��
			if(reqForm.getMajor_name()==null||reqForm.getMajor_name().trim()=="")
			{
				code=Constants.INFO_NOT_COMPLETE;
			}
			//ѧԺID
			if(reqForm.getAcademy_id()==null||reqForm.getAcademy_id().trim()=="")
			{
				code=Constants.INFO_NOT_COMPLETE;
			}
		}
		return code;
	}

}
