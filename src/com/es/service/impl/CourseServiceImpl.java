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
		//��ȡ�γ̶���
		code = courseDao.getCourseByID(course, reqForm.getCourse_id());
		if(code==Constants.NO_ERROR_EXIST)
		{
			//�ҵ�����Ӧ���󣬸��ݲ����Զ�������޸�
			if(operation.equals(OPERATION.DELETE)){
				//ɾ��ѧԺ����
				course.setCourseStatus(Course.COURSE_STATUS.DELETE.ordinal());
			}else if(operation.equals(OPERATION.UPDATE)){
				//���¿γ̲���
				if(reqForm.getTeacher_id()!=null&&reqForm.getTeacher_id().trim()!=""){
					course.setTeacherID(reqForm.getTeacher_id());
				}
				if(reqForm.getCourse_name()!=null&&reqForm.getCourse_name().trim()!=""){
					course.setCourseName(reqForm.getCourse_name());
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
				//����¿γ�
				course.setCourseID(Utils.getGenerateKey());
				course.setTeacherID(reqForm.getTeacher_id());
				course.setAcademyID(reqForm.getAcademy_id());
				course.setCourseName(reqForm.getCourse_name());
				course.setCreateTime(Utils.stringToExactDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())));
				
			}else
			{
				code=Constants.INFO_NOT_COMPLETE;
				logger.error("������Ϣ������������{}��λ�ã�{}", course, getClass().getSimpleName());
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
				logger.error("Redis����ʵ�����,ʵ��{},λ��{}",course ,getClass().getSimpleName());
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
		//��redis��ȡ���пγ�
		List<Course> courses=(List<Course>) RedisService.get("service/collections/syn/courses");
		//������ȥmysql�ж�ȡ
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
			
			
			//ʵ������
			Collections.sort(courses);
			Course course = null;
			//��������
			for(Integer i = start;(i<=end||end<0)&&i<courses.size();i++){
				
				course = courses.get(i);
				//ɸѡ����
				boolean flag = true;
				//���ݿγ̱��
				if(reqForm.getCourse_id()!=null&&reqForm.getCourse_id().trim()!=""){
					if(!reqForm.getCourse_id().equals(course.getCourseID())){
						flag = false;
					}
				}
				
				//���ݽ̎����
				if(reqForm.getTeacher_id()!=null&&reqForm.getTeacher_id().trim()!=""){
					if(!reqForm.getTeacher_id().equals(course.getTeacherID())){
						flag = false;
					}
				}
				
				//���݌WԺ���
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
					//��course����respForm��
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
		//�����Ƿ�����
		if(operation.equals(Operation.OPERATION.ADD))
		{
			//�������ʱ
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
