package com.es.entity;

import java.io.Serializable;

/**
 * @author Jason
 *	Copyright by EasyShare 2018
 *  
 *  All right reserved
 *
 *  Created on 2018��2��24�� ����9:29:25
 *  
 *  ѧУ����ʵ����
 */
public class School implements Serializable,Comparable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** ѧУ��ID **/
	private String schoolID = null;
	/** ѧУ������ **/
	private String schoolName = null;
	/** ѧУ��״̬ **/
	private int schoolStatus = SCHOOL_STATUS.USAGE.ordinal();
	
	/** ѧУ״̬ **/
	public static enum SCHOOL_STATUS
	{
		/** ��ѧУ����ʹ���� **/
		USAGE,
		/** ��ɾ����ѧУ **/
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
