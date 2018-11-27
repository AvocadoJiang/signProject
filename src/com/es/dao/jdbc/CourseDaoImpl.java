package com.es.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.es.dao.CourseDao;
import com.es.entity.Course;
import com.es.globle.Constants;
import com.es.redis.CachePackager;
import com.es.redis.RedisService;
import com.es.utils.Packager;

@Service("courseDao")
public class CourseDaoImpl extends JDBCBase implements CourseDao {

	@Override
	public int getCourseByID(Course course, String courseID) {
		int code=Constants.NO_ERROR_EXIST;
		Object object=null;
		//去redis中根据ID找对象
		object=RedisService.get("orm/course/entity/"+courseID);
		if(object!=null)
		{
			CachePackager.packCourseCache(course, object);
		}else
		{
			Connection conn=JDBCUtil.getConnection();
			PreparedStatement ps=null;
			ResultSet rs=null;
			StringBuilder sql=new StringBuilder("select * from tb_course where courseID = ?");
			Object[] params={
				courseID,
			};
			try {
				ps=conn.prepareStatement(sql.toString());
				rs=query(ps, params);
				if(rs.next())
				{
					code=Packager.packCourse(course, rs);
				}else
				{
					code=Constants.INFORMATION_NOT_EXIST;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return code;
	}

	@Override
	public int getCourseCollections(List<Course> courses) {
		int code=Constants.NO_ERROR_EXIST;
		List<Object> objects=RedisService.getbat("orm/course/entity/");
		if(objects!=null&&!objects.isEmpty())
		{
			for(Object object:objects)
			{
				courses.add((Course)object);
			}
		}else
		{
			Connection conn=JDBCUtil.getConnection();
			PreparedStatement ps=null;
			ResultSet rs=null;
			StringBuilder sql=new StringBuilder("select * from tb_course where courseStatus = ?");
			Object[] params={
				Course.COURSE_STATUS.USAGE.ordinal()
			};
			try {
				ps=conn.prepareStatement(sql.toString());
				rs=query(ps, params);
				Course course=null;
				//缓存的数据集
				while(rs.next()&&code==Constants.NO_ERROR_EXIST)
				{
					course=new Course();
					code=Packager.packCourse(course, rs);
					//获取图片
					if(code==Constants.NO_ERROR_EXIST)
					{
						courses.add(course);
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return code;
	}

	@Override
	public int saveOrUpdate(Course course) {
		int code=Constants.NO_ERROR_EXIST;
		StringBuilder sql=new StringBuilder("insert into tb_course "
				+ "(courseID, academyID, teacherID, courseName, createTime, courseStatus) values(?,?,?,?,?,?) on duplicate key update courseID=?");
		List<Object> params=new ArrayList<Object>();
		params.add(course.getCourseID());
		params.add(course.getAcademyID());
		params.add(course.getTeacherID());
		params.add(course.getCourseName());
		params.add(course.getCreateTime());
		params.add(course.getCourseStatus());
		params.add(course.getCourseID());
		
		if(course.getCourseName()!=null)
		{
			sql.append(", courseName=?");
			params.add(course.getCourseName());
		}
		if(course.getTeacherID()!=null)
		{
			sql.append(", teacherID=?");
			params.add(course.getTeacherID());
		}
		if(course.getCourseStatus()!=-1)
		{
			sql.append(", courseStatus=?");
			params.add(course.getCourseStatus());
		}

		if(!saveOrUpdateOrDelete(sql.toString(), params.toArray()))
		{
			code=Constants.DB_SAVE_ERROR;
		}
		return code;
	}

}
