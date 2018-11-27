package com.es.entity;

import java.io.Serializable;

/**
 * @author Jason
 *	Copyright by EasyShare 2018
 *  
 *  All right reserved
 *
 *  Created on 2018��2��25�� ����4:31:29
 *  
 *  ѧ��ǩ������ʵ����
 */
public class Stusign implements Serializable,Comparable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** ѧ��ǩ����ID **/
	private String stuSignID = null;
	/** ѧ����ID **/
	private String studentID = null;
	/** ��ʱ��ID **/
	private String lessonID = null;
	/** ѧ��ǩ��״̬��ID **/
	private String stuSignStatus = STU_SIGN_STATUS.NOT_SIGN.name();
	
	/** �û��˻���� **/
	public static enum STU_SIGN_STATUS
	{
		/** δǩ�� **/
		NOT_SIGN,
		/** ����ǩ�� **/
		NORMAL_SIGN,
		/** �ٵ�ǩ�� **/
		LATE_SIGN,
		/** ǩ����� **/
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
