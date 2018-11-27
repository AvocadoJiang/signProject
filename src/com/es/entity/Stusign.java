package com.es.entity;

import java.io.Serializable;

/**
 * @author Jason
 *	Copyright by EasyShare 2018
 *  
 *  All right reserved
 *
 *  Created on 2018年2月25日 下午4:31:29
 *  
 *  学生签到对象实体类
 */
public class Stusign implements Serializable,Comparable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 学生签到的ID **/
	private String stuSignID = null;
	/** 学生的ID **/
	private String studentID = null;
	/** 课时的ID **/
	private String lessonID = null;
	/** 学生签到状态的ID **/
	private String stuSignStatus = STU_SIGN_STATUS.NOT_SIGN.name();
	
	/** 用户账户身份 **/
	public static enum STU_SIGN_STATUS
	{
		/** 未签到 **/
		NOT_SIGN,
		/** 正常签到 **/
		NORMAL_SIGN,
		/** 迟到签到 **/
		LATE_SIGN,
		/** 签到请假 **/
		LEAVE_SIGN,
	
	}

	

	@Override
	public String toString() {
		return "Stusign [stuSignID=" + stuSignID + ", studentID=" + studentID + ", lessonID=" + lessonID
				+ ", stuSignStatus=" + stuSignStatus + "]";
	}



	public Stusign() {
		super();
	}



	public Stusign(String stuSignID, String studentID, String lessonID, String stuSignStatus) {
		super();
		this.stuSignID = stuSignID;
		this.studentID = studentID;
		this.lessonID = lessonID;
		this.stuSignStatus = stuSignStatus;
	}



	public String getStuSignID() {
		return stuSignID;
	}

	public void setStuSignID(String stuSignID) {
		this.stuSignID = stuSignID;
	}

	public String getStudentID() {
		return studentID;
	}

	public void setStudentID(String studentID) {
		this.studentID = studentID;
	}

	public String getLessonID() {
		return lessonID;
	}

	public void setLessonID(String lessonID) {
		this.lessonID = lessonID;
	}

	public String getStuSignStatus() {
		return stuSignStatus;
	}

	public void setStuSignStatus(String stuSignStatus) {
		this.stuSignStatus = stuSignStatus;
	}
	@Override
	public int compareTo(Object arg0) {
		Stusign object = (Stusign)arg0;
		return this.getStudentID().compareTo(object.getStudentID());
	}
	

}
