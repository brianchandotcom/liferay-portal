/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.job.controller;

import com.liferay.jethr0.bui1d.BuildEntity;
import com.liferay.jethr0.job.ProjectEntity;
import com.liferay.jethr0.job.queue.ProjectQueue;
import com.liferay.jethr0.job.repository.ProjectEntityRepository;

import org.json.JSONArray;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Michael Hashimoto
 */
@RequestMapping("/projects")
@RestController
public class ProjectRestController {

	@GetMapping("/{id}")
	public ResponseEntity<String> project(
		@AuthenticationPrincipal Jwt jwt, @PathVariable("id") int projectId) {

		ProjectEntity projectEntity = _projectEntityRepository.getById(
			projectId);

		JSONObject projectJSONObject = projectEntity.getJSONObject();

		JSONArray buildsJSONArray = new JSONArray();

		for (BuildEntity buildEntity : projectEntity.getBuildEntities()) {
			buildsJSONArray.put(buildEntity.getJSONObject());
		}

		projectJSONObject.put("builds", buildsJSONArray);

		return new ResponseEntity<>(
			projectJSONObject.toString(), HttpStatus.OK);
	}

	@GetMapping("/queue")
	public ResponseEntity<String> projectQueue(
		@AuthenticationPrincipal Jwt jwt) {

		JSONArray projectsJSONArray = new JSONArray();

		for (ProjectEntity projectEntity : _projectQueue.getProjectEntities()) {
			JSONObject projectJSONObject = projectEntity.getJSONObject();

			int completedBuilds = 0;
			int queuedBuilds = 0;
			int runningBuilds = 0;
			int totalBuilds = 0;

			for (BuildEntity buildEntity : projectEntity.getBuildEntities()) {
				if (buildEntity.getState() == BuildEntity.State.COMPLETED) {
					completedBuilds++;
				}
				else if (buildEntity.getState() == BuildEntity.State.RUNNING) {
					runningBuilds++;
				}
				else {
					queuedBuilds++;
				}

				totalBuilds++;
			}

			projectJSONObject.put(
				"completedBuilds", completedBuilds
			).put(
				"queuedBuilds", queuedBuilds
			).put(
				"runningBuilds", runningBuilds
			).put(
				"totalBuilds", totalBuilds
			);

			projectsJSONArray.put(projectJSONObject);
		}

		return new ResponseEntity<>(
			projectsJSONArray.toString(), HttpStatus.OK);
	}

	@Autowired
	private ProjectEntityRepository _projectEntityRepository;

	@Autowired
	private ProjectQueue _projectQueue;

}