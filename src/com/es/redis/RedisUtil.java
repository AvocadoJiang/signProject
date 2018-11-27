package com.es.redis;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author Anson
 *	Copyright by EasyShare 2017
 *  
 *  All right reserved
 *
 *  Created on 2017年6月25日 下午4:12:56
 *  
 *  redis工具类
 */
public final class RedisUtil {
    
    /** Redis服务器IP */
    private static String ADDR;
    /** Redis的端口号 */
    private static int PORT;
    /** 访问密码 */
    private static String AUTH;
    /** 可用连接实例的最大数目，默认值为8<br>
     *  如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)
     */
    private static int MAX_ACTIVE;
    /** 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8 */
    private static int MAX_IDLE;
    /** 等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException */
    private static int MAX_WAIT;
    /** 尝试建立连接的最大等待时间 */
    private static int TIMEOUT;
    /** 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的 */
    private static boolean TEST_ON_BORROW;
    /** redis连接池对象 */
    private static JedisPool jedisPool = null;
    /** 日志 */
    private static Logger logger=LoggerFactory.getLogger(RedisUtil.class.getName());
    
    /**
     * 初始化Redis连接池
     */
    static
    {
        try 
        {
        	//加载参数
           loadProperty();
        } catch (Exception e) 
        {
            e.printStackTrace();
        }
    }
    
    /**
     * 加载redis配置参数
     */
    private final static void loadProperty() {
		Properties prop=new Properties();
		try {
			prop.load(new FileInputStream(new File(Thread.currentThread().getContextClassLoader().getResource("").getPath()+"redis.properties")));
			ADDR=prop.getProperty("ADDR").trim();
			PORT=Integer.parseInt(prop.getProperty("PORT").trim());
			AUTH=prop.getProperty("AUTH");
			MAX_ACTIVE=Integer.parseInt(prop.getProperty("MAX_ACTIVE").trim());
			MAX_IDLE=Integer.parseInt(prop.getProperty("MAX_IDLE").trim());
			MAX_WAIT=Integer.parseInt(prop.getProperty("MAX_WAIT").trim());
			TIMEOUT=Integer.parseInt(prop.getProperty("TIMEOUT").trim());
			TEST_ON_BORROW=prop.getProperty("TEST_ON_BORROW").trim().toLowerCase().equals("true");
		
			logger.info("redis参数加载成功,"
					+ "参数：ADDR="+ADDR+" PORT="+PORT+" AUTH="+AUTH+
					"MAX_ACTIVE="+MAX_ACTIVE+"  MAX_IDLE="+MAX_IDLE+"  MAX_WAIT="+MAX_WAIT+"  TIMEOUT="+TIMEOUT+"  TEST_ON_BORROW="+TEST_ON_BORROW);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

    
    /**
     * 创建redis池
     */
    private final static void initJedisPool() {
    	try
    	{
    		JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxTotal(MAX_ACTIVE);
            config.setMaxIdle(MAX_IDLE);
            config.setMaxWaitMillis(MAX_WAIT);
            config.setTestOnBorrow(TEST_ON_BORROW);
            jedisPool = new JedisPool(config, ADDR, PORT, TIMEOUT, AUTH);
           // logger.info(jedisPool!=null?"redis池创建成功":"redis池创建失败，位置："+RedisUtil.class.getName());	
            logger.info(jedisPool!=null?"redis连接池创建成功":"");	
    	}catch(Exception e)
    	{
    		logger.error("第一次尝试创建jedis连接池错误，位置："+RedisUtil.class.getName());
    		try
    		{
    			JedisPoolConfig config = new JedisPoolConfig();
                config.setMaxIdle(MAX_ACTIVE);
                config.setMaxIdle(MAX_IDLE);
                config.setMaxWaitMillis(MAX_WAIT);
                config.setTestOnBorrow(TEST_ON_BORROW);
                jedisPool = new JedisPool(config, ADDR, PORT, TIMEOUT, AUTH);
                logger.info(jedisPool!=null?"redis连接池创建成功":"");
    		}catch(Exception e1)
    		{
    			logger.error("第二次尝试创建jedis连接池错误，位置："+RedisUtil.class.getName());
    		}
    	}
  	}
    
    /**
     * 多线程下同步初始化
     */
    private synchronized static void loadPool()
    {
    	if(jedisPool==null)
    	{
    		initJedisPool();
    	}
    }
    
    
	/**
     * 同步获取Jedis实例
     * @return
     * 		Jedis - redis操作实例
     */
    public synchronized static Jedis getJedis(){
    	if(jedisPool==null)
    	{
    		//未创建连接池是创建连接池
    		loadPool();
    	}
    	Jedis jedis=null;
        try
        {
            if (jedisPool != null) 
            {
                 jedis= jedisPool.getResource();
            }
        } catch (Exception e) 
        {
        	logger.info("获取redis对象失败，位置："+RedisUtil.class.getName());
            e.printStackTrace();
        }
        return jedis;
    }
    

	/**
     * 释放jedis资源
     * @param jedis
     * 		Jedis - redis操作实例
     */
	public synchronized static void close(final Jedis jedis){
        if (jedis!=null) 
        {
        	jedis.close();
        }
    }
}