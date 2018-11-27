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
 *  ѧԺ����ʵ����
 */
public class Academy implements Serializable,Comparable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** ѧԺ��ID **/
	private String academyID = null;
	/** ѧУ��ID **/
	private String schoolID = null;
	/** ѧԺ������ **/
	private String academyName = null;
	/** ѧУ��״̬ **/
	private int academyStatus = ACADEMY_STATUS.USAGE.ordinal();
	/** ѧԺ״̬ **/
	public static enum ACADEMY_STATUS
	{
		/** ��ѧУ����ʹ���� **/
		USAGE,
		/** ��ɾ����ѧУ **/
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
