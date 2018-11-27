package com.es.service;

import com.es.json.form.request.LessonReqForm;
import java.util.List;
import com.es.entity.Lesson;
import com.es.entity.Operation.OPERATION;

/**
 * @author Anson
 *	Copyright by EasyShare 2017
 *  
 *  All right reserved
 *
 *  Created on 2017年6月25日 下午4:40:36
 *  
 *  课时信息服务接口
 */
public interface LessonService {

	/**
	 * 获取课时对象
	 * @param lesson
	 * 		待填充的课时对象
	 * @param reqForm
	 * 		课时请求对象
	 * @param operation
	 * 		操作类型
	 * @return
	 * 		错误码
	 */
	public int getLesson(Lesson lesson, LessonReqForm reqForm, Enum<OPERATION> operation);
	
	/**
	 * 检查添加数据时信息的完整性
	 * @param reqForm
	 * 		与视图交互的注册表格
	 * @param operation
	 * 		操作类型
	 * @return
	 * 		错误码
	 */
	public int check(LessonReqForm reqForm, Enum<OPERATION> operation);

	/**
	 * 保存、修改或删除课时
	 * @param lesson
	 * 		待处理的课时对象
	 * @return
	 * 		错误码
	 */
	public int saveOrUpdate(Lesson lesson);

	/**
	 * 获取课时集合
	 * @param reqForm
	 * 		待获取的课时信息请求对象
	 * @param objects
	 * 		待填充的课时对象集合
	 * @return
	 * 		错误码
	 */
	public int getLessonCollections(LessonReqForm reqForm, List<Object> respForm,Integer start,Integer end);
	
	
}
