package com.es.dao;

import java.util.List;

import com.es.entity.Major;

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
public interface MajorDao {

	/**
	 * ��ȡ�༶����
	 * @param major
	 * 		������רҵ����
	 * @param majorID
	 * 		����ȡרҵ���
	 * @return
	 * 		������
	 */
	public int getMajorByID(Major major, String majorID);
	


	/**
	 * ��ȡ��רҵ����
	 * @param majors
	 * 		�����İ༶���󼯺�
	 * @return
	 * 		������
	 */
	public int getMajorCollections(List<Major> majors);

	/**
	 * ��������רҵ����
	 * @param major
	 * 		�������רҵ����
	 * @param conn 
	 * 		���������ݿ����Ӷ���
	 * @return
	 * 		������
	 */
	public int saveOrUpdate(Major major);
}
