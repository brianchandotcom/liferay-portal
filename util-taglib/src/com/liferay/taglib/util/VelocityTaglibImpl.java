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
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.servlet.DirectRequestDispatcherFactoryUtil;
import com.liferay.portal.kernel.servlet.JSPSupportServlet;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.taglib.TagSupport;
import com.liferay.taglib.portlet.ActionURLTag;
import com.liferay.taglib.portletext.IconOptionsTag;
import com.liferay.taglib.portletext.IconPortletTag;
import com.liferay.taglib.portletext.RuntimeTag;
import com.liferay.taglib.security.DoAsURLTag;
import com.liferay.taglib.security.PermissionsURLTag;
import com.liferay.taglib.servlet.PipingPageContext;
import com.liferay.taglib.theme.MetaTagsTag;
import com.liferay.taglib.theme.WrapPortletTag;
import com.liferay.taglib.ui.AssetCategoriesSummaryTag;
import com.liferay.taglib.ui.AssetLinksTag;
import com.liferay.taglib.ui.AssetTagsSummaryTag;
import com.liferay.taglib.ui.BreadcrumbTag;
import com.liferay.taglib.ui.DiscussionTag;
import com.liferay.taglib.ui.IconTag;
import com.liferay.taglib.ui.JournalArticleTag;
import com.liferay.taglib.ui.LanguageTag;
import com.liferay.taglib.ui.RatingsTag;
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

	public VelocityTaglibImpl(
		ServletContext servletContext, HttpServletRequest request,
		HttpServletResponse response, Map<String, Object> contextObjects) {

		_servletContext = servletContext;
		_request = request;
		_response = response;
		_contextObjects = contextObjects;

		JspFactory jspFactory = JspFactory.getDefaultFactory();

		_pageContext = jspFactory.getPageContext(
			new JSPSupportServlet(_servletContext), _request, _response, null,
			false, 0, false);
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

		AssetCategoriesSummaryTag<?> assetCategorySummaryTag =
			new AssetCategoriesSummaryTag<>();

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

		AssetTagsSummaryTag<?> assetTagsSummaryTag =
			new AssetTagsSummaryTag<>();

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
			long ddmTemplateGroupId, String ddmTemplateKey,
			boolean showGuestGroup, boolean showParentGroups,
			boolean showLayout, boolean showPortletBreadcrumb)
		throws Exception {

		BreadcrumbTag breadcrumbTag = new BreadcrumbTag();

		setUp(breadcrumbTag);

		breadcrumbTag.setDdmTemplateGroupId(ddmTemplateGroupId);
		breadcrumbTag.setDdmTemplateKey(ddmTemplateKey);
		breadcrumbTag.setShowGuestGroup(showGuestGroup);
		breadcrumbTag.setShowLayout(showLayout);
		breadcrumbTag.setShowParentGroups(showParentGroups);
		breadcrumbTag.setShowPortletBreadcrumb(showPortletBreadcrumb);

		breadcrumbTag.runTag();
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #breadcrumb(long, String,
	 *             boolean, boolean, boolean, boolean)}}
	 */
	@Deprecated
	@Override
	public void breadcrumb(
			String ddmTemplateKey, boolean showGuestGroup,
			boolean showParentGroups, boolean showLayout,
			boolean showPortletBreadcrumb)
		throws Exception {

		breadcrumb(
			0, ddmTemplateKey, showGuestGroup, showParentGroups, showLayout,
			showPortletBreadcrumb);
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

	@Override
	public void doAsURL(long doAsUserId) throws Exception {
		DoAsURLTag.doTag(doAsUserId, _request);
	}

	@Override
	public AssetCategoriesSummaryTag<?> getAssetCategoriesSummaryTag()
		throws Exception {

		AssetCategoriesSummaryTag<?> assetCategoriesSummaryTag =
			new AssetCategoriesSummaryTag<>();

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
	public AssetTagsSummaryTag<?> getAssetTagsSummaryTag() throws Exception {
		AssetTagsSummaryTag<?> assetTagsSummaryTag =
			new AssetTagsSummaryTag<>();

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
	public DiscussionTag getDiscussionTag() throws Exception {
		DiscussionTag discussionTag = new DiscussionTag();

		setUp(discussionTag);

		return discussionTag;
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

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	@Override
	public com.liferay.taglib.ui.MySitesTag getMySitesTag() throws Exception {
		com.liferay.taglib.ui.MySitesTag mySitesTag =
			new com.liferay.taglib.ui.MySitesTag();

		setUp(mySitesTag);

		return mySitesTag;
	}

	@Override
	public PageContext getPageContext() {
		return _pageContext;
	}

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	@Override
	public com.liferay.taglib.ui.PngImageTag getPngImageTag() throws Exception {
		com.liferay.taglib.ui.PngImageTag pngImageTag =
			new com.liferay.taglib.ui.PngImageTag();

		setUp(pngImageTag);

		return pngImageTag;
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

	@Override
	public void iconHelp(String message) throws Exception {
		com.liferay.taglib.ui.IconHelpTag iconHelpTag =
			new com.liferay.taglib.ui.IconHelpTag();

		setUp(iconHelpTag);

		iconHelpTag.setMessage(message);

		iconHelpTag.runTag();
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
		journalContentSearch(true, null);
	}

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	@Override
	public void journalContentSearch(boolean showListed, String targetPortletId)
		throws Exception {

		com.liferay.taglib.ui.JournalContentSearchTag journalContentSearchTag =
			new com.liferay.taglib.ui.JournalContentSearchTag();

		setUp(journalContentSearchTag);

		journalContentSearchTag.setShowListed(showListed);
		journalContentSearchTag.setTargetPortletId(targetPortletId);

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
			String ddmTemplateKey)
		throws Exception {

		LanguageTag languageTag = new LanguageTag();

		setUp(languageTag);

		languageTag.setDdmTemplateKey(ddmTemplateKey);
		languageTag.setFormAction(formAction);
		languageTag.setFormName(formName);
		languageTag.setName(name);

		languageTag.runTag();
	}

	@Override
	public void language(
			String formName, String formAction, String name,
			String[] languageIds, String ddmTemplateKey)
		throws Exception {

		LanguageTag languageTag = new LanguageTag();

		setUp(languageTag);

		languageTag.setDdmTemplateKey(ddmTemplateKey);
		languageTag.setFormAction(formAction);
		languageTag.setFormName(formName);
		languageTag.setLanguageIds(languageIds);
		languageTag.setName(name);

		languageTag.runTag();
	}

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	@Override
	public void layoutIcon(Layout layout) throws Exception {
		com.liferay.taglib.theme.LayoutIconTag.doTag(
			layout, _servletContext, _request, _response);
	}

	@Override
	public void metaTags() throws Exception {
		MetaTagsTag.doTag(_servletContext, _request, _response);
	}

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	@Override
	public void mySites() throws Exception {
		com.liferay.taglib.ui.MySitesTag mySitesTag =
			new com.liferay.taglib.ui.MySitesTag();

		setUp(mySitesTag);

		mySitesTag.runTag();
	}

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	@Override
	public void mySites(int max) throws Exception {
		com.liferay.taglib.ui.MySitesTag mySitesTag =
			new com.liferay.taglib.ui.MySitesTag();

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
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	@Override
	public void portletIconBack() throws Exception {
		com.liferay.taglib.portletext.IconBackTag iconBackTag =
			new com.liferay.taglib.portletext.IconBackTag();

		setUp(iconBackTag);

		iconBackTag.runTag();
	}

	@Override
	public void portletIconOptions() throws Exception {
		IconOptionsTag iconOptionsTag = new IconOptionsTag();

		setUp(iconOptionsTag);

		iconOptionsTag.runTag();
	}

	@Override
	public void portletIconOptions(String direction, String markupView)
		throws Exception {

		IconOptionsTag iconOptionsTag = new IconOptionsTag();

		setUp(iconOptionsTag);

		iconOptionsTag.setDirection(direction);
		iconOptionsTag.setMarkupView(markupView);

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
		runtime(portletName, (String)null);
	}

	@Override
	public void runtime(
			String portletProviderClassName,
			PortletProvider.Action portletProviderAction)
		throws Exception {

		RuntimeTag.doTag(
			portletProviderClassName, portletProviderAction, StringPool.BLANK,
			null, null, true, _pageContext, _request, _response);
	}

	@Override
	public void runtime(
			String portletProviderClassName,
			PortletProvider.Action portletProviderAction, String instanceId)
		throws Exception {

		RuntimeTag.doTag(
			portletProviderClassName, portletProviderAction, instanceId, null,
			null, true, _pageContext, _request, _response);
	}

	@Override
	public void runtime(
			String portletProviderClassName,
			PortletProvider.Action portletProviderAction, String instanceId,
			String defaultPreferences)
		throws Exception {

		RuntimeTag.doTag(
			portletProviderClassName, portletProviderAction, instanceId, null,
			defaultPreferences, true, _pageContext, _request, _response);
	}

	@Override
	public void runtime(String portletName, String queryString)
		throws Exception {

		RuntimeTag.doTag(
			portletName, queryString, _pageContext, _request, _response);
	}

	@Override
	public void runtime(
			String portletName, String queryString, String defaultPreferences)
		throws Exception {

		RuntimeTag.doTag(
			portletName, queryString, defaultPreferences, _pageContext,
			_request, _response);
	}

	@Override
	public void runtime(
			String portletName, String instanceId, String queryString,
			String defaultPreferences)
		throws Exception {

		RuntimeTag.doTag(
			portletName, instanceId, queryString, defaultPreferences,
			_pageContext, _request, _response);
	}

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	@Override
	public void search() throws Exception {
		com.liferay.taglib.ui.SearchTag searchTag =
			new com.liferay.taglib.ui.SearchTag();

		setUp(searchTag);

		searchTag.runTag();
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

	protected void setUp(TagSupport tagSupport) throws Exception {
		Writer writer = null;

		if (_contextObjects != null) {
			writer = (Writer)_contextObjects.get(TemplateConstants.WRITER);
		}

		if (writer == null) {
			writer = _response.getWriter();
		}

		tagSupport.setPageContext(new PipingPageContext(_pageContext, writer));
	}

	private final Map<String, Object> _contextObjects;
	private final PageContext _pageContext;
	private final HttpServletRequest _request;
	private final HttpServletResponse _response;
	private final ServletContext _servletContext;

}