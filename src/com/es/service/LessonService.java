package com.es.service;

import com.es.json.form.request.LessonReqForm;
import java.util.List;
import com.es.entity.Lesson;
import com.es.entity.Operation.OPERATION;

/**
 * @author Anson
 *	Copyright by EasyShare 2017
 *  
 *  All right reserved
 *
 *  Created on 2017��6��25�� ����4:40:36
 *  
 *  ��ʱ��Ϣ����ӿ�
 */
public interface LessonService {

	/**
	 * ��ȡ��ʱ����
	 * @param lesson
	 * 		�����Ŀ�ʱ����
	 * @param reqForm
	 * 		��ʱ�������
	 * @param operation
	 * 		��������
	 * @return
	 * 		������
	 */
	public int getLesson(Lesson lesson, LessonReqForm reqForm, Enum<OPERATION> operation);
	
	/**
	 * ����������ʱ��Ϣ��������
	 * @param reqForm
	 * 		����ͼ������ע����
	 * @param operation
	 * 		��������
	 * @return
	 * 		������
	 */
	public int check(LessonReqForm reqForm, Enum<OPERATION> operation);

	/**
	 * ���桢�޸Ļ�ɾ����ʱ
	 * @param lesson
	 * 		������Ŀ�ʱ����
	 * @return
	 * 		������
	 */
	public int saveOrUpdate(Lesson lesson);

	/**
	 * ��ȡ��ʱ����
	 * @param reqForm
	 * 		����ȡ�Ŀ�ʱ��Ϣ�������
	 * @param objects
	 * 		�����Ŀ�ʱ���󼯺�
	 * @return
	 * 		������
	 */
	public int getLessonCollections(LessonReqForm reqForm, List<Object> respForm,Integer start,Integer end);
	
	
}
