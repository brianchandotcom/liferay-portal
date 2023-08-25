/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.project.prioritizer;

import com.liferay.jethr0.entity.Entity;
import com.liferay.jethr0.project.comparator.ProjectComparator;

import java.util.Set;

/**
 * @author Michael Hashimoto
 */
public interface ProjectPrioritizerEntity extends Entity {

	public void addProjectComparator(ProjectComparator projectComparator);

	public void addProjectComparators(
		Set<ProjectComparator> projectComparators);

	public String getName();

	public Set<ProjectComparator> getProjectComparators();

	public void removeProjectComparator(ProjectComparator projectComparator);

	public void removeProjectComparators(
		Set<ProjectComparator> projectComparators);

	public void setName(String name);

}