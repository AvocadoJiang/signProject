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
	//�������ҳ��
	private String initConfig=null;
	
	/**
	 * ��ȡ��ʼ�����ò���
	 */
	@Override
	public void init(FilterConfig config) throws ServletException {
		error_url = config.getInitParameter("error_url").trim();
		initConfig=config.getInitParameter("init_config").trim();
	}

	/**
	 * ��������Ƿ�Ϸ�<br>
	 * <p>
	 * 	1.����������Դ�Ƿ����<br>
	 *  2.���������û��Ƿ�����<br>
	 * </p>
	 * <strong>��������������û�Ϊ�Ϸ��û������������Ӧ�ò��ֵ�URL</strong>
	 */
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
//		//��վ����
//		response.setHeader("Access-Control-Allow-Origin", "*");
//		response.setHeader("Access-Control-Allow-Methods", "POST,GET");
		HttpSession session=request.getSession();
//		String url=request.getRequestURI();
//		System.out.println("������Դ��"+url+"�� ����ͷ��Ϣ��"+request.getHeader("Cookie"));
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
//			System.out.println("������ת����"+error_url);
			response.sendRedirect(request.getContextPath()+error_url);
		}
	}

	/**
	 * ��������URL�Ƿ�Ϸ���ͨ����װ��URL������֤��ʽ�����������Դ��ϵͳӵ�е���Դһ�αȶ�
	 * @param request
	 * 		request
	 * @param parma
	 * 		������url
	 * @return
	 * 		�Ƿ�Ϸ�
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
