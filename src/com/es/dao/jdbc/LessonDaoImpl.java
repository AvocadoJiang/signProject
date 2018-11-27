package com.es.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.es.dao.LessonDao;
import com.es.entity.Lesson;
import com.es.globle.Constants;
import com.es.redis.CachePackager;
import com.es.redis.RedisService;
import com.es.utils.Packager;

@Service("lessonDao")
public class LessonDaoImpl extends JDBCBase implements LessonDao {

	@Override
	public int getLessonByID(Lesson lesson, String lessonID) {
		int code=Constants.NO_ERROR_EXIST;
		Object object=null;
		//去redis中根据ID找对象
		object=RedisService.get("orm/lesson/entity/"+lessonID);
		if(object!=null)
		{
			CachePackager.packLessonCache(lesson, object);
		}else
		{
			Connection conn=JDBCUtil.getConnection();
			PreparedStatement ps=null;
			ResultSet rs=null;
			StringBuilder sql=new StringBuilder("select * from tb_lesson where lessonID = ?");
			Object[] params={
				lessonID,
			};
			try {
				ps=conn.prepareStatement(sql.toString());
				rs=query(ps, params);
				if(rs.next())
				{
					code=Packager.packLesson(lesson, rs);
					
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
	public int getLessonCollections(List<Lesson> lessons) {
		int code=Constants.NO_ERROR_EXIST;
		List<Object> objects=RedisService.getbat("orm/lesson/entity/");
		if(objects!=null&&!objects.isEmpty())
		{
			for(Object object:objects)
			{
				lessons.add((Lesson)object);
			}
		}else
		{
			Connection conn=JDBCUtil.getConnection();
			PreparedStatement ps=null;
			ResultSet rs=null;
			StringBuilder sql=new StringBuilder("select * from tb_lesson");
			Object[] params={};
			try {
				ps=conn.prepareStatement(sql.toString());
				rs=query(ps, params);
				Lesson lesson=null;
				//缓存的数据集
				while(rs.next()&&code==Constants.NO_ERROR_EXIST)
				{
					lesson=new Lesson();
					code=Packager.packLesson(lesson, rs);
					//获取图片
					if(code==Constants.NO_ERROR_EXIST)
					{
						lessons.add(lesson);
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return code;
	}

	@Override
	public int saveOrUpdate(Lesson lesson) {
		int code=Constants.NO_ERROR_EXIST;
		StringBuilder sql=new StringBuilder("insert into tb_lesson "
				+ "(lessonID, courseID, lessonStartTime, lessonEndTime, lessonSignStatus) values(?,?,?,?,?) on duplicate key update lessonID=?");
		List<Object> params=new ArrayList<Object>();
		params.add(lesson.getLessonID());
		params.add(lesson.getCourseID());
		params.add(lesson.getLessonStartTime());
		params.add(lesson.getLessonEndTime());
		params.add(lesson.getLessonSignStatus());
		params.add(lesson.getLessonID());
		
		if(lesson.getLessonStartTime()!=null)
		{
			sql.append(", lessonStartTime=?");
			params.add(lesson.getLessonStartTime());
		}
		if(lesson.getLessonEndTime()!=null)
		{
			sql.append(", lessonEndTime=?");
			params.add(lesson.getLessonEndTime());
		}
		if(lesson.getLessonSignStatus()!=null)
		{
			sql.append(", lessonSignStatus=?");
			params.add(lesson.getLessonSignStatus());
		}
		
		if(!saveOrUpdateOrDelete(sql.toString(), params.toArray()))
		{
			code=Constants.DB_SAVE_ERROR;
		}
		return code;
	}

}
