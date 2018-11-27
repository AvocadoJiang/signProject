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
 *  专业对象实体类
 */
public class Major implements Serializable,Comparable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 专业的ID **/
	private String majorID = null;
	/** 学院的ID **/
	private String academyID = null;
	/** 专业的名称 **/
	private String majorName = null;
	/** 专业的状态 **/
	private int majorStatus = MAJOR_STATUS.USAGE.ordinal();
	/** 专业状态 **/
	public static enum MAJOR_STATUS
	{
		/** 该班级正常使用中 **/
		USAGE,
		/** 已删除该学校 **/
		DELETE
	}
	
	@Override
	public String toString() {
		return "Major [majorID=" + majorID + ", academyID=" + academyID + ", majorName=" + majorName + ", majorStatus="
				+ majorStatus + "]";
	}

	public Major(String majorID, String academyID, String majorName, int majorStatus) {
		super();
		this.majorID = majorID;
		this.academyID = academyID;
		this.majorName = majorName;
		this.majorStatus = majorStatus;
	}

	public String getMajorID() {
		return majorID;
	}

	public void setMajorID(String majorID) {
		this.majorID = majorID;
	}

	public String getAcademyID() {
		return academyID;
	}

	public void setAcademyID(String academyID) {
		this.academyID = academyID;
	}

	public String getMajorName() {
		return majorName;
	}

	public void setMajorName(String majorName) {
		this.majorName = majorName;
	}

	public int getMajorStatus() {
		return majorStatus;
	}

	public void setMajorStatus(int majorStatus) {
		this.majorStatus = majorStatus;
	}

	public Major() {
		super();
	}

	@Override
	public int compareTo(Object arg0) {
		Major major = (Major)arg0;
		
		return this.majorName.compareTo(major.getMajorName());
	}
	
	
	
}
