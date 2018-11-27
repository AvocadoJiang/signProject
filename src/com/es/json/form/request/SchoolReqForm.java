package com.es.json.form.request;

import java.io.Serializable;


/**
 * @author Anson
 *	Copyright by EasyShare 2017
 *  
 *  All right reserved
 *
 *  Created on 2017年6月25日 下午4:36:07
 *  
 *  用户信息请求类<br>
 */
public class SchoolReqForm implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 学校的ID **/
	private String school_id = null;
	/** 学校名 **/
	private String school_name = null;
	
	public String getSchool_id() {
		return school_id;
	}
	public void setSchool_id(String school_id) {
		this.school_id = school_id;
	}
	public String getSchool_name() {
		return school_name;
	}
	public void setSchool_name(String school_name) {
		this.school_name = school_name;
	}
	
	public SchoolReqForm() {
		super();
	}
	@Override
	public String toString() {
		return "SchoolReqForm [school_id=" + school_id + ", school_name=" + school_name + "]";
	}
	public SchoolReqForm(String school_id, String school_name) {
		super();
		this.school_id = school_id;
		this.school_name = school_name;
	}
	
	
	
	
	
	
}
