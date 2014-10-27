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

import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.kernel.util.StringPool;
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
import com.liferay.taglib.ui.QuickAccessTag;
import com.liferay.taglib.ui.RatingsTag;

import javax.portlet.PortletURL;
import javax.portlet.WindowState;

import javax.servlet.ServletContext;
import javax.servlet.jsp.PageContext;

/**
 * @author Daniel Reuther
 */
public class DummyVelocityTaglib implements VelocityTaglib {

	@Override
	public String actionURL(long plid, String portletName, String queryString)
		throws Exception {

		return null;
	}

	@Override
	public String actionURL(String portletName, String queryString)
		throws Exception {

		return null;
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #actionURL(String, String,
	 *             Boolean, Boolean, Boolean, String, long, long, String,
	 *             Boolean, Boolean, long, long, Boolean, String)}
	 */
	@Deprecated
	@Override
	public String actionURL(
			String windowState, String portletMode, Boolean secure,
			Boolean copyCurrentRenderParameters, Boolean escapeXml, String name,
			long plid, long refererPlid, String portletName, Boolean anchor,
			Boolean encrypt, long doAsUserId, Boolean portletConfiguration,
			String queryString)
		throws Exception {

		return null;
	}

	@Override
	public String actionURL(
			String windowState, String portletMode, Boolean secure,
			Boolean copyCurrentRenderParameters, Boolean escapeXml, String name,
			long plid, long refererPlid, String portletName, Boolean anchor,
			Boolean encrypt, long doAsGroupId, long doAsUserId,
			Boolean portletConfiguration, String queryString)
		throws Exception {

		return null;
	}

	@Override
	public String actionURL(
			String windowState, String portletMode, long plid,
			String portletName, String queryString)
		throws Exception {

		return null;
	}

	@Override
	public String actionURL(
			String windowState, String portletMode, String portletName,
			String queryString)
		throws Exception {

		return null;
	}

	@Override
	public void assetCategoriesSummary(
			String className, long classPK, String message,
			PortletURL portletURL)
		throws Exception {
	}

	@Override
	public void assetLinks(long assetEntryId, String className, long classPK)
		throws Exception {
	}

	@Override
	public void assetTagsSummary(
			String className, long classPK, String message,
			String assetTagNames, PortletURL portletURL)
		throws Exception {
	}

	@Override
	public void breadcrumb() throws Exception {
	}

	@Override
	public void breadcrumb(
			String displayStyle, boolean showGuestGroup,
			boolean showParentGroups, boolean showLayout,
			boolean showPortletBreadcrumb)
		throws Exception {
	}

	@Override
	public void discussion(
			String className, long classPK, String formAction, String formName,
			boolean hideControls, boolean ratingsEnabled, String redirect,
			long userId)
		throws Exception {
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #discussion(String, long,
	 *             String, String, boolean, boolean, String, long)})}
	 */
	@Deprecated
	@Override
	public void discussion(
			String className, long classPK, String formAction, String formName,
			boolean hideControls, boolean ratingsEnabled, String redirect,
			String subject, long userId)
		throws Exception {
	}

	@Override
	public void doAsURL(long doAsUserId) throws Exception {
	}

	@Override
	public void flags(
			String className, long classPK, String contentTitle, boolean label,
			String message, long reportedUserId)
		throws Exception {
	}

	@Override
	public AssetCategoriesSummaryTag getAssetCategoriesSummaryTag()
		throws Exception {

		return null;
	}

	@Override
	public AssetLinksTag getAssetLinksTag() throws Exception {
		return null;
	}

	@Override
	public AssetTagsSummaryTag getAssetTagsSummaryTag() throws Exception {
		return null;
	}

	@Override
	public BreadcrumbTag getBreadcrumbTag() throws Exception {
		return null;
	}

	@Override
	public ColumnTag getColumnTag() throws Exception {
		return null;
	}

	@Override
	public DiscussionTag getDiscussionTag() throws Exception {
		return null;
	}

	@Override
	public FlagsTag getFlagsTag() throws Exception {
		return null;
	}

	@Override
	public IconTag getIconTag() throws Exception {
		return null;
	}

	@Override
	public JournalArticleTag getJournalArticleTag() throws Exception {
		return null;
	}

	@Override
	public LayoutTag getLayoutTag() throws Exception {
		return null;
	}

	@Override
	public MySitesTag getMySitesTag() throws Exception {
		return null;
	}

	@Override
	public PageContext getPageContext() {
		return null;
	}

	@Override
	public PngImageTag getPngImageTag() throws Exception {
		return null;
	}

	@Override
	public QuickAccessTag getQuickAccessTag() throws Exception {
		return null;
	}

	@Override
	public RatingsTag getRatingsTag() throws Exception {
		return null;
	}

	@Override
	public String getSetting(String name) {
		return null;
	}

	@Override
	public WindowState getWindowState(String windowState) {
		return null;
	}

	@Override
	public void icon(String image, boolean label, String message, String url)
		throws Exception {
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #iconBack}
	 */
	@Deprecated
	@Override
	public void iconBack() throws Exception {
		portletIconBack();
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #portletIconClose}
	 */
	@Deprecated
	@Override
	public void iconClose() throws Exception {
		portletIconClose();
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #portletIconConfiguration}
	 */
	@Deprecated
	@Override
	public void iconConfiguration() throws Exception {
		portletIconConfiguration();
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #portletIconEdit}
	 */
	@Deprecated
	@Override
	public void iconEdit() throws Exception {
		portletIconEdit();
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #portletIconEditDefaults}
	 */
	@Deprecated
	@Override
	public void iconEditDefaults() throws Exception {
		portletIconEditDefaults();
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #portletIconEditGuest}
	 */
	@Deprecated
	@Override
	public void iconEditGuest() throws Exception {
		portletIconEditGuest();
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #portletIconHelp}
	 */
	@Deprecated
	@Override
	public void iconHelp() throws Exception {
		portletIconHelp();
	}

	@Override
	public void iconHelp(String message) throws Exception {
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #portletIconMaximize}
	 */
	@Deprecated
	@Override
	public void iconMaximize() throws Exception {
		portletIconMaximize();
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #portletIconMinimize}
	 */
	@Deprecated
	@Override
	public void iconMinimize() throws Exception {
		portletIconMinimize();
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #portletIconOptions}
	 */
	@Deprecated
	@Override
	public void iconOptions() throws Exception {
		portletIconOptions();
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #portletIconPortlet}
	 */
	@Deprecated
	@Override
	public void iconPortlet() throws Exception {
		portletIconPortlet();
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #portletIconPortlet(Portlet)}
	 */
	@Deprecated
	@Override
	public void iconPortlet(Portlet portlet) throws Exception {
		portletIconPortlet();
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #portletIconPortletCss}
	 */
	@Deprecated
	@Override
	public void iconPortletCss() throws Exception {
		portletIconPortletCss();
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #portletIconPrint}
	 */
	@Deprecated
	@Override
	public void iconPrint() throws Exception {
		portletIconPrint();
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #portletIconRefresh}
	 */
	@Deprecated
	@Override
	public void iconRefresh() throws Exception {
		portletIconRefresh();
	}

	@Override
	public void include(ServletContext servletContext, String page)
		throws Exception {
	}

	@Override
	public void include(String page) throws Exception {
	}

	@Override
	public void journalArticle(
			String articleId, long groupId, String ddmTemplateKey)
		throws Exception {
	}

	@Override
	public void journalContentSearch() throws Exception {
	}

	@Override
	public void journalContentSearch(
			boolean showListed, String targetPortletId, String type)
		throws Exception {
	}

	@Override
	public void language() throws Exception {
	}

	@Override
	public void language(
			String formName, String formAction, String name,
			String displayStyle)
		throws Exception {
	}

	@Override
	public void language(
			String formName, String formAction, String name,
			String[] languageIds, String displayStyle)
		throws Exception {
	}

	@Override
	public void layoutIcon(Layout layout) throws Exception {
	}

	@Override
	public void metaTags() throws Exception {
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #mySites}
	 */
	@Deprecated
	@Override
	public void myPlaces() throws Exception {
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #mySites(int)}
	 */
	@Deprecated
	@Override
	public void myPlaces(int max) throws Exception {
	}

	@Override
	public void mySites() throws Exception {
	}

	@Override
	public void mySites(int max) throws Exception {
	}

	@Override
	public String permissionsURL(
			String redirect, String modelResource,
			String modelResourceDescription, Object resourceGroupId,
			String resourcePrimKey, String windowState, int[] roleTypes)
		throws Exception {

		return null;
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #permissionsURL(String,
	 *             String, String, Object, String, String, int[])}
	 */
	@Deprecated
	@Override
	public String permissionsURL(
			String redirect, String modelResource,
			String modelResourceDescription, String resourcePrimKey,
			String windowState, int[] roleTypes)
		throws Exception {

		return null;
	}

	@Override
	public void portletIconBack() throws Exception {
	}

	@Override
	public void portletIconClose() throws Exception {
	}

	@Override
	public void portletIconConfiguration() throws Exception {
	}

	@Override
	public void portletIconEdit() throws Exception {
	}

	@Override
	public void portletIconEditDefaults() throws Exception {
	}

	@Override
	public void portletIconEditGuest() throws Exception {
	}

	@Override
	public void portletIconHelp() throws Exception {
	}

	@Override
	public void portletIconMaximize() throws Exception {
	}

	@Override
	public void portletIconMinimize() throws Exception {
	}

	@Override
	public void portletIconOptions() throws Exception {
	}

	@Override
	public void portletIconPortlet() throws Exception {
	}

	@Override
	public void portletIconPortlet(Portlet portlet) throws Exception {
	}

	@Override
	public void portletIconPortletCss() throws Exception {
	}

	@Override
	public void portletIconPrint() throws Exception {
	}

	@Override
	public void portletIconRefresh() throws Exception {
	}

	@Override
	public void quickAccess() throws Exception {
	}

	@Override
	public void quickAccess(String contentId) throws Exception {
	}

	@Override
	public void ratings(
			String className, long classPK, int numberOfStars, String type,
			String url)
		throws Exception {
	}

	@Override
	public String renderURL(long plid, String portletName, String queryString)
		throws Exception {

		return null;
	}

	@Override
	public String renderURL(String portletName, String queryString)
		throws Exception {

		return null;
	}

	@Override
	public String renderURL(
			String windowState, String portletMode, Boolean secure,
			Boolean copyCurrentRenderParameters, Boolean escapeXml, long plid,
			long refererPlid, String portletName, Boolean anchor,
			Boolean encrypt, long doAsGroupId, long doAsUserId,
			Boolean portletConfiguration, String queryString)
		throws Exception {

		return null;
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #renderURL(String, String,
	 *             Boolean, Boolean, Boolean, long, long, String, Boolean,
	 *             Boolean, long, long, Boolean, String)}
	 */
	@Deprecated
	@Override
	public String renderURL(
			String windowState, String portletMode, Boolean secure,
			Boolean copyCurrentRenderParameters, Boolean escapeXml, long plid,
			String portletName, Boolean anchor, Boolean encrypt,
			long doAsUserId, Boolean portletConfiguration, String queryString)
		throws Exception {

		return null;
	}

	@Override
	public String renderURL(
			String windowState, String portletMode, long plid,
			String portletName, String queryString)
		throws Exception {

		return null;
	}

	@Override
	public String renderURL(
			String windowState, String portletMode, String portletName,
			String queryString)
		throws Exception {

		return null;
	}

	@Override
	public void runtime(String portletName) throws Exception {
	}

	@Override
	public void runtime(String portletName, String queryString)
		throws Exception {
	}

	@Override
	public void runtime(
			String portletName, String queryString, String defaultPreferences)
		throws Exception {
	}

	@Override
	public void search() throws Exception {
	}

	@Override
	public void setTemplate(Template template) {
	}

	@Override
	public void sitesDirectory() throws Exception {
	}

	@Override
	public void sitesDirectory(String displayStyle, String sites)
		throws Exception {
	}

	@Override
	public void socialBookmarks(
			String displayStyle, String target, String types, String title,
			String url)
		throws Exception {
	}

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	@Override
	public void staging() throws Exception {
	}

	@Override
	public void toggle(
			String id, String showImage, String hideImage, String showMessage,
			String hideMessage, boolean defaultShowContent)
		throws Exception {
	}

	@Override
	public String wrapPortlet(String wrapPage, String portletPage)
		throws Exception {

		return StringPool.BLANK;
	}

}