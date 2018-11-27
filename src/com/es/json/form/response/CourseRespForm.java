package com.es.json.form.response;

import java.io.Serializable;
import java.util.Date;
import com.es.entity.Course;

/**
 * @author Anson<br>
 *	Copyright by EasyShare 2017<br>
 *  
 *  All right reserved<br>
 *
 *  Created on 2017年12月26日 下午3:30:56<br>
 *  请求响应对象<br>
 */
public class CourseRespForm implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 课程的ID **/
	private String course_id;
	/** 教师ID **/
	private String teacher_id;
	/** 教师姓名 **/
	private String teacher_name;
	/** 学院ID **/
	private String academy_id;
	/** 课程名称 **/
	private String course_name;
	/** 创建时间 **/
	private Date create_time;
	public String getCourse_id() {
		return course_id;
	}
	public void setCourse_id(String course_id) {
		this.course_id = course_id;
	}
	public String getTeacher_id() {
		return teacher_id;
	}
	public void setTeacher_id(String teacher_id) {
		this.teacher_id = teacher_id;
	}
	public String getAcademy_id() {
		return academy_id;
	}
	public void setAcademy_id(String academy_id) {
		this.academy_id = academy_id;
	}
	public String getCourse_name() {
		return course_name;
	}
	public void setCourse_name(String course_name) {
		this.course_name = course_name;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	
	public String getTeacher_name() {
		return teacher_name;
	}
	public void setTeacher_name(String teacher_name) {
		this.teacher_name = teacher_name;
	}
	@Override
	public String toString() {
		return "CourseRespForm [course_id=" + course_id + ", teacher_id=" + teacher_id + ", teacher_name="
				+ teacher_name + ", academy_id=" + academy_id + ", course_name=" + course_name + ", create_time="
				+ create_time + "]";
	}
	
	public CourseRespForm(String course_id, String teacher_id, String teacher_name, String academy_id,
			String course_name, Date create_time) {
		super();
		this.course_id = course_id;
		this.teacher_id = teacher_id;
		this.teacher_name = teacher_name;
		this.academy_id = academy_id;
		this.course_name = course_name;
		this.create_time = create_time;
	}
	public CourseRespForm() {
		super();
	}
	
	public CourseRespForm(Course course) {
		this.course_id = course.getCourseID();
		this.academy_id = course.getAcademyID();
		this.teacher_id = course.getTeacherID();
		this.course_name = course.getCourseName();
		this.create_time = course.getCreateTime();
	}
	
	
}
