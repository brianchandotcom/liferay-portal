/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.taglib.aui;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.taglib.aui.base.BaseATag;
import com.liferay.taglib.util.InlineUtil;
import com.liferay.taglib.util.TagResourceBundleUtil;

import java.util.Map;

import javax.portlet.PortletResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;

/**
 * @author Julio Camarero
 * @author Jorge Ferrer
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
 *             com.liferay.frontend.taglib.clay.servlet.taglib.LinkTag}
 */
@Deprecated
public class ATag extends BaseATag {

	@Override
	protected int processEndTag() throws Exception {
		JspWriter jspWriter = pageContext.getOut();

		if (Validator.isNotNull(getHref())) {
			if (AUIUtil.isOpensNewWindow(getTarget()) &&
				Validator.isNull(getIcon())) {

				HttpServletRequest httpServletRequest = getRequest();

				ThemeDisplay themeDisplay =
					(ThemeDisplay)httpServletRequest.getAttribute(
						WebKeys.THEME_DISPLAY);

				jspWriter.write(StringPool.SPACE);
				jspWriter.write("<svg class=\"lexicon-icon ");
				jspWriter.write("lexicon-icon-shortcut\" focusable=\"false\" ");
				jspWriter.write("role=\"img\"><use href=\"");
				jspWriter.write(themeDisplay.getPathThemeSpritemap());
				jspWriter.write("#shortcut\" /><span ");
				jspWriter.write("class=\"sr-only\">");

				String opensNewWindowLabel = LanguageUtil.get(
					TagResourceBundleUtil.getResourceBundle(pageContext),
					"opens-new-window");

				jspWriter.write(opensNewWindowLabel);

				jspWriter.write("</span>");
				jspWriter.write("<title>");
				jspWriter.write(opensNewWindowLabel);
				jspWriter.write("</title>");
				jspWriter.write("</svg>");
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

		String ariaLabel = getAriaLabel();
		String ariaRole = getAriaRole();
		String cssClass = getCssClass();
		Map<String, Object> data = getData();
		String href = getHref();
		String id = getId();
		String iconCssClass = getIconCssClass();
		String label = getLabel();
		String lang = getLang();
		String onClick = getOnClick();
		String title = getTitle();

		if (Validator.isNotNull(href)) {
			jspWriter.write("<a ");

			jspWriter.write("href=\"");
			jspWriter.write(HtmlUtil.escapeAttribute(href));
			jspWriter.write("\" ");

			String target = getTarget();

			if (Validator.isNotNull(target)) {
				jspWriter.write("target=\"");
				jspWriter.write(target);
				jspWriter.write("\" ");
			}
		}
		else {
			jspWriter.write("<span ");
		}

		if (Validator.isNotNull(ariaLabel)) {
			jspWriter.write("aria-label=\"");
			jspWriter.write(ariaLabel);
			jspWriter.write("\" ");
		}

		if (Validator.isNotNull(cssClass)) {
			jspWriter.write("class=\"");
			jspWriter.write(cssClass);
			jspWriter.write("\" ");
		}

		if (Validator.isNotNull(id)) {
			jspWriter.write("id=\"");
			jspWriter.write(_getNamespace());
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
			jspWriter.write(HtmlUtil.escapeAttribute(onClick));
			jspWriter.write("\" ");
		}

		if (Validator.isNotNull(ariaRole)) {
			jspWriter.write("role=\"");
			jspWriter.write(ariaRole);
			jspWriter.write("\" ");
		}

		if (Validator.isNotNull(title)) {
			jspWriter.write("title=\"");

			if (Validator.isNotNull(title)) {
				jspWriter.write(
					LanguageUtil.get(
						TagResourceBundleUtil.getResourceBundle(pageContext),
						title));
			}

			jspWriter.write("\" ");
		}

		if ((data != null) && !data.isEmpty()) {
			jspWriter.write(AUIUtil.buildData(data));
		}

		_writeDynamicAttributes(jspWriter);

		jspWriter.write(">");

		if (Validator.isNotNull(label)) {
			if (getLocalizeLabel()) {
				jspWriter.write(
					LanguageUtil.get(
						TagResourceBundleUtil.getResourceBundle(pageContext),
						label));
			}
			else {
				jspWriter.write(label);
			}
		}

		if (Validator.isNotNull(iconCssClass)) {
			jspWriter.write("<span class=\"icon-monospaced ");
			jspWriter.write(iconCssClass);
			jspWriter.write("\"></span>");
		}

		return EVAL_BODY_INCLUDE;
	}

	private String _getNamespace() {
		HttpServletRequest httpServletRequest =
			(HttpServletRequest)pageContext.getRequest();

		PortletResponse portletResponse =
			(PortletResponse)httpServletRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_RESPONSE);

		if (portletResponse == null) {
			return StringPool.BLANK;
		}

		if (GetterUtil.getBoolean(
				(String)httpServletRequest.getAttribute(
					"aui:form:useNamespace"),
				true)) {

			return portletResponse.getNamespace();
		}

		return StringPool.BLANK;
	}

	private void _writeDynamicAttributes(JspWriter jspWriter) throws Exception {
		String dynamicAttributesString = InlineUtil.buildDynamicAttributes(
			getDynamicAttributes());

		if (!dynamicAttributesString.isEmpty()) {
			jspWriter.write(dynamicAttributesString);
		}
	}

}