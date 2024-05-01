/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.routine;

import com.liferay.jethr0.git.commit.GitCommitEntity;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseUpstreamBranchRoutineEntity
	extends BaseRoutineEntity implements UpstreamBranchRoutineEntity {

	@Override
	public JSONObject getJSONObject() {
		JSONObject jsonObject = super.getJSONObject();

		jsonObject.put(
			"r_previousGitCommitToRoutines_c_gitCommitId",
			getPreviousGitCommitEntityId());

		return jsonObject;
	}

	@Override
	public GitCommitEntity getPreviousGitCommitEntity() {
		return _previousGitCommitEntity;
	}

	@Override
	public long getPreviousGitCommitEntityId() {
		return _previousGitCommitEntityId;
	}

	@Override
	public void setJSONObject(JSONObject jsonObject) {
		super.setJSONObject(jsonObject);

		_previousGitCommitEntityId = jsonObject.optLong(
			"r_previousGitCommitToRoutines_c_gitCommitId");
	}

	@Override
	public void setPreviousGitCommitEntity(
		GitCommitEntity previousGitCommitEntity) {

		_previousGitCommitEntity = previousGitCommitEntity;

		if (_previousGitCommitEntity != null) {
			_previousGitCommitEntityId = _previousGitCommitEntity.getId();
		}
		else {
			_previousGitCommitEntityId = 0;
		}
	}

	protected BaseUpstreamBranchRoutineEntity(JSONObject jsonObject) {
		super(jsonObject);
	}

	private GitCommitEntity _previousGitCommitEntity;
	private long _previousGitCommitEntityId;

}