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

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetLink;
import com.liferay.asset.kernel.service.AssetEntryLocalServiceUtil;
import com.liferay.asset.kernel.service.AssetLinkLocalServiceUtil;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.taglib.util.IncludeTag;

import java.util.List;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Juan Fernández
 * @author Shuyang Zhou
 */
public class AssetLinksTag extends IncludeTag {

	public long getAssetEntryId() {
		return _assetEntryId;
	}

	public String getClassName() {
		return _className;
	}

	public long getClassPK() {
		return _classPK;
	}

	public PortletURL getPortletURL() {
		return _portletURL;
	}

	public void setAssetEntryId(long assetEntryId) {
		_assetEntryId = assetEntryId;
	}

	public void setClassName(String className) {
		_className = className;
	}

	public void setClassPK(long classPK) {
		_classPK = classPK;
	}

	public void setPortletURL(PortletURL portletURL) {
		_portletURL = portletURL;
	}

	@Override
	protected void cleanUp() {
		_assetEntryId = 0;
		_className = StringPool.BLANK;
		_classPK = 0;
		_page = _PAGE;
		_portletURL = null;
	}

	@Override
	protected String getPage() {
		return _page;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		if (_page == null) {
			return;
		}

		if ((_assetEntryId <= 0) && (_classPK > 0)) {
			try {
				AssetEntry assetEntry = AssetEntryLocalServiceUtil.fetchEntry(
					_className, _classPK);

				if (assetEntry != null) {
					_assetEntryId = assetEntry.getEntryId();
				}
			}
			catch (SystemException se) {

				// LPS-52675

				if (_log.isDebugEnabled()) {
					_log.debug(se, se);
				}
			}
		}

		if (_assetEntryId <= 0) {
			_page = null;

			return;
		}

		List<AssetLink> assetLinks = AssetLinkLocalServiceUtil.getDirectLinks(
			_assetEntryId);

		if (assetLinks.isEmpty()) {
			_page = null;

			return;
		}

		request.setAttribute(
			"liferay-ui:asset-links:assetEntryId",
			String.valueOf(_assetEntryId));

		request.setAttribute("liferay-ui:asset-links:assetLinks", assetLinks);

		request.setAttribute("liferay-ui:asset-links:portletURL", _portletURL);
	}

	private static final String _PAGE = "/html/taglib/ui/asset_links/page.jsp";

	private static final Log _log = LogFactoryUtil.getLog(AssetLinksTag.class);

	private long _assetEntryId;
	private String _className = StringPool.BLANK;
	private long _classPK;
	private String _page = _PAGE;
	private PortletURL _portletURL;

}