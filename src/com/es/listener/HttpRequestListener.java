package com.es.listener;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;


/**
 * @author Anson<br>
 *	Copyright by EasyShare 2018<br>
 *  
 *  All right reserved<br>
 *
 *  Created on 2018��2��1�� ����3:33:04<br>
 *  ��ȡ��¼�û���ip����ϸ��Ϣ������Ϊ�����û�<br>
 */
@WebListener
public class HttpRequestListener implements ServletRequestListener {

	@Override
	public void requestDestroyed(ServletRequestEvent event) {
		
	}

	@Override
	public void requestInitialized(ServletRequestEvent event) {
		
 
	}


}
