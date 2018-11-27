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
 *  Created on 2018年3月3日
 *  
 *  课程信息请求类<br>
 */ 
public class LessonReqForm implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 签到的ID **/
	private String lesson_id = null;
	/** 课程信息 **/
	private String course_id = null;
	/** 教师信息 **/
	private String teacher_id = null;
	/** 签到开始时间 **/
	private Date start_time = null;
	/** 签到结束时间 **/
	private Date end_time = null;
	/** 签到状态 **/
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
