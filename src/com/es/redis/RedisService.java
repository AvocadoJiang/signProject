package com.es.redis;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;


/**
 * @author Anson
 *	Copyright by EasyShare 2017
 *  
 *  All right reserved
 *
 *  Created on 2017��6��25�� ����4:12:38
 *  
 *  redis������
 */
public class RedisService {
	
	private static Logger logger=LoggerFactory.getLogger(RedisService.class.getName());

	/**
	 * ͨ��keyֵ��ȡredis�е�value����ȡ���ͷ�����
	 * @param key
	 * 		String - ��
	 * @return
	 * 		object - ֵ
	 */
	public synchronized static Object get(String key)
	{
		Jedis jedis=null;
		byte[] value=null;
		Object obj=null;
		jedis=RedisUtil.getJedis();
		value=jedis.get(key.getBytes());
		if(value!=null)
		{
			obj=deSerialize(value);
		}
		RedisUtil.close(jedis);
		return obj;
	}
	
	/**
	 * ����һ������key�����򸲸�
	 * @param key
	 * 		String - �豣��ĵļ�
	 * @param value
	 * 		Object - �豣���ֵ
	 * @return
	 * 		boolean
	 */
	public synchronized static boolean set(String key, Object obj)
	{
		Jedis jedis=null;
		String code=null;
		jedis=RedisUtil.getJedis();
		code=jedis.set(key.getBytes(), serialize(obj));
		RedisUtil.close(jedis);
		return code.toLowerCase().equals("ok");
	}
	
	/**
	 * ������д��ʱ���Object��key�����򸲸�
	 * @param key
	 * 			String - �������ļ�
	 * @param obj
	 * 			Object - �������
	 * @param expire
	 * 			int - ���ʱ�䣬��λ��
	 * @return
	 * 		boolean
	 */
	public synchronized static boolean set(String key, Object obj, int expire)
	{
		Jedis jedis=null;
		String code=null;
		jedis=RedisUtil.getJedis();
		code=jedis.set(key.getBytes(), serialize(obj));
		jedis.expire(key.getBytes(), expire);//key���s
		RedisUtil.close(jedis);
		return code.toLowerCase().equals("ok");
	}
	
	/**
	 * ����һ����ֵ�ԣ�key-value��key�����򸲸�
	 * @param key
	 * 		String - ��
	 * @param value
	 * 		String - ֵ
	 * @return
	 * 		boolean
	 */
	public synchronized static boolean set(String key, String value)
	{
		Jedis jedis=null;
		String code=null;
		jedis=RedisUtil.getJedis();
		code=jedis.set(key.getBytes(), serialize(value));
		RedisUtil.close(jedis);
		return code.toLowerCase().equals("ok");
	}
	
	/**
	 * ����һ����ֵ�ԣ�key-value�������û�����ʱ�䣬key�����򸲸�
	 * @param key
	 * 		String - ��
	 * @param value
	 * 		String - ֵ
	 * @param expire
	 * 		int - ���ʱ�䣬��
	 * @return
	 * 		boolean
	 */
	public synchronized static boolean set(String key, String value, int expire)
	{
		Jedis jedis=null;
		String code=null;
		jedis=RedisUtil.getJedis();
		code=jedis.set(key.getBytes(), serialize(value));
		jedis.expire(key.getBytes(), expire);//key���ʱ��
		RedisUtil.close(jedis);
		return code.toLowerCase().equals("ok");
	}
	
	/**
	 * redis����д��
	 * �������
	 * ��ǰ���е��������������Ӧ�����ݱ����������޸ģ�������ｫ�����
	 * @param objects
	 * 		Map<String, Object> - Map��ֵ����ɵĴ��洢����
	 * @return
	 * 		boolean
	 */
	public synchronized static boolean tset(Map<String, Object> objects)
	{
		Jedis jedis=null;
		List<Object> result=null;
		jedis=RedisUtil.getJedis();
		Transaction t=null;
		try {
			t=jedis.multi();
			for(Entry<String, Object> entry:objects.entrySet())
			{
				//��������
				t.watch(entry.getKey().getBytes());
				//��������
				t.set(entry.getKey().getBytes(), serialize(entry.getValue()));
			}
			result=t.exec();
		} catch (Exception e) {
			//�ع�����
			t.discard();
			logger.error("Redis�����������ϣ�λ�ã�{}���׳��쳣��{}", RedisService.class.getName(),e);
		}
		RedisUtil.close(jedis);
		return result!=null;
	}
	
	/**
	 * redis����д��
	 * �������
	 * ��ǰ���е��������������Ӧ�����ݱ����������޸ģ�������ｫ�����
	 * @param objects
	 * 		Map<String, Object> - Map��ֵ����ɵĴ��洢����
	 * @param expire
	 * 		����ʧЧʱ��
	 * @return
	 * 		boolean
	 */
	public synchronized static boolean tset(Map<String, Object> objects, int expire)
	{
		Jedis jedis=null;
		List<Object> result=null;
		jedis=RedisUtil.getJedis();
		Transaction t=null;
		try {
			t=jedis.multi();
			for(Entry<String, Object> entry:objects.entrySet())
			{
				//��������
				t.watch(entry.getKey().getBytes());
				//��������
				t.set(entry.getKey().getBytes(), serialize(entry.getValue()));
				t.expire(entry.getKey().getBytes(), expire);//ÿ��key���expire��
			}
			result=t.exec();
		} catch (Exception e) {
			//�ع�����
			t.discard();
			logger.error("Redis�����������ϣ�λ�ã�{}���׳��쳣��{}", RedisService.class.getName(),e);
		}
		RedisUtil.close(jedis);
		return result!=null;
	}
	
	/**
	 * ģ������key
	 * @param pattern
	 * 		keyƥ��ģ��
	 * @return
	 * 		ƥ���key����
	 */
	public synchronized static Set<String> keys(String pattern)
	{
		Jedis jedis=null;
		jedis=RedisUtil.getJedis();
		Set<String> keys=new HashSet<String>();
		keys=jedis.keys(pattern);
		RedisUtil.close(jedis);
		return keys;
	}
	
	/**
	 * ɾ��key��Ӧ�Ļ�������
	 * @param key
	 * 		String-�ɱ�������key
	 * @return
	 * 		boolean
	 */
	public synchronized static long del(String ...  keys)
	{
		Jedis jedis=null;
		long code=0;
		jedis=RedisUtil.getJedis();
		code=jedis.del(keys);
		RedisUtil.close(jedis);
		return code;
	}
	
	/**
	 * ����ɾ������
	 * @param key
	 * 		String - ��
	 * @return
	 * 		boolean
	 */
	public synchronized static long delbat(String key)
	{
		Jedis jedis=null;
		long code=0;
		jedis=RedisUtil.getJedis();
		Set<String> keySet=jedis.keys(key+"*");
		if(keySet!=null&&keySet.size()>0)
		{
			//����õ�keysת������������
			String[] keys=new String[keySet.size()];
			keySet.toArray(keys);
			code=jedis.del(keys);
		}
		RedisUtil.close(jedis);
		return code;
	}
	
	/**
	 * ������ݿ�
	 * @param isClearAll
	 * 		�Ƿ�����������ݿ�<br>
	 * 		<p>false-��յ�ǰʹ�õ����ݿ⣬Ĭ��Ϊ0</p>
	 * @return
	 * 		true||false
	 */
	public synchronized static boolean clear(boolean isClearAll)
	{
		Jedis jedis=null;
		String code=null;
		jedis=RedisUtil.getJedis();
		if(isClearAll)
		{
			code=jedis.flushAll();
		}else
		{
			code = jedis.flushDB();
		}
		RedisUtil.close(jedis);
		return code.toLowerCase().equals("ok");
	}
	
	/**
	 * ������ȡ����
	 * @param key
	 * 		String - ��
	 * @return
	 * 		List<Object> - ��ȡ�Ķ����б�
	 */
	public synchronized static List<Object> getbat(String key)
	{
		Jedis jedis=null;
		List<Object> objects=new ArrayList<Object>();
		Object object=null;
		jedis=RedisUtil.getJedis();
		Set<String> keySet=jedis.keys(key+"*");
		RedisUtil.close(jedis);
		if(keySet!=null&&keySet.size()>0)
		{
			Iterator<String> it=keySet.iterator();
			while(it.hasNext())
			{
				String item=it.next();
				object=get(item);
				if(object!=null)
				{
					objects.add(object);
				}
			}
		}
		return objects;
	}
	
	
	/**
	 * ���л��洢����
	 * @param obj
	 * 		Object - �����л�����
	 * @return
	 * 		byte[] - ���л����
	 */
	private synchronized static byte[] serialize(Object obj)
	{
		byte[] serialObj=null;
		ObjectOutputStream oos=null;
		ByteArrayOutputStream baos=null;
		try {
			baos=new ByteArrayOutputStream();
			oos=new ObjectOutputStream(baos);
			oos.writeObject(obj);
			serialObj=baos.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		}finally
		{
			try {
				if(baos!=null)
				{
					baos.close();
				}
				if(oos!=null)
				{
					oos.close();
				}
			} catch (IOException e) {
				logger.error("�ͷ����л���Դʧ�ܣ�λ�ã�{}���ų��쳣:{}",RedisService.class.getName(),e);
			}
		}
		return serialObj;
	}
	
	/**
	 * �����л�����
	 * @param serialObj
	 * 		byte[] - ���л�����
	 * @return
	 * 		Object - �����л����
	 */
	private synchronized static Object deSerialize(byte[] serialObj)
	{
		Object object=null;
		ObjectInputStream ois=null;
		ByteArrayInputStream bais=null;
		try {
			if(serialObj!=null&&serialObj.length>0)
			{
				bais=new ByteArrayInputStream(serialObj);
				ois=new ObjectInputStream(bais);
				object=ois.readObject();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}finally
		{
			try {
				if(bais!=null)
				{
					bais.close();
				}
				if(ois!=null)
				{
					ois.close();
				}
			} catch (IOException e) {
				logger.error("�ͷŷ����л���Դʧ�ܣ�λ�ã�{},�׳��쳣:{}",RedisService.class.getName(),e);
			}
		}
		return object;
	}
}
