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

import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.util.Encryptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * @author Brian Wing Shun Chan
 */
public class DoAsURLTag extends TagSupport {

	public static String doTag(long doAsUserId, HttpServletRequest request)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		Company company = themeDisplay.getCompany();

		String doAsURL = company.getHomeURL();

		if (Validator.isNull(doAsURL)) {
			Layout layout = null;

			List<Layout> layouts = LayoutLocalServiceUtil.getLayouts(
				themeDisplay.getScopeGroupId(), false,
				LayoutConstants.DEFAULT_PARENT_LAYOUT_ID);

			if (!layouts.isEmpty()) {
				layout = layouts.get(0);

				doAsURL = layout.getFriendlyURL();
			}

			if (Validator.isNull(doAsURL)) {
				doAsURL = _COMPANY_DEFAULT_HOME_URL;
			}
		}

		doAsURL = themeDisplay.getPathContext() + doAsURL;

		if (doAsUserId <= 0) {
			doAsUserId = company.getDefaultUser().getUserId();
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

	private static final String _COMPANY_DEFAULT_HOME_URL = PropsUtil.get(
		PropsKeys.COMPANY_DEFAULT_HOME_URL);

	private long _doAsUserId;
	private String _var;

}
