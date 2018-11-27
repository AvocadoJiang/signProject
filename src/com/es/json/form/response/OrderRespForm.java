package com.es.json.form.response;

import java.io.Serializable;

import com.es.entity.Order;


/**
 * @author Anson<br>
 *	Copyright by EasyShare 2017<br>
 *  
 *  All right reserved<br>
 *
 *  Created on 2017年12月26日 下午3:30:56<br>
 *  请求响应对象<br>
 */
public class OrderRespForm implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 学生选课的ID **/
	private String order_id;
	/** 学生选课的课程ID **/
	private String course_id;
	/** 学生选课的课程名称 **/
	private String course_name;
	/** 学生选课的学生ID **/
	private String stu_id;
	/** 学生选课的学生姓名 **/
	private String student_name;
	
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getCourse_id() {
		return course_id;
	}
	public void setCourse_id(String course_id) {
		this.course_id = course_id;
	}
	public String getStu_id() {
		return stu_id;
	}
	public void setStu_id(String stu_id) {
		this.stu_id = stu_id;
	}
	
	public String getStudent_name() {
		return student_name;
	}
	public void setStudent_name(String student_name) {
		this.student_name = student_name;
	}
	public String getCourse_name() {
		return course_name;
	}
	public void setCourse_name(String course_name) {
		this.course_name = course_name;
	}
	
	public OrderRespForm(String order_id, String course_id, String course_name, String stu_id, String student_name) {
		super();
		this.order_id = order_id;
		this.course_id = course_id;
		this.course_name = course_name;
		this.stu_id = stu_id;
		this.student_name = student_name;
	}
	@Override
	public String toString() {
		return "OrderRespForm [order_id=" + order_id + ", course_id=" + course_id + ", course_name=" + course_name
				+ ", stu_id=" + stu_id + ", student_name=" + student_name + "]";
	}
	public OrderRespForm() {
		super();
	}
	public OrderRespForm(Order order) {
		super();
		this.order_id = order.getOrderID();
		this.course_id = order.getCourseID();
		this.stu_id = order.getStudentID();
	}
}
