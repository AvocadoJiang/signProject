package com.es.listener;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * @author Anson<br>
 *	Copyright by EasyShare 2018<br>
 *  
 *  All right reserved<br>
 *
 *  Created on 2018��2��1�� ����3:47:27<br>
 *  ����session�������ڣ��û�һ��ʱ���ڲ�����������session<br>
 */
@WebListener
public class HsessionListener implements HttpSessionListener {

	@Override
	public void sessionCreated(HttpSessionEvent event) {
		 
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		 
	}

}
