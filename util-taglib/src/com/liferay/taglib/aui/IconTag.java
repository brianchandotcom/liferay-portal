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

import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.taglib.aui.base.BaseIconTag;
import com.liferay.taglib.ui.MessageTag;
import com.liferay.taglib.util.InlineUtil;

import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

/**
 * @author Eduardo Lundgren
 * @author Bruno Basto
 * @author Nathan Cavanaugh
 * @author Julio Camarero
 */
public class IconTag extends BaseIconTag {

	@Override
	public String getId() {
		String id = super.getId();

		if (Validator.isNotNull(id)) {
			return id;
		}

		id = PortalUtil.generateRandomKey(
			getRequest(), IconTag.class.getName());

		id = HtmlUtil.getAUICompatibleId(id);

		return id;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected int processEndTag() throws Exception {
		String url = getUrl();

		if (url == null) {
			JspWriter jspWriter = pageContext.getOut();

			jspWriter.write("<span ");

			String cssClass = GetterUtil.getString(getCssClass());

			jspWriter.write("class=\"");

			if (Validator.isNotNull(cssClass)) {
				jspWriter.write(cssClass);
				jspWriter.write("\" ");
			}
			else {
				jspWriter.write("c-inner");
				jspWriter.write("\" tabindex=\"-1\" ");
			}

			jspWriter.write(AUIUtil.buildData(getData()));
			jspWriter.write(" id=\"");
			jspWriter.write(GetterUtil.getString(getId()));
			jspWriter.write("\">");

			_processIconContent(pageContext);

			jspWriter.write("</span>");
		}
		else {
			ATag aTag = new ATag();

			aTag.setAriaLabel(getAriaLabel());
			aTag.setCssClass(getCssClass());
			aTag.setData(getData());
			aTag.setHref(getUrl());
			aTag.setId(getId());
			aTag.setTarget(getTarget());

			aTag.doBodyTag(pageContext, this::_processIconContent);
		}

		return EVAL_PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		if (getSrc() == null) {
			String src = (String)httpServletRequest.getAttribute(
				"aui:icon:src:ext");

			if (Validator.isNotNull(src)) {
				setSrc(src);
			}

			httpServletRequest.removeAttribute("aui:icon:src:ext");
		}

		super.setAttributes(httpServletRequest);
	}

	private void _processIconContent(PageContext pageContext) {
		JspWriter jspWriter = pageContext.getOut();

		try {
			if (Objects.equals(getMarkupView(), "lexicon")) {
				jspWriter.write("<svg aria-hidden=\"true\" ");
				jspWriter.write("class=\"lexicon-icon lexicon-icon-");
				jspWriter.write(GetterUtil.getString(getImage()));
				jspWriter.write("\" focusable=\"false\" ");
				jspWriter.write(
					InlineUtil.buildDynamicAttributes(getDynamicAttributes()));
				jspWriter.write("><use href=\"");

				String src = getSrc();

				if (src == null) {
					HttpServletRequest httpServletRequest =
						(HttpServletRequest)pageContext.getRequest();

					ThemeDisplay themeDisplay =
						(ThemeDisplay)httpServletRequest.getAttribute(
							WebKeys.THEME_DISPLAY);

					src = themeDisplay.getPathThemeImages() + "/clay/icons.svg";
				}

				jspWriter.write(src);
				jspWriter.write(StringPool.POUND);
				jspWriter.write(GetterUtil.getString(getImage()));
				jspWriter.write("\"></use>");
				jspWriter.write("</svg>");
			}
			else {
				jspWriter.write("<i class=\"icon-");
				jspWriter.write(GetterUtil.getString(getImage()));
				jspWriter.write("\"></i>");
			}

			String label = getLabel();

			if (label != null) {
				jspWriter.write("<span class=\"ml-2 taglib-icon-label\">");

				MessageTag messageTag = new MessageTag();

				messageTag.setKey(label);

				messageTag.doTag(pageContext);

				jspWriter.write("</span>");
			}
		}
		catch (Exception exception) {
			ReflectionUtil.throwException(exception);
		}
	}

	private static final String _PAGE = "/html/taglib/aui/icon/page.jsp";

}