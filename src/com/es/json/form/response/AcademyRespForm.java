package com.es.json.form.response;

import java.io.Serializable;
import com.es.entity.Academy;


/**
 * @author Anson<br>
 *	Copyright by EasyShare 2017<br>
 *  
 *  All right reserved<br>
 *
 *  Created on 2017��12��26�� ����3:30:56<br>
 *  ������Ӧ����<br>
 */
public class AcademyRespForm implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** ѧУ��ID **/
	private String academy_id;
	/** ѧУ��ID **/
	private String school_id;
	/** ѧУ�� **/
	private String academy_name;
	
	public AcademyRespForm() {
		super();
	}

	
	
	public AcademyRespForm(String academy_id, String school_id, String academy_name) {
		super();
		this.academy_id = academy_id;
		this.school_id = school_id;
		this.academy_name = academy_name;
	}



	public String getAcademy_id() {
		return academy_id;
	}
	
	

	@Override
	public String toString() {
		return "AcademyRespForm [academy_id=" + academy_id + ", school_id=" + school_id + ", academy_name="
				+ academy_name + "]";
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
	public String getSchool_id() {
		return school_id;
	}
	public void setSchool_id(String school_id) {
		this.school_id = school_id;
	}




	public AcademyRespForm(Academy academy) {
		super();
		this.academy_id = academy.getAcademyID();
		this.school_id = academy.getSchoolID();
		this.academy_name = academy.getAcademyName();
	}
	
}
