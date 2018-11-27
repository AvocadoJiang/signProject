package com.es.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.es.entity.Class;
import com.es.entity.Operation;
import com.es.entity.Operation.OPERATION;
import com.es.entity.User;
import com.es.entity.User.USER_IDENTITY;
import com.es.globle.Constants;
import com.es.json.form.request.ClassReqForm;
import com.es.json.form.response.ClassRespForm;
import com.es.service.ClassService;
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
 *  Created on 2018��3��3��<br>
 *  ��Ӱ༶<br>
 */
@Component
public class ClassAction extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	ClassService classService;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String type=request.getParameter("type").trim();
		request.getSession().setAttribute("identity", USER_IDENTITY.ROOT.name());
		//��ӻ��޸İ༶��Ϣ����ɾ���༶��Ϣ
		saveOrUpdate(request, response, Operation.getOperation(type));
	} 
	
	/**
	 * ��������޸Ļ���ɾ��ѧУ��Ϣ
	 * @param request
	 * 		request
	 * @param response
	 * 		response
	 * @param operation
	 * 		��������ö��ֵ
	 */
	private void saveOrUpdate(HttpServletRequest request, HttpServletResponse response, Enum<OPERATION> operation) {
		int code=Constants.NO_ERROR_EXIST;
		
		//��ȡ�û����
		USER_IDENTITY identity =  User.getUserTypeEnum((String)(request.getSession().getAttribute(Constants.IDENTITY)));
		
		String jsonString=request.getParameter("data").trim();
		ClassReqForm classReqForm=(ClassReqForm) JSONUtil.jsonToBean(jsonString, ClassReqForm.class);
		Class c=new Class();
		//���ɶ���
		code=classService.getClass(c, classReqForm, operation);
		System.out.println(c);
		if(!(identity==USER_IDENTITY.ROOT||identity==USER_IDENTITY.ADMIN))
		{
			code=Constants.INVALID_REQUEST;
		}
		if(code==Constants.NO_ERROR_EXIST)
		{
			//������߸�������
			code=classService.saveOrUpdate(c);
		}
		//������������
		JSONObject data=new JSONObject();
		data.element("status", code==Constants.NO_ERROR_EXIST?true:false);
		data.element("msg", code==Constants.NO_ERROR_EXIST?JSONUtil.beanToJson(new ClassRespForm(c)):ERRORUtil.message(code));
		
		ControllerUtil.out(response, data);
	}


	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
