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
 *  Created on 2018年3月3日 下午10:34:57
 *  将数据库中的数据写入缓存
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
		//清除所有缓存
		RedisService.clear(true);
		//重新加载缓存
		if(event.getApplicationContext().getParent()!=null)
		{
			
			//获取数据库中school表数据
			getSchoolCollectionsToCache();
			//获取数据库中academy表数据
			getAcademyCollectionsToCache();
			
			//获取数据库中class表数据
			getClassCollectionsToCache();
			//获取数据库中major表数据
			getMajorCollectionsToCache();
			//获取数据库中course表数据
			getCourseCollectionsToCache();
			//获取数据库中lesson表数据
			getLessonCollectionsToCache();
			//获取数据库中order表数据
			getOrderCollectionsToCache();
			//获取数据库中stusign表数据
			getStusignCollectionsToCache();
			//获取数据库中user表数据
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
				logger.info(">>>>>>>>>>>>>>>>>>对象{}装载成功,数据量：{} <<<<<<<<<<<<<<<<<<<<<<<<<", School.class.getName(), schools.size());
			}
		}else
		{
			logger.error("装载{}对象数据出错，位置：{}", School.class.getName(), this.getClass().getName());
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
				logger.info(">>>>>>>>>>>>>>>>>>对象{}装载成功,数据量：{} <<<<<<<<<<<<<<<<<<<<<<<<<", Academy.class.getName(), academies.size());
			}
		}else
		{
			logger.error("装载{}对象数据出错，位置：{}", Academy.class.getName(), this.getClass().getName());
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
				logger.info(">>>>>>>>>>>>>>>>>>对象{}装载成功,数据量：{} <<<<<<<<<<<<<<<<<<<<<<<<<", Class.class.getName(), classes.size());
			}
		}else
		{
			logger.error("装载{}对象数据出错，位置：{}", Class.class.getName(), this.getClass().getName());
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
				logger.info(">>>>>>>>>>>>>>>>>>对象{}装载成功,数据量：{} <<<<<<<<<<<<<<<<<<<<<<<<<", Major.class.getName(), majors.size());
			}
		}else
		{
			logger.error("装载{}对象数据出错，位置：{}", Major.class.getName(), this.getClass().getName());
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
				logger.info(">>>>>>>>>>>>>>>>>>对象{}装载成功,数据量：{} <<<<<<<<<<<<<<<<<<<<<<<<<", Course.class.getName(), courses.size());
			}
		}else
		{
			logger.error("装载{}对象数据出错，位置：{}", Course.class.getName(), this.getClass().getName());
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
				logger.info(">>>>>>>>>>>>>>>>>>对象{}装载成功,数据量：{} <<<<<<<<<<<<<<<<<<<<<<<<<", Lesson.class.getName(), lessons.size());
			}
		}else
		{
			logger.error("装载{}对象数据出错，位置：{}", Lesson.class.getName(), this.getClass().getName());
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
				logger.info(">>>>>>>>>>>>>>>>>>对象{}装载成功,数据量：{} <<<<<<<<<<<<<<<<<<<<<<<<<", Order.class.getName(), orders.size());
			}
		}else
		{
			logger.error("装载{}对象数据出错，位置：{}", Order.class.getName(), this.getClass().getName());
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
				logger.info(">>>>>>>>>>>>>>>>>>对象{}装载成功,数据量：{} <<<<<<<<<<<<<<<<<<<<<<<<<", Stusign.class.getName(), stusigns.size());
			}
		}else
		{
			logger.error("装载{}对象数据出错，位置：{}", Stusign.class.getName(), this.getClass().getName());
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
				logger.info(">>>>>>>>>>>>>>>>>>对象{}装载成功,数据量：{} <<<<<<<<<<<<<<<<<<<<<<<<<", User.class.getName(), users.size());
			}
		}else
		{
			logger.error("装载{}对象数据出错，位置：{}", User.class.getName(), this.getClass().getName());
		}
	}
}
