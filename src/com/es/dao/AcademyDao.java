package com.es.dao;

import java.util.List;

import com.es.entity.Academy;

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
public interface AcademyDao {

	/**
	 * ��ȡѧԺ����
	 * @param academy
	 * 		������ѧԺ����
	 * @param academyID
	 * 		����ȡѧԺ���
	 * @return
	 * 		������
	 */
	public int getAcademyByID(Academy academy, String academyID);
	


	/**
	 * ��ȡ��ѧԺ����
	 * @param academys
	 * 		������ѧԺ���󼯺�
	 * @return
	 * 		������
	 */
	public int getAcademyCollections(List<Academy> academys);

	/**
	 * ��������ѧԺ����
	 * @param academy
	 * 		�������ѧԺ����
	 * @param conn 
	 * 		���������ݿ����Ӷ���
	 * @return
	 * 		������
	 */
	public int saveOrUpdate(Academy academy);
}
