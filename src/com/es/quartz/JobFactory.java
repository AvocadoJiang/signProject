package com.es.quartz;

import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.scheduling.quartz.AdaptableJobFactory;

/**
 * @author Anson<br>
 *	Copyright by EasyShare 2018<br>
 *  
 *  All right reserved<br>
 *
 *  Created on 2018年1月5日 上午2:01:02<br>
 *  向quartz中注入由spring管理的service<br>
 */
public class JobFactory extends AdaptableJobFactory {

	@Autowired
	private AutowireCapableBeanFactory autowireCapableBeanFactory;
	
	public JobFactory() {
		
	}
	
	@Override
	protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {
		//调用父类
		Object instance = super.createJobInstance(bundle);
		//自动注入
		autowireCapableBeanFactory.autowireBean(instance);
		return instance;
	}
}
