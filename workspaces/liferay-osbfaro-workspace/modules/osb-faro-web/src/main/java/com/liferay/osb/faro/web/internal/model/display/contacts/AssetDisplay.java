/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.web.internal.model.display.contacts;

import com.liferay.osb.faro.engine.client.model.Asset;
import com.liferay.osb.faro.web.internal.model.display.main.EntityDisplay;

/**
 * @author Matthew Kong
 */
@SuppressWarnings({"FieldCanBeLocal", "UnusedDeclaration"})
public class AssetDisplay extends EntityDisplay {

	public AssetDisplay() {
	}

	public AssetDisplay(Asset asset) {
		setId(asset.getId());

		_canonicalUrl = asset.getCanonicalUrl();
		_dataSourceAssetPK = asset.getDataSourceAssetPK();
		_dataSourceId = asset.getDataSourceId();
		_description = asset.getDescription();
		_name = asset.getName();
		_url = asset.getUrl();
		_type = asset.getAssetType();
	}

	private String _canonicalUrl;
	private String _dataSourceAssetPK;
	private String _dataSourceId;
	private String _description;
	private String _name;
	private String _type;
	private String _url;

}