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

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.servlet.DirectRequestDispatcherFactoryUtil;
import com.liferay.portal.kernel.servlet.JSPSupportServlet;
import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.taglib.TagSupport;
import com.liferay.taglib.aui.ColumnTag;
import com.liferay.taglib.aui.LayoutTag;
import com.liferay.taglib.portlet.ActionURLTag;
import com.liferay.taglib.portletext.IconBackTag;
import com.liferay.taglib.portletext.IconCloseTag;
import com.liferay.taglib.portletext.IconConfigurationTag;
import com.liferay.taglib.portletext.IconEditDefaultsTag;
import com.liferay.taglib.portletext.IconEditGuestTag;
import com.liferay.taglib.portletext.IconEditTag;
import com.liferay.taglib.portletext.IconHelpTag;
import com.liferay.taglib.portletext.IconMaximizeTag;
import com.liferay.taglib.portletext.IconMinimizeTag;
import com.liferay.taglib.portletext.IconOptionsTag;
import com.liferay.taglib.portletext.IconPortletCssTag;
import com.liferay.taglib.portletext.IconPortletTag;
import com.liferay.taglib.portletext.IconPrintTag;
import com.liferay.taglib.portletext.IconRefreshTag;
import com.liferay.taglib.portletext.RuntimeTag;
import com.liferay.taglib.security.DoAsURLTag;
import com.liferay.taglib.security.PermissionsURLTag;
import com.liferay.taglib.servlet.PipingPageContext;
import com.liferay.taglib.theme.LayoutIconTag;
import com.liferay.taglib.theme.MetaTagsTag;
import com.liferay.taglib.theme.WrapPortletTag;
import com.liferay.taglib.ui.AssetCategoriesSummaryTag;
import com.liferay.taglib.ui.AssetLinksTag;
import com.liferay.taglib.ui.AssetTagsSummaryTag;
import com.liferay.taglib.ui.BreadcrumbTag;
import com.liferay.taglib.ui.DiscussionTag;
import com.liferay.taglib.ui.FlagsTag;
import com.liferay.taglib.ui.IconTag;
import com.liferay.taglib.ui.JournalArticleTag;
import com.liferay.taglib.ui.JournalContentSearchTag;
import com.liferay.taglib.ui.LanguageTag;
import com.liferay.taglib.ui.MySitesTag;
import com.liferay.taglib.ui.PngImageTag;
import com.liferay.taglib.ui.QuickAccessTag;
import com.liferay.taglib.ui.RatingsTag;
import com.liferay.taglib.ui.SearchTag;
import com.liferay.taglib.ui.SitesDirectoryTag;
import com.liferay.taglib.ui.SocialBookmarksTag;
import com.liferay.taglib.ui.ToggleTag;

import java.io.Writer;

import java.util.Map;
import java.util.Set;

import javax.portlet.PortletMode;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.WindowState;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspFactory;
import javax.servlet.jsp.PageContext;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public class VelocityTaglibImpl implements VelocityTaglib {

	public VelocityTaglibImpl() {
	}

	public VelocityTaglibImpl(
		ServletContext servletContext, HttpServletRequest request,
		HttpServletResponse response, Template template) {

		init(servletContext, request, response, template);
	}

	@Override
	public String actionURL(long plid, String portletName, String queryString)
		throws Exception {

		String windowState = WindowState.NORMAL.toString();
		String portletMode = PortletMode.VIEW.toString();

		return actionURL(
			windowState, portletMode, plid, portletName, queryString);
	}

	@Override
	public String actionURL(String portletName, String queryString)
		throws Exception {

		return actionURL(
			LayoutConstants.DEFAULT_PLID, portletName, queryString);
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

		return actionURL(
			windowState, portletMode, secure, copyCurrentRenderParameters,
			escapeXml, name, plid, refererPlid, portletName, anchor, encrypt, 0,
			doAsUserId, portletConfiguration, queryString);
	}

	@Override
	public String actionURL(
			String windowState, String portletMode, Boolean secure,
			Boolean copyCurrentRenderParameters, Boolean escapeXml, String name,
			long plid, long refererPlid, String portletName, Boolean anchor,
			Boolean encrypt, long doAsGroupId, long doAsUserId,
			Boolean portletConfiguration, String queryString)
		throws Exception {

		String resourceID = null;
		String cacheability = null;
		Map<String, String[]> parameterMap = HttpUtil.parameterMapFromString(
			queryString);
		Set<String> removedParameterNames = null;

		PortletURL portletURL = ActionURLTag.doTag(
			PortletRequest.ACTION_PHASE, windowState, portletMode, secure,
			copyCurrentRenderParameters, escapeXml, name, resourceID,
			cacheability, plid, refererPlid, portletName, anchor, encrypt,
			doAsGroupId, doAsUserId, portletConfiguration, parameterMap,
			removedParameterNames, _request);

		return portletURL.toString();
	}

	@Override
	public String actionURL(
			String windowState, String portletMode, long plid,
			String portletName, String queryString)
		throws Exception {

		Boolean secure = null;
		Boolean copyCurrentRenderParameters = null;
		Boolean escapeXml = null;
		long refererPlid = LayoutConstants.DEFAULT_PLID;
		String name = null;
		Boolean anchor = null;
		Boolean encrypt = null;
		long doAsGroupId = 0;
		long doAsUserId = 0;
		Boolean portletConfiguration = null;

		return actionURL(
			windowState, portletMode, secure, copyCurrentRenderParameters,
			escapeXml, name, plid, refererPlid, portletName, anchor, encrypt,
			doAsGroupId, doAsUserId, portletConfiguration, queryString);
	}

	@Override
	public String actionURL(
			String windowState, String portletMode, String portletName,
			String queryString)
		throws Exception {

		return actionURL(
			windowState, portletMode, LayoutConstants.DEFAULT_PLID, portletName,
			queryString);
	}

	@Override
	public void assetCategoriesSummary(
			String className, long classPK, String message,
			PortletURL portletURL)
		throws Exception {

		AssetCategoriesSummaryTag assetCategorySummaryTag =
			new AssetCategoriesSummaryTag();

		setUp(assetCategorySummaryTag);

		assetCategorySummaryTag.setClassName(className);
		assetCategorySummaryTag.setClassPK(classPK);
		assetCategorySummaryTag.setMessage(message);
		assetCategorySummaryTag.setPortletURL(portletURL);

		assetCategorySummaryTag.runTag();
	}

	@Override
	public void assetLinks(long assetEntryId, String className, long classPK)
		throws Exception {

		AssetLinksTag assetLinksTag = new AssetLinksTag();

		setUp(assetLinksTag);

		assetLinksTag.setAssetEntryId(assetEntryId);
		assetLinksTag.setClassName(className);
		assetLinksTag.setClassPK(classPK);

		assetLinksTag.runTag();
	}

	@Override
	public void assetTagsSummary(
			String className, long classPK, String message,
			String assetTagNames, PortletURL portletURL)
		throws Exception {

		AssetTagsSummaryTag assetTagsSummaryTag = new AssetTagsSummaryTag();

		setUp(assetTagsSummaryTag);

		assetTagsSummaryTag.setClassName(className);
		assetTagsSummaryTag.setClassPK(classPK);
		assetTagsSummaryTag.setMessage(message);
		assetTagsSummaryTag.setPortletURL(portletURL);
		assetTagsSummaryTag.setAssetTagNames(assetTagNames);

		assetTagsSummaryTag.runTag();
	}

	@Override
	public void breadcrumb() throws Exception {
		BreadcrumbTag breadcrumbTag = new BreadcrumbTag();

		setUp(breadcrumbTag);

		breadcrumbTag.runTag();
	}

	@Override
	public void breadcrumb(
			String displayStyle, boolean showGuestGroup,
			boolean showParentGroups, boolean showLayout,
			boolean showPortletBreadcrumb)
		throws Exception {

		BreadcrumbTag breadcrumbTag = new BreadcrumbTag();

		setUp(breadcrumbTag);

		breadcrumbTag.setDisplayStyle(displayStyle);
		breadcrumbTag.setShowGuestGroup(showGuestGroup);
		breadcrumbTag.setShowLayout(showLayout);
		breadcrumbTag.setShowParentGroups(showParentGroups);
		breadcrumbTag.setShowPortletBreadcrumb(showPortletBreadcrumb);

		breadcrumbTag.runTag();
	}

	@Override
	public void discussion(
			String className, long classPK, String formAction, String formName,
			boolean hideControls, boolean ratingsEnabled, String redirect,
			long userId)
		throws Exception {

		DiscussionTag discussionTag = new DiscussionTag();

		setUp(discussionTag);

		discussionTag.setClassName(className);
		discussionTag.setClassPK(classPK);
		discussionTag.setFormAction(formAction);
		discussionTag.setFormName(formName);
		discussionTag.setHideControls(hideControls);
		discussionTag.setRatingsEnabled(ratingsEnabled);
		discussionTag.setRedirect(redirect);
		discussionTag.setUserId(userId);

		discussionTag.runTag();
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

		discussion(
			className, classPK, formAction, formName, hideControls,
			ratingsEnabled, redirect, userId);
	}

	@Override
	public void doAsURL(long doAsUserId) throws Exception {
		DoAsURLTag.doTag(doAsUserId, _request);
	}

	@Override
	public void flags(
			String className, long classPK, String contentTitle, boolean label,
			String message, long reportedUserId)
		throws Exception {

		FlagsTag flagsTag = new FlagsTag();

		setUp(flagsTag);

		flagsTag.setClassName(className);
		flagsTag.setClassPK(classPK);
		flagsTag.setContentTitle(contentTitle);
		flagsTag.setLabel(label);
		flagsTag.setMessage(message);
		flagsTag.setReportedUserId(reportedUserId);

		flagsTag.runTag();
	}

	@Override
	public AssetCategoriesSummaryTag getAssetCategoriesSummaryTag()
		throws Exception {

		AssetCategoriesSummaryTag assetCategoriesSummaryTag =
			new AssetCategoriesSummaryTag();

		setUp(assetCategoriesSummaryTag);

		return assetCategoriesSummaryTag;
	}

	@Override
	public AssetLinksTag getAssetLinksTag() throws Exception {
		AssetLinksTag assetLinksTag = new AssetLinksTag();

		setUp(assetLinksTag);

		return assetLinksTag;
	}

	@Override
	public AssetTagsSummaryTag getAssetTagsSummaryTag() throws Exception {
		AssetTagsSummaryTag assetTagsSummaryTag = new AssetTagsSummaryTag();

		setUp(assetTagsSummaryTag);

		return assetTagsSummaryTag;
	}

	@Override
	public BreadcrumbTag getBreadcrumbTag() throws Exception {
		BreadcrumbTag breadcrumbTag = new BreadcrumbTag();

		setUp(breadcrumbTag);

		return breadcrumbTag;
	}

	@Override
	public ColumnTag getColumnTag() throws Exception {
		ColumnTag columnTag = new ColumnTag();

		setUp(columnTag);

		return columnTag;
	}

	@Override
	public DiscussionTag getDiscussionTag() throws Exception {
		DiscussionTag discussionTag = new DiscussionTag();

		setUp(discussionTag);

		return discussionTag;
	}

	@Override
	public FlagsTag getFlagsTag() throws Exception {
		FlagsTag flagsTag = new FlagsTag();

		setUp(flagsTag);

		return flagsTag;
	}

	@Override
	public IconTag getIconTag() throws Exception {
		IconTag iconTag = new IconTag();

		setUp(iconTag);

		return iconTag;
	}

	@Override
	public JournalArticleTag getJournalArticleTag() throws Exception {
		JournalArticleTag journalArticleTag = new JournalArticleTag();

		setUp(journalArticleTag);

		return journalArticleTag;
	}

	@Override
	public LayoutTag getLayoutTag() throws Exception {
		LayoutTag layoutTag = new LayoutTag();

		setUp(layoutTag);

		return layoutTag;
	}

	@Override
	public MySitesTag getMySitesTag() throws Exception {
		MySitesTag mySitesTag = new MySitesTag();

		setUp(mySitesTag);

		return mySitesTag;
	}

	@Override
	public PageContext getPageContext() {
		return _pageContext;
	}

	@Override
	public PngImageTag getPngImageTag() throws Exception {
		PngImageTag pngImageTag = new PngImageTag();

		setUp(pngImageTag);

		return pngImageTag;
	}

	@Override
	public QuickAccessTag getQuickAccessTag() throws Exception {
		QuickAccessTag quickAccessTag = new QuickAccessTag();

		setUp(quickAccessTag);

		return quickAccessTag;
	}

	@Override
	public RatingsTag getRatingsTag() throws Exception {
		RatingsTag ratingsTag = new RatingsTag();

		setUp(ratingsTag);

		return ratingsTag;
	}

	@Override
	public String getSetting(String name) {
		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		return themeDisplay.getThemeSetting(name);
	}

	@Override
	public WindowState getWindowState(String windowState) {
		return new WindowState(windowState);
	}

	@Override
	public void icon(String image, boolean label, String message, String url)
		throws Exception {

		IconTag iconTag = new IconTag();

		setUp(iconTag);

		iconTag.setImage(image);
		iconTag.setLabel(label);
		iconTag.setMessage(message);
		iconTag.setUrl(url);

		iconTag.runTag();
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
		com.liferay.taglib.ui.IconHelpTag iconHelpTag =
			new com.liferay.taglib.ui.IconHelpTag();

		setUp(iconHelpTag);

		iconHelpTag.setMessage(message);

		iconHelpTag.runTag();
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

		RequestDispatcher requestDispatcher =
			servletContext.getRequestDispatcher(page);

		requestDispatcher.include(_request, _response);
	}

	@Override
	public void include(String page) throws Exception {
		RequestDispatcher requestDispatcher =
			DirectRequestDispatcherFactoryUtil.getRequestDispatcher(
				_servletContext, page);

		requestDispatcher.include(_request, _response);
	}

	@Override
	public void journalArticle(
			String articleId, long groupId, String ddmTemplateKey)
		throws Exception {

		JournalArticleTag journalArticleTag = new JournalArticleTag();

		setUp(journalArticleTag);

		journalArticleTag.setArticleId(articleId);
		journalArticleTag.setGroupId(groupId);
		journalArticleTag.setLanguageId(LanguageUtil.getLanguageId(_request));
		journalArticleTag.setDDMTemplateKey(ddmTemplateKey);

		journalArticleTag.runTag();
	}

	@Override
	public void journalContentSearch() throws Exception {
		journalContentSearch(true, null, null);
	}

	@Override
	public void journalContentSearch(
			boolean showListed, String targetPortletId, String type)
		throws Exception {

		JournalContentSearchTag journalContentSearchTag =
			new JournalContentSearchTag();

		setUp(journalContentSearchTag);

		journalContentSearchTag.setShowListed(showListed);
		journalContentSearchTag.setTargetPortletId(targetPortletId);
		journalContentSearchTag.setType(type);

		journalContentSearchTag.runTag();
	}

	@Override
	public void language() throws Exception {
		LanguageTag languageTag = new LanguageTag();

		setUp(languageTag);

		languageTag.runTag();
	}

	@Override
	public void language(
			String formName, String formAction, String name,
			String displayStyle)
		throws Exception {

		LanguageTag languageTag = new LanguageTag();

		setUp(languageTag);

		languageTag.setDisplayStyle(displayStyle);
		languageTag.setFormAction(formAction);
		languageTag.setFormName(formName);
		languageTag.setName(name);

		languageTag.runTag();
	}

	@Override
	public void language(
			String formName, String formAction, String name,
			String[] languageIds, String displayStyle)
		throws Exception {

		LanguageTag languageTag = new LanguageTag();

		setUp(languageTag);

		languageTag.setDisplayStyle(displayStyle);
		languageTag.setFormAction(formAction);
		languageTag.setFormName(formName);
		languageTag.setLanguageIds(languageIds);
		languageTag.setName(name);

		languageTag.runTag();
	}

	@Override
	public void layoutIcon(Layout layout) throws Exception {
		LayoutIconTag.doTag(layout, _servletContext, _request, _response);
	}

	@Override
	public void metaTags() throws Exception {
		MetaTagsTag.doTag(_servletContext, _request, _response);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #mySites}
	 */
	@Deprecated
	@Override
	public void myPlaces() throws Exception {
		mySites();
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #mySites(int)}
	 */
	@Deprecated
	@Override
	public void myPlaces(int max) throws Exception {
		mySites(max);
	}

	@Override
	public void mySites() throws Exception {
		MySitesTag mySitesTag = new MySitesTag();

		setUp(mySitesTag);

		mySitesTag.runTag();
	}

	@Override
	public void mySites(int max) throws Exception {
		MySitesTag mySitesTag = new MySitesTag();

		setUp(mySitesTag);

		mySitesTag.setMax(max);

		mySitesTag.runTag();
	}

	@Override
	public String permissionsURL(
			String redirect, String modelResource,
			String modelResourceDescription, Object resourceGroupId,
			String resourcePrimKey, String windowState, int[] roleTypes)
		throws Exception {

		return PermissionsURLTag.doTag(
			redirect, modelResource, modelResourceDescription, resourceGroupId,
			resourcePrimKey, windowState, roleTypes, _request);
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

		return permissionsURL(
			redirect, modelResourceDescription, modelResourceDescription, null,
			resourcePrimKey, windowState, roleTypes);
	}

	@Override
	public void portletIconBack() throws Exception {
		IconBackTag iconBackTag = new IconBackTag();

		setUp(iconBackTag);

		iconBackTag.runTag();
	}

	@Override
	public void portletIconClose() throws Exception {
		IconCloseTag iconCloseTag = new IconCloseTag();

		setUp(iconCloseTag);

		iconCloseTag.runTag();
	}

	@Override
	public void portletIconConfiguration() throws Exception {
		IconConfigurationTag iconConfigurationTag = new IconConfigurationTag();

		setUp(iconConfigurationTag);

		iconConfigurationTag.runTag();
	}

	@Override
	public void portletIconEdit() throws Exception {
		IconEditTag iconEditTag = new IconEditTag();

		setUp(iconEditTag);

		iconEditTag.runTag();
	}

	@Override
	public void portletIconEditDefaults() throws Exception {
		IconEditDefaultsTag iconEditDefaultsTag = new IconEditDefaultsTag();

		setUp(iconEditDefaultsTag);

		iconEditDefaultsTag.runTag();
	}

	@Override
	public void portletIconEditGuest() throws Exception {
		IconEditGuestTag iconEditGuestTag = new IconEditGuestTag();

		setUp(iconEditGuestTag);

		iconEditGuestTag.runTag();
	}

	@Override
	public void portletIconHelp() throws Exception {
		IconHelpTag iconHelpTag = new IconHelpTag();

		setUp(iconHelpTag);

		iconHelpTag.runTag();
	}

	@Override
	public void portletIconMaximize() throws Exception {
		IconMaximizeTag iconMaximizeTag = new IconMaximizeTag();

		setUp(iconMaximizeTag);

		iconMaximizeTag.runTag();
	}

	@Override
	public void portletIconMinimize() throws Exception {
		IconMinimizeTag iconMinimizeTag = new IconMinimizeTag();

		setUp(iconMinimizeTag);

		iconMinimizeTag.runTag();
	}

	@Override
	public void portletIconOptions() throws Exception {
		IconOptionsTag iconOptionsTag = new IconOptionsTag();

		setUp(iconOptionsTag);

		iconOptionsTag.runTag();
	}

	@Override
	public void portletIconPortlet() throws Exception {
		IconPortletTag iconPortletTag = new IconPortletTag();

		setUp(iconPortletTag);

		iconPortletTag.runTag();
	}

	@Override
	public void portletIconPortlet(Portlet portlet) throws Exception {
		IconPortletTag iconPortletTag = new IconPortletTag();

		setUp(iconPortletTag);

		iconPortletTag.setPortlet(portlet);

		iconPortletTag.runTag();
	}

	@Override
	public void portletIconPortletCss() throws Exception {
		IconPortletCssTag iconPortletCssTag = new IconPortletCssTag();

		setUp(iconPortletCssTag);

		iconPortletCssTag.runTag();
	}

	@Override
	public void portletIconPrint() throws Exception {
		IconPrintTag iconPrintTag = new IconPrintTag();

		setUp(iconPrintTag);

		iconPrintTag.runTag();
	}

	@Override
	public void portletIconRefresh() throws Exception {
		IconRefreshTag iconRefreshTag = new IconRefreshTag();

		setUp(iconRefreshTag);

		iconRefreshTag.runTag();
	}

	@Override
	public void quickAccess() throws Exception {
		QuickAccessTag quickAccessTag = new QuickAccessTag();

		setUp(quickAccessTag);

		quickAccessTag.runTag();
	}

	@Override
	public void quickAccess(String contentId) throws Exception {
		QuickAccessTag quickAccessTag = new QuickAccessTag();

		setUp(quickAccessTag);

		quickAccessTag.setContentId(contentId);

		quickAccessTag.runTag();
	}

	@Override
	public void ratings(
			String className, long classPK, int numberOfStars, String type,
			String url)
		throws Exception {

		RatingsTag ratingsTag = new RatingsTag();

		setUp(ratingsTag);

		ratingsTag.setClassName(className);
		ratingsTag.setClassPK(classPK);
		ratingsTag.setNumberOfStars(numberOfStars);
		ratingsTag.setType(type);
		ratingsTag.setUrl(url);

		ratingsTag.runTag();
	}

	@Override
	public String renderURL(long plid, String portletName, String queryString)
		throws Exception {

		String windowState = WindowState.NORMAL.toString();
		String portletMode = PortletMode.VIEW.toString();

		return renderURL(
			windowState, portletMode, plid, portletName, queryString);
	}

	@Override
	public String renderURL(String portletName, String queryString)
		throws Exception {

		return renderURL(
			LayoutConstants.DEFAULT_PLID, portletName, queryString);
	}

	@Override
	public String renderURL(
			String windowState, String portletMode, Boolean secure,
			Boolean copyCurrentRenderParameters, Boolean escapeXml, long plid,
			long refererPlid, String portletName, Boolean anchor,
			Boolean encrypt, long doAsGroupId, long doAsUserId,
			Boolean portletConfiguration, String queryString)
		throws Exception {

		String name = null;
		String resourceID = null;
		String cacheability = null;
		Map<String, String[]> parameterMap = HttpUtil.parameterMapFromString(
			queryString);
		Set<String> removedParameterNames = null;

		PortletURL portletURL = ActionURLTag.doTag(
			PortletRequest.RENDER_PHASE, windowState, portletMode, secure,
			copyCurrentRenderParameters, escapeXml, name, resourceID,
			cacheability, plid, refererPlid, portletName, anchor, encrypt,
			doAsGroupId, doAsUserId, portletConfiguration, parameterMap,
			removedParameterNames, _request);

		return portletURL.toString();
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

		long refererPlid = LayoutConstants.DEFAULT_PLID;

		return renderURL(
			windowState, portletMode, secure, copyCurrentRenderParameters,
			escapeXml, plid, refererPlid, portletName, anchor, encrypt, 0,
			doAsUserId, portletConfiguration, queryString);
	}

	@Override
	public String renderURL(
			String windowState, String portletMode, long plid,
			String portletName, String queryString)
		throws Exception {

		Boolean secure = null;
		Boolean copyCurrentRenderParameters = null;
		Boolean escapeXml = null;
		long referPlid = LayoutConstants.DEFAULT_PLID;
		Boolean anchor = null;
		Boolean encrypt = null;
		long doAsGroupId = 0;
		long doAsUserId = 0;
		Boolean portletConfiguration = null;

		return renderURL(
			windowState, portletMode, secure, copyCurrentRenderParameters,
			escapeXml, plid, referPlid, portletName, anchor, encrypt,
			doAsGroupId, doAsUserId, portletConfiguration, queryString);
	}

	@Override
	public String renderURL(
			String windowState, String portletMode, String portletName,
			String queryString)
		throws Exception {

		return renderURL(
			windowState, portletMode, LayoutConstants.DEFAULT_PLID, portletName,
			queryString);
	}

	@Override
	public void runtime(String portletName) throws Exception {
		runtime(portletName, null);
	}

	@Override
	public void runtime(String portletName, String queryString)
		throws Exception {

		RuntimeTag.doTag(portletName, queryString, null, _request, _response);
	}

	@Override
	public void runtime(
			String portletName, String queryString, String defaultPreferences)
		throws Exception {

		RuntimeTag.doTag(
			portletName, queryString, defaultPreferences, null, _request,
			_response);
	}

	@Override
	public void search() throws Exception {
		SearchTag searchTag = new SearchTag();

		setUp(searchTag);

		searchTag.runTag();
	}

	@Override
	public void setTemplate(Template template) {
		_template = template;
	}

	@Override
	public void sitesDirectory() throws Exception {
		SitesDirectoryTag sitesDirectoryTag = new SitesDirectoryTag();

		setUp(sitesDirectoryTag);

		sitesDirectoryTag.runTag();
	}

	@Override
	public void sitesDirectory(String displayStyle, String sites)
		throws Exception {

		SitesDirectoryTag sitesDirectoryTag = new SitesDirectoryTag();

		setUp(sitesDirectoryTag);

		sitesDirectoryTag.setDisplayStyle(displayStyle);
		sitesDirectoryTag.setSites(sites);

		sitesDirectoryTag.runTag();
	}

	@Override
	public void socialBookmarks(
			String displayStyle, String target, String types, String title,
			String url)
		throws Exception {

		SocialBookmarksTag socialBookmarksTag = new SocialBookmarksTag();

		setUp(socialBookmarksTag);

		socialBookmarksTag.setDisplayStyle(displayStyle);
		socialBookmarksTag.setTarget(target);
		socialBookmarksTag.setTypes(types);
		socialBookmarksTag.setTitle(title);
		socialBookmarksTag.setUrl(url);

		socialBookmarksTag.runTag();
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

		ToggleTag.doTag(
			id, showImage, hideImage, showMessage, hideMessage,
			defaultShowContent, null, _servletContext, _request, _response);
	}

	@Override
	public String wrapPortlet(String wrapPage, String portletPage)
		throws Exception {

		return WrapPortletTag.doTag(
			wrapPage, portletPage, _servletContext, _request, _response);
	}

	protected VelocityTaglibImpl init(
		ServletContext servletContext, HttpServletRequest request,
		HttpServletResponse response, Template template) {

		_servletContext = servletContext;
		_request = request;
		_response = response;
		_template = template;

		JspFactory jspFactory = JspFactory.getDefaultFactory();

		_pageContext = jspFactory.getPageContext(
			new JSPSupportServlet(_servletContext), _request, _response, null,
			false, 0, false);

		return this;
	}

	protected void setUp(TagSupport tagSupport) throws Exception {
		Writer writer = null;

		if (_template != null) {
			writer = (Writer)_template.get(TemplateConstants.WRITER);
		}

		if (writer == null) {
			writer = _response.getWriter();
		}

		tagSupport.setPageContext(new PipingPageContext(_pageContext, writer));
	}

	private PageContext _pageContext;
	private HttpServletRequest _request;
	private HttpServletResponse _response;
	private ServletContext _servletContext;
	private Template _template;

}