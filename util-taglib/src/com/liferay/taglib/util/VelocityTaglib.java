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

package com.liferay.taglib.util;

import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.Portlet;
import com.liferay.taglib.aui.ColumnTag;
import com.liferay.taglib.aui.LayoutTag;
import com.liferay.taglib.ui.AssetCategoriesSummaryTag;
import com.liferay.taglib.ui.AssetLinksTag;
import com.liferay.taglib.ui.AssetTagsSummaryTag;
import com.liferay.taglib.ui.BreadcrumbTag;
import com.liferay.taglib.ui.DiscussionTag;
import com.liferay.taglib.ui.FlagsTag;
import com.liferay.taglib.ui.IconTag;
import com.liferay.taglib.ui.JournalArticleTag;
import com.liferay.taglib.ui.MySitesTag;
import com.liferay.taglib.ui.PngImageTag;
import com.liferay.taglib.ui.RatingsTag;

import javax.portlet.PortletURL;
import javax.portlet.WindowState;

import javax.servlet.ServletContext;

/**
 * @author Daniel Reuther
 */
public interface VelocityTaglib {

	public void actionURL(long plid, String portletName, String queryString)
		throws Exception;

	public void actionURL(String portletName, String queryString)
		throws Exception;

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #actionURL(String, String,
	 *             Boolean, Boolean, Boolean, String, long, long, String,
	 *             Boolean, Boolean, long, long, Boolean, String)}
	 */
	public void actionURL(
			String windowState, String portletMode, Boolean secure,
			Boolean copyCurrentRenderParameters, Boolean escapeXml, String name,
			long plid, long refererPlid, String portletName, Boolean anchor,
			Boolean encrypt, long doAsUserId, Boolean portletConfiguration,
			String queryString)
		throws Exception;

	public void actionURL(
			String windowState, String portletMode, Boolean secure,
			Boolean copyCurrentRenderParameters, Boolean escapeXml, String name,
			long plid, long refererPlid, String portletName, Boolean anchor,
			Boolean encrypt, long doAsGroupId, long doAsUserId,
			Boolean portletConfiguration, String queryString)
		throws Exception;

	public void actionURL(
			String windowState, String portletMode, long plid,
			String portletName, String queryString)
		throws Exception;

	public void actionURL(
			String windowState, String portletMode, String portletName,
			String queryString)
		throws Exception;

	public void assetCategoriesSummary(
			String className, long classPK, String message,
			PortletURL portletURL)
		throws Exception;

	public void assetLinks(long assetEntryId, String className, long classPK)
		throws Exception;

	public void assetTagsSummary(
			String className, long classPK, String message,
			String assetTagNames, PortletURL portletURL)
		throws Exception;

	public void breadcrumb() throws Exception;

	public void breadcrumb(
			String displayStyle, boolean showGuestGroup,
			boolean showParentGroups, boolean showLayout,
			boolean showPortletBreadcrumb)
		throws Exception;

	public void discussion(
			String className, long classPK, String formAction, String formName,
			boolean hideControls, boolean ratingsEnabled, String redirect,
			String subject, long userId)
		throws Exception;

	public void doAsURL(long doAsUserId) throws Exception;

	public void flags(
			String className, long classPK, String contentTitle, boolean label,
			String message, long reportedUserId)
		throws Exception;

	public AssetCategoriesSummaryTag getAssetCategoriesSummaryTag()
		throws Exception;

	public AssetLinksTag getAssetLinksTag() throws Exception;

	public AssetTagsSummaryTag getAssetTagsSummaryTag() throws Exception;

	public BreadcrumbTag getBreadcrumbTag() throws Exception;

	public ColumnTag getColumnTag() throws Exception;

	public DiscussionTag getDiscussionTag() throws Exception;

	public FlagsTag getFlagsTag() throws Exception;

	public IconTag getIconTag() throws Exception;

	public JournalArticleTag getJournalArticleTag() throws Exception;

	public LayoutTag getLayoutTag() throws Exception;

	public MySitesTag getMySitesTag() throws Exception;

	public PngImageTag getPngImageTag() throws Exception;

	public RatingsTag getRatingsTag() throws Exception;

	public String getSetting(String name);

	public WindowState getWindowState(String windowState);

	public void icon(String image, boolean label, String message, String url)
		throws Exception;

	public void iconBack() throws Exception;

	public void iconClose() throws Exception;

	public void iconConfiguration() throws Exception;

	public void iconEdit() throws Exception;

	public void iconEditDefaults() throws Exception;

	public void iconEditGuest() throws Exception;

	public void iconHelp() throws Exception;

	public void iconHelp(String message) throws Exception;

	public void iconMaximize() throws Exception;

	public void iconMinimize() throws Exception;

	public void iconOptions() throws Exception;

	public void iconPortlet() throws Exception;

	public void iconPortlet(Portlet portlet) throws Exception;

	public void iconPortletCss() throws Exception;

	public void iconPrint() throws Exception;

	public void iconRefresh() throws Exception;

	public void include(ServletContext servletContext, String page)
		throws Exception;

	public void include(String page) throws Exception;

	public void journalArticle(
			String articleId, long groupId, String templateId)
		throws Exception;

	public void journalContentSearch() throws Exception;

	public void journalContentSearch(
			boolean showListed, String targetPortletId, String type)
		throws Exception;

	public void language() throws Exception;

	public void language(
			String formName, String formAction, String name, int displayStyle)
		throws Exception;

	public void language(
			String formName, String formAction, String name,
			String[] languageIds, int displayStyle)
		throws Exception;

	public void layoutIcon(Layout layout) throws Exception;

	public void metaTags() throws Exception;

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #mySites}
	 */
	public void myPlaces() throws Exception;

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #mySites(int)}
	 */
	public void myPlaces(int max) throws Exception;

	public void mySites() throws Exception;

	public void mySites(int max) throws Exception;

	public void permissionsURL(
			String redirect, String modelResource,
			String modelResourceDescription, Object resourceGroupId,
			String resourcePrimKey, String windowState, int[] roleTypes)
		throws Exception;

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #permissionsURL(String,
	 *             String, String, long, String, String, int[])}
	 */
	public void permissionsURL(
			String redirect, String modelResource,
			String modelResourceDescription, String resourcePrimKey,
			String windowState, int[] roleTypes)
		throws Exception;

	public void ratings(
			String className, long classPK, int numberOfStars, String type,
			String url)
		throws Exception;

	public void renderURL(long plid, String portletName, String queryString)
		throws Exception;

	public void renderURL(String portletName, String queryString)
		throws Exception;

	public void renderURL(
			String windowState, String portletMode, Boolean secure,
			Boolean copyCurrentRenderParameters, Boolean escapeXml, long plid,
			long refererPlid, String portletName, Boolean anchor,
			Boolean encrypt, long doAsGroupId, long doAsUserId,
			Boolean portletConfiguration, String queryString)
		throws Exception;

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #renderURL(String, String,
	 *             Boolean, Boolean, Boolean, long, long, String, Boolean,
	 *             Boolean, long, long, Boolean, String)}
	 */
	public void renderURL(
			String windowState, String portletMode, Boolean secure,
			Boolean copyCurrentRenderParameters, Boolean escapeXml, long plid,
			String portletName, Boolean anchor, Boolean encrypt,
			long doAsUserId, Boolean portletConfiguration, String queryString)
		throws Exception;

	public void renderURL(
			String windowState, String portletMode, long plid,
			String portletName, String queryString)
		throws Exception;

	public void renderURL(
			String windowState, String portletMode, String portletName,
			String queryString)
		throws Exception;

	public void runtime(String portletName) throws Exception;

	public void runtime(String portletName, String queryString)
		throws Exception;

	public void runtime(
			String portletName, String queryString, String defaultPreferences)
		throws Exception;

	public void search() throws Exception;

	public void setTemplate(Template template);

	public void sitesDirectory() throws Exception;

	public void sitesDirectory(String displayStyle, String sites)
		throws Exception;

	public void socialBookmarks(
			String displayStyle, String target, String types, String title,
			String url)
		throws Exception;

	public void staging() throws Exception;

	public void toggle(
			String id, String showImage, String hideImage, String showMessage,
			String hideMessage, boolean defaultShowContent)
		throws Exception;

	public String wrapPortlet(String wrapPage, String portletPage)
		throws Exception;

}