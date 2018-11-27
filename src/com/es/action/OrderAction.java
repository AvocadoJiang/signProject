package com.es.action;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartRequest;

import com.es.entity.Course;
import com.es.entity.Lesson;
import com.es.entity.Operation;
import com.es.entity.Operation.OPERATION;
import com.es.entity.Order;
import com.es.entity.User;
import com.es.entity.User.USER_IDENTITY;
import com.es.excel.ExcelReader;
import com.es.globle.Constants;
import com.es.json.form.request.AcademyReqForm;
import com.es.json.form.request.OrderReqForm;
import com.es.json.form.request.UserReqForm;
import com.es.json.form.response.OrderRespForm;
import com.es.service.OrderService;
import com.es.utils.ControllerUtil;
import com.es.utils.ERRORUtil;
import com.es.utils.FileUtil;
import com.es.utils.JSONUtil;
import net.sf.json.JSONObject;

/**
 * @author Jason<br>
 *	Copyright by EasyShare 2018<br>
 *  
 *  All right reserved<br>
 *
 *  Created on 2018��3��3��<br>
 *  ���ѡ��<br>
 */
@Component
public class OrderAction extends HttpServlet{
	
	/**
	 * 
	 */
	String filepath = null;
	private static final long serialVersionUID = 1L;
	@Autowired
	OrderService orderService;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String type=request.getParameter("type").trim();
		filepath = getServletContext().getRealPath("/")+File.separator+"WEB-INF"+File.separator+"uploadfile"; // �ϴ��ļ���Ŀ¼
		request.getSession().setAttribute("identity", USER_IDENTITY.ROOT.name());
		//��ӻ��޸Ŀ�ʱ��Ϣ����ɾ��ʱ����Ϣ
		
		if(type.equals(Operation.OPERATION.UPLOAD.name())){
			//��excel�ļ��ϴ���Ӷ���γ���Ϣ
			addOrderFromExcel(request,response);
		}else{
			saveOrUpdate(request, response, Operation.getOperation(type));
		}
	} 
	
	private void addOrderFromExcel(HttpServletRequest request, HttpServletResponse response){
		//���ݸ�ʽ��Ȩ�޿���
		int code=Constants.NO_ERROR_EXIST;
		
		//��ȡ�û����
		USER_IDENTITY identity = User.getUserTypeEnum((String)(request.getSession().getAttribute("identity")));
		String jsonString=request.getParameter("data").trim();
		System.out.println(jsonString);
		OrderReqForm orderReqForm=(OrderReqForm) JSONUtil.jsonToBean(jsonString, OrderReqForm.class);
		
		
		if(!(identity==USER_IDENTITY.ROOT||identity==USER_IDENTITY.ADMIN))
		{
			code=Constants.INVALID_REQUEST;
		}else{
			//���ļ��洢��
			FileUtil fileUtil = new FileUtil(filepath);
			Map<String,File> files = new HashMap<String,File>();
			code = fileUtil.acceptFiles((MultipartRequest)request, files,"file");
			//���ļ���ȡ
			if(code == Constants.NO_ERROR_EXIST){
				ExcelReader excelReader = new ExcelReader(files.get("file"));
				
				List<Order> orders = new ArrayList<Order>();
				code = excelReader.readExcelOrder(orders,orderReqForm);
				if(code == Constants.NO_ERROR_EXIST){
					//�����û�
					for(Order order:orders){
						
						orderService.saveOrUpdate(order);
					}
				}
				
				
			}
		}
		//������������
		JSONObject data=new JSONObject();
		data.element("status", code==Constants.NO_ERROR_EXIST?true:false);
		data.element("msg", code==Constants.NO_ERROR_EXIST?"�ϴ��ɹ�":ERRORUtil.message(code));
		ControllerUtil.out(response, data);
		
	}
	
	/**
	 * ��������޸Ļ���ɾ����ʱ��Ϣ
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
		USER_IDENTITY identity = User.getUserTypeEnum((String)(request.getSession().getAttribute(Constants.IDENTITY)));
		
		String jsonString=request.getParameter("data").trim();
		OrderReqForm orderReqForm=(OrderReqForm) JSONUtil.jsonToBean(jsonString, OrderReqForm.class);
		Order order=new Order();
		//���ɶ���
		code=orderService.getOrder(order, orderReqForm, operation);
		if(!(identity==USER_IDENTITY.ROOT||identity==USER_IDENTITY.ADMIN||identity==USER_IDENTITY.TEACHER||identity==USER_IDENTITY.STUDENT))
		{
			code=Constants.INVALID_REQUEST;
		}
		if(code==Constants.NO_ERROR_EXIST)
		{
			//������߸�������
			code=orderService.saveOrUpdate(order);
		}
		//������������
		JSONObject data=new JSONObject();
		data.element("status", code==Constants.NO_ERROR_EXIST?true:false);
		data.element("msg", code==Constants.NO_ERROR_EXIST?JSONUtil.beanToJson(new OrderRespForm(order)):ERRORUtil.message(code));
		
		ControllerUtil.out(response, data);
	}


	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
