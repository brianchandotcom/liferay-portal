/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.git.commit;

import com.liferay.jethr0.entity.BaseEntity;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseGitCommitEntity
	extends BaseEntity implements GitCommitEntity {

	@Override
	public JSONObject getJSONObject() {
		JSONObject jsonObject = super.getJSONObject();

		jsonObject.put("sha", getSHA());

		return jsonObject;
	}

	@Override
	public String getSHA() {
		return _sha;
	}

	@Override
	public void setJSONObject(JSONObject jsonObject) {
		super.setJSONObject(jsonObject);

		_sha = jsonObject.getString("sha");
	}

	@Override
	public void setSHA(String sha) {
		_sha = sha;
	}

	protected BaseGitCommitEntity(JSONObject jsonObject) {
		super(jsonObject);
	}

	private String _sha;

}