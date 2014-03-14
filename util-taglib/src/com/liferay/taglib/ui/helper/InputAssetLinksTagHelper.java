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

package com.liferay.taglib.ui.helper;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UniqueList;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.User;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.PortletURLFactoryUtil;
import com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.model.AssetLink;
import com.liferay.portlet.asset.model.AssetRendererFactory;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetEntryServiceUtil;
import com.liferay.portlet.asset.service.AssetLinkLocalServiceUtil;
import com.liferay.portlet.asset.util.comparator.AssetRendererFactoryTypeNameComparator;

import javax.portlet.Portlet;
import javax.portlet.PortletMode;
import javax.portlet.PortletModeException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.WindowState;
import javax.portlet.WindowStateException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * This class is intended to extract code from tag's JSP file.
 * @author JM Navarro
 */
public class InputAssetLinksTagHelper {

	public InputAssetLinksTagHelper(PageContext pageContext)
		throws PortalException, SystemException {

		super();

		_pageContext = pageContext;
		_request = (HttpServletRequest)pageContext.getRequest();

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);
		_locale = themeDisplay.getLocale();
		_companyId = themeDisplay.getCompany().getCompanyId();
		_companyGroupId = themeDisplay.getCompanyGroupId();
		_user = themeDisplay.getUser();

		_portletRequest = (PortletRequest)_request.getAttribute(
			JavaConstants.JAVAX_PORTLET_REQUEST);

		_randomNamespace = PortalUtil.generateRandomKey(
			_request, "taglib_ui_input_asset_links_page") +
			StringPool.UNDERLINE;

		_eventName = _randomNamespace + "selectAsset";

		_assetEntryId = GetterUtil.getLong(
			(String)_request.getAttribute(
				"liferay-ui:input-asset-links:assetEntryId"));

		_scopeGroup = GroupLocalServiceUtil.getGroup(
			themeDisplay.getScopeGroupId());

		_stagedLocally =
			_scopeGroup.isStaged() && !_scopeGroup.isStagedRemotely();
		_stagedReferrerPortlet = false;

		if (_stagedLocally) {
			String className = (String)_request.getAttribute(
				"liferay-ui:input-asset-links:className");

			AssetRendererFactory factory =
				AssetRendererFactoryRegistryUtil.
					getAssetRendererFactoryByClassName(className);

			_stagedReferrerPortlet = _scopeGroup.isStagedPortlet(
				factory.getPortletId());
		}

	}

	public Map<String, Object> getAssetBrowserData(AssetRendererFactory factory)
			throws PortalException, SystemException {

		Map<String, Object> data = new HashMap<String, Object>();

		data.put("href", _getAssetBrowserPortletURL(factory).toString());

		data.put("title",
			LanguageUtil.format(
				_pageContext , "select-x",
				factory.getTypeName(_locale, false), false));

		data.put("type", factory.getClassName());

		return data;
	}

	public String getAssetBrowserId(AssetRendererFactory factory) {
		return FriendlyURLNormalizerUtil.normalize(
			factory.getTypeName(_locale, false));
	}

	public String getAssetBrowserMessage(AssetRendererFactory factory) {
		return factory.getTypeName(_locale, false);
	}

	public String getAssetBrowserSrc(AssetRendererFactory factory) {
		return factory.getIconPath(_portletRequest);
	}

	public String getAssetColumnScope(AssetEntry entry)
			throws PortalException, SystemException {

		Group group = GroupLocalServiceUtil.getGroup(entry.getGroupId());

		return HtmlUtil.escape(group.getDescriptiveName(_locale));
	}

	public String getAssetColumnTitle(AssetEntry entry) {
		return entry.getTitle(_locale);
	}

	public String getAssetColumnType(AssetEntry entry) {
		return entry.getAssetRendererFactory().getTypeName(_locale, false);
	}

	public AssetEntry getAssetLinkEntry(AssetLink assetLink, boolean escaped)
			throws PortalException, SystemException {

		AssetEntry entry;

		long entryId;
		if (_assetEntryId > 0 || assetLink.getEntryId1() == _assetEntryId) {
			entryId = assetLink.getEntryId2();
		}
		else {
			entryId = assetLink.getEntryId1();
		}

		entry = AssetEntryLocalServiceUtil.getEntry(entryId);

		if (escaped) {
			entry = entry.toEscapedModel();
		}

		return entry;
	}

	public List<AssetLink> getAssetLinks()
		throws PortalException, SystemException {

		if (_assetLinks == null) {
			_assetLinks = _createAssetLinks();
		}

		return _assetLinks;
	}

	public List<AssetRendererFactory> getAssetRendererFactories() {

		List<AssetRendererFactory> factories =
			new ArrayList<AssetRendererFactory>();

		for (AssetRendererFactory factory :
			AssetRendererFactoryRegistryUtil.getAssetRendererFactories(
				_companyId)) {

			if (factory.isLinkable() && factory.isSelectable()) {
				factories.add(factory);
			}
		}

		return ListUtil.sort(
			factories, new AssetRendererFactoryTypeNameComparator(_locale));
	}


	public String getEventName() {
		return _eventName;
	}

	public String getRandomNamespace() {
		return _randomNamespace;
	}

	private static void _addGroupIds(
		List<Long> holder, List<Group> groupsToAdd) {

		for (Group g : groupsToAdd) {
			holder.add(g.getPrimaryKey());
		}
	}

	private List<AssetLink> _createAssetLinks()
			throws PortalException, SystemException {

		List<AssetLink> assetLinks = new ArrayList<AssetLink>();

		String assetLinksSearchContainerPrimaryKeys = ParamUtil.getString(
			_request, "assetLinksSearchContainerPrimaryKeys");

		if (Validator.isNull(assetLinksSearchContainerPrimaryKeys) &&
			SessionErrors.isEmpty(_portletRequest) && _assetEntryId > 0) {

			List<AssetLink> directAssetLinks =
				AssetLinkLocalServiceUtil.getDirectLinks(_assetEntryId);

			for (AssetLink assetLink : directAssetLinks) {
				AssetEntry assetLinkEntry = getAssetLinkEntry(assetLink, false);

				AssetRendererFactory assetRendererFactory =
					AssetRendererFactoryRegistryUtil.
						getAssetRendererFactoryByClassName(
							assetLinkEntry.getClassName());

				if (assetRendererFactory.isActive(_companyId)) {
					assetLinks.add(assetLink);
				}
			}
		}
		else {
			String[] assetEntriesPrimaryKeys =
				StringUtil.split(assetLinksSearchContainerPrimaryKeys);

			for (String assetEntryPrimaryKey : assetEntriesPrimaryKeys) {
				long assetEntryPrimaryKeyLong =
					GetterUtil.getLong(assetEntryPrimaryKey);

				AssetEntry assetEntry =
					AssetEntryServiceUtil.getEntry(assetEntryPrimaryKeyLong);

				AssetLink assetLink =
					AssetLinkLocalServiceUtil.createAssetLink(0);

				assetLink.setEntryId1(_assetEntryId > 0 ? _assetEntryId : 0);
				assetLink.setEntryId2(assetEntry.getEntryId());

				assetLinks.add(assetLink);
			}
		}

		return assetLinks;
	}

	private PortletURL _getAssetBrowserPortletURL(AssetRendererFactory factory)
		throws PortalException, SystemException {

		long controlPanelPlid = PortalUtil.getControlPanelPlid(_companyId);

		PortletURL url = PortletURLFactoryUtil.create(
			_request, PortletKeys.ASSET_BROWSER, controlPanelPlid,
			PortletRequest.RENDER_PHASE);

		url.setParameter("struts_action", "/asset_browser/view");
		url.setParameter("eventName", getEventName());
		try {
			url.setPortletMode(PortletMode.VIEW);
		}
		catch (PortletModeException e) {
			throw new PortalException(e);
		}
		try {
			url.setWindowState(LiferayWindowState.POP_UP);
		}
		catch (WindowStateException e) {
			throw new PortalException(e);
		}

		if (_assetEntryId > 0) {
			url.setParameter(
				"refererAssetEntryId", String.valueOf(_assetEntryId));
		}

		long groupId = _scopeGroup.getGroupId();

		if (_stagedLocally) {
			boolean stagedReferencePortlet =
				_scopeGroup.isStagedPortlet(factory.getPortletId());

			if (_stagedReferrerPortlet && !stagedReferencePortlet) {
				groupId = _scopeGroup.getLiveGroupId();
			}
		}

		url.setParameter("groupId", String.valueOf(groupId));
		url.setParameter(
			"selectedGroupIds", _getSelectedGroupIdsParam(groupId));
		url.setParameter("typeSelection", factory.getClassName());

		return url;
	}

	private String _getSelectedGroupIdsParam(long groupId)
		throws PortalException, SystemException {

		List<Long> groups = new UniqueList<Long>();

		groups.add(_companyId);
		groups.add(groupId);
		_addGroupIds(groups, _user.getMySiteGroups());
		_addGroupIds(groups, _user.getGroups());
		_addGroupIds(groups, _user.getSiteGroups());

		StringBundler sb = new StringBundler(2 * groups.size() - 1);
		int size = groups.size();

		for (int i = 0; i < size; ++i) {
			sb.append(groups.get(i));

			if ((i + 1) != size) {
				sb.append(CharPool.COMMA);
			}
		}

		return sb.toString();
	}

	private long _assetEntryId;
	private List<AssetLink> _assetLinks;
	private long _companyGroupId;
	private long _companyId;
	private String _eventName;
	private Locale _locale;
	private PageContext _pageContext;
	private PortletRequest _portletRequest;
	private String _randomNamespace;
	private HttpServletRequest _request;
	private Group _scopeGroup;
	private boolean _stagedLocally;
	private boolean _stagedReferrerPortlet;
	private User _user;

}
