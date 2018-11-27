package com.es.service.impl;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.es.dao.UserDao;
import com.es.entity.Operation.OPERATION;
import com.es.entity.Operation;
import com.es.entity.Order;
import com.es.entity.User;
import com.es.globle.Constants;
import com.es.json.form.request.SigninForm;
import com.es.redis.RedisService;
import com.es.json.form.request.UserReqForm;
import com.es.json.form.response.UserRespForm;
import com.es.service.UserService;
import com.es.utils.Utils;
import com.mchange.net.SocketUtils;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	UserDao userDao;
	
	Logger logger=LoggerFactory.getLogger(getClass());

	@Override
	public int checkLoginInfo(User user, SigninForm signinForm) {
		int code=Constants.NO_ERROR_EXIST;
		code = userDao.getUserByID(user, signinForm.getUser_id());
		if(code==Constants.NO_ERROR_EXIST)
		{
			System.out.println(signinForm.getUser_psd());
			if(!user.getPassword().equals(signinForm.getUser_psd()))
			{
				
				//ֱ����ʾ�������
				code=Constants.USER_LOGIN_PWD_ERROR;
			}
			
		}else
		{
			//ֱ����ʾ�û������������
			code=Constants.USERNAME_OR_PASSWORD_NOT_EXIST;
		}
		return code;
	}

	@Override
	public int check(UserReqForm reqForm, Enum<OPERATION> operation) {
		int code=Constants.NO_ERROR_EXIST;
		//�����Ƿ�����
		if(operation.equals(Operation.OPERATION.ADD))
		{
			//�������ʱ
			if(reqForm.getUser_id()==null||reqForm.getUser_id().trim()=="")
			{
				code=Constants.INFO_NOT_COMPLETE;
			}
			
			if((reqForm.getIdentity()==null||reqForm.getIdentity().trim()==""))
			{
				code=Constants.INFO_NOT_COMPLETE;
			}else{
				if(!reqForm.getIdentity().equals(User.USER_IDENTITY.ADMIN.name())
						&&!reqForm.getIdentity().equals(User.USER_IDENTITY.TEACHER.name())
						&&!reqForm.getIdentity().equals(User.USER_IDENTITY.STUDENT.name())
						&&!reqForm.getIdentity().equals(User.USER_IDENTITY.ROOT.name()))
				{
					code = Constants.IDENTITY_ERROR;
				}
				if(reqForm.getIdentity().equals(User.USER_IDENTITY.ADMIN.name())
						&&(reqForm.getSchool_id()==null||reqForm.getSchool_id().trim()=="")
						&&(reqForm.getAcademy_id()==null||reqForm.getAcademy_id().trim()==""))
				{
					code=Constants.INFO_NOT_COMPLETE;
				}
				
				if(reqForm.getIdentity().equals(User.USER_IDENTITY.TEACHER.name())
						&&(reqForm.getSchool_id()==null||reqForm.getSchool_id().trim()=="")
						&&(reqForm.getAcademy_id()==null||reqForm.getAcademy_id().trim()=="")
						&&(reqForm.getMajor_id()==null||reqForm.getMajor_id().trim()=="")
						)
				{
					code=Constants.INFO_NOT_COMPLETE;
				}
				if(reqForm.getIdentity().equals(User.USER_IDENTITY.STUDENT.name())
						&&(reqForm.getSchool_id()==null||reqForm.getSchool_id().trim()=="")
						&&(reqForm.getAcademy_id()==null||reqForm.getAcademy_id().trim()=="")
						&&(reqForm.getMajor_id()==null||reqForm.getMajor_id().trim()=="")
						&&(reqForm.getClass_id()==null||reqForm.getClass_id().trim()==""))
				{
					code=Constants.INFO_NOT_COMPLETE;
				}
			}
			
				
			
			
		}
		/*
		 * ���ų���֤����
		 * //�����Ƿ���ȷ���ɿղ���֤
		if(!Utils.isEmail(registerForm.getEmail()))
		{
			code=Constants.EMAIL_PATTERN_NOT_CORRECT;
		}
		//�ֻ����Ƿ���ȷ����֤�򲻿ɿ�
		if(!Utils.isPhone(registerForm.getPhone()))
		{
			code=Constants.PHONE_PATTERN_NOT_CORRECT;
		}
		 * 
		 */
		return code;
	}

	@Override
	public int getUser(User user, UserReqForm reqForm, Enum<OPERATION> operation) {
		int code = Constants.NO_ERROR_EXIST;
		//��ȡ�û�����
		code = userDao.getUserByID(user, reqForm.getUser_id());
		if(code == Constants.NO_ERROR_EXIST){
			//�ҵ�����Ӧ���󣬸��ݲ����Զ�������޸�
			if(operation.equals(OPERATION.DELETE)){
				//ɾ���û�����
				user.setUserStatus(User.USER_ACCOUNT_STATUS.DELETE.ordinal());
			}else if(operation.equals(OPERATION.UPDATE)){
				//�����û�����
				if(reqForm.getUser_psd()!=null&&reqForm.getUser_psd().trim()!=""){
					user.setPassword(reqForm.getUser_psd());
				}
				if(reqForm.getSchool_id()!=null&&reqForm.getSchool_id().trim()!=""){
					user.setSchoolID(reqForm.getSchool_id());
				}
				if(reqForm.getAcademy_id()!=null&&reqForm.getAcademy_id().trim()!=""){
					user.setAcademyID(reqForm.getAcademy_id());
				}
				if(reqForm.getMajor_id()!=null&&reqForm.getMajor_id().trim()!=""){
					user.setMajorID(reqForm.getMajor_id());
				}
				if(reqForm.getClass_id()!=null&&reqForm.getClass_id().trim()!=""){
					user.setClassID(reqForm.getClass_id());
				}
				if(reqForm.getUser_name()!=null&&reqForm.getUser_name().trim()!=""){
					user.setName(reqForm.getUser_name());
				}
				if(reqForm.getUser_phone()!=null&&reqForm.getUser_phone().trim()!=""){
					user.setPhone(reqForm.getUser_phone());
				}
				if(reqForm.getOpen_id()!=null&&reqForm.getOpen_id().trim()!=""){
					user.setOpenID(reqForm.getOpen_id());
				}
				if(reqForm.getLocation_x()!=0){
					user.setLocationX(reqForm.getLocation_x());
				}
				if(reqForm.getLocation_y()!=0){
					user.setLocationY(reqForm.getLocation_y());
				}
			}
		}else{
			//���ݿ�û���ҵ����û������
			code=Constants.NO_ERROR_EXIST;
			//����ύ����Ϣ�Ƿ�Ϸ�
			code = check(reqForm, operation);
			if(code==Constants.NO_ERROR_EXIST)
			{
				//������û�
				user.setUserID(reqForm.getUser_id());
				user.setSchoolID(reqForm.getSchool_id());
				user.setAcademyID(reqForm.getAcademy_id());
				user.setMajorID(reqForm.getMajor_id());
				user.setClassID(reqForm.getClass_id());
				user.setName(reqForm.getUser_name());
				user.setPhone(reqForm.getUser_phone());
				user.setIdentity(reqForm.getIdentity());
				user.setOpenID(reqForm.getOpen_id());
				user.setCreateTime(Utils.stringToExactDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())));
				user.setLocationX(0);
				user.setLocationY(0);
				
				if(reqForm.getUser_psd()==null||reqForm.getUser_psd().trim()==""){
					//����Ĭ��Ϊѧ����
					user.setPassword(reqForm.getUser_id());
				}else{
					user.setPassword(reqForm.getUser_psd());
				}
				
				
			}else
			{
				code=Constants.INFO_NOT_COMPLETE;
				logger.error("������Ϣ������������{}��λ�ã�{}", user, getClass().getSimpleName());
			}
		}
		return code;
	}

	@Override
	public int saveOrUpdate(User user) {
		int code=Constants.NO_ERROR_EXIST;
		code = userDao.saveOrUpdate(user);
		if(code == Constants.NO_ERROR_EXIST){
			if(!RedisService.set("orm/user/entity/"+user.getUserID(), user))
			{
				code=Constants.REDIS_ENTITY_SAVE_ERROR;
				logger.error("Redis����ʵ�����,ʵ��{},λ��{}",user ,getClass().getSimpleName());
			}else
			{
				RedisService.del("service/collections/syn/users");
				RedisService.del("service/collections/syn/all");
			}
		}
		
		return code;
	}

	@Override
	public int getUserCollections(UserReqForm reqForm, List<Object> respForm,Integer start,Integer end) {
		int code=Constants.NO_ERROR_EXIST;
		@SuppressWarnings("unchecked")
		//��redis��ȡ�����û�
		List<User> users=(List<User>) RedisService.get("service/collections/syn/users");
		//������ȥmysql�ж�ȡ
		if(users==null||users.isEmpty())
		{
			users=new ArrayList<User>();
			code=userDao.getUserCollections(users);
		
			if(code == Constants.NO_ERROR_EXIST){
				RedisService.set("service/collections/syn/users", users);
			}
			
		}
	
		if(code==Constants.NO_ERROR_EXIST)
		{
			
			
			//ʵ������
			Collections.sort(users);
			User user = null;
			//��������
			for(Integer i = start;(i<=end||end<0)&&i<users.size();i++){
				
				user = users.get(i);
				//ɸѡ����
				boolean flag = true;
				//����΢��openIDɸѡ
				if(reqForm.getOpen_id()!=null&&reqForm.getOpen_id().trim()!=""){
					if(!reqForm.getOpen_id().equals(user.getOpenID())){
						flag = false;
					}
				}
				//�����û�ID
				if(reqForm.getUser_id()!=null&&reqForm.getUser_id().trim()!=""){
					if(!reqForm.getUser_id().equals(user.getUserID())){
						flag = false;
					}
				}
				
				//����ѧУID
				if(reqForm.getSchool_id()!=null&&reqForm.getSchool_id().trim()!=""){
					if(!reqForm.getSchool_id().equals(user.getSchoolID())){
						flag = false;
					}
				}
				
				//����ѧԺID
				if(reqForm.getAcademy_id()!=null&&reqForm.getAcademy_id().trim()!=""){
					if(!reqForm.getAcademy_id().equals(user.getAcademyID())){
						flag = false;
					}
				}
				//����רҵID
				if(reqForm.getMajor_id()!=null&&reqForm.getMajor_id().trim()!=""){
					if(!reqForm.getMajor_id().equals(user.getMajorID())){
						flag = false;
					}
				}
				
				//���ݰ༶ID
				if(reqForm.getClass_id()!=null&&reqForm.getClass_id().trim()!=""){
					
					if(!reqForm.getClass_id().equals(user.getClassID())){
						flag = false;
					}
				}
				
				//��������
				if(reqForm.getUser_name()!=null&&reqForm.getUser_name().trim()!=""){
					if(!reqForm.getUser_name().equals(user.getName())){
						flag = false;
					}
				}
				
				//�������
				if(reqForm.getIdentity()!=null&&reqForm.getIdentity().trim()!=""){
					if(!User.getUserTypeEnum(reqForm.getIdentity()).name().equals(user.getIdentity())){
						flag = false;
					}
				}
				
				//ɸѡ��ɾ�����û�
				if(user.getUserStatus()==User.USER_ACCOUNT_STATUS.DELETE.ordinal()){
					flag = false;
				}
	
				if(flag){
					//��user����respForm��
					respForm.add(new UserRespForm(user));
				}
			}
		}
		return code;
	}




}
