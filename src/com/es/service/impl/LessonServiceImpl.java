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
		//获取学院对象
		code = lessonDao.getLessonByID(lesson, reqForm.getLesson_id());
		if(code==Constants.NO_ERROR_EXIST)
		{
			//找到了相应对象，根据操作对对象进行修改
			if(operation.equals(OPERATION.DELETE)){
				//删除課時操作
				lesson.setLessonSignStatus(Lesson.LESSON_SIGN_STATUS.SIGN_CANCELED.name());
			}else if(operation.equals(OPERATION.UPDATE)){
				//更新課時操作
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
			//数据库没有找到该用户的情况
			code=Constants.NO_ERROR_EXIST;
			//检查提交的信息是否合法
			code = check(reqForm, operation);
			if(code==Constants.NO_ERROR_EXIST)
			{
				//添加新课时
				lesson.setLessonID(Utils.getGenerateKey());
				lesson.setCourseID(reqForm.getCourse_id());
				lesson.setLessonStartTime(reqForm.getStart_time());
				lesson.setLessonEndTime(reqForm.getEnd_time());
				lesson.setLessonSignStatus(reqForm.getLesson_status());
				
			}else
			{
				code=Constants.INFO_NOT_COMPLETE;
				logger.error("请求信息不完整，对象：{}，位置：{}", lesson, getClass().getSimpleName());
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
				logger.error("Redis保存实体出错,实体{},位置{}",lesson ,getClass().getSimpleName());
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
		//从redis读取所有课时
		List<Lesson> lessons=(List<Lesson>) RedisService.get("service/collections/syn/lessons");
		//读不到去mysql中读取
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
			
			
			//实现排序
			Collections.sort(lessons);
			Lesson lesson = null;
			//遍历集合
			
			for(Integer i = start;(i<=end||end<0)&&i<lessons.size();i++){
				
				lesson = lessons.get(i);
				
				//筛选内容
				boolean flag = true;
				//根据用户课时编号
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
				
				//将user存入respForm中
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
		//数据是否完整
		if(operation.equals(Operation.OPERATION.ADD))
		{
			//添加数据时
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
