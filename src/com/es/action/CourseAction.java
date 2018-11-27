package com.es.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.util.CellRangeAddressList;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFDataValidationConstraint;
import org.apache.poi.xssf.usermodel.XSSFDataValidationHelper;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartRequest;

import com.es.entity.Academy;
import com.es.entity.Course;
import com.es.entity.Lesson;
import com.es.entity.Operation;
import com.es.entity.Operation.OPERATION;
import com.es.entity.User;
import com.es.entity.User.USER_IDENTITY;
import com.es.excel.ExcelReader;
import com.es.globle.Constants;
import com.es.json.form.request.AcademyReqForm;
import com.es.json.form.request.CourseReqForm;
import com.es.json.form.request.UserReqForm;
import com.es.json.form.response.AcademyRespForm;
import com.es.json.form.response.CourseRespForm;
import com.es.json.form.response.UserRespForm;
import com.es.service.AcademyService;
import com.es.service.CourseService;
import com.es.service.LessonService;
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
 *  添加课程<br>
 */
@Component
public class CourseAction extends HttpServlet{

	
	String filepath = null;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	CourseService courseService;
	@Autowired
	LessonService lessonService;
	@Autowired
	AcademyService academyService;
	@Autowired
	UserService userService;
	

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String type=request.getParameter("type").trim().toUpperCase();
		

		
		filepath = getServletContext().getRealPath("/")+File.separator+"WEB-INF"+File.separator+"uploadfile"; // 上传文件的目录
		request.getSession().setAttribute("identity", USER_IDENTITY.ROOT.name());
		//添加或修改课时信息，或删除时刻信息
		if(type.equals(Operation.OPERATION.UPLOAD.name())){
			//用excel文件上传添加多个课程信息
			addCourseFromExcel(request,response);
		}else if(type.equals(Operation.OPERATION.DOWNLOAD.name())){
			createCourseExcel(request,response);
		}else{
			saveOrUpdate(request, response, Operation.getOperation(type));
		}
		
	} 
	
	private void createCourseExcel(HttpServletRequest request, HttpServletResponse response) throws FileNotFoundException, IOException{
		//数据格式化权限控制
		int code=Constants.NO_ERROR_EXIST;
		
		//获取用户身份
		USER_IDENTITY identity = User.getUserTypeEnum((String)(request.getSession().getAttribute("identity")));
		
		if(!(identity==USER_IDENTITY.ROOT||identity==USER_IDENTITY.ADMIN))
		{
			code=Constants.INVALID_REQUEST;
		}else{
			
			
			UserReqForm userReqForm = new UserReqForm();
			userReqForm.setIdentity(User.USER_IDENTITY.TEACHER.name());
			List<Object> userRespForm = new ArrayList<Object>();
			
			code = userService.getUserCollections(userReqForm, userRespForm,0,-1);
			
			if(code == Constants.NO_ERROR_EXIST){
				List<String> userList= new ArrayList<String>();  
				for(Object user:userRespForm){
					userList.add(((UserRespForm)user).getUser_name());
					
				}
				
				File file = new File(getServletContext().getRealPath("/")+File.separator+"WEB-INF"+File.separator+"uploadfile"+File.separator+"开课测试.xlsx");
				Workbook wb = new XSSFWorkbook(new FileInputStream(file.getAbsoluteFile()));
				Sheet sheet = wb.getSheetAt(0);
				
				
				DataValidationHelper helper = new XSSFDataValidationHelper((XSSFSheet) sheet);  
				String[] userArray = new String[userList.size()];
				
				userList.toArray(userArray);
				XSSFDataValidationConstraint constraintBoolean = new XSSFDataValidationConstraint(userArray);  
				//起始行 终止行 起始列 终止列，行从0开始，列从0开始
				CellRangeAddressList regionsBoolean = new CellRangeAddressList(1,1, 4, 4);   
				DataValidation validationBoolean = helper.createValidation(constraintBoolean, regionsBoolean);   
				sheet.addValidationData(validationBoolean);
				FileOutputStream out=new FileOutputStream(file.getAbsoluteFile()); 
				wb.write(out);  
		        out.close(); 
				InputStream fis = new BufferedInputStream(new FileInputStream(file.getAbsoluteFile()));
		        byte[] buffer = new byte[fis.available()];
		        fis.read(buffer);
		        fis.close();
		        
				
				
		        response.setContentType("application/octet-stream");
		        response.setCharacterEncoding("utf-8");
		        //文件名是中文名需要经过url编码
		        response.setHeader("Content-Disposition","attachment; filename="+URLEncoder.encode("课程开设.xlsx", "UTF-8"));
		        
				response.addHeader("Content-Length", "" + file.length());
				OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
				
		        toClient.write(buffer);
		        toClient.flush();
		        toClient.close();
			}
			
			
		}
	}
	
	private void addCourseFromExcel(HttpServletRequest request, HttpServletResponse response){
		//数据格式化权限控制
		int code=Constants.NO_ERROR_EXIST;
		
		//获取用户身份
		USER_IDENTITY identity = User.getUserTypeEnum((String)(request.getSession().getAttribute("identity")));
		
		if(!(identity==USER_IDENTITY.ROOT||identity==USER_IDENTITY.ADMIN))
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
				List<Course> courses = new ArrayList<Course>();
				List<Lesson> lessons = new ArrayList<Lesson>();
				
				//获取全部学院
		        List<Object> academys = new ArrayList<Object>();
		        
				academyService.getAcademyCollections(new AcademyReqForm(), academys,0,-1);
				
				//获取全部教师
				List<Object> teachers = new ArrayList<Object>();
				UserReqForm userRepFrom = new UserReqForm();
				userRepFrom.setIdentity(User.USER_IDENTITY.TEACHER.name());
				userService.getUserCollections(userRepFrom, teachers,0,-1);
				
				code = excelReader.readExcelCourse(courses,lessons,academys,teachers);
				if(code == Constants.NO_ERROR_EXIST){
					for(Course course:courses){
						courseService.saveOrUpdate(course);
						
					}
					for(Lesson lesson:lessons){
						lessonService.saveOrUpdate(lesson);
						
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
		//获取用户身份
		USER_IDENTITY identity = User.getUserTypeEnum((String)(request.getSession().getAttribute(Constants.IDENTITY)));
		String jsonString=request.getParameter("data").trim();
		CourseReqForm courseReqForm=(CourseReqForm) JSONUtil.jsonToBean(jsonString, CourseReqForm.class);
		Course course=new Course();
		
		//生成对象
		code=courseService.getCourse(course, courseReqForm, operation);
		if(!(identity==USER_IDENTITY.ROOT||identity==USER_IDENTITY.ADMIN))
		{
			code=Constants.INVALID_REQUEST;
		}
		if(code==Constants.NO_ERROR_EXIST)
		{
			//保存或者更新数据
			code=courseService.saveOrUpdate(course);
		}
		//返回详情数据
		JSONObject data=new JSONObject();
		data.element("status", code==Constants.NO_ERROR_EXIST?true:false);
		data.element("msg", code==Constants.NO_ERROR_EXIST?JSONUtil.beanToJson(new CourseRespForm(course)):ERRORUtil.message(code));
		
		ControllerUtil.out(response, data);
	}
	
	


	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
