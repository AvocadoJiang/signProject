package com.es.dao;

import java.util.List;

import com.es.entity.User;

/**
 * @author Anson
 *	Copyright by EasyShare 2017
 *  
 *  All right reserved
 *
 *  Created on 2017��6��25�� ����4:21:59
 *  
 *  �û���Ϣ���ݷ��ʽӿ�
 */
public interface UserDao {

	/**
	 * ��ȡ�û�����
	 * @param user
	 * 		�������û�����
	 * @param userId
	 * 		����ȡ�û����
	 * @return
	 * 		������
	 */
	public int getUserByID(User user, String userID);
	


	/**
	 * ��ȡ���û�����
	 * @param users
	 * 		�������û����󼯺�
	 * @return
	 * 		������
	 */
	public int getUserCollections(List<User> users);

	/**
	 * ���������û�����
	 * @param user
	 * 		��������û�����
	 * @return
	 * 		������
	 */
	public int saveOrUpdate(User user);
}
