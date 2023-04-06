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

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class ProjectComparatorFactory {

	public static ProjectComparator newProjectComparator(
		ProjectPrioritizer projectPrioritizer, JSONObject jsonObject) {

		ProjectComparator.Type type = ProjectComparator.Type.get(
			jsonObject.getJSONObject("type"));

		if (type == ProjectComparator.Type.FIFO) {
			return new FIFOProjectComparator(projectPrioritizer, jsonObject);
		}
		else if (type == ProjectComparator.Type.PROJECT_PRIORITY) {
			return new PriorityProjectComparator(
				projectPrioritizer, jsonObject);
		}

		throw new UnsupportedOperationException();
	}

	public static ProjectComparator newProjectComparator(
		ProjectPrioritizer projectPrioritizer, long position,
		ProjectComparator.Type type, String value) {

		JSONObject jsonObject = new JSONObject();

		jsonObject.put(
			"position", position
		).put(
			"type", type.getJSONObject()
		).put(
			"value", value
		);

		return newProjectComparator(projectPrioritizer, jsonObject);
	}

}