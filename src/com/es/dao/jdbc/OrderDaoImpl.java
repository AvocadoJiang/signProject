package com.es.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.es.dao.OrderDao;
import com.es.entity.Order;
import com.es.globle.Constants;
import com.es.redis.CachePackager;
import com.es.redis.RedisService;
import com.es.utils.Packager;

@Service("orderDao")
public class OrderDaoImpl extends JDBCBase implements OrderDao {

	@Override
	public int getOrderByID(Order order, String orderID) {
		int code=Constants.NO_ERROR_EXIST;
		Object object=null;
		//去redis中根据ID找对象
		object=RedisService.get("orm/order/entity/"+orderID);
		if(object!=null)
		{
			CachePackager.packOrderCache(order, object);
		}else
		{
			Connection conn=JDBCUtil.getConnection();
			PreparedStatement ps=null;
			ResultSet rs=null;
			StringBuilder sql=new StringBuilder("select * from tb_order where orderID = ?");
			Object[] params={
				orderID,
			};
			try {
				ps=conn.prepareStatement(sql.toString());
				rs=query(ps, params);
				if(rs.next())
				{
					code=Packager.packOrder(order, rs);
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
	public int getOrderCollections(List<Order> orders) {
		int code=Constants.NO_ERROR_EXIST;
		List<Object> objects=RedisService.getbat("orm/order/entity/");
		if(objects!=null&&!objects.isEmpty())
		{
			for(Object object:objects)
			{
				orders.add((Order)object);
			}
		}else
		{
			Connection conn=JDBCUtil.getConnection();
			PreparedStatement ps=null;
			ResultSet rs=null;
			StringBuilder sql=new StringBuilder("select * from tb_order where orderStatus = ?");
			Object[] params={
				Order.ORDER_STATUS.USAGE.ordinal()
			};
			try {
				ps=conn.prepareStatement(sql.toString());
				rs=query(ps, params);
				Order order=null;
				//缓存的数据集
				while(rs.next()&&code==Constants.NO_ERROR_EXIST)
				{
					order=new Order();
					code=Packager.packOrder(order, rs);
					//获取图片
					if(code==Constants.NO_ERROR_EXIST)
					{
						orders.add(order);
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return code;
	}

	@Override
	public int saveOrUpdate(Order order) {
		int code=Constants.NO_ERROR_EXIST;
		StringBuilder sql=new StringBuilder("insert into tb_order "
				+ "(orderID, courseID, studentID, orderStatus) values(?,?,?,?) on duplicate key update orderID=?");
		List<Object> params=new ArrayList<Object>();
		params.add(order.getOrderID());
		params.add(order.getCourseID());
		params.add(order.getStudentID());
		params.add(order.getOrderStatus());
		params.add(order.getOrderID());
		
		if(order.getOrderStatus()!=-1)
		{
			sql.append(", orderStatus=?");
			params.add(order.getOrderStatus());
		}
		
		if(!saveOrUpdateOrDelete(sql.toString(), params.toArray()))
		{
			code=Constants.DB_SAVE_ERROR;
		}
		return code;
	}

}
