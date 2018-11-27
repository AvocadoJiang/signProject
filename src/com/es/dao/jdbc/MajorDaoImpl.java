package com.es.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.es.dao.MajorDao;
import com.es.entity.Major;
import com.es.globle.Constants;
import com.es.redis.CachePackager;
import com.es.redis.RedisService;
import com.es.utils.Packager;

@Service("majorDao")
public class MajorDaoImpl extends JDBCBase implements MajorDao {

	@Override
	public int getMajorByID(Major major, String majorID) {
		int code=Constants.NO_ERROR_EXIST;
		Object object=null;
		//去redis中根据ID找对象
		object=RedisService.get("orm/major/entity/"+majorID);
		if(object!=null)
		{
			CachePackager.packMajorCache(major, object);
		}else
		{
			Connection conn=JDBCUtil.getConnection();
			PreparedStatement ps=null;
			ResultSet rs=null;
			StringBuilder sql=new StringBuilder("select * from tb_major where majorID = ?");
			Object[] params={
				majorID,
			};
			try {
				ps=conn.prepareStatement(sql.toString());
				rs=query(ps, params);
				if(rs.next())
				{
					code=Packager.packMajor(major, rs);
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
	public int getMajorCollections(List<Major> majors) {
		int code=Constants.NO_ERROR_EXIST;
		List<Object> objects=RedisService.getbat("orm/major/entity/");
		if(objects!=null&&!objects.isEmpty())
		{
			for(Object object:objects)
			{
				majors.add((Major)object);
			}
		}else
		{
			Connection conn=JDBCUtil.getConnection();
			PreparedStatement ps=null;
			ResultSet rs=null;
			StringBuilder sql=new StringBuilder("select * from tb_major where majorStatus = ?");
			Object[] params={
				Major.MAJOR_STATUS.USAGE.ordinal()
			};
			try {
				ps=conn.prepareStatement(sql.toString());
				rs=query(ps, params);
				Major major=null;
				//缓存的数据集
				while(rs.next()&&code==Constants.NO_ERROR_EXIST)
				{
					major=new Major();
					code=Packager.packMajor(major, rs);
					//获取图片
					if(code==Constants.NO_ERROR_EXIST)
					{
						majors.add(major);
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return code;
	}

	@Override
	public int saveOrUpdate(Major major) {
		int code=Constants.NO_ERROR_EXIST;
		StringBuilder sql=new StringBuilder("insert into tb_major "
				+ "(majorID, academyID, majorName, majorStatus) values(?,?,?,?) on duplicate key update majorID=?");
		List<Object> params=new ArrayList<Object>();
		params.add(major.getMajorID());
		params.add(major.getAcademyID());
		params.add(major.getMajorName());
		params.add(major.getMajorStatus());
		params.add(major.getMajorID());
		
		if(major.getMajorName()!=null)
		{
			sql.append(", majorName=?");
			params.add(major.getMajorName());
		}
		if(major.getMajorStatus()!=-1)
		{
			sql.append(", majorStatus=?");
			params.add(major.getMajorStatus());
		}
		if(!saveOrUpdateOrDelete(sql.toString(), params.toArray()))
		{
			code=Constants.DB_SAVE_ERROR;
		}
		return code;
	}

}
