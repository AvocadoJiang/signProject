package com.es.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.es.dao.AcademyDao;
import com.es.entity.Academy;
import com.es.globle.Constants;
import com.es.redis.CachePackager;
import com.es.redis.RedisService;
import com.es.utils.Packager;

@Service("academyDao")
public class AcademyDaoImpl extends JDBCBase implements AcademyDao {

	@Override
	public int getAcademyByID(Academy academy, String academyID) {
		int code=Constants.NO_ERROR_EXIST;
		Object object=null;
		//去redis中根据ID找对象
		object=RedisService.get("orm/academy/entity/"+academyID);
		if(object!=null)
		{
			CachePackager.packAcademyCache(academy, object);
		}else
		{
			Connection conn=JDBCUtil.getConnection();
			PreparedStatement ps=null;
			ResultSet rs=null;
			StringBuilder sql=new StringBuilder("select * from tb_academy where academyID = ?");
			Object[] params={
				academyID,
			};
			try {
				ps=conn.prepareStatement(sql.toString());
				rs=query(ps, params);
				if(rs.next())
				{
					code=Packager.packAcademy(academy, rs);
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
	public int getAcademyCollections(List<Academy> academys) {
		int code=Constants.NO_ERROR_EXIST;
		List<Object> objects=RedisService.getbat("orm/academy/entity/");
		if(objects!=null&&!objects.isEmpty())
		{
			for(Object object:objects)
			{
				academys.add((Academy)object);
			}
		}else
		{
			Connection conn=JDBCUtil.getConnection();
			PreparedStatement ps=null;
			ResultSet rs=null;
			StringBuilder sql=new StringBuilder("select * from tb_academy where academyStatus = ?");
			Object[] params={
				Academy.ACADEMY_STATUS.USAGE.ordinal()
			};
			try {
				ps=conn.prepareStatement(sql.toString());
				rs=query(ps, params);
				Academy academy=null;
				//缓存的数据集
				while(rs.next()&&code==Constants.NO_ERROR_EXIST)
				{
					academy=new Academy();
					code=Packager.packAcademy(academy, rs);
					//获取图片
					if(code==Constants.NO_ERROR_EXIST)
					{
						academys.add(academy);
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return code;
	}

	@Override
	public int saveOrUpdate(Academy academy) {
		int code=Constants.NO_ERROR_EXIST;
		StringBuilder sql=new StringBuilder("insert into tb_academy "
				+ "(academyID, schoolID, academyName, academyStatus) values(?,?,?,?) on duplicate key update academyID=?");
		List<Object> params=new ArrayList<Object>();
		params.add(academy.getAcademyID());
		params.add(academy.getSchoolID());
		params.add(academy.getAcademyName());
		params.add(academy.getAcademyStatus());
		params.add(academy.getAcademyID());
		
		if(academy.getAcademyName()!=null)
		{
			sql.append(", academyName=?");
			params.add(academy.getAcademyName());
		}
		if(academy.getAcademyStatus()!=-1)
		{
			sql.append(", academyStatus=?");
			params.add(academy.getAcademyStatus());
		}
		if(!saveOrUpdateOrDelete(sql.toString(), params.toArray()))
		{
			code=Constants.DB_SAVE_ERROR;
		}
		return code;
	}

}
