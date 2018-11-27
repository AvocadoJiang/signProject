package com.es.dao;

import java.util.List;

import com.es.entity.Lesson;

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
public interface LessonDao {

	/**
	 * 获取课程对象
	 * @param lesson
	 * 		待填充的课程对象
	 * @param lessonID
	 * 		待获取课程编号
	 * @return
	 * 		错误码
	 */
	public int getLessonByID(Lesson lesson, String lessonID);
	


	/**
	 * 获取的课程集合
	 * @param lessons
	 * 		待填充的课程对象集合
	 * @return
	 * 		错误码
	 */
	public int getLessonCollections(List<Lesson> lessons);

	/**
	 * 保存或更新课程对象
	 * @param lesson
	 * 		待处理的课程对象
	 * @param conn 
	 * 		批处理数据库连接对象
	 * @return
	 * 		错误码
	 */
	public int saveOrUpdate(Lesson lesson);
}
