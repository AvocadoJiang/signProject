package com.es.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.es.entity.Major;
import com.es.entity.Operation;
import com.es.entity.Operation.OPERATION;
import com.es.entity.User;
import com.es.entity.User.USER_IDENTITY;
import com.es.globle.Constants;
import com.es.json.form.request.MajorReqForm;
import com.es.json.form.response.MajorRespForm;
import com.es.service.MajorService;
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
 *  添加专业<br>
 */
@Component
public class MajorAction extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	MajorService majorService;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String type=request.getParameter("type").trim();
		request.getSession().setAttribute("identity", USER_IDENTITY.ROOT.name());
		//添加或修改班级信息，或删除班级信息
		saveOrUpdate(request, response, Operation.getOperation(type));
	} 
	
	/**
	 * 保存或者修改或者删除专业信息
	 * @param request
	 * 		request
	 * @param response
	 * 		response
	 * @param operation
	 * 		操作类型枚举值
	 */
	private void saveOrUpdate(HttpServletRequest request, HttpServletResponse response, Enum<OPERATION> operation) {
		int code=Constants.NO_ERROR_EXIST;
		
		//获取用户身份
		USER_IDENTITY identity =  User.getUserTypeEnum((String)(request.getSession().getAttribute(Constants.IDENTITY)));
		
		String jsonString=request.getParameter("data").trim();
		MajorReqForm majorReqForm=(MajorReqForm) JSONUtil.jsonToBean(jsonString, MajorReqForm.class);
		Major major=new Major();
		//生成对象
		code=majorService.getMajor(major, majorReqForm, operation);
		if(!(identity==USER_IDENTITY.ROOT||identity==USER_IDENTITY.ADMIN))
		{
			code=Constants.INVALID_REQUEST;
		}
		if(code==Constants.NO_ERROR_EXIST)
		{
			//保存或者更新数据
			code=majorService.saveOrUpdate(major);
		}
		//返回详情数据
		JSONObject data=new JSONObject();
		data.element("status", code==Constants.NO_ERROR_EXIST?true:false);
		data.element("msg", code==Constants.NO_ERROR_EXIST?JSONUtil.beanToJson(new MajorRespForm(major)):ERRORUtil.message(code));
		
		ControllerUtil.out(response, data);
	}


	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
