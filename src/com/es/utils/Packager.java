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
 *  Created on 2017年6月25日 下午4:45:18
 *  
 *  数据打包工具类
 */
public class Packager {
	/**
	 * 打包User表数据
	 * @param user
	 * 		待填充的用户对象
	 * @param rs
	 * 		查询所得数据集
	 * @return
	 * 		错误码
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
	 * 打包School表数据
	 * @param school
	 * 		待填充的学校对象
	 * @param rs
	 * 		查询所得数据集
	 * @return
	 * 		错误码
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
	 * 打包Academy表数据
	 * @param academy
	 * 		待填充的学院对象
	 * @param rs
	 * 		查询所得数据集
	 * @return
	 * 		错误码
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
	 * 打包Class表数据
	 * @param c
	 * 		待填充的班级对象
	 * @param rs
	 * 		查询所得数据集
	 * @return
	 * 		错误码
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
	 * 打包Major表数据
	 * @param major
	 * 		待填充的专业对象
	 * @param rs
	 * 		查询所得数据集
	 * @return
	 * 		错误码
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
	 * 打包Course表数据
	 * @param course
	 * 		待填充的课程对象
	 * @param rs
	 * 		查询所得数据集
	 * @return
	 * 		错误码
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
	 * 打包Lesson表数据
	 * @param lesson
	 * 		待填充的课程对象
	 * @param rs
	 * 		查询所得数据集
	 * @return
	 * 		错误码
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
	 * 打包Order表数据
	 * @param order
	 * 		待填充的课程对象
	 * @param rs
	 * 		查询所得数据集
	 * @return
	 * 		错误码
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
	 * 打包Stusign表数据
	 * @param stusign
	 * 		待填充的课程对象
	 * @param rs
	 * 		查询所得数据集
	 * @return
	 * 		错误码
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
