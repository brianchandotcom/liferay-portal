/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.gitbranch;

import com.liferay.jethr0.entity.Entity;
import com.liferay.jethr0.job.JobEntity;

import java.net.URL;

import java.util.Set;

/**
 * @author Michael Hashimoto
 */
public interface GitBranchEntity extends Entity {

	public void addJobEntities(Set<JobEntity> jobEntities);

	public void addJobEntity(JobEntity jobEntity);

	public String getBranchName();

	public String getBranchSHA();

	public Set<JobEntity> getJobEntities();

	public boolean getRebased();

	public String getRepositoryName();

	public String getUpstreamBranchName();

	public String getUpstreamBranchSHA();

	public URL getURL();

	public void removeJobEntities(Set<JobEntity> jobEntities);

	public void removeJobEntity(JobEntity jobEntity);

	public void setBranchName(String branchName);

	public void setBranchSHA(String branchSHA);

	public void setRebased(boolean rebased);

	public void setRepositoryName(String repositoryName);

	public void setUpstreamBranchName(String upstreamBranchName);

	public void setUpstreamBranchSHA(String upstreamBranchSHA);

	public void setURL(URL url);

}