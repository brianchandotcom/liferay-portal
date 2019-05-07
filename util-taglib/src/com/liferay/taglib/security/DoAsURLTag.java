/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.taglib.security;

import com.liferay.petra.encryptor.Encryptor;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * @author Brian Wing Shun Chan
 */
public class DoAsURLTag extends TagSupport {

	public static String doTag(
			long doAsUserId, HttpServletRequest httpServletRequest)
		throws Exception {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Company company = themeDisplay.getCompany();

		String doAsURL = company.getHomeURL();

		if (Validator.isNull(doAsURL)) {
			doAsURL = _PORTAL_IMPERSONATION_DEFAULT_URL;
		}

		doAsURL = themeDisplay.getPathContext() + doAsURL;

		if (doAsUserId <= 0) {
			User defaultUser = company.getDefaultUser();

			doAsUserId = defaultUser.getUserId();
		}

		String encDoAsUserId = Encryptor.encrypt(
			company.getKeyObj(), String.valueOf(doAsUserId));

		return HttpUtil.addParameter(doAsURL, "doAsUserId", encDoAsUserId);
	}

	@Override
	public int doEndTag() throws JspException {
		try {
			String doAsURL = doTag(
				_doAsUserId, (HttpServletRequest)pageContext.getRequest());

			if (Validator.isNotNull(_var)) {
				pageContext.setAttribute(_var, doAsURL);
			}
			else {
				JspWriter jspWriter = pageContext.getOut();

				jspWriter.write(doAsURL);
			}
		}
		catch (Exception e) {
			throw new JspException(e);
		}

		return EVAL_PAGE;
	}

	public void setDoAsUserId(long doAsUserId) {
		_doAsUserId = doAsUserId;
	}

	public void setVar(String var) {
		_var = var;
	}

	private static final String _PORTAL_IMPERSONATION_DEFAULT_URL =
		PropsUtil.get(PropsKeys.PORTAL_IMPERSONATION_DEFAULT_URL);

	private long _doAsUserId;
	private String _var;

}