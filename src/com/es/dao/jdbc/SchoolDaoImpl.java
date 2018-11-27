package com.es.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.es.dao.SchoolDao;
import com.es.entity.School;
import com.es.globle.Constants;
import com.es.redis.CachePackager;
import com.es.redis.RedisService;
import com.es.utils.Packager;

@Service("schoolrDao")
public class SchoolDaoImpl extends JDBCBase implements SchoolDao {

	@Override
	public int getSchoolByID(School school, String schoolID) {
		int code=Constants.NO_ERROR_EXIST;
		Object object=null;
		//去redis中根据ID找对象
		object=RedisService.get("orm/school/entity/"+schoolID);
		if(object!=null)
		{
			CachePackager.packSchoolCache(school, object);
		}else
		{
			Connection conn=JDBCUtil.getConnection();
			PreparedStatement ps=null;
			ResultSet rs=null;
			StringBuilder sql=new StringBuilder("select * from tb_school where schoolID = ?");
			Object[] params={
				schoolID,
			};
			try {
				ps=conn.prepareStatement(sql.toString());
				rs=query(ps, params);
				if(rs.next())
				{
					code=Packager.packSchool(school, rs);
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
	public int getSchoolCollections(List<School> schools) {
		int code=Constants.NO_ERROR_EXIST;
		List<Object> objects=RedisService.getbat("orm/school/entity/");
		if(objects!=null&&!objects.isEmpty())
		{
			for(Object object:objects)
			{
				schools.add((School)object);
			}
		}else
		{
			Connection conn=JDBCUtil.getConnection();
			
			PreparedStatement ps=null;
			ResultSet rs=null;
			StringBuilder sql=new StringBuilder("select * from tb_school where schoolStatus = ?");
			Object[] params={
				School.SCHOOL_STATUS.USAGE.ordinal()
			};
			try {
				ps=conn.prepareStatement(sql.toString());
				rs=query(ps, params);
				School school=null;
				//缓存的数据集
				while(rs.next()&&code==Constants.NO_ERROR_EXIST)
				{
					school=new School();
					code=Packager.packSchool(school, rs);
					if(code==Constants.NO_ERROR_EXIST)
					{
						schools.add(school);
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return code;
	}

	@Override
	public int saveOrUpdate(School school) {
		int code=Constants.NO_ERROR_EXIST;
		StringBuilder sql=new StringBuilder("insert into tb_school "
				+ "(schoolID, schoolName, schoolStatus) values(?,?,?) on duplicate key update schoolID=?");
		List<Object> params=new ArrayList<Object>();
		params.add(school.getSchoolID());
		params.add(school.getSchoolName());
		params.add(school.getSchoolStatus());
		params.add(school.getSchoolID());
	
		if(school.getSchoolName()!=null)
		{
			sql.append(", schoolName=?");
			params.add(school.getSchoolName());
		}
		
		if(school.getSchoolStatus()!=-1)
		{
			sql.append(", schoolStatus=?");
			params.add(school.getSchoolStatus());
		}
	
		
		
		if(!saveOrUpdateOrDelete(sql.toString(), params.toArray()))
		{
			code=Constants.DB_SAVE_ERROR;
		}
		return code;
	}

}
