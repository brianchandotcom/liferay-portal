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

package com.liferay.taglib.aui;

import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.taglib.aui.base.BaseIconTag;
import com.liferay.taglib.servlet.PipingServletResponse;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;

/**
 * @author Eduardo Lundgren
 * @author Bruno Basto
 * @author Nathan Cavanaugh
 * @author Julio Camarero
 */
public class IconTag extends BaseIconTag {

	public static String doTag(
			String cssClass, String image, String markupView,
			HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {

		IconTag iconTag = new IconTag();

		iconTag.setCssClass(cssClass);
		iconTag.setImage(image);
		iconTag.setMarkupView(markupView);

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		try {
			iconTag.doTag(
				request,
				new PipingServletResponse(response, unsyncStringWriter));
		}
		catch (JspException je) {
			throw new ServletException(je);
		}

		return unsyncStringWriter.toString();
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		if (getSrc() == null) {
			String src = (String)request.getAttribute("aui:icon:src:ext");

			if (Validator.isNotNull(src)) {
				setSrc(src);
			}

			request.removeAttribute("aui:icon:src:ext");
		}

		super.setAttributes(request);
	}

	private static final String _PAGE = "/html/taglib/aui/icon/page.jsp";

}