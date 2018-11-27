package com.es.service;

import com.es.json.form.request.StusignReqForm;
import java.util.List;
import com.es.entity.Operation.OPERATION;
import com.es.entity.Stusign;


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
public interface StusignService {

	/**
	 * ��ȡѧ��ǩ������
	 * @param stusign
	 * 		������ѧ��ǩ������
	 * @param reqForm
	 * 		ѧ��ǩ���������
	 * @param operation
	 * 		��������
	 * @return
	 * 		������
	 */
	public int getStusign(Stusign stusign, StusignReqForm reqForm, Enum<OPERATION> operation);
	
	/**
	 * ����������ʱ��Ϣ��������
	 * @param reqForm
	 * 		����ͼ������ע����
	 * @param operation
	 * 		��������
	 * @return
	 * 		������
	 */
	public int check(StusignReqForm reqForm, Enum<OPERATION> operation);

	/**
	 * ���桢�޸Ļ�ɾ��ѧ��ǩ��
	 * @param stusign
	 * 		�������ѧ��ǩ������
	 * @return
	 * 		������
	 */
	public int saveOrUpdate(Stusign stusign);

	/**
	 * ��ȡѧ��ǩ������
	 * @param reqForm
	 * 		����ȡ��ѧ��ǩ����Ϣ�������
	 * @param objects
	 * 		������ѧ��ǩ�����󼯺�
	 * @return
	 * 		������
	 */
	public int getStusignCollections(StusignReqForm reqForm, List<Object> respForm,Integer start,Integer end);
	
	
}
