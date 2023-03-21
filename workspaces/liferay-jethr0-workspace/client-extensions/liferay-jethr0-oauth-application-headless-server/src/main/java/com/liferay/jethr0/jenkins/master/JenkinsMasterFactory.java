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

package com.liferay.jethr0.jenkins.master;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class JenkinsMasterFactory {

	public static JenkinsMaster newJenkinsMaster(JSONObject jsonObject) {
		long id = jsonObject.getLong("id");

		JenkinsMaster jenkinsMaster = null;

		synchronized (_jenkinsMasters) {
			if (_jenkinsMasters.containsKey(id)) {
				return _jenkinsMasters.get(id);
			}

			jenkinsMaster = new DefaultJenkinsMaster(jsonObject);

			_jenkinsMasters.put(jenkinsMaster.getId(), jenkinsMaster);
		}

		return jenkinsMaster;
	}

	public static void removeJenkinsMaster(JenkinsMaster jenkinsMaster) {
		if (jenkinsMaster == null) {
			return;
		}

		synchronized (_jenkinsMasters) {
			_jenkinsMasters.remove(jenkinsMaster.getId());
		}
	}

	private static final Map<Long, JenkinsMaster> _jenkinsMasters =
		Collections.synchronizedMap(new HashMap<>());

}