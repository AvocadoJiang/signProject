package com.es.json.form.response;

import java.io.Serializable;
import com.es.entity.Class;


/**
 * @author Anson<br>
 *	Copyright by EasyShare 2017<br>
 *  
 *  All right reserved<br>
 *
 *  Created on 2017年12月26日 下午3:30:56<br>
 *  请求响应对象<br>
 */
public class ClassRespForm implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 班级的ID **/
	private String class_id;
	/** 专业的ID **/
	private String major_id;
	/** 年级的ID **/
	private int grade;
	/** 班级名 **/
	private String class_name;
	
	public String getClass_id() {
		return class_id;
	}
	public void setClass_id(String class_id) {
		this.class_id = class_id;
	}
	public String getMajor_id() {
		return major_id;
	}
	public void setMajor_id(String major_id) {
		this.major_id = major_id;
	}
	public String getClass_name() {
		return class_name;
	}
	public void setClass_name(String class_name) {
		this.class_name = class_name;
	}
	public int getGrade() {
		return grade;
	}
	public void setGrade(int grade) {
		this.grade = grade;
	}
	public ClassRespForm() {
		super();
	}
	public ClassRespForm(String class_id, String major_id, int grade, String class_name) {
		super();
		this.class_id = class_id;
		this.major_id = major_id;
		this.grade = grade;
		this.class_name = class_name;
	}
	public ClassRespForm(Class c) {
		super();
		this.class_id = c.getClassID();
		this.major_id = c.getMajorID();
		this.grade = c.getGrade();
		this.class_name = c.getClassName();
	}
	@Override
	public String toString() {
		return "ClassRespForm [class_id=" + class_id + ", major_id=" + major_id + ", grade=" + grade + ", class_name="
				+ class_name + "]";
	}
	
}
