package com.es.service;

import com.es.json.form.request.SigninForm;

import java.util.List;

import com.es.entity.Operation.OPERATION;
import com.es.entity.User;
import com.es.json.form.request.UserReqForm;

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
public interface UserService {

	/**
	 * ����û���¼
	 * @param signinForm
	 * 		SigninForm - ����ͼ������ע����
	 * @param user
	 * 		�������û�����
	 * @return
	 * 		int - ������
	 */
	public int checkLoginInfo(User user, SigninForm signinForm);

	/**
	 * ���ע����Ϣ
	 * @param registerForm
	 * 		����ͼ������ע����
	 * @param operation
	 * 		��������
	 * @return
	 * 		������
	 */
	public int check(UserReqForm reqForm, Enum<OPERATION> operation);

	/**
	 * ��ȡ�û�����
	 * @param user
	 * 		�������û�����
	 * @param reqForm
	 * 		�û�ע���������
	 * @param operation
	 * 		��������
	 * @return
	 * 		������
	 */
	public int getUser(User user, UserReqForm reqForm, Enum<OPERATION> operation);

	/**
	 * ���桢�޸Ļ�ɾ���û�
	 * @param user
	 * 		��������û�����
	 * @return
	 * 		������
	 */
	public int saveOrUpdate(User user);

	/**
	 * ��ȡ�û�����
	 * @param reqForm
	 * 		����ȡ���û���Ϣ�������
	 * @param objects
	 * 		�������û����󼯺�
	 * @return
	 * 		������
	 */
	public int getUserCollections(UserReqForm reqForm, List<Object> respForm,Integer start,Integer end);
	
	
}
