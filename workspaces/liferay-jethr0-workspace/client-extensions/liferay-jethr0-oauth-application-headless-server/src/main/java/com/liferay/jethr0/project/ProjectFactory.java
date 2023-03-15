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

package com.liferay.jethr0.project;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class ProjectFactory {

	public static Project newProject(JSONObject jsonObject) {
		long id = jsonObject.getLong("id");

		synchronized (_projects) {
			if (_projects.containsKey(id)) {
				return _projects.get(id);
			}

			Project project = new DefaultProject(jsonObject);

			_projects.put(project.getId(), project);
		}

		return project;
	}

	public static void removeProject(Project project) {
		if (project == null) {
			return;
		}

		synchronized (_projects) {
			_projects.remove(project.getId());
		}
	}

	private static final Map<Long, Project> _projects =
		Collections.synchronizedMap(new HashMap<>());

}