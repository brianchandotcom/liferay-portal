/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.jethr0.gitbranch;

import com.liferay.jethr0.entity.BaseEntity;
import com.liferay.jethr0.project.Project;
import com.liferay.jethr0.util.StringUtil;

import java.net.URL;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class BaseGitBranch extends BaseEntity implements GitBranch {

	@Override
	public void addProject(Project project) {
		addProjects(Collections.singleton(project));
	}

	@Override
	public void addProjects(Set<Project> projects) {
		_projects.addAll(projects);
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
	public Set<Project> getProjects() {
		return _projects;
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
	public void removeProject(Project project) {
		_projects.remove(project);
	}

	@Override
	public void removeProjects(Set<Project> projects) {
		_projects.removeAll(projects);
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

	protected BaseGitBranch(JSONObject jsonObject) {
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
	private final Set<Project> _projects = new HashSet<>();
	private boolean _rebased;
	private String _repositoryName;
	private String _upstreamBranchName;
	private String _upstreamBranchSHA;
	private URL _url;

}