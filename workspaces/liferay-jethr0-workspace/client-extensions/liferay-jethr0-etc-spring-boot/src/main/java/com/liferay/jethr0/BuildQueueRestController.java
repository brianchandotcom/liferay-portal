/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0;

import com.liferay.jethr0.bui1d.Build;
import com.liferay.jethr0.project.Project;
import com.liferay.jethr0.project.queue.ProjectQueue;

import org.json.JSONArray;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Michael Hashimoto
 */
@RequestMapping("/build-queue")
@RestController
public class BuildQueueRestController {

	@GetMapping
	public ResponseEntity<String> get(@AuthenticationPrincipal Jwt jwt) {
		JSONArray projectsJSONArray = new JSONArray();

		for (Project project : _projectQueue.getProjects()) {
			JSONObject projectJSONObject = project.getJSONObject();

			int completedBuilds = 0;
			int queuedBuilds = 0;
			int runningBuilds = 0;
			int totalBuilds = 0;

			for (Build build : project.getBuilds()) {
				if (build.getState() == Build.State.COMPLETED) {
					completedBuilds++;
				}
				else if (build.getState() == Build.State.RUNNING) {
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
	private ProjectQueue _projectQueue;

}