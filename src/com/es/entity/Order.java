package com.es.entity;

import java.io.Serializable;

/**
 * @author Jason
 *	Copyright by EasyShare 2018
 *  
 *  All right reserved
 *
 *  Created on 2018年2月25日 下午4:27:15
 *  
 *  学生选课对象实体类
 */
public class Order implements Serializable,Comparable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 学生选课的ID **/
	private String orderID = null;
	/** 学生选课的课程ID **/
	private String courseID = null;
	/** 学生选课的学生ID **/
	private String studentID = null;
	/** 学生选课的状态 **/
	private int orderStatus = ORDER_STATUS.USAGE.ordinal();
	
	
	/** 选课状态 **/
	public static enum ORDER_STATUS
	{
		/** 该选课正常使用中 **/
		USAGE,
		/** 已删除该选课 **/
		DELETE
	}
	
	public Order() {
		super();
	}
	
	public Order(String orderID, String courseID, String studentID, int orderStatus) {
		super();
		this.orderID = orderID;
		this.courseID = courseID;
		this.studentID = studentID;
		this.orderStatus = orderStatus;
	}
	@Override
	public String toString() {
		return "Order [orderID=" + orderID + ", courseID=" + courseID + ", studentID=" + studentID + ", orderStatus="
				+ orderStatus + "]";
	}
	public String getOrderID() {
		return orderID;
	}
	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}
	public int getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(int orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getCourseID() {
		return courseID;
	}
	public void setCourseID(String courseID) {
		this.courseID = courseID;
	}
	public String getStudentID() {
		return studentID;
	}
	public void setStudentID(String studentID) {
		this.studentID = studentID;
	}
	
	@Override
	public int compareTo(Object arg0) {
		Order object = (Order)arg0;
		return this.getStudentID().compareTo(object.getStudentID());
	}
	
	
}
