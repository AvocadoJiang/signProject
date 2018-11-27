package com.es.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.es.dao.ClassDao;
import com.es.entity.Class;
import com.es.globle.Constants;
import com.es.redis.CachePackager;
import com.es.redis.RedisService;
import com.es.utils.Packager;

@Service("classDao")
public class ClassDaoImpl extends JDBCBase implements ClassDao {

	@Override
	public int getClassByID(Class c, String classID) {
		int code=Constants.NO_ERROR_EXIST;
		Object object=null;
		//去redis中根据ID找对象
		object=RedisService.get("orm/class/entity/"+classID);
		if(object!=null)
		{
			CachePackager.packClassCache(c, object);
		}else
		{
			Connection conn=JDBCUtil.getConnection();
			PreparedStatement ps=null;
			ResultSet rs=null;
			StringBuilder sql=new StringBuilder("select * from tb_class where classID = ?");
			Object[] params={
				classID,
			};
			try {
				ps=conn.prepareStatement(sql.toString());
				rs=query(ps, params);
				if(rs.next())
				{
					code=Packager.packClass(c, rs);
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
	public int getClassCollections(List<Class> classes) {
		int code=Constants.NO_ERROR_EXIST;
		List<Object> objects=RedisService.getbat("orm/class/entity/");
		if(objects!=null&&!objects.isEmpty())
		{
			for(Object object:objects)
			{
				classes.add((Class)object);
			}
		}else
		{
			Connection conn=JDBCUtil.getConnection();
			PreparedStatement ps=null;
			ResultSet rs=null;
			StringBuilder sql=new StringBuilder("select * from tb_class where classStatus = ?");
			Object[] params={
				Class.CLASS_STATUS.USAGE.ordinal()
			};
			try {
				ps=conn.prepareStatement(sql.toString());
				rs=query(ps, params);
				Class c=null;
				//缓存的数据集
				while(rs.next()&&code==Constants.NO_ERROR_EXIST)
				{
					c=new Class();
					code=Packager.packClass(c, rs);
					//获取图片
					if(code==Constants.NO_ERROR_EXIST)
					{
						classes.add(c);
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return code;
	}

	@Override
	public int saveOrUpdate(Class c) {
		int code=Constants.NO_ERROR_EXIST;
		StringBuilder sql=new StringBuilder("insert into tb_class "
				+ "(classID, majorID, grade, className, classStatus) values(?,?,?,?,?) on duplicate key update classID=?");
		List<Object> params=new ArrayList<Object>();
		params.add(c.getClassID());
		params.add(c.getMajorID());
		params.add(c.getGrade());
		params.add(c.getClassName());
		params.add(c.getClassStatus());
		params.add(c.getClassID());
		
		if(c.getClassName()!=null)
		{
			sql.append(", className=?");
			params.add(c.getClassName());
		}
		if(c.getMajorID()!=null)
		{
			sql.append(", majorID=?");
			params.add(c.getMajorID());
		}
		if(c.getGrade()!=0)
		{
			sql.append(", grade=?");
			params.add(c.getGrade());
		}
		if(c.getClassStatus()!=-1)
		{
			sql.append(", classStatus=?");
			params.add(c.getClassStatus());
		}
		if(!saveOrUpdateOrDelete(sql.toString(), params.toArray()))
		{
			code=Constants.DB_SAVE_ERROR;
		}
		return code;
	}

}
