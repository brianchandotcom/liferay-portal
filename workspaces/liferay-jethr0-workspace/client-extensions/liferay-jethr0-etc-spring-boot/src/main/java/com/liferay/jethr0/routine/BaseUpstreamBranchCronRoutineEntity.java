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
public abstract class BaseUpstreamBranchCronRoutineEntity
	extends BaseCronRoutineEntity implements UpstreamBranchCronRoutineEntity {

	@Override
	public JSONObject getJSONObject() {
		JSONObject jsonObject = super.getJSONObject();

		jsonObject.put(
			"previousBranchSHA", getPreviousBranchSHA()
		).put(
			"r_previousGitCommitToJobs_c_gitCommitId",
			getPreviousGitCommitEntityId()
		);

		return jsonObject;
	}

	public String getPreviousBranchSHA() {
		return _previousBranchSHA;
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
			"r_previousGitCommitToJobs_c_gitCommitId");
		_previousBranchSHA = jsonObject.optString("previousBranchSHA");
	}

	public void setPreviousBranchSHA(String previousBranchSHA) {
		_previousBranchSHA = previousBranchSHA;
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

	protected BaseUpstreamBranchCronRoutineEntity(JSONObject jsonObject) {
		super(jsonObject);
	}

	private String _previousBranchSHA;
	private GitCommitEntity _previousGitCommitEntity;
	private long _previousGitCommitEntityId;

}