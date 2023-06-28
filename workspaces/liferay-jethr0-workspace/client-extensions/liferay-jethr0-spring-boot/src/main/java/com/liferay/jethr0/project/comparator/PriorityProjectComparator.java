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

package com.liferay.jethr0.project.comparator;

import com.liferay.jethr0.project.Project;
import com.liferay.jethr0.project.prioritizer.ProjectPrioritizer;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class PriorityProjectComparator extends BaseProjectComparator {

	public PriorityProjectComparator(JSONObject jsonObject) {
		super(jsonObject);
	}

	public PriorityProjectComparator(
		ProjectPrioritizer projectPrioritizer, JSONObject jsonObject) {

		super(projectPrioritizer, jsonObject);
	}

	@Override
	public int compare(Project project1, Project project2) {
		return Integer.compare(project1.getPriority(), project2.getPriority());
	}

}