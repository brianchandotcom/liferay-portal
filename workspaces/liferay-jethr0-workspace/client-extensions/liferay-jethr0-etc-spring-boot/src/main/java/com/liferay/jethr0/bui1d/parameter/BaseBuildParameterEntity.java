/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.bui1d.parameter;

import com.liferay.jethr0.bui1d.BuildEntity;
import com.liferay.jethr0.entity.BaseEntity;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseBuildParameterEntity
	extends BaseEntity implements BuildParameterEntity {

	@Override
	public BuildEntity getBuildEntity() {
		return _buildEntity;
	}

	@Override
	public long getBuildEntityId() {
		return _buildEntityId;
	}

	@Override
	public JSONObject getJSONObject() {
		JSONObject jsonObject = super.getJSONObject();

		jsonObject.put(
			"name", getName()
		).put(
			"r_buildToBuildParameters_c_buildId", getBuildEntityId()
		).put(
			"value", getValue()
		).put(
			"value", getValue()
		);

		return jsonObject;
	}

	@Override
	public String getName() {
		return _name;
	}

	@Override
	public String getValue() {
		return _value;
	}

	@Override
	public void setBuildEntity(BuildEntity buildEntity) {
		_buildEntity = buildEntity;

		if (_buildEntity != null) {
			_buildEntityId = _buildEntity.getId();
		}
		else {
			_buildEntityId = 0;
		}
	}

	protected BaseBuildParameterEntity(JSONObject jsonObject) {
		super(jsonObject);

		_buildEntityId = jsonObject.optLong(
			"r_buildToBuildParameters_c_buildId");
		_name = jsonObject.getString("name");
		_value = jsonObject.getString("value");
	}

	private BuildEntity _buildEntity;
	private long _buildEntityId;
	private final String _name;
	private final String _value;

}