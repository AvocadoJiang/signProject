package com.es.json.form.response;

import java.io.Serializable;
import com.es.entity.User;

/**
 * @author Anson<br>
 *	Copyright by EasyShare 2017<br>
 *  
 *  All right reserved<br>
 *
 *  Created on 2017��12��28�� ����9:39:07<br>
 *  ��¼��Ϣ��Ӧ��<br>
 */
public class LoginRespForm implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** �û���� **/
	private String userID;
	/** ��ǰ��¼�û����� **/
	private String name;
	/** ��¼�û�ϵͳ��� **/
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
