package com.es.dao;

import java.util.List;

import com.es.entity.Stusign;

/**
 * @author Anson
 *	Copyright by EasyShare 2017
 *  
 *  All right reserved
 *
 *  Created on 2017��6��25�� ����4:21:59
 *  
 *  ѧ��ǩ����Ϣ���ݷ��ʽӿ�
 */
public interface StusignDao {

	/**
	 * ��ȡѧ��ǩ������
	 * @param stusign
	 * 		������ѧ��ǩ������
	 * @param stusignID
	 * 		����ȡѧ��ǩ�����
	 * @return
	 * 		������
	 */
	public int getStusignByID(Stusign stusign, String stusignID);
	


	/**
	 * ��ȡ��ѧ��ǩ������
	 * @param stusigns
	 * 		������ѧ��ǩ�����󼯺�
	 * @return
	 * 		������
	 */
	public int getStusignCollections(List<Stusign> stusigns);

	/**
	 * ��������ѧ��ǩ������
	 * @param stusign
	 * 		�������ѧ��ǩ������
	 * @param conn 
	 * 		���������ݿ����Ӷ���
	 * @return
	 * 		������
	 */
	public int saveOrUpdate(Stusign stusign);
}
