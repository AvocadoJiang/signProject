package com.es.json.form.response;

import java.io.Serializable;
import com.es.entity.Stusign;

/**
 * @author Anson<br>
 *	Copyright by EasyShare 2017<br>
 *  
 *  All right reserved<br>
 *
 *  Created on 2017年12月26日 下午3:30:56<br>
 *  请求响应对象<br>
 */
public class StusignRespForm implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 学生签到的ID **/
	private String sign_id;
	/** 学生的ID **/
	private String stu_id;
	/** 课时的ID **/
	private String lesson_id;
	/** 学生签到状态的ID **/
	private String sign_status;
	public String getSign_id() {
		return sign_id;
	}
	public void setSign_id(String sign_id) {
		this.sign_id = sign_id;
	}
	public String getStu_id() {
		return stu_id;
	}
	public void setStu_id(String stu_id) {
		this.stu_id = stu_id;
	}
	public String getLesson_id() {
		return lesson_id;
	}
	public void setLesson_id(String lesson_id) {
		this.lesson_id = lesson_id;
	}
	public String getSign_status() {
		return sign_status;
	}
	public void setSign_status(String sign_status) {
		this.sign_status = sign_status;
	}
	@Override
	public String toString() {
		return "StusignRespForm [sign_id=" + sign_id + ", stu_id=" + stu_id + ", lesson_id=" + lesson_id
				+ ", sign_status=" + sign_status + "]";
	}
	public StusignRespForm(String sign_id, String stu_id, String lesson_id, String sign_status) {
		super();
		this.sign_id = sign_id;
		this.stu_id = stu_id;
		this.lesson_id = lesson_id;
		this.sign_status = sign_status;
	}
	public StusignRespForm() {
		super();
	}
	public StusignRespForm(Stusign stusign) {
		super();
		this.sign_id = stusign.getStuSignID();
		this.stu_id = stusign.getStudentID();
		this.lesson_id = stusign.getLessonID();
		this.sign_status = stusign.getStuSignStatus();
	}
	
	
}
