package com.es.json.form.response;

import java.io.Serializable;
import com.es.entity.School;

/**
 * @author Anson<br>
 *	Copyright by EasyShare 2017<br>
 *  
 *  All right reserved<br>
 *
 *  Created on 2017��12��26�� ����3:30:56<br>
 *  ������Ӧ����<br>
 */
public class SchoolRespForm implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** ѧУ��ID **/
	private String school_id;
	/** ѧУ�� **/
	private String school_name;
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
	
	public SchoolRespForm(String school_id, String school_name) {
		super();
		this.school_id = school_id;
		this.school_name = school_name;
	}
	public SchoolRespForm() {
		super();
	}
	@Override
	public String toString() {
		return "SchoolRespForm [school_id=" + school_id + ", school_name=" + school_name + "]";
	}
	
	public SchoolRespForm(School school) {
		super();
		this.school_id = school.getSchoolID();
		this.school_name = school.getSchoolName();
		
	}
	

	
}
