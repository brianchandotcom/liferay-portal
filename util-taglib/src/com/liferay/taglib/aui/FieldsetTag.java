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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.taglib.aui.base.BaseFieldsetTag;
import com.liferay.taglib.ui.IconHelpTag;
import com.liferay.taglib.ui.MessageTag;
import com.liferay.taglib.util.InlineUtil;

import javax.portlet.PortletResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

/**
 * @author Julio Camarero
 * @author Jorge Ferrer
 * @author Brian Wing Shun Chan
 */
public class FieldsetTag extends BaseFieldsetTag {

	@Override
	public int doStartTag() throws JspException {
		FieldsetGroupTag fieldsetGroupTag =
			(FieldsetGroupTag)findAncestorWithClass(
				this, FieldsetGroupTag.class);

		if (Validator.isNull(getMarkupView()) && (fieldsetGroupTag != null)) {
			setMarkupView(fieldsetGroupTag.getMarkupView());
		}

		return super.doStartTag();
	}

	@Override
	protected String getEndPage() {
		if (Validator.isNotNull(getMarkupView())) {
			return "/html/taglib/aui/fieldset/" + getMarkupView() + "/end.jsp";
		}

		return "/html/taglib/aui/fieldset/end.jsp";
	}

	@Override
	protected String getStartPage() {
		if (Validator.isNotNull(getMarkupView())) {
			return "/html/taglib/aui/fieldset/" + getMarkupView() +
				"/start.jsp";
		}

		return "/html/taglib/aui/fieldset/start.jsp";
	}

	@Override
	protected boolean isCleanUpSetAttributes() {
		return _CLEAN_UP_SET_ATTRIBUTES;
	}

	@Override
	protected int processEndTag() throws Exception {
		JspWriter jspWriter = pageContext.getOut();

		jspWriter.write("</div></fieldset>");

		return EVAL_PAGE;
	}

	@Override
	protected int processStartTag() throws Exception {
		JspWriter jspWriter = pageContext.getOut();

		jspWriter.write("<fieldset class=\"fieldset ");
		jspWriter.write(GetterUtil.getString(getCssClass()));
		jspWriter.write("\" ");

		String id = getId();

		if (id != null) {
			jspWriter.write("id=\"");
			jspWriter.write(id);
			jspWriter.write("\" ");
		}

		jspWriter.write(
			InlineUtil.buildDynamicAttributes(getDynamicAttributes()));

		jspWriter.write(StringPool.GREATER_THAN);

		String lable = getLabel();

		if (lable != null) {
			jspWriter.write(
				"<legend class=\"fieldset-legend\"><span class=\"legend\">");

			MessageTag messageTag = new MessageTag();

			messageTag.setKey(lable);
			messageTag.setLocalizeKey(getLocalizeLabel());

			messageTag.doTag(pageContext);

			String helpMessage = getHelpMessage();

			if (helpMessage != null) {
				IconHelpTag iconHelpTag = new IconHelpTag();

				iconHelpTag.setMessage(helpMessage);

				iconHelpTag.doTag(pageContext);
			}

			jspWriter.write("</span></legend>");
		}

		if (getColumn()) {
			jspWriter.write("<div class=\"row\">");
		}
		else {
			jspWriter.write("<div class=\"\">");
		}

		return EVAL_BODY_INCLUDE;
	}

	@Override
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		if (Validator.isNull(getId()) && Validator.isNotNull(getLabel()) &&
			getCollapsible()) {

			String id = PortalUtil.getUniqueElementId(
				httpServletRequest, _getNamespace(),
				AUIUtil.normalizeId(getLabel()));

			setId(_getNamespace() + id);
		}

		super.setAttributes(httpServletRequest);
	}

	private String _getNamespace() {
		HttpServletRequest httpServletRequest =
			(HttpServletRequest)pageContext.getRequest();

		PortletResponse portletResponse =
			(PortletResponse)httpServletRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_RESPONSE);

		if (portletResponse != null) {
			return portletResponse.getNamespace();
		}

		return StringPool.BLANK;
	}

	private static final boolean _CLEAN_UP_SET_ATTRIBUTES = true;

}