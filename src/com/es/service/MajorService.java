package com.es.service;

import com.es.json.form.request.MajorReqForm;

import java.util.List;
import com.es.entity.Major;
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
public interface MajorService {

	/**
	 * ��ȡרҵ����
	 * @param c
	 * 		������רҵ����
	 * @param reqForm
	 * 		�༶�������
	 * @param operation
	 * 		��������
	 * @return
	 * 		������
	 */
	public int getMajor(Major c, MajorReqForm reqForm, Enum<OPERATION> operation);
	
	/**
	 * ����������ʱ��Ϣ��������
	 * @param reqForm
	 * 		����ͼ������ע����
	 * @param operation
	 * 		��������
	 * @return
	 * 		������
	 */
	public int check(MajorReqForm reqForm, Enum<OPERATION> operation);

	/**
	 * ���桢�޸Ļ�ɾ��רҵ
	 * @param c
	 * 		�������רҵ����
	 * @return
	 * 		������
	 */
	public int saveOrUpdate(Major c);

	/**
	 * ��ȡרҵ����
	 * @param reqForm
	 * 		����ȡ��רҵ��Ϣ�������
	 * @param objects
	 * 		������רҵ���󼯺�
	 * @return
	 * 		������
	 */
	public int getMajorCollections(MajorReqForm reqForm, List<Object> respForm,Integer start,Integer end);
	
	
}
