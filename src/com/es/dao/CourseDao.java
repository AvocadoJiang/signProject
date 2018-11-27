package com.es.dao;

import java.util.List;

import com.es.entity.Course;

/**
 * @author Anson
 *	Copyright by EasyShare 2017
 *  
 *  All right reserved
 *
 *  Created on 2017年6月25日 下午4:21:59
 *  
 *  课程信息数据访问接口
 */
public interface CourseDao {

	/**
	 * 获取课程对象
	 * @param course
	 * 		待填充的课程对象
	 * @param courseID
	 * 		待获取课程编号
	 * @return
	 * 		错误码
	 */
	public int getCourseByID(Course course, String courseID);
	


	/**
	 * 获取的课程集合
	 * @param courses
	 * 		待填充的课程对象集合
	 * @return
	 * 		错误码
	 */
	public int getCourseCollections(List<Course> courses);

	/**
	 * 保存或更新课程对象
	 * @param Course
	 * 		待处理的课程对象
	 * @return
	 * 		错误码
	 */
	public int saveOrUpdate(Course course);
}
