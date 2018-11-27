package com.es.entity;

import java.io.Serializable;


/**
 * @author Jason
 *	Copyright by EasyShare 2018
 *  
 *  All right reserved
 *
 *  Created on 2018年2月25日 下午4:24:30
 *  
 *  班级对象实体类
 */
public class Class implements Serializable,Comparable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 班级的ID **/
	private String classID = null;
	/** 年级 **/
	private int grade = 0;
	/** 专业的ID **/
	private String majorID = null;
	/** 班级的名称 **/
	private String className = null;
	/** 班级的状态 **/
	private int classStatus = CLASS_STATUS.USAGE.ordinal();
	/** 班级状态 **/
	public static enum CLASS_STATUS
	{
		/** 该班级正常使用中 **/
		USAGE,
		/** 已删除该学校 **/
		DELETE
	}
	public String getClassID() {
		return classID;
	}
	public void setClassID(String classID) {
		this.classID = classID;
	}
	
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public int getClassStatus() {
		return classStatus;
	}
	public void setClassStatus(int classStatus) {
		this.classStatus = classStatus;
	}	
	public String getMajorID() {
		return majorID;
	}
	public void setMajorID(String majorID) {
		this.majorID = majorID;
	}
	public int getGrade() {
		return grade;
	}
	public void setGrade(int grade) {
		this.grade = grade;
	}
	public Class() {
		super();
	}
	
	@Override
	public String toString() {
		return "Class [classID=" + classID + ", grade=" + grade + ", majorID=" + majorID + ", className=" + className + ", classStatus=" + classStatus + "]";
	}
	public Class(String classID, int grade, String majorID, String className, int classStatus) {
		super();
		this.classID = classID;
		this.grade = grade;
		this.majorID = majorID;
		this.className = className;
		this.classStatus = classStatus;
	}
	@Override
	public int compareTo(Object arg0) {
		Class object = (Class)arg0;
		
		return this.getClassName().compareTo(object.getClassName());
	}
	
	
	
}
