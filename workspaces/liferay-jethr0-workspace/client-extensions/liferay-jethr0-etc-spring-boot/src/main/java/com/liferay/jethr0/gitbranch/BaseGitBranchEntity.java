/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.gitbranch;

import com.liferay.jethr0.entity.BaseEntity;
import com.liferay.jethr0.job.JobEntity;
import com.liferay.jethr0.util.StringUtil;

import java.net.URL;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class BaseGitBranchEntity extends BaseEntity implements GitBranchEntity {

	@Override
	public void addJobEntities(Set<JobEntity> jobEntities) {
		_jobEntities.addAll(jobEntities);
	}

	@Override
	public void addJobEntity(JobEntity jobEntity) {
		addJobEntities(Collections.singleton(jobEntity));
	}

	@Override
	public String getBranchName() {
		return _branchName;
	}

	@Override
	public String getBranchSHA() {
		return _branchSHA;
	}

	@Override
	public Set<JobEntity> getJobEntities() {
		return _jobEntities;
	}

	@Override
	public JSONObject getJSONObject() {
		JSONObject jsonObject = super.getJSONObject();

		jsonObject.put(
			"branchName", getBranchName()
		).put(
			"branchSHA", getBranchSHA()
		).put(
			"rebased", getRebased()
		).put(
			"repositoryName", getRepositoryName()
		).put(
			"upstreamBranchName", getUpstreamBranchName()
		).put(
			"upstreamBranchSHA", getUpstreamBranchSHA()
		).put(
			"url", getURL()
		);

		return jsonObject;
	}

	@Override
	public boolean getRebased() {
		return _rebased;
	}

	@Override
	public String getRepositoryName() {
		return _repositoryName;
	}

	@Override
	public String getUpstreamBranchName() {
		return _upstreamBranchName;
	}

	@Override
	public String getUpstreamBranchSHA() {
		return _upstreamBranchSHA;
	}

	@Override
	public URL getURL() {
		return _url;
	}

	@Override
	public void removeJobEntities(Set<JobEntity> jobEntities) {
		_jobEntities.removeAll(jobEntities);
	}

	@Override
	public void removeJobEntity(JobEntity jobEntity) {
		_jobEntities.remove(jobEntity);
	}

	@Override
	public void setBranchName(String branchName) {
		_branchName = branchName;
	}

	@Override
	public void setBranchSHA(String branchSHA) {
		_branchSHA = branchSHA;
	}

	@Override
	public void setRebased(boolean rebased) {
		_rebased = rebased;
	}

	@Override
	public void setRepositoryName(String repositoryName) {
		_repositoryName = repositoryName;
	}

	@Override
	public void setUpstreamBranchName(String upstreamBranchName) {
		_upstreamBranchName = upstreamBranchName;
	}

	@Override
	public void setUpstreamBranchSHA(String upstreamBranchSHA) {
		_upstreamBranchSHA = upstreamBranchSHA;
	}

	@Override
	public void setURL(URL url) {
		_url = url;
	}

	protected BaseGitBranchEntity(JSONObject jsonObject) {
		super(jsonObject);

		_branchName = jsonObject.getString("branchName");
		_branchSHA = jsonObject.getString("branchSHA");
		_rebased = jsonObject.getBoolean("rebased");
		_repositoryName = jsonObject.getString("repositoryName");
		_upstreamBranchName = jsonObject.getString("upstreamBranchName");
		_upstreamBranchSHA = jsonObject.getString("upstreamBranchSHA");
		_url = StringUtil.toURL(jsonObject.getString("url"));
	}

	private String _branchName;
	private String _branchSHA;
	private final Set<JobEntity> _jobEntities = new HashSet<>();
	private boolean _rebased;
	private String _repositoryName;
	private String _upstreamBranchName;
	private String _upstreamBranchSHA;
	private URL _url;

}