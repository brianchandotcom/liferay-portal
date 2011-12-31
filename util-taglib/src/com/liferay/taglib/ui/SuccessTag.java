/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.taglib.ui;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.servlet.PortalIncludeUtil;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.servlet.taglib.FileAvailabilityUtil;
import com.liferay.portal.kernel.servlet.taglib.TagSupport;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import javax.portlet.PortletRequest;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public class SuccessTag extends TagSupport {

	@Override
	public int doEndTag() throws JspException {
		HttpServletRequest request =
			(HttpServletRequest)pageContext.getRequest();

		PortletRequest portletRequest = (PortletRequest)request.getAttribute(
			JavaConstants.JAVAX_PORTLET_REQUEST);

		boolean contains = SessionMessages.contains(portletRequest, _key);

		if (contains) {
			// Do output, only when there is an actual message.

			String message = _message;

			if (_translateMessage) {
				message = LanguageUtil.get(pageContext, message);
			}

			ServletContext servletContext =
				(ServletContext)request.getAttribute(WebKeys.CTX);

			if (FileAvailabilityUtil.isAvailable(servletContext, _PAGE)) {
				// Do jsp output, if there is an overwritten jsp.

				request.setAttribute("liferay-ui:success:message", message);

				try {
					PortalIncludeUtil.include(pageContext, _PAGE);
				}
				catch (Exception e) {
					throw new JspException(e);
				}
			}
			else {
				// Do inline output by default for best performance.

				JspWriter jspWriter = pageContext.getOut();

				try {
					jspWriter.write("<div class=\"portlet-msg-success\">");
					jspWriter.write(message);
					jspWriter.write("</div>");
				}
				catch (IOException ioe) {
					throw new JspException(ioe);
				}
			}
		}

		return EVAL_PAGE;
	}

	public void setKey(String key) {
		_key = key;
	}

	public void setMessage(String message) {
		_message = message;
	}

	public void setTranslateMessage(boolean translateMessage) {
		_translateMessage = translateMessage;
	}

	private static final String _PAGE = "/html/taglib/ui/success/page.jsp";

	private String _key;
	private String _message;
	private boolean _translateMessage = true;

}