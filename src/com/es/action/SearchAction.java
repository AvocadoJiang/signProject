package com.es.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.es.entity.Operation;
import com.es.entity.Operation.OPERATION;
import com.es.entity.User;
import com.es.entity.User.USER_IDENTITY;
import com.es.globle.Constants;
import com.es.json.form.request.AcademyReqForm;
import com.es.json.form.request.ClassReqForm;
import com.es.json.form.request.CourseReqForm;
import com.es.json.form.request.LessonReqForm;
import com.es.json.form.request.MajorReqForm;
import com.es.json.form.request.OrderReqForm;
import com.es.json.form.request.SchoolReqForm;
import com.es.json.form.request.StusignReqForm;
import com.es.json.form.request.UserReqForm;
import com.es.service.AcademyService;
import com.es.service.ClassService;
import com.es.service.CourseService;
import com.es.service.LessonService;
import com.es.service.MajorService;
import com.es.service.OrderService;
import com.es.service.SchoolService;
import com.es.service.StusignService;
import com.es.service.UserService;
import com.es.utils.ControllerUtil;
import com.es.utils.ERRORUtil;
import com.es.utils.JSONUtil;

import net.sf.json.JSONObject;

/**
 * @author Anson<br>
 *	Copyright by EasyShare 2017<br>
 *  
 *  All right reserved<br>
 *
 *  Created on 2017年12月31日 下午8:37:57<br>
 *  服务内容检索组件<br>
 */
@Component
public class SearchAction extends HttpServlet {

	@Autowired
	SchoolService schoolService;
	@Autowired
	AcademyService academyService;
	@Autowired
	MajorService majorService;
	@Autowired
	ClassService classService;
	@Autowired
	CourseService courseService;
	@Autowired
	LessonService lessonService;
	@Autowired
	OrderService orderService;
	@Autowired
	StusignService stusignService;
	@Autowired
	UserService userService;
	/**
	 * 
	 */
	private static final long serialVersionUID = 7417015006105952399L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String type=request.getParameter("type").trim();
		getSobj(request, response, Operation.getOperation(type));
	}
	
	/**
	 * 获取服务检索对象
	 * @param request
	 * 		request
	 * @param response
	 * 		response
	 * @param operation
	 * 		操作类型
	 */
	private void getSobj(HttpServletRequest request, HttpServletResponse response, Enum<OPERATION> operation) {
		int code=Constants.NO_ERROR_EXIST;
		
		//获取用户身份
		//USER_IDENTITY identity =  User.getUserTypeEnum((String)(request.getSession().getAttribute("identity")));
		String jsonString=request.getParameter("data").trim();
		Integer start=(request.getParameter("start")==null||request.getParameter("start").trim()=="")?0:Integer.valueOf(request.getParameter("start"));
		Integer end=(request.getParameter("end")==null||request.getParameter("end").trim()=="")?-1:Integer.valueOf(request.getParameter("end"));
		List<Object> objects=new ArrayList<Object>();
		
//		if(!(identity==USER_IDENTITY.ROOT||identity==USER_IDENTITY.ADMIN||identity==USER_IDENTITY.TEACHER||identity==USER_IDENTITY.STUDENT))
//		{
//			code=Constants.INVALID_REQUEST;
//		}
		if(code==Constants.NO_ERROR_EXIST)
		{
			if(operation.equals(Operation.OPERATION.SEARCH_SCHOOL)){
				SchoolReqForm reqForm=(SchoolReqForm) JSONUtil.jsonToBean(jsonString, SchoolReqForm.class);
				code = schoolService.getSchoolCollections(reqForm,objects,start,end);
			}
			if(operation.equals(Operation.OPERATION.SEARCH_ACADEMY)){
				AcademyReqForm reqForm=(AcademyReqForm) JSONUtil.jsonToBean(jsonString, AcademyReqForm.class);
				code = academyService.getAcademyCollections(reqForm,objects,start,end);
				System.out.println(objects);
			}
			
			if(operation.equals(Operation.OPERATION.SEARCH_MAJOR)){
				MajorReqForm reqForm=(MajorReqForm) JSONUtil.jsonToBean(jsonString, MajorReqForm.class);
				code = majorService.getMajorCollections(reqForm,objects,start,end);
				System.out.println(objects);
			}
			if(operation.equals(Operation.OPERATION.SEARCH_CLASS)){
				ClassReqForm reqForm=(ClassReqForm) JSONUtil.jsonToBean(jsonString, ClassReqForm.class);
				code = classService.getClassCollections(reqForm,objects,start,end);
				System.out.println(objects);
			}
			if(operation.equals(Operation.OPERATION.SEARCH_COURSE)){
				CourseReqForm reqForm=(CourseReqForm) JSONUtil.jsonToBean(jsonString, CourseReqForm.class);
				code = courseService.getCourseCollections(reqForm,objects,start,end);
			}
			if(operation.equals(Operation.OPERATION.SEARCH_LESSON)){
				LessonReqForm reqForm=(LessonReqForm) JSONUtil.jsonToBean(jsonString, LessonReqForm.class);
				System.out.println(reqForm);
				code = lessonService.getLessonCollections(reqForm,objects,start,end);
				System.out.println(objects);
			}
			if(operation.equals(Operation.OPERATION.SEARCH_ORDER)){
				OrderReqForm reqForm=(OrderReqForm) JSONUtil.jsonToBean(jsonString, OrderReqForm.class);
				code = orderService.getOrderCollections(reqForm,objects,start,end);
			}
			if(operation.equals(Operation.OPERATION.SEARCH_STUSIGN)){
				StusignReqForm reqForm=(StusignReqForm) JSONUtil.jsonToBean(jsonString, StusignReqForm.class);
				code = stusignService.getStusignCollections(reqForm,objects,start,end);
			}
			if(operation.equals(Operation.OPERATION.SEARCH_USER)){
				
				UserReqForm reqForm=(UserReqForm) JSONUtil.jsonToBean(jsonString, UserReqForm.class);
				code = userService.getUserCollections(reqForm,objects,start,end);
			}
		}
		//返回数据
		JSONObject data=new JSONObject();
		data.element("status", code==Constants.NO_ERROR_EXIST?true:false);
		data.element("msg", code==Constants.NO_ERROR_EXIST?JSONUtil.beanToJson(objects):ERRORUtil.message(code));

		ControllerUtil.out(response, data);
		
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
