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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class ProjectPrioritizerFactory {

	public static ProjectPrioritizer newProjectPrioritizer(
		JSONObject jsonObject) {

		long id = jsonObject.getLong("id");

		synchronized (_projectPrioritizers) {
			if (_projectPrioritizers.containsKey(id)) {
				return _projectPrioritizers.get(id);
			}

			ProjectPrioritizer projectPrioritizer =
				new DefaultProjectPrioritizer(jsonObject);

			_projectPrioritizers.put(
				projectPrioritizer.getId(), projectPrioritizer);
		}

		return projectPrioritizer;
	}

	public static void removeProjectPrioritizer(
		ProjectPrioritizer projectPrioritizer) {

		if (projectPrioritizer == null) {
			return;
		}

		synchronized (_projectPrioritizers) {
			_projectPrioritizers.remove(projectPrioritizer.getId());
		}
	}

	private static final Map<Long, ProjectPrioritizer> _projectPrioritizers =
		Collections.synchronizedMap(new HashMap<>());

}