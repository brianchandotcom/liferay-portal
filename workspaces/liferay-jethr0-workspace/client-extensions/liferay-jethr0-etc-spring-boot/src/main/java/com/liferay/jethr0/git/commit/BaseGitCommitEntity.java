/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.git.commit;

import com.liferay.jethr0.entity.BaseEntity;
import com.liferay.jethr0.git.branch.GitBranchEntity;
import com.liferay.jethr0.job.JobEntity;
import com.liferay.jethr0.routine.RoutineEntity;

import java.util.Set;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseGitCommitEntity
	extends BaseEntity implements GitCommitEntity {

	@Override
	public void addJobEntities(Set<JobEntity> jobEntities) {
		addRelatedEntities(jobEntities);
	}

	@Override
	public void addJobEntity(JobEntity jobEntity) {
		addRelatedEntity(jobEntity);
	}

	@Override
	public void addRoutineEntities(Set<RoutineEntity> routineEntities) {
		addRelatedEntities(routineEntities);
	}

	@Override
	public void addRoutineEntity(RoutineEntity routineEntity) {
		addRelatedEntity(routineEntity);
	}

	@Override
	public GitBranchEntity getGitBranchEntity() {
		return _gitBranchEntity;
	}

	@Override
	public long getGitBranchEntityId() {
		return _gitBranchEntityId;
	}

	@Override
	public Set<JobEntity> getJobEntities() {
		return getRelatedEntities(JobEntity.class);
	}

	@Override
	public JSONObject getJSONObject() {
		JSONObject jsonObject = super.getJSONObject();

		jsonObject.put(
			"r_gitBranchToGitCommits_c_gitBranchId", _gitBranchEntityId
		).put(
			"sha", getSHA()
		);

		return jsonObject;
	}

	@Override
	public Set<RoutineEntity> getRoutineEntities() {
		return getRelatedEntities(RoutineEntity.class);
	}

	@Override
	public String getSHA() {
		return _sha;
	}

	@Override
	public void removeJobEntities(Set<JobEntity> jobEntities) {
		removeRelatedEntities(jobEntities);
	}

	@Override
	public void removeJobEntity(JobEntity jobEntity) {
		removeRelatedEntity(jobEntity);
	}

	@Override
	public void removeRoutineEntities(Set<RoutineEntity> routineEntities) {
		removeRelatedEntities(routineEntities);
	}

	@Override
	public void removeRoutineEntity(RoutineEntity routineEntity) {
		removeRelatedEntity(routineEntity);
	}

	@Override
	public void setGitBranchEntity(GitBranchEntity gitBranchEntity) {
		_gitBranchEntity = gitBranchEntity;

		if (gitBranchEntity == null) {
			_gitBranchEntityId = 0;
		}
		else {
			_gitBranchEntityId = gitBranchEntity.getId();
		}
	}

	@Override
	public void setJSONObject(JSONObject jsonObject) {
		super.setJSONObject(jsonObject);

		_gitBranchEntityId = jsonObject.optLong(
			"r_gitBranchToGitCommits_c_gitBranchId");
		_sha = jsonObject.getString("sha");
	}

	@Override
	public void setSHA(String sha) {
		_sha = sha;
	}

	protected BaseGitCommitEntity(JSONObject jsonObject) {
		super(jsonObject);
	}

	private GitBranchEntity _gitBranchEntity;
	private long _gitBranchEntityId;
	private String _sha;

}