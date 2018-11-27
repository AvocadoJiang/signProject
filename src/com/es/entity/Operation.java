package com.es.entity;

public class Operation {

	/** 操作类型 **/
	public static enum OPERATION
	{
		/** 添加 **/
		ADD,
		/** 删除 **/
		DELETE,
		/** 修改 **/
		UPDATE,
		/** 学校 **/
		SEARCH_SCHOOL,
		/** 学院 **/
		SEARCH_ACADEMY,
		/** 专业 **/
		SEARCH_MAJOR,
		/** 班级 **/
		SEARCH_CLASS,
		/** 课程 **/
		SEARCH_COURSE,
		/** 课时 **/
		SEARCH_LESSON,
		/** 选课 **/
		SEARCH_ORDER,
		/** 学生签到 **/
		SEARCH_STUSIGN,
		/** 用户 **/
		SEARCH_USER,
		/** 通过账号登录 **/
		LOGIN_BY_ACCOUNT,
		/** 通过微信登录 **/
		LOGIN_BY_WECHAT,
		
		
		/** 上传 **/
		UPLOAD,
		
		/** 下载 **/ 
		DOWNLOAD,
	}
	
	/**
	 * 获取操作类型
	 * @param type
	 * 		操作类型名称
	 * @return
	 * 		操作类型枚举对象
	 */
	public static Enum<OPERATION> getOperation(String type)
	{
		return OPERATION.values()[OPERATION.valueOf(type.toUpperCase().trim()).ordinal()];
	}
}
