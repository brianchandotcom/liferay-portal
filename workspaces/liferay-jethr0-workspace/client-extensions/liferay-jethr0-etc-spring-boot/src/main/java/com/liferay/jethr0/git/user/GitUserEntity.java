/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.git.user;

import com.liferay.jethr0.entity.Entity;
import com.liferay.jethr0.git.branch.GitBranchEntity;
import com.liferay.jethr0.git.pull.GitPullEntity;

import java.net.URL;

import java.util.Set;

/**
 * @author Michael Hashimoto
 */
public interface GitUserEntity extends Entity {

	public void addGitBranchEntities(Set<GitBranchEntity> gitBranchEntities);

	public void addGitBranchEntity(GitBranchEntity gitBranchEntity);

	public void addGitPullEntities(Set<GitPullEntity> gitPullEntities);

	public void addGitPullEntity(GitPullEntity gitPullEntity);

	public Set<GitBranchEntity> getGitBranchEntities();

	public Set<GitPullEntity> getGitPullEntities();

	public URL getURL();

	public void removeGitBranchEntities(Set<GitBranchEntity> gitBranchEntities);

	public void removeGitBranchEntity(GitBranchEntity gitBranchEntity);

	public void removeGitPullEntities(Set<GitPullEntity> gitPullEntities);

	public void removeGitPullEntity(GitPullEntity gitPullEntity);

	public void setURL(URL url);

}