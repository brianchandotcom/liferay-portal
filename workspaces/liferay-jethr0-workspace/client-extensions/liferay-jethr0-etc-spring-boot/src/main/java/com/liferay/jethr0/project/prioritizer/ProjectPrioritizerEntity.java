/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.project.prioritizer;

import com.liferay.jethr0.entity.Entity;
import com.liferay.jethr0.project.comparator.ProjectComparatorEntity;

import java.util.Set;

/**
 * @author Michael Hashimoto
 */
public interface ProjectPrioritizerEntity extends Entity {

	public void addProjectComparatorEntities(
		Set<ProjectComparatorEntity> projectComparatorEntities);

	public void addProjectComparatorEntity(
		ProjectComparatorEntity projectComparatorEntity);

	public String getName();

	public Set<ProjectComparatorEntity> getProjectComparatorEntities();

	public void removeProjectComparatorEntities(
		Set<ProjectComparatorEntity> projectComparatorEntities);

	public void removeProjectComparatorEntity(
		ProjectComparatorEntity projectComparatorEntity);

	public void setName(String name);

}