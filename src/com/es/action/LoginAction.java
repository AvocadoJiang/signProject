package com.es.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.es.entity.Operation;
import com.es.entity.User;
import com.es.globle.Constants;
import com.es.json.form.request.SigninForm;
import com.es.json.form.request.UserReqForm;
import com.es.json.form.response.UserRespForm;
import com.es.json.form.response.WeCahtLoginRespForm;
import com.es.service.UserService;
import com.es.utils.ControllerUtil;
import com.es.utils.ERRORUtil;
import com.es.utils.JSONUtil;
import com.es.utils.Utils;

import net.sf.json.JSON;
import net.sf.json.JSONObject;

/**
 * @author Jason
 *	Copyright by EasyShare 2018
 *  
 *  All right reserved
 *
 *  Created on 2018年3月3日
 *  
 *  用户登录组件
 */
@Component
public class LoginAction extends HttpServlet {
	

	private static final long serialVersionUID = 1L;
	@Autowired
	UserService userService;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获取登录方式
		String type = request.getParameter("type").trim();
		String jsonString=request.getParameter("data").trim();
		
		
		int code=Constants.NO_ERROR_EXIST;
		//判断用户是否存在
		if(type.equals(Operation.OPERATION.LOGIN_BY_ACCOUNT.name())){
			User user=new User();
			//将前端上传的json数据格式化成表单的bean
			SigninForm signinForm=(SigninForm) JSONUtil.jsonToBean(jsonString, SigninForm.class);
			code=userService.checkLoginInfo(user, signinForm);
			
			//登录成功，保存用户Id
			
			if(code==Constants.NO_ERROR_EXIST)
			{
				//设置session
				request.getSession().setAttribute(Constants.USER_ID, user.getUserID());
				request.getSession().setAttribute(Constants.IDENTITY, user.getIdentity());
				request.getSession().setAttribute(Constants.NAME, user.getName());
				request.getSession().setAttribute(Constants.SCHOOL_ID, user.getSchoolID());
				request.getSession().setAttribute(Constants.ACADEMY_ID, user.getAcademyID());	
				request.getSession().setAttribute(Constants.MAJOR_ID, user.getMajorID());
				request.getSession().setAttribute(Constants.CLASS_ID, user.getClassID());
				
			}
			
			//返回json：状态，信息，url
			JSONObject data=new JSONObject();
			data.element("status", code==Constants.NO_ERROR_EXIST?true:false);
			data.element("msg", code==Constants.NO_ERROR_EXIST?JSONUtil.beanToJson(
					new UserRespForm(user)):ERRORUtil.message(code));
			
			ControllerUtil.out(response, data);
		}else if(type.equals(Operation.OPERATION.LOGIN_BY_WECHAT.name())){
			//将前端上传的json数据格式化成表单的bean
			SigninForm signinForm=(SigninForm) JSONUtil.jsonToBean(jsonString, SigninForm.class);
			System.out.println(signinForm);
			WeCahtLoginRespForm jsonObject = new WeCahtLoginRespForm();
			//获取openID
			code = getSessionKeyOrOpenId(signinForm.getCode(),jsonObject);
			if(code == Constants.NO_ERROR_EXIST){
				
				if((signinForm.getUser_id()!=null||signinForm.getUser_id().trim()!="")&&(signinForm.getUser_psd()!=null||signinForm.getUser_psd().trim()!="")){
					//第一次使用微信登录，绑定openid
					//查找用户
					User entity = new User();
					code=userService.checkLoginInfo(entity, signinForm);
					entity.setOpenID(jsonObject.getOpenid());
					if(code==Constants.NO_ERROR_EXIST)
					{
						//保存或者更新数据
						code=userService.saveOrUpdate(entity);
					}
					//登录成功，保存用户Id
					
					if(code==Constants.NO_ERROR_EXIST)
					{
						//设置session
						request.getSession().setAttribute(Constants.USER_ID, entity.getUserID());
						request.getSession().setAttribute(Constants.IDENTITY, entity.getIdentity());
						request.getSession().setAttribute(Constants.NAME, entity.getName());
						request.getSession().setAttribute(Constants.SCHOOL_ID, entity.getSchoolID());
						request.getSession().setAttribute(Constants.ACADEMY_ID, entity.getAcademyID());	
						request.getSession().setAttribute(Constants.MAJOR_ID, entity.getMajorID());
						request.getSession().setAttribute(Constants.CLASS_ID, entity.getClassID());
						
					}
					
					//返回json：状态，信息，url
					JSONObject data=new JSONObject();
					data.element("status", code==Constants.NO_ERROR_EXIST?true:false);
					data.element("msg", code==Constants.NO_ERROR_EXIST?JSONUtil.beanToJson(
							new UserRespForm(entity)):ERRORUtil.message(code));
					
					ControllerUtil.out(response, data);
				}else{

					UserReqForm reqForm=new UserReqForm();
					reqForm.setOpen_id(jsonObject.getOpenid());
					List<Object> objects=new ArrayList<Object>();
					code = userService.getUserCollections(reqForm,objects,0,-1);
					UserRespForm user = null;
					if(objects.size()==0){
						code=Constants.USER_ACCPUNT_NOT_EXIST;
					}else{
						user = (UserRespForm) objects.get(0);
						
						//设置session
						request.getSession().setAttribute(Constants.USER_ID,user.getUser_id());
						request.getSession().setAttribute(Constants.IDENTITY, user.getIdentity());
						request.getSession().setAttribute(Constants.NAME, user.getUser_name());
						request.getSession().setAttribute(Constants.SCHOOL_ID, user.getSchool_id());
						request.getSession().setAttribute(Constants.ACADEMY_ID, user.getAcademy_id());	
						request.getSession().setAttribute(Constants.MAJOR_ID, user.getMajor_id());
						request.getSession().setAttribute(Constants.CLASS_ID, user.getClass_id());
					}
					
					
					
					//返回json：状态，信息，url
					JSONObject data=new JSONObject();
					data.element("status", code==Constants.NO_ERROR_EXIST?true:false);
					data.element("msg", code==Constants.NO_ERROR_EXIST?JSONUtil.beanToJson(user):ERRORUtil.message(code));
					
					ControllerUtil.out(response, data);
				}
			}
			
			
			
			
			
		}else{
			
			code=Constants.LOGIN_WAY_ERROR;
		}
		
		
	} 
	

	public static int getSessionKeyOrOpenId(String code,WeCahtLoginRespForm jsonObject){
	    //微信端登录code
	    String wxCode = code;
	    String requestUrl = "https://api.weixin.qq.com/sns/jscode2session";
	    Map<String,String> requestUrlParam = new HashMap<String, String>(  );
	    requestUrlParam.put( "appid","wx21a0dc26566af158" );//小程序appId
	    requestUrlParam.put( "secret","07d9754d022cd25b613622bf804ce7f7" );
	    requestUrlParam.put( "js_code",wxCode );//小程序端返回的code
	    requestUrlParam.put( "grant_type","authorization_code" );//默认参数
	    String json=  Utils.sendPost( requestUrl,requestUrlParam );
	   
	    //发送post请求读取调用微信接口获取openid用户唯一标识
	    jsonObject = (WeCahtLoginRespForm) JSONUtil.jsonToBean(json, WeCahtLoginRespForm.class);
	    if(jsonObject.getErrcode()!=null){
	    	return Constants.OPENID_ERROR;
	    }else{
	    	return Constants.NO_ERROR_EXIST;
	    }
	}

	
}



