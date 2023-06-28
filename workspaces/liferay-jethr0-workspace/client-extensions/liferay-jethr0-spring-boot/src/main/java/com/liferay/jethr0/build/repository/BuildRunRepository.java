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

package com.liferay.jethr0.build.repository;

import com.liferay.jethr0.build.Build;
import com.liferay.jethr0.build.dalo.BuildRunDALO;
import com.liferay.jethr0.build.dalo.BuildToBuildRunsDALO;
import com.liferay.jethr0.build.run.BuildRun;
import com.liferay.jethr0.entity.dalo.EntityDALO;
import com.liferay.jethr0.entity.repository.BaseEntityRepository;

import java.util.HashSet;
import java.util.Set;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class BuildRunRepository extends BaseEntityRepository<BuildRun> {

	public BuildRun add(Build build, BuildRun.State state) {
		JSONObject jsonObject = new JSONObject();

		if (state == null) {
			state = BuildRun.State.OPENED;
		}

		jsonObject.put(
			"r_buildToBuildRuns_c_buildId", build.getId()
		).put(
			"state", state.getJSONObject()
		);

		BuildRun buildRun = _buildRunDALO.create(jsonObject);

		buildRun.setBuild(build);

		build.addBuildRun(buildRun);

		return add(buildRun);
	}

	public Set<BuildRun> getAll(Build build) {
		Set<BuildRun> buildRuns = new HashSet<>();

		Set<Long> buildRunIds = _buildToBuildRunsDALO.getChildEntityIds(build);

		for (BuildRun buildRun : getAll()) {
			if (!buildRunIds.contains(buildRun.getId())) {
				continue;
			}

			build.addBuildRun(buildRun);

			buildRun.setBuild(build);

			buildRuns.add(buildRun);
		}

		return buildRuns;
	}

	@Override
	public EntityDALO<BuildRun> getEntityDALO() {
		return _buildRunDALO;
	}

	@Autowired
	private BuildRunDALO _buildRunDALO;

	@Autowired
	private BuildToBuildRunsDALO _buildToBuildRunsDALO;

}