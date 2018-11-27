package com.es.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import com.es.entity.User;
import com.es.entity.User.USER_IDENTITY;
import com.es.globle.Constants;
import com.es.json.form.response.UserRespForm;
import com.es.utils.ControllerUtil;
import com.es.utils.ERRORUtil;
import com.es.utils.JSONUtil;
import net.sf.json.JSONObject;

/**
 * @author Jason<br>
 *	Copyright by EasyShare 2018<br>
 *  
 *  All right reserved<br>
 *
 *  Created on 2018年3月3日<br>
 *  获取会话用户的session信息<br>
 */
@Component
public class SessionAction extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int code=Constants.NO_ERROR_EXIST;
		System.out.println(request.getSession().getAttribute(Constants.IDENTITY));
		if(request.getSession().getAttribute(Constants.IDENTITY)==null){
			//返回详情数据
			JSONObject data=new JSONObject();
			data.element("status", false);
			data.element("msg", "您还没有登录");
		
			ControllerUtil.out(response, data);
			return ;
		}
		USER_IDENTITY identity =  User.getUserTypeEnum((String)(request.getSession().getAttribute(Constants.IDENTITY)));
		if(!(identity==USER_IDENTITY.ROOT||identity==USER_IDENTITY.ADMIN||identity==USER_IDENTITY.STUDENT||identity==USER_IDENTITY.TEACHER))
		{
			code=Constants.INVALID_REQUEST;
		}
		User user = new User();
		if(code==Constants.NO_ERROR_EXIST)
		{			
			user.setIdentity(identity.name());
			user.setUserID((String)request.getSession().getAttribute(Constants.USER_ID));
			user.setIdentity((String)request.getSession().getAttribute(Constants.IDENTITY));
			user.setSchoolID((String)request.getSession().getAttribute(Constants.SCHOOL_ID));
			user.setAcademyID((String)request.getSession().getAttribute(Constants.ACADEMY_ID));
			user.setMajorID((String)request.getSession().getAttribute(Constants.MAJOR_ID));
			user.setClassID((String)request.getSession().getAttribute(Constants.CLASS_ID));
			user.setName((String)request.getSession().getAttribute(Constants.NAME));
			
		}
		
		//返回详情数据
		JSONObject data=new JSONObject();
		data.element("status", code==Constants.NO_ERROR_EXIST?true:false);
		data.element("msg", code==Constants.NO_ERROR_EXIST?JSONUtil.beanToJson(new UserRespForm(user)):ERRORUtil.message(code));
	
		ControllerUtil.out(response, data);
	} 
	
	


	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
