package com.es.globle;

import java.awt.Color;
import java.awt.Font;

/**
 * @author Anson
 *	Copyright by EasyShare 2017
 *  
 *  All right reserved
 *
 *  Created on 2017年6月25日 下午4:33:44
 *  
 *  常量类，用于管理常量参数
 */
public class Constants {
	/**
	 * 验证码长度
	 */
	public static final Integer IMAGE_WIDTH=90;
	
	/**
	 * 验证码宽度
	 */
	public static final Integer IMAGE_HEIGHT=30;
	
	/**
	 * 验证码字库
	 */
	public static Font[] codeFont={new Font("Times New Roman",Font.PLAIN,20),
			new Font("Times New Roman",Font.PLAIN,20),new Font("Times New Roman",Font.PLAIN,20),
			new Font("Times New Roman",Font.PLAIN,20),new Font("Times New Roman",Font.PLAIN,20)};
	/**
	 * 验证码每个字符颜色
	 */
	public static Color[] color={Color.BLACK,Color.BLUE,Color.RED,Color.DARK_GRAY,Color.GREEN};
	
	/**
	 * 验证码字库
	 */
	public static final String IMAGE_CHAR="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
	
	/**
	 * session中的用户ID
	 */
	public static String USER_ID = "userID";
	public static String IDENTITY = "identity";
	public static String NAME = "name";
	public static String SCHOOL_ID = "school_id";
	public static String ACADEMY_ID = "academy_id";
	public static String MAJOR_ID = "major_id";
	public static String CLASS_ID = "class_id";
	/**
	 * 验证码
	 */
	public static String CHECK_NUMBER_NAME = "identify_code";
	

	/**
	 * 保存在ServletContext中的用户列表
	 */
	public static String ONLINE_USERS = "users";
	
	/**
	 * 软件拥有方
	 */
	public static final String SOFTWARE_OWNER = "宁工信息安全实验室";
	
	/**
	 * 默认头像名
	 */
	public static final String DEFAULT_HEAD_PICTURE_NAME = "default.png";
	
	/**
	 * 初始化
	 */
	public static final String INIT = "init";
	
	/**
	 * 增加
	 */
	public static final String ADD = "add";
	
	/**
	 * 删除
	 */
	public static final String DELETE = "delete";
	
	/**
	 * 搜索
	 */
	public static final String SEARCH = "search";
	
	/**
	 * 更新
	 */
	public static final String UPDATE = "update";
	
	/**
	 * 详情
	 */
	public static final String DETAIL = "detail";
	
	/**
	 * 重置密码
	 */
	public static final String RESET_PASSWORD = "resetPwd";
	
	/**
	 * 验证请求
	 */
	public static final String IDENTIFY_CODE = "identify";
	
	/**
	 * 用户或者记录状态活跃
	 */
	public static final int STATIC_ACTIVE=1;
	
	/**
	 * 用户或记录状态非法
	 */
	public static final int STATIC_INVALID=0;
	
	/**
	 * 用户或记录不存在
	 */
	public static final int STATIC_DELETE=-1;
	
	
	/**
	 * 不存在任何错误，指向null
	 */
	public static final int NO_ERROR_EXIST=-1;
	

	/**
	 * 该用户名已经被注册
	 */
	public static final int USER_ACCOUNT_EXIST = 1;
	/**
	 * 该用户名包含非法字符
	 */
	public static final int USER_ACCOUNT_CONTAIN_INVALIDE_WORD=2;
	
	/**
	 * 注册成功
	 */
	public static final int REGISTER_SUCCESS=3;
	
	/**
	 * 由于网络等未知因素，造成注册失败
	 */
	public static final int UNKNOWN_REGISTER_ERROR=4;
	
	/**
	 * 用户账户不存在
	 */
	public static final int USER_ACCPUNT_NOT_EXIST=5;
	
	/**
	 * 登录密码错误
	 */
	public static final int USER_LOGIN_PWD_ERROR=6;
	
	/**
	 * 非法访问
	 */
	public static final int INVALID_REQUEST=7;
	
	/**
	 * bean实体创建失败
	 */
	public static final int ENTITY_INSTANCE_DEFEAT=8;

	/**
	 * 当前请求没有对应的资源
	 */
	public static final int INFORMATION_NOT_EXIST = 10;

	/**
	 * 信息更新成功
	 */
	public static final int INFORMATION_UPDATE_SECCESS = 11;

	/**
	 * 由于网络等未知因素导致，信息处理失败，请刷新重试
	 */
	public static final int UNKNOWN_OPERATION_ERROR = 12;

	/**
	 * redis事物被打断，需重新提交
	 */
	public static final int REDIS_TRANSACTION_INTERRUPT = 13;
	
	/**
	 * 手机号与预留号码不匹配
	 */
	public static final int INVALID_PHONE_NUMBER = 14;
	
	/**
	 * 验证码错误
	 */
	public static final int IDENTIFY_CODE_ERROR=15;
	/**
	 * 文件类型有错误
	 */
	public static final int FILE_TYPE_ERROR=16;
	/**
	 * 文件不存在
	 */
	public static final int FILE_NOT_FIND=17;
	/**
	 * 缓存过期时间
	 */
	public static final int CACHE_SERVICE_EXPIRE_TIME = 60;
	/** --------------------------------------------------------------- **/
	/** 对象存储失败 **/
	public static final int REDIS_ENTITY_SAVE_ERROR = 1000;
    /** 上传信息不完整 **/
	public static final int INFO_NOT_COMPLETE = 1001;
	/** 对象删除失败 **/
	public static final int REDIS_ENTITY_DELETE_ERROR = 1002;
	/** 不可能报出的错，无法得出错误原因 **/
	public static final int UNKNOWN_ERROR = 1003;
	/** 用户类型不存在 **/
	public static final int USER_TYPE_NOT_EXIST = 1004;
	/** 邮箱格式不正确 **/
	public static final int EMAIL_PATTERN_NOT_CORRECT = 1005;
	/** 手机号格式不正确 **/
	public static final int PHONE_PATTERN_NOT_CORRECT = 1006;
	/** 用户名或密码不正确 **/
	public static final int USERNAME_OR_PASSWORD_NOT_EXIST = 1007;
	/** 信息加载失败，请刷新重试 **/
	public static final int INFORMATION_LOAD_DEFEAT=1008;
	/** 请求信息中包含非法数据 **/
	public static final int INVALID_NUMBER_REQUEST = 1009;
	/** 请求参数错误 **/
	public static final int SEARCH_PARAM_ERROR = 1010;
	/** 数据库数据保存出错 **/
	public static final int DB_SAVE_ERROR = 1011;
	/** 数据库获取数据失败 **/
	public static final int DB_GET_ERROR = 1012;
	/** 身份错误 **/
	public static final int IDENTITY_ERROR = 1013;
	/** 错误的登录方式 **/
	public static final int LOGIN_WAY_ERROR = 1014;
	/** openID获取失败 **/
	public static final int OPENID_ERROR = 1015;
}
