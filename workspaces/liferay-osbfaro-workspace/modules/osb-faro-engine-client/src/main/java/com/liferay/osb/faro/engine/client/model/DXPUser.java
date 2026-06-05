/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.engine.client.model;

/**
 * @author Matthew Kong
 */
public class DXPUser {

	public String getId() {
		return _id;
	}

	public String getName() {
		return _name;
	}

	public String getOsbAsahDataSourceId() {
		return _osbAsahDataSourceId;
	}

	public long getUserId() {
		return _userId;
	}

	public void setId(String id) {
		_id = id;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setOsbAsahDataSourceId(String osbAsahDataSourceId) {
		_osbAsahDataSourceId = osbAsahDataSourceId;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	private String _id;
	private String _name;
	private String _osbAsahDataSourceId;
	private long _userId;

}