/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.git.pull;

import com.liferay.jethr0.entity.BaseEntity;
import com.liferay.jethr0.git.branch.GitBranchEntity;
import com.liferay.jethr0.util.StringUtil;

import java.net.URL;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseGitPullEntity
	extends BaseEntity implements GitPullEntity {

	@Override
	public GitBranchEntity getBaseGitBranchEntity() {
		return _baseGitBranchEntity;
	}

	@Override
	public long getBaseGitBranchEntityId() {
		return _baseGitBranchEntityId;
	}

	@Override
	public GitBranchEntity getHeadGitBranchEntity() {
		return _headGitBranchEntity;
	}

	@Override
	public long getHeadGitBranchEntityId() {
		return _headGitBranchEntityId;
	}

	@Override
	public JSONObject getJSONObject() {
		JSONObject jsonObject = super.getJSONObject();

		jsonObject.put(
			"r_baseGitBranchToGitPulls_c_gitBranchId", _baseGitBranchEntityId
		).put(
			"r_headGitBranchToGitPulls_c_gitBranchId", _headGitBranchEntityId
		).put(
			"url", getURL()
		);

		return jsonObject;
	}

	@Override
	public URL getURL() {
		return _url;
	}

	@Override
	public void setBaseGitBranchEntity(GitBranchEntity baseGitBranchEntity) {
		_baseGitBranchEntity = baseGitBranchEntity;

		if (_baseGitBranchEntity != null) {
			_baseGitBranchEntityId = _baseGitBranchEntity.getId();
		}
		else {
			_baseGitBranchEntityId = 0;
		}
	}

	@Override
	public void setHeadGitBranchEntity(GitBranchEntity headGitBranchEntity) {
		_headGitBranchEntity = headGitBranchEntity;

		if (_headGitBranchEntity != null) {
			_headGitBranchEntityId = _headGitBranchEntity.getId();
		}
		else {
			_headGitBranchEntityId = 0;
		}
	}

	@Override
	public void setJSONObject(JSONObject jsonObject) {
		super.setJSONObject(jsonObject);

		_baseGitBranchEntityId = jsonObject.optLong(
			"r_baseGitBranchToGitPulls_c_gitBranchId");
		_headGitBranchEntityId = jsonObject.optLong(
			"r_headGitBranchToGitPulls_c_gitBranchId");
		_url = StringUtil.toURL(jsonObject.getString("url"));
	}

	@Override
	public void setURL(URL sha) {
		_url = sha;
	}

	protected BaseGitPullEntity(JSONObject jsonObject) {
		super(jsonObject);
	}

	private GitBranchEntity _baseGitBranchEntity;
	private long _baseGitBranchEntityId;
	private GitBranchEntity _headGitBranchEntity;
	private long _headGitBranchEntityId;
	private URL _url;

}