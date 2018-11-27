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

import com.es.entity.Operation;
import com.es.entity.Operation.OPERATION;
import com.es.entity.User;
import com.es.entity.User.USER_IDENTITY;
import com.es.excel.ExcelReader;
import com.es.globle.Constants;
import com.es.json.form.request.UserReqForm;
import com.es.json.form.response.UserRespForm;
import com.es.service.UserService;
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
 *  Created on 2018年3月3日<br>
 *  添加选课<br>
 */
@Component
public class UserAction extends HttpServlet{
 
	String filepath = null;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	UserService userService;
	

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		filepath = getServletContext().getRealPath("/")+File.separator+"WEB-INF"+File.separator+"uploadfile"; // 上传文件的目录
		String type=request.getParameter("type").trim();
		
		if(type.equals(Operation.OPERATION.UPLOAD.name())){
			//用excel文件上传添加多个用户信息
			addUserFromExcel(request,response);
		}else{
			//添加或修改单个用户信息，或删除时刻信息
			saveOrUpdate(request, response, Operation.getOperation(type));
		}
		
		
	} 
	

	/**
	 * 保存或者修改或者删除课时信息
	 * @param request
	 * 		request
	 * @param response
	 * 		response
	 * @param operation
	 * 		操作类型枚举值
	 */
	private void saveOrUpdate(HttpServletRequest request, HttpServletResponse response, Enum<OPERATION> operation) {
		
		int code=Constants.NO_ERROR_EXIST;
		
		request.getSession().setAttribute("identity","ROOT");
		//获取用户身份
		USER_IDENTITY identity = User.getUserTypeEnum((String)(request.getSession().getAttribute("identity")));
		
		String jsonString=request.getParameter("data").trim();
		
		
		UserReqForm userReqForm=(UserReqForm) JSONUtil.jsonToBean(jsonString, UserReqForm.class);
		User user=new User();
		//生成对象
		code=userService.getUser(user, userReqForm, operation);
		if(!(identity==USER_IDENTITY.ROOT||identity==USER_IDENTITY.ADMIN))
		{
			code=Constants.INVALID_REQUEST;
		}
		if(code==Constants.NO_ERROR_EXIST)
		{
			//保存或者更新数据
			code=userService.saveOrUpdate(user);
		}
		//返回详情数据
		JSONObject data=new JSONObject();
		data.element("status", code==Constants.NO_ERROR_EXIST?true:false);
		data.element("msg", code==Constants.NO_ERROR_EXIST?JSONUtil.beanToJson(new UserRespForm(user)):ERRORUtil.message(code));
		
		

		ControllerUtil.out(response, data);
	}
	
	private void addUserFromExcel(HttpServletRequest request, HttpServletResponse response){
		//数据格式化权限控制
		int code=Constants.NO_ERROR_EXIST;
		request.getSession().setAttribute("identity","ROOT");
		//获取用户身份
		USER_IDENTITY identity = User.getUserTypeEnum((String)(request.getSession().getAttribute("identity")));
		
		String jsonString=request.getParameter("data").trim();
		
		
		UserReqForm userReqForm=(UserReqForm) JSONUtil.jsonToBean(jsonString, UserReqForm.class);
		userReqForm.setUser_id("undefineded");
		userReqForm.setUser_psd("undefineded");
		userReqForm.setUser_name("undefineded");
		code = userService.check(userReqForm,Operation.getOperation("ADD"));
		if(code !=Constants.NO_ERROR_EXIST){
			
		}else if(!(identity==USER_IDENTITY.ROOT||identity==USER_IDENTITY.ADMIN))
		{
			code=Constants.INVALID_REQUEST;
		}else{
			//将文件存储好
			FileUtil fileUtil = new FileUtil(filepath);
			Map<String,File> files = new HashMap<String,File>();
			code = fileUtil.acceptFiles((MultipartRequest)request, files,"file");
			//将文件读取
			if(code == Constants.NO_ERROR_EXIST){
				ExcelReader excelReader = new ExcelReader(files.get("file"));
				List<User> list = new ArrayList<User>();
				code = excelReader.readExcelUser(list,userReqForm);
				if(code == Constants.NO_ERROR_EXIST){
					//新增用户
					for(User user:list){
						userService.saveOrUpdate(user);
					}
				}
			}
		}
		//返回详情数据
		JSONObject data=new JSONObject();
		data.element("status", code==Constants.NO_ERROR_EXIST?true:false);
		data.element("msg", code==Constants.NO_ERROR_EXIST?"上传成功":ERRORUtil.message(code));
		ControllerUtil.out(response, data);
		
	}


	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
