/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.engine.client.model;

/**
 * @author Matthew Kong
 */
public class ActivityAsset {

	public long getCount() {
		return _count;
	}

	public String getDataSourceAssetPK() {
		return _dataSourceAssetPK;
	}

	public String getDataSourceName() {
		return _dataSourceName;
	}

	public String getId() {
		return _id;
	}

	public String getName() {
		return _name;
	}

	public void setCount(long count) {
		_count = count;
	}

	public void setDataSourceAssetPK(String dataSourceAssetPK) {
		_dataSourceAssetPK = dataSourceAssetPK;
	}

	public void setDataSourceName(String dataSourceName) {
		_dataSourceName = dataSourceName;
	}

	public void setId(String id) {
		_id = id;
	}

	public void setName(String name) {
		_name = name;
	}

	private long _count;
	private String _dataSourceAssetPK;
	private String _dataSourceName;
	private String _id;
	private String _name;

}