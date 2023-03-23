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

package com.liferay.jethr0.dalo;

import com.liferay.jethr0.environment.Environment;
import com.liferay.jethr0.environment.EnvironmentFactory;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class EnvironmentDALO extends BaseDALO {

	public Environment createEnvironment(
		String appServer, String batchName, String browser, String database,
		String javaVersion, Environment.LiferayBundle liferayBundle,
		Environment.LiferayPortalBranch liferayPortalBranch,
		String operatingSystem) {

		JSONObject requestJSONObject = new JSONObject();

		requestJSONObject.put(
			"appServer", appServer
		).put(
			"batchName", batchName
		).put(
			"browser", browser
		).put(
			"database", database
		).put(
			"javaVersion", javaVersion
		).put(
			"liferayBundle", liferayBundle.getJSONObject()
		).put(
			"liferayPortalBranch", liferayPortalBranch.getJSONObject()
		).put(
			"operatingSystem", operatingSystem
		);

		JSONObject responseJSONObject = create(requestJSONObject);

		if (responseJSONObject == null) {
			throw new RuntimeException("No response");
		}

		return EnvironmentFactory.newEnvironment(responseJSONObject);
	}

	public void deleteEnvironment(Environment environment) {
		if (environment == null) {
			return;
		}

		delete(environment.getId());

		EnvironmentFactory.removeEnvironment(environment);
	}

	public List<Environment> retrieveEnvironments() {
		List<Environment> environments = new ArrayList<>();

		for (JSONObject responseJSONObject : retrieve()) {
			environments.add(
				EnvironmentFactory.newEnvironment(responseJSONObject));
		}

		return environments;
	}

	public Environment updateEnvironment(Environment environment) {
		JSONObject responseJSONObject = update(environment.getJSONObject());

		if (responseJSONObject == null) {
			throw new RuntimeException("No response");
		}

		return environment;
	}

	@Override
	protected String getObjectDefinitionLabel() {
		return "Environment";
	}

}