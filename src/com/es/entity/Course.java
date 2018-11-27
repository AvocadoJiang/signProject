package com.es.entity;

import java.io.Serializable;
import java.util.Date;


/**
 * @author Jason
 *	Copyright by EasyShare 2018
 *  
 *  All right reserved
 *
 *  Created on 2018��2��24�� ����2:05:34
 *  
 *  �γ̶���ʵ����
 */
public class Course implements Serializable,Comparable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** �γ̵�ID **/
	private String courseID = null;
	/** ��ʦID **/
	private String teacherID = null;
	/** ѧԺID **/
	private String academyID = null;
	/** �γ����� **/
	private String courseName = null;
	/** ����ʱ�� **/
	private Date createTime = null;
	/** �γ��Ƿ�ɾ�� **/
	private int courseStatus = 0;
	
	public Course() {
		super();
	}
	
	public static enum COURSE_STATUS
	{
		/** �ÿγ�����ʹ���� **/
		USAGE,
		/** ��ɾ���ÿγ� **/
		DELETE
	}
	


	@Override
	public String toString() {
		return "Course [courseID=" + courseID + ", teacherID=" + teacherID + ", academyID=" + academyID
				+ ", courseName=" + courseName + ", createTime=" + createTime + ", courseStatus=" + courseStatus + "]";
	}


	public Course(String courseID, String teacherID, String academyID, String courseName, Date createTime,
			int courseStatus) {
		super();
		this.courseID = courseID;
		this.teacherID = teacherID;
		this.academyID = academyID;
		this.courseName = courseName;
		this.createTime = createTime;
		this.courseStatus = courseStatus;
	}


	public int getCourseStatus() {
		return courseStatus;
	}


	public void setCourseStatus(int courseStatus) {
		this.courseStatus = courseStatus;
	}


	public String getCourseID() {
		return courseID;
	}

	public void setCourseID(String courseID) {
		this.courseID = courseID;
	}

	public String getTeacherID() {
		return teacherID;
	}

	public void setTeacherID(String teacherID) {
		this.teacherID = teacherID;
	}

	public String getAcademyID() {
		return academyID;
	}

	public void setAcademyID(String academyID) {
		this.academyID = academyID;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	@Override
	public int compareTo(Object arg0) {
		Course object = (Course)arg0;
		
		return this.getCourseName().compareTo(object.getCourseName());
	}
	
	
}
