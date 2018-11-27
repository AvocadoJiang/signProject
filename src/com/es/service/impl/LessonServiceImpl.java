package com.es.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.es.dao.CourseDao;
import com.es.dao.LessonDao;
import com.es.dao.UserDao;
import com.es.entity.Course;
import com.es.entity.Lesson;
import com.es.entity.Operation;
import com.es.entity.Operation.OPERATION;
import com.es.entity.User;
import com.es.globle.Constants;
import com.es.json.form.request.LessonReqForm;
import com.es.json.form.response.LessonRespForm;
import com.es.redis.RedisService;
import com.es.utils.Utils;

@Service("lessonService")
public class LessonServiceImpl implements com.es.service.LessonService {

	@Autowired
	LessonDao lessonDao;
	@Autowired
	UserDao userDao;
	@Autowired
	CourseDao courseDao;
	
	Logger logger=LoggerFactory.getLogger(getClass());
	
	@Override
	public int getLesson(Lesson lesson, LessonReqForm reqForm, Enum<OPERATION> operation) {
		int code = Constants.NO_ERROR_EXIST;
		//��ȡѧԺ����
		code = lessonDao.getLessonByID(lesson, reqForm.getLesson_id());
		if(code==Constants.NO_ERROR_EXIST)
		{
			//�ҵ�����Ӧ���󣬸��ݲ����Զ�������޸�
			if(operation.equals(OPERATION.DELETE)){
				//ɾ���n�r����
				lesson.setLessonSignStatus(Lesson.LESSON_SIGN_STATUS.SIGN_CANCELED.name());
			}else if(operation.equals(OPERATION.UPDATE)){
				//�����n�r����
				if(reqForm.getStart_time()!=null){
					lesson.setLessonStartTime(reqForm.getStart_time());
				}
				if(reqForm.getEnd_time()!=null){
					lesson.setLessonEndTime(reqForm.getEnd_time());
				}
				if(reqForm.getLesson_status()!=null){
					lesson.setLessonSignStatus(reqForm.getLesson_status());
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
				lesson.setLessonID(Utils.getGenerateKey());
				lesson.setCourseID(reqForm.getCourse_id());
				lesson.setLessonStartTime(reqForm.getStart_time());
				lesson.setLessonEndTime(reqForm.getEnd_time());
				lesson.setLessonSignStatus(reqForm.getLesson_status());
				
			}else
			{
				code=Constants.INFO_NOT_COMPLETE;
				logger.error("������Ϣ������������{}��λ�ã�{}", lesson, getClass().getSimpleName());
			}
		}
		return code;
	}

	@Override
	public int saveOrUpdate(Lesson lesson) {
		int code=Constants.NO_ERROR_EXIST;
		code = lessonDao.saveOrUpdate(lesson);
		if(code == Constants.NO_ERROR_EXIST){
			if(!RedisService.set("orm/lesson/entity/"+lesson.getLessonID(), lesson))
			{
				code=Constants.REDIS_ENTITY_SAVE_ERROR;
				logger.error("Redis����ʵ�����,ʵ��{},λ��{}",lesson ,getClass().getSimpleName());
			}else
			{
				RedisService.del("service/collections/syn/lessons");
				RedisService.del("service/collections/syn/all");
			}
		}
		
		return code;
	}

	@Override
	public int getLessonCollections(LessonReqForm reqForm, List<Object> respForm,Integer start,Integer end) {
		int code=Constants.NO_ERROR_EXIST;
	
		@SuppressWarnings("unchecked")
		//��redis��ȡ���п�ʱ
		List<Lesson> lessons=(List<Lesson>) RedisService.get("service/collections/syn/lessons");
		//������ȥmysql�ж�ȡ
		if(lessons==null||lessons.isEmpty())
		{
			lessons=new ArrayList<Lesson>();
			code=lessonDao.getLessonCollections(lessons);
			if(code == Constants.NO_ERROR_EXIST){
				RedisService.set("service/collections/syn/lessons", lessons);
			}
			
		}
		if(code==Constants.NO_ERROR_EXIST)
		{
			
			
			//ʵ������
			Collections.sort(lessons);
			Lesson lesson = null;
			//��������
			
			for(Integer i = start;(i<=end||end<0)&&i<lessons.size();i++){
				
				lesson = lessons.get(i);
				
				//ɸѡ����
				boolean flag = true;
				//�����û���ʱ���
				if(reqForm.getLesson_id()!=null&&reqForm.getLesson_id().trim()!=""){
					if(!reqForm.getLesson_id().equals(lesson.getLessonID())){
						flag = false;
					}
				}
				
				if(reqForm.getCourse_id()!=null&&reqForm.getCourse_id().trim()!=""){
					if(!reqForm.getCourse_id().equals(lesson.getCourseID())){
						flag = false;
					}
				}
				
				if(reqForm.getLesson_status()!=null&&reqForm.getLesson_status().trim()!=""){
					if(!reqForm.getLesson_status().equals(lesson.getLessonSignStatus())){
						flag = false;
					}
				}
				if(reqForm.getStart_time()!=null){
					if(reqForm.getStart_time().getTime()>lesson.getLessonStartTime().getTime()){
						flag = false;
					}
				}
				if(reqForm.getEnd_time()!=null){
					if(reqForm.getEnd_time().getTime()<lesson.getLessonEndTime().getTime()){
						flag = false;
					}
				}
				System.out.println(flag);
				
				//��user����respForm��
				LessonRespForm lessonRespForm = new LessonRespForm(lesson);
				
				Course course = new Course();
				courseDao.getCourseByID(course,lessonRespForm.getCourse_id());
				lessonRespForm.setCourse_name(course.getCourseName());
	
				User user = new User();
				userDao.getUserByID(user,course.getTeacherID());
				
				lessonRespForm.setTeacher_id(user.getUserID());
				lessonRespForm.setTeacher_name(user.getName());
				if(reqForm.getTeacher_id()!=null&&reqForm.getTeacher_id().trim()!=""){
					if(!lessonRespForm.getTeacher_id().equals(reqForm.getTeacher_id())){
						flag = false;
					}
				}
			
				
				if(flag){
					respForm.add(lessonRespForm);
				}
			}
		}
		return code;
	}

	@Override
	public int check(LessonReqForm reqForm, Enum<OPERATION> operation) {
		int code=Constants.NO_ERROR_EXIST;
		//�����Ƿ�����
		if(operation.equals(Operation.OPERATION.ADD))
		{
			//�������ʱ
			if(reqForm.getCourse_id()==null||reqForm.getCourse_id().trim()=="")
			{
				code=Constants.INFO_NOT_COMPLETE;
			}
			if(reqForm.getStart_time()==null)
			{
				code=Constants.INFO_NOT_COMPLETE;
			}
			if(reqForm.getEnd_time()==null)
			{
				code=Constants.INFO_NOT_COMPLETE;
			}
		}
		return code;
	}


}
