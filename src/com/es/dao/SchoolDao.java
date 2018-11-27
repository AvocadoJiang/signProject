package com.es.dao;

import java.util.List;

import com.es.entity.School;

/**
 * @author Anson
 *	Copyright by EasyShare 2017
 *  
 *  All right reserved
 *
 *  Created on 2017��6��25�� ����4:21:59
 *  
 *  ѧУ��Ϣ���ݷ��ʽӿ�
 */
public interface SchoolDao {

	/**
	 * ��ȡѧУ����
	 * @param school
	 * 		������ѧУ����
	 * @param schoolID
	 * 		����ȡѧУ���
	 * @return
	 * 		������
	 */
	public int getSchoolByID(School school, String schoolID);
	


	/**
	 * ��ȡ��ѧУ����
	 * @param schools
	 * 		������ѧУ���󼯺�
	 * @return
	 * 		������
	 */
	public int getSchoolCollections(List<School> schools);

	/**
	 * ��������ѧУ����
	 * @param school
	 * 		�������ѧУ����
	 * @return
	 * 		������
	 */
	public int saveOrUpdate(School school);
}
