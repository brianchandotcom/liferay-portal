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

package com.liferay.taglib.theme;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ListMergeable;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.taglib.util.IncludeTag;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspWriter;

/**
 * @author Brian Wing Shun Chan
 */
public class MetaTagsTag extends IncludeTag {

	public static void doTag(
			ServletContext servletContext, HttpServletRequest request,
			HttpServletResponse response)
		throws Exception {

		doTag(_PAGE, servletContext, request, response);
	}

	public static void doTag(
			String page, ServletContext servletContext,
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		RequestDispatcher requestDispatcher =
			servletContext.getRequestDispatcher(page);

		requestDispatcher.include(request, response);
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected int processEndTag() throws Exception {
		HttpServletRequest request =
			(HttpServletRequest)pageContext.getRequest();

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (themeDisplay == null) {
			return EVAL_PAGE;
		}

		Layout layout = themeDisplay.getLayout();

		if (layout == null) {
			return EVAL_PAGE;
		}

		String currentLanguageId = LanguageUtil.getLanguageId(request);
		String defaultLanguageId = LocaleUtil.toLanguageId(
			LocaleUtil.getSiteDefault());

		String w3cCurrentLanguageId = LocaleUtil.toW3cLanguageId(
			currentLanguageId);
		String w3cDefaultLanguageId = LocaleUtil.toW3cLanguageId(
			defaultLanguageId);

		String metaRobots = layout.getRobots(
			themeDisplay.getLanguageId(), false);
		String metaRobotsLanguageId = w3cCurrentLanguageId;

		if (Validator.isNull(metaRobots)) {
			metaRobots = layout.getRobots(defaultLanguageId);
			metaRobotsLanguageId = w3cDefaultLanguageId;
		}

		if (Validator.isNotNull(metaRobots)) {
			_writeMeta(
				HtmlUtil.escape(metaRobots), metaRobotsLanguageId, "robots");
		}

		String metaDescription = layout.getDescription(
			themeDisplay.getLanguageId(), false);
		String metaDescriptionLanguageId = w3cCurrentLanguageId;

		if (Validator.isNull(metaDescription)) {
			metaDescription = layout.getDescription(defaultLanguageId);
			metaDescriptionLanguageId = w3cDefaultLanguageId;
		}

		ListMergeable<String> pageDescriptionListMergeable =
			(ListMergeable<String>)request.getAttribute(
				WebKeys.PAGE_DESCRIPTION);

		if (pageDescriptionListMergeable != null) {
			if (Validator.isNotNull(metaDescription)) {
				StringBundler sb = new StringBundler(4);

				sb.append(
					pageDescriptionListMergeable.mergeToString(
						StringPool.SPACE));
				sb.append(StringPool.PERIOD);
				sb.append(StringPool.SPACE);
				sb.append(metaDescription);

				metaDescription = sb.toString();
			}
			else {
				metaDescription = pageDescriptionListMergeable.mergeToString(
					StringPool.SPACE);
			}
		}

		if (Validator.isNotNull(metaDescription)) {
			_writeMeta(
				HtmlUtil.escape(metaDescription), metaDescriptionLanguageId,
				"description");
		}

		String metaKeywords = layout.getKeywords(
			themeDisplay.getLanguageId(), false);
		String metaKeywordsLanguageId = w3cCurrentLanguageId;

		if (Validator.isNull(metaKeywords)) {
			metaKeywords = layout.getKeywords(defaultLanguageId);
			metaKeywordsLanguageId = w3cDefaultLanguageId;
		}

		ListMergeable<String> pageKeywordsListMergeable =
			(ListMergeable<String>)request.getAttribute(WebKeys.PAGE_KEYWORDS);

		if (pageKeywordsListMergeable != null) {
			if (Validator.isNotNull(metaKeywords)) {
				StringBundler sb = new StringBundler(4);

				sb.append(
					pageKeywordsListMergeable.mergeToString(StringPool.COMMA));
				sb.append(StringPool.COMMA);
				sb.append(StringPool.SPACE);
				sb.append(metaKeywords);

				metaKeywords = sb.toString();
			}
			else {
				metaKeywords = pageKeywordsListMergeable.mergeToString(
					StringPool.COMMA);
			}
		}

		if (Validator.isNotNull(metaKeywords)) {
			_writeMeta(
				HtmlUtil.escape(metaKeywords), metaKeywordsLanguageId,
				"keywords");
		}

		return EVAL_PAGE;
	}

	private void _writeMeta(String content, String lang, String name)
		throws IOException {

		JspWriter jspWriter = pageContext.getOut();

		jspWriter.write("<meta content=\"");
		jspWriter.write(content);
		jspWriter.write("\" lang=\"");
		jspWriter.write(lang);
		jspWriter.write("\" name=\"");
		jspWriter.write(name);
		jspWriter.write("\" />");
	}

	private static final String _PAGE = "/html/taglib/theme/meta_tags/page.jsp";

}