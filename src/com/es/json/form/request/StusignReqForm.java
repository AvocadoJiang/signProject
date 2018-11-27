package com.es.json.form.request;

import java.io.Serializable;
import com.es.entity.Stusign.STU_SIGN_STATUS;

/**
 * @author Anson
 *	Copyright by EasyShare 2017
 *  
 *  All right reserved
 *
 *  Created on 2017��6��25�� ����4:36:07
 *  
 *  �û���Ϣ������<br>
 */
public class StusignReqForm implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** ѧ��ǩ����ID **/
	private String stusign_id = null;
	/** ѧ����ID **/
	private String stu_id = null;
	/** ��ʱ��ID **/
	private String lesson_id = null;
	/** ѧ��ǩ��״̬��ID **/
	private String sign_status = null;
	
	public String getStusign_id() {
		return stusign_id;
	}
	public void setStusign_id(String stusign_id) {
		this.stusign_id = stusign_id;
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
	
	public StusignReqForm(String stusign_id, String stu_id, String lesson_id, String sign_status) {
		super();
		this.stusign_id = stusign_id;
		this.stu_id = stu_id;
		this.lesson_id = lesson_id;
		this.sign_status = sign_status;
	}
	@Override
	public String toString() {
		return "StusignReqForm [stusign_id=" + stusign_id + ", stu_id=" + stu_id + ", lesson_id=" + lesson_id
				+ ", sign_status=" + sign_status + "]";
	}
	public StusignReqForm() {
		super();
	}
	
}
