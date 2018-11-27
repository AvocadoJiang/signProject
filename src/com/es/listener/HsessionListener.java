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
 *  Created on 2018年2月1日 下午3:47:27<br>
 *  监听session生命周期，用户一段时间内不操作则销毁session<br>
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
