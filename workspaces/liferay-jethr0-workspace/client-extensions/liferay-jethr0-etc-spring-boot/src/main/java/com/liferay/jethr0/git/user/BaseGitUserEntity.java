/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.git.user;

import com.liferay.jethr0.entity.BaseEntity;
import com.liferay.jethr0.git.branch.GitBranchEntity;
import com.liferay.jethr0.git.pull.GitPullEntity;
import com.liferay.jethr0.util.StringUtil;

import java.net.URL;

import java.util.Set;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseGitUserEntity
	extends BaseEntity implements GitUserEntity {

	@Override
	public void addGitBranchEntities(Set<GitBranchEntity> gitBranchEntities) {
		addRelatedEntities(gitBranchEntities);
	}

	@Override
	public void addGitBranchEntity(GitBranchEntity gitBranchEntity) {
		addRelatedEntity(gitBranchEntity);
	}

	@Override
	public void addGitPullEntities(Set<GitPullEntity> gitPullEntities) {
		addRelatedEntities(gitPullEntities);
	}

	@Override
	public void addGitPullEntity(GitPullEntity gitPullEntity) {
		addRelatedEntity(gitPullEntity);
	}

	@Override
	public Set<GitBranchEntity> getGitBranchEntities() {
		return getRelatedEntities(GitBranchEntity.class);
	}

	@Override
	public Set<GitPullEntity> getGitPullEntities() {
		return getRelatedEntities(GitPullEntity.class);
	}

	@Override
	public JSONObject getJSONObject() {
		JSONObject jsonObject = super.getJSONObject();

		jsonObject.put("url", getURL());

		return jsonObject;
	}

	@Override
	public URL getURL() {
		return _url;
	}

	@Override
	public void removeGitBranchEntities(
		Set<GitBranchEntity> gitBranchEntities) {

		removeRelatedEntities(gitBranchEntities);
	}

	@Override
	public void removeGitBranchEntity(GitBranchEntity gitBranchEntity) {
		removeRelatedEntity(gitBranchEntity);
	}

	@Override
	public void removeGitPullEntities(Set<GitPullEntity> gitPullEntities) {
		removeRelatedEntities(gitPullEntities);
	}

	@Override
	public void removeGitPullEntity(GitPullEntity gitPullEntity) {
		removeRelatedEntity(gitPullEntity);
	}

	@Override
	public void setJSONObject(JSONObject jsonObject) {
		super.setJSONObject(jsonObject);

		_url = StringUtil.toURL(jsonObject.getString("url"));
	}

	@Override
	public void setURL(URL sha) {
		_url = sha;
	}

	protected BaseGitUserEntity(JSONObject jsonObject) {
		super(jsonObject);
	}

	private URL _url;

}