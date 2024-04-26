/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.git.pullrequest;

import com.liferay.jethr0.entity.BaseEntity;
import com.liferay.jethr0.git.branch.GitBranchEntity;
import com.liferay.jethr0.git.user.GitUserEntity;
import com.liferay.jethr0.job.JobEntity;
import com.liferay.jethr0.util.StringUtil;

import java.net.URL;

import java.util.Set;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseGitPullRequestEntity
	extends BaseEntity implements GitPullRequestEntity {

	@Override
	public void addJobEntities(Set<JobEntity> jobEntities) {
		addRelatedEntities(jobEntities);
	}

	@Override
	public void addJobEntity(JobEntity jobEntity) {
		addRelatedEntity(jobEntity);
	}

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
	public Set<JobEntity> getJobEntities() {
		return getRelatedEntities(JobEntity.class);
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
	public GitUserEntity getReceiverGitUserEntity() {
		return _receiverGitUserEntity;
	}

	@Override
	public long getReceiverGitUserEntityId() {
		return _receiverGitUserEntityId;
	}

	@Override
	public GitUserEntity getSenderGitUserEntity() {
		return _senderGitUserEntity;
	}

	@Override
	public long getSenderGitUserEntityId() {
		return _senderGitUserEntityId;
	}

	@Override
	public URL getURL() {
		return _url;
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
	public void setReceiverGitUserEntity(GitUserEntity receiverGitUserEntity) {
		_receiverGitUserEntity = receiverGitUserEntity;

		if (_receiverGitUserEntity != null) {
			_receiverGitUserEntityId = _receiverGitUserEntity.getId();
		}
		else {
			_receiverGitUserEntityId = 0;
		}
	}

	@Override
	public void setSenderGitUserEntity(GitUserEntity senderGitUserEntity) {
		_senderGitUserEntity = senderGitUserEntity;

		if (_senderGitUserEntity != null) {
			_senderGitUserEntityId = _senderGitUserEntity.getId();
		}
		else {
			_senderGitUserEntityId = 0;
		}
	}

	@Override
	public void setURL(URL sha) {
		_url = sha;
	}

	protected BaseGitPullRequestEntity(JSONObject jsonObject) {
		super(jsonObject);
	}

	private GitBranchEntity _baseGitBranchEntity;
	private long _baseGitBranchEntityId;
	private GitBranchEntity _headGitBranchEntity;
	private long _headGitBranchEntityId;
	private GitUserEntity _receiverGitUserEntity;
	private long _receiverGitUserEntityId;
	private GitUserEntity _senderGitUserEntity;
	private long _senderGitUserEntityId;
	private URL _url;

}