package com.es.entity;

import java.io.Serializable;

/**
 * @author Jason
 *	Copyright by EasyShare 2018
 *  
 *  All right reserved
 *
 *  Created on 2018年2月24日 下午9:29:25
 *  
 *  学校对象实体类
 */
public class School implements Serializable,Comparable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 学校的ID **/
	private String schoolID = null;
	/** 学校的名称 **/
	private String schoolName = null;
	/** 学校的状态 **/
	private int schoolStatus = SCHOOL_STATUS.USAGE.ordinal();
	
	/** 学校状态 **/
	public static enum SCHOOL_STATUS
	{
		/** 该学校正常使用中 **/
		USAGE,
		/** 已删除该学校 **/
		DELETE
	}
	
	public String getSchoolID() {
		return schoolID;
	}
	public void setSchoolID(String schoolID) {
		this.schoolID = schoolID;
	}
	public String getSchoolName() {
		return schoolName;
	}
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	
	
	public int getSchoolStatus() {
		return schoolStatus;
	}
	public void setSchoolStatus(int schoolStatus) {
		this.schoolStatus = schoolStatus;
	}
	public School(String schoolID, String schoolName, int schoolStatus) {
		super();
		this.schoolID = schoolID;
		this.schoolName = schoolName;
		this.schoolStatus = schoolStatus;
	}
	@Override
	public String toString() {
		return "School [schoolID=" + schoolID + ", schoolName=" + schoolName + ", schoolStatus=" + schoolStatus + "]";
	}
	public School() {
		super();
	}
	@Override
	public int compareTo(Object arg0) {
		School object = (School)arg0;
		return this.getSchoolName().compareTo(object.getSchoolName());
	}
	
	
}
