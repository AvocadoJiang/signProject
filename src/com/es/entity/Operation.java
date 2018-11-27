package com.es.entity;

public class Operation {

	/** �������� **/
	public static enum OPERATION
	{
		/** ��� **/
		ADD,
		/** ɾ�� **/
		DELETE,
		/** �޸� **/
		UPDATE,
		/** ѧУ **/
		SEARCH_SCHOOL,
		/** ѧԺ **/
		SEARCH_ACADEMY,
		/** רҵ **/
		SEARCH_MAJOR,
		/** �༶ **/
		SEARCH_CLASS,
		/** �γ� **/
		SEARCH_COURSE,
		/** ��ʱ **/
		SEARCH_LESSON,
		/** ѡ�� **/
		SEARCH_ORDER,
		/** ѧ��ǩ�� **/
		SEARCH_STUSIGN,
		/** �û� **/
		SEARCH_USER,
		/** ͨ���˺ŵ�¼ **/
		LOGIN_BY_ACCOUNT,
		/** ͨ��΢�ŵ�¼ **/
		LOGIN_BY_WECHAT,
		
		
		/** �ϴ� **/
		UPLOAD,
		
		/** ���� **/ 
		DOWNLOAD,
	}
	
	/**
	 * ��ȡ��������
	 * @param type
	 * 		������������
	 * @return
	 * 		��������ö�ٶ���
	 */
	public static Enum<OPERATION> getOperation(String type)
	{
		return OPERATION.values()[OPERATION.valueOf(type.toUpperCase().trim()).ordinal()];
	}
}
