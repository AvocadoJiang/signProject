package com.es.quartz;
import java.util.List;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.es.dao.AcademyDao;
import com.es.dao.CourseDao;
import com.es.dao.LessonDao;
import com.es.dao.OrderDao;
import com.es.dao.SchoolDao;
import com.es.dao.StusignDao;
import com.es.dao.UserDao;
import com.es.entity.Academy;
import com.es.entity.Course;
import com.es.entity.Lesson;
import com.es.entity.Order;
import com.es.entity.School;
import com.es.entity.Stusign;
import com.es.entity.User;
import com.es.redis.RedisService;

/**
 * @author Jason
 *	Copyright by EasyShare 2018
 *  
 *  All right reserved
 *
 *  Created on 2018��3��3�� ����10:34:57
 *  ����������д�����ݿ���
 */
public class DBWriter extends QuartzJobBean {

	@Autowired
	AcademyDao academyDao;
	@Autowired
	SchoolDao schoolDao;
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
	
	public DBWriter() {
		super();
	}
	
	@Override
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
		/**
		//��������ѧУ��Ϣ
		this.saveOrUpdateSchool();
		//��������ѧԺ��Ϣ
		this.saveOrUpdateAcademy();
		
		//�������¿γ���Ϣ
		
		this.saveOrUpdateCourse();
		//�������¿�ʱ��Ϣ
		
		this.saveOrUpdateLesson();
		
		//�������¿�ʱ��Ϣ
		
		this.saveOrUpdateOrder();
		//�������¿�ʱ��Ϣ
		
		this.saveOrUpdateStusign();
		//�������¿�ʱ��Ϣ
		
		this.saveOrUpdateUser();
		**/
		
	}
	
	/**
	 * ������߸���ѧУ��Ϣ
	 */
	private void saveOrUpdateSchool()
	{
		List<Object> objects=null;
		objects=RedisService.getbat(new String("orm/school/entity/"));
		if(objects!=null&&!objects.isEmpty())
		{
			for(Object object:objects)
			{
				schoolDao.saveOrUpdate((School) object);
			}
		}
	}
	
	/**
	 * ������߸���ѧԺ��Ϣ
	 */
	private void saveOrUpdateAcademy()
	{
		List<Object> objects=null;
		objects=RedisService.getbat(new String("orm/academy/entity/"));
		if(objects!=null&&!objects.isEmpty())
		{
			for(Object object:objects)
			{
				academyDao.saveOrUpdate((Academy) object);
			}
		}
	}
	
	/**
	 * ������߸��¿γ���Ϣ
	 */
	private void saveOrUpdateCourse()
	{
		List<Object> objects=null;
		objects=RedisService.getbat(new String("orm/course/entity/"));
		if(objects!=null&&!objects.isEmpty())
		{
			for(Object object:objects)
			{
				courseDao.saveOrUpdate((Course) object);
			}
		}
	}
	
	/**
	 * ������߸��¿�ʱ��Ϣ
	 */
	private void saveOrUpdateLesson()
	{
		List<Object> objects=null;
		objects=RedisService.getbat(new String("orm/lesson/entity/"));
		if(objects!=null&&!objects.isEmpty())
		{
			for(Object object:objects)
			{
				lessonDao.saveOrUpdate((Lesson) object);
			}
		}
	}
	
	/**
	 * ������߸���ѡ����Ϣ
	 */
	private void saveOrUpdateOrder()
	{
		List<Object> objects=null;
		objects=RedisService.getbat(new String("orm/order/entity/"));
		if(objects!=null&&!objects.isEmpty())
		{
			for(Object object:objects)
			{
				orderDao.saveOrUpdate((Order) object);
			}
		}
	}
	
	/**
	 * ������߸���ѧ��ǩ����Ϣ
	 */
	private void saveOrUpdateStusign()
	{
		List<Object> objects=null;
		objects=RedisService.getbat(new String("orm/stusign/entity/"));
		if(objects!=null&&!objects.isEmpty())
		{
			for(Object object:objects)
			{
				stusignDao.saveOrUpdate((Stusign) object);
			}
		}
	}
	
	/**
	 * ������߸���ѧ��ǩ����Ϣ
	 */
	private void saveOrUpdateUser()
	{
		List<Object> objects=null;
		objects=RedisService.getbat(new String("orm/user/entity/"));
		if(objects!=null&&!objects.isEmpty())
		{
			for(Object object:objects)
			{
				userDao.saveOrUpdate((User) object);
			}
		}
	}
	
	
}
