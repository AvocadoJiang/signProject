package com.es.json.form.request;

import java.io.Serializable;

/**
 * @author Jason
 *	Copyright by EasyShare 2017
 *  
 *  All right reserved
 *
 *  Created on 2017年6月25日 下午4:36:07
 *  
 *  班级信息请求类<br>
 */
public class MajorReqForm implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 专业的ID **/
	private String major_id = null;
	/** 学院的ID **/
	private String academy_id = null;
	/** 专业名 **/
	private String major_name = null;
	
	public String getMajor_id() {
		return major_id;
	}

	public void setMajor_id(String major_id) {
		this.major_id = major_id;
	}

	public String getAcademy_id() {
		return academy_id;
	}

	public void setAcademy_id(String academy_id) {
		this.academy_id = academy_id;
	}

	public String getMajor_name() {
		return major_name;
	}

	public void setMajor_name(String major_name) {
		this.major_name = major_name;
	}

	public MajorReqForm(String major_id, String academy_id, String major_name) {
		super();
		this.major_id = major_id;
		this.academy_id = academy_id;
		this.major_name = major_name;
	}

	@Override
	public String toString() {
		return "MajorReqForm [major_id=" + major_id + ", academy_id=" + academy_id + ", major_name=" + major_name + "]";
	}

	public MajorReqForm() {
		super();
	}
}
