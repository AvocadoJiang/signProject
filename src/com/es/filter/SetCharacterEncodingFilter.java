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
 *  Created on 2017年6月25日 下午4:33:01
 *  
 *  编码过滤器<br>
 */
public class SetCharacterEncodingFilter implements Filter{

	//编码格式
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
		// 判断当前请求是否单独设置编码格式
		
		if(request.getCharacterEncoding()==null)
		{
			
			
			//获取默认编码格式
			String encoding = this.encoding;
			if(encoding!=null)
			{
				request.setCharacterEncoding(encoding);
				response.setContentType("text/plain;charset="+encoding);
				
			}
		}else{
			response.setContentType("text/plain;charset="+request.getCharacterEncoding());
		}
		
		//跨域访问
		response.setHeader("Access-Control-Allow-Origin",request.getHeader("Origin"));
		response.setHeader("Access-Control-Allow-Headers","X-Requested-With");
		response.setHeader("Access-Control-Allow-Methods","GET,POST,DELETE,PUT,OPTIONS");
		response.setHeader("Access-Control-Allow-Credentials","true"); //是否支持cookie跨域
		
		//如果请求的参数类型是multipart，将HttpServletRequest转换为MultipartHttpServletRequest
		if(request.getContentType()!=null&&request.getContentType().contains("multipart")){
			MultipartHttpServletRequest multiReq = new CommonsMultipartResolver().resolveMultipart(request);
			request = multiReq;
		}
		
		
		//向下一个过滤器转发
		chain.doFilter(request, response);
		
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
		//获取初始化参数
		this.encoding=filterConfig.getInitParameter("encoding");
	}

}
