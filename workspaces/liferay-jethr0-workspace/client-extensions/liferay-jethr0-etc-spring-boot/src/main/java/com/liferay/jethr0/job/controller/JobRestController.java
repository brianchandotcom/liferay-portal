/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.job.controller;

import com.liferay.jethr0.bui1d.BuildEntity;
import com.liferay.jethr0.bui1d.queue.BuildQueue;
import com.liferay.jethr0.bui1d.repository.BuildEntityRepository;
import com.liferay.jethr0.bui1d.repository.BuildParameterEntityRepository;
import com.liferay.jethr0.bui1d.run.BuildRunEntity;
import com.liferay.jethr0.jenkins.JenkinsQueue;
import com.liferay.jethr0.job.JobEntity;
import com.liferay.jethr0.job.dalo.JobEntityDALO;
import com.liferay.jethr0.job.queue.JobQueue;
import com.liferay.jethr0.job.repository.JobEntityRepository;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Michael Hashimoto
 */
@RequestMapping("/jobs")
@RestController
public class JobRestController {

	@PostMapping("/create")
	public ResponseEntity<String> createJob(
		@AuthenticationPrincipal Jwt jwt, @RequestBody String body) {

		JobEntity jobEntity = _jobEntityRepository.create(new JSONObject(body));

		for (JSONObject initialBuildJSONObject :
				jobEntity.getInitialBuildJSONObjects()) {

			BuildEntity buildEntity = _buildEntityRepository.create(
				jobEntity, initialBuildJSONObject);

			JSONArray buildParametersJSONArray =
				initialBuildJSONObject.optJSONArray("buildParameters");

			for (int i = 0; i < buildParametersJSONArray.length(); i++) {
				JSONObject buildParameterJSONObject =
					buildParametersJSONArray.getJSONObject(i);

				_buildParameterEntityRepository.create(
					buildEntity, buildParameterJSONObject);
			}
		}

		if (jobEntity.getState() == JobEntity.State.QUEUED) {
			_buildQueue.addJobEntity(jobEntity);

			_jenkinsQueue.invoke();
		}

		return new ResponseEntity<>(jobEntity.toString(), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<String> job(
		@AuthenticationPrincipal Jwt jwt, @PathVariable("id") int jobEntityId) {

		JobEntity jobEntity = _jobEntityRepository.getById(jobEntityId);

		JSONObject jobJSONObject = jobEntity.getJSONObject();

		return new ResponseEntity<>(jobJSONObject.toString(), HttpStatus.OK);
	}

	@GetMapping("/builds/{id}")
	public ResponseEntity<String> jobBuilds(
		@AuthenticationPrincipal Jwt jwt, @PathVariable("id") int jobEntityId) {

		JobEntity jobEntity = _jobEntityRepository.getById(jobEntityId);

		JSONArray buildsJSONArray = new JSONArray();

		for (BuildEntity buildEntity : jobEntity.getBuildEntities()) {
			JSONObject buildJSONObject = buildEntity.getJSONObject();

			List<BuildRunEntity> historyBuildRunEntities =
				buildEntity.getHistoryBuildRunEntities();

			if (!historyBuildRunEntities.isEmpty()) {
				BuildRunEntity latestBuildRunEntity =
					historyBuildRunEntities.get(
						historyBuildRunEntities.size() - 1);

				buildJSONObject.put(
					"latestJenkinsBuildURL",
					latestBuildRunEntity.getJenkinsBuildURL());
			}

			buildsJSONArray.put(buildJSONObject);
		}

		return new ResponseEntity<>(buildsJSONArray.toString(), HttpStatus.OK);
	}

	@GetMapping("/queue")
	public ResponseEntity<String> jobQueue(@AuthenticationPrincipal Jwt jwt) {
		JSONArray jobsJSONArray = new JSONArray();

		int position = 0;

		for (JobEntity jobEntity : _jobQueue.getJobEntities()) {
			if (jobEntity.getState() == JobEntity.State.COMPLETED) {
				continue;
			}

			position++;

			JSONObject jobJSONObject = jobEntity.getJSONObject();

			jobJSONObject.put("position", position);

			int completedBuilds = 0;
			int queuedBuilds = 0;
			int runningBuilds = 0;
			int totalBuilds = 0;

			for (BuildEntity buildEntity : jobEntity.getBuildEntities()) {
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

			jobJSONObject.put(
				"completedBuilds", completedBuilds
			).put(
				"queuedBuilds", queuedBuilds
			).put(
				"runningBuilds", runningBuilds
			).put(
				"totalBuilds", totalBuilds
			);

			jobsJSONArray.put(jobJSONObject);
		}

		return new ResponseEntity<>(jobsJSONArray.toString(), HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<String> jobs(@AuthenticationPrincipal Jwt jwt) {
		JSONArray jobsJSONArray = new JSONArray();

		for (JobEntity jobEntity : _jobEntityDALO.getAll()) {
			jobsJSONArray.put(jobEntity.getJSONObject());
		}

		return new ResponseEntity<>(jobsJSONArray.toString(), HttpStatus.OK);
	}

	@GetMapping("/types")
	public ResponseEntity<String> jobTypes(@AuthenticationPrincipal Jwt jwt) {
		JSONArray jobTypesJSONArray = new JSONArray();

		for (JobEntity.Type type : JobEntity.Type.values()) {
			jobTypesJSONArray.put(type.getJSONObject());
		}

		return new ResponseEntity<>(
			jobTypesJSONArray.toString(), HttpStatus.OK);
	}

	@Autowired
	private BuildEntityRepository _buildEntityRepository;

	@Autowired
	private BuildParameterEntityRepository _buildParameterEntityRepository;

	@Autowired
	private BuildQueue _buildQueue;

	@Autowired
	private JenkinsQueue _jenkinsQueue;

	@Autowired
	private JobEntityDALO _jobEntityDALO;

	@Autowired
	private JobEntityRepository _jobEntityRepository;

	@Autowired
	private JobQueue _jobQueue;

}