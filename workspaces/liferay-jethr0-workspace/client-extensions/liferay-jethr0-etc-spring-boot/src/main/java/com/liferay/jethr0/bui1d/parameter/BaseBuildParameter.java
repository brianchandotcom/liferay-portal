/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.bui1d.parameter;

import com.liferay.jethr0.bui1d.Build;
import com.liferay.jethr0.entity.BaseEntity;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseBuildParameter
	extends BaseEntity implements BuildParameter {

	@Override
	public Build getBuild() {
		return _build;
	}

	@Override
	public long getBuildId() {
		return _buildId;
	}

	@Override
	public JSONObject getJSONObject() {
		JSONObject jsonObject = super.getJSONObject();

		jsonObject.put(
			"name", getName()
		).put(
			"r_buildToBuildParameters_c_buildId", getBuildId()
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
	public void setBuild(Build build) {
		_build = build;

		if (_build != null) {
			_buildId = _build.getId();
		}
		else {
			_buildId = 0;
		}
	}

	protected BaseBuildParameter(JSONObject jsonObject) {
		super(jsonObject);

		_buildId = jsonObject.optLong("r_buildToBuildParameters_c_buildId");
		_name = jsonObject.getString("name");
		_value = jsonObject.getString("value");
	}

	private Build _build;
	private long _buildId;
	private final String _name;
	private final String _value;

}