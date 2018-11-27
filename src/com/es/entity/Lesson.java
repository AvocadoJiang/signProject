package com.es.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Jason
 *	Copyright by EasyShare 2018
 *  
 *  All right reserved
 *
 *  Created on 2018��2��24�� ����4:24:54
 *  
 *  �γ̶���ʵ����
 */
public class Lesson implements Serializable,Comparable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** ǩ����ID **/
	private String lessonID = null;
	/** �γ���Ϣ **/
	private String courseID = null;
	/** ǩ����ʼʱ�� **/
	private Date lessonStartTime = null;
	/** ǩ������ʱ�� **/
	private Date lessonEndTime = null;
	/** ǩ��״̬ **/
	private String lessonSignStatus = LESSON_SIGN_STATUS.SIGN_UNSTART.name();
	
	/** ǩ��״̬ȡֵ **/
	public static enum LESSON_SIGN_STATUS
	{
		/** ǩ����δ��ʼ **/
		SIGN_UNSTART,
		/** ����ǩ�������� **/
		NORMAL_SIGN_STARTING,
		/** �ٵ�ǩ�������� **/
		LATE_SIGN_STARTING,
		/** ǩ����� **/
		SIGN_COMPLETED,
		/** ǩ��ȡ�� **/
		SIGN_CANCELED
	}
	
	/**
	 * ��ȡǩ��״̬ö�ٲ�������
	 * @param status
	 * 		ǩ��״̬��������ֵ
	 * @return
	 * 		ö�ٱ���
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
