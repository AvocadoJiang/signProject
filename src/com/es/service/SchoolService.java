package com.es.service;

import com.es.json.form.request.SchoolReqForm;
import java.util.List;
import com.es.entity.Operation.OPERATION;
import com.es.entity.School;

/**
 * @author Anson
 *	Copyright by EasyShare 2017
 *  
 *  All right reserved
 *
 *  Created on 2017��6��25�� ����4:40:36
 *  
 *  �û���Ϣ����ӿ�
 */
public interface SchoolService {

	/**
	 * ��ȡѧУ����
	 * @param school
	 * 		������ѧУ����
	 * @param reqForm
	 * 		ѧУ�������
	 * @param operation
	 * 		��������
	 * @return
	 * 		������
	 */
	public int getSchool(School school, SchoolReqForm reqForm, Enum<OPERATION> operation);
	
	/**
	 * ����������ʱ��Ϣ��������
	 * @param registerForm
	 * 		����ͼ������ע����
	 * @param operation
	 * 		��������
	 * @return
	 * 		������
	 */
	public int check(SchoolReqForm reqForm, Enum<OPERATION> operation);

	/**
	 * ���桢�޸Ļ�ɾ��ѧУ
	 * @param school
	 * 		�������ѧУ����
	 * @return
	 * 		������
	 */
	public int saveOrUpdate(School school);

	/**
	 * ��ȡѧУ����
	 * @param reqForm
	 * 		����ȡ��ѧУ��Ϣ�������
	 * @param objects
	 * 		������ѧУ���󼯺�
	 * @return
	 * 		������
	 */
	public int getSchoolCollections(SchoolReqForm reqForm, List<Object> respForm,Integer start,Integer end);
	
	
}
