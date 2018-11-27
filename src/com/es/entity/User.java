package com.es.entity;

import java.io.Serializable;
import java.util.Date;

import com.es.json.form.response.UserRespForm;


/**
 * @author Jason
 *	Copyright by EasyShare 2018
 *  
 *  All right reserved
 *
 *  Created on 2018��2��4�� ����4:32:32
 *  
 *  �û�����ʵ����
 */
public class User implements Serializable,Comparable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** �û���ID **/
	private String userID = null;
	/** �û������� **/
	private String password = null;
	/** �û��İ༶ID **/
	private String classID = null;
	/** �û���רҵID **/
	private String majorID = null;
	/** �û���ѧԺID **/
	private String academyID = null;
	/** �û���ѧУID **/
	private String schoolID = null;
	/** �û������� **/
	private String name = null;
	/** �û����ֻ��� **/
	private String phone = null;
	/** �û������ **/
	private String identity = null;
	/** �û���΢����ݱ��� **/
	private String openID = null;
	/** �û��Ĵ���ʱ�� **/
	private Date createTime = null;
	/** �û�λ����Ϣ��γ�� **/
	private double locationX = 0;
	/** �û�λ����Ϣ�ľ��� **/
	private double locationY = 0;
	/** �û��Ƿ�ɾ�� **/
	private int userStatus = 0;
	
	
	/** �û��˻�״̬ **/
	public static enum USER_ACCOUNT_STATUS
	{
		/** ���˺�����ʹ���� **/
		USAGE,
		/** ��ɾ�����˺� **/
		DELETE
	}

	/** �û��˻���� **/
	public static enum USER_IDENTITY
	{
		/** ѧ���˺� **/
		STUDENT,
		/** ��ʦ�˺� **/
		TEACHER,
		/** ����Ա�˺� **/
		ADMIN,
		/** ��߹����˺� **/
		ROOT
	}
	
	
	/**
	 * ��ȡ�û����ö�ٲ�������
	 * @param utype
	 * 		�û���������ֵ
	 * @return
	 * 		ö�ٱ���
	 */
	public static USER_IDENTITY getUserTypeEnum(String utype)
	{
		return USER_IDENTITY.values()[USER_IDENTITY.valueOf(utype.toUpperCase().trim()).ordinal()];
	}
	
	
	public User() {
		super();
	}


	

	public String getMajorID() {
		return majorID;
	}


	public void setMajorID(String majorID) {
		this.majorID = majorID;
	}




	public String getSchoolID() {
		return schoolID;
	}


	public void setSchoolID(String schoolID) {
		this.schoolID = schoolID;
	}


	public User(String userID, String password, String classID, String majorID, String academyID, String schoolID,
			String name, String phone, String identity, String openID, Date createTime, double locationX,
			double locationY, int userStatus) {
		super();
		this.userID = userID;
		this.password = password;
		this.classID = classID;
		this.majorID = majorID;
		this.academyID = academyID;
		this.schoolID = schoolID;
		this.name = name;
		this.phone = phone;
		this.identity = identity;
		this.openID = openID;
		this.createTime = createTime;
		this.locationX = locationX;
		this.locationY = locationY;
		this.userStatus = userStatus;
	}
	


	@Override
	public String toString() {
		return "User [userID=" + userID + ", password=" + password + ", classID=" + classID + ", majorID=" + majorID
				+ ", academyID=" + academyID + ", schoolID=" + schoolID + ", name=" + name + ", phone=" + phone
				+ ", identity=" + identity + ", openID=" + openID + ", createTime=" + createTime + ", locationX="
				+ locationX + ", locationY=" + locationY + ", userStatus=" + userStatus + "]";
	}


	public String getClassID() {
		return classID;
	}

	public void setClassID(String classID) {
		this.classID = classID;
	}

	

	

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getAcademyID() {
		return academyID;
	}

	public void setAcademyID(String academyID) {
		this.academyID = academyID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public String getOpenID() {
		return openID;
	}

	public void setOpenID(String openID) {
		this.openID = openID;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public double getLocationX() {
		return locationX;
	}

	public void setLocationX(double locationX) {
		this.locationX = locationX;
	}

	public double getLocationY() {
		return locationY;
	}

	public void setLocationY(double locationY) {
		this.locationY = locationY;
	}

	public int getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(int userStatus) {
		this.userStatus = userStatus;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public int compareTo(Object arg0) {
		User object = (User)arg0;
		return this.getUserID().compareTo(object.getUserID());
	}
	
	
	
	
}
