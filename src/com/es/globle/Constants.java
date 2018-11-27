package com.es.globle;

import java.awt.Color;
import java.awt.Font;

/**
 * @author Anson
 *	Copyright by EasyShare 2017
 *  
 *  All right reserved
 *
 *  Created on 2017��6��25�� ����4:33:44
 *  
 *  �����࣬���ڹ���������
 */
public class Constants {
	/**
	 * ��֤�볤��
	 */
	public static final Integer IMAGE_WIDTH=90;
	
	/**
	 * ��֤����
	 */
	public static final Integer IMAGE_HEIGHT=30;
	
	/**
	 * ��֤���ֿ�
	 */
	public static Font[] codeFont={new Font("Times New Roman",Font.PLAIN,20),
			new Font("Times New Roman",Font.PLAIN,20),new Font("Times New Roman",Font.PLAIN,20),
			new Font("Times New Roman",Font.PLAIN,20),new Font("Times New Roman",Font.PLAIN,20)};
	/**
	 * ��֤��ÿ���ַ���ɫ
	 */
	public static Color[] color={Color.BLACK,Color.BLUE,Color.RED,Color.DARK_GRAY,Color.GREEN};
	
	/**
	 * ��֤���ֿ�
	 */
	public static final String IMAGE_CHAR="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
	
	/**
	 * session�е��û�ID
	 */
	public static String USER_ID = "userID";
	public static String IDENTITY = "identity";
	public static String NAME = "name";
	public static String SCHOOL_ID = "school_id";
	public static String ACADEMY_ID = "academy_id";
	public static String MAJOR_ID = "major_id";
	public static String CLASS_ID = "class_id";
	/**
	 * ��֤��
	 */
	public static String CHECK_NUMBER_NAME = "identify_code";
	

	/**
	 * ������ServletContext�е��û��б�
	 */
	public static String ONLINE_USERS = "users";
	
	/**
	 * ���ӵ�з�
	 */
	public static final String SOFTWARE_OWNER = "������Ϣ��ȫʵ����";
	
	/**
	 * Ĭ��ͷ����
	 */
	public static final String DEFAULT_HEAD_PICTURE_NAME = "default.png";
	
	/**
	 * ��ʼ��
	 */
	public static final String INIT = "init";
	
	/**
	 * ����
	 */
	public static final String ADD = "add";
	
	/**
	 * ɾ��
	 */
	public static final String DELETE = "delete";
	
	/**
	 * ����
	 */
	public static final String SEARCH = "search";
	
	/**
	 * ����
	 */
	public static final String UPDATE = "update";
	
	/**
	 * ����
	 */
	public static final String DETAIL = "detail";
	
	/**
	 * ��������
	 */
	public static final String RESET_PASSWORD = "resetPwd";
	
	/**
	 * ��֤����
	 */
	public static final String IDENTIFY_CODE = "identify";
	
	/**
	 * �û����߼�¼״̬��Ծ
	 */
	public static final int STATIC_ACTIVE=1;
	
	/**
	 * �û����¼״̬�Ƿ�
	 */
	public static final int STATIC_INVALID=0;
	
	/**
	 * �û����¼������
	 */
	public static final int STATIC_DELETE=-1;
	
	
	/**
	 * �������κδ���ָ��null
	 */
	public static final int NO_ERROR_EXIST=-1;
	

	/**
	 * ���û����Ѿ���ע��
	 */
	public static final int USER_ACCOUNT_EXIST = 1;
	/**
	 * ���û��������Ƿ��ַ�
	 */
	public static final int USER_ACCOUNT_CONTAIN_INVALIDE_WORD=2;
	
	/**
	 * ע��ɹ�
	 */
	public static final int REGISTER_SUCCESS=3;
	
	/**
	 * ���������δ֪���أ����ע��ʧ��
	 */
	public static final int UNKNOWN_REGISTER_ERROR=4;
	
	/**
	 * �û��˻�������
	 */
	public static final int USER_ACCPUNT_NOT_EXIST=5;
	
	/**
	 * ��¼�������
	 */
	public static final int USER_LOGIN_PWD_ERROR=6;
	
	/**
	 * �Ƿ�����
	 */
	public static final int INVALID_REQUEST=7;
	
	/**
	 * beanʵ�崴��ʧ��
	 */
	public static final int ENTITY_INSTANCE_DEFEAT=8;

	/**
	 * ��ǰ����û�ж�Ӧ����Դ
	 */
	public static final int INFORMATION_NOT_EXIST = 10;

	/**
	 * ��Ϣ���³ɹ�
	 */
	public static final int INFORMATION_UPDATE_SECCESS = 11;

	/**
	 * ���������δ֪���ص��£���Ϣ����ʧ�ܣ���ˢ������
	 */
	public static final int UNKNOWN_OPERATION_ERROR = 12;

	/**
	 * redis���ﱻ��ϣ��������ύ
	 */
	public static final int REDIS_TRANSACTION_INTERRUPT = 13;
	
	/**
	 * �ֻ�����Ԥ�����벻ƥ��
	 */
	public static final int INVALID_PHONE_NUMBER = 14;
	
	/**
	 * ��֤�����
	 */
	public static final int IDENTIFY_CODE_ERROR=15;
	/**
	 * �ļ������д���
	 */
	public static final int FILE_TYPE_ERROR=16;
	/**
	 * �ļ�������
	 */
	public static final int FILE_NOT_FIND=17;
	/**
	 * �������ʱ��
	 */
	public static final int CACHE_SERVICE_EXPIRE_TIME = 60;
	/** --------------------------------------------------------------- **/
	/** ����洢ʧ�� **/
	public static final int REDIS_ENTITY_SAVE_ERROR = 1000;
    /** �ϴ���Ϣ������ **/
	public static final int INFO_NOT_COMPLETE = 1001;
	/** ����ɾ��ʧ�� **/
	public static final int REDIS_ENTITY_DELETE_ERROR = 1002;
	/** �����ܱ����Ĵ��޷��ó�����ԭ�� **/
	public static final int UNKNOWN_ERROR = 1003;
	/** �û����Ͳ����� **/
	public static final int USER_TYPE_NOT_EXIST = 1004;
	/** �����ʽ����ȷ **/
	public static final int EMAIL_PATTERN_NOT_CORRECT = 1005;
	/** �ֻ��Ÿ�ʽ����ȷ **/
	public static final int PHONE_PATTERN_NOT_CORRECT = 1006;
	/** �û��������벻��ȷ **/
	public static final int USERNAME_OR_PASSWORD_NOT_EXIST = 1007;
	/** ��Ϣ����ʧ�ܣ���ˢ������ **/
	public static final int INFORMATION_LOAD_DEFEAT=1008;
	/** ������Ϣ�а����Ƿ����� **/
	public static final int INVALID_NUMBER_REQUEST = 1009;
	/** ����������� **/
	public static final int SEARCH_PARAM_ERROR = 1010;
	/** ���ݿ����ݱ������ **/
	public static final int DB_SAVE_ERROR = 1011;
	/** ���ݿ��ȡ����ʧ�� **/
	public static final int DB_GET_ERROR = 1012;
	/** ��ݴ��� **/
	public static final int IDENTITY_ERROR = 1013;
	/** ����ĵ�¼��ʽ **/
	public static final int LOGIN_WAY_ERROR = 1014;
	/** openID��ȡʧ�� **/
	public static final int OPENID_ERROR = 1015;
}
