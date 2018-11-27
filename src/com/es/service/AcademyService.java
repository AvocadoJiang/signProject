package com.es.service;

import com.es.json.form.request.AcademyReqForm;
import java.util.List;
import com.es.entity.Academy;
import com.es.entity.Operation.OPERATION;

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
public interface AcademyService {

	/**
	 * ��ȡѧԺ����
	 * @param academy
	 * 		������ѧԺ����
	 * @param reqForm
	 * 		ѧԺ�������
	 * @param operation
	 * 		��������
	 * @return
	 * 		������
	 */
	public int getAcademy(Academy academy, AcademyReqForm reqForm, Enum<OPERATION> operation);
	
	/**
	 * ����������ʱ��Ϣ��������
	 * @param reqForm
	 * 		����ͼ������ע����
	 * @param operation
	 * 		��������
	 * @return
	 * 		������
	 */
	public int check(AcademyReqForm reqForm, Enum<OPERATION> operation);

	/**
	 * ���桢�޸Ļ�ɾ��ѧԺ
	 * @param academy
	 * 		�������ѧԺ����
	 * @return
	 * 		������
	 */
	public int saveOrUpdate(Academy academy);

	/**
	 * ��ȡѧԺ����
	 * @param reqForm
	 * 		����ȡ��ѧԺ��Ϣ�������
	 * @param objects
	 * 		������ѧԺ���󼯺�
	 * @return
	 * 		������
	 */
	public int getAcademyCollections(AcademyReqForm reqForm, List<Object> respForm,Integer start,Integer end);
	
	
}
