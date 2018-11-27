package com.es.json.form.response;

import java.io.Serializable;
import com.es.entity.Major;


/**
 * @author Anson<br>
 *	Copyright by EasyShare 2017<br>
 *  
 *  All right reserved<br>
 *
 *  Created on 2017年12月26日 下午3:30:56<br>
 *  请求响应对象<br>
 */
public class MajorRespForm implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 专业的ID **/
	private String major_id;
	/** 学院的ID **/
	private String academy_id;
	/** 专业名 **/
	private String major_name;
	
	public MajorRespForm(String major_id, String academy_id, String major_name) {
		super();
		this.major_id = major_id;
		this.academy_id = academy_id;
		this.major_name = major_name;
	}

	@Override
	public String toString() {
		return "MajorRespForm [major_id=" + major_id + ", academy_id=" + academy_id + ", major_name=" + major_name
				+ "]";
	}

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

	public MajorRespForm() {
		super();
	}

	public MajorRespForm(Major major){
		super();
		this.major_id = major.getMajorID();
		this.academy_id = major.getAcademyID();
		this.major_name = major.getMajorName();
	}
	
}
