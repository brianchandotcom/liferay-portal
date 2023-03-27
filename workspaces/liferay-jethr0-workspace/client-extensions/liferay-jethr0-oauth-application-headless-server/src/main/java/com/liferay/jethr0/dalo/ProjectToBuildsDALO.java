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
import com.liferay.jethr0.build.BuildFactory;
import com.liferay.jethr0.project.Project;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONObject;

import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class ProjectToBuildsDALO extends BaseRelationshipDALO {

	public JSONObject createRelationship(Project project, Build build) {
		return create("/o/c/projects", project.getId(), build.getId());
	}

	public void deleteRelationship(Project project, Build build) {
		delete("/o/c/projects", project.getId(), build.getId());
	}

	public List<Build> retrieveBuilds(Project project) {
		List<Build> builds = new ArrayList<>();

		for (JSONObject responseJSONObject :
				retrieve("/o/c/projects", project.getId())) {

			builds.add(BuildFactory.newBuild(project, responseJSONObject));
		}

		return builds;
	}

	public void updateRelationships(Project project) {
		List<Build> remoteBuilds = retrieveBuilds(project);

		for (Build build : project.getBuilds()) {
			if (remoteBuilds.contains(build)) {
				remoteBuilds.removeAll(Collections.singletonList(build));

				continue;
			}

			createRelationship(project, build);
		}

		for (Build remoteBuild : remoteBuilds) {
			deleteRelationship(project, remoteBuild);
		}
	}

	@Override
	protected String getObjectRelationshipName() {
		return "projectToBuilds";
	}

}