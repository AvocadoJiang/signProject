package com.es.json.form.request;

import java.io.Serializable;
import java.util.Date;

import com.es.entity.Lesson;

/**
 * @author Jason
 *	Copyright by EasyShare 2018
 *  
 *  All right reserved
 *
 *  Created on 2018��3��3��
 *  
 *  �γ���Ϣ������<br>
 */ 
public class LessonReqForm implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** ǩ����ID **/
	private String lesson_id = null;
	/** �γ���Ϣ **/
	private String course_id = null;
	/** ��ʦ��Ϣ **/
	private String teacher_id = null;
	/** ǩ����ʼʱ�� **/
	private Date start_time = null;
	/** ǩ������ʱ�� **/
	private Date end_time = null;
	/** ǩ��״̬ **/
	private String lesson_status = null;
	
	public String getLesson_id() {
		return lesson_id;
	}
	public void setLesson_id(String lesson_id) {
		this.lesson_id = lesson_id;
	}
	public String getCourse_id() {
		return course_id;
	}
	public void setCourse_id(String course_id) {
		this.course_id = course_id;
	}
	public Date getStart_time() {
		return start_time;
	}
	public void setStart_time(Date start_time) {
		this.start_time = start_time;
	}
	public Date getEnd_time() {
		return end_time;
	}
	public void setEnd_time(Date end_time) {
		this.end_time = end_time;
	}
	public String getLesson_status() {
		return lesson_status;
	}
	public void setLesson_status(String lesson_status) {
		this.lesson_status = lesson_status;
	}
	public String getTeacher_id() {
		return teacher_id;
	}
	public void setTeacher_id(String teacher_id) {
		this.teacher_id = teacher_id;
	}
	public LessonReqForm(String lesson_id, String course_id, String teacher_id, Date start_time, Date end_time,
			String lesson_status) {
		super();
		this.lesson_id = lesson_id;
		this.course_id = course_id;
		this.teacher_id = teacher_id;
		this.start_time = start_time;
		this.end_time = end_time;
		this.lesson_status = lesson_status;
	}
	@Override
	public String toString() {
		return "LessonReqForm [lesson_id=" + lesson_id + ", course_id=" + course_id + ", teacher_id=" + teacher_id
				+ ", start_time=" + start_time + ", end_time=" + end_time + ", lesson_status=" + lesson_status + "]";
	}
	public LessonReqForm() {
		super();
	}
	
	
	
	
}
