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
 *  Created on 2017年6月25日 下午4:12:38
 *  
 *  redis服务类
 */
public class RedisService {
	
	private static Logger logger=LoggerFactory.getLogger(RedisService.class.getName());

	/**
	 * 通过key值获取redis中的value，获取后释放链接
	 * @param key
	 * 		String - 键
	 * @return
	 * 		object - 值
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
	 * 缓存一个对象，key存在则覆盖
	 * @param key
	 * 		String - 需保存的的键
	 * @param value
	 * 		Object - 需保存的值
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
	 * 缓存带有存活时间的Object，key存在则覆盖
	 * @param key
	 * 			String - 缓存对象的键
	 * @param obj
	 * 			Object - 缓存对象
	 * @param expire
	 * 			int - 存活时间，单位秒
	 * @return
	 * 		boolean
	 */
	public synchronized static boolean set(String key, Object obj, int expire)
	{
		Jedis jedis=null;
		String code=null;
		jedis=RedisUtil.getJedis();
		code=jedis.set(key.getBytes(), serialize(obj));
		jedis.expire(key.getBytes(), expire);//key存活s
		RedisUtil.close(jedis);
		return code.toLowerCase().equals("ok");
	}
	
	/**
	 * 缓存一个键值对，key-value，key存在则覆盖
	 * @param key
	 * 		String - 键
	 * @param value
	 * 		String - 值
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
	 * 缓存一个键值对，key-value，并设置缓存存活时间，key存在则覆盖
	 * @param key
	 * 		String - 键
	 * @param value
	 * 		String - 值
	 * @param expire
	 * 		int - 存活时间，秒
	 * @return
	 * 		boolean
	 */
	public synchronized static boolean set(String key, String value, int expire)
	{
		Jedis jedis=null;
		String code=null;
		jedis=RedisUtil.getJedis();
		code=jedis.set(key.getBytes(), serialize(value));
		jedis.expire(key.getBytes(), expire);//key存活时间
		RedisUtil.close(jedis);
		return code.toLowerCase().equals("ok");
	}
	
	/**
	 * redis批量写入
	 * 事物操作
	 * 当前进行的事物操作，若对应的数据被其他进程修改，则该事物将被打断
	 * @param objects
	 * 		Map<String, Object> - Map键值对组成的待存储对象
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
				//监视数据
				t.watch(entry.getKey().getBytes());
				//保存数据
				t.set(entry.getKey().getBytes(), serialize(entry.getValue()));
			}
			result=t.exec();
		} catch (Exception e) {
			//回滚操作
			t.discard();
			logger.error("Redis事物操作被打断，位置：{}，抛出异常：{}", RedisService.class.getName(),e);
		}
		RedisUtil.close(jedis);
		return result!=null;
	}
	
	/**
	 * redis批量写入
	 * 事物操作
	 * 当前进行的事物操作，若对应的数据被其他进程修改，则该事物将被打断
	 * @param objects
	 * 		Map<String, Object> - Map键值对组成的待存储对象
	 * @param expire
	 * 		数据失效时间
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
				//监视数据
				t.watch(entry.getKey().getBytes());
				//保存数据
				t.set(entry.getKey().getBytes(), serialize(entry.getValue()));
				t.expire(entry.getKey().getBytes(), expire);//每个key存活expire秒
			}
			result=t.exec();
		} catch (Exception e) {
			//回滚操作
			t.discard();
			logger.error("Redis事物操作被打断，位置：{}，抛出异常：{}", RedisService.class.getName(),e);
		}
		RedisUtil.close(jedis);
		return result!=null;
	}
	
	/**
	 * 模糊检索key
	 * @param pattern
	 * 		key匹配模板
	 * @return
	 * 		匹配的key集合
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
	 * 删除key对应的缓存数据
	 * @param key
	 * 		String-可变数量的key
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
	 * 批量删除操作
	 * @param key
	 * 		String - 键
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
			//将获得的keys转换成数组类型
			String[] keys=new String[keySet.size()];
			keySet.toArray(keys);
			code=jedis.del(keys);
		}
		RedisUtil.close(jedis);
		return code;
	}
	
	/**
	 * 清空数据库
	 * @param isClearAll
	 * 		是否清空所有数据库<br>
	 * 		<p>false-清空当前使用的数据库，默认为0</p>
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
	 * 批量获取操作
	 * @param key
	 * 		String - 键
	 * @return
	 * 		List<Object> - 获取的对象列表
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
	 * 序列化存储对象
	 * @param obj
	 * 		Object - 待序列化对象
	 * @return
	 * 		byte[] - 序列化结果
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
				logger.error("释放序列化资源失败，位置：{}，排除异常:{}",RedisService.class.getName(),e);
			}
		}
		return serialObj;
	}
	
	/**
	 * 反序列化对象
	 * @param serialObj
	 * 		byte[] - 序列化对象
	 * @return
	 * 		Object - 反序列化结果
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
				logger.error("释放反序列化资源失败，位置：{},抛出异常:{}",RedisService.class.getName(),e);
			}
		}
		return object;
	}
}
