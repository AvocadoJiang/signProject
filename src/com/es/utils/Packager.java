package com.es.utils;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.es.entity.Academy;
import com.es.entity.Course;
import com.es.entity.Lesson;
import com.es.entity.Major;
import com.es.entity.Order;
import com.es.entity.Class;
import com.es.entity.School;
import com.es.entity.Stusign;
import com.es.entity.User;
import com.es.globle.Constants;

/**
 * @author Anson
 *	Copyright by EasyShare 2017
 *  
 *  All right reserved
 *
 *  Created on 2017��6��25�� ����4:45:18
 *  
 *  ���ݴ��������
 */
public class Packager {
	/**
	 * ���User������
	 * @param user
	 * 		�������û�����
	 * @param rs
	 * 		��ѯ�������ݼ�
	 * @return
	 * 		������
	 */
	public static int packUser(User user, ResultSet rs) {
		int code=Constants.NO_ERROR_EXIST;
		try {
			user.setUserID(rs.getString("userID"));
			user.setPassword(rs.getString("password"));
			user.setClassID(rs.getString("classID"));
			user.setMajorID(rs.getString("majorID"));
			user.setAcademyID(rs.getString("academyID"));
			user.setSchoolID(rs.getString("schoolID"));
			user.setName(rs.getString("name"));
			user.setPhone(rs.getString("phone"));
			user.setIdentity(rs.getString("identity"));
			user.setOpenID(rs.getString("openID"));
			user.setCreateTime(rs.getDate("createTime"));
			user.setLocationX(rs.getDouble("locationX"));
			user.setLocationY(rs.getDouble("locationY"));
			user.setUserStatus(rs.getInt("userStatus"));
		} catch (SQLException e) {
			code=Constants.DB_GET_ERROR;
		}
		return code;
	}
	
	/**
	 * ���School������
	 * @param school
	 * 		������ѧУ����
	 * @param rs
	 * 		��ѯ�������ݼ�
	 * @return
	 * 		������
	 */
	public static int packSchool(School school, ResultSet rs) {
		int code=Constants.NO_ERROR_EXIST;
		try {
			school.setSchoolID(rs.getString("schoolID"));
			school.setSchoolName(rs.getString("schoolName"));
			school.setSchoolStatus(rs.getInt("schoolStatus"));
			
		} catch (SQLException e) {
			code=Constants.DB_GET_ERROR;
		}
		return code;
	}
	
	/**
	 * ���Academy������
	 * @param academy
	 * 		������ѧԺ����
	 * @param rs
	 * 		��ѯ�������ݼ�
	 * @return
	 * 		������
	 */
	public static int packAcademy(Academy academy, ResultSet rs) {
		int code=Constants.NO_ERROR_EXIST;
		try {
			academy.setAcademyID(rs.getString("academyID"));
			academy.setSchoolID(rs.getString("schoolID"));
			academy.setAcademyName(rs.getString("academyName"));
			academy.setAcademyStatus(rs.getInt("academyStatus"));
			
		} catch (SQLException e) {
			code=Constants.DB_GET_ERROR;
		}
		return code;
	}
	
	
	/**
	 * ���Class������
	 * @param c
	 * 		�����İ༶����
	 * @param rs
	 * 		��ѯ�������ݼ�
	 * @return
	 * 		������
	 */
	public static int packClass(Class c, ResultSet rs) {
		int code=Constants.NO_ERROR_EXIST;
		try {
			c.setMajorID(rs.getString("majorID"));
			c.setGrade(rs.getInt("grade"));
			c.setClassID(rs.getString("classID"));
			c.setClassName(rs.getString("className"));
			c.setClassStatus(rs.getInt("classStatus"));
			
		} catch (SQLException e) {
			code=Constants.DB_GET_ERROR;
		}
		return code;
	}
	
	/**
	 * ���Major������
	 * @param major
	 * 		������רҵ����
	 * @param rs
	 * 		��ѯ�������ݼ�
	 * @return
	 * 		������
	 */
	public static int packMajor(Major major, ResultSet rs) {
		int code=Constants.NO_ERROR_EXIST;
		try {
			major.setMajorID(rs.getString("majorID"));
			major.setAcademyID(rs.getString("academyID"));
			major.setMajorName(rs.getString("majorName"));
			major.setMajorStatus(rs.getInt("majorStatus"));
		} catch (SQLException e) {
			code=Constants.DB_GET_ERROR;
		}
		return code;
	}
	
	/**
	 * ���Course������
	 * @param course
	 * 		�����Ŀγ̶���
	 * @param rs
	 * 		��ѯ�������ݼ�
	 * @return
	 * 		������
	 */
	public static int packCourse(Course course, ResultSet rs) {
		int code=Constants.NO_ERROR_EXIST;
		try {
			course.setCourseID(rs.getString("courseID"));
			course.setAcademyID(rs.getString("academyID"));
			course.setTeacherID(rs.getString("teacherID"));
			course.setCourseName(rs.getString("courseName"));
			course.setCreateTime(rs.getTimestamp("createTime"));
			course.setCourseStatus(rs.getInt("courseStatus"));
		} catch (SQLException e) {
			code=Constants.DB_GET_ERROR;
		}
		return code;
	}
	
	/**
	 * ���Lesson������
	 * @param lesson
	 * 		�����Ŀγ̶���
	 * @param rs
	 * 		��ѯ�������ݼ�
	 * @return
	 * 		������
	 */
	public static int packLesson(Lesson lesson, ResultSet rs) {
		int code=Constants.NO_ERROR_EXIST;
		try {
			lesson.setLessonID(rs.getString("lessonID"));
			lesson.setCourseID(rs.getString("courseID"));
			lesson.setLessonStartTime(rs.getTimestamp("lessonStartTime"));
			lesson.setLessonEndTime(rs.getTimestamp("lessonEndTime"));
			lesson.setLessonSignStatus(rs.getString("lessonSignStatus"));
		} catch (SQLException e) {
			code=Constants.DB_GET_ERROR;
		}
		return code;
	}
	
	/**
	 * ���Order������
	 * @param order
	 * 		�����Ŀγ̶���
	 * @param rs
	 * 		��ѯ�������ݼ�
	 * @return
	 * 		������
	 */
	public static int packOrder(Order order, ResultSet rs) {
		int code=Constants.NO_ERROR_EXIST;
		try {
			order.setOrderID(rs.getString("orderID"));
			order.setCourseID(rs.getString("courseID"));
			order.setStudentID(rs.getString("studentID"));
			order.setOrderStatus(rs.getInt("orderStatus"));
		} catch (SQLException e) {
			code=Constants.DB_GET_ERROR;
		}
		return code;
	}
	
	/**
	 * ���Stusign������
	 * @param stusign
	 * 		�����Ŀγ̶���
	 * @param rs
	 * 		��ѯ�������ݼ�
	 * @return
	 * 		������
	 */
	public static int packStusign(Stusign stusign, ResultSet rs) {
		int code=Constants.NO_ERROR_EXIST;
		try {
			stusign.setStuSignID(rs.getString("stuSignID"));
			stusign.setLessonID(rs.getString("lessonID"));
			stusign.setStudentID(rs.getString("studentID"));
			stusign.setStuSignStatus(rs.getString("stuSignStatus"));
		} catch (SQLException e) {
			code=Constants.DB_GET_ERROR;
		}
		return code;
	}
}
