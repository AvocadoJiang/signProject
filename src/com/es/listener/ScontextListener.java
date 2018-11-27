package com.es.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * @author Anson<br>
 *	Copyright by EasyShare 2018<br>
 *  
 *  All right reserved<br>
 *
 *  Created on 2018年2月1日 下午3:27:13<br>
 *  监听服务器的启动和销毁<br>
 */
@WebListener
public class ScontextListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		System.out.println(">>>>>>>>>>>>>>>>>>>服务销毁>>>>>>>>>>>>>>>>>>>>>>>>>");
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		 
		 
		 System.out.println(">>>>>>>>>>>>>>>>>>>服务启动>>>>>>>>>>>>>>>>>>>>>>>>");
	}

}
