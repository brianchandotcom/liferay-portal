/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.engine.client.model;

/**
 * @author Matthew Kong
 */
public class DXPTeam {

	public long getGroupId() {
		return _groupId;
	}

	public String getId() {
		return _id;
	}

	public String getName() {
		return _name;
	}

	public String getOsbAsahDataSourceId() {
		return _osbAsahDataSourceId;
	}

	public long getTeamId() {
		return _teamId;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
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

	public void setTeamId(long teamId) {
		_teamId = teamId;
	}

	private long _groupId;
	private String _id;
	private String _name;
	private String _osbAsahDataSourceId;
	private long _teamId;

}