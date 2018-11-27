package com.es.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.io.PrintStream;

/**
 * @author Anson
 *	Copyright by EasyShare 2017
 *  
 *  All right reserved
 *
 *  Created on 2017��6��25�� ����4:23:53
 *  
 *  ���ݿ������װ
 */
public class JDBCBase {

	protected JDBCBase(){}
	
	/**
	 * ��ѯ�����
	 * @param ps ��sql���ʵ������PreparedStatement����
	 * @return �����
	 */
	protected ResultSet query(PreparedStatement ps)
	{
		return query(ps,null);
	}

	
	/**
	 * ��װδ֪��������ѯ�����
	 * @param ps ��sql���ʵ������PreparedStatement����
	 * @param parma ���δ֪����������
	 * @return �����
	 */
	protected ResultSet query(PreparedStatement ps, Object[] parma)
	{
		return query(ps,parma,null,null);
	}
	
	
	/**
	 * ��ѯ��ҳ���
	 * @param ps ��sql���ʵ������PreparedStatement����
	 * @param start ��ʼ����
	 * @param maxCount ��ҳ���ֵ
	 * @return �����
	 */
	protected ResultSet query(PreparedStatement ps,Integer start,Integer maxCount)
	{
		return query(ps,null,start,maxCount);
	}

	
	/**
	 * ��װδ֪��������ѯ��ҳ�����
	 * @param ps ��sql���ʵ������PreparedStatement����
	 * @param parma ���δ֪����������
	 * @param start ��ʼ����
	 * @param maxCount ��ҳ���ֵ
	 * @return �����
	 */
	protected ResultSet query(PreparedStatement ps, Object[] parma, Integer start, Integer maxCount)
	{
		ResultSet rs=null;
		
		try
		{
			if(parma!=null)
			{
				for(int i=0;i<parma.length;i++)
				{
					ps.setObject(i+1, parma[i]);
				}
			}
			
			//���ò�ѯ���ļ�¼���������
			if(start!=null&&maxCount!=null)
			{
				ps.setMaxRows(start+maxCount);
			}
			
			
			rs=ps.executeQuery();
			
			//���ò�ѯ���ļ�¼����ʼ����
			if(start!=null&&maxCount!=null)
			{
				rs.first();
				rs.relative(start-1);
			}
			
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		return rs;
	}
	
	
	/**
	 * ���������/���²���
	 * @param sql sql���
	 * @param parma ���δ֪��������
	 * @return true||false
	 */
	protected boolean save(String sql,Object[] parma)
	{
		boolean flag=false;
		Connection conn=JDBCUtil.getConnection();
		PreparedStatement ps=null;
		ResultSet rs=null;
		try
		{
			ps=conn.prepareStatement(sql);
			if(parma!=null)
			{
				for(int i=0;i<parma.length;i++)
				{
					ps.setObject(i+1, parma[i]);
				}
			}
			ps.executeUpdate();
			flag=true;
		}catch(SQLException e)
		{
			e.printStackTrace();
		}finally
		{
			JDBCUtil.close(rs, ps, conn);
		}
		return flag;
	}
	
	/**
	 * �������
	 * @param sql
	 * 		sql���
	 * @param parma
	 * 		δ֪���͵����ݶ��󼯺�
	 * @param conn
	 * 		���ݿ����Ӷ���
	 * @return
	 * 		true||false
	 */
	protected boolean saveOrUpdate(String sql,Object[] parma,Connection conn)
	{
		boolean flag=false;
		PreparedStatement ps=null;
		try
		{
			ps=conn.prepareStatement(sql);
			if(parma!=null)
			{
				for(int i=0;i<parma.length;i++)
				{
					ps.setObject(i+1, parma[i]);
				}
			}
			ps.executeUpdate();
			flag=true;
		}catch(SQLException e)
		{
			e.printStackTrace();
			try
			{
				conn.rollback();
			} catch (SQLException e1)
			{
				e1.printStackTrace();
			}
		}finally
		{
			JDBCUtil.close(ps);
		}
		
		return flag;
	}
	
	protected int getCount(String sql)
	{
		Connection conn=JDBCUtil.getConnection();
		PreparedStatement ps=null;
		ResultSet rs=null;
		
		int Count=0;
		
		try
		{
			ps=conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			rs=query(ps);
			if(rs.next())
			{
				Count=rs.getInt(1);
			}
		}catch(SQLException e)
		{
			e.printStackTrace();
		}finally
		{
			JDBCUtil.close(rs, ps, conn);
		}
		return Count;
	}
	
	
	/**
	 * ��������ɾ���Ĳ���
	 * @param sql sql���
	 * @param value �������ֵ����
	 * @return
	 * 		�Ƿ�ɹ�
	 */
	protected boolean saveOrUpdateOrDelete(String sql,Object value[])
	{
		boolean flag=false;
		PreparedStatement ps=null;
		Connection conn=JDBCUtil.getConnection();
		
		try
		{
			ps=conn.prepareStatement(sql);
			
			if(value!=null)
			{
				for(int i=0;i<value.length;i++)
				{
					
					ps.setObject(i+1, value[i]);
				}
			}
			
			ps.execute();
			
			flag=true;
		}catch(SQLException e)
		{
			
			e.printStackTrace();
		}finally
		{
			JDBCUtil.close(null, ps, conn);
		}
		return flag;
	}
}
