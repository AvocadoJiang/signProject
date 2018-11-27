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
 *  Created on 2018��1��5�� ����2:01:02<br>
 *  ��quartz��ע����spring�����service<br>
 */
public class JobFactory extends AdaptableJobFactory {

	@Autowired
	private AutowireCapableBeanFactory autowireCapableBeanFactory;
	
	public JobFactory() {
		
	}
	
	@Override
	protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {
		//���ø���
		Object instance = super.createJobInstance(bundle);
		//�Զ�ע��
		autowireCapableBeanFactory.autowireBean(instance);
		return instance;
	}
}
