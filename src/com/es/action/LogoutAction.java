package com.es.action;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.es.globle.Constants;
import com.es.service.UserService;
import com.es.utils.ControllerUtil;
import com.es.utils.ERRORUtil;
import net.sf.json.JSONObject;

/**
 * @author Jason
 *	Copyright by EasyShare 2018
 *  
 *  All right reserved
 *
 *  Created on 2018年3月3日
 *  
 *  登出
 */
@Component
public class LogoutAction extends HttpServlet {
	

	private static final long serialVersionUID = 1L;
	@Autowired
	UserService userService;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int code = Constants.NO_ERROR_EXIST;
		request.getSession().invalidate();
		JSONObject data=new JSONObject();
		data.element("status", code==Constants.NO_ERROR_EXIST?true:false);
		data.element("msg", code==Constants.NO_ERROR_EXIST?"注销成功！":ERRORUtil.message(code));
		
		ControllerUtil.out(response, data);
		
		
		
	}
	
	
}



