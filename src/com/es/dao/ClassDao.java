package com.es.dao;

import java.util.List;

import com.es.entity.Class;

/**
 * @author Anson
 *	Copyright by EasyShare 2017
 *  
 *  All right reserved
 *
 *  Created on 2017��6��25�� ����4:21:59
 *  
 *  ѧԺ��Ϣ���ݷ��ʽӿ�
 */
public interface ClassDao {

	/**
	 * ��ȡ�༶����
	 * @param c
	 * 		�����İ༶����
	 * @param classID
	 * 		����ȡѧԺ���
	 * @return
	 * 		������
	 */
	public int getClassByID(Class c, String classID);
	


	/**
	 * ��ȡ�İ༶����
	 * @param classes
	 * 		�����İ༶���󼯺�
	 * @return
	 * 		������
	 */
	public int getClassCollections(List<Class> classes);

	/**
	 * �������°༶����
	 * @param c
	 * 		������İ༶����
	 * @param conn 
	 * 		���������ݿ����Ӷ���
	 * @return
	 * 		������
	 */
	public int saveOrUpdate(Class c);
}
