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

package com.liferay.taglib.ui;

import com.liferay.portal.kernel.portlet.PortletRequestModel;
import com.liferay.taglib.util.IncludeTag;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Raymond Augé
 */
public class JournalArticleTag extends IncludeTag {

	public void setArticleId(String articleId) {
		_articleId = articleId;
	}

	public void setArticlePage(int articlePage) {
		_articlePage = articlePage;
	}

	public void setArticleResourcePrimKey(long articleResourcePrimKey) {
		_articleResourcePrimKey = articleResourcePrimKey;
	}

	public void setDDMTemplateKey(String ddmTemplateKey) {
		_ddmTemplateKey = ddmTemplateKey;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	public void setLanguageId(String languageId) {
		_languageId = languageId;
	}

	public void setPortletRequestModel(
		PortletRequestModel portletRequestModel) {

		_portletRequestModel = portletRequestModel;
	}

	public void setShowAvailableLocales(boolean showAvailableLocales) {
		_showAvailableLocales = showAvailableLocales;
	}

	public void setShowTitle(boolean showTitle) {
		_showTitle = showTitle;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_articleId = null;
		_articlePage = 1;
		_articleResourcePrimKey = 0;
		_ddmTemplateKey = null;
		_groupId = 0;
		_languageId = null;
		_portletRequestModel = null;
		_showAvailableLocales = false;
		_showTitle = false;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		httpServletRequest.setAttribute(
			"liferay-ui:journal-article:articleId", _articleId);
		httpServletRequest.setAttribute(
			"liferay-ui:journal-article:articlePage",
			String.valueOf(_articlePage));
		httpServletRequest.setAttribute(
			"liferay-ui:journal-article:articleResourcePrimKey",
			String.valueOf(_articleResourcePrimKey));
		httpServletRequest.setAttribute(
			"liferay-ui:journal-article:ddmTemplateKey", _ddmTemplateKey);
		httpServletRequest.setAttribute(
			"liferay-ui:journal-article:groupId", String.valueOf(_groupId));
		httpServletRequest.setAttribute(
			"liferay-ui:journal-article:languageId", _languageId);
		httpServletRequest.setAttribute(
			"liferay-ui:journal-article:portletRequestModel",
			_portletRequestModel);
		httpServletRequest.setAttribute(
			"liferay-ui:journal-article:showAvailableLocales",
			String.valueOf(_showAvailableLocales));
		httpServletRequest.setAttribute(
			"liferay-ui:journal-article:showTitle", String.valueOf(_showTitle));
	}

	private static final String _PAGE =
		"/html/taglib/ui/journal_article/page.jsp";

	private String _articleId;
	private int _articlePage = 1;
	private long _articleResourcePrimKey;
	private String _ddmTemplateKey;
	private long _groupId;
	private String _languageId;
	private PortletRequestModel _portletRequestModel;
	private boolean _showAvailableLocales;
	private boolean _showTitle;

}