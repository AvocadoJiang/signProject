package com.es.json.form.response;

import java.io.Serializable;

import com.es.entity.Order;


/**
 * @author Anson<br>
 *	Copyright by EasyShare 2017<br>
 *  
 *  All right reserved<br>
 *
 *  Created on 2017��12��26�� ����3:30:56<br>
 *  ������Ӧ����<br>
 */
public class OrderRespForm implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** ѧ��ѡ�ε�ID **/
	private String order_id;
	/** ѧ��ѡ�εĿγ�ID **/
	private String course_id;
	/** ѧ��ѡ�εĿγ����� **/
	private String course_name;
	/** ѧ��ѡ�ε�ѧ��ID **/
	private String stu_id;
	/** ѧ��ѡ�ε�ѧ������ **/
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
