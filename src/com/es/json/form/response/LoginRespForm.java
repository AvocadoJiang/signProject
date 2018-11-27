package com.es.json.form.response;

import java.io.Serializable;
import com.es.entity.User;

/**
 * @author Anson<br>
 *	Copyright by EasyShare 2017<br>
 *  
 *  All right reserved<br>
 *
 *  Created on 2017年12月28日 下午9:39:07<br>
 *  登录信息响应类<br>
 */
public class LoginRespForm implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 用户编号 **/
	private String userID;
	/** 当前登录用户姓名 **/
	private String name;
	/** 登录用户系统身份 **/
	private String identity;
	
	public LoginRespForm(User user) {
		userID = user.getUserID();
		name = user.getName();
		identity = user.getIdentity();
	}
	
	public LoginRespForm() {
		super();
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}



	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	@Override
	public String toString() {
		return "LoginRespForm [userID=" + userID + ", name=" + name + ", identity="
				+ identity + "]";
	}

}
