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
 *  Created on 2018��2��1�� ����3:27:13<br>
 *  ����������������������<br>
 */
@WebListener
public class ScontextListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		System.out.println(">>>>>>>>>>>>>>>>>>>��������>>>>>>>>>>>>>>>>>>>>>>>>>");
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		 
		 
		 System.out.println(">>>>>>>>>>>>>>>>>>>��������>>>>>>>>>>>>>>>>>>>>>>>>");
	}

}
