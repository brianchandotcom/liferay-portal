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
import com.liferay.jethr0.build.run.BuildRun;
import com.liferay.jethr0.build.run.BuildRunFactory;

import java.net.URL;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class BuildRunDALO extends BaseDALO {

	public BuildRun createBuildRun(
		Build build, URL buildURL, long duration, BuildRun.Result result,
		BuildRun.State state) {

		JSONObject requestJSONObject = new JSONObject();

		requestJSONObject.put(
			"buildURL", buildURL
		).put(
			"duration", duration
		).put(
			"r_buildToBuildRuns_c_buildId", build.getId()
		).put(
			"result", result.getJSONObject()
		).put(
			"state", state.getJSONObject()
		);

		JSONObject responseJSONObject = create(requestJSONObject);

		if (responseJSONObject == null) {
			throw new RuntimeException("No response");
		}

		return BuildRunFactory.newBuildRun(build, responseJSONObject);
	}

	public void deleteBuildRun(BuildRun buildRun) {
		if (buildRun == null) {
			return;
		}

		delete(buildRun.getId());

		BuildRunFactory.removeBuildRun(buildRun);
	}

	public List<BuildRun> retrieveBuildRuns(Build build) {
		List<BuildRun> buildRuns = new ArrayList<>();

		for (JSONObject responseJSONObject : retrieve()) {
			buildRuns.add(
				BuildRunFactory.newBuildRun(build, responseJSONObject));
		}

		return buildRuns;
	}

	public BuildRun updateBuildRun(BuildRun buildRun) {
		JSONObject responseJSONObject = update(buildRun.getJSONObject());

		if (responseJSONObject == null) {
			throw new RuntimeException("No response");
		}

		return buildRun;
	}

	@Override
	protected String getObjectDefinitionLabel() {
		return "Build Run";
	}

}