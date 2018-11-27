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
 *  Created on 2017��6��25�� ����4:44:45
 *  
 *  ��ͼ����, ajax����, ������
 */
public final class ControllerUtil {

	private ControllerUtil() {

	}

	/**
	 * ����ַ���
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
	 * �������json��
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
	 * ��������ַ���
	 * @param response
	 * 		HttpServletResponse
	 * @param object
	 * 		Object
	 */
	public static void out(HttpServletResponse response, Object object) {
		out(response, JSONUtil.beanToJson(object));
	}
}
