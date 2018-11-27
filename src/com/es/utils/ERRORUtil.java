package com.es.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.es.globle.Constants;

/**
 * @author Anson
 *	Copyright by EasyShare 2017
 *  
 *  All right reserved
 *
 *  Created on 2017��6��25�� ����4:44:53
 *  
 *  ������빤����
 */
public class ERRORUtil {
	/** ��������� */
	private static String CODE=null;
	/** ������Ϣ */
	private static String MESSAGE=null;
	/** Property���� */
	private static Properties PROP=null;
	/** ��־ */
	static Logger logger=LoggerFactory.getLogger(ERRORUtil.class);
	
	/**
	 * ���������ļ�
	 */
	static
	{
		if(PROP==null)
		{
			PROP=new Properties();;
		}
		loadProperties();
	}
	
	
	
	/**
	 * ���������ļ�
	 */
	private static void loadProperties()
	{
		
		try 
		{
			PROP.load(new FileInputStream(Thread.currentThread().getContextClassLoader().getResource("").getPath()
					+"error.properties"));
		} catch (IOException e)
		{
			logger.info("------------------����������ļ�����------------------");
			e.printStackTrace();
		}
	}
	
	
	
	
	
	/**
	 * ���ش������Ӧ�Ĵ�����Ϣ
	 * @param code
	 * 		int
	 * @return
	 * 		String
	 */
	public static String message(int code)
	{
		CODE=new String(code+"");
		
		String temp=PROP.getProperty(CODE);
		if(temp==null)
		{
			temp=PROP.getProperty((Constants.UNKNOWN_REGISTER_ERROR+"").trim());
		}
		try {
			MESSAGE=new String(temp.getBytes("ISO-8859-1"),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			logger.info("----------------------������Ϣת�����----------------------------");
			e.printStackTrace();
		}
		return MESSAGE;
	}
	
	
	
	
	
	/**
	 * ��ȡ��ǰ������Ϣ�Ĵ�����
	 * @return
	 * 		String
	 */
	public static String code()
	{
		return CODE;
	}
	
	
	
	
	
	/**
	 * �ͷŴ�����Ϣ
	 */
	public void clear()
	{
		if(CODE!=null)
		{
			CODE=null;
		}
		if(MESSAGE!=null)
		{
			MESSAGE=null;
		}
	}
	
}
