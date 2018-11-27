package com.es.dao;

import java.util.List;

import com.es.entity.Lesson;

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
public interface LessonDao {

	/**
	 * ��ȡ�γ̶���
	 * @param lesson
	 * 		�����Ŀγ̶���
	 * @param lessonID
	 * 		����ȡ�γ̱��
	 * @return
	 * 		������
	 */
	public int getLessonByID(Lesson lesson, String lessonID);
	


	/**
	 * ��ȡ�Ŀγ̼���
	 * @param lessons
	 * 		�����Ŀγ̶��󼯺�
	 * @return
	 * 		������
	 */
	public int getLessonCollections(List<Lesson> lessons);

	/**
	 * �������¿γ̶���
	 * @param lesson
	 * 		������Ŀγ̶���
	 * @param conn 
	 * 		���������ݿ����Ӷ���
	 * @return
	 * 		������
	 */
	public int saveOrUpdate(Lesson lesson);
}
