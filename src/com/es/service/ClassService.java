package com.es.service;

import com.es.json.form.request.ClassReqForm;

import java.util.List;
import com.es.entity.Class;
import com.es.entity.Operation.OPERATION;

/**
 * @author Anson
 *	Copyright by EasyShare 2017
 *  
 *  All right reserved
 *
 *  Created on 2017��6��25�� ����4:40:36
 *  
 *  �༶��Ϣ����ӿ�
 */
public interface ClassService {

	/**
	 * ��ȡ�༶����
	 * @param c
	 * 		�����İ༶����
	 * @param reqForm
	 * 		�༶�������
	 * @param operation
	 * 		��������
	 * @return
	 * 		������
	 */
	public int getClass(Class c, ClassReqForm reqForm, Enum<OPERATION> operation);
	
	/**
	 * ����������ʱ��Ϣ��������
	 * @param reqForm
	 * 		����ͼ������ע����
	 * @param operation
	 * 		��������
	 * @return
	 * 		������
	 */
	public int check(ClassReqForm reqForm, Enum<OPERATION> operation);

	/**
	 * ���桢�޸Ļ�ɾ���༶
	 * @param c
	 * 		������İ༶����
	 * @return
	 * 		������
	 */
	public int saveOrUpdate(Class c);

	/**
	 * ��ȡ�༶����
	 * @param reqForm
	 * 		����ȡ�İ༶��Ϣ�������
	 * @param objects
	 * 		�����İ༶���󼯺�
	 * @return
	 * 		������
	 */
	public int getClassCollections(ClassReqForm reqForm, List<Object> respForm,Integer start,Integer end);
	
	
}
