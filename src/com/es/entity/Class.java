package com.es.entity;

import java.io.Serializable;


/**
 * @author Jason
 *	Copyright by EasyShare 2018
 *  
 *  All right reserved
 *
 *  Created on 2018��2��25�� ����4:24:30
 *  
 *  �༶����ʵ����
 */
public class Class implements Serializable,Comparable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** �༶��ID **/
	private String classID = null;
	/** �꼶 **/
	private int grade = 0;
	/** רҵ��ID **/
	private String majorID = null;
	/** �༶������ **/
	private String className = null;
	/** �༶��״̬ **/
	private int classStatus = CLASS_STATUS.USAGE.ordinal();
	/** �༶״̬ **/
	public static enum CLASS_STATUS
	{
		/** �ð༶����ʹ���� **/
		USAGE,
		/** ��ɾ����ѧУ **/
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
