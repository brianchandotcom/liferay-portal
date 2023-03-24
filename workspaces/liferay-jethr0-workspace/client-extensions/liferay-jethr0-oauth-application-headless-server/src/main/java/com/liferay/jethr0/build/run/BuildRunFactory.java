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

package com.liferay.jethr0.build.run;

import com.liferay.jethr0.build.Build;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class BuildRunFactory {

	public static BuildRun newBuildRun(Build build, JSONObject jsonObject) {
		long id = jsonObject.getLong("id");

		BuildRun buildRun = null;

		synchronized (_buildRuns) {
			if (_buildRuns.containsKey(id)) {
				return _buildRuns.get(id);
			}

			buildRun = new DefaultBuildRun(build, jsonObject);

			_buildRuns.put(buildRun.getId(), buildRun);
		}

		return buildRun;
	}

	public static void removeBuildRun(BuildRun buildRun) {
		if (buildRun == null) {
			return;
		}

		synchronized (_buildRuns) {
			_buildRuns.remove(buildRun.getId());
		}
	}

	private static final Map<Long, BuildRun> _buildRuns =
		Collections.synchronizedMap(new HashMap<>());

}