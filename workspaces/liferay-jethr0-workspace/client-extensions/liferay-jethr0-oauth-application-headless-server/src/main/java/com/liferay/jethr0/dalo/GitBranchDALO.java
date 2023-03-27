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

package com.liferay.jethr0.dalo;

import com.liferay.jethr0.gitbranch.GitBranch;
import com.liferay.jethr0.gitbranch.GitBranchFactory;

import java.net.URL;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class GitBranchDALO extends BaseDALO {

	public GitBranch createGitBranch(
		String branchName, String branchSHA, boolean rebased,
		String repositoryName, String upstreamBranchName,
		String upstreamBranchSHA, URL url) {

		JSONObject requestJSONObject = new JSONObject();

		requestJSONObject.put(
			"branchName", branchName
		).put(
			"branchSHA", branchSHA
		).put(
			"rebased", rebased
		).put(
			"repositoryName", repositoryName
		).put(
			"upstreamBranchName", upstreamBranchName
		).put(
			"upstreamBranchSHA", upstreamBranchSHA
		).put(
			"url", url
		);

		JSONObject responseJSONObject = create(requestJSONObject);

		if (responseJSONObject == null) {
			throw new RuntimeException("No response");
		}

		return GitBranchFactory.newGitBranch(responseJSONObject);
	}

	public void deleteGitBranch(GitBranch gitBranch) {
		if (gitBranch == null) {
			return;
		}

		delete(gitBranch.getId());

		GitBranchFactory.removeGitBranch(gitBranch);
	}

	public List<GitBranch> retrieveGitBranches() {
		List<GitBranch> gitBranches = new ArrayList<>();

		for (JSONObject responseJSONObject : retrieve()) {
			GitBranch gitBranch = GitBranchFactory.newGitBranch(
				responseJSONObject);

			gitBranch.addProjects(
				_projectsToGitBranchesDALO.retrieveProjects(gitBranch));

			gitBranches.add(gitBranch);
		}

		return gitBranches;
	}

	public GitBranch updateGitBranch(GitBranch gitBranch) {
		_projectsToGitBranchesDALO.updateRelationships(gitBranch);

		JSONObject responseJSONObject = update(gitBranch.getJSONObject());

		if (responseJSONObject == null) {
			throw new RuntimeException("No response");
		}

		return gitBranch;
	}

	@Override
	protected String getObjectDefinitionLabel() {
		return "Git Branch";
	}

	@Override
	protected String getObjectDefinitionPluralLabel() {
		return "Git Branches";
	}

	@Autowired
	private ProjectsToGitBranchesDALO _projectsToGitBranchesDALO;

}