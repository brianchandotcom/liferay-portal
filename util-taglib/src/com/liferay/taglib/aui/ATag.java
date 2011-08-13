/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.servlet.taglib.CustomAttributes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.taglib.aui.base.BaseATag;

import java.util.Map;

import javax.portlet.PortletResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;

/**
 * @author Julio Camarero
 * @author Jorge Ferrer
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public class ATag extends BaseATag {

	protected boolean isOpensNewWindow() {
		String target = getTarget();

		if ((target != null) &&
			(target.equals("_blank") || target.equals("_new"))) {

			return true;
		}
		else {
			return false;
		}
	}

	@Override
	protected int processEndTag() throws Exception {
		JspWriter jspWriter = pageContext.getOut();

		if (Validator.isNotNull(getHref())) {
			if (isOpensNewWindow()) {
				jspWriter.write("<span class=\"opens-new-window-accessible\">");
				jspWriter.write(
					LanguageUtil.get(pageContext, "opens-new-window"));
				jspWriter.write("</span>");
			}

			jspWriter.write("</a>");
		}
		else {
			jspWriter.write("</span>");
		}

		return EVAL_PAGE;
	}

	@Override
	protected int processStartTag() throws Exception {
		JspWriter jspWriter = pageContext.getOut();

		String href = getHref();
		String cssClass = getCssClass();
		String id = getId();
		String namespace = _getNamespace();
		String lang = getLang();
		String onClick = getOnClick();
		String target = getTarget();
		String title = getTitle();
		Map<String, Object> data = getData();
		String label = getLabel();

		if (Validator.isNotNull(href)) {
			jspWriter.write("<a ");

			if (Validator.isNotNull(cssClass)) {
				jspWriter.write("class=\"");
				jspWriter.write(cssClass);
				jspWriter.write("\" ");
			}

			jspWriter.write("href=\"");
			jspWriter.write(HtmlUtil.escape(href));
			jspWriter.write("\" ");

			if (Validator.isNotNull(id)) {
				jspWriter.write("id=\"");
				jspWriter.write(namespace);
				jspWriter.write(id);
				jspWriter.write("\" ");
			}

			if (Validator.isNotNull(lang)) {
				jspWriter.write("lang=\"");
				jspWriter.write(lang);
				jspWriter.write("\" ");
			}

			if (Validator.isNotNull(onClick)) {
				jspWriter.write("onClick=\"");
				jspWriter.write(onClick);
				jspWriter.write("\" ");
			}

			if (Validator.isNotNull(target)) {
				jspWriter.write("target=\"");
				jspWriter.write(target);
				jspWriter.write("\" ");
			}

			if (Validator.isNotNull(title) || isOpensNewWindow()) {
				jspWriter.write("title=\"");

				if (Validator.isNotNull(title)) {
					jspWriter.write(LanguageUtil.get(pageContext, title));
				}

				if (isOpensNewWindow()) {
					jspWriter.write(
						LanguageUtil.get(pageContext, "opens-new-window"));
				}

				jspWriter.write("\" ");
			}

			CustomAttributes customAttributes = getCustomAttributes();

			if (customAttributes != null) {
				jspWriter.write(customAttributes.toString());
			}

			if (data != null) {
				jspWriter.write(AUIUtil.buildData(data));
			}

			writeDynamicAttributes(jspWriter);

			jspWriter.write(">");

			if (Validator.isNotNull(label)) {
				jspWriter.write(LanguageUtil.get(pageContext, label));
			}
		}
		else {
			jspWriter.write("<span ");

			if (Validator.isNotNull(cssClass)) {
				jspWriter.write("class=\"");
				jspWriter.write(cssClass);
				jspWriter.write("\" ");
			}

			if (Validator.isNotNull(id)) {
				jspWriter.write("id=\"");
				jspWriter.write(namespace);
				jspWriter.write(id);
				jspWriter.write("\" ");
			}

			if (Validator.isNotNull(lang)) {
				jspWriter.write("lang=\"");
				jspWriter.write(lang);
				jspWriter.write("\" ");
			}

			if (Validator.isNotNull(title) || isOpensNewWindow()) {
				jspWriter.write("title=\"");

				if (Validator.isNotNull(title)) {
					jspWriter.write(LanguageUtil.get(pageContext, title));
				}

				if (isOpensNewWindow()) {
					jspWriter.write(
						LanguageUtil.get(pageContext, "opens-new-window"));
				}

				jspWriter.write("\" ");
			}

			CustomAttributes customAttributes = getCustomAttributes();

			if (customAttributes != null) {
				jspWriter.write(customAttributes.toString());
			}

			if (data != null) {
				jspWriter.write(AUIUtil.buildData(data));
			}

			writeDynamicAttributes(jspWriter);

			jspWriter.write(">");

			if (Validator.isNotNull(label)) {
				jspWriter.write(LanguageUtil.get(pageContext, label));
			}
		}

		return EVAL_BODY_INCLUDE;
	}

	private String _getNamespace() {
		HttpServletRequest request =
			(HttpServletRequest)pageContext.getRequest();

		PortletResponse portletResponse = (PortletResponse)request.getAttribute(
			JavaConstants.JAVAX_PORTLET_RESPONSE);

		String namespace = StringPool.BLANK;

		boolean useNamespace = GetterUtil.getBoolean(
			(String)request.getAttribute("aui:form:useNamespace"), true);

		if ((portletResponse != null) && useNamespace) {
			namespace = portletResponse.getNamespace();
		}

		return namespace;
	}

}