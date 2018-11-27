package com.es.json.form.request;

import java.io.Serializable;

/**
 * @author Jason
 *	Copyright by EasyShare 2017
 *  
 *  All right reserved
 *
 *  Created on 2017年6月25日 下午4:36:07
 *  
 *  班级信息请求类<br>
 */
public class ClassReqForm implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 班级的ID **/
	private String class_id = null;
	/** 专业的ID **/
	private String major_id = null;
	/** 年级 **/
	private int grade = 0;
	/** 班级名 **/
	private String class_name = null;
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
	
	
	@Override
	public String toString() {
		return "ClassReqForm [class_id=" + class_id + ", major_id=" + major_id + ", grade=" + grade + ", class_name="
				+ class_name + "]";
	}
	public ClassReqForm(String class_id, String major_id, int grade, String class_name) {
		super();
		this.class_id = class_id;
		this.major_id = major_id;
		this.grade = grade;
		this.class_name = class_name;
	}
	public ClassReqForm() {
		super();
	}
}
