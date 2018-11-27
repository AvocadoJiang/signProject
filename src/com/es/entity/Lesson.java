package com.es.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Jason
 *	Copyright by EasyShare 2018
 *  
 *  All right reserved
 *
 *  Created on 2018年2月24日 下午4:24:54
 *  
 *  课程对象实体类
 */
public class Lesson implements Serializable,Comparable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 签到的ID **/
	private String lessonID = null;
	/** 课程信息 **/
	private String courseID = null;
	/** 签到开始时间 **/
	private Date lessonStartTime = null;
	/** 签到结束时间 **/
	private Date lessonEndTime = null;
	/** 签到状态 **/
	private String lessonSignStatus = LESSON_SIGN_STATUS.SIGN_UNSTART.name();
	
	/** 签到状态取值 **/
	public static enum LESSON_SIGN_STATUS
	{
		/** 签到还未开始 **/
		SIGN_UNSTART,
		/** 正常签到进行中 **/
		NORMAL_SIGN_STARTING,
		/** 迟到签到进行中 **/
		LATE_SIGN_STARTING,
		/** 签到完成 **/
		SIGN_COMPLETED,
		/** 签到取消 **/
		SIGN_CANCELED
	}
	
	/**
	 * 获取签到状态枚举参数名称
	 * @param status
	 * 		签到状态类型请求值
	 * @return
	 * 		枚举变量
	 */
	public static Enum<LESSON_SIGN_STATUS> getUserTypeEnum(String status)
	{
		return LESSON_SIGN_STATUS.values()[LESSON_SIGN_STATUS.valueOf(status.toUpperCase().trim()).ordinal()];
	}

	public String getLessonID() {
		return lessonID;
	}

	public void setLessonID(String lessonID) {
		this.lessonID = lessonID;
	}

	public String getCourseID() {
		return courseID;
	}

	public void setCourseID(String courseID) {
		this.courseID = courseID;
	}

	public Date getLessonStartTime() {
		return lessonStartTime;
	}

	public void setLessonStartTime(Date lessonStartTime) {
		this.lessonStartTime = lessonStartTime;
	}

	public Date getLessonEndTime() {
		return lessonEndTime;
	}

	public void setLessonEndTime(Date lessonEndTime) {
		this.lessonEndTime = lessonEndTime;
	}

	public String getLessonSignStatus() {
		return lessonSignStatus;
	}

	public void setLessonSignStatus(String lessonSignStatus) {
		this.lessonSignStatus = lessonSignStatus;
	}

	@Override
	public String toString() {
		return "Lesson [lessonID=" + lessonID + ", courseID=" + courseID + ", lessonStartTime=" + lessonStartTime
				+ ", lessonEndTime=" + lessonEndTime + ", lessonSignStatus=" + lessonSignStatus + "]";
	}

	public Lesson(String lessonID, String courseID, Date lessonStartTime, Date lessonEndTime, String lessonSignStatus) {
		super();
		this.lessonID = lessonID;
		this.courseID = courseID;
		this.lessonStartTime = lessonStartTime;
		this.lessonEndTime = lessonEndTime;
		this.lessonSignStatus = lessonSignStatus;
	}

	public Lesson() {
		super();
	}
	
	@Override
	public int compareTo(Object arg0) {
		Lesson object = (Lesson)arg0;
		
		return this.getLessonStartTime().compareTo(object.getLessonStartTime());
	}
	
	
}
