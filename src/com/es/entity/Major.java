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
 *  רҵ����ʵ����
 */
public class Major implements Serializable,Comparable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** רҵ��ID **/
	private String majorID = null;
	/** ѧԺ��ID **/
	private String academyID = null;
	/** רҵ������ **/
	private String majorName = null;
	/** רҵ��״̬ **/
	private int majorStatus = MAJOR_STATUS.USAGE.ordinal();
	/** רҵ״̬ **/
	public static enum MAJOR_STATUS
	{
		/** �ð༶����ʹ���� **/
		USAGE,
		/** ��ɾ����ѧУ **/
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
