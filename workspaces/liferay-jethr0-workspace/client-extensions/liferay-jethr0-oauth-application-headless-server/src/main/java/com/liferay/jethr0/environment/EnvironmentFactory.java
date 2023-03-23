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

package com.liferay.jethr0.environment;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class EnvironmentFactory {

	public static Environment newEnvironment(JSONObject jsonObject) {
		long id = jsonObject.getLong("id");

		Environment environment = null;

		synchronized (_environments) {
			if (_environments.containsKey(id)) {
				return _environments.get(id);
			}

			environment = new DefaultEnvironment(jsonObject);

			_environments.put(environment.getId(), environment);
		}

		return environment;
	}

	public static void removeEnvironment(Environment environment) {
		if (environment == null) {
			return;
		}

		synchronized (_environments) {
			_environments.remove(environment.getId());
		}
	}

	private static final Map<Long, Environment> _environments =
		Collections.synchronizedMap(new HashMap<>());

}