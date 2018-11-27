package com.es.proxy;

import java.io.IOException;
import javax.servlet.GenericServlet;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * @author Anson
 *	Copyright by EasyShare 2017
 *  
 *  All right reserved
 *
 *  Created on 2017��6��25�� ����4:37:36
 *  
 *  ������ʽ��ȡBean<br>
 */
public class DelegatingServletProxy extends GenericServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** ��IOC�����Ķ��� **/
	private String targetBean=null;
	private Servlet proxy=null;

	/**
	 * ��ʼ��
	 */
	@Override
	public void init() throws ServletException {
		this.targetBean=getServletName();

		getServletBean();
	
		proxy.init(getServletConfig());
		
	}
	
	/**
	 * ��ȡBean
	 */
	private void getServletBean() {
		WebApplicationContext webApplicationContext=
				WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
		this.proxy=(Servlet) webApplicationContext.getBean(this.targetBean);
	}

	@Override
	public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
		this.proxy.service(request, response);
	}

}