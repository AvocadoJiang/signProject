package com.es.json.form.response;

import java.io.Serializable;
import java.util.Date;
import com.es.entity.User;

/**
 * @author Anson<br>
 *	Copyright by EasyShare 2017<br>
 *  
 *  All right reserved<br>
 *
 *  Created on 2017年12月26日 下午3:30:56<br>
 *  请求响应对象<br>
 */
public class UserRespForm implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 用户的ID **/
	private String user_id;
	/** 用户的密码 **/
	private String user_psd;
	/** 用户的学校ID **/
	private String school_id;
	/** 用户的学院ID **/
	private String academy_id;
	/** 用户的专业ID **/
	private String major_id;
	/** 用户的班级ID **/
	private String class_id;
	/** 用户的姓名 **/
	private String user_name;
	/** 用户的手机号 **/
	private String user_phone;
	/** 用户的身份 **/
	private String identity;
	/** 用户的微信身份编码 **/
	private String open_id;
	/** 用户的创建时间 **/
	private Date create_time;
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	
	public String getUser_psd() {
		return user_psd;
	}
	public void setUser_psd(String user_psd) {
		this.user_psd = user_psd;
	}
	public String getAcademy_id() {
		return academy_id;
	}
	public void setAcademy_id(String academy_id) {
		this.academy_id = academy_id;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getSchool_id() {
		return school_id;
	}
	public void setSchool_id(String school_id) {
		this.school_id = school_id;
	}
	public String getUser_phone() {
		return user_phone;
	}
	public void setUser_phone(String user_phone) {
		this.user_phone = user_phone;
	}
	public String getIdentity() {
		return identity;
	}
	public void setIdentity(String identity) {
		this.identity = identity;
	}
	public String getOpen_id() {
		return open_id;
	}
	public void setOpen_id(String open_id) {
		this.open_id = open_id;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	
	public String getMajor_id() {
		return major_id;
	}
	public void setMajor_id(String major_id) {
		this.major_id = major_id;
	}
	public String getClass_id() {
		return class_id;
	}
	public void setClass_id(String class_id) {
		this.class_id = class_id;
	}
	
	public UserRespForm(User user) {
		this.user_id = user.getUserID();
		this.user_psd = user.getPassword();
		this.school_id = user.getSchoolID();
		this.academy_id = user.getAcademyID();
		this.class_id = user.getClassID();
		this.user_name = user.getName();
		this.user_phone = user.getPhone();
		this.identity = user.getIdentity();
		this.open_id = user.getOpenID();
		this.major_id = user.getMajorID();
		this.create_time = user.getCreateTime();
		
	}
	@Override
	public String toString() {
		return "UserRespForm [user_id=" + user_id + ", user_psd=" + user_psd + ", school_id=" + school_id
				+ ", academy_id=" + academy_id + ", major_id=" + major_id + ", class_id=" + class_id + ", user_name="
				+ user_name + ", user_phone=" + user_phone + ", identity=" + identity + ", open_id=" + open_id
				+ ", create_time=" + create_time + "]";
	}
	public UserRespForm(String user_id, String user_psd, String school_id, String academy_id, String major_id,
			String class_id, String user_name, String user_phone, String identity, String open_id, Date create_time) {
		super();
		this.user_id = user_id;
		this.user_psd = user_psd;
		this.school_id = school_id;
		this.academy_id = academy_id;
		this.major_id = major_id;
		this.class_id = class_id;
		this.user_name = user_name;
		this.user_phone = user_phone;
		this.identity = identity;
		this.open_id = open_id;
		this.create_time = create_time;
	}
	public UserRespForm() {
		super();
	}
	

	
}
