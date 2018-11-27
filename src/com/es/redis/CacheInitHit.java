package com.es.redis;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import com.es.dao.AcademyDao;
import com.es.dao.ClassDao;
import com.es.dao.CourseDao;
import com.es.dao.LessonDao;
import com.es.dao.MajorDao;
import com.es.dao.OrderDao;
import com.es.dao.SchoolDao;
import com.es.dao.StusignDao;
import com.es.dao.UserDao;
import com.es.entity.Academy;
import com.es.entity.Course;
import com.es.entity.Lesson;
import com.es.entity.Major;
import com.es.entity.Order;
import com.es.entity.Class;
import com.es.entity.School;
import com.es.entity.Stusign;
import com.es.entity.User;
import com.es.globle.Constants;


/**
 * @author Jason
 *	Copyright by EasyShare 2018
 *  
 *  All right reserved
 *
 *  Created on 2018��3��3�� ����10:34:57
 *  �����ݿ��е�����д�뻺��
 */
@Service
public class CacheInitHit implements ApplicationListener<ContextRefreshedEvent> {
	
	@Autowired
	AcademyDao academyDao;
	@Autowired
	SchoolDao schoolDao;
	@Autowired
	ClassDao classDao;
	@Autowired
	MajorDao majorDao;
	@Autowired
	CourseDao courseDao;
	@Autowired
	LessonDao lessonDao;
	@Autowired
	OrderDao orderDao;
	@Autowired
	StusignDao stusignDao;
	@Autowired
	UserDao userDao;

	Logger logger=LoggerFactory.getLogger(getClass());
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		//������л���
		RedisService.clear(true);
		//���¼��ػ���
		if(event.getApplicationContext().getParent()!=null)
		{
			
			//��ȡ���ݿ���school������
			getSchoolCollectionsToCache();
			//��ȡ���ݿ���academy������
			getAcademyCollectionsToCache();
			
			//��ȡ���ݿ���class������
			getClassCollectionsToCache();
			//��ȡ���ݿ���major������
			getMajorCollectionsToCache();
			//��ȡ���ݿ���course������
			getCourseCollectionsToCache();
			//��ȡ���ݿ���lesson������
			getLessonCollectionsToCache();
			//��ȡ���ݿ���order������
			getOrderCollectionsToCache();
			//��ȡ���ݿ���stusign������
			getStusignCollectionsToCache();
			//��ȡ���ݿ���user������
			getUserCollectionsToCache();
			
		}
	}
	
	

	private void getSchoolCollectionsToCache() {
		int code=Constants.NO_ERROR_EXIST;
		boolean flag=false;
		List<School> schools=new ArrayList<School>();
		code=schoolDao.getSchoolCollections(schools);
		if(code==Constants.NO_ERROR_EXIST)
		{
			for(School school:schools)
			{
				if(code==Constants.NO_ERROR_EXIST)
				{
					if(!RedisService.set("orm/school/entity/"+school.getSchoolID(), school))
					{
						flag=true;
						code=Constants.DB_GET_ERROR;
					}
				}else
				{
					break;
				}
			}
			if(!flag)
			{
				logger.info(">>>>>>>>>>>>>>>>>>����{}װ�سɹ�,��������{} <<<<<<<<<<<<<<<<<<<<<<<<<", School.class.getName(), schools.size());
			}
		}else
		{
			logger.error("װ��{}�������ݳ���λ�ã�{}", School.class.getName(), this.getClass().getName());
		}
	}
	
	private void getAcademyCollectionsToCache() {
		int code=Constants.NO_ERROR_EXIST;
		boolean flag=false;
		List<Academy> academies=new ArrayList<Academy>();
		code=academyDao.getAcademyCollections(academies);
		if(code==Constants.NO_ERROR_EXIST)
		{
			for(Academy academy:academies)
			{
				if(code==Constants.NO_ERROR_EXIST)
				{
					if(!RedisService.set("orm/academy/entity/"+academy.getAcademyID(), academy))
					{
						flag=true;
						code=Constants.DB_GET_ERROR;
					}
				}else
				{
					break;
				}
			}
			if(!flag)
			{
				logger.info(">>>>>>>>>>>>>>>>>>����{}װ�سɹ�,��������{} <<<<<<<<<<<<<<<<<<<<<<<<<", Academy.class.getName(), academies.size());
			}
		}else
		{
			logger.error("װ��{}�������ݳ���λ�ã�{}", Academy.class.getName(), this.getClass().getName());
		}
	}
	
	
	private void getClassCollectionsToCache() {
		int code=Constants.NO_ERROR_EXIST;
		boolean flag=false;
		List<Class> classes=new ArrayList<Class>();
		code=classDao.getClassCollections(classes);
		if(code==Constants.NO_ERROR_EXIST)
		{
			for(Class c:classes)
			{
				if(code==Constants.NO_ERROR_EXIST)
				{
					if(!RedisService.set("orm/class/entity/"+c.getClassID(), c))
					{
						flag=true;
						code=Constants.DB_GET_ERROR;
					}
				}else
				{
					break;
				}
			}
			if(!flag)
			{
				logger.info(">>>>>>>>>>>>>>>>>>����{}װ�سɹ�,��������{} <<<<<<<<<<<<<<<<<<<<<<<<<", Class.class.getName(), classes.size());
			}
		}else
		{
			logger.error("װ��{}�������ݳ���λ�ã�{}", Class.class.getName(), this.getClass().getName());
		}
	}
	

	private void getMajorCollectionsToCache() {
		int code=Constants.NO_ERROR_EXIST;
		boolean flag=false;
		List<Major> majors=new ArrayList<Major>();
		code=majorDao.getMajorCollections(majors);
		if(code==Constants.NO_ERROR_EXIST)
		{
			for(Major major:majors)
			{
				if(code==Constants.NO_ERROR_EXIST)
				{
					if(!RedisService.set("orm/major/entity/"+major.getMajorID(), major))
					{
						flag=true;
						code=Constants.DB_GET_ERROR;
					}
				}else
				{
					break;
				}
			}
			if(!flag)
			{
				logger.info(">>>>>>>>>>>>>>>>>>����{}װ�سɹ�,��������{} <<<<<<<<<<<<<<<<<<<<<<<<<", Major.class.getName(), majors.size());
			}
		}else
		{
			logger.error("װ��{}�������ݳ���λ�ã�{}", Major.class.getName(), this.getClass().getName());
		}
	}
	
	private void getCourseCollectionsToCache() {
		int code=Constants.NO_ERROR_EXIST;
		boolean flag=false;
		List<Course> courses=new ArrayList<Course>();
		code=courseDao.getCourseCollections(courses);
		if(code==Constants.NO_ERROR_EXIST)
		{
			for(Course course:courses)
			{
				if(code==Constants.NO_ERROR_EXIST)
				{
					if(!RedisService.set("orm/course/entity/"+course.getCourseID(), course))
					{
						flag=true;
						code=Constants.DB_GET_ERROR;
					}
				}else
				{
					break;
				}
			}
			if(!flag)
			{
				logger.info(">>>>>>>>>>>>>>>>>>����{}װ�سɹ�,��������{} <<<<<<<<<<<<<<<<<<<<<<<<<", Course.class.getName(), courses.size());
			}
		}else
		{
			logger.error("װ��{}�������ݳ���λ�ã�{}", Course.class.getName(), this.getClass().getName());
		}
	}
	
	private void getLessonCollectionsToCache() {
		int code=Constants.NO_ERROR_EXIST;
		boolean flag=false;
		List<Lesson> lessons=new ArrayList<Lesson>();
		code=lessonDao.getLessonCollections(lessons);
		if(code==Constants.NO_ERROR_EXIST)
		{
			for(Lesson lesson:lessons)
			{
				if(code==Constants.NO_ERROR_EXIST)
				{
					if(!RedisService.set("orm/lesson/entity/"+lesson.getLessonID(), lesson))
					{
						flag=true;
						code=Constants.DB_GET_ERROR;
					}
				}else
				{
					break;
				}
			}
			if(!flag)
			{
				logger.info(">>>>>>>>>>>>>>>>>>����{}װ�سɹ�,��������{} <<<<<<<<<<<<<<<<<<<<<<<<<", Lesson.class.getName(), lessons.size());
			}
		}else
		{
			logger.error("װ��{}�������ݳ���λ�ã�{}", Lesson.class.getName(), this.getClass().getName());
		}
	}

	private void getOrderCollectionsToCache() {
		int code=Constants.NO_ERROR_EXIST;
		boolean flag=false;
		List<Order> orders=new ArrayList<Order>();
		code=orderDao.getOrderCollections(orders);
		if(code==Constants.NO_ERROR_EXIST)
		{
			for(Order order:orders)
			{
				if(code==Constants.NO_ERROR_EXIST)
				{
					if(!RedisService.set("orm/order/entity/"+order.getOrderID(), order))
					{
						flag=true;
						code=Constants.DB_GET_ERROR;
					}
				}else
				{
					break;
				}
			}
			if(!flag)
			{
				logger.info(">>>>>>>>>>>>>>>>>>����{}װ�سɹ�,��������{} <<<<<<<<<<<<<<<<<<<<<<<<<", Order.class.getName(), orders.size());
			}
		}else
		{
			logger.error("װ��{}�������ݳ���λ�ã�{}", Order.class.getName(), this.getClass().getName());
		}
	}
	
	private void getStusignCollectionsToCache() {
		int code=Constants.NO_ERROR_EXIST;
		boolean flag=false;
		List<Stusign> stusigns=new ArrayList<Stusign>();
		code=stusignDao.getStusignCollections(stusigns);
		if(code==Constants.NO_ERROR_EXIST)
		{
			for(Stusign stusign:stusigns)
			{
				if(code==Constants.NO_ERROR_EXIST)
				{
					if(!RedisService.set("orm/stusign/entity/"+stusign.getStuSignID(), stusign))
					{
						flag=true;
						code=Constants.DB_GET_ERROR;
					}
				}else
				{
					break;
				}
			}
			if(!flag)
			{
				logger.info(">>>>>>>>>>>>>>>>>>����{}װ�سɹ�,��������{} <<<<<<<<<<<<<<<<<<<<<<<<<", Stusign.class.getName(), stusigns.size());
			}
		}else
		{
			logger.error("װ��{}�������ݳ���λ�ã�{}", Stusign.class.getName(), this.getClass().getName());
		}
	}
	
	private void getUserCollectionsToCache() {
		int code=Constants.NO_ERROR_EXIST;
		boolean flag=false;
		List<User> users=new ArrayList<User>();
		code=userDao.getUserCollections(users);
		if(code==Constants.NO_ERROR_EXIST)
		{
			for(User user:users)
			{
				if(code==Constants.NO_ERROR_EXIST)
				{
					if(!RedisService.set("orm/user/entity/"+user.getUserID(), user))
					{
						flag=true;
						code=Constants.DB_GET_ERROR;
					}
				}else
				{
					break;
				}
			}
			if(!flag)
			{
				logger.info(">>>>>>>>>>>>>>>>>>����{}װ�سɹ�,��������{} <<<<<<<<<<<<<<<<<<<<<<<<<", User.class.getName(), users.size());
			}
		}else
		{
			logger.error("װ��{}�������ݳ���λ�ã�{}", User.class.getName(), this.getClass().getName());
		}
	}
}
