package com.es.json.form.request;

import java.io.Serializable;

/**
 * @author Jason
 *	Copyright by EasyShare 2017
 *  
 *  All right reserved
 *
 *  Created on 2017��6��25�� ����4:36:07
 *  
 *  �û���Ϣ������<br>
 */
public class AcademyReqForm implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** ѧԺ��ID **/
	private String academy_id = null;
	/** ѧУ��ID **/
	private String school_id = null;
	/** ѧԺ�� **/
	private String academy_name = null;
	
	public String getAcademy_id() {
		return academy_id;
	}
	public void setAcademy_id(String academy_id) {
		this.academy_id = academy_id;
	}
	public String getAcademy_name() {
		return academy_name;
	}
	public void setAcademy_name(String academy_name) {
		this.academy_name = academy_name;
	}
	public AcademyReqForm() {
		super();
	}
	public String getSchool_id() {
		return school_id;
	}
	public void setSchool_id(String school_id) {
		this.school_id = school_id;
	}
	@Override
	public String toString() {
		return "AcademyReqForm [academy_id=" + academy_id + ", school_id=" + school_id + ", academy_name=" + academy_name
				+ "]";
	}
	public AcademyReqForm(String academy_id, String school_id, String academy_name) {
		super();
		this.academy_id = academy_id;
		this.school_id = school_id;
		this.academy_name = academy_name;
	}

	
	
	
	
	
}
