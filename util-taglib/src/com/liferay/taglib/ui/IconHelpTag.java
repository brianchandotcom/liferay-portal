/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.servlet.taglib.FileAvailabilityUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.util.PwdGenerator;

import javax.servlet.jsp.JspWriter;

/**
 * @author Scott Lee
 * @author Shuyang Zhou
 */
public class IconHelpTag extends IconTag {

	@Override
	protected String getPage() {
		if (FileAvailabilityUtil.isAvailable(servletContext, _PAGE)) {
			return _PAGE;
		}
		else {
			return null;
		}
	}

	@Override
	protected int processEndTag() throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay)pageContext.getAttribute(
			"themeDisplay");

		JspWriter jspWriter = pageContext.getOut();

		String id = PwdGenerator.getPassword(PwdGenerator.KEY3, 4);

		jspWriter.write("<span class=\"taglib-icon-help\"><img alt=\"\" ");
		jspWriter.write("aria-labelledby=\"");
		jspWriter.write(id);
		jspWriter.write("\" ");
		jspWriter.write("onBlur=\"Liferay.Portal.ToolTip.hide();\" ");
		jspWriter.write("onFocus=\"Liferay.Portal.ToolTip.show(this);\" ");
		jspWriter.write("onMouseOver=\"Liferay.Portal.ToolTip.show(this);\" ");
		jspWriter.write("src=\"");
		jspWriter.write(themeDisplay.getPathThemeImages());
		jspWriter.write("/portlet/help.png\" tabIndex=\"0\" ");
		jspWriter.write("/><span ");
		jspWriter.write("class=\"hide-accessible tooltip-text\" ");
		jspWriter.write("id=\"");
		jspWriter.write(id);
		jspWriter.write("\" >");
		jspWriter.write(LanguageUtil.get(pageContext, getMessage()));
		jspWriter.write("</span></span>");

		return EVAL_PAGE;
	}

	private static final String _PAGE = "/html/taglib/ui/icon_help/page.jsp";

}