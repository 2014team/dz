
package com.artcweb.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.artcweb.baen.AdminUser;
import com.artcweb.constant.SessionConstant;

public class SessionUtil {

	/**
	 * @Title: getSessionUser
	 * @Description: 获取用户session信息
	 * @param request
	 * @return
	 */
	public static AdminUser getSessionUser(HttpServletRequest request) {

		HttpSession session = request.getSession();
		Object object = session.getAttribute(SessionConstant.USER_KEY_SESSION);
		if (null != object) {
			return ( AdminUser ) object;
		}
		return null;
	}

	/**
	 * @Title: deleteSessionUser
	 * @Description: 删除用户session信息
	 * @param request
	 */
	public static void deleteSessionUser(HttpServletRequest request) {

		HttpSession session = request.getSession();
		Object object = session.getAttribute(SessionConstant.USER_KEY_SESSION);
		if (null != object) {
			session.removeAttribute(SessionConstant.USER_KEY_SESSION);
		}
	}

	/**
	 * @Title: saveSessionUser
	 * @Description: 保存用户session信息
	 * @param request
	 * @param user
	 */
	public static void saveSessionUser(HttpServletRequest request, AdminUser user) {

		HttpSession session = request.getSession();
		session.setAttribute(SessionConstant.USER_KEY_SESSION, user);
	}
}
