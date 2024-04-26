/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.git.user;

import com.liferay.jethr0.entity.Entity;
import com.liferay.jethr0.git.branch.GitBranchEntity;
import com.liferay.jethr0.git.pullrequest.GitPullRequestEntity;

import java.net.URL;

import java.util.Set;

/**
 * @author Michael Hashimoto
 */
public interface GitUserEntity extends Entity {

	public void addGitBranchEntities(Set<GitBranchEntity> gitBranchEntities);

	public void addGitBranchEntity(GitBranchEntity gitBranchEntity);

	public void addGitPullRequestEntities(
		Set<GitPullRequestEntity> gitPullRequestEntities);

	public void addGitPullRequestEntity(
		GitPullRequestEntity gitPullRequestEntity);

	public Set<GitBranchEntity> getGitBranchEntities();

	public Set<GitPullRequestEntity> getGitPullRequestEntities();

	public URL getURL();

	public void removeGitBranchEntities(Set<GitBranchEntity> gitBranchEntities);

	public void removeGitBranchEntity(GitBranchEntity gitBranchEntity);

	public void removeGitPullRequestEntities(
		Set<GitPullRequestEntity> gitPullRequestEntities);

	public void removeGitPullRequestEntity(
		GitPullRequestEntity gitPullRequestEntity);

	public void setURL(URL url);

}