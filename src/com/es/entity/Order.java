package com.es.entity;

import java.io.Serializable;

/**
 * @author Jason
 *	Copyright by EasyShare 2018
 *  
 *  All right reserved
 *
 *  Created on 2018��2��25�� ����4:27:15
 *  
 *  ѧ��ѡ�ζ���ʵ����
 */
public class Order implements Serializable,Comparable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** ѧ��ѡ�ε�ID **/
	private String orderID = null;
	/** ѧ��ѡ�εĿγ�ID **/
	private String courseID = null;
	/** ѧ��ѡ�ε�ѧ��ID **/
	private String studentID = null;
	/** ѧ��ѡ�ε�״̬ **/
	private int orderStatus = ORDER_STATUS.USAGE.ordinal();
	
	
	/** ѡ��״̬ **/
	public static enum ORDER_STATUS
	{
		/** ��ѡ������ʹ���� **/
		USAGE,
		/** ��ɾ����ѡ�� **/
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
