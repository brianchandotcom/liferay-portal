/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0;

import com.liferay.jethr0.bui1d.Build;
import com.liferay.jethr0.project.Project;
import com.liferay.jethr0.project.queue.ProjectQueue;
import com.liferay.jethr0.project.repository.ProjectRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

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

			int queuedBuilds = 0;
			int runningBuilds = 0;
			int completedBuilds = 0;
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

			projectJSONObject.put("queuedBuilds", queuedBuilds);
			projectJSONObject.put("runningBuilds", runningBuilds);
			projectJSONObject.put("completedBuilds", completedBuilds);
			projectJSONObject.put("totalBuilds", totalBuilds);

			projectsJSONArray.put(projectJSONObject);
		}

		return new ResponseEntity<>(projectsJSONArray.toString(), HttpStatus.OK);
	}

	@Autowired
	private ProjectQueue _projectQueue;

	private static final Log _log = LogFactory.getLog(
		BuildQueueRestController.class);

}