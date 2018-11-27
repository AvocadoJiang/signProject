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
 *  Created on 2017��6��25�� ����4:12:56
 *  
 *  redis������
 */
public final class RedisUtil {
    
    /** Redis������IP */
    private static String ADDR;
    /** Redis�Ķ˿ں� */
    private static int PORT;
    /** �������� */
    private static String AUTH;
    /** ��������ʵ���������Ŀ��Ĭ��ֵΪ8<br>
     *  �����ֵΪ-1�����ʾ�����ƣ����pool�Ѿ�������maxActive��jedisʵ�������ʱpool��״̬Ϊexhausted(�ľ�)
     */
    private static int MAX_ACTIVE;
    /** ����һ��pool����ж��ٸ�״̬Ϊidle(���е�)��jedisʵ����Ĭ��ֵҲ��8 */
    private static int MAX_IDLE;
    /** �ȴ��������ӵ����ʱ�䣬��λ���룬Ĭ��ֵΪ-1����ʾ������ʱ����������ȴ�ʱ�䣬��ֱ���׳�JedisConnectionException */
    private static int MAX_WAIT;
    /** ���Խ������ӵ����ȴ�ʱ�� */
    private static int TIMEOUT;
    /** ��borrowһ��jedisʵ��ʱ���Ƿ���ǰ����validate���������Ϊtrue����õ���jedisʵ�����ǿ��õ� */
    private static boolean TEST_ON_BORROW;
    /** redis���ӳض��� */
    private static JedisPool jedisPool = null;
    /** ��־ */
    private static Logger logger=LoggerFactory.getLogger(RedisUtil.class.getName());
    
    /**
     * ��ʼ��Redis���ӳ�
     */
    static
    {
        try 
        {
        	//���ز���
           loadProperty();
        } catch (Exception e) 
        {
            e.printStackTrace();
        }
    }
    
    /**
     * ����redis���ò���
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
		
			logger.info("redis�������سɹ�,"
					+ "������ADDR="+ADDR+" PORT="+PORT+" AUTH="+AUTH+
					"MAX_ACTIVE="+MAX_ACTIVE+"  MAX_IDLE="+MAX_IDLE+"  MAX_WAIT="+MAX_WAIT+"  TIMEOUT="+TIMEOUT+"  TEST_ON_BORROW="+TEST_ON_BORROW);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

    
    /**
     * ����redis��
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
           // logger.info(jedisPool!=null?"redis�ش����ɹ�":"redis�ش���ʧ�ܣ�λ�ã�"+RedisUtil.class.getName());	
            logger.info(jedisPool!=null?"redis���ӳش����ɹ�":"");	
    	}catch(Exception e)
    	{
    		logger.error("��һ�γ��Դ���jedis���ӳش���λ�ã�"+RedisUtil.class.getName());
    		try
    		{
    			JedisPoolConfig config = new JedisPoolConfig();
                config.setMaxIdle(MAX_ACTIVE);
                config.setMaxIdle(MAX_IDLE);
                config.setMaxWaitMillis(MAX_WAIT);
                config.setTestOnBorrow(TEST_ON_BORROW);
                jedisPool = new JedisPool(config, ADDR, PORT, TIMEOUT, AUTH);
                logger.info(jedisPool!=null?"redis���ӳش����ɹ�":"");
    		}catch(Exception e1)
    		{
    			logger.error("�ڶ��γ��Դ���jedis���ӳش���λ�ã�"+RedisUtil.class.getName());
    		}
    	}
  	}
    
    /**
     * ���߳���ͬ����ʼ��
     */
    private synchronized static void loadPool()
    {
    	if(jedisPool==null)
    	{
    		initJedisPool();
    	}
    }
    
    
	/**
     * ͬ����ȡJedisʵ��
     * @return
     * 		Jedis - redis����ʵ��
     */
    public synchronized static Jedis getJedis(){
    	if(jedisPool==null)
    	{
    		//δ�������ӳ��Ǵ������ӳ�
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
        	logger.info("��ȡredis����ʧ�ܣ�λ�ã�"+RedisUtil.class.getName());
            e.printStackTrace();
        }
        return jedis;
    }
    

	/**
     * �ͷ�jedis��Դ
     * @param jedis
     * 		Jedis - redis����ʵ��
     */
	public synchronized static void close(final Jedis jedis){
        if (jedis!=null) 
        {
        	jedis.close();
        }
    }
}