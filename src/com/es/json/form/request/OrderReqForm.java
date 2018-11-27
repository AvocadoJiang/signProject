package com.es.json.form.request;

import java.io.Serializable;

import com.es.entity.Order.ORDER_STATUS;

/**
 * @author Anson
 *	Copyright by EasyShare 2017
 *  
 *  All right reserved
 *
 *  Created on 2017年6月25日 下午4:36:07
 *  
 *  用户信息请求类<br>
 */
public class OrderReqForm implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 学生选课的ID **/
	private String order_id = null;
	/** 学生选课的课程ID **/
	private String course_id = null;
	/** 学生选课的学生ID **/
	private String stu_id = null;
	/** 学生选课的状态 **/
	private int order_status = ORDER_STATUS.USAGE.ordinal();
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
	public int getOrder_status() {
		return order_status;
	}
	public void setOrder_status(int order_status) {
		this.order_status = order_status;
	}
	@Override
	public String toString() {
		return "OrderReqForm [order_id=" + order_id + ", course_id=" + course_id + ", student_id=" + stu_id
				+ ", order_status=" + order_status + "]";
	}
	public OrderReqForm(String order_id, String course_id, String stu_id, int order_status) {
		super();
		this.order_id = order_id;
		this.course_id = course_id;
		this.stu_id = stu_id;
		this.order_status = order_status;
	}
	public OrderReqForm() {
		super();
	}
	
	
}
