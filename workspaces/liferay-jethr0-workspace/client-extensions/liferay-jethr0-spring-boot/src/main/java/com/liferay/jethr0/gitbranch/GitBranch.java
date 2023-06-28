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

import com.liferay.jethr0.entity.Entity;
import com.liferay.jethr0.project.Project;

import java.net.URL;

import java.util.Set;

/**
 * @author Michael Hashimoto
 */
public interface GitBranch extends Entity {

	public void addProject(Project project);

	public void addProjects(Set<Project> projects);

	public String getBranchName();

	public String getBranchSHA();

	public Set<Project> getProjects();

	public boolean getRebased();

	public String getRepositoryName();

	public String getUpstreamBranchName();

	public String getUpstreamBranchSHA();

	public URL getURL();

	public void removeProject(Project project);

	public void removeProjects(Set<Project> projects);

	public void setBranchName(String branchName);

	public void setBranchSHA(String branchSHA);

	public void setRebased(boolean rebased);

	public void setRepositoryName(String repositoryName);

	public void setUpstreamBranchName(String upstreamBranchName);

	public void setUpstreamBranchSHA(String upstreamBranchSHA);

	public void setURL(URL url);

}