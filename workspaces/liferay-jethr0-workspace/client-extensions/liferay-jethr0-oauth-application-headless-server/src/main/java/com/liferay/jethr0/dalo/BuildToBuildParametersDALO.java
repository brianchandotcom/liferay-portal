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
import java.util.Collections;
import java.util.List;

import org.json.JSONObject;

import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class BuildToBuildParametersDALO extends BaseRelationshipDALO {

	public JSONObject createRelationship(
		Build build, BuildParameter buildParameter) {

		return create("/o/c/builds", build.getId(), buildParameter.getId());
	}

	public void deleteRelationship(Build build, BuildParameter buildParameter) {
		delete("/o/c/builds", build.getId(), buildParameter.getId());
	}

	public List<BuildParameter> retrieveBuildParameters(Build build) {
		List<BuildParameter> buildParameters = new ArrayList<>();

		for (JSONObject responseJSONObject :
				retrieve("/o/c/builds", build.getId())) {

			buildParameters.add(
				BuildParameterFactory.newBuildParameter(
					build, responseJSONObject));
		}

		return buildParameters;
	}

	public void updateRelationships(Build build) {
		List<BuildParameter> remoteBuildParameters = retrieveBuildParameters(
			build);

		for (BuildParameter buildParameter : build.getBuildParameters()) {
			if (remoteBuildParameters.contains(buildParameter)) {
				remoteBuildParameters.removeAll(
					Collections.singletonList(buildParameter));

				continue;
			}

			createRelationship(build, buildParameter);
		}

		for (BuildParameter remoteBuildParameter : remoteBuildParameters) {
			deleteRelationship(build, remoteBuildParameter);
		}
	}

	@Override
	protected String getObjectRelationshipName() {
		return "buildToBuildParameters";
	}

}