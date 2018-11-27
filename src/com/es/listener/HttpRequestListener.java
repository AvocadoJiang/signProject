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
 *  Created on 2018年2月1日 下午3:33:04<br>
 *  获取登录用户的ip等详细信息，保存为在线用户<br>
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
