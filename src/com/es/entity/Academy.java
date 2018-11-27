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
 *  学院对象实体类
 */
public class Academy implements Serializable,Comparable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 学院的ID **/
	private String academyID = null;
	/** 学校的ID **/
	private String schoolID = null;
	/** 学院的名称 **/
	private String academyName = null;
	/** 学校的状态 **/
	private int academyStatus = ACADEMY_STATUS.USAGE.ordinal();
	/** 学院状态 **/
	public static enum ACADEMY_STATUS
	{
		/** 该学校正常使用中 **/
		USAGE,
		/** 已删除该学校 **/
		DELETE
	}


	public Academy(String academyID, String schoolID, String academyName, int academyStatus) {
		super();
		this.academyID = academyID;
		this.schoolID = schoolID;
		this.academyName = academyName;
		this.academyStatus = academyStatus;
	}



	@Override
	public String toString() {
		return "Academy [academyID=" + academyID + ", schoolID=" + schoolID + ", academyName=" + academyName
				+ ", academyStatus=" + academyStatus + "]";
	}



	public int getAcademyStatus() {
		return academyStatus;
	}



	public void setAcademyStatus(int academyStatus) {
		this.academyStatus = academyStatus;
	}



	public Academy() {
		super();
	}
	
	
	
	public String getAcademyID() {
		return academyID;
	}
	public void setAcademyID(String academyID) {
		this.academyID = academyID;
	}
	public String getSchoolID() {
		return schoolID;
	}
	public void setSchoolID(String schoolID) {
		this.schoolID = schoolID;
	}
	public String getAcademyName() {
		return academyName;
	}
	public void setAcademyName(String academyName) {
		this.academyName = academyName;
	}
	
	@Override
	public int compareTo(Object arg0) {
		Academy academy = (Academy)arg0;
		
		return this.getAcademyName().compareTo(academy.getAcademyName());
	}
	
	
}
