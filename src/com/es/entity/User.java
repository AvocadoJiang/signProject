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
 *  Created on 2018年2月4日 下午4:32:32
 *  
 *  用户对象实体类
 */
public class User implements Serializable,Comparable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 用户的ID **/
	private String userID = null;
	/** 用户的密码 **/
	private String password = null;
	/** 用户的班级ID **/
	private String classID = null;
	/** 用户的专业ID **/
	private String majorID = null;
	/** 用户的学院ID **/
	private String academyID = null;
	/** 用户的学校ID **/
	private String schoolID = null;
	/** 用户的姓名 **/
	private String name = null;
	/** 用户的手机号 **/
	private String phone = null;
	/** 用户的身份 **/
	private String identity = null;
	/** 用户的微信身份编码 **/
	private String openID = null;
	/** 用户的创建时间 **/
	private Date createTime = null;
	/** 用户位置信息的纬度 **/
	private double locationX = 0;
	/** 用户位置信息的经度 **/
	private double locationY = 0;
	/** 用户是否被删除 **/
	private int userStatus = 0;
	
	
	/** 用户账户状态 **/
	public static enum USER_ACCOUNT_STATUS
	{
		/** 该账号正常使用中 **/
		USAGE,
		/** 已删除该账号 **/
		DELETE
	}

	/** 用户账户身份 **/
	public static enum USER_IDENTITY
	{
		/** 学生账号 **/
		STUDENT,
		/** 教师账号 **/
		TEACHER,
		/** 管理员账号 **/
		ADMIN,
		/** 最高管理账号 **/
		ROOT
	}
	
	
	/**
	 * 获取用户身份枚举参数名称
	 * @param utype
	 * 		用户类型请求值
	 * @return
	 * 		枚举变量
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
