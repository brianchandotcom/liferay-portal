/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.routine;

import com.liferay.jethr0.git.branch.GitBranchEntity;

import java.util.Collections;
import java.util.Set;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseUpstreamBranchCronRoutineEntity
	extends BaseCronRoutineEntity implements UpstreamBranchCronRoutineEntity {

	@Override
	public void addGitBranchEntities(Set<GitBranchEntity> gitBranchEntities) {
		addRelatedEntities(gitBranchEntities);
	}

	@Override
	public void addGitBranchEntity(GitBranchEntity gitBranchEntity) {
		addRelatedEntities(Collections.singleton(gitBranchEntity));
	}

	@Override
	public Set<GitBranchEntity> getGitBranchEntities() {
		return getRelatedEntities(GitBranchEntity.class);
	}

	@Override
	public JSONObject getJSONObject() {
		JSONObject jsonObject = super.getJSONObject();

		jsonObject.put("previousBranchSHA", getPreviousBranchSHA());

		return jsonObject;
	}

	public String getPreviousBranchSHA() {
		return _previousBranchSHA;
	}

	@Override
	public void removeGitBranchEntities(
		Set<GitBranchEntity> gitBranchEntities) {

		removeRelatedEntities(gitBranchEntities);
	}

	@Override
	public void removeGitBranchEntity(GitBranchEntity gitBranchEntity) {
		removeRelatedEntities(Collections.singleton(gitBranchEntity));
	}

	@Override
	public void setJSONObject(JSONObject jsonObject) {
		super.setJSONObject(jsonObject);

		_previousBranchSHA = jsonObject.optString("previousBranchSHA");
	}

	public void setPreviousBranchSHA(String previousBranchSHA) {
		_previousBranchSHA = previousBranchSHA;
	}

	protected BaseUpstreamBranchCronRoutineEntity(JSONObject jsonObject) {
		super(jsonObject);

		_previousBranchSHA = jsonObject.getString("previousBranchSHA");
	}

	private String _previousBranchSHA;

}