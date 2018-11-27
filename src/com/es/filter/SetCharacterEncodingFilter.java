package com.es.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

/**
 * @author Anson
 *	Copyright by EasyShare 2017
 *  
 *  All right reserved
 *
 *  Created on 2017��6��25�� ����4:33:01
 *  
 *  ���������<br>
 */
public class SetCharacterEncodingFilter implements Filter{

	//�����ʽ
	protected String encoding=null;
	
	@Override
	public void destroy() {
		this.encoding=null;
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		// �жϵ�ǰ�����Ƿ񵥶����ñ����ʽ
		
		if(request.getCharacterEncoding()==null)
		{
			
			
			//��ȡĬ�ϱ����ʽ
			String encoding = this.encoding;
			if(encoding!=null)
			{
				request.setCharacterEncoding(encoding);
				response.setContentType("text/plain;charset="+encoding);
				
			}
		}else{
			response.setContentType("text/plain;charset="+request.getCharacterEncoding());
		}
		
		//�������
		response.setHeader("Access-Control-Allow-Origin",request.getHeader("Origin"));
		response.setHeader("Access-Control-Allow-Headers","X-Requested-With");
		response.setHeader("Access-Control-Allow-Methods","GET,POST,DELETE,PUT,OPTIONS");
		response.setHeader("Access-Control-Allow-Credentials","true"); //�Ƿ�֧��cookie����
		
		//�������Ĳ���������multipart����HttpServletRequestת��ΪMultipartHttpServletRequest
		if(request.getContentType()!=null&&request.getContentType().contains("multipart")){
			MultipartHttpServletRequest multiReq = new CommonsMultipartResolver().resolveMultipart(request);
			request = multiReq;
		}
		
		
		//����һ��������ת��
		chain.doFilter(request, response);
		
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
		//��ȡ��ʼ������
		this.encoding=filterConfig.getInitParameter("encoding");
	}

}
