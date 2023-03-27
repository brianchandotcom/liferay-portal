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

package com.liferay.jethr0.build;

import com.liferay.jethr0.project.Project;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class BuildFactory {

	public static Build newBuild(Project project, JSONObject jsonObject) {
		long id = jsonObject.getLong("id");

		Build build = null;

		synchronized (_builds) {
			if (_builds.containsKey(id)) {
				return _builds.get(id);
			}

			build = new DefaultBuild(project, jsonObject);

			_builds.put(build.getId(), build);
		}

		return build;
	}

	public static void removeBuild(Build build) {
		if (build == null) {
			return;
		}

		synchronized (_builds) {
			_builds.remove(build.getId());
		}
	}

	private static final Map<Long, Build> _builds = Collections.synchronizedMap(
		new HashMap<>());

}