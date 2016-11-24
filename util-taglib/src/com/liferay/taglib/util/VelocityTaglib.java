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

package com.liferay.taglib.util;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.taglib.ui.AssetCategoriesSummaryTag;
import com.liferay.taglib.ui.AssetLinksTag;
import com.liferay.taglib.ui.AssetTagsSummaryTag;
import com.liferay.taglib.ui.BreadcrumbTag;
import com.liferay.taglib.ui.DiscussionTag;
import com.liferay.taglib.ui.IconTag;
import com.liferay.taglib.ui.JournalArticleTag;
import com.liferay.taglib.ui.RatingsTag;

import javax.portlet.PortletURL;
import javax.portlet.WindowState;

import javax.servlet.ServletContext;
import javax.servlet.jsp.PageContext;

/**
 * @author Daniel Reuther
 */
@ProviderType
public interface VelocityTaglib {

	public String actionURL(long plid, String portletName, String queryString)
		throws Exception;

	public String actionURL(String portletName, String queryString)
		throws Exception;

	public String actionURL(
			String windowState, String portletMode, Boolean secure,
			Boolean copyCurrentRenderParameters, Boolean escapeXml, String name,
			long plid, long refererPlid, String portletName, Boolean anchor,
			Boolean encrypt, long doAsGroupId, long doAsUserId,
			Boolean portletConfiguration, String queryString)
		throws Exception;

	public String actionURL(
			String windowState, String portletMode, long plid,
			String portletName, String queryString)
		throws Exception;

	public String actionURL(
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
			long ddmTemplateGroupId, String ddmTemplateKey,
			boolean showGuestGroup, boolean showParentGroups,
			boolean showLayout, boolean showPortletBreadcrumb)
		throws Exception;

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #breadcrumb(long, String,
	 *             boolean, boolean, boolean, boolean)}}
	 */
	@Deprecated
	public void breadcrumb(
			String ddmTemplateKey, boolean showGuestGroup,
			boolean showParentGroups, boolean showLayout,
			boolean showPortletBreadcrumb)
		throws Exception;

	public void discussion(
			String className, long classPK, String formAction, String formName,
			boolean hideControls, boolean ratingsEnabled, String redirect,
			long userId)
		throws Exception;

	public void doAsURL(long doAsUserId) throws Exception;

	public AssetCategoriesSummaryTag<?> getAssetCategoriesSummaryTag()
		throws Exception;

	public AssetLinksTag getAssetLinksTag() throws Exception;

	public AssetTagsSummaryTag<?> getAssetTagsSummaryTag() throws Exception;

	public BreadcrumbTag getBreadcrumbTag() throws Exception;

	public DiscussionTag getDiscussionTag() throws Exception;

	public IconTag getIconTag() throws Exception;

	public JournalArticleTag getJournalArticleTag() throws Exception;

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	public com.liferay.taglib.ui.MySitesTag getMySitesTag() throws Exception;

	public PageContext getPageContext();

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	public com.liferay.taglib.ui.PngImageTag getPngImageTag() throws Exception;

	public RatingsTag getRatingsTag() throws Exception;

	public String getSetting(String name);

	public WindowState getWindowState(String windowState);

	public void icon(String image, boolean label, String message, String url)
		throws Exception;

	public void iconHelp(String message) throws Exception;

	public void include(ServletContext servletContext, String page)
		throws Exception;

	public void include(String page) throws Exception;

	public void journalArticle(
			String articleId, long groupId, String ddmTemplateKey)
		throws Exception;

	public void journalContentSearch() throws Exception;

	public void journalContentSearch(boolean showListed, String targetPortletId)
		throws Exception;

	public void language() throws Exception;

	public void language(
			String formName, String formAction, String name,
			String displayStyle)
		throws Exception;

	public void language(
			String formName, String formAction, String name,
			String[] languageIds, String displayStyle)
		throws Exception;

	public void layoutIcon(Layout layout) throws Exception;

	public void metaTags() throws Exception;

	public void mySites() throws Exception;

	public void mySites(int max) throws Exception;

	public String permissionsURL(
			String redirect, String modelResource,
			String modelResourceDescription, Object resourceGroupId,
			String resourcePrimKey, String windowState, int[] roleTypes)
		throws Exception;

	public void portletIconBack() throws Exception;

	public void portletIconOptions() throws Exception;

	public void portletIconOptions(String direction, String markupView)
		throws Exception;

	public void portletIconPortlet() throws Exception;

	public void portletIconPortlet(Portlet portlet) throws Exception;

	public void ratings(
			String className, long classPK, int numberOfStars, String type,
			String url)
		throws Exception;

	public String renderURL(long plid, String portletName, String queryString)
		throws Exception;

	public String renderURL(String portletName, String queryString)
		throws Exception;

	public String renderURL(
			String windowState, String portletMode, Boolean secure,
			Boolean copyCurrentRenderParameters, Boolean escapeXml, long plid,
			long refererPlid, String portletName, Boolean anchor,
			Boolean encrypt, long doAsGroupId, long doAsUserId,
			Boolean portletConfiguration, String queryString)
		throws Exception;

	public String renderURL(
			String windowState, String portletMode, long plid,
			String portletName, String queryString)
		throws Exception;

	public String renderURL(
			String windowState, String portletMode, String portletName,
			String queryString)
		throws Exception;

	public void runtime(String portletName) throws Exception;

	public void runtime(
			String portletProviderClassName,
			PortletProvider.Action portletProviderAction)
		throws Exception;

	public void runtime(
			String portletProviderClassName,
			PortletProvider.Action portletProviderAction, String instanceId)
		throws Exception;

	public void runtime(
			String portletProviderClassName,
			PortletProvider.Action portletProviderAction, String instanceId,
			String defaultPreferences)
		throws Exception;

	public void runtime(String portletName, String queryString)
		throws Exception;

	public void runtime(
			String portletName, String queryString, String defaultPreferences)
		throws Exception;

	public void runtime(
			String portletName, String instanceId, String queryString,
			String defaultPreferences)
		throws Exception;

	public void search() throws Exception;

	public void sitesDirectory() throws Exception;

	public void sitesDirectory(String displayStyle, String sites)
		throws Exception;

	public void socialBookmarks(
			String displayStyle, String target, String types, String title,
			String url)
		throws Exception;

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	public void staging() throws Exception;

	public void toggle(
			String id, String showImage, String hideImage, String showMessage,
			String hideMessage, boolean defaultShowContent)
		throws Exception;

	public String wrapPortlet(String wrapPage, String portletPage)
		throws Exception;

}