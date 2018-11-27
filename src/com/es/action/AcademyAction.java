package com.es.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.es.entity.Academy;
import com.es.entity.Operation;
import com.es.entity.Operation.OPERATION;
import com.es.entity.User;
import com.es.entity.User.USER_IDENTITY;
import com.es.globle.Constants;
import com.es.json.form.request.AcademyReqForm;
import com.es.json.form.response.AcademyRespForm;
import com.es.service.AcademyService;
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
 *  ���ѧԺ<br>
 */
@Component
public class AcademyAction extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	AcademyService academyService;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String type=request.getParameter("type").trim();
		//��ӻ��޸�ѧԺ��Ϣ����ɾ��ѧԺ��Ϣ
		request.getSession().setAttribute("identity", USER_IDENTITY.ROOT.name());
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
		AcademyReqForm academyReqForm=(AcademyReqForm) JSONUtil.jsonToBean(jsonString, AcademyReqForm.class);
		Academy academy=new Academy();
		//���ɶ���
		code=academyService.getAcademy(academy, academyReqForm, operation);
		if(!(identity==USER_IDENTITY.ROOT))
		{
			code=Constants.INVALID_REQUEST;
		}
		if(code==Constants.NO_ERROR_EXIST)
		{
			//������߸�������
			code=academyService.saveOrUpdate(academy);
		}
		//������������
		JSONObject data=new JSONObject();
		data.element("status", code==Constants.NO_ERROR_EXIST?true:false);
		data.element("msg", code==Constants.NO_ERROR_EXIST?JSONUtil.beanToJson(new AcademyRespForm(academy)):ERRORUtil.message(code));
		
		ControllerUtil.out(response, data);
	}


	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
