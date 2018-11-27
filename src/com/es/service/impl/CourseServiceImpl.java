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

import com.es.dao.CourseDao;
import com.es.dao.UserDao;
import com.es.entity.Academy;
import com.es.entity.Course;
import com.es.entity.Operation;
import com.es.entity.Operation.OPERATION;
import com.es.entity.User;
import com.es.globle.Constants;
import com.es.json.form.request.CourseReqForm;
import com.es.json.form.response.CourseRespForm;
import com.es.redis.RedisService;
import com.es.service.CourseService;
import com.es.utils.Utils;

@Service("courseService")
public class CourseServiceImpl implements CourseService {

	@Autowired
	CourseDao courseDao;
	@Autowired
	UserDao userDao;
	
	Logger logger=LoggerFactory.getLogger(getClass());
	@Override
	public int getCourse(Course course, CourseReqForm reqForm, Enum<OPERATION> operation) {
		int code = Constants.NO_ERROR_EXIST;
		//获取课程对象
		code = courseDao.getCourseByID(course, reqForm.getCourse_id());
		if(code==Constants.NO_ERROR_EXIST)
		{
			//找到了相应对象，根据操作对对象进行修改
			if(operation.equals(OPERATION.DELETE)){
				//删除学院操作
				course.setCourseStatus(Course.COURSE_STATUS.DELETE.ordinal());
			}else if(operation.equals(OPERATION.UPDATE)){
				//更新课程操作
				if(reqForm.getTeacher_id()!=null&&reqForm.getTeacher_id().trim()!=""){
					course.setTeacherID(reqForm.getTeacher_id());
				}
				if(reqForm.getCourse_name()!=null&&reqForm.getCourse_name().trim()!=""){
					course.setCourseName(reqForm.getCourse_name());
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
				//添加新课程
				course.setCourseID(Utils.getGenerateKey());
				course.setTeacherID(reqForm.getTeacher_id());
				course.setAcademyID(reqForm.getAcademy_id());
				course.setCourseName(reqForm.getCourse_name());
				course.setCreateTime(Utils.stringToExactDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())));
				
			}else
			{
				code=Constants.INFO_NOT_COMPLETE;
				logger.error("请求信息不完整，对象：{}，位置：{}", course, getClass().getSimpleName());
			}
		}
		return code;
	}

	@Override
	public int saveOrUpdate(Course course) {
		int code=Constants.NO_ERROR_EXIST;
		code = courseDao.saveOrUpdate(course);
		if(code == Constants.NO_ERROR_EXIST){
			if(!RedisService.set("orm/course/entity/"+course.getCourseID(), course))
			{
				code=Constants.REDIS_ENTITY_SAVE_ERROR;
				logger.error("Redis保存实体出错,实体{},位置{}",course ,getClass().getSimpleName());
			}else
			{
				RedisService.del("service/collections/syn/courses");
				RedisService.del("service/collections/syn/all");
			}
		}
		
		return code;
	}

	@Override
	public int getCourseCollections(CourseReqForm reqForm, List<Object> respForm,Integer start,Integer end) {
		int code=Constants.NO_ERROR_EXIST;
		@SuppressWarnings("unchecked")
		//从redis读取所有课程
		List<Course> courses=(List<Course>) RedisService.get("service/collections/syn/courses");
		//读不到去mysql中读取
		if(courses==null||courses.isEmpty())
		{
			courses=new ArrayList<Course>();
			code=courseDao.getCourseCollections(courses);
			if(code == Constants.NO_ERROR_EXIST){
				RedisService.set("service/collections/syn/courses", courses);
			}
			
		}
		if(code==Constants.NO_ERROR_EXIST)
		{
			
			
			//实现排序
			Collections.sort(courses);
			Course course = null;
			//遍历集合
			for(Integer i = start;(i<=end||end<0)&&i<courses.size();i++){
				
				course = courses.get(i);
				//筛选内容
				boolean flag = true;
				//根据课程编号
				if(reqForm.getCourse_id()!=null&&reqForm.getCourse_id().trim()!=""){
					if(!reqForm.getCourse_id().equals(course.getCourseID())){
						flag = false;
					}
				}
				
				//根据教師编号
				if(reqForm.getTeacher_id()!=null&&reqForm.getTeacher_id().trim()!=""){
					if(!reqForm.getTeacher_id().equals(course.getTeacherID())){
						flag = false;
					}
				}
				
				//根据學院编号
				if(reqForm.getAcademy_id()!=null&&reqForm.getAcademy_id().trim()!=""){
					if(!reqForm.getAcademy_id().equals(course.getAcademyID())){
						flag = false;
					}
				}
				if(reqForm.getCourse_name()!=null&&reqForm.getCourse_name().trim()!=""){
					if(!reqForm.getCourse_name().equals(course.getCourseName())){
						flag = false;
					}
				}
				
				if(Course.COURSE_STATUS.DELETE.ordinal()==course.getCourseStatus()){
					flag = false;
				}
				
				if(flag){
					//将course存入respForm中
					User user = new User();
					userDao.getUserByID(user, course.getTeacherID());
					CourseRespForm courseResp = new CourseRespForm(course);
					courseResp.setTeacher_name(user.getName());
					respForm.add(courseResp);
				}
			}
		}
		return code;
	}

	@Override
	public int check(CourseReqForm reqForm, Enum<OPERATION> operation) {
		int code=Constants.NO_ERROR_EXIST;
		//数据是否完整
		if(operation.equals(Operation.OPERATION.ADD))
		{
			//添加数据时
			if(reqForm.getAcademy_id()==null||reqForm.getAcademy_id().trim()=="")
			{
				code=Constants.INFO_NOT_COMPLETE;
			}
			if(reqForm.getCourse_name()==null||reqForm.getCourse_name().trim()=="")
			{
				code=Constants.INFO_NOT_COMPLETE;
			}
			if(reqForm.getTeacher_id()==null||reqForm.getTeacher_id().trim()=="")
			{
				code=Constants.INFO_NOT_COMPLETE;
			}
		}
		return code;
	}


}
