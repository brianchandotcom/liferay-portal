/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.gitbranch;

import com.liferay.jethr0.entity.Entity;
import com.liferay.jethr0.project.ProjectEntity;

import java.net.URL;

import java.util.Set;

/**
 * @author Michael Hashimoto
 */
public interface GitBranch extends Entity {

	public void addProjectEntities(Set<ProjectEntity> projectEntities);

	public void addProjectEntity(ProjectEntity projectEntity);

	public String getBranchName();

	public String getBranchSHA();

	public Set<ProjectEntity> getProjectEntities();

	public boolean getRebased();

	public String getRepositoryName();

	public String getUpstreamBranchName();

	public String getUpstreamBranchSHA();

	public URL getURL();

	public void removeProjectEntities(Set<ProjectEntity> projectEntities);

	public void removeProjectEntity(ProjectEntity projectEntity);

	public void setBranchName(String branchName);

	public void setBranchSHA(String branchSHA);

	public void setRebased(boolean rebased);

	public void setRepositoryName(String repositoryName);

	public void setUpstreamBranchName(String upstreamBranchName);

	public void setUpstreamBranchSHA(String upstreamBranchSHA);

	public void setURL(URL url);

}