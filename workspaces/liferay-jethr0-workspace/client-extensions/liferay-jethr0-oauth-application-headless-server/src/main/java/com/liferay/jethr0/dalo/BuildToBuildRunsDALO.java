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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONObject;

import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class BuildToBuildRunsDALO extends BaseRelationshipDALO {

	public JSONObject createRelationship(Build build, BuildRun buildRun) {
		return create("/o/c/builds", build.getId(), buildRun.getId());
	}

	public void deleteRelationship(Build build, BuildRun buildRun) {
		delete("/o/c/builds", build.getId(), buildRun.getId());
	}

	public List<BuildRun> retrieveBuildRuns(Build build) {
		List<BuildRun> buildRuns = new ArrayList<>();

		for (JSONObject responseJSONObject :
				retrieve("/o/c/builds", build.getId())) {

			buildRuns.add(
				BuildRunFactory.newBuildRun(build, responseJSONObject));
		}

		return buildRuns;
	}

	public void updateRelationships(Build build) {
		List<BuildRun> remoteBuildRuns = retrieveBuildRuns(build);

		for (BuildRun buildRun : build.getBuildRuns()) {
			if (remoteBuildRuns.contains(buildRun)) {
				remoteBuildRuns.removeAll(Collections.singletonList(buildRun));

				continue;
			}

			createRelationship(build, buildRun);
		}

		for (BuildRun remoteBuildRun : remoteBuildRuns) {
			deleteRelationship(build, remoteBuildRun);
		}
	}

	@Override
	protected String getObjectRelationshipName() {
		return "buildToBuildRuns";
	}

}