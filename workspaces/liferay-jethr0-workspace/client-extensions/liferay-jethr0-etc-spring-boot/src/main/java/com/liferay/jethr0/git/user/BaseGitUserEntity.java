/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.git.user;

import com.liferay.jethr0.entity.BaseEntity;
import com.liferay.jethr0.git.branch.GitBranchEntity;
import com.liferay.jethr0.git.pullrequest.GitPullRequestEntity;
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
	public void addGitPullRequestEntities(
		Set<GitPullRequestEntity> gitPullRequestEntities) {

		addRelatedEntities(gitPullRequestEntities);
	}

	@Override
	public void addGitPullRequestEntity(
		GitPullRequestEntity gitPullRequestEntity) {

		addRelatedEntity(gitPullRequestEntity);
	}

	@Override
	public Set<GitBranchEntity> getGitBranchEntities() {
		return getRelatedEntities(GitBranchEntity.class);
	}

	@Override
	public Set<GitPullRequestEntity> getGitPullRequestEntities() {
		return getRelatedEntities(GitPullRequestEntity.class);
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
	public void removeGitPullRequestEntities(
		Set<GitPullRequestEntity> gitPullRequestEntities) {

		removeRelatedEntities(gitPullRequestEntities);
	}

	@Override
	public void removeGitPullRequestEntity(
		GitPullRequestEntity gitPullRequestEntity) {

		removeRelatedEntity(gitPullRequestEntity);
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