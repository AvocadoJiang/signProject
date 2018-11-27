package com.es.redis;


import com.es.entity.Academy;
import com.es.entity.Class;
import com.es.entity.Course;
import com.es.entity.Lesson;
import com.es.entity.Major;
import com.es.entity.Order;
import com.es.entity.School;
import com.es.entity.Stusign;
import com.es.entity.User;

/**
 * @author Anson<br>
 *	Copyright by EasyShare 2018<br>
 *  
 *  All right reserved<br>
 *
 *  Created on 2018年1月22日 下午5:45:04<br>
 *  
 *  打包缓存信息<br>
 */
public class CachePackager {
	/**
	 * 打包User缓存对象
	 * @param user
	 * 		待填充的user对象
	 * @param object
	 * 		获取的缓存对象
	 */
	public static void packUserCache(User user, Object object)
	{
		//User
		user.setUserID(((User)object).getUserID());
		user.setPassword(((User)object).getPassword());
		user.setSchoolID(((User)object).getSchoolID());
		user.setAcademyID(((User)object).getAcademyID());
		user.setMajorID(((User)object).getMajorID());
		user.setClassID(((User)object).getClassID());
		user.setName(((User)object).getName());
		user.setPhone(((User)object).getPhone());
		user.setIdentity(((User)object).getIdentity());
		user.setOpenID(((User)object).getOpenID());
		user.setCreateTime(((User)object).getCreateTime());
		user.setLocationX(((User)object).getLocationX());
		user.setLocationY(((User)object).getLocationY());
		user.setUserStatus(((User)object).getUserStatus());
	}
	
	/**
	 * 打包School缓存对象
	 * @param school
	 * 		待填充的school对象
	 * @param object
	 * 		获取的缓存对象
	 */
	public static void packSchoolCache(School school, Object object)
	{
		//School
		school.setSchoolID(((School)object).getSchoolID());
		school.setSchoolName(((School)object).getSchoolName());
		school.setSchoolStatus(((School)object).getSchoolStatus());
	}
	
	/**
	 * 打包Academy缓存对象
	 * @param academy
	 * 		待填充的academy对象
	 * @param object
	 * 		获取的缓存对象
	 */
	public static void packAcademyCache(Academy academy, Object object)
	{
		//Academy
		academy.setAcademyID(((Academy)object).getAcademyID());
		academy.setSchoolID(((Academy)object).getSchoolID());
		academy.setAcademyName(((Academy)object).getAcademyName());
		academy.setAcademyStatus(((Academy)object).getAcademyStatus());
		
	}
	
	
	/**
	 * 打包Class缓存对象
	 * @param c
	 * 		待填充的c对象
	 * @param object
	 * 		获取的缓存对象
	 */
	public static void packClassCache(Class c, Object object)
	{
		//class
		c.setMajorID(((Class)object).getMajorID());
		c.setGrade(((Class)object).getGrade());
		c.setClassID(((Class)object).getClassID());
		c.setClassName(((Class)object).getClassName());
		c.setClassStatus(((Class)object).getClassStatus());
		
	}
	
	/**
	 * 打包Major缓存对象
	 * @param major
	 * 		待填充的major对象
	 * @param object
	 * 		获取的缓存对象
	 */
	public static void packMajorCache(Major major, Object object)
	{
		//major
		major.setMajorID(((Major)object).getMajorID());
		major.setAcademyID(((Major)object).getAcademyID());
		major.setMajorName(((Major)object).getMajorName());
		major.setMajorStatus(((Major)object).getMajorStatus());
		
	}
	
	/**
	 * 打包Course缓存对象
	 * @param course
	 * 		待填充的course对象
	 * @param object
	 * 		获取的缓存对象
	 */ 
	public static void packCourseCache(Course course, Object object)
	{
		//Course
		course.setCourseID(((Course)object).getCourseID());
		course.setAcademyID(((Course)object).getAcademyID());
		course.setCourseName(((Course)object).getCourseName());
		course.setTeacherID(((Course)object).getTeacherID());
		course.setCreateTime(((Course)object).getCreateTime());
		course.setCourseStatus(((Course)object).getCourseStatus());
	}
	
	/**
	 * 打包Lesson缓存对象
	 * @param lesson
	 * 		待填充的lesson对象
	 * @param object
	 * 		获取的缓存对象
	 */ 
	public static void packLessonCache(Lesson lesson, Object object)
	{
		//Lesson
		lesson.setLessonID(((Lesson)object).getLessonID());
		lesson.setCourseID(((Lesson)object).getCourseID());
		lesson.setLessonStartTime(((Lesson)object).getLessonStartTime());
		lesson.setLessonEndTime(((Lesson)object).getLessonEndTime());
		lesson.setLessonSignStatus(((Lesson)object).getLessonSignStatus());
	}
	
	/**
	 * 打包Order缓存对象
	 * @param order
	 * 		待填充的order对象
	 * @param object
	 * 		获取的缓存对象
	 */ 
	public static void packOrderCache(Order order, Object object)
	{
		//Order
		order.setOrderID(((Order)object).getOrderID());
		order.setCourseID(((Order)object).getCourseID());
		order.setStudentID(((Order)object).getStudentID());
		order.setOrderStatus(((Order)object).getOrderStatus());
	}
	
	/**
	 * 打包Stusign缓存对象
	 * @param stusign
	 * 		待填充的stusign对象
	 * @param stusign
	 * 		获取的缓存对象
	 */ 
	public static void packStusignCache(Stusign stusign, Object object)
	{
		//Stusign
		stusign.setStuSignID(((Stusign)object).getStuSignID());
		stusign.setLessonID(((Stusign)object).getLessonID());
		stusign.setStudentID(((Stusign)object).getStudentID());
		stusign.setStuSignStatus(((Stusign)object).getStuSignStatus());
	}

}
