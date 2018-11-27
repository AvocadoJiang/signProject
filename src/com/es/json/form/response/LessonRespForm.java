package com.es.json.form.response;

import java.io.Serializable;
import java.util.Date;
import com.es.entity.Lesson;

/**
 * @author Anson<br>
 *	Copyright by EasyShare 2017<br>
 *  
 *  All right reserved<br>
 *
 *  Created on 2017年12月26日 下午3:30:56<br>
 *  请求响应对象<br>
 */
public class LessonRespForm implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 签到的ID **/
	private String lesson_id;
	/** 课程信息 **/
	private String course_id;
	/** 课程信息 **/
	private String teacher_id;
	/** 教师姓名 **/
	private String teacher_name = null;
	/** 课程名称 **/
	private String course_name = null;
	/** 签到开始时间 **/
	private Date start_time;
	/** 签到结束时间 **/
	private Date end_time;
	/** 签到状态 **/
	private String lesson_status;
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
	public String getTeacher_name() {
		return teacher_name;
	}
	public void setTeacher_name(String teacher_name) {
		this.teacher_name = teacher_name;
	}
	public String getCourse_name() {
		return course_name;
	}
	public void setCourse_name(String course_name) {
		this.course_name = course_name;
	}
	
	public LessonRespForm(String lesson_id, String course_id, String teacher_id, String teacher_name,
			String course_name, Date start_time, Date end_time, String lesson_status) {
		super();
		this.lesson_id = lesson_id;
		this.course_id = course_id;
		this.teacher_id = teacher_id;
		this.teacher_name = teacher_name;
		this.course_name = course_name;
		this.start_time = start_time;
		this.end_time = end_time;
		this.lesson_status = lesson_status;
	}
	@Override
	public String toString() {
		return "LessonRespForm [lesson_id=" + lesson_id + ", course_id=" + course_id + ", teacher_id=" + teacher_id
				+ ", teacher_name=" + teacher_name + ", course_name=" + course_name + ", start_time=" + start_time
				+ ", end_time=" + end_time + ", lesson_status=" + lesson_status + "]";
	}
	public LessonRespForm() {
		super();
	}
	public LessonRespForm(Lesson lesson) {
		this.lesson_id = lesson.getLessonID();
		this.course_id = lesson.getCourseID();
		this.start_time = lesson.getLessonStartTime();
		this.end_time = lesson.getLessonEndTime();
		this.lesson_status = lesson.getLessonSignStatus();
	}
	
}
