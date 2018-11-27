package com.es.dao;

import java.util.List;

import com.es.entity.Course;

/**
 * @author Anson
 *	Copyright by EasyShare 2017
 *  
 *  All right reserved
 *
 *  Created on 2017��6��25�� ����4:21:59
 *  
 *  �γ���Ϣ���ݷ��ʽӿ�
 */
public interface CourseDao {

	/**
	 * ��ȡ�γ̶���
	 * @param course
	 * 		�����Ŀγ̶���
	 * @param courseID
	 * 		����ȡ�γ̱��
	 * @return
	 * 		������
	 */
	public int getCourseByID(Course course, String courseID);
	


	/**
	 * ��ȡ�Ŀγ̼���
	 * @param courses
	 * 		�����Ŀγ̶��󼯺�
	 * @return
	 * 		������
	 */
	public int getCourseCollections(List<Course> courses);

	/**
	 * �������¿γ̶���
	 * @param Course
	 * 		������Ŀγ̶���
	 * @return
	 * 		������
	 */
	public int saveOrUpdate(Course course);
}
