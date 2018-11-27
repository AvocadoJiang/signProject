package com.es.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.es.entity.Course;
import com.es.entity.Lesson;
import com.es.entity.Order;
import com.es.entity.User;
import com.es.globle.Constants;
import com.es.json.form.request.AcademyReqForm;
import com.es.json.form.request.CourseReqForm;
import com.es.json.form.request.OrderReqForm;
import com.es.json.form.request.UserReqForm;
import com.es.json.form.response.AcademyRespForm;
import com.es.json.form.response.UserRespForm;
import com.es.service.AcademyService;
import com.es.service.UserService;
import com.es.service.impl.AcademyServiceImpl;
import com.es.utils.Utils;

/**
 * @author Anson<br>
 *	Copyright by EasyShare 2018<br>
 *  
 *  All right reserved<br>
 *
 *  Created on 2018��1��20�� ����3:15:33<br>
 *  
 *  Excel���ݶ�ȡ����<br>
 *  ����xls��xlsx��ʽ��excel�ļ�<br>
 */
public class ExcelReader {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
    private Workbook wb;
    private Sheet sheet;
    private Row row;
    
    
    //��������
    private Integer[][] lessonTABLE = {
    		{480,1920,3360,4800,6240,10560,12000,13440,14880,16320},
    		{535,1975,3415,4855,6295,10615,12055,13495,14935,16375},
    		{600,2040,3480,4920,6360,10680,12120,13560,15000,16440},
    		{655,2095,3535,4975,6415,10735,12175,13615,15055,16495},
    		{0,0,0,0,0,0,0,0,0,0},
    		{810,2250,3690,5130,6570,10890,12330,13770,15210,16650},
    		{865,2305,3745,5185,6625,10945,12385,13825,15265,16705},
    		{930,2370,3810,5250,6690,11010,12450,13890,15330,16770},
    		{985,2425,3865,5305,6745,11065,12505,13945,15385,16825},
    		{0,0,0,0,0,0,0,0,0,0},
    		{1080,2520,3960,5400,6840,11160,12600,14040,15480,16920},
    		{1135,2575,4015,5455,6895,11215,12655,14095,15535,16975},
    		{1200,2640,3080,5520,6960,11280,12720,14160,15600,17040},
    };

    public ExcelReader(String filepath) {
        if(filepath==null)
        {
            return;
        }
        String ext = filepath.substring(filepath.lastIndexOf("."));
        try 
        {
            InputStream is = new FileInputStream(filepath);
            if(".xls".equals(ext))
            {
                wb = new HSSFWorkbook(is);
            }else if(".xlsx".equals(ext))
            {
                wb = new XSSFWorkbook(is);
            }else
            {
                wb=null;
            }
        } catch (FileNotFoundException e) {
            logger.error("FileNotFoundException", e);
        } catch (IOException e) {
            logger.error("IOException", e);
        }
    }
    
    public ExcelReader(File file) {
    	if(file == null){
    		return;
    	}
    	String filepath = file.getAbsolutePath();
    	
        if(filepath==null)
        {
            return;
        }
        
        String ext = file.getAbsolutePath().substring(filepath.lastIndexOf("."));
        try 
        {
            InputStream is = new FileInputStream(filepath);
            if(".xls".equals(ext))
            {
                wb = new HSSFWorkbook(is);
            }else if(".xlsx".equals(ext))
            {
                wb = new XSSFWorkbook(is);
               
            }else
            {
                wb=null;
            }
        } catch (FileNotFoundException e) {
            logger.error("FileNotFoundException", e);
        } catch (IOException e) {
            logger.error("IOException", e);
        }
    }


    /**
     * ��ȡExcel��������
     * @return int ״̬��
     * @param List<User> �û��б�
     * @throws Exception
     * 		����Ϊ��
     */
    public int readExcelUser(List<User> list,UserReqForm userReqForm){
    	int code = Constants.NO_ERROR_EXIST;
        if(wb==null)
        {
            code = Constants.FILE_NOT_FIND;
        }
        
        User user = null;
        //�õ���һƬ
       
        sheet = wb.getSheetAt(0);
        // �õ�������
        int rowNum = sheet.getPhysicalNumberOfRows();
        
        // ��������Ӧ�ôӵڶ��п�ʼ,��һ��Ϊ��ͷ�ı���
        for (int i = 1; i < rowNum; i++)
        {
        	row = sheet.getRow(i);
        	if(row!=null){
        		user = new User();
            	//�������е�ÿһ��
        		if(row.getCell(0)!=null){
        			row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
        			user.setUserID(row.getCell(0).getStringCellValue());
        		}
        		
        		if(row.getCell(1)!=null){
        			row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
            		user.setName(row.getCell(1).getStringCellValue());
        		}
        		
        		if(row.getCell(2)!=null){
        			row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
            		user.setPhone(row.getCell(2).getStringCellValue());
        		}
        		
        		user.setIdentity(userReqForm.getIdentity());
        		user.setCreateTime(Utils.stringToExactDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())));
        		
        		if(userReqForm.getClass_id()!=null&&userReqForm.getClass_id().trim()!=""){
        			user.setClassID(userReqForm.getClass_id());
        		}
        		if(userReqForm.getAcademy_id()!=null&&userReqForm.getAcademy_id().trim()!=""){
        			user.setAcademyID(userReqForm.getAcademy_id());
        		}
        		if(userReqForm.getSchool_id()!=null&&userReqForm.getSchool_id().trim()!=""){
        			user.setSchoolID(userReqForm.getSchool_id());
        		}
        		if(userReqForm.getMajor_id()!=null&&userReqForm.getMajor_id().trim()!=""){
        			user.setMajorID(userReqForm.getMajor_id());
        		}
        		
        		list.add(user);
        	}
        }
        return code;
    }
    
    /**
     * ��ȡExcel��������
     * @return int ״̬��
     * @param List<Order> �û��б�
     * @throws Exception
     * 		����Ϊ��
     */
    public int readExcelOrder(List<Order> list,OrderReqForm orderReqForm){
    	int code = Constants.NO_ERROR_EXIST;
        if(wb==null)
        {
            code = Constants.FILE_NOT_FIND;
        }
        
        Order order = null;
        //�õ���һƬ
       
        sheet = wb.getSheetAt(0);
        // �õ�������
        int rowNum = sheet.getPhysicalNumberOfRows();
        
        // ��������Ӧ�ôӵڶ��п�ʼ,��һ��Ϊ��ͷ�ı���
        for (int i = 1; i < rowNum; i++)
        {
        	row = sheet.getRow(i);
        	if(row!=null){
        		order = new Order();
            	//�������е�ÿһ��
        		if(row.getCell(0)!=null){
        			row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
        			order.setStudentID(row.getCell(0).getStringCellValue());
        		}
        		
        		
        		
        		if(orderReqForm.getCourse_id()!=null&&orderReqForm.getCourse_id().trim()!=""){
        			order.setCourseID(orderReqForm.getCourse_id());
        		}else{
        			code = Constants.INFO_NOT_COMPLETE;
        		}
        		order.setOrderID(Utils.getGenerateKey());
        		
        		list.add(order);
        	}
        }
        return code;
    }
    
    /**
     * ��ȡExcel��������
     * @return int ״̬��
     * @param List<User> �û��б�
     * @throws Exception
     * 		����Ϊ��
     */
    public int readExcelCourse(List<Course> courses,List<Lesson> lessons,List<Object> academys,List<Object> teachers){
    	int code = Constants.NO_ERROR_EXIST;
        if(wb==null)
        {
            code = Constants.FILE_NOT_FIND;
        }
        
		
		
        Course course = null;
        Lesson lesson = null;
        //����ÿһƬ
        for(int i = 0;i<wb.getNumberOfSheets();i++){
        	sheet = wb.getSheetAt(i);
        	course = new Course();
        	//��ȡѧԺID
        	sheet.getRow(0).getCell(0).setCellType(Cell.CELL_TYPE_STRING);
        	String academyName = sheet.getRow(0).getCell(0).getStringCellValue();
        	
        	for(Object academy : academys){
        		
        		if(academyName.equals(((AcademyRespForm)academy).getAcademy_name())){
        			
        			course.setAcademyID(((AcademyRespForm)academy).getAcademy_id());
        			break;
        		}
        	}
        	
        	if(course.getAcademyID()==null||course.getAcademyID().trim()==""){
        		code = Constants.INFO_NOT_COMPLETE;
        		
        	}
        	
        	
        	//��ȡ��ʦID
        	sheet.getRow(1).getCell(4).setCellType(Cell.CELL_TYPE_STRING);
        	String teacherName = sheet.getRow(1).getCell(4).getStringCellValue();
        	for(Object teacher : teachers){
        		if(teacherName.equals(((UserRespForm)teacher).getUser_name())){
        			course.setTeacherID(((UserRespForm)teacher).getUser_id());
        			break;
        		}
        	}
        	if(course.getTeacherID()==null||course.getTeacherID().trim()==""){
        		code = Constants.INFO_NOT_COMPLETE;
        		
        	}
        	
        	//��ȡ�γ�����
        	sheet.getRow(1).getCell(1).setCellType(Cell.CELL_TYPE_STRING);
        	course.setCourseName(sheet.getRow(1).getCell(1).getStringCellValue());
        	
        	//���ÿγ̴���ʱ��
        	course.setCreateTime(Utils.stringToExactDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())));
    		
        	//���ÿγ�����
        	course.setCourseID(Utils.getGenerateKey());
        	
        	
        	
        	//�����ÿγ̵Ķ�Ӧ��ʱ
        	
        	//��ȡ��ѧʱ��
        	sheet.getRow(1).getCell(7).setCellType(HSSFCell.CELL_TYPE_NUMERIC);
        	Date startTime = sheet.getRow(1).getCell(7).getDateCellValue();
        	Calendar cal = Calendar.getInstance(); 
        	SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        	cal.setTime(startTime);
        	

        	//�ҵ������һ����һ
        	for(int k = 0;k<7;k++){
        		if(2 != cal.get(Calendar.DAY_OF_WEEK)){
        			cal.add(Calendar.DATE, 1);
        		}else{
        			break;
        		}
        	}
        	
        	//��ȡѧʱ
        	sheet.getRow(1).getCell(10).setCellType(HSSFCell.CELL_TYPE_NUMERIC);
        	double lessonNum = sheet.getRow(1).getCell(10).getNumericCellValue();
        
        	
        	
        	//��ȡ
        	
        	
        	//������һ
        	List<Integer> l = new ArrayList<Integer>();
        	for(int k=4;k<17;k++){
        		for(int j=1;j<11;j++){
        			if(sheet.getRow(k).getCell(j).getStringCellValue().equals("��")){
        				l.add(lessonTABLE[k-4][j-1]);
        			}
        		}
        	}
        	
        	for(int k=0;k<l.size();k++){
        		Calendar tempCal = Calendar.getInstance();
            	tempCal.setTime(cal.getTime());
            	
            	tempCal.add(Calendar.MINUTE,l.get(k));
            	
        		for(int j=0;j<lessonNum/l.size();j++){
        			
        			Date tempDate = tempCal.getTime();
        			
        			lesson = new Lesson();
        			lesson.setLessonID(Utils.getGenerateKey());
        			lesson.setCourseID(course.getCourseID());
        			
        			lesson.setLessonStartTime(tempDate);
        			
        			
        			lesson.setLessonEndTime(new Date(tempDate.getTime()+45*60*1000));
        			
        			lessons.add(lesson);
        			
        			tempCal.add(Calendar.DATE,7);
        		}
        		
        	}
        	courses.add(course);
        }
        
        return code;
    }
    
    

    /**
     * ����Cell������������
     * @param cell
     * 		������ĵ�Ԫ��
     * @return Object
     * 		����ʽ���ص�Ԫ����������
     */
    private Object getCellFormatValue(Cell cell) {
        Object cellvalue = "";
        if (cell != null) 
        {
            // �жϵ�ǰCell��Type
            switch (cell.getCellType()) 
            {
                case Cell.CELL_TYPE_NUMERIC:// �����ǰCell��TypeΪNUMERIC
                case Cell.CELL_TYPE_FORMULA:
                {
                    // �жϵ�ǰ��cell�Ƿ�ΪDate
                    if (DateUtil.isCellDateFormatted(cell))
                    {
                    	//����ʽת��
                    	cellvalue=new SimpleDateFormat("yyyy-MM-dd").format(DateUtil.getJavaDate(cell.getNumericCellValue()));
                    } else
                    {
                    	//�����ֿ����ֻ��ŵ��ַ�������
                        cellvalue = new DecimalFormat("#").format(cell.getNumericCellValue());  
                    }
                    break;
                }
                case Cell.CELL_TYPE_STRING:
                	// �����ǰCell��TypeΪSTRING
                    // ȡ�õ�ǰ��Cell�ַ���
                    cellvalue = cell.getRichStringCellValue().getString();
                    break;
                default:// Ĭ�ϵ�Cellֵ
                    cellvalue = "";
            }
        } else 
        {
            cellvalue = "";
        }
        return cellvalue;
    }
}