package com.es.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.es.dao.UserDao;

import com.es.entity.User;
import com.es.globle.Constants;
import com.es.redis.CachePackager;
import com.es.redis.RedisService;
import com.es.utils.Packager;

@Service("userDao")
public class UserDaoImpl extends JDBCBase implements UserDao{

	@Override
	public int getUserByID(User user, String userID) {
		int code=Constants.NO_ERROR_EXIST;
		Object object=null;
		//去redis中根据ID找对象
		object=RedisService.get("orm/user/entity/"+userID);
		if(object!=null)
		{
			CachePackager.packUserCache(user, object);
		}else
		{
			System.out.println("从数据库中读取数据");
			Connection conn=JDBCUtil.getConnection();
			PreparedStatement ps=null;
			ResultSet rs=null;
			StringBuilder sql=new StringBuilder("select * from tb_user where userID = ?");
			Object[] params={
				userID,
			};
			try {
				ps=conn.prepareStatement(sql.toString());
				rs=query(ps, params);
				if(rs.next())
				{
					code=Packager.packUser(user, rs);
					System.out.println(code);
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
	public int getUserCollections(List<User> users) {
		int code=Constants.NO_ERROR_EXIST;
		List<Object> objects=RedisService.getbat("orm/user/entity/");
		if(objects!=null&&!objects.isEmpty())
		{
			for(Object object:objects)
			{
				users.add((User)object);
			}
		}else
		{
			Connection conn=JDBCUtil.getConnection();
			PreparedStatement ps=null;
			ResultSet rs=null;
			StringBuilder sql=new StringBuilder("select * from tb_user where userStatus = ?");
			Object[] params={
				User.USER_ACCOUNT_STATUS.USAGE.ordinal()
			};
			try {
				ps=conn.prepareStatement(sql.toString());
				rs=query(ps, params);
				User user=null;
				while(rs.next()&&code==Constants.NO_ERROR_EXIST)
				{
					user=new User();
					code=Packager.packUser(user, rs);
					//获取图片
					if(code==Constants.NO_ERROR_EXIST)
					{
						users.add(user);
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return code;
	}

	@Override
	public int saveOrUpdate(User user) {
		int code=Constants.NO_ERROR_EXIST;
		StringBuilder sql=new StringBuilder("insert into tb_user "
				+ "(userID, schoolID, academyID, majorID, classID, password, name, phone, identity, createTime, userStatus) values(?,?,?,?,?,?,?,?,?,?,?) on duplicate key update userID=?");
		List<Object> params=new ArrayList<Object>();
		params.add(user.getUserID());
		params.add(user.getSchoolID());
		params.add(user.getAcademyID());
		params.add(user.getMajorID());
		params.add(user.getClassID());
		params.add(user.getPassword());
		params.add(user.getName());
		params.add(user.getPhone());
		params.add(user.getIdentity());
		params.add(user.getCreateTime());
		params.add(user.getUserStatus());
		params.add(user.getUserID());
		
		if(user.getPassword()!=null)
		{
			sql.append(", password=?");
			params.add(user.getPassword());
		}
		if(user.getName()!=null)
		{
			sql.append(", name=?");
			params.add(user.getName());
		}
		if(user.getSchoolID()!=null)
		{
			sql.append(", schoolID=?");
			params.add(user.getSchoolID());
		}
		if(user.getAcademyID()!=null)
		{
			sql.append(", academyID=?");
			params.add(user.getAcademyID());
		}
		if(user.getMajorID()!=null)
		{
			sql.append(", majorID=?");
			params.add(user.getMajorID());
		}
		
		if(user.getClassID()!=null)
		{
			sql.append(", classID=?");
			params.add(user.getClassID());
		}
		if(user.getPhone()!=null)
		{
			sql.append(", phone=?");
			params.add(user.getPhone());
		}
		if(user.getOpenID()!=null)
		{
			sql.append(", openID=?");
			params.add(user.getOpenID());
		}
		
		sql.append(", locationX=?");
		params.add(user.getLocationX());
		
		
		sql.append(", locationY=?");
		params.add(user.getLocationY());
		
		if(user.getUserStatus()!=-1)
		{
			sql.append(", userStatus=?");
			params.add(user.getUserStatus());
		}
		
		if(!saveOrUpdateOrDelete(sql.toString(), params.toArray()))
		{
			code=Constants.DB_SAVE_ERROR;
		}
		return code;
	}


	
}
