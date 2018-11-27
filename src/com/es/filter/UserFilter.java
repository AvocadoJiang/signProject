package com.es.filter;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.es.globle.Constants;


/**
 * check user login status
 */
public class UserFilter implements Filter {
	// bad url
	private String error_url = null;
	//无需检测的页面
	private String initConfig=null;
	
	/**
	 * 获取初始化配置参数
	 */
	@Override
	public void init(FilterConfig config) throws ServletException {
		error_url = config.getInitParameter("error_url").trim();
		initConfig=config.getInitParameter("init_config").trim();
	}

	/**
	 * 检查请求是否合法<br>
	 * <p>
	 * 	1.检查请求的资源是否存在<br>
	 *  2.检查请求的用户是否在线<br>
	 * </p>
	 * <strong>部分请求仅在线用户为合法用户，可以请求对应该部分的URL</strong>
	 */
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
//		//跨站请求
//		response.setHeader("Access-Control-Allow-Origin", "*");
//		response.setHeader("Access-Control-Allow-Methods", "POST,GET");
		HttpSession session=request.getSession();
//		String url=request.getRequestURI();
//		System.out.println("请求资源："+url+"， 请求头信息："+request.getHeader("Cookie"));
		boolean flag=false;
		Object object=session.getAttribute(Constants.USER_ID);
//		System.out.println(object);
		if(object!=null&&object!="")
		{
			flag=true;
		}else
		{
			if(isCheckAble(request, initConfig))
			{
				flag=true;
			}
		}
		if(flag)
		{
			chain.doFilter(req, resp);
			return;
		}else
		{
//			System.out.println("错误跳转链接"+error_url);
			response.sendRedirect(request.getContextPath()+error_url);
		}
	}

	/**
	 * 检查请求的URL是否合法，通过封装的URL正则验证方式；将请求的资源与系统拥有的资源一次比对
	 * @param request
	 * 		request
	 * @param parma
	 * 		被检查的url
	 * @return
	 * 		是否合法
	 */
	private boolean isCheckAble(HttpServletRequest request, String parma) {
		boolean flag=false;
		if (parma == null)
		{
			flag=false;
		}else
		{
			String url = request.getRequestURI();
			String[] permitParams = parma.split(";");
			for (String permit : permitParams)
			{
				if (isURLPassable(request.getContextPath() + permit, url, request))
				{
					flag=true;
					break;
				}
			}
		}
		return flag;
	}

	/**
	 * @see #isCheckAble(HttpServletRequest, String)
	 */
	private boolean isURLPassable(String permit, String url, HttpServletRequest request) {
		try {

			String reg = "";
			if (permit.equals(request.getContextPath() + "/"))
				reg = "^" + permit + "$";
			else
				reg = "^" + permit + ".*$";
			Pattern p = Pattern.compile(reg);
			return p.matcher(url).matches();
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public void destroy() {
	}
}
