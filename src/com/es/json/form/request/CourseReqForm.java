package com.es.json.form.request;

import java.io.Serializable;

/**
 * @author Anson
 *	Copyright by EasyShare 2017
 *  
 *  All right reserved
 *
 *  Created on 2017年6月25日 下午4:36:07
 *  
 *  课程信息请求类<br>
 */
public class CourseReqForm implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 课程的ID **/
	private String course_id = null;
	/** 教师ID **/
	private String teacher_id = null;
	/** 学院ID **/
	private String academy_id = null;
	/** 课程名称 **/
	private String course_name = null;
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
	@Override
	public String toString() {
		return "CourseReqForm [course_id=" + course_id + ", teacher_id=" + teacher_id + ", academy_id=" + academy_id
				+ ", course_name=" + course_name + "]";
	}
	public CourseReqForm(String course_id, String teacher_id, String academy_id, String course_name) {
		super();
		this.course_id = course_id;
		this.teacher_id = teacher_id;
		this.academy_id = academy_id;
		this.course_name = course_name;
	}
	public CourseReqForm() {
		super();
	}           
	
	
	
	
}
