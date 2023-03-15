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

import com.liferay.jethr0.project.prioritizer.ProjectPrioritizer;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class ProjectComparatorFactory {

	public static ProjectComparator newProjectComparator(
		ProjectPrioritizer projectPrioritizer, JSONObject jsonObject) {

		long id = jsonObject.getLong("id");

		synchronized (_projectComparators) {
			if (_projectComparators.containsKey(id)) {
				return _projectComparators.get(id);
			}

			ProjectComparator.Type type = ProjectComparator.Type.get(
				jsonObject.getJSONObject("type"));

			ProjectComparator projectComparator;

			if (type == ProjectComparator.Type.FIFO) {
				projectComparator = new FIFOProjectComparator(
					projectPrioritizer, jsonObject);
			}
			else if (type == ProjectComparator.Type.PROJECT_PRIORITY) {
				projectComparator = new PriorityProjectComparator(
					projectPrioritizer, jsonObject);
			}
			else {
				throw new UnsupportedOperationException();
			}

			_projectComparators.put(
				projectComparator.getId(), projectComparator);
		}

		return projectComparator;
	}

	public static void removeProjectComparator(
		ProjectComparator projectComparator) {

		if (projectComparator == null) {
			return;
		}

		synchronized (_projectComparators) {
			_projectComparators.remove(projectComparator.getId());
		}
	}

	private static final Map<Long, ProjectComparator> _projectComparators =
		Collections.synchronizedMap(new HashMap<>());

}