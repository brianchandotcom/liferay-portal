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

package com.liferay.jethr0.project.prioritizer;

import com.liferay.jethr0.entity.Entity;
import com.liferay.jethr0.project.comparator.ProjectComparator;

import java.util.Set;

/**
 * @author Michael Hashimoto
 */
public interface ProjectPrioritizer extends Entity {

	public void addProjectComparator(ProjectComparator projectComparator);

	public void addProjectComparators(
		Set<ProjectComparator> projectComparators);

	public String getName();

	public Set<ProjectComparator> getProjectComparators();

	public void removeProjectComparator(ProjectComparator projectComparator);

	public void setName(String name);

}