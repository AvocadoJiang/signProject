package com.es.json.form.request;

import java.io.Serializable;

/**
 * @author Jason
 *	Copyright by EasyShare 2018
 *  
 *  All right reserved
 *
 *  Created on 2018��3��4��
 *  
 *  �ۺ���Ϣ������<br>
 */
public class SearchReqForm implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** �������� **/
	private String sobj=null;
	/** �û���Ϣ����������� **/
	private UserReqForm userReqForm=null;
	/** ѧУ��Ϣ����������� **/
	private SchoolReqForm schoolReqForm=null;
	/** ѧԺ��Ϣ����������� **/
	private AcademyReqForm academyReqForm=null;
	/** �γ���Ϣ����������� **/
	private CourseReqForm courseReqForm=null;
	/** ѡ����Ϣ����������� **/
	private OrderReqForm orderReqForm=null;
	/** ��ʱ��Ϣ����������� **/
	private LessonReqForm lessonReqForm=null;
	/** ѧ��ǩ����Ϣ����������� **/
	private StusignReqForm stusignReqForm=null;
	public String getSobj() {
		return sobj;
	}
	public void setSobj(String sobj) {
		this.sobj = sobj;
	}
	public UserReqForm getUserReqForm() {
		return userReqForm;
	}
	public void setUserReqForm(UserReqForm userReqForm) {
		this.userReqForm = userReqForm;
	}
	public SchoolReqForm getSchoolReqForm() {
		return schoolReqForm;
	}
	public void setSchoolReqForm(SchoolReqForm schoolReqForm) {
		this.schoolReqForm = schoolReqForm;
	}
	public AcademyReqForm getAcademyReqForm() {
		return academyReqForm;
	}
	public void setAcademyReqForm(AcademyReqForm academyReqForm) {
		this.academyReqForm = academyReqForm;
	}
	public CourseReqForm getCourseReqForm() {
		return courseReqForm;
	}
	public void setCourseReqForm(CourseReqForm courseReqForm) {
		this.courseReqForm = courseReqForm;
	}
	public OrderReqForm getOrderReqForm() {
		return orderReqForm;
	}
	public void setOrderReqForm(OrderReqForm orderReqForm) {
		this.orderReqForm = orderReqForm;
	}
	public LessonReqForm getLessonReqForm() {
		return lessonReqForm;
	}
	public void setLessonReqForm(LessonReqForm lessonReqForm) {
		this.lessonReqForm = lessonReqForm;
	}
	public StusignReqForm getStusignReqForm() {
		return stusignReqForm;
	}
	public void setStusignReqForm(StusignReqForm stusignReqForm) {
		this.stusignReqForm = stusignReqForm;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "SearchReqForm [sobj=" + sobj + ", userReqForm=" + userReqForm + ", schoolReqForm=" + schoolReqForm
				+ ", academyReqForm=" + academyReqForm + ", courseReqForm=" + courseReqForm + ", orderReqForm="
				+ orderReqForm + ", lessonReqForm=" + lessonReqForm + ", stusignReqForm=" + stusignReqForm + "]";
	}
	public SearchReqForm(String sobj, UserReqForm userReqForm, SchoolReqForm schoolReqForm,
			AcademyReqForm academyReqForm, CourseReqForm courseReqForm, OrderReqForm orderReqForm,
			LessonReqForm lessonReqForm, StusignReqForm stusignReqForm) {
		super();
		this.sobj = sobj;
		this.userReqForm = userReqForm;
		this.schoolReqForm = schoolReqForm;
		this.academyReqForm = academyReqForm;
		this.courseReqForm = courseReqForm;
		this.orderReqForm = orderReqForm;
		this.lessonReqForm = lessonReqForm;
		this.stusignReqForm = stusignReqForm;
	}
	public SearchReqForm() {
		super();
		this.userReqForm = new UserReqForm();
		this.schoolReqForm = new SchoolReqForm();
		this.academyReqForm = new AcademyReqForm();
		this.courseReqForm = new CourseReqForm();
		this.orderReqForm = new OrderReqForm();
		this.lessonReqForm = new LessonReqForm();
		this.stusignReqForm = new StusignReqForm();
	}
	
	
	
	
}
