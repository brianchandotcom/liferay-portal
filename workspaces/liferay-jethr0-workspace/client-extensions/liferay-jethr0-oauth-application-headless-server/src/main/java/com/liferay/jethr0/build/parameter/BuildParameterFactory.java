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

package com.liferay.jethr0.build.parameter;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.liferay.jethr0.build.Build;

/**
 * @author Michael Hashimoto
 */
public class BuildParameterFactory {

	public static BuildParameter newBuildParameter(
		Build build, JSONObject jsonObject) {

		long id = jsonObject.getLong("id");

		BuildParameter buildParameter = null;

		synchronized (_buildParameters) {
			if (_buildParameters.containsKey(id)) {
				return _buildParameters.get(id);
			}

			buildParameter = new DefaultBuildParameter(build, jsonObject);

			_buildParameters.put(buildParameter.getId(), buildParameter);
		}

		return buildParameter;
	}

	public static void removeBuildParameter(BuildParameter buildParameter) {
		if (buildParameter == null) {
			return;
		}

		synchronized (_buildParameters) {
			_buildParameters.remove(buildParameter.getId());
		}
	}

	private static final Map<Long, BuildParameter> _buildParameters =
		Collections.synchronizedMap(new HashMap<>());

}