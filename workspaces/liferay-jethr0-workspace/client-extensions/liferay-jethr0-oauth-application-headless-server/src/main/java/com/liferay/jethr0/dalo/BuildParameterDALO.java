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

import com.liferay.jethr0.build.Build;
import com.liferay.jethr0.build.parameter.BuildParameter;
import com.liferay.jethr0.build.parameter.BuildParameterFactory;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class BuildParameterDALO extends BaseDALO {

	public BuildParameter createBuildParameter(
		Build build, String name, String value) {

		JSONObject requestJSONObject = new JSONObject();

		requestJSONObject.put(
			"name", name
		).put(
			"r_buildToBuildParameters_c_buildId", build.getId()
		).put(
			"value", value
		);

		JSONObject responseJSONObject = create(requestJSONObject);

		if (responseJSONObject == null) {
			throw new RuntimeException("No response");
		}

		return BuildParameterFactory.newBuildParameter(
			build, responseJSONObject);
	}

	public void deleteBuildParameter(BuildParameter buildParameter) {
		if (buildParameter == null) {
			return;
		}

		delete(buildParameter.getId());

		BuildParameterFactory.removeBuildParameter(buildParameter);
	}

	public List<BuildParameter> retrieveBuildParameters(Build build) {
		List<BuildParameter> buildParameters = new ArrayList<>();

		for (JSONObject responseJSONObject : retrieve()) {
			buildParameters.add(
				BuildParameterFactory.newBuildParameter(
					build, responseJSONObject));
		}

		return buildParameters;
	}

	public BuildParameter updateBuildParameter(BuildParameter buildParameter) {
		JSONObject responseJSONObject = update(buildParameter.getJSONObject());

		if (responseJSONObject == null) {
			throw new RuntimeException("No response");
		}

		return buildParameter;
	}

	@Override
	protected String getObjectDefinitionLabel() {
		return "Build Parameter";
	}

}