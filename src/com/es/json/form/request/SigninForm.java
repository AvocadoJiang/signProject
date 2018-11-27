package com.es.json.form.request;

import java.io.Serializable;


/**
 * @author Anson
 *	Copyright by EasyShare 2017
 *  
 *  All right reserved
 *
 *  Created on 2017��6��25�� ����4:35:58
 *  
 *  ��¼��Ϣ����<br>
 */
public class SigninForm implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** �û�ID */
	private String user_id;
	/** ���� */
	private String user_psd = null;
	/** ΢�ŵ�¼���贫���code */
	private String code = null;
	
	public String getUser_psd() {
		return user_psd;
	}
	public void setUser_psd(String user_psd) {
		this.user_psd = user_psd;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public SigninForm(String user_id, String user_psd) {
		super();
		this.user_id = user_id;
		this.user_psd = user_psd;
	}
	
	@Override
	public String toString() {
		return "SigninForm [user_id=" + user_id + ", user_psd=" + user_psd + ", code=" + code + "]";
	}
	public SigninForm() {
		super();
	}

	

	
	

	
}
