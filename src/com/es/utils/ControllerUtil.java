package com.es.utils;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;

import javax.servlet.http.HttpServletResponse;

/**
 * @author Anson
 *	Copyright by EasyShare 2017
 *  
 *  All right reserved
 *
 *  Created on 2017年6月25日 下午4:44:45
 *  
 *  视图工具, ajax交互, 输出结果
 */
public final class ControllerUtil {

	private ControllerUtil() {

	}

	/**
	 * 输出字符串
	 * @param response
	 * 		HttpServletResponse
	 * @param str
	 * 		String
	 */
	public static void out(HttpServletResponse response, String str) {
		OutputStream os = null;
		try {
			response.setContentType("text/plain");
			os = response.getOutputStream();
			os.write(str.getBytes("UTF-8"));
			os.flush();
		} catch (IOException e) {
		} finally {
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
				}
			}
		}
	}

	/**
	 * 输出集合json串
	 * @param response
	 * 		HttpServletResponse
	 * @param collection
	 * 		Collection
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void out(HttpServletResponse response, Collection collection) {
		out(response, JSONUtil.listToJson(collection));
	}

	/**
	 * 输出对象字符串
	 * @param response
	 * 		HttpServletResponse
	 * @param object
	 * 		Object
	 */
	public static void out(HttpServletResponse response, Object object) {
		out(response, JSONUtil.beanToJson(object));
	}
}
