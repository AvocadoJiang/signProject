package com.es.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.es.dao.StusignDao;
import com.es.entity.Stusign;
import com.es.globle.Constants;
import com.es.redis.CachePackager;
import com.es.redis.RedisService;
import com.es.utils.Packager;

@Service("stusignDao")
public class StusignDaoImpl extends JDBCBase implements StusignDao {

	@Override
	public int getStusignByID(Stusign stusign, String stusignID) {
		int code=Constants.NO_ERROR_EXIST;
		Object object=null;
		//去redis中根据ID找对象
		object=RedisService.get("orm/stusign/entity/"+stusignID);
		if(object!=null)
		{
			CachePackager.packStusignCache(stusign, object);
		}else
		{
			Connection conn=JDBCUtil.getConnection();
			PreparedStatement ps=null;
			ResultSet rs=null;
			StringBuilder sql=new StringBuilder("select * from tb_stusign where stusignID = ?");
			Object[] params={
				stusignID,
			};
			try {
				ps=conn.prepareStatement(sql.toString());
				rs=query(ps, params);
				if(rs.next())
				{
					code=Packager.packStusign(stusign, rs);
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
	public int getStusignCollections(List<Stusign> stusigns) {
		int code=Constants.NO_ERROR_EXIST;
		List<Object> objects=RedisService.getbat("orm/stusign/entity/");
		if(objects!=null&&!objects.isEmpty())
		{
			for(Object object:objects)
			{
				stusigns.add((Stusign)object);
			}
		}else
		{
			Connection conn=JDBCUtil.getConnection();
			PreparedStatement ps=null;
			ResultSet rs=null;
			StringBuilder sql=new StringBuilder("select * from tb_stusign");
			Object[] params={};
			try {
				ps=conn.prepareStatement(sql.toString());
				rs=query(ps, params);
				Stusign stusign=null;
				//缓存的数据集
				while(rs.next()&&code==Constants.NO_ERROR_EXIST)
				{
					stusign=new Stusign();
					code=Packager.packStusign(stusign, rs);
					//获取图片
					if(code==Constants.NO_ERROR_EXIST)
					{
						stusigns.add(stusign);
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return code;
	}

	@Override
	public int saveOrUpdate(Stusign stusign) {
		int code=Constants.NO_ERROR_EXIST;
		StringBuilder sql=new StringBuilder("insert into tb_stusign "
				+ "(stuSignID, studentID, lessonID, stuSignStatus) values(?,?,?,?) on duplicate key update stuSignID=?");
		List<Object> params=new ArrayList<Object>();
		params.add(stusign.getStuSignID());
		params.add(stusign.getStudentID());
		params.add(stusign.getLessonID());
		params.add(stusign.getStuSignStatus());
		params.add(stusign.getStuSignID());
		
		if(stusign.getStuSignStatus()!=null)
		{
			sql.append(", stuSignStatus=?");
			params.add(stusign.getStuSignStatus());
		}
		if(!saveOrUpdateOrDelete(sql.toString(), params.toArray()))
		{
			code=Constants.DB_SAVE_ERROR;
		}
		return code;
	}

}
