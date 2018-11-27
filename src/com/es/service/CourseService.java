package com.es.service;

import com.es.json.form.request.CourseReqForm;
import java.util.List;
import com.es.entity.Course;
import com.es.entity.Operation.OPERATION;

/**
 * @author Anson
 *	Copyright by EasyShare 2017
 *  
 *  All right reserved
 *
 *  Created on 2017��6��25�� ����4:40:36
 *  
 *  ѧԺ��Ϣ����ӿ�
 */
public interface CourseService {

	/**
	 * ��ȡѧԺ����
	 * @param course
	 * 		������ѧԺ����
	 * @param reqForm
	 * 		ѧԺ�������
	 * @param operation
	 * 		��������
	 * @return
	 * 		������
	 */
	public int getCourse(Course course, CourseReqForm reqForm, Enum<OPERATION> operation);
	
	/**
	 * ����������ʱ��Ϣ��������
	 * @param reqForm
	 * 		����ͼ������ע����
	 * @param operation
	 * 		��������
	 * @return
	 * 		������
	 */
	public int check(CourseReqForm reqForm, Enum<OPERATION> operation);

	/**
	 * ���桢�޸Ļ�ɾ��ѧԺ
	 * @param course
	 * 		�������ѧԺ����
	 * @return
	 * 		������
	 */
	public int saveOrUpdate(Course course);

	/**
	 * ��ȡѧԺ����
	 * @param reqForm
	 * 		����ȡ��ѧԺ��Ϣ�������
	 * @param objects
	 * 		������ѧԺ���󼯺�
	 * @return
	 * 		������
	 */
	public int getCourseCollections(CourseReqForm reqForm, List<Object> respForm,Integer start,Integer end);
	
	
}
