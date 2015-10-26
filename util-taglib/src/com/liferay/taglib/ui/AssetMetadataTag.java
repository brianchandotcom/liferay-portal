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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.model.AssetRenderer;
import com.liferay.portlet.asset.model.AssetRendererFactory;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.taglib.util.IncludeTag;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class AssetMetadataTag extends IncludeTag {

	public String getClassName() {
		return _className;
	}

	public long getClassPK() {
		return _classPK;
	}

	public String[] getMetadataFields() {
		return _metadataFields;
	}

	public boolean isFilterByMetadata() {
		return _filterByMetadata;
	}

	public void setClassName(String className) {
		_className = className;
	}

	public void setClassPK(long classPK) {
		_classPK = classPK;
	}

	public void setFilterByMetadata(boolean filterByMetadata) {
		_filterByMetadata = filterByMetadata;
	}

	public void setMetadataFields(String[] metadataFields) {
		_metadataFields = metadataFields;
	}

	@Override
	protected void cleanUp() {
		_className = StringPool.BLANK;
		_classPK = 0;
		_filterByMetadata = false;
		_metadataFields = null;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		AssetEntry assetEntry = AssetEntryLocalServiceUtil.fetchEntry(
			_className, _classPK);

		request.setAttribute(
			"liferay-ui:asset-metadata:assetEntry", assetEntry);

		AssetRendererFactory<?> assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				_className);

		try {
			AssetRenderer<?> assetRenderer =
				assetRendererFactory.getAssetRenderer(_classPK);

			request.setAttribute(
				"liferay-ui:asset-metadata:assetRenderer", assetRenderer);
		}
		catch (PortalException pe) {
			_log.error(pe, pe);
		}

		request.setAttribute("liferay-ui:asset-metadata:className", _className);
		request.setAttribute("liferay-ui:asset-metadata:classPK", _classPK);
		request.setAttribute(
			"liferay-ui:asset-metadata:filterByMetadata", _filterByMetadata);
		request.setAttribute(
			"liferay-ui:asset-metadata:metadataFields", _metadataFields);
	}

	private static final String _PAGE =
		"/html/taglib/ui/asset_metadata/page.jsp";

	private static final Log _log = LogFactoryUtil.getLog(
		AssetMetadataTag.class);

	private String _className = StringPool.BLANK;
	private long _classPK;
	private boolean _filterByMetadata;
	private String[] _metadataFields;

}